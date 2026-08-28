-- Require a business partner on M_Delivery_Planning -- the dictionary flag AND the physical constraint.
--
-- The partner is already required in practice: a delivery instruction's Verlader
-- (Shipper_BPartner_ID) is seeded from the planning's partner and written on through to the shipping
-- package rows, so a planning without one cannot produce a correct instruction.
--
-- What each side buys. AD_Column.IsMandatory='Y' is enforced where a row is saved through the model
-- layer, and it makes the model state a rule that already holds. It does NOT buy a field-level
-- message on this window and no one should expect one: the planning tab has IsInsertRecord='N' and
-- the field is unconditionally read-only, so nobody creates a planning or types a partner there.
-- The PG NOT NULL is what closes the remaining path -- every direct writer that bypasses the model
-- layer (raw SQL, test step definitions, repositories that assemble the row themselves) -- and
-- closing that is the point of the change. AD_Column 585012 carries IsSyncDatabase='N', so the flag
-- propagates no DDL of its own; step 4 is what creates the constraint.
--
-- Backfill source: the row's OWN links, in the order the writers use. A planning is generated from a
-- receipt schedule (incoming) or a shipment schedule (outgoing), and the generator takes the partner
-- straight off that schedule; the order line and the order are the weaker links the same row also
-- carries. So the partner is recovered from exactly where it came from, and a backfilled row is
-- indistinguishable from one written correctly in the first place.
--
-- NOT from the organisation's own business partner (AD_OrgInfo.Org_BPartner_ID): that is a different
-- party, not a weaker version of the right one. Substituting it would hand every backfilled row a
-- Verlader of "ourselves", and because the instruction and its packages copy the planning's partner,
-- a later Combine would emit a shipping document addressed to the wrong party -- silently, and
-- manufactured by the very migration meant to prevent partnerless plannings. Measured on the live
-- stack: the four links reproduce the stored partner on every row, while the organisation's partner
-- differs from it on every row.
--
-- No IsActive filter on the four subqueries, deliberately: each selects by primary key, so it returns
-- at most one row, and a deactivated schedule or order still records truthfully which partner the
-- planning was created for.
--
-- A row with none of the four links stays null and step 4 then FAILS the migration. That is
-- deliberate and must stay: such a row has no recoverable partner, so it is data a human has to look
-- at, and migration time is a better place to discover it than a production Combine. Guarding step 4
-- so the script passes anyway would register it as applied while leaving the column nullable and the
-- bad rows in place. Section 5 tells the operator which rows to look at when that happens.
--
-- ID from idserver.metas.de: AD_MigrationScript 5821220 /*From ID Server*/ (this file).
-- No AD row is created.

-- 1) Business table and rows are written below -- back it up first.
SELECT backup_table('m_delivery_planning', '_C_BPartner_ID_mandatory');

-- 2) Backfill from the row's own links, in the generators' precedence order. A row with no link at
--    all stays null on purpose; step 4 is what surfaces it.
UPDATE M_Delivery_Planning dp
   SET C_BPartner_ID = COALESCE(
           (SELECT rs.C_BPartner_ID FROM M_ReceiptSchedule  rs WHERE rs.M_ReceiptSchedule_ID  = dp.M_ReceiptSchedule_ID),
           (SELECT ss.C_BPartner_ID FROM M_ShipmentSchedule ss WHERE ss.M_ShipmentSchedule_ID = dp.M_ShipmentSchedule_ID),
           (SELECT ol.C_BPartner_ID FROM C_OrderLine        ol WHERE ol.C_OrderLine_ID        = dp.C_OrderLine_ID),
           (SELECT o.C_BPartner_ID  FROM C_Order            o  WHERE o.C_Order_ID             = dp.C_Order_ID)),
       Updated   = TO_TIMESTAMP('2026-08-28 14:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 99
 WHERE dp.C_BPartner_ID IS NULL
;

-- 3) Step 2 writes a foreign-key column, which leaves deferred referential checks pending, and
--    Postgres refuses to ALTER a table that still has them. Fire them here instead of at COMMIT, so
--    a genuine violation also reports against the statement that caused it.
SET CONSTRAINTS ALL IMMEDIATE;

-- 4) Declare it mandatory on both sides. The 5th argument is null: a mandatory foreign key gets a
--    constraint, never a DB default.
UPDATE AD_Column
   SET IsMandatory='Y',
       Updated=TO_TIMESTAMP('2026-08-28 14:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=585012 /* M_Delivery_Planning.C_BPartner_ID */
;
INSERT INTO t_alter_column values('m_delivery_planning','C_BPartner_ID','NUMERIC(10)','NOT NULL',null)
;

-- ===========================================================================
-- 5. IF STEP 4 ABORTED with 'column "c_bpartner_id" ... contains null values'
-- ===========================================================================
-- Postgres names neither the rows nor the reason, so start here -- these are plannings whose four
-- links are all empty, i.e. rows no automatic source can attribute:
--   SELECT M_Delivery_Planning_ID, AD_Org_ID, M_ReceiptSchedule_ID, M_ShipmentSchedule_ID,
--          C_OrderLine_ID, C_Order_ID, DocumentNo
--     FROM M_Delivery_Planning WHERE C_BPartner_ID IS NULL;
-- Each one needs a human decision -- give it the partner it belongs to, or deactivate it if it is
-- junk -- and then the migration is re-run. Do NOT work around this by relaxing step 4.
--
-- ===========================================================================
-- 6. Verification -- run by hand after applying
-- ===========================================================================
-- (a) SELECT column_name, is_nullable FROM information_schema.columns
--      WHERE table_name='m_delivery_planning' AND column_name='c_bpartner_id';
--     -- expect is_nullable = NO
-- (b) SELECT IsMandatory, IsSyncDatabase FROM AD_Column WHERE AD_Column_ID=585012;
--     -- expect Y and N
-- (c) SELECT count(*) FROM M_Delivery_Planning WHERE C_BPartner_ID IS NULL;
--     -- expect 0 (step 4 could not have succeeded otherwise)
-- (d) UPDATE M_Delivery_Planning SET C_BPartner_ID=NULL
--      WHERE M_Delivery_Planning_ID=(SELECT min(M_Delivery_Planning_ID) FROM M_Delivery_Planning);
--     -- must ERROR with a not-null violation; run inside BEGIN ... ROLLBACK
