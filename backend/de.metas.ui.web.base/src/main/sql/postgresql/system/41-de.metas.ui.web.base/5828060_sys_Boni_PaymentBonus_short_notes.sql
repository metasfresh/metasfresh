-- Boni: short payment bonus notes, so that they fit the column "Zahlungsbonus-Hinweis" of the allocation view (about 330 px); the full text is in the tooltip
-- the amounts are passed as amounts and formatted for the user's language
-- 2026-10-06

UPDATE AD_Message SET MsgText = 'Bonus {0} > zahlbar {1}', Updated = TO_TIMESTAMP('2026-10-06 16:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545902
;
UPDATE AD_Message_Trl SET MsgText = 'Bonus {0} > zahlbar {1}', Updated = TO_TIMESTAMP('2026-10-06 16:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545902 AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Message_Trl SET MsgText = 'Bonus {0} > payable {1}', Updated = TO_TIMESTAMP('2026-10-06 16:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545902 AND AD_Language = 'en_US'
;

UPDATE AD_Message SET MsgText = 'Bonus nicht berechnet: {0}', Updated = TO_TIMESTAMP('2026-10-06 16:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545903
;
UPDATE AD_Message_Trl SET MsgText = 'Bonus nicht berechnet: {0}', Updated = TO_TIMESTAMP('2026-10-06 16:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545903 AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Message_Trl SET MsgText = 'Bonus not computed: {0}', Updated = TO_TIMESTAMP('2026-10-06 16:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545903 AND AD_Language = 'en_US'
;

UPDATE AD_Message SET MsgText = 'Bonus {0} → {1} (MwSt.-Rundung)', Updated = TO_TIMESTAMP('2026-10-06 16:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545904
;
UPDATE AD_Message_Trl SET MsgText = 'Bonus {0} → {1} (MwSt.-Rundung)', Updated = TO_TIMESTAMP('2026-10-06 16:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545904 AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Message_Trl SET MsgText = 'Bonus {0} → {1} (VAT rounding)', Updated = TO_TIMESTAMP('2026-10-06 16:00:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545904 AND AD_Language = 'en_US'
;

UPDATE AD_Message SET MsgText = 'Bonus nur in {0}, nicht in {1}', Updated = TO_TIMESTAMP('2026-10-06 16:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545905
;
UPDATE AD_Message_Trl SET MsgText = 'Bonus nur in {0}, nicht in {1}', Updated = TO_TIMESTAMP('2026-10-06 16:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545905 AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Message_Trl SET MsgText = 'Bonus only in {0}, not in {1}', Updated = TO_TIMESTAMP('2026-10-06 16:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545905 AND AD_Language = 'en_US'
;
