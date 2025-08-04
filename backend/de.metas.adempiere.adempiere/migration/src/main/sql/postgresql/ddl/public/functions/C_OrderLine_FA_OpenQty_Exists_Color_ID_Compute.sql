DROP FUNCTION IF EXISTS C_OrderLine_FA_OpenQty_Exists_Color_ID_Compute (
    p_C_OrderLine numeric
)
;

CREATE FUNCTION C_OrderLine_FA_OpenQty_Exists_Color_ID_Compute(p_C_OrderLine numeric) RETURNS numeric
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
                WHEN (summary.qtydeliveredinuom IS NULL)                                                THEN v_Red
                WHEN (summary.qtydeliveredinuom <= 0)                                                   THEN v_Red
                WHEN (summary.qtydeliveredinuom > 0 AND summary.qtydeliveredinuom < summary.qtyentered) THEN v_Yellow
                WHEN (summary.qtydeliveredinuom >= summary.qtyentered)                                  THEN v_Green
            END
               )

    FROM public.c_orderline ol
             LEFT JOIN c_callordersummary summary ON (summary.c_orderline_id = ol.c_orderline_id OR
                                                      EXISTS (SELECT 1 FROM c_callorderdetail detail WHERE detail.c_orderline_id = ol.c_orderline_id AND detail.c_callordersummary_id = summary.c_callordersummary_id))

    WHERE ol.c_orderline_id = p_C_OrderLine
    INTO v_ColorID;

    RETURN v_ColorID;
END;
$$
;


/* WITH colors AS (SELECT C_OrderLine_FA_OpenQty_Exists_Color_ID_Compute(ol.c_orderline_id) AS color, ol.*
                FROM c_orderline ol
                         JOIN c_order o ON o.c_order_id = ol.c_order_id)
SELECT name
FROM ad_color c
         JOIN colors o ON o.color = c.ad_color_id
; */
