-- 2018-08-08T21:03:12.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Description='Zugeordneter Betrag in der Währung des Vertrags-Rechnungskandidaten' WHERE AD_Element_ID=544095 AND AD_Language='de_CH'
;

-- 2018-08-08T21:03:12.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544095,'de_CH') 
;

-- 2018-08-08T21:03:47.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Description='Assinged money amount in the refund candidate''s currency' WHERE AD_Element_ID=544095 AND AD_Language='en_US'
;

-- 2018-08-08T21:03:47.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544095,'en_US') 
;

-- 2018-08-08T21:04:01.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zugeordneter Geldbetrag in der Währung des Vertrags-Rechnungskandidaten.' WHERE AD_Element_ID=544095 AND AD_Language='de_CH'
;

-- 2018-08-08T21:04:01.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544095,'de_CH') 
;

-- 2018-08-08T21:04:09.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Zugeordneter Geldbetrag in der Währung des Vertrags-Rechnungskandidaten.',Updated=TO_TIMESTAMP('2018-08-08 21:04:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544095
;

-- 2018-08-08T21:04:09.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AssignedAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Geldbetrag in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL WHERE AD_Element_ID=544095
;

-- 2018-08-08T21:04:09.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Geldbetrag in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL, AD_Element_ID=544095 WHERE UPPER(ColumnName)='ASSIGNEDAMOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-08T21:04:09.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedAmount', Name='Zugeordneter Betrag', Description='Zugeordneter Geldbetrag in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL WHERE AD_Element_ID=544095 AND IsCentrallyMaintained='Y'
;

-- 2018-08-08T21:04:09.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordneter Betrag', Description='Zugeordneter Geldbetrag in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544095) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544095)
;

-- 2018-08-08T21:04:56.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544204,0,'AssignedQuantity',TO_TIMESTAMP('2018-08-08 21:04:56','YYYY-MM-DD HH24:MI:SS'),100,'Zugeordneter Menge in der Maßeinheit des jeweiligen Produktes','de.metas.contracts','Y','Zugeordnete Menge','Zugeordnete Menge',TO_TIMESTAMP('2018-08-08 21:04:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T21:04:56.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544204 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-08T21:05:06.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544204 AND AD_Language='de_CH'
;

-- 2018-08-08T21:05:06.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544204,'de_CH') 
;

-- 2018-08-08T21:05:42.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Assigned quantitiy',PrintName='Assigned quantitiy',Description='Assigned quantitiy in the respective product''s UOM.' WHERE AD_Element_ID=544204 AND AD_Language='en_US'
;

-- 2018-08-08T21:05:42.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544204,'en_US') 
;

-- 2018-08-08T21:05:44.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544204 AND AD_Language='en_US'
;

-- 2018-08-08T21:05:44.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544204,'en_US') 
;

-- 2018-08-08T21:06:22.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (29,'0',14,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-08-08 21:06:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-08-08 21:06:22','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540981,'N','AssignedQuantity',560759,'N','Y','N','N','N','N','N','N','Zugeordneter Menge in der Maßeinheit des jeweiligen Produktes',0,0,'Zugeordnete Menge',544204,'de.metas.contracts','N','N')
;

-- 2018-08-08T21:06:22.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560759 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-08T21:06:27.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate_Assignment','ALTER TABLE public.C_Invoice_Candidate_Assignment ADD COLUMN AssignedQuantity NUMERIC DEFAULT 0 NOT NULL')
;

-- 2018-08-09T08:00:59.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560760,544072,0,30,540981,'C_Flatrate_RefundConfig_ID',TO_TIMESTAMP('2018-08-09 08:00:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','C_Flatrate_RefundConfig',0,0,TO_TIMESTAMP('2018-08-09 08:00:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-08-09T08:00:59.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560760 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-09T08:01:02.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-08-09 08:01:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560760
;

-- 2018-08-09T08:01:04.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate_Assignment','ALTER TABLE public.C_Invoice_Candidate_Assignment ADD COLUMN C_Flatrate_RefundConfig_ID NUMERIC(10)')
;

-- 2018-08-09T08:01:04.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Invoice_Candidate_Assignment ADD CONSTRAINT CFlatrateRefundConfig_CInvoiceCandidateAssignment FOREIGN KEY (C_Flatrate_RefundConfig_ID) REFERENCES public.C_Flatrate_RefundConfig DEFERRABLE INITIALLY DEFERRED
;

-- 2018-08-09T08:01:08.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-08-09 08:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560760
;

-- 2018-08-09T08:01:12.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate_assignment','C_Flatrate_RefundConfig_ID','NUMERIC(10)',null,null)
;

-- 2018-08-09T08:01:12.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate_assignment','C_Flatrate_RefundConfig_ID',null,'NOT NULL',null)
;

-- 2018-08-09T08:02:09.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560760,540884,540437,0,TO_TIMESTAMP('2018-08-09 08:02:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',20,TO_TIMESTAMP('2018-08-09 08:02:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-09T08:02:17.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS uc_c_invoice_candidate_assignment_assigned_id
;

-- 2018-08-09T08:02:17.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_C_Invoice_Candidate_Assignment_Assigned_ID ON C_Invoice_Candidate_Assignment (C_Invoice_Candidate_Assigned_ID,C_Flatrate_RefundConfig_ID) WHERE ISActive='Y'
;

-- 2018-08-09T09:47:03.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsRangeFilter='Y',Updated=TO_TIMESTAMP('2018-08-09 09:47:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559438
;

