# Shop Shwocase

## Overview

This Android Kotlin project is a shopping cart app that allows users to add items to a cart, update the cart, and proceed to checkout. The app utilizes Room Database to store and manage product data efficiently. This README file provides instructions on setting up and running the project.

## Prerequisites

Before setting up the project, ensure that you have the following prerequisites installed on your development machine:

- Android Studio (Version 4.0 or later)
- Kotlin plugin for Android Studio

## Download

You can download a prebuilt version of the app from the releases

## Getting Started

Follow the steps below to set up and run the project on your local machine:

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/sevenreup/shop-showcase.git
   ```

2. **Open Project in Android Studio:**

   Open Android Studio and select "Open an existing Android Studio project." Navigate to the location where you cloned the repository and select the project folder.

3. **Sync Gradle:**

   After opening the project, wait for Android Studio to sync the Gradle files. This may take a few moments.

4. **Configure Firebase:**

   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new project or use an existing one.
   - Add an Android app to your Firebase project and follow the setup instructions to download the `google-services.json` file.
   - Place the `google-services.json` file in the `app` directory of your project.

5. **Enable Google Sign-In:**

   - In the Firebase Console, navigate to "Authentication" > "Sign-in method."
   - Enable Google as a sign-in provider and configure the necessary details.

6. **Run the App:**

   Connect an Android device or use an emulator, and click the "Run" button in Android Studio to install and launch the app.

## Setup Google sign-in

## Room Database Setup

The app uses Room Database to handle product data storage. The data is prepopulated using the **DatabaseSeederWorker** which reads a json file with all the products **`app/src/main/assets/seed-data.json`**

## Features

- **Add Items to Cart**: Users can add items to their shopping cart.
- **Update Cart**: Users can update the quantity of items in the cart.
- **Checkout**: Users can proceed to checkout and complete their purchase.
- **Google sign** Users can login using google