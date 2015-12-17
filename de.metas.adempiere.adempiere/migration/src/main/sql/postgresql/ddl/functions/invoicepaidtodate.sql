DROP FUNCTION IF EXISTS invoicePaidToDate(numeric, numeric, numeric, timestamp with time zone);

CREATE OR REPLACE FUNCTION invoicePaidToDate(p_C_Invoice_ID numeric, p_C_Currency_ID numeric, p_MultiplierAP numeric, p_Date timestamp with time zone)
RETURNS numeric AS
$BODY$
DECLARE
	v_MultiplierAP		numeric;
	v_DateType			char(1);
	v_PaidAmt			numeric := 0;
	v_result InvoiceOpenResult;
BEGIN
	--
	-- MultiplierAP
	v_MultiplierAP := coalesce(p_MultiplierAP, 1);
	if (v_MultiplierAP != 1 and v_MultiplierAP != -1) then
		raise exception 'Invalid MultiplierAP parameter. It shall be -1 or +1 but it was %', v_MultiplierAP;
	end if;
	
	--
	-- Get the open amount result
	if (p_Date is null) then
		v_DateType := 'X'; -- don't enforce the date
	else 
		v_DateType := 'T'; -- use DateTrx
	end if;
	v_Result := invoiceOpenToDate(p_C_Invoice_ID
					, null::numeric /* p_c_invoicepayschedule_id */
					, v_DateType
					, p_Date);
	if (v_Result is null) then
		return null;
	end if;
	
	--
	-- Backward compatibility support for p_C_Currency_ID parameter: make sure it's the invoice currency
	if (p_C_Currency_ID is not null and p_C_Currency_ID != v_Result.C_Currency_ID) then
		raise exception 'The C_Currency_ID parameter shall be the invoice currency (i.e. %) and not %',  v_Result.C_Currency_ID, p_C_Currency_ID;
	end if;

	-- Adjust the paid amount by v_MultiplierAP
	v_PaidAmt := v_Result.PaidAmt * v_MultiplierAP;
	raise debug 'C_Invoice_ID=%, Date=%/%, Result=%, MultiplierAP=% => PaidAmt=%', p_C_Invoice_ID, v_DateType, p_Date, v_Result, v_MultiplierAP, v_PaidAmt;
	
	return v_PaidAmt;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
