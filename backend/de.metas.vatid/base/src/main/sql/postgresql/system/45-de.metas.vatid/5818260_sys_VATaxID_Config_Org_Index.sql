-- VAT-ID online check: declarative AD_Index_Table entry for the "one active config per
-- organisation" unique index, so a violation surfaces as a translatable message instead of a
-- raw Postgres error. The physical index (VATaxID_Config_AD_Org_ID_active_uidx) already exists,
-- created by 5818220_sys_VATaxID_Config_Table.sql; this migration only adds its AD metadata.
--
-- IDs allocated from idserver.metas.de:
--   AD_Index_Table  540868
--   AD_Index_Column 541537

INSERT INTO AD_Index_Table (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Index_Table_ID, AD_Table_ID, IsUnique, Name, ErrorMsg, WhereClause, EntityType)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-08-11 15:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 15:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        540868 /*From ID Server*/, 542638, 'Y', 'VATaxID_Config_AD_Org_ID_active_uidx',
        'Es kann nur eine aktive USt-IdNr.-Konfiguration pro Organisation geben.', 'IsActive=''Y''', 'D');

INSERT INTO AD_Index_Table_Trl (AD_Language, AD_Index_Table_ID, ErrorMsg, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Index_Table t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Index_Table_ID = 540868
  AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Index_Table_ID = t.AD_Index_Table_ID);

UPDATE AD_Index_Table_Trl
SET IsTranslated = 'Y', ErrorMsg = 'Only one active VAT-ID check configuration is allowed per organisation.',
    Updated = TO_TIMESTAMP('2026-08-11 15:20:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Index_Table_ID = 540868;

UPDATE AD_Index_Table_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-11 15:20:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Index_Table_ID = 540868;

INSERT INTO AD_Index_Column (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Index_Column_ID, AD_Index_Table_ID, AD_Column_ID, SeqNo, EntityType)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-08-11 15:20:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 15:20:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        541537 /*From ID Server*/, 540868, 593134, 10, 'D');
