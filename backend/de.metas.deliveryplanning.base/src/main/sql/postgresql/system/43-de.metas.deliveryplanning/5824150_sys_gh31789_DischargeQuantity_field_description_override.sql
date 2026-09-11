-- 5823750 gave the two discharge-quantity columns their row-type explanation by UPDATEing
-- AD_Column/AD_Column_Trl.Description directly. That is the wrong mechanism.
--
-- AD_Element is the single source of truth for Name/Description/Help and their translations
-- (metasfresh-application-dictionary, MANDATORY review-rule). Text written straight onto a child record
-- has no element behind it, so update_TRL_Tables_On_AD_Element_TRL_Update has nothing to push from: the
-- description is orphaned and never propagates. Where the meaning genuinely differs per usage - which is
-- the case here, since the row-type rule holds only on this view and not on the other nine columns sharing
-- elements 581795/581796 - the sanctioned mechanism is a DEDICATED element reached via AD_Field.AD_Name_ID.
--
-- 5824030 already does exactly this in this same change set for IsReadyForReceipt. This script follows it
-- statement for statement; the shared elements keep the general meaning 5823750 correctly gave them.
--
-- Also undoes 5823750's AD_Column writes, so a stack that already applied it converges with a fresh one.
--
-- IDs allocated from idserver.metas.de on 2026-09-11:
--   AD_Element 585456 (PlannedDischargeQuantity, this view's field only)
--   AD_Element 585457 (ActualDischargeQuantity, this view's field only)

-- ---------------------------------------------------------------------------
-- 1. Undo the direct column writes 5823750 made
-- ---------------------------------------------------------------------------
UPDATE AD_Column SET Description=NULL, Updated=TO_TIMESTAMP('2026-09-11 09:40:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID IN (593501,593530)
;

UPDATE AD_Column_Trl SET Description=NULL, Updated=TO_TIMESTAMP('2026-09-11 09:40:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID IN (593501,593530)
;

-- ---------------------------------------------------------------------------
-- 2. PlannedDischargeQuantity - the dedicated element for this view's field
-- ---------------------------------------------------------------------------
INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,ColumnName,Name,PrintName,Description,EntityType)
SELECT 585456 /*From ID Server*/,0,0,'Y',
       TO_TIMESTAMP('2026-09-11 09:40:01','YYYY-MM-DD HH24:MI:SS'),100,
       TO_TIMESTAMP('2026-09-11 09:40:01','YYYY-MM-DD HH24:MI:SS'),100,
       'PlannedDischargeQuantity_ReceiptDisposition',
       'Geplante Entlademenge','Geplante Entlademenge',
       'Die für die Entladung geplante Menge. Auf einer Zeile mit Lieferplanung ist es die Entscheidung der Lieferplanung selbst; eine Zeile ohne Lieferplanung hat keine Planung, die man fragen könnte, daher gilt dort die bestellte Menge - auf diesen Zeilen stimmt der Wert deshalb mit der Spalte "Bestellt/ Beauftragt" überein.',
       'D'
WHERE NOT EXISTS (SELECT 1 FROM AD_Element WHERE AD_Element_ID=585456)
;

-- cloned per language from the shared element so the CAPTION stays identical in every language
INSERT INTO AD_Element_Trl (AD_Element_ID,AD_Language,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,PrintName,Description,IsTranslated)
SELECT 585456, src.AD_Language, 0,0,'Y',
       TO_TIMESTAMP('2026-09-11 09:40:02','YYYY-MM-DD HH24:MI:SS'),100,
       TO_TIMESTAMP('2026-09-11 09:40:02','YYYY-MM-DD HH24:MI:SS'),100,
       src.Name, src.PrintName, src.Description, src.IsTranslated
  FROM AD_Element_Trl src
 WHERE src.AD_Element_ID = 581795
   AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl x WHERE x.AD_Element_ID=585456 AND x.AD_Language=src.AD_Language)
;

-- ... and only the DESCRIPTION differs: it carries the row-type clause
UPDATE AD_Element_Trl
   SET Description = 'Die für die Entladung geplante Menge. Auf einer Zeile mit Lieferplanung ist es die Entscheidung der Lieferplanung selbst; eine Zeile ohne Lieferplanung hat keine Planung, die man fragen könnte, daher gilt dort die bestellte Menge - auf diesen Zeilen stimmt der Wert deshalb mit der Spalte "Bestellt/ Beauftragt" überein.',
       Updated = TO_TIMESTAMP('2026-09-11 09:40:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585456 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl
   SET Description = 'The quantity planned for discharge. On a row with a delivery planning it is the planning''s own decision; a row with no delivery planning has no planning to ask, so what was ordered applies there - which is why on those rows this equals the Qty Ordered column.',
       Updated = TO_TIMESTAMP('2026-09-11 09:40:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585456 AND AD_Language IN ('en_US','fr_CH')
;

-- ---------------------------------------------------------------------------
-- 3. ActualDischargeQuantity - the same, for its own field
-- ---------------------------------------------------------------------------
INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,ColumnName,Name,PrintName,Description,EntityType)
SELECT 585457 /*From ID Server*/,0,0,'Y',
       TO_TIMESTAMP('2026-09-11 09:40:05','YYYY-MM-DD HH24:MI:SS'),100,
       TO_TIMESTAMP('2026-09-11 09:40:05','YYYY-MM-DD HH24:MI:SS'),100,
       'ActualDischargeQuantity_ReceiptDisposition',
       'Tatsächliche Entlademenge','Tatsächliche Entlademenge',
       'Die tatsächlich entladene Menge. Auf einer Zeile mit Lieferplanung ist es die von der Lieferplanung erfasste tatsächliche Menge; auf einer Zeile ohne Lieferplanung die bereits eingegangene Menge.',
       'D'
WHERE NOT EXISTS (SELECT 1 FROM AD_Element WHERE AD_Element_ID=585457)
;

INSERT INTO AD_Element_Trl (AD_Element_ID,AD_Language,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,PrintName,Description,IsTranslated)
SELECT 585457, src.AD_Language, 0,0,'Y',
       TO_TIMESTAMP('2026-09-11 09:40:06','YYYY-MM-DD HH24:MI:SS'),100,
       TO_TIMESTAMP('2026-09-11 09:40:06','YYYY-MM-DD HH24:MI:SS'),100,
       src.Name, src.PrintName, src.Description, src.IsTranslated
  FROM AD_Element_Trl src
 WHERE src.AD_Element_ID = 581796
   AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl x WHERE x.AD_Element_ID=585457 AND x.AD_Language=src.AD_Language)
;

UPDATE AD_Element_Trl
   SET Description = 'Die tatsächlich entladene Menge. Auf einer Zeile mit Lieferplanung ist es die von der Lieferplanung erfasste tatsächliche Menge; auf einer Zeile ohne Lieferplanung die bereits eingegangene Menge.',
       Updated = TO_TIMESTAMP('2026-09-11 09:40:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585457 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl
   SET Description = 'The quantity actually discharged. On a row with a delivery planning it is the actual recorded by the planning; on a row with no delivery planning it is the quantity already moved on the receipt schedule.',
       Updated = TO_TIMESTAMP('2026-09-11 09:40:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585457 AND AD_Language IN ('en_US','fr_CH')
;

-- ---------------------------------------------------------------------------
-- 4. The view's fields override via AD_Name_ID - the columns keep the shared elements
-- ---------------------------------------------------------------------------
UPDATE AD_Field
   SET AD_Name_ID = 585456,
       Updated = TO_TIMESTAMP('2026-09-11 09:40:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Field_ID = 784950
;

UPDATE AD_Field
   SET AD_Name_ID = 585457,
       Updated = TO_TIMESTAMP('2026-09-11 09:40:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Field_ID = 784963
;

-- ---------------------------------------------------------------------------
-- 5. The sync propagates - never hand-write a _Trl copy
--    581795 / 581796 -> the shared columns, restoring the general meaning after step 1
--    585456 / 585457 -> AD_Field/AD_Field_Trl 784950 / 784963, via their AD_Name_ID
-- ---------------------------------------------------------------------------
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581795)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581796)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585456)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585457)
;
