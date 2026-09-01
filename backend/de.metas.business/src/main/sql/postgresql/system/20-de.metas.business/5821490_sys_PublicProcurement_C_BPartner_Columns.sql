-- New C_BPartner columns for French public-procurement invoice references: a service code and
-- a State SIRET, captured once on the business partner (their natural source, stable per
-- public-sector customer per REQUIREMENTS.md assumption A2) so they can be read through to the
-- invoice by a later migration's virtual columns, without re-entry at invoicing time.
--
-- Columns are portal-neutral by design (PublicProcurement*/PublicAuthority*, not tied to any one
-- submission platform) so they survive a change of platform. Both are plain VARCHAR, not
-- mandatory (only relevant for public-sector business partners subject to these statutory
-- references).
--
-- Not to be confused with C_BPartner.CommercialRegisterNumber (VARCHAR(60), label FirmenbuchNR,
-- added by 5643560_sys_add_FirmenbuchNR_to_Business_Partner_Window.sql) which is reused as-is
-- for the invoiced body's OWN SIRET and is NOT touched by this script.
--
-- Known glossary gap: the metasfresh terminology glossary has no entry for the French statutory
-- terms behind these two references, so no German neologism is coined here. Each AD_Element's
-- BASE-language Name/PrintName is the FRENCH STATUTORY TERM exactly as it is printed on the
-- customer's reference invoice ('CODE SERVICE' -> 'Code service', 'SIRET ETAT' -> 'SIRET État')
-- -- grounded in that document, not invented -- and en_US carries the English caption, marked
-- translated. This is a deliberate, documented deviation from the usual German-base convention,
-- and it is what the approved plan specifies. de_DE/de_CH are left IsTranslated='N' and therefore
-- fall back to the French base text; the German wording is a terminology question for a human.
--
-- AD_Table_ID for C_BPartner = 291 (verified via psql against the local stack).
-- EntityType='D' (core dictionary metadata; C_BPartner is a core table).
-- PersonalDataCategory='NP' for both -- administrative references to a public contract/market
-- and a French State body, not personal data about a natural person.
--
-- IDs allocated from the central ID server:
--   AD_MigrationScript 5821490  (this script's prefix)
--   AD_Element  585397  (PublicProcurementServiceCode)
--   AD_Column   593445  (C_BPartner.PublicProcurementServiceCode)
--   AD_Element  585398  (PublicAuthoritySiret)
--   AD_Column   593446  (C_BPartner.PublicAuthoritySiret)
--
-- Do NOT touch: any existing migration file; C_Order; C_Invoice; CommercialRegisterNumber;
-- any window, tab or field row (those belong to later, separate tasks).

-- ============================================================
-- Column 1: C_BPartner.PublicProcurementServiceCode
-- ============================================================

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585397 /*From ID Server*/,0,'PublicProcurementServiceCode',TO_TIMESTAMP('2026-09-01 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Code service','Code service',TO_TIMESTAMP('2026-09-01 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585397
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- English is the deliberately-translated language here (base column already carries the French
-- statutory term); mark it translated. German is intentionally left IsTranslated='N' (falls back
-- to the French base text) -- see header comment.
UPDATE AD_Element_Trl SET Name='Public Procurement Service Code', PrintName='Public Procurement Service Code', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 11:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585397 AND AD_Language='en_US'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585397,'en_US')
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593445 /*From ID Server*/,585397,0,10,291,'PublicProcurementServiceCode',TO_TIMESTAMP('2026-09-01 11:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,40,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Code service','NP',0,0,TO_TIMESTAMP('2026-09-01 11:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593445
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585397)
;

/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN PublicProcurementServiceCode VARCHAR(40)')
;

-- ============================================================
-- Column 2: C_BPartner.PublicAuthoritySiret
-- ============================================================

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585398 /*From ID Server*/,0,'PublicAuthoritySiret',TO_TIMESTAMP('2026-09-01 11:02:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SIRET État','SIRET État',TO_TIMESTAMP('2026-09-01 11:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585398
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- English is the deliberately-translated language here (base column already carries the French
-- statutory term); mark it translated. German is intentionally left IsTranslated='N' (falls back
-- to the French base text) -- see header comment.
UPDATE AD_Element_Trl SET Name='Public Authority SIRET', PrintName='Public Authority SIRET', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 11:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585398 AND AD_Language='en_US'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585398,'en_US')
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593446 /*From ID Server*/,585398,0,10,291,'PublicAuthoritySiret',TO_TIMESTAMP('2026-09-01 11:03:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SIRET État','NP',0,0,TO_TIMESTAMP('2026-09-01 11:03:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593446
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585398)
;

/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN PublicAuthoritySiret VARCHAR(20)')
;
