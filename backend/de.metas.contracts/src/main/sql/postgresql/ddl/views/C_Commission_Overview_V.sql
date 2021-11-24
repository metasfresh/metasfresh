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
--LIMIT 10
;