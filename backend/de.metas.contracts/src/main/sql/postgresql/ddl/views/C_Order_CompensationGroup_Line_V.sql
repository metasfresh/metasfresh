DROP VIEW IF EXISTS C_Order_CompensationGroup_Line_V
;

-- Plain read-only view over C_OrderLine for the order-line tab of the window "Order Compensation Group".
-- Same definition as migration script 5834200_sys_C_Order_CompensationGroup_Line_V_view.sql.
CREATE OR REPLACE VIEW C_Order_CompensationGroup_Line_V AS
SELECT ol.C_OrderLine_ID,
       ol.AD_Client_ID,
       ol.AD_Org_ID,
       ol.IsActive,
       ol.Created,
       ol.CreatedBy,
       ol.Updated,
       ol.UpdatedBy,
       ol.C_Order_ID,
       ol.C_Order_CompensationGroup_ID,
       ol.Line,
       ol.M_Product_ID,
       ol.QtyEntered,
       ol.C_UOM_ID,
       ol.PriceEntered,
       ol.Price_UOM_ID,
       ol.LineNetAmt,
       ol.GroupCompensationType,
       ol.GroupCompensationAmtType
FROM C_OrderLine ol
;
