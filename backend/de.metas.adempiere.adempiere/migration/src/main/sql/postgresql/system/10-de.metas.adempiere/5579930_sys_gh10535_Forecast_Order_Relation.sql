-- 2021-02-19T08:16:23.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540676,540276,TO_TIMESTAMP('2021-02-19 10:16:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','C_Order -> M_Forecast',TO_TIMESTAMP('2021-02-19 10:16:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-19T08:16:59.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541261,TO_TIMESTAMP('2021-02-19 10:16:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Forecast target for C_Order',TO_TIMESTAMP('2021-02-19 10:16:59','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-02-19T08:16:59.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541261 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-19T08:23:16.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,11918,0,541261,720,TO_TIMESTAMP('2021-02-19 10:23:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-02-19 10:23:16','YYYY-MM-DD HH24:MI:SS'),100,'(EXISTS(     SELECT 1     from M_Forecast f     JOIN C_PurchaseCandidate pc on f.M_Forecast_ID = pc.M_Forecast_ID     JOIN c_purchasecandidate_alloc pca on pc.c_purchasecandidate_id = pca.c_purchasecandidate_id     JOIN C_Order o on pca.c_orderpo_id = o.c_order_id      (EXISTS (SELECT 1 from PP_Product_BOMLine pbl     where f.M_Forecast_ID = M_Forecast.M_Forecast_ID AND o.C_Order_ID = @C_Order_ID / -1@))))')
;

-- 2021-02-19T08:23:40.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541261, EntityType='D',Updated=TO_TIMESTAMP('2021-02-19 10:23:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540276
;

-- 2021-02-19T10:07:57.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' (EXISTS(     SELECT 1     from M_Forecast f     JOIN C_PurchaseCandidate pc on f.M_Forecast_ID = pc.M_Forecast_ID     JOIN c_purchasecandidate_alloc pca on pc.c_purchasecandidate_id = pca.c_purchasecandidate_id     JOIN C_Order o on pca.c_orderpo_id = o.c_order_id         where f.M_Forecast_ID = M_Forecast.M_Forecast_ID AND o.C_Order_ID = @C_Order_ID / -1@))',Updated=TO_TIMESTAMP('2021-02-19 12:07:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541261
;
