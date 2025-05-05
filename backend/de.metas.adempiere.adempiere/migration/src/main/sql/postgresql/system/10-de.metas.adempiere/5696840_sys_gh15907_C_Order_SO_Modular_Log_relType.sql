-- Name: ModCntr_Log_For_SO
-- 2023-07-25T10:59:04.475870700Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541805,TO_TIMESTAMP('2023-07-25 13:59:04.291','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ModCntr_Log_For_SO',TO_TIMESTAMP('2023-07-25 13:59:04.291','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-25T10:59:04.487871800Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541805 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_For_SO
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-07-25T11:00:03.601218900Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,586758,0,541805,542338,TO_TIMESTAMP('2023-07-25 14:00:03.533','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-25 14:00:03.533','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Log.Record_ID IN (SELECT ol.C_OrderLine_ID from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID / -1@) AND ModCntr_Log.IsSOTrx = ''Y''')
;

-- Name: SO_with_ModCntr_Log
-- 2023-07-25T11:14:43.926881600Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541806,TO_TIMESTAMP('2023-07-25 14:14:43.784','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','SO_with_ModCntr_Log',TO_TIMESTAMP('2023-07-25 14:14:43.784','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-25T11:14:43.930051Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541806 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: SO_with_ModCntr_Log
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-07-25T11:15:36.696173Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541806,259,TO_TIMESTAMP('2023-07-25 14:15:36.689','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-25 14:15:36.689','YYYY-MM-DD HH24:MI:SS.US'),100,'EXISTS (select 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@) AND IsSOTrx = ''Y''')
;

-- 2023-07-25T11:18:58.057830700Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541806,541805,540415,TO_TIMESTAMP('2023-07-25 14:18:57.935','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Order (SO)-> ModCntr_Log',TO_TIMESTAMP('2023-07-25 14:18:57.935','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: ModCntr_Log_For_SO
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-07-25T17:09:17.246573200Z
UPDATE AD_Ref_Table SET WhereClause='ModCntr_Log.Record_ID IN (SELECT ol.C_OrderLine_ID from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID / -1@)',Updated=TO_TIMESTAMP('2023-07-25 20:09:17.246','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541805
;


