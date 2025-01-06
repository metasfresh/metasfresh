

create table backup.ModCntr_Type_BKP_17042024 as select * from modcntr_type;

UPDATE modcntr_type set modularcontracthandlertype='PurchaseOrderLine_Modular', description = description || ' set to a deprecated computing method to avoid null values' 
WHERE modularcontracthandlertype is null;



