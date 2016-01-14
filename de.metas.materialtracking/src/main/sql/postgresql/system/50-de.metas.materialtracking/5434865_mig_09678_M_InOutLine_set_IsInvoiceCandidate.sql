
UPDATE M_InOutLine iol_outer
SET IsInvoiceCandidate='Y',
	Updated=now(),
	UpdatedBy=99
FROM (
	SELECT DISTINCT
		po_issue.PP_Order_ID,
		po_issue.Documentno, 
		po_issue.IsInvoiceCandidate AS po_issue_IsInvoiceCandidate,
		iol.Line, 
		iol.M_InOut_ID,
		iol.M_InOutLine_ID,
		iol.IsInvoiceCandidate
	FROM PP_Order po_issue
		JOIN PP_Cost_Collector cc_issue ON 
				po_issue.PP_Order_ID=cc_issue.PP_Order_ID
				AND cc_issue.DocStatus IN ('CO','CL') 
				AND cc_issue.CostCollectorType='110' /*110=ComponentIssue*/
			JOIN M_HU_Assignment hu_as_cc ON 
					hu_as_cc.AD_Table_ID=get_table_id('PP_Cost_Collector')
					AND cc_issue.PP_Cost_Collector_ID=hu_as_cc.Record_ID 
					AND hu_as_cc.M_LU_HU_ID IS NULL AND hu_as_cc.M_TU_HU_ID IS NULL AND hu_as_cc.VHU_ID IS NULL /* only the toplevel assignment */

				JOIN M_HU_Assignment hu_as_iol ON 
						hu_as_iol.AD_Table_ID=get_table_id('M_InOutLine') 
						AND hu_as_iol.M_HU_ID=hu_as_cc.M_HU_ID 
						AND hu_as_iol.M_LU_HU_ID IS NULL AND hu_as_iol.M_TU_HU_ID IS NULL AND hu_as_iol.VHU_ID IS NULL /* only the toplevel assignment */
					LEFT JOIN M_InOutLine iol ON 
							iol.M_InOutLine_ID=hu_as_iol.Record_ID
	WHERE true
		AND po_issue.IsInvoiceCandidate='Y'
		AND iol.IsInvoiceCandidate='N'
) data
WHERE data.M_InOutLine_ID=iol_outer.M_InOutLine_ID
;
