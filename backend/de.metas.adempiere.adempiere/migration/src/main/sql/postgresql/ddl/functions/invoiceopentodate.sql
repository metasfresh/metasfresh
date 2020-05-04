-- drop the old functions:
DROP FUNCTION IF EXISTS invoiceopentodate(numeric, numeric, date);
DROP FUNCTION IF EXISTS invoiceOpenToDate(numeric, numeric, timestamp with time zone);
DROP FUNCTION IF EXISTS invoiceOpenToDate(numeric, numeric, char, timestamp with time zone);
DROP FUNCTION IF EXISTS invoiceOpenToDate(numeric, numeric, char, timestamp with time zone, numeric);
-- drop current function:
DROP FUNCTION IF EXISTS invoiceOpenToDate(numeric, numeric, char, timestamp with time zone, numeric, numeric);



drop type if exists InvoiceOpenResult;
create type InvoiceOpenResult as (
	GrandTotal numeric
	, OpenAmt numeric
	, PaidAmt numeric
	--
	, C_Currency_ID numeric
);
COMMENT ON TYPE InvoiceOpenResult IS 'The result of some invoiceOpen functions. It mainly contains the invoice grand total, open amount, paid amount and the currency of those amounts.';


CREATE OR REPLACE FUNCTION invoiceOpenToDate
(
	p_c_invoice_id numeric
	, p_c_invoicepayschedule_id numeric
	/* DateType: T - use DateTrx, A - use DateAcct, X - don't enforce the date */
	, p_DateType char(1)
	, p_Date timestamp with time zone
	, p_Last_AllocationLine_ID numeric = null
	/** Currency of the result. If null, invoice currency will be used */ 
	, p_Result_Currency_ID numeric = null
)
RETURNS InvoiceOpenResult AS
$BODY$
DECLARE
	v_InvoiceCurrency_ID numeric;
	v_InvoiceDate timestamp with time zone;
	v_InvoiceConversionType_ID numeric;
	v_InvoiceClient_ID numeric;
	v_InvoiceOrg_ID numeric;
	--
	v_TotalOpenAmt numeric := 0;
	v_MultiplierAP numeric := 0;
	v_MultiplierCM numeric := 0;
	--
	v_Currency_ID numeric;
	v_Precision numeric := 0;
	v_Min numeric := 0;
	--
	v_PaidAmt numeric := 0;
	v_Remaining numeric := 0;
	v_AllocAmtSource numeric := 0;
	v_AllocAmtConv numeric := 0;
	--
	v_result InvoiceOpenResult;
	--
	allocationline record;
	invoiceschedule record;
BEGIN
	--
	-- Validate parameters
	if (p_DateType not in ('T', 'A', 'X')) then
		raise exception 'Invalid DateType: %. Only T, A, X are allowed', p_DateType;
	end if;
	if (p_DateType != 'X' and p_Date is null) then
		raise debug 'Null Date not allowed when DateType=% (C_Invoice_ID=%). Returning NULL.', p_DateType, p_C_Invoice_ID;
		return null;
	end if;
	
	--
	-- Get Invoice total, multipliers and currency informations
	BEGIN
		SELECT
			SUM(i.GrandTotal)
			, (case
				when p_DateType='A' then i.DateAcct
				else i.DateInvoiced
				end) as Date
			, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID
			, MAX(i.MultiplierAP), MAX(i.Multiplier)
			, cy.C_Currency_ID, cy.StdPrecision, 1/10^cy.StdPrecision
		INTO
			v_TotalOpenAmt
			, v_InvoiceDate
			, v_InvoiceConversionType_ID, v_InvoiceClient_ID, v_InvoiceOrg_ID
			, v_MultiplierAP, v_MultiplierCM
			, v_InvoiceCurrency_ID, v_Precision, v_Min
		FROM C_Invoice_v  i -- corrected for CM / Split Payment
		INNER JOIN C_Currency cy on (cy.C_Currency_ID=i.C_Currency_ID)
		WHERE i.C_Invoice_ID = p_C_Invoice_ID
		and (case
			when p_DateType='T' then i.DateInvoiced <= p_Date
			when p_DateType='A' then i.DateAcct <= p_Date
			else true
			end)
		GROUP BY
			-- NOTE: we assume all invoice schedules (if any) are on same currency, DateAcct/DateInvoiced etc
			cy.C_Currency_ID
			, i.DateAcct, i.DateInvoiced
			, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID
		;
		
		-- Check if we found our invoice.
		-- (if not, it means the invoice does not exist or the p_Date is before our invoice's Date) 
		if (v_TotalOpenAmt is null) then
			raise exception 'No invoice info found for C_Invoice_ID=%, DateType=%, Date=%', p_C_Invoice_ID, p_DateType, p_Date;
		end if;

		-- Handle the case when we need to return the result in a different currency (from invoice currency)
		if (p_Result_Currency_ID is not null and v_InvoiceCurrency_ID != p_Result_Currency_ID) then
			raise debug 'GrandTotal(abs)=%, InvoiceCurrency_ID=% (before converting to result currency)', v_TotalOpenAmt, v_InvoiceCurrency_ID;
			SELECT cy.StdPrecision, 1/10^cy.StdPrecision
			INTO v_Precision, v_Min
			FROM C_Currency cy
			where cy.C_Currency_ID=p_Result_Currency_ID;
			--
			v_Currency_ID := p_Result_Currency_ID;
			v_TotalOpenAmt := currencyconvert(v_TotalOpenAmt, v_InvoiceCurrency_ID, p_Result_Currency_ID, v_InvoiceDate, v_InvoiceConversionType_ID, v_InvoiceClient_ID, v_InvoiceOrg_ID);
			
		-- Use the invoice currency for result
		else
			v_Currency_ID := v_InvoiceCurrency_ID;
		end if;
	EXCEPTION
		WHEN OTHERS THEN
            RAISE DEBUG 'Error while fetching invoice and currency informations for C_Invoice_ID=% - %. Returning NULL.', p_C_Invoice_ID, SQLERRM;
			RETURN NULL;
	END;
	raise debug 'C_Currency_ID=%, GrandTotal(abs)=%, MultiplierAP=%, MultiplierCM=%', v_Currency_ID, v_TotalOpenAmt, v_MultiplierAP, v_MultiplierCM;
	raise debug 'StdPrecision=%, Rounding tolerance=%', v_Precision, v_Min;
	--
	
	--
	-- Initialize the result
	v_result.GrandTotal := v_TotalOpenAmt;
	v_result.C_Currency_ID := v_Currency_ID;

	--
	--	Calculate Allocated Amount
	FOR allocationline IN  
		SELECT a.AD_Client_ID, a.AD_Org_ID
			, al.Amount, al.DiscountAmt, al.WriteOffAmt
			, a.C_Currency_ID
			, (case
				when p_DateType='A' then a.DateAcct
				else a.DateTrx
			end) as Date
			, al.C_AllocationLine_ID
		FROM C_AllocationLine al
		INNER JOIN C_AllocationHdr a ON (al.C_AllocationHdr_ID=a.C_AllocationHdr_ID)
		WHERE al.C_Invoice_ID = p_C_Invoice_ID
          	AND a.IsActive='Y'
			AND (case
				when p_DateType='T' then a.DateTrx <= p_Date
				when p_DateType='A' then a.DateAcct <= p_Date
				else true
			end)
		-- for debugging: have a predictible order
		-- ORDER BY (case when p_DateType='T' then a.DateAcct else a.DateTrx end), al.C_AllocationLine_ID
	LOOP
		-- Skip allocation lines which are for given p_Date and which are after given p_Last_AllocationLine_ID
		-- NOTE: the p_Last_AllocationLine_ID parameter is used by some queries which are checking preciselly how much was allocated until a given allocation line.
		if (p_Last_AllocationLine_ID is not null and p_Date is not null
				and p_Date::date = allocationline.Date::date
				and allocationline.C_AllocationLine_ID > p_Last_AllocationLine_ID)
		then
			RAISE DEBUG '   Skip C_AllocationLine_ID=% because is after given Last_AllocationLine_ID=%', allocationline.C_AllocationLine_ID, p_Last_AllocationLine_ID;
			continue;
		end if;

		v_AllocAmtSource := allocationline.Amount + allocationline.DiscountAmt + allocationline.WriteOffAmt;
		v_AllocAmtConv := currencyConvert(v_AllocAmtSource, allocationline.C_Currency_ID, v_Currency_ID, allocationline.Date, NULL, allocationline.AD_Client_ID, allocationline.AD_Org_ID);

		v_PaidAmt := v_PaidAmt + v_AllocAmtConv * v_MultiplierAP;
		RAISE DEBUG '   C_AllocationLine_ID=%: Amount(source->target currency)= %->%, Date=%', allocationline.C_AllocationLine_ID, v_AllocAmtSource, v_AllocAmtConv, allocationline.Date;
		RAISE DEBUG '   => PaidAmt(Accumulated)=%', v_PaidAmt;
	END LOOP;

	--
	--  Do we have a Payment Schedule ?
	IF (p_C_InvoicePaySchedule_ID is not null and p_C_InvoicePaySchedule_ID > 0) THEN --   if not valid = lists invoice amount
		v_Remaining := v_PaidAmt;
		FOR invoiceschedule IN 
			SELECT  C_InvoicePaySchedule_ID, DueAmt
			FROM    C_InvoicePaySchedule
			WHERE	C_Invoice_ID = p_C_Invoice_ID
			AND   IsValid='Y'
			ORDER BY DueDate
		LOOP
			IF (invoiceschedule.C_InvoicePaySchedule_ID = p_C_InvoicePaySchedule_ID) THEN
				v_TotalOpenAmt := (invoiceschedule.DueAmt*v_MultiplierCM) - v_Remaining;
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
	v_TotalOpenAmt := ROUND(COALESCE(v_TotalOpenAmt,0), v_Precision);
	
	--
	-- Update the result and return it
	v_result.OpenAmt := v_TotalOpenAmt;
	v_result.PaidAmt := v_result.GrandTotal - v_result.OpenAmt;
	
	RETURN	v_result;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;


/* Legacy version: invoice open to DateTrx */
CREATE OR REPLACE FUNCTION invoiceOpenToDate(p_c_invoice_id numeric, p_c_invoicepayschedule_id numeric, p_DateTrx timestamp with time zone)
RETURNS numeric AS
$BODY$
DECLARE
	v_result InvoiceOpenResult;
BEGIN
	v_result := invoiceOpenToDate (
		p_C_Invoice_ID
		, p_C_InvoicePaySchedule_ID
		, 'T' -- p_DateType
		, p_DateTrx
	);
	
	if (v_result is null) then
		return null;
	end if;
	
	return v_result.OpenAmt;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
