-- 2022-07-11T08:40:26.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=610023
;

-- 2022-07-11T08:40:26.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701329
;

-- 2022-07-11T08:40:26.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=701329
;

-- 2022-07-11T08:40:26.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=701329
;

-- 2022-07-11T08:40:26.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('LeichMehl_PluFile_Config','ALTER TABLE LeichMehl_PluFile_Config DROP COLUMN IF EXISTS ReplacePattern')
;

-- 2022-07-11T08:40:26.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583581
;

-- 2022-07-11T08:40:26.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=583581
;

-- 2022-07-11T08:41:39.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=581082
;

-- 2022-07-11T08:41:39.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=581082
;

-- 2022-07-11T08:42:35.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581091,0,'ReplaceRegExp',TO_TIMESTAMP('2022-07-11 11:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Regular expression used when replacing the value from the matched field. With this configuration a full replacement can be done or a partial one. full replacement e.g. given that there is a "value" = "Test sentence.", "ReplaceRegExp" = ":*" and the new value is "TEST", after replacement is performed "value" = "TEST". Partial replacement e.g. given that there is a "value" = "Test sentence.", "ReplaceRegExp" = ".*(Test).*" and the new value is "Dummy test", after replacement is performed "value" = "Dummy test sentence."','D','Y','Replace Regexp','Replace Regexp',TO_TIMESTAMP('2022-07-11 11:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-11T08:42:35.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581091 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-11T08:42:52.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', Name='Regulärer Ersetzungsausdruck', PrintName='Regulärer Ersetzungsausdruck',Updated=TO_TIMESTAMP('2022-07-11 11:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581091 AND AD_Language='de_CH'
;

-- 2022-07-11T08:42:52.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581091,'de_CH') 
;

-- 2022-07-11T08:43:04.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', Name='Regulärer Ersetzungsausdruck', PrintName='Regulärer Ersetzungsausdruck',Updated=TO_TIMESTAMP('2022-07-11 11:43:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581091 AND AD_Language='de_DE'
;

-- 2022-07-11T08:43:04.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581091,'de_DE') 
;

-- 2022-07-11T08:43:04.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581091,'de_DE') 
;

-- 2022-07-11T08:43:04.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ReplaceRegExp', Name='Regulärer Ersetzungsausdruck', Description='Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', Help=NULL WHERE AD_Element_ID=581091
;

-- 2022-07-11T08:43:04.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReplaceRegExp', Name='Regulärer Ersetzungsausdruck', Description='Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', Help=NULL, AD_Element_ID=581091 WHERE UPPER(ColumnName)='REPLACEREGEXP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-11T08:43:04.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReplaceRegExp', Name='Regulärer Ersetzungsausdruck', Description='Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', Help=NULL WHERE AD_Element_ID=581091 AND IsCentrallyMaintained='Y'
;

-- 2022-07-11T08:43:04.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Regulärer Ersetzungsausdruck', Description='Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581091) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581091)
;

-- 2022-07-11T08:43:04.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Regulärer Ersetzungsausdruck', Name='Regulärer Ersetzungsausdruck' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581091)
;

-- 2022-07-11T08:43:04.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Regulärer Ersetzungsausdruck', Description='Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581091
;

-- 2022-07-11T08:43:04.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Regulärer Ersetzungsausdruck', Description='Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', Help=NULL WHERE AD_Element_ID = 581091
;

-- 2022-07-11T08:43:04.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Regulärer Ersetzungsausdruck', Description = 'Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581091
;

-- 2022-07-11T08:43:12.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."', Name='Regulärer Ersetzungsausdruck', PrintName='Regulärer Ersetzungsausdruck',Updated=TO_TIMESTAMP('2022-07-11 11:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581091 AND AD_Language='nl_NL'
;

-- 2022-07-11T08:43:12.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581091,'nl_NL') 
;

-- 2022-07-11T08:43:53.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583590,581091,0,10,542182,'ReplaceRegExp',TO_TIMESTAMP('2022-07-11 11:43:53','YYYY-MM-DD HH24:MI:SS'),100,'N',':*','Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Regulärer Ersetzungsausdruck',0,0,TO_TIMESTAMP('2022-07-11 11:43:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-11T08:43:53.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583590 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-11T08:43:53.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581091) 
;

-- 2022-07-11T08:43:56.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('LeichMehl_PluFile_Config','ALTER TABLE public.LeichMehl_PluFile_Config ADD COLUMN ReplaceRegExp VARCHAR(255) DEFAULT '':*''')
;

-- 2022-07-11T08:44:21.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='PLU-Datei Konfig',Updated=TO_TIMESTAMP('2022-07-11 11:44:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542182
;

-- 2022-07-11T08:44:24.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='PLU-Datei Konfig',Updated=TO_TIMESTAMP('2022-07-11 11:44:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=542182
;

-- 2022-07-11T08:44:27.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='PLU file config',Updated=TO_TIMESTAMP('2022-07-11 11:44:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542182
;

-- 2022-07-11T08:44:36.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='PLU-Datei Konfig',Updated=TO_TIMESTAMP('2022-07-11 11:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542182
;

-- 2022-07-11T08:45:06.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies the name of the field from the PLU file which will be updated.', Name='Target field', PrintName='Target field',Updated=TO_TIMESTAMP('2022-07-11 11:45:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581080 AND AD_Language='de_CH'
;

-- 2022-07-11T08:45:06.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581080,'de_CH') 
;

-- 2022-07-11T08:45:22.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', Name='Zielfeld', PrintName='Zielfeld',Updated=TO_TIMESTAMP('2022-07-11 11:45:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581080 AND AD_Language='de_CH'
;

-- 2022-07-11T08:45:22.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581080,'de_CH') 
;

-- 2022-07-11T08:45:29.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', Name='Zielfeld', PrintName='Zielfeld',Updated=TO_TIMESTAMP('2022-07-11 11:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581080 AND AD_Language='de_DE'
;

-- 2022-07-11T08:45:29.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581080,'de_DE') 
;

-- 2022-07-11T08:45:29.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581080,'de_DE') 
;

-- 2022-07-11T08:45:29.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TargetFieldName', Name='Zielfeld', Description='Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', Help=NULL WHERE AD_Element_ID=581080
;

-- 2022-07-11T08:45:29.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TargetFieldName', Name='Zielfeld', Description='Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', Help=NULL, AD_Element_ID=581080 WHERE UPPER(ColumnName)='TARGETFIELDNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-11T08:45:29.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TargetFieldName', Name='Zielfeld', Description='Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', Help=NULL WHERE AD_Element_ID=581080 AND IsCentrallyMaintained='Y'
;

-- 2022-07-11T08:45:29.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zielfeld', Description='Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581080) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581080)
;

-- 2022-07-11T08:45:29.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zielfeld', Name='Zielfeld' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581080)
;

-- 2022-07-11T08:45:29.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zielfeld', Description='Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581080
;

-- 2022-07-11T08:45:29.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zielfeld', Description='Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', Help=NULL WHERE AD_Element_ID = 581080
;

-- 2022-07-11T08:45:29.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zielfeld', Description = 'Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581080
;

-- 2022-07-11T08:45:36.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Namen des Feldes aus der importierten PLU-Datei an, das aktualisiert werden soll. ', Name='Zielfeld', PrintName='Zielfeld',Updated=TO_TIMESTAMP('2022-07-11 11:45:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581080 AND AD_Language='nl_NL'
;

-- 2022-07-11T08:45:36.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581080,'nl_NL') 
;

-- 2022-07-11T08:45:45.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies the name of the field from the PLU file which will be updated.', Name='Target field', PrintName='Target field',Updated=TO_TIMESTAMP('2022-07-11 11:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581080 AND AD_Language='en_US'
;

-- 2022-07-11T08:45:45.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581080,'en_US') 
;

-- 2022-07-11T08:46:26.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', Name='Zielfeldtyp', PrintName='Zielfeldtyp',Updated=TO_TIMESTAMP('2022-07-11 11:46:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581081 AND AD_Language='de_CH'
;

-- 2022-07-11T08:46:26.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581081,'de_CH') 
;

-- 2022-07-11T08:46:32.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', Name='Zielfeldtyp', PrintName='Zielfeldtyp',Updated=TO_TIMESTAMP('2022-07-11 11:46:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581081 AND AD_Language='de_DE'
;

-- 2022-07-11T08:46:32.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581081,'de_DE') 
;

-- 2022-07-11T08:46:32.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581081,'de_DE') 
;

-- 2022-07-11T08:46:32.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TargetFieldType', Name='Zielfeldtyp', Description='Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', Help='' WHERE AD_Element_ID=581081
;

-- 2022-07-11T08:46:32.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TargetFieldType', Name='Zielfeldtyp', Description='Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', Help='', AD_Element_ID=581081 WHERE UPPER(ColumnName)='TARGETFIELDTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-11T08:46:32.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TargetFieldType', Name='Zielfeldtyp', Description='Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', Help='' WHERE AD_Element_ID=581081 AND IsCentrallyMaintained='Y'
;

-- 2022-07-11T08:46:32.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zielfeldtyp', Description='Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581081) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581081)
;

-- 2022-07-11T08:46:32.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zielfeldtyp', Name='Zielfeldtyp' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581081)
;

-- 2022-07-11T08:46:32.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zielfeldtyp', Description='Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 581081
;

-- 2022-07-11T08:46:32.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zielfeldtyp', Description='Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', Help='' WHERE AD_Element_ID = 581081
;

-- 2022-07-11T08:46:32.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zielfeldtyp', Description = 'Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581081
;

-- 2022-07-11T08:46:40.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Typ des Feldes aus der PLU-Datei an, das aktualisiert werden soll.', Name='Zielfeldtyp', PrintName='Zielfeldtyp',Updated=TO_TIMESTAMP('2022-07-11 11:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581081 AND AD_Language='nl_NL'
;

-- 2022-07-11T08:46:40.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581081,'nl_NL') 
;

-- 2022-07-11T08:46:50.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies the type of the field from the PLU file which will be updated.', Name='Target field type', PrintName='Target field type',Updated=TO_TIMESTAMP('2022-07-11 11:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581081 AND AD_Language='en_US'
;

-- 2022-07-11T08:46:50.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581081,'en_US') 
;

-- 2022-07-11T08:47:27.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', Name='Ersatz', PrintName='Ersatz',Updated=TO_TIMESTAMP('2022-07-11 11:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581083 AND AD_Language='de_CH'
;

-- 2022-07-11T08:47:27.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581083,'de_CH') 
;

-- 2022-07-11T08:47:36.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', Name='Ersatz', PrintName='Ersatz',Updated=TO_TIMESTAMP('2022-07-11 11:47:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581083 AND AD_Language='de_DE'
;

-- 2022-07-11T08:47:36.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581083,'de_DE') 
;

-- 2022-07-11T08:47:36.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581083,'de_DE') 
;

-- 2022-07-11T08:47:36.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Replacement', Name='Ersatz', Description='Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', Help=NULL WHERE AD_Element_ID=581083
;

-- 2022-07-11T08:47:36.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Replacement', Name='Ersatz', Description='Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', Help=NULL, AD_Element_ID=581083 WHERE UPPER(ColumnName)='REPLACEMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-11T08:47:36.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Replacement', Name='Ersatz', Description='Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', Help=NULL WHERE AD_Element_ID=581083 AND IsCentrallyMaintained='Y'
;

-- 2022-07-11T08:47:36.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ersatz', Description='Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581083) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581083)
;

-- 2022-07-11T08:47:36.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ersatz', Name='Ersatz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581083)
;

-- 2022-07-11T08:47:36.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ersatz', Description='Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581083
;

-- 2022-07-11T08:47:36.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ersatz', Description='Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', Help=NULL WHERE AD_Element_ID = 581083
;

-- 2022-07-11T08:47:36.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ersatz', Description = 'Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581083
;

-- 2022-07-11T08:47:44.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Ersatzwert für das Zielfeld an, das unter dem angegebenen JsonPath vom Quellobjekt identifiziert wurde.', Name='Ersatz', PrintName='Ersatz',Updated=TO_TIMESTAMP('2022-07-11 11:47:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581083 AND AD_Language='nl_NL'
;

-- 2022-07-11T08:47:44.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581083,'nl_NL') 
;

-- 2022-07-11T08:47:53.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies the replacement value for the target field identified at the given JsonPath from the source object.',Updated=TO_TIMESTAMP('2022-07-11 11:47:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581083 AND AD_Language='en_US'
;

-- 2022-07-11T08:47:53.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581083,'en_US') 
;

-- 2022-07-11T08:48:38.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', Name='Ersatzquelle', PrintName='Ersatzquelle',Updated=TO_TIMESTAMP('2022-07-11 11:48:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581089 AND AD_Language='de_CH'
;

-- 2022-07-11T08:48:38.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581089,'de_CH') 
;

-- 2022-07-11T08:48:49.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', Name='Ersatzquelle', PrintName='Ersatzquelle',Updated=TO_TIMESTAMP('2022-07-11 11:48:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581089 AND AD_Language='de_DE'
;

-- 2022-07-11T08:48:49.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581089,'de_DE') 
;

-- 2022-07-11T08:48:49.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581089,'de_DE') 
;

-- 2022-07-11T08:48:49.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ReplacementSource', Name='Ersatzquelle', Description='Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', Help=NULL WHERE AD_Element_ID=581089
;

-- 2022-07-11T08:48:49.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReplacementSource', Name='Ersatzquelle', Description='Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', Help=NULL, AD_Element_ID=581089 WHERE UPPER(ColumnName)='REPLACEMENTSOURCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-11T08:48:49.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReplacementSource', Name='Ersatzquelle', Description='Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', Help=NULL WHERE AD_Element_ID=581089 AND IsCentrallyMaintained='Y'
;

-- 2022-07-11T08:48:49.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ersatzquelle', Description='Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581089) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581089)
;

-- 2022-07-11T08:48:49.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ersatzquelle', Name='Ersatzquelle' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581089)
;

-- 2022-07-11T08:48:49.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ersatzquelle', Description='Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581089
;

-- 2022-07-11T08:48:49.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ersatzquelle', Description='Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', Help=NULL WHERE AD_Element_ID = 581089
;

-- 2022-07-11T08:48:49.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ersatzquelle', Description = 'Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581089
;

-- 2022-07-11T08:48:59.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies the source object from where the replacement value for the target field will be taken.', Name='Replacement source', PrintName='Replacement source',Updated=TO_TIMESTAMP('2022-07-11 11:48:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581089 AND AD_Language='en_US'
;

-- 2022-07-11T08:48:59.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581089,'en_US') 
;

-- 2022-07-11T08:49:13.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt das Quellobjekt an, aus dem der Ersatzwert für das Zielfeld übernommen wird.', Name='Ersatzquelle', PrintName='Ersatzquelle',Updated=TO_TIMESTAMP('2022-07-11 11:49:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581089 AND AD_Language='nl_NL'
;

-- 2022-07-11T08:49:13.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581089,'nl_NL') 
;

-- 2022-07-11T08:49:50.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Bom Line',Updated=TO_TIMESTAMP('2022-07-11 11:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543231
;

-- 2022-07-11T08:49:55.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='BOM Zeile',Updated=TO_TIMESTAMP('2022-07-11 11:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543231
;

-- 2022-07-11T08:49:58.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='BOM Zeile',Updated=TO_TIMESTAMP('2022-07-11 11:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543231
;

-- 2022-07-11T08:50:02.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='BOM Zeile',Updated=TO_TIMESTAMP('2022-07-11 11:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543231
;

-- 2022-07-11T08:50:16.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Produkt',Updated=TO_TIMESTAMP('2022-07-11 11:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543229
;

-- 2022-07-11T08:50:18.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Produkt',Updated=TO_TIMESTAMP('2022-07-11 11:50:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543229
;

-- 2022-07-11T08:50:21.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Produkt',Updated=TO_TIMESTAMP('2022-07-11 11:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543229
;

-- 2022-07-11T08:50:41.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Manufacturing Order',Updated=TO_TIMESTAMP('2022-07-11 11:50:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543230
;

-- 2022-07-11T08:50:45.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Produktionsauftrag',Updated=TO_TIMESTAMP('2022-07-11 11:50:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543230
;

-- 2022-07-11T08:50:49.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Produktionsauftrag',Updated=TO_TIMESTAMP('2022-07-11 11:50:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543230
;

-- 2022-07-11T08:50:54.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Produktionsauftrag',Updated=TO_TIMESTAMP('2022-07-11 11:50:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543230
;

-- 2022-07-11T09:00:22.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583590,701335,0,546426,TO_TIMESTAMP('2022-07-11 12:00:22','YYYY-MM-DD HH24:MI:SS'),100,'Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Regulärer Ersetzungsausdruck',TO_TIMESTAMP('2022-07-11 12:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-11T09:00:22.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701335 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-11T09:00:22.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581091) 
;

-- 2022-07-11T09:00:22.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701335
;

-- 2022-07-11T09:00:22.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(701335)
;

-- 2022-07-11T09:00:50.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701335,0,546426,610052,549450,'F',TO_TIMESTAMP('2022-07-11 12:00:50','YYYY-MM-DD HH24:MI:SS'),100,'Regulärer Ausdruck, der beim Ersetzen des Wertes aus dem übereinstimmenden Feld verwendet wird. Mit dieser Konfiguration kann ein vollständiger Austausch oder ein teilweiser Austausch vorgenommen werden. Vollständige Ersetzung e.g. bei "value" = "Test sentence.", "ReplaceRegExp" = ":*" und der neue Wert ist "TEST", nach der Ersetzung ist "value" = "TEST". Teilweise Ersetzung, e.g. wenn es einen "value" = "Test sentence." gibt, "ReplaceRegExp" = ".*(Test).*" und der neue Wert "Dummy Test" ist, ist nach der Ersetzung "value" = "Dummy test sentence."','Y','N','N','Y','N','N','N',0,'Regulärer Ersetzungsausdruck',55,0,0,TO_TIMESTAMP('2022-07-11 12:00:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-11T09:02:09.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='PLU-Datei Konfig', PrintName='PLU-Datei Konfig',Updated=TO_TIMESTAMP('2022-07-11 12:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581079 AND AD_Language='de_CH'
;

-- 2022-07-11T09:02:09.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581079,'de_CH') 
;

-- 2022-07-11T09:02:13.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='PLU-Datei Konfig', PrintName='PLU-Datei Konfig',Updated=TO_TIMESTAMP('2022-07-11 12:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581079 AND AD_Language='de_DE'
;

-- 2022-07-11T09:02:13.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581079,'de_DE') 
;

-- 2022-07-11T09:02:13.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581079,'de_DE') 
;

-- 2022-07-11T09:02:13.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LeichMehl_PluFile_Config_ID', Name='PLU-Datei Konfig', Description=NULL, Help=NULL WHERE AD_Element_ID=581079
;

-- 2022-07-11T09:02:13.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LeichMehl_PluFile_Config_ID', Name='PLU-Datei Konfig', Description=NULL, Help=NULL, AD_Element_ID=581079 WHERE UPPER(ColumnName)='LEICHMEHL_PLUFILE_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-11T09:02:13.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LeichMehl_PluFile_Config_ID', Name='PLU-Datei Konfig', Description=NULL, Help=NULL WHERE AD_Element_ID=581079 AND IsCentrallyMaintained='Y'
;

-- 2022-07-11T09:02:13.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='PLU-Datei Konfig', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581079) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581079)
;

-- 2022-07-11T09:02:13.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='PLU-Datei Konfig', Name='PLU-Datei Konfig' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581079)
;

-- 2022-07-11T09:02:13.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='PLU-Datei Konfig', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581079
;

-- 2022-07-11T09:02:13.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='PLU-Datei Konfig', Description=NULL, Help=NULL WHERE AD_Element_ID = 581079
;

-- 2022-07-11T09:02:13.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'PLU-Datei Konfig', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581079
;

-- 2022-07-11T09:02:17.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='PLU-Datei Konfig', PrintName='PLU-Datei Konfig',Updated=TO_TIMESTAMP('2022-07-11 12:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581079 AND AD_Language='nl_NL'
;

-- 2022-07-11T09:02:17.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581079,'nl_NL') 
;

-- 2022-07-11T09:02:27.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='PLU file config', PrintName='PLU file config',Updated=TO_TIMESTAMP('2022-07-11 12:02:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581079 AND AD_Language='en_US'
;

-- 2022-07-11T09:02:27.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581079,'en_US') 
;

-- 2022-07-14T09:57:35.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-14 12:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610024
;

-- 2022-07-14T09:57:35.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-07-14 12:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610052
;

