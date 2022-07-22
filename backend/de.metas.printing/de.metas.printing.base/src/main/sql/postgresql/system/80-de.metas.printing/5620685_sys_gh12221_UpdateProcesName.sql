
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



-- 2022-01-07T09:14:16.735692100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='The process will enqueue the ICs given by the selection and will trigger after invoicing the concatenation of PDF invoices using the filters given by system configs starting with PREFIX de.metas.printing.pdf_file.whereClause.',Updated=TO_TIMESTAMP('2022-01-07 11:14:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584953
;

-- 2022-01-07T09:15:59.098467300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Der Prozess reiht die durch die Auswahl angegebenen ICs in die Warteschlange ein und löst nach der Rechnungsstellung die Verkettung von PDF-Rechnungen mit den von den Systemkonfigurationen vorgegebenen Filtern aus, beginnend mit PREFIX de.metas.printing.pdf_file.whereClause.',Updated=TO_TIMESTAMP('2022-01-07 11:15:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584953
;

-- 2022-01-07T09:16:02.085500700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Auswahl in Warteschlange für Rechnungsstellung und PDF-Verkettung stellen', Help='Der Prozess reiht die durch die Auswahl angegebenen ICs in die Warteschlange ein und löst nach der Rechnungsstellung die Verkettung von PDF-Rechnungen mit den von den Systemkonfigurationen vorgegebenen Filtern aus, beginnend mit PREFIX de.metas.printing.pdf_file.whereClause.', Name='Abrechnung starten',Updated=TO_TIMESTAMP('2022-01-07 11:16:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584953
;

-- 2022-01-07T09:16:02.079801100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Der Prozess reiht die durch die Auswahl angegebenen ICs in die Warteschlange ein und löst nach der Rechnungsstellung die Verkettung von PDF-Rechnungen mit den von den Systemkonfigurationen vorgegebenen Filtern aus, beginnend mit PREFIX de.metas.printing.pdf_file.whereClause.',Updated=TO_TIMESTAMP('2022-01-07 11:16:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584953
;

-- 2022-01-07T09:16:25.705073500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Will invoice the invoice candidate from the selection', Help='The process will enqueue the ICs given by the selection and will trigger after invoicing the concatenation of PDF invoices using the filters given by system configs starting with PREFIX de.metas.printing.pdf_file.whereClause.',Updated=TO_TIMESTAMP('2022-01-07 11:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584953
;

-- 2022-01-07T09:19:35.832284200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='This sys config is used de.metas.printing.model.validator.ConcatenatePDFsCommand for filter which printing queues to enqueue. The value of the sys config is a where clause that will be used in _C_Printing_Queue table. The part ''de.metas.printing.pdf_file.whereClause.'' is the prefix wich will be used for finding all posible filters.', IsActive='Y',Updated=TO_TIMESTAMP('2022-01-07 11:19:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541440
;




-- 2022-01-07T09:36:39.626213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='The process will enqueue the invoice canidates given by the parameters. Then will trigger after invoicing the concatenation of PDF invoices using the filters given by system configs starting with PREFIX de.metas.printing.pdf_file.whereClause.',Updated=TO_TIMESTAMP('2022-01-07 11:36:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584953
;

-- 2022-01-07T09:37:39.225913700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='The process will enqueue the billing candidates given by the parameters. When billing is done, will trigger  the concatenation of PDF invoices. For splitting in different PDF files will use the filters given by system configs starting with PREFIX de.metas.printing.pdf_file.whereClause.',Updated=TO_TIMESTAMP('2022-01-07 11:37:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584953
;

-- 2022-01-07T09:46:11.100875700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='',Updated=TO_TIMESTAMP('2022-01-07 11:46:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584953
;

-- 2022-01-07T09:46:16.424041900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='The process selects and enqueues the billing candidates according to the process parameters. 
When the invoices are created, it triggers the concatenation of the invoice-PDFs. To aggregate those PDFs into different files, it uses the filters specified in the system configs starting with the prefix de.metas.printing.pdf_file.whereClause.',Updated=TO_TIMESTAMP('2022-01-07 11:46:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584953
;

-- 2022-01-07T09:48:49.098688900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='The process selects and enqueues the billing candidates according to the process parameters. 
When the invoices are created, it triggers the concatenation of the invoice-PDFs. To aggregate those PDFs into different files, it uses the filters specified in the system configs starting with the prefix "de.metas.printing.pdf_file.whereClause.".',Updated=TO_TIMESTAMP('2022-01-07 11:48:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584953
;

-- 2022-01-07T09:49:07.313661700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Der Prozess wählt die Rechnungskandidaten entsprechend den Prozessparametern aus und stellt sie in die Warteschlange. 
Wenn die Rechnungen erstellt sind, löst er die Aggregation der einzelnen Rechnungs-PDFs in größere PDF-Dateien aus. 
Um diese PDFs in verschiedene Dateien zu aggregieren, verwendet er die in den Systemkonfigurationen festgelegten Filter, die mit dem Präfix "de.metas.printing.pdf_file.whereClause" beginnen.',Updated=TO_TIMESTAMP('2022-01-07 11:49:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584953
;

-- 2022-01-07T09:49:16.058350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Auswahl in Warteschlange für Rechnungsstellung und PDF-Verkettung stellen', Help='Der Prozess wählt die Rechnungskandidaten entsprechend den Prozessparametern aus und stellt sie in die Warteschlange. 
Wenn die Rechnungen erstellt sind, löst er die Aggregation der einzelnen Rechnungs-PDFs in größere PDF-Dateien aus. 
Um diese PDFs in verschiedene Dateien zu aggregieren, verwendet er die in den Systemkonfigurationen festgelegten Filter, die mit dem Präfix "de.metas.printing.pdf_file.whereClause." beginnen.', Name='Abrechnung starten',Updated=TO_TIMESTAMP('2022-01-07 11:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584953
;

-- 2022-01-07T09:49:16.050964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Der Prozess wählt die Rechnungskandidaten entsprechend den Prozessparametern aus und stellt sie in die Warteschlange. 
Wenn die Rechnungen erstellt sind, löst er die Aggregation der einzelnen Rechnungs-PDFs in größere PDF-Dateien aus. 
Um diese PDFs in verschiedene Dateien zu aggregieren, verwendet er die in den Systemkonfigurationen festgelegten Filter, die mit dem Präfix "de.metas.printing.pdf_file.whereClause." beginnen.',Updated=TO_TIMESTAMP('2022-01-07 11:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584953
;

-- 2022-01-07T09:49:22.913955700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Der Prozess wählt die Rechnungskandidaten entsprechend den Prozessparametern aus und stellt sie in die Warteschlange. 
Wenn die Rechnungen erstellt sind, löst er die Aggregation der einzelnen Rechnungs-PDFs in größere PDF-Dateien aus. 
Um diese PDFs in verschiedene Dateien zu aggregieren, verwendet er die in den Systemkonfigurationen festgelegten Filter, die mit dem Präfix "de.metas.printing.pdf_file.whereClause." beginnen.',Updated=TO_TIMESTAMP('2022-01-07 11:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584953
;

