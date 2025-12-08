-- Column: PP_Order_BOMLine.IsEnforceIssuingTolerance
-- 2022-11-28T14:58:37.261Z
UPDATE AD_Column SET AD_Element_ID=581751, ColumnName='IsEnforceIssuingTolerance', Description=NULL, Help=NULL, Name='Toleranz erzw.',Updated=TO_TIMESTAMP('2022-11-28 16:58:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579283
;

-- 2022-11-28T14:58:37.262Z
/* DDL */  select update_Column_Translation_From_AD_Element(581751) 
;

-- Column: PP_Order_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T14:59:18.489Z
UPDATE AD_Column SET AD_Element_ID=581754, ColumnName='IssuingTolerance_Perc', Description=NULL, FieldLength=1, Help=NULL, Name='Toleranz %',Updated=TO_TIMESTAMP('2022-11-28 16:59:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579284
;

-- 2022-11-28T14:59:18.490Z
/* DDL */  select update_Column_Translation_From_AD_Element(581754) 
;


alter table pp_order_bomline rename IsEnforceTolerance to IsEnforceIssuingTolerance;
alter table pp_order_bomline rename Tolerance_Perc to IssuingTolerance_Perc;


-- Column: PP_Order_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T15:00:11.703Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585168,581752,0,17,541693,53025,'IssuingTolerance_ValueType',TO_TIMESTAMP('2022-11-28 17:00:11','YYYY-MM-DD HH24:MI:SS'),100,'N','','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tolerance Value Type',0,0,TO_TIMESTAMP('2022-11-28 17:00:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T15:00:11.705Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585168 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T15:00:11.709Z
/* DDL */  select update_Column_Translation_From_AD_Element(581752) 
;

-- Column: PP_Order_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T15:00:37.144Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585169,581756,0,29,53025,'IssuingTolerance_Qty',TO_TIMESTAMP('2022-11-28 17:00:37','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Toleranz',0,0,TO_TIMESTAMP('2022-11-28 17:00:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T15:00:37.146Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585169 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T15:00:37.150Z
/* DDL */  select update_Column_Translation_From_AD_Element(581756) 
;

-- 2022-11-28T15:00:59.273Z
/* DDL */ SELECT public.db_alter_table('PP_Order_BOMLine','ALTER TABLE public.PP_Order_BOMLine ADD COLUMN IssuingTolerance_Qty NUMERIC')
;

-- 2022-11-28T15:01:02.570Z
/* DDL */ SELECT public.db_alter_table('PP_Order_BOMLine','ALTER TABLE public.PP_Order_BOMLine ADD COLUMN IssuingTolerance_ValueType CHAR(1)')
;

-- Column: PP_Order_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T15:01:22.272Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585170,581757,0,30,114,53025,'IssuingTolerance_UOM_ID',TO_TIMESTAMP('2022-11-28 17:01:22','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Toleranz Einheiten',0,0,TO_TIMESTAMP('2022-11-28 17:01:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T15:01:22.273Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585170 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T15:01:22.277Z
/* DDL */  select update_Column_Translation_From_AD_Element(581757) 
;

-- 2022-11-28T15:01:24.202Z
/* DDL */ SELECT public.db_alter_table('PP_Order_BOMLine','ALTER TABLE public.PP_Order_BOMLine ADD COLUMN IssuingTolerance_UOM_ID NUMERIC(10)')
;

-- 2022-11-28T15:01:24.474Z
ALTER TABLE PP_Order_BOMLine ADD CONSTRAINT IssuingToleranceUOM_PPOrderBOMLine FOREIGN KEY (IssuingTolerance_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Column: PP_Order_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T15:02:09.808Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y',Updated=TO_TIMESTAMP('2022-11-28 17:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585168
;

-- Column: PP_Order_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T15:02:14.989Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 17:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585170
;

-- Column: PP_Order_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T15:02:39.297Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P',Updated=TO_TIMESTAMP('2022-11-28 17:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585169
;

-- Column: PP_Order_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T15:02:42.397Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 17:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585169
;

-- Column: PP_Order_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T15:02:48.205Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P',Updated=TO_TIMESTAMP('2022-11-28 17:02:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579284
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> Toleranz
-- Column: PP_Order_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T15:08:26.076Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585169,708254,0,53039,TO_TIMESTAMP('2022-11-28 17:08:25','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Toleranz',TO_TIMESTAMP('2022-11-28 17:08:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:08:26.078Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708254 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:08:26.080Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581756) 
;

-- 2022-11-28T15:08:26.084Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708254
;

-- 2022-11-28T15:08:26.085Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708254)
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> Tolerance Value Type
-- Column: PP_Order_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T15:08:37.580Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585168,708255,0,53039,TO_TIMESTAMP('2022-11-28 17:08:37','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Tolerance Value Type',TO_TIMESTAMP('2022-11-28 17:08:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:08:37.582Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:08:37.584Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581752) 
;

-- 2022-11-28T15:08:37.588Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708255
;

-- 2022-11-28T15:08:37.589Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708255)
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> Toleranz Einheiten
-- Column: PP_Order_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T15:08:47.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585170,708256,0,53039,TO_TIMESTAMP('2022-11-28 17:08:47','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Toleranz Einheiten',TO_TIMESTAMP('2022-11-28 17:08:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:08:47.277Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708256 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:08:47.278Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581757) 
;

-- 2022-11-28T15:08:47.281Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708256
;

-- 2022-11-28T15:08:47.282Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708256)
;

-- UI Section: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> main
-- UI Column: 20
-- 2022-11-28T15:09:02.008Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546474,540125,TO_TIMESTAMP('2022-11-28 17:09:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-28 17:09:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> main -> 20
-- UI Element Group: Issuing Tolerance
-- 2022-11-28T15:09:14.005Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546474,550061,TO_TIMESTAMP('2022-11-28 17:09:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Issuing Tolerance',10,TO_TIMESTAMP('2022-11-28 17:09:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> main -> 20 -> Issuing Tolerance.Enforce Tolerance
-- Column: PP_Order_BOMLine.IsEnforceIssuingTolerance
-- 2022-11-28T15:12:43.058Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550061, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-11-28 17:12:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601391
;

-- UI Element: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> main -> 20 -> Issuing Tolerance.Tolerance Value Type
-- Column: PP_Order_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T15:12:52.636Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708255,0,53039,550061,613627,'F',TO_TIMESTAMP('2022-11-28 17:12:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Tolerance Value Type',20,0,0,TO_TIMESTAMP('2022-11-28 17:12:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> main -> 20 -> Issuing Tolerance.Tolerance %
-- Column: PP_Order_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T15:13:16.856Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550061, IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-11-28 17:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601392
;

-- UI Element: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> main -> 20 -> Issuing Tolerance.Toleranz
-- Column: PP_Order_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T15:13:25.386Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708254,0,53039,550061,613628,'F',TO_TIMESTAMP('2022-11-28 17:13:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz',40,0,0,TO_TIMESTAMP('2022-11-28 17:13:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> main -> 20 -> Issuing Tolerance.Toleranz Einheiten
-- Column: PP_Order_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T15:13:32.302Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708256,0,53039,550061,613629,'F',TO_TIMESTAMP('2022-11-28 17:13:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz Einheiten',50,0,0,TO_TIMESTAMP('2022-11-28 17:13:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> Toleranz %
-- Column: PP_Order_BOMLine.IssuingTolerance_Perc
-- 2022-11-28T15:14:05.855Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P',Updated=TO_TIMESTAMP('2022-11-28 17:14:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680657
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> Toleranz
-- Column: PP_Order_BOMLine.IssuingTolerance_Qty
-- 2022-11-28T15:14:28.079Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 17:14:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708254
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> Toleranz Einheiten
-- Column: PP_Order_BOMLine.IssuingTolerance_UOM_ID
-- 2022-11-28T15:14:32.677Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 17:14:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708256
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Komponenten(53039,EE01) -> Tolerance Value Type
-- Column: PP_Order_BOMLine.IssuingTolerance_ValueType
-- 2022-11-28T15:14:40.727Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y',Updated=TO_TIMESTAMP('2022-11-28 17:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708255
;

