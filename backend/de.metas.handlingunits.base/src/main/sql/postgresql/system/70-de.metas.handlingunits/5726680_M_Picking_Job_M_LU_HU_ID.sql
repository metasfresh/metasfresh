-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- 2024-06-19T12:35:19.187Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588620,542487,0,30,540396,541906,'XX','M_LU_HU_PI_ID',TO_TIMESTAMP('2024-06-19 15:35:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Packvorschrift (LU)',0,0,TO_TIMESTAMP('2024-06-19 15:35:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-06-19T12:35:19.193Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588620 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-19T12:35:19.221Z
/* DDL */  select update_Column_Translation_From_AD_Element(542487) 
;

-- 2024-06-19T12:35:21.900Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN M_LU_HU_PI_ID NUMERIC(10)')
;

-- 2024-06-19T12:35:21.914Z
ALTER TABLE M_Picking_Job ADD CONSTRAINT MLUHUPI_MPickingJob FOREIGN KEY (M_LU_HU_PI_ID) REFERENCES public.M_HU_PI DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Picking_Job.M_LU_HU_ID
-- Column: M_Picking_Job.M_LU_HU_ID
-- 2024-06-19T12:35:50.523Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588621,542455,0,30,540499,541906,'XX','M_LU_HU_ID',TO_TIMESTAMP('2024-06-19 15:35:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Loading Unit','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'LU',0,0,TO_TIMESTAMP('2024-06-19 15:35:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-06-19T12:35:50.524Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588621 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-19T12:35:50.528Z
/* DDL */  select update_Column_Translation_From_AD_Element(542455) 
;

-- 2024-06-19T12:35:51.196Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN M_LU_HU_ID NUMERIC(10)')
;

-- 2024-06-19T12:35:51.203Z
ALTER TABLE M_Picking_Job ADD CONSTRAINT MLUHU_MPickingJob FOREIGN KEY (M_LU_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- Field: Picking Job -> Picking Job -> LU
-- Column: M_Picking_Job.M_LU_HU_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> LU
-- Column: M_Picking_Job.M_LU_HU_ID
-- 2024-06-19T12:36:41.712Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588621,729001,0,544861,TO_TIMESTAMP('2024-06-19 15:36:41','YYYY-MM-DD HH24:MI:SS'),100,'Loading Unit',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','LU',TO_TIMESTAMP('2024-06-19 15:36:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-19T12:36:41.717Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-19T12:36:41.720Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542455) 
;

-- 2024-06-19T12:36:41.735Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729001
;

-- 2024-06-19T12:36:41.737Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729001)
;

-- Field: Picking Job -> Picking Job -> Packvorschrift (LU)
-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Packvorschrift (LU)
-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- 2024-06-19T12:36:51.916Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588620,729002,0,544861,TO_TIMESTAMP('2024-06-19 15:36:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Packvorschrift (LU)',TO_TIMESTAMP('2024-06-19 15:36:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-19T12:36:51.919Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-19T12:36:51.921Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542487) 
;

-- 2024-06-19T12:36:51.927Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729002
;

-- 2024-06-19T12:36:51.928Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729002)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10
-- UI Element Group: Picking to LU
-- 2024-06-19T12:37:57.149Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547151,551853,TO_TIMESTAMP('2024-06-19 15:37:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','Picking to LU',30,TO_TIMESTAMP('2024-06-19 15:37:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Packvorschrift (LU)
-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> Picking to LU.Packvorschrift (LU)
-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- 2024-06-19T12:38:10.981Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729002,0,544861,551853,624946,'F',TO_TIMESTAMP('2024-06-19 15:38:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift (LU)',10,0,0,TO_TIMESTAMP('2024-06-19 15:38:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.LU
-- Column: M_Picking_Job.M_LU_HU_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> Picking to LU.LU
-- Column: M_Picking_Job.M_LU_HU_ID
-- 2024-06-19T12:38:17.329Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729001,0,544861,551853,624947,'F',TO_TIMESTAMP('2024-06-19 15:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Loading Unit','Y','N','Y','N','N','LU',20,0,0,TO_TIMESTAMP('2024-06-19 15:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Picking Job -> Picking Job -> Packvorschrift (LU)
-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Packvorschrift (LU)
-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- 2024-06-19T12:38:41.717Z
UPDATE AD_Field SET DisplayLogic='@M_LU_HU_PI_ID/0@>0',Updated=TO_TIMESTAMP('2024-06-19 15:38:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729002
;

-- Field: Picking Job -> Picking Job -> Packvorschrift (LU)
-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Packvorschrift (LU)
-- Column: M_Picking_Job.M_LU_HU_PI_ID
-- 2024-06-19T12:38:46.521Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-06-19 15:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729002
;

-- Field: Picking Job -> Picking Job -> LU
-- Column: M_Picking_Job.M_LU_HU_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> LU
-- Column: M_Picking_Job.M_LU_HU_ID
-- 2024-06-19T12:39:00.513Z
UPDATE AD_Field SET DisplayLogic='@M_LU_HU_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-06-19 15:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729001
;

