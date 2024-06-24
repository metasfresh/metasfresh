-- Column: PP_Product_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T14:08:34.226Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585148,581756,0,29,53019,'IssuingTolerance_Qty',TO_TIMESTAMP('2022-11-28 16:08:34','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Toleranz',0,0,TO_TIMESTAMP('2022-11-28 16:08:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:08:34.228Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585148 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:08:34.232Z
/* DDL */  select update_Column_Translation_From_AD_Element(581756) 
;

-- Column: PP_Product_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T14:09:15.473Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585149,581757,0,30,114,53019,'IssuingTolerance_UOM_ID',TO_TIMESTAMP('2022-11-28 16:09:15','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Toleranz Einheiten',0,0,TO_TIMESTAMP('2022-11-28 16:09:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:09:15.476Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585149 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:09:15.481Z
/* DDL */  select update_Column_Translation_From_AD_Element(581757) 
;

-- 2022-11-28T14:09:23.024Z
/* DDL */ SELECT public.db_alter_table('PP_Product_BOMLine','ALTER TABLE public.PP_Product_BOMLine ADD COLUMN IssuingTolerance_UOM_ID NUMERIC(10)')
;

-- 2022-11-28T14:09:23.573Z
ALTER TABLE PP_Product_BOMLine ADD CONSTRAINT IssuingToleranceUOM_PPProductBOMLine FOREIGN KEY (IssuingTolerance_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2022-11-28T14:09:39.685Z
/* DDL */ SELECT public.db_alter_table('PP_Product_BOMLine','ALTER TABLE public.PP_Product_BOMLine ADD COLUMN IssuingTolerance_Qty NUMERIC')
;

-- Field: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Toleranz
-- Column: PP_Product_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T14:10:17.814Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585148,708230,0,53029,TO_TIMESTAMP('2022-11-28 16:10:17','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Toleranz',TO_TIMESTAMP('2022-11-28 16:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:10:17.816Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708230 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T14:10:17.818Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581756) 
;

-- 2022-11-28T14:10:17.821Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708230
;

-- 2022-11-28T14:10:17.822Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708230)
;

-- Field: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Toleranz Einheiten
-- Column: PP_Product_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T14:10:17.935Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585149,708231,0,53029,TO_TIMESTAMP('2022-11-28 16:10:17','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Toleranz Einheiten',TO_TIMESTAMP('2022-11-28 16:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:10:17.937Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708231 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T14:10:17.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581757) 
;

-- 2022-11-28T14:10:17.942Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708231
;

-- 2022-11-28T14:10:17.943Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708231)
;

-- Column: PP_Product_BOMLine.IsEnforceIssuingTolerance
-- 2022-11-28T14:11:42.773Z
UPDATE AD_Column SET AD_Element_ID=581751, ColumnName='IsEnforceIssuingTolerance', Description=NULL, Help=NULL, Name='Toleranz erzw.',Updated=TO_TIMESTAMP('2022-11-28 16:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579281
;

-- 2022-11-28T14:11:42.775Z
/* DDL */  select update_Column_Translation_From_AD_Element(581751) 
;

alter table pp_product_bomline rename IsEnforceTolerance to IsEnforceIssuingTolerance;

-- Column: PP_Product_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:15:36.503Z
UPDATE AD_Column SET AD_Element_ID=581754, ColumnName='IssuingTolerance_Perc', Description=NULL, FieldLength=1, Help=NULL, Name='Toleranz %',Updated=TO_TIMESTAMP('2022-11-28 16:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579282
;

-- 2022-11-28T14:15:36.504Z
/* DDL */  select update_Column_Translation_From_AD_Element(581754) 
;

alter table pp_product_bomline rename Tolerance_Perc to IssuingTolerance_Perc;

-- Column: PP_Product_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:16:17.269Z
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2022-11-28 16:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579282
;

-- Column: PP_Product_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T14:17:20.826Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585150,581752,0,17,541693,53019,'IssuingTolerance_ValueType',TO_TIMESTAMP('2022-11-28 16:17:20','YYYY-MM-DD HH24:MI:SS'),100,'N','','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tolerance Value Type',0,0,TO_TIMESTAMP('2022-11-28 16:17:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:17:20.828Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585150 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:17:20.830Z
/* DDL */  select update_Column_Translation_From_AD_Element(581752) 
;

-- 2022-11-28T14:17:31.200Z
/* DDL */ SELECT public.db_alter_table('PP_Product_BOMLine','ALTER TABLE public.PP_Product_BOMLine ADD COLUMN IssuingTolerance_ValueType CHAR(1)')
;

-- Column: PP_Product_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T14:22:21.384Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y',Updated=TO_TIMESTAMP('2022-11-28 16:22:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585150
;

-- Column: PP_Product_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:22:57.425Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P',Updated=TO_TIMESTAMP('2022-11-28 16:22:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579282
;

-- Column: PP_Product_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T14:23:02.178Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 16:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585148
;

-- Column: PP_Product_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T14:23:09.156Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 16:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585149
;

-- Field: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Tolerance Value Type
-- Column: PP_Product_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T14:23:41.380Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585150,708232,0,53029,TO_TIMESTAMP('2022-11-28 16:23:41','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Tolerance Value Type',TO_TIMESTAMP('2022-11-28 16:23:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:23:41.382Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708232 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T14:23:41.384Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581752) 
;

-- 2022-11-28T14:23:41.388Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708232
;

-- 2022-11-28T14:23:41.389Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708232)
;

-- UI Section: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main
-- UI Column: 20
-- 2022-11-28T14:25:02.398Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546470,540196,TO_TIMESTAMP('2022-11-28 16:25:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-28 16:25:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20
-- UI Element Group: Issuing Tolerance
-- 2022-11-28T14:25:10.329Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546470,550057,TO_TIMESTAMP('2022-11-28 16:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','Issuing Tolerance',10,TO_TIMESTAMP('2022-11-28 16:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> Issuing Tolerance.Enforce Tolerance
-- Column: PP_Product_BOMLine.IsEnforceIssuingTolerance
-- 2022-11-28T14:26:29.638Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550057, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-28 16:26:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601389
;

-- UI Element: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> Issuing Tolerance.Tolerance %
-- Column: PP_Product_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:26:40.083Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550057, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-28 16:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601390
;

-- UI Element: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> Issuing Tolerance.Tolerance Value Type
-- Column: PP_Product_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T14:27:15.472Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708232,0,53029,550057,613606,'F',TO_TIMESTAMP('2022-11-28 16:27:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Tolerance Value Type',30,0,0,TO_TIMESTAMP('2022-11-28 16:27:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> Issuing Tolerance.Toleranz
-- Column: PP_Product_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T14:27:23.866Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708230,0,53029,550057,613607,'F',TO_TIMESTAMP('2022-11-28 16:27:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz',40,0,0,TO_TIMESTAMP('2022-11-28 16:27:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> Issuing Tolerance.Toleranz Einheiten
-- Column: PP_Product_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T14:27:29.783Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708231,0,53029,550057,613608,'F',TO_TIMESTAMP('2022-11-28 16:27:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz Einheiten',50,0,0,TO_TIMESTAMP('2022-11-28 16:27:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> Issuing Tolerance.Tolerance %
-- Column: PP_Product_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:27:48.679Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2022-11-28 16:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601390
;

-- UI Element: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> Issuing Tolerance.Tolerance Value Type
-- Column: PP_Product_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T14:27:50.626Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-11-28 16:27:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613606
;

-- Field: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Tolerance Value Type
-- Column: PP_Product_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T14:29:00.867Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y',Updated=TO_TIMESTAMP('2022-11-28 16:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708232
;

-- Field: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Toleranz %
-- Column: PP_Product_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:29:17.738Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P',Updated=TO_TIMESTAMP('2022-11-28 16:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680614
;

-- Field: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Toleranz
-- Column: PP_Product_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T14:29:24.741Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 16:29:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708230
;

-- Field: Stückliste_OLD(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Toleranz Einheiten
-- Column: PP_Product_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T14:29:27.530Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 16:29:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708231
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Toleranz erzw.
-- Column: PP_Product_BOMLine.IsEnforceIssuingTolerance
-- 2022-11-28T14:30:20.529Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579281,708233,0,542034,TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Toleranz erzw.',TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:30:20.530Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708233 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T14:30:20.532Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581751) 
;

-- 2022-11-28T14:30:20.537Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708233
;

-- 2022-11-28T14:30:20.538Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708233)
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Toleranz %
-- Column: PP_Product_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:30:20.675Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579282,708234,0,542034,TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Toleranz %',TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:30:20.677Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708234 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T14:30:20.678Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581754) 
;

-- 2022-11-28T14:30:20.681Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708234
;

-- 2022-11-28T14:30:20.681Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708234)
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Nur manuelle Mengeneingabe
-- Column: PP_Product_BOMLine.IsManualQtyInput
-- 2022-11-28T14:30:20.779Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584728,708235,0,542034,TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Nur manuelle Mengeneingabe',TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:30:20.781Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708235 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T14:30:20.783Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581561) 
;

-- 2022-11-28T14:30:20.785Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708235
;

-- 2022-11-28T14:30:20.786Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708235)
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Toleranz
-- Column: PP_Product_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T14:30:20.900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585148,708236,0,542034,TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Toleranz',TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:30:20.902Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708236 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T14:30:20.903Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581756) 
;

-- 2022-11-28T14:30:20.905Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708236
;

-- 2022-11-28T14:30:20.906Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708236)
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Toleranz Einheiten
-- Column: PP_Product_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T14:30:21.014Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585149,708237,0,542034,TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Toleranz Einheiten',TO_TIMESTAMP('2022-11-28 16:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:30:21.016Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708237 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T14:30:21.018Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581757) 
;

-- 2022-11-28T14:30:21.021Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708237
;

-- 2022-11-28T14:30:21.022Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708237)
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Tolerance Value Type
-- Column: PP_Product_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T14:30:21.130Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585150,708238,0,542034,TO_TIMESTAMP('2022-11-28 16:30:21','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Tolerance Value Type',TO_TIMESTAMP('2022-11-28 16:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:30:21.131Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708238 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T14:30:21.133Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581752) 
;

-- 2022-11-28T14:30:21.136Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708238
;

-- 2022-11-28T14:30:21.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708238)
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Tolerance Value Type
-- Column: PP_Product_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T14:32:00.072Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y',Updated=TO_TIMESTAMP('2022-11-28 16:32:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708238
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Toleranz %
-- Column: PP_Product_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:32:05.690Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P',Updated=TO_TIMESTAMP('2022-11-28 16:32:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708234
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Toleranz
-- Column: PP_Product_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T14:32:08.386Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 16:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708236
;

-- Field: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Toleranz Einheiten
-- Column: PP_Product_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T14:32:11.154Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 16:32:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708237
;

-- UI Column: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10
-- UI Element Group: Issuing Tolerance
-- 2022-11-28T14:33:27.757Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542015,550058,TO_TIMESTAMP('2022-11-28 16:33:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','Issuing Tolerance',30,TO_TIMESTAMP('2022-11-28 16:33:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> Issuing Tolerance.Toleranz erzw.
-- Column: PP_Product_BOMLine.IsEnforceIssuingTolerance
-- 2022-11-28T14:34:01.527Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708233,0,542034,550058,613609,'F',TO_TIMESTAMP('2022-11-28 16:34:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz erzw.',10,0,0,TO_TIMESTAMP('2022-11-28 16:34:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> Issuing Tolerance.Tolerance Value Type
-- Column: PP_Product_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T14:34:12.899Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708238,0,542034,550058,613610,'F',TO_TIMESTAMP('2022-11-28 16:34:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Tolerance Value Type',20,0,0,TO_TIMESTAMP('2022-11-28 16:34:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> Issuing Tolerance.Toleranz %
-- Column: PP_Product_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:34:20.152Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708234,0,542034,550058,613611,'F',TO_TIMESTAMP('2022-11-28 16:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz %',30,0,0,TO_TIMESTAMP('2022-11-28 16:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> Issuing Tolerance.Toleranz
-- Column: PP_Product_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T14:34:26.214Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708236,0,542034,550058,613612,'F',TO_TIMESTAMP('2022-11-28 16:34:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz',40,0,0,TO_TIMESTAMP('2022-11-28 16:34:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stücklistenbestandteile_OLD(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> Issuing Tolerance.Toleranz Einheiten
-- Column: PP_Product_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T14:34:43.492Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708237,0,542034,550058,613613,'F',TO_TIMESTAMP('2022-11-28 16:34:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz Einheiten',50,0,0,TO_TIMESTAMP('2022-11-28 16:34:43','YYYY-MM-DD HH24:MI:SS'),100)
;

