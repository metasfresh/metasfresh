-- 2018-05-08T07:34:03.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy
	,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel
	,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence
	,IsDLMPartitionBoundary,IsGenericZoomKeyColumn
	,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin)
VALUES (16,'N','N','N','N',0,'Y',100,543388,'Y','N','N','N','N','N','N','N','Y','N','N','N',540861,'N','N','ReminderDate',560047,'N','N','N','N','N','N',0,100,'Wiedervorlage Datum','de.metas.purchasecandidate','N',7,0,0,TO_TIMESTAMP('2018-05-08 07:34:03','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-05-08 07:34:03','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-05-08T07:34:03.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560047 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-11T18:05:37.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN ReminderDate TIMESTAMP WITH TIME ZONE')
;

