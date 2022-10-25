DROP FUNCTION IF EXISTS computeFrameOrderRemainingQtyPercent(p_call_order_id NUMERIC)
;

CREATE FUNCTION computeFrameOrderRemainingQtyPercent(p_call_order_id numeric)
    RETURNS numeric

    STABLE
    LANGUAGE sql
AS
$$


SELECT 100 - (SUM(otherCOD.qtyentered / cos.qtyentered) * 100) AS sum
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
              THEN otherCallOrder.preparationdate <= currentOrder.preparationdate
              ELSE otherCallOrder.datepromised < currentOrder.datepromised
      END

  AND CASE
          WHEN otherCallOrder.preparationdate = currentOrder.preparationdate
              THEN otherCallOrder.c_order_ID <= currentOrder.c_order_ID AND otherCallOrder.preparationdate <= currentOrder.preparationdate
              ELSE otherCallOrder.preparationdate <= currentOrder.preparationdate OR otherCallOrder.c_order_ID <= currentOrder.c_order_ID
      END

GROUP BY cod.qtyentered;

$$
;

-- select computeFrameOrderRemainingQtyPercent(1000368)


