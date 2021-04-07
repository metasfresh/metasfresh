-- 2021-02-15T11:10:40.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@InvoiceIdentifier@!''''',Updated=TO_TIMESTAMP('2021-02-15 13:10:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628793
;

-- 2021-02-15T11:12:17.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,628793,0,543320,577946,544779,'F',TO_TIMESTAMP('2021-02-15 13:12:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Invoice identifier',250,0,0,TO_TIMESTAMP('2021-02-15 13:12:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-15T11:12:41.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2021-02-15 13:12:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576444
;

-- 2021-02-15T11:13:36.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=240,Updated=TO_TIMESTAMP('2021-02-15 13:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576444
;

-- 2021-02-15T11:13:42.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2021-02-15 13:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577946
;

-- 2021-02-15T11:29:17.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540564,0,541573,TO_TIMESTAMP('2021-02-15 13:29:14','YYYY-MM-DD HH24:MI:SS'),100,'Unique index to prevent importing the same remittance advice twice.','D','Unique index to prevent importing the same remittance advice twice.','Y','Y','C_RemittanceAdvice_Unq_ExternalDocNo_SendDate','N',TO_TIMESTAMP('2021-02-15 13:29:14','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2021-02-15T11:29:17.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540564 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-02-15T11:29:40.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,572567,541057,540564,0,TO_TIMESTAMP('2021-02-15 13:29:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-02-15 13:29:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-15T11:30:05.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,572568,541058,540564,0,'COALESCE(sendAt, to_timestamp(0)::date)',TO_TIMESTAMP('2021-02-15 13:30:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-02-15 13:30:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-15T11:33:18.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(sendAt, ''1970-01-01'')',Updated=TO_TIMESTAMP('2021-02-15 13:33:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=541058
;

-- 2021-02-15T11:35:07.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_RemittanceAdvice_Unq_ExternalDocNo_SendDate ON C_RemittanceAdvice (ExternalDocumentNo,COALESCE(sendAt, '1970-01-01')) WHERE IsActive='Y'
;

-- 2021-02-15T12:14:18.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=628771
;

-- 2021-02-15T12:14:18.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=628771
;

-- 2021-02-15T12:14:18.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=628771
;

-- 2021-02-15T12:14:18.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice','ALTER TABLE C_RemittanceAdvice DROP COLUMN IF EXISTS AD_InputDataSource_ID')
;

-- 2021-02-15T12:14:18.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572565
;

-- 2021-02-15T12:14:18.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=572565
;

-- 2021-02-15T12:16:10.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Avis-Datum', PrintName='Avis-Datum',Updated=TO_TIMESTAMP('2021-02-15 14:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578679 AND AD_Language='de_CH'
;

-- 2021-02-15T12:16:10.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578679,'de_CH') 
;

-- 2021-02-15T12:16:16.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Avis-Datum', PrintName='Avis-Datum',Updated=TO_TIMESTAMP('2021-02-15 14:16:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578679 AND AD_Language='de_DE'
;

-- 2021-02-15T12:16:16.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578679,'de_DE') 
;

-- 2021-02-15T12:16:16.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578679,'de_DE') 
;

-- 2021-02-15T12:16:16.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AnnouncedDateTrx', Name='Avis-Datum', Description=NULL, Help=NULL WHERE AD_Element_ID=578679
;

-- 2021-02-15T12:16:16.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AnnouncedDateTrx', Name='Avis-Datum', Description=NULL, Help=NULL, AD_Element_ID=578679 WHERE UPPER(ColumnName)='ANNOUNCEDDATETRX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:16:16.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AnnouncedDateTrx', Name='Avis-Datum', Description=NULL, Help=NULL WHERE AD_Element_ID=578679 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:16:16.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Avis-Datum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578679) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578679)
;

-- 2021-02-15T12:16:16.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Avis-Datum', Name='Avis-Datum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578679)
;

-- 2021-02-15T12:16:16.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Avis-Datum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578679
;

-- 2021-02-15T12:16:16.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Avis-Datum', Description=NULL, Help=NULL WHERE AD_Element_ID = 578679
;

-- 2021-02-15T12:16:16.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Avis-Datum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578679
;

-- 2021-02-15T12:16:23.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Avis-Datum', PrintName='Avis-Datum',Updated=TO_TIMESTAMP('2021-02-15 14:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578679 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:16:23.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578679,'nl_NL') 
;

-- 2021-02-15T12:17:12.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zielbelegart', PrintName='Zielbelegart',Updated=TO_TIMESTAMP('2021-02-15 14:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578674 AND AD_Language='de_CH'
;

-- 2021-02-15T12:17:12.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578674,'de_CH') 
;

-- 2021-02-15T12:17:16.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zielbelegart', PrintName='Zielbelegart',Updated=TO_TIMESTAMP('2021-02-15 14:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578674 AND AD_Language='de_DE'
;

-- 2021-02-15T12:17:16.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578674,'de_DE') 
;

-- 2021-02-15T12:17:16.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578674,'de_DE') 
;

-- 2021-02-15T12:17:16.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Payment_Doctype_Target_ID', Name='Zielbelegart', Description=NULL, Help=NULL WHERE AD_Element_ID=578674
;

-- 2021-02-15T12:17:16.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Payment_Doctype_Target_ID', Name='Zielbelegart', Description=NULL, Help=NULL, AD_Element_ID=578674 WHERE UPPER(ColumnName)='C_PAYMENT_DOCTYPE_TARGET_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:17:16.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Payment_Doctype_Target_ID', Name='Zielbelegart', Description=NULL, Help=NULL WHERE AD_Element_ID=578674 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:17:16.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zielbelegart', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578674) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578674)
;

-- 2021-02-15T12:17:16.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zielbelegart', Name='Zielbelegart' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578674)
;

-- 2021-02-15T12:17:16.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zielbelegart', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578674
;

-- 2021-02-15T12:17:16.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zielbelegart', Description=NULL, Help=NULL WHERE AD_Element_ID = 578674
;

-- 2021-02-15T12:17:16.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zielbelegart', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578674
;

-- 2021-02-15T12:17:22.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zielbelegart', PrintName='Zielbelegart',Updated=TO_TIMESTAMP('2021-02-15 14:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578674 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:17:22.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578674,'nl_NL') 
;

-- 2021-02-15T12:17:42.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlungsavis', PrintName='Zahlungsavis',Updated=TO_TIMESTAMP('2021-02-15 14:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578673 AND AD_Language='de_CH'
;

-- 2021-02-15T12:17:42.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578673,'de_CH') 
;

-- 2021-02-15T12:17:48.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlungsavis', PrintName='Zahlungsavis',Updated=TO_TIMESTAMP('2021-02-15 14:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578673 AND AD_Language='de_DE'
;

-- 2021-02-15T12:17:48.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578673,'de_DE') 
;

-- 2021-02-15T12:17:48.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578673,'de_DE') 
;

-- 2021-02-15T12:17:48.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_RemittanceAdvice_ID', Name='Zahlungsavis', Description=NULL, Help=NULL WHERE AD_Element_ID=578673
;

-- 2021-02-15T12:17:48.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_RemittanceAdvice_ID', Name='Zahlungsavis', Description=NULL, Help=NULL, AD_Element_ID=578673 WHERE UPPER(ColumnName)='C_REMITTANCEADVICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:17:48.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_RemittanceAdvice_ID', Name='Zahlungsavis', Description=NULL, Help=NULL WHERE AD_Element_ID=578673 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:17:48.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zahlungsavis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578673) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578673)
;

-- 2021-02-15T12:17:48.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zahlungsavis', Name='Zahlungsavis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578673)
;

-- 2021-02-15T12:17:48.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zahlungsavis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578673
;

-- 2021-02-15T12:17:48.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zahlungsavis', Description=NULL, Help=NULL WHERE AD_Element_ID = 578673
;

-- 2021-02-15T12:17:48.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zahlungsavis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578673
;

-- 2021-02-15T12:17:52.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlungsavis', PrintName='Zahlungsavis',Updated=TO_TIMESTAMP('2021-02-15 14:17:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578673 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:17:52.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578673,'nl_NL') 
;

-- 2021-02-15T12:18:26.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zielkonto', PrintName='Zielkonto',Updated=TO_TIMESTAMP('2021-02-15 14:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578681 AND AD_Language='de_CH'
;

-- 2021-02-15T12:18:26.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578681,'de_CH') 
;

-- 2021-02-15T12:18:31.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zielkonto', PrintName='Zielkonto',Updated=TO_TIMESTAMP('2021-02-15 14:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578681 AND AD_Language='de_DE'
;

-- 2021-02-15T12:18:31.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578681,'de_DE') 
;

-- 2021-02-15T12:18:31.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578681,'de_DE') 
;

-- 2021-02-15T12:18:31.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Destination_BP_BankAccount_ID', Name='Zielkonto', Description=NULL, Help=NULL WHERE AD_Element_ID=578681
;

-- 2021-02-15T12:18:31.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Destination_BP_BankAccount_ID', Name='Zielkonto', Description=NULL, Help=NULL, AD_Element_ID=578681 WHERE UPPER(ColumnName)='DESTINATION_BP_BANKACCOUNT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:18:31.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Destination_BP_BankAccount_ID', Name='Zielkonto', Description=NULL, Help=NULL WHERE AD_Element_ID=578681 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:18:31.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zielkonto', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578681) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578681)
;

-- 2021-02-15T12:18:31.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zielkonto', Name='Zielkonto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578681)
;

-- 2021-02-15T12:18:31.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zielkonto', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578681
;

-- 2021-02-15T12:18:31.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zielkonto', Description=NULL, Help=NULL WHERE AD_Element_ID = 578681
;

-- 2021-02-15T12:18:31.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zielkonto', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578681
;

-- 2021-02-15T12:18:35.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zielkonto', PrintName='Zielkonto',Updated=TO_TIMESTAMP('2021-02-15 14:18:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578681 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:18:35.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578681,'nl_NL') 
;

-- 2021-02-15T12:19:03.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zielgeschäftspartner', PrintName='Zielgeschäftspartner',Updated=TO_TIMESTAMP('2021-02-15 14:19:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578683 AND AD_Language='de_CH'
;

-- 2021-02-15T12:19:03.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578683,'de_CH') 
;

-- 2021-02-15T12:19:07.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zielgeschäftspartner', PrintName='Zielgeschäftspartner',Updated=TO_TIMESTAMP('2021-02-15 14:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578683 AND AD_Language='de_DE'
;

-- 2021-02-15T12:19:07.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578683,'de_DE') 
;

-- 2021-02-15T12:19:07.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578683,'de_DE') 
;

-- 2021-02-15T12:19:07.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Destintion_BPartner_ID', Name='Zielgeschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=578683
;

-- 2021-02-15T12:19:07.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Destintion_BPartner_ID', Name='Zielgeschäftspartner', Description=NULL, Help=NULL, AD_Element_ID=578683 WHERE UPPER(ColumnName)='DESTINTION_BPARTNER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:19:07.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Destintion_BPartner_ID', Name='Zielgeschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=578683 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:19:07.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zielgeschäftspartner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578683) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578683)
;

-- 2021-02-15T12:19:07.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zielgeschäftspartner', Name='Zielgeschäftspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578683)
;

-- 2021-02-15T12:19:07.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zielgeschäftspartner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578683
;

-- 2021-02-15T12:19:07.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zielgeschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID = 578683
;

-- 2021-02-15T12:19:07.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zielgeschäftspartner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578683
;

-- 2021-02-15T12:19:12.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zielgeschäftspartner', PrintName='Zielgeschäftspartner',Updated=TO_TIMESTAMP('2021-02-15 14:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578683 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:19:12.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578683,'nl_NL') 
;

-- 2021-02-15T12:19:39.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Externe Belegnr', PrintName='Externe Belegnr',Updated=TO_TIMESTAMP('2021-02-15 14:19:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578675 AND AD_Language='de_CH'
;

-- 2021-02-15T12:19:39.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578675,'de_CH') 
;

-- 2021-02-15T12:19:42.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Externe Belegnr', PrintName='Externe Belegnr',Updated=TO_TIMESTAMP('2021-02-15 14:19:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578675 AND AD_Language='de_DE'
;

-- 2021-02-15T12:19:42.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578675,'de_DE') 
;

-- 2021-02-15T12:19:42.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578675,'de_DE') 
;

-- 2021-02-15T12:19:42.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalDocumentNo', Name='Externe Belegnr', Description=NULL, Help=NULL WHERE AD_Element_ID=578675
;

-- 2021-02-15T12:19:42.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalDocumentNo', Name='Externe Belegnr', Description=NULL, Help=NULL, AD_Element_ID=578675 WHERE UPPER(ColumnName)='EXTERNALDOCUMENTNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:19:42.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalDocumentNo', Name='Externe Belegnr', Description=NULL, Help=NULL WHERE AD_Element_ID=578675 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:19:42.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Externe Belegnr', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578675) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578675)
;

-- 2021-02-15T12:19:42.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Externe Belegnr', Name='Externe Belegnr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578675)
;

-- 2021-02-15T12:19:42.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Externe Belegnr', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578675
;

-- 2021-02-15T12:19:42.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Externe Belegnr', Description=NULL, Help=NULL WHERE AD_Element_ID = 578675
;

-- 2021-02-15T12:19:42.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Externe Belegnr', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578675
;

-- 2021-02-15T12:19:45.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Externe Belegnr', PrintName='Externe Belegnr',Updated=TO_TIMESTAMP('2021-02-15 14:19:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578675 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:19:45.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578675,'nl_NL') 
;

-- 2021-02-15T12:20:05.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Dokument OK', PrintName='Dokument OK',Updated=TO_TIMESTAMP('2021-02-15 14:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578723 AND AD_Language='de_CH'
;

-- 2021-02-15T12:20:05.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578723,'de_CH') 
;

-- 2021-02-15T12:20:08.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Dokument OK', PrintName='Dokument OK',Updated=TO_TIMESTAMP('2021-02-15 14:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578723 AND AD_Language='de_DE'
;

-- 2021-02-15T12:20:08.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578723,'de_DE') 
;

-- 2021-02-15T12:20:08.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578723,'de_DE') 
;

-- 2021-02-15T12:20:08.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsDocumentAcknowledged', Name='Dokument OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578723
;

-- 2021-02-15T12:20:08.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDocumentAcknowledged', Name='Dokument OK', Description=NULL, Help=NULL, AD_Element_ID=578723 WHERE UPPER(ColumnName)='ISDOCUMENTACKNOWLEDGED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:20:08.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDocumentAcknowledged', Name='Dokument OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578723 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:20:08.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dokument OK', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578723) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578723)
;

-- 2021-02-15T12:20:08.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Dokument OK', Name='Dokument OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578723)
;

-- 2021-02-15T12:20:08.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Dokument OK', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578723
;

-- 2021-02-15T12:20:08.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Dokument OK', Description=NULL, Help=NULL WHERE AD_Element_ID = 578723
;

-- 2021-02-15T12:20:08.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Dokument OK', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578723
;

-- 2021-02-15T12:20:12.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Dokument OK', PrintName='Dokument OK',Updated=TO_TIMESTAMP('2021-02-15 14:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578723 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:20:12.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578723,'nl_NL') 
;

-- 2021-02-15T12:20:37.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Skonto', PrintName='Skonto',Updated=TO_TIMESTAMP('2021-02-15 14:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578744 AND AD_Language='de_CH'
;

-- 2021-02-15T12:20:37.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578744,'de_CH') 
;

-- 2021-02-15T12:20:39.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Skonto', PrintName='Skonto',Updated=TO_TIMESTAMP('2021-02-15 14:20:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578744 AND AD_Language='de_DE'
;

-- 2021-02-15T12:20:39.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578744,'de_DE') 
;

-- 2021-02-15T12:20:39.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578744,'de_DE') 
;

-- 2021-02-15T12:20:39.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PaymentDiscountAmountSum', Name='Skonto', Description=NULL, Help=NULL WHERE AD_Element_ID=578744
;

-- 2021-02-15T12:20:39.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PaymentDiscountAmountSum', Name='Skonto', Description=NULL, Help=NULL, AD_Element_ID=578744 WHERE UPPER(ColumnName)='PAYMENTDISCOUNTAMOUNTSUM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:20:39.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PaymentDiscountAmountSum', Name='Skonto', Description=NULL, Help=NULL WHERE AD_Element_ID=578744 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:20:39.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Skonto', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578744) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578744)
;

-- 2021-02-15T12:20:39.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Skonto', Name='Skonto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578744)
;

-- 2021-02-15T12:20:39.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Skonto', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578744
;

-- 2021-02-15T12:20:39.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Skonto', Description=NULL, Help=NULL WHERE AD_Element_ID = 578744
;

-- 2021-02-15T12:20:39.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Skonto', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578744
;

-- 2021-02-15T12:20:43.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Skonto', PrintName='Skonto',Updated=TO_TIMESTAMP('2021-02-15 14:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578744 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:20:43.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578744,'nl_NL') 
;

-- 2021-02-15T12:21:04.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlbetrag', PrintName='Zahlbetrag',Updated=TO_TIMESTAMP('2021-02-15 14:21:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578677 AND AD_Language='de_CH'
;

-- 2021-02-15T12:21:04.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578677,'de_CH') 
;

-- 2021-02-15T12:21:07.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlbetrag', PrintName='Zahlbetrag',Updated=TO_TIMESTAMP('2021-02-15 14:21:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578677 AND AD_Language='de_DE'
;

-- 2021-02-15T12:21:07.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578677,'de_DE') 
;

-- 2021-02-15T12:21:07.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578677,'de_DE') 
;

-- 2021-02-15T12:21:07.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RemittanceAmt', Name='Zahlbetrag', Description=NULL, Help=NULL WHERE AD_Element_ID=578677
;

-- 2021-02-15T12:21:07.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemittanceAmt', Name='Zahlbetrag', Description=NULL, Help=NULL, AD_Element_ID=578677 WHERE UPPER(ColumnName)='REMITTANCEAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:21:07.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemittanceAmt', Name='Zahlbetrag', Description=NULL, Help=NULL WHERE AD_Element_ID=578677 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:21:07.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zahlbetrag', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578677) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578677)
;

-- 2021-02-15T12:21:07.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zahlbetrag', Name='Zahlbetrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578677)
;

-- 2021-02-15T12:21:07.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zahlbetrag', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578677
;

-- 2021-02-15T12:21:07.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zahlbetrag', Description=NULL, Help=NULL WHERE AD_Element_ID = 578677
;

-- 2021-02-15T12:21:07.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zahlbetrag', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578677
;

-- 2021-02-15T12:21:10.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlbetrag', PrintName='Zahlbetrag',Updated=TO_TIMESTAMP('2021-02-15 14:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578677 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:21:10.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578677,'nl_NL') 
;

-- 2021-02-15T12:21:34.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Währung', PrintName='Währung',Updated=TO_TIMESTAMP('2021-02-15 14:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578678 AND AD_Language='de_CH'
;

-- 2021-02-15T12:21:34.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578678,'de_CH') 
;

-- 2021-02-15T12:21:37.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Währung', PrintName='Währung',Updated=TO_TIMESTAMP('2021-02-15 14:21:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578678 AND AD_Language='de_DE'
;

-- 2021-02-15T12:21:37.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578678,'de_DE') 
;

-- 2021-02-15T12:21:37.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578678,'de_DE') 
;

-- 2021-02-15T12:21:37.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RemittanceAmt_Currency_ID', Name='Währung', Description=NULL, Help=NULL WHERE AD_Element_ID=578678
;

-- 2021-02-15T12:21:37.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemittanceAmt_Currency_ID', Name='Währung', Description=NULL, Help=NULL, AD_Element_ID=578678 WHERE UPPER(ColumnName)='REMITTANCEAMT_CURRENCY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:21:37.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemittanceAmt_Currency_ID', Name='Währung', Description=NULL, Help=NULL WHERE AD_Element_ID=578678 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:21:37.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Währung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578678) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578678)
;

-- 2021-02-15T12:21:37.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Währung', Name='Währung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578678)
;

-- 2021-02-15T12:21:37.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Währung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578678
;

-- 2021-02-15T12:21:37.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Währung', Description=NULL, Help=NULL WHERE AD_Element_ID = 578678
;

-- 2021-02-15T12:21:37.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Währung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578678
;

-- 2021-02-15T12:21:41.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Währung', PrintName='Währung',Updated=TO_TIMESTAMP('2021-02-15 14:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578678 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:21:41.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578678,'nl_NL') 
;

-- 2021-02-15T12:21:58.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Avis gesendet am', PrintName='Avis gesendet am',Updated=TO_TIMESTAMP('2021-02-15 14:21:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578676 AND AD_Language='de_CH'
;

-- 2021-02-15T12:21:58.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578676,'de_CH') 
;

-- 2021-02-15T12:22:01.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Avis gesendet am', PrintName='Avis gesendet am',Updated=TO_TIMESTAMP('2021-02-15 14:22:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578676 AND AD_Language='de_DE'
;

-- 2021-02-15T12:22:01.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578676,'de_DE') 
;

-- 2021-02-15T12:22:01.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578676,'de_DE') 
;

-- 2021-02-15T12:22:01.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SendAt', Name='Avis gesendet am', Description=NULL, Help=NULL WHERE AD_Element_ID=578676
;

-- 2021-02-15T12:22:01.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SendAt', Name='Avis gesendet am', Description=NULL, Help=NULL, AD_Element_ID=578676 WHERE UPPER(ColumnName)='SENDAT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:22:01.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SendAt', Name='Avis gesendet am', Description=NULL, Help=NULL WHERE AD_Element_ID=578676 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:22:01.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Avis gesendet am', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578676) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578676)
;

-- 2021-02-15T12:22:01.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Avis gesendet am', Name='Avis gesendet am' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578676)
;

-- 2021-02-15T12:22:01.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Avis gesendet am', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578676
;

-- 2021-02-15T12:22:01.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Avis gesendet am', Description=NULL, Help=NULL WHERE AD_Element_ID = 578676
;

-- 2021-02-15T12:22:01.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Avis gesendet am', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578676
;

-- 2021-02-15T12:22:04.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Avis gesendet am', PrintName='Avis gesendet am',Updated=TO_TIMESTAMP('2021-02-15 14:22:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578676 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:22:04.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578676,'nl_NL') 
;

-- 2021-02-15T12:22:18.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Servicegebühr', PrintName='Servicegebühr',Updated=TO_TIMESTAMP('2021-02-15 14:22:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578684 AND AD_Language='de_CH'
;

-- 2021-02-15T12:22:18.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578684,'de_CH') 
;

-- 2021-02-15T12:22:22.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Servicegebühr', PrintName='Servicegebühr',Updated=TO_TIMESTAMP('2021-02-15 14:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578684 AND AD_Language='de_DE'
;

-- 2021-02-15T12:22:22.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578684,'de_DE') 
;

-- 2021-02-15T12:22:22.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578684,'de_DE') 
;

-- 2021-02-15T12:22:22.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceFeeAmount', Name='Servicegebühr', Description=NULL, Help=NULL WHERE AD_Element_ID=578684
;

-- 2021-02-15T12:22:22.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFeeAmount', Name='Servicegebühr', Description=NULL, Help=NULL, AD_Element_ID=578684 WHERE UPPER(ColumnName)='SERVICEFEEAMOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:22:22.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFeeAmount', Name='Servicegebühr', Description=NULL, Help=NULL WHERE AD_Element_ID=578684 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:22:22.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Servicegebühr', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578684) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578684)
;

-- 2021-02-15T12:22:22.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Servicegebühr', Name='Servicegebühr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578684)
;

-- 2021-02-15T12:22:22.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Servicegebühr', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578684
;

-- 2021-02-15T12:22:22.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Servicegebühr', Description=NULL, Help=NULL WHERE AD_Element_ID = 578684
;

-- 2021-02-15T12:22:22.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Servicegebühr', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578684
;

-- 2021-02-15T12:22:25.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Servicegebühr', PrintName='Servicegebühr',Updated=TO_TIMESTAMP('2021-02-15 14:22:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578684 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:22:25.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578684,'nl_NL') 
;

-- 2021-02-15T12:22:45.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Währ. Servicegebühr', PrintName='Währ. Servicegebühr',Updated=TO_TIMESTAMP('2021-02-15 14:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578685 AND AD_Language='de_CH'
;

-- 2021-02-15T12:22:45.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578685,'de_CH') 
;

-- 2021-02-15T12:22:48.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Währ. Servicegebühr', PrintName='Währ. Servicegebühr',Updated=TO_TIMESTAMP('2021-02-15 14:22:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578685 AND AD_Language='de_DE'
;

-- 2021-02-15T12:22:48.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578685,'de_DE') 
;

-- 2021-02-15T12:22:48.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578685,'de_DE') 
;

-- 2021-02-15T12:22:48.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceFeeAmount_Currency_ID', Name='Währ. Servicegebühr', Description=NULL, Help=NULL WHERE AD_Element_ID=578685
;

-- 2021-02-15T12:22:48.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFeeAmount_Currency_ID', Name='Währ. Servicegebühr', Description=NULL, Help=NULL, AD_Element_ID=578685 WHERE UPPER(ColumnName)='SERVICEFEEAMOUNT_CURRENCY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:22:48.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFeeAmount_Currency_ID', Name='Währ. Servicegebühr', Description=NULL, Help=NULL WHERE AD_Element_ID=578685 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:22:48.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Währ. Servicegebühr', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578685) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578685)
;

-- 2021-02-15T12:22:48.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Währ. Servicegebühr', Name='Währ. Servicegebühr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578685)
;

-- 2021-02-15T12:22:48.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Währ. Servicegebühr', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578685
;

-- 2021-02-15T12:22:48.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Währ. Servicegebühr', Description=NULL, Help=NULL WHERE AD_Element_ID = 578685
;

-- 2021-02-15T12:22:48.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Währ. Servicegebühr', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578685
;

-- 2021-02-15T12:22:50.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Währ. Servicegebühr', PrintName='Währ. Servicegebühr',Updated=TO_TIMESTAMP('2021-02-15 14:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578685 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:22:50.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578685,'nl_NL') 
;

-- 2021-02-15T12:23:13.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Externe Servicerechnungsnr', PrintName='Externe Servicerechnungsnr',Updated=TO_TIMESTAMP('2021-02-15 14:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578686 AND AD_Language='de_CH'
;

-- 2021-02-15T12:23:13.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578686,'de_CH') 
;

-- 2021-02-15T12:23:15.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Externe Servicerechnungsnr', PrintName='Externe Servicerechnungsnr',Updated=TO_TIMESTAMP('2021-02-15 14:23:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578686 AND AD_Language='de_DE'
;

-- 2021-02-15T12:23:15.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578686,'de_DE') 
;

-- 2021-02-15T12:23:15.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578686,'de_DE') 
;

-- 2021-02-15T12:23:15.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceFeeExternalInvoiceDocumentNo', Name='Externe Servicerechnungsnr', Description=NULL, Help=NULL WHERE AD_Element_ID=578686
;

-- 2021-02-15T12:23:15.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFeeExternalInvoiceDocumentNo', Name='Externe Servicerechnungsnr', Description=NULL, Help=NULL, AD_Element_ID=578686 WHERE UPPER(ColumnName)='SERVICEFEEEXTERNALINVOICEDOCUMENTNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:23:15.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFeeExternalInvoiceDocumentNo', Name='Externe Servicerechnungsnr', Description=NULL, Help=NULL WHERE AD_Element_ID=578686 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:23:15.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Externe Servicerechnungsnr', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578686) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578686)
;

-- 2021-02-15T12:23:15.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Externe Servicerechnungsnr', Name='Externe Servicerechnungsnr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578686)
;

-- 2021-02-15T12:23:15.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Externe Servicerechnungsnr', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578686
;

-- 2021-02-15T12:23:15.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Externe Servicerechnungsnr', Description=NULL, Help=NULL WHERE AD_Element_ID = 578686
;

-- 2021-02-15T12:23:15.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Externe Servicerechnungsnr', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578686
;

-- 2021-02-15T12:23:18.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Externe Servicerechnungsnr', PrintName='Externe Servicerechnungsnr',Updated=TO_TIMESTAMP('2021-02-15 14:23:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578686 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:23:18.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578686,'nl_NL') 
;

-- 2021-02-15T12:23:38.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Servicerechnungsdatum', PrintName='Servicerechnungsdatum',Updated=TO_TIMESTAMP('2021-02-15 14:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578711 AND AD_Language='de_CH'
;

-- 2021-02-15T12:23:38.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578711,'de_CH') 
;

-- 2021-02-15T12:23:41.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Servicerechnungsdatum', PrintName='Servicerechnungsdatum',Updated=TO_TIMESTAMP('2021-02-15 14:23:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578711 AND AD_Language='de_DE'
;

-- 2021-02-15T12:23:41.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578711,'de_DE') 
;

-- 2021-02-15T12:23:41.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578711,'de_DE') 
;

-- 2021-02-15T12:23:41.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceFeeInvoicedDate', Name='Servicerechnungsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=578711
;

-- 2021-02-15T12:23:41.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFeeInvoicedDate', Name='Servicerechnungsdatum', Description=NULL, Help=NULL, AD_Element_ID=578711 WHERE UPPER(ColumnName)='SERVICEFEEINVOICEDDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:23:41.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceFeeInvoicedDate', Name='Servicerechnungsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=578711 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:23:41.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Servicerechnungsdatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578711) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578711)
;

-- 2021-02-15T12:23:41.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Servicerechnungsdatum', Name='Servicerechnungsdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578711)
;

-- 2021-02-15T12:23:41.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Servicerechnungsdatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578711
;

-- 2021-02-15T12:23:41.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Servicerechnungsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 578711
;

-- 2021-02-15T12:23:41.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Servicerechnungsdatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578711
;

-- 2021-02-15T12:23:45.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Servicerechnungsdatum', PrintName='Servicerechnungsdatum',Updated=TO_TIMESTAMP('2021-02-15 14:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578711 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:23:45.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578711,'nl_NL') 
;

-- 2021-02-15T12:24:01.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ausgangskonto', PrintName='Ausgangskonto',Updated=TO_TIMESTAMP('2021-02-15 14:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578680 AND AD_Language='de_CH'
;

-- 2021-02-15T12:24:01.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578680,'de_CH') 
;

-- 2021-02-15T12:24:04.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ausgangskonto', PrintName='Ausgangskonto',Updated=TO_TIMESTAMP('2021-02-15 14:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578680 AND AD_Language='de_DE'
;

-- 2021-02-15T12:24:04.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578680,'de_DE') 
;

-- 2021-02-15T12:24:04.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578680,'de_DE') 
;

-- 2021-02-15T12:24:04.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Source_BP_BankAccount_ID', Name='Ausgangskonto', Description=NULL, Help=NULL WHERE AD_Element_ID=578680
;

-- 2021-02-15T12:24:04.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Source_BP_BankAccount_ID', Name='Ausgangskonto', Description=NULL, Help=NULL, AD_Element_ID=578680 WHERE UPPER(ColumnName)='SOURCE_BP_BANKACCOUNT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:24:04.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Source_BP_BankAccount_ID', Name='Ausgangskonto', Description=NULL, Help=NULL WHERE AD_Element_ID=578680 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:24:04.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ausgangskonto', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578680) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578680)
;

-- 2021-02-15T12:24:04.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ausgangskonto', Name='Ausgangskonto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578680)
;

-- 2021-02-15T12:24:04.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ausgangskonto', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578680
;

-- 2021-02-15T12:24:04.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ausgangskonto', Description=NULL, Help=NULL WHERE AD_Element_ID = 578680
;

-- 2021-02-15T12:24:04.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ausgangskonto', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578680
;

-- 2021-02-15T12:24:07.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ausgangskonto', PrintName='Ausgangskonto',Updated=TO_TIMESTAMP('2021-02-15 14:24:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578680 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:24:07.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578680,'nl_NL') 
;

-- 2021-02-15T12:24:24.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ausgangsgeschäftspartner', PrintName='Ausgangsgeschäftspartner',Updated=TO_TIMESTAMP('2021-02-15 14:24:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578682 AND AD_Language='de_CH'
;

-- 2021-02-15T12:24:24.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578682,'de_CH') 
;

-- 2021-02-15T12:24:27.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ausgangsgeschäftspartner', PrintName='Ausgangsgeschäftspartner',Updated=TO_TIMESTAMP('2021-02-15 14:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578682 AND AD_Language='de_DE'
;

-- 2021-02-15T12:24:27.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578682,'de_DE') 
;

-- 2021-02-15T12:24:27.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578682,'de_DE') 
;

-- 2021-02-15T12:24:27.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Source_BPartner_ID', Name='Ausgangsgeschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=578682
;

-- 2021-02-15T12:24:27.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Source_BPartner_ID', Name='Ausgangsgeschäftspartner', Description=NULL, Help=NULL, AD_Element_ID=578682 WHERE UPPER(ColumnName)='SOURCE_BPARTNER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:24:27.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Source_BPartner_ID', Name='Ausgangsgeschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=578682 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:24:27.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ausgangsgeschäftspartner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578682) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578682)
;

-- 2021-02-15T12:24:27.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ausgangsgeschäftspartner', Name='Ausgangsgeschäftspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578682)
;

-- 2021-02-15T12:24:27.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ausgangsgeschäftspartner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578682
;

-- 2021-02-15T12:24:27.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ausgangsgeschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID = 578682
;

-- 2021-02-15T12:24:27.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ausgangsgeschäftspartner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578682
;

-- 2021-02-15T12:24:30.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ausgangsgeschäftspartner', PrintName='Ausgangsgeschäftspartner',Updated=TO_TIMESTAMP('2021-02-15 14:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578682 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:24:30.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578682,'nl_NL') 
;

-- 2021-02-15T12:25:11.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Währung', PrintName='Währung',Updated=TO_TIMESTAMP('2021-02-15 14:25:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578694 AND AD_Language='de_CH'
;

-- 2021-02-15T12:25:11.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578694,'de_CH') 
;

-- 2021-02-15T12:25:14.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Währung', PrintName='Währung',Updated=TO_TIMESTAMP('2021-02-15 14:25:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578694 AND AD_Language='de_DE'
;

-- 2021-02-15T12:25:14.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578694,'de_DE') 
;

-- 2021-02-15T12:25:14.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578694,'de_DE') 
;

-- 2021-02-15T12:25:14.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Currency_ID', Name='Währung', Description=NULL, Help=NULL WHERE AD_Element_ID=578694
;

-- 2021-02-15T12:25:14.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Currency_ID', Name='Währung', Description=NULL, Help=NULL, AD_Element_ID=578694 WHERE UPPER(ColumnName)='C_INVOICE_CURRENCY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:25:14.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Currency_ID', Name='Währung', Description=NULL, Help=NULL WHERE AD_Element_ID=578694 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:25:14.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Währung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578694) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578694)
;

-- 2021-02-15T12:25:14.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Währung', Name='Währung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578694)
;

-- 2021-02-15T12:25:14.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Währung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578694
;

-- 2021-02-15T12:25:14.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Währung', Description=NULL, Help=NULL WHERE AD_Element_ID = 578694
;

-- 2021-02-15T12:25:14.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Währung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578694
;

-- 2021-02-15T12:25:19.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Währung', PrintName='Währung',Updated=TO_TIMESTAMP('2021-02-15 14:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578694 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:25:19.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578694,'nl_NL') 
;

-- 2021-02-15T12:27:03.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlungsavis Linie', PrintName='Zahlungsavis Linie',Updated=TO_TIMESTAMP('2021-02-15 14:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578688 AND AD_Language='de_CH'
;

-- 2021-02-15T12:27:03.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578688,'de_CH') 
;

-- 2021-02-15T12:27:09.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlungsavis Linie', PrintName='Zahlungsavis Linie',Updated=TO_TIMESTAMP('2021-02-15 14:27:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578688 AND AD_Language='de_DE'
;

-- 2021-02-15T12:27:09.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578688,'de_DE') 
;

-- 2021-02-15T12:27:09.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578688,'de_DE') 
;

-- 2021-02-15T12:27:09.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_RemittanceAdvice_Line_ID', Name='Zahlungsavis Linie', Description=NULL, Help=NULL WHERE AD_Element_ID=578688
;

-- 2021-02-15T12:27:09.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_RemittanceAdvice_Line_ID', Name='Zahlungsavis Linie', Description=NULL, Help=NULL, AD_Element_ID=578688 WHERE UPPER(ColumnName)='C_REMITTANCEADVICE_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:27:09.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_RemittanceAdvice_Line_ID', Name='Zahlungsavis Linie', Description=NULL, Help=NULL WHERE AD_Element_ID=578688 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:27:09.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zahlungsavis Linie', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578688) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578688)
;

-- 2021-02-15T12:27:09.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zahlungsavis Linie', Name='Zahlungsavis Linie' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578688)
;

-- 2021-02-15T12:27:09.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zahlungsavis Linie', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578688
;

-- 2021-02-15T12:27:09.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zahlungsavis Linie', Description=NULL, Help=NULL WHERE AD_Element_ID = 578688
;

-- 2021-02-15T12:27:09.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zahlungsavis Linie', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578688
;

-- 2021-02-15T12:27:16.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlungsavis Linie', PrintName='Zahlungsavis Linie',Updated=TO_TIMESTAMP('2021-02-15 14:27:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578688 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:27:16.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578688,'nl_NL') 
;

-- 2021-02-15T12:28:36.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlungsavis Position', PrintName='Zahlungsavis Position',Updated=TO_TIMESTAMP('2021-02-15 14:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578688 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:28:36.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578688,'nl_NL') 
;

-- 2021-02-15T12:28:42.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlungsavis Position', PrintName='Zahlungsavis Position',Updated=TO_TIMESTAMP('2021-02-15 14:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578688 AND AD_Language='de_DE'
;

-- 2021-02-15T12:28:42.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578688,'de_DE') 
;

-- 2021-02-15T12:28:42.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578688,'de_DE') 
;

-- 2021-02-15T12:28:42.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_RemittanceAdvice_Line_ID', Name='Zahlungsavis Position', Description=NULL, Help=NULL WHERE AD_Element_ID=578688
;

-- 2021-02-15T12:28:42.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_RemittanceAdvice_Line_ID', Name='Zahlungsavis Position', Description=NULL, Help=NULL, AD_Element_ID=578688 WHERE UPPER(ColumnName)='C_REMITTANCEADVICE_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:28:42.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_RemittanceAdvice_Line_ID', Name='Zahlungsavis Position', Description=NULL, Help=NULL WHERE AD_Element_ID=578688 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:28:42.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zahlungsavis Position', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578688) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578688)
;

-- 2021-02-15T12:28:42.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zahlungsavis Position', Name='Zahlungsavis Position' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578688)
;

-- 2021-02-15T12:28:42.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zahlungsavis Position', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578688
;

-- 2021-02-15T12:28:42.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zahlungsavis Position', Description=NULL, Help=NULL WHERE AD_Element_ID = 578688
;

-- 2021-02-15T12:28:42.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zahlungsavis Position', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578688
;

-- 2021-02-15T12:28:46.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zahlungsavis Position', PrintName='Zahlungsavis Position',Updated=TO_TIMESTAMP('2021-02-15 14:28:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578688 AND AD_Language='de_CH'
;

-- 2021-02-15T12:28:46.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578688,'de_CH') 
;

-- 2021-02-15T12:29:16.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Externe Belegart', PrintName='Externe Belegart',Updated=TO_TIMESTAMP('2021-02-15 14:29:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578691 AND AD_Language='de_CH'
;

-- 2021-02-15T12:29:16.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578691,'de_CH') 
;

-- 2021-02-15T12:29:19.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Externe Belegart', PrintName='Externe Belegart',Updated=TO_TIMESTAMP('2021-02-15 14:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578691 AND AD_Language='de_DE'
;

-- 2021-02-15T12:29:19.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578691,'de_DE') 
;

-- 2021-02-15T12:29:19.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578691,'de_DE') 
;

-- 2021-02-15T12:29:19.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalInvoiceDocBaseType', Name='Externe Belegart', Description=NULL, Help=NULL WHERE AD_Element_ID=578691
;

-- 2021-02-15T12:29:19.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalInvoiceDocBaseType', Name='Externe Belegart', Description=NULL, Help=NULL, AD_Element_ID=578691 WHERE UPPER(ColumnName)='EXTERNALINVOICEDOCBASETYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:29:19.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalInvoiceDocBaseType', Name='Externe Belegart', Description=NULL, Help=NULL WHERE AD_Element_ID=578691 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:29:19.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Externe Belegart', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578691) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578691)
;

-- 2021-02-15T12:29:19.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Externe Belegart', Name='Externe Belegart' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578691)
;

-- 2021-02-15T12:29:19.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Externe Belegart', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578691
;

-- 2021-02-15T12:29:19.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Externe Belegart', Description=NULL, Help=NULL WHERE AD_Element_ID = 578691
;

-- 2021-02-15T12:29:19.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Externe Belegart', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578691
;

-- 2021-02-15T12:29:22.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Externe Belegart', PrintName='Externe Belegart',Updated=TO_TIMESTAMP('2021-02-15 14:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578691 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:29:22.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578691,'nl_NL') 
;

-- 2021-02-15T12:29:41.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Avis-Rechnungsbetrag', PrintName='Avis-Rechnungsbetrag',Updated=TO_TIMESTAMP('2021-02-15 14:29:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578695 AND AD_Language='de_CH'
;

-- 2021-02-15T12:29:41.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578695,'de_CH') 
;

-- 2021-02-15T12:29:45.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Avis-Rechnungsbetrag', PrintName='Avis-Rechnungsbetrag',Updated=TO_TIMESTAMP('2021-02-15 14:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578695 AND AD_Language='de_DE'
;

-- 2021-02-15T12:29:45.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578695,'de_DE') 
;

-- 2021-02-15T12:29:45.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578695,'de_DE') 
;

-- 2021-02-15T12:29:45.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoiceAmtInREMADVCurrency', Name='Avis-Rechnungsbetrag', Description=NULL, Help=NULL WHERE AD_Element_ID=578695
;

-- 2021-02-15T12:29:45.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceAmtInREMADVCurrency', Name='Avis-Rechnungsbetrag', Description=NULL, Help=NULL, AD_Element_ID=578695 WHERE UPPER(ColumnName)='INVOICEAMTINREMADVCURRENCY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:29:45.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceAmtInREMADVCurrency', Name='Avis-Rechnungsbetrag', Description=NULL, Help=NULL WHERE AD_Element_ID=578695 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:29:45.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Avis-Rechnungsbetrag', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578695) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578695)
;

-- 2021-02-15T12:29:45.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Avis-Rechnungsbetrag', Name='Avis-Rechnungsbetrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578695)
;

-- 2021-02-15T12:29:45.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Avis-Rechnungsbetrag', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578695
;

-- 2021-02-15T12:29:45.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Avis-Rechnungsbetrag', Description=NULL, Help=NULL WHERE AD_Element_ID = 578695
;

-- 2021-02-15T12:29:45.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Avis-Rechnungsbetrag', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578695
;

-- 2021-02-15T12:29:50.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Avis-Rechnungsbetrag', PrintName='Avis-Rechnungsbetrag',Updated=TO_TIMESTAMP('2021-02-15 14:29:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578695 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:29:50.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578695,'nl_NL') 
;

-- 2021-02-15T12:30:18.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungsbrutto', PrintName='Rechnungsbrutto',Updated=TO_TIMESTAMP('2021-02-15 14:30:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578743 AND AD_Language='de_CH'
;

-- 2021-02-15T12:30:18.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578743,'de_CH') 
;

-- 2021-02-15T12:30:21.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungsbrutto', PrintName='Rechnungsbrutto',Updated=TO_TIMESTAMP('2021-02-15 14:30:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578743 AND AD_Language='de_DE'
;

-- 2021-02-15T12:30:21.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578743,'de_DE') 
;

-- 2021-02-15T12:30:21.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578743,'de_DE') 
;

-- 2021-02-15T12:30:21.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoiceGrossAmount', Name='Rechnungsbrutto', Description=NULL, Help=NULL WHERE AD_Element_ID=578743
;

-- 2021-02-15T12:30:21.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceGrossAmount', Name='Rechnungsbrutto', Description=NULL, Help=NULL, AD_Element_ID=578743 WHERE UPPER(ColumnName)='INVOICEGROSSAMOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:30:21.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceGrossAmount', Name='Rechnungsbrutto', Description=NULL, Help=NULL WHERE AD_Element_ID=578743 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:30:21.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungsbrutto', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578743) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578743)
;

-- 2021-02-15T12:30:21.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungsbrutto', Name='Rechnungsbrutto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578743)
;

-- 2021-02-15T12:30:21.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungsbrutto', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578743
;

-- 2021-02-15T12:30:21.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungsbrutto', Description=NULL, Help=NULL WHERE AD_Element_ID = 578743
;

-- 2021-02-15T12:30:21.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnungsbrutto', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578743
;

-- 2021-02-15T12:30:24.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungsbrutto', PrintName='Rechnungsbrutto',Updated=TO_TIMESTAMP('2021-02-15 14:30:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578743 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:30:24.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578743,'nl_NL') 
;

-- 2021-02-15T12:30:39.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungs-Identifikator', PrintName='Rechnungs-Identifikator',Updated=TO_TIMESTAMP('2021-02-15 14:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578689 AND AD_Language='de_CH'
;

-- 2021-02-15T12:30:39.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578689,'de_CH') 
;

-- 2021-02-15T12:30:42.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungs-Identifikator', PrintName='Rechnungs-Identifikator',Updated=TO_TIMESTAMP('2021-02-15 14:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578689 AND AD_Language='de_DE'
;

-- 2021-02-15T12:30:42.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578689,'de_DE') 
;

-- 2021-02-15T12:30:42.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578689,'de_DE') 
;

-- 2021-02-15T12:30:42.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoiceIdentifier', Name='Rechnungs-Identifikator', Description=NULL, Help=NULL WHERE AD_Element_ID=578689
;

-- 2021-02-15T12:30:42.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceIdentifier', Name='Rechnungs-Identifikator', Description=NULL, Help=NULL, AD_Element_ID=578689 WHERE UPPER(ColumnName)='INVOICEIDENTIFIER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:30:42.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceIdentifier', Name='Rechnungs-Identifikator', Description=NULL, Help=NULL WHERE AD_Element_ID=578689 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:30:42.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungs-Identifikator', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578689) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578689)
;

-- 2021-02-15T12:30:42.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungs-Identifikator', Name='Rechnungs-Identifikator' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578689)
;

-- 2021-02-15T12:30:42.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungs-Identifikator', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578689
;

-- 2021-02-15T12:30:42.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungs-Identifikator', Description=NULL, Help=NULL WHERE AD_Element_ID = 578689
;

-- 2021-02-15T12:30:42.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnungs-Identifikator', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578689
;

-- 2021-02-15T12:30:45.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungs-Identifikator', PrintName='Rechnungs-Identifikator',Updated=TO_TIMESTAMP('2021-02-15 14:30:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578689 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:30:45.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578689,'nl_NL') 
;

-- 2021-02-15T12:31:05.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Betrag OK', PrintName='Betrag OK',Updated=TO_TIMESTAMP('2021-02-15 14:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578720 AND AD_Language='de_CH'
;

-- 2021-02-15T12:31:05.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578720,'de_CH') 
;

-- 2021-02-15T12:31:08.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Betrag OK', PrintName='Betrag OK',Updated=TO_TIMESTAMP('2021-02-15 14:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578720 AND AD_Language='de_DE'
;

-- 2021-02-15T12:31:08.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578720,'de_DE') 
;

-- 2021-02-15T12:31:08.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578720,'de_DE') 
;

-- 2021-02-15T12:31:08.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAmountValid', Name='Betrag OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578720
;

-- 2021-02-15T12:31:08.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAmountValid', Name='Betrag OK', Description=NULL, Help=NULL, AD_Element_ID=578720 WHERE UPPER(ColumnName)='ISAMOUNTVALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:31:08.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAmountValid', Name='Betrag OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578720 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:31:08.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Betrag OK', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578720) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578720)
;

-- 2021-02-15T12:31:08.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Betrag OK', Name='Betrag OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578720)
;

-- 2021-02-15T12:31:08.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Betrag OK', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578720
;

-- 2021-02-15T12:31:08.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Betrag OK', Description=NULL, Help=NULL WHERE AD_Element_ID = 578720
;

-- 2021-02-15T12:31:08.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Betrag OK', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578720
;

-- 2021-02-15T12:31:10.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Betrag OK', PrintName='Betrag OK',Updated=TO_TIMESTAMP('2021-02-15 14:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578720 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:31:10.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578720,'nl_NL') 
;

-- 2021-02-15T12:31:26.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geschäftspartner OK', PrintName='Geschäftspartner OK',Updated=TO_TIMESTAMP('2021-02-15 14:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578717 AND AD_Language='de_CH'
;

-- 2021-02-15T12:31:26.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578717,'de_CH') 
;

-- 2021-02-15T12:31:29.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geschäftspartner OK', PrintName='Geschäftspartner OK',Updated=TO_TIMESTAMP('2021-02-15 14:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578717 AND AD_Language='de_DE'
;

-- 2021-02-15T12:31:29.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578717,'de_DE') 
;

-- 2021-02-15T12:31:29.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578717,'de_DE') 
;

-- 2021-02-15T12:31:29.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsBPartnerValid', Name='Geschäftspartner OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578717
;

-- 2021-02-15T12:31:29.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsBPartnerValid', Name='Geschäftspartner OK', Description=NULL, Help=NULL, AD_Element_ID=578717 WHERE UPPER(ColumnName)='ISBPARTNERVALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:31:29.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsBPartnerValid', Name='Geschäftspartner OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578717 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:31:29.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschäftspartner OK', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578717) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578717)
;

-- 2021-02-15T12:31:29.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geschäftspartner OK', Name='Geschäftspartner OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578717)
;

-- 2021-02-15T12:31:29.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geschäftspartner OK', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578717
;

-- 2021-02-15T12:31:29.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geschäftspartner OK', Description=NULL, Help=NULL WHERE AD_Element_ID = 578717
;

-- 2021-02-15T12:31:29.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geschäftspartner OK', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578717
;

-- 2021-02-15T12:31:35.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geschäftspartner OK', PrintName='Geschäftspartner OK',Updated=TO_TIMESTAMP('2021-02-15 14:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578717 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:31:35.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578717,'nl_NL') 
;

-- 2021-02-15T12:31:53.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungsdatum OK', PrintName='Rechnungsdatum OK',Updated=TO_TIMESTAMP('2021-02-15 14:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578715 AND AD_Language='de_CH'
;

-- 2021-02-15T12:31:53.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578715,'de_CH') 
;

-- 2021-02-15T12:31:55.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungsdatum OK', PrintName='Rechnungsdatum OK',Updated=TO_TIMESTAMP('2021-02-15 14:31:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578715 AND AD_Language='de_DE'
;

-- 2021-02-15T12:31:55.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578715,'de_DE') 
;

-- 2021-02-15T12:31:55.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578715,'de_DE') 
;

-- 2021-02-15T12:31:55.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInvoiceDateValid', Name='Rechnungsdatum OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578715
;

-- 2021-02-15T12:31:55.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceDateValid', Name='Rechnungsdatum OK', Description=NULL, Help=NULL, AD_Element_ID=578715 WHERE UPPER(ColumnName)='ISINVOICEDATEVALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:31:55.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceDateValid', Name='Rechnungsdatum OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578715 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:31:55.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungsdatum OK', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578715) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578715)
;

-- 2021-02-15T12:31:55.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungsdatum OK', Name='Rechnungsdatum OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578715)
;

-- 2021-02-15T12:31:55.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungsdatum OK', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578715
;

-- 2021-02-15T12:31:55.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungsdatum OK', Description=NULL, Help=NULL WHERE AD_Element_ID = 578715
;

-- 2021-02-15T12:31:55.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnungsdatum OK', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578715
;

-- 2021-02-15T12:31:58.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungsdatum OK', PrintName='Rechnungsdatum OK',Updated=TO_TIMESTAMP('2021-02-15 14:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578715 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:31:58.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578715,'nl_NL') 
;

-- 2021-02-15T12:32:18.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Belegart OK', PrintName='Belegart OK',Updated=TO_TIMESTAMP('2021-02-15 14:32:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578716 AND AD_Language='de_CH'
;

-- 2021-02-15T12:32:18.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578716,'de_CH') 
;

-- 2021-02-15T12:32:20.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Belegart OK', PrintName='Belegart OK',Updated=TO_TIMESTAMP('2021-02-15 14:32:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578716 AND AD_Language='de_DE'
;

-- 2021-02-15T12:32:20.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578716,'de_DE') 
;

-- 2021-02-15T12:32:20.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578716,'de_DE') 
;

-- 2021-02-15T12:32:20.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInvoiceDocTypeValid', Name='Belegart OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578716
;

-- 2021-02-15T12:32:20.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceDocTypeValid', Name='Belegart OK', Description=NULL, Help=NULL, AD_Element_ID=578716 WHERE UPPER(ColumnName)='ISINVOICEDOCTYPEVALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:32:20.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceDocTypeValid', Name='Belegart OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578716 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:32:20.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Belegart OK', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578716) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578716)
;

-- 2021-02-15T12:32:20.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Belegart OK', Name='Belegart OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578716)
;

-- 2021-02-15T12:32:20.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Belegart OK', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578716
;

-- 2021-02-15T12:32:20.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Belegart OK', Description=NULL, Help=NULL WHERE AD_Element_ID = 578716
;

-- 2021-02-15T12:32:20.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Belegart OK', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578716
;

-- 2021-02-15T12:32:23.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Belegart OK', PrintName='Belegart OK',Updated=TO_TIMESTAMP('2021-02-15 14:32:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578716 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:32:23.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578716,'nl_NL') 
;

-- 2021-02-15T12:32:44.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnung zugeordnet', PrintName='Rechnung zugeordnet',Updated=TO_TIMESTAMP('2021-02-15 14:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578719 AND AD_Language='de_CH'
;

-- 2021-02-15T12:32:44.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578719,'de_CH') 
;

-- 2021-02-15T12:32:47.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnung zugeordnet', PrintName='Rechnung zugeordnet',Updated=TO_TIMESTAMP('2021-02-15 14:32:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578719 AND AD_Language='de_DE'
;

-- 2021-02-15T12:32:47.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578719,'de_DE') 
;

-- 2021-02-15T12:32:47.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578719,'de_DE') 
;

-- 2021-02-15T12:32:47.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInvoiceResolved', Name='Rechnung zugeordnet', Description=NULL, Help=NULL WHERE AD_Element_ID=578719
;

-- 2021-02-15T12:32:47.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceResolved', Name='Rechnung zugeordnet', Description=NULL, Help=NULL, AD_Element_ID=578719 WHERE UPPER(ColumnName)='ISINVOICERESOLVED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:32:47.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceResolved', Name='Rechnung zugeordnet', Description=NULL, Help=NULL WHERE AD_Element_ID=578719 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:32:47.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnung zugeordnet', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578719) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578719)
;

-- 2021-02-15T12:32:47.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung zugeordnet', Name='Rechnung zugeordnet' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578719)
;

-- 2021-02-15T12:32:47.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnung zugeordnet', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578719
;

-- 2021-02-15T12:32:47.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnung zugeordnet', Description=NULL, Help=NULL WHERE AD_Element_ID = 578719
;

-- 2021-02-15T12:32:47.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnung zugeordnet', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578719
;

-- 2021-02-15T12:32:52.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnung zugeordnet', PrintName='Rechnung zugeordnet',Updated=TO_TIMESTAMP('2021-02-15 14:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578719 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:32:52.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578719,'nl_NL') 
;

-- 2021-02-15T12:33:08.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zeile OK', PrintName='Zeile OK',Updated=TO_TIMESTAMP('2021-02-15 14:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578721 AND AD_Language='de_CH'
;

-- 2021-02-15T12:33:08.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578721,'de_CH') 
;

-- 2021-02-15T12:33:11.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zeile OK', PrintName='Zeile OK',Updated=TO_TIMESTAMP('2021-02-15 14:33:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578721 AND AD_Language='de_DE'
;

-- 2021-02-15T12:33:11.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578721,'de_DE') 
;

-- 2021-02-15T12:33:11.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578721,'de_DE') 
;

-- 2021-02-15T12:33:11.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsLineAcknowledged', Name='Zeile OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578721
;

-- 2021-02-15T12:33:11.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLineAcknowledged', Name='Zeile OK', Description=NULL, Help=NULL, AD_Element_ID=578721 WHERE UPPER(ColumnName)='ISLINEACKNOWLEDGED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:33:11.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLineAcknowledged', Name='Zeile OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578721 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:33:11.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zeile OK', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578721) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578721)
;

-- 2021-02-15T12:33:11.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zeile OK', Name='Zeile OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578721)
;

-- 2021-02-15T12:33:11.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zeile OK', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578721
;

-- 2021-02-15T12:33:11.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zeile OK', Description=NULL, Help=NULL WHERE AD_Element_ID = 578721
;

-- 2021-02-15T12:33:11.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zeile OK', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578721
;

-- 2021-02-15T12:33:14.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zeile OK', PrintName='Zeile OK',Updated=TO_TIMESTAMP('2021-02-15 14:33:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578721 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:33:14.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578721,'nl_NL') 
;

-- 2021-02-15T12:33:30.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Serviceeinstellungen zugeordnet', PrintName='Serviceeinstellungen zugeordnet',Updated=TO_TIMESTAMP('2021-02-15 14:33:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578722 AND AD_Language='de_CH'
;

-- 2021-02-15T12:33:30.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578722,'de_CH') 
;

-- 2021-02-15T12:33:32.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Serviceeinstellungen zugeordnet', PrintName='Serviceeinstellungen zugeordnet',Updated=TO_TIMESTAMP('2021-02-15 14:33:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578722 AND AD_Language='de_DE'
;

-- 2021-02-15T12:33:32.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578722,'de_DE') 
;

-- 2021-02-15T12:33:32.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578722,'de_DE') 
;

-- 2021-02-15T12:33:32.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsServiceColumnsResolved', Name='Serviceeinstellungen zugeordnet', Description=NULL, Help=NULL WHERE AD_Element_ID=578722
;

-- 2021-02-15T12:33:32.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsServiceColumnsResolved', Name='Serviceeinstellungen zugeordnet', Description=NULL, Help=NULL, AD_Element_ID=578722 WHERE UPPER(ColumnName)='ISSERVICECOLUMNSRESOLVED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:33:32.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsServiceColumnsResolved', Name='Serviceeinstellungen zugeordnet', Description=NULL, Help=NULL WHERE AD_Element_ID=578722 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:33:32.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serviceeinstellungen zugeordnet', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578722) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578722)
;

-- 2021-02-15T12:33:32.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Serviceeinstellungen zugeordnet', Name='Serviceeinstellungen zugeordnet' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578722)
;

-- 2021-02-15T12:33:32.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Serviceeinstellungen zugeordnet', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578722
;

-- 2021-02-15T12:33:32.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Serviceeinstellungen zugeordnet', Description=NULL, Help=NULL WHERE AD_Element_ID = 578722
;

-- 2021-02-15T12:33:32.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Serviceeinstellungen zugeordnet', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578722
;

-- 2021-02-15T12:33:36.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Serviceeinstellungen zugeordnet', PrintName='Serviceeinstellungen zugeordnet',Updated=TO_TIMESTAMP('2021-02-15 14:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578722 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:33:36.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578722,'nl_NL') 
;

-- 2021-02-15T12:33:55.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Service-Steuer OK', PrintName='Service-Steuer OK',Updated=TO_TIMESTAMP('2021-02-15 14:33:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578718 AND AD_Language='de_CH'
;

-- 2021-02-15T12:33:55.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578718,'de_CH') 
;

-- 2021-02-15T12:33:58.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Service-Steuer OK', PrintName='Service-Steuer OK',Updated=TO_TIMESTAMP('2021-02-15 14:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578718 AND AD_Language='de_DE'
;

-- 2021-02-15T12:33:58.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578718,'de_DE') 
;

-- 2021-02-15T12:33:58.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578718,'de_DE') 
;

-- 2021-02-15T12:33:58.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsServiceFeeVatRateValid', Name='Service-Steuer OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578718
;

-- 2021-02-15T12:33:58.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsServiceFeeVatRateValid', Name='Service-Steuer OK', Description=NULL, Help=NULL, AD_Element_ID=578718 WHERE UPPER(ColumnName)='ISSERVICEFEEVATRATEVALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:33:58.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsServiceFeeVatRateValid', Name='Service-Steuer OK', Description=NULL, Help=NULL WHERE AD_Element_ID=578718 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:33:58.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Service-Steuer OK', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578718) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578718)
;

-- 2021-02-15T12:33:58.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Service-Steuer OK', Name='Service-Steuer OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578718)
;

-- 2021-02-15T12:33:58.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Service-Steuer OK', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578718
;

-- 2021-02-15T12:33:58.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Service-Steuer OK', Description=NULL, Help=NULL WHERE AD_Element_ID = 578718
;

-- 2021-02-15T12:33:58.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Service-Steuer OK', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578718
;

-- 2021-02-15T12:34:01.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Service-Steuer OK', PrintName='Service-Steuer OK',Updated=TO_TIMESTAMP('2021-02-15 14:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578718 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:34:01.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578718,'nl_NL') 
;

-- 2021-02-15T12:35:56.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Über-/Unterzahlung', PrintName='Über-/Unterzahlung',Updated=TO_TIMESTAMP('2021-02-15 14:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1819 AND AD_Language='de_CH'
;

-- 2021-02-15T12:35:56.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1819,'de_CH') 
;

-- 2021-02-15T12:36:10.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Über-/Unterzahlung', PrintName='Über-/Unterzahlung',Updated=TO_TIMESTAMP('2021-02-15 14:36:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1819 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:36:10.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1819,'nl_NL') 
;

-- 2021-02-15T12:36:15.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Über-/Unterzahlung', PrintName='Über-/Unterzahlung',Updated=TO_TIMESTAMP('2021-02-15 14:36:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1819 AND AD_Language='de_DE'
;

-- 2021-02-15T12:36:15.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1819,'de_DE') 
;

-- 2021-02-15T12:36:15.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1819,'de_DE') 
;

-- 2021-02-15T12:36:15.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='OverUnderAmt', Name='Über-/Unterzahlung', Description='Over-Payment (unallocated) or Under-Payment (partial payment) Amount', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.' WHERE AD_Element_ID=1819
;

-- 2021-02-15T12:36:15.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OverUnderAmt', Name='Über-/Unterzahlung', Description='Over-Payment (unallocated) or Under-Payment (partial payment) Amount', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.', AD_Element_ID=1819 WHERE UPPER(ColumnName)='OVERUNDERAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:36:15.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OverUnderAmt', Name='Über-/Unterzahlung', Description='Over-Payment (unallocated) or Under-Payment (partial payment) Amount', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.' WHERE AD_Element_ID=1819 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:36:15.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Über-/Unterzahlung', Description='Over-Payment (unallocated) or Under-Payment (partial payment) Amount', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1819) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1819)
;

-- 2021-02-15T12:36:15.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Über-/Unterzahlung', Name='Über-/Unterzahlung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1819)
;

-- 2021-02-15T12:36:15.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Über-/Unterzahlung', Description='Over-Payment (unallocated) or Under-Payment (partial payment) Amount', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.', CommitWarning = NULL WHERE AD_Element_ID = 1819
;

-- 2021-02-15T12:36:15.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Über-/Unterzahlung', Description='Over-Payment (unallocated) or Under-Payment (partial payment) Amount', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.' WHERE AD_Element_ID = 1819
;

-- 2021-02-15T12:36:15.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Über-/Unterzahlung', Description = 'Over-Payment (unallocated) or Under-Payment (partial payment) Amount', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1819
;

-- 2021-02-15T12:48:46.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Skonto', PrintName='Skonto',Updated=TO_TIMESTAMP('2021-02-15 14:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578692 AND AD_Language='de_CH'
;

-- 2021-02-15T12:48:46.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578692,'de_CH') 
;

-- 2021-02-15T12:48:48.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Skonto', PrintName='Skonto',Updated=TO_TIMESTAMP('2021-02-15 14:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578692 AND AD_Language='de_DE'
;

-- 2021-02-15T12:48:48.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578692,'de_DE') 
;

-- 2021-02-15T12:48:48.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578692,'de_DE') 
;

-- 2021-02-15T12:48:48.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PaymentDiscountAmt', Name='Skonto', Description=NULL, Help=NULL WHERE AD_Element_ID=578692
;

-- 2021-02-15T12:48:48.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PaymentDiscountAmt', Name='Skonto', Description=NULL, Help=NULL, AD_Element_ID=578692 WHERE UPPER(ColumnName)='PAYMENTDISCOUNTAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:48:48.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PaymentDiscountAmt', Name='Skonto', Description=NULL, Help=NULL WHERE AD_Element_ID=578692 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:48:48.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Skonto', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578692) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578692)
;

-- 2021-02-15T12:48:48.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Skonto', Name='Skonto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578692)
;

-- 2021-02-15T12:48:48.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Skonto', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578692
;

-- 2021-02-15T12:48:48.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Skonto', Description=NULL, Help=NULL WHERE AD_Element_ID = 578692
;

-- 2021-02-15T12:48:48.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Skonto', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578692
;

-- 2021-02-15T12:48:52.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Skonto', PrintName='Skonto',Updated=TO_TIMESTAMP('2021-02-15 14:48:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578692 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:48:52.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578692,'nl_NL') 
;

-- 2021-02-15T12:49:17.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Serviceunternehmen', PrintName='Serviceunternehmen',Updated=TO_TIMESTAMP('2021-02-15 14:49:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578696 AND AD_Language='de_CH'
;

-- 2021-02-15T12:49:17.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578696,'de_CH') 
;

-- 2021-02-15T12:49:20.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Serviceunternehmen', PrintName='Serviceunternehmen',Updated=TO_TIMESTAMP('2021-02-15 14:49:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578696 AND AD_Language='de_DE'
;

-- 2021-02-15T12:49:20.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578696,'de_DE') 
;

-- 2021-02-15T12:49:20.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578696,'de_DE') 
;

-- 2021-02-15T12:49:20.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Service_BPartner_ID', Name='Serviceunternehmen', Description=NULL, Help=NULL WHERE AD_Element_ID=578696
;

-- 2021-02-15T12:49:20.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Service_BPartner_ID', Name='Serviceunternehmen', Description=NULL, Help=NULL, AD_Element_ID=578696 WHERE UPPER(ColumnName)='SERVICE_BPARTNER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:49:20.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Service_BPartner_ID', Name='Serviceunternehmen', Description=NULL, Help=NULL WHERE AD_Element_ID=578696 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:49:20.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serviceunternehmen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578696) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578696)
;

-- 2021-02-15T12:49:20.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Serviceunternehmen', Name='Serviceunternehmen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578696)
;

-- 2021-02-15T12:49:20.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Serviceunternehmen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578696
;

-- 2021-02-15T12:49:20.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Serviceunternehmen', Description=NULL, Help=NULL WHERE AD_Element_ID = 578696
;

-- 2021-02-15T12:49:20.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Serviceunternehmen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578696
;

-- 2021-02-15T12:49:28.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Serviceunternehmen', PrintName='Serviceunternehmen',Updated=TO_TIMESTAMP('2021-02-15 14:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578696 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:49:28.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578696,'nl_NL') 
;

-- 2021-02-15T12:49:45.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Servicerechnung', PrintName='Servicerechnung',Updated=TO_TIMESTAMP('2021-02-15 14:49:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578699 AND AD_Language='de_CH'
;

-- 2021-02-15T12:49:45.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578699,'de_CH') 
;

-- 2021-02-15T12:49:48.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Servicerechnung', PrintName='Servicerechnung',Updated=TO_TIMESTAMP('2021-02-15 14:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578699 AND AD_Language='de_DE'
;

-- 2021-02-15T12:49:48.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578699,'de_DE') 
;

-- 2021-02-15T12:49:48.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578699,'de_DE') 
;

-- 2021-02-15T12:49:48.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Service_Fee_Invoice_ID', Name='Servicerechnung', Description=NULL, Help=NULL WHERE AD_Element_ID=578699
;

-- 2021-02-15T12:49:48.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Service_Fee_Invoice_ID', Name='Servicerechnung', Description=NULL, Help=NULL, AD_Element_ID=578699 WHERE UPPER(ColumnName)='SERVICE_FEE_INVOICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:49:48.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Service_Fee_Invoice_ID', Name='Servicerechnung', Description=NULL, Help=NULL WHERE AD_Element_ID=578699 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:49:48.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Servicerechnung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578699) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578699)
;

-- 2021-02-15T12:49:48.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Servicerechnung', Name='Servicerechnung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578699)
;

-- 2021-02-15T12:49:48.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Servicerechnung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578699
;

-- 2021-02-15T12:49:48.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Servicerechnung', Description=NULL, Help=NULL WHERE AD_Element_ID = 578699
;

-- 2021-02-15T12:49:48.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Servicerechnung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578699
;

-- 2021-02-15T12:49:52.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Servicerechnung', PrintName='Servicerechnung',Updated=TO_TIMESTAMP('2021-02-15 14:49:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578699 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:49:52.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578699,'nl_NL') 
;

-- 2021-02-15T12:50:06.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Serviceprodukt', PrintName='Serviceprodukt',Updated=TO_TIMESTAMP('2021-02-15 14:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578697 AND AD_Language='de_CH'
;

-- 2021-02-15T12:50:06.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578697,'de_CH') 
;

-- 2021-02-15T12:50:10.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Serviceprodukt', PrintName='Serviceprodukt',Updated=TO_TIMESTAMP('2021-02-15 14:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578697 AND AD_Language='de_DE'
;

-- 2021-02-15T12:50:10.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578697,'de_DE') 
;

-- 2021-02-15T12:50:10.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578697,'de_DE') 
;

-- 2021-02-15T12:50:10.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Service_Product_ID', Name='Serviceprodukt', Description=NULL, Help=NULL WHERE AD_Element_ID=578697
;

-- 2021-02-15T12:50:10.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Service_Product_ID', Name='Serviceprodukt', Description=NULL, Help=NULL, AD_Element_ID=578697 WHERE UPPER(ColumnName)='SERVICE_PRODUCT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:50:10.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Service_Product_ID', Name='Serviceprodukt', Description=NULL, Help=NULL WHERE AD_Element_ID=578697 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:50:10.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serviceprodukt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578697) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578697)
;

-- 2021-02-15T12:50:10.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Serviceprodukt', Name='Serviceprodukt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578697)
;

-- 2021-02-15T12:50:10.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Serviceprodukt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578697
;

-- 2021-02-15T12:50:10.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Serviceprodukt', Description=NULL, Help=NULL WHERE AD_Element_ID = 578697
;

-- 2021-02-15T12:50:10.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Serviceprodukt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578697
;

-- 2021-02-15T12:50:13.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Serviceprodukt', PrintName='Serviceprodukt',Updated=TO_TIMESTAMP('2021-02-15 14:50:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578697 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:50:13.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578697,'nl_NL') 
;

-- 2021-02-15T12:50:30.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Service-Steuer', PrintName='Service-Steuer',Updated=TO_TIMESTAMP('2021-02-15 14:50:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578698 AND AD_Language='de_CH'
;

-- 2021-02-15T12:50:30.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578698,'de_CH') 
;

-- 2021-02-15T12:50:32.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Service-Steuer', PrintName='Service-Steuer',Updated=TO_TIMESTAMP('2021-02-15 14:50:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578698 AND AD_Language='de_DE'
;

-- 2021-02-15T12:50:32.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578698,'de_DE') 
;

-- 2021-02-15T12:50:32.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578698,'de_DE') 
;

-- 2021-02-15T12:50:32.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Service_Tax_ID', Name='Service-Steuer', Description=NULL, Help=NULL WHERE AD_Element_ID=578698
;

-- 2021-02-15T12:50:32.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Service_Tax_ID', Name='Service-Steuer', Description=NULL, Help=NULL, AD_Element_ID=578698 WHERE UPPER(ColumnName)='SERVICE_TAX_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T12:50:32.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Service_Tax_ID', Name='Service-Steuer', Description=NULL, Help=NULL WHERE AD_Element_ID=578698 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T12:50:32.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Service-Steuer', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578698) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578698)
;

-- 2021-02-15T12:50:32.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Service-Steuer', Name='Service-Steuer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578698)
;

-- 2021-02-15T12:50:32.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Service-Steuer', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578698
;

-- 2021-02-15T12:50:32.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Service-Steuer', Description=NULL, Help=NULL WHERE AD_Element_ID = 578698
;

-- 2021-02-15T12:50:32.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Service-Steuer', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578698
;

-- 2021-02-15T12:50:35.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Service-Steuer', PrintName='Service-Steuer',Updated=TO_TIMESTAMP('2021-02-15 14:50:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578698 AND AD_Language='nl_NL'
;

-- 2021-02-15T12:50:35.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578698,'nl_NL') 
;

-- 2021-02-15T12:56:24.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-02-15 14:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576448
;

-- 2021-02-15T12:56:24.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-02-15 14:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576414
;

-- 2021-02-15T12:56:24.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-02-15 14:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576406
;

-- 2021-02-15T12:56:24.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-02-15 14:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576407
;

-- 2021-02-15T12:56:24.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-02-15 14:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576408
;

-- 2021-02-15T12:56:24.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-02-15 14:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576409
;

-- 2021-02-15T12:56:50.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576405
;

-- 2021-02-15T12:56:50.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576410
;

-- 2021-02-15T12:56:50.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576401
;

-- 2021-02-15T12:56:50.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576403
;

-- 2021-02-15T12:56:50.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576448
;

-- 2021-02-15T12:56:50.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576414
;

-- 2021-02-15T12:56:50.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576406
;

-- 2021-02-15T12:56:50.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576407
;

-- 2021-02-15T12:56:50.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576408
;

-- 2021-02-15T12:56:50.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-02-15 14:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576409
;

-- 2021-02-15T12:58:03.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2021-02-15 14:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572558
;

-- 2021-02-15T12:58:03.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-02-15 14:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572564
;

-- 2021-02-15T12:58:03.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-02-15 14:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572575
;

-- 2021-02-15T12:58:03.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2021-02-15 14:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572574
;

-- 2021-02-15T12:58:03.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2021-02-15 14:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572582
;

-- 2021-02-15T12:58:03.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2021-02-15 14:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572613
;

-- 2021-02-15T12:58:03.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2021-02-15 14:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572581
;

-- 2021-02-15T12:58:03.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2021-02-15 14:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572623
;

-- 2021-02-15T13:02:55.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='1=0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-02-15 15:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628784
;

-- 2021-02-15T13:03:29.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='1=0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-02-15 15:03:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628815
;


