
DROP TABLE IF EXISTS C_PartialPayment_Overview_Line;



DROP TABLE IF EXISTS C_PartialPayment_Overview;


-- 2022-08-01T13:19:36.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TableName='C_InterimInvoice_FlatrateTerm_Line',Updated=TO_TIMESTAMP('2022-08-01 16:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542194
;

-- 2022-08-01T13:19:36.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='C_InterimInvoice_FlatrateTerm_Line',Updated=TO_TIMESTAMP('2022-08-01 16:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555986
;

-- 2022-08-01T13:19:36.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE C_PartialPayment_Overview_Line_SEQ RENAME TO C_InterimInvoice_FlatrateTerm_Line_SEQ
;

-- 2022-08-01T13:20:08.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Interim Invoice Flatrate Term Line',Updated=TO_TIMESTAMP('2022-08-01 16:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542194
;

-- 2022-08-01T13:20:56.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TableName='C_InterimInvoice_FlatrateTerm',Updated=TO_TIMESTAMP('2022-08-01 16:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542193
;

-- 2022-08-01T13:20:56.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='C_InterimInvoice_FlatrateTerm',Updated=TO_TIMESTAMP('2022-08-01 16:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555985
;

-- 2022-08-01T13:20:56.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE C_PartialPayment_Overview_SEQ RENAME TO C_InterimInvoice_FlatrateTerm_SEQ
;

-- 2022-08-01T13:21:12.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Interim Invoice Flatrate Term',Updated=TO_TIMESTAMP('2022-08-01 16:21:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542193
;

-- 2022-08-01T13:21:43.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=610770
;

-- 2022-08-01T13:21:43.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702487
;

-- 2022-08-01T13:21:43.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=702487
;

-- 2022-08-01T13:21:43.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=702487
;

-- 2022-08-01T13:22:35.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702508
;

-- 2022-08-01T13:22:35.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=702508
;

-- 2022-08-01T13:22:35.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=702508
;

-- 2022-08-01T13:23:01.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702501
;

-- 2022-08-01T13:23:01.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=702501
;

-- 2022-08-01T13:23:01.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=702501
;

-- 2022-08-01T13:23:05.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702503
;

-- 2022-08-01T13:23:05.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=702503
;

-- 2022-08-01T13:23:05.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=702503
;

-- 2022-08-01T13:23:46.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_InterimInvoice_FlatrateTerm_ID',Updated=TO_TIMESTAMP('2022-08-01 16:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581186
;

-- 2022-08-01T13:23:46.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_InterimInvoice_FlatrateTerm_ID', Name='Akonto Rechnungskandidat', Description=NULL, Help=NULL WHERE AD_Element_ID=581186
;

-- 2022-08-01T13:23:46.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_InterimInvoice_FlatrateTerm_ID', Name='Akonto Rechnungskandidat', Description=NULL, Help=NULL, AD_Element_ID=581186 WHERE UPPER(ColumnName)='C_INTERIMINVOICE_FLATRATETERM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-01T13:23:46.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_InterimInvoice_FlatrateTerm_ID', Name='Akonto Rechnungskandidat', Description=NULL, Help=NULL WHERE AD_Element_ID=581186 AND IsCentrallyMaintained='Y'
;

-- 2022-08-01T13:24:27.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_Interim_Invoice_Candidate_ID',Updated=TO_TIMESTAMP('2022-08-01 16:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581186
;

-- 2022-08-01T13:24:27.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Interim_Invoice_Candidate_ID', Name='Akonto Rechnungskandidat', Description=NULL, Help=NULL WHERE AD_Element_ID=581186
;

-- 2022-08-01T13:24:27.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Interim_Invoice_Candidate_ID', Name='Akonto Rechnungskandidat', Description=NULL, Help=NULL, AD_Element_ID=581186 WHERE UPPER(ColumnName)='C_INTERIM_INVOICE_CANDIDATE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-01T13:24:27.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Interim_Invoice_Candidate_ID', Name='Akonto Rechnungskandidat', Description=NULL, Help=NULL WHERE AD_Element_ID=581186 AND IsCentrallyMaintained='Y'
;

-- 2022-08-01T13:24:42.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Interim Invoice Candidate', PrintName='Interim Invoice Candidate',Updated=TO_TIMESTAMP('2022-08-01 16:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581186 AND AD_Language='en_US'
;

-- 2022-08-01T13:24:42.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581186,'en_US') 
;

-- 2022-08-01T13:25:51.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_InterimInvoice_FlatrateTerm_ID',Updated=TO_TIMESTAMP('2022-08-01 16:25:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581184
;

-- 2022-08-01T13:25:51.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_InterimInvoice_FlatrateTerm_ID', Name='Akontozahlung Übersicht', Description=NULL, Help=NULL WHERE AD_Element_ID=581184
;

-- 2022-08-01T13:25:51.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_InterimInvoice_FlatrateTerm_ID', Name='Akontozahlung Übersicht', Description=NULL, Help=NULL, AD_Element_ID=581184 WHERE UPPER(ColumnName)='C_INTERIMINVOICE_FLATRATETERM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-01T13:25:51.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_InterimInvoice_FlatrateTerm_ID', Name='Akontozahlung Übersicht', Description=NULL, Help=NULL WHERE AD_Element_ID=581184 AND IsCentrallyMaintained='Y'
;

-- 2022-08-01T13:26:19.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Interim Invoice Flatrate Term',Updated=TO_TIMESTAMP('2022-08-01 16:26:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581184 AND AD_Language='en_US'
;

-- 2022-08-01T13:26:19.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581184,'en_US') 
;

-- 2022-08-01T13:27:11.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_InterimInvoice_FlatrateTerm_Line_ID',Updated=TO_TIMESTAMP('2022-08-01 16:27:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581188
;

-- 2022-08-01T13:27:11.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_InterimInvoice_FlatrateTerm_Line_ID', Name='Akontozahlung Übersicht Position', Description=NULL, Help=NULL WHERE AD_Element_ID=581188
;

-- 2022-08-01T13:27:11.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_InterimInvoice_FlatrateTerm_Line_ID', Name='Akontozahlung Übersicht Position', Description=NULL, Help=NULL, AD_Element_ID=581188 WHERE UPPER(ColumnName)='C_INTERIMINVOICE_FLATRATETERM_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-01T13:27:11.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_InterimInvoice_FlatrateTerm_Line_ID', Name='Akontozahlung Übersicht Position', Description=NULL, Help=NULL WHERE AD_Element_ID=581188 AND IsCentrallyMaintained='Y'
;

-- 2022-08-01T13:27:29.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Interim Invoice Flatrate Term', PrintName='Interim Invoice Flatrate Term',Updated=TO_TIMESTAMP('2022-08-01 16:27:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581188 AND AD_Language='en_US'
;

-- 2022-08-01T13:27:29.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581188,'en_US') 
;



-- 2022-08-01T13:30:27.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET InternalName='C_InterimInvoice_FlatrateTerm',Updated=TO_TIMESTAMP('2022-08-01 16:30:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546485
;

-- 2022-08-01T13:30:34.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET InternalName='C_InterimInvoice_FlatrateTerm_Line',Updated=TO_TIMESTAMP('2022-08-01 16:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546486
;

-- 2022-08-01T13:35:32.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583826
;

-- 2022-08-01T13:35:33Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=583826
;

-- 2022-08-01T13:36:08.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-08-01 16:36:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583843
;

-- 2022-08-01T13:36:17.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2022-08-01 16:36:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583833
;

-- 2022-08-01T13:36:42.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_InterimInvoice_FlatrateTerm (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10), C_Flatrate_Term_ID NUMERIC(10) NOT NULL, C_Interim_Invoice_Candidate_ID NUMERIC(10), C_InterimInvoice_FlatrateTerm_ID NUMERIC(10) NOT NULL, C_Invoice_Candidate_Withholding_ID NUMERIC(10), C_Order_ID NUMERIC(10), C_OrderLine_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_ID NUMERIC(10), PriceActual NUMERIC, QtyDeliveredInUOM NUMERIC, QtyInvoiced NUMERIC, QtyOrdered NUMERIC, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCurrency_CInterimInvoiceFlatrateTerm FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CFlatrateTerm_CInterimInvoiceFlatrateTerm FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CInterimInvoiceCandidate_CInterimInvoiceFlatrateTerm FOREIGN KEY (C_Interim_Invoice_Candidate_ID) REFERENCES public.C_Invoice_Candidate DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_InterimInvoice_FlatrateTerm_Key PRIMARY KEY (C_InterimInvoice_FlatrateTerm_ID), CONSTRAINT CInvoiceCandidateWithholding_CInterimInvoiceFlatrateTerm FOREIGN KEY (C_Invoice_Candidate_Withholding_ID) REFERENCES public.C_Invoice_Candidate DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrder_CInterimInvoiceFlatrateTerm FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrderLine_CInterimInvoiceFlatrateTerm FOREIGN KEY (C_OrderLine_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CUOM_CInterimInvoiceFlatrateTerm FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MProduct_CInterimInvoiceFlatrateTerm FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;









-- 2022-08-01T13:37:18.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583850
;

-- 2022-08-01T13:37:18.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=583850
;

-- 2022-08-01T13:37:24.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_InterimInvoice_FlatrateTerm_Line (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_InterimInvoice_FlatrateTerm_ID NUMERIC(10), C_InterimInvoice_FlatrateTerm_Line_ID NUMERIC(10) NOT NULL, C_Invoice_ID NUMERIC(10), C_InvoiceLine_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_InOut_ID NUMERIC(10), M_InOutLine_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CInterimInvoiceFlatrateTerm_CInterimInvoiceFlatrateTermLine FOREIGN KEY (C_InterimInvoice_FlatrateTerm_ID) REFERENCES public.C_InterimInvoice_FlatrateTerm DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_InterimInvoice_FlatrateTerm_Line_Key PRIMARY KEY (C_InterimInvoice_FlatrateTerm_Line_ID), CONSTRAINT CInvoice_CInterimInvoiceFlatrateTermLine FOREIGN KEY (C_Invoice_ID) REFERENCES public.C_Invoice DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CInvoiceLine_CInterimInvoiceFlatrateTermLine FOREIGN KEY (C_InvoiceLine_ID) REFERENCES public.C_InvoiceLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MInOut_CInterimInvoiceFlatrateTermLine FOREIGN KEY (M_InOut_ID) REFERENCES public.M_InOut DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MInOutLine_CInterimInvoiceFlatrateTermLine FOREIGN KEY (M_InOutLine_ID) REFERENCES public.M_InOutLine DEFERRABLE INITIALLY DEFERRED)
;




-- 2022-08-01T13:38:20.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583835,702648,0,546485,TO_TIMESTAMP('2022-08-01 16:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','Y','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2022-08-01 16:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-01T13:38:20.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702648 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-01T13:38:20.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2022-08-01T13:38:20.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702648
;

-- 2022-08-01T13:38:20.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(702648)
;

-- 2022-08-01T13:38:32.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583857,702649,0,546486,TO_TIMESTAMP('2022-08-01 16:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',10,'D','The Invoice Document.','Y','Y','N','N','N','N','N','Rechnung',TO_TIMESTAMP('2022-08-01 16:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-01T13:38:32.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702649 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-01T13:38:32.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008) 
;

-- 2022-08-01T13:38:32.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702649
;

-- 2022-08-01T13:38:32.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(702649)
;

-- 2022-08-01T13:38:32.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583859,702650,0,546486,TO_TIMESTAMP('2022-08-01 16:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',10,'D','The Material Shipment / Receipt ','Y','Y','N','N','N','N','N','Lieferung/Wareneingang',TO_TIMESTAMP('2022-08-01 16:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-01T13:38:32.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702650 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-01T13:38:32.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1025) 
;

-- 2022-08-01T13:38:32.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702650
;

-- 2022-08-01T13:38:32.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(702650)
;

