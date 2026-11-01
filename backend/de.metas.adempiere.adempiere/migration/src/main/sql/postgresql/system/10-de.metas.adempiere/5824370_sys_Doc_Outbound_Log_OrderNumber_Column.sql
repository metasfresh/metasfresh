-- A sortable "Auftragsnr." (order-number) column on the outgoing-documents window
-- (AD_Window 540170 / AD_Tab 540474 / C_Doc_Outbound_Log, AD_Table_ID 540453).
--
-- Value = C_Order.DocumentNo of the sales order reached via the invoice the log references
-- (Record_ID -> C_Invoice (AD_Table_ID 318) -> C_Invoice.C_Order_ID -> C_Order); empty when the log is
-- not an invoice, or the invoice has no order. Virtual (ColumnSQL) column: raw C_Doc_Outbound_Log.
-- self-reference (this table has a generated PO model class) + lowercase keywords; lazy-loaded.

-- 1) AD_Element -- German in the base column, en_US as the translation override.
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,Description,UpdatedBy,Created,Updated)
VALUES (0,'Y',100,'Auftragsnr.','D','OrderNumber',585460 /*From ID Server*/,0,
        'Auftragsnr.',
        'Auftragsnummer des zugehörigen Verkaufsauftrags (über die zugehörige Rechnung).',
        100,
        TO_TIMESTAMP('2026-09-14 12:00:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-09-14 12:00:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585460
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Auftragsnr.', PrintName='Auftragsnr.',
       Description='Auftragsnummer des zugehörigen Verkaufsauftrags (über die zugehörige Rechnung).',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-14 12:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585460 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl SET Name='Order no.', PrintName='Order no.',
       Description='Sales order number of the related order, reached via the invoice.',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-14 12:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585460 AND AD_Language='en_US'
;

-- 2) The virtual column (AD_Reference 10 = String). Display + sort only: IsSelectionColumn='N'
--    (no cross-window filter propagation). Lazy-loaded (correlated cross-table subquery).
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,
                        AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,
                        IsEncrypted,AD_Table_ID,ColumnSQL,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,
                        Name,Description,EntityType,FieldLength,Version,SeqNo,PersonalDataCategory,IsCalculated,
                        Created,Updated)
VALUES (10,'N','N','N','N',0,'Y',100,
        585460 /*From ID Server*/,'N','N','N','N','Y',
        'N',540453,
        '(select o.DocumentNo from c_invoice i join c_order o on o.C_Order_ID = i.C_Order_ID where i.C_Invoice_ID = C_Doc_Outbound_Log.Record_ID and C_Doc_Outbound_Log.AD_Table_ID = 318)',
        'OrderNumber',593555 /*From ID Server*/,'N',0,100,
        'Auftragsnr.',
        'Auftragsnummer des zugehörigen Verkaufsauftrags (über die zugehörige Rechnung).',
        'D',40,0,0,'NP','Y',
        TO_TIMESTAMP('2026-09-14 12:01:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-09-14 12:01:00','YYYY-MM-DD HH24:MI:SS'))
;

UPDATE AD_Column set islazyloading = 'Y' where ad_column_id = 593555;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593555
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 3) Source-table dependencies for cache invalidation (the value reads C_Invoice + C_Order).
--    3a) invoice change (the log's Record_ID references the invoice)
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Source_Table_ID,Updated,UpdatedBy,SQL_GetTargetRecordIdBySourceRecordId)
VALUES (0,593555,0,540243 /*From ID Server*/,540453,
        TO_TIMESTAMP('2026-09-14 12:02:00','YYYY-MM-DD HH24:MI:SS'),100,
        'S','Y',318,
        TO_TIMESTAMP('2026-09-14 12:02:00','YYYY-MM-DD HH24:MI:SS'),100,
        'SELECT C_Doc_Outbound_Log_ID FROM C_Doc_Outbound_Log WHERE Record_ID=@Record_ID/-1@ AND AD_Table_ID=318')
;
--    3b) order change (order DocumentNo, reached via the invoice)
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Source_Table_ID,Updated,UpdatedBy,SQL_GetTargetRecordIdBySourceRecordId)
VALUES (0,593555,0,540244 /*From ID Server*/,540453,
        TO_TIMESTAMP('2026-09-14 12:02:10','YYYY-MM-DD HH24:MI:SS'),100,
        'S','Y',259,
        TO_TIMESTAMP('2026-09-14 12:02:10','YYYY-MM-DD HH24:MI:SS'),100,
        'SELECT l.C_Doc_Outbound_Log_ID FROM C_Doc_Outbound_Log l JOIN C_Invoice i ON i.C_Invoice_ID=l.Record_ID AND l.AD_Table_ID=318 WHERE i.C_Order_ID=@Record_ID/-1@')
;

-- 4) AD_Field on the outgoing-documents tab (540474): displayed in the grid (sortable) and the form.
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,IsFilterField,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,593555,784980 /*From ID Server*/,0,540474,
        TO_TIMESTAMP('2026-09-14 12:03:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','Y','N','N',
        'Auftragsnr.',0,230,
        TO_TIMESTAMP('2026-09-14 12:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=784980
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(585460);

DELETE FROM AD_Element_Link WHERE AD_Field_ID=784980;
SELECT AD_Element_Link_Create_Missing_Field(784980);

-- 5) AD_UI_Element pairing (same group as the sibling DatePromised field, 541071).
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784980,0,540474,541071,654748 /*From ID Server*/,'F',
        TO_TIMESTAMP('2026-09-14 12:03:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N',
        'Auftragsnr.',40,55,0,
        TO_TIMESTAMP('2026-09-14 12:03:10','YYYY-MM-DD HH24:MI:SS'),100)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585460);
