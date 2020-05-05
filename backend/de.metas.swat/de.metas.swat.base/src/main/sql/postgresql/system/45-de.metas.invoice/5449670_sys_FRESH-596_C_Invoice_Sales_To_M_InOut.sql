

-- 26.08.2016 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540684,TO_TIMESTAMP('2016-08-26 16:52:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','M_In_Out_SOTrx_Target_For_C_Invoice',TO_TIMESTAMP('2016-08-26 16:52:24','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540684 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3521,0,540684,319,169,TO_TIMESTAMP('2016-08-26 16:52:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 16:52:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 16:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540678,540166,TO_TIMESTAMP('2016-08-26 16:54:10','YYYY-MM-DD HH24:MI:SS'),100,'added 596','de.metas.invoice','Y','Y','N','C_Invoice -> M_InOut SOTrx',TO_TIMESTAMP('2016-08-26 16:54:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 16:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists(
	select 1 from M_InOut io
	join M_InOutLine iol on io.M_InOut_ID = iol.M_InOut_ID
	join C_InvoiceLine il on iol.M_InOutline_ID = il.M_InOutline_ID
	join C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	
	where M_InOut.M_InOut_ID = io.M_InOut_ID and i.C_Invoice_ID = @C_Invoice_ID/-1@
)',Updated=TO_TIMESTAMP('2016-08-26 16:56:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540684
;

-- 26.08.2016 16:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540684,Updated=TO_TIMESTAMP('2016-08-26 16:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540166
;

-- 26.08.2016 16:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Description=NULL,Updated=TO_TIMESTAMP('2016-08-26 16:57:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540166
;

