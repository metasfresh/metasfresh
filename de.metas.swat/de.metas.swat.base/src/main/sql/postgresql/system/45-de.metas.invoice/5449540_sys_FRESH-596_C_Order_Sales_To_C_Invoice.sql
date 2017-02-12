-- 26.08.2016 13:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-26 13:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 26.08.2016 13:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-26 13:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 26.08.2016 13:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-26 13:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 26.08.2016 13:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-26 13:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 26.08.2016 13:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2016-08-26 13:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 26.08.2016 13:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2016-08-26 13:53:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 26.08.2016 13:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2016-08-26 13:53:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 26.08.2016 13:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2016-08-26 13:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 26.08.2016 13:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540666,540160,TO_TIMESTAMP('2016-08-26 13:54:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','Y','N','C_Order -> C_Invoice SOTrx',TO_TIMESTAMP('2016-08-26 13:54:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540675,TO_TIMESTAMP('2016-08-26 14:00:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice_SOTrx_Target_For_C_Order',TO_TIMESTAMP('2016-08-26 14:00:17','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.08.2016 14:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540675 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.08.2016 14:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540675,318,167,TO_TIMESTAMP('2016-08-26 14:00:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2016-08-26 14:00:50','YYYY-MM-DD HH24:MI:SS'),100,'
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

-- 26.08.2016 14:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540675,Updated=TO_TIMESTAMP('2016-08-26 14:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540160
;
