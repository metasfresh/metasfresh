UPDATE AD_Column
SET columnsql='( SELECT sop.TrackingURL FROM M_ShippingPackage p INNER JOIN Carrier_ShipmentOrder_Parcel sop ON sop.M_Package_ID = p.M_Package_ID WHERE p.M_InOut_ID = M_InOut.M_InOut_ID )',
    updatedby=99,
    updated=TO_TIMESTAMP('2026-06-05 10:20:30','YYYY-MM-DD HH24:MI:SS')
WHERE AD_Column_ID = 592668
;