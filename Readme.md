# Flood Fill App

This Android application demonstrates the flood fill algorithm using both View-based and Compose UI implementations. Users can interact with a grid, selecting cells to "flood fill" with a chosen color, visually updating connected regions of the same color.

## Features

- **Flood Fill Algorithm:** Implements the flood fill algorithm to change the color of connected regions in a grid.
- **View-based Implementation:** Utilizes traditional Android Views and RecyclerView for the grid display.
- **Compose UI Implementation:**  Employs Jetpack Compose for a modern, declarative UI approach.
- **Color Selection:** Offers flexibility to choose different colors for the flood fill operation.

## Techniques Used

- **Flood Fill Algorithm:** The core algorithm for color filling connected regions.
- **RecyclerView (View-based):** Efficiently displays a large grid of items.
- **Data Binding (View-based):** Binds UI components in the View-based implementation to data sources.
- **Jetpack Compose:**  Modern declarative UI toolkit for building native Android UIs.
- **State Management (Compose):** Manages the grid state and color selection within the Compose UI.
- **Kotlin:** The primary programming language for the application.

## Dependencies

- **Core Libraries:**
    - `androidx.core:core-ktx`
    - `androidx.appcompat:appcompat`
    - `com.google.android.material:material`
    - `androidx.constraintlayout:constraintlayout`

- **View-based Implementation:**
    - `androidx.recyclerview:recyclerview`
    - `androidx.databinding:viewbinding`

- **Compose UI Implementation:**
    - `androidx.activity:activity-compose`
    - `androidx.compose.ui:ui`
    - `androidx.compose.material3:material3`

- **Lifecycle Management:**
    - `androidx.lifecycle:lifecycle-runtime-ktx`

- **Testing (Optional):**
    - `junit:junit`
    - `androidx.test.ext:junit`
    - `androidx.test.espresso:espresso-core`

## Color Flexibility

The application allows users to select from a variety of colors to use for the flood fill operation. This flexibility is achieved by:

- **Color Palette:**  A set of predefined colors is available for the user to choose from.
- **Dynamic Color Application:** The selected color is passed to the flood fill algorithm, which updates the grid's underlying data, triggering a UI refresh to reflect the change.

## Getting Started

1. **Clone the repository:**  `git clone <repository_url>`
2. **Open in Android Studio:** Import the project into Android Studio.
3. **Build and Run:** Build the project and run it on an emulator or a physical device.

TODO:
1. add compose version
2. fix weird main screen overlay
3. allow user to specify grid size
4. allow user to specify color using slider