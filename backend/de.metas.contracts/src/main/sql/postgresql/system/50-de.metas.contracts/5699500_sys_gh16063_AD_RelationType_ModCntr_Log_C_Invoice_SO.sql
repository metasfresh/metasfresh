
-- Reference: ModCntr_Log_For_SO
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-08-18T09:34:46.203217400Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS ( SELECT 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID@  AND ModCntr_Log.ad_table_id = get_table_id(''C_OrderLine'') AND ModCntr_Log.Record_ID = ol.C_OrderLine_ID )',Updated=TO_TIMESTAMP('2023-08-18 12:34:46.202','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541805
;

-- 2023-08-18T09:36:40.664943500Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=540100,Updated=TO_TIMESTAMP('2023-08-18 12:36:40.664','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540415
;

-- Name: SO_with_ModCntr_Log
-- 2023-08-18T09:37:28.427094300Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541806
;

-- 2023-08-18T09:37:28.438907100Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541806
;

-- Name: ModCntr_Log_Target_C_Invoice_SO
-- 2023-08-18T09:39:16.354990500Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541815,TO_TIMESTAMP('2023-08-18 12:39:16.191','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ModCntr_Log_Target_C_Invoice_SO',TO_TIMESTAMP('2023-08-18 12:39:16.191','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-08-18T09:39:16.356990200Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541815 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_Target_C_Invoice_SO
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-08-18T09:41:51.558484400Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,586758,0,541815,542338,TO_TIMESTAMP('2023-08-18 12:41:51.553','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-08-18 12:41:51.553','YYYY-MM-DD HH24:MI:SS.US'),100,'EXISTS ( SELECT 1 from C_InvoiceLine il where il.C_Invoice_ID = @C_Invoice_ID@ AND ModCntr_Log.ad_table_id = get_table_id(''C_InvoiceLine'') AND ModCntr_Log.Record_ID = il.C_InvoiceLine_ID )')
;

-- 2023-08-18T09:42:54.820533500Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540101,541815,540419,TO_TIMESTAMP('2023-08-18 12:42:54.67','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Invoice (SO) -> ModCntr_Log',TO_TIMESTAMP('2023-08-18 12:42:54.67','YYYY-MM-DD HH24:MI:SS.US'),100)
;

