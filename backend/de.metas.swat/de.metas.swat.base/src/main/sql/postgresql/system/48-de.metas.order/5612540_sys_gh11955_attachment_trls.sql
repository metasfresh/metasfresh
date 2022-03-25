-- 2021-11-09T07:55:35.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580201,0,TO_TIMESTAMP('2021-11-09 09:55:35','YYYY-MM-DD HH24:MI:SS'),100,'Zu benutzender Lieferant, wenn aus dieser Auftragsposition eine Bestellposition erzeugt wird','de.metas.order','Zu benutzender Lieferant, wenn aus dieser Auftragsposition eine Bestellposition erzeugt wird','Y','Lieferant','Lieferant',TO_TIMESTAMP('2021-11-09 09:55:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-09T07:55:35.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580201 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-09T07:56:02.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Vendor to be used for this sales order line when a purchase order line is created for it', Help='Vendor to be used for this sales order line when a purchase order line is created for it', Name='Vendor', PrintName='Vendor',Updated=TO_TIMESTAMP('2021-11-09 09:56:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580201 AND AD_Language='en_US'
;

-- 2021-11-09T07:56:02.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580201,'en_US') 
;

-- 2021-11-09T07:56:46.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580201, Description='Zu benutzender Lieferant, wenn aus dieser Auftragsposition eine Bestellposition erzeugt wird', Help='Zu benutzender Lieferant, wenn aus dieser Auftragsposition eine Bestellposition erzeugt wird', Name='Lieferant',Updated=TO_TIMESTAMP('2021-11-09 09:56:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=660988
;

-- 2021-11-09T07:56:46.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580201) 
;

-- 2021-11-09T07:56:46.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=660988
;

-- 2021-11-09T07:56:46.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(660988)
;

-- 2021-11-09T07:58:45.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Übersprungene Auftragszeilen: {0}',Updated=TO_TIMESTAMP('2021-11-09 09:58:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545060
;

-- 2021-11-09T08:00:55.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545079,0,TO_TIMESTAMP('2021-11-09 10:00:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','Missing vendor for lines: {}','I',TO_TIMESTAMP('2021-11-09 10:00:55','YYYY-MM-DD HH24:MI:SS'),100,'MissingVendorForOrderLine')
;

-- 2021-11-09T08:00:55.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545079 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-11-09T08:01:07.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Missing vendor for lines: {0}', MsgType='E',Updated=TO_TIMESTAMP('2021-11-09 10:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545079
;

-- 2021-11-09T08:02:24.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.order.C_Order_CreatePOFromSOs.Missing_C_BPartner_Vendor_ID',Updated=TO_TIMESTAMP('2021-11-09 10:02:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545079
;

-- 2021-11-09T08:20:11.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@IsVendorInOrderLinesRequired/''N''@=''N''',Updated=TO_TIMESTAMP('2021-11-09 10:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=256
;

-- 2021-11-09T08:30:02.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580202,0,'IsDirectlyAttachToPurchaseOrder',TO_TIMESTAMP('2021-11-09 10:30:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','Attached','Attached',TO_TIMESTAMP('2021-11-09 10:30:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-09T08:30:02.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580202 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- 2021-11-09T08:31:32.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Angehängt', PrintName='Angehängte',Updated=TO_TIMESTAMP('2021-11-09 10:31:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580202 AND AD_Language='de_CH'
;

-- 2021-11-09T08:31:32.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580202,'de_CH')
;

-- 2021-11-09T08:31:39.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Angehängte', PrintName='Angehängt',Updated=TO_TIMESTAMP('2021-11-09 10:31:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580202 AND AD_Language='de_DE'
;

-- 2021-11-09T08:31:39.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580202,'de_DE')
;

-- 2021-11-09T08:31:39.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580202,'de_DE')
;

-- 2021-11-09T08:31:39.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsDirectlyAttachToPurchaseOrder', Name='Angehängt', Description=NULL, Help=NULL WHERE AD_Element_ID=580202
;

-- 2021-11-09T08:31:39.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDirectlyAttachToPurchaseOrder', Name='Angehängt', Description=NULL, Help=NULL, AD_Element_ID=580202 WHERE UPPER(ColumnName)='ISDIRECTLYATTACHTOPURCHASEORDER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-09T08:31:39.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDirectlyAttachToPurchaseOrder', Name='Angehängt', Description=NULL, Help=NULL WHERE AD_Element_ID=580202 AND IsCentrallyMaintained='Y'
;

-- 2021-11-09T08:31:39.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Angehängt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580202) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580202)
;

-- 2021-11-09T08:31:39.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Angehängte', Name='Angehängt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580202)
;

-- 2021-11-09T08:31:39.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Angehängt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580202
;

-- 2021-11-09T08:31:39.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Angehängt', Description=NULL, Help=NULL WHERE AD_Element_ID = 580202
;

-- 2021-11-09T08:31:39.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Angehängt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580202
;

-- 2021-11-09T08:31:43.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Angehängte', PrintName='Angehängt',Updated=TO_TIMESTAMP('2021-11-09 10:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580202 AND AD_Language='nl_NL'
;

-- 2021-11-09T08:31:43.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580202,'nl_NL')
;

-- 2021-11-09T08:57:50.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Lieferantenangabe für Zeile {0} fehlt',Updated=TO_TIMESTAMP('2021-11-09 10:57:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545079
;

-- 2021-11-09T08:57:53.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Missing vendor for line {0}',Updated=TO_TIMESTAMP('2021-11-09 10:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545079
;

-- 2021-11-09T08:57:55.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Missing vendor for lines: {0}',Updated=TO_TIMESTAMP('2021-11-09 10:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545079
;

-- 2021-11-09T08:57:58.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Lieferantenangabe für Zeile {0} fehlt',Updated=TO_TIMESTAMP('2021-11-09 10:57:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545079
;
