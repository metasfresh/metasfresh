-- 26.08.2016 17:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540672,540169,TO_TIMESTAMP('2016-08-26 17:22:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','Y','N',' M_InOut -> C_Invoice POTrx',TO_TIMESTAMP('2016-08-26 17:22:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 17:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540687,TO_TIMESTAMP('2016-08-26 17:22:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_POTrx_Target_For_M_InOut',TO_TIMESTAMP('2016-08-26 17:22:47','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 17:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540687 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 17:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540687,318,183,TO_TIMESTAMP('2016-08-26 17:23:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 17:23:17','YYYY-MM-DD HH24:MI:SS'),100,'exists (
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

-- 26.08.2016 17:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540687,Updated=TO_TIMESTAMP('2016-08-26 17:23:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540169
;

