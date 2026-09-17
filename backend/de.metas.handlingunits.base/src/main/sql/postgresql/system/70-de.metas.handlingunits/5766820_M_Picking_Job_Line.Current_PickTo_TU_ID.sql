-- Run mode: SWING_CLIENT

-- 2025-09-04T14:51:48.640Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583925,0,'Current_PickTo_TU_ID',TO_TIMESTAMP('2025-09-04 14:51:48.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Current Pick To TU','Current Pick To TU',TO_TIMESTAMP('2025-09-04 14:51:48.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T14:51:48.653Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583925 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Picking_Job_Line.Current_PickTo_TU_ID
-- 2025-09-04T14:52:12.757Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590810,583925,0,30,540499,541907,'XX','Current_PickTo_TU_ID',TO_TIMESTAMP('2025-09-04 14:52:12.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Current Pick To TU',0,0,TO_TIMESTAMP('2025-09-04 14:52:12.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-04T14:52:12.761Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590810 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-04T14:52:12.917Z
/* DDL */  select update_Column_Translation_From_AD_Element(583925)
;

-- 2025-09-04T14:52:14.072Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN Current_PickTo_TU_ID NUMERIC(10)')
;

-- 2025-09-04T14:52:14.087Z
ALTER TABLE M_Picking_Job_Line ADD CONSTRAINT CurrentPickToTU_MPickingJobLine FOREIGN KEY (Current_PickTo_TU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Current Pick To TU
-- Column: M_Picking_Job_Line.Current_PickTo_TU_ID
-- 2025-09-04T14:52:49.285Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590810,753501,0,544862,TO_TIMESTAMP('2025-09-04 14:52:48.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Current Pick To TU',TO_TIMESTAMP('2025-09-04 14:52:48.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T14:52:49.288Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753501 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-04T14:52:49.291Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583925)
;

-- 2025-09-04T14:52:49.311Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753501
;

-- 2025-09-04T14:52:49.317Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753501)
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> pick to.Current Pick To TU
-- Column: M_Picking_Job_Line.Current_PickTo_TU_ID
-- 2025-09-04T14:53:37.494Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753501,0,544862,552640,636944,'F',TO_TIMESTAMP('2025-09-04 14:53:37.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Current Pick To TU',60,0,0,TO_TIMESTAMP('2025-09-04 14:53:37.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T14:54:11.687Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583926,0,'Current_PickTo_TU_QRCode',TO_TIMESTAMP('2025-09-04 14:54:11.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Current Pick To TU QR Code','Current Pick To TU QR Code',TO_TIMESTAMP('2025-09-04 14:54:11.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T14:54:11.691Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583926 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Picking_Job_Line.Current_PickTo_TU_QRCode
-- 2025-09-04T14:54:29.691Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590811,583926,0,36,541907,'XX','Current_PickTo_TU_QRCode',TO_TIMESTAMP('2025-09-04 14:54:29.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,9999999,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Current Pick To TU QR Code',0,0,TO_TIMESTAMP('2025-09-04 14:54:29.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-04T14:54:29.693Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590811 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-04T14:54:29.697Z
/* DDL */  select update_Column_Translation_From_AD_Element(583926)
;

-- 2025-09-04T14:54:32.137Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN Current_PickTo_TU_QRCode TEXT')
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Current Pick To TU QR Code
-- Column: M_Picking_Job_Line.Current_PickTo_TU_QRCode
-- 2025-09-04T14:54:49.485Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590811,753502,0,544862,TO_TIMESTAMP('2025-09-04 14:54:49.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,9999999,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Current Pick To TU QR Code',TO_TIMESTAMP('2025-09-04 14:54:49.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T14:54:49.488Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753502 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-04T14:54:49.490Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583926)
;

-- 2025-09-04T14:54:49.493Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753502
;

-- 2025-09-04T14:54:49.494Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753502)
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> pick to.Current Pick To TU QR Code
-- Column: M_Picking_Job_Line.Current_PickTo_TU_QRCode
-- 2025-09-04T14:55:05.869Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753502,0,544862,552640,636945,'F',TO_TIMESTAMP('2025-09-04 14:55:05.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Current Pick To TU QR Code',70,0,0,TO_TIMESTAMP('2025-09-04 14:55:05.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Run mode: SWING_CLIENT

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Current Pick To TU
-- Column: M_Picking_Job_Line.Current_PickTo_TU_ID
-- 2025-09-04T14:58:38.795Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-09-04 14:58:38.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753501
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Current Pick To TU QR Code
-- Column: M_Picking_Job_Line.Current_PickTo_TU_QRCode
-- 2025-09-04T14:58:40.244Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-09-04 14:58:40.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753502
;

