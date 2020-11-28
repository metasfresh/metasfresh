
-- 05.11.2015 18:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540136,TO_TIMESTAMP('2015-11-05 18:17:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','N','C_Order -> M_Material_Tracking',TO_TIMESTAMP('2015-11-05 18:17:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 18:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540598,TO_TIMESTAMP('2015-11-05 18:21:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','C_Order -> M_Material_Tracking',TO_TIMESTAMP('2015-11-05 18:21:12','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 05.11.2015 18:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540598 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 05.11.2015 18:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540598,259,181,TO_TIMESTAMP('2015-11-05 18:21:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N',TO_TIMESTAMP('2015-11-05 18:21:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 18:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isactive = ''Y''
	join M_Material_Tracking_Ref mtr on  ol.C_OrderLine_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''C_OrderLine'') and mtr.isActive = ''Y''
	join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''
	where
		C_Order.C_Order_ID = o.C_Order_ID and
		( o.C_Order_ID = @C_Order_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@)
)',Updated=TO_TIMESTAMP('2015-11-05 18:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540598
;

-- 05.11.2015 18:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540598,Updated=TO_TIMESTAMP('2015-11-05 18:24:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540136
;

-- 05.11.2015 18:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540599,TO_TIMESTAMP('2015-11-05 18:25:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','RelType C_Order -> M_Material_Tracking',TO_TIMESTAMP('2015-11-05 18:25:26','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 05.11.2015 18:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540599 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 05.11.2015 18:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,551110,0,540599,540610,540226,TO_TIMESTAMP('2015-11-05 18:25:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N',TO_TIMESTAMP('2015-11-05 18:25:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 18:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isactive = ''Y''
	join M_Material_Tracking_Ref mtr on  ol.C_OrderLine_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''C_OrderLine'') and mtr.isActive = ''Y''
	join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''
	where
		C_Order.C_Order_ID = o.C_Order_ID and
		( o.C_Order_ID = @C_Order_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@)
)
',Updated=TO_TIMESTAMP('2015-11-05 18:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540599
;

-- 06.11.2015 11:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540599,Updated=TO_TIMESTAMP('2015-11-06 11:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540136
;

-- 06.11.2015 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isactive = ''Y''
	join M_Material_Tracking_Ref mtr on  ol.C_OrderLine_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''C_OrderLine'') and mtr.isActive = ''Y''
	join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''
	where
		M_Material_Tracking.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and
		( o.C_Order_ID = @C_Order_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@)
)
',Updated=TO_TIMESTAMP('2015-11-06 11:14:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540599
;

