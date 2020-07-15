-- 2020-06-24T11:59:31.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,ValueMin,Version) VALUES (0,570892,577696,0,22,541494,'FeePercentageOfGrandTotal',TO_TIMESTAMP('2020-06-24 14:59:31','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Prozentsatz der Rechnungssumme',0,0,TO_TIMESTAMP('2020-06-24 14:59:31','YYYY-MM-DD HH24:MI:SS'),100,'0',0)
;

-- 2020-06-24T11:59:31.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570892 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-06-24T11:59:31.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577696) 
;

-- 2020-06-24T12:02:04.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542596,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2020-06-24 15:02:04','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2020-06-24 15:02:04','YYYY-MM-DD HH24:MI:SS'),100,614953,'Y',50,40,1,1,570892,'Prozentsatz der Rechnungssumme',0,'D')
;

-- 2020-06-24T12:02:04.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614953 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-24T12:02:04.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577696) 
;

-- 2020-06-24T12:02:04.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614953
;

-- 2020-06-24T12:02:04.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(614953)
;

-- 2020-06-24T12:10:56.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=25,Updated=TO_TIMESTAMP('2020-06-24 15:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=614953
;

-- 2020-06-24T12:11:52.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50, SeqNoGrid=4015,Updated=TO_TIMESTAMP('2020-06-24 15:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=614953
;

-- 2020-06-24T12:11:57.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=15,Updated=TO_TIMESTAMP('2020-06-24 15:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=614953
;

-- 2020-06-24T12:12:49.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2020-06-24 15:12:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570892
;

-- 2020-06-24T12:12:52.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('InvoiceProcessingServiceCompany_BPartnerAssignment','ALTER TABLE public.InvoiceProcessingServiceCompany_BPartnerAssignment ADD COLUMN FeePercentageOfGrandTotal NUMERIC DEFAULT 0 NOT NULL')
;

-- 2020-06-24T12:14:01.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=606042
;

-- 2020-06-24T12:14:01.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=606042
;

-- 2020-06-24T12:14:01.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=606042
;

-- 2020-06-24T12:16:56.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=570666
;

-- 2020-06-24T12:16:56.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=570666
;

-- DROP the column from header table
SELECT public.db_alter_table('InvoiceProcessingServiceCompany','ALTER TABLE InvoiceProcessingServiceCompany DROP COLUMN IF EXISTS FeePercentageOfGrandTotal');
