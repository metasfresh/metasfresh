-- Column: M_Delivery_Planning.IsClosed
-- 2022-12-07T15:01:23.421Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585272,2723,0,20,542259,'IsClosed',TO_TIMESTAMP('2022-12-07 17:01:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','D',0,1,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Geschlossen',0,0,TO_TIMESTAMP('2022-12-07 17:01:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-07T15:01:23.424Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585272 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-07T15:01:23.452Z
/* DDL */  select update_Column_Translation_From_AD_Element(2723) 
;

-- 2022-12-07T15:01:24.636Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN IsClosed CHAR(1) DEFAULT ''N'' CHECK (IsClosed IN (''Y'',''N'')) NOT NULL')
;

-- Column: M_Delivery_Planning.Processed
-- 2022-12-07T15:01:41.292Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585273,1047,0,20,542259,'Processed',TO_TIMESTAMP('2022-12-07 17:01:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2022-12-07 17:01:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-07T15:01:41.294Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585273 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-07T15:01:41.297Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- 2022-12-07T15:01:42.145Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Geschlossen
-- Column: M_Delivery_Planning.IsClosed
-- 2022-12-07T15:02:02.506Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585272,708910,0,546674,TO_TIMESTAMP('2022-12-07 17:02:02','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','','Y','Y','N','N','N','N','N','Geschlossen',TO_TIMESTAMP('2022-12-07 17:02:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-07T15:02:02.508Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-07T15:02:02.510Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2723) 
;

-- 2022-12-07T15:02:02.523Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708910
;

-- 2022-12-07T15:02:02.526Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708910)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Verarbeitet
-- Column: M_Delivery_Planning.Processed
-- 2022-12-07T15:02:02.623Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585273,708911,0,546674,TO_TIMESTAMP('2022-12-07 17:02:02','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2022-12-07 17:02:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-07T15:02:02.625Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708911 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-07T15:02:02.626Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-12-07T15:02:02.651Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708911
;

-- 2022-12-07T15:02:02.653Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708911)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.B2B
-- Column: M_Delivery_Planning.IsB2B
-- 2022-12-07T15:09:48.694Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2022-12-07 17:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613483
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Type
-- Column: M_Delivery_Planning.M_Delivery_Planning_Type
-- 2022-12-07T15:09:53.576Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2022-12-07 17:09:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613482
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Verarbeitet
-- Column: M_Delivery_Planning.Processed
-- 2022-12-07T15:10:30.288Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708911,0,546674,550029,613915,'F',TO_TIMESTAMP('2022-12-07 17:10:30','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',20,0,0,TO_TIMESTAMP('2022-12-07 17:10:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Geschlossen
-- Column: M_Delivery_Planning.IsClosed
-- 2022-12-07T15:10:53.393Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708910,0,546674,550029,613916,'F',TO_TIMESTAMP('2022-12-07 17:10:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Geschlossen',30,0,0,TO_TIMESTAMP('2022-12-07 17:10:53','YYYY-MM-DD HH24:MI:SS'),100)
;

