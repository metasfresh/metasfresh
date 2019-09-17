-- 2019-09-17T15:08:33.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-09-17 17:08:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568741
;

-- 2019-09-17T15:22:45.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Kundenbetreuer', PrintName='Kundenbetreuer',Updated=TO_TIMESTAMP('2019-09-17 17:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1063 AND AD_Language='de_CH'
;

-- 2019-09-17T15:22:45.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1063,'de_CH') 
;

-- 2019-09-17T15:23:12.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Account manager', PrintName='Account manager',Updated=TO_TIMESTAMP('2019-09-17 17:23:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1063 AND AD_Language='en_US'
;

-- 2019-09-17T15:23:12.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1063,'en_US') 
;

-- 2019-09-17T15:23:21.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kundenbetreuer', PrintName='Kundenbetreuer',Updated=TO_TIMESTAMP('2019-09-17 17:23:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1063 AND AD_Language='de_DE'
;

-- 2019-09-17T15:23:21.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1063,'de_DE') 
;

-- 2019-09-17T15:23:21.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1063,'de_DE') 
;

-- 2019-09-17T15:23:21.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SalesRep_ID', Name='Kundenbetreuer', Description='', Help='' WHERE AD_Element_ID=1063
;

-- 2019-09-17T15:23:21.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesRep_ID', Name='Kundenbetreuer', Description='', Help='', AD_Element_ID=1063 WHERE UPPER(ColumnName)='SALESREP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-17T15:23:21.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesRep_ID', Name='Kundenbetreuer', Description='', Help='' WHERE AD_Element_ID=1063 AND IsCentrallyMaintained='Y'
;

-- 2019-09-17T15:23:21.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kundenbetreuer', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1063) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1063)
;

-- 2019-09-17T15:23:21.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kundenbetreuer', Name='Kundenbetreuer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1063)
;

-- 2019-09-17T15:23:21.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kundenbetreuer', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 1063
;

-- 2019-09-17T15:23:21.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kundenbetreuer', Description='', Help='' WHERE AD_Element_ID = 1063
;

-- 2019-09-17T15:23:21.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kundenbetreuer', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1063
;

-- 2019-09-17T15:25:24.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568785,541357,0,30,353,259,'C_BPartner_SalesRep_ID',TO_TIMESTAMP('2019-09-17 17:25:24','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Vertriebspartner',0,0,TO_TIMESTAMP('2019-09-17 17:25:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-17T15:25:24.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568785 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-17T15:25:24.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(541357) 
;

-- 2019-09-17T15:26:11.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-09-17 17:26:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357
;

-- 2019-09-17T15:26:18.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN C_BPartner_SalesRep_ID NUMERIC(10)')
;

-- 2019-09-17T15:26:19.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Order ADD CONSTRAINT CBPartnerSalesRep_COrder FOREIGN KEY (C_BPartner_SalesRep_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2019-09-17T15:26:55.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2019-09-17 17:26:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568741
;

-- 2019-09-17T15:27:06.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','C_BPartner_SalesRep_ID','NUMERIC(10)',null,null)
;

