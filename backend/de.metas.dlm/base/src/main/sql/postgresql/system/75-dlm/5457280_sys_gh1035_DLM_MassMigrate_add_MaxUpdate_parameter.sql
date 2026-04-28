-- 27.02.2017 15:00
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540761,541162,11,'MaxUpdates',TO_TIMESTAMP('2017-02-27 15:00:35','YYYY-MM-DD HH24:MI:SS'),100,'10000000','Bei einer Zahl > 0 wird der Prozess die DB Funktion update_production_table nur so oft aufrufen, bis die Zahl überschritten ist und sich danach beenden.','de.metas.dlm',0,'Siehe https://github.com/metasfresh/metasfresh/issues/1035','Y','N','Y','N','Y','N','Maximale Anzahl von Updates',30,TO_TIMESTAMP('2017-02-27 15:00:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.02.2017 15:00
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541162 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

