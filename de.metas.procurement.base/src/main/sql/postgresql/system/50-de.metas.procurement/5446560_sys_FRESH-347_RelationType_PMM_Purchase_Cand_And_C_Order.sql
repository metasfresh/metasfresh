-- 27.05.2016 17:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540653,TO_TIMESTAMP('2016-05-27 17:13:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','N','PMM_PurchaseCandidate -> C_Order',TO_TIMESTAMP('2016-05-27 17:13:12','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 27.05.2016 17:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540653 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 27.05.2016 17:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,554126,0,540653,540746,TO_TIMESTAMP('2016-05-27 17:14:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','N',TO_TIMESTAMP('2016-05-27 17:14:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.05.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540285, WhereClause='exists
(
	select 1
	from	PMM_PurchaseCandidate pc
	join	PMM_PurchaseCandidate_OrderLine pco ON pc.PMM_PurchaseCandidate_ID = pco.PMM_PurchaseCandidate_ID
	join	C_OrderLine ol ON pco.C_OrderLine_ID = ol.C_OrderLine_ID
	join	C_Order o on ol.C_Order_ID = o.C_Order_ID
	where  PMM_PurchaseCandidate.PMM_PurchaseCandidate_ID = pc.PMM_PurchaseCandidate_ID and
	(pc.PMM_PurchaseCandidate_ID = @PMM_PurchaseCandidate_ID/-1@ or o.C_Order_ID = @C_Order_ID/-1@)
)',Updated=TO_TIMESTAMP('2016-05-27 17:18:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540653
;

-- 27.05.2016 17:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540654,TO_TIMESTAMP('2016-05-27 17:20:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','N','RelType PMM_PurchaseCandidate -> C_Order',TO_TIMESTAMP('2016-05-27 17:20:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 27.05.2016 17:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540654 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 27.05.2016 17:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540654,259,181,TO_TIMESTAMP('2016-05-27 17:22:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','N',TO_TIMESTAMP('2016-05-27 17:22:15','YYYY-MM-DD HH24:MI:SS'),100,'exists
(
	select 1
	from	PMM_PurchaseCandidate pc
	join	PMM_PurchaseCandidate_OrderLine pco ON pc.PMM_PurchaseCandidate_ID = pco.PMM_PurchaseCandidate_ID
	join	C_OrderLine ol ON pco.C_OrderLine_ID = ol.C_OrderLine_ID
	join	C_Order o on ol.C_Order_ID = o.C_Order_ID
	where
		C_Order.C_Order_ID = o.C_Order_ID and
		(pc.PMM_PurchaseCandidate_ID = @PMM_PurchaseCandidate_ID/-1@ or o.C_Order_ID = @C_Order_ID/-1@)
)')
;

-- 27.05.2016 17:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540653,540654,540152,TO_TIMESTAMP('2016-05-27 17:22:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','N','N','PMM_PurchaseCandidate -> C_Order',TO_TIMESTAMP('2016-05-27 17:22:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.05.2016 17:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1
	from	PMM_PurchaseCandidate pc
	join	PMM_PurchaseCandidate_OrderLine pco ON pc.PMM_PurchaseCandidate_ID = pco.PMM_PurchaseCandidate_ID
	join	C_OrderLine ol on pco.C_OrderLine_ID = ol.C_OrderLine_ID
	join	C_Order o on ol.C_Order_ID = o.C_Order_ID
	where
		C_Order.C_Order_ID = o.C_Order_ID and
		(pc.PMM_PurchaseCandidate_ID = @PMM_PurchaseCandidate_ID/-1@ or o.C_Order_ID = @C_Order_ID/-1@)
)',Updated=TO_TIMESTAMP('2016-05-27 17:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540654
;

-- 27.05.2016 17:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1
	from	PMM_PurchaseCandidate pc
	join	PMM_PurchaseCandidate_OrderLine pco on pc.PMM_PurchaseCandidate_ID = pco.PMM_PurchaseCandidate_ID
	join	C_OrderLine ol ON pco.C_OrderLine_ID = ol.C_OrderLine_ID
	join	C_Order o on ol.C_Order_ID = o.C_Order_ID
	where  PMM_PurchaseCandidate.PMM_PurchaseCandidate_ID = pc.PMM_PurchaseCandidate_ID and
	(pc.PMM_PurchaseCandidate_ID = @PMM_PurchaseCandidate_ID/-1@ or o.C_Order_ID = @C_Order_ID/-1@)
)',Updated=TO_TIMESTAMP('2016-05-27 17:42:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540653
;

-- 27.05.2016 17:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=554109, AD_Table_ID=540745,Updated=TO_TIMESTAMP('2016-05-27 17:44:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540653
;

