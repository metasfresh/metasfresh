-- The shared AD_Element behind IsAutoPrint carries a description written for its original usage
-- (a Handling-Unit auto-print flag) that does not fit the C_BP_PrintFormat.IsAutoPrint field added
-- by an earlier script in this folder. Fork a dedicated element for THIS field only (via
-- AD_Field.AD_Name_ID) with a print-format-appropriate description, on both live windows that carry
-- the field. Do not touch the shared AD_Column.AD_Element_ID.

-- 2026-09-01T12:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585402 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-01 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob dieses Druckformat automatisch gedruckt wird.','D',NULL,'Y','Sofort drucken','Sofort drucken',TO_TIMESTAMP('2026-09-01 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01T12:00:01.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585402 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAutoPrint (dedicated, C_BP_PrintFormat only)
-- 2026-09-01T12:00:02.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585402 AND AD_Language='de_CH'
;

-- 2026-09-01T12:00:03.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585402,'de_CH')
;

-- Element: IsAutoPrint (dedicated, C_BP_PrintFormat only)
-- 2026-09-01T12:00:04.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585402 AND AD_Language='de_DE'
;

-- 2026-09-01T12:00:05.000Z
/* DDL */ select update_ad_element_on_ad_element_trl_update(585402,'de_DE')
;

-- 2026-09-01T12:00:06.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585402,'de_DE')
;

-- Element: IsAutoPrint (dedicated, C_BP_PrintFormat only)
-- 2026-09-01T12:00:07.000Z
UPDATE AD_Element_Trl SET Description='Determines whether this print format is printed automatically.', IsTranslated='Y', Name='Print immediately', PrintName='Print immediately', Updated=TO_TIMESTAMP('2026-09-01 12:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585402 AND AD_Language='en_US'
;

-- 2026-09-01T12:00:08.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585402,'en_US')
;

-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Sofort drucken
-- Field: Geschäftspartner Pharma(540409,U) -> Druck Format(541019,D) -> Sofort drucken
-- Column: C_BP_PrintFormat.IsAutoPrint
-- 2026-09-01T12:00:09.000Z
UPDATE AD_Field SET AD_Name_ID=585402, Description='Legt fest, ob dieses Druckformat automatisch gedruckt wird.', Help=NULL, Name='Sofort drucken', Updated=TO_TIMESTAMP('2026-09-01 12:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID IN (783060,783062)
;

-- 2026-09-01T12:00:10.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585402)
;

-- 2026-09-01T12:00:11.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (783060,783062)
;

-- 2026-09-01T12:00:12.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(783060)
;

-- 2026-09-01T12:00:13.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(783062)
;
