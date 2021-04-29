-- 2021-04-28T10:53:06.459104200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579062,0,TO_TIMESTAMP('2021-04-28 13:53:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sales rep JSON-path','Sales rep JSON-path',TO_TIMESTAMP('2021-04-28 13:53:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-28T10:53:06.515139600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579062 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-28T10:53:32.460280100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', IsTranslated='Y', Name='Vertriebpartner JSON-Path', PrintName='Vertriebpartner JSON-Path',Updated=TO_TIMESTAMP('2021-04-28 13:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579062 AND AD_Language='de_CH'
;

-- 2021-04-28T10:53:32.514107700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579062,'de_CH') 
;

-- 2021-04-28T10:53:47.655099200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', IsTranslated='Y', Name='Vertriebpartner JSON-Path', PrintName='Vertriebpartner JSON-Path',Updated=TO_TIMESTAMP('2021-04-28 13:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579062 AND AD_Language='de_DE'
;

-- 2021-04-28T10:53:47.656178900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579062,'de_DE') 
;

-- 2021-04-28T10:53:47.676427300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579062,'de_DE') 
;

-- 2021-04-28T10:53:47.722013400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID=579062
;

-- 2021-04-28T10:53:47.723013300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID=579062 AND IsCentrallyMaintained='Y'
;

-- 2021-04-28T10:53:47.723013300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579062) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579062)
;

-- 2021-04-28T10:53:47.735992100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vertriebpartner JSON-Path', Name='Vertriebpartner JSON-Path' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579062)
;

-- 2021-04-28T10:53:47.747823700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579062
;

-- 2021-04-28T10:53:47.749840100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID = 579062
;

-- 2021-04-28T10:53:47.750840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertriebpartner JSON-Path', Description = 'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579062
;

-- 2021-04-28T10:54:04.974538500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path expression that specifies where within a customized Shopware order the sales rep can be found.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-28 13:54:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579062 AND AD_Language='en_US'
;

-- 2021-04-28T10:54:04.975538800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579062,'en_US') 
;

-- 2021-04-28T10:57:30.878902200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='JSONPathSalesRepID',Updated=TO_TIMESTAMP('2021-04-28 13:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579062
;

-- 2021-04-28T10:57:30.880902200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathSalesRepID', Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID=579062
;

-- 2021-04-28T10:57:30.881902300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathSalesRepID', Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', Help=NULL, AD_Element_ID=579062 WHERE UPPER(ColumnName)='JSONPATHSALESREPID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-28T10:57:30.882900400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathSalesRepID', Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID=579062 AND IsCentrallyMaintained='Y'
;

-- 2021-04-28T10:57:55.308118900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573639,579062,0,10,541585,'JSONPathSalesRepID',TO_TIMESTAMP('2021-04-28 13:57:55','YYYY-MM-DD HH24:MI:SS'),100,'N','JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vertriebpartner JSON-Path',0,0,TO_TIMESTAMP('2021-04-28 13:57:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-28T10:57:55.344423100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573639 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-28T10:57:55.371323200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579062) 
;

-- 2021-04-28T10:58:23.622504100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN JSONPathSalesRepID VARCHAR(255)')
;

-- 2021-04-28T10:59:19.110321500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!', Name='Kunden JSON-Path', PrintName='Kunden JSON-Path',Updated=TO_TIMESTAMP('2021-04-28 13:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578989 AND AD_Language='de_CH'
;

-- 2021-04-28T10:59:19.112321900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578989,'de_CH') 
;

-- 2021-04-28T10:59:31.589826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!', Name='Kunden JSON-Path', PrintName='Kunden JSON-Path',Updated=TO_TIMESTAMP('2021-04-28 13:59:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578989 AND AD_Language='de_DE'
;

-- 2021-04-28T10:59:31.591831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578989,'de_DE') 
;

-- 2021-04-28T10:59:31.601843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578989,'de_DE') 
;

-- 2021-04-28T10:59:31.646974100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathConstantBPartnerID', Name='Kunden JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE AD_Element_ID=578989
;

-- 2021-04-28T10:59:31.660669700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathConstantBPartnerID', Name='Kunden JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!', Help=NULL, AD_Element_ID=578989 WHERE UPPER(ColumnName)='JSONPATHCONSTANTBPARTNERID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-28T10:59:31.661665900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathConstantBPartnerID', Name='Kunden JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE AD_Element_ID=578989 AND IsCentrallyMaintained='Y'
;

-- 2021-04-28T10:59:31.662665800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kunden JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578989) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578989)
;

-- 2021-04-28T10:59:31.697432200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kunden JSON-Path', Name='Kunden JSON-Path' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578989)
;

-- 2021-04-28T10:59:31.698434400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kunden JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578989
;

-- 2021-04-28T10:59:31.699416900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kunden JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE AD_Element_ID = 578989
;

-- 2021-04-28T10:59:31.700427600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kunden JSON-Path', Description = 'JSON-Path, der angibt wo innerhalb einer kundenspezifisch angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578989
;

-- 2021-04-28T10:59:59.638807300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path expression that specifies where within a customized Shopware order the permanent customer-ID can be found. IMPORTANT: if set, then orders without a respective value are ignored!', Name='Customer JSON-path', PrintName='Customer JSON-path',Updated=TO_TIMESTAMP('2021-04-28 13:59:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578989 AND AD_Language='en_US'
;

-- 2021-04-28T10:59:59.639900900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578989,'en_US') 
;

-- 2021-04-28T11:00:28.597859400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Name='Adress JSON-Path', PrintName='Adress JSON-Path',Updated=TO_TIMESTAMP('2021-04-28 14:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578990 AND AD_Language='de_CH'
;

-- 2021-04-28T11:00:28.599859600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578990,'de_CH') 
;

-- 2021-04-28T11:00:35.638167700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Adress JSON-Path', PrintName='Adress JSON-Path',Updated=TO_TIMESTAMP('2021-04-28 14:00:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578990 AND AD_Language='de_DE'
;

-- 2021-04-28T11:00:35.639773700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578990,'de_DE') 
;

-- 2021-04-28T11:00:35.647181400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578990,'de_DE') 
;

-- 2021-04-28T11:00:35.648180500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathConstantBPartnerLocationID', Name='JSON Path Constant BPartner Location ID', Description='Adress JSON-Path', Help=NULL WHERE AD_Element_ID=578990
;

-- 2021-04-28T11:00:35.649181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathConstantBPartnerLocationID', Name='JSON Path Constant BPartner Location ID', Description='Adress JSON-Path', Help=NULL, AD_Element_ID=578990 WHERE UPPER(ColumnName)='JSONPATHCONSTANTBPARTNERLOCATIONID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-28T11:00:35.650183100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathConstantBPartnerLocationID', Name='JSON Path Constant BPartner Location ID', Description='Adress JSON-Path', Help=NULL WHERE AD_Element_ID=578990 AND IsCentrallyMaintained='Y'
;

-- 2021-04-28T11:00:35.651201700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='JSON Path Constant BPartner Location ID', Description='Adress JSON-Path', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578990) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578990)
;

-- 2021-04-28T11:00:35.666200200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Adress JSON-Path', Name='JSON Path Constant BPartner Location ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578990)
;

-- 2021-04-28T11:00:35.667196400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='JSON Path Constant BPartner Location ID', Description='Adress JSON-Path', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578990
;

-- 2021-04-28T11:00:35.668196500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='JSON Path Constant BPartner Location ID', Description='Adress JSON-Path', Help=NULL WHERE AD_Element_ID = 578990
;

-- 2021-04-28T11:00:35.669197700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'JSON Path Constant BPartner Location ID', Description = 'Adress JSON-Path', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578990
;

-- 2021-04-28T11:00:46.493779700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Name='Adress JSON-Path',Updated=TO_TIMESTAMP('2021-04-28 14:00:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578990 AND AD_Language='de_DE'
;

-- 2021-04-28T11:00:46.494779700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578990,'de_DE') 
;

-- 2021-04-28T11:00:46.501799600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578990,'de_DE') 
;

-- 2021-04-28T11:00:46.502797500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathConstantBPartnerLocationID', Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE AD_Element_ID=578990
;

-- 2021-04-28T11:00:46.503799100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathConstantBPartnerLocationID', Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL, AD_Element_ID=578990 WHERE UPPER(ColumnName)='JSONPATHCONSTANTBPARTNERLOCATIONID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-28T11:00:46.504798700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathConstantBPartnerLocationID', Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE AD_Element_ID=578990 AND IsCentrallyMaintained='Y'
;

-- 2021-04-28T11:00:46.505799300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578990) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578990)
;

-- 2021-04-28T11:00:46.519064100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Adress JSON-Path', Name='Adress JSON-Path' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578990)
;

-- 2021-04-28T11:00:46.520066800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578990
;

-- 2021-04-28T11:00:46.522082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Adress JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', Help=NULL WHERE AD_Element_ID = 578990
;

-- 2021-04-28T11:00:46.522082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Adress JSON-Path', Description = 'JSON-Path, der angibt wo innerhalb einer kundenspezifisch angepassten Shopware-Addresse die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578990
;

-- 2021-04-28T11:01:08.333913400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path expression that specifies where within a customized Shopware address the permanent address-ID can be found. IMPORTANT: if set, then addresses without a respective value are ignored!', Name='Address JSON-path', PrintName='Address JSON-path',Updated=TO_TIMESTAMP('2021-04-28 14:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578990 AND AD_Language='en_US'
;

-- 2021-04-28T11:01:08.334914200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578990,'en_US') 
;

-- 2021-04-28T11:43:55.371516900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573639,644698,0,543435,0,TO_TIMESTAMP('2021-04-28 14:43:55','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.',0,'U',0,'Y','Y','Y','N','N','N','N','N','Vertriebpartner JSON-Path',40,40,0,1,1,TO_TIMESTAMP('2021-04-28 14:43:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-28T11:43:55.375516600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644698 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-28T11:43:55.379516200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579062) 
;

-- 2021-04-28T11:43:55.393513100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644698
;

-- 2021-04-28T11:43:55.426774200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(644698)
;

-- 2021-04-28T11:44:10.933656200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2021-04-28 14:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=644698
;

-- 2021-04-28T11:46:20.884980900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,644698,0,543435,584110,544975,'F',TO_TIMESTAMP('2021-04-28 14:46:20','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die externe ID der Vertriebspartners zu ausgelesen werden kann.','Y','N','N','Y','N','N','N',0,'Vertriebpartner JSON-Path',60,0,0,TO_TIMESTAMP('2021-04-28 14:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-28T11:46:27.216215700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2021-04-28 14:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584110
;

-- 2021-04-29T11:24:40.446103500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.',Updated=TO_TIMESTAMP('2021-04-29 14:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579062 AND AD_Language='de_CH'
;

-- 2021-04-29T11:24:40.457373600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579062,'de_CH') 
;

-- 2021-04-29T11:24:46.154639800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.',Updated=TO_TIMESTAMP('2021-04-29 14:24:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579062 AND AD_Language='de_DE'
;

-- 2021-04-29T11:24:46.155636600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579062,'de_DE') 
;

-- 2021-04-29T11:24:46.180636600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579062,'de_DE') 
;

-- 2021-04-29T11:24:46.193811900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathSalesRepID', Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID=579062
;

-- 2021-04-29T11:24:46.195810400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathSalesRepID', Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.', Help=NULL, AD_Element_ID=579062 WHERE UPPER(ColumnName)='JSONPATHSALESREPID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;


-- 2021-04-29T11:24:46.197444600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathSalesRepID', Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID=579062 AND IsCentrallyMaintained='Y'
;

-- 2021-04-29T11:24:46.198516200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579062) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579062)
;

-- 2021-04-29T11:24:46.218085800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579062
;

-- 2021-04-29T11:24:46.220103600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertriebpartner JSON-Path', Description='JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID = 579062
;

-- 2021-04-29T11:24:46.221104100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertriebpartner JSON-Path', Description = 'JSON-Path, der angibt wo innerhalb einer kundenspezifisch angepassten Shopware-Order der Suchschlüssel des Vertriebspartners zu ausgelesen werden kann.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579062
;

-- 2021-04-29T11:24:55.186433100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='JSON-Path expression that specifies where within a customized Shopware order the sales rep''s search-key can be found.',Updated=TO_TIMESTAMP('2021-04-29 14:24:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579062 AND AD_Language='en_US'
;

-- 2021-04-29T11:24:55.187448300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579062,'en_US') 
;
