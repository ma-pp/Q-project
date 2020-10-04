## Run kotlin webserver

Make sure you're already install Kotlin cli

`$ kotlinc sqws2.kt; kotlin -cp ".:./sqlite-jdbc-3.32.3.2.jar" sqws2`

## browse

type `localhost:8000/test?page=1` on application search bar. Or `localhost:8000/test?page=1:3` for a more specific search (ayah).

![result](./images/web_ui.png)