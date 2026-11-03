-- Run mode: SWING_CLIENT

-- Column: C_Customs_Invoice.Description
-- 2026-07-21T18:43:08.559Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592974,275,0,14,541360,'XX','Description',TO_TIMESTAMP('2026-07-21 18:43:07.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,2000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2026-07-21 18:43:07.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-07-21T18:43:08.636Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592974 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-07-21T18:43:08.811Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2026-07-21T18:43:49.384Z
/* DDL */ SELECT public.db_alter_table('C_Customs_Invoice','ALTER TABLE public.C_Customs_Invoice ADD COLUMN Description VARCHAR(2000)')
;

-- Field: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> Beschreibung
-- Column: C_Customs_Invoice.Description
-- 2026-07-21T18:44:36.353Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592974,781763,0,541767,0,TO_TIMESTAMP('2026-07-21 18:44:35.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Beschreibung',0,0,60,0,1,1,TO_TIMESTAMP('2026-07-21 18:44:35.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-21T18:44:36.475Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781763 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-07-21T18:44:36.551Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2026-07-21T18:44:36.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781763
;

-- 2026-07-21T18:44:36.773Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781763)
;

-- UI Column: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 20
-- UI Element Group: note
-- 2026-07-21T18:46:02.344Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541693,555518,TO_TIMESTAMP('2026-07-21 18:46:01.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','note',25,TO_TIMESTAMP('2026-07-21 18:46:01.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 20 -> note.Beschreibung
-- Column: C_Customs_Invoice.Description
-- 2026-07-21T18:46:29.917Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781763,0,541767,555518,652694,'F',TO_TIMESTAMP('2026-07-21 18:46:29.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2026-07-21 18:46:29.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 20 -> note.Dokumentnotiz
-- Column: C_Customs_Invoice.DocumentNote
-- 2026-07-21T18:47:38.142Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555518, SeqNo=20,Updated=TO_TIMESTAMP('2026-07-21 18:47:38.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652005
;

-- UI Column: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 10
-- UI Element Group: document_note
-- 2026-07-21T18:47:48.085Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=555425
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 20 -> note.Dokumentnotiz
-- Column: C_Customs_Invoice.DocumentNote
-- 2026-07-21T18:48:06.929Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2026-07-21 18:48:06.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652005
;

-- Column: C_Customs_Invoice.Description
-- 2026-07-21T18:48:45.183Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2026-07-21 18:48:45.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592974
;

-- 2026-07-21T18:49:10.817Z
INSERT INTO t_alter_column values('c_customs_invoice','Description','VARCHAR(2000)',null,null)
;

