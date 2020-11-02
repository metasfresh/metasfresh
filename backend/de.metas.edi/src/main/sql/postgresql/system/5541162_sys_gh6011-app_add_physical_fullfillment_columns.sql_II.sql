
-- 2020-01-10T19:13:41.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','SumDeliveredInStockingUOM',null,'NOT NULL',null)
;

-- 2020-01-10T19:13:50.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2020-01-10 20:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569830
;

-- 2020-01-10T19:16:52.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569831,577456,0,29,540644,'SumOrderedInStockingUOM',TO_TIMESTAMP('2020-01-10 20:16:52','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','SumOrderedInStockingUOM',0,0,TO_TIMESTAMP('2020-01-10 20:16:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-01-10T19:16:52.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569831 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-01-10T19:16:52.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577456) 
;

-- 2020-01-10T19:16:53.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_Desadv','ALTER TABLE public.EDI_Desadv ADD COLUMN SumOrderedInStockingUOM NUMERIC')
;

-- 2020-01-10T19:16:58.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2020-01-10 20:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569831
;

-- 2020-01-10T19:16:59.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','SumOrderedInStockingUOM','NUMERIC',null,'0')
;

-- 2020-01-10T19:16:59.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EDI_Desadv SET SumOrderedInStockingUOM=0 WHERE SumOrderedInStockingUOM IS NULL
;

-- 2020-01-10T19:16:59.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','SumOrderedInStockingUOM',null,'NOT NULL',null)
;

-- 2020-01-10T19:17:04.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2020-01-10 20:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569831
;

-- 2020-01-10T19:17:06.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','SumOrderedInStockingUOM','NUMERIC',null,null)
;

-- 2020-01-10T19:17:19.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2020-01-10 20:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569831
;

-- 2020-01-10T19:17:21.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','SumOrderedInStockingUOM','NUMERIC',null,'0')
;

-- 2020-01-10T19:17:21.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EDI_Desadv SET SumOrderedInStockingUOM=0 WHERE SumOrderedInStockingUOM IS NULL
;

ALTER TABLE EDI_Desadv RENAME COLUMN EDI_DESADV_SumPercentage TO FulFillmentPercent;

-- 2020-01-10T19:19:21.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FulFillmentPercentage',Updated=TO_TIMESTAMP('2020-01-10 20:19:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542906
;

-- 2020-01-10T19:19:21.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FulFillmentPercentage', Name='Geliefert %', Description=NULL, Help=NULL WHERE AD_Element_ID=542906
;

-- 2020-01-10T19:19:21.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FulFillmentPercentage', Name='Geliefert %', Description=NULL, Help=NULL, AD_Element_ID=542906 WHERE UPPER(ColumnName)='FULFILLMENTPERCENTAGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-10T19:19:21.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FulFillmentPercentage', Name='Geliefert %', Description=NULL, Help=NULL WHERE AD_Element_ID=542906 AND IsCentrallyMaintained='Y'
;

-- 2020-01-10T19:19:49.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FulFillmentPercent',Updated=TO_TIMESTAMP('2020-01-10 20:19:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542906
;

-- 2020-01-10T19:19:49.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FulFillmentPercent', Name='Geliefert %', Description=NULL, Help=NULL WHERE AD_Element_ID=542906
;

-- 2020-01-10T19:19:49.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FulFillmentPercent', Name='Geliefert %', Description=NULL, Help=NULL, AD_Element_ID=542906 WHERE UPPER(ColumnName)='FULFILLMENTPERCENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-10T19:19:49.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FulFillmentPercent', Name='Geliefert %', Description=NULL, Help=NULL WHERE AD_Element_ID=542906 AND IsCentrallyMaintained='Y'
;

-- 2020-01-10T19:21:15.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2020-01-10 20:21:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552776
;

-- 2020-01-10T19:21:15.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','FulFillmentPercent','NUMERIC',null,'0')
;

-- 2020-01-10T19:21:15.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EDI_Desadv SET FulFillmentPercent=0 WHERE FulFillmentPercent IS NULL
;

