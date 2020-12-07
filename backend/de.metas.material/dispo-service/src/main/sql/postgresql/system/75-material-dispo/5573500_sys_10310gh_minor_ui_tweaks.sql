-- 2020-11-27T07:35:55.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-27 08:35:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543909 AND AD_Language='de_CH'
;

-- 2020-11-27T07:35:55.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543909,'de_CH') 
;

-- 2020-11-27T07:36:10.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-27 08:36:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543909 AND AD_Language='de_DE'
;

-- 2020-11-27T07:36:10.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543909,'de_DE') 
;

-- 2020-11-27T07:36:10.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543909,'de_DE') 
;

-- 2020-11-27T07:36:10.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Qty_AvailableToPromise', Name='Zusagbar (ATP)', Description='ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=543909
;

-- 2020-11-27T07:36:10.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Qty_AvailableToPromise', Name='Zusagbar (ATP)', Description='ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', Help=NULL, AD_Element_ID=543909 WHERE UPPER(ColumnName)='QTY_AVAILABLETOPROMISE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-27T07:36:10.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Qty_AvailableToPromise', Name='Zusagbar (ATP)', Description='ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=543909 AND IsCentrallyMaintained='Y'
;

-- 2020-11-27T07:36:10.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zusagbar (ATP)', Description='ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543909) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543909)
;

-- 2020-11-27T07:36:11.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zusagbar (ATP)', Description='ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543909
;

-- 2020-11-27T07:36:11.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zusagbar (ATP)', Description='ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID = 543909
;

-- 2020-11-27T07:36:11.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zusagbar (ATP)', Description = 'ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543909
;

-- 2020-11-27T07:52:56.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2020-11-27 08:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543909 AND AD_Language='de_CH'
;

-- 2020-11-27T07:52:56.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543909,'de_CH') 
;

-- 2020-11-27T07:53:01.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2020-11-27 08:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543909 AND AD_Language='de_DE'
;

-- 2020-11-27T07:53:01.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543909,'de_DE') 
;

-- 2020-11-27T07:53:01.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543909,'de_DE') 
;

-- 2020-11-27T07:53:01.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Qty_AvailableToPromise', Name='Zusagbar (ATP)', Description='', Help=NULL WHERE AD_Element_ID=543909
;

-- 2020-11-27T07:53:01.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Qty_AvailableToPromise', Name='Zusagbar (ATP)', Description='', Help=NULL, AD_Element_ID=543909 WHERE UPPER(ColumnName)='QTY_AVAILABLETOPROMISE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-27T07:53:01.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Qty_AvailableToPromise', Name='Zusagbar (ATP)', Description='', Help=NULL WHERE AD_Element_ID=543909 AND IsCentrallyMaintained='Y'
;

-- 2020-11-27T07:53:01.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zusagbar (ATP)', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543909) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543909)
;

-- 2020-11-27T07:53:01.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zusagbar (ATP)', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543909
;

-- 2020-11-27T07:53:01.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zusagbar (ATP)', Description='', Help=NULL WHERE AD_Element_ID = 543909
;

-- 2020-11-27T07:53:01.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zusagbar (ATP)', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543909
;

-- 2020-11-27T07:54:07.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578582,0,'Qty_AvailableToPromise_Incl_QtyPlanned',TO_TIMESTAMP('2020-11-27 08:54:07','YYYY-MM-DD HH24:MI:SS'),100,'ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes','de.metas.material.dispo','Y','Zusagbar (ATP)','Zusagbar (ATP)',TO_TIMESTAMP('2020-11-27 08:54:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-27T07:54:07.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578582 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-27T07:54:11.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-27 08:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578582 AND AD_Language='de_CH'
;

-- 2020-11-27T07:54:11.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578582,'de_CH') 
;

-- 2020-11-27T07:54:14.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-27 08:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578582 AND AD_Language='de_DE'
;

-- 2020-11-27T07:54:14.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578582,'de_DE') 
;

-- 2020-11-27T07:54:14.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578582,'de_DE') 
;

-- 2020-11-27T07:54:26.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=578582, Description='ATP-Menge unter Einbeziehung der geplanten Menge des jeweiligen Datensatzes', Help=NULL, Name='Zusagbar (ATP)',Updated=TO_TIMESTAMP('2020-11-27 08:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563011
;

-- 2020-11-27T07:54:26.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578582) 
;

-- 2020-11-27T07:54:26.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=563011
;

-- 2020-11-27T07:54:26.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(563011)
;
