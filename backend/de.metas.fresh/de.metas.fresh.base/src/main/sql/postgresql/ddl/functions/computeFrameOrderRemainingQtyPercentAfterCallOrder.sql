DROP FUNCTION IF EXISTS computeFrameOrderRemainingQtyPercentAfterCallOrder(p_call_order_id NUMERIC)
;

CREATE FUNCTION computeFrameOrderRemainingQtyPercentAfterCallOrder(p_call_order_id NUMERIC)
    RETURNS numeric

    STABLE
    LANGUAGE sql
AS
$$

SELECT  100-(SUM(otherCOD.qtyentered / cos.qtyentered) * 100) AS sum
FROM C_callOrderDetail cod
         JOIN C_Order currentOrder ON cod.c_order_id = currentOrder.c_order_id
         JOIN C_CallOrderSummary cos ON cos.c_callordersummary_id = cod.c_callordersummary_id
         JOIN C_callOrderDetail otherCOD ON cos.c_callordersummary_id = otherCOD.c_callordersummary_id
         JOIN C_Order otherCallOrder ON otherCOD.c_order_id = otherCallOrder.c_order_id
         JOIN C_Doctype dt ON otherCallOrder.c_doctype_id = dt.c_doctype_id
WHERE currentOrder.c_order_ID = p_call_order_id

  AND dt.docsubtype = 'CAO' -- Call Order
  AND currentOrder.docstatus IN ('CO', 'CL')
  AND otherCallOrder.docstatus IN ('CO', 'CL')
  AND CASE
          WHEN otherCallOrder.datepromised = currentOrder.datepromised
              THEN( CASE WHEN otherCallOrder.preparationdate = currentOrder.preparationdate THEN otherCallOrder.c_order_id <= currentOrder.c_order_id ELSE otherCallOrder.preparationdate < currentOrder.preparationdate END)
              ELSE (otherCallOrder.datepromised < currentOrder.datepromised )
      END

GROUP BY cod.c_callordersummary_id;


$$
;

-- select computeFrameOrderRemainingQtyPercentAfterCallOrder(1000364)