-- Delivery Planning: Task B7 - Generate gains an IsComplete parameter, default 'N'.
--
-- Mirrors the Combine action's parameter (5820450, AD_Process_Para 543276) exactly: same element
-- (2047 'Fertigstellen'), same reference (20 Yes-No), same seq (10), same default 'N' - Generate no
-- longer completes the delivery instruction automatically; completing it stays available as an
-- option (AC5: neither Generate nor Combine completes by default).
--
-- Bundled: the section 5.1(b) rename of AD_Process 585176 (approved 2026-08-26,
-- ai-work/31608/AGGREGATION-PROPOSAL.md lines 919, 1491, 1992). Drops "Lieferinstruktion" (the
-- document is named "Lieferanweisung" everywhere else in the glossary) and adds the cardinality
-- now that Generate and Combine sit side by side on the same grid:
--   de: "Lieferanweisung je Lieferplanung erzeugen"
--   en: "Generate Delivery Instruction per Planning"
--
-- AD_Process is self-owned (no AD_Element_ID, no propagation function) - base row + every
-- AD_Process_Trl row are updated directly here, by explicit language, rather than a blanket
-- "every IsTranslated='N' row gets the base text" pass:
--   de_DE / de_CH: already carried the (old) German text with IsTranslated='Y' -> set to the new
--     German text, IsTranslated stays 'Y'.
--   en_US: carried the OLD English name with IsTranslated='N' (a stale review flag - the text
--     itself was already real English, just never marked reviewed). Set to the NEW English name,
--     IsTranslated='Y'. A blanket IsTranslated='N' fallback would have overwritten this row's
--     correct English text with German, which is wrong for an English-language UI.
--   fr_CH: carried the OLD English name (not French) with IsTranslated='N' - i.e. it was already
--     an untranslated fallback masquerading as content, not a real French translation. Rather than
--     leave the now-retired English name in place, it is reset to the new German base text with
--     IsTranslated='N' - the same fallback shape a freshly seeded translation row gets (see
--     5820450's seed INSERT), so an unmaintained fr_CH tracks the current name. No French
--     translation is introduced here, deliberately: nobody on this branch has provided one.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Process_Para  543279 (IsComplete, on 585176; reuses the existing AD_Element 2047 'Fertigstellen')

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

-- fr_CH: no French translation exists on this branch; reset the stale (retired) English name to
-- the current German base text - the same fallback shape a freshly seeded translation row gets.
UPDATE AD_Process_Trl
SET Name='Lieferanweisung je Lieferplanung erzeugen',
    IsTranslated='N',
    Updated=TO_TIMESTAMP('2026-08-27 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585176 AND AD_Language='fr_CH'
;

-- ---------------------------------------------------------------------------------------------
-- 2) the IsComplete parameter - default 'N', mirrors Combine's 543276 (5820450) exactly
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

-- seed AD_Process_Para_Trl for every active system language ...
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_Para_ID=543279
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ... and fill them from the parameter's element, which owns the label in every language
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2047)
;
