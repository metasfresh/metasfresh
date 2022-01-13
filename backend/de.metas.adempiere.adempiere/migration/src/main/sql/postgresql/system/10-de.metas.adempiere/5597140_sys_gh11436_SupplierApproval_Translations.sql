-- 2021-07-07T08:58:48.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe', PrintName='Lieferantenfreigabe',Updated=TO_TIMESTAMP('2021-07-07 11:58:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579449 AND AD_Language='de_CH'
;

-- 2021-07-07T08:58:48.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579449,'de_CH') 
;

-- 2021-07-07T08:58:52.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe', PrintName='Lieferantenfreigabe',Updated=TO_TIMESTAMP('2021-07-07 11:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579449 AND AD_Language='de_DE'
;

-- 2021-07-07T08:58:52.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579449,'de_DE') 
;

-- 2021-07-07T08:58:52.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579449,'de_DE') 
;

-- 2021-07-07T08:58:52.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BP_SupplierApproval_ID', Name='Lieferantenfreigabe', Description=NULL, Help=NULL WHERE AD_Element_ID=579449
;

-- 2021-07-07T08:58:52.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_SupplierApproval_ID', Name='Lieferantenfreigabe', Description=NULL, Help=NULL, AD_Element_ID=579449 WHERE UPPER(ColumnName)='C_BP_SUPPLIERAPPROVAL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-07T08:58:52.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_SupplierApproval_ID', Name='Lieferantenfreigabe', Description=NULL, Help=NULL WHERE AD_Element_ID=579449 AND IsCentrallyMaintained='Y'
;

-- 2021-07-07T08:58:52.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferantenfreigabe', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579449) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579449)
;

-- 2021-07-07T08:58:52.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferantenfreigabe', Name='Lieferantenfreigabe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579449)
;

-- 2021-07-07T08:58:52.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferantenfreigabe', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579449
;

-- 2021-07-07T08:58:52.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferantenfreigabe', Description=NULL, Help=NULL WHERE AD_Element_ID = 579449
;

-- 2021-07-07T08:58:52.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferantenfreigabe', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579449
;

-- 2021-07-07T08:59:51.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe', PrintName='Lieferantenfreigabe',Updated=TO_TIMESTAMP('2021-07-07 11:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579452 AND AD_Language='de_CH'
;

-- 2021-07-07T08:59:51.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579452,'de_CH') 
;

-- 2021-07-07T09:00:01.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe', PrintName='Lieferantenfreigabe',Updated=TO_TIMESTAMP('2021-07-07 12:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579452 AND AD_Language='de_DE'
;

-- 2021-07-07T09:00:01.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579452,'de_DE') 
;

-- 2021-07-07T09:00:01.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579452,'de_DE') 
;

-- 2021-07-07T09:00:01.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SupplierApproval', Name='Lieferantenfreigabe', Description=NULL, Help=NULL WHERE AD_Element_ID=579452
;

-- 2021-07-07T09:00:01.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval', Name='Lieferantenfreigabe', Description=NULL, Help=NULL, AD_Element_ID=579452 WHERE UPPER(ColumnName)='SUPPLIERAPPROVAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-07T09:00:01.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval', Name='Lieferantenfreigabe', Description=NULL, Help=NULL WHERE AD_Element_ID=579452 AND IsCentrallyMaintained='Y'
;

-- 2021-07-07T09:00:01.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferantenfreigabe', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579452) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579452)
;

-- 2021-07-07T09:00:01.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferantenfreigabe', Name='Lieferantenfreigabe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579452)
;

-- 2021-07-07T09:00:01.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferantenfreigabe', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579452
;

-- 2021-07-07T09:00:01.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferantenfreigabe', Description=NULL, Help=NULL WHERE AD_Element_ID = 579452
;

-- 2021-07-07T09:00:01.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferantenfreigabe', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579452
;











-- 2021-07-07T10:37:56.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The partner {0} doesn''t have a supplier approval for the norm {1}.',Updated=TO_TIMESTAMP('2021-07-07 13:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545046
;

-- 2021-07-07T10:41:24.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The partner {0} doesn''t have a supplier approval for the norm {1}.',Updated=TO_TIMESTAMP('2021-07-07 13:41:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545046
;

-- 2021-07-07T10:41:28.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The partner {0} doesn''t have a supplier approval for the norm {1}.',Updated=TO_TIMESTAMP('2021-07-07 13:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545046
;

-- 2021-07-07T11:14:41.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2021-07-07 14:14:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545046
;

-- 2021-07-07T11:14:56.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The partner {0} doesn''t have a supplier approval for the norm  {1}.',Updated=TO_TIMESTAMP('2021-07-07 14:14:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545046
;

-- 2021-07-07T12:49:03.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The partner {0} doesn''t have a supplier approval for the norm {1}.',Updated=TO_TIMESTAMP('2021-07-07 15:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545046
;

-- 2021-07-07T12:49:12.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-07 15:49:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545046
;

-- 2021-07-07T12:49:19.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-07 15:49:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545046
;

-- 2021-07-07T12:51:59.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The partner {0} does not have a supplier approval for the norm {1}.',Updated=TO_TIMESTAMP('2021-07-07 15:51:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545046
;

-- 2021-07-07T12:52:08.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The partner {0} does not have a supplier approval for the norm {1}.',Updated=TO_TIMESTAMP('2021-07-07 15:52:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545046
;

-- 2021-07-07T12:52:14.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The partner {0} does not have a supplier approval for the norm {1}.',Updated=TO_TIMESTAMP('2021-07-07 15:52:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545046
;
















-- 2021-07-07T13:31:26.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Geschäftspartner {0} hat keine Lieferantenfreigabe für die Norm {1}.',Updated=TO_TIMESTAMP('2021-07-07 16:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545046
;

-- 2021-07-07T13:31:29.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Geschäftspartner {0} hat keine Lieferantenfreigabe für die Norm {1}.',Updated=TO_TIMESTAMP('2021-07-07 16:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545046
;

-- 2021-07-07T13:31:45.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The partner {0} does not have a supplier approval for the norm {1}',Updated=TO_TIMESTAMP('2021-07-07 16:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545046
;

-- 2021-07-07T13:31:53.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The partner {0} does not have a supplier approval for the norm {1}',Updated=TO_TIMESTAMP('2021-07-07 16:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545046
;

-- 2021-07-07T13:32:53.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The supplier approval of the business partner {0} for the norm {1} has the expiry date {2}.',Updated=TO_TIMESTAMP('2021-07-07 16:32:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545045
;

-- 2021-07-07T13:33:05.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Lieferantenfreigabe des Geschäftspartners {0} für die Norm {1} hat das Ablaufdatum {2}.',Updated=TO_TIMESTAMP('2021-07-07 16:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545045
;

-- 2021-07-07T13:33:11.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Lieferantenfreigabe des Geschäftspartners {0} für die Norm {1} hat das Ablaufdatum {2}.',Updated=TO_TIMESTAMP('2021-07-07 16:33:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545045
;

-- 2021-07-07T13:33:18.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The supplier approval of the business partner {0} for the norm {1} has the expiry date {2}.',Updated=TO_TIMESTAMP('2021-07-07 16:33:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545045
;

-- 2021-07-07T13:33:34.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The supplier approval of the business partner {0} for the norm {1} has the expiry date {2}.',Updated=TO_TIMESTAMP('2021-07-07 16:33:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545045
;

-- 2021-07-07T13:35:07.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Lieferantenfreigabe ändern',Updated=TO_TIMESTAMP('2021-07-07 16:35:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584855
;

-- 2021-07-07T13:35:10.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Lieferantenfreigabe ändern',Updated=TO_TIMESTAMP('2021-07-07 16:35:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584855
;

-- 2021-07-07T13:35:10.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Lieferantenfreigabe ändern',Updated=TO_TIMESTAMP('2021-07-07 16:35:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584855
;

-- 2021-07-07T13:36:30.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='You can use this process to update the duration and date of a business partner''s supplier approval.',Updated=TO_TIMESTAMP('2021-07-07 16:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584855
;

-- 2021-07-07T13:36:49.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Mit diesem Prozess können Dauer und Datum der Lieferantenfreigabe eines Geschäftspartners aktualisiert werden.',Updated=TO_TIMESTAMP('2021-07-07 16:36:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584855
;

-- 2021-07-07T13:36:52.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Mit diesem Prozess können Dauer und Datum der Lieferantenfreigabe eines Geschäftspartners aktualisiert werden.', Help=NULL, Name='Lieferantenfreigabe ändern',Updated=TO_TIMESTAMP('2021-07-07 16:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584855
;

-- 2021-07-07T13:36:52.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Mit diesem Prozess können Dauer und Datum der Lieferantenfreigabe eines Geschäftspartners aktualisiert werden.',Updated=TO_TIMESTAMP('2021-07-07 16:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584855
;

-- 2021-07-07T13:37:11.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='You can use this process to update the duration and date of a business partner''s supplier approval.',Updated=TO_TIMESTAMP('2021-07-07 16:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584855
;

-- 2021-07-07T13:37:23.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Help='Mit diesem Prozess können Dauer und Datum der Lieferantenfreigabe eines Geschäftspartners aktualisiert werden.',Updated=TO_TIMESTAMP('2021-07-07 16:37:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584855
;

-- 2021-07-07T13:37:29.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Mit diesem Prozess können Dauer und Datum der Lieferantenfreigabe eines Geschäftspartners aktualisiert werden.',Updated=TO_TIMESTAMP('2021-07-07 16:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584855
;

-- 2021-07-07T13:37:32.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Mit diesem Prozess können Dauer und Datum der Lieferantenfreigabe eines Geschäftspartners aktualisiert werden.',Updated=TO_TIMESTAMP('2021-07-07 16:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584855
;

-- 2021-07-07T13:37:36.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='You can use this process to update the duration and date of a business partner''s supplier approval.',Updated=TO_TIMESTAMP('2021-07-07 16:37:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584855
;

-- 2021-07-07T13:38:18.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe Art', PrintName='Lieferantenfreigabe Art',Updated=TO_TIMESTAMP('2021-07-07 16:38:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579450 AND AD_Language='de_CH'
;

-- 2021-07-07T13:38:18.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579450,'de_CH') 
;

-- 2021-07-07T13:38:22.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferantenfreigabe Art', PrintName='Lieferantenfreigabe Art',Updated=TO_TIMESTAMP('2021-07-07 16:38:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579450 AND AD_Language='de_DE'
;

-- 2021-07-07T13:38:22.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579450,'de_DE') 
;

-- 2021-07-07T13:38:22.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579450,'de_DE') 
;

-- 2021-07-07T13:38:22.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SupplierApproval_Type', Name='Lieferantenfreigabe Art', Description=NULL, Help=NULL WHERE AD_Element_ID=579450
;

-- 2021-07-07T13:38:22.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval_Type', Name='Lieferantenfreigabe Art', Description=NULL, Help=NULL, AD_Element_ID=579450 WHERE UPPER(ColumnName)='SUPPLIERAPPROVAL_TYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-07T13:38:22.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval_Type', Name='Lieferantenfreigabe Art', Description=NULL, Help=NULL WHERE AD_Element_ID=579450 AND IsCentrallyMaintained='Y'
;

-- 2021-07-07T13:38:22.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferantenfreigabe Art', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579450) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579450)
;

-- 2021-07-07T13:38:22.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferantenfreigabe Art', Name='Lieferantenfreigabe Art' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579450)
;

-- 2021-07-07T13:38:22.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferantenfreigabe Art', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579450
;

-- 2021-07-07T13:38:22.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferantenfreigabe Art', Description=NULL, Help=NULL WHERE AD_Element_ID = 579450
;

-- 2021-07-07T13:38:22.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferantenfreigabe Art', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579450
;

-- 2021-07-07T13:40:10.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='Lieferantenfreigabe Norm',Updated=TO_TIMESTAMP('2021-07-07 16:40:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=546184
;

-- 2021-07-07T13:41:04.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Lieferantenfreigabe Norm',Updated=TO_TIMESTAMP('2021-07-07 16:41:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586998
;













-- 2021-07-09T08:34:50.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Approved for', PrintName='Approved for',Updated=TO_TIMESTAMP('2021-07-09 11:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579450 AND AD_Language='en_US'
;

-- 2021-07-09T08:34:50.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579450,'en_US') 
;

-- 2021-07-09T08:35:00.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Freigabe für', PrintName='Freigabe für',Updated=TO_TIMESTAMP('2021-07-09 11:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579450 AND AD_Language='de_DE'
;

-- 2021-07-09T08:35:00.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579450,'de_DE') 
;

-- 2021-07-09T08:35:00.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579450,'de_DE') 
;

-- 2021-07-09T08:35:00.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SupplierApproval_Type', Name='Freigabe für', Description=NULL, Help=NULL WHERE AD_Element_ID=579450
;

-- 2021-07-09T08:35:00.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval_Type', Name='Freigabe für', Description=NULL, Help=NULL, AD_Element_ID=579450 WHERE UPPER(ColumnName)='SUPPLIERAPPROVAL_TYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-09T08:35:00.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval_Type', Name='Freigabe für', Description=NULL, Help=NULL WHERE AD_Element_ID=579450 AND IsCentrallyMaintained='Y'
;

-- 2021-07-09T08:35:00.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Freigabe für', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579450) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579450)
;

-- 2021-07-09T08:35:01.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Freigabe für', Name='Freigabe für' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579450)
;

-- 2021-07-09T08:35:01.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Freigabe für', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579450
;

-- 2021-07-09T08:35:01.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Freigabe für', Description=NULL, Help=NULL WHERE AD_Element_ID = 579450
;

-- 2021-07-09T08:35:01.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Freigabe für', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579450
;

-- 2021-07-09T08:35:05.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Freigabe für', PrintName='Freigabe für',Updated=TO_TIMESTAMP('2021-07-09 11:35:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579450 AND AD_Language='de_CH'
;

-- 2021-07-09T08:35:05.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579450,'de_CH') 
;

-- 2021-07-09T08:46:52.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Products with the relevant standard may be purchased from the business partner.', IsActive='Y', Name='Sales',Updated=TO_TIMESTAMP('2021-07-09 11:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542699
;

-- 2021-07-09T08:47:05.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Products with the relevant standard may be sold to the business partner.',Updated=TO_TIMESTAMP('2021-07-09 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542699
;

-- 2021-07-09T08:47:25.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Produkte mit der betreffenden Norm dürfen an den Geschäftspartner verkauft werden.',Updated=TO_TIMESTAMP('2021-07-09 11:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542699
;

-- 2021-07-09T08:47:44.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Produkte mit der betreffenden Norm dürfen an den Geschäftspartner verkauft werden.', Name='Verkauf',Updated=TO_TIMESTAMP('2021-07-09 11:47:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542699
;

-- 2021-07-09T08:48:01.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Verkauf',Updated=TO_TIMESTAMP('2021-07-09 11:48:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542699
;

-- 2021-07-09T08:48:15.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Products with the relevant standard may be sold to the business partner.', Name='Sales',Updated=TO_TIMESTAMP('2021-07-09 11:48:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542699
;

-- 2021-07-09T08:48:22.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2021-07-09 11:48:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542699
;

-- 2021-07-09T08:48:58.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Products with the relevant standard may be purchased from the business partner.', Name='Purchase',Updated=TO_TIMESTAMP('2021-07-09 11:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542700
;

-- 2021-07-09T08:49:27.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Products with the relevant standard may be purchased from the business partner.', Name='Purchase',Updated=TO_TIMESTAMP('2021-07-09 11:49:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542700
;

-- 2021-07-09T08:49:38.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Produkte mit der betreffenden Norm dürfen bei dem Geschäftspartner eingekauft werden.',Updated=TO_TIMESTAMP('2021-07-09 11:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542700
;

-- 2021-07-09T08:49:52.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Produkte mit der betreffenden Norm dürfen bei dem Geschäftspartner eingekauft werden.', Name='Einkauf',Updated=TO_TIMESTAMP('2021-07-09 11:49:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542700
;

-- 2021-07-09T08:49:59.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Einkauf',Updated=TO_TIMESTAMP('2021-07-09 11:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542700
;

-- 2021-07-09T08:50:02.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-09 11:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542700
;

-- 2021-07-09T08:50:05.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-09 11:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542700
;

-- 2021-07-09T08:50:28.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bp_supplierapproval','SupplierApproval_Type','VARCHAR(25)',null,'V')
;

-- 2021-07-09T09:05:21.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Freigabe Dauer', PrintName='Freigabe Dauer',Updated=TO_TIMESTAMP('2021-07-09 12:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579460 AND AD_Language='de_CH'
;

-- 2021-07-09T09:05:21.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579460,'de_CH') 
;

-- 2021-07-09T09:05:30.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Freigabe Dauer', PrintName='Freigabe Dauer',Updated=TO_TIMESTAMP('2021-07-09 12:05:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579460 AND AD_Language='de_DE'
;

-- 2021-07-09T09:05:30.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579460,'de_DE') 
;

-- 2021-07-09T09:05:30.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579460,'de_DE') 
;

-- 2021-07-09T09:05:30.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SupplierApproval_Parameter', Name='Freigabe Dauer', Description=NULL, Help=NULL WHERE AD_Element_ID=579460
;

-- 2021-07-09T09:05:30.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval_Parameter', Name='Freigabe Dauer', Description=NULL, Help=NULL, AD_Element_ID=579460 WHERE UPPER(ColumnName)='SUPPLIERAPPROVAL_PARAMETER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-09T09:05:30.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SupplierApproval_Parameter', Name='Freigabe Dauer', Description=NULL, Help=NULL WHERE AD_Element_ID=579460 AND IsCentrallyMaintained='Y'
;

-- 2021-07-09T09:05:30.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Freigabe Dauer', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579460) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579460)
;

-- 2021-07-09T09:05:30.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Freigabe Dauer', Name='Freigabe Dauer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579460)
;

-- 2021-07-09T09:05:30.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Freigabe Dauer', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579460
;

-- 2021-07-09T09:05:30.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Freigabe Dauer', Description=NULL, Help=NULL WHERE AD_Element_ID = 579460
;

-- 2021-07-09T09:05:30.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Freigabe Dauer', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579460
;

-- 2021-07-09T09:07:59.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Supplier Approval Duration', PrintName='Supplier Approval Duration',Updated=TO_TIMESTAMP('2021-07-09 12:07:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579460 AND AD_Language='en_US'
;

-- 2021-07-09T09:07:59.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579460,'en_US') 
;

-- 2021-07-09T09:09:16.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Geschäftspartner {0} hat keine Lieferantenfreigabe für die Norm {1}.',Updated=TO_TIMESTAMP('2021-07-09 12:09:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545046
;



-- 2021-07-09T10:52:34.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Die Lieferantenfreigabe des Geschäftspartners {0} für die Norm {1} hat das Ablaufdatum {2}.',Updated=TO_TIMESTAMP('2021-07-09 13:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545045
;

-- 2021-07-09T13:01:12.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='3 Jahre',Updated=TO_TIMESTAMP('2021-07-09 16:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542703
;

-- 2021-07-09T13:01:24.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='2 Jahre',Updated=TO_TIMESTAMP('2021-07-09 16:01:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542704
;

-- 2021-07-09T13:01:35.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='1 Jahr',Updated=TO_TIMESTAMP('2021-07-09 16:01:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542705
;

-- 2021-07-09T13:02:00.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Einkauf',Updated=TO_TIMESTAMP('2021-07-09 16:02:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542700
;

-- 2021-07-09T13:02:06.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='Y',Updated=TO_TIMESTAMP('2021-07-09 16:02:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542699
;

-- 2021-07-09T13:02:21.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Verkauf',Updated=TO_TIMESTAMP('2021-07-09 16:02:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542699
;



