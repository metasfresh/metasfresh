
-- 25.08.2016 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540157,TO_TIMESTAMP('2016-08-25 15:46:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','Y','N','M_InOut -> C_Order',TO_TIMESTAMP('2016-08-25 15:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.08.2016 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540670,TO_TIMESTAMP('2016-08-25 15:48:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','M_InOut_SOTrx_Source',TO_TIMESTAMP('2016-08-25 15:48:36','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.08.2016 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540670 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.08.2016 15:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3521,0,540670,319,TO_TIMESTAMP('2016-08-25 15:54:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2016-08-25 15:54:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.08.2016 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='IsSOTrx = ''Y''',Updated=TO_TIMESTAMP('2016-08-25 15:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540670
;

-- 25.08.2016 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540670,Updated=TO_TIMESTAMP('2016-08-25 15:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540157
;

-- 25.08.2016 16:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540671,TO_TIMESTAMP('2016-08-25 16:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','C_Order_Sales_Target_For_M_InOut',TO_TIMESTAMP('2016-08-25 16:00:05','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.08.2016 16:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540671 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.08.2016 16:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540671,259,TO_TIMESTAMP('2016-08-25 16:00:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2016-08-25 16:00:25','YYYY-MM-DD HH24:MI:SS'),100,'exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID 
	join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
	where
	io.M_InOut_ID = @M_InOut_ID/-1@  and C_Order.C_Order_ID = o.C_Order_ID
)')
;

-- 25.08.2016 16:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540671,Updated=TO_TIMESTAMP('2016-08-25 16:01:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540157
;

-- 25.08.2016 16:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='M_InOut -> C_Order SOTrx',Updated=TO_TIMESTAMP('2016-08-25 16:23:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540157
;

-- 25.08.2016 16:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=169,Updated=TO_TIMESTAMP('2016-08-25 16:23:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540670
;

-- 25.08.2016 16:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2016-08-25 16:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540671
;

-- 25.08.2016 16:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2016-08-25 16:27:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540671
;

-- 25.08.2016 16:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Order_Target_For_M_InOut',Updated=TO_TIMESTAMP('2016-08-25 16:27:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540671
;

-- 25.08.2016 16:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540671
;

