-- Run mode: SWING_CLIENT

-- Adds the picking-profile setting that decides how the packing launcher offers its filter groups:
-- unset (the default, and today's behaviour) the groups are revealed progressively, one at a time as
-- the operator narrows down; set, every configured group is offered right away.

-- IDs allocated from idserver.metas.de on 2026-08-11:
--   AD_Element    585163 (new IsShowAllFilterGroups label for MobileUI_UserProfile_Picking)
--   AD_Column     593131 (MobileUI_UserProfile_Picking.IsShowAllFilterGroups, default 'N', NOT NULL)
--   AD_Field      781902 (picking-profile window 541743 / tab 547258)
--   AD_UI_Element 652815 (header, group 551252 "flags", SeqNo 200)

-- Element: IsShowAllFilterGroups
-- 2026-08-11T08:40:30.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585163 /*From ID Server*/,0,'IsShowAllFilterGroups',TO_TIMESTAMP('2026-08-11 08:40:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Alle Filtergruppen gleichzeitig anzeigen','Alle Filtergruppen gleichzeitig anzeigen',TO_TIMESTAMP('2026-08-11 08:40:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-11T08:40:30.100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585163 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsShowAllFilterGroups (de_CH)
-- 2026-08-11T08:40:31.000Z
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2026-08-11 08:40:31.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585163 AND AD_Language='de_CH'
;

-- 2026-08-11T08:40:31.010Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585163,'de_CH')
;

-- 2026-08-11T08:40:31.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585163,'de_CH')
;

-- Element: IsShowAllFilterGroups (de_DE, base language)
-- 2026-08-11T08:40:32.000Z
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2026-08-11 08:40:32.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585163 AND AD_Language='de_DE'
;

-- 2026-08-11T08:40:32.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585163,'de_DE')
;

-- Element: IsShowAllFilterGroups (en_US)
-- 2026-08-11T08:40:33.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Show all filter groups at once', PrintName='Show all filter groups at once',Updated=TO_TIMESTAMP('2026-08-11 08:40:33.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585163 AND AD_Language='en_US'
;

-- 2026-08-11T08:40:33.010Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-08-11T08:40:33.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585163,'en_US')
;

-- Column: MobileUI_UserProfile_Picking.IsShowAllFilterGroups
-- 2026-08-11T08:40:34.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version) VALUES (0,593131 /*From ID Server*/,585163,0,20,542373,'XX','IsShowAllFilterGroups',TO_TIMESTAMP('2026-08-11 08:40:34.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Alle Filtergruppen gleichzeitig anzeigen',0,0,TO_TIMESTAMP('2026-08-11 08:40:34.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'NP',0)
;

-- 2026-08-11T08:40:34.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593131 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-08-11T08:40:34.200Z
/* DDL */  select update_Column_Translation_From_AD_Element(585163)
;

-- 2026-08-11T08:40:35.000Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsShowAllFilterGroups CHAR(1) DEFAULT ''N'' CHECK (IsShowAllFilterGroups IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Alle Filtergruppen gleichzeitig anzeigen
-- Column: MobileUI_UserProfile_Picking.IsShowAllFilterGroups
-- 2026-08-11T08:40:36.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593131,781902 /*From ID Server*/,0,547258,TO_TIMESTAMP('2026-08-11 08:40:36.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Alle Filtergruppen gleichzeitig anzeigen',TO_TIMESTAMP('2026-08-11 08:40:36.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-11T08:40:36.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781902 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-08-11T08:40:36.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585163)
;

-- 2026-08-11T08:40:36.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781902
;

-- 2026-08-11T08:40:36.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781902)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Alle Filtergruppen gleichzeitig anzeigen
-- Column: MobileUI_UserProfile_Picking.IsShowAllFilterGroups
-- 2026-08-11T08:40:37.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781902,0,547258,551252,652815 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-11 08:40:37.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Alle Filtergruppen gleichzeitig anzeigen',200,0,0,TO_TIMESTAMP('2026-08-11 08:40:37.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
