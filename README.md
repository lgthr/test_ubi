# test_ubi

## Flavors : 
- Default: la vitesse est récupérée grâce au GPS
- Mock: la vitesse est simulée 

## Architecture :

- MVVM pour la partie présentation
- Navigator pour la navigation
- Managers/Repositories pour la partie data
- Injection de dépendances avec Koin
- Gestion du flux de données avec ReactiveX

## À améliorer :

- Le GPS n'est pas très précis, à voir si il est possible d'améliorer cela en le couplant avec l'accéléromètre où en utilisant l'API Google Play
- Une UI plus friendly pour demander à l'utilisateur d'activer le GPS
