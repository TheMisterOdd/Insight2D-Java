# Insight Engine, free and OpenSource Engine. Using OpenGL
### 2D cross-platform game engine
Insight Engine is a **Java**, multiplatform game engine to create 2D and 3D games. It provides a varaity of new classes to handle different aspects like **window, input, model loading, shaders, audio...**

Unfortunately we have not managed to port the engine to Android or HTML, but we have translated it to [C](https://github.com/AlKiam/Insight2D), we will try this in later versions.

### Free and open source
Insight is completely free and open source under GNU General Public License. No strings attached, no royalties, nothing. The users' games are theirs, down to the last line of engine code. We don't want to make profit from this project.

### Example code
```java

public class MyClass {
    public static void main(String[] args) {
        
        Window window = new Window(1280, 720, "OpenGL Test", false);
        
        while (window.isRunning()) {
            
            glClear(GL_COLOR_BUFFER_BIT);
            glClearColor(1.f, 0.f, 0.f, 1.f);
         
        }
    }
}


```
### Output
<img width="854" height="480" src="https://raw.githubusercontent.com/AlKiam/InsightEngine/master/images/examplewindow.png" alt="window output"/>

##### Dependencies
  * **LWJGL** https://www.lwjgl.org/
  * **JOML** https://github.com/JOML-CI/JOML

##### Examples
<img width="854" height="480" src="https://raw.githubusercontent.com/AlKiam/InsightEngine/master/images/exmple1.png" alt="window output"/>

