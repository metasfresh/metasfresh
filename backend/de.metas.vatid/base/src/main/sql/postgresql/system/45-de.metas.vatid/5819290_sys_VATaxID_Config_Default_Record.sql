-- VAT-ID online check: create the default VATaxID_Config record for every real organisation, so that the
-- "USt-IdNr.-Konfiguration" window (AD_Window 542182) opens on a real, editable row instead of an empty
-- list.
--
-- No VATaxID_Config row exists anywhere today -- there is no INSERT INTO VATaxID_Config in any migration
-- script -- so every organisation currently runs on the repository's no-record fallback.
--
-- ============================================================================================
-- BEHAVIOUR-PRESERVING BY CONSTRUCTION -- this is the whole point of the SELECT below.
-- ============================================================================================
-- VATaxIDConfigRepository.getByOrgId returns, for an organisation with no record, a synthesized config
-- whose formatCheckEnabled follows the System-level SysConfig VATaxID_Config.IsFormatCheckEnabledByDefault
-- (via sysConfigBL.getBooleanValue(name, true) -- System scope, defaulting to true) and whose
-- viesCheckEnabled is hardcoded false.
--
-- The moment a record exists for an organisation, THAT RECORD governs and the SysConfig stops applying to
-- it. So seeding a hardcoded 'Y' would silently switch the format check back ON for any installation that
-- had deliberately set that SysConfig to 'N'. IsFormatCheckEnabled is therefore READ FROM THE SYSCONFIG
-- rather than hardcoded: the record materialises whatever was already in effect, on every installation,
-- and no behaviour changes anywhere.
--
-- IsVIESCheckEnabled is hardcoded 'N' because the fallback hardcodes viesCheckEnabled=false -- there is no
-- configuration that could have made it anything else.
--
-- What the record buys, given it changes no behaviour today:
--   1. Visibility and editability. The window currently lists nothing, so an administrator has to know to
--      create a row before any of these settings can be seen or changed.
--   2. It is the ONLY route to ever enabling VIES. The fallback hardcodes viesCheckEnabled=false, so
--      without a record VIES can never be switched on, whatever is configured elsewhere.
--   3. It is the only place that can hold RestApiBaseURL, RequesterMemberStateCode and RequesterNumber.
--      The fallback leaves all three null and has no mechanism to supply them, and they are exactly what
--      an organisation needs once (2) is exercised. They are left NULL here on purpose: they are per
--      installation values this migration has no business inventing.
--
-- The one CAVEAT worth knowing: after this runs, the control point for the format check moves from the
-- SysConfig to this window. Anyone later flipping that SysConfig and expecting it to reach these
-- organisations will find it silently ignored.
--
-- ============================================================================================
-- SCOPE OF THE INSERT
-- ============================================================================================
-- One row per ACTIVE, REAL organisation (AD_Org_ID > 0), with AD_Client_ID taken from the organisation
-- itself. Deliberately NOT a single row at org 0: VATaxIDConfigRepository.retrieveByOrgId filters with
-- addEqualsFilter(AD_Org_ID, orgId) -- an exact match with NO org-0 fallback -- so a row at org 0 would be
-- inert for every real organisation while looking authoritative in the window. AD_Client_ID=0 is invalid
-- too: AD_Table.AccessLevel for this table is '3' (Client+Org).
--
-- Guarded by NOT EXISTS against the table's one-active-row-per-organisation partial unique index, so the
-- script is safe on an installation that already created rows by hand.
--
-- RecheckAfterDays=90 and OnServiceUnavailable='ServiceUnavailable' are the DDL defaults. Both are
-- unreachable while VIES is off, so they cannot change behaviour here; they carry sensible fail-open
-- values for the day someone switches VIES on, rather than the fallback's 0-day window, which would send
-- a request for every single check.
--
-- IDs: AD_MigrationScript 5819290 from idserver.metas.de (this file's prefix). The VATaxID_Config_ID
-- values come from the table's own Postgres sequence -- this is an application data row, not an AD
-- metadata row, so it does not draw from the AD id server.

INSERT INTO VATaxID_Config (
    VATaxID_Config_ID,
    AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    IsFormatCheckEnabled, IsVIESCheckEnabled,
    RecheckAfterDays, OnServiceUnavailable
)
SELECT
    nextval('vataxid_config_seq'),
    org.AD_Client_ID, org.AD_Org_ID, 'Y',
    TO_TIMESTAMP('2026-08-15 16:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-08-15 16:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    -- mirrors sysConfigBL.getBooleanValue('VATaxID_Config.IsFormatCheckEnabledByDefault', true):
    -- System-scoped row, defaulting to 'Y' when absent
    COALESCE((SELECT CASE WHEN UPPER(TRIM(sc.Value)) IN ('Y', 'TRUE') THEN 'Y' ELSE 'N' END
              FROM AD_SysConfig sc
              WHERE sc.Name = 'VATaxID_Config.IsFormatCheckEnabledByDefault'
                AND sc.IsActive = 'Y'
                AND sc.AD_Client_ID = 0
                AND sc.AD_Org_ID = 0
              LIMIT 1), 'Y'),
    'N',   -- IsVIESCheckEnabled -- the fallback hardcodes false, so nothing else is possible today
    90,    -- RecheckAfterDays
    'ServiceUnavailable'
FROM AD_Org org
WHERE org.AD_Org_ID > 0
  AND org.IsActive = 'Y'
  AND NOT EXISTS (
      SELECT 1 FROM VATaxID_Config cfg
      WHERE cfg.AD_Org_ID = org.AD_Org_ID
        AND cfg.IsActive = 'Y'
  );
