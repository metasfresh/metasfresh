-- Boni refund engine: the refund always goes to the invoice partner; the "bonus recipient" setting of a refund line is removed again.
--
-- Removes what 5827800 / 5827820 / 5827900 created for it:
--   AD_Field 785603 (window 540113, tab 541106 "Rückvergütung") with its AD_UI_Element 654931 and AD_Element_Link
--   AD_Column 593708 C_Flatrate_RefundConfig.BonusRecipient and the physical column (its check constraint goes with it)
--   AD_Reference 542145 "BonusRecipient" with its list entries 544374 (S), 544375 (I)
--   AD_Element 585513 "BonusRecipient" (Bonusempfänger)
--   AD_Message 545895 ...SameBonusRecipient
-- Existing refund lines that were set to the shipment partner fall back to the invoice partner.
--
-- Pre-drop dependency sweep (views, functions, AD_Val_Rule, ColumnSQL, EXP_FormatLine) on 2026-10-07: no dependents.

-- 1) the field (anchored by the column, so a field of an overriding window is removed too)
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 593708)
;
DELETE FROM AD_UI_Element WHERE Labels_Selector_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 593708)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 593708)
;
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 593708)
;
DELETE FROM AD_Field_ContextMenu WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 593708)
;
DELETE FROM AD_UI_ElementField WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 593708)
;
DELETE FROM AD_UserDef_Field WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 593708)
;
DELETE FROM AD_User_SortPref_Line WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 593708)
;
DELETE FROM AD_Field WHERE AD_Column_ID = 593708
;

-- tab 541106: BonusRecipient was at SeqNoGrid=35; close the gap
UPDATE AD_Field SET SeqNoGrid=35, Updated=TO_TIMESTAMP('2026-10-07 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID = 785604 -- IsPackingOptionFiltered
;

-- 2) the column
DELETE FROM AD_Element_Link WHERE AD_Element_ID = 585513
;
DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 593708
;
DELETE FROM AD_Column WHERE AD_Column_ID = 593708
;
SELECT public.db_alter_table('C_Flatrate_RefundConfig', 'ALTER TABLE public.C_Flatrate_RefundConfig DROP COLUMN IF EXISTS BonusRecipient')
;

-- 3) the reference list
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID IN (SELECT AD_Ref_List_ID FROM AD_Ref_List WHERE AD_Reference_ID = 542145)
;
DELETE FROM AD_Ref_List WHERE AD_Reference_ID = 542145
;
DELETE FROM AD_Reference_Trl WHERE AD_Reference_ID = 542145
;
DELETE FROM AD_Reference WHERE AD_Reference_ID = 542145
;

-- 4) the element
DELETE FROM AD_Element_Trl WHERE AD_Element_ID = 585513
;
DELETE FROM AD_Element WHERE AD_Element_ID = 585513
;

-- 5) the error message "all refund lines of a condition need the same bonus recipient"
DELETE FROM AD_Message_Trl WHERE AD_Message_ID = 545895
;
DELETE FROM AD_Message WHERE AD_Message_ID = 545895
;
