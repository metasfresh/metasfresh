DROP VIEW IF EXISTS C_Commission_Trigger_With_Instance_V CASCADE
;
;

CREATE OR REPLACE VIEW C_Commission_Trigger_With_Instance_V AS
SELECT *
FROM (
         SELECT ic_rev.C_Invoice_Candidate_ID,
                NULL               AS C_InvoiceLine_ID,
                ic_rev.AD_Client_ID,
                ic_rev.AD_Org_ID,
                ic_rev.Created,
                ic_rev.CreatedBy,
                ic_rev.Updated,
                ic_rev.UpdatedBy,
                ic_rev.IsActive,
                ic_rev.DateOrdered AS CommissionDate,
                ic_rev.IsSOTrx,
                ic_rev.POReference,
                ic_rev.M_Product_ID,
                'InvoiceCandidate' AS CommissionTrigger_Type,
                ic_rev.QtyEntered,
                ic_rev.C_UOM_ID,
                ic_rev.Bill_BPartner_ID,
                ic_rev.C_Order_ID,
                ic_rev.C_OrderLine_ID,
                NULL               AS C_Invoice_ID,
                ci.C_Commission_Instance_ID
         FROM C_Invoice_Candidate ic_rev
                  LEFT JOIN C_Commission_Instance ci ON ic_rev.C_Invoice_Candidate_ID = ci.C_Invoice_Candidate_ID AND ic_rev.isactive = 'Y' AND ic_rev.AD_Client_ID = ci.AD_Client_ID
         UNION ALL
         SELECT NULL                    AS C_Invoice_Candidate_ID,
                il_rev.C_InvoiceLine_ID AS C_InvoiceLine_ID,
                il_rev.AD_Client_ID,
                il_rev.AD_Org_ID,
                il_rev.Created,
                il_rev.CreatedBy,
                il_rev.Updated,
                il_rev.UpdatedBy,
                il_rev.IsActive,
                i_rev.DateInvoiced      AS CommissionDate,
                i_rev.IsSOTrx,
                i_rev.POReference,
                il_rev.M_Product_ID,
                CASE
                    WHEN dt.DocBaseType = 'ARI' THEN 'CustomerInvoice'
                    WHEN dt.DocBaseType = 'ARC' THEN 'CustomerCreditmemo'
                END                     AS CommissionTrigger_Type,
                il_rev.QtyEntered,
                il_rev.C_UOM_ID,
                i_rev.C_BPartner_ID     AS Bill_BPartner_ID,
                NULL                    AS C_Order_ID,
                NULL                    AS C_OrderLine_ID,
                il_rev.C_Invoice_ID,
                ci.C_Commission_Instance_ID
         FROM C_InvoiceLine il_rev
                  LEFT JOIN C_Invoice i_rev ON i_rev.C_Invoice_ID = il_rev.C_Invoice_ID
                  LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i_rev.C_DocType_ID
                  LEFT JOIN C_Commission_Instance ci ON il_rev.C_InvoiceLine_ID = ci.C_InvoiceLine_ID AND il_rev.isactive = 'Y' AND il_rev.AD_Client_ID = ci.AD_Client_ID
         WHERE il_rev.C_OrderLine_ID IS NULL
         UNION ALL
         SELECT NULL                  AS C_Invoice_Candidate_ID,
                NULL                  AS C_InvoiceLine_ID,
                ol_rev.AD_Client_ID,
                ol_rev.AD_Org_ID,
                ol_rev.Created,
                ol_rev.CreatedBy,
                ol_rev.Updated,
                ol_rev.UpdatedBy,
                ol_rev.IsActive,
                o_rev.DateAcct        AS CommissionDate,
                o_rev.IsSOTrx,
                o_rev.POReference,
                ol_rev.M_Product_ID,
                'MediatedOrder'       AS CommissionTrigger_Type,
                ol_rev.QtyEntered,
                ol_rev.C_UOM_ID,
                ol_rev.C_BPartner_ID  AS Bill_BPartner_ID,
                o_rev.C_Order_ID      AS C_Order_ID,
                ol_rev.C_OrderLine_ID AS C_OrderLine_ID,
                NULL                  AS C_Invoice_ID,
                ci.C_Commission_Instance_ID
         FROM c_orderline ol_rev
                  INNER JOIN c_order o_rev ON o_rev.C_Order_ID = ol_rev.C_Order_ID
                  INNER JOIN C_DocType dt ON dt.C_DocType_ID = o_rev.C_DocType_ID
                  INNER JOIN C_Commission_Instance ci ON ol_rev.C_OrderLine_ID = ci.C_OrderLine_ID AND ol_rev.isactive = 'Y' AND ol_rev.AD_Client_ID = ci.AD_Client_ID
         WHERE dt.DocSubType = 'MED' -- mediated order
     ) TRIGGER
;