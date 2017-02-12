-- 25.08.2016 13:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540156,TO_TIMESTAMP('2016-08-25 13:03:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Y','N','C_OLCand -> C_Order',TO_TIMESTAMP('2016-08-25 13:03:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.08.2016 13:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540668,TO_TIMESTAMP('2016-08-25 13:04:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N','C_OLCand_Source',TO_TIMESTAMP('2016-08-25 13:04:50','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.08.2016 13:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540668 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;


-- 25.08.2016 14:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,544275,0,540668,540244,TO_TIMESTAMP('2016-08-25 14:06:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N',TO_TIMESTAMP('2016-08-25 14:06:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.08.2016 14:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='1=1',Updated=TO_TIMESTAMP('2016-08-25 14:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540668
;

-- 25.08.2016 14:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540095,Updated=TO_TIMESTAMP('2016-08-25 14:07:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540668
;

-- 25.08.2016 14:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540668, IsActive='Y',Updated=TO_TIMESTAMP('2016-08-25 14:08:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540156
;

-- 25.08.2016 14:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540669,TO_TIMESTAMP('2016-08-25 14:08:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N','C_Order_Target_For_C_OLCand',TO_TIMESTAMP('2016-08-25 14:08:54','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.08.2016 14:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540669 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.08.2016 14:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540669,259,TO_TIMESTAMP('2016-08-25 14:09:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N',TO_TIMESTAMP('2016-08-25 14:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.08.2016 14:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143, WhereClause='
exists
(
	select 1 from C_Order o
	join C_OrderLine ol on ol.C_Order_ID = o.C_Order_ID	
	join C_Order_Line_Alloc ola on ol.C_OrderLine_ID = ola.C_OrderLine_ID	
	join C_OLCand olc on ola.C_OLCand_ID = olc.C_OLCand_ID
	
	where
	olc.C_OLCand_ID = @C_OLCand_ID/-1@  AND C_Order.C_Order_ID = o.C_Order_ID and o.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2016-08-25 14:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540669
;

-- 25.08.2016 14:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540669,Updated=TO_TIMESTAMP('2016-08-25 14:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540156
;

