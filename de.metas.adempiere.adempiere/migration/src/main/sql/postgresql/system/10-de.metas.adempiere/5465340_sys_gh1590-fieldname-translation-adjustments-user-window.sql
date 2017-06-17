-- 2017-06-17T09:43:45.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:43:45','YYYY-MM-DD HH24:MI:SS'),Name='Language',Description='Language for this entry',Help='Defines the Language for data recording and processing.' WHERE AD_Field_ID=558688 AND AD_Language='en_US'
;

-- 2017-06-17T09:44:06.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:44:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=558688 AND AD_Language='en_US'
;

-- 2017-06-17T09:45:01.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:45:01','YYYY-MM-DD HH24:MI:SS'),Description='Language for this entity',Help='The Language identifies the language to use for display and formatting' WHERE AD_Field_ID=558688 AND AD_Language='en_US'
;

-- 2017-06-17T09:45:31.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:45:31','YYYY-MM-DD HH24:MI:SS'),Name='System User' WHERE AD_Field_ID=551889 AND AD_Language='en_US'
;

-- 2017-06-17T09:46:12.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:46:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='System User',PrintName='System User' WHERE AD_Element_ID=542103 AND AD_Language='en_US'
;

-- 2017-06-17T09:46:12.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542103,'en_US') 
;

-- 2017-06-17T09:47:37.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:47:37','YYYY-MM-DD HH24:MI:SS'),Name='Organisation',IsTranslated='Y' WHERE AD_Column_ID=423 AND AD_Language='en_US'
;

-- 2017-06-17T09:48:17.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:48:17','YYYY-MM-DD HH24:MI:SS'),Name='Organisation',PrintName='Organisation',Description='Organisational entity within client',Help='An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.' WHERE AD_Element_ID=113 AND AD_Language='en_US'
;

-- 2017-06-17T09:48:17.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(113,'en_US') 
;

-- 2017-06-17T09:49:12.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:49:12','YYYY-MM-DD HH24:MI:SS'),Name='LDAP Processor' WHERE AD_Field_ID=13738 AND AD_Language='en_US'
;

-- 2017-06-17T09:49:34.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Description='LDAP Access Log', Name='LDAP Access',Updated=TO_TIMESTAMP('2017-06-17 09:49:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=13737
;

-- 2017-06-17T09:49:34.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=13737
;

-- 2017-06-17T09:49:47.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:49:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='LDAP Access',Description='LDAP Access Log' WHERE AD_Field_ID=13737 AND AD_Language='en_US'
;

-- 2017-06-17T09:50:13.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='LDAPAccess Log', Name='LDAP Access', PrintName='LDAP Access',Updated=TO_TIMESTAMP('2017-06-17 09:50:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=3096
;

-- 2017-06-17T09:50:13.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=3096
;

-- 2017-06-17T09:50:13.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_LdapAccess_ID', Name='LDAP Access', Description='LDAPAccess Log', Help='Access via LDAP' WHERE AD_Element_ID=3096
;

-- 2017-06-17T09:50:13.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_LdapAccess_ID', Name='LDAP Access', Description='LDAPAccess Log', Help='Access via LDAP', AD_Element_ID=3096 WHERE UPPER(ColumnName)='AD_LDAPACCESS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T09:50:13.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_LdapAccess_ID', Name='LDAP Access', Description='LDAPAccess Log', Help='Access via LDAP' WHERE AD_Element_ID=3096 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T09:50:13.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LDAP Access', Description='LDAPAccess Log', Help='Access via LDAP' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=3096) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T09:50:13.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LDAP Access', Name='LDAP Access' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=3096)
;

-- 2017-06-17T09:50:34.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:50:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='LDAP Access',PrintName='LDAP Access',Description='LDAP Access Log' WHERE AD_Element_ID=3096 AND AD_Language='en_US'
;

-- 2017-06-17T09:50:34.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(3096,'en_US') 
;

-- 2017-06-17T09:52:22.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:52:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Role' WHERE AD_Field_ID=555231 AND AD_Language='en_US'
;

-- 2017-06-17T09:52:39.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:52:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='User Session' WHERE AD_Field_ID=555232 AND AD_Language='en_US'
;

-- 2017-06-17T09:55:02.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:55:02','YYYY-MM-DD HH24:MI:SS'),Name='Purchasing Contact' WHERE AD_Field_ID=553935 AND AD_Language='en_US'
;

-- 2017-06-17T09:55:46.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:55:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Purchasing Contact',PrintName='Purchasing Contact' WHERE AD_Element_ID=542329 AND AD_Language='en_US'
;

-- 2017-06-17T09:55:46.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542329,'en_US') 
;

-- 2017-06-17T09:56:37.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:56:37','YYYY-MM-DD HH24:MI:SS'),Name='Full Access to Business Partner' WHERE AD_Field_ID=12323 AND AD_Language='en_US'
;

-- 2017-06-17T09:57:25.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:57:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Unlock Account' WHERE AD_Field_ID=551899 AND AD_Language='en_US'
;

-- 2017-06-17T09:58:57.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Zugang entsperren', PrintName='Zugang entsperren',Updated=TO_TIMESTAMP('2017-06-17 09:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540824
;

-- 2017-06-17T09:58:57.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=540824
;

-- 2017-06-17T09:58:57.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='UnlockAccount', Name='Zugang entsperren', Description='Button that will call a process to unlock current selected account', Help=NULL WHERE AD_Element_ID=540824
;

-- 2017-06-17T09:58:57.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UnlockAccount', Name='Zugang entsperren', Description='Button that will call a process to unlock current selected account', Help=NULL, AD_Element_ID=540824 WHERE UPPER(ColumnName)='UNLOCKACCOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T09:58:57.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UnlockAccount', Name='Zugang entsperren', Description='Button that will call a process to unlock current selected account', Help=NULL WHERE AD_Element_ID=540824 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T09:58:57.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugang entsperren', Description='Button that will call a process to unlock current selected account', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540824) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T09:58:57.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugang entsperren', Name='Zugang entsperren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540824)
;

-- 2017-06-17T09:59:13.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 09:59:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Unlock Account',PrintName='Unlock Account' WHERE AD_Element_ID=540824 AND AD_Language='en_US'
;

-- 2017-06-17T09:59:13.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540824,'en_US') 
;

-- 2017-06-17T10:00:30.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugang gesperrt',Updated=TO_TIMESTAMP('2017-06-17 10:00:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551896
;

-- 2017-06-17T10:00:30.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=551896
;

-- 2017-06-17T10:00:52.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:00:52','YYYY-MM-DD HH24:MI:SS'),Name='Account locked' WHERE AD_Field_ID=551896 AND AD_Language='en_US'
;

-- 2017-06-17T10:01:50.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Kennzeichen das anzeigt ob der Zugang gesperrt wurde', Name='Zugang gesperrt', PrintName='Zugang gesperrt',Updated=TO_TIMESTAMP('2017-06-17 10:01:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540817
;

-- 2017-06-17T10:01:50.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=540817
;

-- 2017-06-17T10:01:50.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAccountLocked', Name='Zugang gesperrt', Description='Kennzeichen das anzeigt ob der Zugang gesperrt wurde', Help=NULL WHERE AD_Element_ID=540817
;

-- 2017-06-17T10:01:50.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAccountLocked', Name='Zugang gesperrt', Description='Kennzeichen das anzeigt ob der Zugang gesperrt wurde', Help=NULL, AD_Element_ID=540817 WHERE UPPER(ColumnName)='ISACCOUNTLOCKED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T10:01:50.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAccountLocked', Name='Zugang gesperrt', Description='Kennzeichen das anzeigt ob der Zugang gesperrt wurde', Help=NULL WHERE AD_Element_ID=540817 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:01:50.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugang gesperrt', Description='Kennzeichen das anzeigt ob der Zugang gesperrt wurde', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540817) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:01:50.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugang gesperrt', Name='Zugang gesperrt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540817)
;

-- 2017-06-17T10:02:09.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:02:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Account locked',PrintName='Account locked' WHERE AD_Element_ID=540817 AND AD_Language='en_US'
;

-- 2017-06-17T10:02:09.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540817,'en_US') 
;

-- 2017-06-17T10:04:20.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Login Fehlversuche',Updated=TO_TIMESTAMP('2017-06-17 10:04:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551895
;

-- 2017-06-17T10:04:20.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=551895
;

-- 2017-06-17T10:04:35.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:04:35','YYYY-MM-DD HH24:MI:SS'),Name='Login Failure Count' WHERE AD_Field_ID=551895 AND AD_Language='en_US'
;

-- 2017-06-17T10:04:37.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:04:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=551895 AND AD_Language='en_US'
;

-- 2017-06-17T10:05:21.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Anzahl Login Fehlversuche', Name='Anzahl Login Fehlversuche', PrintName='Anzahl Login Fehlversuche',Updated=TO_TIMESTAMP('2017-06-17 10:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540816
;

-- 2017-06-17T10:05:21.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=540816
;

-- 2017-06-17T10:05:21.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LoginFailureCount', Name='Anzahl Login Fehlversuche', Description='Anzahl Login Fehlversuche', Help=NULL WHERE AD_Element_ID=540816
;

-- 2017-06-17T10:05:21.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginFailureCount', Name='Anzahl Login Fehlversuche', Description='Anzahl Login Fehlversuche', Help=NULL, AD_Element_ID=540816 WHERE UPPER(ColumnName)='LOGINFAILURECOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T10:05:21.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginFailureCount', Name='Anzahl Login Fehlversuche', Description='Anzahl Login Fehlversuche', Help=NULL WHERE AD_Element_ID=540816 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:05:21.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Login Fehlversuche', Description='Anzahl Login Fehlversuche', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540816) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:05:21.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anzahl Login Fehlversuche', Name='Anzahl Login Fehlversuche' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540816)
;

-- 2017-06-17T10:05:34.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:05:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Login Failure Count',PrintName='Login Failure Count' WHERE AD_Element_ID=540816 AND AD_Language='en_US'
;

-- 2017-06-17T10:05:34.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540816,'en_US') 
;

-- 2017-06-17T10:06:37.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Datum Zugang gesperrt',Updated=TO_TIMESTAMP('2017-06-17 10:06:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551898
;

-- 2017-06-17T10:06:37.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=551898
;

-- 2017-06-17T10:06:46.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:06:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Login Failure Date' WHERE AD_Field_ID=551898 AND AD_Language='en_US'
;

-- 2017-06-17T10:07:27.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Datum Zugang gesperrt', Name='Datum Zugang gesperrt', PrintName='Datum Zugang gesperrt',Updated=TO_TIMESTAMP('2017-06-17 10:07:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540820
;

-- 2017-06-17T10:07:27.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=540820
;

-- 2017-06-17T10:07:27.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LoginFailureDate', Name='Datum Zugang gesperrt', Description='Datum Zugang gesperrt', Help=NULL WHERE AD_Element_ID=540820
;

-- 2017-06-17T10:07:27.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginFailureDate', Name='Datum Zugang gesperrt', Description='Datum Zugang gesperrt', Help=NULL, AD_Element_ID=540820 WHERE UPPER(ColumnName)='LOGINFAILUREDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T10:07:27.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginFailureDate', Name='Datum Zugang gesperrt', Description='Datum Zugang gesperrt', Help=NULL WHERE AD_Element_ID=540820 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:07:27.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Datum Zugang gesperrt', Description='Datum Zugang gesperrt', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540820) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:07:27.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Datum Zugang gesperrt', Name='Datum Zugang gesperrt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540820)
;

-- 2017-06-17T10:07:43.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:07:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Login Failure Date',PrintName='Login Failure Date' WHERE AD_Element_ID=540820 AND AD_Language='en_US'
;

-- 2017-06-17T10:07:43.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540820,'en_US') 
;

-- 2017-06-17T10:08:14.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Datum Zugang Fehleingabe', Name='Datum Zugang Fehleingabe', PrintName='Datum Zugang Fehleingabe',Updated=TO_TIMESTAMP('2017-06-17 10:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540820
;

-- 2017-06-17T10:08:14.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=540820
;

-- 2017-06-17T10:08:14.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LoginFailureDate', Name='Datum Zugang Fehleingabe', Description='Datum Zugang Fehleingabe', Help=NULL WHERE AD_Element_ID=540820
;

-- 2017-06-17T10:08:14.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginFailureDate', Name='Datum Zugang Fehleingabe', Description='Datum Zugang Fehleingabe', Help=NULL, AD_Element_ID=540820 WHERE UPPER(ColumnName)='LOGINFAILUREDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T10:08:14.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginFailureDate', Name='Datum Zugang Fehleingabe', Description='Datum Zugang Fehleingabe', Help=NULL WHERE AD_Element_ID=540820 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:08:14.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Datum Zugang Fehleingabe', Description='Datum Zugang Fehleingabe', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540820) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:08:14.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Datum Zugang Fehleingabe', Name='Datum Zugang Fehleingabe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540820)
;

-- 2017-06-17T10:08:57.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Datum Login Fehler', Name='Datum Login Fehler', PrintName='Datum Login Fehler',Updated=TO_TIMESTAMP('2017-06-17 10:08:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540820
;

-- 2017-06-17T10:08:57.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=540820
;

-- 2017-06-17T10:08:57.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LoginFailureDate', Name='Datum Login Fehler', Description='Datum Login Fehler', Help=NULL WHERE AD_Element_ID=540820
;

-- 2017-06-17T10:08:57.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginFailureDate', Name='Datum Login Fehler', Description='Datum Login Fehler', Help=NULL, AD_Element_ID=540820 WHERE UPPER(ColumnName)='LOGINFAILUREDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T10:08:57.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LoginFailureDate', Name='Datum Login Fehler', Description='Datum Login Fehler', Help=NULL WHERE AD_Element_ID=540820 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:08:57.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Datum Login Fehler', Description='Datum Login Fehler', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540820) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:08:57.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Datum Login Fehler', Name='Datum Login Fehler' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540820)
;

-- 2017-06-17T10:10:25.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sperrung von IP',Updated=TO_TIMESTAMP('2017-06-17 10:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551897
;

-- 2017-06-17T10:10:25.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=551897
;

-- 2017-06-17T10:10:32.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sperrung von IP Adresse',Updated=TO_TIMESTAMP('2017-06-17 10:10:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551897
;

-- 2017-06-17T10:10:32.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=551897
;

-- 2017-06-17T10:10:42.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:10:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Locked From IP' WHERE AD_Field_ID=551897 AND AD_Language='en_US'
;

-- 2017-06-17T10:13:04.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Sperrung von IP', PrintName='Sperrung von IP',Updated=TO_TIMESTAMP('2017-06-17 10:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540819
;

-- 2017-06-17T10:13:04.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=540819
;

-- 2017-06-17T10:13:04.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LockedFromIP', Name='Sperrung von IP', Description='Client IP address that was used when this account was locked ', Help=NULL WHERE AD_Element_ID=540819
;

-- 2017-06-17T10:13:04.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LockedFromIP', Name='Sperrung von IP', Description='Client IP address that was used when this account was locked ', Help=NULL, AD_Element_ID=540819 WHERE UPPER(ColumnName)='LOCKEDFROMIP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T10:13:04.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LockedFromIP', Name='Sperrung von IP', Description='Client IP address that was used when this account was locked ', Help=NULL WHERE AD_Element_ID=540819 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:13:04.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sperrung von IP', Description='Client IP address that was used when this account was locked ', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540819) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:13:04.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sperrung von IP', Name='Sperrung von IP' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540819)
;

-- 2017-06-17T10:14:02.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:14:02','YYYY-MM-DD HH24:MI:SS'),Name='Locked From IP',PrintName='Locked From IP' WHERE AD_Element_ID=540819 AND AD_Language='en_US'
;

-- 2017-06-17T10:14:02.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540819,'en_US') 
;

-- 2017-06-17T10:14:06.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:14:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=540819 AND AD_Language='en_US'
;

-- 2017-06-17T10:14:06.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540819,'en_US') 
;

-- 2017-06-17T10:16:35.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Volle Geschäftspartner Berechtigung',Updated=TO_TIMESTAMP('2017-06-17 10:16:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12323
;

-- 2017-06-17T10:16:35.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=12323
;

-- 2017-06-17T10:17:00.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vollzugriff Geschäftspartner',Updated=TO_TIMESTAMP('2017-06-17 10:17:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12323
;

-- 2017-06-17T10:17:00.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=12323
;

-- 2017-06-17T10:17:50.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Suchschlüssel',Updated=TO_TIMESTAMP('2017-06-17 10:17:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=13755
;

-- 2017-06-17T10:17:50.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=13755
;

-- 2017-06-17T10:18:23.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Telefon',Updated=TO_TIMESTAMP('2017-06-17 10:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6517
;

-- 2017-06-17T10:18:23.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=6517
;

-- 2017-06-17T10:19:08.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Beschreibt eine Telefon Nummer', Help='Beschreibt eine Telefon Nummer', Name='Telefon', PrintName='Telefon',Updated=TO_TIMESTAMP('2017-06-17 10:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=505
;

-- 2017-06-17T10:19:08.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=505
;

-- 2017-06-17T10:19:08.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Phone', Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='Beschreibt eine Telefon Nummer' WHERE AD_Element_ID=505
;

-- 2017-06-17T10:19:08.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Phone', Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='Beschreibt eine Telefon Nummer', AD_Element_ID=505 WHERE UPPER(ColumnName)='PHONE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T10:19:08.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Phone', Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='Beschreibt eine Telefon Nummer' WHERE AD_Element_ID=505 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:19:08.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Telefon', Description='Beschreibt eine Telefon Nummer', Help='Beschreibt eine Telefon Nummer' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=505) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:19:08.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Telefon', Name='Telefon' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=505)
;

-- 2017-06-17T10:19:16.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:19:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=505 AND AD_Language='en_US'
;

-- 2017-06-17T10:19:16.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505,'en_US') 
;

-- 2017-06-17T10:19:46.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Alternative Mobile Telefonnummer', Name='Mobil', PrintName='Mobil',Updated=TO_TIMESTAMP('2017-06-17 10:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=506
;

-- 2017-06-17T10:19:46.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=506
;

-- 2017-06-17T10:19:46.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Phone2', Name='Mobil', Description='Alternative Mobile Telefonnummer', Help='"Telfon (alternativ)" gibt eine weitere Telefonnummer an.' WHERE AD_Element_ID=506
;

-- 2017-06-17T10:19:46.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Phone2', Name='Mobil', Description='Alternative Mobile Telefonnummer', Help='"Telfon (alternativ)" gibt eine weitere Telefonnummer an.', AD_Element_ID=506 WHERE UPPER(ColumnName)='PHONE2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T10:19:46.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Phone2', Name='Mobil', Description='Alternative Mobile Telefonnummer', Help='"Telfon (alternativ)" gibt eine weitere Telefonnummer an.' WHERE AD_Element_ID=506 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:19:46.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mobil', Description='Alternative Mobile Telefonnummer', Help='"Telfon (alternativ)" gibt eine weitere Telefonnummer an.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=506) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:19:46.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mobil', Name='Mobil' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=506)
;

-- 2017-06-17T10:20:12.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:20:12','YYYY-MM-DD HH24:MI:SS'),Name='Mobile',PrintName='Mobile',Description='Identifies a mobile telephone number.' WHERE AD_Element_ID=506 AND AD_Language='en_US'
;

-- 2017-06-17T10:20:12.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(506,'en_US') 
;

-- 2017-06-17T10:20:15.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:20:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=506 AND AD_Language='en_US'
;

-- 2017-06-17T10:20:15.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(506,'en_US') 
;

-- 2017-06-17T10:21:28.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Benutzer PIN',Updated=TO_TIMESTAMP('2017-06-17 10:21:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=52010
;

-- 2017-06-17T10:21:28.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=52010
;

-- 2017-06-17T10:21:33.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:21:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=52010 AND AD_Language='en_US'
;

-- 2017-06-17T10:22:00.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Benutzer PIN', PrintName='Benutzer PIN',Updated=TO_TIMESTAMP('2017-06-17 10:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=52023
;

-- 2017-06-17T10:22:00.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=52023
;

-- 2017-06-17T10:22:00.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='UserPIN', Name='Benutzer PIN', Description=NULL, Help=NULL WHERE AD_Element_ID=52023
;

-- 2017-06-17T10:22:00.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UserPIN', Name='Benutzer PIN', Description=NULL, Help=NULL, AD_Element_ID=52023 WHERE UPPER(ColumnName)='USERPIN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-06-17T10:22:00.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UserPIN', Name='Benutzer PIN', Description=NULL, Help=NULL WHERE AD_Element_ID=52023 AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:22:00.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Benutzer PIN', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=52023) AND IsCentrallyMaintained='Y'
;

-- 2017-06-17T10:22:00.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Benutzer PIN', Name='Benutzer PIN' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=52023)
;

-- 2017-06-17T10:22:09.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:22:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=52023 AND AD_Language='en_US'
;

-- 2017-06-17T10:22:09.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(52023,'en_US') 
;

-- 2017-06-17T10:42:30.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Suchschlüssel',Updated=TO_TIMESTAMP('2017-06-17 10:42:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57987
;

-- 2017-06-17T10:42:30.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=57987
;

-- 2017-06-17T10:42:37.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:42:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=57987 AND AD_Language='en_US'
;

-- 2017-06-17T10:43:14.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bemerkungen',Updated=TO_TIMESTAMP('2017-06-17 10:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57989
;

-- 2017-06-17T10:43:14.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=57989
;

-- 2017-06-17T10:43:18.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-17 10:43:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=57989 AND AD_Language='en_US'
;

-- 2017-06-17T10:44:38.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Benachrichtigungs Art',Updated=TO_TIMESTAMP('2017-06-17 10:44:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58001
;

-- 2017-06-17T10:44:38.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58001
;

-- 2017-06-17T10:45:03.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vollzugriff Geschäftspartner',Updated=TO_TIMESTAMP('2017-06-17 10:45:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58003
;

-- 2017-06-17T10:45:03.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58003
;

-- 2017-06-17T10:45:56.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='EMail Nutzer-ID',Updated=TO_TIMESTAMP('2017-06-17 10:45:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58004
;

-- 2017-06-17T10:45:56.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58004
;

-- 2017-06-17T10:46:03.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorgesetzter',Updated=TO_TIMESTAMP('2017-06-17 10:46:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58006
;

-- 2017-06-17T10:46:03.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58006
;

-- 2017-06-17T10:46:31.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Trx Sektion',Updated=TO_TIMESTAMP('2017-06-17 10:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58008
;

-- 2017-06-17T10:46:31.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58008
;

-- 2017-06-17T10:46:47.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verbndungsart',Updated=TO_TIMESTAMP('2017-06-17 10:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58009
;

-- 2017-06-17T10:46:47.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58009
;

-- 2017-06-17T10:47:03.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='EMail überprüft',Updated=TO_TIMESTAMP('2017-06-17 10:47:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58011
;

-- 2017-06-17T10:47:03.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58011
;

-- 2017-06-17T10:47:27.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Überprüfung EMail',Updated=TO_TIMESTAMP('2017-06-17 10:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58013
;

-- 2017-06-17T10:47:27.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58013
;

-- 2017-06-17T10:47:36.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verarbieten',Updated=TO_TIMESTAMP('2017-06-17 10:47:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57983
;

-- 2017-06-17T10:47:36.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=57983
;

-- 2017-06-17T10:47:41.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='eMail Nutzer-ID',Updated=TO_TIMESTAMP('2017-06-17 10:47:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58004
;

-- 2017-06-17T10:47:41.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58004
;

-- 2017-06-17T10:47:43.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='eMail überprüft',Updated=TO_TIMESTAMP('2017-06-17 10:47:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58011
;

-- 2017-06-17T10:47:43.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58011
;

-- 2017-06-17T10:47:50.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Überprüfung eMail',Updated=TO_TIMESTAMP('2017-06-17 10:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58013
;

-- 2017-06-17T10:47:50.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=58013
;

