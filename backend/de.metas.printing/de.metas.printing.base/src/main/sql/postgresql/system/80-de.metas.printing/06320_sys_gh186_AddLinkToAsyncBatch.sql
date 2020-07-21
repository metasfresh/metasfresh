-- 20.01.2017 11:18:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556046,542581,0,19,540435,'N','C_Async_Batch_ID',TO_TIMESTAMP('2017-01-20 11:18:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Async Batch',0,TO_TIMESTAMP('2017-01-20 11:18:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 20.01.2017 11:18:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556046 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 20.01.2017 11:18:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Printing_Queue ADD C_Async_Batch_ID NUMERIC(10) DEFAULT NULL 
;

