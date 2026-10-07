DROP FUNCTION IF EXISTS c_order_deliverystatus_compute(p_order c_order) CASCADE;

CREATE OR REPLACE FUNCTION c_order_deliverystatus_compute(p_order c_order) RETURNS text
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_DeliveryStatus text;
BEGIN

    SELECT (CASE
        WHEN p_order.QtyOrdered <= p_order.QtyMoved AND p_order.QtyOrdered > 0
            THEN 'CD'   --completely delivered
        WHEN p_order.QtyOrdered > p_order.QtyMoved AND p_order.QtyMoved > 0
            THEN 'PD'   --partially delivered
            ELSE 'O'    --open
    END)

    INTO v_DeliveryStatus;

    RETURN v_DeliveryStatus;
END;
$$
;
