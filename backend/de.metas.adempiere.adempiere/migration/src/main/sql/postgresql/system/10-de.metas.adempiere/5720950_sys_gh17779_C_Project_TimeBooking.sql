-- Table: C_Project_TimeBooking
-- 2024-04-08T05:25:40.491Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542407,'N',TO_TIMESTAMP('2024-04-08 08:25:40','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Project Time','NP','L','C_Project_TimeBooking','DTI',TO_TIMESTAMP('2024-04-08 08:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:25:40.496Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542407 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-04-08T05:25:40.614Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556343,TO_TIMESTAMP('2024-04-08 08:25:40','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Project_TimeBooking',1,'Y','N','Y','Y','C_Project_TimeBooking','N',1000000,TO_TIMESTAMP('2024-04-08 08:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:25:40.624Z
CREATE SEQUENCE C_PROJECT_TIMEBOOKING_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

