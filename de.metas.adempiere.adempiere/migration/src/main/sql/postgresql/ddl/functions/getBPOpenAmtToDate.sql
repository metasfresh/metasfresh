DROP FUNCTION IF EXISTS getBPOpenAmtToDate(numeric, numeric, date, numeric, numeric, text, text);

CREATE OR REPLACE FUNCTION getBPOpenAmtToDate(p_AD_Client_ID numeric,
                                              p_AD_Org_ID numeric,
                                              p_Date date,
                                              p_C_BPartner_id numeric,
                                              p_C_Currency_ID numeric,
                                              p_UseDateAcct text = 'Y',
                                              p_IsSOTrx text = 'Y')
    RETURNS numeric
AS
$$
DECLARE
    v_sum      numeric;
    v_orgNotSet    boolean;
    v_dateType text;
BEGIN
    SELECT INTO v_orgNotSet COALESCE(p_AD_Org_ID, 0) < 0;

    SELECT INTO v_dateType CASE
                               WHEN p_UseDateAcct = 'Y' THEN 'A'
                                                        ELSE 'T'
                           END;
    WITH t AS
             (SELECT i.C_Invoice_ID,
                     ips.C_InvoicePaySchedule_ID

              FROM C_Invoice i
                       LEFT OUTER JOIN C_InvoicePaySchedule ips
                                       ON i.C_Invoice_ID = ips.C_Invoice_ID
                                           AND ips.isvalid = 'Y'
                                           AND ips.isActive = 'Y'
              WHERE TRUE
                AND i.IsSOTrx = p_IsSOTrx
                AND (CASE
                         WHEN p_UseDateAcct = 'Y' THEN i.dateacct
                                                  ELSE i.dateinvoiced
                     END) <= p_Date
                AND i.DocStatus IN ('CO', 'CL')
                AND i.c_bpartner_id = p_c_bpartner_id
                AND i.AD_CLient_ID = p_AD_Client_ID
                AND (v_orgNotSet OR i.AD_Org_ID = p_AD_Org_ID))

    SELECT INTO v_sum sum(
                              (SELECT openamt
                               FROM invoiceOpenToDate(
                                       p_C_Invoice_ID := t.C_Invoice_ID,
                                       p_c_invoicepayschedule_id := COALESCE(t.C_InvoicePaySchedule_ID, 0::numeric),
                                       p_DateType := v_dateType,
                                       p_Date := p_Date,
                                       p_Result_Currency_ID := p_C_Currency_ID
                                   )
                              )
                          )

    FROM t;


    RETURN coalesce(v_sum, 0);
END ;
$$
    LANGUAGE plpgsql VOLATILE;


-- TEST
-- SELECT value,
--        C_Bpartner_ID,
--        getBPOpenAmtToDate(1000000,
--                           1000000,
--                           '9999-01-01' :: date,
--                           C_BPartner_ID,
--                           318,
--                           'Y',
--                           'Y')
--
-- FROM C_Bpartner;

