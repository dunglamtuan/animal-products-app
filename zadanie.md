# Backend vyvojar zadanie

Naimplementujte server s REST API pre jednoduchu e-shop aplikaciu, ktora predava produkty urcene pre zvierata a poskytuje administracne rozhranie (pre juniorov volitelna cast zadania).

## Domenovy model

**Product**
- id
- name
- animal categories
- price
- description
- gallery (list of image URLS)


**AnimalCategory**
- name

**User**
- email
- username

**Order**
- id
- total price
- list of items with id of the product, price at time of purchase, count
- time


Kategorie zvierat:
- *dogs*
- *cats*
- *other*


## Architektura

Clientom servera je SPA, ktore poskytuje UI k e-shopu. Data sa drzia v relacnej alebo Mongo databaze.

## API

Standardna RESTful API vratane validacii inputov. API ma public a authenticated endpointy, t.j. len pre prihlaseneho pouzivatela (sucastou HTTP requestu musia byt authentication data).

### Public endpointy

- strankovany zoznam produktov s filtrom podla ceny (min, max) a mena (match podla 'starts with') - vratene data neobsahuju `description` a `gallery` property
- detail konkretneho produktu so vsetkymi datami o produkte

### Authenticated endpointy
- registracia bezneho usera
- prihlasenie bezneho usera
- vytvorenie objednavky prihlasenym pouzivatelom
- zoznam objednavok prihlaseneho pouzivatela so vsetkymi datami

### Admin cast (pre junior pozicie volitelne)
- prihlasenie admin usera
- strankovany zoznam vsetkych objednavok
- strankovany zoznam vsetkych produkty
- pridanie noveho produktu


## Implementacne technologie

Java 8+ a moderny Spring stack (Spring Boot, Spring Data, ...).  V pripade SQL perzistencie Hibernate. 

## Vseobecne informacie
- API validacie (typove aj semanticky) - postaci vracat 4xx kod
- k datam bezneho pouzivatela ma pristup len on sam alebo admin
- aspon jeden unit a integracny test
- navrh a implementacia na production-grade urovni (nie prototyp)
- nasadenie na lubovolnej platforme dostupnej z Internetu (Heroku, vlastny VPS..) vratene testovacich dat

Extra body za riesenie mozno ziskat za:

- pouzitie modernych technologie pre auth vrstvu (JWT, OAuth, OpenID Connect)
- testovatelny kod a zmysluplne testy
- pouzitie Docker-a
- jednoduchy CI setup (CircleCI, Travis)
