-- Run mode: SWING_CLIENT

-- Per-field override of name/description on the Lieferweg field M_Shipper.DocumentCopies (AD_Field 785688).
-- The shared AD_Element 866 (DocumentCopies) stays untouched: it is also used by C_DocType and C_BP_PrintFormat.
-- Semantics shown to the user: empty = fall back to business partner print format / document type, 0 = print nothing, N = N copies.
--
-- IDs allocated from idserver.metas.de on 2026-10-19:
--   AD_Element 585544 (name-only element, ColumnName NULL, used as AD_Field.AD_Name_ID)

-- Field gets the EARLIER timestamp, element + element translations the LATER ones (propagation guard f.updated <> e_trl.updated)
-- 2026-10-19T11:00:00.000Z
UPDATE AD_Field
SET AD_Name_ID=585544, Name='Lieferschein-Kopien', Description='Anzahl der Lieferschein-Kopien für Lieferungen mit diesem Lieferweg. Leer = Anzahl aus dem Geschäftspartner-Druckformat bzw. der Belegart; 0 = kein Druck.', Help='Anzahl der Lieferschein-Kopien für Lieferungen mit diesem Lieferweg. Leer = Anzahl aus dem Geschäftspartner-Druckformat bzw. der Belegart; 0 = kein Druck.',
    Updated=TO_TIMESTAMP('2026-10-19 11:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=785688
;

-- Element: name-only override for the Lieferweg copy-count field
-- 2026-10-19T11:00:10.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585544 /*From ID Server*/,0,NULL,TO_TIMESTAMP('2026-10-19 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Lieferschein-Kopien für Lieferungen mit diesem Lieferweg. Leer = Anzahl aus dem Geschäftspartner-Druckformat bzw. der Belegart; 0 = kein Druck.','D','Anzahl der Lieferschein-Kopien für Lieferungen mit diesem Lieferweg. Leer = Anzahl aus dem Geschäftspartner-Druckformat bzw. der Belegart; 0 = kein Druck.','Y','Lieferschein-Kopien','Lieferschein-Kopien',TO_TIMESTAMP('2026-10-19 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-10-19T11:00:10.100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585544
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: de_CH
-- 2026-10-19T11:00:11.000Z
UPDATE AD_Element_Trl
SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-19 11:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585544 AND AD_Language='de_CH'
;

-- 2026-10-19T11:00:11.010Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585544,'de_CH')
;

-- 2026-10-19T11:00:11.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585544,'de_CH')
;

-- Element: de_DE (base language)
-- 2026-10-19T11:00:12.000Z
UPDATE AD_Element_Trl
SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-19 11:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585544 AND AD_Language='de_DE'
;

-- 2026-10-19T11:00:12.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585544,'de_DE')
;

-- Element: en_US
-- 2026-10-19T11:00:13.000Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Delivery Note Copies', PrintName='Delivery Note Copies',
    Description='Number of delivery note copies for shipments with this shipping method. Empty = copies from the business partner print format or document type; 0 = do not print.',
    Help='Number of delivery note copies for shipments with this shipping method. Empty = copies from the business partner print format or document type; 0 = do not print.',
    Updated=TO_TIMESTAMP('2026-10-19 11:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585544 AND AD_Language='en_US'
;

-- 2026-10-19T11:00:13.010Z
UPDATE AD_Element base
SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage() AND base.AD_Element_ID=585544
;

-- 2026-10-19T11:00:13.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585544,'en_US')
;

-- Propagate the name element to the field translations (field has AD_Name_ID set)
-- 2026-10-19T11:00:14.000Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585544)
;

-- Recreate the element link of the field
-- 2026-10-19T11:00:14.100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=785688
;

-- 2026-10-19T11:00:14.200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(785688)
;
