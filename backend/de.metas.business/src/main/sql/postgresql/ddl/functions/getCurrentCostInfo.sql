DROP FUNCTION IF EXISTS getCurrentCost(
    p_M_Product_ID     numeric,
    p_C_UOM_ID         numeric,
    p_Date             date,
    p_AcctSchema_ID    numeric,
    p_M_CostElement_Id numeric,
    p_AD_Client_ID     numeric,
    p_AD_Org_ID        numeric)
;

DROP FUNCTION IF EXISTS getCurrentCostInfo(
    p_M_Product_ID     numeric,
    p_C_UOM_ID         numeric,
    p_Date             date,
    p_AcctSchema_ID    numeric,
    p_M_CostElement_Id numeric,
    p_AD_Client_ID     numeric,
    p_AD_Org_ID        numeric)
;

DROP TYPE IF EXISTS CurrentCostPrice
;

CREATE TYPE CurrentCostPrice AS
(
    --
    -- IMPORTANT!!! DO NOT CHANGE FIELDS ORDER. WE RELLY ON THAT IN OUR JAVA CODE
    --

    C_Currency_ID       numeric,
    OwnCostPrice        numeric,
    ComponentsCostPrice numeric,
    C_UOM_ID            numeric
)
;

COMMENT ON TYPE CurrentCostPrice IS 'The result of some getCurrentCostInfo function.'
;


CREATE OR REPLACE FUNCTION getCurrentCostInfo(
    p_M_Product_ID     numeric,
    p_C_UOM_ID         numeric,
    p_Date             date,
    p_AcctSchema_ID    numeric,
    p_M_CostElement_Id numeric,
    p_AD_Client_ID     numeric,
    p_AD_Org_ID        numeric)
    RETURNS CurrentCostPrice
    LANGUAGE plpgsql
    STABLE
AS
$$
DECLARE
    v_context             record;
    v_ad_org_id_effective numeric;
    v_costdetail          record;
    v_cost                record;
    --
    v_result              CurrentCostPrice;
BEGIN
    RAISE NOTICE 'getCurrentCostInfo: p_M_Product_ID=%, p_C_UOM_ID=%, p_Date=%, p_AcctSchema_ID=%, p_M_CostElement_ID=%, p_AD_Client_ID=%, p_AD_Org_ID=%',
        p_M_Product_ID, p_C_UOM_ID, p_Date, p_AcctSchema_ID, p_M_CostElement_Id, p_AD_Client_ID, p_AD_Org_ID;

    --
    -- Fetch context (i.e. costing level)
    --
    SELECT COALESCE(pca.costinglevel, cas.costinglevel) AS costinglevel
    INTO v_context
    FROM m_product p
             INNER JOIN m_product_category_acct pca ON pca.m_product_category_id = p.m_product_category_id AND pca.c_acctschema_id = p_AcctSchema_ID
             INNER JOIN c_acctschema cas ON cas.c_acctschema_id = pca.c_acctschema_id
    WHERE p.m_product_id = p_M_Product_ID;
    RAISE NOTICE 'Context: %', v_context;
    --
    IF (v_context IS NULL) THEN
        RAISE WARNING 'No info found for M_Product_ID=%, C_AcctSchema_ID=%', p_M_Product_ID, p_AcctSchema_ID;
        RETURN NULL;
    END IF;

    --
    -- Calculate the effective dimensions based on costing level
    --
    IF (v_context.costinglevel = 'C') THEN
        v_ad_org_id_effective := NULL;
    ELSIF (v_context.costinglevel = 'O') THEN
        v_ad_org_id_effective := (CASE WHEN p_AD_Org_ID > 0 THEN p_AD_Org_ID END);
    ELSE
        RAISE EXCEPTION 'Costing level not handled: %', v_context.costinglevel;
    END IF;
    RAISE NOTICE 'v_ad_org_id_effective=%', v_ad_org_id_effective;


    --
    -- Check cost details:
    --
    SELECT cd.m_costdetail_id,
           cd.prev_currentcostprice,
           cd.prev_currentcostpricell,
           cd.M_Product_ID,
           cd.c_uom_id,
           cd.c_currency_id,
           currency.costingprecision::integer AS costingprecision
    INTO v_costdetail
    FROM m_costdetail cd
             INNER JOIN c_currency currency ON currency.c_currency_id = cd.c_currency_id
    WHERE cd.ischangingcosts = 'Y'
      AND cd.c_acctschema_id = p_acctSchema_ID
      AND cd.m_costelement_id = p_M_CostElement_Id
      AND cd.M_Product_ID = p_M_Product_ID
      AND cd.ad_client_id = p_AD_Client_ID
      AND (v_ad_org_id_effective IS NULL OR cd.ad_org_ID = v_ad_org_id_effective)
      -- AND cd.m_attributesetinstance_id = any
      AND cd.dateAcct > p_Date
    ORDER BY cd.dateacct, cd.m_costdetail_id
    LIMIT 1;

    IF (v_costdetail IS NOT NULL) THEN
        v_result.C_Currency_ID := v_costdetail.c_currency_id;

        IF (p_C_UOM_ID IS NULL OR p_C_UOM_ID <= 0 OR v_costdetail.c_uom_id = p_C_UOM_ID) THEN
            v_result.C_UOM_ID = v_costdetail.c_uom_id;
            v_result.OwnCostPrice := v_costdetail.prev_currentcostprice;
            v_result.ComponentsCostPrice := v_costdetail.prev_currentcostpricell;
        ELSE
            v_result.C_UOM_ID = p_C_UOM_ID;
            v_result.OwnCostPrice := priceuomconvert(
                    p_m_product_id := v_costdetail.m_product_id,
                    p_price := v_costdetail.prev_currentcostprice,
                    p_price_uom_id := v_costdetail.c_uom_id,
                    p_c_uom_to_id := p_C_UOM_ID,
                    p_priceprecision := v_costdetail.costingprecision);
            v_result.ComponentsCostPrice := priceuomconvert(
                    p_m_product_id := v_costdetail.m_product_id,
                    p_price := v_costdetail.prev_currentcostpricell,
                    p_price_uom_id := v_costdetail.c_uom_id,
                    p_c_uom_to_id := p_C_UOM_ID,
                    p_priceprecision := v_costdetail.costingprecision);
        END IF;

        RAISE NOTICE 'Calculated cost price from cost detail: % (CD=%, p_C_UOM_ID=%)', v_result, v_costdetail, p_C_UOM_ID;
        RETURN v_result;
    END IF;

    --
    -- Check current cost:
    --
    SELECT cost.m_product_id,
           cost.currentcostprice,
           cost.currentcostpricell,
           cost.c_uom_id,
           cost.c_currency_id,
           currency.costingprecision::integer AS costingprecision
    INTO v_cost
    FROM m_cost cost
             INNER JOIN c_currency currency ON currency.c_currency_id = cost.c_currency_id
    WHERE cost.c_acctschema_id = p_acctSchema_ID
      AND cost.m_costelement_id = p_M_CostElement_Id
      AND cost.M_Product_ID = p_M_Product_ID
      AND cost.ad_client_id = p_AD_Client_ID
      AND (v_ad_org_id_effective IS NULL OR cost.ad_org_ID = v_ad_org_id_effective)
      AND cost.m_attributesetinstance_id = 0;

    IF (v_cost IS NOT NULL) THEN
        v_result.C_Currency_ID := v_cost.c_currency_id;

        IF (p_C_UOM_ID IS NULL OR p_C_UOM_ID <= 0 OR v_cost.c_uom_id = p_C_UOM_ID) THEN
            v_result.C_UOM_ID := v_cost.c_uom_id;
            v_result.OwnCostPrice := v_cost.currentcostprice;
            v_result.ComponentsCostPrice := v_cost.currentcostpricell;
        ELSE
            v_result.C_UOM_ID := p_C_UOM_ID;
            v_result.OwnCostPrice := priceuomconvert(
                    p_m_product_id := v_cost.m_product_id,
                    p_price := v_cost.currentcostprice,
                    p_price_uom_id := v_cost.c_uom_id,
                    p_c_uom_to_id := p_C_UOM_ID,
                    p_priceprecision := v_cost.costingprecision);
            v_result.ComponentsCostPrice := priceuomconvert(
                    p_m_product_id := v_cost.m_product_id,
                    p_price := v_cost.currentcostpricell,
                    p_price_uom_id := v_cost.c_uom_id,
                    p_c_uom_to_id := p_C_UOM_ID,
                    p_priceprecision := v_cost.costingprecision);
        END IF;

        RAISE NOTICE 'Calculated cost price from M_Cost: % (C=%, p_C_UOM_ID=%)', v_result, v_cost, p_C_UOM_ID;
        RETURN v_result;
    END IF;

    --
    -- Nothing found, shall not happen
    RAISE WARNING 'No cost price found. Returning null';
    RETURN NULL;
END
$$
;



CREATE OR REPLACE FUNCTION getCurrentCost(
    p_M_Product_ID     numeric,
    p_C_UOM_ID         numeric,
    p_Date             date,
    p_AcctSchema_ID    numeric,
    p_M_CostElement_Id numeric,
    p_AD_Client_ID     numeric,
    p_AD_Org_ID        numeric)
    RETURNS numeric
    LANGUAGE plpgsql
    STABLE
AS
$$
DECLARE
    v_costPriceInfo CurrentCostPrice;
BEGIN
    v_costPriceInfo := getCurrentCostInfo(
            p_M_Product_ID := p_M_Product_ID,
            p_C_UOM_ID := p_C_UOM_ID,
            p_Date := p_Date,
            p_AcctSchema_ID := p_AcctSchema_ID,
            p_M_CostElement_Id := p_M_CostElement_Id,
            p_AD_Client_ID := p_AD_Client_ID,
            p_AD_Org_ID := p_AD_Org_ID
        );

    RETURN v_costPriceInfo.OwnCostPrice;
END;
$$
;



