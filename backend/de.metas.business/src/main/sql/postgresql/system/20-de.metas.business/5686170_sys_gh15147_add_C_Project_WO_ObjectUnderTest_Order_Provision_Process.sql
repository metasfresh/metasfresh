-- Value: C_Project_Order_Provision
-- 2023-04-27T15:14:32.953359200Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585259,'Y','N',TO_TIMESTAMP('2023-04-27 18:14:31.9','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Order Provision','json','N','N','xls','Java',TO_TIMESTAMP('2023-04-27 18:14:31.9','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Project_Order_Provision')
;

-- 2023-04-27T15:14:32.967356500Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585259 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Project_Order_Provision
-- 2023-04-27T15:15:30.881774700Z
UPDATE AD_Process_Trl SET Name='Beistellung Bestellen',Updated=TO_TIMESTAMP('2023-04-27 18:15:30.881','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585259
;

-- Process: C_Project_Order_Provision
-- 2023-04-27T15:15:33.403597300Z
UPDATE AD_Process_Trl SET Name='Beistellung Bestellen',Updated=TO_TIMESTAMP('2023-04-27 18:15:33.403','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585259
;

-- 2023-04-27T15:15:33.404597900Z
UPDATE AD_Process SET Name='Beistellung Bestellen' WHERE AD_Process_ID=585259
;

-- Process: C_Project_Order_Provision
-- 2023-04-27T15:15:41.513537100Z
UPDATE AD_Process_Trl SET Name='Provision Order',Updated=TO_TIMESTAMP('2023-04-27 18:15:41.513','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585259
;

-- Process: C_Project_Order_Provision
-- 2023-04-27T15:15:49.354499800Z
UPDATE AD_Process_Trl SET Name='Provision Order',Updated=TO_TIMESTAMP('2023-04-27 18:15:49.353','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585259
;

-- Process: C_Project_Order_Provision
-- ParameterName: Parameter_C_BPartner_ID
-- 2023-04-27T15:17:30.060299Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577421,0,585259,542622,30,'Parameter_C_BPartner_ID',TO_TIMESTAMP('2023-04-27 18:17:29.874','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnet einen Geschäftspartner','U',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',10,TO_TIMESTAMP('2023-04-27 18:17:29.874','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-04-27T15:17:30.062242500Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542622 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-04-27T15:17:30.092334600Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(577421) 
;

-- Process: C_Project_Order_Provision
-- ParameterName: Parameter_C_BPartner_ID
-- 2023-04-27T15:17:36.530359400Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-04-27 18:17:36.529','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542622
;

-- Process: C_Project_Order_Provision
-- ParameterName: Parameter_C_BPartner_ID
-- 2023-04-27T15:18:28.110299Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540353, DefaultValue='@SQL=SELECT C_BPartner_ID FROM C_Project WHERE C_Project_ID=@C_Project_ID@',Updated=TO_TIMESTAMP('2023-04-27 18:18:28.11','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542622
;

-- Process: C_Project_Order_Provision
-- ParameterName: PurchaseDatePromised
-- 2023-04-27T15:21:00.359478200Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,544170,0,585259,542623,15,'PurchaseDatePromised',TO_TIMESTAMP('2023-04-27 18:21:00.207','YYYY-MM-DD HH24:MI:SS.US'),100,'@SQL=SELECT MAX(WODeliveryDate) FROM C_Project_WO_Step WHERE C_Project_ID=@C_Project_ID@','de.metas.purchasecandidate',0,'Y','N','Y','N','Y','N','Liefer-Zusagedatum',20,TO_TIMESTAMP('2023-04-27 18:21:00.207','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-04-27T15:21:00.361477100Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542623 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-04-27T15:21:00.362477400Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(544170) 
;

-- Value: C_Project_Order_Provision
-- Classname: de.metas.project.process.C_WO_Project_ObjectUnderTest_OrderProvision
-- 2023-04-27T15:27:22.250316700Z
UPDATE AD_Process SET Classname='de.metas.project.process.C_WO_Project_ObjectUnderTest_OrderProvision',Updated=TO_TIMESTAMP('2023-04-27 18:27:22.249','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585259
;

-- Process: C_Project_Order_Provision(de.metas.project.process.C_WO_Project_ObjectUnderTest_OrderProvision)
-- ParameterName: C_BPartner_ID
-- 2023-04-27T15:33:03.015790400Z
UPDATE AD_Process_Para SET ColumnName='C_BPartner_ID',Updated=TO_TIMESTAMP('2023-04-27 18:33:03.015','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542622
;

-- Process: C_Project_Order_Provision(de.metas.project.process.C_WO_Project_ObjectUnderTest_OrderProvision)
-- ParameterName: C_BPartner_ID
-- 2023-04-27T15:35:14.283242300Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-04-27 18:35:14.282','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542622
;

-- Process: C_Project_Order_Provision(de.metas.project.process.C_WO_Project_ObjectUnderTest_OrderProvision)
-- ParameterName: DatePromised
-- 2023-04-27T15:36:16.535158300Z
UPDATE AD_Process_Para SET AD_Element_ID=269, ColumnName='DatePromised', Description='Zugesagter Termin für diesen Auftrag', Help='Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.', Name='Zugesagter Termin',Updated=TO_TIMESTAMP('2023-04-27 18:36:16.535','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542623
;

-- 2023-04-27T15:36:16.540156700Z
UPDATE AD_Process_Para_Trl trl SET Description='Zugesagter Termin für diesen Auftrag',Help='Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.',Name='Zugesagter Termin' WHERE AD_Process_Para_ID=542623 AND AD_Language='de_DE'
;

-- 2023-04-27T15:36:16.544276100Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(269) 
;

-- Process: C_Project_Order_Provision(de.metas.project.process.C_WO_Project_ObjectUnderTest_OrderProvision)
-- ParameterName: DatePromised
-- 2023-04-27T15:36:22.444602500Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-04-27 18:36:22.444','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542623
;

-- Value: C_Project_WO_ObjectUnderTest
-- 2023-05-02T11:30:10.245035400Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545264,0,TO_TIMESTAMP('2023-05-02 14:30:09.977','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','You cannot create provision order since this project has Object Under Test records with no product associated.','I',TO_TIMESTAMP('2023-05-02 14:30:09.977','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Project_WO_ObjectUnderTest')
;

-- 2023-05-02T11:30:10.253862100Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545264 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_Project_WO_ObjectUnderTest
-- 2023-05-02T11:30:17.421171Z
UPDATE AD_Message_Trl SET MsgText='Sie können keinen Bereitstellungsauftrag erstellen, da dieses Projekt Datensätze von "Object Under Test" enthält, denen kein Produkt zugeordnet ist.',Updated=TO_TIMESTAMP('2023-05-02 14:30:17.421','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545264
;

-- Value: C_Project_WO_ObjectUnderTest
-- 2023-05-02T11:30:20.726654400Z
UPDATE AD_Message_Trl SET MsgText='Sie können keinen Bereitstellungsauftrag erstellen, da dieses Projekt Datensätze von "Object Under Test" enthält, denen kein Produkt zugeordnet ist.',Updated=TO_TIMESTAMP('2023-05-02 14:30:20.726','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545264
;

-- Value: C_Project_WO_ObjectUnderTest
-- 2023-05-02T11:30:36.711019600Z
UPDATE AD_Message_Trl SET MsgText='Sie können keinen Bereitstellungsauftrag erstellen, da dieses Projekt Datensätze von "Object Under Test" enthält, denen kein Produkt zugeordnet ist.',Updated=TO_TIMESTAMP('2023-05-02 14:30:36.711','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545264
;

-- 2023-05-02T11:30:36.711019600Z
UPDATE AD_Message SET MsgText='Sie können keinen Bereitstellungsauftrag erstellen, da dieses Projekt Datensätze von "Object Under Test" enthält, denen kein Produkt zugeordnet ist.' WHERE AD_Message_ID=545264
;

-- Value: C_Project_WO_ObjectUnderTest
-- 2023-05-02T11:30:42.949589300Z
UPDATE AD_Message_Trl SET MsgText='You cannot create provision order since this project has Object Under Test records with no product associated.',Updated=TO_TIMESTAMP('2023-05-02 14:30:42.949','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545264
;

