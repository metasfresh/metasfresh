-- 26.08.2016 15:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540163,TO_TIMESTAMP('2016-08-26 15:15:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','Y','N','C_Invoice -> C_Order POTrx',TO_TIMESTAMP('2016-08-26 15:15:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 15:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,Help,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540680,TO_TIMESTAMP('2016-08-26 15:16:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','		','Y','N','C_Invoice_POTrx_Source',TO_TIMESTAMP('2016-08-26 15:16:19','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 15:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540680 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 15:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540680,318,183,TO_TIMESTAMP('2016-08-26 15:16:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 15:16:56','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''N''')
;

-- 26.08.2016 15:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540680,Updated=TO_TIMESTAMP('2016-08-26 15:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540163
;

-- 26.08.2016 15:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540681,TO_TIMESTAMP('2016-08-26 15:17:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Order_POTrx_Target_For_C_Invoice',TO_TIMESTAMP('2016-08-26 15:17:52','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 15:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540681 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540681,259,183,TO_TIMESTAMP('2016-08-26 15:18:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 15:18:30','YYYY-MM-DD HH24:MI:SS'),100,'exists(
select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID
	join C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
where 		
	C_Order.C_Order_ID = o.C_Order_ID
	and i.C_Invoice_ID = @C_Invoice_ID/-1@
)')
;

-- 26.08.2016 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540681,Updated=TO_TIMESTAMP('2016-08-26 15:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540163
;

-- 26.08.2016 15:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2016-08-26 15:19:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540681
;

