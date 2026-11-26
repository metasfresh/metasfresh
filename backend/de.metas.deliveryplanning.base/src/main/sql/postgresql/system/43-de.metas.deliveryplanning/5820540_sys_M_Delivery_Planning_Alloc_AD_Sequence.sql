-- Supply the AD_Sequence dictionary row for M_Delivery_Planning_Alloc. The table was created by a raw
-- INSERT INTO AD_Table, which bypasses the model-layer save path that creates both the native PK
-- sequence and this dictionary row.
--
-- Not a functional fix: with SYSTEM_NATIVE_SEQUENCE='Y' the ID generation for a new row calls nextval()
-- on the physical Postgres sequence and never consults AD_Sequence. The missing row costs visibility
-- only -- the table does not appear in the Sequence admin window for inspection or manual reset,
-- unlike every sibling allocation table.
--
-- The row mirrors M_ReceiptSchedule_Alloc's, except that CurrentNext is DERIVED AT APPLY TIME as
-- max(id)+1 floored at StartNo. It must not be a literal: the ids come from the native sequence, whose
-- position depends on how much data the target instance already holds, so a value read off one stack
-- would be right there and wrong everywhere else. StartNo stays at the table's designed 1000000.
--
-- Idempotence: NOT EXISTS on the name, leaning on the partial unique index ad_sequence_tableidname
-- UNIQUE (upper(Name)) WHERE IsTableID='Y'.
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
