-- BPartner number-generation feature — core sysconfig gates and pre-wired default sequences.
-- Three AD_SysConfig rows (client 0 / org 0) ship the sysconfig keys the BPartner number-generation
-- service reads.
--   NumberResolverOverride ships blank + IsActive='Y' (no shipped default; customer sets a DB function name).
--   DebtorNoSequence / CreditorNoSequence ship Value=AD_Sequence_ID of the default sequence + IsActive='N'.
--     IsActive='N' means SysConfigDAO's active-filtered lookup treats the row as "not configured"
--     (feature off) until a customer flips IsActive='Y', which activates the shipped default sequence
--     with no additional id-lookup step.
-- Two AD_Sequence rows at AD_Org_ID=1000000 are the default sequences pre-wired into the sysconfigs above.
--
-- IDs allocated from idserver.metas.de on 2026-08-04:
--   AD_SysConfig 541842 (de.metas.bpartner.NumberResolverOverride)
--   AD_SysConfig 541843 (de.metas.bpartner.DebtorNoSequence)
--   AD_SysConfig 541844 (de.metas.bpartner.CreditorNoSequence)
--   AD_Sequence  556615 (BPartner_DebtorNo_Default)
--   AD_Sequence  556616 (BPartner_CreditorNo_Default)

-- Sysconfig: override switch (blank = no override; set to a schema-qualified DB function name to activate)
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
SELECT 0, 0, 541842 /*From ID Server*/, 'O',
       TO_TIMESTAMP('2026-08-04 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-04 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'D', 'Y',
       'de.metas.bpartner.NumberResolverOverride',
       '',
       'SQL function name (plain or schema-qualified, e.g. public.fn_bpartner_no) for custom number resolution. Leave blank to use sequence-based generation.'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'de.metas.bpartner.NumberResolverOverride'
);

-- Sysconfig: AD_Sequence_ID to use for debtor numbers (IsActive='N' = feature off until customer activates)
-- Value=556615 is the AD_Sequence_ID of BPartner_DebtorNo_Default (created below in this script).
-- IsActive='N': SysConfigDAO filters WHERE IsActive='Y', so this row is invisible until a customer
-- sets IsActive='Y', which activates the shipped default sequence with no further id-lookup step.
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
SELECT 0, 0, 541843 /*From ID Server*/, 'O',
       TO_TIMESTAMP('2026-08-04 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-04 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'D', 'N',
       'de.metas.bpartner.DebtorNoSequence',
       '556615',
       'AD_Sequence_ID to use for auto-generating debtor numbers. Set IsActive=Y to activate the shipped default sequence (ID 556615 = BPartner_DebtorNo_Default).'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'de.metas.bpartner.DebtorNoSequence'
);

-- Sysconfig: AD_Sequence_ID to use for creditor numbers (IsActive='N' = feature off until customer activates)
-- Value=556616 is the AD_Sequence_ID of BPartner_CreditorNo_Default (created below in this script).
-- IsActive='N': SysConfigDAO filters WHERE IsActive='Y', so this row is invisible until a customer
-- sets IsActive='Y', which activates the shipped default sequence with no further id-lookup step.
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
SELECT 0, 0, 541844 /*From ID Server*/, 'O',
       TO_TIMESTAMP('2026-08-04 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-04 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'D', 'N',
       'de.metas.bpartner.CreditorNoSequence',
       '556616',
       'AD_Sequence_ID to use for auto-generating creditor numbers. Set IsActive=Y to activate the shipped default sequence (ID 556616 = BPartner_CreditorNo_Default).'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'de.metas.bpartner.CreditorNoSequence'
);

-- Default debtor-number sequence at org 1000000 (deactivated — unreferenced scaffolding).
-- StartNo and CurrentNext = 10000; IsTableID='N'; IsActive='N' keeps it inert.
INSERT INTO AD_Sequence (AD_Client_ID, AD_Org_ID, AD_Sequence_ID,
                         Created, CreatedBy, Updated, UpdatedBy,
                         Name, Description,
                         IsActive, IsAutoSequence, IsTableID, IsAudited,
                         IncrementNo, StartNo, CurrentNext, CurrentNextSys)
SELECT 1000000, 1000000, 556615 /*From ID Server*/,
       TO_TIMESTAMP('2026-08-04 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-04 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'BPartner_DebtorNo_Default',
       'Default sequence for BPartner debtor numbers. Wire up via sysconfig de.metas.bpartner.DebtorNoSequence to activate.',
       'N', 'Y', 'N', 'N',
       1, 10000, 10000, 100
WHERE NOT EXISTS (
    SELECT 1 FROM AD_Sequence WHERE Name = 'BPartner_DebtorNo_Default'
);

-- Default creditor-number sequence at org 1000000 (deactivated — unreferenced scaffolding).
-- StartNo and CurrentNext = 70000; IsTableID='N'; IsActive='N' keeps it inert.
INSERT INTO AD_Sequence (AD_Client_ID, AD_Org_ID, AD_Sequence_ID,
                         Created, CreatedBy, Updated, UpdatedBy,
                         Name, Description,
                         IsActive, IsAutoSequence, IsTableID, IsAudited,
                         IncrementNo, StartNo, CurrentNext, CurrentNextSys)
SELECT 1000000, 1000000, 556616 /*From ID Server*/,
       TO_TIMESTAMP('2026-08-04 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-04 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'BPartner_CreditorNo_Default',
       'Default sequence for BPartner creditor numbers. Wire up via sysconfig de.metas.bpartner.CreditorNoSequence to activate.',
       'N', 'Y', 'N', 'N',
       1, 70000, 70000, 100
WHERE NOT EXISTS (
    SELECT 1 FROM AD_Sequence WHERE Name = 'BPartner_CreditorNo_Default'
);
