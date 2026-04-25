-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/functions/invoiceopentodate.sql
-- https://github.com/metasfresh/me03/issues/29368
-- Unify invoiceOpenToDate so it handles proforma invoices (DocBaseType IN APF/ARF, IsFinancial='N')
-- internally via C_Payment.Proforma_Invoice_ID + DocStatus IN (CO,CL). The separate function
-- proformaInvoiceOpen() is dropped in this same PR (the COALESCE in PaySelectionUpdater.buildInvoiceSql
-- collapses to a single invoiceOpen() call).
--
-- The 3-arg overload is unchanged and not re-created here.

DROP FUNCTION IF EXISTS proformaInvoiceOpen(numeric)
;



CREATE OR REPLACE FUNCTION invoiceOpenToDate(
    p_C_Invoice_ID            numeric,
    p_C_InvoicePaySchedule_ID numeric,
    /* DateType: T - use DateTrx, A - use DateAcct, X - don't enforce the date */
    p_DateType                char(1),
    p_Date                    timestamp WITH TIME ZONE,
    p_Last_AllocationLine_ID  numeric = NULL,
    /** Currency of the result. If null, invoice currency will be used */
    p_Result_Currency_ID      numeric = NULL,
    p_Exclude_Payment_IDs     numeric[] = NULL,
    p_ReturnNullOnError       char(1) = 'Y'
)
    RETURNS InvoiceOpenResult
    LANGUAGE plpgsql
    VOLATILE
AS
$BODY$
DECLARE
    v_Exclude_Payment_IDs      numeric[];
    v_InvoiceCurrency_ID       numeric;
    v_InvoiceDate              timestamp WITH TIME ZONE;
    v_InvoiceConversionType_ID numeric;
    v_InvoiceClient_ID         numeric;
    v_InvoiceOrg_ID            numeric;
    v_InvoiceDocBaseType       char(3);
    --
    v_TotalOpenAmt             numeric := 0;
    v_MultiplierAP             numeric := 0;
    v_MultiplierCM             numeric := 0;
    --
    v_Currency_ID              numeric; -- target/result currency
    v_Precision                numeric := 0; -- currency precision
    v_Min                      numeric := 0; -- rounding error (i.e. 0.1 ^ precision)
    v_BP_AccountConceptualName char(255);
    --
    v_TotalAllocatedAmt        numeric := 0; -- total allocated amount, in target currency, AP corrected
    v_Remaining                numeric := 0; -- locally used
    v_LineAllocAmtSource       numeric := 0; -- locally used
    v_LineAllocAmtConv         numeric := 0; -- locally used
    --
    v_result                   InvoiceOpenResult;
    --
    allocationLine             record;
    glJournalLine              record;
    invoiceSchedule            record;
BEGIN
    --
    -- Validate parameters
    IF (p_DateType NOT IN ('T', 'A', 'X')) THEN
        RAISE EXCEPTION 'Invalid DateType: %. Only T, A, X are allowed', p_DateType;
    END IF;
    IF (p_Exclude_Payment_IDs IS NOT NULL AND ARRAY_LENGTH(p_Exclude_Payment_IDs, 1) > 0) THEN
        v_Exclude_Payment_IDs := p_Exclude_Payment_IDs;
    END IF;

    --
    -- Proforma early-return (https://github.com/metasfresh/me03/issues/29368).
    -- The financial branch below reads C_Invoice_v which filters out IsFinancial='N'
    -- (i.e., proforma invoices). Proformas have no C_AllocationLine rows by design (no
    -- accounting), so the allocation walk would always return GrandTotal regardless of
    -- payment state. We instead detect "paid" via C_Payment.Proforma_Invoice_ID +
    -- DocStatus IN ('CO','CL'). Reversed payments (DocStatus='RE') don't count;
    -- reversal-payment rows have Proforma_Invoice_ID cleared in MPayment.reverseCorrectIt()
    -- (the dual-CO window guard), so they're excluded by the same filter naturally.
    DECLARE
        v_inv_IsFinancial      char(1);
        v_inv_DocBaseType      char(3);
        v_inv_GrandTotal       numeric;
        v_inv_Currency_ID      numeric;
        v_inv_Date             timestamp WITH TIME ZONE;
        v_inv_Client_ID        numeric;
        v_inv_Org_ID           numeric;
        v_inv_ConvType_ID      numeric;
        v_inv_MultiplierAP     numeric;
        v_pf_ResultCurrency_ID numeric;
        v_pf_Precision         numeric;
        v_pf_Min               numeric;
        v_pf_PaidAmt           numeric := 0;
        v_pf_OpenAmt           numeric;
        v_pf_GrandTotalConv    numeric;
        v_pf_Result            InvoiceOpenResult;
    BEGIN
        SELECT i.IsFinancial, dt.DocBaseType, i.GrandTotal, i.C_Currency_ID,
               CASE WHEN p_DateType = 'A' THEN i.DateAcct ELSE i.DateInvoiced END,
               i.AD_Client_ID, i.AD_Org_ID, i.C_ConversionType_ID,
               CASE WHEN charat(dt.docbasetype::character varying, 2)::text = ANY (ARRAY['P'::text, 'E'::text]) THEN -1.0 ELSE 1.0 END
          INTO v_inv_IsFinancial, v_inv_DocBaseType, v_inv_GrandTotal, v_inv_Currency_ID,
               v_inv_Date, v_inv_Client_ID, v_inv_Org_ID, v_inv_ConvType_ID, v_inv_MultiplierAP
          FROM C_Invoice i
                 INNER JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocType_ID
         WHERE i.C_Invoice_ID = p_C_Invoice_ID
           AND (CASE WHEN p_Date IS NULL   THEN TRUE
                     WHEN p_DateType = 'T' THEN i.DateInvoiced <= p_Date
                     WHEN p_DateType = 'A' THEN i.DateAcct <= p_Date
                                           ELSE TRUE END);

        IF v_inv_IsFinancial = 'N' THEN
            -- Resolve result currency and precision-derived rounding.
            IF p_Result_Currency_ID IS NOT NULL AND p_Result_Currency_ID > 0 AND p_Result_Currency_ID != v_inv_Currency_ID THEN
                v_pf_ResultCurrency_ID := p_Result_Currency_ID;
                v_pf_GrandTotalConv := currencyconvert(v_inv_GrandTotal, v_inv_Currency_ID, p_Result_Currency_ID, v_inv_Date, v_inv_ConvType_ID, v_inv_Client_ID, v_inv_Org_ID);
            ELSE
                v_pf_ResultCurrency_ID := v_inv_Currency_ID;
                v_pf_GrandTotalConv := v_inv_GrandTotal;
            END IF;
            SELECT cy.StdPrecision, 1 / 10 ^ cy.StdPrecision
              INTO v_pf_Precision, v_pf_Min
              FROM C_Currency cy
             WHERE cy.C_Currency_ID = v_pf_ResultCurrency_ID;

            -- SUM(PayAmt) over completed-or-closed payments tagged with this proforma.
            -- AC #16 (full-payment-only guard) ensures abs(PayAmt) = proforma.GrandTotal,
            -- so the SUM lands on 0 (unpaid or post-reversal) or GrandTotal (paid).
            -- p_Exclude_Payment_IDs is honoured for parity with the financial branch.
            SELECT COALESCE(SUM(
                CASE WHEN p.C_Currency_ID = v_pf_ResultCurrency_ID THEN p.PayAmt * v_inv_MultiplierAP
                     ELSE currencyconvert(p.PayAmt * v_inv_MultiplierAP, p.C_Currency_ID, v_pf_ResultCurrency_ID, p.DateTrx, NULL, p.AD_Client_ID, p.AD_Org_ID)
                END), 0)
              INTO v_pf_PaidAmt
              FROM C_Payment p
             WHERE p.Proforma_Invoice_ID = p_C_Invoice_ID
               AND p.DocStatus IN ('CO', 'CL')
               AND p.IsActive = 'Y'
               AND (v_Exclude_Payment_IDs IS NULL OR p.C_Payment_ID != ALL (v_Exclude_Payment_IDs));

            v_pf_OpenAmt := v_pf_GrandTotalConv - v_pf_PaidAmt;
            -- Snap rounding noise to zero using precision-derived tolerance.
            IF v_pf_OpenAmt > -v_pf_Min AND v_pf_OpenAmt < v_pf_Min THEN
                v_pf_OpenAmt := 0;
            END IF;
            v_pf_OpenAmt := ROUND(v_pf_OpenAmt, v_pf_Precision);

            v_pf_Result.GrandTotal := v_pf_GrandTotalConv;
            v_pf_Result.OpenAmt := v_pf_OpenAmt;
            v_pf_Result.PaidAmt := v_pf_GrandTotalConv - v_pf_OpenAmt;
            v_pf_Result.C_Currency_ID := v_pf_ResultCurrency_ID;
            v_pf_Result.HasAllocations := 'N';
            v_pf_Result.InvoiceDocBaseType := v_inv_DocBaseType;
            RETURN v_pf_Result;
        END IF;
        -- Else: fall through to the existing financial branch below.
    EXCEPTION
        WHEN OTHERS THEN
            IF p_ReturnNullOnError = 'Y' THEN
                RAISE DEBUG 'Error in proforma branch for C_Invoice_ID=% — %. Falling through.', p_C_Invoice_ID, SQLERRM;
            ELSE
                RAISE;
            END IF;
    END;

    --
    -- Get Invoice total, multipliers and currency informations
    BEGIN
        SELECT SUM(i.GrandTotal)
             , (CASE
                    WHEN p_DateType = 'A' THEN i.DateAcct
                                          ELSE i.DateInvoiced
                END)                                                                               AS Date
             , i.DocBaseType
             , i.C_ConversionType_ID
             , i.AD_Client_ID
             , i.AD_Org_ID
             , i.MultiplierAP
             , i.Multiplier
             , cy.C_Currency_ID
             , cy.StdPrecision
             , 1 / 10 ^ cy.StdPrecision
             , (CASE WHEN i.multiplierap < 0 THEN 'V_Liability_Acct' ELSE 'C_Receivable_Acct' END) AS BP_AccountConceptualName
        INTO
            v_TotalOpenAmt,
            v_InvoiceDate,
            v_InvoiceDocBaseType,
            v_InvoiceConversionType_ID,
            v_InvoiceClient_ID,
            v_InvoiceOrg_ID,
            v_MultiplierAP,
            v_MultiplierCM,
            v_InvoiceCurrency_ID,
            v_Precision,
            v_Min,
            v_BP_AccountConceptualName
        FROM C_Invoice_v i -- corrected for CM / Split Payment
                 INNER JOIN C_Currency cy ON (cy.C_Currency_ID = i.C_Currency_ID)
        WHERE i.C_Invoice_ID = p_C_Invoice_ID
          AND (CASE
                   WHEN p_Date IS NULL   THEN TRUE
                   WHEN p_DateType = 'T' THEN i.DateInvoiced <= p_Date
                   WHEN p_DateType = 'A' THEN i.DateAcct <= p_Date
                                         ELSE TRUE
               END)
        GROUP BY
            -- NOTE: we assume all invoice schedules (if any) are on same currency, DateAcct/DateInvoiced etc
            cy.C_Currency_ID,
            i.DateAcct,
            i.DateInvoiced,
            i.DocBaseType,
            i.MultiplierAP,
            i.Multiplier,
            i.C_ConversionType_ID,
            i.AD_Client_ID,
            i.AD_Org_ID;

        -- Check if we found our invoice.
        -- (if not, it means the invoice does not exist or the p_Date is before our invoice's Date)
        IF (v_TotalOpenAmt IS NULL) THEN
            RAISE EXCEPTION 'No invoice info found for C_Invoice_ID=%, DateType=%, Date=%', p_C_Invoice_ID, p_DateType, p_Date;
        END IF;

        -- Handle the case when we need to return the result in a different currency (from invoice currency)
        IF (p_Result_Currency_ID IS NOT NULL AND p_Result_Currency_ID > 0 AND v_InvoiceCurrency_ID != p_Result_Currency_ID) THEN
            RAISE DEBUG 'GrandTotal(abs)=%, InvoiceCurrency_ID=% (before converting to result currency)', v_TotalOpenAmt, v_InvoiceCurrency_ID;
            SELECT cy.StdPrecision, 1 / 10 ^ cy.StdPrecision
            INTO v_Precision, v_Min
            FROM C_Currency cy
            WHERE cy.C_Currency_ID = p_Result_Currency_ID;
            --
            v_Currency_ID := p_Result_Currency_ID;
            v_TotalOpenAmt := currencyconvert(v_TotalOpenAmt, v_InvoiceCurrency_ID, p_Result_Currency_ID, v_InvoiceDate, v_InvoiceConversionType_ID, v_InvoiceClient_ID, v_InvoiceOrg_ID);

            -- Use the invoice currency for result
        ELSE
            v_Currency_ID := v_InvoiceCurrency_ID;
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            IF (p_ReturnNullOnError = 'Y') THEN
                RAISE DEBUG 'Error while fetching invoice and currency information for C_Invoice_ID=% - %. Returning NULL.', p_C_Invoice_ID, SQLERRM;
                RETURN NULL;
            ELSE
                -- Re-raise the original exception here
                RAISE;
            END IF;
    END;
    RAISE DEBUG 'C_Currency_ID=%, GrandTotal(abs)=%, MultiplierAP=%, MultiplierCM=%', v_Currency_ID, v_TotalOpenAmt, v_MultiplierAP, v_MultiplierCM;
    RAISE DEBUG 'StdPrecision=%, Rounding tolerance=%', v_Precision, v_Min;
    RAISE DEBUG 'v_BP_AccountConceptualName=%', v_BP_AccountConceptualName;
    --

    --
    -- Initialize the result
    v_result.GrandTotal := v_TotalOpenAmt;
    v_result.C_Currency_ID := v_Currency_ID;
    v_result.HasAllocations := 'N';
    v_result.InvoiceDocBaseType := v_InvoiceDocBaseType;

    --
    --	Calculate Allocated Amount
    v_TotalAllocatedAmt := 0;
    FOR allocationLine IN (SELECT a.AD_Client_ID
                                , a.AD_Org_ID
                                , al.Amount
                                , al.DiscountAmt
                                , al.WriteOffAmt
                                , a.C_Currency_ID
                                , (CASE
                                       WHEN p_DateType = 'A' THEN a.DateAcct
                                                             ELSE a.DateTrx
                                   END) AS Date
                                , al.C_AllocationLine_ID
                           FROM C_AllocationLine al
                                    INNER JOIN C_AllocationHdr a ON (al.C_AllocationHdr_ID = a.C_AllocationHdr_ID)
                           WHERE al.C_Invoice_ID = p_C_Invoice_ID
                             AND a.IsActive = 'Y'
                             AND (CASE
                                      WHEN p_Date IS NULL   THEN TRUE
                                      WHEN p_DateType = 'T' THEN a.DateTrx <= p_Date
                                      WHEN p_DateType = 'A' THEN a.DateAcct <= p_Date
                                                            ELSE TRUE
                                  END)
                             AND (v_Exclude_Payment_IDs IS NULL OR al.c_payment_id IS NULL OR al.c_payment_id != ANY (v_Exclude_Payment_IDs)))
        LOOP
            v_result.HasAllocations := 'Y';

            -- Skip allocation lines which are for given p_Date and which are after given p_Last_AllocationLine_ID
            -- NOTE: the p_Last_AllocationLine_ID parameter is used by some queries which are checking preciselly how much was allocated until a given allocation line.
            IF (p_Last_AllocationLine_ID IS NOT NULL AND p_Date IS NOT NULL
                AND p_Date::date = allocationLine.Date::date
                AND allocationLine.C_AllocationLine_ID > p_Last_AllocationLine_ID)
            THEN
                RAISE DEBUG '   Skip C_AllocationLine_ID=% because is after given Last_AllocationLine_ID=%', allocationLine.C_AllocationLine_ID, p_Last_AllocationLine_ID;
                CONTINUE;
            END IF;

            v_LineAllocAmtSource := allocationLine.Amount + allocationLine.DiscountAmt + allocationLine.WriteOffAmt;
            v_LineAllocAmtConv := currencyConvert(v_LineAllocAmtSource, allocationLine.C_Currency_ID, v_Currency_ID, allocationLine.Date, NULL, allocationLine.AD_Client_ID, allocationLine.AD_Org_ID);

            v_TotalAllocatedAmt := v_TotalAllocatedAmt + v_LineAllocAmtConv * v_MultiplierAP;
            RAISE DEBUG '   C_AllocationLine_ID=%: Amount(source->target currency)= %->%, Date=%', allocationLine.C_AllocationLine_ID, v_LineAllocAmtSource, v_LineAllocAmtConv, allocationLine.Date;
            RAISE DEBUG '   => v_TotalAllocatedAmt=%', v_TotalAllocatedAmt;
        END LOOP;


    --
    -- GL Journal clearing lines
    FOR glJournalLine IN (SELECT (CASE WHEN l.postingsign = 'C' THEN - l.amount ELSE l.amount END)   AS amount,
                                 j.c_currency_id,
                                 (CASE WHEN l.postingsign = 'C' THEN - l.amtacct ELSE l.amtacct END) AS amtacct,
                                 j.acct_currency_id,
                                 (CASE
                                      WHEN p_DateType = 'A' THEN j.DateAcct
                                                            ELSE j.DateDoc
                                  END)                                                               AS Date,
                                 l.ad_client_id,
                                 l.ad_org_id,
                                 l.sap_gljournal_id,
                                 l.sap_gljournalline_id
                          FROM SAP_GLJournalLine l
                                   INNER JOIN SAP_GLJournal j ON j.sap_gljournal_id = l.sap_gljournal_id
                          WHERE TRUE
                            AND l.Processed = 'Y'
                            AND j.DocStatus IN ('CO', 'CL')
                            AND l.OI_Invoice_ID = p_C_Invoice_ID
                            AND l.OI_AccountConceptualName = v_BP_AccountConceptualName
                            AND (CASE
                                     WHEN p_Date IS NULL   THEN TRUE
                                     WHEN p_DateType = 'T' THEN j.DateDoc <= p_Date
                                     WHEN p_DateType = 'A' THEN j.DateAcct <= p_Date
                                                           ELSE TRUE
                                 END))
        LOOP
            v_result.HasAllocations := 'Y'; -- GL journal entries are also allocations
            
            IF (glJournalLine.c_currency_id = v_Currency_ID) THEN
                v_LineAllocAmtConv := glJournalLine.amount;
            ELSIF (glJournalLine.acct_currency_id = v_Currency_ID) THEN
                v_LineAllocAmtConv := glJournalLine.amtacct;
            ELSE
                v_LineAllocAmtConv := currencyConvert(
                        glJournalLine.amount,
                        glJournalLine.C_Currency_ID,
                        v_Currency_ID,
                        glJournalLine.Date,
                        NULL,
                        glJournalLine.AD_Client_ID,
                        glJournalLine.AD_Org_ID);
            END IF;

            v_TotalAllocatedAmt := v_TotalAllocatedAmt + v_LineAllocAmtConv * (-1) * v_MultiplierAP;
            RAISE DEBUG '   SAP_GLJournalLine_ID=%/%: Amount=% (%)', glJournalLine.sap_gljournal_id, glJournalLine.sap_gljournalline_id, v_LineAllocAmtConv, glJournalLine;
            RAISE DEBUG '   => v_TotalAllocatedAmt=%', v_TotalAllocatedAmt;
        END LOOP;


    --
    --  Do we have a Payment Schedule ?
    IF (p_C_InvoicePaySchedule_ID IS NOT NULL AND p_C_InvoicePaySchedule_ID > 0) THEN --   if not valid = lists invoice amount
        v_Remaining := v_TotalAllocatedAmt;
        FOR invoiceSchedule IN
            SELECT C_InvoicePaySchedule_ID, DueAmt
            FROM C_InvoicePaySchedule
            WHERE C_Invoice_ID = p_C_Invoice_ID
              AND IsValid = 'Y'
            ORDER BY DueDate
            LOOP
                IF (invoiceSchedule.C_InvoicePaySchedule_ID = p_C_InvoicePaySchedule_ID) THEN
                    v_TotalOpenAmt := (invoiceSchedule.DueAmt * v_MultiplierCM) - v_Remaining;
                    IF (invoiceSchedule.DueAmt - v_Remaining < 0) THEN
                        v_TotalOpenAmt := 0;
                    END IF;
                ELSE -- calculate amount, which can be allocated to next schedule
                    v_Remaining := v_Remaining - invoiceSchedule.DueAmt;
                    IF (v_Remaining < 0) THEN
                        v_Remaining := 0;
                    END IF;
                END IF;
            END LOOP;
    ELSE
        v_TotalOpenAmt := v_TotalOpenAmt - v_TotalAllocatedAmt;
    END IF;
    RAISE DEBUG 'Total Open Amt: %', v_TotalOpenAmt;

    --	Ignore Rounding
    IF (v_TotalOpenAmt > -v_Min AND v_TotalOpenAmt < v_Min) THEN
        v_TotalOpenAmt := 0;
    END IF;

    --	Round to currency precision
    v_TotalOpenAmt := ROUND(COALESCE(v_TotalOpenAmt, 0), v_Precision);

    --
    -- Update the result and return it
    v_result.OpenAmt := v_TotalOpenAmt;
    v_result.PaidAmt := v_result.GrandTotal - v_result.OpenAmt;

    RETURN v_result;
END;
$BODY$
;
