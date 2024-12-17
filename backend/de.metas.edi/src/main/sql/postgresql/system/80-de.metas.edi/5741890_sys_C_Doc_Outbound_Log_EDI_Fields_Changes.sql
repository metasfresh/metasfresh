-- UI Element: Ausgehende Belege -> Ausgehende Belege.EDI Status exportieren
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> flags.EDI Status exportieren
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- 2024-12-16T16:37:34.501Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-12-16 18:37:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543705
;

-- UI Element: Ausgehende Belege -> Ausgehende Belege.Sektion
-- Column: C_Doc_Outbound_Log.AD_Org_ID
-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> org.Sektion
-- Column: C_Doc_Outbound_Log.AD_Org_ID
-- 2024-12-16T16:37:34.731Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-12-16 18:37:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543700
;

-- UI Element: Ausgehende Belege -> Ausgehende Belege.per EDI übermitteln
-- Column: C_Doc_Outbound_Log.IsEdiEnabled
-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> flags.per EDI übermitteln
-- Column: C_Doc_Outbound_Log.IsEdiEnabled
-- 2024-12-16T16:42:36.195Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-12-16 18:42:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543690
;

-- UI Element: Ausgehende Belege -> Ausgehende Belege.EDI Status exportieren
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> flags.EDI Status exportieren
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- 2024-12-16T16:42:36.420Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-12-16 18:42:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543705
;

-- UI Element: Ausgehende Belege -> Ausgehende Belege.Sektion
-- Column: C_Doc_Outbound_Log.AD_Org_ID
-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> org.Sektion
-- Column: C_Doc_Outbound_Log.AD_Org_ID
-- 2024-12-16T16:42:36.640Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2024-12-16 18:42:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543700
;

-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- Column SQL (old):
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- 2024-12-16T16:51:42.043Z
UPDATE AD_Column SET ColumnSQL='(SELECT COALESCE(i.EDI_ExportStatus, '''') from C_Invoice i where C_Doc_Outbound_Log.AD_Table_ID = get_Table_ID(''C_Invoice'') and C_Doc_Outbound_Log.Record_ID= i.C_Invoice_ID)', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2024-12-16 18:51:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551500
;

-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- Source Table: C_Invoice
-- 2024-12-16T16:52:36.083Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,551500,0,540169,540453,TO_TIMESTAMP('2024-12-16 18:52:35','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',548468,548468,318,TO_TIMESTAMP('2024-12-16 18:52:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- Column SQL (old): (SELECT COALESCE(i.EDI_ExportStatus, '') from C_Invoice i where C_Doc_Outbound_Log.AD_Table_ID = get_Table_ID('C_Invoice') and C_Doc_Outbound_Log.Record_ID= i.C_Invoice_ID)
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- Column SQL (old): (SELECT COALESCE(i.EDI_ExportStatus, '') from C_Invoice i where C_Doc_Outbound_Log.AD_Table_ID = get_Table_ID('C_Invoice') and C_Doc_Outbound_Log.Record_ID= i.C_Invoice_ID)
-- 2024-12-17T08:59:34.121Z
UPDATE AD_Column SET ColumnSQL='', IsLazyLoading='N',Updated=TO_TIMESTAMP('2024-12-17 10:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551500
;

-- 2024-12-17T09:03:37.351Z
INSERT INTO t_alter_column values('c_doc_outbound_log','EDI_ExportStatus','CHAR(1)',null,null)
;

