-- 2018-05-14T14:41:51.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544723,0,TO_TIMESTAMP('2018-05-14 14:41:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Rückvergütungskonfiguration fehlt','E',TO_TIMESTAMP('2018-05-14 14:41:51','YYYY-MM-DD HH24:MI:SS'),100,'Conditions_Error_RefundConfigMissing')
;

-- 2018-05-14T14:41:51.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544723 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-05-14T14:41:58.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-14 14:41:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544723 AND AD_Language='de_CH'
;

-- 2018-05-14T14:42:12.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-14 14:42:12','YYYY-MM-DD HH24:MI:SS'),MsgText='missing refund config' WHERE AD_Message_ID=544723 AND AD_Language='en_US'
;

-- 2018-05-14T14:46:23.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.contracts.process.C_Flatrate_Term_Create_For_BPartners',Updated=TO_TIMESTAMP('2018-05-14 14:46:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540460
;

-- 2018-05-14T20:57:26.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-14 20:57:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544075 AND AD_Language='de_CH'
;

-- 2018-05-14T20:57:26.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544075,'de_CH') 
;

-- 2018-05-14T20:57:38.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-14 20:57:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Assigned invoice candidate',PrintName='Assigned invoice candidate' WHERE AD_Element_ID=544075 AND AD_Language='en_US'
;

-- 2018-05-14T20:57:38.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544075,'en_US') 
;

-- 2018-05-14T20:58:38.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_Invoice_Candidate_Term_ID',Updated=TO_TIMESTAMP('2018-05-14 20:58:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541266
;

-- 2018-05-14T20:58:38.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Candidate_Term_ID', Name='Rechnungskandidat', Description='Eindeutige Identifikationsnummer eines Rechnungskandidaten', Help=NULL WHERE AD_Element_ID=541266
;

-- 2018-05-14T20:58:38.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Term_ID', Name='Rechnungskandidat', Description='Eindeutige Identifikationsnummer eines Rechnungskandidaten', Help=NULL, AD_Element_ID=541266 WHERE UPPER(ColumnName)='C_INVOICE_CANDIDATE_TERM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-14T20:58:38.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Term_ID', Name='Rechnungskandidat', Description='Eindeutige Identifikationsnummer eines Rechnungskandidaten', Help=NULL WHERE AD_Element_ID=541266 AND IsCentrallyMaintained='Y'
;

-- 2018-05-14T20:58:52.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Name='Vertrag-Rechnungskandidat', PrintName='Vertrag-Rechnungskandidat',Updated=TO_TIMESTAMP('2018-05-14 20:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541266
;

-- 2018-05-14T20:58:52.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Candidate_Term_ID', Name='Vertrag-Rechnungskandidat', Description='', Help=NULL WHERE AD_Element_ID=541266
;

-- 2018-05-14T20:58:52.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Term_ID', Name='Vertrag-Rechnungskandidat', Description='', Help=NULL, AD_Element_ID=541266 WHERE UPPER(ColumnName)='C_INVOICE_CANDIDATE_TERM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-14T20:58:52.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_Term_ID', Name='Vertrag-Rechnungskandidat', Description='', Help=NULL WHERE AD_Element_ID=541266 AND IsCentrallyMaintained='Y'
;

-- 2018-05-14T20:58:52.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertrag-Rechnungskandidat', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541266) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541266)
;

-- 2018-05-14T20:58:52.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vertrag-Rechnungskandidat', Name='Vertrag-Rechnungskandidat' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541266)
;

-- 2018-05-16T14:48:35.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540437,0,540981,TO_TIMESTAMP('2018-05-16 14:48:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Y','UC_C_Invoice_Candidate_Assignment_Assigned_ID','N',TO_TIMESTAMP('2018-05-16 14:48:35','YYYY-MM-DD HH24:MI:SS'),100,'ISActive=''Y''')
;

-- 2018-05-16T14:48:35.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540437 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-05-16T14:49:25.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560145,540863,540437,0,TO_TIMESTAMP('2018-05-16 14:49:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',10,TO_TIMESTAMP('2018-05-16 14:49:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T18:31:17.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Help='This table allowes to assing many assignable invoice candidates to one candidate that references a refund cotnract C_FlatrateTerm.
The assignable contracts all match that respective term via their product and bill bpartner',Updated=TO_TIMESTAMP('2018-05-16 18:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540981
;

-- 2018-05-16T18:31:38.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540279,Updated=TO_TIMESTAMP('2018-05-16 18:31:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560144
;

-- 2018-05-16T18:31:48.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540266,Updated=TO_TIMESTAMP('2018-05-16 18:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560144
;

-- 2018-05-16T18:31:54.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_Invoice_Candidate_Assignment (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Flatrate_Term_ID NUMERIC(10), C_Invoice_Candidate_Assigned_ID NUMERIC(10) NOT NULL, C_Invoice_Candidate_Assignment_ID NUMERIC(10) NOT NULL, C_Invoice_Candidate_Term_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CFlatrateTerm_CInvoiceCandidateAssignment FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CInvoiceCandidateAssigned_CInvoiceCandidateAssignment FOREIGN KEY (C_Invoice_Candidate_Assigned_ID) REFERENCES public.C_Invoice_Candidate DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Invoice_Candidate_Assignment_Key PRIMARY KEY (C_Invoice_Candidate_Assignment_ID), CONSTRAINT CInvoiceCandidateTerm_CInvoiceCandidateAssignment FOREIGN KEY (C_Invoice_Candidate_Term_ID) REFERENCES public.C_Invoice_Candidate DEFERRABLE INITIALLY DEFERRED)
;

-- 2018-05-16T18:32:03.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_C_Invoice_Candidate_Assignment_Assigned_ID ON C_Invoice_Candidate_Assignment (C_Invoice_Candidate_Assigned_ID) WHERE ISActive='Y'
;

-- 2018-05-16T18:34:10.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_refundconfig','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2018-05-16T18:48:19.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_Invoice_Candidate_ID', Name='Rechnungskandidat', PrintName='Rechnungskandidat',Updated=TO_TIMESTAMP('2018-05-16 18:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541266
;

-- 2018-05-16T18:48:19.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Candidate_ID', Name='Rechnungskandidat', Description='', Help=NULL WHERE AD_Element_ID=541266
;

-- 2018-05-16T18:48:19.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_ID', Name='Rechnungskandidat', Description='', Help=NULL, AD_Element_ID=541266 WHERE UPPER(ColumnName)='C_INVOICE_CANDIDATE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-16T18:48:19.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Candidate_ID', Name='Rechnungskandidat', Description='', Help=NULL WHERE AD_Element_ID=541266 AND IsCentrallyMaintained='Y'
;

-- 2018-05-16T18:48:19.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungskandidat', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541266) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541266)
;

-- 2018-05-16T18:48:19.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungskandidat', Name='Rechnungskandidat' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541266)
;

-- 2018-05-16T18:48:38.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544086,0,'C_Invoice_Candidate_Term_ID',TO_TIMESTAMP('2018-05-16 18:48:38','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.contracts','Y','Vertrag-Rechnungskandidat','Vertrag-Rechnungskandidat',TO_TIMESTAMP('2018-05-16 18:48:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T18:48:38.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544086 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-16T18:48:49.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544086, AD_Reference_ID=19, ColumnName='C_Invoice_Candidate_Term_ID', Description='', Help=NULL, Name='Vertrag-Rechnungskandidat',Updated=TO_TIMESTAMP('2018-05-16 18:48:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560144
;

-- 2018-05-16T18:48:49.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertrag-Rechnungskandidat', Description='', Help=NULL WHERE AD_Column_ID=560144
;

-- 2018-05-16T18:48:54.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-05-16 18:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560144
;

CREATE INDEX IF NOT EXISTS c_invoice_candidate_assigned_id_c_invoice_candidate_assigned_id
   ON public.c_invoice_candidate_assignment (c_invoice_candidate_assigned_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS c_invoice_candidate_assigned_id_c_invoice_candidate_term_id
   ON public.c_invoice_candidate_assignment (c_invoice_candidate_term_id ASC NULLS LAST);

-- 2018-05-17T12:38:43.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions@=''Subscr'' | @Type_Conditions@=''Refund''', ReadOnlyLogic='@Type_Conditions@=''Procuremnt'' | @Type_Conditions@=''Subscr'' | @Type_Conditions@=''Refund''',Updated=TO_TIMESTAMP('2018-05-17 12:38:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547283
;

-- 2018-05-17T17:23:16.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2018-05-17 17:23:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546600
;

-- 2018-05-17T17:23:24.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_InvoiceSchedule_ID NUMERIC(10)')
;

-- 2018-05-17T21:23:46.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM C_ILCandHandler WHERE C_ILCandHandler_ID=540019
;

-- 2018-05-17T21:25:18.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_ILCandHandler SET Classname='de.metas.contracts.invoicecandidate.FlatrateTerm_Handler', Is_AD_User_InCharge_UI_Setting='N', Name='Laufender Vertrag', TableName='C_Flatrate_Term',Updated=TO_TIMESTAMP('2018-05-17 21:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ILCandHandler_ID=540004
;

