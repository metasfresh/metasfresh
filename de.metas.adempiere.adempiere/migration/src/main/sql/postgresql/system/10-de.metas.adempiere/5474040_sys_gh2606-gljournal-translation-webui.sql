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

-- 2017-10-09T16:10:04.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:10:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Line' WHERE AD_Tab_ID=540855 AND AD_Language='en_US'
;

-- 2017-10-09T16:11:59.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:11:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description' WHERE AD_Field_ID=559541 AND AD_Language='en_US'
;

-- 2017-10-09T16:12:17.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:12:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='GL Journal',Description='',Help='' WHERE AD_Field_ID=559540 AND AD_Language='en_US'
;

-- 2017-10-09T16:13:00.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:13:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Debit',Description='',Help='' WHERE AD_Field_ID=559563 AND AD_Language='en_US'
;

-- 2017-10-09T16:13:28.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:13:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Credit',Description='',Help='' WHERE AD_Field_ID=559564 AND AD_Language='en_US'
;

-- 2017-10-09T16:14:10.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:14:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Line No.',Description='' WHERE AD_Field_ID=559542 AND AD_Language='en_US'
;

-- 2017-10-09T16:14:42.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:14:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description' WHERE AD_Field_ID=559543 AND AD_Language='en_US'
;

-- 2017-10-09T16:15:05.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:15:05','YYYY-MM-DD HH24:MI:SS'),Name='Description (GL Journal)' WHERE AD_Field_ID=559541 AND AD_Language='en_US'
;

-- 2017-10-09T16:15:30.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:15:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Debit Account' WHERE AD_Field_ID=559551 AND AD_Language='en_US'
;

-- 2017-10-09T16:15:45.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:15:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Credit Account' WHERE AD_Field_ID=559552 AND AD_Language='de_CH'
;

-- 2017-10-09T16:16:05.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:16:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=559539 AND AD_Language='en_US'
;

-- 2017-10-09T16:16:38.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:16:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Taxamount (Credit)',Description='',Help='' WHERE AD_Field_ID=559561 AND AD_Language='en_US'
;

-- 2017-10-09T16:17:15.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:17:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sum Credit (Group)' WHERE AD_Field_ID=559568 AND AD_Language='en_US'
;

-- 2017-10-09T16:17:52.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:17:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Journal Line (Group)' WHERE AD_Field_ID=559569 AND AD_Language='en_US'
;

-- 2017-10-09T16:18:17.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:18:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=559544 AND AD_Language='en_US'
;

-- 2017-10-09T16:18:45.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:18:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Conversion Type',Description='',Help='' WHERE AD_Field_ID=559549 AND AD_Language='en_US'
;

-- 2017-10-09T16:19:14.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:19:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Split Accounting' WHERE AD_Field_ID=559547 AND AD_Language='en_US'
;

-- 2017-10-09T16:20:08.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:20:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Tax Account (Debit)',Description='',Help='' WHERE AD_Field_ID=559554 AND AD_Language='en_US'
;

-- 2017-10-09T16:20:49.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:20:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Tax Base Amount (Debit)',Description='',Help='' WHERE AD_Field_ID=559555 AND AD_Language='en_US'
;

-- 2017-10-09T16:21:38.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:21:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Taxamount (Debit)',Description='',Help='' WHERE AD_Field_ID=559556 AND AD_Language='en_US'
;

-- 2017-10-09T16:22:38.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:22:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sum Credit (Group)' WHERE AD_Field_ID=559567 AND AD_Language='en_US'
;

-- 2017-10-09T16:22:52.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:22:52','YYYY-MM-DD HH24:MI:SS'),Name='Sum Debit (Group)' WHERE AD_Field_ID=559567 AND AD_Language='en_US'
;

-- 2017-10-09T16:23:17.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:23:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Currency',Description='',Help='' WHERE AD_Field_ID=559546 AND AD_Language='en_US'
;

-- 2017-10-09T16:24:06.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:24:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Currency',Description='',Help='' WHERE AD_Field_ID=559550 AND AD_Language='en_US'
;

-- 2017-10-09T16:24:31.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:24:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Activity',Description='',Help='' WHERE AD_Field_ID=559570 AND AD_Language='en_US'
;

-- 2017-10-09T16:24:53.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:24:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Debit',Description='',Help='' WHERE AD_Field_ID=559565 AND AD_Language='en_US'
;

-- 2017-10-09T16:26:32.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:26:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Tax Account (Debit)' WHERE AD_Field_ID=559553 AND AD_Language='en_US'
;

-- 2017-10-09T16:27:06.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:27:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Accounting Date' WHERE AD_Field_ID=559548 AND AD_Language='en_US'
;

-- 2017-10-09T16:27:31.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:27:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Credit',Description='',Help='' WHERE AD_Field_ID=559566 AND AD_Language='en_US'
;

-- 2017-10-09T16:29:03.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:29:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=559558 AND AD_Language='en_US'
;

-- 2017-10-09T16:29:44.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:29:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sum (Debit)' WHERE AD_Field_ID=559557 AND AD_Language='en_US'
;

-- 2017-10-09T16:30:15.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:30:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sum (Credit)',Description='',Help='' WHERE AD_Field_ID=559559 AND AD_Language='en_US'
;

-- 2017-10-09T16:30:57.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:30:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sum (Credit)' WHERE AD_Field_ID=559562 AND AD_Language='en_US'
;

-- 2017-10-09T16:31:27.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:31:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Tax Base Amt (Credit)',Description='',Help='' WHERE AD_Field_ID=559560 AND AD_Language='en_US'
;

-- 2017-10-09T16:32:00.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:32:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=559538 AND AD_Language='en_US'
;

-- 2017-10-09T16:35:23.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:35:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Credit Account' WHERE AD_Field_ID=559552 AND AD_Language='en_US'
;

-- 2017-10-09T16:35:50.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:35:50','YYYY-MM-DD HH24:MI:SS'),Name='Currency Rate' WHERE AD_Field_ID=559550 AND AD_Language='en_US'
;

-- 2017-10-09T16:37:14.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:37:14','YYYY-MM-DD HH24:MI:SS'),Name='Debit Source' WHERE AD_Field_ID=559563 AND AD_Language='en_US'
;

-- 2017-10-09T16:37:28.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-09 16:37:28','YYYY-MM-DD HH24:MI:SS'),Name='Credit Source' WHERE AD_Field_ID=559564 AND AD_Language='en_US'
;

-- 2017-10-09T16:55:02.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-10-09 16:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547476
;

-- 2017-10-09T16:55:02.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-10-09 16:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547520
;

-- 2017-10-09T16:55:02.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-10-09 16:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547522
;

-- 2017-10-09T16:55:02.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-10-09 16:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547523
;

-- 2017-10-09T16:55:02.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-10-09 16:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547521
;

-- 2017-10-09T16:55:02.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-10-09 16:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547531
;

-- 2017-10-09T16:55:02.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-10-09 16:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547528
;

-- 2017-10-09T16:55:46.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-10-09 16:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547519
;

-- 2017-10-09T16:55:46.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-10-09 16:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547476
;

-- 2017-10-09T16:55:46.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-10-09 16:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547520
;

-- 2017-10-09T16:55:46.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-10-09 16:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547522
;

-- 2017-10-09T16:55:46.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-10-09 16:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547523
;

-- 2017-10-09T16:55:46.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-10-09 16:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547521
;

-- 2017-10-09T16:55:46.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-10-09 16:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547531
;

-- 2017-10-09T16:55:46.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-10-09 16:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547528
;

-- 2017-10-09T16:56:11.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2017-10-09 16:56:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559511
;

-- 2017-10-09T16:56:22.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-10-09 16:56:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559504
;

-- 2017-10-09T16:57:54.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2017-10-09 16:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1634
;

-- 2017-10-09T16:57:56.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2017-10-09 16:57:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1635
;

-- 2017-10-09T16:58:42.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2017-10-09 16:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1634
;

-- 2017-10-09T16:58:42.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2017-10-09 16:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1626
;

-- 2017-10-09T16:58:42.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2017-10-09 16:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1631
;

-- 2017-10-09T16:58:42.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2017-10-09 16:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1635
;

-- 2017-10-09T16:58:42.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2017-10-09 16:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1636
;

-- 2017-10-09T16:58:42.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2017-10-09 16:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1619
;

