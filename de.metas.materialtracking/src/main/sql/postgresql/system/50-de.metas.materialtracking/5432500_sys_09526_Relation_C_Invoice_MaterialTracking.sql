------- INVOICE ----------------


-- 05.11.2015 14:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540134,TO_TIMESTAMP('2015-11-05 14:54:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Invoice -> M_Material_Tracking',TO_TIMESTAMP('2015-11-05 14:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540594,TO_TIMESTAMP('2015-11-05 14:55:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Invoice -> M_MaterialTracking',TO_TIMESTAMP('2015-11-05 14:55:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 05.11.2015 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540594 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 05.11.2015 15:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3484,0,540594,318,TO_TIMESTAMP('2015-11-05 15:06:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-11-05 15:06:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 15:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-11-05 15:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540594
;

-- 05.11.2015 15:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-11-05 15:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540594
;

-- 05.11.2015 15:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-11-05 15:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540134
;

-- 05.11.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 
	from C_Invoice i
	join M_Material_Tracking_Ref mtr on  i.C_Invoice_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''C_Invoice'') and mtr.isActive = ''Y'' and i.isActive = ''Y''
	join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID
	where
		C_Invoice.C_Invoice_ID = i.C_Invoice_ID and
		( i.C_Invoice_ID = @C_Invoice_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@)
		
)',Updated=TO_TIMESTAMP('2015-11-05 15:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540594
;

-- 05.11.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540594,Updated=TO_TIMESTAMP('2015-11-05 15:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540134
;

-- 05.11.2015 15:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540595,TO_TIMESTAMP('2015-11-05 15:14:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','RelType C_Invoice -> M_Material_Tracking',TO_TIMESTAMP('2015-11-05 15:14:29','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 05.11.2015 15:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540595 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 05.11.2015 15:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,551110,0,540595,540610,TO_TIMESTAMP('2015-11-05 15:15:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N',TO_TIMESTAMP('2015-11-05 15:15:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.11.2015 15:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	
exists
(
	select 1
	from C_Invoice i
	join M_Material_Tracking_Ref mtr on  i.C_Invoice_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''C_Invoice'') and mtr.isActive = ''Y'' and i.isActive = ''Y''
	join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''
	where M_Material_Tracking.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and
	( i.C_Invoice_ID = @C_Invoice_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-05 15:17:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540595
;

-- 05.11.2015 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540595,Updated=TO_TIMESTAMP('2015-11-05 15:18:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540134
;

-- 05.11.2015 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 
	from C_Invoice i
	join M_Material_Tracking_Ref mtr on  i.C_Invoice_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''C_Invoice'') and mtr.isActive = ''Y'' and i.isActive = ''Y''
	join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''
	where
		C_Invoice.C_Invoice_ID = i.C_Invoice_ID and
		( i.C_Invoice_ID = @C_Invoice_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@)
		
)',Updated=TO_TIMESTAMP('2015-11-05 15:18:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540594
;

-- 05.11.2015 15:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540226,Updated=TO_TIMESTAMP('2015-11-05 15:24:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540594
;

-- 05.11.2015 15:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2015-11-05 15:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540594
;

-- 05.11.2015 15:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540226,Updated=TO_TIMESTAMP('2015-11-05 15:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540595
;


