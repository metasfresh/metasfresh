-- Classname: de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook
-- 2025-10-31T09:37:35.735Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585521,'Y','de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook','N',TO_TIMESTAMP('2025-10-31 09:37:35.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Update Count Quantity to Book Quantity','json','N','N','xls','Java',TO_TIMESTAMP('2025-10-31 09:37:35.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_Inventory_Update_QtyCount_to_QtyBook')
;

-- 2025-10-31T09:37:35.740Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585521 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_Inventory_Update_QtyCount_to_QtyBook
-- Classname: de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook
-- 2025-10-31T09:37:50.268Z
UPDATE AD_Process SET AllowProcessReRun='N', IsFormatExcelFile='N',Updated=TO_TIMESTAMP('2025-10-31 09:37:50.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585521
;

-- Process: M_Inventory_Update_QtyCount_to_QtyBook(de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook)
-- 2025-10-31T09:38:22.251Z
UPDATE AD_Process_Trl SET Description='This function updates or aligns the physically counted quantity (CountQty) in inventory with the system quantity (BookQty) stored in the books.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-31 09:38:22.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585521
;

-- 2025-10-31T09:38:22.252Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_Inventory_Update_QtyCount_to_QtyBook(de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook)
-- 2025-10-31T11:46:25.853Z
UPDATE AD_Process_Trl SET Description='Diese Funktion aktualisiert die Zählmenge im Lagerbestand auf die im System geführte Buchmenge.
Sie wird verwendet, um Differenzen zwischen gezählter und gebuchter Menge auszugleichen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-31 11:46:25.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585521
;

-- 2025-10-31T11:46:25.856Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_Inventory_Update_QtyCount_to_QtyBook(de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook)
-- 2025-10-31T11:46:40.732Z
UPDATE AD_Process_Trl SET Name='Zählmenge auf Buchmenge aktualisieren',Updated=TO_TIMESTAMP('2025-10-31 11:46:40.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585521
;

-- 2025-10-31T11:46:40.734Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;


-- Process: M_Inventory_Update_QtyCount_to_QtyBook(de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook)
-- 2025-10-31T11:46:40.732Z
UPDATE AD_Process_Trl SET Name='Zählmenge auf Buchmenge aktualisieren',Updated=TO_TIMESTAMP('2025-10-31 11:46:40.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585521
;

-- 2025-10-31T11:46:40.734Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;



-- Process: M_Inventory_Update_QtyCount_to_QtyBook(de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook)
-- Table: M_Inventory
-- EntityType: D
-- 2025-10-31T13:18:43.204Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585521,321,541582,TO_TIMESTAMP('2025-10-31 13:18:43.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-10-31 13:18:43.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;


-- Process: M_Inventory_Update_QtyCount_to_QtyBook(de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook)
-- 2025-10-31T11:46:25.853Z
UPDATE AD_Process_Trl SET Description='Diese Funktion aktualisiert die Zählmenge im Lagerbestand auf die im System geführte Buchmenge.
Sie wird verwendet, um Differenzen zwischen gezählter und gebuchter Menge auszugleichen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-31 11:46:25.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585521
;

-- 2025-10-31T11:46:25.856Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Run mode: SWING_CLIENT

-- Value: M_Inventory_Update_QtyCount_to_QtyBook
-- Classname: de.metas.inventory.process.M_Inventory_Update_QtyCount_to_QtyBook
-- 2025-10-31T15:00:31.447Z
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2025-10-31 15:00:31.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585521
;


-- Value: M_Inventory_Update_QtyCount_to_QtyBook_ProcessedDoc
-- 2025-10-31T15:29:40.203Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545604,0,TO_TIMESTAMP('2025-10-31 15:29:40.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','We can not update processed Inventory.','I',TO_TIMESTAMP('2025-10-31 15:29:40.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_Inventory_Update_QtyCount_to_QtyBook_ProcessedDoc')
;

-- 2025-10-31T15:29:40.214Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545604 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: M_Inventory_Update_QtyCount_to_QtyBook_ProcessedDoc
-- 2025-10-31T15:29:47.633Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Wir können den verarbeiteten Bestand nicht aktualisieren.',Updated=TO_TIMESTAMP('2025-10-31 15:29:47.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545604
;

-- 2025-10-31T15:29:47.634Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: M_Inventory_Update_QtyCount_to_QtyBook_ProcessedDoc
-- 2025-10-31T15:29:53.123Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-31 15:29:53.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545604
;

-- Value: M_Inventory_Update_QtyCount_to_QtyBook_ProcessedDoc
-- 2025-10-31T15:29:58.262Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-31 15:29:58.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545604
;

-- Value: M_Inventory_Update_QtyCount_to_QtyBook_ProcessedDoc
-- 2025-10-31T15:30:02.561Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Wir können den verarbeiteten Bestand nicht aktualisieren.',Updated=TO_TIMESTAMP('2025-10-31 15:30:02.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545604
;

-- 2025-10-31T15:30:02.563Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

