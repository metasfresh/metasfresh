-- Column: M_HU_Attribute.IsUnique
-- 2022-11-01T08:19:17.457Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584862,540471,0,20,540504,'IsUnique',TO_TIMESTAMP('2022-11-01 10:19:17.219','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Unique',0,0,TO_TIMESTAMP('2022-11-01 10:19:17.219','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-11-01T08:19:17.459Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584862 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-01T08:19:17.488Z
/* DDL */  select update_Column_Translation_From_AD_Element(540471) 
;

-- 2022-11-01T08:19:18.416Z
/* DDL */ SELECT public.db_alter_table('M_HU_Attribute','ALTER TABLE public.M_HU_Attribute ADD COLUMN IsUnique CHAR(1) DEFAULT ''N'' CHECK (IsUnique IN (''Y'',''N'')) NOT NULL')
;

-- Column: M_HU_Attribute.IsUnique
-- 2022-11-01T08:19:26.506Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-01 10:19:26.503','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584862
;



-- Field: Handling Unit -> Handling Unit Attribut -> Unique
-- Column: M_HU_Attribute.IsUnique
-- 2022-11-02T16:03:42.680Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584862,707933,0,540512,0,TO_TIMESTAMP('2022-11-02 18:03:42.539','YYYY-MM-DD HH24:MI:SS.US'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Unique',120,120,0,1,1,TO_TIMESTAMP('2022-11-02 18:03:42.539','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-02T16:03:42.682Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-02T16:03:42.710Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540471) 
;

-- 2022-11-02T16:03:42.724Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707933
;

-- 2022-11-02T16:03:42.726Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707933)
;

-- UI Element: Handling Unit -> Handling Unit Attribut.Unique
-- Column: M_HU_Attribute.IsUnique
-- 2022-11-02T16:04:04.430Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707933,0,540512,540912,613366,'F',TO_TIMESTAMP('2022-11-02 18:04:04.302','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Unique',10,0,0,TO_TIMESTAMP('2022-11-02 18:04:04.302','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit -> Handling Unit Attribut.Unique
-- Column: M_HU_Attribute.IsUnique
-- 2022-11-02T16:04:15.447Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-11-02 18:04:15.446','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613366
;

-- UI Element: Handling Unit -> Handling Unit Attribut.Sektion
-- Column: M_HU_Attribute.AD_Org_ID
-- 2022-11-02T16:04:15.454Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-11-02 18:04:15.454','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=547037
;

