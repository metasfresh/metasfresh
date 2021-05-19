DROP INDEX IF EXISTS servicerepair_old_shipped_hu_serialNo
;

CREATE INDEX servicerepair_old_shipped_hu_serialNo ON servicerepair_old_shipped_hu (serialno)
;
