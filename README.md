# Final Project: Weather

An Android application that allows the user to view current weather data and an hourly forecast for
a variety of locations.

By Samuel Schumacher and Cristian Pedraza.

### GitHub Link

The GitHub link for this project is https://github.com/sam-schu/cs4520-final-weather.

### Getting Started

To run the project, simply open it in Android Studio, choose a device to emulate (this project was
tested primarily with an emulated Pixel 5), and press the Run button at the top of the window. The
UI components in the app, as well as the device's back button, can be used for navigation.

### Project Overview

This is a single-activity application with four screens. The home screen displays basic current
weather data about the currently selected location. The additional details screen includes more
detailed weather information than can be displayed on the home screen. The location screen allows
the user to change the location for which weather data is displayed, and the hourly forecast screen
displays hourly weather for the next 48 hours for the selected location. Times are displayed in the
selected location's local time. All weather data is obtained from the OpenWeather API.

### Project Structure

The source code for the project is divided into three packages under the com.samschu.cs4520.weather
package: model, view, and viewmodel. The model package includes files needed for API requests and
offline data storage using Room. The view package includes UI and navigation code, and the viewmodel
package includes the view model used by the project to store data. Tests are also included.