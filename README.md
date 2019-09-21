### Features

- Konfigurierbarer Port
- Hinzufügen von Benutzern
- Editieren von Server Gruppen
- Erstellen von Server Gruppen
- Anzeige der BASE's
- Anzeige der Spieler

# Installation

- Lade dir die neuste Version des Interfaces [hier](https://github.com/RagingElias/TimoCloudWeb/releases/latest "hier") runter.
- Packe die heruntergeladene **.jar**-Datei in das **plugin** Verzeichniss deines TimoCloud-Cores.
- Starte den TimoCloud Core.
	- Prüfe nun, ob du über **serverip:8080** ins Interface kommst.
	- Nun siehst du die Anmeldeseite. Der Standard Benutzer heißt **admin** und hat das Gleichnamige Passwort **admin**
	- Um das Passwort zu ändern, gehe in die **web.yml**, diese befindet sich im selben Verzeichniss wie die **TimoCloud.jar**
	*Standard-Konfiguration*
	```
	web:
        port: 8080
    users:
        admin: admin```
	Um nun das Passwort zu ändern, ändere `admin: admin` 
	zu `admin: DEINPASSWORT`
	
	Um einen neuen Benutzer hinzuzufügen, füge unter `admin: PASSWORT` `BENUTZERNAME: PASSWORT` hinzu.
	
- Bei Problemen oder Fragen: (Discord) P2W_RagingElias#2919



