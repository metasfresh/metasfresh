-- IDs allocated from idserver.metas.de on 2026-06-14:
--   AD_MigrationScript prefix: 5807680
--   AD_Process_Para_ID: 543251 (AD_InputDataSource_ID param for DD_Order_Candidate_EnqueueToProcess)

-- Process: DD_Order_Candidate_EnqueueToProcess (AD_Process_ID=585419)
-- Repoint ClassName to new core FQN
-- 2026-06-14T00:00:00.000Z
UPDATE AD_Process
SET ClassName = 'de.metas.distribution.ddordercandidate.process.DD_Order_Candidate_EnqueueToProcess',
    Updated   = TO_TIMESTAMP('2026-06-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Process_ID = 585419
;

-- Add optional AD_InputDataSource_ID parameter
-- 2026-06-14T00:00:00.000Z
INSERT INTO AD_Process_Para
    (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID,
     AD_Reference_ID, AD_Reference_Value_ID,
     ColumnName, Created, CreatedBy, Description, EntityType, FieldLength,
     Help, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted,
     IsMandatory, IsRange,
     Name, SeqNo, Updated, UpdatedBy)
VALUES
    (0, 541291 /*AD_Element: AD_InputDataSource_ID*/, 0, 585419, 543251 /*From ID Server*/,
     19 /*Table Direct*/, NULL,
     'AD_InputDataSource_ID',
     TO_TIMESTAMP('2026-06-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 'EE01', 10,
     NULL, 'Y', 'N', 'Y', 'N',
     'N', 'N',
     'Eingabequelle',
     10,
     TO_TIMESTAMP('2026-06-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- Trl skeleton for the new parameter
-- 2026-06-14T00:00:00.000Z
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
  AND t.AD_Process_Para_ID = 543251
  AND NOT EXISTS (
      SELECT 1 FROM AD_Process_Para_Trl tt
      WHERE tt.AD_Language = l.AD_Language
        AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID
  )
;
