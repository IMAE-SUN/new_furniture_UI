import cv2
import numpy as np
import matplotlib.pyplot as plt


"3차원"
# # 이미지 파일 읽어오기
# img1 = cv2.imread('grad_proj\images\\bedroom.jpg')
# img2 = cv2.imread('grad_proj\images\\bedroom6.jpg')

# # 이미지를 HSV 색상 공간으로 변환
# hsv1 = cv2.cvtColor(img1, cv2.COLOR_BGR2HSV)
# hsv2 = cv2.cvtColor(img2, cv2.COLOR_BGR2HSV)

# # 히스토그램 계산에 사용할 채널 지정
# channels = [0, 1, 2]

# # 히스토그램 계산에 사용할 색상 범위 지정
# histSize = [180, 256, 256]
# ranges = [0, 180, 0, 256, 0, 256]

# # 히스토그램 계산
# hist1 = cv2.calcHist([hsv1], channels, None, histSize, ranges)
# hist2 = cv2.calcHist([hsv2], channels, None, histSize, ranges)

# # 히스토그램 정규화
# hist1_norm = cv2.normalize(hist1, hist1, alpha=0, beta=1, norm_type=cv2.NORM_MINMAX)
# hist2_norm = cv2.normalize(hist2, hist2, alpha=0, beta=1, norm_type=cv2.NORM_MINMAX)

# # 히스토그램 비교
# result = cv2.compareHist(hist1_norm, hist2_norm, cv2.HISTCMP_CORREL)

# print(result)

"2차원"
import cv2
import numpy as np

def run():       
    # 이미지 읽어오기
    img1 = cv2.imread('room_img_from_server/img.jpg')
    img2 = cv2.imread('room_img_from_server/img.jpg')

    # 히스토그램 계산을 위한 hsv 변환
    hsv_img1 = cv2.cvtColor(img1, cv2.COLOR_BGR2HSV)
    hsv_img2 = cv2.cvtColor(img2, cv2.COLOR_BGR2HSV)

    # 2차원 히스토그램 계산
    hist_img1 = cv2.calcHist([hsv_img1], [0, 1], None, [180, 256], [0, 180, 0, 256])
    hist_img2 = cv2.calcHist([hsv_img2], [0, 1], None, [180, 256], [0, 180, 0, 256])

    # 히스토그램 정규화
    cv2.normalize(hist_img1, hist_img1, alpha=0, beta=1, norm_type=cv2.NORM_MINMAX)
    cv2.normalize(hist_img2, hist_img2, alpha=0, beta=1, norm_type=cv2.NORM_MINMAX)

    # 히스토그램 유사도 계산
    similarity = cv2.compareHist(hist_img1, hist_img2, cv2.HISTCMP_CORREL)

    # 유사도 출력
    print('Similarity: ', similarity)
    return similarity



if __name__ == '__main__':
    run()
    
"""
메소드는 4개 해봤지만.... 만족스러운것 없었음.
그 이유는 예상컨데.. rgb 히스토그램을 그려보면 애초에 좀 많이 다름.
예상되는 문제로는

1.HSV 히스토그램을 찾아서 차이가 있나 살펴보기
2. 차이가 있다면.... 음... 
3. 차이가 만약 별로 없다! 면 계속 다른 방법을 찾아봐야겠지,,
"""