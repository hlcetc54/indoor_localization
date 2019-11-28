# Indoor Localization

## Description
This is Mobile Application which uses Machine Learning to detect an indoor location.
The app specifically made for indoor localization at UNIST university, 106 building, 2nd floor.
My app reads WIFI RSSI signals then makes classification using Deep Learning to detect the location.

## Data Collection
The biggest challenge in this project was a data collection. 
In fact, my phone detects up to 130 different WIFI signals, thus I had to choose most important signals.
So I have picked 12 WIFI signals that can be received from any point on the 2nd floor.
I have made an [wifiinfo](https://github.com/kanybekasanbekov/indoor_localization/tree/master/wifiinfo) app which receives those 12 signals and saves them in a txt file as an array.
Data was collected from 23 location, image attached below.
Dataset can be found in [WIFI_DATA](https://github.com/kanybekasanbekov/indoor_localization/tree/master/WIFI_DATA) folder.
In total approximately 4600 datapoints were collected.

<p align="center">
    <img src="./assets/image1.jpg" width="600"/>
</p>

## Train
Before training, I had to manage the data: divide into train and test sets, save the data in approriate numpy folder.
The code for that is in [data_management](https://github.com/kanybekasanbekov/indoor_localization/blob/master/data_management.py) file.

When data is prepaired it is time for [training](https://github.com/kanybekasanbekov/indoor_localization/blob/master/localization_NN.py).
The model built with [Tensorflow 1.12.0](https://www.tensorflow.org/) and it's [Keras 2.2.4](https://keras.io/) library.
"Adam" optimizer and "Categorical Crossentropy" loss were used for training.
After training, trained model was saved and converted into tflite version in Google Collab.

## Test 
When model was ready, it was deployed into mobile app.
So for testing, whenever you want to know your location you should press on a button then app shows your position on a map.
As you move in a building, app reads new WIFI signals, makes inference and shows your new position.
Moreover, the app can draw your moving path as in a photo below.

<p align="center">
    <img src="./assets/image2.png" width="600"/>
</p>