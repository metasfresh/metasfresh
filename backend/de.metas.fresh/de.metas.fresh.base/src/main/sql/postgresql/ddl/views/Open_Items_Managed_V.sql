DROP VIEW IF EXISTS Open_Items_Managed_V
;

CREATE VIEW Open_Items_Managed_V
            (Open_Items_Managed_V_ID,
             ad_client_id,
             ad_org_id,
             created,
             createdby,
             updated,
             updatedby,
             isactive,
             C_BPartner_ID,
             DateAcct,
             M_Department_ID,
             m_sectioncode_id,
             UserElementString1,
             documentno,
             DocumentDate,
             poreference,
             description,
             Source_Currency_ID, -- Document currency AD_Element
             Amount_FC,
             Acct_Currency_ID, -- Accounting Currency AD_Element
             Amount_LC,
             isopenitem
                )
AS

SELECT fa.fact_acct_id                                                    AS Open_Items_Managed_V_ID,
       fa.ad_client_id,
       fa.ad_org_id,
       fa.created,
       fa.createdby,
       fa.updated,
       fa.updatedby,
       fa.isactive,
       fa.C_BPartner_ID,
       fa.DateAcct,
       get_section_code_department(fa.m_sectioncode_id, fa.datetrx::date) AS M_Department_ID,
       fa.m_sectioncode_id,
       fa.UserElementString1,
       fa.documentno,
       fa.datetrx                                                         AS DocumentDate,
       CASE
           WHEN fa.ad_table_id = get_table_id('C_Order')
               THEN (SELECT o.poreference FROM c_order o WHERE o.c_order_id = fa.record_id)
           WHEN fa.ad_table_id = get_table_id('C_Invoice')
               THEN (SELECT i.poreference FROM c_invoice i WHERE i.c_invoice_id = fa.record_id)
       END                                                                AS poreference,
       fa.description,
       fa.c_currency_id,
       fa.AmtSourceDr - fa.AmtSourceCr                                    AS Amount_FC,
       schema.c_currency_id,
       fa.AmtAcctDr - fa.AmtAcctCr                                        AS Amount_LC,
       acc.isopenitem
FROM fact_acct fa
         INNER JOIN c_elementvalue acc ON fa.account_id = acc.c_elementvalue_id
         INNER JOIN C_AcctSchema schema ON fa.c_acctschema_id = schema.c_acctschema_id
WHERE fa.OpenItemKey IS NOT NULL
  AND fa.OI_TrxType = 'O' -- Just open items (exclude Clearing transactions)
  AND fa.IsOpenItemsReconciled = 'N'
;