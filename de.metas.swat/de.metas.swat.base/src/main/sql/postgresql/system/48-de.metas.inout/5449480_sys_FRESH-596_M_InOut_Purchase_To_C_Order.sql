-- 25.08.2016 16:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540158,TO_TIMESTAMP('2016-08-25 16:39:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','Y','N','M_InOut -> C_Order POTrx',TO_TIMESTAMP('2016-08-25 16:39:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.08.2016 16:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540671,Updated=TO_TIMESTAMP('2016-08-25 16:40:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540158
;

-- 25.08.2016 16:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540672,TO_TIMESTAMP('2016-08-25 16:40:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','M_InOut_POTrx_Source',TO_TIMESTAMP('2016-08-25 16:40:51','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.08.2016 16:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540672 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.08.2016 16:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,540672,319,184,TO_TIMESTAMP('2016-08-25 16:41:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2016-08-25 16:41:26','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''N''')
;

-- 25.08.2016 16:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540672,Updated=TO_TIMESTAMP('2016-08-25 16:41:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540158
;

-- 25.08.2016 16:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID 
	join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
	where
	io.M_InOut_ID = @M_InOut_ID/-1@  and C_Order.C_Order_ID = o.C_Order_ID and o.IsSoTRX = @IsSOTrx@
)',Updated=TO_TIMESTAMP('2016-08-25 16:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540671
;

-- 25.08.2016 16:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID 
	join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
	where
	io.M_InOut_ID = @M_InOut_ID/-1@  and C_Order.C_Order_ID = o.C_Order_ID  and o.IsSOTrx = @IsSOTrx@
)',Updated=TO_TIMESTAMP('2016-08-25 16:47:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540671
;

-- 25.08.2016 16:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID 
	join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
	where
	io.M_InOut_ID = @M_InOut_ID/-1@  and C_Order.C_Order_ID = o.C_Order_ID  and o.IsSOTrx = io.IsSOTrx
)',Updated=TO_TIMESTAMP('2016-08-25 16:55:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540671
;

-- 25.08.2016 16:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Order_SOTrx_Target_For_M_InOut',Updated=TO_TIMESTAMP('2016-08-25 16:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540671
;

-- 25.08.2016 16:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540671
;

-- 25.08.2016 16:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2016-08-25 16:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540671
;

-- 25.08.2016 16:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540673,TO_TIMESTAMP('2016-08-25 16:58:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','C_Order_POTrx_Target_For_M_InOut',TO_TIMESTAMP('2016-08-25 16:58:37','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.08.2016 16:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540673 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.08.2016 16:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540673,259,181,TO_TIMESTAMP('2016-08-25 16:59:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2016-08-25 16:59:10','YYYY-MM-DD HH24:MI:SS'),100,'exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID 
	join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
	where
	io.M_InOut_ID = @M_InOut_ID/-1@  and C_Order.C_Order_ID = o.C_Order_ID  and o.IsSOTrx = io.IsSOTrx
)')
;

-- 25.08.2016 17:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540673,Updated=TO_TIMESTAMP('2016-08-25 17:01:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540158
;

