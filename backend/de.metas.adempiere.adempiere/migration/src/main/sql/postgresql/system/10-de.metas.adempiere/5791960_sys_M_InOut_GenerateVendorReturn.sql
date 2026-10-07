-- Migration: Register M_InOut_GenerateVendorReturn process
-- Purpose: Generate vendor return from completed material receipt (me03#28144)

--
-- 1. AD_Process: M_InOut_GenerateVendorReturn
--
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585591,'Y','de.metas.ui.web.shipment.process.M_InOut_GenerateVendorReturn','N',TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Lieferantenrücklieferung erstellen','json','N','N','xls','Java',TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'),100,'M_InOut_GenerateVendorReturn');

--
-- 2. AD_Process_Trl
--
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585591
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID);

-- Set English translation
UPDATE AD_Process_Trl SET Name='Generate Vendor Return', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585591 AND AD_Language='en_US';

--
-- 3. AD_Table_Process: Link process to M_InOut table (AD_Table_ID=319)
--    WEBUI_DocumentAction='Y' so it appears in the document actions menu on receipts
--
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default)
VALUES (0,0,585591,319,541629,TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'),100,'D','Y',TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'),100,'Y','N','N','N','N');

--
-- 4. AD_Field: Add Return_Origin_InOut_ID field on Vendor Return window header tab (AD_Tab_ID=53276)
--    AD_Column_ID=591915 (Return_Origin_InOut_ID on M_InOut)
--    Display as read-only so users can see the linked receipt
--
INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_Column_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy)
VALUES (0,0,774826,53276,591915,TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'),100,'D','Y','Y','Y','N','N','N','Y','N','Ursprüngliche Lieferung',390,0,TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'),100);

-- AD_Field_Trl for Return_Origin_InOut_ID field
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774826
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

-- English translation for the field
UPDATE AD_Field_Trl SET Name='Original Delivery', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=774826 AND AD_Language='en_US';

--
-- 5. AD_SysConfig: VendorReturnsInOut.FailIfNoHUsAssigned (default Y)
--
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541796,'S',TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'),100,'D','Y','VendorReturnsInOut.FailIfNoHUsAssigned',TO_TIMESTAMP('2026-03-04 22:00','YYYY-MM-DD HH24:MI'),100,'Y');
