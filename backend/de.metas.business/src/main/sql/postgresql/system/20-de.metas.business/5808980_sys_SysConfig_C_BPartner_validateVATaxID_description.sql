-- 2026-06-19T10:30:00.000Z
-- Reword the C_BPartner.validateVATaxID description: drop the internal ticket reference (the
-- Description column is shown to admins in the System Configurator window) and mention both
-- validated columns.
UPDATE AD_SysConfig SET Description='If Y, the format of C_BPartner.VATaxID and C_BPartner_Location.VATaxID is validated on save (offline, format-only).',Updated=TO_TIMESTAMP('2026-06-19 10:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Name='C_BPartner.validateVATaxID'
;
