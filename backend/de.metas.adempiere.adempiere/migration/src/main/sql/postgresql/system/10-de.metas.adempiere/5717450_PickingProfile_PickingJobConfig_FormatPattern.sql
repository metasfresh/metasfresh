-- Column: PickingProfile_PickingJobConfig.FormatPattern
-- Column: PickingProfile_PickingJobConfig.FormatPattern
-- 2024-02-15T14:13:39.005Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587947,53687,0,10,542390,'XX','FormatPattern',TO_TIMESTAMP('2024-02-15 16:13:38','YYYY-MM-DD HH24:MI:SS'),100,'N','The pattern used to format a number or date.','de.metas.picking',0,25,'A string complying with either Java SimpleDateFormat or DecimalFormat pattern syntax used to override the default presentation format of a date or number type field.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Format Pattern',0,0,TO_TIMESTAMP('2024-02-15 16:13:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-15T14:13:39.008Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587947 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-15T14:13:39.042Z
/* DDL */  select update_Column_Translation_From_AD_Element(53687) 
;

-- 2024-02-15T14:13:40.278Z
/* DDL */ SELECT public.db_alter_table('PickingProfile_PickingJobConfig','ALTER TABLE public.PickingProfile_PickingJobConfig ADD COLUMN FormatPattern VARCHAR(25)')
;

-- Field: Mobile UI Kommissionierprofil -> Feld -> Format Pattern
-- Column: PickingProfile_PickingJobConfig.FormatPattern
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Feld(547360,de.metas.picking) -> Format Pattern
-- Column: PickingProfile_PickingJobConfig.FormatPattern
-- 2024-02-15T14:14:09.446Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587947,725184,0,547360,TO_TIMESTAMP('2024-02-15 16:14:09','YYYY-MM-DD HH24:MI:SS'),100,'The pattern used to format a number or date.',25,'de.metas.picking','A string complying with either Java SimpleDateFormat or DecimalFormat pattern syntax used to override the default presentation format of a date or number type field.','Y','N','N','N','N','N','N','N','Format Pattern',TO_TIMESTAMP('2024-02-15 16:14:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T14:14:09.448Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725184 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-15T14:14:09.451Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53687) 
;

-- 2024-02-15T14:14:09.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725184
;

-- 2024-02-15T14:14:09.465Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725184)
;

-- UI Element: Mobile UI Kommissionierprofil -> Feld.Format Pattern
-- Column: PickingProfile_PickingJobConfig.FormatPattern
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Feld(547360,de.metas.picking) -> main -> 10 -> default.Format Pattern
-- Column: PickingProfile_PickingJobConfig.FormatPattern
-- 2024-02-15T14:14:50.921Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,725184,0,547360,551431,622947,'F',TO_TIMESTAMP('2024-02-15 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,'The pattern used to format a number or date.','A string complying with either Java SimpleDateFormat or DecimalFormat pattern syntax used to override the default presentation format of a date or number type field.','Y','N','Y','N','N','Format Pattern',20,0,0,TO_TIMESTAMP('2024-02-15 16:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Feld.Format Pattern
-- Column: PickingProfile_PickingJobConfig.FormatPattern
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Feld(547360,de.metas.picking) -> main -> 10 -> default.Format Pattern
-- Column: PickingProfile_PickingJobConfig.FormatPattern
-- 2024-02-15T15:08:43.482Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-02-15 17:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622947
;

