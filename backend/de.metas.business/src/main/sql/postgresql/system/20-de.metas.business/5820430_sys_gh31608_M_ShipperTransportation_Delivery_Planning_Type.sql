-- Delivery Planning aggregation: give M_ShipperTransportation the same three-valued
-- direction the delivery planning already carries, and derive the direction of every
-- existing transport from its contents.
--
-- The transport order / delivery instruction so far expressed its direction through the
-- sales/purchase flag IsSOTrx, which is 'Y' on every existing row -- including the
-- purchase-side delivery instructions. Seeding the new column from that flag would write
-- that defect into the new column permanently, so the direction is derived from what the
-- transport actually contains (its delivery plannings, else the orders reachable through
-- its packages) and the flag is used only where content has no answer at all.
--
-- IsSOTrx itself is deliberately left untouched here: it is still written by its current
-- writers and still read by its single reader. It is removed in a later step, after that
-- reader has been reworked.
--
-- THIS SCRIPT MUST NEVER FAIL. It runs unattended on customer instances, so there is no
-- assertion, no RAISE and no branch that can abort: the physical column is created with a
-- DEFAULT so that no row is ever NULL, and every branch of the derivation yields one of
-- the three reference values. The two post-conditions are stated as verification queries
-- at the end of this file and are run by hand -- deliberately not as in-script asserts.
--
-- IDs fetched from the ID server (http://idserver.metas.de):
--   AD_MigrationScript -> 582043  (x10 = 5820430, this file)
--   AD_Column          -> 593410  /*From ID Server*/
--
-- Reused, NOT newly created (one vocabulary across both records, so comparing a
-- transport's direction with a planning's is a plain equality):
--   AD_Element   581679  ColumnName 'M_Delivery_Planning_Type'
--   AD_Reference 541689  list reference, values 'Incoming' / 'Outgoing' / 'Dropship'
--
-- DB lookups (deep_tundra_uat_2, port 21632):
--   AD_Table_ID of M_ShipperTransportation                      -> 540030
--   EntityType of that table, and of all 59 of its columns      -> METAS_SHIPPING
--   M_Delivery_Planning.M_Delivery_Planning_Type shape           -> VARCHAR(250), NOT NULL,
--                                                                  no DB check constraint
--
-- Written into the backup schema (one table, and it IS a backup):
--   backup.m_shippertransportation_bkp_<ts>_gh31608_direction
--                                                      full copy of the table before any change.
--                                                      Named by backup_table's own convention
--                                                      (suffix form), so the timestamp is part of
--                                                      the name and a second application makes a
--                                                      SECOND backup instead of colliding with the
--                                                      first. Discover it via backup.backup_tables,
--                                                      which backup_table registers it in -- do not
--                                                      hardcode the name anywhere.
--
-- Nothing else is written to that schema. The per-transport resolution is computed once
-- into a TEMP table (gh31608_resolution), so the UPDATE and the not-clean-cut report are
-- still driven from the SAME computation and cannot drift apart, and it disappears with
-- the session. The not-clean-cut rows are printed to the run's output rather than stored.

-- ===========================================================================
-- 1. Back up the whole table BEFORE anything is written
-- ===========================================================================
SELECT backup_table('m_shippertransportation', '_gh31608_direction');

-- ===========================================================================
-- 2. AD_Column: M_ShipperTransportation.M_Delivery_Planning_Type
-- ===========================================================================
-- Description / Help are left unset on purpose: they are owned by AD_Element 581679 and
-- would be overwritten by the propagation call below anyway.
-- 2026-08-26T22:00:00.000Z
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID,
     AD_Table_ID, AD_Element_ID,
     AD_Reference_ID, AD_Reference_Value_ID,
     ColumnName, Name, DefaultValue,
     FieldLength, IsMandatory, IsActive, IsUpdateable, IsTranslated,
     IsIdentifier, IsKey, IsParent, IsEncrypted, IsSelectionColumn,
     IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
     IsCalculated, DDL_NoForeignKey, IsSyncDatabase,
     FacetFilterSeqNo, IsFacetFilter,
     IsShowFilterIncrementButtons, IsShowFilterInline,
     SelectionColumnSeqNo, SeqNo,
     EntityType, PersonalDataCategory, Version,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (593410 /*From ID Server*/, 0, 0,
     540030 /*M_ShipperTransportation AD_Table_ID, From DB lookup*/, 581679 /*existing AD_Element, From DB lookup*/,
     17 /*List reference type*/, 541689 /*direction list reference, From DB lookup*/,
     'M_Delivery_Planning_Type', 'Lieferplanung Art', 'Outgoing',
     250, 'Y', 'Y', 'Y', 'N',
     'N', 'N', 'N', 'N', 'N',
     'Y', 'N', 'N',
     'N', 'N', 'N',
     0, 'N',
     'N', 'N',
     0, 0,
     'METAS_SHIPPING' /*same EntityType as the table and all its other columns, so no
                        IsForceIncludeInGeneratedModel is needed*/, 'NP', 0,
     TO_TIMESTAMP('2026-08-26 22:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 22:00:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- AD_Column_Trl skeleton for every active system/base language, then let the element
-- propagation fill in the localized texts.
-- 2026-08-26T22:00:01.000Z
INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-08-26 22:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 22:00:01','YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Column_ID=593410 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language=l.AD_Language
                    AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(581679 /*From DB lookup*/);

-- ===========================================================================
-- 3. Physical column
-- ===========================================================================
-- New column, so ALTER TABLE ADD COLUMN (t_alter_column needs the column to exist).
-- DEFAULT and NOT NULL are declared together on purpose: ADD COLUMN with a DEFAULT
-- populates the existing rows, so the NOT NULL is satisfied the moment it is declared and
-- cannot abort. 'Outgoing' is also the right default going forward -- it preserves today's
-- behaviour for the creation paths that hardcode a sales transport.
-- Shape follows the sibling column M_Delivery_Planning.M_Delivery_Planning_Type, which has
-- no DB check constraint either; the admissible values are enforced by the list reference.
ALTER TABLE M_ShipperTransportation
    ADD COLUMN IF NOT EXISTS M_Delivery_Planning_Type VARCHAR(250) NOT NULL DEFAULT 'Outgoing';

-- ===========================================================================
-- 4. Derive the direction of every existing transport from its contents
-- ===========================================================================
-- First matching rule wins:
--   1  the transport has delivery plannings that agree on one readable direction
--      -> that direction
--   2  it has no planning, and every order reachable through its packages is a purchase
--      order -> 'Dropship' if all of them are dropship orders, or if one of them ships to
--      the transport's own delivery location; otherwise 'Incoming'
--   3  it has no planning, and every reachable order is a sales order -> 'Outgoing'
--   4  anything else -- no content, an unreadable planning direction, disagreeing
--      plannings, or a purchase/sales mix -> fall back to IsSOTrx ('Y' -> 'Outgoing',
--      otherwise 'Incoming')
--
-- Rule 4 is the safety property: it has no precondition and its expression is total, so
-- every transport gets a direction and no row can be left out.
-- TEMP, not a stored table: the migration CLI runs this whole file through ONE psql
-- process with --single-transaction, so every statement below sees it and it disappears
-- with the session. It exists only so the UPDATE and the not-clean-cut report are driven
-- from the SAME computation and cannot drift apart.
CREATE TEMP TABLE gh31608_resolution AS
WITH planning AS (
    -- Every delivery planning reachable from a transport, with its direction.
    -- A planning that is still 'Incoming' plus the B2B flag is a dropship in the old
    -- two-field model, so its effective direction is 'Dropship' -- the same retyping the
    -- planning-side migration applies. The flag is read through to_jsonb so that this
    -- script also runs unchanged on a database where that column has already been
    -- dropped; a direct reference would abort the whole migration run there.
    SELECT a.M_ShipperTransportation_ID AS m_shippertransportation_id,
           CASE WHEN dp.M_Delivery_Planning_Type = 'Incoming'
                     AND to_jsonb(dp) ->> 'isb2b' = 'Y'
                THEN 'Dropship'
                ELSE dp.M_Delivery_Planning_Type
           END AS direction
    FROM M_Delivery_Planning_Alloc a
    JOIN M_Delivery_Planning dp ON dp.M_Delivery_Planning_ID = a.M_Delivery_Planning_ID
                               AND dp.IsActive = 'Y'
    WHERE a.IsActive = 'Y'
      AND a.M_ShipperTransportation_ID IS NOT NULL
    UNION
    SELECT dp.M_ShipperTransportation_ID,
           CASE WHEN dp.M_Delivery_Planning_Type = 'Incoming'
                     AND to_jsonb(dp) ->> 'isb2b' = 'Y'
                THEN 'Dropship'
                ELSE dp.M_Delivery_Planning_Type
           END
    FROM M_Delivery_Planning dp
    WHERE dp.IsActive = 'Y'
      AND dp.M_ShipperTransportation_ID IS NOT NULL
),
planning_agg AS (
    SELECT m_shippertransportation_id,
           count(DISTINCT direction)                                                             AS n_directions,
           count(DISTINCT direction) FILTER (WHERE direction NOT IN ('Incoming','Outgoing','Dropship')) AS n_unreadable,
           min(direction)                                                                        AS single_direction
    FROM planning
    GROUP BY m_shippertransportation_id
),
orders AS (
    -- Every order reachable through the transport's packages: the package's own order
    -- reference where it has one, otherwise the order behind its order line.
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
           count(*)                                                              AS n_orders,
           count(*) FILTER (WHERE IsSOTrx = 'N')                                 AS n_purchase,
           count(*) FILTER (WHERE IsSOTrx = 'Y')                                 AS n_sales,
           count(*) FILTER (WHERE IsSOTrx = 'N' AND IsDropShip = 'Y')            AS n_purchase_dropship,
           count(*) FILTER (WHERE IsSOTrx = 'N'
                              AND ships_to_transport_delivery_location = 'Y')    AS n_dropship_to_delivery_location
    FROM orders
    GROUP BY m_shippertransportation_id
),
classified AS (
    SELECT st.M_ShipperTransportation_ID                       AS m_shippertransportation_id,
           st.IsSOTrx                                          AS issotrx,
           pa.m_shippertransportation_id IS NOT NULL           AS has_planning,
           COALESCE(pa.n_directions, 0)                        AS n_directions,
           COALESCE(pa.n_unreadable, 0)                        AS n_unreadable,
           pa.single_direction                                 AS single_direction,
           COALESCE(oa.n_orders, 0)                            AS n_orders,
           COALESCE(oa.n_purchase, 0)                          AS n_purchase,
           COALESCE(oa.n_sales, 0)                             AS n_sales,
           COALESCE(oa.n_purchase_dropship, 0)                 AS n_purchase_dropship,
           COALESCE(oa.n_dropship_to_delivery_location, 0)     AS n_dropship_to_delivery_location,
           CASE
               WHEN pa.m_shippertransportation_id IS NOT NULL
                    AND pa.n_directions = 1 AND pa.n_unreadable = 0            THEN 1
               WHEN pa.m_shippertransportation_id IS NOT NULL                  THEN 4
               WHEN COALESCE(oa.n_orders, 0) > 0 AND oa.n_purchase = oa.n_orders THEN 2
               WHEN COALESCE(oa.n_orders, 0) > 0 AND oa.n_sales = oa.n_orders    THEN 3
               ELSE 4
           END AS rule_no
    FROM M_ShipperTransportation st
    LEFT JOIN planning_agg pa ON pa.m_shippertransportation_id = st.M_ShipperTransportation_ID
    LEFT JOIN orders_agg   oa ON oa.m_shippertransportation_id = st.M_ShipperTransportation_ID
)
SELECT c.m_shippertransportation_id,
       c.rule_no,
       CASE c.rule_no
           WHEN 1 THEN c.single_direction
           WHEN 2 THEN CASE WHEN c.n_purchase_dropship = c.n_orders
                              OR c.n_dropship_to_delivery_location > 0
                            THEN 'Dropship' ELSE 'Incoming' END
           WHEN 3 THEN 'Outgoing'
           ELSE        CASE WHEN c.issotrx = 'Y' THEN 'Outgoing' ELSE 'Incoming' END
       END AS direction,
       CASE
           WHEN c.rule_no = 2
                AND c.n_purchase_dropship > 0
                AND c.n_purchase_dropship < c.n_orders
               THEN 'rule 2 tie-break: all-purchase transport mixing '
                    || c.n_purchase_dropship || ' dropship and '
                    || (c.n_orders - c.n_purchase_dropship) || ' plain purchase order(s)'
           WHEN c.rule_no = 4 AND c.has_planning AND c.n_unreadable > 0
               THEN 'rule 4: planning direction outside the direction reference; fell back to IsSOTrx'
           WHEN c.rule_no = 4 AND c.has_planning
               THEN 'rule 4: ' || c.n_directions
                    || ' disagreeing planning directions; fell back to IsSOTrx'
           WHEN c.rule_no = 4 AND c.n_orders = 0
               THEN 'rule 4: no planning and no reachable order; fell back to IsSOTrx'
           WHEN c.rule_no = 4
               THEN 'rule 4: reachable orders mix purchase and sales ('
                    || c.n_purchase || ' purchase / ' || c.n_sales || ' of '
                    || c.n_orders || '); fell back to IsSOTrx'
           ELSE NULL
       END AS fallback_reason
FROM classified c
;

-- Apply it. `direction IS NOT NULL` is not defensive decoration: it is what makes the
-- NOT NULL declared in section 3 unfalsifiable regardless of the expression above --
-- the column starts at 'Outgoing' and this statement can only ever replace it with a
-- non-null value.
-- 2026-08-26T22:00:05.000Z
UPDATE M_ShipperTransportation st
SET M_Delivery_Planning_Type = r.direction,
    Updated   = TO_TIMESTAMP('2026-08-26 22:00:05','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
FROM gh31608_resolution r
WHERE r.m_shippertransportation_id = st.M_ShipperTransportation_ID
  AND r.direction IS NOT NULL
;

-- ===========================================================================
-- 5. Report what was not clean-cut -- to the run's own output, not to a stored table
-- ===========================================================================
-- Two kinds of row are not clean-cut: the ones rule 2 resolved through its dropship/plain
-- tie-break, and the ones rule 4 resolved from the flag because content had no answer.
-- Both keep working; a legacy mixed transport is only slightly narrower afterwards,
-- because the direction now fixes what may be added to it.
--
-- Printed rather than stored, so the applying run's log carries it -- which is the moment
-- it matters: whoever applies this to the customer instance sees in the output exactly
-- which transports were guessed at. Section 6 (c) recomputes it on demand afterwards.
SELECT r.m_shippertransportation_id,
       st.DocumentNo,
       st.IsSOTrx,
       r.rule_no,
       r.direction,
       r.fallback_reason
FROM gh31608_resolution r
JOIN M_ShipperTransportation st ON st.M_ShipperTransportation_ID = r.m_shippertransportation_id
WHERE r.fallback_reason IS NOT NULL
ORDER BY r.rule_no, st.DocumentNo
;

-- ===========================================================================
-- 6. Verification -- run by hand after applying; both must return 0
-- ===========================================================================
-- (a) nothing unset
--     SELECT count(*) FROM M_ShipperTransportation WHERE M_Delivery_Planning_Type IS NULL;
-- (b) every row still accounted for against the backup. Resolve the backup's name first --
--     it carries backup_table's timestamp, so it differs per application:
--       SELECT table_name FROM backup.backup_tables
--        WHERE table_name LIKE 'm_shippertransportation_bkp_%_gh31608_direction'
--        ORDER BY table_name DESC LIMIT 1;
--     then, substituting that name for <bkp>:
--       SELECT count(*)
--         FROM backup.<bkp> b
--         FULL JOIN M_ShipperTransportation s USING (M_ShipperTransportation_ID)
--        WHERE b.M_ShipperTransportation_ID IS NULL
--           OR s.M_ShipperTransportation_ID IS NULL;
-- (c) the not-clean-cut transports, recomputed on demand. Section 5 prints these during
--     the run; this recovers them afterwards WITHOUT the resolution table. It reports the
--     ambiguity itself -- a transport whose plannings do not agree on one direction -- which
--     is what a human acts on. It does NOT recover the deciding rule: that reasoning also
--     read M_Delivery_Planning.IsB2B, and a later script on this branch drops that column,
--     so the "why" survives only in THAT script's backup of m_delivery_planning. Hence the
--     run-time print in section 5.
--       SELECT st.M_ShipperTransportation_ID, st.DocumentNo, st.M_Delivery_Planning_Type,
--              count(DISTINCT dp.M_Delivery_Planning_Type) AS n_planning_directions
--         FROM M_ShipperTransportation st
--         JOIN M_Delivery_Planning dp
--           ON dp.M_ShipperTransportation_ID = st.M_ShipperTransportation_ID
--          AND dp.IsActive = 'Y'
--        GROUP BY st.M_ShipperTransportation_ID, st.DocumentNo, st.M_Delivery_Planning_Type
--       HAVING count(DISTINCT dp.M_Delivery_Planning_Type) > 1
--        ORDER BY st.DocumentNo;
