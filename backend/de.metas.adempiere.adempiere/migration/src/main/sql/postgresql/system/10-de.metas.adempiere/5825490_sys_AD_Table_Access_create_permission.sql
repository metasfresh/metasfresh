-- 2026-09-21
-- AD_Table_Access.IsCanCreateNewRecords — the per-table "may create new records" permission.
--
-- Introduced directly in its FINAL shape: a NOT NULL Yes-No (AD_Reference_ID=20) column with a
-- non-restricting DB default ('Y') and a CHECK ('Y','N') constraint. It SUBTRACTS the CREATE access
-- from the role's default access set: IsCanCreateNewRecords='N' removes CREATE, 'Y' removes nothing,
-- so a row left at its default is indistinguishable from no row at all — the same shape as the other
-- three flags (IsReadOnly / IsCanReport / IsCanExport). There is no nullable / three-state stage:
-- AD_Table_Access carries no third state on any column.
-- AD_Column.IsMandatory matches the physical NOT NULL — both on.
--
-- The Description (rewritten in step "§3.2c" below) carries what a role administrator needs to know,
-- because the WebUI renders Description but never Help. CUSTOMER-VISIBLE WORDING — first draft, flag
-- for human review before UAT.
--
-- IDs allocated from idserver.metas.de on 2026-09-21:
--   AD_Element 585478 (IsCanCreateNewRecords)
--   AD_Column  593636 (AD_Table_Access.IsCanCreateNewRecords)

-- Element: IsCanCreateNewRecords — German in the base columns, en_US as the translation override.
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Description,Updated,UpdatedBy)
VALUES (0,585478 /*From ID Server*/,0,'IsCanCreateNewRecords',TO_TIMESTAMP('2026-09-21 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
        'Neue Datensätze anlegen',
        'Neue Datensätze anlegen',
        'Legt fest, ob diese Rolle in dieser Tabelle neue Datensätze anlegen darf. Nicht gesetzt = keine Einschränkung durch die Rolle.',
        TO_TIMESTAMP('2026-09-21 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Seed one _Trl row per active system language from the base row (fr_CH included — it stays
-- IsTranslated='N' and falls back to the German base text; fr_CH is out of this change's scope).
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585478
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsCanCreateNewRecords (de_CH) — Swiss German uses the same wording as de_DE.
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name='Neue Datensätze anlegen',
    PrintName='Neue Datensätze anlegen',
    Description='Legt fest, ob diese Rolle in dieser Tabelle neue Datensätze anlegen darf. Nicht gesetzt = keine Einschränkung durch die Rolle.',
    Updated=TO_TIMESTAMP('2026-09-21 10:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585478 AND AD_Language='de_CH'
;

/* DDL */  select update_ad_element_on_ad_element_trl_update(585478,'de_CH')
;

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585478,'de_CH')
;

-- Element: IsCanCreateNewRecords (de_DE, base language)
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name='Neue Datensätze anlegen',
    PrintName='Neue Datensätze anlegen',
    Description='Legt fest, ob diese Rolle in dieser Tabelle neue Datensätze anlegen darf. Nicht gesetzt = keine Einschränkung durch die Rolle.',
    Updated=TO_TIMESTAMP('2026-09-21 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585478 AND AD_Language='de_DE'
;

/* DDL */  select update_ad_element_on_ad_element_trl_update(585478,'de_DE')
;

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585478,'de_DE')
;

-- Element: IsCanCreateNewRecords (en_US)
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name='Create new records',
    PrintName='Create new records',
    Description='Controls whether this role may create new records in this table. Not set = no restriction from this role.',
    Updated=TO_TIMESTAMP('2026-09-21 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585478 AND AD_Language='en_US'
;

/* DDL */  select update_ad_element_on_ad_element_trl_update(585478,'en_US')
;

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585478,'en_US')
;

-- Column: AD_Table_Access.IsCanCreateNewRecords
-- AD_Table_ID=565 (AD_Table_Access), AD_Reference_ID=20 (Yes-No), mandatory, DB default 'Y'
-- (non-restricting). Introduced final — no nullable List/_YesNo stage.
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,Description,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version)
VALUES (0,593636 /*From ID Server*/,585478,0,20,NULL,565,'XX','IsCanCreateNewRecords',TO_TIMESTAMP('2026-09-21 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,
        'Neue Datensätze anlegen',
        'Legt fest, ob diese Rolle in dieser Tabelle neue Datensätze anlegen darf. Nicht gesetzt = keine Einschränkung durch die Rolle.',
        0,0,TO_TIMESTAMP('2026-09-21 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'NP',0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593636
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */  select update_Column_Translation_From_AD_Element(585478)
;

-- Physical column: NOT NULL CHAR(1) DEFAULT 'Y' with a CHECK ('Y','N') — the final Yes-No shape.
/* DDL */ SELECT public.db_alter_table('AD_Table_Access','ALTER TABLE public.AD_Table_Access ADD COLUMN IsCanCreateNewRecords CHAR(1) NOT NULL DEFAULT ''Y''')
;
/* DDL */ SELECT public.db_alter_table('AD_Table_Access','ALTER TABLE public.AD_Table_Access ADD CONSTRAINT ad_table_access_iscancreatenewrecords_check CHECK (IsCanCreateNewRecords IN (''Y'',''N''))')
;

-- =================================================================================================
-- §3.2c — rewrite the Description to the final wording (per REQUIREMENTS.md AC16 as amended). The
-- earlier "Nicht gesetzt = keine Einschränkung" text described a third state that this column never
-- has; the final wording states what the setting GUARANTEES (desktop-WebUI-scoped) and carries the
-- AC16 role-inclusion-chain warning. Own element (585478), not shared — mutated directly here.
-- de_DE/de_CH/en_US only; fr_CH keeps the seeded German base text (IsTranslated='N'), authoring
-- French is outside this task. Later timestamps than the seed above so the propagation guard fires.
-- =================================================================================================
UPDATE AD_Element_Trl
   SET Description = 'Bei ''Nein'' bietet die Desktop-WebUI für diese Rolle in dieser Tabelle das Anlegen neuer Datensätze nicht an und akzeptiert es nicht. Achtung: Ein Datensatz, der einer eingeschlossenen Rolle aus einem anderen Grund hinzugefügt wird, hat hier standardmäßig den Wert ''Ja'' und kann so in einer Rollen-Einschlusskette die Einschränkung einer anderen Rolle aufheben.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-22 16:02:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Element_ID=585478 AND AD_Language='de_DE';

-- de_CH follows the Swiss convention (ss, never ß): "standardmässig".
UPDATE AD_Element_Trl
   SET Description = 'Bei ''Nein'' bietet die Desktop-WebUI für diese Rolle in dieser Tabelle das Anlegen neuer Datensätze nicht an und akzeptiert es nicht. Achtung: Ein Datensatz, der einer eingeschlossenen Rolle aus einem anderen Grund hinzugefügt wird, hat hier standardmässig den Wert ''Ja'' und kann so in einer Rollen-Einschlusskette die Einschränkung einer anderen Rolle aufheben.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-22 16:02:05','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Element_ID=585478 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
   SET Description = 'When set to No, the desktop WebUI will not offer or accept creating a new record for this role in this table. Warning: a row added to an included role for an unrelated reason defaults to Yes here and can, in a role-inclusion chain, lift another role''s restriction.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-22 16:02:10','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Element_ID=585478 AND AD_Language='en_US';

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585478, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585478, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585478, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585478, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585478, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585478, 'en_US');
