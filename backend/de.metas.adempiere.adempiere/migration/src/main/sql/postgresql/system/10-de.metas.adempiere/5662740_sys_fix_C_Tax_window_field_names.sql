-- Field: Tax Rate(137,D) -> Tax(174,D) -> Requires Tax Certificate
-- Column: C_Tax.RequiresTaxCertificate
-- 2022-11-01T14:34:10.701Z
UPDATE AD_Field SET AD_Name_ID=NULL, Description='This tax rate requires the Business Partner to be tax exempt', Help='The Requires Tax Certificate indicates that a tax certificate is required for a Business Partner to be tax exempt.', Name='Requires Tax Certificate',Updated=TO_TIMESTAMP('2022-11-01 16:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2872
;

-- 2022-11-01T14:34:10.766Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1066) 
;

-- 2022-11-01T14:34:10.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=2872
;

-- 2022-11-01T14:34:10.859Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(2872)
;

-- 2022-11-01T14:37:00.411Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581631,0,TO_TIMESTAMP('2022-11-01 16:37:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Warehouse country','Warehouse country',TO_TIMESTAMP('2022-11-01 16:37:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-01T14:37:00.449Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581631 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Tax Rate(137,D) -> Tax(174,D) -> Warehouse country
-- Column: C_Tax.C_Country_ID
-- 2022-11-01T14:37:14.470Z
UPDATE AD_Field SET AD_Name_ID=581631, Description=NULL, Help=NULL, Name='Warehouse country',Updated=TO_TIMESTAMP('2022-11-01 16:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=974
;

-- 2022-11-01T14:37:14.507Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581631) 
;

-- 2022-11-01T14:37:14.547Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=974
;

-- 2022-11-01T14:37:14.582Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(974)
;

-- 2022-11-01T14:37:45.956Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581632,0,TO_TIMESTAMP('2022-11-01 16:37:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','From/to country','From/to country',TO_TIMESTAMP('2022-11-01 16:37:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-01T14:37:45.993Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581632 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Tax Rate(137,D) -> Tax(174,D) -> From/to country
-- Column: C_Tax.To_Country_ID
-- 2022-11-01T14:37:55.399Z
UPDATE AD_Field SET AD_Name_ID=581632, Description=NULL, Help=NULL, Name='From/to country',Updated=TO_TIMESTAMP('2022-11-01 16:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=976
;

-- 2022-11-01T14:37:55.435Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581632) 
;

-- 2022-11-01T14:37:55.475Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=976
;

-- 2022-11-01T14:37:55.510Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(976)
;

