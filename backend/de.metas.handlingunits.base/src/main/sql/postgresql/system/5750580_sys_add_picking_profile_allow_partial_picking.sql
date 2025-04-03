-- Run mode: SWING_CLIENT

-- 2025-04-02T08:06:58.214Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583562,0,'IsAllowCompletingPartialPickingJob',TO_TIMESTAMP('2025-04-02 08:06:58.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Allow completing partial picking jobs','Allow completing partial picking jobs',TO_TIMESTAMP('2025-04-02 08:06:58.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-02T08:06:58.222Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583562 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAllowCompletingPartialPickingJob
-- 2025-04-02T08:08:01.923Z
UPDATE AD_Element_Trl SET Name='Teilweise Kommissionierung erlauben', PrintName='Teilweise Kommissionierung erlauben',Updated=TO_TIMESTAMP('2025-04-02 08:08:01.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583562 AND AD_Language='de_CH'
;

-- 2025-04-02T08:08:01.924Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-02T08:08:02.492Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583562,'de_CH')
;

-- Element: IsAllowCompletingPartialPickingJob
-- 2025-04-02T08:08:05.634Z
UPDATE AD_Element_Trl SET Name='Teilweise Kommissionierung erlauben', PrintName='Teilweise Kommissionierung erlauben',Updated=TO_TIMESTAMP('2025-04-02 08:08:05.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583562 AND AD_Language='de_DE'
;

-- 2025-04-02T08:08:05.635Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-02T08:08:07.416Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583562,'de_DE')
;

-- 2025-04-02T08:08:07.421Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583562,'de_DE')
;

-- Element: IsAllowCompletingPartialPickingJob
-- 2025-04-02T08:08:28.849Z
UPDATE AD_Element_Trl SET Name='Teilweise Kommissionierung erlauben', PrintName='Teilweise Kommissionierung erlauben',Updated=TO_TIMESTAMP('2025-04-02 08:08:28.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583562 AND AD_Language='fr_CH'
;

-- 2025-04-02T08:08:28.850Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-02T08:08:29.393Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583562,'fr_CH')
;

-- Column: MobileUI_UserProfile_Picking.IsAllowCompletingPartialPickingJob
-- 2025-04-02T08:09:02.511Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589827,583562,0,20,542373,'XX','IsAllowCompletingPartialPickingJob',TO_TIMESTAMP('2025-04-02 08:09:02.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Teilweise Kommissionierung erlauben',0,0,TO_TIMESTAMP('2025-04-02 08:09:02.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-02T08:09:02.524Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589827 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-02T08:09:02.540Z
/* DDL */  select update_Column_Translation_From_AD_Element(583562)
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Teilweise Kommissionierung erlauben
-- Column: MobileUI_UserProfile_Picking.IsAllowCompletingPartialPickingJob
-- 2025-04-02T08:10:00.806Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589827,741854,0,547258,TO_TIMESTAMP('2025-04-02 08:10:00.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Teilweise Kommissionierung erlauben',TO_TIMESTAMP('2025-04-02 08:10:00.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-02T08:10:00.813Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741854 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-02T08:10:00.822Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583562)
;

-- 2025-04-02T08:10:00.848Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741854
;

-- 2025-04-02T08:10:00.857Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741854)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Teilweise Kommissionierung erlauben
-- Column: MobileUI_UserProfile_Picking.IsAllowCompletingPartialPickingJob
-- 2025-04-02T08:10:29.124Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741854,0,547258,631300,551252,'F',TO_TIMESTAMP('2025-04-02 08:10:28.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Teilweise Kommissionierung erlauben',110,0,0,TO_TIMESTAMP('2025-04-02 08:10:28.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Run mode: SWING_CLIENT

-- 2025-04-02T08:12:22.412Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsAllowCompletingPartialPickingJob CHAR(1) DEFAULT ''N'' CHECK (IsAllowCompletingPartialPickingJob IN (''Y'',''N'')) NOT NULL')
;


-- Element: IsAllowCompletingPartialPickingJob
-- 2025-04-02T17:32:12.378Z
UPDATE AD_Element_Trl SET Description='Users can complete jobs on mobile without picking the full ordered quantity.', Help='Users can complete jobs on mobile without picking the full ordered quantity.',Updated=TO_TIMESTAMP('2025-04-02 17:32:12.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583562 AND AD_Language='en_US'
;

-- 2025-04-02T17:32:12.384Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-02T17:32:12.910Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583562,'en_US')
;

-- Element: IsAllowCompletingPartialPickingJob
-- 2025-04-02T17:35:46.759Z
UPDATE AD_Element_Trl SET Description='Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.', Help='Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.',Updated=TO_TIMESTAMP('2025-04-02 17:35:46.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583562 AND AD_Language='fr_CH'
;

-- 2025-04-02T17:35:46.760Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-02T17:35:47.212Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583562,'fr_CH')
;

-- Element: IsAllowCompletingPartialPickingJob
-- 2025-04-02T17:35:50.761Z
UPDATE AD_Element_Trl SET Description='Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.', Help='Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.',Updated=TO_TIMESTAMP('2025-04-02 17:35:50.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583562 AND AD_Language='de_DE'
;

-- 2025-04-02T17:35:50.762Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-02T17:35:52.347Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583562,'de_DE')
;

-- 2025-04-02T17:35:52.352Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583562,'de_DE')
;

-- Element: IsAllowCompletingPartialPickingJob
-- 2025-04-02T17:35:59.177Z
UPDATE AD_Element_Trl SET Description='Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.', Help='Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.',Updated=TO_TIMESTAMP('2025-04-02 17:35:59.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583562 AND AD_Language='de_CH'
;

-- 2025-04-02T17:35:59.178Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-02T17:35:59.675Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583562,'de_CH')
;

