
-- Process: C_BankStatement_Import_File_Camt53_ImportAttachment(de.metas.banking.camt53.process.C_BankStatement_Import_File_Camt53_ImportAttachment)
-- 2022-10-25T13:01:59.130Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 15:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585129
;

-- Process: C_BankStatement_Import_File_Camt53_ImportAttachment(de.metas.banking.camt53.process.C_BankStatement_Import_File_Camt53_ImportAttachment)
-- 2022-10-25T13:02:01.563Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 15:02:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585129
;

-- Value: C_BankStatement_Import_File_Camt53_ImportAttachment
-- Classname: de.metas.banking.camt53.process.C_BankStatement_Import_File_Camt53_ImportAttachment
-- 2022-10-25T13:02:14.663Z
UPDATE AD_Process SET Name='Import Bank Statement File (camt53)',Updated=TO_TIMESTAMP('2022-10-25 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585129
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Imported Bill Partner Name
-- Column: C_BankStatementLine.ImportedBillPartnerName
-- 2022-10-25T14:21:56.754Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-10-25 16:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565086
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Debitor No./Kreditor No.
-- Column: C_BankStatementLine.DebitorOrCreditorId
-- 2022-10-25T14:21:56.801Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-10-25 16:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578406
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Referenznummer
-- Column: C_BankStatementLine.ReferenceNo
-- 2022-10-25T14:21:56.813Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-10-25 16:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543112
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Beschreibung
-- Column: C_BankStatementLine.Description
-- 2022-10-25T14:21:56.826Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-10-25 16:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543093
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Memo
-- Column: C_BankStatementLine.Memo
-- 2022-10-25T14:21:56.837Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-10-25 16:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543113
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Imported Bill Partner Name
-- Column: C_BankStatementLine.ImportedBillPartnerName
-- 2022-10-25T14:44:04.335Z
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2022-10-25 16:44:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565086
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Rechnung
-- Column: C_BankStatementLine.C_Invoice_ID
-- 2022-10-25T14:44:17.417Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2022-10-25 16:44:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543116
;

-- Column: C_BankStatement_Import_File.FileName
-- 2022-10-25T14:45:27.769Z
UPDATE AD_Column SET FieldLength=1024, IsMandatory='N',Updated=TO_TIMESTAMP('2022-10-25 16:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584725
;

-- 2022-10-25T14:45:34.452Z
INSERT INTO t_alter_column values('c_bankstatement_import_file','FileName','VARCHAR(1024)',null,null)
;

-- 2022-10-25T14:45:34.472Z
INSERT INTO t_alter_column values('c_bankstatement_import_file','FileName',null,'NULL',null)
;

-- Window: Kontoauszug Import-Datei, InternalName=C_BankStatement_Import_File
-- 2022-10-25T14:46:41.412Z
UPDATE AD_Window SET InternalName='C_BankStatement_Import_File',Updated=TO_TIMESTAMP('2022-10-25 16:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541623
;

-- Table: C_BankStatement_Import_File
-- 2022-10-25T14:46:51.556Z
UPDATE AD_Table SET AD_Window_ID=541623,Updated=TO_TIMESTAMP('2022-10-25 16:46:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542246
;

-- Field: Kontoauszug Import-Datei(541623,D) -> Kontoauszug Import-Datei(546658,D) -> Dateiname
-- Column: C_BankStatement_Import_File.FileName
-- 2022-10-25T14:47:20.086Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-10-25 16:47:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707795
;

