-- Run mode: SWING_CLIENT

-- Column: C_Dunning_Candidate.POReference
-- 2025-01-13T14:37:36.938Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589575,952,0,10,540396,'POReference','(SELECT i.POReference From C_Invoice i where  i.C_Invoice_ID = C_Dunning_Candidate.Record_ID)',TO_TIMESTAMP('2025-01-13 15:37:36.085','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Referenz-Nummer des Kunden','de.metas.dunning',0,40,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Referenz',0,0,TO_TIMESTAMP('2025-01-13 15:37:36.085','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-01-13T14:37:37Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589575 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-13T14:37:37.132Z
/* DDL */  select update_Column_Translation_From_AD_Element(952)
;

-- Column: C_Dunning_Candidate.POReference
-- 2025-01-13T14:43:18.156Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-13 15:43:18.156','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589575
;

-- Column: C_Dunning_Candidate.POReference
-- Source Table: C_Invoice
-- 2025-01-13T14:47:33.635Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,589575,0,540170,540396,TO_TIMESTAMP('2025-01-13 15:47:32.953','YYYY-MM-DD HH24:MI:SS.US'),100,'L','Y',3785,3484,318,TO_TIMESTAMP('2025-01-13 15:47:32.953','YYYY-MM-DD HH24:MI:SS.US'),100)
;

CREATE INDEX C_Invoice_POReference
    ON C_Invoice (C_Invoice_ID, POReference)
;

-- Field: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> Referenz
-- Column: C_Dunning_Candidate.POReference
-- 2025-01-13T14:56:21.884Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589575,734645,0,540424,0,TO_TIMESTAMP('2025-01-13 15:56:20.853','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden',0,'D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','Y','Y','N','N','N','N','N','N','Referenz',0,210,0,1,1,TO_TIMESTAMP('2025-01-13 15:56:20.853','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-01-13T14:56:21.938Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734645 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-13T14:56:21.990Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952)
;

-- 2025-01-13T14:56:22.056Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734645
;

-- 2025-01-13T14:56:22.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734645)
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> default.Referenz
-- Column: C_Dunning_Candidate.POReference
-- 2025-01-13T14:57:17.445Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734645,0,540424,541192,627789,'F',TO_TIMESTAMP('2025-01-13 15:57:16.731','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Referenz',45,0,0,TO_TIMESTAMP('2025-01-13 15:57:16.731','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> default.Referenz
-- Column: C_Dunning_Candidate.POReference
-- 2025-01-13T14:57:49.858Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-01-13 15:57:49.858','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=627789
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> default.Datensatz
-- Column: C_Dunning_Candidate.Record_ID
-- 2025-01-13T14:57:50.180Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-01-13 15:57:50.179','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548988
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> dates.Datum Fälligkeit
-- Column: C_Dunning_Candidate.DueDate
-- 2025-01-13T14:57:50.502Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-01-13 15:57:50.502','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548973
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> default.Mahnstufe
-- Column: C_Dunning_Candidate.C_DunningLevel_ID
-- 2025-01-13T14:57:50.813Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-01-13 15:57:50.813','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548994
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Offener Betrag
-- Column: C_Dunning_Candidate.OpenAmt
-- 2025-01-13T14:57:51.131Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-01-13 15:57:51.131','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548989
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Mahnzins
-- Column: C_Dunning_Candidate.DunningInterestAmt
-- 2025-01-13T14:57:51.441Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-01-13 15:57:51.441','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548990
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Gesamtbetrag
-- Column: C_Dunning_Candidate.TotalAmt
-- 2025-01-13T14:57:51.751Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-01-13 15:57:51.751','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548992
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Währung
-- Column: C_Dunning_Candidate.C_Currency_ID
-- 2025-01-13T14:57:52.060Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-01-13 15:57:52.06','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548993
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Tage fällig
-- Column: C_Dunning_Candidate.DaysDue
-- 2025-01-13T14:57:52.371Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-01-13 15:57:52.371','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548974
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> flags.Verarbeitet
-- Column: C_Dunning_Candidate.Processed
-- 2025-01-13T14:57:52.679Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-01-13 15:57:52.679','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548984
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> flags.Mahnung erstellt
-- Column: C_Dunning_Candidate.IsDunningDocProcessed
-- 2025-01-13T14:57:52.987Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-01-13 15:57:52.987','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548985
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> dates.Dunning Date Effective
-- Column: C_Dunning_Candidate.DunningDateEffective
-- 2025-01-13T14:57:53.300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-01-13 15:57:53.3','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548970
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> dates.Dunning Date
-- Column: C_Dunning_Candidate.DunningDate
-- 2025-01-13T14:57:53.611Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-01-13 15:57:53.611','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548969
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> dates.Mahnkarenz
-- Column: C_Dunning_Candidate.DunningGrace
-- 2025-01-13T14:57:53.930Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-01-13 15:57:53.93','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548991
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> org.Sektion
-- Column: C_Dunning_Candidate.AD_Org_ID
-- 2025-01-13T14:57:54.241Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-01-13 15:57:54.241','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548995
;

-- Table: C_Dunning_Candidate
-- 2025-01-13T14:59:37.699Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2025-01-13 15:59:37.542','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=540396
;

-- Tab: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze
-- Table: C_Dunning_Candidate
-- 2025-01-13T15:01:18.735Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='Y',Updated=TO_TIMESTAMP('2025-01-13 16:01:18.735','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=540424
;

