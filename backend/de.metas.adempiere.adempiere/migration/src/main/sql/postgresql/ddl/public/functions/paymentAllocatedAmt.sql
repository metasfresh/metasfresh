DROP FUNCTION IF EXISTS paymentAllocatedAmt(p_c_payment_id numeric)
;

CREATE OR REPLACE FUNCTION paymentAllocatedAmt(p_c_payment_id numeric)
    RETURNS numeric
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_C_Charge_ID                     NUMERIC(10);
    v_Payment_Currency_ID             NUMERIC(10);
    v_FixedConversion_FromCurrency_ID NUMERIC(10);
    v_FixedConversion_Rate            NUMERIC(10);
    v_PayAmt                          NUMERIC := 0;
    v_GL_AccountConceptualName        varchar(255);
    --
    v_AllocatedAmt                    NUMERIC := 0;
    v_CurrentAllocatedAmt             NUMERIC := 0;
    --
    allocationLine                    RECORD;
    glJournalLine                     record;
BEGIN
    --
    --	Get payment info
    SELECT C_Charge_ID,
           PayAmt,
           C_Currency_ID,
           source_currency_id,
           currencyrate,
           (CASE WHEN IsReceipt = 'Y' THEN 'B_UnallocatedCash_Acct' ELSE 'B_PaymentSelect_Acct' END)
    INTO
        v_C_Charge_ID,
        v_PayAmt,
        v_Payment_Currency_ID,
        v_FixedConversion_FromCurrency_ID,
        v_FixedConversion_Rate,
        v_GL_AccountConceptualName
    FROM C_Payment_v -- corrected for AP/AR
    WHERE C_Payment_ID = p_C_Payment_ID;

    --
    --  Payments with Charge are always fully allocated (legacy)
    IF (v_C_Charge_ID IS NOT NULL AND v_C_Charge_ID > 0) THEN
        RETURN v_PayAmt;
    END IF;

    --
    --	Calculate Allocated Amount from Allocation Lines
    v_AllocatedAmt := 0;
    FOR allocationLine IN (SELECT ah.AD_Client_ID,
                                  ah.AD_Org_ID,
                                  ah.C_Currency_ID,
                                  ah.DateTrx,
                                  SUM(al.Amount + al.PaymentWriteOffAmt) AS AllocatedAmt
                           FROM C_AllocationLine al
                                    INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                           WHERE al.C_Payment_ID = p_C_Payment_ID
                             AND ah.IsActive = 'Y'
                             AND al.IsActive = 'Y'
                             -- IMPORTANT: do not filter by DocStatus because if we do then
                             -- the payment testAllocation performed when the allocation is completed will skip that allocation
                             -- so the result will be wrong.
                             -- AND ah.DocStatus IN ('CO', 'CL')
                           GROUP BY ah.AD_Client_ID,
                                    ah.AD_Org_ID,
                                    ah.C_Currency_ID,
                                    ah.DateTrx)
        LOOP
            IF (v_FixedConversion_FromCurrency_ID IS NOT NULL
                AND v_FixedConversion_Rate IS NOT NULL
                AND v_FixedConversion_Rate <> 0
                AND v_FixedConversion_FromCurrency_ID = allocationLine.C_Currency_ID)
            THEN
                v_CurrentAllocatedAmt := allocationLine.AllocatedAmt / v_FixedConversion_Rate;
                v_CurrentAllocatedAmt := currencyround(v_CurrentAllocatedAmt, v_Payment_Currency_ID, 'N');
                RAISE DEBUG '[C_Payment_ID=%] Alloc Amt: % (currency:%) => % (currency:%) | FIXED currencyRate=%', p_c_payment_id, allocationLine.AllocatedAmt, allocationLine.c_currency_id, v_CurrentAllocatedAmt, v_Payment_Currency_ID, v_FixedConversion_Rate;
            ELSE
                v_CurrentAllocatedAmt := currencyConvert(
                        allocationLine.AllocatedAmt,
                        allocationLine.C_Currency_ID,
                        v_Payment_Currency_ID,
                        allocationLine.DateTrx,
                        NULL,
                        allocationLine.AD_Client_ID,
                        allocationLine.AD_Org_ID);
                RAISE DEBUG '[C_Payment_ID=%] Alloc Amt: % (currency:%) => % (currency:%)', p_c_payment_id, allocationLine.AllocatedAmt, allocationLine.c_currency_id, v_CurrentAllocatedAmt, v_Payment_Currency_ID;
            END IF;

            v_AllocatedAmt := v_AllocatedAmt + v_CurrentAllocatedAmt;
            RAISE DEBUG '[C_Payment_ID=%] => AllocatedAmt: %', p_c_payment_id, v_AllocatedAmt;
        END LOOP;

    --
    -- GL Journal clearing lines
    FOR glJournalLine IN (SELECT (CASE WHEN l.postingsign = 'C' THEN - l.amount ELSE l.amount END)   AS amount,
                                 j.c_currency_id,
                                 (CASE WHEN l.postingsign = 'C' THEN - l.amtacct ELSE l.amtacct END) AS amtacct,
                                 j.acct_currency_id,
                                 j.datedoc,
                                 l.ad_client_id,
                                 l.ad_org_id,
                                 l.sap_gljournal_id,
                                 l.sap_gljournalline_id
                          FROM SAP_GLJournalLine l
                                   INNER JOIN SAP_GLJournal j ON j.sap_gljournal_id = l.sap_gljournal_id
                          WHERE TRUE
                            AND l.Processed = 'Y'
                            AND j.DocStatus IN ('CO', 'CL')
                            AND l.OI_Payment_ID = p_c_payment_id
                            AND l.OI_AccountConceptualName = v_GL_AccountConceptualName)
        LOOP
            IF (glJournalLine.c_currency_id = v_Payment_Currency_ID) THEN
                v_CurrentAllocatedAmt := glJournalLine.amount;
            ELSIF (glJournalLine.acct_currency_id = v_Payment_Currency_ID) THEN
                v_CurrentAllocatedAmt := glJournalLine.amtacct;
            ELSIF (v_FixedConversion_FromCurrency_ID IS NOT NULL
                AND v_FixedConversion_Rate IS NOT NULL
                AND v_FixedConversion_Rate <> 0
                AND v_FixedConversion_FromCurrency_ID = glJournalLine.c_currency_id) THEN
                v_CurrentAllocatedAmt := glJournalLine.amount / v_FixedConversion_Rate;
                v_CurrentAllocatedAmt := currencyround(v_CurrentAllocatedAmt, v_Payment_Currency_ID, 'N');
                RAISE DEBUG '[SAP_GLJournalLine=%/%] Alloc Amt: % (currency:%) => % (currency:%) | FIXED currencyRate=%', glJournalLine.sap_gljournal_id, glJournalLine.sap_gljournalline_id,
                    glJournalLine.amount, glJournalLine.c_currency_id, v_CurrentAllocatedAmt, v_Payment_Currency_ID, v_FixedConversion_Rate;
            ELSE
                v_CurrentAllocatedAmt := currencyConvert(
                        glJournalLine.Amount,
                        glJournalLine.C_Currency_ID,
                        v_Payment_Currency_ID,
                        glJournalLine.DateDoc,
                        NULL,
                        glJournalLine.AD_Client_ID,
                        glJournalLine.AD_Org_ID);
                RAISE DEBUG '[SAP_GLJournalLine=%/%] Alloc Amt: % (currency:%) => % (currency:%)', glJournalLine.sap_gljournal_id, glJournalLine.sap_gljournalline_id,
                    glJournalLine.amount, glJournalLine.c_currency_id, v_CurrentAllocatedAmt, v_Payment_Currency_ID;
            END IF;

            v_AllocatedAmt := v_AllocatedAmt + v_CurrentAllocatedAmt;
            RAISE DEBUG '[SAP_GLJournalLine=%/%] => AllocatedAmt: %', glJournalLine.sap_gljournal_id, glJournalLine.sap_gljournalline_id, v_AllocatedAmt;
        END LOOP;

    --
    RETURN v_AllocatedAmt;
END;
$$
;
