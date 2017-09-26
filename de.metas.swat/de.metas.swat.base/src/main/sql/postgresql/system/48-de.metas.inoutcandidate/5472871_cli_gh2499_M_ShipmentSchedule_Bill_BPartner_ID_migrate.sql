update M_ShipmentSchedule ss set
Bill_BPartner_ID=(select o.Bill_BPartner_ID from C_OrderLine ol inner join C_Order o on (o.C_Order_ID=ol.C_Order_ID) where ol.C_OrderLine_ID=ss.C_OrderLine_ID)
where ss.C_OrderLine_ID is not null
and Bill_BPartner_ID is null
;

update M_ShipmentSchedule ss set Bill_BPartner_ID=C_BPartner_ID
where Bill_BPartner_ID is null
;

