-- Run mode: SWING_CLIENT

-- Column: C_BPartner.IncotermLocation
-- 2025-10-31T15:30:42.444Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591466,501608,0,10,291,'XX','IncotermLocation',TO_TIMESTAMP('2025-10-31 15:30:41.879000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Anzugebender Ort für Handelsklausel','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterm Ort',0,0,TO_TIMESTAMP('2025-10-31 15:30:41.879000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-31T15:30:42.521Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591466 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-31T15:30:42.676Z
/* DDL */  select update_Column_Translation_From_AD_Element(501608)
;

-- 2025-10-31T15:38:44.137Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IncotermLocation VARCHAR(255)')
;

-- Column: C_BPartner.PO_IncotermLocation
-- 2025-10-31T15:40:00.822Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591467,584188,0,10,291,'XX','PO_IncotermLocation',TO_TIMESTAMP('2025-10-31 15:40:00.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Anzugebender Ort für Handelsklausel','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'PO Incoterm Ort',0,0,TO_TIMESTAMP('2025-10-31 15:40:00.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-31T15:40:01.018Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591467 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-31T15:40:01.171Z
/* DDL */  select update_Column_Translation_From_AD_Element(584188)
;

-- 2025-10-31T15:40:18.579Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN PO_IncotermLocation VARCHAR(255)')
;

-- Field: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> Incoterm Ort
-- Column: C_BPartner.IncotermLocation
-- 2025-10-31T15:44:08.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591466,755926,0,223,0,TO_TIMESTAMP('2025-10-31 15:44:07.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel',0,'@C_Incoterms_Customer_ID/-1@>0','D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Incoterm Ort',0,0,360,0,1,1,TO_TIMESTAMP('2025-10-31 15:44:07.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T15:44:08.443Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755926 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-31T15:44:08.517Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608)
;

-- 2025-10-31T15:44:08.605Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755926
;

-- 2025-10-31T15:44:08.680Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755926)
;

-- Field: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> Incoterms (Kunde)
-- Column: C_BPartner.C_Incoterms_Customer_ID
-- 2025-10-31T15:48:12.784Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579096,755927,0,223,0,TO_TIMESTAMP('2025-10-31 15:48:11.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Incoterms (Kunde)',0,0,370,0,1,1,TO_TIMESTAMP('2025-10-31 15:48:11.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T15:48:12.860Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755927 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-31T15:48:12.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580486)
;

-- 2025-10-31T15:48:13.014Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755927
;

-- 2025-10-31T15:48:13.088Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755927)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Incoterms (Kunde)
-- Column: C_BPartner.C_Incoterms_Customer_ID
-- 2025-10-31T15:48:51.695Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755927,0,223,540672,638535,'F',TO_TIMESTAMP('2025-10-31 15:48:51.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Incoterms (Kunde)',35,0,0,TO_TIMESTAMP('2025-10-31 15:48:51.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Incoterm Ort
-- Column: C_BPartner.IncotermLocation
-- 2025-10-31T15:49:37.135Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755926,0,223,540672,638536,'F',TO_TIMESTAMP('2025-10-31 15:49:36.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel','Y','N','N','Y','N','N','N',0,'Incoterm Ort',40,0,0,TO_TIMESTAMP('2025-10-31 15:49:36.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Incoterms (Kunde)
-- Column: C_BPartner.C_Incoterms_Customer_ID
-- 2025-10-31T15:50:09.888Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-31 15:50:09.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638535
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Incoterm Ort
-- Column: C_BPartner.IncotermLocation
-- 2025-10-31T15:50:10.343Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-31 15:50:10.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638536
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Terminplan Rechnung
-- Column: C_BPartner.C_InvoiceSchedule_ID
-- 2025-10-31T15:50:10.793Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-31 15:50:10.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000249
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Lieferung
-- Column: C_BPartner.DeliveryViaRule
-- 2025-10-31T15:50:11.242Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-31 15:50:11.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000250
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Lieferart
-- Column: C_BPartner.DeliveryRule
-- 2025-10-31T15:50:11.691Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-31 15:50:11.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552192
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Preissystem
-- Column: C_BPartner.M_PricingSystem_ID
-- 2025-10-31T15:50:12.139Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-10-31 15:50:12.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545722
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Mutate Price
-- Column: C_BPartner.IsAllowPriceMutation
-- 2025-10-31T15:50:12.587Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-10-31 15:50:12.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560686
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Rabatt Schema
-- Column: C_BPartner.M_DiscountSchema_ID
-- 2025-10-31T15:50:13.034Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-10-31 15:50:13.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545723
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Mahnung
-- Column: C_BPartner.C_Dunning_ID
-- 2025-10-31T15:50:13.485Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-10-31 15:50:13.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545724
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Erster Verkauf
-- Column: C_BPartner.FirstSale
-- 2025-10-31T15:50:13.931Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-10-31 15:50:13.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000266
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.IsAllowActionPrice
-- Column: C_BPartner.IsAllowActionPrice
-- 2025-10-31T15:50:14.381Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-10-31 15:50:14.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560295
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Vollständige LU Erforderlich
-- Column: C_BPartner.IsFullLURequired
-- 2025-10-31T15:50:14.832Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-10-31 15:50:14.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631381
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Sektion
-- Column: C_BPartner.AD_Org_ID
-- 2025-10-31T15:50:15.280Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-10-31 15:50:15.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545731
;

-- Field: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> Incoterms (Lieferant)
-- Column: C_BPartner.C_Incoterms_Vendor_ID
-- 2025-10-31T15:55:28.035Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579095,755928,0,224,0,TO_TIMESTAMP('2025-10-31 15:55:27.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Incoterms (Lieferant)',0,0,220,0,1,1,TO_TIMESTAMP('2025-10-31 15:55:27.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T15:55:28.115Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-31T15:55:28.191Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580487)
;

-- 2025-10-31T15:55:28.267Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755928
;

-- 2025-10-31T15:55:28.342Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755928)
;

-- Field: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> PO Incoterm Ort
-- Column: C_BPartner.PO_IncotermLocation
-- 2025-10-31T16:55:56.866Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591467,755929,0,224,0,TO_TIMESTAMP('2025-10-31 16:55:55.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'PO Incoterm Ort',0,0,230,0,1,1,TO_TIMESTAMP('2025-10-31 16:55:55.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T16:55:56.942Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755929 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-31T16:55:57.019Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584188)
;

-- 2025-10-31T16:55:57.100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755929
;

-- 2025-10-31T16:55:57.174Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755929)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.PO_Incoterm
-- Column: C_BPartner.PO_Incoterm
-- 2025-10-31T16:59:24.895Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2025-10-31 16:59:24.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=584844
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Incoterms (Lieferant)
-- Column: C_BPartner.C_Incoterms_Vendor_ID
-- 2025-10-31T16:59:58.838Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755928,0,224,1000033,638537,'F',TO_TIMESTAMP('2025-10-31 16:59:58.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Incoterms (Lieferant)',42,0,0,TO_TIMESTAMP('2025-10-31 16:59:58.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.PO Incoterm Ort
-- Column: C_BPartner.PO_IncotermLocation
-- 2025-10-31T17:00:32.994Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755929,0,224,1000033,638538,'F',TO_TIMESTAMP('2025-10-31 17:00:32.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel','Y','N','N','Y','N','N','N',0,'PO Incoterm Ort',44,0,0,TO_TIMESTAMP('2025-10-31 17:00:32.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.PO_Incoterm
-- Column: C_BPartner.PO_Incoterm
-- 2025-10-31T17:01:01.853Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-10-31 17:01:01.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=584844
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Incoterms (Lieferant)
-- Column: C_BPartner.C_Incoterms_Vendor_ID
-- 2025-10-31T17:01:02.299Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-31 17:01:02.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638537
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.PO Incoterm Ort
-- Column: C_BPartner.PO_IncotermLocation
-- 2025-10-31T17:01:02.755Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-31 17:01:02.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638538
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Lieferung
-- Column: C_BPartner.PO_DeliveryViaRule
-- 2025-10-31T17:01:03.212Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-31 17:01:03.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000271
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Einkaufspreissystem
-- Column: C_BPartner.PO_PricingSystem_ID
-- 2025-10-31T17:01:03.669Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-31 17:01:03.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000281
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Rabattschema
-- Column: C_BPartner.PO_DiscountSchema_ID
-- 2025-10-31T17:01:04.119Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-31 17:01:04.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000279
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Rechnungsstellung (Kreditoren)
-- Column: C_BPartner.PO_InvoiceRule
-- 2025-10-31T17:01:04.570Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-10-31 17:01:04.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=594987
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Anbauplanung
-- Column: C_BPartner.IsPlanning
-- 2025-10-31T17:01:05.016Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-10-31 17:01:05.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000280
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Urproduzent
-- Column: C_BPartner.Fresh_Urproduzent
-- 2025-10-31T17:01:05.468Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-10-31 17:01:05.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000282
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner.AD_Org_ID
-- 2025-10-31T17:01:05.919Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-10-31 17:01:05.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546537
;

-- Field: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> PO Incoterm Ort
-- Column: C_BPartner.PO_IncotermLocation
-- 2025-10-31T17:03:43.256Z
UPDATE AD_Field SET DisplayLogic='@C_Incoterms_Vendor_ID/-1@>0',Updated=TO_TIMESTAMP('2025-10-31 17:03:43.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755929
;

-- Field: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> PO Incoterms
-- Column: C_BPartner.C_Incoterms_Vendor_ID
-- 2025-10-31T17:04:50.504Z
UPDATE AD_Field SET AD_Name_ID=584187, Description=NULL, Help=NULL, Name='PO Incoterms',Updated=TO_TIMESTAMP('2025-10-31 17:04:50.504000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755928
;

-- 2025-10-31T17:04:50.582Z
UPDATE AD_Field_Trl trl SET Name='PO Incoterms' WHERE AD_Field_ID=755928 AND AD_Language='de_DE'
;

-- 2025-10-31T17:04:50.662Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584187)
;

-- 2025-10-31T17:04:50.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755928
;

-- 2025-10-31T17:04:50.818Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755928)
;

-- Field: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> Incoterms
-- Column: C_BPartner.C_Incoterms_Customer_ID
-- 2025-10-31T17:08:45.020Z
UPDATE AD_Field SET AD_Name_ID=579927, Description=NULL, Help=NULL, Name='Incoterms',Updated=TO_TIMESTAMP('2025-10-31 17:08:45.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755927
;

-- 2025-10-31T17:08:45.094Z
UPDATE AD_Field_Trl trl SET Name='Incoterms' WHERE AD_Field_ID=755927 AND AD_Language='de_DE'
;

-- 2025-10-31T17:08:45.172Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927)
;

-- 2025-10-31T17:08:45.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755927
;

-- 2025-10-31T17:08:45.326Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755927)
;

-- Column: C_BPartner.IncotermLocation
-- 2025-11-03T09:56:25.694Z
UPDATE AD_Column SET MandatoryLogic='@C_Incoterms_Customer_ID/-1@>0',Updated=TO_TIMESTAMP('2025-11-03 09:56:25.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591466
;

-- Column: C_BPartner.PO_IncotermLocation
-- 2025-11-03T09:57:16.103Z
UPDATE AD_Column SET MandatoryLogic='@C_Incoterms_Vendor_ID/-1@>0',Updated=TO_TIMESTAMP('2025-11-03 09:57:16.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591467
;
