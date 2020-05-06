-- 24.08.2016 17:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540666,TO_TIMESTAMP('2016-08-24 17:24:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','C_Order_SOTrx_Source',TO_TIMESTAMP('2016-08-24 17:24:42','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 24.08.2016 17:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540666 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 24.08.2016 17:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540666,259,TO_TIMESTAMP('2016-08-24 17:25:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N',TO_TIMESTAMP('2016-08-24 17:25:25','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''Y''')
;

-- 24.08.2016 17:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2016-08-24 17:25:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540666
;

-- 24.08.2016 17:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540666,540155,TO_TIMESTAMP('2016-08-24 17:25:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','C_Order -> C_OLCand',TO_TIMESTAMP('2016-08-24 17:25:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.08.2016 17:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540667,TO_TIMESTAMP('2016-08-24 17:31:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N','C_OLCand_Target_For_C_Order',TO_TIMESTAMP('2016-08-24 17:31:13','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 24.08.2016 17:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540667 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 24.08.2016 17:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,544275,0,540667,540244,540095,TO_TIMESTAMP('2016-08-24 17:32:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N',TO_TIMESTAMP('2016-08-24 17:32:10','YYYY-MM-DD HH24:MI:SS'),100,'exists
(
	select 1 from C_OLCand olc
	join C_Order_Line_Alloc ola on olc.C_OLCand_ID = ola.C_Olcand_ID 
	join C_OrderLine ol on ola.C_OrderLine_ID = ol.C_OrderLine_ID
	join C_Order o on o.C_Order_ID = ol.C_Order_ID
	
	where
	o.C_Order_ID = @C_Order_ID/-1@ 
)')
;

-- 24.08.2016 17:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540667,Updated=TO_TIMESTAMP('2016-08-24 17:32:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540155
;

-- 24.08.2016 17:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2016-08-24 17:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540155
;

-- 25.08.2016 11:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2016-08-25 11:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540155
;

-- 25.08.2016 11:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2016-08-25 11:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540145
;

-- 25.08.2016 11:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_OLCand olc
	join C_Order_Line_Alloc ola on olc.C_OLCand_ID = ola.C_Olcand_ID 
	join C_OrderLine ol on ola.C_OrderLine_ID = ol.C_OrderLine_ID	
	where
	ol.C_Order_ID = @C_Order_ID/-1@ 
)',Updated=TO_TIMESTAMP('2016-08-25 11:39:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540667
;

-- 25.08.2016 11:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_OLCand olc
	join C_Order_Line_Alloc ola on olc.C_OLCand_ID = ola.C_Olcand_ID 
	join C_OrderLine ol on ola.C_OrderLine_ID = ol.C_OrderLine_ID	
	where
	ol.C_Order_ID = @C_Order_ID/-1@  AND C_OLCand_ID = olc.C_OLCand_ID
)',Updated=TO_TIMESTAMP('2016-08-25 11:43:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540667
;

-- 25.08.2016 11:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_OLCand olc
	join C_Order_Line_Alloc ola on olc.C_OLCand_ID = ola.C_Olcand_ID 
	join C_OrderLine ol on ola.C_OrderLine_ID = ol.C_OrderLine_ID	
	where
	ol.C_Order_ID = @C_Order_ID/-1@  AND C_OLCand.C_OLCand_ID = olc.C_OLCand_ID
)',Updated=TO_TIMESTAMP('2016-08-25 11:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540667
;


