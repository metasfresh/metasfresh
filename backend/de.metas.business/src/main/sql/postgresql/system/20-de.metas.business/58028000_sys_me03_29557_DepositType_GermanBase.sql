-- Run mode: SWING_CLIENT

-- me03#29557 follow-up: move the German label to the BASE column of
-- AD_Reference / AD_Ref_List (introduced by 58023600). Reason:
-- the WebUI list-picker for List-typed columns renders option labels
-- from AD_Ref_List.Name directly in several paths and does NOT always
-- resolve through AD_Ref_List_Trl. The de_DE / de_CH _Trl rows are
-- present with IsTranslated='Y' and the right German text, but a
-- de_DE session still shows the base column 'Disposable Deposit' /
-- 'Reusable Deposit'. Fix: put German on the base column and keep the
-- English text as an en_US _Trl override.
-- See mf15-ai-dev-support/claude/.claude/skills/metasfresh-db/SKILL.md
-- §"AD_Message / AD_Element Base Language — German in the Base Column".

-- No new ID-server entities are needed — this migration only updates
-- existing rows (AD_Reference_ID=542089, AD_Ref_List_IDs 544229 / 544230,
-- AD_Element_ID=584866) introduced by 58023600.

-- ===========================================================================
-- 1. AD_Reference: 'Deposit Type' -> 'Pfandart' on the base column
-- ===========================================================================
UPDATE AD_Reference
SET    Name='Pfandart',
       Updated=TO_TIMESTAMP('2026-05-16 09:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
WHERE  AD_Reference_ID=542089
;

-- AD_Reference_Trl(en_US) keeps the English label as an override
UPDATE AD_Reference_Trl
SET    Name='Deposit Type', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-05-16 09:00:01','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
WHERE  AD_Reference_ID=542089 AND AD_Language='en_US'
;

-- AD_Reference_Trl(de_DE / de_CH) mirror the new base text (and are already
-- IsTranslated='Y' from the original migration; we just re-affirm the text
-- so the rows agree with the base column)
UPDATE AD_Reference_Trl
SET    Name='Pfandart', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-05-16 09:00:02','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
WHERE  AD_Reference_ID=542089 AND AD_Language IN ('de_DE','de_CH')
;

-- ===========================================================================
-- 2. AD_Ref_List: NRC -> 'Einwegpfand' on the base column
-- ===========================================================================
UPDATE AD_Ref_List
SET    Name='Einwegpfand',
       Updated=TO_TIMESTAMP('2026-05-16 09:00:03','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
WHERE  AD_Ref_List_ID=544229
;

UPDATE AD_Ref_List_Trl
SET    Name='Disposable Deposit', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-05-16 09:00:04','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
WHERE  AD_Ref_List_ID=544229 AND AD_Language='en_US'
;

UPDATE AD_Ref_List_Trl
SET    Name='Einwegpfand', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-05-16 09:00:05','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
WHERE  AD_Ref_List_ID=544229 AND AD_Language IN ('de_DE','de_CH')
;

-- ===========================================================================
-- 3. AD_Ref_List: RC -> 'Mehrwegpfand' on the base column
-- ===========================================================================
UPDATE AD_Ref_List
SET    Name='Mehrwegpfand',
       Updated=TO_TIMESTAMP('2026-05-16 09:00:06','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
WHERE  AD_Ref_List_ID=544230
;

UPDATE AD_Ref_List_Trl
SET    Name='Reusable Deposit', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-05-16 09:00:07','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
WHERE  AD_Ref_List_ID=544230 AND AD_Language='en_US'
;

UPDATE AD_Ref_List_Trl
SET    Name='Mehrwegpfand', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-05-16 09:00:08','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
WHERE  AD_Ref_List_ID=544230 AND AD_Language IN ('de_DE','de_CH')
;
