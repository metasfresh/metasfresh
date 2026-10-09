-- Run mode: SWING_CLIENT

-- Column: I_Invoice_Candidate.C_Project_ID
-- 2025-05-02T07:55:13.242Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589922,208,0,30,542207,'C_Project_ID',TO_TIMESTAMP('2025-05-02 08:55:12.062','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt',0,0,TO_TIMESTAMP('2025-05-02 08:55:12.062','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-02T07:55:13.312Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589922 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-02T07:55:13.523Z
/* DDL */  select update_Column_Translation_From_AD_Element(208)
;

-- Column: I_Invoice_Candidate.C_Project_ID
-- 2025-05-02T07:55:34.152Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-05-02 08:55:34.152','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589922
;

-- 2025-05-02T07:55:57.378Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN C_Project_ID NUMERIC(10)')
;

-- 2025-05-02T07:55:57.432Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT CProject_IInvoiceCandidate FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Projekt
-- Column: I_Invoice_Candidate.C_Project_ID
-- 2025-05-02T07:56:23.300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589922,741992,0,546594,0,TO_TIMESTAMP('2025-05-02 08:56:22.217','YYYY-MM-DD HH24:MI:SS.US'),100,'Financial Project',0,'D','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','N','Projekt',0,50,0,1,1,TO_TIMESTAMP('2025-05-02 08:56:22.217','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-02T07:56:23.352Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-02T07:56:23.403Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2025-05-02T07:56:23.482Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741992
;

-- 2025-05-02T07:56:23.532Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741992)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> default.Projekt
-- Column: I_Invoice_Candidate.C_Project_ID
-- 2025-05-02T07:57:04.209Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741992,0,546594,549832,631389,'F',TO_TIMESTAMP('2025-05-02 08:57:03.406','YYYY-MM-DD HH24:MI:SS.US'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',90,0,0,TO_TIMESTAMP('2025-05-02 08:57:03.406','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> default.Projekt
-- Column: I_Invoice_Candidate.C_Project_ID
-- 2025-05-02T07:57:29.273Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-05-02 08:57:29.273','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631389
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> default.Product Value
-- Column: I_Invoice_Candidate.M_Product_Value
-- 2025-05-02T07:57:29.582Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-05-02 08:57:29.582','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612174
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> val&resolved ids.Produkt
-- Column: I_Invoice_Candidate.M_Product_ID
-- 2025-05-02T07:57:29.902Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-05-02 08:57:29.902','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612227
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Bestellt/ Beauftragt
-- Column: I_Invoice_Candidate.QtyOrdered
-- 2025-05-02T07:57:30.222Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-05-02 08:57:30.222','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612229
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Gelieferte Menge
-- Column: I_Invoice_Candidate.QtyDelivered
-- 2025-05-02T07:57:30.538Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-05-02 08:57:30.538','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612230
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 20 -> doc.Auftragsdatum
-- Column: I_Invoice_Candidate.DateOrdered
-- 2025-05-02T07:57:30.852Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-05-02 08:57:30.852','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612222
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 20 -> doc.Dokument Basis Typ
-- Column: I_Invoice_Candidate.DocBaseType
-- 2025-05-02T07:57:31.162Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-05-02 08:57:31.162','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612220
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 20 -> doc.Doc Sub Type
-- Column: I_Invoice_Candidate.DocSubType
-- 2025-05-02T07:57:31.488Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-05-02 08:57:31.488','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612221
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 20 -> doc.Belegart
-- Column: I_Invoice_Candidate.C_DocType_ID
-- 2025-05-02T07:57:31.812Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-05-02 08:57:31.812','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612219
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 20 -> org.Sektion
-- Column: I_Invoice_Candidate.AD_Org_ID
-- 2025-05-02T07:57:32.142Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-05-02 08:57:32.139','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612224
;

