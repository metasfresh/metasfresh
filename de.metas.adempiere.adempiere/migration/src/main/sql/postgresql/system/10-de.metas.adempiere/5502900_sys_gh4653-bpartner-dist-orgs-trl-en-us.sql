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

-- 2018-10-09T15:09:15.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:09:15','YYYY-MM-DD HH24:MI:SS'),Name='Address ' WHERE AD_Element_ID=505104 AND AD_Language='en_US'
;

-- 2018-10-09T15:09:15.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505104,'en_US') 
;

-- 2018-10-09T15:09:18.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:09:18','YYYY-MM-DD HH24:MI:SS'),Name='Address' WHERE AD_Element_ID=505104 AND AD_Language='en_US'
;

-- 2018-10-09T15:09:18.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505104,'en_US') 
;

-- 2018-10-09T15:09:41.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:09:41','YYYY-MM-DD HH24:MI:SS'),Name='Is Ship To Default ' WHERE AD_Element_ID=540412 AND AD_Language='en_US'
;

-- 2018-10-09T15:09:41.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540412,'en_US') 
;

-- 2018-10-09T15:09:48.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:09:48','YYYY-MM-DD HH24:MI:SS'),Name='Is Ship To Default',PrintName='Is Ship To Default' WHERE AD_Element_ID=540412 AND AD_Language='en_US'
;

-- 2018-10-09T15:09:48.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540412,'en_US') 
;

-- 2018-10-09T15:10:07.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:10:07','YYYY-MM-DD HH24:MI:SS'),PrintName='Address' WHERE AD_Element_ID=505104 AND AD_Language='en_US'
;

-- 2018-10-09T15:10:07.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505104,'en_US') 
;

-- 2018-10-09T15:10:33.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:10:33','YYYY-MM-DD HH24:MI:SS'),PrintName='Is Invoice Default' WHERE AD_Element_ID=540413 AND AD_Language='en_US'
;

-- 2018-10-09T15:10:33.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540413,'en_US') 
;

-- 2018-10-09T15:18:01.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:18:01','YYYY-MM-DD HH24:MI:SS'),PrintName='Pricing System' WHERE AD_Element_ID=505135 AND AD_Language='en_US'
;

-- 2018-10-09T15:18:01.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505135,'en_US') 
;

-- 2018-10-09T15:18:32.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:18:32','YYYY-MM-DD HH24:MI:SS'),PrintName='Aggregation for Sales Invoices' WHERE AD_Element_ID=542719 AND AD_Language='en_US'
;

-- 2018-10-09T15:18:32.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542719,'en_US') 
;

-- 2018-10-09T15:18:52.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:18:52','YYYY-MM-DD HH24:MI:SS'),PrintName='Aggregation for Sales Invoice Lines' WHERE AD_Element_ID=542849 AND AD_Language='en_US'
;

-- 2018-10-09T15:18:52.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542849,'en_US') 
;

-- 2018-10-09T15:19:17.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:19:17','YYYY-MM-DD HH24:MI:SS'),PrintName='Consolidate Shipments allowed' WHERE AD_Element_ID=502393 AND AD_Language='en_US'
;

-- 2018-10-09T15:19:17.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(502393,'en_US') 
;

-- 2018-10-09T15:19:38.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:19:38','YYYY-MM-DD HH24:MI:SS'),PrintName='ADR Customer' WHERE AD_Element_ID=542343 AND AD_Language='en_US'
;

-- 2018-10-09T15:19:38.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542343,'en_US') 
;

-- 2018-10-09T15:20:05.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:20:05','YYYY-MM-DD HH24:MI:SS'),PrintName='Disable Order Checkup Report' WHERE AD_Element_ID=542907 AND AD_Language='en_US'
;

-- 2018-10-09T15:20:05.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542907,'en_US') 
;

-- 2018-10-09T15:20:30.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:20:30','YYYY-MM-DD HH24:MI:SS'),PrintName='Hide Packing Material in Shipment' WHERE AD_Element_ID=542803 AND AD_Language='en_US'
;

-- 2018-10-09T15:20:30.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542803,'en_US') 
;

-- 2018-10-09T15:21:19.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:21:19','YYYY-MM-DD HH24:MI:SS'),PrintName='Customer Group' WHERE AD_Element_ID=542888 AND AD_Language='en_US'
;

-- 2018-10-09T15:21:19.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542888,'en_US') 
;

-- 2018-10-09T15:22:49.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:22:49','YYYY-MM-DD HH24:MI:SS'),PrintName='Delivery Via Rule' WHERE AD_Element_ID=542451 AND AD_Language='en_US'
;

-- 2018-10-09T15:22:49.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542451,'en_US') 
;

-- 2018-10-09T15:23:22.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:23:22','YYYY-MM-DD HH24:MI:SS'),PrintName='Purchase Pricing System' WHERE AD_Element_ID=505274 AND AD_Language='en_US'
;

-- 2018-10-09T15:23:22.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(505274,'en_US') 
;

-- 2018-10-09T15:24:02.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:24:02','YYYY-MM-DD HH24:MI:SS'),PrintName='Farming Producer' WHERE AD_Element_ID=542256 AND AD_Language='en_US'
;

-- 2018-10-09T15:24:02.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542256,'en_US') 
;

-- 2018-10-09T15:25:35.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:25:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Aggregation for Purchase Invoices',PrintName='Aggregation for Purchase Invoices',Description='' WHERE AD_Element_ID=542718 AND AD_Language='en_US'
;

-- 2018-10-09T15:25:35.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542718,'en_US') 
;

-- 2018-10-09T15:26:05.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:26:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Aggregation for Purchase Invoice Lines',PrintName='Aggregation for Purchase Invoice Lines' WHERE AD_Element_ID=542848 AND AD_Language='en_US'
;

-- 2018-10-09T15:26:05.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542848,'en_US') 
;

-- 2018-10-09T15:27:45.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:27:45','YYYY-MM-DD HH24:MI:SS'),Name='Active ' WHERE AD_Element_ID=348 AND AD_Language='en_US'
;

-- 2018-10-09T15:27:45.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(348,'en_US') 
;

-- 2018-10-09T15:27:48.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:27:48','YYYY-MM-DD HH24:MI:SS'),Name='Active' WHERE AD_Element_ID=348 AND AD_Language='en_US'
;

-- 2018-10-09T15:27:48.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(348,'en_US') 
;

-- 2018-10-09T15:29:02.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:29:02','YYYY-MM-DD HH24:MI:SS'),Name='Default Contact',PrintName='Default Contact' WHERE AD_Element_ID=500035 AND AD_Language='en_US'
;

-- 2018-10-09T15:29:02.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(500035,'en_US') 
;

-- 2018-10-09T15:29:47.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:29:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Christmas Gift',PrintName='Christmas Gift' WHERE AD_Element_ID=542245 AND AD_Language='en_US'
;

-- 2018-10-09T15:29:47.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542245,'en_US') 
;

-- 2018-10-09T15:30:07.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-09 15:30:07','YYYY-MM-DD HH24:MI:SS'),PrintName='Christmas Gift' WHERE AD_Element_ID=542543 AND AD_Language='en_US'
;

-- 2018-10-09T15:30:07.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542543,'en_US') 
;

-- 2018-10-11T10:48:47.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:48:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544129 AND AD_Language='en_US'
;

-- 2018-10-11T10:48:47.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544129,'en_US') 
;

-- 2018-10-11T10:49:30.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:49:30','YYYY-MM-DD HH24:MI:SS'),Name='Active ' WHERE AD_Element_ID=348 AND AD_Language='en_US'
;

-- 2018-10-11T10:49:30.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(348,'en_US') 
;

-- 2018-10-11T10:49:34.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:49:34','YYYY-MM-DD HH24:MI:SS'),Name='Active' WHERE AD_Element_ID=348 AND AD_Language='en_US'
;

-- 2018-10-11T10:49:34.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(348,'en_US') 
;

-- 2018-10-11T10:50:16.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:50:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Mandant ' WHERE AD_Element_ID=102 AND AD_Language='fr_CH'
;

-- 2018-10-11T10:50:16.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(102,'fr_CH') 
;

-- 2018-10-11T10:50:29.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:50:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='N' WHERE AD_Element_ID=102 AND AD_Language='fr_CH'
;

-- 2018-10-11T10:50:29.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(102,'fr_CH') 
;

-- 2018-10-11T10:50:45.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:50:45','YYYY-MM-DD HH24:MI:SS'),Name='Client ' WHERE AD_Element_ID=102 AND AD_Language='en_US'
;

-- 2018-10-11T10:50:45.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(102,'en_US') 
;

-- 2018-10-11T10:50:49.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:50:49','YYYY-MM-DD HH24:MI:SS'),Name='Client' WHERE AD_Element_ID=102 AND AD_Language='en_US'
;

-- 2018-10-11T10:50:49.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(102,'en_US') 
;

-- 2018-10-11T10:51:11.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:51:11','YYYY-MM-DD HH24:MI:SS'),Name='Organisation ' WHERE AD_Element_ID=113 AND AD_Language='en_US'
;

-- 2018-10-11T10:51:11.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(113,'en_US') 
;

-- 2018-10-11T10:51:14.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:51:14','YYYY-MM-DD HH24:MI:SS'),Name='Organisation' WHERE AD_Element_ID=113 AND AD_Language='en_US'
;

-- 2018-10-11T10:51:14.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(113,'en_US') 
;

-- 2018-10-11T10:57:12.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:57:12','YYYY-MM-DD HH24:MI:SS'),Name='ESR Receiver ' WHERE AD_Element_ID=541924 AND AD_Language='en_US'
;

-- 2018-10-11T10:57:12.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541924,'en_US') 
;

-- 2018-10-11T10:57:15.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:57:15','YYYY-MM-DD HH24:MI:SS'),Name='ESR Receiver' WHERE AD_Element_ID=541924 AND AD_Language='en_US'
;

-- 2018-10-11T10:57:15.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541924,'en_US') 
;

-- 2018-10-11T10:57:41.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:57:41','YYYY-MM-DD HH24:MI:SS'),PrintName='ESR Participant' WHERE AD_Element_ID=541925 AND AD_Language='en_US'
;

-- 2018-10-11T10:57:41.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541925,'en_US') 
;

-- 2018-10-11T10:58:02.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:58:02','YYYY-MM-DD HH24:MI:SS'),Name='ESR Receiver ' WHERE AD_Element_ID=541924 AND AD_Language='en_US'
;

-- 2018-10-11T10:58:02.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541924,'en_US') 
;

-- 2018-10-11T10:58:05.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:58:05','YYYY-MM-DD HH24:MI:SS'),Name='ESR Receiver' WHERE AD_Element_ID=541924 AND AD_Language='en_US'
;

-- 2018-10-11T10:58:05.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541924,'en_US') 
;

-- 2018-10-11T10:59:02.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 10:59:02','YYYY-MM-DD HH24:MI:SS'),PrintName='ESR Account' WHERE AD_Element_ID=541939 AND AD_Language='en_US'
;

-- 2018-10-11T10:59:02.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541939,'en_US') 
;

-- 2018-10-11T11:05:14.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:05:14','YYYY-MM-DD HH24:MI:SS'),Name='Product ' WHERE AD_Element_ID=454 AND AD_Language='en_US'
;

-- 2018-10-11T11:05:14.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(454,'en_US') 
;

-- 2018-10-11T11:05:18.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:05:18','YYYY-MM-DD HH24:MI:SS'),Name='Product' WHERE AD_Element_ID=454 AND AD_Language='en_US'
;

-- 2018-10-11T11:05:18.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(454,'en_US') 
;

-- 2018-10-11T11:07:07.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt Hersteller',Updated=TO_TIMESTAMP('2018-10-11 11:07:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541155
;

-- 2018-10-11T11:07:28.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:07:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product Manufacturer' WHERE AD_Tab_ID=541155 AND AD_Language='en_US'
;

-- 2018-10-11T11:11:45.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Hersteller ',Updated=TO_TIMESTAMP('2018-10-11 11:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544128
;

-- 2018-10-11T11:11:45.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Manufacturer_ID', Name='Hersteller ', Description='Hersteller des Produktes', Help='' WHERE AD_Element_ID=544128
;

-- 2018-10-11T11:11:45.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Manufacturer_ID', Name='Hersteller ', Description='Hersteller des Produktes', Help='', AD_Element_ID=544128 WHERE UPPER(ColumnName)='MANUFACTURER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-11T11:11:45.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Manufacturer_ID', Name='Hersteller ', Description='Hersteller des Produktes', Help='' WHERE AD_Element_ID=544128 AND IsCentrallyMaintained='Y'
;

-- 2018-10-11T11:11:45.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Hersteller ', Description='Hersteller des Produktes', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544128) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544128)
;

-- 2018-10-11T11:11:45.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Hersteller', Name='Hersteller ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544128)
;

-- 2018-10-11T11:11:48.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Hersteller',Updated=TO_TIMESTAMP('2018-10-11 11:11:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544128
;

-- 2018-10-11T11:11:48.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Manufacturer_ID', Name='Hersteller', Description='Hersteller des Produktes', Help='' WHERE AD_Element_ID=544128
;

-- 2018-10-11T11:11:48.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Manufacturer_ID', Name='Hersteller', Description='Hersteller des Produktes', Help='', AD_Element_ID=544128 WHERE UPPER(ColumnName)='MANUFACTURER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-11T11:11:48.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Manufacturer_ID', Name='Hersteller', Description='Hersteller des Produktes', Help='' WHERE AD_Element_ID=544128 AND IsCentrallyMaintained='Y'
;

-- 2018-10-11T11:11:48.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Hersteller', Description='Hersteller des Produktes', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544128) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544128)
;

-- 2018-10-11T11:11:48.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Hersteller', Name='Hersteller' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544128)
;

-- 2018-10-11T11:12:03.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:12:03','YYYY-MM-DD HH24:MI:SS'),PrintName='Manufacturer' WHERE AD_Element_ID=544128 AND AD_Language='en_US'
;

-- 2018-10-11T11:12:03.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544128,'en_US') 
;

-- 2018-10-11T11:13:11.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Beschreibung ',Updated=TO_TIMESTAMP('2018-10-11 11:13:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275
;

-- 2018-10-11T11:13:11.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Description', Name='Beschreibung ', Description=NULL, Help=NULL WHERE AD_Element_ID=275
;

-- 2018-10-11T11:13:11.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung ', Description=NULL, Help=NULL, AD_Element_ID=275 WHERE UPPER(ColumnName)='DESCRIPTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-11T11:13:11.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung ', Description=NULL, Help=NULL WHERE AD_Element_ID=275 AND IsCentrallyMaintained='Y'
;

-- 2018-10-11T11:13:11.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beschreibung ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=275) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 275)
;

-- 2018-10-11T11:13:11.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beschreibung', Name='Beschreibung ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=275)
;

-- 2018-10-11T11:13:14.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Beschreibung',Updated=TO_TIMESTAMP('2018-10-11 11:13:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275
;

-- 2018-10-11T11:13:14.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Description', Name='Beschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID=275
;

-- 2018-10-11T11:13:14.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung', Description=NULL, Help=NULL, AD_Element_ID=275 WHERE UPPER(ColumnName)='DESCRIPTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-11T11:13:14.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID=275 AND IsCentrallyMaintained='Y'
;

-- 2018-10-11T11:13:14.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beschreibung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=275) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 275)
;

-- 2018-10-11T11:13:14.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beschreibung', Name='Beschreibung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=275)
;

-- 2018-10-11T11:13:24.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:13:24','YYYY-MM-DD HH24:MI:SS'),Name='Description ' WHERE AD_Element_ID=275 AND AD_Language='en_US'
;

-- 2018-10-11T11:13:24.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'en_US') 
;

-- 2018-10-11T11:13:27.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:13:27','YYYY-MM-DD HH24:MI:SS'),Name='Description' WHERE AD_Element_ID=275 AND AD_Language='en_US'
;

-- 2018-10-11T11:13:27.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'en_US') 
;

-- 2018-10-11T11:16:10.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Sperre Verkauf Grund', PrintName='Sperre Verkauf Grund',Updated=TO_TIMESTAMP('2018-10-11 11:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543956
;

-- 2018-10-11T11:16:10.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExclusionFromSaleReason', Name='Sperre Verkauf Grund', Description=NULL, Help=NULL WHERE AD_Element_ID=543956
;

-- 2018-10-11T11:16:10.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromSaleReason', Name='Sperre Verkauf Grund', Description=NULL, Help=NULL, AD_Element_ID=543956 WHERE UPPER(ColumnName)='EXCLUSIONFROMSALEREASON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-11T11:16:10.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromSaleReason', Name='Sperre Verkauf Grund', Description=NULL, Help=NULL WHERE AD_Element_ID=543956 AND IsCentrallyMaintained='Y'
;

-- 2018-10-11T11:16:10.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sperre Verkauf Grund', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543956) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543956)
;

-- 2018-10-11T11:16:10.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sperre Verkauf Grund', Name='Sperre Verkauf Grund' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543956)
;

-- 2018-10-11T11:16:21.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:16:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543956 AND AD_Language='en_US'
;

-- 2018-10-11T11:16:21.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543956,'en_US') 
;

-- 2018-10-11T11:19:50.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:19:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Marketing Approval' WHERE AD_Tab_ID=541096 AND AD_Language='en_US'
;

-- 2018-10-11T11:20:22.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Beschreibung ',Updated=TO_TIMESTAMP('2018-10-11 11:20:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275
;

-- 2018-10-11T11:20:22.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Description', Name='Beschreibung ', Description=NULL, Help=NULL WHERE AD_Element_ID=275
;

-- 2018-10-11T11:20:22.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung ', Description=NULL, Help=NULL, AD_Element_ID=275 WHERE UPPER(ColumnName)='DESCRIPTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-11T11:20:22.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung ', Description=NULL, Help=NULL WHERE AD_Element_ID=275 AND IsCentrallyMaintained='Y'
;

-- 2018-10-11T11:20:22.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beschreibung ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=275) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 275)
;

-- 2018-10-11T11:20:22.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beschreibung', Name='Beschreibung ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=275)
;

-- 2018-10-11T11:20:25.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Beschreibung',Updated=TO_TIMESTAMP('2018-10-11 11:20:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275
;

-- 2018-10-11T11:20:25.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Description', Name='Beschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID=275
;

-- 2018-10-11T11:20:25.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung', Description=NULL, Help=NULL, AD_Element_ID=275 WHERE UPPER(ColumnName)='DESCRIPTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-11T11:20:25.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Description', Name='Beschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID=275 AND IsCentrallyMaintained='Y'
;

-- 2018-10-11T11:20:25.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beschreibung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=275) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 275)
;

-- 2018-10-11T11:20:25.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beschreibung', Name='Beschreibung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=275)
;

-- 2018-10-11T11:20:36.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:20:36','YYYY-MM-DD HH24:MI:SS'),Name='Description ' WHERE AD_Element_ID=275 AND AD_Language='en_US'
;

-- 2018-10-11T11:20:36.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'en_US') 
;

-- 2018-10-11T11:20:39.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:20:39','YYYY-MM-DD HH24:MI:SS'),Name='Description' WHERE AD_Element_ID=275 AND AD_Language='en_US'
;

-- 2018-10-11T11:20:39.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'en_US') 
;

-- 2018-10-11T11:21:07.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:21:07','YYYY-MM-DD HH24:MI:SS'),Name='Contact ' WHERE AD_Element_ID=138 AND AD_Language='en_US'
;

-- 2018-10-11T11:21:07.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(138,'en_US') 
;

-- 2018-10-11T11:21:10.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:21:10','YYYY-MM-DD HH24:MI:SS'),Name='Contact' WHERE AD_Element_ID=138 AND AD_Language='en_US'
;

-- 2018-10-11T11:21:10.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(138,'en_US') 
;

-- 2018-10-11T11:21:22.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:21:22','YYYY-MM-DD HH24:MI:SS'),Name='Business Partner ' WHERE AD_Element_ID=187 AND AD_Language='en_US'
;

-- 2018-10-11T11:21:22.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(187,'en_US') 
;

-- 2018-10-11T11:21:25.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:21:25','YYYY-MM-DD HH24:MI:SS'),Name='Business Partner' WHERE AD_Element_ID=187 AND AD_Language='en_US'
;

-- 2018-10-11T11:21:25.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(187,'en_US') 
;

-- 2018-10-11T11:21:53.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:21:53','YYYY-MM-DD HH24:MI:SS'),PrintName='Agreement from' WHERE AD_Element_ID=544042 AND AD_Language='en_US'
;

-- 2018-10-11T11:21:53.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544042,'en_US') 
;

-- 2018-10-11T11:22:38.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:22:38','YYYY-MM-DD HH24:MI:SS'),PrintName='Agreement cancelled on' WHERE AD_Element_ID=544043 AND AD_Language='en_US'
;

-- 2018-10-11T11:22:38.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544043,'en_US') 
;

-- 2018-10-11T11:23:33.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:23:33','YYYY-MM-DD HH24:MI:SS'),Name='Marketing Platform Gateway ID ' WHERE AD_Element_ID=544040 AND AD_Language='en_US'
;

-- 2018-10-11T11:23:33.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544040,'en_US') 
;

-- 2018-10-11T11:23:40.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:23:40','YYYY-MM-DD HH24:MI:SS'),Name='Marketing Platform Gateway ID',PrintName='Marketing Platform Gateway ID' WHERE AD_Element_ID=544040 AND AD_Language='en_US'
;

-- 2018-10-11T11:23:40.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544040,'en_US') 
;

-- 2018-10-11T11:24:15.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 11:24:15','YYYY-MM-DD HH24:MI:SS'),PrintName='Marketing Contact Person' WHERE AD_Element_ID=544035 AND AD_Language='en_US'
;

-- 2018-10-11T11:24:15.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544035,'en_US') 
;

-- 2018-10-11T11:26:22.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541096,540904,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-10-11T11:26:22.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540904 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-10-11T11:26:22.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541180,540904,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541180,541887,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564026,0,541096,541887,553915,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564027,0,541096,541887,553916,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564028,0,541096,541887,553917,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,30,0,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564030,0,541096,541887,553918,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Ansprechpartner',0,40,0,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564031,0,541096,541887,553919,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','MKTG_ContactPerson',0,50,0,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564032,0,541096,541887,553920,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Marketing Platform GatewayId',0,60,0,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564033,0,541096,541887,553921,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','N','Y','N','Einverständnis erklärt am',0,70,0,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564034,0,541096,541887,553922,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Einverständnis widerrufen am',0,80,0,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,564035,0,540290,553918,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:22.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564036,0,541096,541887,553923,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,90,0,TO_TIMESTAMP('2018-10-11 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-11T11:26:59.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-10-11 11:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553915
;

-- 2018-10-11T11:26:59.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-10-11 11:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553918
;

-- 2018-10-11T11:26:59.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-10-11 11:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553919
;

-- 2018-10-11T11:26:59.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-10-11 11:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553920
;

-- 2018-10-11T11:26:59.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-10-11 11:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553921
;

-- 2018-10-11T11:26:59.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-10-11 11:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553922
;

-- 2018-10-11T11:26:59.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-10-11 11:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553923
;

-- 2018-10-11T11:26:59.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-10-11 11:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553917
;

-- 2018-10-11T11:26:59.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-10-11 11:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553916
;

-- 2018-10-11T11:27:20.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-10-11 11:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553918
;

-- 2018-10-11T11:27:25.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-10-11 11:27:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553919
;

-- 2018-10-11T11:27:27.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-10-11 11:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553920
;

-- 2018-10-11T11:27:28.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-10-11 11:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553921
;

-- 2018-10-11T11:27:30.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-10-11 11:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553922
;

-- 2018-10-11T11:27:31.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-10-11 11:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553923
;

-- 2018-10-11T11:27:33.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2018-10-11 11:27:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553917
;

-- 2018-10-11T11:27:35.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2018-10-11 11:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553916
;

-- 2018-10-11T11:27:38.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2018-10-11 11:27:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553915
;

-- 2018-10-11T11:28:11.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-10-11 11:28:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553915
;

-- 2018-10-11T11:30:10.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-10-11 11:30:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553915
;

-- 2018-10-11T11:30:37.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-10-11 11:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553916
;

-- 2018-10-11T11:30:38.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2018-10-11 11:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553915
;

-- 2018-10-11T11:30:38.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-10-11 11:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553918
;

-- 2018-10-11T11:30:39.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-10-11 11:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553919
;

-- 2018-10-11T11:30:39.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-10-11 11:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553920
;

-- 2018-10-11T11:30:40.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-10-11 11:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553921
;

-- 2018-10-11T11:30:40.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-10-11 11:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553922
;

-- 2018-10-11T11:30:41.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-10-11 11:30:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553923
;

-- 2018-10-11T11:30:42.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-10-11 11:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553917
;

