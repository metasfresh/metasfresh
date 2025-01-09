-- 2025-01-09T10:57:03.280Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583422,0,TO_TIMESTAMP('2025-01-09 11:57:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferung an','Lieferung an',TO_TIMESTAMP('2025-01-09 11:57:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-01-09T10:57:03.342Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583422 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-01-09T10:57:36.301Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivery to', PrintName='Delivery to',Updated=TO_TIMESTAMP('2025-01-09 11:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583422 AND AD_Language='en_US'
;

-- 2025-01-09T10:57:36.425Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583422,'en_US') 
;

-- Field: Auftrag_OLD -> Auftrag -> Lieferung an
-- Column: C_Order.DropShip_BPartner_ID
-- Field: Auftrag_OLD(143,D) -> Auftrag(186,D) -> Lieferung an
-- Column: C_Order.DropShip_BPartner_ID
-- 2025-01-09T10:58:28.611Z
UPDATE AD_Field SET AD_Name_ID=583422, Description=NULL, Help=NULL, Name='Lieferung an',Updated=TO_TIMESTAMP('2025-01-09 11:58:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=55410
;

-- 2025-01-09T10:58:28.662Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583422) 
;

-- 2025-01-09T10:58:28.741Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=55410
;

-- 2025-01-09T10:58:28.793Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(55410)
;

-- Column: C_Order.DropShip_BPartner_ID
-- Column: C_Order.DropShip_BPartner_ID
-- 2025-01-09T10:59:56.394Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-09 11:59:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=55314
;

-- Column: C_Order.IsDropShip
-- Column: C_Order.IsDropShip
-- 2025-01-09T11:01:11.127Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-09 12:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11580
;

