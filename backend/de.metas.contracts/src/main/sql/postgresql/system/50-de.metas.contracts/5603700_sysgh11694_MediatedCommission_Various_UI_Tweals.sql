-- 2021-09-07T14:30:26.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Bestellvermittlung',Updated=TO_TIMESTAMP('2021-09-07 17:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542678
;

-- 2021-09-07T14:43:16.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-09-07 17:43:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586816
;

-- 2021-09-07T14:44:58.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-09-07 17:44:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=569942
;

-- 2021-09-07T14:45:01.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-09-07 17:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=569942
;

-- 2021-09-07T15:02:23.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=579388, Description=NULL, Help=NULL, Name='Provisionsgeber',Updated=TO_TIMESTAMP('2021-09-07 18:02:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=658066
;

-- 2021-09-07T15:02:23.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579388) 
;

-- 2021-09-07T15:02:23.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=658066
;

-- 2021-09-07T15:02:23.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(658066)
;

-- 2021-09-07T18:01:05.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Brokerage commission settings', PrintName='Brokerage commission settings',Updated=TO_TIMESTAMP('2021-09-07 21:01:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579623 AND AD_Language='en_US'
;

-- 2021-09-07T18:01:05.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579623,'en_US')
;

-- 2021-09-07T18:01:15.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einstellungen für Vermittlungsprovision', PrintName='Einstellungen für Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-09-07 21:01:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579623 AND AD_Language='nl_NL'
;

-- 2021-09-07T18:01:15.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579623,'nl_NL')
;

-- 2021-09-07T18:01:20.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einstellungen für Vermittlungsprovision', PrintName='Einstellungen für Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-09-07 21:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579623 AND AD_Language='de_DE'
;

-- 2021-09-07T18:01:20.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579623,'de_DE')
;

-- 2021-09-07T18:01:20.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579623,'de_DE')
;

-- 2021-09-07T18:01:20.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_MediatedCommissionSettings_ID', Name='Einstellungen für Vermittlungsprovision', Description=NULL, Help=NULL WHERE AD_Element_ID=579623
;

-- 2021-09-07T18:01:20.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_MediatedCommissionSettings_ID', Name='Einstellungen für Vermittlungsprovision', Description=NULL, Help=NULL, AD_Element_ID=579623 WHERE UPPER(ColumnName)='C_MEDIATEDCOMMISSIONSETTINGS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-07T18:01:20.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_MediatedCommissionSettings_ID', Name='Einstellungen für Vermittlungsprovision', Description=NULL, Help=NULL WHERE AD_Element_ID=579623 AND IsCentrallyMaintained='Y'
;

-- 2021-09-07T18:01:20.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einstellungen für Vermittlungsprovision', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579623) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579623)
;

-- 2021-09-07T18:01:20.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Einstellungen für Vermittlungsprovision', Name='Einstellungen für Vermittlungsprovision' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579623)
;

-- 2021-09-07T18:01:20.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Einstellungen für Vermittlungsprovision', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579623
;

-- 2021-09-07T18:01:20.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Einstellungen für Vermittlungsprovision', Description=NULL, Help=NULL WHERE AD_Element_ID = 579623
;

-- 2021-09-07T18:01:20.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Einstellungen für Vermittlungsprovision', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579623
;

-- 2021-09-07T18:01:23.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einstellungen für Vermittlungsprovision', PrintName='Einstellungen für Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-09-07 21:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579623 AND AD_Language='de_CH'
;

-- 2021-09-07T18:01:23.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579623,'de_CH')
;

-- 2021-09-07T18:02:35.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=579623, Description=NULL, Help=NULL, Name='Einstellungen für Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-09-07 21:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541218
;

-- 2021-09-07T18:02:35.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Einstellungen für Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-09-07 21:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541754
;

-- 2021-09-07T18:02:35.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(579623)
;

-- 2021-09-07T18:02:35.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541218
;

-- 2021-09-07T18:02:35.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(541218)
;

-- 2021-09-07T18:03:47.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Brokerage commission detail', PrintName='Brokerage commission detail',Updated=TO_TIMESTAMP('2021-09-07 21:03:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579664 AND AD_Language='de_CH'
;

-- 2021-09-07T18:03:47.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579664,'de_CH')
;

-- 2021-09-07T18:03:51.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Brokerage commission detail', PrintName='Brokerage commission detail',Updated=TO_TIMESTAMP('2021-09-07 21:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579664 AND AD_Language='en_US'
;

-- 2021-09-07T18:03:51.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579664,'en_US')
;

-- 2021-09-07T18:04:00.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vermittlungsprovisionsdetail', PrintName='Vermittlungsprovisionsdetail',Updated=TO_TIMESTAMP('2021-09-07 21:04:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579664 AND AD_Language='nl_NL'
;

-- 2021-09-07T18:04:00.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579664,'nl_NL')
;

-- 2021-09-07T18:04:05.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vermittlungsprovisionsdetail', PrintName='Vermittlungsprovisionsdetail',Updated=TO_TIMESTAMP('2021-09-07 21:04:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579664 AND AD_Language='de_DE'
;

-- 2021-09-07T18:04:05.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579664,'de_DE')
;

-- 2021-09-07T18:04:05.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579664,'de_DE')
;

-- 2021-09-07T18:04:05.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_MediatedCommissionSettingsLine_ID', Name='Vermittlungsprovisionsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=579664
;

-- 2021-09-07T18:04:05.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_MediatedCommissionSettingsLine_ID', Name='Vermittlungsprovisionsdetail', Description=NULL, Help=NULL, AD_Element_ID=579664 WHERE UPPER(ColumnName)='C_MEDIATEDCOMMISSIONSETTINGSLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-07T18:04:05.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_MediatedCommissionSettingsLine_ID', Name='Vermittlungsprovisionsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=579664 AND IsCentrallyMaintained='Y'
;

-- 2021-09-07T18:04:05.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vermittlungsprovisionsdetail', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579664) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579664)
;

-- 2021-09-07T18:04:05.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vermittlungsprovisionsdetail', Name='Vermittlungsprovisionsdetail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579664)
;

-- 2021-09-07T18:04:05.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vermittlungsprovisionsdetail', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579664
;

-- 2021-09-07T18:04:05.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vermittlungsprovisionsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID = 579664
;

-- 2021-09-07T18:04:05.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vermittlungsprovisionsdetail', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579664
;

-- 2021-09-07T18:04:09.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vermittlungsprovisionsdetail', PrintName='Vermittlungsprovisionsdetail',Updated=TO_TIMESTAMP('2021-09-07 21:04:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579664 AND AD_Language='de_CH'
;

-- 2021-09-07T18:04:09.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579664,'de_CH')
;

-- 2021-09-07T18:04:25.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=579664, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Vermittlungsprovisionsdetail',Updated=TO_TIMESTAMP('2021-09-07 21:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544418
;

-- 2021-09-07T18:04:25.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(579664)
;

-- 2021-09-07T18:04:25.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544418)
;

-- 2021-09-07T18:06:12.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sales commission detail', PrintName='Sales commission detail',Updated=TO_TIMESTAMP('2021-09-07 21:06:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577241 AND AD_Language='en_US'
;

-- 2021-09-07T18:06:12.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577241,'en_US')
;

-- 2021-09-07T18:06:22.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verkaufsprovisionsdetail', PrintName='Verkaufsprovisionsdetail',Updated=TO_TIMESTAMP('2021-09-07 21:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577241 AND AD_Language='nl_NL'
;

-- 2021-09-07T18:06:22.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577241,'nl_NL')
;

-- 2021-09-07T18:06:26.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verkaufsprovisionsdetail', PrintName='Verkaufsprovisionsdetail',Updated=TO_TIMESTAMP('2021-09-07 21:06:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577241 AND AD_Language='de_DE'
;

-- 2021-09-07T18:06:26.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577241,'de_DE')
;

-- 2021-09-07T18:06:26.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577241,'de_DE')
;

-- 2021-09-07T18:06:26.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_CommissionSettingsLine_ID', Name='Verkaufsprovisionsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=577241
;

-- 2021-09-07T18:06:26.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CommissionSettingsLine_ID', Name='Verkaufsprovisionsdetail', Description=NULL, Help=NULL, AD_Element_ID=577241 WHERE UPPER(ColumnName)='C_COMMISSIONSETTINGSLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-07T18:06:26.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CommissionSettingsLine_ID', Name='Verkaufsprovisionsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=577241 AND IsCentrallyMaintained='Y'
;

-- 2021-09-07T18:06:26.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verkaufsprovisionsdetail', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577241) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577241)
;

-- 2021-09-07T18:06:26.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verkaufsprovisionsdetail', Name='Verkaufsprovisionsdetail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577241)
;

-- 2021-09-07T18:06:26.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verkaufsprovisionsdetail', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577241
;

-- 2021-09-07T18:06:26.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verkaufsprovisionsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID = 577241
;

-- 2021-09-07T18:06:26.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verkaufsprovisionsdetail', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577241
;

-- 2021-09-07T18:06:29.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verkaufsprovisionsdetail', PrintName='Verkaufsprovisionsdetail',Updated=TO_TIMESTAMP('2021-09-07 21:06:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577241 AND AD_Language='de_CH'
;

-- 2021-09-07T18:06:29.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577241,'de_CH')
;

-- 2021-09-07T18:08:15.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,576054,658108,0,541943,0,TO_TIMESTAMP('2021-09-07 21:08:15','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.contracts.commission',0,'Y','Y','Y','N','N','N','N','N','Vermittlungsprovisionsdetail',0,50,0,1,1,TO_TIMESTAMP('2021-09-07 21:08:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-07T18:08:15.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=658108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-07T18:08:15.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579664)
;

-- 2021-09-07T18:08:15.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=658108
;

-- 2021-09-07T18:08:15.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(658108)
;

-- 2021-09-07T18:10:46.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_CommissionSettingsLine_ID/-1@>0',Updated=TO_TIMESTAMP('2021-09-07 21:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=598486
;

-- 2021-09-07T18:11:18.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_MediatedCommissionSettingsLine_ID/-1@>0',Updated=TO_TIMESTAMP('2021-09-07 21:11:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=658108
;

-- 2021-09-07T18:22:16.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_MediatedCommissionSettingsLine_ID/0@!0',Updated=TO_TIMESTAMP('2021-09-07 21:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=658108
;

-- 2021-09-07T18:23:52.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_CommissionSettingsLine_ID/0@!0',Updated=TO_TIMESTAMP('2021-09-07 21:23:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=598486
;


-- 2021-09-07T18:28:01.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,658108,0,541943,590466,542899,'F',TO_TIMESTAMP('2021-09-07 21:28:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vermittlungsprovisionsdetail',80,0,0,TO_TIMESTAMP('2021-09-07 21:28:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-07T18:28:18.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=55,Updated=TO_TIMESTAMP('2021-09-07 21:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=590466
;


