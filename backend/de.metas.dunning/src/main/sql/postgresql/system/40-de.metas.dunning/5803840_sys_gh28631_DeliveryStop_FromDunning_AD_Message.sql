-- gh#28631: Translatable AD_Message for the auto-generated DeliveryStopReason
-- produced by DunningBL.makeDeliveryStopIfNeeded(). Replaces the hard-coded
-- German prefix "Dunning: <level> (DocNo <no>)" used previously.
--
-- IDs allocated from idserver.metas.de on 2026-05-21:
--   AD_Message          545688
--   AD_MigrationScript  5803840 (this file's prefix)

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES (545688 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-21 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-21 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        'DeliveryStopReason_FromDunning',
        'Mahnung: {0} (Belegnr. {1})',
        'I',
        'de.metas.dunning');

-- Skeleton _Trl rows for all active system languages.
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText, IsTranslated)
SELECT 545688, AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-05-21 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-05-21 10:00', 'YYYY-MM-DD HH24:MI'), 0,
       'Mahnung: {0} (Belegnr. {1})', 'N'
FROM AD_Language
WHERE IsSystemLanguage = 'Y'
  AND AD_Language NOT IN (SELECT AD_Language FROM AD_Message_Trl WHERE AD_Message_ID = 545688);

-- English translation.
UPDATE AD_Message_Trl
SET MsgText = 'Dunning: {0} (DocNo {1})',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-21 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Message_ID = 545688 AND AD_Language = 'en_US';
