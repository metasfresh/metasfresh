-- BPartner number-generation feature — core sysconfig gates and deactivated default sequences.
-- Three AD_SysConfig rows (blank value, client 0 / org 0) ship the sysconfig keys the
-- BPartner number-generation service reads; blank value means the feature is off for every
-- tenant until a customer explicitly sets the value via set_sysconfig_value().
-- Two AD_Sequence rows at AD_Org_ID=1000000 are inert scaffolding: they are not referenced
-- by any sysconfig row here; a customer wires them up by writing the sequence name into the
-- DebtorNoSequence / CreditorNoSequence sysconfigs.
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

-- Sysconfig: name of the AD_Sequence to use for debtor numbers (blank = feature off)
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
SELECT 0, 0, 541843 /*From ID Server*/, 'O',
       TO_TIMESTAMP('2026-08-04 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-04 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'D', 'Y',
       'de.metas.bpartner.DebtorNoSequence',
       '',
       'Name of the AD_Sequence to use for auto-generating debtor numbers. Leave blank to disable auto-generation.'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'de.metas.bpartner.DebtorNoSequence'
);

-- Sysconfig: name of the AD_Sequence to use for creditor numbers (blank = feature off)
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
SELECT 0, 0, 541844 /*From ID Server*/, 'O',
       TO_TIMESTAMP('2026-08-04 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-04 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'D', 'Y',
       'de.metas.bpartner.CreditorNoSequence',
       '',
       'Name of the AD_Sequence to use for auto-generating creditor numbers. Leave blank to disable auto-generation.'
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
