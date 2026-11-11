# Zadanie_3.txt 
# SPRAWOZDANIE (czytać uważnie wytyczne):

FORMAT -> .pdf
NAZWA -> BAISM_<NumerCwiczenia>_<NumerIndeksu>_<Imie>_<Nazwisko>
STRONA TYTUŁOWA:
1. Nazwa przedmiotu
2. <Imię nazwisko indeks>
3. Zadanie <Numer ćwiczenia>
4. Source code <LINK DO PUBLICZNEGO PROJEKTU GITHUB>

# SOURCE CODE:
Source code projektu ma być wrzucony na publiczne repozytorium GitHub i na pierwszej stronie sprawozdania podlinkowany.

## Nazwa projektu na GitHub: BAISM-CW-<NumerCwiczenia> (patrz na nazwe zadania, na końcu jest zawsze numer ćwiczenia)

# TREŚĆ ZADANIA

Przygotuj sprawozdanie z całego procesu. Co robiłeś, krok po kroku, opisy + screenshoty. Podaj z jakich narzędzi korzystałeś, w jakiej wersji, skąd pobranych. Aplikacje uruchamiaj na emulatorze z rootem, Android SDK 30+. 
Do oddania sprawozdanie + kod źródłowy aplikacji - kod źródłowy projektu na być umieszczony na publicznie dostępnym repozytorium GitHub

1. Stwórz prostą aplikację mobilną - technologia Kotlin aplikacja na Androida, która rozmawia z "https://rickandmortyapi.com/" za pomocą API - implementacja endpointa listującego wszystkie postaci oraz zwracająca dane danej postaci po ID. Wystarczy minimalna implementacja.
1.1 Minimalny potrzebny UI - dwa przyciski realizujące requesty, LazyColumn dla list i wypisywanie danych konkretnej postaci.
1.2 Dla chętnych można wyżyć się artystycznie i stworzyć piękną aplikacje ;)

2. Wgraj MITM Proxy certificate do User CA i spróbuj podsłuchać ruch. 
Podpowiedź 1: Przejrzyj dokumentacje mitm proxy. Są tam film instruktażowe i nie bój się korzystać z Google czy modeli LLM :) Tylko zapoznaj się chociaż z tym co ci wygenerował...
Podpowiedź 2: Dostępne certyfikaty są w ustawieniach twojego telefonu/emulatora poszperaj.

3. Spisz wnioski - czy widzisz ruch, jeśli tak to zmodyfikuj go tak, aby zmienić ID żądanej postaci.
Podpowiedź: Postaw interceptor, który przechwyci dany request i zatrzyma go na etapie proxy.

4. Zezwól na user CA w wersji debug aplikacji (Network Security Config).
Podpowiedź: https://developer.android.com/privacy-and-security/security-config

5. Spisz wnioski, co widzisz, czy możesz modyfikować zapytania - jeśli tak zmodyfikuj je tak aby zmienić ID postaci.

6. Dodaj certificate pinning. Za pomocą dowolnego narzędzia np. openssl, uzyskaj SPKI (SubjectPublicKeyInfo) ze strony https://rickandmortyapi.com/
Podpowiedź: Przeczytaj dokładnie https://stackoverflow.com/questions/40404963/how-do-i-get-public-key-hash-for-ssl-pinning

7. Spróbuj podsłuchać podsłuchać ruch.

8. Spisz wnioski - co widzisz, zmodyfikuj zapytania tak aby podmienić ID.

Na wyższą ocene: Wgraj certyfikat zamiast do User CA to jako systemowy. Będzie to wymagało zmodyfikowania emulatora i dopalenia go jako -writable-system. Po tym wszystkim usuń z kodu zezwolenie na User CA i pokaż, że ruch dalej jest widoczny.

https://docs.mitmproxy.org/stable/howto/install-system-trusted-ca-android/#2-rename-certificate

Do oddania sprawozdanie + kod źródłowy aplikacji
