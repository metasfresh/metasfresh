DROP FUNCTION IF EXISTS report.M_Cost_CostPrice_Function(
    IN p_DateAcct       timestamp WITH TIME ZONE,
    IN p_M_Product_ID   numeric(10, 0),
    IN p_M_Warehouse_ID numeric(10, 0),
    IN p_ad_language    character varying(6)
)
;
