-- Update AD_Element description for SftpFilenamePattern to document available placeholders

-- German (base language)
UPDATE AD_Element
SET Description = 'Muster für ausgehende Dateinamen. Platzhalter: {timestamp} = Zeitstempel (yyyyMMdd_HHmmss), {documentno} = Belegnummer, {table} = Tabellenname, {recordid} = Datensatz-ID. Beispiel: DESADV_{documentno}_{timestamp}.json',
    Help        = 'Definiert das Namensmuster für Dateien, die per SFTP übertragen werden. Verfügbare Platzhalter in geschweiften Klammern: {timestamp} wird durch den aktuellen Zeitstempel im Format yyyyMMdd_HHmmss ersetzt (z.B. 20260328_143022). {documentno} wird durch die Belegnummer des exportierten Dokuments ersetzt (z.B. Lieferscheinnummer) — steht nur zur Verfügung, wenn der exportierte Datensatz eine Belegnummer hat; andernfalls bleibt {documentno} als Text im Dateinamen stehen. {table} wird durch den Tabellennamen ersetzt (z.B. M_InOut). {recordid} wird durch die Datensatz-ID ersetzt. Nicht auflösbare Platzhalter bleiben unverändert im Dateinamen stehen.',
    Updated     = TO_TIMESTAMP('2026-03-28 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584677; -- SftpFilenamePattern

-- English translation
UPDATE AD_Element_Trl
SET Description = 'Pattern for outbound filenames. Placeholders: {timestamp} = current time (yyyyMMdd_HHmmss), {documentno} = document number, {table} = table name, {recordid} = record ID. Example: DESADV_{documentno}_{timestamp}.json',
    Help        = 'Defines the naming pattern for files delivered via SFTP. Available placeholders in curly braces: {timestamp} is replaced with the current timestamp in yyyyMMdd_HHmmss format (e.g. 20260328_143022). {documentno} is replaced with the document number of the exported record (e.g. shipment number) — only available if the exported record has a DocumentNo column; otherwise {documentno} remains as literal text in the filename. {table} is replaced with the table name (e.g. M_InOut). {recordid} is replaced with the record ID. Unresolvable placeholders are left unchanged in the filename.',
    Updated     = TO_TIMESTAMP('2026-03-28 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584677
  AND AD_Language = 'en_US';
