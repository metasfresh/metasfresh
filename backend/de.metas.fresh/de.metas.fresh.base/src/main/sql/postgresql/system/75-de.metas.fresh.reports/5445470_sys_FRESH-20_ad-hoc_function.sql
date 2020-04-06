
CREATE OR REPLACE FUNCTION report.fresh_monthly_warehouse_stats(month_enddate date)
  RETURNS TABLE(Value text, Name text, Empfangen numeric, Ausgelagert numeric, Differenz numeric) AS
$BODY$
SELECT p.Value, p.Name, --mt.Lot,
		SUM(iol.MovementQty) AS Empfangen, 
		SUM(ppo.QtyIssued) AS Ausgelagert,
		SUM(iol.MovementQty)-SUM(ppo.QtyIssued) AS Differenz
FROM M_Material_Tracking mt
	JOIN M_Product p ON p.M_Product_ID=mt.M_Product_ID
	LEFT JOIN (
		SELECT mtr_iol.M_Material_Tracking_ID, iol.M_Product_ID, SUM(iol.MovementQty) AS MovementQty
		FROM M_Material_Tracking_ref mtr_iol 
			JOIN M_InOutLine iol ON iol.M_InOutLine_ID=mtr_iol.Record_ID
				JOIN M_InOut io ON io.M_InOut_ID=iol.M_InOut_ID 
		WHERE mtr_iol.AD_Table_ID=get_table_id('M_InOutLine')
			AND mtr_iol.IsActive='Y'
			AND io.DocStatus IN ('CO', 'CL')
			AND io.MovementDate<=$1
		GROUP BY mtr_iol.M_Material_Tracking_ID, iol.M_Product_ID
	) iol ON iol.M_Material_Tracking_ID=mt.M_Material_Tracking_ID AND iol.M_Product_ID=mt.M_Product_ID
	LEFT JOIN (
		SELECT ppo.M_Material_Tracking_ID, SUM(mtr_ppo.QtyIssued) AS QtyIssued
		FROM  M_Material_Tracking_ref mtr_ppo
			JOIN PP_Order ppo ON ppo.PP_Order_ID=mtr_ppo.Record_ID --AND ppo.M_Material_Tracking_ID=mtr_ppo.M_Material_Tracking_ID
		WHERE mtr_ppo.AD_Table_ID=get_table_id('PP_Order')
			AND mtr_ppo.IsActive='Y'
			AND ppo.DocStatus='CL'
			AND COALESCE(ppo.DateDelivered, ppo.DateFinishSchedule)<=$1
		GROUP BY ppo.M_Material_Tracking_ID
	) ppo ON ppo.M_Material_Tracking_ID=mt.M_Material_Tracking_ID
WHERE true
	AND mt.ValidFrom<=$1
	AND mt.ValidTo>=$1
--	AND mt.Lot='15010601010101'
--	AND p.Value='P000362'
GROUP BY p.Value, p.Name--, mt.Lot, mt.QtyIssued
--HAVING SUM(ppo.QtyIssued) != mt.QtyIssued
ORDER BY p.Value --, mt.Lot
$BODY$
  LANGUAGE sql STABLE
;
COMMENT ON FUNCTION report.fresh_monthly_warehouse_stats(date) IS 'Statistik für monatliche Lagermeldung; Valie examples for "month_enddate" are 2016-01-31 or 2016-02-29. Also see issue FRESH-20.';

--select * from report.fresh_monthly_warehouse_stats('2016-04-30');
