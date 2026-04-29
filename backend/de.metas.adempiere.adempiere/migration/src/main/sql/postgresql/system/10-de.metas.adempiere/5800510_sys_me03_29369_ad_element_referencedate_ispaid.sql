-- 2026-04-30 https://github.com/metasfresh/me03/issues/29369
-- AD_Element rows for ReferenceDate and IsPaid columns

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, Name, PrintName, Description, Help, EntityType) VALUES
  (584813, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0, 'ReferenceDate', 'Bezugsdatum', 'Bezugsdatum', 'Datum, aus dem das Fälligkeitsdatum berechnet wird (Fälligkeitsdatum = Bezugsdatum + Verschiebungstage).', 'Im Zahlungsplan wird das Fälligkeitsdatum für jede Zeile berechnet als Bezugsdatum + Verschiebungstage. Das Bezugsdatum ist je nach Bezugsdatumstyp das Bestelldatum, Akkreditiv-Datum, Konnossement-Datum, ETA-Datum oder Rechnungsdatum. Solange noch kein reales Datum verfügbar ist (Status Ausstehend), bleibt das Bezugsdatum leer.', 'D'),
  (584814, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0, 'IsPaid', 'Bezahlt', 'Bezahlt', 'Wenn aktiviert, ist diese Zahlungsplan-Zeile bezahlt.', 'Vom System gepflegtes Kennzeichen — wird genau dann auf "Y" gesetzt, wenn der Status "Bezahlt" (PR) ist. Dient als zusätzliche, indizierbare Spalte für SQL-Filter und Berichte.', 'D');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, PrintName, Description, Help, IsTranslated)
  SELECT t.AD_Element_ID, l.AD_Language, t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy,
         t.Name, t.PrintName, t.Description, t.Help, 'N'
    FROM AD_Element t, AD_Language l
   WHERE t.AD_Element_ID = 584813
     AND l.IsSystemLanguage = 'Y'
     AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl WHERE AD_Element_ID = t.AD_Element_ID AND AD_Language = l.AD_Language);

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, PrintName, Description, Help, IsTranslated)
  SELECT t.AD_Element_ID, l.AD_Language, t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy,
         t.Name, t.PrintName, t.Description, t.Help, 'N'
    FROM AD_Element t, AD_Language l
   WHERE t.AD_Element_ID = 584814
     AND l.IsSystemLanguage = 'Y'
     AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl WHERE AD_Element_ID = t.AD_Element_ID AND AD_Language = l.AD_Language);

-- Update translations for ReferenceDate (584813)
UPDATE AD_Element_Trl SET
  Name = 'Bezugsdatum',
  PrintName = 'Bezugsdatum',
  Description = 'Datum, aus dem das Fälligkeitsdatum berechnet wird (Fälligkeitsdatum = Bezugsdatum + Verschiebungstage).',
  Help = 'Im Zahlungsplan wird das Fälligkeitsdatum für jede Zeile berechnet als Bezugsdatum + Verschiebungstage. Das Bezugsdatum ist je nach Bezugsdatumstyp das Bestelldatum, Akkreditiv-Datum, Konnossement-Datum, ETA-Datum oder Rechnungsdatum. Solange noch kein reales Datum verfügbar ist (Status Ausstehend), bleibt das Bezugsdatum leer.',
  IsTranslated = 'N'
WHERE AD_Element_ID = 584813 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl SET
  Name = 'Reference date',
  PrintName = 'Reference date',
  Description = 'Date the due date is computed from (DueDate = ReferenceDate + OffsetDays).',
  Help = 'In the pay schedule, each line''s DueDate is computed as ReferenceDate + OffsetDays. ReferenceDate is whichever of OrderDate, LetterOfCreditDate, BillOfLadingDate, ETADate, or InvoiceDate matches the line''s reference-date type. Empty while the line is still Pending — populated once a real date becomes available.',
  IsTranslated = 'Y'
WHERE AD_Element_ID = 584813 AND AD_Language = 'en_US';

-- Update translations for IsPaid (584814)
UPDATE AD_Element_Trl SET
  Name = 'Bezahlt',
  PrintName = 'Bezahlt',
  Description = 'Wenn aktiviert, ist diese Zahlungsplan-Zeile bezahlt.',
  Help = 'Vom System gepflegtes Kennzeichen — wird genau dann auf "Y" gesetzt, wenn der Status "Bezahlt" (PR) ist. Dient als zusätzliche, indizierbare Spalte für SQL-Filter und Berichte.',
  IsTranslated = 'N'
WHERE AD_Element_ID = 584814 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl SET
  Name = 'Paid',
  PrintName = 'Paid',
  Description = 'When checked, this pay-schedule line is paid.',
  Help = 'System-managed flag — set to Y when Status is Paid (PR). Provided as a separate, indexable column for SQL filtering and reporting.',
  IsTranslated = 'Y'
WHERE AD_Element_ID = 584814 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584813);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584814);
