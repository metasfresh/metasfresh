-- Function: "de.metas.handlingunits".getorderlineqtypromisedtodate(numeric, timestamp without time zone)

-- DROP FUNCTION "de.metas.handlingunits".getorderlineqtypromisedtodate(numeric, timestamp without time zone);

CREATE OR REPLACE FUNCTION "de.metas.handlingunits".getorderlineqtypromisedtodate(m_product_id numeric, datepromised timestamp without time zone)
  RETURNS numeric AS
$BODY$
	SELECT
		-- Qty ATP from orders (i.e. purchase orders - sales orders)
		COALESCE(agg_ol.QtyPromised, 0) AS QtyPromised
	FROM
	(
		SELECT
			-- Add Quantities from Purchase Orders
			-- Subtract Quantities from Sales Orders
			SUM(CASE WHEN o.IsSOTrx='Y' THEN -1 * ol.QtyReserved ELSE ol.QtyReserved END) AS QtyPromised
		FROM C_Order o
		INNER JOIN C_OrderLine ol ON (o.C_Order_ID=ol.C_Order_ID)
		WHERE ol.M_Product_ID = $1
		-- Filter by DatePromised
		-- NOTE: atm we are showing only the Qtys for current day (mark, fresh_08273, temporary workaround, fresh_GOLIVE)
		AND o.DatePromised::Date = $2::Date
		AND ol.IsHUStorageDisabled='N' /* task 08242: hide pre-go-live records */
	) agg_ol
$BODY$
  LANGUAGE sql STABLE
  COST 100;

COMMENT ON FUNCTION "de.metas.handlingunits".getorderlineqtypromisedtodate(numeric, timestamp without time zone) IS 'fresh 07701: Select all reserved quantity from orderlines to date for given Product ID';
