CREATE UNIQUE INDEX IDX_Unique_M_Product_ID_M_Warehouse_ID
    ON M_Product_Warehouse (M_Product_ID, M_Warehouse_ID) WHERE isactive = 'Y';
