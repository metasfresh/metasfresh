CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh(IN DateFrom date, IN DateTo date)
  RETURNS void AS
$BODY$

    DELETE FROM X_MRP_ProductInfo_Detail_MV WHERE DateGeneral::Date >= $1 AND DateGeneral::Date <= $2;

	-- do the "actual" refreshing
    INSERT INTO X_MRP_ProductInfo_Detail_MV
    SELECT * FROM X_MRP_ProductInfo_Detail_V($1, $2);

    INSERT INTO X_MRP_ProductInfo_Detail_MV
    SELECT * FROM X_MRP_ProductInfo_Detail_Fallback_V($1, $2);

    SELECT "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP($1, $2);

$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh()
  OWNER TO adempiere;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh() IS 'tasks 08681 and 08682: refreshes the table X_MRP_ProductInfo_Detail_MV.
1. deleting existing records
2. calling X_MRP_ProductInfo_Detail_MV so add new records
3. calling X_MRP_ProductInfo_Detail_Fallback_V to supplement "fallback" records
4. updating fresh_qtymrp using the view "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V

Note that all thes steps are only done with records that have a DateGeneral range from 4 day back to 4 days into the future.
';


CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh()
  RETURNS VOID AS
$BODY$
	SELECT X_MRP_ProductInfo_Detail_MV_Refresh(
		(now() - interval '4 day')::date, 
		(now() + interval '4 days')::date);
$BODY$
LANGUAGE sql VOLATILE;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh() IS 'Invokes X_MRP_ProductInfo_Detail_MV_Refresh(date, date) for a timespan of 4 days back until 4 days into the future.'
