'''
Snippet ini menjelaskan perbedaan antara struktur data pertama (array of sentence) 
dengan struktur kedua yaitu array of word.
'''

from pprint import pprint
f = open("../dummy-quran-text.txt", "r")
T = f.readlines()

print(T[9:14])
# pprint(T[:5]) #Dimulai dari elemen pertama sampai lima

a = []
for sentence in T:
    words = sentence.split()
    for w in words:
        a.append(w)

# print(a[:5])

