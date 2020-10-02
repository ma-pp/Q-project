f = open("./Quran-Text/Quran-Unicode-Tashkeel.txt", "r")
T = f.readlines()

def teori1():
    for i in range(len(T[1])):
        print(T[1][i], ord(T[1][i]))