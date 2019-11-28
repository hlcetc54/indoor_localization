#First we upload data from numpy files, make some modifications(divide into X and Y, convert to one hot vector)
#After that network is built and trained

from __future__ import print_function

import numpy as np
import matplotlib.pyplot as plt
import matplotlib
import tensorflow as tf
from tensorflow.contrib import lite
import keras
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import Conv1D
from keras.layers import Dropout
from keras.layers import MaxPooling1D
from keras.layers import Flatten
from keras.utils import to_categorical
from sklearn.metrics import accuracy_score

trainX = []
trainY = []
testX = []
testY = []

test_data = np.load('test.npy',allow_pickle=True)
train_data = np.load('train.npy',allow_pickle=True)

for i in range(0,len(train_data)):
	trainX.append(train_data[i][0])
	trainY.append(train_data[i][1])

for i in range(0,len(test_data)):
	testX.append(test_data[i][0])
	testY.append(test_data[i][1])

_trainY = trainY
_testY = testY
trainY = to_categorical(trainY)
testY = to_categorical(testY)

#count_classes = testY.shape[1]
#print(count_classes)

trainX = np.asarray(trainX)
testX = np.asarray(testX)

#print("\n\n*********************************************")
#print(testX)
#print(_testY)
#print("*********************************************\n\n")

#print("testX[0]", testX[0])


print("\nThe number of training data:",len(trainX)) #4234
print("The number of testing data:",len(testX)) #460
print("\n")

"""
print("Here is trainX:")
print(trainX)

print("\nHere is trainY:")
print(trainY)
"""

model = Sequential()
model.add(Dense(500, activation='relu', input_dim=12))
model.add(Dense(400, activation='relu'))
model.add(Dense(300, activation='relu'))
model.add(Dense(200, activation='relu'))
model.add(Dense(100, activation='relu'))
model.add(Dense(50, activation='relu'))
model.add(Dense(24, activation='softmax'))
# Compile the model
model.compile(optimizer='adam', 
              loss='categorical_crossentropy', 
              metrics=['accuracy'])
model.fit(trainX, trainY, epochs=50)


scores = model.evaluate(trainX, trainY, verbose=0)
print('\nAccuracy on training data: {}% \nError on training data: {}\n'.format(scores[1], 1 - scores[1]))  

scores2 = model.evaluate(testX, testY, verbose=0)
print('Accuracy on test data: {}% \nError on test data: {}\n'.format(scores2[1], 1 - scores2[1]))     

#predicted = model.predict(np.array([testX[0],]))
#predicted = model.predicted(testX)
#predicted = np.argmax(predicted, axis=1)

#print("The shape of predicted is:",predicted.shape)
#print(predicted)
#print(predicted)
#print(_testY)
#print(accuracy_score(_testY, predicted))

"""
print("Here is testX")
print(testX)

print("\n\n")
print("Here is testY")
print(testY)
"""

keras_file = "indoor2_model.h5"
keras.models.save_model(model, keras_file)


"""
Used the following script to convert model to tflite version on google collab
import tensorflow as tf
converter = tf.lite.TFLiteConverter.from_keras_model_file( 'indoor_model.h5')
tfmodel = converter.convert()
open ("model.tflite" , "wb") .write(tfmodel)
"""
