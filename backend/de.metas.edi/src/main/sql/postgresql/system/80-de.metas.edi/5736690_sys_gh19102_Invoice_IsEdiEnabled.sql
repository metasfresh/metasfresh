-- UI Element: Rechnung -> Rechnung.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T13:55:13.847Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551567,0,263,626188,541214,'F',TO_TIMESTAMP('2024-10-10 16:55:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Beleg soll per EDI übermittelt werden',35,0,0,TO_TIMESTAMP('2024-10-10 16:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Invoice.IsEdiEnabled
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T14:00:04.204Z
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2024-10-10 17:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548484
;

-- Column: C_Invoice.DocSubType
-- Column SQL (old): null
-- Column: C_Invoice.DocSubType
-- 2024-10-10T16:21:08.473Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589302,1018,0,10,318,'XX','DocSubType','(SELECT dt.docsubtype from C_DocType dt where dt.C_DocType_ID=(SELECT   CASE WHEN C_Invoice.C_DocType_ID > 0 THEN C_Invoice.C_DocType_ID  ELSE C_Invoice.C_DocTypeTarget_ID  END))',TO_TIMESTAMP('2024-10-10 19:21:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Document Sub Type','D',0,100,'The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Doc Sub Type',0,0,TO_TIMESTAMP('2024-10-10 19:21:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-10T16:21:08.477Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589302 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-10T16:21:08.610Z
/* DDL */  select update_Column_Translation_From_AD_Element(1018) 
;

-- Field: Rechnung -> Rechnung -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:23:54.038Z
UPDATE AD_Field SET DisplayLogic='@DocSubType@=''CS''',Updated=TO_TIMESTAMP('2024-10-10 19:23:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551567
;

-- Field: Rechnung -> Rechnung -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:26:44.484Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2024-10-10 19:26:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551567
;

-- Field: Rechnung -> Rechnung -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:30:49.254Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-10-10 19:30:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551567
;

-- Field: Rechnung -> Rechnung -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:31:29.678Z
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2024-10-10 19:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551567
;

-- UI Element: Rechnung -> Rechnung.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:31:52.023Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-10-10 19:31:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626188
;

-- UI Element: Rechnung -> Rechnung.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:32:25.572Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-10-10 19:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626188
;

-- UI Element: Rechnung -> Rechnung.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> main -> 10 -> rest.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:34:24.115Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540029, SeqNo=50,Updated=TO_TIMESTAMP('2024-10-10 19:34:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626188
;

-- UI Element: Rechnung -> Rechnung.EDI Status
-- Column: C_Invoice.EDI_ExportStatus
-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.EDI Status
-- Column: C_Invoice.EDI_ExportStatus
-- 2024-10-10T16:34:27.094Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2024-10-10 19:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547015
;

-- UI Element: Rechnung -> Rechnung.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> main -> 10 -> rest.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:35:31.220Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2024-10-10 19:35:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626188
;

-- Field: Rechnung -> Rechnung -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:37:18.090Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2024-10-10 19:37:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551567
;

-- Field: Rechnung -> Rechnung -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:40:41.807Z
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''ARC''',Updated=TO_TIMESTAMP('2024-10-10 19:40:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551567
;

-- Field: Rechnung -> Rechnung -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:41:58.560Z
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''ARC'' & @DocSubType@=''CS''',Updated=TO_TIMESTAMP('2024-10-10 19:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551567
;

-- UI Element: Rechnung -> Rechnung.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:43:05.271Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541214, SeqNo=100,Updated=TO_TIMESTAMP('2024-10-10 19:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626188
;

-- UI Element: Rechnung -> Rechnung.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:43:32.233Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-10-10 19:43:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626188
;

-- UI Element: Rechnung -> Rechnung.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2024-10-10T16:43:39.777Z
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2024-10-10 19:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626188
;

-- Column: C_Doc_Outbound_Log.IsEdiEnabled
-- Column SQL (old): (select bp.IsEdiInvoicRecipient from C_BPartner bp where bp.C_BPartner_ID=C_Doc_Outbound_Log.C_BPartner_ID)
-- Column: C_Doc_Outbound_Log.IsEdiEnabled
-- Column SQL (old): (select bp.IsEdiInvoicRecipient from C_BPartner bp where bp.C_BPartner_ID=C_Doc_Outbound_Log.C_BPartner_ID)
-- 2024-10-10T17:47:55.979Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN ''CS'' = (SELECT docsubtype from C_DocType where C_DocType_ID = C_Doc_Outbound_Log.C_DocType_ID) THEN (SELECT invoice.IsEdiEnabled from C_Invoice invoice where invoice.C_Invoice_ID = C_Doc_Outbound_Log.Record_ID) ELSE ((SELECT bp.IsEdiInvoicRecipient from C_BPartner bp where bp.C_BPartner_ID = C_Doc_Outbound_Log.C_BPartner_ID)) END)',Updated=TO_TIMESTAMP('2024-10-10 20:47:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551507
;
