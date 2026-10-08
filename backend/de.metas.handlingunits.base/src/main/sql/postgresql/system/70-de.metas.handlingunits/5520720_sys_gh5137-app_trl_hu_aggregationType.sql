-- 2019-05-02T13:30:53.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET IsActive='N', Description='Please don''t use this UOM (anymore). Instead, please use the "Hour" UOM with ID=101',Updated=TO_TIMESTAMP('2019-05-02 13:30:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540024
;

-- 2019-05-02T15:31:24.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='HU Aggregation', IsTranslated='Y', Name='HU Aggregation', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.',Updated=TO_TIMESTAMP('2019-05-02 15:31:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=576580
;

-- 2019-05-02T15:31:25.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576580,'de_CH') 
;

-- 2019-05-02T15:31:34.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.',Updated=TO_TIMESTAMP('2019-05-02 15:31:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=576580
;

-- 2019-05-02T15:31:34.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576580,'de_DE') 
;

-- 2019-05-02T15:31:34.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576580,'de_DE') 
;

-- 2019-05-02T15:31:34.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HUAggregationType', Name='HU aggregation type', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL WHERE AD_Element_ID=576580
;

-- 2019-05-02T15:31:34.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HUAggregationType', Name='HU aggregation type', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL, AD_Element_ID=576580 WHERE UPPER(ColumnName)='HUAGGREGATIONTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-02T15:31:34.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HUAggregationType', Name='HU aggregation type', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL WHERE AD_Element_ID=576580 AND IsCentrallyMaintained='Y'
;

-- 2019-05-02T15:31:34.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='HU aggregation type', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576580) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576580)
;

-- 2019-05-02T15:31:34.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='HU aggregation type', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576580
;

-- 2019-05-02T15:31:34.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='HU aggregation type', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL WHERE AD_Element_ID = 576580
;

-- 2019-05-02T15:31:34.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'HU aggregation type', Description = 'Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576580
;

-- 2019-05-02T15:31:40.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='HU Aggregation', Name='HU Aggregation',Updated=TO_TIMESTAMP('2019-05-02 15:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=576580
;

-- 2019-05-02T15:31:40.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576580,'de_DE') 
;

-- 2019-05-02T15:31:40.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576580,'de_DE') 
;

-- 2019-05-02T15:31:40.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HUAggregationType', Name='HU Aggregation', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL WHERE AD_Element_ID=576580
;

-- 2019-05-02T15:31:40.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HUAggregationType', Name='HU Aggregation', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL, AD_Element_ID=576580 WHERE UPPER(ColumnName)='HUAGGREGATIONTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-02T15:31:40.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HUAggregationType', Name='HU Aggregation', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL WHERE AD_Element_ID=576580 AND IsCentrallyMaintained='Y'
;

-- 2019-05-02T15:31:40.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='HU Aggregation', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576580) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576580)
;

-- 2019-05-02T15:31:40.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='HU Aggregation', Name='HU Aggregation' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576580)
;

-- 2019-05-02T15:31:40.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='HU Aggregation', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576580
;

-- 2019-05-02T15:31:40.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='HU Aggregation', Description='Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', Help=NULL WHERE AD_Element_ID = 576580
;

-- 2019-05-02T15:31:40.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'HU Aggregation', Description = 'Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576580
;

-- 2019-05-02T15:32:08.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='HU aggregation', IsTranslated='Y', Name='HU aggregation', Description='Specifies whether the respective line is about one HU or about potientielly many HUs.',Updated=TO_TIMESTAMP('2019-05-02 15:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576580
;

-- 2019-05-02T15:32:08.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576580,'en_US') 
;

-- 2019-05-02T15:33:07.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Viele HUs', Description='Der Inventurzeile können mehrere HUs zugeordnet sein',Updated=TO_TIMESTAMP('2019-05-02 15:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541943 AND AD_Language='de_CH'
;

-- 2019-05-02T15:33:21.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='viele HUs', Description='Der Inventurzeile können mehrere HUs zugeordnet sein',Updated=TO_TIMESTAMP('2019-05-02 15:33:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541943 AND AD_Language='de_DE'
;

-- 2019-05-02T15:33:27.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Viele HUs',Updated=TO_TIMESTAMP('2019-05-02 15:33:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541943 AND AD_Language='de_DE'
;

-- 2019-05-02T15:33:35.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='The inventory line can have multiple HUs',Updated=TO_TIMESTAMP('2019-05-02 15:33:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541943 AND AD_Language='en_US'
;

-- 2019-05-02T15:33:38.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-05-02 15:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541943 AND AD_Language='en_US'
;

