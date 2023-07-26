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

DROP VIEW IF EXISTS C_Commission_Overview_V
;

CREATE OR REPLACE VIEW C_Commission_Overview_V AS
SELECT
    -- Make sure that every view record has a unique and stable ID
    CASE
        WHEN ila_settlement.C_Invoice_Line_Alloc_ID IS NOT NULL THEN (ila_settlement.C_Invoice_Line_Alloc_ID * 10) + 1
        WHEN ic_settlement.C_Invoice_Candidate_ID IS NOT NULL   THEN (ic_settlement.C_Invoice_Candidate_ID * 10) + 2
        WHEN cs.C_Commission_Share_ID IS NOT NULL               THEN (cs.C_Commission_Share_ID * 10) + 3
        WHEN trigger.C_Commission_Instance_ID IS NOT NULL       THEN (trigger.C_Commission_Instance_ID * 10) + 4
        WHEN trigger.C_Invoice_Candidate_ID IS NOT NULL         THEN (trigger.C_Invoice_Candidate_ID * 10) + 5
        WHEN trigger.C_InvoiceLine_ID IS NOT NULL               THEN (trigger.C_InvoiceLine_ID * 10) + 6
        WHEN trigger.CommissionTrigger_Type = 'MediatedOrder'   THEN (trigger.C_OrderLine_ID * 10) + 7
    END                                  AS C_Commission_Overview_V_ID,
    trigger.IsSOTrx,
    trigger.C_Commission_Instance_ID,
    trigger.Created,
    trigger.CreatedBy,
    trigger.Updated,
    trigger.UpdatedBy,
    trigger.AD_Client_ID,
    trigger.AD_Org_ID,
    trigger.IsActive,
    trigger.CommissionDate,
    trigger.POreference,
    trigger.M_Product_ID                 AS M_Product_Order_ID,
    trigger.CommissionTrigger_Type,
    ci.MostRecentTriggerTimestamp,
    ci.PointsBase_Forecasted,
    ci.PointsBase_Invoiceable,
    ci.PointsBase_Invoiced,
    trigger.QtyEntered                   AS QtyEntered,
    trigger.C_UOM_ID                     AS C_UOM_ID,
    trigger.Bill_BPartner_ID,
    trigger.C_Invoice_Candidate_ID,
    trigger.C_Order_ID,
    trigger.C_OrderLine_ID,
    trigger.C_Invoice_ID,
    trigger.C_InvoiceLine_ID,
    cs.C_Commission_Share_ID,
    cs.LevelHierarchy,
    cs.C_BPartner_SalesRep_ID,
    cs.C_Flatrate_Term_ID,
    cs.C_CommissionSettingsLine_ID,
    cs.C_MediatedCommissionSettingsLine_ID,
    cs.PointsSum_ToSettle,
    cs.PointsSum_Settled,
    cs.IsSimulation,
    cs.C_BPartner_Payer_ID,
    CASE
        WHEN (ci.PointsBase_Forecasted + ci.PointsBase_Invoiceable + ci.PointsBase_Invoiced) != 0
            THEN ROUND((cs.PointsSum_ToSettle + cs.PointsSum_Settled) / (ci.PointsBase_Forecasted + ci.PointsBase_Invoiceable + ci.PointsBase_Invoiced) * 100, 0)
            ELSE NULL
    END                                  AS percentofbasepoints,
    ic_settlement.C_Invoice_Candidate_ID AS C_Invoice_Candidate_Commission_ID,
    il_settlement.C_InvoiceLine_ID       AS C_InvoiceLine_Commission_ID,
    il_settlement.C_Invoice_ID           AS C_Invoice_Commission_ID

FROM C_Commission_Trigger_With_Instance_V trigger
         LEFT JOIN C_Commission_Instance ci ON ci.C_Commission_Instance_ID = trigger.C_Commission_Instance_ID
         LEFT JOIN C_Commission_Share cs ON cs.C_Commission_Instance_ID = ci.C_Commission_Instance_ID AND cs.isactive = 'Y'
         LEFT JOIN C_Invoice_Candidate ic_settlement ON ic_settlement.Record_ID = cs.C_Commission_Share_ID AND ic_settlement.AD_Table_ID = get_table_id('C_Commission_Share') AND ic_settlement.isactive = 'Y'
         LEFT JOIN C_Invoice_Line_Alloc ila_settlement ON ila_settlement.C_Invoice_Candidate_ID = ic_settlement.C_Invoice_Candidate_ID AND ila_settlement.isactive = 'Y'
         LEFT JOIN C_InvoiceLine il_settlement ON il_settlement.C_InvoiceLine_ID = ila_settlement.C_InvoiceLine_ID AND il_settlement.isactive = 'Y'
/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

--LIMIT 10
;