-- 2018-08-29T16:31:58.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560851,544225,0,20,540981,'IsAssignedQuantityIncludeInSum',TO_TIMESTAMP('2018-08-29 16:31:58','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.contracts',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Zugeordnete Menge wird in Summe einbez.',0,0,TO_TIMESTAMP('2018-08-29 16:31:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-08-29T16:31:58.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560851 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 2018-08-29T16:35:16.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsAssignedQuantityIncludedInSum',Updated=TO_TIMESTAMP('2018-08-29 16:35:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544225
;

-- 2018-08-29T16:35:16.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAssignedQuantityIncludedInSum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL WHERE AD_Element_ID=544225
;

-- 2018-08-29T16:35:16.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAssignedQuantityIncludedInSum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL, AD_Element_ID=544225 WHERE UPPER(ColumnName)='ISASSIGNEDQUANTITYINCLUDEDINSUM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-29T16:35:16.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAssignedQuantityIncludedInSum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL WHERE AD_Element_ID=544225 AND IsCentrallyMaintained='Y'
;

-- 2018-08-29T16:35:44.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate_Assignment','ALTER TABLE public.C_Invoice_Candidate_Assignment ADD COLUMN IsAssignedQuantityIncludedInSum CHAR(1) DEFAULT ''N'' CHECK (IsAssignedQuantityIncludedInSum IN (''Y'',''N'')) NOT NULL')
;

DROP VIEW IF EXISTS C_Invoice_Candidate_Assignment_Aggregate_V;
CREATE VIEW C_Invoice_Candidate_Assignment_Aggregate_V AS
SELECT
	C_Invoice_Candidate_Term_ID,
	C_Flatrate_RefundConfig_ID,
	SUM(CASE WHEN IsAssignedQuantityIncludedInSum='Y' THEN AssignedQuantity ELSE 0 END) AS AssignedQuantity,
	SUM(AssignedMoneyAmount) AS AssignedMoneyAmount
FROM C_Invoice_Candidate_Assignment
WHERE IsActive='Y'
GROUP BY 
	C_Invoice_Candidate_Term_ID,
	C_Flatrate_RefundConfig_ID;
	
-- 2018-08-29T21:08:49.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560852,544095,0,37,541009,'AssignedMoneyAmount',TO_TIMESTAMP('2018-08-29 21:08:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.','de.metas.contracts',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Zugeordneter Geldbetrag',0,0,TO_TIMESTAMP('2018-08-29 21:08:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-08-29T21:08:49.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560852 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-29T22:55:54.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540437
;

-- 2018-08-29T22:55:54.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540437
;

DROP INDEX IF EXISTS public.uc_c_invoice_candidate_assignment_assigned_id;

-- 2018-08-29T23:36:19.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@RefundBase/P@=''F''',Updated=TO_TIMESTAMP('2018-08-29 23:36:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565580
;

-- 2018-08-29T23:41:56.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Erzeuge Vertrag',Updated=TO_TIMESTAMP('2018-08-29 23:41:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540460
;
