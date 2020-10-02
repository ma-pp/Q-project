function getVal() {
    var x = document.getElementById("id-form-group").elements[1].value;
    console.log(x);
}

function hide() {
    h = document.getElementById("search-bar").style.display;// = "none";
    // console.log(h)
    if (h == "none") {
        
        sb = document.getElementById("search-bar"); 
        sb.style.display = "table";
    }else{
        document.getElementById("search-bar").style.display = "none";
    }
}

window.addEventListener('click', function(e){	
    if (document.getElementById('quran-page').contains(e.target)){
        hide()
    }
})

