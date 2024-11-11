-- Column: MobileUI_UserProfile_Picking.IsAllowPickingAnyHU
-- Column: MobileUI_UserProfile_Picking.IsAllowPickingAnyHU
-- 2023-10-23T14:19:53.932Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587604,582776,0,20,542373,'IsAllowPickingAnyHU',TO_TIMESTAMP('2023-10-23 17:19:53','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow Picking any HU',0,0,TO_TIMESTAMP('2023-10-23 17:19:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-23T14:19:53.937Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587604 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-23T14:19:53.941Z
/* DDL */  select update_Column_Translation_From_AD_Element(582776) 
;

-- 2023-10-23T14:19:54.686Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsAllowPickingAnyHU CHAR(1) DEFAULT ''N'' CHECK (IsAllowPickingAnyHU IN (''Y'',''N'')) NOT NULL')
;

-- Column: MobileUI_UserProfile_Picking.IsAllowPickingAnyHU
-- Column: MobileUI_UserProfile_Picking.IsAllowPickingAnyHU
-- 2023-10-23T14:21:22.394Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-10-23 17:21:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587604
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Allow Picking any HU
-- Column: MobileUI_UserProfile_Picking.IsAllowPickingAnyHU
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Allow Picking any HU
-- Column: MobileUI_UserProfile_Picking.IsAllowPickingAnyHU
-- 2023-10-23T16:53:17.793Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587604,721715,0,547258,TO_TIMESTAMP('2023-10-23 19:53:17','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Allow Picking any HU',TO_TIMESTAMP('2023-10-23 19:53:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T16:53:17.795Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721715 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-23T16:53:17.797Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582776) 
;

-- 2023-10-23T16:53:17.810Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721715
;

-- 2023-10-23T16:53:17.811Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721715)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Allow Picking any HU
-- Column: MobileUI_UserProfile_Picking.IsAllowPickingAnyHU
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Allow Picking any HU
-- Column: MobileUI_UserProfile_Picking.IsAllowPickingAnyHU
-- 2023-10-23T16:54:54.817Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721715,0,547258,551252,621166,'F',TO_TIMESTAMP('2023-10-23 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Allow Picking any HU',20,0,0,TO_TIMESTAMP('2023-10-23 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

