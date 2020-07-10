-- backup first
create table if not exists backup.Margin_Conf_Acct_06_05_2020 as select * from   report.Margin_Conf_Acct;



UPDATE report.Margin_Conf_Acct SET Name = 'Total Personalaufwand Produktion' WHERE Margin_Conf_ID = 1000001 AND value = '5';
UPDATE report.Margin_Conf_Acct SET Name = 'Total Personalaufwand Fuhrpark und (Logistik) Verlad' WHERE Margin_Conf_ID = 1000002 AND value = '5';
UPDATE report.Margin_Conf_Acct SET Name = 'Total Personalaufwand Verwaltung' WHERE Margin_Conf_ID = 1000003 AND value = '5';





UPDATE report.Margin_Conf_Acct SET Name = null WHERE Margin_Conf_ID = 1000002 AND value = '3';
UPDATE report.Margin_Conf_Acct SET lvl1_SeqNo = 10, Name = null WHERE Margin_Conf_ID = 1000002 AND value = '4';
UPDATE report.Margin_Conf_Acct SET lvl1_SeqNo = 10 WHERE Parent_Account_ID = (SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Margin_Conf_ID = 1000002 AND value = '4');
UPDATE report.Margin_Conf_Acct SET lvl1_SeqNo = 10 WHERE Parent_Account_ID IN (SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Parent_Account_ID = 
		(SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Margin_Conf_ID = 1000002 AND value = '4')
	);
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 15 WHERE Margin_Conf_ID = 1000002 AND value = '440';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 15 WHERE Parent_Account_ID = (SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Margin_Conf_ID = 1000002 AND value = '440');
	
UPDATE report.Margin_Conf_Acct SET Name = 'Total Personalaufwand Fuhrpark und (Logistik) Verlad' WHERE Margin_Conf_ID = 1000002 AND value = '5';
UPDATE report.Margin_Conf_Acct SET Name = 'Gebindeaufwand' WHERE Margin_Conf_ID = 1000002 AND value = '450';
UPDATE report.Margin_Conf_Acct SET Name = 'Lohnaufwand Fuhrpark und (Logistik) Verlad' WHERE Margin_Conf_ID = 1000002 AND value = '500';
UPDATE report.Margin_Conf_Acct SET Name = 'Sozialversicherungsaufwand Fuhrpark und Logistik' WHERE Margin_Conf_ID = 1000002 AND value = '570';
UPDATE report.Margin_Conf_Acct SET Name = null WHERE Margin_Conf_ID = 1000002 AND value = '6';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 100 WHERE Margin_Conf_ID = 1000002 AND value = '600';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 100 WHERE Parent_Account_ID = (SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Margin_Conf_ID = 1000002 AND value = '600');
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 90 WHERE Margin_Conf_ID = 1000002 AND value = '620';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 90 WHERE Parent_Account_ID = (SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Margin_Conf_ID = 1000002 AND value = '620');




UPDATE report.Margin_Conf_Acct SET Name = null WHERE Margin_Conf_ID = 1000003 AND value = '3';
UPDATE report.Margin_Conf_Acct SET Name = 'Total Personalaufwand Verwaltung' WHERE Margin_Conf_ID = 1000003 AND value = '5';
UPDATE report.Margin_Conf_Acct SET Name = 'Sozialversicherungsaufwand Verwaltung' WHERE Margin_Conf_ID = 1000003 AND value = '570';
UPDATE report.Margin_Conf_Acct SET Name = null WHERE Margin_Conf_ID = 1000003 AND value = '6';
UPDATE report.Margin_Conf_Acct SET Name = 'Unterhalt, Reparaturen und Ersatz' WHERE Margin_Conf_ID = 1000003 AND value = '610';
UPDATE report.Margin_Conf_Acct SET Name = null WHERE Margin_Conf_ID = 1000003 AND value = '7';
UPDATE report.Margin_Conf_Acct SET lvl1_SeqNo = 10 WHERE Margin_Conf_ID = 1000003;
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 29 WHERE Margin_Conf_ID = 1000003 AND value = '5';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 120 WHERE Margin_Conf_ID = 1000003 AND value IN ('650');
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 140 WHERE Margin_Conf_ID = 1000003 AND value IN ('360', '3600');
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 150 WHERE Margin_Conf_ID = 1000003 AND value IN ('670', '6700', '6701');
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 160 WHERE Margin_Conf_ID = 1000003 AND value IN ('8015'); 

UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 100, lvl3_SeqNo = 635  WHERE Margin_Conf_ID = 1000003 AND value = '3408';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 50, lvl3_SeqNo = 277  WHERE Margin_Conf_ID = 1000003 AND value = '3407';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 50, lvl3_SeqNo = 276  WHERE Margin_Conf_ID = 1000003 AND value = '3406';
UPDATE report.Margin_Conf_Acct SET Parent_Account_ID = (SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Margin_Conf_ID = 1000003 AND value = '580'), lvl2_SeqNo = 50, lvl3_SeqNo = 275 WHERE Margin_Conf_ID = 1000003 AND value = '3403';

UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 10 WHERE Margin_Conf_ID = 1000004 AND value = '690';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 10 WHERE Parent_Account_ID = (SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Margin_Conf_ID = 1000004 AND value = '690');
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 20 WHERE Margin_Conf_ID = 1000004 AND value = '695';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 20 WHERE Parent_Account_ID = (SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Margin_Conf_ID = 1000004 AND value = '695');
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 30 WHERE Margin_Conf_ID = 1000004 AND value = '680';
UPDATE report.Margin_Conf_Acct SET lvl2_SeqNo = 30 WHERE Parent_Account_ID = (SELECT Margin_Conf_Acct_ID FROM report.Margin_Conf_Acct WHERE Margin_Conf_ID = 1000004 AND value = '680');
UPDATE report.Margin_Conf_Acct SET Name = '' WHERE Margin_Conf_ID = 1000003 AND value IN ( '6', '7', '8' );

