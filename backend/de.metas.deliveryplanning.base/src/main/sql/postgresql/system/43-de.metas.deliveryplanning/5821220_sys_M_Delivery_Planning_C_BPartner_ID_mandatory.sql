-- Require a business partner on M_Delivery_Planning -- the dictionary flag AND the physical constraint.
--
-- The partner is already required in practice: a delivery instruction's Verlader (Shipper_BPartner_ID)
-- is seeded from the planning's partner and written on through to the shipping package rows.
--
-- Both sides are needed. AD_Column.IsMandatory='Y' states the rule for rows saved through the model
-- layer; the PG NOT NULL is what closes every direct writer that bypasses it. AD_Column 585012 carries
-- IsSyncDatabase='N', so the flag propagates no DDL of its own -- step 5 creates the constraint.
--
-- Backfill source: the row's OWN links, in the order the writers use. A planning is generated from a
-- receipt schedule (incoming) or a shipment schedule (outgoing) and the generator takes the partner
-- straight off that schedule; the order line and the order are the weaker links the same row carries.
--
-- NOT from the organisation's own business partner (AD_OrgInfo.Org_BPartner_ID): that is a different
-- party, not a weaker version of the right one. It would give every backfilled row a Verlader of
-- "ourselves", and since instruction and packages copy the planning's partner, a later Combine would
-- emit a shipping document addressed to the wrong party.
--
-- No IsActive filter on the four subqueries: each selects by primary key, so it returns at most one
-- row, and a deactivated schedule or order still records truthfully which partner the planning was for.
--
-- ID from idserver.metas.de: AD_MigrationScript 5821220 /*From ID Server*/ (this file).
-- No AD row is created.

-- 1) Business table and rows are written below -- back it up first.
SELECT backup_table('m_delivery_planning', '_C_BPartner_ID_mandatory');

-- 2) Backfill from the row's own links, in the generators' precedence order. A row with no link at
--    all stays null on purpose; step 3's pre-flight is what surfaces it.
UPDATE M_Delivery_Planning dp
   SET C_BPartner_ID = COALESCE(
           (SELECT rs.C_BPartner_ID FROM M_ReceiptSchedule  rs WHERE rs.M_ReceiptSchedule_ID  = dp.M_ReceiptSchedule_ID),
           (SELECT ss.C_BPartner_ID FROM M_ShipmentSchedule ss WHERE ss.M_ShipmentSchedule_ID = dp.M_ShipmentSchedule_ID),
           (SELECT ol.C_BPartner_ID FROM C_OrderLine        ol WHERE ol.C_OrderLine_ID        = dp.C_OrderLine_ID),
           (SELECT o.C_BPartner_ID  FROM C_Order            o  WHERE o.C_Order_ID             = dp.C_Order_ID)),
       Updated   = TO_TIMESTAMP('2026-08-28 14:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE dp.C_BPartner_ID IS NULL
;

-- 3) Pre-flight: if any planning is still partnerless after the backfill, abort HERE with a message
--    that names the rows, instead of letting step 5's ALTER produce a bare
--    'column "c_bpartner_id" ... contains null values'. The whole file runs in one transaction, so
--    nothing written above survives the abort.
DO $$
DECLARE
    v_partnerless integer;
    v_sample      text;
BEGIN
    SELECT count(*) INTO v_partnerless
      FROM M_Delivery_Planning WHERE C_BPartner_ID IS NULL;

    IF v_partnerless > 0 THEN
        SELECT string_agg(x.id::text, ', ') INTO v_sample
          FROM (SELECT M_Delivery_Planning_ID AS id
                  FROM M_Delivery_Planning
                 WHERE C_BPartner_ID IS NULL
                 ORDER BY M_Delivery_Planning_ID
                 LIMIT 20) x;

        RAISE EXCEPTION
            'M_Delivery_Planning.C_BPartner_ID mandatory: % planning row(s) still have no business '
            'partner after the backfill - all four recovery links (M_ReceiptSchedule, '
            'M_ShipmentSchedule, C_OrderLine, C_Order) are empty on them, so no automatic source can '
            'attribute them. M_Delivery_Planning_ID(s) (first 20): %. Each one needs a human '
            'decision - give it the partner it belongs to, or deactivate it if it is junk - then '
            're-run this script. Do NOT relax the NOT NULL to get past this.',
            v_partnerless, v_sample;
    END IF;
END $$;

-- 4) Step 2 writes a foreign-key column, which leaves deferred referential checks pending, and
--    Postgres refuses to ALTER a table that still has them. Fire them here instead of at COMMIT, so
--    a genuine violation also reports against the statement that caused it.
SET CONSTRAINTS ALL IMMEDIATE;

-- 5) Declare it mandatory on both sides. The 5th argument is null: a mandatory foreign key gets a
--    constraint, never a DB default.
UPDATE AD_Column
   SET IsMandatory='Y',
       Updated=TO_TIMESTAMP('2026-08-28 14:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=585012 /* M_Delivery_Planning.C_BPartner_ID */
;
INSERT INTO t_alter_column values('m_delivery_planning','C_BPartner_ID','NUMERIC(10)','NOT NULL',null)
;
