-- Run mode: SWING_CLIENT

-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T07:55:09.761Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592056,54070,0,19,284,'XX','AD_RelationType_ID',TO_TIMESTAMP('2026-02-23 07:55:09.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Relationstyp',0,0,TO_TIMESTAMP('2026-02-23 07:55:09.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-23T07:55:09.764Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592056 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-23T07:55:09.793Z
/* DDL */  select update_Column_Translation_From_AD_Element(54070)
;

-- 2021-06-30T11:27:47.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN AD_RelationType_ID NUMERIC(10)')
;

-- 2026-02-23T08:27:15.015Z
INSERT INTO t_alter_column values('ad_process','AD_RelationType_ID','NUMERIC(10)',null,null)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Relationstyp
-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T08:29:46.546Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592056,774693,0,245,0,TO_TIMESTAMP('2026-02-23 08:29:46.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Relationstyp',0,0,360,0,1,1,TO_TIMESTAMP('2026-02-23 08:29:46.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-23T08:29:46.549Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774693 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-23T08:29:46.552Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(54070)
;

-- 2026-02-23T08:29:46.565Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774693
;

-- 2026-02-23T08:29:46.568Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774693)
;

-- Reference: AD_Process Type
-- Value: RelationTypeInOverlay
-- ValueName: RelationTypeInOverlay
-- 2026-02-23T08:32:38.112Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540087,544124,TO_TIMESTAMP('2026-02-23 08:32:37.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Takes a relation type and opens the related window in an overlay.','D','Y','Open Relation Type In Overlay',TO_TIMESTAMP('2026-02-23 08:32:37.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RelationTypeInOverlay','RelationTypeInOverlay')
;

-- 2026-02-23T08:32:38.115Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544124 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Relationstyp
-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T08:35:41.807Z
UPDATE AD_Field SET DisplayLogic='@Type/Java@=RelationTypeInOverlay', EntityType='D',Updated=TO_TIMESTAMP('2026-02-23 08:35:41.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=774693
;

-- UI Element: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> main -> 10 -> description.Relationstyp
-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T08:37:43.557Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774693,0,245,541397,648389,'F',TO_TIMESTAMP('2026-02-23 08:37:43.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Relationstyp',80,0,0,TO_TIMESTAMP('2026-02-23 08:37:43.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Relationstyp
-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T14:46:34.487Z
UPDATE AD_Field SET ColumnDisplayLength=10,Updated=TO_TIMESTAMP('2026-02-23 14:46:34.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=774693
;

-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T14:57:48.759Z
UPDATE AD_Column SET MandatoryLogic='@Type/Java@=RelationTypeInOverlay',Updated=TO_TIMESTAMP('2026-02-23 14:57:48.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592056
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Relationstyp
-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T15:02:56.045Z
UPDATE AD_Field SET DisplayLogic='@Type/Java@=''RelationTypeInOverlay''',Updated=TO_TIMESTAMP('2026-02-23 15:02:56.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=774693
;

-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T15:03:52.759Z
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2026-02-23 15:03:52.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592056
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Relationstyp
-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T15:07:45.761Z
UPDATE AD_Field SET DisplayLogic='@Type@=''RelationTypeInOverlay''',Updated=TO_TIMESTAMP('2026-02-23 15:07:45.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=774693
;

-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T15:19:17.187Z
UPDATE AD_Column SET MandatoryLogic='@Type@=''RelationTypeInOverlay''',Updated=TO_TIMESTAMP('2026-02-23 15:19:17.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592056
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Relationstyp
-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T16:40:59.977Z
UPDATE AD_Field SET SeqNo=310,Updated=TO_TIMESTAMP('2026-02-23 16:40:59.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=774693
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Relationstyp
-- Column: AD_Process.AD_RelationType_ID
-- 2026-02-23T16:41:12.929Z
UPDATE AD_Field SET DisplayLength=10,Updated=TO_TIMESTAMP('2026-02-23 16:41:12.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=774693
;

