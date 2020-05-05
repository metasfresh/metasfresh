-- 10.12.2015 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540145,TO_TIMESTAMP('2015-12-10 12:19:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N','N','C_Order -> C_OLCand',TO_TIMESTAMP('2015-12-10 12:19:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.12.2015 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540618,TO_TIMESTAMP('2015-12-10 12:19:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N','C_Order -> C_OLCand',TO_TIMESTAMP('2015-12-10 12:19:51','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 10.12.2015 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540618 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 10.12.2015 12:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540618,259,TO_TIMESTAMP('2015-12-10 12:20:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N',TO_TIMESTAMP('2015-12-10 12:20:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.12.2015 12:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2015-12-10 12:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540618
;

-- 10.12.2015 13:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join C_Order_Line_Alloc ola on ol.C_OrderLine_ID = ola.C_OrderLine_ID
	join C_OLCand olc on ola.C_OLCand_ID = olc.C_Olcand_ID 
	
	where
	C_Order.C_Order_ID = o.C_Order_ID
	and
	(o.C_Order_ID = @C_Order_ID/-1@ or olc.C_OLCand_ID = @C_OLCand_ID/-1@)
)',Updated=TO_TIMESTAMP('2015-12-10 13:08:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540618
;

-- 10.12.2015 13:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540618,Updated=TO_TIMESTAMP('2015-12-10 13:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540145
;

-- 10.12.2015 13:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540619,TO_TIMESTAMP('2015-12-10 13:08:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N','RelType C_OLCand -> C_Order',TO_TIMESTAMP('2015-12-10 13:08:58','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 10.12.2015 13:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540619 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 10.12.2015 13:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,544275,0,540619,540244,540095,TO_TIMESTAMP('2015-12-10 13:09:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','N',TO_TIMESTAMP('2015-12-10 13:09:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.12.2015 13:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join C_Order_Line_Alloc ola on ol.C_OrderLine_ID = ola.C_OrderLine_ID
	join C_OLCand olc on ola.C_OLCand_ID = olc.C_Olcand_ID 
	
	where
	C_OLCand.C_OLCand_ID = olc.C_OLCand_ID
	and
	(o.C_Order_ID = @C_Order_ID/-1@ or olc.C_OLCand_ID = @C_OLCand_ID/-1@)
)',Updated=TO_TIMESTAMP('2015-12-10 13:09:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540619
;

-- 10.12.2015 13:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540619,Updated=TO_TIMESTAMP('2015-12-10 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540145
;

