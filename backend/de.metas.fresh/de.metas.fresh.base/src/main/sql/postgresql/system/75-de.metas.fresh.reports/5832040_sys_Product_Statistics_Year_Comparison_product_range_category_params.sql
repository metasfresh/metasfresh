-- Function DDL: 5832030_sys_Product_Statistics_Year_Comparison_product_range_category.sql
-- Report process "Artikelstatistik Jahresvergleich" / "Product Statistics Year Comparison" (AD_Process 585690):
--   the Product parameter becomes "Produkt von" / "Product from", plus "Produkt bis" / "Product to" (M_Product_To_ID) and
--   "Produkt Kategorie" / "Product Category" (M_Product_Category_ID).
-- Process parameter names are synchronised from their AD_Element (update_process_para_translation_from_ad_element), so the labels live on the elements:
--   new element "Produkt von" for the existing M_Product_ID parameter (its ColumnName stays M_Product_ID, the name the report and the function use),
--   and the German name "Produkt bis" on the existing element M_Product_To_ID (2658, so far named "To Product" in every language and not used by any column).
-- Both product parameters: Search on the M_Product table (AD_Reference 540272) with the validation rule 540377 "M_Product_of_@AD_Org_ID@_or_0".

-- Element "Produkt von"
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585570 /*From ID Server*/, 0, 'M_Product_From_ID', TO_TIMESTAMP('2026-10-29 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Produkt von', 'Produkt von', TO_TIMESTAMP('2026-10-29 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585570
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Product from', PrintName='Product from', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-29 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585570 AND AD_Language IN ('en_US', 'en_GB')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-29 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585570 AND AD_Language IN ('de_DE', 'de_CH')
;

-- Element M_Product_To_ID: German name "Produkt bis", English "Product to"; its old description (UOM conversion "to product") does not fit and is removed
UPDATE AD_Element SET Name='Produkt bis', PrintName='Produkt bis', Description=NULL, Help=NULL, Updated=TO_TIMESTAMP('2026-10-29 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=2658
;

UPDATE AD_Element_Trl SET Name='Produkt bis', PrintName='Produkt bis', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-29 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=2658 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Element_Trl SET Description=NULL, Help=NULL, Updated=TO_TIMESTAMP('2026-10-29 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=2658
;

UPDATE AD_Element_Trl SET Name='Product to', PrintName='Product to', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-29 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=2658 AND AD_Language IN ('en_US', 'en_GB')
;

-- Produkt von: the existing M_Product_ID parameter takes the new element and a Search on the M_Product table
UPDATE AD_Process_Para SET AD_Element_ID=585570, AD_Reference_Value_ID=540272, Name='Produkt von', Updated=TO_TIMESTAMP('2026-10-29 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_Para_ID=543363
;

-- Produkt bis
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 2658, 0, 585690, 543364 /*From ID Server*/, 30, 540272, 540377, 'M_Product_To_ID', TO_TIMESTAMP('2026-10-29 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'Produkt bis', 40, TO_TIMESTAMP('2026-10-29 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- Produkt Kategorie
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 453, 0, 585690, 543365 /*From ID Server*/, 19, 'M_Product_Category_ID', TO_TIMESTAMP('2026-10-29 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'Produkt Kategorie', 50, TO_TIMESTAMP('2026-10-29 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID IN (543364, 543365)
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- Parameter names and translations from the elements (the same sync that runs after every migration)
SELECT update_Process_Para_Translation_From_AD_Element(585570)
;

SELECT update_Process_Para_Translation_From_AD_Element(2658)
;

SELECT update_Process_Para_Translation_From_AD_Element(453)
;
