-- Drop the 'Outgoing' default on M_ShipperTransportation.TransportDirection, at both layers
-- (the physical column default AND AD_Column.DefaultValue).
--
-- The column stays NOT NULL / AD_Column.IsMandatory='Y' -- that part of the contract is
-- unchanged. What changes is that a value is no longer INVENTED for a caller that forgets to
-- set it: every application creation path now derives and sets the real direction itself
-- (shipped in the same code change as this migration), and every existing row was just
-- backfilled to the direction its content implies (5821070). With both of those true, a
-- future creation path that still forgets to set the direction must fail loudly with a NOT
-- NULL constraint violation instead of silently writing 'Outgoing' again -- that is the
-- whole point of this step, and it is why it runs LAST.
--
-- Manual creation is affected the same way, by design: the WebUI 'New' action on the
-- Transport Order (540020) and Delivery Instructions (541657) windows will show the
-- direction field EMPTY and mandatory, exactly like M_Delivery_Planning.TransportDirection
-- already does on window 541632, which carries no default at either layer and has not for
-- as long as it has existed on this branch -- this migration brings the transport/instruction
-- windows to the SAME shape that planning already proves works. No AD_Field carries its own
-- default for this column on any of the three windows (708076 / 783020 / 783021), so removing
-- the column-level default is the only change needed for the field to come up empty.
--
-- THIS SCRIPT MUST NEVER FAIL. It runs unattended on customer instances: the column is
-- already NOT NULL with a value on every row (verified by 5821070's own postcondition), so
-- dropping the default changes nothing about what is currently stored -- only what happens
-- on a future INSERT that omits the column.
--
-- IDs fetched from the ID server (http://idserver.metas.de):
--   AD_MigrationScript -> 5821080 (this file)
--
-- DB lookup (deep_tundra_uat_2, port 21632):
--   AD_Column_ID of M_ShipperTransportation.TransportDirection -> 593410 (added by 5820430,
--   unchanged by the later ColumnName rename 5820620)

-- ===========================================================================
-- 1. AD_Column: clear the dictionary-level default
-- ===========================================================================
-- 2026-08-28T13:00:00.000Z
UPDATE AD_Column
SET    DefaultValue = NULL,
       Updated      = TO_TIMESTAMP('2026-08-28 13:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Column_ID = 593410 /*From DB lookup*/
;

-- ===========================================================================
-- 2. Physical column: drop the DB default, keep NOT NULL
-- ===========================================================================
-- nullclause (4th value) is SQL NULL -- "leave the NOT NULL constraint untouched".
-- defaultclause (5th value) is the STRING 'NULL' -- altercolumn()'s documented trigger for
-- "ALTER COLUMN ... DROP DEFAULT" (see backend/de.metas.swat/de.metas.swat.base/src/main/sql/
-- postgresql/ddl/functions/altercolumn.sql, the defaultclause branch).
INSERT INTO t_alter_column VALUES('m_shippertransportation','TransportDirection','VARCHAR(250)',null,'NULL');
