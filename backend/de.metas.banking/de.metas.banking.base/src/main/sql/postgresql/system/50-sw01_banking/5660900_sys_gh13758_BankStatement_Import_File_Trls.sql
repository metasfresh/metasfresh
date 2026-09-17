-- Element: C_BankStatement_Import_File_ID
-- 2022-10-18T08:14:28.502Z
UPDATE AD_Element_Trl SET Name='Kontoauszug Import-Datei', PrintName='Kontoauszug Import-Datei',Updated=TO_TIMESTAMP('2022-10-18 11:14:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581559 AND AD_Language='de_CH'
;

-- 2022-10-18T08:14:28.530Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581559,'de_CH') 
;

-- Element: C_BankStatement_Import_File_ID
-- 2022-10-18T08:14:30.891Z
UPDATE AD_Element_Trl SET Name='Kontoauszug Import-Datei', PrintName='Kontoauszug Import-Datei',Updated=TO_TIMESTAMP('2022-10-18 11:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581559 AND AD_Language='de_DE'
;

-- 2022-10-18T08:14:30.892Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581559,'de_DE') 
;

-- 2022-10-18T08:14:30.901Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581559,'de_DE') 
;

-- Element: C_BankStatement_Import_File_ID
-- 2022-10-18T08:14:35.444Z
UPDATE AD_Element_Trl SET Name='Kontoauszug Import-Datei', PrintName='Kontoauszug Import-Datei',Updated=TO_TIMESTAMP('2022-10-18 11:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581559 AND AD_Language='nl_NL'
;

-- 2022-10-18T08:14:35.447Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581559,'nl_NL') 
;

-- Element: Imported
-- 2022-10-18T08:18:36.234Z
UPDATE AD_Element_Trl SET Name='Importierter Zeitstempel', PrintName='Importierter Zeitstempel',Updated=TO_TIMESTAMP('2022-10-18 11:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581560 AND AD_Language='de_CH'
;

-- 2022-10-18T08:18:36.235Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581560,'de_CH') 
;

-- Element: Imported
-- 2022-10-18T08:18:38.307Z
UPDATE AD_Element_Trl SET Name='Importierter Zeitstempel', PrintName='Importierter Zeitstempel',Updated=TO_TIMESTAMP('2022-10-18 11:18:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581560 AND AD_Language='de_DE'
;

-- 2022-10-18T08:18:38.312Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581560,'de_DE') 
;

-- 2022-10-18T08:18:38.326Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581560,'de_DE') 
;

-- Element: Imported
-- 2022-10-18T08:18:41.619Z
UPDATE AD_Element_Trl SET Name='Importierter Zeitstempel', PrintName='Importierter Zeitstempel',Updated=TO_TIMESTAMP('2022-10-18 11:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581560 AND AD_Language='nl_NL'
;

-- 2022-10-18T08:18:41.620Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581560,'nl_NL') 
;

-- Name: AD_AttachmentEntry_for_C_BankStatement_Import_File
-- 2022-10-18T08:24:59.223Z
UPDATE AD_Val_Rule SET Code='AD_AttachmentEntry_ID IN ( select r.AD_AttachmentEntry_ID from AD_Attachment_MultiRef r where r.AD_Table_ID=get_Table_ID(''C_BankStatement_Import_File'') and r.Record_ID=@C_Import_Camt53_File_ID@ )', Name='AD_AttachmentEntry_for_C_BankStatement_Import_File',Updated=TO_TIMESTAMP('2022-10-18 11:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540605
;

-- Column: C_BankStatement_Import_File.C_BankStatement_Import_File_ID
-- 2022-10-18T08:26:48.932Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-10-18 11:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584724
;

-- Column: C_BankStatement_Import_File.C_BankStatement_Import_File_ID
-- 2022-10-18T08:32:39.546Z
UPDATE AD_Column SET IsIdentifier='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-10-18 11:32:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584724
;

-- Name: AD_AttachmentEntry_for_C_BankStatement_Import_File
-- 2022-10-18T08:39:22.586Z
UPDATE AD_Val_Rule SET Code='AD_AttachmentEntry_ID IN ( select r.AD_AttachmentEntry_ID from AD_Attachment_MultiRef r where r.AD_Table_ID=get_Table_ID(''C_BankStatement_Import_File'') and r.Record_ID=@C_BankStatement_Import_File_ID@ )',Updated=TO_TIMESTAMP('2022-10-18 11:39:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540605
;

