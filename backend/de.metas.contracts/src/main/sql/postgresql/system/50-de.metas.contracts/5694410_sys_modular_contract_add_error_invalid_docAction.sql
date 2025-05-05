-- Value: de.metas.contracts.DocAction
-- 2023-07-04T11:42:43.412845300Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545283, 0, TO_TIMESTAMP('2023-07-04 13:42:43.245', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Diese Dokument Aktion ist für Dokumente mit Vertragsbaustein nicht erlaubt!', 'E', TO_TIMESTAMP('2023-07-04 13:42:43.245', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.DocActionNotAllowed')
;

-- 2023-07-04T11:42:43.419629500Z
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545283
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.DocActionNotAllowed
-- 2023-07-04T11:43:19.270968400Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-07-04 13:43:19.27', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545283
;

-- Value: de.metas.contracts.DocActionNotAllowed
-- 2023-07-04T11:43:20.524681700Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-07-04 13:43:20.524', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545283
;

-- Value: de.metas.contracts.DocActionNotAllowed
-- 2023-07-04T11:44:11.809646800Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='This Document Action is for Documents with modular Contract not allowed!', Updated=TO_TIMESTAMP('2023-07-04 13:44:11.809', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545283
;

