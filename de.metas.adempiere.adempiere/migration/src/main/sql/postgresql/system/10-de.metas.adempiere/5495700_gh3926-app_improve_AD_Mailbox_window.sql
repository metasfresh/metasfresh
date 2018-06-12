-- 2018-06-11T17:13:18.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:13:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540236
;

-- 2018-06-11T17:15:48.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Help='', Name='Nutzer-ID/Login', PrintName='Nutzer-ID/Login',Updated=TO_TIMESTAMP('2018-06-11 17:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1903
;

-- 2018-06-11T17:15:48.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='UserName', Name='Nutzer-ID/Login', Description='', Help='' WHERE AD_Element_ID=1903
;

-- 2018-06-11T17:15:48.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UserName', Name='Nutzer-ID/Login', Description='', Help='', AD_Element_ID=1903 WHERE UPPER(ColumnName)='USERNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-06-11T17:15:48.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UserName', Name='Nutzer-ID/Login', Description='', Help='' WHERE AD_Element_ID=1903 AND IsCentrallyMaintained='Y'
;

-- 2018-06-11T17:15:48.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nutzer-ID/Login', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1903) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1903)
;

-- 2018-06-11T17:15:48.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Nutzer-ID/Login', Name='Nutzer-ID/Login' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1903)
;

-- 2018-06-11T17:16:40.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544118,0,TO_TIMESTAMP('2018-06-11 17:16:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SMTP-Login','SMTP-Login',TO_TIMESTAMP('2018-06-11 17:16:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-11T17:16:40.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544118 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-06-11T17:18:11.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-11 17:18:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544118 AND AD_Language='de_CH'
;

-- 2018-06-11T17:18:12.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544118,'de_CH') 
;

-- 2018-06-11T17:18:20.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-11 17:18:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='SMTP login',PrintName='SMTP login' WHERE AD_Element_ID=544118 AND AD_Language='en_US'
;

-- 2018-06-11T17:18:20.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544118,'en_US') 
;

-- 2018-06-11T17:18:38.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='SMTP Login', PrintName='SMTP Login',Updated=TO_TIMESTAMP('2018-06-11 17:18:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544118
;

-- 2018-06-11T17:18:38.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='SMTP Login', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544118) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544118)
;

-- 2018-06-11T17:18:38.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='SMTP Login', Name='SMTP Login' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544118)
;

-- 2018-06-11T17:18:43.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-11 17:18:43','YYYY-MM-DD HH24:MI:SS'),Name='SMTP Login',PrintName='SMTP Login' WHERE AD_Element_ID=544118 AND AD_Language='de_CH'
;

-- 2018-06-11T17:18:43.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544118,'de_CH') 
;

-- 2018-06-11T17:19:25.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=544118, Description=NULL, Help=NULL, Name='SMTP Login',Updated=TO_TIMESTAMP('2018-06-11 17:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545482
;

-- 2018-06-11T17:19:25.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544118) 
;

-- 2018-06-11T17:19:43.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544119,0,TO_TIMESTAMP('2018-06-11 17:19:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SMTP Kennwort','SMTP Kennwort',TO_TIMESTAMP('2018-06-11 17:19:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-11T17:19:43.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544119 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-06-11T17:19:46.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-11 17:19:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544119 AND AD_Language='de_CH'
;

-- 2018-06-11T17:19:46.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544119,'de_CH') 
;

-- 2018-06-11T17:19:56.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-11 17:19:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='SMTP password',PrintName='SMTP password' WHERE AD_Element_ID=544119 AND AD_Language='en_US'
;

-- 2018-06-11T17:19:56.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544119,'en_US') 
;

-- 2018-06-11T17:20:28.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=544119, Description=NULL, Help=NULL, Name='SMTP Kennwort',Updated=TO_TIMESTAMP('2018-06-11 17:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545478
;

-- 2018-06-11T17:20:28.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544119) 
;

-- 2018-06-11T17:20:33.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=1,Updated=TO_TIMESTAMP('2018-06-11 17:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545478
;

-- 2018-06-11T17:20:37.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=1,Updated=TO_TIMESTAMP('2018-06-11 17:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545482
;

-- 2018-06-11T17:21:18.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2018-06-11 17:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547486
;

-- 2018-06-11T17:21:18.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2018-06-11 17:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545482
;

-- 2018-06-11T17:21:18.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2018-06-11 17:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545478
;

-- 2018-06-11T17:21:18.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2018-06-11 17:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563534
;

-- 2018-06-11T17:21:18.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2018-06-11 17:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563533
;

-- 2018-06-11T17:21:56.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-06-11 17:21:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559714
;

-- 2018-06-11T17:22:02.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_mailbox','SMTPPort','NUMERIC(10)',null,'25')
;

-- 2018-06-11T17:22:02.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_MailBox SET SMTPPort=25 WHERE SMTPPort IS NULL
;

-- 2018-06-11T17:22:02.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_mailbox','SMTPPort',null,'NOT NULL',null)
;

-- 2018-06-11T17:22:36.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='SMTP Anmeldung', PrintName='SMTP Anmeldung',Updated=TO_TIMESTAMP('2018-06-11 17:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1898
;

-- 2018-06-11T17:22:36.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSmtpAuthorization', Name='SMTP Anmeldung', Description='Ihr EMail-Server verlangt eine Anmeldung', Help='Some email servers require authentication before sending emails.  If yes, users are required to define their email user name and password.  If authentication is required and no user name and password is required, delivery will fail.' WHERE AD_Element_ID=1898
;

-- 2018-06-11T17:22:36.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSmtpAuthorization', Name='SMTP Anmeldung', Description='Ihr EMail-Server verlangt eine Anmeldung', Help='Some email servers require authentication before sending emails.  If yes, users are required to define their email user name and password.  If authentication is required and no user name and password is required, delivery will fail.', AD_Element_ID=1898 WHERE UPPER(ColumnName)='ISSMTPAUTHORIZATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-06-11T17:22:36.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSmtpAuthorization', Name='SMTP Anmeldung', Description='Ihr EMail-Server verlangt eine Anmeldung', Help='Some email servers require authentication before sending emails.  If yes, users are required to define their email user name and password.  If authentication is required and no user name and password is required, delivery will fail.' WHERE AD_Element_ID=1898 AND IsCentrallyMaintained='Y'
;

-- 2018-06-11T17:22:36.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='SMTP Anmeldung', Description='Ihr EMail-Server verlangt eine Anmeldung', Help='Some email servers require authentication before sending emails.  If yes, users are required to define their email user name and password.  If authentication is required and no user name and password is required, delivery will fail.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1898) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1898)
;

-- 2018-06-11T17:22:36.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='SMTP Anmeldung', Name='SMTP Anmeldung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1898)
;

-- 2018-06-11T17:22:48.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2018-06-11 17:22:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545259
;

-- 2018-06-11T17:23:05.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsSmtpAuthorization@=Y',Updated=TO_TIMESTAMP('2018-06-11 17:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544218
;

-- 2018-06-11T17:23:12.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsSmtpAuthorization@=Y',Updated=TO_TIMESTAMP('2018-06-11 17:23:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544217
;

-- 2018-06-11T17:24:49.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2018-06-11 17:24:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559715
;

-- 2018-06-11T17:26:39.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545480
;

-- 2018-06-11T17:26:40.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545481
;

-- 2018-06-11T17:26:43.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545479
;

-- 2018-06-11T17:26:45.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545472
;

-- 2018-06-11T17:26:49.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545471
;

-- 2018-06-11T17:26:50.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545477
;

-- 2018-06-11T17:26:52.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545476
;

-- 2018-06-11T17:26:55.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545473
;

-- 2018-06-11T17:26:57.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563533
;

-- 2018-06-11T17:26:59.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563534
;

-- 2018-06-11T17:27:00.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:27:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545478
;

-- 2018-06-11T17:27:03.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545482
;

-- 2018-06-11T17:27:05.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:27:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547486
;

-- 2018-06-11T17:27:07.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:27:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545474
;

-- 2018-06-11T17:27:10.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-06-11 17:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545475
;

