
--
-- insert OldestFirst paramt for the DLM_Partiton_Create process
--
-- 25.10.2016 08:28
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540728,541044,20,'OldestFirst',TO_TIMESTAMP('2016-10-25 08:28:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','Legt fest, ob Datensätze mit der kleinsten ID oder Datensätze mit der größten ID zuerst verarbeitet werden','de.metas.dlm',0,'Wenn der DB-Paramter "metasfresh.DLM_Coalesce_Level" auf 2 eingestellt ist, machte es in der Regel Sinn, die neuesten Daten zuerst zu Partitionieren.','Y','N','Y','N','Y','N','Alte Datensätze zuerst',30,TO_TIMESTAMP('2016-10-25 08:28:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.10.2016 08:28
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541044 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;


--
-- move the process classes out of de.metas.dlm.partition
--
-- 25.10.2016 08:30
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.dlm.process.Add_Table_to_DLM',Updated=TO_TIMESTAMP('2016-10-25 08:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540729
;

-- 25.10.2016 08:30
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.dlm.process.Remove_Table_from_DLM',Updated=TO_TIMESTAMP('2016-10-25 08:30:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540730
;

