# Emp Search App

The Emp Search app is a simple Android app that allows you to search for employees by name or job title. It uses the Kotlin Streams API to fetch employee data from a JSON file hosted on a remote server, and provides a simple search interface to allow you to find employees quickly and easily.

<img src="https://user-images.githubusercontent.com/76834976/235994675-79ec9c63-f190-48c7-ae87-4f55be8b823f.png" width="200" />

## Features

- Search for employees by name or job title
- View employee details, including name, job title, email, phone number, and profile picture
- Lazy loading of employee data using Kotlin Streams API

## Getting Started

To use the Emp Search app, you'll need an Android device running Android 5.0 (Lollipop) or higher. You can download the app from the Google Play Store or build it from source.

### Building from Source

To build the Emp Search app from source, follow these steps:

1. Clone the repository:

   ```
   git clone https://github.com/aiyu-ayaan/EmpSearch/
   ```

2. Open the project in Android Studio.

3. Build the project by selecting "Build > Make Project" from the menu.

4. Run the app on an Android emulator or physical device by selecting "Run > Run app" from the menu.

5. Once the app is running, you can search for employees by entering a name or job title in the search bar.

## Technical Details

The Emp Search app is built using Kotlin and the Android SDK. It uses the Kotlin Streams API to fetch employee data from a remote JSON file hosted on GitHub Pages. The app includes the following classes:

- `MainActivity`: The main activity of the app, which contains the search bar and search results.
- `EmpAdapter`: A RecyclerView adapter that displays a list of employees.
- `EmpViewHolder`: A RecyclerView.ViewHolder that displays an individual employee item in the list.
- `EmpResponseItem`: A data class that represents an individual employee.

The app uses the following libraries:

- `GSON Converter`: A library for serializing and deserializing Kotlin data classes to and from JSON.
- `Retrofit`: A library for making HTTP requests and handling responses.


## Credits

The employee data used in this app is taken from [Dummy JSON](https://aiyu-ayaan.github.io/Dummy-JSON/), a tool for generating realistic test data in JSON format.
