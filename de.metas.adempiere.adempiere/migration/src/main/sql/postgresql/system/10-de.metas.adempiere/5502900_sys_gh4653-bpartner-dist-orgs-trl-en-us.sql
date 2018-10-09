-- 2018-10-09T14:00:37.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:00:37','YYYY-MM-DD HH24:MI:SS'),PrintName='Company Name' WHERE AD_Element_ID=540648 AND AD_Language='en_US'
;

-- 2018-10-09T14:00:37.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540648,'en_US') 
;

-- 2018-10-09T14:01:50.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:01:50','YYYY-MM-DD HH24:MI:SS'),Name='Company 1' WHERE AD_Element_ID=540400 AND AD_Language='en_US'
;

-- 2018-10-09T14:01:50.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540400,'en_US') 
;

-- 2018-10-09T14:02:13.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:02:13','YYYY-MM-DD HH24:MI:SS'),Name='Company',PrintName='Company' WHERE AD_Element_ID=540400 AND AD_Language='en_US'
;

-- 2018-10-09T14:02:13.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540400,'en_US') 
;

-- 2018-10-09T14:03:13.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Umsatzsteuer ID', PrintName='Umsatzsteuer ID',Updated=TO_TIMESTAMP('2018-10-09 14:03:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=502388
;

-- 2018-10-09T14:03:13.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='VATaxID', Name='Umsatzsteuer ID', Description=NULL, Help=NULL WHERE AD_Element_ID=502388
;

-- 2018-10-09T14:03:13.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VATaxID', Name='Umsatzsteuer ID', Description=NULL, Help=NULL, AD_Element_ID=502388 WHERE UPPER(ColumnName)='VATAXID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-09T14:03:13.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VATaxID', Name='Umsatzsteuer ID', Description=NULL, Help=NULL WHERE AD_Element_ID=502388 AND IsCentrallyMaintained='Y'
;

-- 2018-10-09T14:03:13.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Umsatzsteuer ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=502388) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 502388)
;

-- 2018-10-09T14:03:13.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Umsatzsteuer ID', Name='Umsatzsteuer ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=502388)
;

-- 2018-10-09T14:04:46.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:04:46','YYYY-MM-DD HH24:MI:SS'),Name='VAT ID 1' WHERE AD_Element_ID=502388 AND AD_Language='en_US'
;

-- 2018-10-09T14:04:46.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(502388,'en_US') 
;

-- 2018-10-09T14:05:53.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:05:53','YYYY-MM-DD HH24:MI:SS'),Name='VAT ID',PrintName='VAT ID' WHERE AD_Element_ID=502388 AND AD_Language='en_US'
;

-- 2018-10-09T14:05:53.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(502388,'en_US') 
;

-- 2018-10-09T14:09:43.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:09:43','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=542000 AND AD_Language='en_US'
;

-- 2018-10-09T14:09:43.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542000,'en_US') 
;

-- 2018-10-09T14:54:18.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:54:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Producer Allotment',PrintName='Producer Allotment' WHERE AD_Element_ID=542317 AND AD_Language='en_US'
;

-- 2018-10-09T14:54:18.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542317,'en_US') 
;

-- 2018-10-09T14:56:22.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:56:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Autom. Reference in Order',PrintName='Autom. Reference in Order',Description='' WHERE AD_Element_ID=542973 AND AD_Language='en_US'
;

-- 2018-10-09T14:56:22.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542973,'en_US') 
;

-- 2018-10-09T14:57:22.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Replikations Standardwert', PrintName='Replikations Standardwert',Updated=TO_TIMESTAMP('2018-10-09 14:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542394
;

-- 2018-10-09T14:57:22.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsReplicationLookupDefault', Name='Replikations Standardwert', Description=NULL, Help='Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.' WHERE AD_Element_ID=542394
;

-- 2018-10-09T14:57:22.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReplicationLookupDefault', Name='Replikations Standardwert', Description=NULL, Help='Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.', AD_Element_ID=542394 WHERE UPPER(ColumnName)='ISREPLICATIONLOOKUPDEFAULT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-09T14:57:22.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReplicationLookupDefault', Name='Replikations Standardwert', Description=NULL, Help='Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.' WHERE AD_Element_ID=542394 AND IsCentrallyMaintained='Y'
;

-- 2018-10-09T14:57:22.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Replikations Standardwert', Description=NULL, Help='Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542394) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542394)
;

-- 2018-10-09T14:57:22.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Replikations Standardwert', Name='Replikations Standardwert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542394)
;

-- 2018-10-09T14:57:35.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:57:35','YYYY-MM-DD HH24:MI:SS'),Name='Replication Lookup Default ' WHERE AD_Element_ID=542394 AND AD_Language='en_US'
;

-- 2018-10-09T14:57:35.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542394,'en_US') 
;

-- 2018-10-09T14:57:39.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:57:39','YYYY-MM-DD HH24:MI:SS'),Name='Replication Lookup Default' WHERE AD_Element_ID=542394 AND AD_Language='en_US'
;

-- 2018-10-09T14:57:39.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542394,'en_US') 
;

-- 2018-10-09T14:57:43.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 14:57:43','YYYY-MM-DD HH24:MI:SS'),PrintName='Replication Lookup Default' WHERE AD_Element_ID=542394 AND AD_Language='en_US'
;

-- 2018-10-09T14:57:43.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542394,'en_US') 
;

