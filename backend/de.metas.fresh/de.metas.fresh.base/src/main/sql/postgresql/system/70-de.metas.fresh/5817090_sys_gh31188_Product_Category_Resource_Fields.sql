-- Run mode: SWING_CLIENT

-- Column: M_Product_Category.S_Resource_ID
-- 2026-07-30T11:05:39.640Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593051,1777,0,18,53320,209,52002,'XX','S_Resource_ID',TO_TIMESTAMP('2026-07-30 11:05:38.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Produktionressource','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produktionressource',0,0,TO_TIMESTAMP('2026-07-30 11:05:38.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-07-30T11:05:39.766Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593051 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-07-30T11:05:39.921Z
/* DDL */  select update_Column_Translation_From_AD_Element(1777)
;

-- 2026-07-30T11:06:19.840Z
/* DDL */ SELECT public.db_alter_table('M_Product_Category','ALTER TABLE public.M_Product_Category ADD COLUMN S_Resource_ID NUMERIC(10)')
;

-- 2026-07-30T11:06:20.355Z
ALTER TABLE M_Product_Category ADD CONSTRAINT SResource_MProductCategory FOREIGN KEY (S_Resource_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Product_Category.WorkStation_ID
-- 2026-07-30T11:08:37.632Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593052,583018,0,30,541855,209,540669,'XX','WorkStation_ID',TO_TIMESTAMP('2026-07-30 11:08:29.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Die Arbeitsstation, an der dieser Produktionsauftrag bearbeitet werden soll. In der MobileUI-Produktion erscheinen nur Aufträge, deren Arbeitsstation der vom Bediener gescannten entspricht.','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Arbeitsstation',0,0,TO_TIMESTAMP('2026-07-30 11:08:29.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-07-30T11:08:37.757Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593052 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-07-30T11:08:37.898Z
/* DDL */  select update_Column_Translation_From_AD_Element(583018)
;

-- 2026-07-30T11:09:52.720Z
/* DDL */ SELECT public.db_alter_table('M_Product_Category','ALTER TABLE public.M_Product_Category ADD COLUMN WorkStation_ID NUMERIC(10)')
;

-- 2026-07-30T11:09:53.110Z
ALTER TABLE M_Product_Category ADD CONSTRAINT WorkStation_MProductCategory FOREIGN KEY (WorkStation_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED
;

-- Field: Produkt Kategorie(144,D) -> Produkt-Kategorie(189,D) -> Arbeitsstation
-- Column: M_Product_Category.WorkStation_ID
-- 2026-07-30T11:16:39.615Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593052,781860,0,189,0,TO_TIMESTAMP('2026-07-30 11:16:38.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Arbeitsstation, an der dieser Produktionsauftrag bearbeitet werden soll. In der MobileUI-Produktion erscheinen nur Aufträge, deren Arbeitsstation der vom Bediener gescannten entspricht.',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Arbeitsstation',0,0,160,0,1,1,TO_TIMESTAMP('2026-07-30 11:16:38.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-30T11:16:39.756Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-07-30T11:16:39.820Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583018)
;

-- 2026-07-30T11:16:39.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781860
;

-- 2026-07-30T11:16:39.956Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781860)
;

-- Field: Produkt Kategorie(144,D) -> Produkt-Kategorie(189,D) -> Produktionsstätte
-- Column: M_Product_Category.S_Resource_ID
-- 2026-07-30T11:17:58.762Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593051,781861,542433,0,189,0,TO_TIMESTAMP('2026-07-30 11:17:58.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Produktionsstätte',0,0,170,0,1,1,TO_TIMESTAMP('2026-07-30 11:17:58.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-30T11:17:58.886Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-07-30T11:17:59.006Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542433)
;

-- 2026-07-30T11:17:59.075Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781861
;

-- 2026-07-30T11:17:59.132Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781861)
;

-- UI Column: Produkt Kategorie(144,D) -> Produkt-Kategorie(189,D) -> main -> 20
-- UI Element Group: resource
-- 2026-07-30T11:19:15.259Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540192,555532,TO_TIMESTAMP('2026-07-30 11:19:14.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','resource',17,TO_TIMESTAMP('2026-07-30 11:19:14.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(144,D) -> Produkt-Kategorie(189,D) -> main -> 20 -> resource.Produktionsstätte
-- Column: M_Product_Category.S_Resource_ID
-- 2026-07-30T11:20:06.602Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781861,0,189,555532,652786,'F',TO_TIMESTAMP('2026-07-30 11:20:06.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Produktionsstätte',10,0,0,TO_TIMESTAMP('2026-07-30 11:20:06.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(144,D) -> Produkt-Kategorie(189,D) -> main -> 20 -> resource.Arbeitsstation
-- Column: M_Product_Category.WorkStation_ID
-- 2026-07-30T11:21:10.042Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781860,0,189,555532,652787,'F',TO_TIMESTAMP('2026-07-30 11:21:09.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Arbeitsstation, an der dieser Produktionsauftrag bearbeitet werden soll. In der MobileUI-Produktion erscheinen nur Aufträge, deren Arbeitsstation der vom Bediener gescannten entspricht.','Y','N','N','Y','N','N','N',0,'Arbeitsstation',20,0,0,TO_TIMESTAMP('2026-07-30 11:21:09.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

