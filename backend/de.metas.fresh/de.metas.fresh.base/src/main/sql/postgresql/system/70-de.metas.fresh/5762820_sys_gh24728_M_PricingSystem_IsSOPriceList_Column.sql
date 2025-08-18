-- Run mode: SWING_CLIENT

-- Column: M_PricingSystem.IsSOPriceList
-- 2025-08-18T08:37:35.802Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590607,408,0,20,505135,'XX','IsSOPriceList','(SELECT     CASE         WHEN MAX(CASE WHEN pl.issopricelist = ''Y'' THEN 1 ELSE 0 END) = 1             THEN ''Y''             ELSE ''N''     END AS issopricelist from M_Pricelist pl where pl.M_PricingSystem_ID = M_PricingSystem.M_PricingSystem_ID   AND pl.isactive = ''Y'')',TO_TIMESTAMP('2025-08-18 08:37:34.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Dies ist eine Verkaufspreisliste','D',0,1,'Das Auswahlfeld "Verkaufspreisliste" gibt an, ob diese Presiliste für Verkaufsvorgänge genutzt wird.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Verkaufspreisliste',0,0,TO_TIMESTAMP('2025-08-18 08:37:34.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-18T08:37:35.873Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590607 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-18T08:37:36.060Z
/* DDL */  select update_Column_Translation_From_AD_Element(408)
;

-- Field: Preissystem(540320,D) -> Preissystem(540775,D) -> Verkaufspreisliste
-- Column: M_PricingSystem.IsSOPriceList
-- 2025-08-18T08:38:36.669Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590607,751862,0,540775,0,TO_TIMESTAMP('2025-08-18 08:38:35.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist eine Verkaufspreisliste',0,'D',0,'Das Auswahlfeld "Verkaufspreisliste" gibt an, ob diese Presiliste für Verkaufsvorgänge genutzt wird.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Verkaufspreisliste',0,0,10,0,1,1,TO_TIMESTAMP('2025-08-18 08:38:35.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-18T08:38:36.744Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-18T08:38:36.816Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(408)
;

-- 2025-08-18T08:38:36.905Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751862
;

-- 2025-08-18T08:38:36.973Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751862)
;

-- UI Element: Preissystem(540320,D) -> Preissystem(540775,D) -> main -> 20 -> active.Verkaufspreisliste
-- Column: M_PricingSystem.IsSOPriceList
-- 2025-08-18T08:45:08.034Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,751862,0,540775,540953,636143,'F',TO_TIMESTAMP('2025-08-18 08:45:07.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist eine Verkaufspreisliste','Das Auswahlfeld "Verkaufspreisliste" gibt an, ob diese Presiliste für Verkaufsvorgänge genutzt wird.','Y','N','N','Y','N','N','N',0,'Verkaufspreisliste',20,0,0,TO_TIMESTAMP('2025-08-18 08:45:07.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

