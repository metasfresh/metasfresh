DROP FUNCTION IF EXISTS C_PaySelectionLine_HasOpenCreditMemos_Color_ID_compute (
    p_paySelectionLine   c_payselectionline
)
;

CREATE OR REPLACE FUNCTION C_PaySelectionLine_HasOpenCreditMemos_Color_ID_compute(
    p_paySelectionLine   c_payselectionline
)
    RETURNS numeric
    LANGUAGE plpgsql
    STABLE
AS
$$
DECLARE
    v_hasOpenCreditMemos char(1);
BEGIN
    v_hasOpenCreditMemos := C_PaySelectionLine_HasOpenCreditMemos_compute(p_paySelectionLine);
    IF (v_hasOpenCreditMemos = 'Y') THEN
        RETURN getColor_ID_By_SysConfig('C_PaySelectionLine.HasOpenCreditMemos_ColorName');
    ELSE
        RETURN NULL;
    END IF;
END;
$$
;

