
UPDATE AD_Column 
SET 
	ColumnSQL = replace(replace(ColumnSQL, 'WHERE', 'where'), 'FROM', 'from'), 
	Updated=now(), 
	UpdatedBy=99
WHERE ColumnSQL like '%WHERE%';

UPDATE AD_Val_Rule 
SET 
	Code = replace(replace(Code, 'WHERE', 'where'), 'FROM', 'from'), 
	Updated=now(), 
	UpdatedBy=99
WHERE Code like '%WHERE%';
