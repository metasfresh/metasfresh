-- 26.08.2016 17:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540680,540167,TO_TIMESTAMP('2016-08-26 17:08:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','Y','N','C_Invoice -> M_InOut POTrx',TO_TIMESTAMP('2016-08-26 17:08:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 17:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540685,TO_TIMESTAMP('2016-08-26 17:09:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','M_In_Out_POTrx_Target_For_C_Invoice',TO_TIMESTAMP('2016-08-26 17:09:15','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 17:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540685 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 17:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,540685,319,184,TO_TIMESTAMP('2016-08-26 17:09:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 17:09:51','YYYY-MM-DD HH24:MI:SS'),100,'
exists(
	select 1 from M_InOut io
	join M_InOutLine iol on io.M_InOut_ID = iol.M_InOut_ID
	join C_InvoiceLine il on iol.M_InOutline_ID = il.M_InOutline_ID
	join C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
	
	where M_InOut.M_InOut_ID = io.M_InOut_ID and i.C_Invoice_ID = @C_Invoice_ID/-1@
)')
;

-- 26.08.2016 17:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540685,Updated=TO_TIMESTAMP('2016-08-26 17:10:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540167
;

