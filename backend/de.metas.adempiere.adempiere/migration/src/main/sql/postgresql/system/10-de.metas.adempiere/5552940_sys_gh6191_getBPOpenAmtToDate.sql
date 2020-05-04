DROP FUNCTION IF EXISTS getBPOpenAmtToDate(numeric, numeric, date, numeric, numeric, text, text);

CREATE OR REPLACE FUNCTION getBPOpenAmtToDate(p_AD_Client_ID numeric,
                                              p_AD_Org_ID numeric,
                                              p_DateTo date,
                                              p_C_BPartner_id numeric,
                                              p_C_Currency_ID numeric,
                                              p_UseDateActual text = 'Y',
                                              p_IsSOTrx text = 'Y')
    RETURNS numeric
AS
$$
DECLARE
    v_sum numeric;
BEGIN

    SELECT INTO v_sum sum(
                              (SELECT openamt
                               FROM invoiceOpenToDate(
                                       p_C_Invoice_ID := i.C_Invoice_ID,
                                       p_c_invoicepayschedule_id := COALESCE(ips.C_InvoicePaySchedule_ID, 0::numeric),
                                       p_DateType := (
                                           CASE
                                               WHEN p_useDateActual = 'Y' THEN 'A'
                                                                          ELSE 'T'
                                           END
                                           ),
                                       p_Date := p_dateTo,
                                       p_Result_Currency_ID := p_C_Currency_ID
                                   )
                              )
                          )
    FROM C_Invoice i
             LEFT OUTER JOIN C_InvoicePaySchedule ips
                             ON i.C_Invoice_ID = ips.C_Invoice_ID
                                 AND ips.isvalid = 'Y'
                                 AND ips.isActive = 'Y'
    WHERE i.IsSOTrx = p_IsSOTrx
      AND i.DocStatus IN ('CO', 'CL')
      AND i.c_bpartner_id = p_c_bpartner_id
      AND i.AD_CLient_ID = p_AD_Client_ID
      AND (COALESCE(p_AD_Org_ID, 0) <= 0 OR i.AD_Org_ID = p_AD_Org_ID);

    RETURN v_sum;
END ;
$$
    LANGUAGE plpgsql VOLATILE;