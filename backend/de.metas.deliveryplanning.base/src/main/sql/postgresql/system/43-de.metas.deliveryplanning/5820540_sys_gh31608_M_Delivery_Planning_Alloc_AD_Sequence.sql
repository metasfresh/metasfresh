-- 5820400 created M_Delivery_Planning_Alloc with a raw INSERT INTO AD_Table, which never goes
-- through MTable.afterSave() (backend/de.metas.adempiere.adempiere/base/src/main/java-legacy/
-- org/compiere/model/MTable.java:294-308). That hook is responsible for TWO things on a
-- Java-model-layer table save: the native PK sequence, and (via
-- ITableSequenceChecker.createOrUpdateTableSequence, TableSequenceChecker.java:220-264,
-- setIsTableID(true)) the table's AD_Sequence dictionary row. The native sequence half was
-- fixed in 5820530 (dba_seq_check_native('M_Delivery_Planning_Alloc'), after CI died on
-- 'relation "m_delivery_planning_alloc_seq" does not exist'). This script supplies the other
-- half: the AD_Sequence row, still missing after both of those scripts.
--
-- Not a functional fix: SYSTEM_NATIVE_SEQUENCE=Y on every instance this has been checked
-- against (verified live, deep_tundra_uat_2 port 21632: AD_SysConfig.Value='Y', IsActive='Y',
-- AD_Client_ID=0/AD_Org_ID=0). With that flag on, DB.getNextID() -> PO.isUseNativeSequences()
-- (org/compiere/model/PO.java:3168) takes the native-sequence branch and calls nextval()
-- straight on the physical Postgres sequence -- AD_Sequence is never consulted for ID
-- generation on that path (org/compiere/util/DB.java:2549-2582; the only escapes from that
-- branch -- migration-script logging, the "Maintain Dictionary" ini flag, or an external
-- dictionary/project ID server -- are all dev/admin-mode switches, not the normal runtime
-- save path DeliveryPlanningRepository.createAllocation() uses). So the missing row costs
-- visibility only: the table does not show up in the *Sequence* admin window for inspection or
-- manual reset, unlike every sibling allocation table (M_ReceiptSchedule_Alloc,
-- M_Delivery_Planning, C_Order_Carrier_Service, M_Product_ASI_Data all have one).
--
-- Row mirrors M_ReceiptSchedule_Alloc's AD_Sequence row field-for-field (the table 5820400's
-- own header cites as this table's design model) EXCEPT AD_Sequence_ID, Name, Description,
-- CurrentNext and the timestamps -- read live, 2026-08-27:
--   SELECT * FROM AD_Sequence WHERE Name='M_ReceiptSchedule_Alloc';
--   -> AD_Client_ID=0, AD_Org_ID=0, IsActive='Y', IsAutoSequence='Y', IncrementNo=1,
--      StartNo=1000000, CurrentNextSys=50000, IsAudited='N', IsTableID='Y', VFormat/Prefix/
--      Suffix/DateColumn/DecimalPattern/RestartFrequency/CustomSequenceNoProvider_JavaClass_ID
--      all empty/NULL.
-- Column set checked against information_schema.columns for ad_sequence on this instance: 24
-- columns, all covered by the INSERT below. No EntityType column exists on ad_sequence here --
-- checked, not assumed (the probe that flagged this as uncertain was right).
--
-- CurrentNext: NOT set to the template's 1000000. The table already holds 8 backfilled rows
-- (5820530) whose ids came from the native sequence (ids 1000023..1000030), and this instance
-- demonstrably runs a periodic sync that corrects a table's AD_Sequence.CurrentNext to match
-- its actual native-sequence position once the table has data -- M_Delivery_Planning's own row
-- (AD_Sequence_ID 556051, 9 rows) already shows CurrentNext=1000009, exactly equal to its
-- native sequence's next nextval() (last_value=1000009, is_called='f'). Since CurrentNext is
-- purely for admin-window inspection/reset on this native-sequence instance (see above -- it is
-- never read by the ID-generation path), inserting 1000000 here would misrepresent the table's
-- real position from the moment this row is created, for no benefit; inserting the true next
-- value keeps the row honest immediately. m_delivery_planning_alloc_seq here reads
-- last_value=1000031, is_called='f' (verified live) -- so CurrentNext is set to 1000031, the
-- value nextval() will actually return next. StartNo stays 1000000 (the table's designed
-- start, per the template), not the current data position.
--
-- Idempotence: NOT EXISTS on the name, matching the AD_Sequence.Name unique constraint anyway,
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
       NULL, 'Y', 1, 1000000, 1000031, 50000,
       'N', 'Y', NULL, NULL, NULL, NULL,
       NULL, NULL
WHERE NOT EXISTS (
    SELECT 1 FROM AD_Sequence WHERE Name = 'M_Delivery_Planning_Alloc'
);
