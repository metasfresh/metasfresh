-- Replaces the retired C_BPartner.validateVATaxID SysConfig. That SysConfig was the ONLY gate the
-- save-time format check (de.metas.vatid.interceptor.C_BPartner / C_BPartner_Location) read, while
-- VATaxIDCheckService always resolved format-check-on/off per organisation from VATaxID_Config -- two
-- gates for one business question, so an organisation's own VATaxID_Config.IsFormatCheckEnabled=N had
-- no effect on the save.
--
-- VATaxIDConfigRepository#getByOrgId now resolves BOTH halves itself: an organisation WITH a
-- VATaxID_Config record always uses that record's own IsFormatCheckEnabled column; an organisation
-- WITHOUT one falls back to THIS System-level SysConfig. Every caller (both interceptors and
-- VATaxIDCheckService) asks the same repository, so they cannot diverge again.

-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5819160 (this file's prefix)
--   AD_SysConfig 541849

INSERT INTO AD_SysConfig (
  AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, Name, Value, Description, ConfigurationLevel,
  EntityType, IsActive, Created, CreatedBy, Updated, UpdatedBy
) VALUES (
  0, 0, 541849 /*From ID Server*/, 'VATaxID_Config.IsFormatCheckEnabledByDefault', 'Y',
  'Whether the VAT-ID save-time format check is enforced for an organisation that has NO VATaxID_Config record. Has no effect on an organisation that DOES have a VATaxID_Config record -- that record''s own IsFormatCheckEnabled column governs instead.',
  'S', 'D', 'Y',
  TO_TIMESTAMP('2026-08-14 09:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-08-14 09:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100
);

-- Drop the retired predecessor gate (system row + any client/org overrides) -- nothing reads it anymore.
SELECT backup_table('ad_sysconfig', '_31060_validateVATaxID');

DELETE FROM AD_SysConfig WHERE Name = 'C_BPartner.validateVATaxID';
