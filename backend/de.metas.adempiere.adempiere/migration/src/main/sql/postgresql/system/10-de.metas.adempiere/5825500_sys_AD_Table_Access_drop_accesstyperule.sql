-- 2026-09-21
-- AD_Table_Access: remove AccessTypeRule and narrow the primary key to (AD_Role_ID, AD_Table_ID).
--
-- WHY
-- AccessTypeRule separated a role's rows for one table into three permission sets (Accessing /
-- Reporting / Exporting). With every flag on the table now three-state (5825480), a row that has
-- nothing to say about an aspect simply says nothing, so the rule type no longer separates anything
-- and the key collapses to one row per role and table.
--
-- WHAT
--   * removes the AccessTypeRule AD_Column (8844-series id 10009), its AD_Element (2225), its field
--     8349 on tab 482 of window 268, and the ref-list behind it (AD_Reference 293);
--   * drops the physical column;
--   * restates the primary key explicitly as (AD_Role_ID, AD_Table_ID) rather than leaving it to
--     whatever Postgres does when a key column disappears.
--
-- Row counts measured across 316 instances on 2026-09-21: 302 returned zero rows in this table,
-- none returned a non-zero count, 14 were unreachable. So the narrowing has nothing to collide
-- with. Should an unchecked instance turn out to hold two rows for one role and table, ADD PRIMARY
-- KEY below rejects them by itself - loudly, and without writing anything.

-- 1. Backups. AD_Table_Access holds operator-configured permission data and is about to lose a
--    column; AD_DefaultValue loses a row's IsActive. Both are cheap insurance against an instance
--    that turns out to hold rows after all.
SELECT backup_table('ad_table_access', '_gh27893_AccessTypeRule');
SELECT backup_table('ad_defaultvalue', '_gh27893_AccessType');

-- 2. Deactivate the AD_DefaultValue row behind AccessTypeRule's '@SQL=' default ('A' = Accessing),
--    while the column it is keyed on still exists. Same treatment IsExclude's row got in 5825480.
UPDATE AD_DefaultValue
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-09-21 11:00:01','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE IsActive = 'Y'
   AND AD_Column_ID = (SELECT AD_Column_ID FROM AD_Column
                        WHERE ColumnName = 'AccessTypeRule'
                          AND AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName='AD_Table_Access'))
;

-- 3. The AccessTypeRule fields, with their full FK chain. On a vanilla instance that is field 8349
--    "Access Type" on tab 482, but the deletes are anchored on AD_Column_ID rather than on that id:
--    a customer override window (AD_Window.Overrides_Window_ID=268) would carry its own AD_Field on
--    the same column, and a hardcoded id would leave it behind to block the AD_Column delete below.
--    AD_UI_Element / AD_UI_ElementField need no clean-up because tab 482 has no AD_UI_Section rows;
--    the other four dependents are not gated on that and are cleared unconditionally. They are
--    empty here, but they accumulate on a long-lived instance from ordinary WebUI use - a user who
--    once sorted or hid the "Access Type" grid column leaves a row behind.
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID=10009)
;

DELETE FROM AD_Field_ContextMenu WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID=10009)
;

DELETE FROM AD_UserDef_Field WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID=10009)
;

DELETE FROM AD_User_SortPref_Line WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID=10009)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID=10009)
;

DELETE FROM AD_Field WHERE AD_Column_ID=10009
;

-- 3b. Close the grid-sequence gap the DELETE leaves at SeqNoGrid=70. The Swing client does this
--     automatically on a field delete; raw SQL does not, so the three fields that sat behind
--     "Access Type" move up one slot. Form SeqNo is left as it is, matching that same behaviour.
UPDATE AD_Field SET SeqNoGrid=70, Updated=TO_TIMESTAMP('2026-09-21 11:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=6714
;

UPDATE AD_Field SET SeqNoGrid=80, Updated=TO_TIMESTAMP('2026-09-21 11:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=8320
;

UPDATE AD_Field SET SeqNoGrid=90, Updated=TO_TIMESTAMP('2026-09-21 11:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=8321
;

-- 4. Column AD_Table_Access.AccessTypeRule. Two tables reach the column directly rather than
--    through a field, so they go first for the same reason as step 3's per-user tables: empty here,
--    but not necessarily on an instance someone has configured. AD_Column_Access is role-based
--    column-level security. AD_Field_ContextMenu carries two independent nullable FKs - step 3
--    cleared the rows reachable by AD_Field_ID, this clears the ones that name the column directly.
DELETE FROM AD_Column_Access WHERE AD_Column_ID=10009
;

DELETE FROM AD_Field_ContextMenu WHERE AD_Column_ID=10009
;

DELETE FROM AD_Column_Trl WHERE AD_Column_ID=10009
;

DELETE FROM AD_Column WHERE AD_Column_ID=10009
;

-- 5. Element AccessTypeRule (2225). Nothing else referenced it: column 10009 was its only usage.
DELETE FROM AD_Element_Link WHERE AD_Element_ID=2225
;

DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2225
;

DELETE FROM AD_Element WHERE AD_Element_ID=2225
;

-- 6. Reference 293 "AD_Table_Access RuleType" and its three entries (A Accessing / R Reporting /
--    E Exporting). Column 10009 was its only consumer, so it is deactivated rather than deleted:
--    an inactive reference is invisible to administrators and still readable on an instance whose
--    backup rows carry the old values.
UPDATE AD_Ref_List
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-09-21 11:00:07','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Reference_ID = 293
;

UPDATE AD_Reference
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-09-21 11:00:08','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Reference_ID = 293
;

-- 7. Physical column and primary key. The key is dropped first and restated explicitly afterwards,
--    so the script says what the key becomes instead of leaving it to fall out of the column drop.
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access DROP CONSTRAINT ad_table_access_pkey');
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access DROP COLUMN AccessTypeRule');
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access ADD CONSTRAINT ad_table_access_pkey PRIMARY KEY (AD_Role_ID, AD_Table_ID)');
