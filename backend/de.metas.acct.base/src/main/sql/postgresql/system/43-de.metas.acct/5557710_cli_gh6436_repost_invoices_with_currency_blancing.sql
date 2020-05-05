WITH
    --
    invoices_with_currencyBlancing AS (
        SELECT DISTINCT fa.record_id AS c_invoice_id
        FROM Fact_acct fa
        WHERE fa.account_id IN (
            SELECT vc.account_id
            FROM c_acctschema_gl gl
                     INNER JOIN c_validcombination vc ON vc.c_validcombination_id = gl.currencybalancing_acct
        )
          AND fa.ad_table_id = 318
    ),
    --
    allocations AS (
        SELECT DISTINCT al.c_allocationhdr_id
        FROM invoices_with_currencyBlancing t
                 INNER JOIN c_allocationline al ON al.c_invoice_id = t.c_invoice_id
    ),
    --
    docs_to_repost AS (
        SELECT 'C_Invoice'    AS TableName,
               t.c_invoice_id AS record_id
        FROM invoices_with_currencyBlancing t
        UNION ALL
        SELECT 'C_AllocationHdr'    AS TableName,
               t.c_allocationhdr_id AS record_id
        FROM allocations t
    )
    --
SELECT t.TableName,
       t.record_id,
       de_metas_acct.fact_acct_unpost(t.TableName, t.record_id)
FROM docs_to_repost t
;

