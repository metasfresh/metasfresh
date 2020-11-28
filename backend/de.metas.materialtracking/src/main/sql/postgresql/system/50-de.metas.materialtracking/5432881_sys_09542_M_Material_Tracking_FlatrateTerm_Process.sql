-- 10.11.2015 18:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- why do I have this?
--UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?
--;



-- 10.11.2015 18:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540625,'N',TO_TIMESTAMP('2015-11-10 18:07:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','N','N','N','N',0,'Create_C_Flatrate_Term_For_Material_Tracking','N','Y',0,0,'Java',TO_TIMESTAMP('2015-11-10 18:07:51','YYYY-MM-DD HH24:MI:SS'),100,'Create_C_Flatrate_Term_For_Material_Tracking')
;

-- 10.11.2015 18:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540625 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 10.11.2015 18:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540625,540610,TO_TIMESTAMP('2015-11-10 18:09:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y',TO_TIMESTAMP('2015-11-10 18:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.11.2015 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540626,'N',TO_TIMESTAMP('2015-11-10 18:10:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','N','N','N','N',0,'Create_C_Flatrate_Term_For_Material_Tracking','N','Y',0,0,'Java',TO_TIMESTAMP('2015-11-10 18:10:44','YYYY-MM-DD HH24:MI:SS'),100,'M_Material_Tracking_Create_Flatrate_Term')
;

-- 10.11.2015 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540626 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 10.11.2015 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540626,540610,TO_TIMESTAMP('2015-11-10 18:10:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y',TO_TIMESTAMP('2015-11-10 18:10:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.11.2015 18:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Process_ID=540625 AND AD_Table_ID=540610
;

-- 10.11.2015 18:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540625
;

-- 10.11.2015 18:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540625
;




-- 11.11.2015 22:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.materialtracking.process.C_FlatrateTerm_Create_For_MaterialTracking',Updated=TO_TIMESTAMP('2015-11-11 22:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540626
;

