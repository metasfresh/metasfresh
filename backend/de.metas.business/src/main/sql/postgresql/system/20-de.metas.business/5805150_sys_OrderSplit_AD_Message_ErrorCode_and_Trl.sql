-- Follow-up for the OrderSplit validation AD_Messages (registered earlier in 5804900):
--   * Set ErrorCode on both MsgType='E' rows so API consumers can handle them
--     programmatically.
--   * Seed AD_Message_Trl for de_DE and de_CH (base text is already German, just
--     copy the MsgText and mark IsTranslated='Y'). The earlier script only seeded
--     en_US.
-- Timestamps are hardcoded (TO_TIMESTAMP) per metasfresh-db convention so the script
-- is deterministic across DB replays.

-- Set ErrorCode on both error messages
UPDATE AD_Message
   SET ErrorCode = 'ORDER_SPLIT_NO_SHIPMENTS',
       Updated = TO_TIMESTAMP('2026-05-28 06:30:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE Value = 'C_Order_Split_NoShipments';

UPDATE AD_Message
   SET ErrorCode = 'ORDER_SPLIT_NOTHING_TO_SPLIT',
       Updated = TO_TIMESTAMP('2026-05-28 06:30:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE Value = 'C_Order_Split_NothingToSplit';

-- Seed AD_Message_Trl for de_DE and de_CH (base text is German, IsTranslated='Y').
-- en_US is already inserted by 5804900. The SELECT … WHERE NOT EXISTS guard keeps this
-- script idempotent.
INSERT INTO AD_Message_Trl (
    AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    MsgText, IsTranslated
)
SELECT m.AD_Message_ID, lang.AD_Language, 0, 0, 'Y',
       TO_TIMESTAMP('2026-05-28 06:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-05-28 06:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       m.MsgText, 'Y'
FROM AD_Message m
CROSS JOIN (VALUES ('de_DE'), ('de_CH')) AS lang(AD_Language)
WHERE m.Value IN ('C_Order_Split_NoShipments', 'C_Order_Split_NothingToSplit')
  AND NOT EXISTS (
      SELECT 1 FROM AD_Message_Trl t
      WHERE t.AD_Message_ID = m.AD_Message_ID
        AND t.AD_Language = lang.AD_Language
  );
