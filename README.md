# a project based on smartcardio package to manipulate the acos3 card
## les deux classes principal sont Acos3Project and verifying
# developped by arouche ayoub using eclipse


•	PersonnalizationFile : Contient les méthodes liées au personnalized file comme le 
Choix de n_of_file byte la sélection du personnalized file APDU command.

•	UserFileManagementFile : contient les méthodes liées au     
userFileManagementFile comme  La personnalisation des fichiers que on veut crée 

•	Acos3Commands : contient des méthodes qui nous retourne des commandes APDU générale comme la command APDU pour choisir un fichier ).

•	Cryptage (il contient les méthodes pour crypter et signe les données ces méthodes sont base sur des commandes au systèmes d’exploitation pour utiliser le logiciel openssl et la gestion des fichiers du pc (comme la lecture du fichier générer par openssl )) . 

•	HelpersFunctions ( il contient des méthodes qui nous aidons par exemple la méthode statique bytesToHex() qui prend comme paramètre un tableau d’octets et retourne une String de Hex pour faciliter la lecture des résultats).

•	SecurityFile (contient les méthodes liées au Security file comme le choix du code pin) 

•	UserFileDataArea (contient les méthodes pour crée sur un fichier d’utilisateur ou lire ce fichier).

•	Verifying (class pour vérifier le résultat) 

•	Acos3Project (class qui contient le projet demander) 

•	Acos3Exception (class qui hérite la class Exception chargé de traiter les Exceptions générer par notre code) 
