update M_Picking_Candidate pc set C_UOM_ID=p.C_UOM_ID
from M_ShipmentSchedule s
inner join M_Product p on (p.M_Product_ID=s.M_Product_ID)
where
pc.C_UOM_ID is null
and s.M_ShipmentSchedule_ID=pc.M_ShipmentSchedule_ID
;

