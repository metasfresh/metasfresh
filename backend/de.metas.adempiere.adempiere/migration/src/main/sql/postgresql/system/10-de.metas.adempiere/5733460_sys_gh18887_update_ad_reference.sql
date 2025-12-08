-- Name: C_Aggregation for PP_Order_Candidate
-- 2024-09-16T10:32:51.514Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541888,TO_TIMESTAMP('2024-09-16 13:32:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Aggregation for PP_Order_Candidate',TO_TIMESTAMP('2024-09-16 13:32:51','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-09-16T10:32:51.519Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541888 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: C_Aggregation for PP_Order_Candidate
-- 2024-09-16T10:33:17.957Z
UPDATE AD_Reference SET EntityType='de.metas.aggregation',Updated=TO_TIMESTAMP('2024-09-16 13:33:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541888
;

-- Reference: C_Aggregation for PP_Order_Candidate
-- Table: C_Aggregation
-- Key: C_Aggregation.C_Aggregation_ID
-- 2024-09-16T10:35:07.806Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,551846,0,541888,540649,TO_TIMESTAMP('2024-09-16 13:35:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y','N','N',TO_TIMESTAMP('2024-09-16 13:35:07','YYYY-MM-DD HH24:MI:SS'),100,'AD_Table_ID = 541913')
;

-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:36:24.298Z
UPDATE AD_Column SET AD_Reference_Value_ID=540620,Updated=TO_TIMESTAMP('2024-09-16 13:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588980
;

-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:36:35.506Z
UPDATE AD_Column SET AD_Reference_Value_ID=540531,Updated=TO_TIMESTAMP('2024-09-16 13:36:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588980
;

-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:36:56.269Z
UPDATE AD_Column SET AD_Reference_Value_ID=541888,Updated=TO_TIMESTAMP('2024-09-16 13:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588980
;

-- 2024-09-16T10:36:58.189Z
INSERT INTO t_alter_column values('m_product_planningschema','C_Manufacturing_Aggregation_ID','NUMERIC(10)',null,null)
;

-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:38:43.737Z
UPDATE AD_Column SET AD_Reference_Value_ID=541888,Updated=TO_TIMESTAMP('2024-09-16 13:38:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588979
;

