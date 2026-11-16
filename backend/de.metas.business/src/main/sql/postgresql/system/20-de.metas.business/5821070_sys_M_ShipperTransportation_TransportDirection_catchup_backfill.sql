-- Catch-up backfill for M_ShipperTransportation.TransportDirection: re-derive each row's direction
-- from its content, so a row whose direction was written before every creation path derived it -- most
-- visibly a delivery instruction generated from an Incoming or Dropship planning -- carries its real
-- direction rather than the column's 'Outgoing' default.
--
-- The derivation reads the plannings reachable from the transport first, then the orders reachable
-- through its active shipping packages. A transport with neither is left UNCHANGED rather than guessed:
-- "keeps the direction its contents imply" means exactly that when there is nothing to imply.
--
-- THIS SCRIPT MUST NEVER FAIL. It runs unattended on customer instances: no assertion, no RAISE, no
-- branch that can abort. The column is already NOT NULL with a value on every row, and this UPDATE only
-- ever replaces an existing value with another valid one or leaves it untouched.
--
-- IDs fetched from the ID server (http://idserver.metas.de):
--   AD_MigrationScript -> 5821070 (this file)
SELECT backup_table('m_shippertransportation', '_direction_catchup');

-- TEMP, not a stored table: the whole file runs in one psql process under --single-transaction, so the
-- UPDATE below sees it and it disappears with the session.
CREATE TEMP TABLE tmp_direction_catchup AS
WITH planning AS (
    -- Every delivery planning reachable from a transport, with its direction. Two paths reach a
    -- transport: through an active allocation (the normal case), and through the planning's own
    -- M_ShipperTransportation_ID, which some writers set directly.
    SELECT a.M_ShipperTransportation_ID AS m_shippertransportation_id,
           dp.TransportDirection AS direction
    FROM M_Delivery_Planning_Alloc a
    JOIN M_Delivery_Planning dp ON dp.M_Delivery_Planning_ID = a.M_Delivery_Planning_ID
                               AND dp.IsActive = 'Y'
    WHERE a.IsActive = 'Y'
      AND a.M_ShipperTransportation_ID IS NOT NULL
    UNION
    SELECT dp.M_ShipperTransportation_ID,
           dp.TransportDirection
    FROM M_Delivery_Planning dp
    WHERE dp.IsActive = 'Y'
      AND dp.M_ShipperTransportation_ID IS NOT NULL
),
planning_agg AS (
    SELECT m_shippertransportation_id,
           count(DISTINCT direction) AS n_directions,
           min(direction) AS single_direction
    FROM planning
    GROUP BY m_shippertransportation_id
),
orders AS (
    -- Every order reachable through the transport's active packages: the package's own
    -- order reference where it has one, otherwise the order behind its order line.
    SELECT DISTINCT
           st.M_ShipperTransportation_ID AS m_shippertransportation_id,
           o.C_Order_ID,
           o.IsSOTrx,
           o.IsDropShip,
           CASE WHEN o.IsDropShip = 'Y'
                     AND o.DropShip_Location_ID IS NOT NULL
                     AND o.DropShip_Location_ID = st.C_BPartner_Location_Delivery_ID
                THEN 'Y' ELSE 'N'
           END AS ships_to_transport_delivery_location
    FROM M_ShipperTransportation st
    JOIN M_ShippingPackage sp ON sp.M_ShipperTransportation_ID = st.M_ShipperTransportation_ID
                             AND sp.IsActive = 'Y'
    LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID = sp.C_OrderLine_ID
    JOIN C_Order o ON o.C_Order_ID = COALESCE(sp.C_Order_ID, ol.C_Order_ID)
),
orders_agg AS (
    SELECT m_shippertransportation_id,
           count(*)                                                           AS n_orders,
           count(*) FILTER (WHERE IsSOTrx = 'N')                              AS n_purchase,
           count(*) FILTER (WHERE IsSOTrx = 'Y')                              AS n_sales,
           count(*) FILTER (WHERE IsSOTrx = 'N' AND IsDropShip = 'Y')         AS n_purchase_dropship,
           count(*) FILTER (WHERE IsSOTrx = 'N'
                              AND ships_to_transport_delivery_location = 'Y') AS n_dropship_to_delivery_location
    FROM orders
    GROUP BY m_shippertransportation_id
),
classified AS (
    -- First matching rule wins:
    --   1  the transport has plannings that agree on one direction -> that direction
    --   2  it has no planning, and every reachable order is a purchase order ->
    --      'Dropship' if all of them are dropship orders, or if one of them ships to the
    --      transport's own delivery location; otherwise 'Incoming'
    --   3  it has no planning, and every reachable order is a sales order -> 'Outgoing'
    --   0  anything else -- no content, disagreeing plannings, or a purchase/sales mix ->
    --      leave the row's current value untouched (see below)
    SELECT st.M_ShipperTransportation_ID AS m_shippertransportation_id,
           CASE
               WHEN pa.m_shippertransportation_id IS NOT NULL AND pa.n_directions = 1     THEN 1
               WHEN COALESCE(oa.n_orders, 0) > 0 AND oa.n_purchase = oa.n_orders          THEN 2
               WHEN COALESCE(oa.n_orders, 0) > 0 AND oa.n_sales = oa.n_orders             THEN 3
               ELSE 0
           END AS rule_no,
           pa.single_direction,
           COALESCE(oa.n_orders, 0)             AS n_orders,
           COALESCE(oa.n_purchase_dropship, 0)  AS n_purchase_dropship,
           COALESCE(oa.n_dropship_to_delivery_location, 0) AS n_dropship_to_delivery_location
    FROM M_ShipperTransportation st
    LEFT JOIN planning_agg pa ON pa.m_shippertransportation_id = st.M_ShipperTransportation_ID
    LEFT JOIN orders_agg   oa ON oa.m_shippertransportation_id = st.M_ShipperTransportation_ID
)
SELECT c.m_shippertransportation_id,
       CASE c.rule_no
           WHEN 1 THEN c.single_direction
           WHEN 2 THEN CASE WHEN c.n_purchase_dropship = c.n_orders
                              OR c.n_dropship_to_delivery_location > 0
                            THEN 'Dropship' ELSE 'Incoming' END
           WHEN 3 THEN 'Outgoing'
           ELSE        NULL -- rule 0: nothing to derive from, current value is left alone below
       END AS direction
FROM classified c
;

-- `direction IS NOT NULL` skips every row rule 0 left with nothing to derive, and `IS DISTINCT FROM`
-- skips every row that already carries its derived value, so this UPDATE only touches a row whose
-- content disagrees with what it stores.
UPDATE M_ShipperTransportation st
SET TransportDirection = t.direction,
    Updated   = TO_TIMESTAMP('2026-08-28 12:50:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
FROM tmp_direction_catchup t
WHERE t.m_shippertransportation_id = st.M_ShipperTransportation_ID
  AND t.direction IS NOT NULL
  AND t.direction IS DISTINCT FROM st.TransportDirection
;
