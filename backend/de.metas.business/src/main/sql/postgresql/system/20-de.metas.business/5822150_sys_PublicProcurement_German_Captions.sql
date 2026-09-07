-- Mark the de_DE and de_CH translations of the four French public-procurement AD_Elements as
-- actually translated (IsTranslated='Y'), carrying the FRENCH statutory term rather than an
-- accidental fallback.
--
-- Background: AD_Element 585393 (PublicProcurementMarketNo), 585394 (PublicProcurementCommitmentNo),
-- 585397 (PublicProcurementServiceCode) and 585398 (PublicAuthoritySiret) were created by
-- 5821370_sys_PublicProcurement_C_Order_Columns.sql / 5821490_sys_PublicProcurement_C_BPartner_Columns.sql
-- with the FRENCH STATUTORY TERM in the BASE-language Name/PrintName ('Marché', 'Engagement',
-- 'Code service', 'SIRET État'). Their skeleton AD_Element_Trl INSERT copied that base text into
-- every active system language, including de_DE and de_CH, with IsTranslated='N' — correct text
-- (verified: it already equals the French statutory term), but marked as an unverified fallback.
--
-- Decision (human, 2026-09-03): German carries the French term for these four fields. The
-- metasfresh terminology glossary has no entry for these French public-procurement statutory
-- terms (Marché/Engagement/Code service/SIRET État are references to a specific French public
-- tender document, not general business vocabulary), and rather than coining German wording for
-- concepts that have no German equivalent, the French term is used as-is. This script does NOT
-- change any wording — the text in AD_Element_Trl already equals the French statutory term — it
-- only flips de_DE/de_CH to IsTranslated='Y' so the fallback becomes a deliberate translation, and
-- re-propagates via update_TRL_Tables_On_AD_Element_TRL_Update() so the dependent AD_Column_Trl /
-- AD_Field_Trl / AD_Tab_Trl rows pick up the now-authoritative status.
--
-- Out of scope, explicitly NOT touched by this script:
--   * AD_Element 581038 (CommercialRegisterNumber) — German text 'FirmenbuchNR' is correct German
--     and stays. Its French translation is already 'SIRET' (set by
--     5821620_sys_PublicProcurement_French_Translations.sql). Overwriting German with French here
--     would regress four business-partner windows (581038 backs 1 AD_Column + 4 AD_Field rows).
--   * AD_Element 585403 (Siren) — its caption 'SIREN' is an acronym, identical in every language.
--     Verified via psql against the local stack: de_DE/de_CH text is already 'SIREN' (matching
--     fr_FR/fr_CH), only IsTranslated='N'. Flipped to 'Y' below with NO text change — this is not
--     a French-into-German substitution, it is confirming an already-correct acronym.
--
-- No new IDs needed (updating existing rows only).
-- AD_MigrationScript 5822150 allocated from the central ID server.

-- ============================================================
-- AD_Element 585393 (PublicProcurementMarketNo) — 'Marché'
-- ============================================================

UPDATE AD_Element_Trl SET Name='Marché', PrintName='Marché', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585393 AND AD_Language='de_DE'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585393,'de_DE')
;

UPDATE AD_Element_Trl SET Name='Marché', PrintName='Marché', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585393 AND AD_Language='de_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585393,'de_CH')
;

-- ============================================================
-- AD_Element 585394 (PublicProcurementCommitmentNo) — 'Engagement'
-- ============================================================

UPDATE AD_Element_Trl SET Name='Engagement', PrintName='Engagement', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585394 AND AD_Language='de_DE'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585394,'de_DE')
;

UPDATE AD_Element_Trl SET Name='Engagement', PrintName='Engagement', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585394 AND AD_Language='de_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585394,'de_CH')
;

-- ============================================================
-- AD_Element 585397 (PublicProcurementServiceCode) — 'Code service'
-- ============================================================

UPDATE AD_Element_Trl SET Name='Code service', PrintName='Code service', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585397 AND AD_Language='de_DE'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585397,'de_DE')
;

UPDATE AD_Element_Trl SET Name='Code service', PrintName='Code service', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585397 AND AD_Language='de_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585397,'de_CH')
;

-- ============================================================
-- AD_Element 585398 (PublicAuthoritySiret) — 'SIRET État'
-- ============================================================

UPDATE AD_Element_Trl SET Name='SIRET État', PrintName='SIRET État', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585398 AND AD_Language='de_DE'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585398,'de_DE')
;

UPDATE AD_Element_Trl SET Name='SIRET État', PrintName='SIRET État', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585398 AND AD_Language='de_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585398,'de_CH')
;

-- ============================================================
-- AD_Element 585403 (Siren) — acronym confirmation only, NO text change
-- ============================================================
-- de_DE/de_CH text is already 'SIREN' (verified via psql before writing this script) — this is
-- the same acronym in every language, not a French-into-German substitution. Only IsTranslated is
-- flipped, from 'N' (accidental fallback) to 'Y' (confirmed correct).

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585403 AND AD_Language='de_DE'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585403,'de_DE')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585403 AND AD_Language='de_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585403,'de_CH')
;
