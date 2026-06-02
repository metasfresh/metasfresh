-- Process: C_Invoice_Candidate_Generate (AD_Process_ID=540166)
-- ParameterName: IsPartialInvoice
-- 2026-05-08T10:00:00.000Z
INSERT INTO AD_Process_Para
    (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID,
     ColumnName, Created, CreatedBy, Description, EntityType, FieldLength,
     Help, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
     Name, SeqNo, Updated, UpdatedBy)
VALUES
    (0, 584794 /*From ID Server*/, 0, 540166, 543194 /*From ID Server*/, 20,
     'IsPartialInvoice',
     TO_TIMESTAMP('2026-05-08 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
     'de.metas.invoicecandidate', 1,
     'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, wie der Anzahlungsbetrag zugeordnet wird: Teilrechnung (Y) → MIN(Wareneingang × LC%, verbleibende Anzahlung); Endrechnung (N) → verbleibende Anzahlung vollständig verbrauchen. Standard Y (Teilrechnung) auf Belegart-Ebene; pro Rechnung überschreibbar, solange der Belegstatus Entwurf (DR) oder In Bearbeitung (IP) ist.',
     'Y', 'N', 'Y', 'N', 'N', 'N',
     'Teilrechnung',
     10,
     TO_TIMESTAMP('2026-05-08 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2026-05-08T10:00:00.000Z
INSERT INTO AD_Process_Para_Trl
    (AD_Language, AD_Process_Para_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language,
    t.AD_Process_Para_ID,
    t.Description,
    t.Help,
    t.Name,
    'N',
    t.AD_Client_ID,
    t.AD_Org_ID,
    t.Created,
    t.CreatedBy,
    t.Updated,
    t.UpdatedBy,
    'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543194
  AND NOT EXISTS (
      SELECT 1 FROM AD_Process_Para_Trl tt
      WHERE tt.AD_Language = l.AD_Language
        AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID
  )
;
