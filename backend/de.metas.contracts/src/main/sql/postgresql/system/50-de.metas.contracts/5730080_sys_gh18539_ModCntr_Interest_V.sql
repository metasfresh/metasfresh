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
                       AND ad_table_id = get_table_id('C_Flatrate_Term')
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
                     ORDER BY datetrx, modcntr_interest_id),
     FinalInvoice AS (SELECT distinct finalIL.C_Invoice_ID,
                                      finalIC.c_invoice_candidate_id,
                                      report.getPricePatternForJasper(finalI.m_pricelist_id) AS PricePattern,
                                      report.getAmountPatternForJasper(finalI.c_currency_id) AS amountpattern
                      FROM c_invoice_candidate finalIC
                               INNER JOIN C_Invoice_Line_Alloc finalILA ON finalIC.c_invoice_candidate_id = finalILA.c_invoice_candidate_id
                               INNER JOIN c_invoiceline finalIL ON finalIL.c_invoiceline_id = finalILA.c_invoiceline_id
                               INNER JOIN c_invoice finalI ON finalI.c_invoice_id = finalIL.c_invoice_id AND finalI.docstatus IN ('CO', 'CL')
                               INNER JOIN modcntr_log l ON finalIC.c_invoice_candidate_id = l.c_invoice_candidate_id)
SELECT mi.modcntr_interest_id                                                                        AS modcntr_interest_v_id,
       FinalInvoice.C_Invoice_ID                                                                     AS C_FinalInvoice_ID,
       ir.modcntr_interest_run_id,
       l.c_flatrate_term_id,
       l.bill_bpartner_id,
       ig.modcntr_invoicinggroup_id,
       l.initial_product_id,
       NULL::NUMERIC                                                                                 AS C_InterimInvoice_ID, -- Don't delete yet to make sure reports which might use it still work
       interimContract.c_flatrate_term_id                                                            AS C_Interim_Flatrate_Term_ID,
       shn.m_shipping_notification_id                                                                AS M_Shipping_Notification_ID,
       uom.c_uom_id,
       l.productname,
       p.name                                                                                        AS NAME,
       p.value                                                                                       AS ProductValue,
       t.modularcontracthandlertype,
       bp.value                                                                                      AS Bill_BPartner_Value,
       bp.name                                                                                       AS Bill_BPartner_Name,
       ig.name                                                                                       AS InvoicingGroup_Name,
       interimContract.documentno                                                                    AS InterimInvoice_documentNo,
       ROUND(interimContract.plannedqtyperunit * interimContract.priceactual, currency.stdprecision) AS InterimInvoice_GrandTotal,
       ROUND(interimContract.plannedqtyperunit * interimContract.priceactual, currency.stdprecision) AS InterimInvoice_TotalLines,
       shn.documentno                                                                                AS shippingNotificationNo,
       l.datetrx,
       l.amount,
       l.qty,
       uom.x12de355                                                                                  AS uom,
       ir.interimdate,
       ir.billingdate,
       ir.totalinterest,
       s.addinterestdays,
       s.interestrate,
       ABS(interim_amt)                                                                              AS InterimAmt,
       mi.matchedamt,
       matchedAmts.matched_amt                                                                       AS TotalAmt,
       mi.interestdays,
       mi.interestscore,
       mi.finalinterest,
       mi.ad_client_id,
       FinalInvoice.PricePattern                                                                     AS PricePattern,
       FinalInvoice.amountpattern                                                                    AS amountpattern

FROM modcntr_interest mi
         INNER JOIN modCntr_log l ON mi.shippingnotification_modcntr_log_id = l.modcntr_log_id AND l.isbillable = 'Y'
         INNER JOIN m_shipping_notification shn ON l.record_id = shn.m_shipping_notification_id
         INNER JOIN modcntr_invoicinggroup ig ON l.modcntr_invoicinggroup_id = ig.modcntr_invoicinggroup_id
         INNER JOIN modcntr_interest_run ir ON ir.modcntr_interest_run_id = mi.modcntr_interest_run_id
         INNER JOIN c_bpartner bp ON l.bill_bpartner_id = bp.c_bpartner_id
         INNER JOIN modCntr_module m ON l.modcntr_module_id = m.modcntr_module_id
         INNER JOIN modcntr_type t ON l.modcntr_type_id = t.modcntr_type_id
         INNER JOIN modcntr_settings s ON m.modcntr_settings_id = s.modcntr_settings_id
         LEFT JOIN interimAmts ON l.c_flatrate_term_id = interimAmts.c_flatrate_term_id AND l.modcntr_module_id = interimAmts.modcntr_module_id
         LEFT JOIN matchedAmts ON matchedamts.modcntr_interest_id = mi.modcntr_interest_id
         LEFT JOIN modcntr_log interimContractLog ON mi.interimcontract_modcntr_log_id = interimContractLog.modcntr_log_id
         LEFT JOIN c_flatrate_term interimContract ON interimContract.c_flatrate_term_id = interimContractLog.record_id
         LEFT JOIN c_currency currency ON interimContract.c_currency_id = currency.c_currency_id
         LEFT JOIN FinalInvoice ON FinalInvoice.c_invoice_candidate_id = l.c_invoice_candidate_id
         INNER JOIN m_product p ON l.initial_product_id = p.m_product_id
         INNER JOIN C_UOM uom ON l.c_uom_id = uom.c_uom_id
WHERE mi.finalinterest != 0
ORDER BY bp.value,
         l.c_flatrate_term_id,
         l.datetrx
;

