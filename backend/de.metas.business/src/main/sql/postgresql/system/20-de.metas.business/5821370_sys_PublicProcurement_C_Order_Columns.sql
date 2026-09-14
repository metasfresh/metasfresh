-- New C_Order columns for French public-procurement invoice references: a market number and
-- a commitment number, captured once on the order (their natural source) so they can be read
-- through to the invoice by a later migration's virtual columns, without re-entry at invoicing
-- time.
--
-- Columns are portal-neutral by design (PublicProcurement*, not tied to any one submission
-- platform) so they survive a change of platform. Both are plain VARCHAR(40), not mandatory
-- (only relevant for public-sector orders subject to these statutory references).
--
-- Known glossary gap: the metasfresh terminology glossary has no entry for the French statutory
-- terms behind these two references, so no German neologism is coined here. Each AD_Element's
-- BASE-language Name/PrintName is the FRENCH STATUTORY TERM exactly as it is printed on the
-- customer's reference invoice ('MARCHE' -> 'Marché', 'ENGAGEMENT' -> 'Engagement') — grounded
-- in that document, not invented — and en_US carries the English caption, marked translated.
-- This is a deliberate, documented deviation from the usual German-base convention, and it is
-- what the approved plan specifies. de_DE/de_CH are left IsTranslated='N' and therefore fall
-- back to the French base text; the German wording is a terminology question for a human.
--
-- AD_Table_ID for C_Order = 259 (verified via psql against the local stack).
-- EntityType='D' (core dictionary metadata; C_Order is a core table).
-- PersonalDataCategory='NP' — both columns are administrative references to a public contract/
-- market held by a government body, not personal data about a natural person.
--
-- IDs allocated from the central ID server:
--   AD_MigrationScript 5821370  (this script's prefix)
--   AD_Element  585393  (PublicProcurementMarketNo)
--   AD_Column   593441  (C_Order.PublicProcurementMarketNo)
--   AD_Element  585394  (PublicProcurementCommitmentNo)
--   AD_Column   593442  (C_Order.PublicProcurementCommitmentNo)
--
-- Do NOT touch: any existing migration file; C_Invoice; C_BPartner; any window, tab or field row
-- (those belong to later, separate tasks).

-- ============================================================
-- Column 1: C_Order.PublicProcurementMarketNo
-- ============================================================

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585393 /*From ID Server*/,0,'PublicProcurementMarketNo',TO_TIMESTAMP('2026-09-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Marché','Marché',TO_TIMESTAMP('2026-09-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585393
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- English is the deliberately-translated language here (base column already carries the English
-- caption); mark it translated. German is intentionally left IsTranslated='N' (falls back to the
-- English base text) — see header comment.
UPDATE AD_Element_Trl SET Name='Public Procurement Market No.', PrintName='Public Procurement Market No.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585393 AND AD_Language='en_US'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585393,'en_US')
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593441 /*From ID Server*/,585393,0,10,259,'PublicProcurementMarketNo',TO_TIMESTAMP('2026-09-01 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,40,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Marché','NP',0,0,TO_TIMESTAMP('2026-09-01 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593441
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585393)
;

/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN PublicProcurementMarketNo VARCHAR(40)')
;

-- ============================================================
-- Column 2: C_Order.PublicProcurementCommitmentNo
-- ============================================================

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585394 /*From ID Server*/,0,'PublicProcurementCommitmentNo',TO_TIMESTAMP('2026-09-01 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Engagement','Engagement',TO_TIMESTAMP('2026-09-01 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585394
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- English is the deliberately-translated language here (base column already carries the English
-- caption); mark it translated. German is intentionally left IsTranslated='N' (falls back to the
-- English base text) — see header comment.
UPDATE AD_Element_Trl SET Name='Public Procurement Commitment No.', PrintName='Public Procurement Commitment No.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 10:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585394 AND AD_Language='en_US'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585394,'en_US')
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593442 /*From ID Server*/,585394,0,10,259,'PublicProcurementCommitmentNo',TO_TIMESTAMP('2026-09-01 10:03:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,40,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Engagement','NP',0,0,TO_TIMESTAMP('2026-09-01 10:03:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593442
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585394)
;

/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN PublicProcurementCommitmentNo VARCHAR(40)')
;
