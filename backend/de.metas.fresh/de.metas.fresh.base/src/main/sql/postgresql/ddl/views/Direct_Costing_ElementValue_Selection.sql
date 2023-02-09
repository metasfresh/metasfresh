

CREATE OR REPLACE VIEW report.Direct_Costing_ElementValue_selection AS
SELECT 
	e1.C_ElementValue_ID as lvl1_C_ElementValue_ID, e1.value as lvl1_value, e1.name as lvl1_name, ( e1.value || ' ' || e1.name ) as lvl1_label,
	e2.C_ElementValue_ID as lvl2_C_ElementValue_ID, e2.value as lvl2_value, e2.name as lvl2_name, ( e2.value || ' ' || e2.name ) as lvl2_label,
	e3.C_ElementValue_ID as lvl3_C_ElementValue_ID, e3.value as lvl3_value, e3.name as lvl3_name, ( e3.value || ' ' || e3.name ) as lvl3_label,
	
	e3.value IN ( 
		'3000','3001',
		'3900','3901','3902',
		'4000','4001','4002','4003','4010','4011','4012','4013',
		'4900'
	) AS Margin1,
	
	e3.value IN ( 
		'3300','3301',
		'4300','4301','4302','4303','4310','4311','4312','4313',
		'5000','5001','5002','5003','5004','5005','5700',
		'5710',	'5720','5730','5740','5750'
	) AS Margin2,
	
	e3.value IN ( 
		'3402','3500','3501',
		'4400','4401','4402','4410',
		'4500','4501','4502','4510','4520',
		'4700','4701','4702','4710','4711',
		'5000','5001','5002','5003','5004','5005',
		'5700','5710','5720','5730','5740','5750',
		'5804','5808',
		'6000',
		'6200','6201','6202','6203','6204','6205','6206','6207','6208','6209','6210',
		'6400','6401','6402','6403','6404','6405'
	) AS Margin3,

	e3.value IN (
		'3403','3404','3405','3407','3406','3408',
		'3600',
		'5000','5001','5002','5003','5004','5005','5020',
		'5700','5710','5720','5730','5740','5750',
		'5800','5801','5802','5803','5805','5806','5807',
		'6001','6002','6003',
		'6100','6101','6102','6103','6104','6105','6106','6107','6108','6109','6110','6111','6115',
		'6300','6301',
		'6406','6500','6501','6502','6503','6504','6505','6506','6507','6508','6509','6510','6511','6512','6513','6514','6515','6516',
		'6600','6601','6602','6603',
		'6700','6701',
		'7500','7501', '7505', '7506',
		'8015'
	) AS Margin4,

	e3.value IN (
		'6800','6801','6802','6803','6804','6805','6806','6807',
		'6900','6901','6902','6903','6904','6905','6951','6954','6980',
		'7900','7901','7902',
		'8000','8010','8011','8012','8013','8014','8100','8101','8900'
	) AS Margin5

	
FROM 
	C_ElementValue e3
	JOIN (
		SELECT	evp.C_ElementValue_ID, evp.value, evp.name, ev.C_ElementValue_ID AS Source_ElementValue_ID
		FROM	C_ElementValue ev
			JOIN C_Element e ON e.C_Element_ID = ev.C_Element_ID
			JOIN AD_TreeNode tn ON tn.AD_Tree_ID = e.AD_Tree_ID AND tn.Node_ID = ev.C_ElementValue_ID 
			JOIN C_ElementValue evp ON evp.C_ElementValue_ID = tn.Parent_ID 
	) e2 ON e3.C_ElementValue_ID = e2.Source_ElementValue_ID
		
	LEFT JOIN (
		SELECT	evp.C_ElementValue_ID, evp.value, evp.name, ev.C_ElementValue_ID AS Source_ElementValue_ID
		FROM	C_ElementValue ev
			JOIN C_Element e ON e.C_Element_ID = ev.C_Element_ID
			JOIN AD_TreeNode tn ON tn.AD_Tree_ID = e.AD_Tree_ID AND tn.Node_ID = ev.C_ElementValue_ID 
			JOIN C_ElementValue evp ON evp.C_ElementValue_ID = tn.Parent_ID 
	) e1 ON e2.C_ElementValue_ID = e1.Source_ElementValue_ID
;
