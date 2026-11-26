-- Run mode: SWING_CLIENT

-- IDs allocated from idserver.metas.de on 2026-07-22:
--   AD_Element    585122 (new IsShowQtyAvailableForLines label for MobileUI_UserProfile_Picking)
--   AD_Column     592976 (MobileUI_UserProfile_Picking.IsShowQtyAvailableForLines, default 'N', NOT NULL)
--   AD_Field      781765 (picking-profile window 541743 / tab 547258)
--   AD_UI_Element 652697 (header, group 551252 "flags", SeqNo 190)

-- Element: IsShowQtyAvailableForLines
-- 2026-07-22T12:12:30.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585122 /*From ID Server*/,0,'IsShowQtyAvailableForLines',TO_TIMESTAMP('2026-07-22 12:12:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Verfügbaren Bestand pro Position anzeigen','Verfügbaren Bestand pro Position anzeigen',TO_TIMESTAMP('2026-07-22 12:12:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-22T12:12:30.100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585122 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsShowQtyAvailableForLines (de_CH)
-- 2026-07-22T12:12:31.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-22 12:12:31.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585122 AND AD_Language='de_CH'
;

-- 2026-07-22T12:12:31.010Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585122,'de_CH')
;

-- 2026-07-22T12:12:31.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585122,'de_CH')
;

-- Element: IsShowQtyAvailableForLines (de_DE, base language)
-- 2026-07-22T12:12:32.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-22 12:12:32.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585122 AND AD_Language='de_DE'
;

-- 2026-07-22T12:12:32.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585122,'de_DE')
;

-- Element: IsShowQtyAvailableForLines (en_US)
-- 2026-07-22T12:12:33.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Show available qty per line', PrintName='Show available qty per line',Updated=TO_TIMESTAMP('2026-07-22 12:12:33.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585122 AND AD_Language='en_US'
;

-- 2026-07-22T12:12:33.010Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-07-22T12:12:33.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585122,'en_US')
;

-- Column: MobileUI_UserProfile_Picking.IsShowQtyAvailableForLines
-- 2026-07-22T12:12:34.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version) VALUES (0,592976 /*From ID Server*/,585122,0,20,542373,'XX','IsShowQtyAvailableForLines',TO_TIMESTAMP('2026-07-22 12:12:34.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verfügbaren Bestand pro Position anzeigen',0,0,TO_TIMESTAMP('2026-07-22 12:12:34.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'NP',0)
;

-- 2026-07-22T12:12:34.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592976 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-07-22T12:12:34.200Z
/* DDL */  select update_Column_Translation_From_AD_Element(585122)
;

-- 2026-07-22T12:12:35.000Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsShowQtyAvailableForLines CHAR(1) DEFAULT ''N'' CHECK (IsShowQtyAvailableForLines IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Verfügbaren Bestand pro Position anzeigen
-- Column: MobileUI_UserProfile_Picking.IsShowQtyAvailableForLines
-- 2026-07-22T12:12:36.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592976,781765 /*From ID Server*/,0,547258,TO_TIMESTAMP('2026-07-22 12:12:36.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Verfügbaren Bestand pro Position anzeigen',TO_TIMESTAMP('2026-07-22 12:12:36.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-22T12:12:36.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781765 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-07-22T12:12:36.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585122)
;

-- 2026-07-22T12:12:36.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781765
;

-- 2026-07-22T12:12:36.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781765)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Verfügbaren Bestand pro Position anzeigen
-- Column: MobileUI_UserProfile_Picking.IsShowQtyAvailableForLines
-- 2026-07-22T12:12:37.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781765,0,547258,551252,652697 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-22 12:12:37.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Verfügbaren Bestand pro Position anzeigen',190,0,0,TO_TIMESTAMP('2026-07-22 12:12:37.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
