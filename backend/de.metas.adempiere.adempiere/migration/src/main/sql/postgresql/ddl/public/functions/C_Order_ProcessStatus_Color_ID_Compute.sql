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
