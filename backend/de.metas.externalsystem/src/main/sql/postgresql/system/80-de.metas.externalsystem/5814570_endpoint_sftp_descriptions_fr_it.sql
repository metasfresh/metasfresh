-- Complete the fr_CH / it_CH translations for the four SFTP direction-tagged endpoint field
-- descriptions. Their fr_CH/it_CH rows were verbatim German copies (pre-existing translation debt);
-- provide proper French/Italian text and propagate to the field (which also creates the missing
-- field-level de_CH rows). Dedicated elements: SftpFilenamePattern 584677 (export), and the three
-- import-polling fields SftpPollingIntervalMs 584678 / SftpProcessedDirectory 584679 /
-- SftpErrorDirectory 584680.

-- SftpPollingIntervalMs (584678) -- import
UPDATE AD_Element_Trl SET Description='Fréquence de vérification des nouveaux fichiers sur le serveur SFTP, en millisecondes (par défaut : 60000 = 1 minute) (import uniquement)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584678 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description='Frequenza di controllo dei nuovi file sul server SFTP, in millisecondi (predefinito: 60000 = 1 minuto) (solo import)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584678 AND AD_Language='it_CH';

-- SftpProcessedDirectory (584679) -- import
UPDATE AD_Element_Trl SET Description='Répertoire distant vers lequel les fichiers traités avec succès sont déplacés (import uniquement)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584679 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description='Directory remota in cui vengono spostati i file elaborati con successo (solo import)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584679 AND AD_Language='it_CH';

-- SftpErrorDirectory (584680) -- import
UPDATE AD_Element_Trl SET Description='Répertoire distant vers lequel les fichiers ayant échoué sont déplacés (import uniquement)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584680 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description='Directory remota in cui vengono spostati i file non elaborati correttamente (solo import)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584680 AND AD_Language='it_CH';

-- SftpFilenamePattern (584677) -- export
UPDATE AD_Element_Trl SET Description='Modèle pour les noms de fichiers sortants, p. ex. DESADV_{documentno}_{timestamp}.json (export uniquement)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584677 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description='Modello per i nomi dei file in uscita, ad es. DESADV_{documentno}_{timestamp}.json (solo export)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584677 AND AD_Language='it_CH';

-- Propagate element descriptions to the derived AD_Field(_Trl) rows (also creates missing field de_CH rows).
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678, 'it_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'it_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'it_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, 'it_CH');
