-- Column: M_Delivery_Planning.IncotermLocation
-- 2022-12-23T11:27:20.874Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585445,501608,0,10,542259,'IncotermLocation',TO_TIMESTAMP('2022-12-23 13:27:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Location to be specified for commercial clause','D',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterm Location',0,0,TO_TIMESTAMP('2022-12-23 13:27:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:27:20.916Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585445 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:27:21Z
/* DDL */  select update_Column_Translation_From_AD_Element(501608) 
;

-- 2022-12-23T11:27:27.659Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN IncotermLocation VARCHAR(500)')
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Incoterm Location
-- Column: M_Delivery_Planning.IncotermLocation
-- 2022-12-23T11:28:34.433Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585445,710071,0,546674,TO_TIMESTAMP('2022-12-23 13:28:33','YYYY-MM-DD HH24:MI:SS'),100,'Location to be specified for commercial clause',500,'D','Y','Y','N','N','N','N','N','Incoterm Location',TO_TIMESTAMP('2022-12-23 13:28:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T11:28:34.474Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T11:28:34.516Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608) 
;

-- 2022-12-23T11:28:34.568Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710071
;

-- 2022-12-23T11:28:34.609Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710071)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Incoterm Location
-- Column: M_Delivery_Planning.IncotermLocation
-- 2022-12-23T12:15:34.987Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710071,0,546674,550028,614583,'F',TO_TIMESTAMP('2022-12-23 14:15:34','YYYY-MM-DD HH24:MI:SS'),100,'Location to be specified for commercial clause','Y','N','N','Y','N','N','N',0,'Incoterm Location',115,0,0,TO_TIMESTAMP('2022-12-23 14:15:34','YYYY-MM-DD HH24:MI:SS'),100)
;

