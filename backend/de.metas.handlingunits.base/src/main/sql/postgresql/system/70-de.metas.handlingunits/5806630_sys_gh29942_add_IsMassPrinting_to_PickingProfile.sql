-- Run mode: SWING_CLIENT
-- IDs allocated from idserver.metas.de on 2026-06-07:
--   AD_Element   584952  (IsMassPrinting — Mass Printing profile option)
--   AD_Column    592732  (MobileUI_UserProfile_Picking.IsMassPrinting)
--   AD_Field     780726  (tab 547258 — Mobile UI Kommissionierprofil)
--   AD_UI_Element 652021 (group 551252 — flags, SeqNo 170)

-- AD_Element: IsMassPrinting
-- 2026-06-07T10:00:00Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584952 /*From ID Server*/,0,'IsMassPrinting',TO_TIMESTAMP('2026-06-07 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Mass Printing','Mass Printing',TO_TIMESTAMP('2026-06-07 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584952
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsMassPrinting — de_DE translation (base language) — 2026-06-07T10:00:01Z
UPDATE AD_Element_Trl SET Name='Massendruck', PrintName='Massendruck', Description='Wenn aktiviert, kann der Benutzer Massendrucketiketten für selbst verpackte Produkte erstellen.', Help='Wenn aktiviert, kann der Benutzer Massendrucketiketten für selbst verpackte Produkte erstellen.',Updated=TO_TIMESTAMP('2026-06-07 10:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584952 AND AD_Language='de_DE';
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage();
/* DDL */  select update_ad_element_on_ad_element_trl_update(584952,'de_DE');
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584952,'de_DE');

-- Element: IsMassPrinting — de_CH translation — 2026-06-07T10:00:02Z
UPDATE AD_Element_Trl SET Name='Massendruck', PrintName='Massendruck', Description='Wenn aktiviert, kann der Benutzer Massendrucketiketten für selbst verpackte Produkte erstellen.', Help='Wenn aktiviert, kann der Benutzer Massendrucketiketten für selbst verpackte Produkte erstellen.',Updated=TO_TIMESTAMP('2026-06-07 10:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584952 AND AD_Language='de_CH';
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage();
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584952,'de_CH');

-- Element: IsMassPrinting — en_US translation — 2026-06-07T10:00:03Z
UPDATE AD_Element_Trl SET Name='Mass Printing', PrintName='Mass Printing', Description='When enabled, the user can perform mass-printing of shipping labels for self-packed products by scanning one LU.', Help='When enabled, the user can perform mass-printing of shipping labels for self-packed products by scanning one LU.',Updated=TO_TIMESTAMP('2026-06-07 10:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584952 AND AD_Language='en_US';
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage();
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584952,'en_US');

-- Column: MobileUI_UserProfile_Picking.IsMassPrinting
-- AD_Table_ID=542373
-- 2026-06-07T10:00:04Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592732 /*From ID Server*/,584952 /*From ID Server*/,0,20,542373,'XX','IsMassPrinting',TO_TIMESTAMP('2026-06-07 10:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Massendruck','NP',0,0,TO_TIMESTAMP('2026-06-07 10:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592732
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */  select update_Column_Translation_From_AD_Element(584952);

-- DDL: add physical column
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IF NOT EXISTS IsMassPrinting CHAR(1) DEFAULT ''N'' CHECK (IsMassPrinting IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Massendruck
-- Column: MobileUI_UserProfile_Picking.IsMassPrinting
-- 2026-06-07T10:00:05Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592732,780726 /*From ID Server*/,0,547258,TO_TIMESTAMP('2026-06-07 10:00:05.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Massendruck',TO_TIMESTAMP('2026-06-07 10:00:05.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780726
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584952);

DELETE FROM AD_Element_Link WHERE AD_Field_ID=780726;
/* DDL */ select AD_Element_Link_Create_Missing_Field(780726);

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Massendruck
-- Column: MobileUI_UserProfile_Picking.IsMassPrinting
-- 2026-06-07T10:00:06Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,780726,0,547258,652021 /*From ID Server*/,551252,'F',TO_TIMESTAMP('2026-06-07 10:00:06.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Massendruck',170,0,0,TO_TIMESTAMP('2026-06-07 10:00:06.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

