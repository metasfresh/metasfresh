-- drop the old functions:
DROP FUNCTION IF EXISTS invoiceopentodate(numeric,
                                          numeric,
                                          date)
;

DROP FUNCTION IF EXISTS invoiceOpenToDate(numeric,
                                          numeric,
                                          timestamp WITH TIME ZONE)
;

DROP FUNCTION IF EXISTS invoiceOpenToDate(numeric,
                                          numeric,
                                          char,
                                          timestamp WITH TIME ZONE)
;

DROP FUNCTION IF EXISTS invoiceOpenToDate(numeric,
                                          numeric,
                                          char,
                                          timestamp WITH TIME ZONE,
                                          numeric)
;

DROP FUNCTION IF EXISTS invoiceOpenToDate(
    p_C_Invoice_ID            numeric,
    p_C_InvoicePaySchedule_ID numeric,
    /* DateType: T - use DateTrx, A - use DateAcct, X - don't enforce the date */
    p_DateType                char(1),
    p_Date                    timestamp WITH TIME ZONE,
    p_Last_AllocationLine_ID  numeric,
    /** Currency of the result. If null, invoice currency will be used */
    p_Result_Currency_ID      numeric
)
;

-- drop current function:
DROP FUNCTION IF EXISTS invoiceOpenToDate(
    p_C_Invoice_ID            numeric,
    p_C_InvoicePaySchedule_ID numeric,
    /* DateType: T - use DateTrx, A - use DateAcct, X - don't enforce the date */
    p_DateType                char(1),
    p_Date                    timestamp WITH TIME ZONE,
    p_Last_AllocationLine_ID  numeric,
    /** Currency of the result. If null, invoice currency will be used */
    p_Result_Currency_ID      numeric,
    p_Exclude_Payment_IDs     numeric[],
    p_ReturnNullOnError       char(1)
)
;



DROP TYPE IF EXISTS InvoiceOpenResult
;

CREATE TYPE InvoiceOpenResult AS
(
    GrandTotal         numeric,
    OpenAmt            numeric,
    PaidAmt            numeric,
    C_Currency_ID      numeric,
    HasAllocations     char(1),
    InvoiceDocBaseType char(3)
)
;

COMMENT ON TYPE InvoiceOpenResult IS 'The result of some invoiceOpen functions. It mainly contains the invoice grand total, open amount, paid amount and the currency of those amounts.'
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
    v_Currency_ID              numeric;
    v_Precision                numeric := 0;
    v_Min                      numeric := 0;
    --
    v_PaidAmt                  numeric := 0;
    v_Remaining                numeric := 0;
    v_AllocAmtSource           numeric := 0;
    v_AllocAmtConv             numeric := 0;
    --
    v_result                   InvoiceOpenResult;
    --
    allocationline             record;
    invoiceschedule            record;
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
    -- Get Invoice total, multipliers and currency informations
    BEGIN
        SELECT SUM(i.GrandTotal)
             , (CASE
                    WHEN p_DateType = 'A' THEN i.DateAcct
                                          ELSE i.DateInvoiced
                END) AS Date
             , i.DocBaseType
             , i.C_ConversionType_ID
             , i.AD_Client_ID
             , i.AD_Org_ID
             , MAX(i.MultiplierAP)
             , MAX(i.Multiplier)
             , cy.C_Currency_ID
             , cy.StdPrecision
             , 1 / 10 ^ cy.StdPrecision
        INTO
            v_TotalOpenAmt
            , v_InvoiceDate
            , v_InvoiceDocBaseType
            , v_InvoiceConversionType_ID, v_InvoiceClient_ID, v_InvoiceOrg_ID
            , v_MultiplierAP, v_MultiplierCM
            , v_InvoiceCurrency_ID, v_Precision, v_Min
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
            cy.C_Currency_ID
               , i.DateAcct, i.DateInvoiced
               , i.DocBaseType
               , i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID;

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
    --

    --
    -- Initialize the result
    v_result.GrandTotal := v_TotalOpenAmt;
    v_result.C_Currency_ID := v_Currency_ID;
    v_result.HasAllocations := 'N';
    v_result.InvoiceDocBaseType := v_InvoiceDocBaseType;

    --
    --	Calculate Allocated Amount
    FOR allocationline IN
        SELECT a.AD_Client_ID
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
          AND (v_Exclude_Payment_IDs IS NULL OR al.c_payment_id IS NULL OR al.c_payment_id != ANY (v_Exclude_Payment_IDs))
        -- for debugging: have a predictible order
        -- ORDER BY (case when p_DateType='T' then a.DateAcct else a.DateTrx end), al.C_AllocationLine_ID
        LOOP
            v_result.HasAllocations := 'Y';

            -- Skip allocation lines which are for given p_Date and which are after given p_Last_AllocationLine_ID
            -- NOTE: the p_Last_AllocationLine_ID parameter is used by some queries which are checking preciselly how much was allocated until a given allocation line.
            IF (p_Last_AllocationLine_ID IS NOT NULL AND p_Date IS NOT NULL
                AND p_Date::date = allocationline.Date::date
                AND allocationline.C_AllocationLine_ID > p_Last_AllocationLine_ID)
            THEN
                RAISE DEBUG '   Skip C_AllocationLine_ID=% because is after given Last_AllocationLine_ID=%', allocationline.C_AllocationLine_ID, p_Last_AllocationLine_ID;
                CONTINUE;
            END IF;

            v_AllocAmtSource := allocationline.Amount + allocationline.DiscountAmt + allocationline.WriteOffAmt;
            v_AllocAmtConv := currencyConvert(v_AllocAmtSource, allocationline.C_Currency_ID, v_Currency_ID, allocationline.Date, NULL, allocationline.AD_Client_ID, allocationline.AD_Org_ID);

            v_PaidAmt := v_PaidAmt + v_AllocAmtConv * v_MultiplierAP;
            RAISE DEBUG '   C_AllocationLine_ID=%: Amount(source->target currency)= %->%, Date=%', allocationline.C_AllocationLine_ID, v_AllocAmtSource, v_AllocAmtConv, allocationline.Date;
            RAISE DEBUG '   => PaidAmt(Accumulated)=%', v_PaidAmt;
        END LOOP;

    --
    --  Do we have a Payment Schedule ?
    IF (p_C_InvoicePaySchedule_ID IS NOT NULL AND p_C_InvoicePaySchedule_ID > 0) THEN --   if not valid = lists invoice amount
        v_Remaining := v_PaidAmt;
        FOR invoiceschedule IN
            SELECT C_InvoicePaySchedule_ID, DueAmt
            FROM C_InvoicePaySchedule
            WHERE C_Invoice_ID = p_C_Invoice_ID
              AND IsValid = 'Y'
            ORDER BY DueDate
            LOOP
                IF (invoiceschedule.C_InvoicePaySchedule_ID = p_C_InvoicePaySchedule_ID) THEN
                    v_TotalOpenAmt := (invoiceschedule.DueAmt * v_MultiplierCM) - v_Remaining;
                    IF (invoiceschedule.DueAmt - v_Remaining < 0) THEN
                        v_TotalOpenAmt := 0;
                    END IF;
                ELSE -- calculate amount, which can be allocated to next schedule
                    v_Remaining := v_Remaining - invoiceschedule.DueAmt;
                    IF (v_Remaining < 0) THEN
                        v_Remaining := 0;
                    END IF;
                END IF;
            END LOOP;
    ELSE
        v_TotalOpenAmt := v_TotalOpenAmt - v_PaidAmt;
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


/* Legacy version: invoice open to DateTrx */
CREATE OR REPLACE FUNCTION invoiceOpenToDate(p_c_invoice_id            numeric,
                                             p_c_invoicepayschedule_id numeric,
                                             p_DateTrx                 timestamp WITH TIME ZONE
)
    RETURNS numeric
    LANGUAGE plpgsql
    VOLATILE
AS
$BODY$
DECLARE
    v_result InvoiceOpenResult;
BEGIN
    v_result := invoiceOpenToDate(
            p_C_Invoice_ID,
            p_C_InvoicePaySchedule_ID,
            'T', -- p_DateType
            p_DateTrx
        );

    IF (v_result IS NULL) THEN
        RETURN NULL;
    END IF;

    RETURN v_result.OpenAmt;
END;
$BODY$
;
