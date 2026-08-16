-- Value: GoodsIssueQuantityParameterError
-- 2023-06-16T07:58:30.347Z
UPDATE AD_Message SET MsgText='Für diese Lieferregel muss die Anzahl kleiner oder gleich der verfügbaren Menge sein.
',Updated=TO_TIMESTAMP('2023-06-16 09:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545247
;

-- 2023-06-16T07:58:30.349Z
UPDATE AD_Message_Trl trl SET MsgText='Für diese Lieferregel muss die Anzahl kleiner oder gleich der verfügbaren Menge sein.
' WHERE AD_Message_ID=545247 AND AD_Language='de_DE'
;

-- Value: GoodsIssueQuantityParameterError
-- 2023-06-16T07:59:53.753Z
UPDATE AD_Message_Trl SET MsgText='Für diese Lieferregel muss die Anzahl kleiner oder gleich der verfügbaren Menge sein.',Updated=TO_TIMESTAMP('2023-06-16 09:59:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545247
;

-- Value: GoodsIssueQuantityParameterError
-- 2023-06-16T08:00:00.401Z
UPDATE AD_Message_Trl SET MsgText='For this delivery rule the Quantity needs to be smaller or equal to Qty Available.',Updated=TO_TIMESTAMP('2023-06-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545247
;

-- Value: GoodsIssueQuantityParameterError
-- 2023-06-16T08:00:06.091Z
UPDATE AD_Message_Trl SET MsgText='For this delivery rule the Quantity needs to be smaller or equal to Qty Available.',Updated=TO_TIMESTAMP('2023-06-16 10:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545247
;

-- 2023-06-16T12:15:13.451Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582444,0,'QtyAvailableParam',TO_TIMESTAMP('2023-06-16 14:15:13','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Qty Available','Qty Available',TO_TIMESTAMP('2023-06-16 14:15:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-16T12:15:13.453Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582444 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: QtyAvailableParam
-- 2023-06-16T12:15:51.961Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-16 14:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582444 AND AD_Language='en_US'
;

-- 2023-06-16T12:15:51.963Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582444,'en_US') 
;

-- Element: QtyAvailableParam
-- 2023-06-16T12:16:06.583Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verfügbare Menge', PrintName='Verfügbare Menge',Updated=TO_TIMESTAMP('2023-06-16 14:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582444 AND AD_Language='de_CH'
;

-- 2023-06-16T12:16:06.586Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582444,'de_CH') 
;

-- Element: QtyAvailableParam
-- 2023-06-16T12:16:16.418Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verfügbare Menge', PrintName='Verfügbare Menge',Updated=TO_TIMESTAMP('2023-06-16 14:16:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582444 AND AD_Language='de_DE'
;

-- 2023-06-16T12:16:16.419Z
UPDATE AD_Element SET Name='Verfügbare Menge', PrintName='Verfügbare Menge' WHERE AD_Element_ID=582444
;

-- 2023-06-16T12:16:22.916Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582444,'de_DE') 
;

-- 2023-06-16T12:16:22.919Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582444,'de_DE') 
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: QtyAvailableParam
-- 2023-06-16T12:18:15.546Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,582444,0,585194,542644,29,'QtyAvailableParam',TO_TIMESTAMP('2023-06-16 14:18:15','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Verfügbare Menge','1=1',26,TO_TIMESTAMP('2023-06-16 14:18:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-16T12:18:15.548Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542644 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-06-16T12:18:15.552Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582444) 
;

