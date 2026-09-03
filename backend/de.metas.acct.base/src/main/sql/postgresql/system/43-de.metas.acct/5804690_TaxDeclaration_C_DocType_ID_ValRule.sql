-- Tax Declaration — filter C_DocType_ID dropdown to DocBaseType='TXD'
-- Caught during UAT walkthrough on https://danthermuat.metasfresh.com: the
-- "Belegart" / "Document Type" field on the Tax Declaration header tab
-- (AD_Field=780485 / AD_Column=592580 on AD_Table=C_TaxDeclaration) was rendering
-- every C_DocType row instead of only Tax Declaration doctypes. Same pattern
-- as existing AD_Val_Rule rows 102 (GL Journals), 121 (GL Documents),
-- 125 (Shipments/Receipts), 126 (AR Pro Forma): one rule per DocBaseType.

INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, Type, Code, EntityType)
VALUES (540789 /*From ID Server*/, 0, 0, 'Y',
    TIMESTAMP '2026-05-26 00:00:00', 100, TIMESTAMP '2026-05-26 00:00:00', 100,
    'C_DocType Tax Declaration', 'S', 'C_DocType.DocBaseType=''TXD''', 'de.metas.acct');

UPDATE AD_Column SET AD_Val_Rule_ID = 540789,
    Updated = TIMESTAMP '2026-05-26 00:00:01', UpdatedBy = 100
WHERE AD_Column_ID = 592580;
