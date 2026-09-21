-- 2026-09-21
-- AD_Table_Access.IsCanCreateNewRecords — the per-table "may create new records" permission.
--
-- Same three-state shape as the four flags converted in 5825480: a nullable CHAR(1) column with
-- no DB default and no CHECK constraint, exposed as AD_Reference_ID=17 (List) over
-- AD_Reference_Value_ID=319 (_YesNo).
--   NULL      = the role expresses no opinion — no restriction from this role
--   'Y' / 'N' = the opinion was set explicitly
-- No CHECK constraint is added: PO.set_Value validates a List column's value against its
-- AD_Ref_List on save, so the two-state invariant is already enforced on the application path.
-- AD_Column.IsMandatory must always match the physical NOT NULL — both stay off here.
--
-- The Description carries what a role administrator needs to know, because the WebUI renders
-- Description but never Help.
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
-- AD_Table_ID=565 (AD_Table_Access), AD_Reference_ID=17 (List) over AD_Reference_Value_ID=319 (_YesNo).
-- DefaultValue stays NULL: a default would give every new row an opinion, which is exactly what
-- the third state exists to avoid.
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,Description,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version)
VALUES (0,593636 /*From ID Server*/,585478,0,17,319,565,'XX','IsCanCreateNewRecords',TO_TIMESTAMP('2026-09-21 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'N',NULL,'D',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,
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

-- Physical column: nullable CHAR(1), no default, no CHECK — the three-state shape.
/* DDL */ SELECT public.db_alter_table('AD_Table_Access','ALTER TABLE public.AD_Table_Access ADD COLUMN IsCanCreateNewRecords CHAR(1)')
;
