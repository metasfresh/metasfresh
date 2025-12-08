-- Column: C_POS_OrderLine.CatchWeight
-- Column: C_POS_OrderLine.CatchWeight
-- 2024-09-24T21:35:06.017Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589164,583166,0,29,542436,'XX','CatchWeight',TO_TIMESTAMP('2024-09-25 00:35:05','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Catch Weight',0,0,TO_TIMESTAMP('2024-09-25 00:35:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-24T21:35:06.028Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589164 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-24T21:35:06.084Z
/* DDL */  select update_Column_Translation_From_AD_Element(583166) 
;

-- Column: C_POS_OrderLine.Catch_UOM_ID
-- Column: C_POS_OrderLine.Catch_UOM_ID
-- 2024-09-24T21:35:30.890Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589165,576953,0,30,114,542436,'XX','Catch_UOM_ID',TO_TIMESTAMP('2024-09-25 00:35:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Aus dem Produktstamm übenommene Catch Weight Einheit.','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Catch Einheit',0,0,TO_TIMESTAMP('2024-09-25 00:35:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-24T21:35:30.894Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589165 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-24T21:35:30.899Z
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- 2024-09-24T21:35:36.644Z
/* DDL */ SELECT public.db_alter_table('C_POS_OrderLine','ALTER TABLE public.C_POS_OrderLine ADD COLUMN CatchWeight NUMERIC')
;

-- 2024-09-24T21:35:40.108Z
/* DDL */ SELECT public.db_alter_table('C_POS_OrderLine','ALTER TABLE public.C_POS_OrderLine ADD COLUMN Catch_UOM_ID NUMERIC(10)')
;

-- 2024-09-24T21:35:40.117Z
ALTER TABLE C_POS_OrderLine ADD CONSTRAINT CatchUOM_CPOSOrderLine FOREIGN KEY (Catch_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Field: POS Order -> POS Order Line -> Catch Weight
-- Column: C_POS_OrderLine.CatchWeight
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Catch Weight
-- Column: C_POS_OrderLine.CatchWeight
-- 2024-09-24T21:36:03.642Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589164,731781,0,547592,TO_TIMESTAMP('2024-09-25 00:36:03','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','Catch Weight',TO_TIMESTAMP('2024-09-25 00:36:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-24T21:36:03.646Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731781 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-24T21:36:03.651Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583166) 
;

-- 2024-09-24T21:36:03.672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731781
;

-- 2024-09-24T21:36:03.679Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731781)
;

-- Field: POS Order -> POS Order Line -> Catch Einheit
-- Column: C_POS_OrderLine.Catch_UOM_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Catch Einheit
-- Column: C_POS_OrderLine.Catch_UOM_ID
-- 2024-09-24T21:36:03.811Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589165,731782,0,547592,TO_TIMESTAMP('2024-09-25 00:36:03','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit.',10,'de.metas.pos','Y','N','N','N','N','N','N','N','Catch Einheit',TO_TIMESTAMP('2024-09-25 00:36:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-24T21:36:03.813Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731782 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-24T21:36:03.816Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576953) 
;

-- 2024-09-24T21:36:03.827Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731782
;

-- 2024-09-24T21:36:03.828Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731782)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10
-- UI Element Group: catch weight
-- 2024-09-24T21:36:30.468Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547548,552015,TO_TIMESTAMP('2024-09-25 00:36:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','catch weight',25,TO_TIMESTAMP('2024-09-25 00:36:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Catch Einheit
-- Column: C_POS_OrderLine.Catch_UOM_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> catch weight.Catch Einheit
-- Column: C_POS_OrderLine.Catch_UOM_ID
-- 2024-09-24T21:36:58.546Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731782,0,547592,552015,626089,'F',TO_TIMESTAMP('2024-09-25 00:36:58','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit.','Y','N','Y','N','N','Catch Einheit',10,0,0,TO_TIMESTAMP('2024-09-25 00:36:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Catch Weight
-- Column: C_POS_OrderLine.CatchWeight
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> catch weight.Catch Weight
-- Column: C_POS_OrderLine.CatchWeight
-- 2024-09-24T21:37:06.837Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731781,0,547592,552015,626090,'F',TO_TIMESTAMP('2024-09-25 00:37:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Catch Weight',20,0,0,TO_TIMESTAMP('2024-09-25 00:37:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: POS Order -> POS Order Line -> Catch Einheit
-- Column: C_POS_OrderLine.Catch_UOM_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Catch Einheit
-- Column: C_POS_OrderLine.Catch_UOM_ID
-- 2024-09-24T21:37:30.695Z
UPDATE AD_Field SET DisplayLogic='@Catch_UOM_ID@>0',Updated=TO_TIMESTAMP('2024-09-25 00:37:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731782
;

-- Field: POS Order -> POS Order Line -> Catch Weight
-- Column: C_POS_OrderLine.CatchWeight
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Catch Weight
-- Column: C_POS_OrderLine.CatchWeight
-- 2024-09-24T21:37:38.444Z
UPDATE AD_Field SET DisplayLogic='@Catch_UOM_ID@>0',Updated=TO_TIMESTAMP('2024-09-25 00:37:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731781
;

