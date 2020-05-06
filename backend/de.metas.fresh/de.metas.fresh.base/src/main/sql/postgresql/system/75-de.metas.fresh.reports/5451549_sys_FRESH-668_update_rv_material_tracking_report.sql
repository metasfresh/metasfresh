DROP VIEW IF EXISTS RV_M_Material_Tracking_Report;


CREATE VIEW RV_M_Material_Tracking_Report
AS
(
	SELECT 
		mr.M_Material_Tracking_Report_ID,
		mrl.M_Material_Tracking_Report_Line_ID, -- in case we need it some day
		asi.Description,
		mr.DateDoc,
		per.name as periodName,
		p.value||' '||p.name  as productName,
		mrl.QtyReceived,
		mrl.QtyIssued,
		mrl.DifferenceQty
		
		
	FROM M_Material_Tracking_Report mr
	JOIN C_Period per ON mr.C_Period_ID = per.C_Period_ID AND per.isActive = 'Y' -- period
	JOIN M_Material_Tracking_Report_Line mrl ON mr.M_Material_Tracking_Report_ID = mrl.M_Material_Tracking_Report_ID AND mrl.isActive = 'Y' -- report line
	JOIN M_AttributeSetInstance asi on mrl.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID AND asi.isActive = 'Y'
	JOIN M_Product p on mrl.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y' -- product
	WHERE mr.isActive = 'Y'
) ;
