-- 2023-07-04T17:37:19.910893900Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540755,540754,540410,TO_TIMESTAMP('2023-07-04 18:37:18.873','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Flatrate_Term -> C_Order ',TO_TIMESTAMP('2023-07-04 18:37:18.873','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: C_Flatrate_Term_Source
-- 2023-07-04T17:43:04.741298800Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541787,TO_TIMESTAMP('2023-07-04 18:43:03.829','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Flatrate_Term_Source',TO_TIMESTAMP('2023-07-04 18:43:03.829','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-04T17:43:04.876806700Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541787 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Flatrate_Term_Source
-- Table: C_Flatrate_Term
-- Key: C_Flatrate_Term.C_Flatrate_Term_ID
-- 2023-07-04T17:48:04.587508300Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,545802,0,541787,540320,540359,TO_TIMESTAMP('2023-07-04 18:48:04.042','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-04 18:48:04.042','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: C_Order_Term_C_Flatrate_Term
-- 2023-07-04T17:53:07.727833700Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541788,TO_TIMESTAMP('2023-07-04 18:53:06.716','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Order_Term_C_Flatrate_Term',TO_TIMESTAMP('2023-07-04 18:53:06.716','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-04T17:53:07.809693800Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541788 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Order_Term_C_Flatrate_Term
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-07-04T17:57:31.790228700Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541788,259,TO_TIMESTAMP('2023-07-04 18:57:31.264','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-04 18:57:31.264','YYYY-MM-DD HH24:MI:SS.US'),100,'EXISTS (select 1 from C_Order o where o.C_Order_ID = @C_Order_Term_ID/-1@)')
;

-- 2023-07-04T17:57:48.213655100Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541787, AD_Reference_Target_ID=541788,Updated=TO_TIMESTAMP('2023-07-04 18:57:47.965','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540410
;

-- Reference: C_Order_Term_C_Flatrate_Term
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-07-04T18:03:53.988494200Z
UPDATE AD_Ref_Table SET WhereClause='C_Order.C_Order_ID = @C_Order_Term_ID/-1@',Updated=TO_TIMESTAMP('2023-07-04 19:03:53.988','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541788
;
