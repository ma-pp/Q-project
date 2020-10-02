import sqlite3
def selectWBW(sura_inp):
    conn_verses = sqlite3.connect("./Quran-Text/dbQuran/files/quran.db")
    conn_words = sqlite3.connect("./Quran-Text/dbQuran/files/words.db")
    cur_verses = conn_verses.cursor()
    cur_words = conn_words.cursor()
    # cur.execute("SELECT surah_id, verse_id, words_ar, translate_indo FROM bywords WHERE words_ar='ٱللَّهِ' AND surah_id=1")
    
    
    cur_verses.execute("SELECT c0sura, c1ayah, c2text FROM verses_content WHERE c0sura = {}".format(sura_inp)) 
    cur_words.execute("SELECT sura, ayah, word, id FROM allwords WHERE sura = {}".format(sura_inp))
    
    rows_verses = cur_verses.fetchall()
    rows_words = cur_words.fetchall()

    #words 

    i = 0
    for v in rows_verses:
        c0sura = v[0]
        c1ayah = v[1]
        c2text = v[2]

        
        ar_word = c2text.split(" ")
        for aw in range(1, len(ar_word)):
            w = rows_words[i]
            
            ayah = w[1] # tiap ayat ada berapa kata
            word = w[2] # index terjemah dari tiap kata
            Id   = w[3]

            i += 1

            out = Id + " " + ar_word[aw-1]
            print(show(out + "<br>"))