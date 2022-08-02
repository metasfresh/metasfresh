DROP FUNCTION IF EXISTS Update_C_BPartner_Stats()
;

CREATE OR REPLACE FUNCTION Update_C_BPartner_Stats()
    RETURNS void
AS
$BODY$

BEGIN

    -- TODO: legacy function, it needs updates/improvements,
    -- i.e.
    -- * check if the logic is still valid!!!
    -- * set/update socreditstatus too
    -- * take an array of C_BPartner_IDs as parameter
    -- * do an UPSERT instead of just update
    -- * consider precomputing the data in a temp table and then doing the update

    UPDATE C_BPartner_Stats bps
    SET ActualLifeTimeValue = x.ActualLifeTimeValue,
        SO_CreditUsed       = x.SO_CreditUsed,
        openitems           = x.TotalOpenBalance
    FROM (
             SELECT bp.C_BPartner_ID,

                    COALESCE((SELECT SUM(currencyBase(i.GrandTotal, i.C_Currency_ID, i.DateInvoiced, i.AD_Client_ID, i.AD_Org_ID))
                              FROM C_Invoice_v i
                              WHERE i.C_BPartner_ID = bp.C_BPartner_ID
                                AND i.IsSOTrx = 'Y'
                                AND i.DocStatus IN ('CO', 'CL')), 0) AS ActualLifeTimeValue,

                    COALESCE((SELECT SUM(currencyBase(invoiceOpen(i.C_Invoice_ID, i.C_InvoicePaySchedule_ID), i.C_Currency_ID, i.DateInvoiced, i.AD_Client_ID, i.AD_Org_ID))
                              FROM C_Invoice_v i
                              WHERE i.C_BPartner_ID = bp.C_BPartner_ID
                                AND i.IsSOTrx = 'Y'
                                AND i.IsPaid = 'N'
                                AND i.DocStatus IN ('CO', 'CL')), 0) AS SO_CreditUsed,

                    COALESCE((SELECT SUM(currencyBase(invoiceOpen(i.C_Invoice_ID, i.C_InvoicePaySchedule_ID), i.C_Currency_ID, i.DateInvoiced, i.AD_Client_ID, i.AD_Org_ID) * i.MultiplierAP)
                              FROM C_Invoice_v i
                              WHERE i.C_BPartner_ID = bp.C_BPartner_ID
                                AND i.IsPaid = 'N'
                                AND i.DocStatus IN ('CO', 'CL')), 0) -
                    COALESCE((SELECT SUM(currencyBase(Paymentavailable(p.C_Payment_ID), p.C_Currency_ID, p.DateTrx, p.AD_Client_ID, p.AD_Org_ID))
                              FROM C_Payment_v p
                              WHERE p.C_BPartner_ID = bp.C_BPartner_ID
                                AND p.IsAllocated = 'N'
                                AND p.C_Charge_ID IS NULL
                                AND p.DocStatus IN ('CO', 'CL')), 0) AS TotalOpenBalance
             FROM C_BPartner bp
         ) x

    WHERE bps.C_BPartner_ID = x.C_BPartner_ID;

END;

$BODY$
    LANGUAGE plpgsql
;




