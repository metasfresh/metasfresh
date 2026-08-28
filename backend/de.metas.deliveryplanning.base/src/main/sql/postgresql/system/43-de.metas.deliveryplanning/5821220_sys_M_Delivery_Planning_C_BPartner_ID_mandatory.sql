-- M_Delivery_Planning.C_BPartner_ID becomes mandatory in the application dictionary.
--
-- The partner is already required in practice: a delivery instruction's Verlader
-- (Shipper_BPartner_ID) is NOT NULL and is seeded from the planning's partner, so a planning without
-- one cannot produce an instruction. Today that surfaces late, as an unnamed zero-id failure while
-- combining, instead of as a missing-mandatory-field message on the field itself. Every creation
-- path in use supplies the partner, but nothing in the model required it; the flag makes the model
-- state what is already true, so the save path and the WebUI reject the gap at entry.
--
-- Metadata only, deliberately: a physical NOT NULL would assert that no row on any instance has a
-- null partner, and one such row aborts the entire migration batch -- and unlike a flag or a status
-- column, a partner reference has no safe backfill value with which to clear the way first.
-- AD_Column 585012 carries IsSyncDatabase='N', so setting the flag triggers no DDL of its own.
--
-- The AD_Field on the planning tab is left untouched on purpose: its IsMandatory is NULL, i.e. it
-- inherits from the column, which is where the rule belongs.
--
-- ID from idserver.metas.de: AD_MigrationScript 5821220 /*From ID Server*/ (this file).
-- No AD row is created.

UPDATE AD_Column
   SET IsMandatory='Y',
       Updated=TO_TIMESTAMP('2026-08-28 14:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=585012 /* M_Delivery_Planning.C_BPartner_ID */
;
