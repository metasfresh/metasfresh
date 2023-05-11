DROP FUNCTION IF EXISTS C_PaySelectionLine_HasOpenCreditMemos_compute (
    p_paySelectionLine c_payselectionline
)
;

CREATE OR REPLACE FUNCTION C_PaySelectionLine_HasOpenCreditMemos_compute(
    p_paySelectionLine c_payselectionline
)
    RETURNS char(1)
    LANGUAGE plpgsql
    STABLE
AS
$$
DECLARE
    v_hasOpenCreditMemos char(1);
BEGIN

    SELECT (
               CASE
                   WHEN EXISTS(
                           SELECT 1
                           FROM C_Invoice i
                                    INNER JOIN C_DocType dt ON (dt.C_DocType_ID = i.C_DocType_ID)
                           WHERE i.C_BPartner_ID = p_paySelectionLine.C_BPartner_ID
                             AND i.IsSOTrx = p_paySelectionLine.IsSOTrx
                             AND i.IsPaid = 'N'
                             AND i.DocStatus IN ('CO', 'CL')
                             AND (dt.DocBaseType IN ('APC', 'ARC') OR i.GrandTotal < 0)
                       )
                       THEN 'Y'
                       ELSE 'N'
               END
               )::char(1)
    INTO v_hasOpenCreditMemos;

    RETURN v_hasOpenCreditMemos;
END;
$$
;

