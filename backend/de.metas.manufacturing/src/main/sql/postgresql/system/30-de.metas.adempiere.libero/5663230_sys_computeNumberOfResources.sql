DROP FUNCTION IF EXISTS computeNumberOfResources(
    p_S_Resource_ID numeric,
    p_Qty           numeric,
    p_Qty_UOM_ID    numeric,
    p_M_Product_ID  numeric
)
;

CREATE OR REPLACE FUNCTION computeNumberOfResources(
    p_S_Resource_ID numeric,
    p_Qty           numeric,
    p_Qty_UOM_ID    numeric,
    p_M_Product_ID  numeric
)
    RETURNS integer
AS
$$
DECLARE
    resource record;
    qtyConv  numeric;
BEGIN
    -- Zero or negative qty to produce => no resource needed
    IF (COALESCE(p_Qty, 0) <= 0) THEN
        RETURN 0;
    END IF;

    SELECT r.capacityperproductioncycle,
           r.capacityperproductioncycle_uom_id
    INTO resource
    FROM s_resource r
    WHERE r.s_resource_id = p_S_Resource_ID;

    -- Infinite capacity
    IF (resource.capacityperproductioncycle_uom_id IS NULL
        OR COALESCE(resource.capacityperproductioncycle, 0) <= 0) THEN
        RETURN 1;
    END IF;

    --
    -- Convert the Qty to resource capacity UOM
    IF (COALESCE(p_Qty_UOM_ID, 0) <= 0) THEN
        RAISE EXCEPTION 'Invalid p_Qty_UOM_ID provided (p_S_Resource_ID=%, p_Qty=%, p_Qty_UOM_ID=%)',p_S_Resource_ID, p_Qty,p_Qty_UOM_ID;
    END IF;
    qtyConv = uomconvert(
            p_M_Product_ID := p_M_Product_ID,
            p_C_UOM_From_ID := p_Qty_UOM_ID,
            p_C_UOM_To_ID := resource.capacityperproductioncycle_uom_id,
            p_Qty := p_Qty
        );
    IF (qtyConv IS NULL) THEN
        RAISE EXCEPTION 'No UOM conversion found from C_UOM_ID=% to C_UOM_ID=%, M_Product_ID=%', p_Qty_UOM_ID, resource.capacityperproductioncycle_uom_id, p_M_Product_ID;
    END IF;

    --
    -- Compute and return the number of resources required to produce given Qty
    RETURN CEIL(qtyConv / resource.capacityperproductioncycle);
END;
$$
    LANGUAGE plpgsql VOLATILE
;

--
-- Test:
--
/*
SELECT r.name
           || ' / ' || COALESCE(r.capacityperproductioncycle, 9999) || ' ' || COALESCE((SELECT uom.uomsymbol FROM c_uom uom WHERE uom.c_uom_id = r.capacityperproductioncycle_uom_id), '')
                                                                                                AS resource,
       (SELECT p.value || '_' || p.name FROM m_product p WHERE p.m_product_id = c.m_product_id) AS product,
       (SELECT uom.uomsymbol FROM c_uom uom WHERE uom.c_uom_id = c.c_uom_id)                    AS uom,
       c.qtyentered,
       c.qtytoprocess,
       c.qtyprocessed,
       computeNumberOfResources(
               p_S_Resource_ID := pp_order_candidate.s_resource_id,
               p_Qty := pp_order_candidate.qtyentered,
               p_Qty_UOM_ID := pp_order_candidate.c_uom_id,
               p_M_Product_ID := pp_order_candidate.m_product_id
           )                                                                                    AS NumberOfResources
FROM pp_order_candidate
         LEFT OUTER JOIN s_resource r ON r.s_resource_id = pp_order_candidate.s_resource_id
;

*/

