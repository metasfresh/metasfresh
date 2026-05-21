-- Tax Declaration Iter 5 — 4 AD_Messages for completeIt + lock-guard interceptors.
-- Convention: MsgText holds German (base language); AD_Message_Trl[en_US] holds English.

-- Message 1: TaxDeclaration_Locked (Error)
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgType, MsgText, ErrorCode)
VALUES (545701 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'TaxDeclaration_Locked', 'E', 'Eine abgeschlossene Steuererklärung kann nicht geändert werden.', 'TAXDECLARATION_LOCKED');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545701, 'de_DE', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'N', 'Eine abgeschlossene Steuererklärung kann nicht geändert werden.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545701, 'de_CH', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'N', 'Eine abgeschlossene Steuererklärung kann nicht geändert werden.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545701, 'en_US', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'Y', 'Cannot modify a completed Tax Declaration.');

-- Message 2: TaxDeclaration_PeriodOverlap (Error)
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgType, MsgText, ErrorCode)
VALUES (545702, 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'TaxDeclaration_PeriodOverlap', 'E', 'Eine andere abgeschlossene Steuererklärung deckt diesen Zeitraum für das gleiche Buchungsschema bereits ab.', 'TAXDECLARATION_PERIOD_OVERLAP');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545702, 'de_DE', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'N', 'Eine andere abgeschlossene Steuererklärung deckt diesen Zeitraum für das gleiche Buchungsschema bereits ab.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545702, 'de_CH', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'N', 'Eine andere abgeschlossene Steuererklärung deckt diesen Zeitraum für das gleiche Buchungsschema bereits ab.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545702, 'en_US', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'Y', 'Another completed Tax Declaration already covers this period for the same accounting schema.');

-- Message 3: TaxDeclaration_PeriodUniqueness (Error — raised when the DB unique-partial-index trips)
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgType, MsgText, ErrorCode)
VALUES (545703, 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'TaxDeclaration_PeriodUniqueness', 'E', 'Pro Buchungsschema und Periode ist nur eine abgeschlossene Steuererklärung zulässig.', 'TAXDECLARATION_PERIOD_UNIQUENESS');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545703, 'de_DE', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'N', 'Pro Buchungsschema und Periode ist nur eine abgeschlossene Steuererklärung zulässig.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545703, 'de_CH', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'N', 'Pro Buchungsschema und Periode ist nur eine abgeschlossene Steuererklärung zulässig.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545703, 'en_US', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'Y', 'Only one completed Tax Declaration per accounting schema and period is allowed.');

-- Message 4: TaxDeclaration_NoLinesYet (Error)
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgType, MsgText, ErrorCode)
VALUES (545704, 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'TaxDeclaration_NoLinesYet', 'E', 'Eine Steuererklärung ohne Zeilen kann nicht abgeschlossen werden. Zuerst „Zeilen erstellen" ausführen.', 'TAXDECLARATION_NO_LINES_YET');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545704, 'de_DE', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'N', 'Eine Steuererklärung ohne Zeilen kann nicht abgeschlossen werden. Zuerst „Zeilen erstellen" ausführen.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545704, 'de_CH', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'N', 'Eine Steuererklärung ohne Zeilen kann nicht abgeschlossen werden. Zuerst „Zeilen erstellen" ausführen.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545704, 'en_US', 0, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, 'Y', 'Cannot complete a Tax Declaration with no lines. Run "Create lines" first.');
