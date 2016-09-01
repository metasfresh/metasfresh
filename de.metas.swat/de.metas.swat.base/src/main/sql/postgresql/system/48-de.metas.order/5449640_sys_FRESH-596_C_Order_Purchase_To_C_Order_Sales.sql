-- 26.08.2016 16:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540165,TO_TIMESTAMP('2016-08-26 16:12:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','Y','N','C_Order_POTrx -> C_Order_SOTrx',TO_TIMESTAMP('2016-08-26 16:12:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 16:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540676,Updated=TO_TIMESTAMP('2016-08-26 16:12:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540165
;

-- 26.08.2016 16:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540683,TO_TIMESTAMP('2016-08-26 16:13:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','C_Order_SOTrx_Target_For_C_Order_POTrx',TO_TIMESTAMP('2016-08-26 16:13:06','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 16:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540683 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 16:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540683,259,143,TO_TIMESTAMP('2016-08-26 16:14:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N',TO_TIMESTAMP('2016-08-26 16:14:45','YYYY-MM-DD HH24:MI:SS'),100,'exists
(
	select 1 
	from C_Order so
	join C_OrderLine sol on so.C_Order_ID = sol.C_Order_ID and so.isActive = ''Y'' and sol.isActive = ''Y''
	join C_OrderLine pol on sol.Link_OrderLine_ID = pol.C_OrderLine_ID and pol.isActive = ''Y''
	join C_Order po on pol.C_Order_ID = po.C_Order_ID and po.isActive = ''Y''
	
	where
		C_Order.C_Order_ID = so.C_Order_ID and
		po.C_Order_ID = @C_Order_ID/1@
)')
;

-- 26.08.2016 16:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540683,Updated=TO_TIMESTAMP('2016-08-26 16:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540165
;

