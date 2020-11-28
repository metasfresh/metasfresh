-- 2018-05-24T13:45:32.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544095,0,'AssignedAmount',TO_TIMESTAMP('2018-05-24 13:45:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Zugeordneter Betrag','Zugeordneter Betrag',TO_TIMESTAMP('2018-05-24 13:45:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-24T13:45:32.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544095 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-24T13:46:07.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Zugeordneter Betrag in der Währung des zugeordneten Rechnungskandidaten',Updated=TO_TIMESTAMP('2018-05-24 13:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544095
;

-- 2018-05-24T13:46:07.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AssignedAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Betrag in der Währung des zugeordneten Rechnungskandidaten', Help=NULL WHERE AD_Element_ID=544095
;

-- 2018-05-24T13:46:07.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Betrag in der Währung des zugeordneten Rechnungskandidaten', Help=NULL, AD_Element_ID=544095 WHERE UPPER(ColumnName)='ASSIGNEDAMOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-24T13:46:07.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Betrag in der Währung des zugeordneten Rechnungskandidaten', Help=NULL WHERE AD_Element_ID=544095 AND IsCentrallyMaintained='Y'
;

-- 2018-05-24T13:46:07.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordneter Betrag', Description='Zugeordneter Betrag in der Währung des zugeordneten Rechnungskandidaten', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544095) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544095)
;

-- 2018-05-24T13:46:18.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560197,544095,0,12,540981,'N','AssignedAmount',TO_TIMESTAMP('2018-05-24 13:46:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Zugeordneter Betrag in der Währung des zugeordneten Rechnungskandidaten','de.metas.contracts',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Zugeordneter Betrag',0,0,TO_TIMESTAMP('2018-05-24 13:46:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-05-24T13:46:18.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560197 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-24T14:58:32.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Zugeordneter Betrag in der Währung des Vertrags-Rechnungskandidaten',Updated=TO_TIMESTAMP('2018-05-24 14:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544095
;

-- 2018-05-24T14:58:32.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AssignedAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Betrag in der Währung des Vertrags-Rechnungskandidaten', Help=NULL WHERE AD_Element_ID=544095
;

-- 2018-05-24T14:58:32.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Betrag in der Währung des Vertrags-Rechnungskandidaten', Help=NULL, AD_Element_ID=544095 WHERE UPPER(ColumnName)='ASSIGNEDAMOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-24T14:58:32.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Betrag in der Währung des Vertrags-Rechnungskandidaten', Help=NULL WHERE AD_Element_ID=544095 AND IsCentrallyMaintained='Y'
;

-- 2018-05-24T14:58:32.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordneter Betrag', Description='Zugeordneter Betrag in der Währung des Vertrags-Rechnungskandidaten', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544095) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544095)
;

-- 2018-05-24T16:22:24.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate_Assignment','ALTER TABLE public.C_Invoice_Candidate_Assignment ADD COLUMN AssignedAmount NUMERIC')
;

