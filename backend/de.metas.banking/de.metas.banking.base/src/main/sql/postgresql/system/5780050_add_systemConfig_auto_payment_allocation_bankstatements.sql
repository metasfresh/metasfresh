-- 2025-12-08T17:16:43.741Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541779,'S',TO_TIMESTAMP('2025-12-08 18:16:43.551','YYYY-MM-DD HH24:MI:SS.US'),100,'When enabled (Y):
- System searches for existing payments matching the bank statement line before creating new payments
- Matching criteria used:
  * Business Partner
  * Payment Amount and Currency
  * Payment Direction (Receipt vs Payment)
  * Document Status = Completed
  * Not already reconciled
- If exactly ONE matching payment is found, it will be linked to the bank statement line
- If MULTIPLE matches are found, no automatic linking occurs (requires manual user action)
- If NO match is found, a new payment will be created

When disabled (N):
- System always creates a new payment for each bank statement line
- No matching attempt is performed
- May result in duplicate payments if corresponding payments already exist
','de.metas.banking','Y','de.metas.banking.payment.findMatchingPayment.enabled',TO_TIMESTAMP('2025-12-08 18:16:43.551','YYYY-MM-DD HH24:MI:SS.US'),100,'Y')
;

