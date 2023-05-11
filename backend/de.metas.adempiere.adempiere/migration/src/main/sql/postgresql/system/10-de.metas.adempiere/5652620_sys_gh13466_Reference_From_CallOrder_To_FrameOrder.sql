



-- Name: C_Order target for C_OrderLine (via C_FLatrate_Term)
-- 2022-08-23T14:16:23.778Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541644,TO_TIMESTAMP('2022-08-23 17:16:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_Order target for C_OrderLine (via C_FLatrate_Term)',TO_TIMESTAMP('2022-08-23 17:16:23','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-23T14:16:23.812Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541644 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Order target for C_OrderLine (via C_FLatrate_Term)
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2022-08-23T14:22:20.087Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,2161,0,541644,259,TO_TIMESTAMP('2022-08-23 17:22:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2022-08-23 17:22:19','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from C_Order  contractOrder JOIN C_Flatrate_Term contract on contractOrder.c_order_id =contract.c_order_term_id     join C_OrderLine ol on contract.c_flatrate_term_id = ol.c_flatrate_term_id where ol.c_orderline_id = @C_OrderLine_ID/-1@ AND contractOrder.c_order_id = C_Order.C_Order_ID)')
;

-- Reference: C_Order target for C_OrderLine (via C_FLatrate_Term)
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2022-08-23T14:22:35.782Z
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2022-08-23 17:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541644
;

-- 2022-08-23T14:24:18.495Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,271,541644,540357,TO_TIMESTAMP('2022-08-23 17:24:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_OrderLinePO -> C_Order (via C_Flatrate_Term)',TO_TIMESTAMP('2022-08-23 17:24:18','YYYY-MM-DD HH24:MI:SS'),100)
;

