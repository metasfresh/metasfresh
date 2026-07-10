-- Fix: 5809900 inserted AD_Reference 542115 with Name='Order Board Status' (English).
-- AD_Reference.Name must be the base language (German). Fix to 'Auftrags-Board-Status'.
UPDATE AD_Reference
SET    Name      = 'Auftrags-Board-Status',
       Updated   = TO_TIMESTAMP('2026-07-02 22:10:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Reference_ID = 542115
;

UPDATE AD_Reference_Trl
SET    Name         = 'Auftrags-Board-Status',
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-07-02 22:10:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Reference_ID = 542115
  AND  AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Reference_Trl
SET    Name         = 'Order Board Status',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-07-02 22:10:02', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Reference_ID = 542115
  AND  AD_Language = 'en_US'
;
