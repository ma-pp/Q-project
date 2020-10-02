# Belajar OOP
# Case study Tkinter
# https://stackoverflow.com/questions/32005839/how-to-pass-multiple-parameters-to-class-during-initialization
from Tkinter import *

class Window(Frame):


    def __init__(self, master=None, labelText=None):
        Frame.__init__(self, master)                 
        self.master = master
        print("__init__")
        self.labelText = labelText
        self.init_window()

    #Creation of init_window
    def init_window(self):
        print("init window")
        # changing the title of our master widget      
        self.master.title("GUI")

        self.pack(fill=BOTH, expand=1)

        # creating a button instance
        self.button = Button(self, text="Button", command=self.changeText) 
        # button adalah object yang dicetak dari class button diaakan merespons event click dengan mengeksekusi function changeText. 
        # Button(self, text="Button", command=self.changeText)
        # Bentuk di atas menggambarkan 2 konsep: bind/callback dan pointer to function dan belum dieksekusi, hanya dicatat di tabel fungsi.

        # self merupakan 

        text = Text(self, width=40, height=10)

        # placing the button on my window
        self.button.place(x=100, y=20)
        text.insert('1.0', 'here is my text to insert')

        text.place(x=100, y=50)

    def changeText(self):  
        self.button['text'] = self.labelText

root = Tk()

# main
print("root=tk")
#size of the window
root.geometry("300x300")
print("root geometry")
# sebelum baris ke 47, 
app = Window(root, "submitted")
app2 = Window(root, "ok")

from pprint import pprint
d = dir(app)
print(type(d))
print(d)
root.mainloop() 
print("mainloop")