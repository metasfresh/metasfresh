-- Column: C_Order.VersionNo
-- 2024-11-21T19:13:07.636Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589448,1949,0,10,259,'VersionNo',TO_TIMESTAMP('2024-11-21 20:13:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Versionsnummer','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Versions-Nr.',0,0,TO_TIMESTAMP('2024-11-21 20:13:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-21T19:13:07.692Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589448 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-21T19:13:07.801Z
/* DDL */  select update_Column_Translation_From_AD_Element(1949) 
;

-- 2024-11-21T19:13:31.028Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN VersionNo VARCHAR(10)')
;

-- Field: OLD-Auftrag -> Auftrag -> Versions-Nr.
-- Column: C_Order.VersionNo
-- 2024-11-21T19:15:07.257Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589448,734038,0,186,0,TO_TIMESTAMP('2024-11-21 20:15:06','YYYY-MM-DD HH24:MI:SS'),100,'Versionsnummer',0,'D',0,'Y','Y','Y','N','N','N','N','N','Versions-Nr.',0,770,0,1,1,TO_TIMESTAMP('2024-11-21 20:15:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-21T19:15:07.309Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-21T19:15:07.362Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1949) 
;

-- 2024-11-21T19:15:07.417Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734038
;

-- 2024-11-21T19:15:07.469Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734038)
;

-- Field: OLD-Auftrag -> Auftrag -> Versions-Nr.
-- Column: C_Order.VersionNo
-- 2024-11-21T19:16:21.884Z
UPDATE AD_Field SET DisplayLogic='@OrderType/XX@=ON',Updated=TO_TIMESTAMP('2024-11-21 20:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=734038
;

-- UI Element: OLD-Auftrag -> Auftrag.Versions-Nr.
-- Column: C_Order.VersionNo
-- 2024-11-21T19:16:53.015Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734038,0,186,1000001,627360,'F',TO_TIMESTAMP('2024-11-21 20:16:52','YYYY-MM-DD HH24:MI:SS'),100,'Versionsnummer','Y','N','N','Y','N','N','N',0,'Versions-Nr.',60,0,0,TO_TIMESTAMP('2024-11-21 20:16:52','YYYY-MM-DD HH24:MI:SS'),100)
;

