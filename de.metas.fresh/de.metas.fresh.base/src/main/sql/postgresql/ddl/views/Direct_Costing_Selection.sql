DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_selection;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.Direct_Costing_selection AS
SELECT
	to_char(mc.SeqNo, '0000') || to_char(l3.lvl1_seqno, '0000') || to_char(l3.lvl2_seqno, '0000') || to_char(l3.lvl3_seqno, '0000') AS SeqNo,
	mc.name AS margin,
	l1.C_ElementValue_ID AS L1_ElementValue_ID, l1.value AS L1_value, l1.name AS L1_Name, 
	l2.C_ElementValue_ID AS L2_ElementValue_ID, l2.value AS L2_value, l2.name AS L2_Name, 
	l3.C_ElementValue_ID AS L3_ElementValue_ID, l3.value AS L3_value, l3.name AS L3_Name, 
	CASE WHEN l3.Use_Activity_1000 THEN 1 ELSE 0 END as Multi_1000, 
	CASE WHEN l3.Use_Activity_2000 THEN 1 ELSE 0 END as Multi_2000, 
	CASE WHEN l3.Use_Activity_100 THEN 1 ELSE 0 END as Multi_100, 
	CASE WHEN l3.Use_Activity_150 THEN 1 ELSE 0 END as Multi_150
FROM
	report.Margin_Conf_Acct l3
	JOIN report.Margin_Conf_Acct l2 ON l3.Parent_Account_ID = l2.Margin_Conf_Acct_ID AND l2.level = 2
	JOIN report.Margin_Conf_Acct l1 ON l2.Parent_Account_ID = l1.Margin_Conf_Acct_ID AND l1.level = 1
	JOIN report.Margin_Conf mc on l3.Margin_Conf_ID = mc.Margin_Conf_ID
WHERE
	l3.level = 3
;
