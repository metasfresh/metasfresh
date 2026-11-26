-- Plain read-only view over C_OrderLine for the order-line tab of the window "Order Compensation Group" (AD_Window 542196).
-- Only the columns the tab shows plus the technical ones: key C_OrderLine_ID (keeps the Related Documents of a line row),
-- client/org/active/created/updated, the link to the order and to the group. Column names equal the C_OrderLine names.
-- No window function, no join: filters push down to C_OrderLine.
-- (AD metadata: script 5834210.)
DROP VIEW IF EXISTS C_Order_CompensationGroup_Line_V$new
;
CREATE OR REPLACE VIEW C_Order_CompensationGroup_Line_V$new AS
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
SELECT db_alter_view('C_Order_CompensationGroup_Line_V',
                     (SELECT view_definition
                      FROM information_schema.views
                      WHERE lower(views.table_name) = lower('C_Order_CompensationGroup_Line_V$new')))
;
DROP VIEW IF EXISTS C_Order_CompensationGroup_Line_V$new
;
