-- 26.08.2016 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540164,TO_TIMESTAMP('2016-08-26 15:58:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','Y','N','C_Order_SOTrx -> C_Order_POTrx',TO_TIMESTAMP('2016-08-26 15:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540666,Updated=TO_TIMESTAMP('2016-08-26 15:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540164
;

-- 26.08.2016 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540682,TO_TIMESTAMP('2016-08-26 15:59:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','C_Order_POTrx_Target_For_C_Order_SOTrx',TO_TIMESTAMP('2016-08-26 15:59:33','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540682 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540682,259,181,TO_TIMESTAMP('2016-08-26 15:59:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N',TO_TIMESTAMP('2016-08-26 15:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 16:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 
	from C_Order po
	join C_OrderLine pol on po.C_Order_ID = pol.C_Order_ID
	join C_OrderLine sol on pol.C_OrderLine_ID = sol.Link_OrderLine_ID
	join C_Order so on sol.C_Order_ID = so.C_Order_ID

	where
		C_Order.C_Order_ID = po.C_Order_ID and
		so.C_Order_ID = @C_Order_ID/1@
)',Updated=TO_TIMESTAMP('2016-08-26 16:04:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540682
;

-- 26.08.2016 16:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540682,Updated=TO_TIMESTAMP('2016-08-26 16:07:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540164
;

