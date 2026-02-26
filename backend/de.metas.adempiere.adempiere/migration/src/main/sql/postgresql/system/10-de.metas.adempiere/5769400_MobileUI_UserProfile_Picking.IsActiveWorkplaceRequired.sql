-- Run mode: SWING_CLIENT

-- 2025-09-18T05:54:36.880Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583975,0,'IsActiveWorkplaceRequired',TO_TIMESTAMP('2025-09-18 05:54:36.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Aktiver Arbeitsplatz erforderlich','Aktiver Arbeitsplatz erforderlich',TO_TIMESTAMP('2025-09-18 05:54:36.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T05:54:36.894Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583975 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsActiveWorkplaceRequired
-- 2025-09-18T05:54:40.062Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-18 05:54:40.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583975 AND AD_Language='de_CH'
;

-- 2025-09-18T05:54:40.229Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583975,'de_CH')
;

-- Element: IsActiveWorkplaceRequired
-- 2025-09-18T05:54:41.832Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-18 05:54:41.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583975 AND AD_Language='de_DE'
;

-- 2025-09-18T05:54:41.836Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583975,'de_DE')
;

-- 2025-09-18T05:54:41.837Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583975,'de_DE')
;

-- Element: IsActiveWorkplaceRequired
-- 2025-09-18T05:55:13.694Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Active Workplace required', PrintName='Active Workplace required',Updated=TO_TIMESTAMP('2025-09-18 05:55:13.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583975 AND AD_Language='en_US'
;

-- 2025-09-18T05:55:13.696Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-18T05:55:14.027Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583975,'en_US')
;

-- Column: MobileUI_UserProfile_Picking.IsActiveWorkplaceRequired
-- 2025-09-18T05:55:38.789Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590937,583975,0,20,542373,'XX','IsActiveWorkplaceRequired',TO_TIMESTAMP('2025-09-18 05:55:38.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Aktiver Arbeitsplatz erforderlich',0,0,TO_TIMESTAMP('2025-09-18 05:55:38.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-18T05:55:38.794Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590937 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-18T05:55:38.799Z
/* DDL */  select update_Column_Translation_From_AD_Element(583975)
;

-- 2025-09-18T05:55:39.904Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsActiveWorkplaceRequired CHAR(1) DEFAULT ''Y'' CHECK (IsActiveWorkplaceRequired IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Aktiver Arbeitsplatz erforderlich
-- Column: MobileUI_UserProfile_Picking.IsActiveWorkplaceRequired
-- 2025-09-18T05:56:59.565Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590937,753780,0,547258,TO_TIMESTAMP('2025-09-18 05:56:59.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Aktiver Arbeitsplatz erforderlich',TO_TIMESTAMP('2025-09-18 05:56:59.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T05:56:59.568Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753780 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T05:56:59.573Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583975)
;

-- 2025-09-18T05:56:59.593Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753780
;

-- 2025-09-18T05:56:59.600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753780)
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20
-- UI Element Group: pick to
-- 2025-09-18T05:58:12.385Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2025-09-18 05:58:12.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553489
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20
-- UI Element Group: workplace
-- 2025-09-18T05:58:20.951Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547137,553515,TO_TIMESTAMP('2025-09-18 05:58:20.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','workplace',20,TO_TIMESTAMP('2025-09-18 05:58:20.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> workplace.Aktiver Arbeitsplatz erforderlich
-- Column: MobileUI_UserProfile_Picking.IsActiveWorkplaceRequired
-- 2025-09-18T05:58:58.571Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753780,0,547258,553515,637130,'F',TO_TIMESTAMP('2025-09-18 05:58:58.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Aktiver Arbeitsplatz erforderlich',10,0,0,TO_TIMESTAMP('2025-09-18 05:58:58.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> workplace.Consider only scheduled jobs
-- Column: MobileUI_UserProfile_Picking.IsConsideredOnlyScheduledJobs
-- 2025-09-18T05:59:16.704Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553515, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2025-09-18 05:59:16.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636564
;

