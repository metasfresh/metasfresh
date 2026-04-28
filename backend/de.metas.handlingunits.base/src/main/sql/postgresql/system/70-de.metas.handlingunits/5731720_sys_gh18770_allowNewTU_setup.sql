-- Column: M_Picking_Job.M_TU_HU_PI_ID
-- Column: M_Picking_Job.M_TU_HU_PI_ID
-- 2024-08-27T09:01:10.531Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588931,542489,0,18,540396,541906,'XX','M_TU_HU_PI_ID',TO_TIMESTAMP('2024-08-27 12:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Packvorschrift (TU)',0,0,TO_TIMESTAMP('2024-08-27 12:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-27T09:01:10.543Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588931 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-27T09:01:10.554Z
/* DDL */  select update_Column_Translation_From_AD_Element(542489) 
;

-- 2024-08-27T09:01:12.892Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN M_TU_HU_PI_ID NUMERIC(10)')
;

-- 2024-08-27T09:01:12.909Z
ALTER TABLE M_Picking_Job ADD CONSTRAINT MTUHUPI_MPickingJob FOREIGN KEY (M_TU_HU_PI_ID) REFERENCES public.M_HU_PI DEFERRABLE INITIALLY DEFERRED
;

-- 2024-08-27T09:40:25.085Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583230,0,'IsAllowNewTU',TO_TIMESTAMP('2024-08-27 12:40:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Allow new TU','Allow new TU',TO_TIMESTAMP('2024-08-27 12:40:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-27T09:40:25.095Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583230 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- 2024-08-27T09:40:59.681Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588932,583230,0,20,542373,'XX','IsAllowNewTU',TO_TIMESTAMP('2024-08-27 12:40:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.picking',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow new TU',0,0,TO_TIMESTAMP('2024-08-27 12:40:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-27T09:40:59.684Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588932 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-27T09:40:59.687Z
/* DDL */  select update_Column_Translation_From_AD_Element(583230) 
;

-- 2024-08-27T09:41:01.773Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsAllowNewTU CHAR(1) DEFAULT ''N'' CHECK (IsAllowNewTU IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Allow new TU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Allow new TU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- 2024-08-27T09:42:10.942Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588932,729840,0,547258,TO_TIMESTAMP('2024-08-27 12:42:10','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Allow new TU',TO_TIMESTAMP('2024-08-27 12:42:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-27T09:42:10.957Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-27T09:42:10.979Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583230) 
;

-- 2024-08-27T09:42:11.018Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729840
;

-- 2024-08-27T09:42:11.025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729840)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Allow new TU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Allow new TU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- 2024-08-27T09:42:49.442Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729840,0,547258,625298,551252,'F',TO_TIMESTAMP('2024-08-27 12:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Allow new TU',50,0,0,TO_TIMESTAMP('2024-08-27 12:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-27T10:02:35.281Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','IsAllowNewTU','CHAR(1)',null,'N')
;

-- 2024-08-27T10:02:35.318Z
UPDATE MobileUI_UserProfile_Picking SET IsAllowNewTU='N' WHERE IsAllowNewTU IS NULL
;

-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- 2024-08-27T10:04:18.506Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-08-27 13:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588932
;

