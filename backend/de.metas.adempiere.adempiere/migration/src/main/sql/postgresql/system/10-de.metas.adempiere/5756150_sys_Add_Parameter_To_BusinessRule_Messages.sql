
SELECT backup_table('AD_Message')
;

SELECT backup_table('AD_Message_TRL')
;

UPDATE AD_Message m
SET MsgText = CONCAT(m.MsgText, ' {}')
WHERE EXISTS (SELECT 1
              FROM AD_BusinessRule br
              WHERE br.warning_message_id = m.AD_Message_ID)
  AND m.MsgText NOT LIKE '% {}'
;

;

UPDATE AD_Message_Trl mt
SET MsgText = CONCAT(mt.MsgText, ' {}')
WHERE EXISTS (SELECT 1
              FROM AD_BusinessRule br
                       INNER JOIN AD_Message m ON br.warning_message_id = m.AD_Message_ID
              WHERE m.AD_Message_ID = mt.AD_Message_ID)
  AND mt.MsgText NOT LIKE '% {}'
;