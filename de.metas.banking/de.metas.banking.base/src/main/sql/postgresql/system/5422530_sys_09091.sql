-- 18.07.2015 09:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=194,Updated=TO_TIMESTAMP('2015-07-18 09:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53065
;

-- 18.07.2015 09:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2015-07-18 09:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53074
;

-- 18.07.2015 09:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-18 09:23:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53074
;

-- 18.07.2015 09:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='Y', IsReadOnly='N',Updated=TO_TIMESTAMP('2015-07-18 09:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53074
;

-- 18.07.2015 09:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552600,540,0,10,53065,'N','ReferenceNo',TO_TIMESTAMP('2015-07-18 09:54:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Ihre Kunden- oder Lieferantennummer beim Geschäftspartner','de.metas.banking',40,'Die "Referenznummer" kann auf Bestellungen und Rechnungen gedruckt werden um Ihre Dokumente beim Geschäftspartner einfacher zuordnen zu können.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Referenznummer',0,TO_TIMESTAMP('2015-07-18 09:54:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 18.07.2015 09:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552600 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 18.07.2015 09:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BankStatementLine_Ref ADD ReferenceNo VARCHAR(40) DEFAULT NULL 
;

-- 18.07.2015 09:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552600,556238,0,53074,TO_TIMESTAMP('2015-07-18 09:55:19','YYYY-MM-DD HH24:MI:SS'),100,'Ihre Kunden- oder Lieferantennummer beim Geschäftspartner',40,'de.metas.banking','Die "Referenznummer" kann auf Bestellungen und Rechnungen gedruckt werden um Ihre Dokumente beim Geschäftspartner einfacher zuordnen zu können.','Y','Y','Y','N','N','N','N','N','Referenznummer',TO_TIMESTAMP('2015-07-18 09:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.07.2015 09:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556238 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 18.07.2015 09:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2015-07-18 09:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556238
;

-- 18.07.2015 09:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2015-07-18 09:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54759
;

-- 18.07.2015 09:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2015-07-18 09:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556238
;

-- 18.07.2015 09:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2015-07-18 09:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54759
;

-- 18.07.2015 09:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2015-07-18 09:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53074
;

-- 18.07.2015 09:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2015-07-18 09:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53074
;

-- 18.07.2015 09:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsSingleRow='N',Updated=TO_TIMESTAMP('2015-07-18 09:56:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53074
;

-- 18.07.2015 10:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552601,53355,0,30,427,'N','C_BankstatementLine_Ref_ID',TO_TIMESTAMP('2015-07-18 10:14:25','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.banking',10,'Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Bankstatementline Reference',0,TO_TIMESTAMP('2015-07-18 10:14:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 18.07.2015 10:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552601 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 18.07.2015 10:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2015-07-18 10:14:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551690
;

-- 18.07.2015 10:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_PaySelectionLine ADD C_BankstatementLine_Ref_ID NUMERIC(10) DEFAULT NULL 
;
ALTER TABLE C_PaySelectionLine ADD CONSTRAINT CBankstatementLineRef_CPaySele FOREIGN KEY (C_BankstatementLine_Ref_ID) REFERENCES C_BankstatementLine_Ref DEFERRABLE INITIALLY DEFERRED;

-- 18.07.2015 10:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT CASE WHEN ''@IsMultiplePayment@''=''N'' THEN (SELECT MAX(C_BPartner_ID) FROM C_BankStatementLine_Ref ref WHERE ref.C_BankStatementLine_ID=@C_BankStatementLine_ID@) ELSE NULL END FROM C_BankStatementLine WHERE C_BankStatementLine_ID=@C_BankStatementLine_ID@',Updated=TO_TIMESTAMP('2015-07-18 10:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54384
;

-- 18.07.2015 10:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT C_Currency_ID FROM C_BP_BankAccount WHERE C_BP_BankAccount_ID=@C_BP_BankAccount_ID@',Updated=TO_TIMESTAMP('2015-07-18 10:58:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54386
;

-- select * from db_Columns_fk where isMissing='Y' and (tableName like 'C_BankStatement%' or tableName like 'C_PaySelection%')
ALTER TABLE C_BankStatementLoader ADD CONSTRAINT CBPBankAccount_CBankStatementL FOREIGN KEY (C_BP_BankAccount_ID) REFERENCES C_BP_BankAccount DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE C_BankStatementLine_Ref ADD CONSTRAINT CBankStatementLine_CBankStatem FOREIGN KEY (C_BankStatementLine_ID) REFERENCES C_BankStatementLine DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE C_PaySelection ADD CONSTRAINT CBPBankAccount_CPaySelection FOREIGN KEY (C_BP_BankAccount_ID) REFERENCES C_BP_BankAccount DEFERRABLE INITIALLY DEFERRED;

-- 18.07.2015 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_BankStatementLine_Ref_ID',Updated=TO_TIMESTAMP('2015-07-18 12:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53355
;

-- 18.07.2015 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BankStatementLine_Ref_ID', Name='Bankstatementline Reference', Description=NULL, Help=NULL WHERE AD_Element_ID=53355
;

-- 18.07.2015 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BankStatementLine_Ref_ID', Name='Bankstatementline Reference', Description=NULL, Help=NULL, AD_Element_ID=53355 WHERE UPPER(ColumnName)='C_BANKSTATEMENTLINE_REF_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 18.07.2015 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BankStatementLine_Ref_ID', Name='Bankstatementline Reference', Description=NULL, Help=NULL WHERE AD_Element_ID=53355 AND IsCentrallyMaintained='Y'
;

-- 18.07.2015 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=1136, ColumnName='TrxAmt', Description='Betrag einer Transaktion', EntityType='de.metas.banking', FieldLength=10, Help='"Bewegungs-Betrag" gibt den Betrag für einen einzelnen Vorgang an.', IsMandatory='Y', Name='Bewegungs-Betrag',Updated=TO_TIMESTAMP('2015-07-18 12:21:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54383
;

-- 18.07.2015 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=54383
;

-- 18.07.2015 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bewegungs-Betrag', Description='Betrag einer Transaktion', Help='"Bewegungs-Betrag" gibt den Betrag für einen einzelnen Vorgang an.' WHERE AD_Column_ID=54383 AND IsCentrallyMaintained='Y'
;

alter table C_BankStatementLine_Ref rename column Amount to TrxAmt;








update AD_ModelValidator set entityType='de.metas.banking' where ModelValidationClass='de.metas.banking.model.validator.Banking';

