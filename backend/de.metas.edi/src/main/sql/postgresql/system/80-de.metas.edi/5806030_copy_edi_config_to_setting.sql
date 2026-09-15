-- EDI location routing — one-time data copy from C_BPartner into C_BPartner_EDI_Setting
--
-- Context: The EDI configuration columns (IsEdiDesadvRecipient, EdiDesadvRecipientGLN,
-- EdiDESADVSendingMode, EdiDESADV_ExternalSystem_Config_ID, EdiDESADVDefaultItemCapacity,
-- IsEdiInvoicRecipient, EdiInvoicRecipientGLN, EdiINVOICSendingMode,
-- EdiINVOIC_ExternalSystem_Config_ID) are being moved from C_BPartner to the new child table
-- C_BPartner_EDI_Setting (created in migration 5805760).
--
-- This script creates one partner-default row (C_BPartner_Location_ID = NULL) in
-- C_BPartner_EDI_Setting for every C_BPartner that is currently an EDI recipient
-- (IsEdiDesadvRecipient='Y' OR IsEdiInvoicRecipient='Y'), copying all EDI field values.
--
-- The old C_BPartner EDI columns are dropped in the NEXT migration — do NOT drop them here.
--
-- Idempotent: skips partners that already have a NULL-location row in C_BPartner_EDI_Setting.

INSERT INTO C_BPartner_EDI_Setting (
    C_BPartner_EDI_Setting_ID,
    AD_Client_ID,
    AD_Org_ID,
    IsActive,
    Created,
    CreatedBy,
    Updated,
    UpdatedBy,
    C_BPartner_ID,
    IsEdiDesadvRecipient,
    EdiDesadvRecipientGLN,
    EdiDESADVSendingMode,
    EdiDESADV_ExternalSystem_Config_ID,
    EdiDESADVDefaultItemCapacity,
    IsEdiInvoicRecipient,
    EdiInvoicRecipientGLN,
    EdiINVOICSendingMode,
    EdiINVOIC_ExternalSystem_Config_ID
)
SELECT
    nextval('c_bpartner_edi_setting_seq'),
    bp.AD_Client_ID,
    bp.AD_Org_ID,
    'Y',
    TO_TIMESTAMP('2026-06-03 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    99,
    TO_TIMESTAMP('2026-06-03 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    99,
    bp.C_BPartner_ID,
    bp.IsEdiDesadvRecipient,
    bp.EdiDesadvRecipientGLN,
    bp.EdiDESADVSendingMode,
    bp.EdiDESADV_ExternalSystem_Config_ID,
    bp.EdiDESADVDefaultItemCapacity,
    bp.IsEdiInvoicRecipient,
    bp.EdiInvoicRecipientGLN,
    bp.EdiINVOICSendingMode,
    bp.EdiINVOIC_ExternalSystem_Config_ID
FROM C_BPartner bp
WHERE (bp.IsEdiDesadvRecipient = 'Y' OR bp.IsEdiInvoicRecipient = 'Y')
  AND NOT EXISTS (
      SELECT 1
      FROM C_BPartner_EDI_Setting s
      WHERE s.C_BPartner_ID = bp.C_BPartner_ID
        AND s.C_BPartner_Location_ID IS NULL
  )
;

-- Re-align the native sequence with the new MAX so application-side INSERTs don't collide.
SELECT public.dba_seq_check_native('C_BPartner_EDI_Setting')
;
