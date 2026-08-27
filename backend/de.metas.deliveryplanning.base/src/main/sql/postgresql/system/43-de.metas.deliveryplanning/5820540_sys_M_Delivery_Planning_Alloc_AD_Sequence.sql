-- 5820400 created M_Delivery_Planning_Alloc with a raw INSERT INTO AD_Table, which never goes
-- through the ORM's normal model-layer save path for a new AD_Table row. That path is
-- responsible for TWO things on a normal table creation: the native PK sequence, and the
-- table's AD_Sequence dictionary row. The native sequence half was fixed in 5820530
-- (dba_seq_check_native('M_Delivery_Planning_Alloc'), after CI died on 'relation
-- "m_delivery_planning_alloc_seq" does not exist'). This script supplies the other half: the
-- AD_Sequence row, still missing after both of those scripts.
--
-- Not a functional fix: SYSTEM_NATIVE_SEQUENCE=Y on every instance this has been checked
-- against (verified live, deep_tundra_uat_2 port 21632: AD_SysConfig.Value='Y', IsActive='Y',
-- AD_Client_ID=0/AD_Org_ID=0). With that flag on, ID generation for a new row on this table
-- takes the native-sequence branch and calls nextval() straight on the physical Postgres
-- sequence -- AD_Sequence is never consulted for ID generation on that path (the only escapes
-- from that branch -- migration-script logging, the "Maintain Dictionary" ini flag, or an
-- external dictionary/project ID server -- are all dev/admin-mode switches, not the normal
-- runtime save path this table's allocation-creation logic uses). So the missing row costs
-- visibility only: the table does not show up in the *Sequence* admin window for inspection or
-- manual reset, unlike every sibling allocation table (M_ReceiptSchedule_Alloc,
-- M_Delivery_Planning, C_Order_Carrier_Service, M_Product_ASI_Data all have one).
--
-- Row mirrors M_ReceiptSchedule_Alloc's AD_Sequence row field-for-field (the table 5820400's
-- own header cites as this table's design model) EXCEPT AD_Sequence_ID, Name, Description,
-- CurrentNext, the timestamps, and UpdatedBy -- read live, 2026-08-27. UpdatedBy diverges
-- deliberately: the template row carries 0 (it was last touched in 2015 by a process that
-- stamped the system user), while every AD-dictionary insert on this branch uses 100, which is
-- also what 5820400's own AD_Table/AD_Element rows use. Following the branch's convention is
-- right here; copying a decade-old row's audit column would not be:
--   SELECT * FROM AD_Sequence WHERE Name='M_ReceiptSchedule_Alloc';
--   -> AD_Client_ID=0, AD_Org_ID=0, IsActive='Y', IsAutoSequence='Y', IncrementNo=1,
--      StartNo=1000000, CurrentNextSys=50000, IsAudited='N', IsTableID='Y', VFormat/Prefix/
--      Suffix/DateColumn/DecimalPattern/RestartFrequency/CustomSequenceNoProvider_JavaClass_ID
--      all empty/NULL.
-- Column set checked against information_schema.columns for ad_sequence on this instance: 24
-- columns, all covered by the INSERT below. No EntityType column exists on ad_sequence here --
-- checked, not assumed (the probe that flagged this as uncertain was right).
--
-- CurrentNext: DERIVED AT APPLY TIME, not the template's 1000000 and not a literal.
--
-- The convention on this instance is that a table's AD_Sequence.CurrentNext tracks its real
-- position once the table has data: M_Delivery_Planning's own row (AD_Sequence_ID 556051, 9
-- rows) reads CurrentNext=1000009, exactly its native sequence's next nextval()
-- (last_value=1000009, is_called='f'). Shipping 1000000 would misrepresent the table from the
-- moment the row is created.
--
-- But the value MUST NOT be hardcoded to what this stack happens to read. The backfill's ids
-- come from the native sequence, whose position depends entirely on how much data the target
-- instance already has -- a customer instance with hundreds of delivery instructions lands
-- nowhere near a freshly-seeded CI database. A literal read off one stack would therefore be
-- correct on that stack and wrong on every other, which is precisely the failure mode that has
-- already cost this branch two CI cycles (a column that exists only in the newer local seed,
-- and a sequence that only an app-server boot had created locally).
--
-- So CurrentNext is computed from the table's own data: max(id) + 1, floored at StartNo for the
-- empty-table case. On this stack that evaluates to 1000031, which equals both max(id)+1 and
-- the sequence's own last_value/is_called='f' position -- verified live, and identical to the
-- literal it replaces, so the already-applied row here is unaffected.
--
-- StartNo stays 1000000 (the table's designed start, per the template), not the data position.
--
-- Idempotence: NOT EXISTS on the name. The constraint it leans on is the PARTIAL unique index
-- ad_sequence_tableidname UNIQUE (upper(Name)) WHERE IsTableID='Y' -- which covers this row --
-- and NOT ad_sequence_name, which is UNIQUE (AD_Client_ID, AD_Org_ID, Name). Either way,
-- so a re-apply can never duplicate. Defense-in-depth: the migration tool tracks this script in
-- AD_MigrationScript and runs it at most once per DB.
INSERT INTO AD_Sequence (
    AD_Sequence_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, Description,
    VFormat, IsAutoSequence, IncrementNo, StartNo, CurrentNext, CurrentNextSys,
    IsAudited, IsTableID, Prefix, Suffix, DateColumn, DecimalPattern,
    CustomSequenceNoProvider_JavaClass_ID, RestartFrequency)
SELECT 556653 /*From ID Server*/, 0, 0, 'Y',
       TO_TIMESTAMP('2026-08-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'M_Delivery_Planning_Alloc', 'Table M_Delivery_Planning_Alloc',
       NULL, 'Y', 1, 1000000,
       GREATEST(1000000, COALESCE((SELECT max(a.M_Delivery_Planning_Alloc_ID)
                                     FROM M_Delivery_Planning_Alloc a), 0) + 1),
       50000,
       'N', 'Y', NULL, NULL, NULL, NULL,
       NULL, NULL
WHERE NOT EXISTS (
    SELECT 1 FROM AD_Sequence WHERE Name = 'M_Delivery_Planning_Alloc'
);
