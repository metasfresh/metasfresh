-- On-switch for VAT-ID format validation on C_BPartner save.
-- Default 'Y' enables format-only (offline) validation. me03 30503.
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Value,Description,AD_Org_ID,Name,EntityType)
VALUES (0,TO_TIMESTAMP('2026-06-19 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','S',TO_TIMESTAMP('2026-06-19 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,541824 /*From ID Server*/,'Y','If Y, the format of C_BPartner.VATaxID is validated on save (offline, format-only). me03 30503.',0,'C_BPartner.validateVATaxID','D')
;
