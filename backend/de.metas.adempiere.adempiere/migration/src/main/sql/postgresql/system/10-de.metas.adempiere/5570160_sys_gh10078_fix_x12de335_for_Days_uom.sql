-- update c_uom set x12de355='DA', isactive='Y' where c_uom_id=540023;
UPDATE c_uom
SET isactive='Y', updated=now(), updatedby=99
WHERE c_uom_id = 102
;