-- me03#29063: Migrate existing C_BPartner_Product GTIN/EAN_CU/UPC/ProductNo/EAN13_ProductCode
-- overrides into M_Product_ASI_Data so they continue to be picked up by the EDI DESADV/INVOIC
-- JSON exports after PR #23505 replaced C_BPartner_Product with M_Product_ASI_Data in those
-- views. Without this migration, every C_BPartner_Product-based GTIN override stops being read
-- the moment the new view definitions take effect on a database that already had data.
--
-- Migrated rows are marked with:
--   * M_AttributeSetInstance_ID = NULL    (empty attributes key = subset of any line ASI)
--   * SeqNo                    = 1000     (low priority -- any explicit M_Product_ASI_Data
--                                          record at default SeqNo wins over this fallback)
--   * ProductDescription       = '[Migrated from C_BPartner_Product me03#29063]'
--                                          (free-text marker for later identification)
--
-- Idempotent: skips rows where a M_Product_ASI_Data row already exists for the same
-- (M_Product_ID, C_BPartner_ID, M_AttributeSetInstance_ID IS NULL) signature.

-- Defensive backup of the destination table before bulk insert.
SELECT backup_table('m_product_asi_data', '_me03_29063_pre_C_BPartner_Product_migration');

INSERT INTO M_Product_ASI_Data (
    M_Product_ASI_Data_ID,
    AD_Client_ID,
    AD_Org_ID,
    M_Product_ID,
    C_BPartner_ID,
    M_AttributeSetInstance_ID,
    SeqNo,
    GTIN,
    EAN_CU,
    UPC,
    ProductNo,
    EAN13_ProductCode,
    ProductDescription,
    IsActive,
    Created,
    CreatedBy,
    Updated,
    UpdatedBy
)
SELECT
    nextval('m_product_asi_data_seq'),
    cbp.AD_Client_ID,
    cbp.AD_Org_ID,
    cbp.M_Product_ID,
    cbp.C_BPartner_ID,
    NULL,                                            -- match any line ASI via subset rule
    1000,                                            -- low-priority fallback SeqNo
    cbp.GTIN,
    cbp.EAN_CU,
    cbp.UPC,
    cbp.ProductNo,
    cbp.EAN13_ProductCode,
    '[Migrated from C_BPartner_Product me03#29063]',
    cbp.IsActive,
    TO_TIMESTAMP('2026-05-09 14:00', 'YYYY-MM-DD HH24:MI'),
    99,                                              -- Support user (system-wide data migration)
    TO_TIMESTAMP('2026-05-09 14:00', 'YYYY-MM-DD HH24:MI'),
    99
FROM C_BPartner_Product cbp
WHERE
    -- only mirror rows that carry at least one EDI-relevant override
    (cbp.GTIN              IS NOT NULL
     OR cbp.EAN_CU         IS NOT NULL
     OR cbp.UPC            IS NOT NULL
     OR cbp.ProductNo      IS NOT NULL
     OR cbp.EAN13_ProductCode IS NOT NULL)
    -- idempotency: don't insert twice if the migration is replayed
    AND NOT EXISTS (
        SELECT 1
        FROM M_Product_ASI_Data asi
        WHERE asi.M_Product_ID = cbp.M_Product_ID
          AND asi.C_BPartner_ID = cbp.C_BPartner_ID
          AND asi.M_AttributeSetInstance_ID IS NULL
    )
;

-- Re-align the native sequence with the new MAX(M_Product_ASI_Data_ID) so subsequent
-- application-side INSERTs don't collide with the IDs we just consumed.
SELECT public.dba_seq_check_native('M_Product_ASI_Data');
