-- 2023-07-04T18:25:08.502264Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540754,540411,TO_TIMESTAMP('2023-07-04 19:25:07.431','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Order -> ModCntr_Log',TO_TIMESTAMP('2023-07-04 19:25:07.431','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: ModCntr_Log_For_C_Order
-- 2023-07-04T18:27:12.651357300Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541791,TO_TIMESTAMP('2023-07-04 19:27:11.724','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ModCntr_Log_For_C_Order',TO_TIMESTAMP('2023-07-04 19:27:11.724','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-04T18:27:12.735883100Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541791 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_For_C_Order
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-07-04T18:30:58.173329600Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,586758,0,541791,542338,TO_TIMESTAMP('2023-07-04 19:30:57.639','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-04 19:30:57.639','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: ModCntr_Log_For_C_Order
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-07-04T18:36:04.464796Z
UPDATE AD_Ref_Table SET WhereClause='ModCntr_Log.C_Flatrate_Term_ID IN       (SELECT ft.C_Flatrate_Term_ID        from C_Flatrate_Term ft        where ft.C_OrderLine_Term_ID IN (SELECT ol.C_OrderLine_ID from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID / -1@))',Updated=TO_TIMESTAMP('2023-07-04 19:36:04.463','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541791
;

-- 2023-07-04T18:36:26.565911400Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541791,Updated=TO_TIMESTAMP('2023-07-04 19:36:26.31','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540411
;

