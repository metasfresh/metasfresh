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
    uom record;
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

    -- Check if the resource capacity UOM is a time type
    SELECT u.uomtype
    INTO uom
    FROM c_uom u
    WHERE u.c_uom_id = resource.capacityperproductioncycle_uom_id;

    IF uom.uomtype = 'TM' THEN
        RETURN 1;
    END IF;

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


