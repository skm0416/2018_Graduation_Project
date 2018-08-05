import glfw
from OpenGL.GL import *
import numpy as np

def render():
	glClear(GL_COLOR_BUFFER_BIT)
	glLoadIdentity()
	glBegin(GL_TRIANGLES)
	glColor3ub(255,0,0)
	glVertex2fv((0.0,1.0))
	glVertex2fv([-1.0,-1.0])
	glVertex2fv(np.array([1.0,-1.0]))
	glEnd()
	
# def render():
# 	glClear(GL_COLOR_BUFFER_BIT)
# 	glLoadIdentity()
# 	glBegin(GL_TRIANGLES)
# 	glColor3f(1.0,0.0,0.0)
# 	glVertex2f(0.0,0.5)
# 	glColor3f(0.0,1.0,0.0)
# 	glVertex2f(-0.5,-0.5)
# 	glColor3f(0.0,0.0,1.0)
# 	glVertex2f(0.5,-0.5)
# 	glEnd()

def main():
	#initialize the library
	if not glfw.init():
		return
	#Create a windowed mode window and its OpenGL context
	window = glfw.create_window(640,480,"Hello World", None, None)
	if not window:
		glfw.terminate()
		return

	#Make the window's context current
	glfw.make_context_current(window)

	#Loop until the user closes the window
	while not glfw.window_should_close(window):
		#Poll events
		glfw.poll_events()

		#Render here, e.g. using pyOpenGL
		render()

		#Swap front and back buffers
		glfw.swap_buffers(window)

	glfw.terminate()

if __name__ == "__main__":
	main()