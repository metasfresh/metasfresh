-- 05.11.2015 17:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540135,TO_TIMESTAMP('2015-11-05 17:04:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','N','PP_Order -> M_Material_Tracking',TO_TIMESTAMP('2015-11-05 17:04:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540596,TO_TIMESTAMP('2015-11-05 17:05:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','PP_Order -> M_Material_Tracking',TO_TIMESTAMP('2015-11-05 17:05:38','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 05.11.2015 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540596 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 05.11.2015 17:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,53659,0,540596,53027,53009,TO_TIMESTAMP('2015-11-05 17:06:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N',TO_TIMESTAMP('2015-11-05 17:06:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 17:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1 
	from PP_Order o
	join  M_Material_Tracking_Ref mtr on  o.PP_Order_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''PP_Order'') and mtr.isActive = ''Y'' and o.isActive = ''Y''
	join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''
	where
		PP_Order.PP_Order_ID = o.PP_Order_ID and
		( o.PP_Order_ID = @PP_Order_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@)
)',Updated=TO_TIMESTAMP('2015-11-05 17:10:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540596
;

-- 05.11.2015 17:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540596,Updated=TO_TIMESTAMP('2015-11-05 17:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540135
;

-- 05.11.2015 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540597,TO_TIMESTAMP('2015-11-05 17:19:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','RelType PP_Order -> M_Material_Tracking',TO_TIMESTAMP('2015-11-05 17:19:40','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 05.11.2015 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540597 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 05.11.2015 17:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,551110,0,540597,540610,540226,TO_TIMESTAMP('2015-11-05 17:20:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N',TO_TIMESTAMP('2015-11-05 17:20:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 17:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1
	from PP_Order i
	join M_Material_Tracking_Ref mtr on  i.PP_Order_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''PP_Order'') and mtr.isActive = ''Y'' and i.isActive = ''Y''
	join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''
	where M_Material_Tracking.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and
	( i.PP_Order_ID = @PP_Order_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-05 17:28:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540597
;

-- 05.11.2015 17:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540597,Updated=TO_TIMESTAMP('2015-11-05 17:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540135
;
