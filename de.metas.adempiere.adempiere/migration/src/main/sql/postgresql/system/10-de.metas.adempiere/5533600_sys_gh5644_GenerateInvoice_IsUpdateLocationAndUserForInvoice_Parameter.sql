-- 2019-10-08T11:34:06.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577153,0,'IsUpdateLocationAndContactForInvoice',TO_TIMESTAMP('2019-10-08 14:34:06','YYYY-MM-DD HH24:MI:SS'),100,'When this parameter is set on true, the invoices that are to be created will get the current users and locations of their business partners, regardless of the users and locations set in the enquewed invoice candidates. The values in the invoice candidates will not be updated.','D','Y','Update Location and Contact for Invoice','Update Location and Contact for Invoice',TO_TIMESTAMP('2019-10-08 14:34:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-08T11:34:06.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577153 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-08T11:42:53.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577153,0,540304,541615,20,'IsUpdateLocationAndContactForInvoice',TO_TIMESTAMP('2019-10-08 14:42:53','YYYY-MM-DD HH24:MI:SS'),100,'N','When this parameter is set on true, the invoices that are to be created will get the current users and locations of their business partners, regardless of the users and locations set in the enquewed invoice candidates. The values in the invoice candidates','de.metas.invoicecandidate',0,'Y','N','Y','N','Y','N','Update Location and Contact for Invoice',80,TO_TIMESTAMP('2019-10-08 14:42:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-08T11:42:53.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541615 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-10-08T11:44:22.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2019-10-08 14:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='de_CH'
;

-- 2019-10-08T11:44:22.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'de_CH') 
;

-- 2019-10-08T11:45:58.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='When this parameter is set on true, the invoices that are to be created will get the current users and locations of their business partners, regardless of the values set in the enqueued invoice candidates. The invoice candidates will suffer no update.',Updated=TO_TIMESTAMP('2019-10-08 14:45:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='en_US'
;

-- 2019-10-08T11:45:58.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'en_US') 
;

-- 2019-10-08T11:46:18.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-08 14:46:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='en_US'
;

-- 2019-10-08T11:46:18.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'en_US') 
;

-- 2019-10-09T11:34:03.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='When this parameter is set on true, the invoices that are to be created will get the current users and locations of their business partners, regardless of the Bill_Location and Bill_User values set in the enqueued invoice candidates. The invoice candidates will suffer no update. Nevertheless, the Bill_Location_Override and Bill_User_Override will be respected if they are set in the invoice candidates.',Updated=TO_TIMESTAMP('2019-10-09 14:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='en_US'
;

-- 2019-10-09T11:34:03.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'en_US') 
;

-- 2019-10-09T11:54:12.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=40,Updated=TO_TIMESTAMP('2019-10-09 14:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541615
;

-- 2019-10-09T11:54:12.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=50,Updated=TO_TIMESTAMP('2019-10-09 14:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540424
;

-- 2019-10-09T11:54:12.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=60,Updated=TO_TIMESTAMP('2019-10-09 14:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540640
;

-- 2019-10-09T11:54:12.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=70,Updated=TO_TIMESTAMP('2019-10-09 14:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540610
;

-- 2019-10-09T11:54:12.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=80,Updated=TO_TIMESTAMP('2019-10-09 14:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540653
;

-- 2019-10-09T12:16:55.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungsadresse und -kontakt aktualisieren', PrintName='Rechnungsadresse und -kontakt aktualisieren',Updated=TO_TIMESTAMP('2019-10-09 15:16:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='de_CH'
;

-- 2019-10-09T12:16:55.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'de_CH') 
;

-- 2019-10-09T12:18:03.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description=' ',Updated=TO_TIMESTAMP('2019-10-09 15:18:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='de_DE'
;

-- 2019-10-09T12:18:03.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'de_DE') 
;

-- 2019-10-09T12:18:03.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577153,'de_DE') 
;

-- 2019-10-09T12:18:03.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsUpdateLocationAndContactForInvoice', Name='Update Location and Contact for Invoice', Description=' ', Help=NULL WHERE AD_Element_ID=577153
;

-- 2019-10-09T12:18:03.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUpdateLocationAndContactForInvoice', Name='Update Location and Contact for Invoice', Description=' ', Help=NULL, AD_Element_ID=577153 WHERE UPPER(ColumnName)='ISUPDATELOCATIONANDCONTACTFORINVOICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-09T12:18:03.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUpdateLocationAndContactForInvoice', Name='Update Location and Contact for Invoice', Description=' ', Help=NULL WHERE AD_Element_ID=577153 AND IsCentrallyMaintained='Y'
;

-- 2019-10-09T12:18:03.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Update Location and Contact for Invoice', Description=' ', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577153) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577153)
;

-- 2019-10-09T12:18:03.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Update Location and Contact for Invoice', Description=' ', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577153
;

-- 2019-10-09T12:18:03.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Update Location and Contact for Invoice', Description=' ', Help=NULL WHERE AD_Element_ID = 577153
;

-- 2019-10-09T12:18:03.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Update Location and Contact for Invoice', Description = ' ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577153
;

-- 2019-10-09T12:18:18.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungsadresse und -kontakt aktualisieren', PrintName='Rechnungsadresse und -kontakt aktualisieren',Updated=TO_TIMESTAMP('2019-10-09 15:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='de_DE'
;

-- 2019-10-09T12:18:18.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'de_DE') 
;

-- 2019-10-09T12:18:18.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577153,'de_DE') 
;

-- 2019-10-09T12:18:18.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsUpdateLocationAndContactForInvoice', Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help=NULL WHERE AD_Element_ID=577153
;

-- 2019-10-09T12:18:18.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUpdateLocationAndContactForInvoice', Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help=NULL, AD_Element_ID=577153 WHERE UPPER(ColumnName)='ISUPDATELOCATIONANDCONTACTFORINVOICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-09T12:18:18.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUpdateLocationAndContactForInvoice', Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help=NULL WHERE AD_Element_ID=577153 AND IsCentrallyMaintained='Y'
;

-- 2019-10-09T12:18:18.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577153) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577153)
;

-- 2019-10-09T12:18:18.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungsadresse und -kontakt aktualisieren', Name='Rechnungsadresse und -kontakt aktualisieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577153)
;

-- 2019-10-09T12:18:18.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577153
;

-- 2019-10-09T12:18:18.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help=NULL WHERE AD_Element_ID = 577153
;

-- 2019-10-09T12:18:18.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnungsadresse und -kontakt aktualisieren', Description = ' ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577153
;

-- 2019-10-09T12:42:21.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.',Updated=TO_TIMESTAMP('2019-10-09 15:42:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='de_CH'
;

-- 2019-10-09T12:42:21.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'de_CH') 
;

-- 2019-10-09T12:42:24.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.',Updated=TO_TIMESTAMP('2019-10-09 15:42:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='de_DE'
;

-- 2019-10-09T12:42:24.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'de_DE') 
;

-- 2019-10-09T12:42:24.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577153,'de_DE') 
;

-- 2019-10-09T12:42:24.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsUpdateLocationAndContactForInvoice', Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help='Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.' WHERE AD_Element_ID=577153
;

-- 2019-10-09T12:42:24.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUpdateLocationAndContactForInvoice', Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help='Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.', AD_Element_ID=577153 WHERE UPPER(ColumnName)='ISUPDATELOCATIONANDCONTACTFORINVOICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-09T12:42:24.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUpdateLocationAndContactForInvoice', Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help='Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.' WHERE AD_Element_ID=577153 AND IsCentrallyMaintained='Y'
;

-- 2019-10-09T12:42:24.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help='Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577153) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577153)
;

-- 2019-10-09T12:42:24.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help='Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.', CommitWarning = NULL WHERE AD_Element_ID = 577153
;

-- 2019-10-09T12:42:24.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungsadresse und -kontakt aktualisieren', Description=' ', Help='Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.' WHERE AD_Element_ID = 577153
;

-- 2019-10-09T12:42:24.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnungsadresse und -kontakt aktualisieren', Description = ' ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577153
;

-- 2019-10-09T12:42:43.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='When this parameter is set on true, the invoices that are to be created will get the current users and locations of their business partners, regardless of the Bill_Location and Bill_User values set in the enqueued invoice candidates. The invoice candidates themselves will not be changed. Nevertheless, the Bill_Location_Override and Bill_User_Override will be respected if they are set in the invoice candidates.',Updated=TO_TIMESTAMP('2019-10-09 15:42:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577153 AND AD_Language='en_US'
;

-- 2019-10-09T12:42:43.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577153,'en_US') 
;

