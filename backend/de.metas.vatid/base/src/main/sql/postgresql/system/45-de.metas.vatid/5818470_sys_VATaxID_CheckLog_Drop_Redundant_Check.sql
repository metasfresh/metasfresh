-- VAT-ID online check: drop the redundant physical CHECK on VATaxIDStatus.
-- AD_Column 593175 already binds VATaxIDStatus to AD_Reference_ID=17 (List) /
-- AD_Reference_Value_ID=542125, which is the single source of truth for the value set. The physical
-- CHECK constraint added by migration 5818420 hardcodes the same six values a second time; if the
-- reference list ever gains or loses a value, the CHECK would silently reject legitimate data with no
-- AD-level signal. Removing it leaves the reference list as the sole authority.

SELECT db_alter_table('VATaxID_CheckLog', 'ALTER TABLE VATaxID_CheckLog DROP CONSTRAINT IF EXISTS vataxid_checklog_vataxidstatus_check');
