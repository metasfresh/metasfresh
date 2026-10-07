-- Fixes failure of: 5817640_sys_C_Conversion_Rate_natural_key_unique_index.sql
--
-- 5817640 creates a UNIQUE index on C_Conversion_Rate (ValidFrom, C_Currency_ID, C_Currency_ID_To,
-- C_ConversionType_ID, AD_Org_ID), but databases that already hold more than one rate for such a key
-- abort the migration run with "could not create unique index c_conversion_rate_natural_key_uq".
-- This script runs right before it and keeps exactly one rate per key, choosing in this order:
-- the active one, then the most recently updated one, then the one with the highest ID.
-- Import rows (I_Conversion_Rate) that point to a removed rate are repointed to the kept one.
-- On a database without duplicates this changes nothing.

SELECT backup_table('c_conversion_rate', '_gh32506_dedup');
SELECT backup_table('i_conversion_rate', '_gh32506_dedup');

CREATE TEMPORARY TABLE tmp_gh32506_conversion_rate_dupes ON COMMIT DROP AS
SELECT ranked.c_conversion_rate_id,
       ranked.keep_c_conversion_rate_id
FROM (SELECT cr.c_conversion_rate_id,
             FIRST_VALUE(cr.c_conversion_rate_id) OVER w AS keep_c_conversion_rate_id,
             ROW_NUMBER() OVER w                         AS rn
      FROM c_conversion_rate cr
      WINDOW w AS (PARTITION BY cr.validfrom, cr.c_currency_id, cr.c_currency_id_to, cr.c_conversiontype_id, cr.ad_org_id
                   ORDER BY (cr.isactive = 'Y') DESC, cr.updated DESC, cr.c_conversion_rate_id DESC)) ranked
WHERE ranked.rn > 1;

UPDATE i_conversion_rate ic
SET c_conversion_rate_id = d.keep_c_conversion_rate_id,
    updated              = TO_TIMESTAMP('2026-10-02 16:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    updatedby            = 99
FROM tmp_gh32506_conversion_rate_dupes d
WHERE ic.c_conversion_rate_id = d.c_conversion_rate_id;

DELETE
FROM c_conversion_rate cr
    USING tmp_gh32506_conversion_rate_dupes d
WHERE cr.c_conversion_rate_id = d.c_conversion_rate_id;
