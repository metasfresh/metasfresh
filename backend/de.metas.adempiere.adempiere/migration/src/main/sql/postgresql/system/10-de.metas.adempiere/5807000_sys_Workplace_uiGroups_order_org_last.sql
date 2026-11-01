-- Workplace window (541744), main tab (547260), right UI column (547140):
-- reorder the element groups so the client/org group is always last.
-- Was: flags(10), orgs(20), limits(30), restrictions(40).
-- Now: flags(10), limits(20), restrictions(30), orgs(40).

UPDATE AD_UI_ElementGroup SET SeqNo=10, Updated=TO_TIMESTAMP('2026-06-09 11:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551258  -- flags
;
UPDATE AD_UI_ElementGroup SET SeqNo=20, Updated=TO_TIMESTAMP('2026-06-09 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553770  -- limits
;
UPDATE AD_UI_ElementGroup SET SeqNo=30, Updated=TO_TIMESTAMP('2026-06-09 11:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=555431  -- restrictions
;
UPDATE AD_UI_ElementGroup SET SeqNo=40, Updated=TO_TIMESTAMP('2026-06-09 11:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551259  -- orgs (client/org) — last
;
