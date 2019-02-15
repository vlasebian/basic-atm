Sistem de facturi fiscale
=========================

Aplicația pentru sistemul de facturi fiscale este împărțită în 3 pachete:    
    * Market    
    * Gestiune    
    * Interfață    

## Task 1

Pachetul Market conține implementarea interfeței (IMagazin), clasei abstracte (Magazin), a magazinelor 
ce moștenesc clasa abstracta (MiniMarket, MediumMarket, HyperMarket) și a clasei creatoare de magazine, 
ce urmează modelul Factory (MarketFactory).

Pachetul Gestiune conține implementarea claselor: Produs, ProdusComandat, Factura, Reader și
Gestiune.

Clasa Reader este utilizată pentru citirea din fișiere, parsarea acestora și salvarea datelor
parsate. Cele 3 metode, readProducts(), readTaxes(), respectiv readBills() citesc și parsează
fișierele produse.txt, taxe.txt, respectiv facturi.txt și creează obiectele de tip Produs,
ProdusComandat, Facturi și Magazine. Clasa Gestiune instanțiază în metoda readFiles() un obiect 
de tipul Reader, realizându-se citirea din fișiere, apoi apelează metodele de tip get ale clasei
Reader pentru a obține vectorii creați(lista de produse, de magazine și dicționarul de taxe). Metoda
care scrie fișierul out.txt se numește writeOutputToFile și se află tot în clasa Gestiune. Această
clasă mai are câteva metode apelate de clasele din pachetul Interfata, mai multe detalii se pot găsi
în comentariile din cod.

## Task 2

#### Utilizator: admin
#### Parola: admin
(Enter în pagina de logare acționează butonul "Logare")

Ultimul pachet se numește Interfata și acesta conține clasele care alcătuiesc interfața grafică.
Aplicația are 5 pagini. Prima pagină este pagina de logare (implementarea în clasa Logare). La 
introducerea greșită a parolei sau a utilizatorului apare un mesaj de eroare ("Utilizator sau 
parolă greșită!"), altfel  pagina de meniu este afișată (implementarea în clasa Meniu). Meniul 
conține 4 butoane: primele 3 duc către o altă pagină, acestea fiind "Încărcare fișiere", 
"Administrare" și "Statistici", iar ultimul buton, "Ieșire", va închide aplicația.

Pagina "Încărcare fișiere" (clasa Incarcare) are 3 butoane pentru încărcarea celor 3 fișiere.
Fiecare buton va deschide o fereastră de dialog pentru încărcarea fișierului respectiv. Butonul
"Creează fișierul out.txt" va crea fișierul de la prima cerință în directorul proiectului. Apăsarea
butonului "Înapoi la meniu" va întoarce aplicația în meniu. Următoarele două pagini pot fi accesate
doar dacă fișierele au fost încărcate, iar out.txt a fost creat! În caz contrar, o fereastră de eroare 
va fi afișată cu mesajul "Nu ați încărcat fișierele!" când se încearcă accesarea paginilor.

Pagina "Administrare" (clasa Administrare) conține o listă cu produsele din fișierul produse.txt și
5 butoane. Primele două ordonează lista alfabetic după nume sau după țară. Al treilea deschide o
fereastra dialog pentru introducerea datelor unui nou produs, iar al patrulea deschide o fereastră
dialog pentru introducerea numelui unui produs de șters. Ultimul întoarce aplicația la meniu.

Pagina "Statistici" (clasa Statistici) conține detaliile cerute în enunț și un buton de întoarcere la meniu.

Cele 5 clase enunțate mai sus sunt unite prin clasa Main unde este implementată trecerea de la o
pagină la alta.

## IDE
IDE-ul folosit pentru această temă este IntelliJ IDEA 2017.2.6 (Ubuntu GNOME 17.04). Am ales IDE-ul 
deoarece am lucrat cu acesta tot semestrul și sunt obișnuit cu interfața și facilitățile pe care le oferă.
