-- Catch-up backfill for M_ShipperTransportation.TransportDirection.
--
-- A prior migration (5820430) already derived every existing row's direction from its
-- content once, at the time it ran. Every application creation path still relied on the
-- column's 'Outgoing' default afterwards -- that gap is closed in the same code change this
-- script ships with, so every creation path now derives and sets the direction itself. But
-- any row created BETWEEN 5820430 running and that code fix landing can still carry the old
-- default instead of its real direction -- most visibly a delivery instruction generated
-- from an Incoming or Dropship delivery planning, which read 'Outgoing' regardless. This
-- script re-applies the same content-based derivation to catch exactly those rows.
--
-- Same two rules as 5820430's rules 1-3 (planning first, else orders reachable through
-- active shipping packages), narrowed for the CURRENT schema:
--   - M_Delivery_Planning.TransportDirection is already a genuine three-value column -- the
--     IsB2B special case 5820430 needed is gone, that column was dropped later on this
--     branch (5820510).
--   - M_ShipperTransportation.IsSOTrx no longer exists (dropped later on this branch,
--     5820850), so there is no flag left to fall back to for a row with neither planning nor
--     a reachable order. Such a row is left UNCHANGED here rather than guessed: "keeps the
--     direction its contents imply" means exactly that when there is nothing to imply.
--
-- THIS SCRIPT MUST NEVER FAIL. It runs unattended on customer instances: no assertion, no
-- RAISE, no branch that can abort. The column is already NOT NULL with a value on every row,
-- and this UPDATE only ever replaces a row's existing value with another valid one or leaves
-- it untouched.
--
-- IDs fetched from the ID server (http://idserver.metas.de):
--   AD_MigrationScript -> 5821070 (this file)
--
-- Nothing else changes here: no AD_Column touched, no default removed from the physical
-- column or from AD_Column.DefaultValue -- that is the LAST step of this branch's direction
-- work, applied only after this backfill, in its own script.

SELECT backup_table('m_shippertransportation', '_direction_catchup');

-- TEMP, not a stored table: the migration CLI runs this whole file through ONE psql process
-- with --single-transaction, so the UPDATE below sees it and it disappears with the session.
CREATE TEMP TABLE tmp_direction_catchup AS
WITH planning AS (
    -- Every delivery planning reachable from a transport, with its direction. Two paths
    -- reach a transport: through an active allocation (the normal case), and through the
    -- planning's own M_ShipperTransportation_ID (a direct reference some writers set
    -- without going through the allocation table). Both are read the same way 5820430 read
    -- them, so a row this script would touch is exactly a row that one would also have
    -- touched had it existed at the time.
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

-- Apply it. `direction IS NOT NULL` skips every row rule 0 left with nothing to derive,
-- and `IS DISTINCT FROM` skips every row that already carries its derived value -- so this
-- UPDATE only ever touches a row whose content disagrees with what it currently stores.
-- 2026-08-28T12:50:00.000Z
UPDATE M_ShipperTransportation st
SET TransportDirection = t.direction,
    Updated   = TO_TIMESTAMP('2026-08-28 12:50:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
FROM tmp_direction_catchup t
WHERE t.m_shippertransportation_id = st.M_ShipperTransportation_ID
  AND t.direction IS NOT NULL
  AND t.direction IS DISTINCT FROM st.TransportDirection
;

-- ===========================================================================
-- Verification -- run by hand after applying
-- ===========================================================================
-- (a) nothing left unset (must always be 0, the column is NOT NULL regardless):
--     SELECT count(*) FROM M_ShipperTransportation WHERE TransportDirection IS NULL;
-- (b) the rows this script actually changed, direction distribution before vs. after --
--     resolve the backup's name first (it carries backup_table's timestamp):
--       SELECT backup_table_name FROM backup.backup_tables
--        WHERE backup_table_name LIKE 'backup.m_shippertransportation_bkp_%_direction_catchup'
--        ORDER BY backup_table_name DESC LIMIT 1;
--     then, substituting that name for <bkp>:
--       SELECT b.TransportDirection AS before, s.TransportDirection AS after, count(*)
--         FROM backup.<bkp> b
--         JOIN M_ShipperTransportation s USING (M_ShipperTransportation_ID)
--        WHERE b.TransportDirection IS DISTINCT FROM s.TransportDirection
--        GROUP BY 1, 2 ORDER BY 1, 2;
