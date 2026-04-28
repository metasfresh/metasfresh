-- Run mode: SWING_CLIENT

-- 2025-12-16T13:18:51.552Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584373,0,'IsRequireTrolley',TO_TIMESTAMP('2025-12-16 13:18:51.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Wagen erforderlich','Wagen erforderlich',TO_TIMESTAMP('2025-12-16 13:18:51.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T13:18:51.587Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584373 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsRequireTrolley
-- 2025-12-16T13:18:57.751Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-16 13:18:57.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584373 AND AD_Language='de_CH'
;

-- 2025-12-16T13:18:57.976Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584373,'de_CH')
;

-- Element: IsRequireTrolley
-- 2025-12-16T13:19:06.551Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-16 13:19:06.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584373 AND AD_Language='de_DE'
;

-- 2025-12-16T13:19:06.556Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584373,'de_DE')
;

-- 2025-12-16T13:19:06.560Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584373,'de_DE')
;

-- Element: IsRequireTrolley
-- 2025-12-16T13:19:21.480Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Require Trolley', PrintName='Require Trolley',Updated=TO_TIMESTAMP('2025-12-16 13:19:21.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584373 AND AD_Language='en_US'
;

-- 2025-12-16T13:19:21.492Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-16T13:19:22.116Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584373,'en_US')
;

-- Element: IsRequireTrolley
-- 2025-12-16T13:19:27.017Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Require Trolley', PrintName='Require Trolley',Updated=TO_TIMESTAMP('2025-12-16 13:19:27.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584373 AND AD_Language='en_GB'
;

-- 2025-12-16T13:19:27.018Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-16T13:19:27.373Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584373,'en_GB')
;

-- Column: MobileUI_UserProfile_DD.IsRequireTrolley
-- 2025-12-16T13:19:43.008Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591760,584373,0,20,542462,'XX','IsRequireTrolley',TO_TIMESTAMP('2025-12-16 13:19:42.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Wagen erforderlich',0,0,TO_TIMESTAMP('2025-12-16 13:19:42.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-16T13:19:43.014Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591760 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-16T13:19:43.021Z
/* DDL */  select update_Column_Translation_From_AD_Element(584373)
;

-- 2025-12-16T13:19:44.310Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_DD','ALTER TABLE public.MobileUI_UserProfile_DD ADD COLUMN IsRequireTrolley CHAR(1) DEFAULT ''N'' CHECK (IsRequireTrolley IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> Wagen erforderlich
-- Column: MobileUI_UserProfile_DD.IsRequireTrolley
-- 2025-12-16T13:20:21.410Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591760,760923,0,547735,TO_TIMESTAMP('2025-12-16 13:20:21.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Wagen erforderlich',TO_TIMESTAMP('2025-12-16 13:20:21.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T13:20:21.417Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-16T13:20:21.423Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584373)
;

-- 2025-12-16T13:20:21.440Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760923
;

-- 2025-12-16T13:20:21.448Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760923)
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 10 -> default.Wagen erforderlich
-- Column: MobileUI_UserProfile_DD.IsRequireTrolley
-- 2025-12-16T13:22:09.516Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760923,0,547735,552270,641264,'F',TO_TIMESTAMP('2025-12-16 13:22:09.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Wagen erforderlich',20,0,0,TO_TIMESTAMP('2025-12-16 13:22:09.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

