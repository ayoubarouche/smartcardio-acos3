# a project based on smartcardio package to manipulate the acos3 card
## les deux classes principal sont Acos3Project and verifying
# developped by arouche ayoub using eclipse


�	PersonnalizationFile : Contient les m�thodes li�es au personnalized file comme le 
Choix de n_of_file byte la s�lection du personnalized file APDU command.

�	UserFileManagementFile : contient les m�thodes li�es au     
userFileManagementFile comme  La personnalisation des fichiers que on veut cr�e 

�	Acos3Commands : contient des m�thodes qui nous retourne des commandes APDU g�n�rale comme la command APDU pour choisir un fichier ).

�	Cryptage (il contient les m�thodes pour crypter et signe les donn�es ces m�thodes sont base sur des commandes au syst�mes d�exploitation pour utiliser le logiciel openssl et la gestion des fichiers du pc (comme la lecture du fichier g�n�rer par openssl )) . 

�	HelpersFunctions ( il contient des m�thodes qui nous aidons par exemple la m�thode statique bytesToHex() qui prend comme param�tre un tableau d�octets et retourne une String de Hex pour faciliter la lecture des r�sultats).

�	SecurityFile (contient les m�thodes li�es au Security file comme le choix du code pin) 

�	UserFileDataArea (contient les m�thodes pour cr�e sur un fichier d�utilisateur ou lire ce fichier).

�	Verifying (class pour v�rifier le r�sultat) 

�	Acos3Project (class qui contient le projet demander) 

�	Acos3Exception (class qui h�rite la class Exception charg� de traiter les Exceptions g�n�rer par notre code) 
