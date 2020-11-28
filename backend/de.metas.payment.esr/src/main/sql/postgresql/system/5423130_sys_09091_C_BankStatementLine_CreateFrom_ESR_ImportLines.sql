-- 29.07.2015 14:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552605,1382,0,30,540410,'N','C_BankStatementLine_ID',TO_TIMESTAMP('2015-07-29 14:54:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Position auf einem Bankauszug zu dieser Bank','de.metas.payment.esr',10,'Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.','Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Auszugs-Position',0,TO_TIMESTAMP('2015-07-29 14:54:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 29.07.2015 14:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552605 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 29.07.2015 14:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE ESR_ImportLine ADD C_BankStatementLine_ID NUMERIC(10) DEFAULT NULL 
;

-- 29.07.2015 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552606,53355,0,30,540410,'N','C_BankStatementLine_Ref_ID',TO_TIMESTAMP('2015-07-29 14:55:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.esr',10,'Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Bankstatementline Reference',0,TO_TIMESTAMP('2015-07-29 14:55:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 29.07.2015 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552606 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 29.07.2015 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE ESR_ImportLine ADD C_BankStatementLine_Ref_ID NUMERIC(10) DEFAULT NULL 
;

-- select * from db_Columns_fk where isMissing='Y' and columnName_entityType='de.metas.payment.esr'
ALTER TABLE ESR_ImportLine ADD CONSTRAINT CBankStatementLine_ESRImportLi FOREIGN KEY (C_BankStatementLine_ID) REFERENCES C_BankStatementLine DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE ESR_ImportLine ADD CONSTRAINT CBankStatementLineRef_ESRImpor FOREIGN KEY (C_BankStatementLine_Ref_ID) REFERENCES C_BankStatementLine_Ref DEFERRABLE INITIALLY DEFERRED;


























-- 29.07.2015 15:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540592,'de.metas.payment.esr.process.C_BankStatementLine_CreateFrom_ESR_Import','N',TO_TIMESTAMP('2015-07-29 15:20:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','N','N','N','N',0,'Bankauszugzeilen aus "ESR Zahlungsimport"','N','Y',0,0,'Java',TO_TIMESTAMP('2015-07-29 15:20:17','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatementLine_CreateFrom_ESR_Impor')
;

-- 29.07.2015 15:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540592 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 29.07.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541827,0,540592,540730,30,'ESR_Import_ID',TO_TIMESTAMP('2015-07-29 15:21:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr',0,'Y','N','Y','N','Y','N','ESR_Import',10,TO_TIMESTAMP('2015-07-29 15:21:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.07.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540730 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 29.07.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='ESR Zahlungsimport', PrintName='ESR Zahlungsimport',Updated=TO_TIMESTAMP('2015-07-29 15:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541827
;

-- 29.07.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541827
;

-- 29.07.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ESR_Import_ID', Name='ESR Zahlungsimport', Description=NULL, Help=NULL WHERE AD_Element_ID=541827
;

-- 29.07.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Import_ID', Name='ESR Zahlungsimport', Description=NULL, Help=NULL, AD_Element_ID=541827 WHERE UPPER(ColumnName)='ESR_IMPORT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 29.07.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Import_ID', Name='ESR Zahlungsimport', Description=NULL, Help=NULL WHERE AD_Element_ID=541827 AND IsCentrallyMaintained='Y'
;

-- 29.07.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='ESR Zahlungsimport', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541827) AND IsCentrallyMaintained='Y'
;

-- 29.07.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='ESR Zahlungsimport', Name='ESR Zahlungsimport' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541827)
;

-- 29.07.2015 15:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540592,392,TO_TIMESTAMP('2015-07-29 15:22:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',TO_TIMESTAMP('2015-07-29 15:22:11','YYYY-MM-DD HH24:MI:SS'),100)
;

