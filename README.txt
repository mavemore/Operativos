----------------------------------
          INSTRUCCIONES
----------------------------------
-Primero debe realizar las instrucciones del SERVIDOR y luego las del CLIENTE.
-El puerto debe ser el mismo en servidor y cliente.

------------------
    SERVIDOR
------------------

1. Abra la l�nea de comando en la carpeta JavaApp.
2. Escriba por la l�nea de comando: java KeyValueStore 
   puerto.
	**puerto debe ser el n�mero de puerto al que se
          va a conectar.
3. Ahora el servidor se encuentra escuchando al puerto.


------------------
    CLIENTE
------------------

1. Abra la l�nea de comando en la carpeta JavaApp.
2. Escriba por la l�nea de comando: java TCPClient ip 
   puerto.
        **ip debe ser la ip donde se encuentra el servidor. 
          Si va a correr ambos en el mismo computador, la 
          ip debe ser "127.0.0.1" o "localhost".
	**puerto debe ser el n�mero de puerto al que se va a conectar.
3. Ahora comienza la conexi�n con el servidor.

