DROP VIEW IF EXISTS C_Commission_Overview_V;
CREATE OR REPLACE VIEW C_Commission_Overview_V AS
SELECT
	-- ROW_NUMBER() OVER ( ORDER BY ci.C_Commission_Instance_ID, cs.C_Commission_Share_ID) AS C_Commission_Overview_V_ID,
	-- Make sure that every view record has a unique and stable ID
	CASE
		WHEN ila_settlement.C_Invoice_Line_Alloc_ID IS NOT NULL THEN (ila_settlement.C_Invoice_Line_Alloc_ID * 10) + 1
		WHEN ic_settlement.C_Invoice_Candidate_ID IS NOT NULL THEN (ic_settlement.C_Invoice_Candidate_ID * 10) + 2
		WHEN cs.C_Commission_Share_ID IS NOT NULL THEN (cs.C_Commission_Share_ID * 10) + 3
		ELSE (ci.C_Commission_Instance_ID * 10) + 4
	END AS C_Commission_Overview_V_ID,
	ci.C_Commission_Instance_ID,
	ci.Created,
	ci.CreatedBy,
	ci.Updated,
	ci.UpdatedBy,
	ci.AD_Client_ID,
	ci.AD_Org_ID,
	ci.IsActive,
	ci.CommissionDate,
	ci.POreference,
	ci.M_Product_Order_ID,
	ci.CommissionTrigger_Type,
	ci.MostRecentTriggerTimestamp,
	ci.PointsBase_Forecasted,
	ci.PointsBase_Invoiceable,
	ci.PointsBase_Invoiced,
	-- only one of them will be not-null, and ic_sales is the more frequent case
	COALESCE(ic_sales.QtyEntered, il_sales.QtyEntered) AS QtyEntered,
	COALESCE(ic_sales.C_UOM_ID, il_sales.C_UOM_ID) AS C_UOM_ID,
	ci.Bill_BPartner_ID,
	ci.C_Invoice_Candidate_ID,
	ci.C_Order_ID,
	CASE WHEN ic_sales.AD_Table_ID=get_table_id('C_OrderLine') THEN ic_sales.Record_ID ELSE NULL END AS C_OrderLine_ID,
	ci.C_Invoice_ID,
	ci.C_InvoiceLine_ID,
	cs.C_Commission_Share_ID,
	cs.LevelHierarchy,
	cs.C_BPartner_SalesRep_ID,
	cs.C_Flatrate_Term_ID,
	cs.C_CommissionSettingsLine_ID,
	cs.PointsSum_ToSettle,
	cs.PointsSum_Settled,
    cs.isSimulation,
	CASE 
		WHEN (ci.PointsBase_Forecasted+ci.PointsBase_Invoiceable+ci.PointsBase_Invoiced)!=0
		THEN ROUND((cs.PointsSum_ToSettle+cs.PointsSum_Settled)/(ci.PointsBase_Forecasted+ci.PointsBase_Invoiceable+ci.PointsBase_Invoiced)*100, 0)
		ELSE NULL
	END as percentofbasepoints,
	ic_settlement.C_Invoice_Candidate_ID AS C_Invoice_Candidate_Commission_ID,
	il_settlement.C_InvoiceLine_ID AS C_InvoiceLine_Commission_ID,
	il_settlement.C_Invoice_ID AS C_Invoice_Commission_ID
	
FROM C_Commission_Instance ci
	LEFT JOIN C_Invoice_Candidate ic_sales ON ic_sales.C_Invoice_Candidate_ID=ci.C_Invoice_Candidate_ID
	LEFT JOIN C_InvoiceLine il_sales ON il_sales.C_InvoiceLine_ID=ci.C_InvoiceLine_ID
	LEFT JOIN C_Commission_Share cs ON cs.C_Commission_Instance_ID=ci.C_Commission_Instance_ID
		LEFT JOIN C_Invoice_Candidate ic_settlement ON ic_settlement.Record_ID=cs.C_Commission_Share_ID AND ic_settlement.AD_Table_ID=get_table_id('C_Commission_Share')
			LEFT JOIN C_Invoice_Line_Alloc ila_settlement ON ila_settlement.C_Invoice_Candidate_ID=ic_settlement.C_Invoice_Candidate_ID
				LEFT JOIN C_InvoiceLine il_settlement ON il_settlement.C_InvoiceLine_ID=ila_settlement.C_InvoiceLine_ID
--LIMIT 10
;
--select * from C_Commission_Overview_V
