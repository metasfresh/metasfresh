DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Root(IN record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Customs_Root(IN record_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	c_orderline_id numeric,
	c_order_id numeric,
	HasWeightSnapshot boolean
	)
AS
$$	
SELECT
 ol.AD_Org_ID,
 ol.C_OrderLine_ID,
 ol.C_Order_ID
,EXISTS(
  SELECT 0
  FROM
   M_InOut io
   INNER JOIN M_HU_Assignment thuas ON iol.M_InOutLine_ID = thuas.Record_ID AND ad_table_id = (SELECT Get_Table_ID('M_InOutLine')) AND thuas.isActive = 'Y'
   -- Get snapshot weight
   INNER JOIN M_HU_Attribute_Snapshot thuwns ON thuas.M_HU_ID = thuwns.M_HU_ID
    AND thuwns.M_Attribute_ID = ((SELECT M_Attribute_ID FROM M_Attribute WHERE value = 'WeightNet'))
    AND thuwns.Snapshot_UUID = io.Snapshot_UUID
	AND thuwns.isActive = 'Y'
  WHERE iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
   
 ) AS HasWeightSnapshot

FROM C_OrderLine ol
INNER JOIN M_ReceiptSchedule rs ON rs.C_OrderLine_ID = ol.C_OrderLine_ID AND rs.AD_Table_ID = ( SELECT Get_Table_ID('C_OrderLine') ) AND rs.isActive = 'Y'
INNER JOIN M_ReceiptSchedule_Alloc rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID AND rsa.isActive = 'Y'
INNER JOIN M_InOutLine iol ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
WHERE iol.M_InOut_ID = $1
LIMIT 1
$$
LANGUAGE sql STABLE;