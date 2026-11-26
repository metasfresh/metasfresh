-- Process "Erzeuge Vertrag" (C_Flatrate_Term_Create_For_BPartners): optional end date parameter.
-- A contract whose conditions' transition has duration 0 keeps the entered end date and is refused without one,
-- so without this parameter the process cannot create such contracts (e.g. compensation-group contracts).
-- The entered end date is applied only when the conditions' transition has duration 0; for a duration > 0 the process
-- ignores it, so the end date computed from the transition stands.
--
-- IDs allocated from idserver.metas.de on 2026-09-30:
--   AD_Process_Para 543331

INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, ColumnName, Name, Description, Help, AD_Reference_ID, SeqNo,
                             EntityType, FieldLength, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange)
SELECT 543331 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-30 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-30 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       540460, e.AD_Element_ID, e.ColumnName, e.Name, e.Description, e.Help, 15, 25,
       'de.metas.contracts', 0, 'N', 'Y', 'N', 'N', 'N'
FROM AD_Element e
WHERE e.AD_Element_ID = 294 -- EndDate
ON CONFLICT (AD_Process_Para_ID) DO NOTHING
;

-- translations are taken over from the EndDate element, for this parameter only
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, t.AD_Process_Para_ID, et.Name, et.Description, et.Help, et.IsTranslated, t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Process_Para t
         JOIN AD_Element_Trl et ON et.AD_Element_ID = t.AD_Element_ID
         JOIN AD_Language l ON l.AD_Language = et.AD_Language AND l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
WHERE t.AD_Process_Para_ID = 543331
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;
