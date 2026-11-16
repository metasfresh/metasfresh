UPDATE AD_Column
SET columnsql='( select sop.TrackingURL from M_ShippingPackage p inner join Carrier_ShipmentOrder_Parcel sop on sop.M_Package_ID = p.M_Package_ID where p.M_InOut_ID = M_InOut.M_InOut_ID limit 1 )',
    updatedby=100,
    updated=TO_TIMESTAMP('2026-06-05 10:20:30','YYYY-MM-DD HH24:MI:SS')
WHERE AD_Column_ID = 592668
;