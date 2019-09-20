-- 2019-09-20T15:12:32.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568799,541357,0,18,138,291,540451,'C_BPartner_SalesRep_ID',TO_TIMESTAMP('2019-09-20 17:12:31','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Vertriebspartner',0,0,TO_TIMESTAMP('2019-09-20 17:12:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-20T15:12:32.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568799 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-20T15:12:32.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(541357) 
;

-- 2019-09-20T15:12:41.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN C_BPartner_SalesRep_ID NUMERIC(10)')
;

-- 2019-09-20T15:12:42.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner ADD CONSTRAINT CBPartnerSalesRep_CBPartner FOREIGN KEY (C_BPartner_SalesRep_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2019-09-20T15:23:40.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577106,0,'IsSalesPartnerRequired',TO_TIMESTAMP('2019-09-20 17:23:40','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob eine Beauftragung durch den Kunden nur unter Angabe eines Vertriebspartners erlaubt ist.','de.metas.contracts.commission','Y','Nur mit Vertriebspartner','Nur mit Vertriebspartner',TO_TIMESTAMP('2019-09-20 17:23:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T15:23:40.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577106 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-20T15:23:44.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-20 17:23:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106 AND AD_Language='de_CH'
;

-- 2019-09-20T15:23:44.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577106,'de_CH') 
;

-- 2019-09-20T15:23:46.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-20 17:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106 AND AD_Language='de_DE'
;

-- 2019-09-20T15:23:46.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577106,'de_DE') 
;

-- 2019-09-20T15:23:46.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577106,'de_DE') 
;

-- 2019-09-20T15:25:29.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies whether a sales order is allowed only if a sales partner is specified', IsTranslated='Y', Name='Sales partner required', PrintName='Sales partner required',Updated=TO_TIMESTAMP('2019-09-20 17:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106 AND AD_Language='en_US'
;

-- 2019-09-20T15:25:29.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577106,'en_US') 
;

-- 2019-09-20T15:25:43.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568800,577106,0,20,291,'IsSalesPartnerRequired',TO_TIMESTAMP('2019-09-20 17:25:43','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Legt fest, ob eine Beauftragung durch den Kunden nur unter Angabe eines Vertriebspartners erlaubt ist.','de.metas.contracts.commission',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Nur mit Vertriebspartner',0,0,TO_TIMESTAMP('2019-09-20 17:25:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-20T15:25:43.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568800 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-20T15:25:43.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577106) 
;

-- 2019-09-20T15:25:44.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsSalesPartnerRequired CHAR(1) DEFAULT ''N'' CHECK (IsSalesPartnerRequired IN (''Y'',''N'')) NOT NULL')
;

-- 2019-09-20T15:35:09.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.',Updated=TO_TIMESTAMP('2019-09-20 17:35:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106 AND AD_Language='de_CH'
;

-- 2019-09-20T15:35:09.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577106,'de_CH') 
;

-- 2019-09-20T15:35:19.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.',Updated=TO_TIMESTAMP('2019-09-20 17:35:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106 AND AD_Language='de_DE'
;

-- 2019-09-20T15:35:19.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577106,'de_DE') 
;

-- 2019-09-20T15:35:19.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577106,'de_DE') 
;

-- 2019-09-20T15:35:19.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSalesPartnerRequired', Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.', Help=NULL WHERE AD_Element_ID=577106
;

-- 2019-09-20T15:35:19.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesPartnerRequired', Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.', Help=NULL, AD_Element_ID=577106 WHERE UPPER(ColumnName)='ISSALESPARTNERREQUIRED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-20T15:35:19.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesPartnerRequired', Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.', Help=NULL WHERE AD_Element_ID=577106 AND IsCentrallyMaintained='Y'
;

-- 2019-09-20T15:35:19.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577106) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577106)
;

-- 2019-09-20T15:35:19.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577106
;

-- 2019-09-20T15:35:19.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.', Help=NULL WHERE AD_Element_ID = 577106
;

-- 2019-09-20T15:35:19.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nur mit Vertriebspartner', Description = 'Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577106
;

-- 2019-09-20T15:35:59.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies for a bill partner whether a sales partner has to be specified in a sales order.',Updated=TO_TIMESTAMP('2019-09-20 17:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106 AND AD_Language='en_US'
;

-- 2019-09-20T15:35:59.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577106,'en_US') 
;

-- 2019-09-20T20:32:45.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner','IsSalesPartnerRequired','CHAR(1)',null,'N')
;

-- 2019-09-20T20:32:46.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_BPartner SET IsSalesPartnerRequired='N' WHERE IsSalesPartnerRequired IS NULL
;

-- 2019-09-20T20:33:05.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-09-20 22:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568800
;

-- 2019-09-20T20:43:55.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544939,0,TO_TIMESTAMP('2019-09-20 22:43:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Für diesen Rechnungspartner ist die Angabe eines Vertriebspartners erforderlich.','I',TO_TIMESTAMP('2019-09-20 22:43:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission.salesorder.CustomerNeedsSalesPartner')
;

-- 2019-09-20T20:43:55.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544939 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-09-20T20:44:05.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-20 22:44:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544939
;

-- 2019-09-20T20:44:59.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='This bill partner requires a sales partner to be specified.',Updated=TO_TIMESTAMP('2019-09-20 22:44:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544939
;

