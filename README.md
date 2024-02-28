# Shop Showcase

## Overview

This Android Kotlin project is a shopping cart app that allows users to add items to a cart, update the cart, and proceed to checkout. The app utilizes Room Database to store and manage product data efficiently. This README file provides instructions on setting up and running the project.

## Prerequisites

Before setting up the project, ensure that you have the following prerequisites installed on your development machine:

- Android Studio (Version 4.0 or later)
- Kotlin plugin for Android Studio

## Download

You can download a prebuilt version of the app from the releases.
[Recent release](https://github.com/sevenreup/shop-showcase/releases/tag/1.0)

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

## Room Database Setup

The app uses Room Database to handle product data storage. The data is prepopulated using the **DatabaseSeederWorker** which reads a json file with all the products **`app/src/main/assets/seed-data.json`**

## Features

- **Add Items to Cart**: Users can add items to their shopping cart.
- **Update Cart**: Users can update the quantity of items in the cart.
- **Checkout**: Users can proceed to checkout and complete their purchase.
- **Google sign** Users can login using google


## Design Decisions for the Shopping Basket Feature

The shopping basket feature is designed to provide a seamless and intuitive shopping experience. Key design decisions include:

1. **Room Database for Cart Storage:**
   - Utilizing Room Database ensures a persistent and efficient storage solution for the user's shopping cart.
   - Entities, DAOs, and the database are structured to manage cart-related data effectively.

2. **Kotlin flows for UI Updates:**
   - Flows is used to observe changes in the cart data, providing real-time updates to the UI.
   - This ensures a responsive user interface as users add or remove items from their shopping cart.

## Challenges and Solutions
### Challenge: No Backend API for Data
Lack of a backend API for product data, requiring local seeding.
#### Solution:
Create a local JSON file containing the required product data.
Implement a data seeding mechanism to populate the Room Database with initial data from the JSON file.
Ensure that the seeding process is executed upon the first launch of the app.

## Making the App Offline-First

To make the app offline-first, consider the following strategies:

1. **Local Data Storage:**
   - Utilize Room Database to store essential data locally on the device.
   - Implement caching mechanisms to ensure that critical data is available even without an internet connection.

2. **Offline Mode for Cart Operations:**
   - Allow users to add items to the cart and perform essential operations offline.
   - Queue these operations and synchronize with the server when the internet connection is restored.

3. **Smart Caching for Product Data:**
   - Implement smart caching strategies to prefetch and store product data that is likely to be accessed by users, optimizing the offline experience.
   - We have to make sure the cart cache is refetch when checkout to make sure the data is collect

4. **Informative UI for Connectivity Status:**
   - Provide clear indications to users about their connectivity status.
   - Display offline-friendly UI elements and messages when the app is in offline mode.