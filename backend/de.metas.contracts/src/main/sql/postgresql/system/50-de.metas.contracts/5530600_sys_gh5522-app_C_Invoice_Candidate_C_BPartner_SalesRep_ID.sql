-- 2019-09-13T14:51:51.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.contracts.commission',Updated=TO_TIMESTAMP('2019-09-13 16:51:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357
;

-- 2019-09-13T14:52:05.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertriebspartner', PrintName='Vertriebspartner',Updated=TO_TIMESTAMP('2019-09-13 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='de_CH'
;

-- 2019-09-13T14:52:05.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'de_CH') 
;

-- 2019-09-13T14:52:15.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sales partner', PrintName='Sales partner',Updated=TO_TIMESTAMP('2019-09-13 16:52:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='en_US'
;

-- 2019-09-13T14:52:15.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'en_US') 
;

-- 2019-09-13T14:52:24.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertriebspartner', PrintName='Vertriebspartner',Updated=TO_TIMESTAMP('2019-09-13 16:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='de_DE'
;

-- 2019-09-13T14:52:24.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'de_DE') 
;

-- 2019-09-13T14:52:24.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541357,'de_DE') 
;

-- 2019-09-13T14:52:24.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_SalesRep_ID', Name='Vertriebspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=541357
;

-- 2019-09-13T14:52:24.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_SalesRep_ID', Name='Vertriebspartner', Description=NULL, Help=NULL, AD_Element_ID=541357 WHERE UPPER(ColumnName)='C_BPARTNER_SALESREP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-13T14:52:24.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_SalesRep_ID', Name='Vertriebspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=541357 AND IsCentrallyMaintained='Y'
;

-- 2019-09-13T14:52:24.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertriebspartner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541357) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541357)
;

-- 2019-09-13T14:52:24.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vertriebspartner', Name='Vertriebspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541357)
;

-- 2019-09-13T14:52:24.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertriebspartner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541357
;

-- 2019-09-13T14:52:24.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertriebspartner', Description=NULL, Help=NULL WHERE AD_Element_ID = 541357
;

-- 2019-09-13T14:52:24.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertriebspartner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541357
;

-- 2019-09-13T14:53:27.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568741,541357,0,30,353,540270,'C_BPartner_SalesRep_ID',TO_TIMESTAMP('2019-09-13 16:53:27','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Vertriebspartner',0,0,TO_TIMESTAMP('2019-09-13 16:53:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-13T14:53:27.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568741 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-13T14:53:27.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(541357) 
;

-- 2019-09-13T14:53:38.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_BPartner_SalesRep_ID NUMERIC(10)')
;

-- 2019-09-13T14:53:39.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CBPartnerSalesRep_CInvoiceCandidate FOREIGN KEY (C_BPartner_SalesRep_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2019-09-13T14:54:01.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.contracts.commission',Updated=TO_TIMESTAMP('2019-09-13 16:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568741
;

