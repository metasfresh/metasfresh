-- noinspection SqlNoDataSourceInspectionForFile

-- Factoring OP-Liste export function.
-- Produces one header row (row_type='01') followed by detail rows (row_type='02'),
-- one per open invoice/credit note of factoring customers in the given currency and org.
--
-- Parameters:
--   p_c_currency_id  -- ISO currency to filter on (e.g. EUR = 102)
--   p_ad_org_id      -- organisation to filter on
--   p_ad_client_id   -- client (mandant) to filter on
--
-- Column layout per AC3 / AC4:
--   row_type  col_1   col_2   col_3                col_4                      col_5        col_6  col_7          col_8        col_9              col_10             col_11
--   01        SAF     EFAG    FactoringContractNo   FactoringClientAccountId   currency     ''     upload date    row count    sum D grandtotal   sum C grandtotal   ''
--   02        debno   name    documentno            dateinvoiced               duedate      curr   grandtotal     openamt      D/C flag           ''

DROP FUNCTION IF EXISTS report_factoring_op_liste(numeric, numeric, numeric);

CREATE OR REPLACE FUNCTION report_factoring_op_liste(
    p_c_currency_id numeric,
    p_ad_org_id     numeric,
    p_ad_client_id  numeric
)
RETURNS TABLE (
    row_type char(2),
    col_1    text,
    col_2    text,
    col_3    text,
    col_4    text,
    col_5    text,
    col_6    text,
    col_7    text,
    col_8    text,
    col_9    text,
    col_10   text,
    col_11   text
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_factoring_contract_no      text;
    v_factoring_client_account   text;
    v_currency_iso               text;
    v_upload_date                text;
    v_row_count                  numeric;   -- total rows including header
    v_sum_d                      numeric;   -- sum of grandtotal for D (debit) rows
    v_sum_c                      numeric;   -- sum of grandtotal for C (credit) rows
BEGIN
    -- -------------------------------------------------------------------------
    -- Resolve factorer BP for this org (unique: constraint c_bpartner_isfactorer_uniqe)
    -- -------------------------------------------------------------------------
    SELECT bpf.factoringcontractno,
           bpf.factoringclientaccountid
      INTO v_factoring_contract_no,
           v_factoring_client_account
      FROM c_bpartner bpf
     WHERE bpf.isfactorer      = 'Y'
       AND bpf.isactive        = 'Y'
       AND bpf.ad_org_id       = p_ad_org_id
       AND bpf.ad_client_id    = p_ad_client_id
     LIMIT 1;

    -- -------------------------------------------------------------------------
    -- Currency ISO code
    -- -------------------------------------------------------------------------
    SELECT cur.iso_code
      INTO v_currency_iso
      FROM c_currency cur
     WHERE cur.c_currency_id = p_c_currency_id;

    -- -------------------------------------------------------------------------
    -- Today formatted as DD.MM.YYYY
    -- -------------------------------------------------------------------------
    v_upload_date := to_char(current_date, 'DD.MM.YYYY');

    -- -------------------------------------------------------------------------
    -- Aggregate over matching invoices
    -- Filter: ad_org_id = param, IsFactoring='Y', currency = param, openamt != 0
    -- D/C: DocBaseType 'ARC' -> C (credit note), all others -> D
    -- -------------------------------------------------------------------------
    SELECT
        COUNT(*)                                                              AS cnt,
        COALESCE(SUM(CASE WHEN dt.docbasetype = 'ARC' THEN 0 ELSE inv.grandtotal END), 0),
        COALESCE(SUM(CASE WHEN dt.docbasetype = 'ARC' THEN inv.grandtotal ELSE 0 END), 0)
      INTO v_row_count, v_sum_d, v_sum_c
      FROM c_invoice     inv
      JOIN c_bpartner    bp  ON bp.c_bpartner_id  = inv.c_bpartner_id
      JOIN c_doctype     dt  ON dt.c_doctype_id   = inv.c_doctype_id
     WHERE inv.ad_org_id       = p_ad_org_id
       AND inv.ad_client_id    = p_ad_client_id
       AND inv.c_currency_id   = p_c_currency_id
       AND bp.isfactoring       = 'Y'
       AND bp.isactive          = 'Y'
       AND inv.openamt          <> 0;

    -- Total row count includes the header row itself
    v_row_count := v_row_count + 1;

    -- -------------------------------------------------------------------------
    -- Emit header row (row_type = '01')
    -- col_8 = row count formatted as "n,00"
    -- col_9 = sum D grandtotal, col_10 = sum C grandtotal -- both "12,2" with comma separator
    -- -------------------------------------------------------------------------
    RETURN QUERY SELECT
        '01'::char(2),
        'SAF',
        'EFAG',
        COALESCE(v_factoring_contract_no, ''),
        COALESCE(v_factoring_client_account, ''),
        COALESCE(v_currency_iso, ''),
        ''::text,
        v_upload_date,
        translate(to_char(v_row_count,     'FM999999999990D00'), '.', ','),
        translate(to_char(v_sum_d,          'FM999999999990D00'), '.', ','),
        translate(to_char(v_sum_c,          'FM999999999990D00'), '.', ','),
        ''::text;

    -- -------------------------------------------------------------------------
    -- Emit detail rows (row_type = '02'), sorted by BPartner.Value then DateInvoiced
    -- col_1  = Debitorennummer (bp.value, max 20)
    -- col_2  = Name (bp.name, max 50)
    -- col_3  = DocumentNo
    -- col_4  = DateInvoiced (DD.MM.YYYY)
    -- col_5  = DueDate (DD.MM.YYYY)
    -- col_6  = currency ISO3
    -- col_7  = GrandTotal (12,2 comma)
    -- col_8  = openamt (12,2 comma)
    -- col_9  = D/C flag
    -- col_10 = '' (reserved)
    -- col_11 = '' (reserved)
    -- -------------------------------------------------------------------------
    RETURN QUERY
    SELECT
        '02'::char(2),
        LEFT(bp.value,      20),
        LEFT(bp.name,       50),
        inv.documentno::text,
        to_char(inv.dateinvoiced, 'DD.MM.YYYY'),
        to_char(inv.duedate,      'DD.MM.YYYY'),
        COALESCE(v_currency_iso, ''),
        translate(to_char(inv.grandtotal, 'FM999999999990D00'), '.', ','),
        translate(to_char(inv.openamt,    'FM999999999990D00'), '.', ','),
        CASE WHEN dt.docbasetype = 'ARC' THEN 'C' ELSE 'D' END,
        ''::text,   -- col_10 (reserved)
        ''::text    -- col_11 (reserved)
      FROM c_invoice     inv
      JOIN c_bpartner    bp  ON bp.c_bpartner_id  = inv.c_bpartner_id
      JOIN c_doctype     dt  ON dt.c_doctype_id   = inv.c_doctype_id
     WHERE inv.ad_org_id       = p_ad_org_id
       AND inv.ad_client_id    = p_ad_client_id
       AND inv.c_currency_id   = p_c_currency_id
       AND bp.isfactoring       = 'Y'
       AND bp.isactive          = 'Y'
       AND inv.openamt          <> 0
     ORDER BY bp.value, inv.dateinvoiced;

END;
$$;

COMMENT ON FUNCTION report_factoring_op_liste(numeric, numeric, numeric)
    IS 'Factoring OP-Liste export: 1 header row (row_type=01) + detail rows (row_type=02) for open invoices of factoring customers.';
