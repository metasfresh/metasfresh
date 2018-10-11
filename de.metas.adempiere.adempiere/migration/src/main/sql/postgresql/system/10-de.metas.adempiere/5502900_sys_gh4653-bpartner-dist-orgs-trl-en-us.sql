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

