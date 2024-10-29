-- Run mode: SWING_CLIENT

-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T12:09:13.593Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589361,952,0,10,540320,'POReference',TO_TIMESTAMP('2024-10-29 14:09:13.311','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Referenz-Nummer des Kunden','de.metas.contracts',0,40,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Referenz',0,0,TO_TIMESTAMP('2024-10-29 14:09:13.311','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-29T12:09:13.602Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589361 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-29T12:09:13.638Z
/* DDL */  select update_Column_Translation_From_AD_Element(952)
;

-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T12:09:16.389Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='N',Updated=TO_TIMESTAMP('2024-10-29 14:09:16.389','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589361
;

-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T12:09:35.925Z
UPDATE AD_Column SET FilterOperator='E', IsIdentifier='Y',Updated=TO_TIMESTAMP('2024-10-29 14:09:35.925','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589361
;

-- -- Column: C_Flatrate_Term.PMM_Product_ID
-- -- 2024-10-29T12:10:49.152Z
-- UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-10-29 14:10:49.152','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=554320
-- ;

-- -- Column: C_Flatrate_Term.POReference
-- -- 2024-10-29T12:11:07.411Z
-- UPDATE AD_Column SET SeqNo=5,Updated=TO_TIMESTAMP('2024-10-29 14:11:07.411','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589361
-- ;

-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T12:12:00.550Z
UPDATE AD_Column SET SeqNo=15,Updated=TO_TIMESTAMP('2024-10-29 14:12:00.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589361
;



-- -- 2024-10-29T12:19:09.929Z
-- INSERT INTO t_alter_column values('c_order','POReference','VARCHAR(40)',null,null)
-- ;

-- 2024-10-29T15:08:09.910Z
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN POReference VARCHAR(40)')
;



-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Referenz
-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T12:24:36.164Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589361,732011,0,540859,TO_TIMESTAMP('2024-10-29 14:24:35.944','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden',40,'de.metas.contracts','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','Y','N','N','N','N','N','Referenz',TO_TIMESTAMP('2024-10-29 14:24:35.944','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-29T12:24:36.166Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=732011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-29T12:24:36.169Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952)
;

-- 2024-10-29T12:24:36.238Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732011
;

-- 2024-10-29T12:24:36.243Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732011)
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> advanced edit -> 10 -> advanced edit.Referenz
-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T12:26:01.313Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,732011,0,540859,541104,626243,'F',TO_TIMESTAMP('2024-10-29 14:26:01.111','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','Referenz',480,0,0,TO_TIMESTAMP('2024-10-29 14:26:01.111','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> advanced edit -> 10 -> advanced edit.Referenz
-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T12:26:09.753Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-10-29 14:26:09.753','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626243
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> advanced edit -> 10 -> advanced edit.Referenz
-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T13:41:38.386Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=626243
;



-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Referenz
-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T14:55:21.264Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,732011,0,540859,541109,626244,'F',TO_TIMESTAMP('2024-10-29 16:55:21.038','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','Referenz',90,0,0,TO_TIMESTAMP('2024-10-29 16:55:21.038','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Referenz
-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T14:55:38.011Z
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2024-10-29 16:55:38.011','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626244
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Referenz
-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T14:55:48.742Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.742','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626244
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> default.Name
-- Column: C_Flatrate_Term.Bill_BPartner_ID
-- 2024-10-29T14:55:48.749Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.749','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548294
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Vertrag Status
-- Column: C_Flatrate_Term.ContractStatus
-- 2024-10-29T14:55:48.755Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.755','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548999
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Planmenge pro Maßeinheit
-- Column: C_Flatrate_Term.PlannedQtyPerUnit
-- 2024-10-29T14:55:48.762Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.762','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548270
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: C_Flatrate_Term.M_Product_ID
-- 2024-10-29T14:55:48.768Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.768','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548268
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Einzelpreis
-- Column: C_Flatrate_Term.PriceActual
-- 2024-10-29T14:55:48.774Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.774','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548258
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> default.Vertragsbedingungen
-- Column: C_Flatrate_Term.C_Flatrate_Conditions_ID
-- 2024-10-29T14:55:48.779Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.779','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548295
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> harvesting details.Erntekalender
-- Column: C_Flatrate_Term.C_Harvesting_Calendar_ID
-- 2024-10-29T14:55:48.786Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.786','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624019
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> harvesting details.Erntejahr
-- Column: C_Flatrate_Term.Harvesting_Year_ID
-- 2024-10-29T14:55:48.792Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.792','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624020
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> default.Vertragsbeginn
-- Column: C_Flatrate_Term.StartDate
-- 2024-10-29T14:55:48.797Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.797','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548298
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> default.Vertragsende
-- Column: C_Flatrate_Term.EndDate
-- 2024-10-29T14:55:48.803Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.803','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548299
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Preissystem
-- Column: C_Flatrate_Term.M_PricingSystem_ID
-- 2024-10-29T14:55:48.807Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.807','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548300
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Vertragsverlängerung
-- Column: C_Flatrate_Term.C_Flatrate_Transition_ID
-- 2024-10-29T14:55:48.812Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.812','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548301
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Kündigungsfrist
-- Column: C_Flatrate_Term.NoticeDate
-- 2024-10-29T14:55:48.816Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.816','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548302
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> flags.Autom. verlängern
-- Column: C_Flatrate_Term.IsAutoRenew
-- 2024-10-29T14:55:48.821Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.821','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548303
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> flags.Verarbeitet
-- Column: C_Flatrate_Term.Processed
-- 2024-10-29T14:55:48.826Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.826','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548304
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Vertrag Nr.
-- Column: C_Flatrate_Term.MasterDocumentNo
-- 2024-10-29T14:55:48.831Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.831','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=551392
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Vertragspartner seit
-- Column: C_Flatrate_Term.MasterStartDate
-- 2024-10-29T14:55:48.835Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.835','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548400
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Vertragspartner bis
-- Column: C_Flatrate_Term.MasterEndDate
-- 2024-10-29T14:55:48.840Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.84','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548401
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Belegstatus
-- Column: C_Flatrate_Term.DocStatus
-- 2024-10-29T14:55:48.845Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.845','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548308
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> org.Sektion
-- Column: C_Flatrate_Term.AD_Org_ID
-- 2024-10-29T14:55:48.850Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2024-10-29 16:55:48.85','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548305
;

-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T14:56:08.012Z
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-29 16:56:08.012','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589361
;

-- Column: C_Flatrate_Term.DocumentNo
-- 2024-10-29T14:57:35.926Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2024-10-29 16:57:35.926','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=557170
;

-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T14:57:36.371Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2024-10-29 16:57:36.371','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589361
;

-- Column: C_Flatrate_Term.Harvesting_Year_ID
-- 2024-10-29T14:57:36.746Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2024-10-29 16:57:36.746','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588108
;

-- Column: C_Flatrate_Term.IsSOTrx
-- 2024-10-29T14:57:37.134Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2024-10-29 16:57:37.134','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589157
;

-- Column: C_Flatrate_Term.AD_Org_ID
-- 2024-10-29T14:57:37.499Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2024-10-29 16:57:37.499','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545796
;

-- Column: C_Flatrate_Term.Bill_BPartner_ID
-- 2024-10-29T14:57:37.871Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2024-10-29 16:57:37.871','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=546056
;

-- Column: C_Flatrate_Term.StartDate
-- 2024-10-29T14:57:38.296Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2024-10-29 16:57:38.296','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=546044
;

-- Column: C_Flatrate_Term.Type_Conditions
-- 2024-10-29T14:57:38.651Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2024-10-29 16:57:38.65','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545858
;

-- Column: C_Flatrate_Term.M_Product_ID
-- 2024-10-29T14:57:39.010Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2024-10-29 16:57:39.01','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=547283
;

-- Column: C_Flatrate_Term.POReference
-- 2024-10-29T15:34:43.880Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2024-10-29 17:34:43.88','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589361
;

