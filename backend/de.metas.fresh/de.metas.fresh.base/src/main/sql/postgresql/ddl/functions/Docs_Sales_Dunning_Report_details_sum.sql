DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_details_sum (IN p_Record_ID numeric)
;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_details_sum(IN p_Record_ID numeric)
    RETURNS TABLE
            (
                grandtotal numeric,
                paidamt    numeric,
                openamt    numeric,
                feeamt     numeric,
                totalamt   numeric,
                iso_code   character
            )
AS
$$
WITH TableIds AS (SELECT get_table_id('C_Invoice') AS InvoiceTableId,
                         get_table_id('C_Order')   AS OrderTableId),
     DunningCandidates AS (SELECT dc.C_Dunning_Candidate_ID,
                                               Documentpaid(dc.Record_ID, dc.AD_Table_ID, dc.C_Currency_ID,
                                                            (CASE
                                                                 WHEN charat(COALESCE(di.docbasetype, dor.docbasetype)::character varying, 2)::text = ANY (ARRAY ['P'::text, 'E'::text]) THEN (-1)
                                                                                                                                                                                         ELSE 1
                                                             END))                              AS PaidAmt,
                                               COALESCE(i.GrandTotal, o.GrandTotal)             AS GrandTotal,
                                               dl.amt                                           AS feeamt,
                                               (invoiceopen(dc.Record_ID, 0::numeric) + dl.amt) AS totalamt,
                                               c.cursymbol
                                        FROM C_DunningDoc dd
                                                 JOIN C_DunningDoc_line dl ON dd.C_DunningDoc_ID = dl.C_DunningDoc_ID
                                                 JOIN C_DunningDoc_Line_Source dls ON dl.C_DunningDoc_Line_ID = dls.C_DunningDoc_Line_ID
                                                 JOIN C_Dunning_Candidate dc ON dls.C_Dunning_Candidate_ID = dc.C_Dunning_Candidate_ID
                                                 JOIN C_Currency c ON dc.C_Currency_ID = c.C_Currency_ID
                                                 JOIN C_DunningLevel dlv ON dc.c_dunninglevel_id = dlv.c_dunninglevel_id
                                                 LEFT JOIN C_Invoice i
                                                           ON dc.Record_ID = i.C_Invoice_ID
                                                               AND i.isActive = 'Y'
                                                               AND dc.AD_Table_ID = (SELECT InvoiceTableId FROM TableIds)
                                                 LEFT JOIN c_doctype di ON i.c_doctype_id = di.c_doctype_id
                                                 LEFT JOIN C_Order o
                                                           ON dc.Record_ID = o.C_Order_ID
                                                               AND o.isActive = 'Y'
                                                               AND dc.AD_Table_ID = (SELECT OrderTableId FROM TableIds)
                                                 LEFT JOIN c_doctype dor ON o.c_doctype_id = dor.c_doctype_id
                                        WHERE dd.c_dunningdoc_id = p_Record_ID)
SELECT SUM(doc.GrandTotal)               AS grandtotal,
       SUM(doc.PaidAmt)                  AS paidamt,
       SUM(doc.GrandTotal - doc.PaidAmt) AS openamt,
       SUM(doc.feeamt)                   AS feeamt,
       SUM(doc.totalamt)                 AS totalamt,
       doc.cursymbol                     AS iso_code
FROM DunningCandidates doc
GROUP BY doc.cursymbol;
$$
    LANGUAGE sql STABLE
;