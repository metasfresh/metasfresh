-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- 2024-04-18T16:49:48.459Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588190,542132,0,30,541907,'XX','M_HU_PI_Item_Product_ID',TO_TIMESTAMP('2024-04-18 19:49:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Packvorschrift',0,0,TO_TIMESTAMP('2024-04-18 19:49:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-18T16:49:48.461Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588190 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-18T16:49:48.491Z
/* DDL */  select update_Column_Translation_From_AD_Element(542132) 
;

-- 2024-04-18T16:49:49.382Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN M_HU_PI_Item_Product_ID NUMERIC(10)')
;

-- 2024-04-18T16:49:49.399Z
ALTER TABLE M_Picking_Job_Line ADD CONSTRAINT MHUPIItemProduct_MPickingJobLine FOREIGN KEY (M_HU_PI_Item_Product_ID) REFERENCES public.M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED
;

-- Field: Picking Job -> Picking Job Line -> Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- 2024-04-18T16:50:20.339Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588190,728052,0,544862,TO_TIMESTAMP('2024-04-18 19:50:20','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Packvorschrift',TO_TIMESTAMP('2024-04-18 19:50:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-18T16:50:20.343Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-18T16:50:20.347Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132) 
;

-- 2024-04-18T16:50:20.369Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728052
;

-- 2024-04-18T16:50:20.371Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728052)
;

-- UI Element: Picking Job -> Picking Job Line.Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- 2024-04-18T16:52:18.351Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728052,0,544862,551272,624524,'F',TO_TIMESTAMP('2024-04-18 19:52:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift',50,0,0,TO_TIMESTAMP('2024-04-18 19:52:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- 2024-04-18T16:52:34.212Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2024-04-18 19:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624524
;

-- Field: Picking Job -> Picking Job Line -> Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- 2024-04-18T16:52:46.714Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-04-18 19:52:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728052
;

-- UI Element: Picking Job -> Picking Job Line.Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Packvorschrift
-- Column: M_Picking_Job_Line.M_HU_PI_Item_Product_ID
-- 2024-04-18T16:54:36.698Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-04-18 19:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624524
;

-- UI Element: Picking Job -> Picking Job Line.Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- 2024-04-18T16:54:36.706Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-04-18 19:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621182
;

-- UI Element: Picking Job -> Picking Job Line.Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- 2024-04-18T16:54:36.714Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-04-18 19:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621183
;

-- UI Element: Picking Job -> Picking Job Line.Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> referenced documents.Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- 2024-04-18T16:54:36.722Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-04-18 19:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621185
;

-- UI Element: Picking Job -> Picking Job Line.Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> referenced documents.Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- 2024-04-18T16:54:36.731Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-04-18 19:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621186
;

-- UI Element: Picking Job -> Picking Job Line.Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> referenced documents.Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- 2024-04-18T16:54:36.739Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-04-18 19:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621187
;

-- UI Element: Picking Job -> Picking Job Line.Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> flags.Manually closed
-- Column: M_Picking_Job_Line.IsManuallyClosed
-- 2024-04-18T16:54:36.747Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-04-18 19:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623759
;

