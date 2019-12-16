
-- 2019-12-16T09:43:18.587Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Name='EDI-ID des DESADV-Empfängers', PrintName='EDI-ID des DESADV-Empfängers',Updated=TO_TIMESTAMP('2019-12-16 10:43:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542001 AND AD_Language='de_CH'
;

-- 2019-12-16T09:43:18.596Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542001,'de_CH') 
;

-- 2019-12-16T09:43:25.172Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-16 10:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542001 AND AD_Language='de_CH'
;

-- 2019-12-16T09:43:25.175Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542001,'de_CH') 
;

-- 2019-12-16T09:43:35.204Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Name='EDI-ID des DESADV-Empfängers', PrintName='EDI-ID des DESADV-Empfängers',Updated=TO_TIMESTAMP('2019-12-16 10:43:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542001 AND AD_Language='de_DE'
;

-- 2019-12-16T09:43:35.205Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542001,'de_DE') 
;

-- 2019-12-16T09:43:35.211Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(542001,'de_DE') 
;

-- 2019-12-16T09:43:35.212Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='EdiRecipientGLN', Name='EDI-ID des DESADV-Empfängers', Description='', Help=NULL WHERE AD_Element_ID=542001
;

-- 2019-12-16T09:43:35.213Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EdiRecipientGLN', Name='EDI-ID des DESADV-Empfängers', Description='', Help=NULL, AD_Element_ID=542001 WHERE UPPER(ColumnName)='EDIRECIPIENTGLN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-16T09:43:35.214Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EdiRecipientGLN', Name='EDI-ID des DESADV-Empfängers', Description='', Help=NULL WHERE AD_Element_ID=542001 AND IsCentrallyMaintained='Y'
;

-- 2019-12-16T09:43:35.214Z
-- URL zum Konzept
UPDATE AD_Field SET Name='EDI-ID des DESADV-Empfängers', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542001) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542001)
;

-- 2019-12-16T09:43:35.227Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='EDI-ID des DESADV-Empfängers', Name='EDI-ID des DESADV-Empfängers' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542001)
;

-- 2019-12-16T09:43:35.228Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='EDI-ID des DESADV-Empfängers', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542001
;

-- 2019-12-16T09:43:35.229Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='EDI-ID des DESADV-Empfängers', Description='', Help=NULL WHERE AD_Element_ID = 542001
;

-- 2019-12-16T09:43:35.229Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'EDI-ID des DESADV-Empfängers', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542001
;

-- 2019-12-16T09:43:39.868Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2019-12-16 10:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542001 AND AD_Language='en_US'
;

-- 2019-12-16T09:43:39.870Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542001,'en_US') 
;



-- 2019-12-16T09:46:26.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsEdiDesadvRecipient/N@=Y',Updated=TO_TIMESTAMP('2019-12-16 10:46:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553178
;

-- 2019-12-16T09:46:34.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsEdiDesadvRecipient/N@=Y',Updated=TO_TIMESTAMP('2019-12-16 10:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556622
;

-- 2019-12-16T10:29:17.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT IsEdiInvoicRecipient from C_BPartner where C_BPartner_ID = C_Order.Bill_BPartner_ID)',Updated=TO_TIMESTAMP('2019-12-16 11:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552603
;

-- 2019-12-16T10:35:51.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select bp.IsEdiInvoicRecipient from C_BPartner bp where bp.C_BPartner_ID=C_Doc_Outbound_Log.C_BPartner_ID)',Updated=TO_TIMESTAMP('2019-12-16 11:35:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551507
;

-- 2019-12-16T10:38:06.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=577426, ColumnName='IsEdiDesadvRecipient', DefaultValue='N', Description='', EntityType='de.metas.inoutcandidate', Help=NULL, IsCalculated='N', IsMandatory='N', Name='Erhält EDI-DESADV',Updated=TO_TIMESTAMP('2019-12-16 11:38:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552463
;

-- 2019-12-16T10:38:06.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erhält EDI-DESADV', Description='', Help=NULL WHERE AD_Column_ID=552463
;

-- 2019-12-16T10:38:06.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577426) 
;

-- 2019-12-16T10:38:22.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( case when exists ( select 1 from C_BPartner bp where  bp.C_BPartner_ID = M_ShipmentSchedule.C_Bpartner_ID AND bp.IsEdiDesadvRecipient = ''Y'') then ''Y'' else ''N'' end )',Updated=TO_TIMESTAMP('2019-12-16 11:38:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552463
;

-- 2019-12-16T10:39:23.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT IsEdiInvoicRecipient from C_BPartner where C_BPartner_ID = C_Invoice.C_BPartner_ID)',Updated=TO_TIMESTAMP('2019-12-16 11:39:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552604
;

-- 2019-12-16T10:42:15.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsEdiDesadvRecipient@=Y', ReadOnlyLogic='@IsEdiDesadvRecipient@=N',Updated=TO_TIMESTAMP('2019-12-16 11:42:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548483
;

-- 2019-12-16T10:44:58.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsEdiInvoicRecipient/N@=N',Updated=TO_TIMESTAMP('2019-12-16 11:44:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552599
;

-- 2019-12-16T10:45:48.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsEdiInvoicRecipient/''N''@ = N',Updated=TO_TIMESTAMP('2019-12-16 11:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548484
;

-- 2019-12-16T10:47:41.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@IsEdiDesadvRecipient/N@ | @IsEdiInvoicRecipient/N@', ReadOnlyLogic='@IsEdiDesadvRecipient/''N''@=''N'' & @IsEdiInvoicRecipient/''N''@=''N''',Updated=TO_TIMESTAMP('2019-12-16 11:47:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 2019-12-16T10:53:05.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsActive='Y',Updated=TO_TIMESTAMP('2019-12-16 11:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540018
;

-- 2019-12-16T10:53:05.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Cockpit',Updated=TO_TIMESTAMP('2019-12-16 11:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540029
;

-- 2019-12-16T10:53:32.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsEdiDesadvRecipient@=Y',Updated=TO_TIMESTAMP('2019-12-16 11:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551573
;

-- 2019-12-16T10:53:39.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2019-12-16 11:53:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540018
;

-- 2019-12-16T10:53:39.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='N', Name='Cockpit',Updated=TO_TIMESTAMP('2019-12-16 11:53:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540029
;

-- 2019-12-16T10:55:12.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsEdiDesadvRecipient@=Y',Updated=TO_TIMESTAMP('2019-12-16 11:55:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559004
;

-- 2019-12-16T10:55:21.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsEdiDesadvRecipient/''N''@=''Y''',Updated=TO_TIMESTAMP('2019-12-16 11:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559004
;

update AD_field_ID SET DisplayLogic='@IsEdiDesadvRecipient/''N''@=''Y''', Updated='2019-12-16 10:56:21.856929+00', UpdatedBy=99 WHERE AD_Field_ID=559003;
update AD_field_ID SET DisplayLogic='@IsEdiDesadvRecipient/''N''@=''Y''', Updated='2019-12-16 10:56:21.856929+00', UpdatedBy=99 WHERE AD_Field_ID=560040;
update AD_field_ID SET DisplayLogic='@IsEdiDesadvRecipient/''N''@=''Y''', Updated='2019-12-16 10:56:21.856929+00', UpdatedBy=99 WHERE AD_Field_ID=560039;
update AD_field_ID SET DisplayLogic='@IsEdiDesadvRecipient/''N''@=''Y''', Updated='2019-12-16 10:56:21.856929+00', UpdatedBy=99 WHERE AD_Field_ID=562075;
update AD_field_ID SET DisplayLogic='@IsEdiDesadvRecipient/''N''@=''Y''', Updated='2019-12-16 10:56:21.856929+00', UpdatedBy=99 WHERE AD_Field_ID=562074;
update AD_field_ID SET DisplayLogic='@IsEdiDesadvRecipient/''N''@=''Y''', Updated='2019-12-16 10:56:21.856929+00', UpdatedBy=99 WHERE AD_Field_ID=583139;
update AD_field_ID SET DisplayLogic='@IsEdiDesadvRecipient/''N''@=''Y''', Updated='2019-12-16 10:56:21.856929+00', UpdatedBy=99 WHERE AD_Field_ID=583138;
