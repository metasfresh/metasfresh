-- 2021-05-05T04:38:34.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Einstellungen für API Revision',Updated=TO_TIMESTAMP('2021-05-05 07:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541635
;

-- 2021-05-05T04:58:41.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645171,0,543896,584466,545771,'F',TO_TIMESTAMP('2021-05-05 07:58:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'API Audit Config',70,0,0,TO_TIMESTAMP('2021-05-05 07:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-05T05:01:53.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='API audit config that matched the request and lead to the creation of this audit record',Updated=TO_TIMESTAMP('2021-05-05 08:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579101
;

-- 2021-05-05T05:01:53.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='API_Audit_Config_ID', Name='API Audit Config', Description='API audit config that matched the request and lead to the creation of this audit record', Help=NULL WHERE AD_Element_ID=579101
;

-- 2021-05-05T05:01:53.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Audit_Config_ID', Name='API Audit Config', Description='API audit config that matched the request and lead to the creation of this audit record', Help=NULL, AD_Element_ID=579101 WHERE UPPER(ColumnName)='API_AUDIT_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-05T05:01:53.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Audit_Config_ID', Name='API Audit Config', Description='API audit config that matched the request and lead to the creation of this audit record', Help=NULL WHERE AD_Element_ID=579101 AND IsCentrallyMaintained='Y'
;

-- 2021-05-05T05:01:53.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='API Audit Config', Description='API audit config that matched the request and lead to the creation of this audit record', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579101) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579101)
;

-- 2021-05-05T05:01:53.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='API Audit Config', Description='API audit config that matched the request and lead to the creation of this audit record', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579101
;

-- 2021-05-05T05:01:53.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='API Audit Config', Description='API audit config that matched the request and lead to the creation of this audit record', Help=NULL WHERE AD_Element_ID = 579101
;

-- 2021-05-05T05:01:53.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'API Audit Config', Description = 'API audit config that matched the request and lead to the creation of this audit record', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579101
;

-- 2021-05-05T05:02:14.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='API audit config that matched the request and lead to the creation of this audit record',Updated=TO_TIMESTAMP('2021-05-05 08:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579101 AND AD_Language='en_US'
;

-- 2021-05-05T05:02:14.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579101,'en_US') 
;

-- 2021-05-05T05:02:19.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='API audit config that matched the request and lead to the creation of this audit record',Updated=TO_TIMESTAMP('2021-05-05 08:02:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579101 AND AD_Language='nl_NL'
;

-- 2021-05-05T05:02:19.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579101,'nl_NL') 
;

-- 2021-05-05T05:02:36.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Einstellung, die zu dem Request gematcht wurde und zu diesem Revision-Datensatz geführt hat', Name='API Revision Einstellung', PrintName='API Revision Einstellung',Updated=TO_TIMESTAMP('2021-05-05 08:02:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579101 AND AD_Language='de_DE'
;

-- 2021-05-05T05:02:36.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579101,'de_DE') 
;

-- 2021-05-05T05:02:36.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579101,'de_DE') 
;

-- 2021-05-05T05:02:36.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='API_Audit_Config_ID', Name='API Revision Einstellung', Description='Einstellung, die zu dem Request gematcht wurde und zu diesem Revision-Datensatz geführt hat', Help=NULL WHERE AD_Element_ID=579101
;

-- 2021-05-05T05:02:36.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Audit_Config_ID', Name='API Revision Einstellung', Description='Einstellung, die zu dem Request gematcht wurde und zu diesem Revision-Datensatz geführt hat', Help=NULL, AD_Element_ID=579101 WHERE UPPER(ColumnName)='API_AUDIT_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-05T05:02:36.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Audit_Config_ID', Name='API Revision Einstellung', Description='Einstellung, die zu dem Request gematcht wurde und zu diesem Revision-Datensatz geführt hat', Help=NULL WHERE AD_Element_ID=579101 AND IsCentrallyMaintained='Y'
;

-- 2021-05-05T05:02:36.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='API Revision Einstellung', Description='Einstellung, die zu dem Request gematcht wurde und zu diesem Revision-Datensatz geführt hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579101) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579101)
;

-- 2021-05-05T05:02:36.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='API Revision Einstellung', Name='API Revision Einstellung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579101)
;

-- 2021-05-05T05:02:36.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='API Revision Einstellung', Description='Einstellung, die zu dem Request gematcht wurde und zu diesem Revision-Datensatz geführt hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579101
;

-- 2021-05-05T05:02:36.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='API Revision Einstellung', Description='Einstellung, die zu dem Request gematcht wurde und zu diesem Revision-Datensatz geführt hat', Help=NULL WHERE AD_Element_ID = 579101
;

-- 2021-05-05T05:02:36.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'API Revision Einstellung', Description = 'Einstellung, die zu dem Request gematcht wurde und zu diesem Revision-Datensatz geführt hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579101
;

-- 2021-05-05T05:02:47.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Einstellung, die zu dem Request gematcht wurde und zu diesem Revision-Datensatz geführt hat', Name='API Revision Einstellung', PrintName='API Revision Einstellung',Updated=TO_TIMESTAMP('2021-05-05 08:02:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579101 AND AD_Language='de_CH'
;

-- 2021-05-05T05:02:47.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579101,'de_CH') 
;

-- 2021-05-05T05:04:04.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='API Aufruf Revision',Updated=TO_TIMESTAMP('2021-05-05 08:04:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541636
;

-- 2021-05-05T05:04:10.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='API Aufruf Revision',Updated=TO_TIMESTAMP('2021-05-05 08:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541636
;

-- 2021-05-05T05:05:22.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-05 08:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573744
;

-- 2021-05-05T05:06:48.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Einstellungen für API Revision',Updated=TO_TIMESTAMP('2021-05-05 08:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541635
;

-- 2021-05-05T05:10:53.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_request_audit','API_Audit_Config_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T05:11:06.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_request_audit','API_Request_Audit_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T05:16:11.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584466
;

-- 2021-05-05T05:16:11.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584438
;

-- 2021-05-05T05:16:11.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584439
;

-- 2021-05-05T05:16:11.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584440
;

-- 2021-05-05T05:16:11.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584441
;

-- 2021-05-05T05:16:11.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584442
;

-- 2021-05-05T05:16:11.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584443
;

-- 2021-05-05T05:16:11.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584444
;

-- 2021-05-05T05:16:11.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584445
;

-- 2021-05-05T05:16:11.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584446
;

-- 2021-05-05T05:16:11.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2021-05-05 08:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584447
;

-- 2021-05-05T05:18:02.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2021-05-05 08:18:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584466
;

-- 2021-05-05T05:18:18.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-05-05 08:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584466
;

-- 2021-05-05T05:18:21.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2021-05-05 08:18:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584438
;

-- 2021-05-05T05:18:24.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2021-05-05 08:18:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584439
;

-- 2021-05-05T05:18:27.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2021-05-05 08:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584440
;

-- 2021-05-05T05:18:29.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2021-05-05 08:18:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584441
;

-- 2021-05-05T05:18:39.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2021-05-05 08:18:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584442
;

