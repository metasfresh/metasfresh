DROP FUNCTION IF EXISTS createM_Warehouse_Acct(numeric)
;

CREATE FUNCTION createM_Warehouse_Acct(p_M_Warehouse_ID numeric DEFAULT NULL)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count numeric;
BEGIN
    INSERT
    INTO M_Warehouse_Acct (M_Warehouse_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created,
                           CreatedBy, Updated, UpdatedBy, W_Inventory_Acct, W_InvActualAdjust_Acct,
                           W_Differences_Acct, W_Revaluation_Acct)
    SELECT w.M_Warehouse_ID,
           asd.C_AcctSchema_ID,
           w.AD_Client_ID,
           w.AD_Org_ID,
           'Y',
           NOW(),
           99,
           NOW(),
           99,
           asd.W_Inventory_Acct,
           asd.W_InvActualAdjust_Acct,
           asd.W_Differences_Acct,
           asd.W_Revaluation_Acct
    FROM M_Warehouse w
             INNER JOIN C_AcctSchema_Default asd ON asd.AD_Client_ID = w.AD_Client_ID
    WHERE TRUE
      AND asd.AD_Client_ID = 1000000
      AND (p_M_Warehouse_ID IS NULL OR w.M_Warehouse_ID = p_M_Warehouse_ID)
      AND NOT EXISTS(SELECT 1 FROM M_Warehouse_Acct e WHERE e.C_AcctSchema_ID = asd.C_AcctSchema_ID AND e.M_Warehouse_ID = w.M_Warehouse_ID );
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Created % M_Warehouse_Acct records', v_count;
END;
$$
;


-- Create accounting records for all warehouses using the default C_AcctSchema and the warehouse's org
-- SELECT createM_Warehouse_Acct();

-- Create accounting records for that specific warehouse using the default C_AcctSchema and the warehouse's org
-- SELECT createM_Warehouse_Acct(540012);
