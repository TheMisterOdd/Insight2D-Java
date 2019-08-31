# Insight Engine, free and OpenSource Engine. Using OpenGL
### 2D and 3D cross-platform game engine
Insight Engine is a **C++**, multiplatform game engine to create 2D and 3D games. It provides a varaity of new classes to handle different aspects like **window, input, model loading, shaders, audio...**

Unfortunately we have not managed to port the engine to Android or HTML, nor translate it into any other language, we will try this in later versions.

### Free and open source
Insight is completely free and open source under GNU General Public License. No strings attached, no royalties, nothing. The users' games are theirs, down to the last line of engine code. We don't want to make profit from this project.

### Example code
```cpp
#include "Window.h"

int main(void) {
 
   Window* window = new Window(1280, 720, false); // Defining the width, height and the fullscreen state
   window->init("OpenGL"); // Init the window with the title
   
   while (window->isRunning()) // Check if window is running
   {
      
      glClear(GL_COLOR_BUFFER_BIT);          // Clear color buffer
      glClearColor(1.0f, 0.0f, 0.0f, 1.0f);  // Setting clear color
      
      window->pollEvents(); // Polling events
   }
   
   return 0;
}
```
### Output
<img width="854" height="480" src="https://raw.githubusercontent.com/AlKiam/InsightEngine/master/images/examplewindow.png" alt="window output"/>

##### Dependencies
  * **LWJGL** https://www.lwjgl.org/
  * **JOML** https://glm.g-truc.net

##### Examples
<img width="854" height="480" src="https://raw.githubusercontent.com/AlKiam/InsightEngine/master/images/exmple1.png" alt="window output"/>

