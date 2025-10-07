-- Run mode: SWING_CLIENT

-- 2025-10-06T09:09:53.730Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584086,0,'IsAllowedToChangeForeignPassword',TO_TIMESTAMP('2025-10-06 09:09:53.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Allow Change Foreign Password','Allow Change Foreign Password',TO_TIMESTAMP('2025-10-06 09:09:53.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T09:09:53.739Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584086 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-06T09:21:10.757Z
UPDATE AD_Element SET ColumnName='IsAllowPasswordChangeForOthers',Updated=TO_TIMESTAMP('2025-10-06 09:21:10.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584086
;

-- 2025-10-06T09:21:10.758Z
UPDATE AD_Column SET ColumnName='IsAllowPasswordChangeForOthers' WHERE AD_Element_ID=584086
;

-- 2025-10-06T09:21:10.759Z
UPDATE AD_Process_Para SET ColumnName='IsAllowPasswordChangeForOthers' WHERE AD_Element_ID=584086
;

-- 2025-10-06T09:21:10.788Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584086,'de_DE')
;

-- Element: IsAllowPasswordChangeForOthers
-- 2025-10-06T09:23:40.416Z
UPDATE AD_Element_Trl SET Description='Allows the role to change the password of any user without requiring the current password. This overrides standard password change behavior and should be used with care.', Help='Allows the role to change the password of any user without requiring the current password.
This overrides standard password change behavior and should be used with care.', Name='Allow Password Change For Others', PrintName='Allow Password Change For Others',Updated=TO_TIMESTAMP('2025-10-06 09:23:40.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584086 AND AD_Language='en_US'
;

-- 2025-10-06T09:23:40.417Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:23:40.629Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584086,'en_US')
;

-- Element: IsAllowPasswordChangeForOthers
-- 2025-10-06T09:23:54.461Z
UPDATE AD_Element_Trl SET Description='Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen. Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.', Help='Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen.
Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.', IsTranslated='Y', Name='Passwortänderung für andere erlauben', PrintName='Passwortänderung für andere erlauben',Updated=TO_TIMESTAMP('2025-10-06 09:23:54.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584086 AND AD_Language='de_DE'
;

-- 2025-10-06T09:23:54.462Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:23:55.240Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584086,'de_DE')
;

-- 2025-10-06T09:23:55.241Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584086,'de_DE')
;

-- Element: IsAllowPasswordChangeForOthers
-- 2025-10-06T09:24:16.775Z
UPDATE AD_Element_Trl SET Description='Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen. Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.', Help='Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen.
Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.', IsTranslated='Y', Name='Passwortänderung für andere erlauben', PrintName='Passwortänderung für andere erlauben',Updated=TO_TIMESTAMP('2025-10-06 09:24:16.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584086 AND AD_Language='de_CH'
;

-- 2025-10-06T09:24:16.776Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-06T09:24:16.995Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584086,'de_CH')
;

-- Column: AD_Role.IsAllowPasswordChangeForOthers
-- 2025-10-06T12:22:18.076Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591277,584086,0,20,156,'XX','IsAllowPasswordChangeForOthers',TO_TIMESTAMP('2025-10-06 12:22:17.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen. Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.','D',0,1,'Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen.
Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Passwortänderung für andere erlauben',0,0,TO_TIMESTAMP('2025-10-06 12:22:17.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-06T12:22:18.082Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591277 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-06T12:22:18.093Z
/* DDL */  select update_Column_Translation_From_AD_Element(584086)
;

-- 2025-10-06T12:22:18.896Z
/* DDL */ SELECT public.db_alter_table('AD_Role','ALTER TABLE public.AD_Role ADD COLUMN IsAllowPasswordChangeForOthers CHAR(1) DEFAULT ''N'' CHECK (IsAllowPasswordChangeForOthers IN (''Y'',''N'')) NOT NULL')
;

-- Field: Rolle - Verwaltung(111,D) -> Rolle(119,D) -> Passwortänderung für andere erlauben
-- Column: AD_Role.IsAllowPasswordChangeForOthers
-- 2025-10-06T12:23:53.154Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591277,754778,0,119,TO_TIMESTAMP('2025-10-06 12:23:52.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen. Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.',1,'D','Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen.
Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.','Y','N','N','N','N','N','N','N','Passwortänderung für andere erlauben',TO_TIMESTAMP('2025-10-06 12:23:52.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-06T12:23:53.156Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754778 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-06T12:23:53.161Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584086)
;

-- 2025-10-06T12:23:53.167Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754778
;

-- 2025-10-06T12:23:53.177Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754778)
;

-- UI Element: Rolle - Verwaltung(111,D) -> Rolle(119,D) -> advanced edit -> 10 -> advanced edit.Passwortänderung für andere erlauben
-- Column: AD_Role.IsAllowPasswordChangeForOthers
-- 2025-10-06T12:24:57.173Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754778,0,119,540229,637723,'F',TO_TIMESTAMP('2025-10-06 12:24:57.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen. Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.','Ermöglicht dieser Rolle, das Passwort eines beliebigen Benutzers zu ändern, ohne das aktuelle Passwort zu kennen.
Diese Einstellung überschreibt das Standardverhalten beim Passwortwechsel und sollte mit Vorsicht verwendet werden.','Y','N','Y','N','N','Passwortänderung für andere erlauben',620,0,0,TO_TIMESTAMP('2025-10-06 12:24:57.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Rolle - Verwaltung(111,D) -> Rolle(119,D) -> advanced edit -> 10 -> advanced edit.Passwortänderung für andere erlauben
-- Column: AD_Role.IsAllowPasswordChangeForOthers
-- 2025-10-06T12:25:16.510Z
UPDATE AD_UI_Element SET SeqNo=485,Updated=TO_TIMESTAMP('2025-10-06 12:25:16.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637723
;

