# -*- coding: utf-8 -*-
import socket
import detect
import os
import shutil
import Recommendation
import recommendationByColor
from collections import deque
from PIL import Image
import sys

host = ''
port = 12125

def get_bytes_stream(sock, length):
    buf = b''
    try:
        step = length
        while True:
            data = sock.recv(step)
            buf += data
            if len(buf) == length:
                break
            elif len(buf) < length:
                step = length - len(buf)
    except Exception as e:
        print(e)
    return buf[:length]

def send_img(sock, img_path):
    img_f = open(img_path, "rb")
    data = img_f.read()
    data_len = len(data)
    sock.sendall(data_len.to_bytes(4, byteorder="big"))
    print("data length:", data_len)
    
    step = 1024
    loop = int(data_len/step)+1
    
    while len(data) > 0:
        if len(data) < step:
            sock.sendall(data)
            data = []
        else:
            sock.sendall(data[:step])
            data = data[step:]
    
    img_f.flush()
    img_f.close()
    
def write_utf8(s, sock):
    encoded = s.encode(encoding='utf-8')
    sock.sendall(len(encoded).to_bytes(4, byteorder="big"))
    sock.sendall(encoded)
    
    
server_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_sock.bind((host, port))
server_sock.listen(10)
result = ''
idx = 0
while True:
    idx += 1
    print("waiting")
    client_sock, addr = server_sock.accept()

    #이미지 수신
    len_bytes_string = bytearray(client_sock.recv(1024))[2:]
    len_bytes = len_bytes_string.decode("utf-8")
    length = int(len_bytes)
    os.chdir("/home/gw8/yolov5")
    img_bytes = get_bytes_stream(client_sock, length)
    img_path = "room_img_from_server/img.jpg"
    
    with open(img_path, "wb") as writer:
        writer.write(img_bytes)
    print(img_path+" is saved")
    img_name="img.jpg"
    
    category_string = bytearray(client_sock.recv(1024))[2:]
    category_bytes = category_string.decode("utf-8")
    category = list(map(int,category_bytes.split()))
    print(category)
    
    """preRate_string = bytearray(client_sock.recv(1024))[2:]
    preRate_bytes = preRate_string.decode("utf-8")
    preRate = list(map(int,preRate_bytes.split()))"""

    
    ### yolo + 색 추출 실행시키는 
    if os.path.exists("./runs/detect/exp"):
        shutil.rmtree("./runs/detect/exp") 
    detect.run()
    
    # color recommendation

    if os.path.exists("./runs/detect/exp/crops"):
            shutil.rmtree("foreground")
            os.mkdir("foreground")
            tone, color = Recommendation.run()
    else:
        print("There is no furniture")
        write_utf8("-1", client_sock)
        client_sock.close()
        continue

        

    #histo_similarity = str(color_histogram_hsv.run())   #히스토그램 유사도
    recommendList = recommendationByColor.run(tone, color, category,"COLDSOFT")
    
    #이미지 전송은 차후에
    
    #img_file_name = "img.jpg"
    #img_path = "runs/detect/exp/"+img_file_name
    #send_img(client_sock, img_path)
    
   #write_utf8(str(color1_h), client_sock);write_utf8(str(color1_s), client_sock);write_utf8(str(color1_v), client_sock);write_utf8(str(color2_h), client_sock);write_utf8(str(color2_s), client_sock);write_utf8(str(color2_v), client_sock);write_utf8(str(color3_h), client_sock);write_utf8(str(color3_s), client_sock);write_utf8(str(color3_v), client_sock)
    #write_utf8(histo_similarity, client_sock)
    write_utf8(str(tone), client_sock)
    write_utf8(str(color), client_sock)
    
    def listToString(str_list):
        result = ""
        for s in str_list:
            result += str(s) + " "
        return result.strip()

    for value in recommendList.values():
        writeTmp = listToString(value)
        write_utf8(writeTmp, client_sock)
        
    
    print("Done")
    client_sock.close()
    

server_sock.close()




