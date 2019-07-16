--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root( IN record_id numeric, IN bPartnerId numeric, IN datePromised date );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root(IN record_id    numeric,
                                                                                           IN bPartnerId   numeric,
                                                                                           IN p_datePromised date)
  RETURNS TABLE
  (
    ad_org_id  numeric,
    docstatus  character(2),
    printname  character varying(60),
    c_order_id integer,
	C_Order_MFGWarehouse_Report_ID integer
  )
AS
$$
SELECT
  r.AD_Org_ID,
  r.DocStatus,
  r.PrintName,
  r.C_Order_ID :: int,
  r.C_Order_MFGWarehouse_Report_ID :: int
FROM report.RV_C_Order_MFGWarehouse_Report_Header r
WHERE
  r.C_Order_MFGWarehouse_Report_ID = record_id
  
UNION 


SELECT
	o.AD_Org_ID,
	o.DocStatus,
	dt.PrintName,
	o.C_Order_ID :: int,
   null as C_Order_MFGWarehouse_Report_ID
FROM C_Order o
	INNER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
WHERE o.C_BPartner_ID = bPartnerId AND o.DatePromised :: date = p_datePromised :: date
  
  
LIMIT 1

$$
LANGUAGE sql
STABLE;


