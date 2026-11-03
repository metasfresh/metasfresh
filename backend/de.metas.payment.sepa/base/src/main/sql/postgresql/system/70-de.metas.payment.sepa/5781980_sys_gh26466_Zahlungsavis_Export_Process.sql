-- Run mode: SWING_CLIENT

-- Process: SEPA_Export_Transactions(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: SEPA_Export_ID
-- 2025-12-18T11:51:55.290Z
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT SEPA_Export_ID FROM SEPA_Export WHERE Record_ID =@C_PaySelection_ID@ and AD_Table_ID = get_table_id(''C_PaySelection'') ORDER BY SEPA_Export_ID DESC LIMIT 1',Updated=TO_TIMESTAMP('2025-12-18 11:51:55.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543087
;

-- Process: SEPA_Export_Transactions(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- Table: C_PaySelection
-- EntityType: de.metas.payment.sepa
-- 2025-12-18T11:55:25.297Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585547,426,541591,TO_TIMESTAMP('2025-12-18 11:55:25.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.payment.sepa','Y',TO_TIMESTAMP('2025-12-18 11:55:25.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Run mode: SWING_CLIENT

-- Process: SEPA_Export_Transactions(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- Table: C_PaySelection
-- EntityType: de.metas.payment.sepa
-- 2025-12-18T11:59:51.897Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2025-12-18 11:59:51.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541591
;

-- Name: SEPA-Exporttransaktionen
-- Action Type: P
-- Process: SEPA_Export_Transactions(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-12-18T12:01:09.680Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=542286
;

-- 2025-12-18T12:01:09.687Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=542286
;

-- 2025-12-18T12:01:09.690Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=542286 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: SEPA_Export_Transactions
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-12-18T12:02:14.297Z
UPDATE AD_Process SET Name='Zahlungsavis Export',Updated=TO_TIMESTAMP('2025-12-18 12:02:14.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585547
;

-- 2025-12-18T12:02:14.298Z
UPDATE AD_Process_Trl trl SET Name='Zahlungsavis Export' WHERE AD_Process_ID=585547 AND AD_Language='de_DE'
;

-- Process: SEPA_Export_Transactions(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-12-18T12:02:24.813Z
UPDATE AD_Process_Trl SET Name='Payment Notification Export',Updated=TO_TIMESTAMP('2025-12-18 12:02:24.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585547
;

-- 2025-12-18T12:02:24.814Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
