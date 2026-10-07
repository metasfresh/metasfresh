-- User-facing error messages for the C_UOM_Conversion unique indexes.
--
-- Two physical unique indexes enforce no-duplicate UOM conversions:
--   * c_uom_conversion_product  — the EXISTING product-scope index (gh18349, migration
--     5727690): UNIQUE (c_uom_id, c_uom_to_id, m_product_id) WHERE isactive='Y'. Left
--     untouched; only declared in AD_Index_Table here so its violations get a friendly message.
--   * c_uom_conversion_generic_uq — the generic-scope index created in
--     5812070_sys_c_uom_conversion_no_self_conversion_and_generic_uq.sql.
-- C_UOM_Conversion is user-editable in the WebUI (AD_Window 120 "Maßeinheit" generic
-- tab, and the product windows' UOM-conversion tab), and the interceptor does not check
-- for duplicate pairs — so a user who enters a duplicate hits the DB unique violation.
-- Declaring the indexes via AD_Index_Table (Name = the physical index name) lets
-- DBUniqueConstraintException surface a translatable ErrorMsg as an HTTP 422 validation
-- message instead of a raw technical error. The Name is the lookup key; the WhereClause
-- mirrors each physical index's WHERE clause.
-- Base language German; en_US overridden in AD_Index_Table_Trl.

-- ============ Product-scope index (existing c_uom_conversion_product) ============
INSERT INTO ad_index_table (ad_index_table_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                            ad_table_id, entitytype, isunique, name, whereclause, errormsg, description)
VALUES (540865 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-02 15:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-02 15:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        175, 'D', 'Y', 'c_uom_conversion_product',
        'isactive = ''Y''',
        'Für dieses Produkt und diese Mengeneinheiten-Kombination existiert bereits eine Umrechnung.',
        'Unique active product-specific UOM conversion per (from-UOM, to-UOM, product).')
ON CONFLICT (ad_index_table_id) DO NOTHING;

INSERT INTO ad_index_table_trl (ad_index_table_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, errormsg, istranslated)
SELECT 540865, l.ad_language, 0, 0, 'Y',
       TO_TIMESTAMP('2026-07-02 15:00:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-02 15:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
       t.errormsg, 'N'
FROM ad_language l, ad_index_table t
WHERE l.isactive='Y' AND l.issystemlanguage='Y' AND t.ad_index_table_id=540865
  AND NOT EXISTS (SELECT 1 FROM ad_index_table_trl tt WHERE tt.ad_index_table_id=540865 AND tt.ad_language=l.ad_language);

UPDATE ad_index_table_trl SET errormsg='A UOM conversion already exists for this product and unit-of-measure pair.',
       istranslated='Y', updated=TO_TIMESTAMP('2026-07-02 15:00:05','YYYY-MM-DD HH24:MI:SS'), updatedby=100
WHERE ad_index_table_id=540865 AND ad_language='en_US';
UPDATE ad_index_table_trl SET istranslated='Y', updated=TO_TIMESTAMP('2026-07-02 15:00:06','YYYY-MM-DD HH24:MI:SS'), updatedby=100
WHERE ad_index_table_id=540865 AND ad_language IN ('de_DE','de_CH');

-- Columns in the physical index order: (c_uom_id, c_uom_to_id, m_product_id).
INSERT INTO ad_index_column (ad_index_column_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, ad_index_table_id, ad_column_id, seqno, entitytype) VALUES
 (541529 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-02 15:00:10','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-02 15:00:10','YYYY-MM-DD HH24:MI:SS'), 100, 540865, 1010, 10, 'D'),
 (541530 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-02 15:00:11','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-02 15:00:11','YYYY-MM-DD HH24:MI:SS'), 100, 540865, 1011, 20, 'D'),
 (541531 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-02 15:00:12','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-02 15:00:12','YYYY-MM-DD HH24:MI:SS'), 100, 540865, 12866, 30, 'D')
ON CONFLICT (ad_index_column_id) DO NOTHING;

-- ============ Generic-scope index (c_uom_conversion_generic_uq) ============
INSERT INTO ad_index_table (ad_index_table_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                            ad_table_id, entitytype, isunique, name, whereclause, errormsg, description)
VALUES (540866 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-02 15:01:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-02 15:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
        175, 'D', 'Y', 'c_uom_conversion_generic_uq',
        'isactive = ''Y'' AND m_product_id IS NULL',
        'Für diese Mengeneinheiten-Kombination existiert bereits eine generische Umrechnung.',
        'Unique active generic UOM conversion per (from-UOM, to-UOM) where no product is set.')
ON CONFLICT (ad_index_table_id) DO NOTHING;

INSERT INTO ad_index_table_trl (ad_index_table_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, errormsg, istranslated)
SELECT 540866, l.ad_language, 0, 0, 'Y',
       TO_TIMESTAMP('2026-07-02 15:01:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-02 15:01:01','YYYY-MM-DD HH24:MI:SS'), 100,
       t.errormsg, 'N'
FROM ad_language l, ad_index_table t
WHERE l.isactive='Y' AND l.issystemlanguage='Y' AND t.ad_index_table_id=540866
  AND NOT EXISTS (SELECT 1 FROM ad_index_table_trl tt WHERE tt.ad_index_table_id=540866 AND tt.ad_language=l.ad_language);

UPDATE ad_index_table_trl SET errormsg='A generic UOM conversion already exists for this unit-of-measure pair.',
       istranslated='Y', updated=TO_TIMESTAMP('2026-07-02 15:01:05','YYYY-MM-DD HH24:MI:SS'), updatedby=100
WHERE ad_index_table_id=540866 AND ad_language='en_US';
UPDATE ad_index_table_trl SET istranslated='Y', updated=TO_TIMESTAMP('2026-07-02 15:01:06','YYYY-MM-DD HH24:MI:SS'), updatedby=100
WHERE ad_index_table_id=540866 AND ad_language IN ('de_DE','de_CH');

-- Columns in the physical index order: (c_uom_id, c_uom_to_id).
INSERT INTO ad_index_column (ad_index_column_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, ad_index_table_id, ad_column_id, seqno, entitytype) VALUES
 (541532 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-02 15:01:10','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-02 15:01:10','YYYY-MM-DD HH24:MI:SS'), 100, 540866, 1010, 10, 'D'),
 (541533 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-02 15:01:11','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-02 15:01:11','YYYY-MM-DD HH24:MI:SS'), 100, 540866, 1011, 20, 'D')
ON CONFLICT (ad_index_column_id) DO NOTHING;
