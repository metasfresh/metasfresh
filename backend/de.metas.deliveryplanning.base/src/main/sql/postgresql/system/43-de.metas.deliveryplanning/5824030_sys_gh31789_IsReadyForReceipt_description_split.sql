-- IsReadyForReceipt's description carried a clause that is only true on the VIEW, on the SHARED element:
--
--   "... eine Zeile ohne Lieferplanung ist immer bereit."
--   "... a row with no delivery planning is always ready."
--
-- Impact query for AD_Element 585451 (AD_Column / AD_Field.AD_Name_ID / AD_Window / AD_Tab / AD_Menu /
-- AD_Process_Para / AD_UI_Element.AD_Name_ID / WEBUI_KPI_Field) returns exactly two AD_Columns and nothing
-- else: M_Delivery_Planning.IsReadyForReceipt and RV_ReceiptDisposition_DeliveryPlanning.IsReadyForReceipt.
-- On M_Delivery_Planning EVERY row IS a planning, so "a row with no delivery planning" describes rows that
-- cannot exist there - the wording is wrong in one of the two usages.
--
-- Wrong-in-one-usage is the FORK case, and for a FIELD the fork is a dedicated element addressed by
-- AD_Field.AD_Name_ID (metasfresh-designing-windows section 4). So the shared element keeps the general
-- meaning and the view's FIELD overrides it; neither AD_Column nor any _Trl copy is written by hand.
--
-- Two earlier attempts in this script were wrong and are recorded so they are not retried:
--   * hand-written per-column UPDATEs of AD_Column/AD_Column_Trl with two different texts while both
--     columns still shared one element. update_Column_Translation_From_AD_Element (reached by
--     after_migration_sync_translations) stamps the element's description onto EVERY column pointing at it,
--     so that split survived only because the script happened to give element and columns the same
--     timestamp - the function skips rows where c.updated = e_trl.updated - and would have been silently
--     reverted by the next touch of the element.
--   * a second element carrying the same ColumnName. AD_Element has a UNIQUE constraint on ColumnName
--     (ad_element_columnname), so a clone under 'IsReadyForReceipt' cannot exist at all.
-- The dedicated element therefore carries its own ColumnName and is reached only via AD_Name_ID.
--
-- Base language is German; en_US is the translation override; de_CH mirrors de_DE; fr_CH takes the en_US
-- text with IsTranslated='N', per this change set's convention. Name/PrintName are identical to 585451's -
-- the caption does not change, only the description.
--
-- ID allocated from idserver.metas.de: AD_Element 585455.

-- ---------------------------------------------------------------------------
-- 1. The shared element keeps the general meaning only
-- ---------------------------------------------------------------------------
UPDATE AD_Element_Trl
   SET Description = 'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist.',
       Updated = TO_TIMESTAMP('2026-09-11 00:40:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585451 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl
   SET Description = 'Indicates whether the material receipt can be done for this row. A delivery planning is ready once it is allocated to a completed delivery instruction.',
       Updated = TO_TIMESTAMP('2026-09-11 00:40:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585451 AND AD_Language IN ('en_US','fr_CH')
;

-- ---------------------------------------------------------------------------
-- 2. The dedicated element for the view's FIELD, where unplanned rows exist
-- ---------------------------------------------------------------------------
INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,ColumnName,Name,PrintName,Description,EntityType)
SELECT 585455 /*From ID Server*/,0,0,'Y',
       TO_TIMESTAMP('2026-09-11 00:40:02','YYYY-MM-DD HH24:MI:SS'),100,
       TO_TIMESTAMP('2026-09-11 00:40:02','YYYY-MM-DD HH24:MI:SS'),100,
       'IsReadyForReceipt_ReceiptDisposition',
       'Bereit für Wareneingang','Bereit für Wareneingang',
       'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist; eine Zeile ohne Lieferplanung ist immer bereit.',
       'D'
WHERE NOT EXISTS (SELECT 1 FROM AD_Element WHERE AD_Element_ID=585455)
;

-- cloned per language from 585451 so the CAPTION stays identical in every language ...
INSERT INTO AD_Element_Trl (AD_Element_ID,AD_Language,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,PrintName,Description,IsTranslated)
SELECT 585455, src.AD_Language, 0,0,'Y',
       TO_TIMESTAMP('2026-09-11 00:40:03','YYYY-MM-DD HH24:MI:SS'),100,
       TO_TIMESTAMP('2026-09-11 00:40:03','YYYY-MM-DD HH24:MI:SS'),100,
       src.Name, src.PrintName, src.Description, src.IsTranslated
  FROM AD_Element_Trl src
 WHERE src.AD_Element_ID = 585451
   AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl x WHERE x.AD_Element_ID=585455 AND x.AD_Language=src.AD_Language)
;

-- ... and only the DESCRIPTION differs: it keeps the row-type clause
UPDATE AD_Element_Trl
   SET Description = 'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist; eine Zeile ohne Lieferplanung ist immer bereit.',
       Updated = TO_TIMESTAMP('2026-09-11 00:40:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585455 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl
   SET Description = 'Indicates whether the material receipt can be done for this row. A delivery planning is ready once it is allocated to a completed delivery instruction; a row with no delivery planning is always ready.',
       Updated = TO_TIMESTAMP('2026-09-11 00:40:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585455 AND AD_Language IN ('en_US','fr_CH')
;

-- ---------------------------------------------------------------------------
-- 3. The view's field overrides via AD_Name_ID - the column keeps the shared element
-- ---------------------------------------------------------------------------
UPDATE AD_Field
   SET AD_Name_ID = 585455,
       Updated = TO_TIMESTAMP('2026-09-11 00:40:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Field_ID = 784967
;

-- ---------------------------------------------------------------------------
-- 4. The sync propagates both - never hand-write a _Trl copy
--    585451 -> AD_Column/AD_Column_Trl of both tables + element-less fields
--    585455 -> AD_Field/AD_Field_Trl 784967, via its AD_Name_ID
-- ---------------------------------------------------------------------------
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585451)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585455)
;
