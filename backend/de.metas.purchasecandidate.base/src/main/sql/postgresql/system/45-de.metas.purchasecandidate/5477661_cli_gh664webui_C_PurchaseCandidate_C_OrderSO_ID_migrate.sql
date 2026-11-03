update C_PurchaseCandidate pc set C_OrderSO_ID=(select ol.C_Order_ID from C_OrderLine ol where ol.C_OrderLine_ID=pc.C_OrderLineSO_ID) where C_OrderSO_ID is null;
