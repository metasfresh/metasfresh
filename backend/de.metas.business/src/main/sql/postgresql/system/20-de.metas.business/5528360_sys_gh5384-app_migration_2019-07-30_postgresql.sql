-- 2019-07-30T08:42:13.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=542776, ColumnName='QtyDeliveredInUOM', Description='Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)', Help=NULL, Name='Liefermenge',Updated=TO_TIMESTAMP('2019-07-30 10:42:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568475
;

-- 2019-07-30T08:42:13.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Liefermenge', Description='Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)', Help=NULL WHERE AD_Column_ID=568475
;

-- 2019-07-30T08:42:13.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542776) 
;

-- 2019-07-30T08:49:28.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Delivered',Updated=TO_TIMESTAMP('2019-07-30 10:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945
;

-- 2019-07-30T08:49:28.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Delivered', Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945
;

-- 2019-07-30T08:49:28.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Delivered', Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, AD_Element_ID=576945 WHERE UPPER(ColumnName)='DELIVERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T08:49:28.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Delivered', Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T08:49:54.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_Invoice_Candidate_Delivered',Updated=TO_TIMESTAMP('2019-07-30 10:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945
;

-- 2019-07-30T08:49:54.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Candidate_Delivered', Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945
;

-- 2019-07-30T08:49:54.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Delivered', Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, AD_Element_ID=576945 WHERE UPPER(ColumnName)='C_INVOICE_CANDIDATE_DELIVERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T08:49:54.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Delivered', Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T08:50:24.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.',Updated=TO_TIMESTAMP('2019-07-30 10:50:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_CH'
;

-- 2019-07-30T08:50:24.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_CH') 
;

-- 2019-07-30T08:50:29.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.',Updated=TO_TIMESTAMP('2019-07-30 10:50:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_DE'
;

-- 2019-07-30T08:50:29.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_DE') 
;

-- 2019-07-30T08:50:29.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576945,'de_DE') 
;

-- 2019-07-30T08:50:29.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Candidate_Delivered', Name='Geliefert', Description='Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945
;

-- 2019-07-30T08:50:29.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Delivered', Name='Geliefert', Description='Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, AD_Element_ID=576945 WHERE UPPER(ColumnName)='C_INVOICE_CANDIDATE_DELIVERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T08:50:29.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Delivered', Name='Geliefert', Description='Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T08:50:29.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert', Description='Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576945) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576945)
;

-- 2019-07-30T08:50:29.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geliefert', Description='Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-30T08:50:29.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geliefert', Description='Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-30T08:50:29.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geliefert', Description = 'Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-30T08:51:05.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effectively delivered quantity to base invoicing on; depends on whether catch weight invoicing is done.', Name='Delivered', PrintName='Delivered',Updated=TO_TIMESTAMP('2019-07-30 10:51:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='en_US'
;

-- 2019-07-30T08:51:05.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'en_US') 
;

-- 2019-07-30T08:51:11.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576945, Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Name='Geliefert',Updated=TO_TIMESTAMP('2019-07-30 10:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582482
;

-- 2019-07-30T08:51:11.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576945) 
;

-- 2019-07-30T08:51:11.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582482
;

-- 2019-07-30T08:51:11.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582482)
;

-- 2019-07-30T08:51:34.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effectively delivered quantity to base invoicing on; depends on whether catch weight invoicing is done. ',Updated=TO_TIMESTAMP('2019-07-30 10:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='en_US'
;

-- 2019-07-30T08:51:34.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'en_US') 
;

-- 2019-07-30T08:51:37.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effectively delivered quantity to base invoicing on; depends on whether catch weight invoicing is done.',Updated=TO_TIMESTAMP('2019-07-30 10:51:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='en_US'
;

-- 2019-07-30T08:51:37.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'en_US') 
;

-- 2019-07-30T08:52:15.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Description='Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.',Updated=TO_TIMESTAMP('2019-07-30 10:52:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582482
;

-- 2019-07-30T08:54:33.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyDeliveredInUOM NUMERIC')
;

-- 2019-07-30T08:55:14.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.invoicecandidate', IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-07-30 10:55:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582497
;

-- 2019-07-30T08:55:27.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.invoicecandidate', IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-07-30 10:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582498
;

