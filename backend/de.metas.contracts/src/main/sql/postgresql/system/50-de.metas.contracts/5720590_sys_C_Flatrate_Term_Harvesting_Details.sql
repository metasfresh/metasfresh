-- Column: C_Flatrate_Term.C_Harvesting_Calendar_ID
-- 2024-04-02T13:05:46.846Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588107,581157,0,30,540260,540320,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2024-04-02 16:05:46.713','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2024-04-02 16:05:46.713','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-02T13:05:46.852Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588107 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-02T13:05:46.900Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157)
;

-- 2024-04-02T13:05:48.037Z
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2024-04-02T13:05:48.229Z
ALTER TABLE C_Flatrate_Term ADD CONSTRAINT CHarvestingCalendar_CFlatrateTerm FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;




-- Column: C_Flatrate_Term.Harvesting_Year_ID
-- 2024-04-02T13:50:55.669Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588108,582471,0,30,540133,540320,540647,'Harvesting_Year_ID',TO_TIMESTAMP('2024-04-02 16:50:55.525','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2024-04-02 16:50:55.525','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-02T13:50:55.671Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588108 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-02T13:50:55.673Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471)
;

-- 2024-04-02T13:50:56.513Z
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2024-04-02T13:50:56.687Z
ALTER TABLE C_Flatrate_Term ADD CONSTRAINT HarvestingYear_CFlatrateTerm FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;





-- Field: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Erntekalender
-- Column: C_Flatrate_Term.C_Harvesting_Calendar_ID
-- 2024-04-02T13:47:23.002Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588107,727311,0,540859,TO_TIMESTAMP('2024-04-02 16:47:22.917','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Erntekalender',TO_TIMESTAMP('2024-04-02 16:47:22.917','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T13:47:23.003Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T13:47:23.005Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157)
;

-- 2024-04-02T13:47:23.011Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727311
;

-- 2024-04-02T13:47:23.012Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727311)
;


-- Field: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Erntejahr
-- Column: C_Flatrate_Term.Harvesting_Year_ID
-- 2024-04-02T13:51:34.043Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588108,727312,0,540859,TO_TIMESTAMP('2024-04-02 16:51:33.919','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Erntejahr',TO_TIMESTAMP('2024-04-02 16:51:33.919','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T13:51:34.044Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T13:51:34.046Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471)
;

-- 2024-04-02T13:51:34.051Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727312
;

-- 2024-04-02T13:51:34.052Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727312)
;







-- UI Column: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10
-- UI Element Group: harvesting details
-- 2024-04-02T14:11:53.714Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540630,551740,TO_TIMESTAMP('2024-04-02 17:11:53.535','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','harvesting details',30,TO_TIMESTAMP('2024-04-02 17:11:53.535','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> harvesting details.Erntekalender
-- Column: C_Flatrate_Term.C_Harvesting_Calendar_ID
-- 2024-04-02T14:12:14.487Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727311,0,540859,551740,624019,'F',TO_TIMESTAMP('2024-04-02 17:12:14.355','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Erntekalender',10,0,0,TO_TIMESTAMP('2024-04-02 17:12:14.355','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> harvesting details.Erntejahr
-- Column: C_Flatrate_Term.Harvesting_Year_ID
-- 2024-04-02T14:12:22.079Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727312,0,540859,551740,624020,'F',TO_TIMESTAMP('2024-04-02 17:12:21.936','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Erntejahr',20,0,0,TO_TIMESTAMP('2024-04-02 17:12:21.936','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Erntekalender
-- Column: C_Flatrate_Term.C_Harvesting_Calendar_ID
-- 2024-04-02T14:13:59.004Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''ModularContract''|@Type_Conditions@=''InterimInvoice''',Updated=TO_TIMESTAMP('2024-04-02 17:13:59.004','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727311
;

-- Field: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Erntejahr
-- Column: C_Flatrate_Term.Harvesting_Year_ID
-- 2024-04-02T14:14:50.416Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''ModularContract''|@Type_Conditions@=''InterimInvoice''',Updated=TO_TIMESTAMP('2024-04-02 17:14:50.416','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727312
;






-- Run mode: SWING_CLIENT

-- Column: C_Flatrate_Term.C_Harvesting_Calendar_ID
-- 2024-04-10T18:32:53.810Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-04-10 21:32:53.81','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588107
;

-- Column: C_Flatrate_Term.Harvesting_Year_ID
-- 2024-04-10T18:33:09.376Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-04-10 21:33:09.376','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588108
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> harvesting details.Erntekalender
-- Column: C_Flatrate_Term.C_Harvesting_Calendar_ID
-- 2024-04-10T18:35:17.237Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.237','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624019
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> harvesting details.Erntejahr
-- Column: C_Flatrate_Term.Harvesting_Year_ID
-- 2024-04-10T18:35:17.248Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.248','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624020
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> default.Vertragsbeginn
-- Column: C_Flatrate_Term.StartDate
-- 2024-04-10T18:35:17.257Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.256','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548298
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> default.Vertragsende
-- Column: C_Flatrate_Term.EndDate
-- 2024-04-10T18:35:17.265Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.265','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548299
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Preissystem
-- Column: C_Flatrate_Term.M_PricingSystem_ID
-- 2024-04-10T18:35:17.300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.3','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548300
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Vertragsverlängerung
-- Column: C_Flatrate_Term.C_Flatrate_Transition_ID
-- 2024-04-10T18:35:17.308Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.308','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548301
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Kündigungsfrist
-- Column: C_Flatrate_Term.NoticeDate
-- 2024-04-10T18:35:17.316Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.316','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548302
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> flags.Autom. verlängern
-- Column: C_Flatrate_Term.IsAutoRenew
-- 2024-04-10T18:35:17.324Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.323','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548303
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> flags.Verarbeitet
-- Column: C_Flatrate_Term.Processed
-- 2024-04-10T18:35:17.332Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.332','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548304
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Vertrag Nr.
-- Column: C_Flatrate_Term.MasterDocumentNo
-- 2024-04-10T18:35:17.340Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.339','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=551392
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Vertragspartner seit
-- Column: C_Flatrate_Term.MasterStartDate
-- 2024-04-10T18:35:17.345Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.345','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548400
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Vertragspartner bis
-- Column: C_Flatrate_Term.MasterEndDate
-- 2024-04-10T18:35:17.351Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.351','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548401
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> doc.Belegstatus
-- Column: C_Flatrate_Term.DocStatus
-- 2024-04-10T18:35:17.358Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.357','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548308
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20 -> org.Sektion
-- Column: C_Flatrate_Term.AD_Org_ID
-- 2024-04-10T18:35:17.363Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2024-04-10 21:35:17.363','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548305
;

