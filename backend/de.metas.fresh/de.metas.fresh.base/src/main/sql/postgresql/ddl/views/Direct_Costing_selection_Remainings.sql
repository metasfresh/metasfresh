--DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_selection_Remainings;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.Direct_Costing_selection_Remainings AS
SELECT
	ev.C_ElementValue_ID AS L_ElementValue_ID, ev.value AS L_value, ev.name AS L_Name, 
	1 as Multi_1000, 
	1 as Multi_2000, 
	1 as Multi_100, 
	1 as Multi_150
FROM
	C_ElementValue ev
where
ev.value not in (select value from report.Margin_Conf_Acct)
and 
char_length(ev.value)<=4
and 
ev.accounttype in ('R', 'E')
and ev.isSummary='N'
;
