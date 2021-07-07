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

















