-- 2022-03-02T09:42:05.639Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Haben (gesamt) in Belegwährung.', Help='Das Haben (gesamt) gibt den gesamten Guthabenbetrag  für eine Journalbuchung oder einen Buchungsstapel in der Ausgangswährung an.', Name='Haben (gesamt)', PrintName='Haben (gesamt)',Updated=TO_TIMESTAMP('2022-03-02 10:42:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=596 AND AD_Language='de_CH'
;

-- 2022-03-02T09:42:05.659Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(596,'de_CH')
;

-- 2022-03-02T09:42:58.306Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Haben (gesamt) in Belegwährung.', Help='Das Haben (gesamt) gibt den gesamten Guthabenbetrag  für eine Journalbuchung oder einen Buchungsstapel in der Ausgangswährung an.', Name='Haben (gesamt)', PrintName='Haben (gesamt)',Updated=TO_TIMESTAMP('2022-03-02 10:42:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=596 AND AD_Language='de_DE'
;

-- 2022-03-02T09:42:58.311Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(596,'de_DE')
;

-- 2022-03-02T09:42:58.321Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(596,'de_DE')
;

-- 2022-03-02T09:42:58.321Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='TotalCr', Name='Haben (gesamt)', Description='Haben (gesamt) in Belegwährung.', Help='Das Haben (gesamt) gibt den gesamten Guthabenbetrag  für eine Journalbuchung oder einen Buchungsstapel in der Ausgangswährung an.' WHERE AD_Element_ID=596
;

-- 2022-03-02T09:42:58.322Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='TotalCr', Name='Haben (gesamt)', Description='Haben (gesamt) in Belegwährung.', Help='Das Haben (gesamt) gibt den gesamten Guthabenbetrag  für eine Journalbuchung oder einen Buchungsstapel in der Ausgangswährung an.', AD_Element_ID=596 WHERE UPPER(ColumnName)='TOTALCR' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-02T09:42:58.323Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='TotalCr', Name='Haben (gesamt)', Description='Haben (gesamt) in Belegwährung.', Help='Das Haben (gesamt) gibt den gesamten Guthabenbetrag  für eine Journalbuchung oder einen Buchungsstapel in der Ausgangswährung an.' WHERE AD_Element_ID=596 AND IsCentrallyMaintained='Y'
;

-- 2022-03-02T09:42:58.324Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Haben (gesamt)', Description='Haben (gesamt) in Belegwährung.', Help='Das Haben (gesamt) gibt den gesamten Guthabenbetrag  für eine Journalbuchung oder einen Buchungsstapel in der Ausgangswährung an.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=596) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 596)
;

-- 2022-03-02T09:42:58.337Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Haben (gesamt)', Name='Haben (gesamt)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=596)
;

-- 2022-03-02T09:42:58.338Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Haben (gesamt)', Description='Haben (gesamt) in Belegwährung.', Help='Das Haben (gesamt) gibt den gesamten Guthabenbetrag  für eine Journalbuchung oder einen Buchungsstapel in der Ausgangswährung an.', CommitWarning = NULL WHERE AD_Element_ID = 596
;

-- 2022-03-02T09:42:58.339Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Haben (gesamt)', Description='Haben (gesamt) in Belegwährung.', Help='Das Haben (gesamt) gibt den gesamten Guthabenbetrag  für eine Journalbuchung oder einen Buchungsstapel in der Ausgangswährung an.' WHERE AD_Element_ID = 596
;

-- 2022-03-02T09:42:58.340Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Haben (gesamt)', Description = 'Haben (gesamt) in Belegwährung.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 596
;

-- 2022-03-02T09:54:15.429Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Finde und liste die fehlerhaften Rechnungskandidaten auf',Updated=TO_TIMESTAMP('2022-03-02 10:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540679
;

-- 2022-03-02T09:54:33.808Z
-- URL zum Konzept
UPDATE AD_Process SET Description='Identifies, logs and reenqueues invoice candidates that need to be updated
! DOES *NOT* CREATE AN ASYNC_WORKPACKAGE TO PERFORM THE RE-EVALUATION !', Help='Under the hood it selects from the view de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v.<br>
It then inserts the found records into the table de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update and also reenqueues them to be updated.<br>
From the table de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update  we can see which were the problematic ICs.<br>
Each problematic IC might be listed multiple times.<br>
Also see issue .FRESH-93', Name='Finde und liste die fehlerhaften Rechnungskandidaten auf',Updated=TO_TIMESTAMP('2022-03-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540679
;

-- 2022-03-02T09:54:33.786Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Finde und liste die fehlerhaften Rechnungskandidaten auf',Updated=TO_TIMESTAMP('2022-03-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540679
;

-- 2022-03-02T09:56:22.402Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Finde und liste die fehlerhaften Rechnungskandidaten auf',Updated=TO_TIMESTAMP('2022-03-02 10:56:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540679
;

-- 2022-03-02T15:36:45.482Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rückerstattung', PrintName='Rückerstattung',Updated=TO_TIMESTAMP('2022-03-02 16:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544072 AND AD_Language='de_CH'
;

-- 2022-03-02T15:36:45.503Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544072,'de_CH')
;

-- 2022-03-02T15:37:00.210Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rückerstattung', PrintName='Rückerstattung',Updated=TO_TIMESTAMP('2022-03-02 16:37:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544072 AND AD_Language='de_DE'
;

-- 2022-03-02T15:37:00.211Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544072,'de_DE')
;

-- 2022-03-02T15:37:00.220Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(544072,'de_DE')
;

-- 2022-03-02T15:37:00.221Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Flatrate_RefundConfig_ID', Name='Rückerstattung', Description=NULL, Help=NULL WHERE AD_Element_ID=544072
;

-- 2022-03-02T15:37:00.222Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Flatrate_RefundConfig_ID', Name='Rückerstattung', Description=NULL, Help=NULL, AD_Element_ID=544072 WHERE UPPER(ColumnName)='C_FLATRATE_REFUNDCONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-02T15:37:00.223Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Flatrate_RefundConfig_ID', Name='Rückerstattung', Description=NULL, Help=NULL WHERE AD_Element_ID=544072 AND IsCentrallyMaintained='Y'
;

-- 2022-03-02T15:37:00.223Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rückerstattung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544072) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544072)
;

-- 2022-03-02T15:37:00.235Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rückerstattung', Name='Rückerstattung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544072)
;

-- 2022-03-02T15:37:00.236Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rückerstattung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 544072
;

-- 2022-03-02T15:37:00.236Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rückerstattung', Description=NULL, Help=NULL WHERE AD_Element_ID = 544072
;

-- 2022-03-02T15:37:00.237Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rückerstattung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544072
;

-- 2022-03-02T15:37:24.542Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Flatrate Refund Configuration', PrintName='Flatrate Refund Configuration',Updated=TO_TIMESTAMP('2022-03-02 16:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544072 AND AD_Language='en_US'
;

-- 2022-03-02T15:37:24.543Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544072,'en_US')
;

