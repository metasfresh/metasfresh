-- 2024-07-04T13:54:40.530Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583164,0,'Picked_RenderedQRCode',TO_TIMESTAMP('2024-07-04 16:54:40','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.handlingunits','Y','Picked QR Code','Picked QR Code',TO_TIMESTAMP('2024-07-04 16:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-04T13:54:40.545Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583164 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Picking_Job_Step_PickedHU.Picked_RenderedQRCode
-- Column: M_Picking_Job_Step_PickedHU.Picked_RenderedQRCode
-- 2024-07-04T13:55:40.957Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588665,583164,0,36,541936,'XX','Picked_RenderedQRCode',TO_TIMESTAMP('2024-07-04 16:55:40','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.handlingunits',0,9999999,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Picked QR Code',0,0,TO_TIMESTAMP('2024-07-04 16:55:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-04T13:55:40.961Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588665 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-04T13:55:40.989Z
/* DDL */  select update_Column_Translation_From_AD_Element(583164) 
;

-- 2024-07-04T13:55:41.790Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Step_PickedHU','ALTER TABLE public.M_Picking_Job_Step_PickedHU ADD COLUMN Picked_RenderedQRCode TEXT')
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Picked QR Code
-- Column: M_Picking_Job_Step_PickedHU.Picked_RenderedQRCode
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Picked QR Code
-- Column: M_Picking_Job_Step_PickedHU.Picked_RenderedQRCode
-- 2024-07-04T13:56:13.825Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588665,729023,0,544866,TO_TIMESTAMP('2024-07-04 16:56:13','YYYY-MM-DD HH24:MI:SS'),100,'',9999999,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Picked QR Code',TO_TIMESTAMP('2024-07-04 16:56:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-04T13:56:13.828Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-04T13:56:13.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583164) 
;

-- 2024-07-04T13:56:13.847Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729023
;

-- 2024-07-04T13:56:13.850Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729023)
;

-- Field: Picking Job Step -> Picking Step Picked HU -> Picked QR Code
-- Column: M_Picking_Job_Step_PickedHU.Picked_RenderedQRCode
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Step Picked HU(544866,de.metas.handlingunits) -> Picked QR Code
-- Column: M_Picking_Job_Step_PickedHU.Picked_RenderedQRCode
-- 2024-07-04T13:56:26.590Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-04 16:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729023
;

