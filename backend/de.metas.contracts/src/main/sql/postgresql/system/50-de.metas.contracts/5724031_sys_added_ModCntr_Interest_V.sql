DROP VIEW IF EXISTS ModCntr_Interest_V
;

CREATE OR REPLACE VIEW ModCntr_Interest_V AS
WITH interimAmts AS (SELECT SUM(amount
                            ) AS interim_amt,
                            c_flatrate_term_id,
                            l.modcntr_module_id,
                            t.modularcontracthandlertype
                     FROM modcntr_log l
                              INNER JOIN modcntr_module m ON l.modcntr_module_id = m.modcntr_module_id
                              INNER JOIN modcntr_type t ON l.modcntr_type_id = t.modcntr_type_id
                     WHERE isbillable = 'Y'
                       AND ad_table_id = get_table_id('C_InvoiceLine')
                     GROUP BY c_flatrate_term_id, l.modcntr_module_id, t.modularcontracthandlertype),
     matchedAmts AS (SELECT SUM(mi.matchedamt
                               ) OVER (PARTITION BY c_flatrate_term_id, modcntr_module_id
                                ORDER BY datetrx,modcntr_interest_id ROWS UNBOUNDED PRECEDING
                                ) AS matched_amt,
                            mi.modcntr_interest_id,
                            c_flatrate_term_id,
                            modcntr_module_id,
                            datetrx
                     FROM modcntr_interest mi
                              INNER JOIN modcntr_log l ON mi.shippingnotification_modcntr_log_id = l.modcntr_log_id AND l.isbillable = 'Y'
                     WHERE mi.interiminvoice_modcntr_log_id IS NOT NULL
                       AND finalinterest != 0
                     ORDER BY datetrx, modcntr_interest_id)
SELECT finalIL.C_Invoice_ID as C_FinalInvoice_ID,
       ir.modcntr_interest_run_id,
       l.c_flatrate_term_id,
       p.name as name,
       t.modularcontracthandlertype,
       bp.value                  AS Bill_BPartner_Value,
       bp.name                   AS Bill_BPartner_Name,
       ig.name                   AS InvoicingGroup_Name,
       interimInvoice.documentno AS InterimInvoice_documentNo,
       l.datetrx,
       ir.interimdate,
       ir.billingdate,
       ir.totalinterest,
       s.addinterestdays,
       s.interestrate,
       ABS(interim_amt)          AS InterimAmt,
       mi.matchedamt,
       matchedAmts.matched_amt   AS TotalAmt,
       mi.interestdays,
       mi.interestscore,
       mi.finalinterest
FROM modcntr_interest mi
         INNER JOIN modCntr_log l ON mi.shippingnotification_modcntr_log_id = l.modcntr_log_id AND l.isbillable = 'Y'
         INNER JOIN modcntr_invoicinggroup ig ON l.modcntr_invoicinggroup_id = ig.modcntr_invoicinggroup_id
         INNER JOIN modcntr_interest_run ir ON ir.modcntr_interest_run_id = mi.modcntr_interest_run_id
         INNER JOIN c_bpartner bp ON l.bill_bpartner_id = bp.c_bpartner_id
         INNER JOIN modCntr_module m ON l.modcntr_module_id = m.modcntr_module_id
         INNER JOIN modcntr_type t ON l.modcntr_type_id = t.modcntr_type_id
         INNER JOIN modcntr_settings s ON m.modcntr_settings_id = s.modcntr_settings_id
         LEFT JOIN interimAmts ON l.c_flatrate_term_id = interimAmts.c_flatrate_term_id AND l.modcntr_module_id = interimAmts.modcntr_module_id
         LEFT JOIN matchedAmts ON matchedamts.modcntr_interest_id = mi.modcntr_interest_id
         LEFT JOIN modcntr_log interimInvoiceLineLog ON mi.interiminvoice_modcntr_log_id = interimInvoiceLineLog.modcntr_log_id
         LEFT JOIN c_invoiceline il ON interimInvoiceLineLog.record_id = il.c_invoiceline_id
         LEFT JOIN c_invoice interimInvoice ON il.c_invoice_id = interimInvoice.c_invoice_id
         left join c_invoice_candidate finalIC on l.c_invoice_candidate_id = finalIC.c_invoice_candidate_id
         left join C_Invoice_Line_Alloc finalILA on finalIC.c_invoice_candidate_id = finalILA.c_invoice_candidate_id
         left join c_invoiceline finalIL on finalIL.c_invoiceline_id = finalILA.c_invoiceline_id
         inner join m_product p on l.initial_product_id=p.m_product_id
WHERE mi.finalinterest != 0
ORDER BY bp.value,
         l.datetrx;