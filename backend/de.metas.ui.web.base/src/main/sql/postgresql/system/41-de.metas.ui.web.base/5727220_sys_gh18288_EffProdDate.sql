-- 2024-06-25T13:20:00.254Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583152,0,TO_TIMESTAMP('2024-06-25 16:20:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Eff. Prod. Datum','Eff. Prod. Datum',TO_TIMESTAMP('2024-06-25 16:20:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-25T13:20:00.271Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583152 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-06-25T13:20:09.775Z
UPDATE AD_Element_Trl SET Name='Eff. Prod. Date', PrintName='Eff. Prod. Date',Updated=TO_TIMESTAMP('2024-06-25 16:20:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583152 AND AD_Language='en_US'
;

-- 2024-06-25T13:20:09.809Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583152,'en_US') 
;

-- Field: Produktionsauftrag_OLD -> Produktionsaufträge -> Eff. Prod. Datum
-- Column: PP_Order.DateDelivered
-- Field: Produktionsauftrag_OLD(53009,EE01) -> Produktionsaufträge(53054,EE01) -> Eff. Prod. Datum
-- Column: PP_Order.DateDelivered
-- 2024-06-25T13:54:03.027Z
UPDATE AD_Field SET AD_Name_ID=583152, Description=NULL, Help=NULL, Name='Eff. Prod. Datum',Updated=TO_TIMESTAMP('2024-06-25 16:54:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54142
;

-- 2024-06-25T13:54:03.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583152)
;

-- 2024-06-25T13:54:03.045Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=54142
;

-- 2024-06-25T13:54:03.050Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(54142)
;
