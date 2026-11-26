-- Drop the 'Outgoing' default on M_ShipperTransportation.TransportDirection, at both layers
-- (the physical column default AND AD_Column.DefaultValue).
--
-- The column stays NOT NULL / AD_Column.IsMandatory='Y'. What changes is that a value is no
-- longer invented for a caller that forgets to set it: a creation path that omits the direction
-- must fail loudly on NOT NULL instead of silently writing 'Outgoing'.
--
-- Manual creation is affected the same way: the WebUI 'New' action on the Transport Order (540020)
-- and Delivery Instructions (541657) windows shows the direction field EMPTY and mandatory, the
-- same shape M_Delivery_Planning.TransportDirection already has on window 541632. No AD_Field
-- carries its own default for this column on any of the three windows (708076 / 783020 / 783021),
-- so removing the column-level default is the only change needed for the field to come up empty.
--
-- THIS SCRIPT MUST NEVER FAIL. It runs unattended on customer instances: the column is already
-- NOT NULL with a value on every row, so dropping the default changes nothing about what is
-- currently stored -- only what happens on a future INSERT that omits the column.
--
-- IDs fetched from the ID server (http://idserver.metas.de):
--   AD_MigrationScript -> 5821080 (this file)
--
-- AD_Column_ID of M_ShipperTransportation.TransportDirection -> 593410

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
-- nullclause (4th value) is SQL NULL -- leave the NOT NULL constraint untouched. defaultclause
-- (5th value) is the STRING 'NULL' -- altercolumn()'s trigger for "ALTER COLUMN ... DROP DEFAULT".
INSERT INTO t_alter_column VALUES('m_shippertransportation','TransportDirection','VARCHAR(250)',null,'NULL');
