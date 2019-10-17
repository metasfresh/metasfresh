DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root( IN record_id numeric, IN bPartnerId numeric, IN datePromised date );

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
  CASE
  WHEN record_id IS NOT NULL
    THEN r.C_Order_MFGWarehouse_Report_ID = record_id
  WHEN bPartnerId IS NOT NULL AND DatePromised :: date IS NOT NULL
    THEN r.C_BPartner_ID = bPartnerId AND r.DatePromised :: date = p_datePromised :: date
  ELSE false -- shall never nappen
  END
LIMIT 1

$$
LANGUAGE sql
STABLE;