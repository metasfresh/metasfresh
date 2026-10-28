-- Mark the fr_FR and fr_CH translations of the four French public-procurement AD_Elements as
-- actually translated (IsTranslated='Y'), so they are authoritative French rather than an
-- accidental fallback.
--
-- Background: AD_Element 585393 (PublicProcurementMarketNo), 585394 (PublicProcurementCommitmentNo)
-- and 585398 (PublicAuthoritySiret) were created by 5821370_sys_PublicProcurement_C_Order_Columns.sql
-- and 5821490_sys_PublicProcurement_C_BPartner_Columns.sql with the FRENCH STATUTORY TERM in the
-- BASE-language Name/PrintName ('Marché', 'Engagement', 'SIRET État'); element 585397
-- (PublicProcurementServiceCode) likewise carries 'Code service'. Their skeleton AD_Element_Trl
-- INSERT (in those same scripts) copied that base text into every active system language,
-- including fr_FR and fr_CH, with IsTranslated='N' — correct text, but marked as an unverified
-- fallback rather than a deliberate French translation.
--
-- This script does NOT change the French wording (already correct, sourced from a French
-- public-procurement reference invoice, not invented here) — it only flips fr_FR/fr_CH to
-- IsTranslated='Y' and re-propagates via update_TRL_Tables_On_AD_Element_TRL_Update() so the
-- dependent AD_Column_Trl / AD_Field_Trl / AD_Tab_Trl rows pick up the now-authoritative French
-- text.
--
-- de_DE / de_CH are intentionally NOT touched by this script and stay IsTranslated='N' — the
-- German wording is a terminology/glossary question for a human. Do not invent or coin German
-- wording for these four terms.
--
-- Out of scope: AD_Element 581038 (CommercialRegisterNumber, base 'FirmenbuchNR') is NOT touched
-- here even though its fr_FR/fr_CH rows are in the same unverified state — it is shared far beyond
-- this scope (1 AD_Column, 4 AD_Field / 4 AD_Tab references, verified via psql against a local
-- stack). Left for a separate, explicitly human-approved change.
--
-- No new IDs needed (updating existing rows only).
-- AD_MigrationScript 5821620 allocated from the central ID server.

-- ============================================================
-- AD_Element 585393 (PublicProcurementMarketNo) — 'Marché'
-- ============================================================

UPDATE AD_Element_Trl SET Name='Marché', PrintName='Marché', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585393 AND AD_Language='fr_FR'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585393,'fr_FR')
;

UPDATE AD_Element_Trl SET Name='Marché', PrintName='Marché', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585393 AND AD_Language='fr_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585393,'fr_CH')
;

-- ============================================================
-- AD_Element 585394 (PublicProcurementCommitmentNo) — 'Engagement'
-- ============================================================

UPDATE AD_Element_Trl SET Name='Engagement', PrintName='Engagement', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585394 AND AD_Language='fr_FR'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585394,'fr_FR')
;

UPDATE AD_Element_Trl SET Name='Engagement', PrintName='Engagement', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585394 AND AD_Language='fr_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585394,'fr_CH')
;

-- ============================================================
-- AD_Element 585397 (PublicProcurementServiceCode) — 'Code service'
-- ============================================================

UPDATE AD_Element_Trl SET Name='Code service', PrintName='Code service', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585397 AND AD_Language='fr_FR'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585397,'fr_FR')
;

UPDATE AD_Element_Trl SET Name='Code service', PrintName='Code service', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585397 AND AD_Language='fr_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585397,'fr_CH')
;

-- ============================================================
-- AD_Element 585398 (PublicAuthoritySiret) — 'SIRET État'
-- ============================================================

UPDATE AD_Element_Trl SET Name='SIRET État', PrintName='SIRET État', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585398 AND AD_Language='fr_FR'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585398,'fr_FR')
;

UPDATE AD_Element_Trl SET Name='SIRET État', PrintName='SIRET État', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585398 AND AD_Language='fr_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585398,'fr_CH')
;

-- ============================================================
-- AD_Element 581038 (CommercialRegisterNumber) — 'SIRET'
-- ============================================================
-- In scope as of 2026-09-01: CommercialRegisterNumber is the home of the invoiced partner's OWN
-- SIRET (no dedicated column is added), so its FRENCH caption has to say so. Its fr_FR/fr_CH rows
-- currently read 'FirmenbuchNR' with IsTranslated='N' — i.e. a French user sees a German word for
-- the field they know as the SIRET.
--
-- Only the FRENCH translations change. The base Name and the German translations stay
-- 'FirmenbuchNR', which is correct for German users; this is a translation, not a rename.
--
-- Blast radius, measured read-only before writing (not assumed): AD_Element 581038 backs exactly
-- 1 AD_Column (C_BPartner.CommercialRegisterNumber, AD_Column_ID 583367) and 4 AD_Field rows, all
-- of them on business-partner tabs (windows 541887, 542087, 540676 and 123). So the change is
-- confined to the partner windows, and only in French.

UPDATE AD_Element_Trl SET Name='SIRET', PrintName='SIRET', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581038 AND AD_Language='fr_FR'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(581038,'fr_FR')
;

UPDATE AD_Element_Trl SET Name='SIRET', PrintName='SIRET', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 12:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581038 AND AD_Language='fr_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(581038,'fr_CH')
;

