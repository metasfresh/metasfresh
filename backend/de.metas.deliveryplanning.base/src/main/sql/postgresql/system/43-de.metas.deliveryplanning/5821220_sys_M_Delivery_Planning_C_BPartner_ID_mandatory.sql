-- Require a business partner on M_Delivery_Planning -- the dictionary flag AND the physical constraint.
--
-- The partner is already required in practice: a delivery instruction's Verlader
-- (Shipper_BPartner_ID) is NOT NULL and is seeded from the planning's partner, so a planning without
-- one cannot produce an instruction. Today that surfaces late, while combining, as an unnamed
-- zero-id failure rather than a missing-mandatory-field message on the field itself.
--
-- Both sides are set on purpose. AD_Column.IsMandatory is enforced only where a row is saved through
-- the model layer, so on its own it leaves every direct writer -- raw SQL, test step definitions,
-- repositories that assemble the row themselves -- free to insert a null, and closing that path is
-- the whole point of the change. The PG NOT NULL is what closes it. AD_Column 585012 carries
-- IsSyncDatabase='N', so the flag propagates no DDL of its own; step 4 is what creates the
-- constraint.
--
-- Backfill source: the planning's OWN organisation's business partner -- AD_OrgInfo.Org_BPartner_ID
-- for the row's AD_Org_ID. That column holds the organisation represented as a C_BPartner, the same
-- record that acts as the organisation's own party on its documents and as its counterparty on
-- inter-organisation movements. So a planning naming no counterparty is attributed to the
-- organisation moving the goods, which is the only party the row itself can vouch for. No global
-- fallback partner is used: inventing a counterparty would be worse than the null it replaces.
--
-- An organisation with no Org_BPartner_ID leaves its plannings null and step 4 then FAILS the
-- migration. That is deliberate and must stay. Not every organisation has one -- the System
-- organisation '*' typically does not -- and a partnerless planning on such an organisation is data
-- a human has to look at. Migration time is a better place to discover it than a production Combine.
-- Guarding step 4 so the script passes anyway would register it as applied while leaving the column
-- nullable and the bad rows in place, which is strictly worse than the loud failure.
--
-- The AD_Field on the planning tab is left alone: its IsMandatory is NULL, i.e. it inherits from the
-- column, which is where the rule belongs.
--
-- ID from idserver.metas.de: AD_MigrationScript 5821220 /*From ID Server*/ (this file).
-- No AD row is created.

-- 1) Business table and rows are written below -- back it up first.
SELECT backup_table('m_delivery_planning', '_C_BPartner_ID_mandatory');

-- 2) Backfill. A row whose organisation has no business partner stays null on purpose; step 4 is
--    what surfaces it.
UPDATE M_Delivery_Planning dp
   SET C_BPartner_ID = (SELECT oi.Org_BPartner_ID FROM AD_OrgInfo oi WHERE oi.AD_Org_ID = dp.AD_Org_ID),
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
