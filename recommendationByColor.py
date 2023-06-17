import os
import random

def extendedSearch(furniture, tone, recommendList, index, num):
    with open(furniture[index] + "_" + tone + ".txt", 'r', encoding='utf-8') as f:
        lines = f.readlines()
        
        # 이미 포함된 elements 삭제(중복방지)
        for i in range(6-num):
            lines.remove(recommendList[index][i])     
        length = len(lines)
        
        #톤이 6개 이상이면 random choose 정상적으로 실행
        if length >= num:
            recommendList[index] += random.sample(lines, num)
        
        # 톤 조차 6개 미만이면,,, 비슷한 톤 추천
        else:
            recommendList[index] += random.sample(lines, length)
            with open(furniture[index] + "_COLDSOFT.txt", 'r', encoding='utf-8') as g:
                glines = g.readlines()
          
                recommendList[index] += random.sample(glines, num-length)
    return recommendList
            
def recommendationByPreRate(furniture, tone, recommendList, index):
    with open(furniture[index] + "_" + tone + ".txt", 'r', encoding='utf-8') as f:
        lines = f.readlines()
        length = len(lines)
        
        #톤이 4개 이상이면 random choose 정상적으로 실행
        if length >= 4:
            recommendList[index] += random.sample(lines, 4 )
        
        # 톤 조차 4개 미만이면 비슷한 톤 추천
        else:
            recommendList[index] += random.sample(lines, length)
            with open(furniture[index] + "_COLDSOFT.txt", 'r', encoding='utf-8') as g:
                glines = g.readlines()
          
                recommendList[index] += random.sample(glines, 4-length)
    return recommendList
            
def run(tone, color, category, preRate) :
    recommendList = {}
    
    # 카테고리별로 loop 돌리기 0: bed 1: chair 2: closet 3: curtain 4: desk 5: lamp 6: shelf 7: sofa 8: table
    furniture = ["bed", "chair", "closet", "curtain", "desk", "lamp", "shelf", "sofa", "table"]
    for _, index in enumerate(category):
        os.chdir('/home/gw8/yolov5/data/furnitures/'+furniture[index])
        recommendList[index] = []
        
        
        #해당 컬러부터 탐색
        with open(furniture[index] + "_" + color + ".txt", 'r', encoding='utf-8') as f:
            lines = f.readlines()
            length = len(lines)
            
         
            # 해당 컬러 가구가 6대 이상 일때 -> random choose
            if length >= 6:
                recommendList[index] = random.sample(lines, 6)
            
            # 10대 미만 일때 -> 추천 범위 넓게 확장
            else:
                recommendList[index] = random.sample(lines, length)
                recommendList = extendedSearch(furniture, tone, recommendList, index, 6-length)
            # preRate 기반 4대 추천
            recommendList = recommendationByPreRate(furniture, preRate, recommendList, index)
            

    # \n 제거
    for _, index in enumerate(category):
        recommendList[index] = [l.strip() for l in recommendList[index]]


    for key, value in recommendList.items():
        print(key,value)
        
    return recommendList
            
            

        
        
        
        
        
if __name__ == '__main__':
    run("WARMHARD","Elaborate",[0,1,4],"COLDSOFT") 
    