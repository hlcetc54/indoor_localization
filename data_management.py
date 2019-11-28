#This code is used to extract data from txt file
#and put it into numpy file as list of int
import numpy as np 
import random
from random import shuffle

train = []
test = []

for i in range(1,24):
	suffix = str(i)
	filename = ("WIFI_DATA"+suffix+".txt")
	input_file = open(filename,"r")
	print(filename, " is opened")

	if input_file.mode == 'r':
		#content = input_file.read()
		lines = input_file.readlines()
		dataset = []
			
		for x in lines:
			new_list = list(x.split(" "))
			new_list[0] = new_list[0][1:]
			new_list[11] = new_list[11][:3]

			for j in range(0,11):
				new_list[j] = new_list[j][:3]

			#print("DATA, for it is STRING")
			#print(new_list)
			new_list = map(int, new_list)
			#Need to delete the following line if it fails
			#new_list = np.asarray(new_list)
			datapoint = [new_list,i]	
			dataset.append(datapoint)
			#print(datapoint)

		#print(dataset)
		shuffle(dataset)
		test = test + dataset[:10]
		train = train +dataset[11:]
		#test.append(dataset[:10])
		#train.append(dataset[11:])
	
print("\n")

for i in range(1,24):
	suffix = str(i)
	filename = ("WIFI_DATA"+suffix+".txt")
	input_file = open(filename,"r")
	print(filename, " is opened")

	if input_file.mode == 'r':
		#content = input_file.read()
		lines = input_file.readlines()
		dataset = []
			
		for x in lines:
			new_list = list(x.split(" "))
			new_list[0] = new_list[0][1:]
			new_list[11] = new_list[11][:3]

			for j in range(0,11):
				new_list[j] = new_list[j][:3]

			#print("DATA, for it is STRING")
			#print(new_list)
			new_list = map(int, new_list)
			#Need to delete the following line if it fails
			#new_list = np.asarray(new_list)
			datapoint = [new_list,i]	
			dataset.append(datapoint)
			#print(datapoint)

		#print(dataset)
		shuffle(dataset)
		test = test + dataset[:10]
		train = train +dataset[11:]	
		

shuffle(test)
shuffle(train)

#test = np.asarray(test)
#print(test)
np.save("train.npy", train)
np.save("test.npy", test)
