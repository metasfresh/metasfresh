-- 2017-10-09T15:46:01.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:46:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Menu_ID=1000082 AND AD_Language='en_US'
;

-- 2017-10-09T15:46:41.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:46:41','YYYY-MM-DD HH24:MI:SS'),Name='GL Journal' WHERE AD_Window_ID=132 AND AD_Language='en_US'
;

-- 2017-10-09T15:49:16.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:49:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='GL Journal',Description='',WEBUI_NameBrowse='GL Journal' WHERE AD_Menu_ID=540905 AND AD_Language='en_US'
;

-- 2017-10-09T15:49:29.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=1000082
;

-- 2017-10-09T15:49:29.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=1000082
;

-- 2017-10-09T15:49:29.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=1000082 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2017-10-09T15:50:07.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:50:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='GL Journal',Description='' WHERE AD_Window_ID=540356 AND AD_Language='en_US'
;

-- 2017-10-09T15:51:15.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:51:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=559500 AND AD_Language='en_US'
;

-- 2017-10-09T15:51:30.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:51:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=559501 AND AD_Language='en_US'
;

-- 2017-10-09T15:51:50.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:51:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Accounting Schema',Description='',Help='' WHERE AD_Field_ID=559502 AND AD_Language='en_US'
;

-- 2017-10-09T15:52:05.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:52:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Journal Run',Description='',Help='' WHERE AD_Field_ID=559503 AND AD_Language='en_US'
;

-- 2017-10-09T15:52:23.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:52:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description' WHERE AD_Field_ID=559505 AND AD_Language='en_US'
;

-- 2017-10-09T15:52:34.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:52:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=559506 AND AD_Language='en_US'
;

-- 2017-10-09T15:52:56.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:52:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Posting Type',Description='',Help='' WHERE AD_Field_ID=559507 AND AD_Language='en_US'
;

-- 2017-10-09T15:53:02.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:53:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=559508 AND AD_Language='en_US'
;

-- 2017-10-09T15:53:14.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:53:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Doctype',Description='',Help='' WHERE AD_Field_ID=559509 AND AD_Language='en_US'
;

-- 2017-10-09T15:53:26.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:53:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='GL Category' WHERE AD_Field_ID=559510 AND AD_Language='en_US'
;

-- 2017-10-09T15:53:38.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:53:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Date',Description='' WHERE AD_Field_ID=559511 AND AD_Language='en_US'
;

-- 2017-10-09T15:53:50.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:53:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='DateAcct' WHERE AD_Field_ID=559512 AND AD_Language='en_US'
;

-- 2017-10-09T15:54:02.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:54:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Period',Description='',Help='' WHERE AD_Field_ID=559513 AND AD_Language='en_US'
;

-- 2017-10-09T15:54:13.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:54:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Currency',Description='',Help='' WHERE AD_Field_ID=559514 AND AD_Language='en_US'
;

-- 2017-10-09T15:54:32.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:54:32','YYYY-MM-DD HH24:MI:SS'),Name='Conversiontype',Description='',Help='' WHERE AD_Field_ID=559515 AND AD_Language='en_US'
;

-- 2017-10-09T15:54:34.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:54:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=559515 AND AD_Language='en_US'
;

-- 2017-10-09T15:54:47.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:54:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Currency Rate',Description='',Help='' WHERE AD_Field_ID=559516 AND AD_Language='en_US'
;

-- 2017-10-09T15:55:06.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:55:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Control Amount',Description='',Help='' WHERE AD_Field_ID=559517 AND AD_Language='en_US'
;

-- 2017-10-09T15:55:17.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:55:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Approved',Description='',Help='' WHERE AD_Field_ID=559518 AND AD_Language='en_US'
;

-- 2017-10-09T15:55:24.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:55:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=559519 AND AD_Language='en_US'
;

-- 2017-10-09T15:55:32.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:55:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=559520 AND AD_Language='en_US'
;

-- 2017-10-09T15:55:44.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:55:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Docstatus' WHERE AD_Field_ID=559521 AND AD_Language='en_US'
;

-- 2017-10-09T15:55:50.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:55:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=559523 AND AD_Language='en_US'
;

-- 2017-10-09T15:56:10.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:56:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='GL Journal',Description='GL Journal',Help='' WHERE AD_Field_ID=559496 AND AD_Language='en_US'
;

-- 2017-10-09T15:56:23.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:56:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Printed' WHERE AD_Field_ID=559497 AND AD_Language='en_US'
;

-- 2017-10-09T15:56:35.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:56:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Processed',Description='',Help='' WHERE AD_Field_ID=559498 AND AD_Language='en_US'
;

-- 2017-10-09T15:56:41.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:56:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=559499 AND AD_Language='en_US'
;

-- 2017-10-09T15:59:07.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 15:59:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Docaction',Description='' WHERE AD_Field_ID=559522 AND AD_Language='en_US'
;

-- 2017-10-09T16:03:58.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:03:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Docaction',Description='' WHERE AD_Field_ID=559522 AND AD_Language='en_US'
;

