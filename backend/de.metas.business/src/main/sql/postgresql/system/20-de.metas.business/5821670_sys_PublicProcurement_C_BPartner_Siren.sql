-- New C_BPartner column for French public-procurement invoice references: the business
-- partner's OWN SIREN, the French legal-entity identifier (9 digits). Optional
-- (IsMandatory='N') -- it only applies to French partners.
--
-- Why SIREN is a NEW column but SIRET is NOT: an earlier version of this task also added a
-- Siret column -- that was cancelled by the human. There is NO Siret column here. The
-- partner's own SIRET already lives in the EXISTING C_BPartner.CommercialRegisterNumber
-- (AD_Column 583367, VARCHAR(60), AD_Element 581038, label FirmenbuchNR), which is reused
-- as-is; its French translation is handled by another, already-written script. This script
-- adds Siren and nothing else -- it does NOT modify, re-label, resize, or drop
-- CommercialRegisterNumber or AD_Element 581038.
--
-- Column is VARCHAR(20): a SIREN is a fixed 9 digits, but 20 chars leaves room for the usual
-- grouping separators (e.g. '123 456 789', 11 chars) while still rejecting input that cannot
-- be a SIREN at all -- same sizing rationale as the sibling PublicAuthoritySiret column
-- (5821490_sys_PublicProcurement_C_BPartner_Columns.sql).
--
-- Captions: SIREN is a French statutory acronym, written identically in every language, so
-- the base-language (de_DE) Name/PrintName is 'SIREN' itself -- no German neologism is
-- coined. en_US, fr_FR and fr_CH are marked IsTranslated='Y' with the same 'SIREN' text
-- (safe here precisely because the acronym is language-neutral). de_DE/de_CH are left
-- IsTranslated='N' (they read 'SIREN' from the base column, which is correct).
--
-- AD_Table_ID for C_BPartner = 291 (verified via psql against the local stack: `SELECT
-- ad_table_id FROM ad_table WHERE tablename='C_BPartner';` -> 291).
-- EntityType='D' (core dictionary metadata; C_BPartner is a core table).
-- PersonalDataCategory='P' -- matches the existing analogous CommercialRegisterNumber column
-- (AD_Column 583367) on the same table: C_BPartner rows can represent a natural person /
-- sole trader, whose own SIREN directly ties the business identity to that person.
--
-- IDs allocated from the central ID server:
--   AD_MigrationScript 5821670  (this script's prefix)
--   AD_Element  585403  (Siren)
--   AD_Column   593462  (C_BPartner.Siren)
--
-- Do NOT touch: any existing migration file; C_Order; C_Invoice; CommercialRegisterNumber;
-- AD_Element 581038; any window, tab or field row (window placement is a separate task).

-- ============================================================
-- Column: C_BPartner.Siren
-- ============================================================

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585403 /*From ID Server*/,0,'Siren',TO_TIMESTAMP('2026-09-01 13:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SIREN','SIREN',TO_TIMESTAMP('2026-09-01 13:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585403
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- SIREN is a language-neutral French statutory acronym: en_US, fr_FR and fr_CH all carry the
-- same text as the base column, marked translated. de_DE/de_CH intentionally stay
-- IsTranslated='N' (no German coinage -- they fall back to the 'SIREN' base text, which is
-- correct here).
UPDATE AD_Element_Trl SET Name='SIREN', PrintName='SIREN', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 13:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585403 AND AD_Language='en_US'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585403,'en_US')
;

UPDATE AD_Element_Trl SET Name='SIREN', PrintName='SIREN', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 13:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585403 AND AD_Language='fr_FR'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585403,'fr_FR')
;

UPDATE AD_Element_Trl SET Name='SIREN', PrintName='SIREN', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 13:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585403 AND AD_Language='fr_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585403,'fr_CH')
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593462 /*From ID Server*/,585403,0,10,291,'Siren',TO_TIMESTAMP('2026-09-01 13:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SIREN','P',0,0,TO_TIMESTAMP('2026-09-01 13:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593462
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585403)
;

/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Siren VARCHAR(20)')
;
