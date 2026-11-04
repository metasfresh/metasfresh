-- add account 3302 to Deckungsbeitrag 2 (Margin_Conf 1000001) without rebuilding Margin_Conf_Acct:
-- placed under its level-2 group, between the preceding and the following level-3 row
-- (+5 relies on the rebuild's SeqNo spacing of 10, see 5755190)
-- nextval: Margin_Conf_Acct IDs are DB-local (every rebuild restarts the sequence) and never referenced across DBs; a fixed ID would collide with the sequence
INSERT INTO report.Margin_Conf_Acct
(Margin_Conf_ID, Margin_Conf_Acct_ID, Parent_Account_ID, C_ElementValue_ID,
 lvl1_SeqNo, lvl2_SeqNo, lvl3_SeqNo, level, value, name,
 Use_activity_1000, Use_activity_2000, Use_activity_100, Use_activity_150)
SELECT l2.Margin_Conf_ID, NextVal('report.Margin_Conf_Acct_Seq'), l2.Margin_Conf_Acct_ID, s.lvl3_C_ElementValue_ID,
       l2.lvl1_SeqNo, l2.lvl2_SeqNo,
       COALESCE((SELECT MAX(p.lvl3_SeqNo) FROM report.Margin_Conf_Acct p
                 WHERE p.Margin_Conf_ID = l2.Margin_Conf_ID AND p.level = 3 AND p.value < s.lvl3_value), 0) + 5,
       3, s.lvl3_value, s.lvl3_name,
       true, true, true, true -- the Use_activity exceptions apply only to groups 500/570
FROM report.Direct_Costing_ElementValue_selection s
         JOIN report.Margin_Conf_Acct l2 ON l2.Margin_Conf_ID = 1000001 AND l2.level = 2 AND l2.value = s.lvl2_value
WHERE s.Margin2
  AND s.lvl3_value = '3302'
  -- natural-key pre-flight: skip (not fail) unless both keys are unique, so other instances' rollouts are never broken
  AND (SELECT COUNT(*) FROM report.Direct_Costing_ElementValue_selection u WHERE u.Margin2 AND u.lvl3_value = '3302') = 1
  AND (SELECT COUNT(*) FROM report.Margin_Conf_Acct u WHERE u.Margin_Conf_ID = 1000001 AND u.level = 2 AND u.value = s.lvl2_value) = 1
  AND NOT EXISTS (SELECT 1 FROM report.Margin_Conf_Acct m
                  WHERE m.Margin_Conf_ID = 1000001 AND m.level = 3 AND m.value = '3302')
;
