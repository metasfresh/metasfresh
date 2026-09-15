-- Column: AD_BoilerPlate_Trl.Subject
-- 2025-12-04T09:00:57.890Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591668,2748,0,10,540026,'XX','Subject',TO_TIMESTAMP('2025-12-04 09:00:56.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mail Betreff','de.metas.letters',0,255,'Subject of the Mail ','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Betreff',0,0,TO_TIMESTAMP('2025-12-04 09:00:56.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-04T09:00:57.896Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591668 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-04T09:00:57.906Z
/* DDL */  select update_Column_Translation_From_AD_Element(2748)
;

-- 2025-12-04T09:01:19.378Z
/* DDL */ SELECT public.db_alter_table('AD_BoilerPlate_Trl','ALTER TABLE public.AD_BoilerPlate_Trl ADD COLUMN Subject VARCHAR(255)')
;

-- Field: Textbaustein Übersetzung(540931,de.metas.letters) -> Übersetzung(542801,de.metas.letters) -> Betreff
-- Column: AD_BoilerPlate_Trl.Subject
-- 2025-12-04T09:26:12.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591668,758540,0,542801,0,TO_TIMESTAMP('2025-12-04 09:26:12.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mail Betreff',0,'D',0,'Subject of the Mail ',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Betreff',0,0,50,0,1,1,TO_TIMESTAMP('2025-12-04 09:26:12.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-04T09:26:12.277Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758540 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-04T09:26:12.282Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2748)
;

-- 2025-12-04T09:26:12.308Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758540
;

-- 2025-12-04T09:26:12.316Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758540)
;

-- UI Element: Textbaustein Übersetzung(540931,de.metas.letters) -> Übersetzung(542801,de.metas.letters) -> main -> 10 -> default.Betreff
-- Column: AD_BoilerPlate_Trl.Subject
-- 2025-12-04T09:26:32.523Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758540,0,542801,543931,639735,'F',TO_TIMESTAMP('2025-12-04 09:26:32.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mail Betreff','Subject of the Mail ','Y','N','N','Y','Y','N','N',0,'Betreff',28,30,0,TO_TIMESTAMP('2025-12-04 09:26:32.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: AD_BoilerPlate.Subject
-- 2025-12-04T11:14:54.946Z
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-04 11:14:54.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=547500
;

