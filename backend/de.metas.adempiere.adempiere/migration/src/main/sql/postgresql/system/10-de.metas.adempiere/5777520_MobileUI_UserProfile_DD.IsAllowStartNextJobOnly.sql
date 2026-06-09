-- Run mode: SWING_CLIENT

-- 2025-11-19T08:06:54.940Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584225,0,'IsAllowStartNextJobOnly',TO_TIMESTAMP('2025-11-19 08:06:54.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Restrict start to next job only','Restrict start to next job only',TO_TIMESTAMP('2025-11-19 08:06:54.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T08:06:54.961Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584225 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAllowStartNextJobOnly
-- 2025-11-19T08:07:30.523Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Start nur für nächsten Job zulassen', PrintName='Start nur für nächsten Job zulassen',Updated=TO_TIMESTAMP('2025-11-19 08:07:30.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584225 AND AD_Language='de_CH'
;

-- 2025-11-19T08:07:30.524Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-19T08:07:30.860Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584225,'de_CH')
;

-- Element: IsAllowStartNextJobOnly
-- 2025-11-19T08:07:34.473Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Start nur für nächsten Job zulassen', PrintName='Start nur für nächsten Job zulassen',Updated=TO_TIMESTAMP('2025-11-19 08:07:34.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584225 AND AD_Language='de_DE'
;

-- 2025-11-19T08:07:34.474Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-19T08:07:36.024Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584225,'de_DE')
;

-- 2025-11-19T08:07:36.027Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584225,'de_DE')
;

-- Element: IsAllowStartNextJobOnly
-- 2025-11-19T08:07:55.079Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-19 08:07:55.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584225 AND AD_Language='en_US'
;

-- 2025-11-19T08:07:55.082Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584225,'en_US')
;

-- Column: MobileUI_UserProfile_DD_CaptionItem.IsAllowStartNextJobOnly
-- 2025-11-19T08:08:08.251Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591577,584225,0,20,542557,'XX','IsAllowStartNextJobOnly',TO_TIMESTAMP('2025-11-19 08:08:08.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Start nur für nächsten Job zulassen',0,0,TO_TIMESTAMP('2025-11-19 08:08:08.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-19T08:08:08.253Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591577 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-19T08:08:08.256Z
/* DDL */  select update_Column_Translation_From_AD_Element(584225)
;

-- 2025-11-19T08:08:08.862Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_DD_CaptionItem','ALTER TABLE public.MobileUI_UserProfile_DD_CaptionItem ADD COLUMN IsAllowStartNextJobOnly CHAR(1) DEFAULT ''N'' CHECK (IsAllowStartNextJobOnly IN (''Y'',''N'')) NOT NULL')
;

-- 2025-11-19T08:08:45.566Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_DD_CaptionItem','ALTER TABLE MobileUI_UserProfile_DD_CaptionItem DROP COLUMN IF EXISTS IsAllowStartNextJobOnly')
;

-- Column: MobileUI_UserProfile_DD_CaptionItem.IsAllowStartNextJobOnly
-- 2025-11-19T08:08:45.738Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591577
;

-- 2025-11-19T08:08:45.745Z
DELETE FROM AD_Column WHERE AD_Column_ID=591577
;

-- Column: MobileUI_UserProfile_DD.IsAllowStartNextJobOnly
-- 2025-11-19T08:08:57.263Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591578,584225,0,20,542462,'XX','IsAllowStartNextJobOnly',TO_TIMESTAMP('2025-11-19 08:08:57.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Start nur für nächsten Job zulassen',0,0,TO_TIMESTAMP('2025-11-19 08:08:57.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-19T08:08:57.265Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591578 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-19T08:08:57.366Z
/* DDL */  select update_Column_Translation_From_AD_Element(584225)
;

-- 2025-11-19T08:08:57.976Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_DD','ALTER TABLE public.MobileUI_UserProfile_DD ADD COLUMN IsAllowStartNextJobOnly CHAR(1) DEFAULT ''N'' CHECK (IsAllowStartNextJobOnly IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> Start nur für nächsten Job zulassen
-- Column: MobileUI_UserProfile_DD.IsAllowStartNextJobOnly
-- 2025-11-19T08:09:11.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591578,756198,0,547735,TO_TIMESTAMP('2025-11-19 08:09:11.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Start nur für nächsten Job zulassen',TO_TIMESTAMP('2025-11-19 08:09:11.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T08:09:11.195Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756198 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-19T08:09:11.199Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584225)
;

-- 2025-11-19T08:09:11.203Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756198
;

-- 2025-11-19T08:09:11.206Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756198)
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 10 -> default.Start nur für nächsten Job zulassen
-- Column: MobileUI_UserProfile_DD.IsAllowStartNextJobOnly
-- 2025-11-19T08:09:30.226Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756198,0,547735,552270,638752,'F',TO_TIMESTAMP('2025-11-19 08:09:30.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Start nur für nächsten Job zulassen',20,0,0,TO_TIMESTAMP('2025-11-19 08:09:30.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20 -> flags.Start nur für nächsten Job zulassen
-- Column: MobileUI_UserProfile_DD.IsAllowStartNextJobOnly
-- 2025-11-19T08:09:52.010Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552272, SeqNo=20,Updated=TO_TIMESTAMP('2025-11-19 08:09:52.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638752
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 10 -> launchers.Start nur für nächsten Job zulassen
-- Column: MobileUI_UserProfile_DD.IsAllowStartNextJobOnly
-- 2025-11-19T08:10:24.141Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553773, SeqNo=30,Updated=TO_TIMESTAMP('2025-11-19 08:10:24.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638752
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 10 -> launchers.Maximum Started Launchers Count
-- Column: MobileUI_UserProfile_DD.MaxStartedLaunchers
-- 2025-11-19T08:10:47.005Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-11-19 08:10:47.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638751
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 10 -> launchers.Start nur für nächsten Job zulassen
-- Column: MobileUI_UserProfile_DD.IsAllowStartNextJobOnly
-- 2025-11-19T08:10:47.012Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-19 08:10:47.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638752
;

