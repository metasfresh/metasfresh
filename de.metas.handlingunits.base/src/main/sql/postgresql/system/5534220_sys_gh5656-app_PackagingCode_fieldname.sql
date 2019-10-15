-- 2019-10-15T12:53:51.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577221,0,'HU_UnitType_Restriction',TO_TIMESTAMP('2019-10-15 14:53:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Beschränkung auf HU-Typ','Beschränkung auf HU-Typ',TO_TIMESTAMP('2019-10-15 14:53:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-15T12:53:51.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577221 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-15T12:53:55.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-15 14:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577221 AND AD_Language='de_CH'
;

-- 2019-10-15T12:53:55.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577221,'de_CH') 
;

-- 2019-10-15T12:53:57.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-15 14:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577221 AND AD_Language='de_DE'
;

-- 2019-10-15T12:53:57.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577221,'de_DE') 
;

-- 2019-10-15T12:53:57.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577221,'de_DE') 
;

-- 2019-10-15T12:54:10.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Restricted to HU type', PrintName='Restricted to HU type',Updated=TO_TIMESTAMP('2019-10-15 14:54:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577221 AND AD_Language='en_US'
;

-- 2019-10-15T12:54:10.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577221,'en_US') 
;

-- 2019-10-15T12:54:17.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beschränkt auf HU-Typ', PrintName='Beschränkt auf HU-Typ',Updated=TO_TIMESTAMP('2019-10-15 14:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577221 AND AD_Language='de_DE'
;

-- 2019-10-15T12:54:17.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577221,'de_DE') 
;

-- 2019-10-15T12:54:17.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577221,'de_DE') 
;

-- 2019-10-15T12:54:17.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HU_UnitType_Restriction', Name='Beschränkt auf HU-Typ', Description=NULL, Help=NULL WHERE AD_Element_ID=577221
;

-- 2019-10-15T12:54:17.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_UnitType_Restriction', Name='Beschränkt auf HU-Typ', Description=NULL, Help=NULL, AD_Element_ID=577221 WHERE UPPER(ColumnName)='HU_UNITTYPE_RESTRICTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-15T12:54:17.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_UnitType_Restriction', Name='Beschränkt auf HU-Typ', Description=NULL, Help=NULL WHERE AD_Element_ID=577221 AND IsCentrallyMaintained='Y'
;

-- 2019-10-15T12:54:17.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beschränkt auf HU-Typ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577221) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577221)
;

-- 2019-10-15T12:54:17.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beschränkt auf HU-Typ', Name='Beschränkt auf HU-Typ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577221)
;

-- 2019-10-15T12:54:17.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Beschränkt auf HU-Typ', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577221
;

-- 2019-10-15T12:54:17.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Beschränkt auf HU-Typ', Description=NULL, Help=NULL WHERE AD_Element_ID = 577221
;

-- 2019-10-15T12:54:17.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Beschränkt auf HU-Typ', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577221
;

-- 2019-10-15T12:54:21.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beschränkt auf HU-Typ', PrintName='Beschränkt auf HU-Typ',Updated=TO_TIMESTAMP('2019-10-15 14:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577221 AND AD_Language='de_CH'
;

-- 2019-10-15T12:54:21.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577221,'de_CH') 
;

-- 2019-10-15T12:54:33.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577221, Description=NULL, Help=NULL, Name='Beschränkt auf HU-Typ',Updated=TO_TIMESTAMP('2019-10-15 14:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589561
;

-- 2019-10-15T12:54:33.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577221) 
;

-- 2019-10-15T12:54:33.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589561
;

-- 2019-10-15T12:54:33.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589561)
;

