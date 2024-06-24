-- DROP FUNCTION IF EXISTS paymentavailable(numeric);

CREATE OR REPLACE FUNCTION paymentavailable(p_c_payment_id numeric)
    RETURNS numeric
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_C_Charge_ID  NUMERIC(10);
    v_PayAmt       NUMERIC := 0;
    v_OpenAmt      NUMERIC := 0;
    v_AllocatedAmt NUMERIC := 0;
BEGIN
    --
    --	Get payment info
    SELECT C_Charge_ID, PayAmt
    INTO v_C_Charge_ID, v_PayAmt
    FROM C_Payment_v -- corrected for AP/AR
    WHERE C_Payment_ID = p_C_Payment_ID;

    --
    --  Payments with Charge are always fully allocated (legacy)
    IF (v_C_Charge_ID IS NOT NULL AND v_C_Charge_ID > 0) THEN
        RETURN 0;
    END IF;

    v_AllocatedAmt := paymentAllocatedAmt(p_c_payment_id);
    v_OpenAmt := v_PayAmt - v_AllocatedAmt;

    --	Ignore Rounding
    IF (v_OpenAmt BETWEEN -0.00999 AND 0.00999) THEN
        v_OpenAmt := 0;
    END IF;

    --	Round to penny
    v_OpenAmt := ROUND(COALESCE(v_OpenAmt, 0), 2);

    RETURN v_OpenAmt;
END;
$$
;
