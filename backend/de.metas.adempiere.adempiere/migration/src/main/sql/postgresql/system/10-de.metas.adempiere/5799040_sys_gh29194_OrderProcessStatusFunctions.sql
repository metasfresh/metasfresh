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
ALTER FUNCTION c_order_deliverystatus_compute(c_order) OWNER TO metasfresh
;

DROP FUNCTION IF EXISTS c_order_invoicestatus_compute(p_order c_order) CASCADE;
CREATE OR REPLACE FUNCTION c_order_invoicestatus_compute(p_order c_order) RETURNS text
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_InvoiceStatus text;
BEGIN

    SELECT (CASE
        WHEN p_order.QtyOrdered <= p_order.QtyInvoiced AND p_order.QtyOrdered > 0
            THEN 'CI'   --completely invoiced
        WHEN p_order.QtyOrdered > p_order.QtyInvoiced AND p_order.QtyInvoiced > 0
            THEN 'PI'   --partially invoiced
            ELSE 'O'    --open
    END) INTO v_InvoiceStatus;

    RETURN v_InvoiceStatus;
END;
$$
;

ALTER FUNCTION c_order_processstate_color_id_compute(c_order) OWNER TO metasfresh
;

DROP FUNCTION IF EXISTS c_order_processstatus_color_id_compute(p_order c_order) CASCADE;
CREATE OR REPLACE FUNCTION c_order_processstatus_color_id_compute(p_order c_order) RETURNS numeric
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_ColorID numeric;
    v_Red     numeric := getColor_ID_By_SysConfig('Red_Color');
    v_Yellow  numeric := getColor_ID_By_SysConfig('Yellow_Color');
    v_Green   numeric := getColor_ID_By_SysConfig('Green_Color');
BEGIN

    SELECT (CASE
        WHEN c_order_deliverystatus_compute(p_order) = 'CD'
            AND c_order_invoicestatus_compute(p_order) = 'CI'
        THEN v_Green
        WHEN c_order_deliverystatus_compute(p_order) = 'O'
           AND c_order_invoicestatus_compute(p_order) = 'O'
        THEN v_Red
        ELSE v_Yellow
    END)

    INTO v_ColorID;

    RETURN v_ColorID;
END;
$$
;
ALTER FUNCTION c_order_processstatus_color_id_compute(c_order) OWNER TO metasfresh
;

