-- Field: Import - Rechnungskandidaten -> Import - Invoice candiate -> Belegart
-- Column: I_Invoice_Candidate.C_DocType_ID
-- 2022-08-25T06:58:43.713Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584227,705542,0,546594,TO_TIMESTAMP('2022-08-25 09:58:43','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben',10,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2022-08-25 09:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-25T06:58:43.718Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705542 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-25T06:58:43.782Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2022-08-25T06:58:43.885Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705542
;

-- 2022-08-25T06:58:43.895Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705542)
;

-- 2022-08-25T07:01:51.110Z
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2022-08-25 10:01:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549834
;

-- 2022-08-25T07:01:54.831Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2022-08-25 10:01:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549833
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Mandant
-- Column: I_Invoice_Candidate.AD_Client_ID
-- 2022-08-25T07:02:26.256Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612168
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Sektion
-- Column: I_Invoice_Candidate.AD_Org_ID
-- 2022-08-25T07:02:29.873Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612169
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Auftragsdatum
-- Column: I_Invoice_Candidate.DateOrdered
-- 2022-08-25T07:02:41.496Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612179
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Dokument Basis Typ
-- Column: I_Invoice_Candidate.DocBaseType
-- 2022-08-25T07:03:12.657Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612184
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Doc Sub Type
-- Column: I_Invoice_Candidate.DocSubType
-- 2022-08-25T07:03:15.852Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612185
;

-- 2022-08-25T07:04:20.369Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546348,549838,TO_TIMESTAMP('2022-08-25 10:04:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','doc',20,TO_TIMESTAMP('2022-08-25 10:04:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-25T07:04:24.884Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2022-08-25 10:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549833
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Belegart
-- Column: I_Invoice_Candidate.C_DocType_ID
-- 2022-08-25T07:04:56.743Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705542,0,546594,612219,549838,'F',TO_TIMESTAMP('2022-08-25 10:04:56','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','N','N','N',0,'Belegart',10,0,0,TO_TIMESTAMP('2022-08-25 10:04:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Dokument Basis Typ
-- Column: I_Invoice_Candidate.DocBaseType
-- 2022-08-25T07:05:11.920Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705490,0,546594,612220,549838,'F',TO_TIMESTAMP('2022-08-25 10:05:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Dokument Basis Typ',20,0,0,TO_TIMESTAMP('2022-08-25 10:05:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Doc Sub Type
-- Column: I_Invoice_Candidate.DocSubType
-- 2022-08-25T07:05:22.150Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705492,0,546594,612221,549838,'F',TO_TIMESTAMP('2022-08-25 10:05:21','YYYY-MM-DD HH24:MI:SS'),100,'Document Sub Type','The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document','Y','N','N','Y','N','N','N',0,'Doc Sub Type',30,0,0,TO_TIMESTAMP('2022-08-25 10:05:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Auftragsdatum
-- Column: I_Invoice_Candidate.DateOrdered
-- 2022-08-25T07:05:38.177Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705488,0,546594,612222,549838,'F',TO_TIMESTAMP('2022-08-25 10:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','N','Y','N','N','N',0,'Auftragsdatum',40,0,0,TO_TIMESTAMP('2022-08-25 10:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Referenz
-- Column: I_Invoice_Candidate.POReference
-- 2022-08-25T07:05:54.233Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705502,0,546594,612223,549838,'F',TO_TIMESTAMP('2022-08-25 10:05:54','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Referenz',50,0,0,TO_TIMESTAMP('2022-08-25 10:05:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-25T07:06:22.905Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546348,549839,TO_TIMESTAMP('2022-08-25 10:06:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',40,TO_TIMESTAMP('2022-08-25 10:06:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Sektion
-- Column: I_Invoice_Candidate.AD_Org_ID
-- 2022-08-25T07:06:41.547Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705481,0,546594,612224,549839,'F',TO_TIMESTAMP('2022-08-25 10:06:41','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2022-08-25 10:06:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Mandant
-- Column: I_Invoice_Candidate.AD_Client_ID
-- 2022-08-25T07:06:48.872Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705480,0,546594,612225,549839,'F',TO_TIMESTAMP('2022-08-25 10:06:48','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2022-08-25 10:06:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Import - Invoice candiate
-- Column: I_Invoice_Candidate.I_Invoice_Candidate_ID
-- 2022-08-25T07:07:19.965Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612167
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Rechnungspartner
-- Column: I_Invoice_Candidate.Bill_BPartner_ID
-- 2022-08-25T07:08:05.196Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612171
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Produkt
-- Column: I_Invoice_Candidate.M_Product_ID
-- 2022-08-25T07:08:35.233Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612175
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.C_UOM_X12DE355
-- Column: I_Invoice_Candidate.X12DE355
-- 2022-08-25T07:08:35.271Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612176
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Maßeinheit
-- Column: I_Invoice_Candidate.C_UOM_ID
-- 2022-08-25T07:08:35.282Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612177
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Beschreibung
-- Column: I_Invoice_Candidate.Description
-- 2022-08-25T07:08:35.294Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612178
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Bestellt/ Beauftragt
-- Column: I_Invoice_Candidate.QtyOrdered
-- 2022-08-25T07:08:35.305Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612180
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Gelieferte Menge
-- Column: I_Invoice_Candidate.QtyDelivered
-- 2022-08-25T07:08:35.317Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612181
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Rechnungsstellung
-- Column: I_Invoice_Candidate.InvoiceRule
-- 2022-08-25T07:08:35.330Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612182
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Vorbelegtes Rechnungsdatum
-- Column: I_Invoice_Candidate.PresetDateInvoiced
-- 2022-08-25T07:08:35.342Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612183
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Externe Datensatz-Kopf-ID
-- Column: I_Invoice_Candidate.ExternalHeaderId
-- 2022-08-25T07:08:35.353Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612189
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Externe Datensatz-Zeilen-ID
-- Column: I_Invoice_Candidate.ExternalLineId
-- 2022-08-25T07:08:35.365Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612190
;

-- 2022-08-25T07:09:14.029Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546347,549840,TO_TIMESTAMP('2022-08-25 10:09:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','ids',20,TO_TIMESTAMP('2022-08-25 10:09:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Rechnungspartner
-- Column: I_Invoice_Candidate.Bill_BPartner_ID
-- 2022-08-25T07:09:26.321Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705479,0,546594,612226,549840,'F',TO_TIMESTAMP('2022-08-25 10:09:26','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner für die Rechnungsstellung','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','Y','N','N','N',0,'Rechnungspartner',10,0,0,TO_TIMESTAMP('2022-08-25 10:09:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Produkt
-- Column: I_Invoice_Candidate.M_Product_ID
-- 2022-08-25T07:09:41.771Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705501,0,546594,612227,549840,'F',TO_TIMESTAMP('2022-08-25 10:09:41','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',20,0,0,TO_TIMESTAMP('2022-08-25 10:09:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Maßeinheit
-- Column: I_Invoice_Candidate.C_UOM_ID
-- 2022-08-25T07:09:53.719Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705486,0,546594,612228,549840,'F',TO_TIMESTAMP('2022-08-25 10:09:53','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',30,0,0,TO_TIMESTAMP('2022-08-25 10:09:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-25T07:10:05.408Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546347,549841,TO_TIMESTAMP('2022-08-25 10:10:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','details',30,TO_TIMESTAMP('2022-08-25 10:10:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Bestellt/ Beauftragt
-- Column: I_Invoice_Candidate.QtyOrdered
-- 2022-08-25T07:10:23.957Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705507,0,546594,612229,549841,'F',TO_TIMESTAMP('2022-08-25 10:10:23','YYYY-MM-DD HH24:MI:SS'),100,'Bestellt/ Beauftragt','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','Y','N','N','N',0,'Bestellt/ Beauftragt',10,0,0,TO_TIMESTAMP('2022-08-25 10:10:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Gelieferte Menge
-- Column: I_Invoice_Candidate.QtyDelivered
-- 2022-08-25T07:10:37.560Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705506,0,546594,612230,549841,'F',TO_TIMESTAMP('2022-08-25 10:10:37','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','N','Y','N','N','N',0,'Gelieferte Menge',20,0,0,TO_TIMESTAMP('2022-08-25 10:10:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Rechnungsstellung
-- Column: I_Invoice_Candidate.InvoiceRule
-- 2022-08-25T07:10:56.154Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705497,0,546594,612231,549841,'F',TO_TIMESTAMP('2022-08-25 10:10:56','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.','Y','N','N','Y','N','N','N',0,'Rechnungsstellung',30,0,0,TO_TIMESTAMP('2022-08-25 10:10:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Vorbelegtes Rechnungsdatum
-- Column: I_Invoice_Candidate.PresetDateInvoiced
-- 2022-08-25T07:11:14.640Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705503,0,546594,612232,549841,'F',TO_TIMESTAMP('2022-08-25 10:11:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vorbelegtes Rechnungsdatum',40,0,0,TO_TIMESTAMP('2022-08-25 10:11:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Externe Datensatz-Kopf-ID
-- Column: I_Invoice_Candidate.ExternalHeaderId
-- 2022-08-25T07:11:28.604Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705509,0,546594,612233,549841,'F',TO_TIMESTAMP('2022-08-25 10:11:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externe Datensatz-Kopf-ID',50,0,0,TO_TIMESTAMP('2022-08-25 10:11:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Externe Datensatz-Kopf-ID
-- Column: I_Invoice_Candidate.ExternalHeaderId
-- 2022-08-25T07:11:44.211Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612233
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Referenz
-- Column: I_Invoice_Candidate.POReference
-- 2022-08-25T07:12:28.734Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612188
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Daten Import
-- Column: I_Invoice_Candidate.C_DataImport_ID
-- 2022-08-25T07:19:36.536Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612191
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Data Import Run
-- Column: I_Invoice_Candidate.C_DataImport_Run_ID
-- 2022-08-25T07:19:36.539Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612166
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Import-Fehlermeldung
-- Column: I_Invoice_Candidate.I_ErrorMsg
-- 2022-08-25T07:19:36.542Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612163
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Importiert
-- Column: I_Invoice_Candidate.I_IsImported
-- 2022-08-25T07:19:36.544Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612161
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Bill_BPartner_Value
-- Column: I_Invoice_Candidate.Bill_BPartner_Value
-- 2022-08-25T07:19:36.547Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612170
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Rechnungspartner
-- Column: I_Invoice_Candidate.Bill_BPartner_ID
-- 2022-08-25T07:19:36.550Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612226
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Rechnungsstandort
-- Column: I_Invoice_Candidate.Bill_Location_ID
-- 2022-08-25T07:19:36.552Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612172
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Rechnungskontakt
-- Column: I_Invoice_Candidate.Bill_User_ID
-- 2022-08-25T07:19:36.554Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612173
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Product Value
-- Column: I_Invoice_Candidate.M_Product_Value
-- 2022-08-25T07:19:36.556Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612174
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Produkt
-- Column: I_Invoice_Candidate.M_Product_ID
-- 2022-08-25T07:19:36.558Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612227
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Bestellt/ Beauftragt
-- Column: I_Invoice_Candidate.QtyOrdered
-- 2022-08-25T07:19:36.559Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612229
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Gelieferte Menge
-- Column: I_Invoice_Candidate.QtyDelivered
-- 2022-08-25T07:19:36.560Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612230
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Auftragsdatum
-- Column: I_Invoice_Candidate.DateOrdered
-- 2022-08-25T07:19:36.561Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612222
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Dokument Basis Typ
-- Column: I_Invoice_Candidate.DocBaseType
-- 2022-08-25T07:19:36.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612220
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Doc Sub Type
-- Column: I_Invoice_Candidate.DocSubType
-- 2022-08-25T07:19:36.564Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612221
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Belegart
-- Column: I_Invoice_Candidate.C_DocType_ID
-- 2022-08-25T07:19:36.565Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-08-25 10:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612219
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Kodierung der Mengeneinheit
-- Column: I_Invoice_Candidate.X12DE355
-- 2022-08-25T07:20:17.354Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705487,0,546594,612234,549840,'F',TO_TIMESTAMP('2022-08-25 10:20:17','YYYY-MM-DD HH24:MI:SS'),100,'Kodierung gemäß UOM EDI X12','"Kodierung" gibt die Kodierung gemäß EDI X12 Code Data Element 355 (Unit or Basis for Measurement) an.','Y','N','N','Y','N','N','N',0,'Kodierung der Mengeneinheit',40,0,0,TO_TIMESTAMP('2022-08-25 10:20:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Kodierung der Mengeneinheit
-- Column: I_Invoice_Candidate.X12DE355
-- 2022-08-25T07:20:40.503Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2022-08-25 10:20:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612234
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Maßeinheit
-- Column: I_Invoice_Candidate.C_UOM_ID
-- 2022-08-25T07:20:47.291Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2022-08-25 10:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612228
;

-- 2022-08-25T07:22:26.239Z
UPDATE AD_UI_ElementGroup SET Name='val&resolved ids',Updated=TO_TIMESTAMP('2022-08-25 10:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549840
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Sektion
-- Column: I_Invoice_Candidate.AD_Org_ID
-- 2022-08-25T07:24:12.271Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-25 10:24:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612224
;

alter table i_invoice_candidate drop constraint i_invoice_candidate_i_isimported_check;

-- Column: I_Invoice_Candidate.IsSOTrx
-- 2022-08-26T11:20:07.733Z
UPDATE AD_Column SET DefaultValue='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-26 14:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584175
;

-- 2022-08-26T11:20:10.302Z
INSERT INTO t_alter_column values('i_invoice_candidate','IsSOTrx','CHAR(1)',null,'Y')
;

-- 2022-08-26T11:20:10.347Z
UPDATE I_Invoice_Candidate SET IsSOTrx='Y' WHERE IsSOTrx IS NULL
;

-- 2022-08-26T11:20:10.350Z
INSERT INTO t_alter_column values('i_invoice_candidate','IsSOTrx',null,'NOT NULL',null)
;

-- 2022-08-26T12:18:11.670Z
INSERT INTO C_DataImport (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,C_DataImport_ID,Created,CreatedBy,DataImport_ConfigType,InternalName,IsActive,Updated,UpdatedBy) VALUES (1000000,540077,1000000,540021,TO_TIMESTAMP('2022-08-26 15:18:11','YYYY-MM-DD HH24:MI:SS'),100,'S','InvoiceCandidate','Y',TO_TIMESTAMP('2022-08-26 15:18:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-26T14:54:41.089Z
UPDATE AD_ImpFormat_Row SET DataFormat='yyyy-MM-dd',Updated=TO_TIMESTAMP('2022-08-26 17:54:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541784
;

-- 2022-08-26T14:54:47.852Z
UPDATE AD_ImpFormat_Row SET DataFormat='yyyy-MM-dd',Updated=TO_TIMESTAMP('2022-08-26 17:54:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541791
;

-- 2022-08-29T05:45:15.242Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581396,0,'OrgCode',TO_TIMESTAMP('2022-08-29 08:45:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Org Code','Org Code',TO_TIMESTAMP('2022-08-29 08:45:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T05:45:15.251Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581396 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: I_Invoice_Candidate.OrgCode
-- 2022-08-29T05:45:45.891Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584230,581396,0,10,542207,'OrgCode',TO_TIMESTAMP('2022-08-29 08:45:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Org Code',0,0,TO_TIMESTAMP('2022-08-29 08:45:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-29T05:45:45.895Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584230 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-29T05:45:45.925Z
/* DDL */  select update_Column_Translation_From_AD_Element(581396)
;

-- 2022-08-29T06:04:31.316Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584230,540077,541796,0,TO_TIMESTAMP('2022-08-29 09:04:31','YYYY-MM-DD HH24:MI:SS'),100,'C','.','N','Y','Org Suchschlüssel',160,TO_TIMESTAMP('2022-08-29 09:04:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T06:04:39.892Z
UPDATE AD_ImpFormat_Row SET ConstantValue='001',Updated=TO_TIMESTAMP('2022-08-29 09:04:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541796
;

-- 2022-08-29T06:04:45.688Z
UPDATE AD_ImpFormat_Row SET StartNo=16,Updated=TO_TIMESTAMP('2022-08-29 09:04:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541796
;

-- 2022-08-29T05:50:49.221Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN OrgCode VARCHAR(255) NOT NULL')
;


