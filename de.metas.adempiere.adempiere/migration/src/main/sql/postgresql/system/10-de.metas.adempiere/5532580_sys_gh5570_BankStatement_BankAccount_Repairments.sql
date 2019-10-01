
-- 2019-09-30T15:32:41.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=230,Updated=TO_TIMESTAMP('2019-09-30 18:32:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3994
;

-- 2019-09-30T15:34:32.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540457,'C_BP_BankAccount.C_BPartner_ID = (select C_BPartner_ID from C_BPartner where C_BPArtner.AD_OrgBP_ID=@AD_Org_ID@)
AND
NOT EXISTS (SELECT 1 FROM  C_Bank b WHERE  b.C_Bank_ID=C_BP_BankAccount.C_Bank_ID AND b.IsCashBank=''Y'')
',TO_TIMESTAMP('2019-09-30 18:34:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BP_BankAccount NO Cash','S',TO_TIMESTAMP('2019-09-30 18:34:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T15:34:51.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Val_Rule_ID=540457,Updated=TO_TIMESTAMP('2019-09-30 18:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3994
;

-- 2019-09-30T15:40:55.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,558327,0,540812,563131,540871,'F',TO_TIMESTAMP('2019-09-30 18:40:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name on creditcard',70,0,0,TO_TIMESTAMP('2019-09-30 18:40:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T15:42:27.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-09-30 18:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563131
;

-- 2019-09-30T15:44:40.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540335, SeqNo=70,Updated=TO_TIMESTAMP('2019-09-30 18:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563131
;

-- 2019-09-30T15:45:16.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-09-30 18:45:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563131
;

-- 2019-09-30T15:50:08.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Name auf Kreditkarte', PrintName='Name auf Kreditkarte',Updated=TO_TIMESTAMP('2019-09-30 18:50:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1354 AND AD_Language='de_DE'
;

-- 2019-09-30T15:50:08.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1354,'de_DE') 
;

-- 2019-09-30T15:50:08.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1354,'de_DE') 
;

-- 2019-09-30T15:50:08.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='A_Name', Name='Name auf Kreditkarte', Description='Name auf Kreditkarte oder des Kontoeigners', Help='"Name" bezeichnet den Namen des Eigentümers von Kreditkarte oder Konto.' WHERE AD_Element_ID=1354
;

-- 2019-09-30T15:50:08.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='A_Name', Name='Name auf Kreditkarte', Description='Name auf Kreditkarte oder des Kontoeigners', Help='"Name" bezeichnet den Namen des Eigentümers von Kreditkarte oder Konto.', AD_Element_ID=1354 WHERE UPPER(ColumnName)='A_NAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-30T15:50:08.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='A_Name', Name='Name auf Kreditkarte', Description='Name auf Kreditkarte oder des Kontoeigners', Help='"Name" bezeichnet den Namen des Eigentümers von Kreditkarte oder Konto.' WHERE AD_Element_ID=1354 AND IsCentrallyMaintained='Y'
;

-- 2019-09-30T15:50:08.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name auf Kreditkarte', Description='Name auf Kreditkarte oder des Kontoeigners', Help='"Name" bezeichnet den Namen des Eigentümers von Kreditkarte oder Konto.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1354) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1354)
;

-- 2019-09-30T15:50:08.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Name auf Kreditkarte', Name='Name auf Kreditkarte' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1354)
;

-- 2019-09-30T15:50:08.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Name auf Kreditkarte', Description='Name auf Kreditkarte oder des Kontoeigners', Help='"Name" bezeichnet den Namen des Eigentümers von Kreditkarte oder Konto.', CommitWarning = NULL WHERE AD_Element_ID = 1354
;

-- 2019-09-30T15:50:08.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Name auf Kreditkarte', Description='Name auf Kreditkarte oder des Kontoeigners', Help='"Name" bezeichnet den Namen des Eigentümers von Kreditkarte oder Konto.' WHERE AD_Element_ID = 1354
;

-- 2019-09-30T15:50:08.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Name auf Kreditkarte', Description = 'Name auf Kreditkarte oder des Kontoeigners', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1354
;

-- 2019-09-30T15:50:14.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Name auf Kreditkarte', PrintName='Name auf Kreditkarte',Updated=TO_TIMESTAMP('2019-09-30 18:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1354 AND AD_Language='de_CH'
;

-- 2019-09-30T15:50:14.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1354,'de_CH') 
;

