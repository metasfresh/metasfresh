-- 2018-07-25T18:29:13.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540884,TO_TIMESTAMP('2018-07-25 18:29:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_SchedulerLog Target for AD_PInstance',TO_TIMESTAMP('2018-07-25 18:29:13','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-25T18:29:13.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540884 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-25T18:32:49.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,11234,11234,0,540884,687,305,TO_TIMESTAMP('2018-07-25 18:32:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2018-07-25 18:32:49','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from AD_SchedulerLog sl  where sl.AD_SchedulerLog_ID = AD_SchedulerLog.AD_SchedulerLog_ID and sl.AD_PInstance_ID = @AD_PInstance_ID@)')
;

-- 2018-07-25T18:33:03.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540169,540884,540209,TO_TIMESTAMP('2018-07-25 18:33:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','N','AD_PInstance -> AD_SchedulerLog',TO_TIMESTAMP('2018-07-25 18:33:03','YYYY-MM-DD HH24:MI:SS'),100)
;

