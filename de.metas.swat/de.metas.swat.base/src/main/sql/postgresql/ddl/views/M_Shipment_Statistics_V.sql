DROP VIEW IF EXISTS M_Shipment_Statistics_V;
CREATE OR REPLACE VIEW M_Shipment_Statistics_V AS 
 SELECT
	iol.M_InOutLine_ID as M_Shipment_Statistic_V_ID,
	io.AD_Client_ID,
	io.AD_Org_ID,
	io.DocumentNo as DocumentNo,
	io.MovementDate as MovementDate,
	
	bp.Value as bpValue,
	bp.Name as bpName,
	bp.Postal as Postal,
	
	p.Value as ProductValue,
	p.Name as ProductName,
	iol.SubProducer_BPartner_ID,
	p.Package_UOM_ID,
	iol.C_UOM_ID,
	p.M_DosageForm_ID,
	iol.QtyEntered as Qty,
	p.Manufacturer as Manufacturer, 
	p.PackageSize as PackageSize
	
	
	
 FROM M_InOut io
	JOIN C_BPartner bp ON io.C_BPartner_ID  = bp.C_BPartner_ID
	JOIN M_InOutLine iol on io.M_InOut_ID = iol.M_InOut_ID
	JOIN M_Product p on iol.M_Product_ID = p.M_Product_ID
    
 WHERE 
	io.IsSOTRX = 'Y' 
	
ORDER BY io.DocumentNo, p.Value
;
