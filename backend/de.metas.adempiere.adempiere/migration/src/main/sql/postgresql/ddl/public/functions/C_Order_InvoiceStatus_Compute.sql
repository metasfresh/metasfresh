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
