-- 25.08.2016 17:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540666,540159,TO_TIMESTAMP('2016-08-25 17:32:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','C_Order -> M_InOut SOTrx',TO_TIMESTAMP('2016-08-25 17:32:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.08.2016 17:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-08-25 17:32:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540159
;

-- 25.08.2016 17:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540674,TO_TIMESTAMP('2016-08-25 17:33:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','M_InOut_SOTrx_Target_For_C_Order',TO_TIMESTAMP('2016-08-25 17:33:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.08.2016 17:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540674 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.08.2016 17:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3521,0,540674,319,169,TO_TIMESTAMP('2016-08-25 17:34:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2016-08-25 17:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.08.2016 17:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1
	from  M_InOut io
	join M_InOutLine iol on io.M_InOut_ID = iol.M_InOut_ID
	join C_OrderLine ol on iol.C_OrderLine_ID = ol.C_OrderLine_ID 
	join C_Order o on ol.C_Order_ID = o.C_Order_ID

	where
	o.C_Order_ID = @C_Order_ID/-1@  and M_InOut.M_InOut_ID = io.M_InOut_ID  
)',Updated=TO_TIMESTAMP('2016-08-25 17:37:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540674
;

-- 25.08.2016 17:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540674,Updated=TO_TIMESTAMP('2016-08-25 17:38:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540159
;

