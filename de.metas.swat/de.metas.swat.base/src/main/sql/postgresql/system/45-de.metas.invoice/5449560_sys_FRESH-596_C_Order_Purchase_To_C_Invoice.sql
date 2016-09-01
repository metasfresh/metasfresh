
-- 26.08.2016 14:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540161,TO_TIMESTAMP('2016-08-26 14:13:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','Y','N','C_Order -> C_Invoice POTrx',TO_TIMESTAMP('2016-08-26 14:13:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540676,TO_TIMESTAMP('2016-08-26 14:14:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','C_Order_POTrx_Source',TO_TIMESTAMP('2016-08-26 14:14:13','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 14:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540676 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 14:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540676,259,181,TO_TIMESTAMP('2016-08-26 14:15:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N',TO_TIMESTAMP('2016-08-26 14:15:11','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''N''')
;

-- 26.08.2016 14:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540676,Updated=TO_TIMESTAMP('2016-08-26 14:15:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540161
;

-- 26.08.2016 14:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540677,TO_TIMESTAMP('2016-08-26 14:16:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_POTrx_Target_For_C_Order',TO_TIMESTAMP('2016-08-26 14:16:04','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 14:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540677 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 14:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540677,318,183,TO_TIMESTAMP('2016-08-26 14:17:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 14:17:30','YYYY-MM-DD HH24:MI:SS'),100,'
exists(

SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID

	WHERE 
	o.C_Order_ID = @C_Order_ID/-1@
	AND i.C_Invoice_ID = C_Invoice.C_Invoice_ID
)
')
;

-- 26.08.2016 14:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540677,Updated=TO_TIMESTAMP('2016-08-26 14:22:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540161
;

