# Databases links
# https://github.com/SadaqaWorks/Word-By-Word-Quran-Android/blob/master/app/src/main/assets/databases/wordbyword.db
# https://github.com/GreentechApps/Al-Quran/tree/master/dbs

# Greentech apps on Lenovo device
# /storage/emulated/0/Android/data/com.greentech.quran/files

import unicodedata
import re
import sqlite3

f = open("./Quran-Text/Quran-Unicode-Tashkeel.txt", "r") # http://quran.mursil.com/ 
# f = open("./dummy-quran-text.txt", "r")
# f = open("./Quran-Text/quran-uthmani.txt", "r")
T = f.readlines()


def show(text):
    ft = open("./parse-proses.html", "r")
    fts = ft.read()
    fts = fts.replace("---", text)
    return fts
    
#! ini hanya jalan di halaman 1 sampai 8, atau halaman 1 digit
def getPages1(p):
    out = ""
    start = False
    for i in range(len(T)):
        s = T[i]
        words = s.split()
        c_page = words[-1]

        # logika ini seperti saklar pusat di rumah
        if c_page[0] == str(p):
            start = True
        # ketika ganti halaman maka berhenti 
        if c_page[0] == str(p+1): 
            print(show(out))
            break 
        else: 
            # ketika saklar pusat nyala, maka akan terus dieksekusi
            if start == True: 
                out = out + s

def getPages2(p):
    out = ""
    start = False
    for i in range(len(T)):
        s = T[i] # kalimat sebaris
        words = s.split() #kata-kata
        try:
            c_page = words[-1]
        except:
            # print("words",words)
            continue
        
        c_page = c_page[:-1]
        
        # print(unicodedata.digit(c_page))
        if len(c_page) < 4 and c_page[0] in "0123456789":
            # print(type(c_page), c_page)
            
            # print(int(c_page))
            
            # print("cpage", c_page)
            c_page = int(c_page)
        # logika ini seperti saklar pusat di rumah
        if c_page == p:
            
            start = True
        # ketika ganti halaman maka berhenti 
        if c_page == (p + 1): 
            print(show(out))
            # print(c_page, str(p+1))
            break 
        else: 
            # ketika saklar pusat nyala, maka akan terus dieksekusi
            if start == True: 
                i = 0
                while  i < len(words) :# in range(len(words) - 1):                    
                    w = words[i]
                    # print(w)
                                        
                    if w[0] == "{" and i < (len(words) - 2):
                        element = words.index(w)
                        out = out + '{} <u><font color="blue"> {} {} </font></u>'.format(w, words[i + 1],  words[i + 2])
                        i += 2
                    else:
                        out = out + " " + w
                    i += 1 
                
                out = out + "<br>"
                # out = out + s + "<br>"
                # out = out + s 
                # out = out + words[0] + " " + words[1] + "<br>" # ngambil kata dari baris pertama 
    
def getPages3(p):
    outPage = ""
    start = False
    for i in range(len(T)):
        s = T[i] # kalimat sebaris
        words = s.split() #kata-kata
        try:
            c_page = words[-1]
        except:
            continue
        c_page = c_page[:-1]

        if len(c_page) < 4 and c_page[0] in "0123456789":
            c_page = int(c_page)

        if c_page == p: # logika ini seperti saklar pusat di rumah        
            start = True
            
        if c_page == (p + 1): # ketika ganti halaman maka berhenti 
            print(show(outPage))
            break 
        else: 
            outPage = outPage + writeAyah(start, words)
'''
getPages3() dan writeAyah() mengasumusikan bahwa inputnya adalah array of string
yang diproses dari atas ke bawah. 
'''
def writeAyah(start, words): # fungsi ini berpasangan dengan getPages3()
    outAyah = ""
    firstAyah = False
    if start == True:     # ketika saklar pusat nyala, maka akan terus dieksekusi
        i = 0
        while  i < len(words) :# in range(len(words) - 1):                    
            w = words[i]
            # print(w)                
            if w[0] == "{" :
                firstAyah = True
            
            if firstAyah == True and i < (len(words) - 2):
                count_underline = 0
                while count_underline <= 2:
                    outAyah = outAyah + ' <u><font color="blue"> {} </font></u>'.format(words[i + count_underline])
                    count_underline += 1
                i += 2
                firstAyah = False
            else:
                outAyah = outAyah + " " + w
            i += 1 
        
        outAyah = outAyah + "<br>"

    return(outAyah)


def getPages(p):
    outPage = ""
    start = False
    count_line = 0
    for i in range(len(T)):
        s = T[i] # kalimat sebaris
        words = s.split() #kata-kata
        try:
            c_page = words[-1]
        except:
            continue
        c_page = c_page[:-1]

        if len(c_page) < 4 and c_page[0] in "0123456789":
            c_page = int(c_page)

        if c_page == p: # logika ini seperti saklar pusat di rumah        
            start = True
            
        if c_page == (p + 1): # ketika ganti halaman maka berhenti 
            #print(show(outPage))
            print( show( underliner( outPage )))
            break 
        else: 
            if start :
                for w in words :
                    outPage = outPage + "#" + w 
                outPage = outPage + "<br>\n"

def underliner( s ) :
    out = ""
    s = s.split( "#")[1:]
    i = 0
    
    while  i < (len(s)) :
    
        if i == 5 :
            
            out = out +  ' {} <u><font color="blue"> {} {} </font></u>'.format( s[i], s[i+1], s[i+2] )
            i += 2
        elif s[i][0] == "{" and i < (len(s)-2):
    
            out = out +  ' {} <u><font color="blue"> {} {} </font></u>'.format( s[i], s[i+1], s[i+2] )
            i += 2
        else :
            out = out + " " + s[i] 
        i += 1

    return out # str( s ) 

getPages(603)