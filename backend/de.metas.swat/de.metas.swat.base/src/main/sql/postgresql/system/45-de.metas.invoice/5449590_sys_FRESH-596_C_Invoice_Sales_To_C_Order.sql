-- 26.08.2016 14:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540162,TO_TIMESTAMP('2016-08-26 14:35:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','C_Invoice -> C_Order SOTrx',TO_TIMESTAMP('2016-08-26 14:35:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.invoice',Updated=TO_TIMESTAMP('2016-08-26 14:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540162
;

-- 26.08.2016 14:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540678,TO_TIMESTAMP('2016-08-26 14:36:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_SOTrx_Source',TO_TIMESTAMP('2016-08-26 14:36:06','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 14:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540678 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 14:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540678,318,167,TO_TIMESTAMP('2016-08-26 14:36:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 14:36:37','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''Y''')
;

-- 26.08.2016 14:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540678,Updated=TO_TIMESTAMP('2016-08-26 14:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540162
;

-- 26.08.2016 14:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540679,TO_TIMESTAMP('2016-08-26 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Order_SOTrx_Target_For_C_Invoice',TO_TIMESTAMP('2016-08-26 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 14:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540679 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 14:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540679,259,143,TO_TIMESTAMP('2016-08-26 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists(

SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
WHERE 		C_Order.C_Order_ID = o.C_Order_ID
		and i.C_Invoice_ID = @C_Invoice_ID/-1@
)',Updated=TO_TIMESTAMP('2016-08-26 14:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540679
;

-- 26.08.2016 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540679,Updated=TO_TIMESTAMP('2016-08-26 14:44:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540162
;

-- 26.08.2016 15:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists(
SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
WHERE 		
	C_Order.C_Order_ID = o.C_Order_ID
	and i.C_Invoice_ID = @C_Invoice_ID/-1@
)',Updated=TO_TIMESTAMP('2016-08-26 15:08:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540679
;

-- 26.08.2016 15:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists(
select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	join C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
where 		
	C_Order.C_Order_ID = o.C_Order_ID
	and i.C_Invoice_ID = @C_Invoice_ID/-1@
)',Updated=TO_TIMESTAMP('2016-08-26 15:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540679
;

