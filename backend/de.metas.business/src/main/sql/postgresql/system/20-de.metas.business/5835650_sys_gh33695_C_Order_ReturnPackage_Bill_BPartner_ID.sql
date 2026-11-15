-- Run mode: SWING_CLIENT

-- Column: C_Order_ReturnPackage.Bill_BPartner_ID (virtual: the order's invoice partner, fallback the order's partner)
-- 2026-11-13T15:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Reference_Value_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593849 /*From ID Server*/,2039,138,0,30,542618,'XX','Bill_BPartner_ID','(SELECT COALESCE(o.Bill_BPartner_ID, o.C_BPartner_ID) FROM c_order o WHERE o.C_Order_ID = C_Order_ReturnPackage.C_Order_ID)',TO_TIMESTAMP('2026-11-13 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Geschäftspartner für die Rechnungsstellung','D',0,10,'Rechnungspartner des Auftrags. Der Gebindesaldo wird je Rechnungspartner geführt.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Rechnungspartner','NP',5,0,TO_TIMESTAMP('2026-11-13 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-11-13T15:00:00.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593849 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-11-13T15:00:00.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(2039)
;

-- Column: C_Order_ReturnPackage.Bill_BPartner_ID
-- 2026-11-13T15:00:00.000Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', SelectionColumnSeqNo=5,Updated=TO_TIMESTAMP('2026-11-13 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=593849
;

-- Column: C_Order_ReturnPackage.C_BPartner_ID (filter after Rechnungspartner)
-- 2026-11-13T15:00:00.000Z
UPDATE AD_Column SET SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2026-11-13 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592856
;

-- Field: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> Rechnungspartner
-- Column: C_Order_ReturnPackage.Bill_BPartner_ID
-- 2026-11-13T15:00:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593849,788182 /*From ID Server*/,0,549320,TO_TIMESTAMP('2026-11-13 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Geschäftspartner für die Rechnungsstellung',10,'D','Rechnungspartner des Auftrags. Der Gebindesaldo wird je Rechnungspartner geführt.','Y','N','N','N','N','N','N','N','Rechnungspartner',TO_TIMESTAMP('2026-11-13 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-11-13T15:00:00.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=788182 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-11-13T15:00:00.000Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2039)
;

-- 2026-11-13T15:00:00.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=788182
;

-- 2026-11-13T15:00:00.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(788182)
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Rechnungspartner
-- Column: C_Order_ReturnPackage.Bill_BPartner_ID
-- 2026-11-13T15:00:00.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,788182,0,549320,555463,656499 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-13 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Geschäftspartner für die Rechnungsstellung','Rechnungspartner des Auftrags. Der Gebindesaldo wird je Rechnungspartner geführt.','Y','N','N','Y','N','N','N',0,'Rechnungspartner',5,0,0,TO_TIMESTAMP('2026-11-13 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Rücknahme Gebinde(542164,D) -> Rücknahme Gebinde(549320,D) -> main -> 10 -> default.Rechnungspartner (first grid column)
-- 2026-11-13T15:00:00.000Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=5,Updated=TO_TIMESTAMP('2026-11-13 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=656499
;
