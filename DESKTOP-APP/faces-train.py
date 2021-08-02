import face_recognition
import pickle
import os
import pathlib

# Initialize some variables
all_face_encodings = {}
known_face_images = []

app_path = str(pathlib.Path(__file__).parent.absolute())

def getImageOfUserFromDir(dirPath):
    for root, dirs, files in os.walk(dirPath):
        path = root.split(os.sep)
        for file in files:
            known_face_images.append(str(file))

getImageOfUserFromDir(app_path + "\\images\\")

for image_names in known_face_images:
    image = face_recognition.load_image_file(f".\images\{image_names}")
    all_face_encodings[image_names[0: str(image_names).rfind(".")]] = face_recognition.face_encodings(image)[0]


with open('dataset_faces.dat', 'wb') as f:
    pickle.dump(all_face_encodings, f)