----- update quantitites for older orders -----

UPDATE C_Order
set QtyOrdered = (select sum(QtyOrdered) from C_OrderLine ol where ol.C_Order_ID = C_Order.C_Order_ID and ol.IsPackagingMaterial = 'N'),
    QtyInvoiced = (select sum(QtyInvoiced) from C_OrderLine ol where ol.C_Order_ID = C_Order.C_Order_ID and ol.IsPackagingMaterial = 'N'),
    QtyMoved = (select sum(QTyDelivered) from C_OrderLine ol where ol.C_Order_ID = C_Order.C_Order_ID and ol.IsPackagingMaterial = 'N');