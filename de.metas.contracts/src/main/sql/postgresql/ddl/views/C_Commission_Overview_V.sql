DROP VIEW IF EXISTS C_Commission_Overview_V;
CREATE OR REPLACE VIEW C_Commission_Overview_V AS
SELECT
	ROW_NUMBER() OVER ( ORDER BY ci.C_Commission_Instance_ID, cs.C_Commission_Share_ID) AS C_Commission_Overview_V_ID,
	ci.C_Commission_Instance_ID,
	ci.Created,
	ci.CreatedBy,
	ci.Updated,
	ci.UpdatedBy,
	ci.AD_Client_ID,
	ci.AD_Org_ID,
	ci.IsActive,
	ci.MostRecentTriggerTimestamp,
	ci.PointsBase_Forecasted,
	ci.PointsBase_Invoiceable,
	ci.PointsBase_Invoiced,
	ic_sales.M_Product_ID,
	ic_sales.QtyEntered,
	ic_sales.C_UOM_ID,
	ci.Bill_BPartner_ID,
	ci.C_Order_ID,
	ic_sales.Record_ID AS C_OrderLine_ID,
	cs.C_Commission_Share_ID,
	cs.LevelHierarchy,
	cs.C_BPartner_SalesRep_ID,
	cs.PointsSum_ToSettle,
	cs.PointsSum_Settled,
	CASE 
		WHEN (ci.PointsBase_Forecasted+ci.PointsBase_Invoiceable+ci.PointsBase_Invoiced)!=0
		THEN ROUND((cs.PointsSum_ToSettle+cs.PointsSum_Settled)/(ci.PointsBase_Forecasted+ci.PointsBase_Invoiceable+ci.PointsBase_Invoiced)*100, 0)
		ELSE NULL
	END as percentofbasepoints,
	il_settlement.C_InvoiceLine_ID,
	il_settlement.C_Invoice_ID
	
FROM C_Commission_Instance ci
	JOIN C_Invoice_Candidate ic_sales ON ic_sales.C_Invoice_Candidate_ID=ci.C_Invoice_Candidate_ID
		JOIN M_Product p ON p.M_producT_ID = ic_sales.M_Product_ID
-- not needed if we only need their IDs
--		JOIN C_BPartner bp_bill ON bp_bill.C_BPartner_ID = ic_sales.Bill_BPartner_ID
--		LEFT JOIN C_OrderLine ol ON ic_sales.Record_ID=ol.C_OrderLine_ID AND ic_sales.AD_Table_ID=get_table_id('C_OrderLine')
--			LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
	JOIN C_Commission_Share cs ON cs.C_Commission_Instance_ID=ci.C_Commission_Instance_ID
		LEFT JOIN LATERAL (
			select distinct cf.C_Invoice_Candidate_Commission_ID AS C_Invoice_Candidate_ID
			from C_Commission_Fact cf 
			where cf.C_Commission_Share_ID=cs.C_Commission_Share_ID AND C_Invoice_Candidate_Commission_ID IS NOT NULL
		) ic_settlement ON true
			LEFT JOIN C_Invoice_Line_Alloc ila_settlement ON ila_settlement.C_Invoice_Candidate_ID=ic_settlement.C_Invoice_Candidate_ID
				LEFT JOIN C_InvoiceLine il_settlement ON il_settlement.C_InvoiceLine_ID=ila_settlement.C_InvoiceLine_ID
-- not needed if we only need its ID				
					LEFT JOIN C_Invoice i_settlement ON i_settlement.C_Invoice_ID=il_settlement.C_Invoice_ID
--LIMIT 10
;
--select * from C_Commission_Overview_V