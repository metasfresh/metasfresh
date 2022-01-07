
-- 2022-01-07T08:53:17.248618200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', IsActive='Y', Name='de.metas.printing.pdf_file.whereClause.Example',Updated=TO_TIMESTAMP('2022-01-07 10:53:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541440
;


-- 2022-01-07T08:53:36.787479600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', IsActive='N',Updated=TO_TIMESTAMP('2022-01-07 10:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541440
;

-- 2022-01-07T09:01:30.219551500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Download PDF Anhang',Updated=TO_TIMESTAMP('2022-01-07 11:01:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584956
;

-- 2022-01-07T09:01:35.712492100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Download PDF Anhang',Updated=TO_TIMESTAMP('2022-01-07 11:01:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584956
;

-- 2022-01-07T09:01:37.504179300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Download PDF Anhang',Updated=TO_TIMESTAMP('2022-01-07 11:01:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584956
;

-- 2022-01-07T09:01:45.446872200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Download PDF Attachment',Updated=TO_TIMESTAMP('2022-01-07 11:01:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584956
;



-- 2022-01-07T09:10:14.084112600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Help='Product Categories parameter is used to filter the inovice candidates that will be invoiced. Will be filtered out the one that do ot have the product category given as parameter.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-01-07 11:10:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542166
;

-- 2022-01-07T09:10:31.274948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Help='Product Categories parameter is used to filter the invoice candidates that will be invoiced. Will be filtered out the one that do ot have the product category given as parameter.',Updated=TO_TIMESTAMP('2022-01-07 11:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542166
;

-- 2022-01-07T09:10:55.521742100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Help='Product Categories parameter is used to filter the invoice candidates that will be invoiced. Will be filtered out the ones that do not have the product category given as a parameter.',Updated=TO_TIMESTAMP('2022-01-07 11:10:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542166
;

-- 2022-01-07T09:11:06.844246100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Help='Der Parameter Produktkategorien wird verwendet, um die Rechnungskandidaten zu filtern, die in Rechnung gestellt werden. Es werden diejenigen herausgefiltert, bei denen die Produktkategorie nicht als Parameter angegeben ist.',Updated=TO_TIMESTAMP('2022-01-07 11:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542166
;

-- 2022-01-07T09:11:11.289021300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-01-07 11:11:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542166
;

-- 2022-01-07T09:11:17.970650100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Help='Der Parameter Produktkategorien wird verwendet, um die Rechnungskandidaten zu filtern, die in Rechnung gestellt werden. Es werden diejenigen herausgefiltert, bei denen die Produktkategorie nicht als Parameter angegeben ist.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-01-07 11:11:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542166
;

-- 2022-01-07T09:11:22.164906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Help='Der Parameter Produktkategorien wird verwendet, um die Rechnungskandidaten zu filtern, die in Rechnung gestellt werden. Es werden diejenigen herausgefiltert, bei denen die Produktkategorie nicht als Parameter angegeben ist.',Updated=TO_TIMESTAMP('2022-01-07 11:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542166
;
