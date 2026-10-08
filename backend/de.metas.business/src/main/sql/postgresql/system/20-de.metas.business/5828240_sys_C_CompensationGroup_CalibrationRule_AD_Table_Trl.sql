-- AD_Table_Trl rows for C_CompensationGroup_CalibrationRule (AD_Table 542655): skeleton for all system languages,
-- German base text, en_US override. Prefix 5828240 allocated from idserver.metas.de.
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Table_ID = 542655
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID);

UPDATE AD_Table_Trl SET Name = 'Calibration rule', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-10-06 12:10:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Table_ID = 542655;

UPDATE AD_Table_Trl SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-10-06 12:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Table_ID = 542655;
