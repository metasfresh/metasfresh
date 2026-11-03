-- Column: M_Picking_Job_Step_PickedHU.Catch_UOM_ID
-- Column: M_Picking_Job_Step_PickedHU.Catch_UOM_ID
-- 2024-07-05T06:43:11.718Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588667,576953,0,30,114,541936,'XX','Catch_UOM_ID',TO_TIMESTAMP('2024-07-05 09:43:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Aus dem Produktstamm übenommene Catch Weight Einheit.','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Catch Einheit',0,0,TO_TIMESTAMP('2024-07-05 09:43:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T06:43:11.727Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588667 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T06:43:11.756Z
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- 2024-07-05T06:43:12.707Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step_PickedHU','ALTER TABLE public.M_Picking_Job_Step_PickedHU ADD COLUMN Catch_UOM_ID NUMERIC(10)')
;

-- 2024-07-05T06:43:12.723Z
ALTER TABLE M_Picking_Job_Step_PickedHU ADD CONSTRAINT CatchUOM_MPickingJobStepPickedHU FOREIGN KEY (Catch_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2024-07-05T06:47:34.107Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583166,0,'CatchWeight',TO_TIMESTAMP('2024-07-05 09:47:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Catch Weight','Catch Weight',TO_TIMESTAMP('2024-07-05 09:47:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T06:47:34.112Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583166 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Picking_Job_Step_PickedHU.CatchWeight
-- Column: M_Picking_Job_Step_PickedHU.CatchWeight
-- 2024-07-05T06:47:56.219Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588668,583166,0,29,541936,'XX','CatchWeight',TO_TIMESTAMP('2024-07-05 09:47:56','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Catch Weight',0,0,TO_TIMESTAMP('2024-07-05 09:47:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T06:47:56.222Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588668 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T06:47:56.227Z
/* DDL */  select update_Column_Translation_From_AD_Element(583166) 
;

-- 2024-07-05T06:47:58.357Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step_PickedHU','ALTER TABLE public.M_Picking_Job_Step_PickedHU ADD COLUMN CatchWeight NUMERIC')
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Catch Einheit
-- Column: M_Picking_Job_Step_PickedHU.Catch_UOM_ID
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Catch Einheit
-- Column: M_Picking_Job_Step_PickedHU.Catch_UOM_ID
-- 2024-07-05T06:59:29.233Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588667,729025,0,544866,TO_TIMESTAMP('2024-07-05 09:59:29','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit.',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Catch Einheit',TO_TIMESTAMP('2024-07-05 09:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T06:59:29.235Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T06:59:29.239Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576953) 
;

-- 2024-07-05T06:59:29.258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729025
;

-- 2024-07-05T06:59:29.263Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729025)
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Catch Weight
-- Column: M_Picking_Job_Step_PickedHU.CatchWeight
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Catch Weight
-- Column: M_Picking_Job_Step_PickedHU.CatchWeight
-- 2024-07-05T06:59:29.375Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588668,729026,0,544866,TO_TIMESTAMP('2024-07-05 09:59:29','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Catch Weight',TO_TIMESTAMP('2024-07-05 09:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T06:59:29.376Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T06:59:29.378Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583166) 
;

-- 2024-07-05T06:59:29.382Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729026
;

-- 2024-07-05T06:59:29.383Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729026)
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Picking candidate
-- Column: M_Picking_Job_Step_PickedHU.M_Picking_Candidate_ID
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Picking candidate
-- Column: M_Picking_Job_Step_PickedHU.M_Picking_Candidate_ID
-- 2024-07-05T07:00:35.380Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2024-07-05 10:00:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669107
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Catch Einheit
-- Column: M_Picking_Job_Step_PickedHU.Catch_UOM_ID
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Catch Einheit
-- Column: M_Picking_Job_Step_PickedHU.Catch_UOM_ID
-- 2024-07-05T07:01:17.752Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-07-05 10:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729025
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Catch Weight
-- Column: M_Picking_Job_Step_PickedHU.CatchWeight
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Catch Weight
-- Column: M_Picking_Job_Step_PickedHU.CatchWeight
-- 2024-07-05T07:01:28.336Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-07-05 10:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729026
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Picking candidate
-- Column: M_Picking_Job_Step_PickedHU.M_Picking_Candidate_ID
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Picking candidate
-- Column: M_Picking_Job_Step_PickedHU.M_Picking_Candidate_ID
-- 2024-07-05T07:02:00.583Z
UPDATE AD_Field SET SeqNo=NULL, SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2024-07-05 10:02:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669107
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Catch Einheit
-- Column: M_Picking_Job_Step_PickedHU.Catch_UOM_ID
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Catch Einheit
-- Column: M_Picking_Job_Step_PickedHU.Catch_UOM_ID
-- 2024-07-05T07:02:10.690Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2024-07-05 10:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729025
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Catch Weight
-- Column: M_Picking_Job_Step_PickedHU.CatchWeight
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Catch Weight
-- Column: M_Picking_Job_Step_PickedHU.CatchWeight
-- 2024-07-05T07:02:15.602Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2024-07-05 10:02:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729026
;

