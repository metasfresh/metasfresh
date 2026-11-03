-- Tax Declaration translations:
--   * C_DocType row with DocBaseType='TXD' (C_DocType_ID=541176) — Name/PrintName: base = de_DE = 'Steuererklärung', en_*/en_US = 'Tax Declaration'.
--   * AD_Ref_List entry for DocBaseType (AD_Reference_ID=183) with Value='TXD' (AD_Ref_List_ID=544233) — same convention.
-- Base language is de_DE. Per-language trl rows are explicit overrides.

-- ===== C_DocType (TXD) =====

UPDATE C_DocType
SET Name='Steuererklärung',
    PrintName='Steuererklärung',
    Updated=TIMESTAMP '2026-05-26 00:00:00',
    UpdatedBy=99
WHERE DocBaseType='TXD';

UPDATE C_DocType_Trl
SET Name='Steuererklärung',
    PrintName='Steuererklärung',
    IsTranslated='Y',
    Updated=TIMESTAMP '2026-05-26 00:00:00',
    UpdatedBy=99
WHERE C_DocType_ID=(SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='TXD')
  AND AD_Language IN ('de_DE','de_CH');

UPDATE C_DocType_Trl
SET Name='Tax Declaration',
    PrintName='Tax Declaration',
    IsTranslated='Y',
    Updated=TIMESTAMP '2026-05-26 00:00:00',
    UpdatedBy=99
WHERE C_DocType_ID=(SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='TXD')
  AND AD_Language IN ('en_US','en_GB');

-- ===== AD_Ref_List DocBaseType=TXD (AD_Reference_ID=183, Value='TXD') =====

UPDATE AD_Ref_List
SET Name='Steuererklärung',
    Updated=TIMESTAMP '2026-05-26 00:00:00',
    UpdatedBy=99
WHERE AD_Reference_ID=183 AND Value='TXD';

UPDATE AD_Ref_List_Trl
SET Name='Steuererklärung',
    IsTranslated='Y',
    Updated=TIMESTAMP '2026-05-26 00:00:00',
    UpdatedBy=99
WHERE AD_Ref_List_ID=(SELECT AD_Ref_List_ID FROM AD_Ref_List WHERE AD_Reference_ID=183 AND Value='TXD')
  AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Ref_List_Trl
SET Name='Tax Declaration',
    IsTranslated='Y',
    Updated=TIMESTAMP '2026-05-26 00:00:00',
    UpdatedBy=99
WHERE AD_Ref_List_ID=(SELECT AD_Ref_List_ID FROM AD_Ref_List WHERE AD_Reference_ID=183 AND Value='TXD')
  AND AD_Language IN ('en_US','en_GB');
