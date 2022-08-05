-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T06:27:38.480Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583964,581100,0,15,203,'WOProjectCreatedDate',TO_TIMESTAMP('2022-08-05 08:27:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem das Prüfprojekt erzeugt wurde.','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt erstellt',0,0,TO_TIMESTAMP('2022-08-05 08:27:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-05T06:27:38.482Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583964 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-05T06:27:38.511Z
/* DDL */  select update_Column_Translation_From_AD_Element(581100)
;



-- Field: Prüf Projekt -> Projekt -> Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T06:42:34.289Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583964,703782,581100,0,546536,0,TO_TIMESTAMP('2022-08-05 08:42:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.',0,'U',0,'Y','Y','Y','N','N','N','N','N','Projekt erstellt',0,400,0,1,1,TO_TIMESTAMP('2022-08-05 08:42:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T06:42:34.291Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703782 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T06:42:34.293Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581100)
;

-- 2022-08-05T06:42:34.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703782
;

-- 2022-08-05T06:42:34.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703782)
;

-- UI Element: Prüf Projekt -> Projekt.Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T06:43:45.097Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703782,0,546536,549668,611253,'F',TO_TIMESTAMP('2022-08-05 08:43:44','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.','Y','N','N','Y','N','N','N',0,'Projekt erstellt',70,0,0,TO_TIMESTAMP('2022-08-05 08:43:44','YYYY-MM-DD HH24:MI:SS'),100)
;

