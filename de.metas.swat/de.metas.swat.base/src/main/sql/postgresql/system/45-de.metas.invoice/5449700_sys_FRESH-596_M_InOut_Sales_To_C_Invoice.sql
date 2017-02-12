-- 26.08.2016 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540670,540168,TO_TIMESTAMP('2016-08-26 17:15:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','Y','N',' M_InOut -> C_Invoice SOTrx',TO_TIMESTAMP('2016-08-26 17:15:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540686,TO_TIMESTAMP('2016-08-26 17:18:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_SOTrx_Target_For_M_In_Out',TO_TIMESTAMP('2016-08-26 17:18:11','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540686 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Invoice_SOTrx_Target_For_M_InOut',Updated=TO_TIMESTAMP('2016-08-26 17:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540686
;

-- 26.08.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540686
;

-- 26.08.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='M_InOut_SOTrx_Target_For_C_Invoice',Updated=TO_TIMESTAMP('2016-08-26 17:18:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540684
;

-- 26.08.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540684
;

-- 26.08.2016 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='M_InOut_POTrx_Target_For_C_Invoice',Updated=TO_TIMESTAMP('2016-08-26 17:19:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540685
;

-- 26.08.2016 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540685
;

-- 26.08.2016 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540686,318,167,TO_TIMESTAMP('2016-08-26 17:19:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 17:19:54','YYYY-MM-DD HH24:MI:SS'),100,'exists (
select 1 
		from C_Invoice i
		join C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		join M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
		join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		where 
		
		C_Invoice.C_Invoice_ID = i.C_Invoice_ID
		and io.M_InOut_ID = @M_InOut_ID/-1@
		)')
;

-- 26.08.2016 17:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540686,Updated=TO_TIMESTAMP('2016-08-26 17:20:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540168
;

