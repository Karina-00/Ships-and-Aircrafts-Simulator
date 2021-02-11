# Ships and Aircraft Simulator

Karina Szubert  
nr indeksu: 145343  
grupa I3.2  
poniedziałek


## Opis działania programu

### Mapa
Po uruchomieniu programu pojawia się mapa ze wszystkimi dostępnymi lotniskami, punktami zwrotnymi dla statków,
oraz trasami zarówno morskimi jak i lotniczymi narysowanymi na mapie.

#### Lotniska
Występują dwa typy lotnisk: wojskowe i cywilne. Przy każdym uruchomieniu programu losowo generowany jest typ
każdego z nich, przy czym prawdopodobieństwo wylosowania lotniska cywilnego wynosi 60%.
Każde lotnisko ma podpisaną umowę (wytyczoną trasę) z minimum dwoma innymi lotniskami.

### Panel kontrolny
Za pomocą panelu kontrolnego możemy dodawać pojazdy o określonych parametrach do mapy.

#### Statki
Lokalizacja startowa statków jest losowana spośród wszystkich morskich punktów zwrotnych.
Pojazd nie ma z góry określonej trasy, porusza się od punktu do punktu za każdym razem losując
kolejny "przystanek" w taki sposób, aby się nie cofać.

#### Samoloty
Samolot wojskowy startuje z losowo wybranego lotniskowca, a jeśli nie ma żadnego na mapie 
to wybiera dowolne lotnisko wojskowe. Samolot pasażerski analogicznie wybiera lotnisko cywilne.

Lotnisko docelowe jest także wybierane losowo. Trasa samolotu jest ustalana za pomocą algorytmu Dijkstry, który 
wybiera najkrótszą trasę łączącą oba lotniska przebiegającą po narzuconych trasach lotniczych.
Samolot jeżdzi po wytyczonej trasie tam i spowrotem.

### Okno informacji o pojeździe
Po ***kliknięciu na dowolny pojazd*** wyświetla się okno z podstawowymi informacjami o nim.
Możemy tam śledzić aktualizujące się w czasie rzeczywistym współrzędne pojazdu, czy aktualny cel
(numer lotniska, pukntu na wodzie). W przypadku samolotów możemy dodatkowo podejrzeć aktualny stan
paliwa oraz całą trasę pojazdu.

Możemy też usunąć każdy pojazd z mapy lub wykonać lądowanie awaryjne na najbliższym lotnisku.
W oknie mapy jest także dostępny przycisk do zrestartowania całej symulacji.

### Uruchomienie
`java --module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml -jar Project.jar`

## Uwagi
Niestety nie udało mi się zaimplementować przepuszczania się pojazdów na skrzyżowaniach, czy oczekiwania nad lotniskiem.
