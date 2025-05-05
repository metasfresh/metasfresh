

CREATE TABLE backup.BKP_C_UOM_08112022 AS Select * from c_uom;
CREATE TABLE backup.BKP_C_UOM_TRL_08112022 AS Select * from c_uom_trl;



UPDATE C_UOM SET name = name||' (inactive)' where isactive = 'N';


UPDATE c_uom_trl
SET name = name||' (inactive)'
WHERE c_uom_id in (SELECT c_uom_id from c_uom WHERE c_uom.isactive = 'N')
;