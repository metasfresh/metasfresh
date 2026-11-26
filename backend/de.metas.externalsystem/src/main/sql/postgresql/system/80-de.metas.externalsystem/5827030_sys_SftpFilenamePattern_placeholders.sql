-- Element SftpFilenamePattern (584677): the description now lists all supported file-name placeholders.
-- Only Description is changed (not Name / Help). Base language is German; it_CH follows the convention of 5814550.

UPDATE AD_Element SET Description='Muster für ausgehende Dateinamen (nur Export). Platzhalter: {timestamp} = Sendezeitpunkt (yyyyMMdd_HHmmss), {documentno} = Belegnummer, {table} = Tabellenname, {recordid} = Datensatz-ID, {index} = laufende Nummer der Datei bei aufgeteiltem Export (leer sonst).', Updated=TO_TIMESTAMP('2026-09-29 10:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584677;

UPDATE AD_Element_Trl SET Description='Muster für ausgehende Dateinamen (nur Export). Platzhalter: {timestamp} = Sendezeitpunkt (yyyyMMdd_HHmmss), {documentno} = Belegnummer, {table} = Tabellenname, {recordid} = Datensatz-ID, {index} = laufende Nummer der Datei bei aufgeteiltem Export (leer sonst).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 10:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584677 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Description='Pattern for outbound file names (export only). Placeholders: {timestamp} = send time (yyyyMMdd_HHmmss), {documentno} = document number, {table} = table name, {recordid} = record ID, {index} = running number of the file in a split export (empty otherwise).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 10:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584677 AND AD_Language='en_US';

UPDATE AD_Element_Trl SET Description='Modèle pour les noms de fichiers sortants (export uniquement). Espaces réservés : {timestamp} = heure d''envoi (yyyyMMdd_HHmmss), {documentno} = numéro de document, {table} = nom de table, {recordid} = ID d''enregistrement, {index} = numéro du fichier dans un export fractionné (vide sinon).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 10:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584677 AND AD_Language='fr_CH';

UPDATE AD_Element_Trl SET Description='Modello per i nomi dei file in uscita (solo export). Segnaposto: {timestamp} = ora di invio (yyyyMMdd_HHmmss), {documentno} = numero del documento, {table} = nome della tabella, {recordid} = ID del record, {index} = numero progressivo del file in un export suddiviso (altrimenti vuoto).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 10:10:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584677 AND AD_Language='it_CH';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, NULL);
