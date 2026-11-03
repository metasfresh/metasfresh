-- Run mode: SWING_CLIENT

-- 2025-02-27T20:33:37.862Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583514,0,'PickFrom_HUQRCode',TO_TIMESTAMP('2025-02-27 20:33:37.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Pick From HU QR Code','Pick From HU QR Code',TO_TIMESTAMP('2025-02-27 20:33:37.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-27T20:33:37.883Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583514 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Picking_Job.PickFrom_HUQRCode
-- 2025-02-27T20:35:42.773Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589748,583514,0,36,541906,'XX','PickFrom_HUQRCode',TO_TIMESTAMP('2025-02-27 20:35:42.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,9999999,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Pick From HU QR Code',0,0,TO_TIMESTAMP('2025-02-27 20:35:42.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-27T20:35:42.777Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589748 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-27T20:35:42.944Z
/* DDL */  select update_Column_Translation_From_AD_Element(583514)
;

-- 2025-02-27T20:35:44.136Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN PickFrom_HUQRCode TEXT')
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Pick From HU QR Code
-- Column: M_Picking_Job.PickFrom_HUQRCode
-- 2025-02-27T20:36:17.381Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589748,740355,0,544861,TO_TIMESTAMP('2025-02-27 20:36:17.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,9999999,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Pick From HU QR Code',TO_TIMESTAMP('2025-02-27 20:36:17.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-27T20:36:17.382Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740355 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-27T20:36:17.385Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583514)
;

-- 2025-02-27T20:36:17.410Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740355
;

-- 2025-02-27T20:36:17.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740355)
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Pick From HU QR Code
-- Column: M_Picking_Job.PickFrom_HUQRCode
-- 2025-02-27T20:37:05.668Z
UPDATE AD_Field SET DisplayLogic='@PickFrom_HU_ID/0@>0',Updated=TO_TIMESTAMP('2025-02-27 20:37:05.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740355
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10 -> Pick from.Pick From HU QR Code
-- Column: M_Picking_Job.PickFrom_HUQRCode
-- 2025-02-27T20:37:26.710Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740355,0,544861,552639,630661,'F',TO_TIMESTAMP('2025-02-27 20:37:26.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Pick From HU QR Code',20,0,0,TO_TIMESTAMP('2025-02-27 20:37:26.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

