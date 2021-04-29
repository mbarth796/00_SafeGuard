Daten vom Emulator in Android Studio:
Name von Emulatorphone: Pixel 3 API R
Resolution: 1080x2160: 440 dpi
API: R
Target: Android 11.0
CPU: x86
Size on Disk 10GB

Bugliste / ToDO-liste:
Für Whatsapp Anruf muss der Kontakt in Kontakten existieren, gewünschte Nummer soll in Code hinterlegt werden
--> Datei: EmergencyFragment.java 
--> Codezeile: 64,65; 
--> Strings: phonenumberWhatsApp, phonenumberSMS

Anruf und Whatsapp geht nicht im Emulator
--> App stürzt im Emulator ab, wenn der Notruf abgesetzt wird, um Funktionsweise des Formulars zu überprüfen kann DemoFragment.java verwendet (Menüpunkt Demo im Emulator)
--> Evtl. auf Android Smartphone ausprobieren für vollen Funktionsumfang (inkl. Whatsappcall, SMS, Notruf per normalen Telefonanruf) 

Known Issue: Standort wird nicht mitgesendet wenn Formular zu schnell ausgefüllt wird, auf Emulator kein Problem
-->  Formular ausfüllen soll mindestens 3-4 Sekunden dauern
