-- 2018-08-20T12:29:19.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AssignedMoneyAmount',Updated=TO_TIMESTAMP('2018-08-20 12:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544095
;

-- 2018-08-20T12:29:19.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AssignedMoneyAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Geldbetrag in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL WHERE AD_Element_ID=544095
;

-- 2018-08-20T12:29:19.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedMoneyAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Geldbetrag in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL, AD_Element_ID=544095 WHERE UPPER(ColumnName)='ASSIGNEDMONEYAMOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-20T12:29:19.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedMoneyAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Geldbetrag in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL WHERE AD_Element_ID=544095 AND IsCentrallyMaintained='Y'
;

SELECT db_alter_table('C_Invoice_Candidate_Assignment','ALTER TABLE C_Invoice_Candidate_Assignment RENAME COLUMN AssignedAmount TO AssignedMoneyAmount;');

-- 2018-08-20T12:32:36.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544207,0,'BaseMoneyAmount',TO_TIMESTAMP('2018-08-20 12:32:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Basisbetrag','Basisbetrag',TO_TIMESTAMP('2018-08-20 12:32:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-20T12:32:36.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544207 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-20T12:32:41.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544207 AND AD_Language='de_CH'
;

-- 2018-08-20T12:32:42.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544207,'de_CH') 
;

-- 2018-08-20T12:33:03.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Base amount',PrintName='Base amount' WHERE AD_Element_ID=544207 AND AD_Language='en_US'
;

-- 2018-08-20T12:33:03.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544207,'en_US') 
;

-- 2018-08-20T12:47:08.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560767,544207,0,12,540981,'BaseMoneyAmount',TO_TIMESTAMP('2018-08-20 12:47:08','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Basisbetrag',0,0,TO_TIMESTAMP('2018-08-20 12:47:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-08-20T12:47:08.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560767 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-20T12:47:14.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate_Assignment','ALTER TABLE public.C_Invoice_Candidate_Assignment ADD COLUMN BaseMoneyAmount NUMERIC')
;

