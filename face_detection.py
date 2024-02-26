import os
import numpy as np
from numpy import asarray
import cv2 as cv
from PIL import Image
from os.path import isdir
from mtcnn.mtcnn import MTCNN


detector = MTCNN()

def extract_face (filename, required_size=(160,160)):

    image = cv.imread(filename)
    image = cv.cvtColor(image, cv.COLOR_BGR2RGB)

    pixels = asarray(image)
    results = detector.detect_faces(pixels)
    if len(results) == 0:
        return None
    x1,y1,width, height = results[0]['box']
    x1,y1 = abs(x1), abs(y1)
    x2,y2 = x1+width, y1+height

    
    face = pixels[y1:y2,x1:x2]
    face = cv.cvtColor(face,cv.COLOR_BGR2RGB)
    
    # image = image.resize(required_size)
    image = Image.fromarray(face)
    image = image.resize(required_size)
    face_array = asarray(image) 
    return face_array

def extract_face_img(image, required_size=(160,160)):
    faces = list()
    retangles = list()
    image = cv.cvtColor(image, cv.COLOR_BGR2RGB)

    pixels = asarray(image)
    results = detector.detect_faces(pixels)
    print('result : ',len(results))
    for i in range(len(results)):
        x1,y1,width, height = results[i]['box']
        x1,y1 = abs(x1), abs(y1)
        x2,y2 = x1+width, y1+height
        retangles.append({"x1":x1,"y1":y1,"x2":x2,"y2":y2})
        face = pixels[y1:y2,x1:x2]
        image = Image.fromarray(face)
        image = image.resize(required_size)
        face_array = asarray(image) 
        faces.append(face_array)
    return faces, retangles

def load_faces(directory):
    faces = list()
    for filename in os.listdir(directory):
        path = directory+'/'+filename
        face = extract_face(path)

        if face is None :
            print("ko thay khuon mat")
            continue
         
        faces.append(face)
    return faces
def load_dataset(directory):
    X, y = list(), list()
    for subdir in os.listdir(directory):
        path = directory+'/'+subdir
        if not isdir(path):
            continue
        faces = load_faces(path)
        labels = [subdir for _ in range(len(faces))]
        X.extend(faces)
        y.extend(labels)
    return asarray(X),asarray(y) 


# trainX, trainy =load_dataset('dataset/PicTrain')
# testX, testy = load_dataset('dataset/PicTest')
# np.savez_compressed('init_face_array.npz',trainX, trainy, testX, testy)
