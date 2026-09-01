-- Delivery Planning: "Generate Delivery Instruction" (AD_Process 585176) gains an IsComplete
-- parameter defaulting to 'N', so completing the generated delivery instruction is the planner's
-- choice rather than automatic -- the same parameter Combine carries. The process is renamed at the
-- same time to state its cardinality, one delivery instruction per planning, now that Generate and
-- Combine sit side by side on the same grid.
--
-- AD_Process owns its labels directly (no AD_Element_ID, no propagation function), so the base row
-- and each AD_Process_Trl row are updated here by explicit language.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Process_Para  543279 (IsComplete, on 585176; reuses the existing AD_Element 2047 'Fertigstellen')
--
-- Any stack that applied this script BEFORE the _Trl seed covered a base language that is not
-- flagged as a system language needs this once -- the runner will not re-run an applied file.
-- A no-op wherever the base language is also flagged a system language, which is the usual setup:
--   INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
--   SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
--     FROM AD_Language l, AD_Process_Para t
--    WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543279
--      AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);
--   SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2047);

-- ---------------------------------------------------------------------------------------------
-- 1) rename AD_Process 585176 + its translations
-- ---------------------------------------------------------------------------------------------
UPDATE AD_Process
SET Name='Lieferanweisung je Lieferplanung erzeugen',
    Updated=TO_TIMESTAMP('2026-08-27 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585176
;

UPDATE AD_Process_Trl
SET Name='Lieferanweisung je Lieferplanung erzeugen',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-08-27 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585176 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Process_Trl
SET Name='Generate Delivery Instruction per Planning',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-08-27 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585176 AND AD_Language='en_US'
;

-- fr_CH has no French translation: fall back to the current German base text with
-- IsTranslated='N', the shape a freshly seeded translation row gets.
UPDATE AD_Process_Trl
SET Name='Lieferanweisung je Lieferplanung erzeugen',
    IsTranslated='N',
    Updated=TO_TIMESTAMP('2026-08-27 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585176 AND AD_Language='fr_CH'
;

-- ---------------------------------------------------------------------------------------------
-- 2) the IsComplete parameter - default 'N', mirroring Combine's 543276
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, ColumnName, Name, SeqNo,
                             AD_Reference_ID, FieldLength, DefaultValue,
                             IsCentrallyMaintained, IsMandatory, IsRange, IsEncrypted, ShowInActiveValues, EntityType)
VALUES (543279 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        585176, 2047, 'IsComplete', 'Fertigstellen', 10,
        20, 0, 'N',
        'Y', 'N', 'N', 'N', 'N', 'D')
;

-- seed AD_Process_Para_Trl for every active system or base language ...
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543279
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ... and fill them from the parameter's element, which owns the label in every language
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2047)
;
