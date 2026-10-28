-- Delivery planning quantities -- one vocabulary across all four quantity figures (load/discharge x
-- planned/actual), and retire the two field-level label overrides that made the instruction line
-- (M_ShippingPackage, M_Delivery_Planning_Delivery_Instructions_V) read differently than the planning.
--
-- PlannedDischargeQuantity (581795) and ActualDischargeQuantity (581796) said "delivery" while meaning
-- "discharge", in a model that is sea-freight shaped (POL_ID/POD_ID). All four elements are also
-- realigned onto one en_US idiom (Qty/Quantity, Plan/Act -> Planned/Actual). PlannedLoadedQuantity's
-- (581794) German is unchanged; only its en_US was inconsistent with the others.
--
-- PrintName moves WITH Name for ALL FOUR elements (established convention for exactly these quantity
-- elements - see #14539/9dc11932bd2, whose 5676520_sys_gh14538_renaming_fields_delivery_planning.sql set
-- Name and PrintName to the identical value together on 581690/581794 as well). Otherwise PrintName keeps
-- the retired wording on printed documents/Jasper labels while the screen label is already fixed. The
-- fr_CH block at the bottom mirrors the en_US text onto all four for the same reason.
--
-- AD_Element 581927 ("Geladene Menge") and 581928 ("Entladene Menge") were label-only overrides
-- (AD_Field.AD_Name_ID) on 4 fields across 2 windows -- the only reason those fields showed different
-- wording than the planning. The underlying columns (ActualLoadQty/ActualDischargeQuantity) already
-- carry elements 581690/581796. Retiring the overrides and syncing 581690/581796's own wording is what
-- unifies the vocabulary.

-- 581690 ActualLoadQty: German corrected, en_US corrected
UPDATE AD_Element_Trl
   SET Name='Tatsächliche Verlademenge', PrintName='Tatsächliche Verlademenge', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-03 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581690 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Element_Trl
   SET Name='Actual Load Quantity', PrintName='Actual Load Quantity', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-03 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581690 AND AD_Language='en_US'
;

-- 581794 PlannedLoadedQuantity: German already correct in BOTH Name and PrintName (unchanged) -- only
-- en_US is realigned, PrintName with it (it still said "Plan Load Qty").
UPDATE AD_Element_Trl
   SET Name='Planned Load Quantity', PrintName='Planned Load Quantity', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-03 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581794 AND AD_Language='en_US'
;

-- 581795 PlannedDischargeQuantity: German corrected ("Liefermenge" -> "Entlademenge"), en_US corrected.
UPDATE AD_Element_Trl
   SET Name='Geplante Entlademenge', PrintName='Geplante Entlademenge', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-03 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581795 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Element_Trl
   SET Name='Planned Discharge Quantity', PrintName='Planned Discharge Quantity', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-03 10:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581795 AND AD_Language='en_US'
;

-- 581796 ActualDischargeQuantity: German corrected ("geliefert" -> "Entlademenge"), en_US corrected.
UPDATE AD_Element_Trl
   SET Name='Tatsächliche Entlademenge', PrintName='Tatsächliche Entlademenge', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-03 10:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581796 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Element_Trl
   SET Name='Actual Discharge Quantity', PrintName='Actual Discharge Quantity', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-03 10:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581796 AND AD_Language='en_US'
;

-- fr_CH per the fr_CH CONVENTION (stated once in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql): the en_US text,
-- IsTranslated='N'. Without this the fr_CH rows keep the pre-change en_US idiom ("Act Load Qty",
-- "Planned Loaded Quantity", "Plan/Act Delivered Qty") after every other language already moved to the
-- Planned/Actual Load/Discharge wording. All four elements, for the same reason.
UPDATE AD_Element_Trl trl
   SET Name         = en.Name,
       PrintName    = en.PrintName,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-03 10:00:07','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Element_Trl en
 WHERE en.AD_Element_ID = trl.AD_Element_ID
   AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH'
   AND trl.AD_Element_ID IN (581690, 581794, 581795, 581796)
;

-- Retire the per-field label overrides on all 4 fields that carried AD_Name_ID 581927/581928
-- (2 on window "Lieferanweisungen" tab "Versandpaket", 2 on window "Lieferplanung" tab
-- "Lieferanweisungen für die Lieferplanung"). Once AD_Name_ID is NULL, the field's Name/Description/Help
-- come from its own column's AD_Element (581690 / 581796) like every other field on these columns.
UPDATE AD_Field
   SET AD_Name_ID=NULL,
       Updated=TO_TIMESTAMP('2026-09-03 10:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Field_ID IN (710204, 710205, 710219, 710220)
;

-- Rebuild the AD_Element_Link rows for those 4 fields so the impact-analysis index no longer points
-- at the retired override elements.
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (710204, 710205, 710219, 710220);
SELECT AD_Element_Link_Create_Missing_Field(710204);
SELECT AD_Element_Link_Create_Missing_Field(710205);
SELECT AD_Element_Link_Create_Missing_Field(710219);
SELECT AD_Element_Link_Create_Missing_Field(710220);

-- Propagate the corrected element text down to AD_Element (base), AD_Column_Trl and AD_Field_Trl.
-- Runs after the AD_Name_ID cleanup above so the 4 previously-overridden fields now sync via their
-- column's element (the "via AD_Column" path requires AD_Field.AD_Name_ID IS NULL).
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581690);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581794);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581795);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581796);

-- Deactivate the two orphaned override elements -- no AD_Column and, after the cleanup above, no
-- active AD_Field references either one any more.
UPDATE AD_Element
   SET IsActive='N',
       Updated=TO_TIMESTAMP('2026-09-03 10:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID IN (581927, 581928)
;
