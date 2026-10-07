-- Column: M_Delivery_Planning.M_MeansOfTransportation_ID
-- 2022-12-08T16:55:35.247Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585306,581776,0,30,542259,'M_MeansOfTransportation_ID',TO_TIMESTAMP('2022-12-08 18:55:35','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Means of Transportation',0,0,TO_TIMESTAMP('2022-12-08 18:55:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T16:55:35.249Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585306 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T16:55:35.346Z
/* DDL */  select update_Column_Translation_From_AD_Element(581776) 
;

-- 2022-12-08T16:55:36.722Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN M_MeansOfTransportation_ID NUMERIC(10)')
;

-- 2022-12-08T16:55:36.730Z
ALTER TABLE M_Delivery_Planning ADD CONSTRAINT MMeansOfTransportation_MDeliveryPlanning FOREIGN KEY (M_MeansOfTransportation_ID) REFERENCES public.M_MeansOfTransportation DEFERRABLE INITIALLY DEFERRED
;




-- 2022-12-08T16:55:36.722Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning DROP COLUMN MeansOfTransportation')
;




-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Means of Transportation
-- Column: M_Delivery_Planning.MeansOfTransportation
-- 2022-12-08T16:56:59.652Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613508
;

-- 2022-12-08T16:56:59.661Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708104
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Means of Transportation Type
-- Column: M_Delivery_Planning.MeansOfTransportation
-- 2022-12-08T16:56:59.665Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708104
;

-- 2022-12-08T16:56:59.671Z
DELETE FROM AD_Field WHERE AD_Field_ID=708104
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Means of Transportation
-- Column: M_Delivery_Planning.M_MeansOfTransportation_ID
-- 2022-12-08T16:57:13.386Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585306,708933,0,546674,0,TO_TIMESTAMP('2022-12-08 18:57:13','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Means of Transportation',0,10,0,1,1,TO_TIMESTAMP('2022-12-08 18:57:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T16:57:13.388Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T16:57:13.390Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581776) 
;

-- 2022-12-08T16:57:13.393Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708933
;

-- 2022-12-08T16:57:13.395Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708933)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Means of Transportation
-- Column: M_Delivery_Planning.M_MeansOfTransportation_ID
-- 2022-12-08T17:03:57.029Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708933,0,546674,550028,613935,'F',TO_TIMESTAMP('2022-12-08 19:03:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Means of Transportation',290,0,0,TO_TIMESTAMP('2022-12-08 19:03:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Delivery_Planning.MeansOfTransportation
-- 2022-12-08T17:04:12.844Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585034
;

-- 2022-12-08T17:04:12.850Z
DELETE FROM AD_Column WHERE AD_Column_ID=585034
;

