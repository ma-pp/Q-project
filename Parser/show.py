# cara debug di Spyder:
# 1. Kasih breakpoint di function yang mau didebug
# 2. Klick "debug file"
# 3. Masuk ke dalam function itu, klick "Step into function or method of current line"
# 4. Run, klick "run current line"

def show_html(ayah, flex):
    if flex == "flex":
        ft = open("./parse-flex.html", "r")
    else:
        ft = open("./parse-proses.html", "r")
 
    fts = ft.read()
    fts = fts.replace("---", ayah)
    return fts

import sqlite3
def getFromDb(p):
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()

    out = ""
    ayah = 0
    i = 0
    while i < len(wbw):
        ayah = wbw[i][2]
        ayahText = wbw[i][4] + " " + wbw[i][5]
        out = out + " " + ayahText + " "
        
        if i < len(wbw)-1:
            if ayah != wbw[i+1][2]:
                ayahNum = " ({}) <br> \n ".format(ayah)
                out += ayahNum

        i += 1
    out += " ({})".format(ayah)
    # print(show_html(str(out)))
    print(str(out))

def showTable(p):
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()

    out = ""
    w = 0

    for i in range(len(wbw)):
        ar_db = wbw[i][4]
        idn_db = wbw[i][5]
        ayah = wbw[i][2]
        
        if i < len(wbw)-1:
            if ayah != wbw[i+1][2]:
                out += "<tr>"
        out += "<th>{}</th>".format(ar_db)
        out += "<td>{}</td>".format(idn_db)


    print(show_html(str(out)))
    # print(str(out))

def showTablev2(p):
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()

    out = ""

    for i in range(len(wbw)):
        ar_db = wbw[i][4]
        ayah = wbw[i][2]
        
        if i < len(wbw)-1:
            if ayah != wbw[i+1][2]:
                out += "<tr>"
        out += "<th>{}</th>".format(ar_db)
    
    for i in range(len(wbw)):
        idn_db = wbw[i][5]
        ayah = wbw[i][2]
        
        if i < len(wbw)-1:
            if ayah != wbw[i+1][2]:
                out += "<tr>"
        out += "<td>{}</td>".format(idn_db)
    
        # out += "</tr>"

    print(show_html(str(out)))

def showTablev3(p):
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    
    out = ""
    ar = ""
    idn = ""
    ayah = ""
    l = len(wbw)
    for i in range(l):
        ar_db = wbw[i][4]
        idn_db = wbw[i][5]
        ayahNumber = wbw[i][2]
        
        if i < len(wbw)-1:
            if ayahNumber != wbw[i-1][2]:
                ar = ar + "<tr>"
                idn = idn + "<tr>"
            if ayahNumber != wbw[i+1][2]:
                ar += "</tr>"
                idn += "</tr>"
                ayah = ayah + ar + idn
        ar = ar + "<th>" + ar_db + "</th>" # ar += "<th>{}</th>".format(ar_db) 
        idn = idn + "<td>" + idn_db + "</td>"  #<td>{}</td>".format(idn_db)
    
    out += ayah
    print(show_html(out))

def showTablev4(p):
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    print("<pre>")
    l = len(wbw)
    for i in range(l):
        ar_db = wbw[i][4]
        idn_db = wbw[i][5]
        ayahNumber = wbw[i][2]

        if ayahNumber != wbw[i-1][2]: # kalau ayahNumber sekarang beda dengan yang sebelumnya
            # kesalahan: karena langkah ini nggak crash di Python, anggapan ini dipakai di langkah setelah ini
            # harusnya langkah ini crash karena jika ayahNumber = 0 maka tidak ada ayahNumber = -1
            print(" awal ayat ",ar_db, " ")
        if i < len(wbw)-1: # kalau i masih lebih kecil dari panjangnya wbw - 1
            if ayahNumber != wbw[i+1][2]: # dan ayahNumbernya beda dengan yang setelahnya. I
                # langkah ini mengandung kesalahan logika: mengakses data di melebihi panjang akhir wbw. out of range
                print(" akhir ayat ",ar_db, " ")
    print("</pre>")
    # print(show_html(out))   

def showTablev5(p):
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    print("<pre>")
    l = len(wbw)
    for i in range(l):
        ar_db = wbw[i][4]
        idn_db = wbw[i][5]
        ayahNumber = wbw[i][2]

        if ayahNumber != wbw[i-1][2]:
            print(" awal ayat ",ar_db, " ")
        if i == len(wbw)-1: # kalau i adalah ayahNumber terakhir
             print(" akhir ayat ",ar_db, " ")
        elif ayahNumber != wbw[i+1][2]: # dan ayahNumbernya beda dengan yang setelahnya
            print(" akhir ayat ",ar_db, " ")
    print("</pre>") 

def showTablev6(p):
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    out = ""
    ar_out = ""
    idn_out = ""
    for i in range(len(wbw)):
        ar_db = wbw[i][4]
        idn_db = wbw[i][5]
        ayahNumber = wbw[i][2]

        if ayahNumber != wbw[i-1][2]:
            # print(" awal ayat ",ar_db, " ")
            ar_out += "<tr><th>{}</th>".format(ar_db)
            idn_out += "<tr><td>{}</td>".format(idn_db)            
        elif i == len(wbw)-1: # kalau i adalah ayahNumber terakhir
            # print(" akhir ayat ",ar_db, " ")
            ar_out += "<th>{}</th></tr>".format(ar_db)
            idn_out += "<td>{}</td></tr>".format(idn_db)
            out = out + ar_out + idn_out
        elif ayahNumber != wbw[i+1][2]: # dan ayahNumbernya beda dengan yang setelahnya
            # print(" akhir ayat ",ar_db, " ")
            ar_out += "<th>{}</th></tr>".format(ar_db)
            idn_out += "<td>{}</td></tr>".format(idn_db)
            out = out + ar_out + idn_out
        else:
            ar_out += "<th>{}</th>".format(ar_db)
            idn_out += "<td>{}</td>".format(idn_db)
    
    # print(show_html(out))
    print(show_html(out))
    # print(wbw)

def showTablev7(p):
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    out = ""
    ar_out = ""
    idn_out = ""
    for i in range(len(wbw)):
        ar_db = wbw[i][4]
        idn_db = wbw[i][5]
        ayahNumber = wbw[i][2]

        if ayahNumber != wbw[i-1][2]:
            # print(" awal ayat ",ar_db, " ")
            ar_out += "<tr><th>{}</th>".format(ar_db)
            idn_out += "<tr><td>{}</td>".format(idn_db)            
        elif i == len(wbw)-1: # kalau i adalah ayahNumber terakhir
            # print(" akhir ayat ",ar_db, " ")
            ar_out += "<th>{}</th></tr>".format(ar_db)
            idn_out += "<td>{}</td></tr>".format(idn_db)
            out = out + ar_out + idn_out
            ar_out = idn_out = ""
        elif ayahNumber != wbw[i+1][2]: # dan ayahNumbernya beda dengan yang setelahnya
            # print(" akhir ayat ",ar_db, " ")
            ar_out += "<th>{}</th></tr>".format(ar_db)
            idn_out += "<td>{}</td></tr>".format(idn_db)
            out = out + ar_out + idn_out
            ar_out = idn_out = ""
        else:
            ar_out += "<th>{}</th>".format(ar_db)
            idn_out += "<td>{}</td>".format(idn_db)
    
    # print(show_html(out))
    print(show_html(out))
    # print(wbw)

def showTablev8(p): # erw, out wajib cuma 1
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    out = "<tr>"
 # 0.. 29  
    # 0...3 ar
    # 0...3 id

    # 4...6 ar
    # 4...6 id

    # 7...10 ar
    # 7...10 id
    k = j = 0
    lw = len( wbw )
    lastAyah = 1
    while  j < lw : # 0..29
        while j < lw  : # loop pertama , loop bahasa arab
            ayahNumber = wbw[j][2] 
            if ayahNumber != lastAyah :
                break # hentikan while terdekat yaitu loop bahasa arab
            out = out + "\t<td>{}</td>\n".format( wbw[j][4] ) 
            # selama ayat numbernya sama looping ke kanan. Ketika beda, buat ganti baris 
            j += 1
            
        out = out + "</tr><tr>\n" # batas arab dan indonesia

        while k < j : # loop bahasa indonesia, sebanyak bahasa arabnya
            out = out + "\t<td>{}</td>\n".format( wbw[k][5] ) 
            k += 1
        out = out + "</tr><tr>\n" 
        if j<lw :
            lastAyah = wbw[j][2]

    out = out + "</tr>"
    
    print(show_html(out))
    # print(wbw)

def showTablev9(p): # erw, out wajib cuma 1
    db = sqlite3.connect("../../Q-db/quran-with-more-detail.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    outar = outid = out = "<tr>"

 # 0.. 29  
    # 0...3 ar
    # 0...3 id

    # 4...6 ar
    # 4...6 id

    # 7...10 ar
    # 7...10 id
    k = j = 0
    lw = len( wbw )
    lastAyah = 1
    while  j < lw : # 0..29
        while j < lw  : # loop pertama , loop bahasa arab
            ayahNumber = wbw[j][2] 
            if ayahNumber != lastAyah :
                break # hentikan while terdekat yaitu loop bahasa arab
            outar = outar + "\t<td>{}</td>\n".format( wbw[j][4] ) 
            outid = outid + "\t<td>{}</td>\n".format( wbw[j][5] ) 

            # selama ayat numbernya sama looping ke kanan. Ketika beda, buat ganti baris 
            j += 1
            
        outar = outar + "</tr><tr>\n" # batas arab dan indonesia
        outid = outid + "</tr><tr>\n" # batas arab dan indonesia
        out = out + outar + outid
        outid = outar = ""

        
        if j<lw :
            lastAyah = wbw[j][2]

    out = out + "</tr>"
    
    print(show_html(out))
    # print(wbw)

def showTablev10(p): # erw, out wajib cuma 1
    db = sqlite3.connect("/home/u/dev/Q-db/quran.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    out = "<tr>"
    k = j = 0
    lw = len( wbw )
    lastAyah = 1
    lastLine = 1
#    while  j < lw : # 0..29
    while j < lw  : # loop pertama , loop bahasa arab
        ayahNumber = wbw[j][2] 
        line = wbw[j][3]
        # if ayahNumber != lastAyah:
        if lastLine != line:
            out = out + "</tr><tr>\n" # batas arab dan indonesia
        
        out = out + '\t<td> {} <br> <span id="id_text">{}</span> </td>\n'.format( wbw[j][5], wbw[j][6] ) 
         # selama ayat numbernya sama looping ke kanan. Ketika beda, buat ganti baris 
        
        if j<lw :
            lastAyah = wbw[j][2]
            lastLine = wbw[j][3]
        j += 1

    out = out + "</tr>"
    
    print(show_html(out))
    # print(wbw)


# showTablev10(3)

def showFlex(p):
    db = sqlite3.connect("/home/u/dev/Q-db/quran.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    out = '<div class="items">'
    k = j = 0
    lw = len( wbw )
    lastAyah = 1
    lastLine = 1

    while j < lw :
        ayahNumber = wbw[j][2] 
        line = wbw[j][3]
        
        # if lastLine != line:
        if j < lw-1:
            if ayahNumber != wbw[j+1][2]:
            # out = out + '</div><div class="item">' # batas arab dan indonesia
                out = out + '<div class="item"> <div class="ar_text"> {} ({}) </div> \n <span class="id_text"> {} </span> </div>\n'.format(wbw[j][5], lastAyah, wbw[j][6])
                j += 1
        else:
            out = out + '<div class="item"> <div class="ar_text"> {} ({}) </div> \n <span class="id_text"> {} </span> </div>\n'.format(wbw[j][5], lastAyah, wbw[j][6])
            break
        
        out = out + '<div class="item"> <div class="ar_text"> {} </div> \n <span class="id_text"> {} </span> </div>\n'.format(wbw[j][5], wbw[j][6])
        # selama ayat numbernya sama looping ke kanan. Ketika beda, buat ganti baris 
        
        if j<lw :
            lastAyah = wbw[j][2]
            lastLine = wbw[j][3]
        j += 1
        
    
    out = out + "</div>"
    
    print(show_html(out, "flex"))

#---------------------------------------#
# https://stackoverflow.com/questions/26626238/how-to-convert-normal-numbers-into-arabic-numbers-in-django
def en_to_ar_num(number_string):
    lis=[]
    dic = {
        '0': '٠',
        '1': '١',
        '2': '٢',
        '3': '٣',
        '4': '٤',
        '5': '٥',
        '6': '٦',
        '7': '٧',
        '8': '٨',
        '9': '٩',
    }

    for char in number_string:
        if char in dic:
            lis.append(dic[char])
        else:
            lis.append(char)
    
    return "".join(lis)[::-1]

# print(en_to_ar_num("140"))
# import sys
# sys.exit()
def showFlexv2(p):
    db = sqlite3.connect("/home/u/dev/Q-db/quran.db") 
    c = db.cursor()

    c.execute("SELECT * FROM words WHERE page={}".format(p))

    wbw = c.fetchall()
    out = '<div class="flex">'
    k = j = 0
    lw = len( wbw )

    lastAyah = 1
    lastLine = 1

    while j < lw  :
        ayahNumber = wbw[j][2] 
        line = wbw[j][3]
        if j < lw-1:
            if line != wbw[j+1][3]:
                out = out + '</div><div class="flex">'
            if ayahNumber != wbw[j+1][2]:
                out = out + '<section><span class="verseContent">{}{} </span><span class="meaning">({})</span></section>\n'.format(wbw[j][5], en_to_ar_num(str(lastAyah)), wbw[j][6])
                j += 1
        else:
            out = out + '<section><span class="verseContent">{}{} </span><span class="meaning">({})</span></section>\n'.format(wbw[j][5],en_to_ar_num(str(lastAyah)), wbw[j][6])
            break
        
        out = out + '<section><span class="verseContent">{} </span><span class="meaning">({})</span></section>\n'.format(wbw[j][5], wbw[j][6])
        
        if j < lw:
            lastLine = wbw[j][3]
            lastAyah = wbw[j][2]
        j += 1
        
    out = out + "</div>"
    
    print(show_html(out, "flex"))
showFlexv2(500)

# select ar from words where ar="كَفَرُوا۟"
# update from words set ar="كَفَرُوْا" where ar ="كَفَرُوا۟"
# update words set ar="وَأُولَـٰئِكَ" where ar="وَأُو۟لَٰٓئِكَ";

# Select db menggunakan substring
# select ar from words where ar like "%وا۟"

# update from words set ar "وْا" where ar like "%وا۟"
