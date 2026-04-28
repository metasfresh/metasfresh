-- Run mode: SWING_CLIENT

-- Element: awb
-- 2025-05-30T12:38:40.771Z
UPDATE AD_Element_Trl SET Name='Sendungsnummer', PrintName='Sendungsnummer',Updated=TO_TIMESTAMP('2025-05-30 12:38:40.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='de_CH'
;

-- 2025-05-30T12:38:40.818Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-30T12:38:42.304Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'de_CH')
;

-- Element: awb
-- 2025-05-30T12:38:48.141Z
UPDATE AD_Element_Trl SET Name='Sendungsnummer', PrintName='Sendungsnummer',Updated=TO_TIMESTAMP('2025-05-30 12:38:48.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='de_DE'
;

-- 2025-05-30T12:38:48.189Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-30T12:38:49.834Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577217,'de_DE')
;

-- 2025-05-30T12:38:49.882Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'de_DE')
;

-- Element: awb
-- 2025-05-30T12:39:24.778Z
UPDATE AD_Element_Trl SET Name='AWB', PrintName='AWB',Updated=TO_TIMESTAMP('2025-05-30 12:39:24.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='en_US'
;

-- 2025-05-30T12:39:24.824Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-30T12:39:26.032Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'en_US')
;

-- 2025-05-30T12:44:41.738Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583677,0,TO_TIMESTAMP('2025-05-30 12:44:41.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Sendungsreferenz','Sendungsreferenz',TO_TIMESTAMP('2025-05-30 12:44:41.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-30T12:44:41.786Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583677 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-05-30T12:44:50.143Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-30 12:44:50.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583677 AND AD_Language='de_CH'
;

-- 2025-05-30T12:44:50.236Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583677,'de_CH')
;

-- Element: null
-- 2025-05-30T12:44:52.284Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-30 12:44:52.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583677 AND AD_Language='de_DE'
;

-- 2025-05-30T12:44:52.378Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583677,'de_DE')
;

-- 2025-05-30T12:44:52.425Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583677,'de_DE')
;

-- Element: null
-- 2025-05-30T12:45:13.328Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sending Reference', PrintName='Sending Reference',Updated=TO_TIMESTAMP('2025-05-30 12:45:13.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583677 AND AD_Language='en_US'
;

-- 2025-05-30T12:45:13.376Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-30T12:45:14.474Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583677,'en_US')
;

-- Field: DHL Versandauftrag(540743,D) -> DHL_ShipmetnOrder(542067,D) -> Sendungsreferenz
-- Column: DHL_ShipmentOrder.CustomerReference
-- 2025-05-30T12:45:45.241Z
UPDATE AD_Field SET AD_Name_ID=583677, Description=NULL, Help=NULL, Name='Sendungsreferenz',Updated=TO_TIMESTAMP('2025-05-30 12:45:45.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=589602
;

-- 2025-05-30T12:45:45.289Z
UPDATE AD_Field_Trl trl SET Name='Sendungsreferenz' WHERE AD_Field_ID=589602 AND AD_Language='de_DE'
;

-- 2025-05-30T12:45:45.338Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583677)
;

-- 2025-05-30T12:45:45.408Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589602
;

-- 2025-05-30T12:45:45.456Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(589602)
;

-- Column: DHL_ShipmentOrder.awb
-- 2025-05-30T12:48:18.810Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-05-30 12:48:18.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569203
;

-- Column: DHL_ShipmentOrder.CustomerReference
-- 2025-05-30T12:48:31.959Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-05-30 12:48:31.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569094
;

-- Column: DHL_ShipmentOrder.C_BPartner_ID
-- 2025-05-30T12:48:48.990Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-05-30 12:48:48.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569090
;

