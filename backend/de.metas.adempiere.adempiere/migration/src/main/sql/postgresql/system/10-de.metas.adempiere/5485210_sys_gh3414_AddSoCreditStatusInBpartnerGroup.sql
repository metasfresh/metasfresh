-- 2018-02-12T14:11:06.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559025,2181,0,17,289,394,'N','SOCreditStatus',TO_TIMESTAMP('2018-02-12 14:11:05','YYYY-MM-DD HH24:MI:SS'),100,'N','X','Kreditstatus des Geschäftspartners','D',1,'Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Kreditstatus',0,0,TO_TIMESTAMP('2018-02-12 14:11:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-12T14:11:06.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559025 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-12T14:11:20.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-02-12 14:11:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559025
;

-- 2018-02-12T14:11:23.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN SOCreditStatus CHAR(1) DEFAULT ''X'' NOT NULL')
;

-- 2018-02-12T14:14:59.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET ValueName='Higher',Updated=TO_TIMESTAMP('2018-02-12 14:14:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=763
;

-- 2018-02-12T14:15:09.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET ValueName='Lower',Updated=TO_TIMESTAMP('2018-02-12 14:15:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=762
;

-- 2018-02-12T14:15:25.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET ValueName='Same',Updated=TO_TIMESTAMP('2018-02-12 14:15:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=761
;

-- 2018-02-12T14:18:56.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2018-02-12 14:18:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554366
;

