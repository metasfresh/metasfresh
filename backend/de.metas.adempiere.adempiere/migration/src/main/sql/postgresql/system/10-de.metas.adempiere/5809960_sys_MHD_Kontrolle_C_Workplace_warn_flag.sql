-- Run mode: SWING_CLIENT
--
-- IDs allocated from idserver.metas.de on 2026-06-29:
--   AD_MigrationScript  5809960  (filename prefix)
--   AD_Element          585073   (IsWarnShelfLifeUndercut)
--   AD_Column           592916   (C_Workplace.IsWarnShelfLifeUndercut)
--   AD_Field            781317   (Workplace window tab 547260)
--   AD_UI_Element       652424   (flags group 551258)
--   AD_Message          545767   (RLZ_TooShort)

-- Element: IsWarnShelfLifeUndercut
-- 2026-06-29T10:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585073 /*From ID Server*/,0,'IsWarnShelfLifeUndercut',TO_TIMESTAMP('2026-06-29 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Warnung wenn Restlaufzeit Vorgabe unterschritten','Warnung wenn Restlaufzeit Vorgabe unterschritten',TO_TIMESTAMP('2026-06-29 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-29T10:00:01.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb,
'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585073
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsWarnShelfLifeUndercut - en_US translation
-- 2026-06-29T10:00:12.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Warn when guaranteed remaining shelf life is undercut', PrintName='Warn when guaranteed remaining shelf life is undercut',
Updated=TO_TIMESTAMP('2026-06-29 10:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585073 AND AD_Language='en_US'
;

-- 2026-06-29T10:00:13.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585073,'en_US')
;

-- Element: IsWarnShelfLifeUndercut - de_DE translation
-- 2026-06-29T10:00:18.000Z
UPDATE AD_Element_Trl SET IsTranslated='N', Name='Warnung wenn Restlaufzeit Vorgabe unterschritten', PrintName='Warnung wenn Restlaufzeit Vorgabe unterschritten',
Updated=TO_TIMESTAMP('2026-06-29 10:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585073 AND AD_Language='de_DE'
;

-- 2026-06-29T10:00:19.000Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-29T10:00:20.000Z
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585073,'de_DE')
;

-- 2026-06-29T10:00:21.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585073,'de_DE')
;

-- Element: IsWarnShelfLifeUndercut - de_CH translation
-- 2026-06-29T10:00:26.000Z
UPDATE AD_Element_Trl SET IsTranslated='N', Name='Warnung wenn Restlaufzeit Vorgabe unterschritten', PrintName='Warnung wenn Restlaufzeit Vorgabe unterschritten',
Updated=TO_TIMESTAMP('2026-06-29 10:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585073 AND AD_Language='de_CH'
;

-- 2026-06-29T10:00:27.000Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-29T10:00:28.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585073,'de_CH')
;

-- Column: C_Workplace.IsWarnShelfLifeUndercut
-- 2026-06-29T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592916 /*From ID Server*/,585073,0,20,542375,'XX','IsWarnShelfLifeUndercut',TO_TIMESTAMP('2026-06-29 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Warnung wenn Restlaufzeit Vorgabe unterschritten','NP',0,0,TO_TIMESTAMP('2026-06-29 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-29T10:01:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592916
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-29T10:01:02.000Z
/* DDL */ SELECT update_Column_Translation_From_AD_Element(585073)
;

-- DDL: add the physical column to the table
-- 2026-06-29T10:01:10.000Z
/* DDL */ SELECT public.db_alter_table('C_Workplace','ALTER TABLE public.C_Workplace ADD COLUMN IF NOT EXISTS IsWarnShelfLifeUndercut CHAR(1) DEFAULT ''N'' NOT NULL')
;

-- Field: Arbeitsplatz(541744,D) -> Workplace(547260,D) -> IsWarnShelfLifeUndercut
-- 2026-06-29T10:02:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592916,781317 /*From ID Server*/,0,547260,TO_TIMESTAMP('2026-06-29 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Warnung wenn Restlaufzeit Vorgabe unterschritten',TO_TIMESTAMP('2026-06-29 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-29T10:02:01.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781317
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:02:02.000Z
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585073)
;

-- 2026-06-29T10:02:03.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781317
;

-- 2026-06-29T10:02:04.000Z
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781317)
;

-- UI Element: Workplace(547260) -> flags group(551258) -> IsWarnShelfLifeUndercut
-- Placement: right column, flags group, SeqNo=20 (after IsActive at SeqNo=10)
-- 2026-06-29T10:02:30.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781317,0,547260,551258,652424 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-29 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Warnung wenn Restlaufzeit Vorgabe unterschritten',20,0,0,TO_TIMESTAMP('2026-06-29 10:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_Message: RLZ_TooShort
-- 2026-06-29T10:03:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545767 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-29 10:03:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','RLZ zu kurz!','E','RLZ_TooShort',TO_TIMESTAMP('2026-06-29 10:03:00','YYYY-MM-DD HH24:MI:SS'),100,'RLZ_TooShort')
;

-- 2026-06-29T10:03:01.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545767
AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- AD_Message_Trl: de_DE
-- 2026-06-29T10:03:12.000Z
UPDATE AD_Message_Trl SET MsgText='RLZ zu kurz!', IsTranslated='N', Updated=TO_TIMESTAMP('2026-06-29 10:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Message_ID=545767 AND AD_Language='de_DE'
;

-- AD_Message_Trl: de_CH
-- 2026-06-29T10:03:13.000Z
UPDATE AD_Message_Trl SET MsgText='RLZ zu kurz!', IsTranslated='N', Updated=TO_TIMESTAMP('2026-06-29 10:03:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Message_ID=545767 AND AD_Language='de_CH'
;

-- AD_Message_Trl: en_US
-- 2026-06-29T10:03:14.000Z
UPDATE AD_Message_Trl SET MsgText='Remaining shelf life too short!', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-29 10:03:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Message_ID=545767 AND AD_Language='en_US'
;
