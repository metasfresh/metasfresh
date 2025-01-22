DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_details (IN p_Record_ID   numeric,
                                                                  IN p_AD_Language Character Varying(6))
;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_details(IN p_Record_ID   numeric,
                                                         IN p_AD_Language Character Varying(6))
    RETURNS TABLE
            (
                printname    character varying,
                documentno   character varying,
                documentdate timestamp,
                currency     character,
                grandtotal   numeric,
                paidamt      numeric,
                openamt      numeric,
                feeamt       numeric,
                totalamt     numeric,
                duedate      timestamp WITH TIME ZONE,
                daysdue      numeric,
                DunningLevel character varying
            )
AS
$$
WITH TableIds AS (SELECT get_table_id('C_Invoice') AS InvoiceTableId,
                         get_table_id('C_Order')   AS OrderTableId),
     DunningDetails AS (SELECT dd.C_DunningDoc_ID,
                               dl.C_DunningDoc_Line_ID,
                               dl.amt                                                                     AS FeeAmt,
                               dc.C_Dunning_Candidate_ID,
                               dc.DaysDue,
                               dlv.printname                                                              AS DunningLevel,
                               c.cursymbol                                                                AS currency,
                               dc.record_id,
                               invoiceopen(dc.Record_ID, 0::numeric) + dl.amt                             AS totalamt,
                               Documentpaid(dc.Record_ID, dc.AD_Table_ID, dc.C_Currency_ID,
                                            (CASE
                                                 WHEN charat(COALESCE(di.docbasetype, dor.docbasetype)::character varying, 2)::text = ANY (ARRAY ['P'::text, 'E'::text]) THEN (-1)
                                                                                                                                                                         ELSE 1
                                             END))                                                        AS PaidAmt,
                               COALESCE(i.DocumentNo, o.DocumentNo)                                       AS DocumentNo,
                               COALESCE(i.DateInvoiced, o.DateOrdered)                                    AS DocumentDate,
                               COALESCE(i.C_Doctype_ID, o.C_Doctype_ID)                                   AS C_Doctype_ID,
                               COALESCE(i.C_PaymentTerm_ID, o.C_PaymentTerm_ID)                           AS C_PaymentTerm_ID,
                               COALESCE(i.GrandTotal, o.GrandTotal)                                       AS GrandTotal,
                               COALESCE(i.DueDate, paymenttermduedate(o.C_PaymentTerm_ID, o.DateOrdered)) AS DueDate
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
SELECT COALESCE(dt_trl.printname, dt.PrintName) AS printname,
       COALESCE(dd.DocumentNo, 'ERROR')         AS documentno,
       dd.DocumentDate,
       dd.currency,
       dd.GrandTotal,
       dd.PaidAmt                               AS paidamt,
       dd.GrandTotal - dd.PaidAmt               AS openamt,
       dd.FeeAmt                                AS feeamt,
       dd.totalamt,
       dd.duedate,
       dd.DaysDue,
       dd.DunningLevel
FROM DunningDetails dd
         LEFT JOIN C_DocType dt
                   ON dd.C_Doctype_ID = dt.C_Doctype_ID
         LEFT JOIN C_DocType_Trl dt_trl
                   ON dd.C_Doctype_ID = dt_trl.C_Doctype_ID
                       AND dt_trl.AD_Language = p_AD_Language;
$$
    LANGUAGE sql STABLE
;