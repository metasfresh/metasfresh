-- 2019-11-28T13:55:32.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577400,0,'CostPrice',TO_TIMESTAMP('2019-11-28 15:55:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost Price','Cost Price',TO_TIMESTAMP('2019-11-28 15:55:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-28T13:55:32.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577400 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-11-28T13:58:18.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569687,577400,0,10,572,'CostPrice',TO_TIMESTAMP('2019-11-28 15:58:18','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Cost Price',0,0,TO_TIMESTAMP('2019-11-28 15:58:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-11-28T13:58:18.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569687 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-28T13:58:18.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577400) 
;

-- 2019-11-28T13:59:37.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,569687,540044,541459,0,TO_TIMESTAMP('2019-11-28 15:59:37','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','CostPrice',70,6,TO_TIMESTAMP('2019-11-28 15:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-28T13:59:55.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=7,Updated=TO_TIMESTAMP('2019-11-28 15:59:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541459
;

-- 2019-11-28T14:20:30.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=37,Updated=TO_TIMESTAMP('2019-11-28 16:20:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569687
;

-- 2019-11-28T14:20:32.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE public.I_Inventory ADD COLUMN CostPrice NUMERIC')
;

-- 2019-11-28T14:25:07.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataType='N',Updated=TO_TIMESTAMP('2019-11-28 16:25:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541459
;

