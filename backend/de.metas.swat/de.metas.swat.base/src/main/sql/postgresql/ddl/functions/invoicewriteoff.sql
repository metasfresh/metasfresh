-- Function: invoicewriteoff(numeric)

-- DROP FUNCTION invoicewriteoff(numeric);

CREATE OR REPLACE FUNCTION invoicewriteoff(p_c_invoice_id numeric)
  RETURNS numeric AS
$BODY$
DECLARE
	v_Currency_ID		NUMERIC(10);
	v_WriteOffAmount  	NUMERIC := 0;
	v_MultiplierAP      	NUMERIC := 0;
	v_MultiplierCM      	NUMERIC := 0;
	v_Precision            	NUMERIC := 0;
	v_Min            	NUMERIC := 0;
	ar			RECORD;
	s			RECORD;

BEGIN
	--	Get Currency
	BEGIN
		SELECT	MAX(C_Currency_ID),  MAX(MultiplierAP), MAX(Multiplier)
		INTO	v_Currency_ID,  v_MultiplierAP, v_MultiplierCM
		FROM	C_Invoice_v		--	corrected for CM / Split Payment
		WHERE	C_Invoice_ID = p_C_Invoice_ID;
	EXCEPTION	--	Invoice in draft form
		WHEN OTHERS THEN
            	RAISE NOTICE 'InvoiceOpen - %', SQLERRM;
			RETURN NULL;
	END;

	SELECT StdPrecision
	    INTO v_Precision
	    FROM C_Currency
	    WHERE C_Currency_ID = v_Currency_ID;

	SELECT 1/10^v_Precision INTO v_Min;

	--	Calculate Allocated Amount
	FOR ar IN 
		SELECT	a.AD_Client_ID, a.AD_Org_ID,
		al.Amount, al.DiscountAmt, al.WriteOffAmt,
		a.C_Currency_ID, a.DateTrx
		FROM	C_AllocationLine al
		INNER JOIN C_AllocationHdr a ON (al.C_AllocationHdr_ID=a.C_AllocationHdr_ID)
		WHERE	al.C_Invoice_ID = p_C_Invoice_ID
          	AND   a.IsActive='Y'
	LOOP
        v_WriteOffAmount := v_WriteOffAmount
			+ currencyConvert(ar.WriteOffAmt, ar.C_Currency_ID, v_Currency_ID, ar.DateTrx, null, ar.AD_Client_ID, ar.AD_Org_ID);
		
      	RAISE NOTICE '   WriteOffAmt=%', v_WriteOffAmount;
	END LOOP;

   
	v_WriteOffAmount := ROUND(COALESCE(v_WriteOffAmount,0), v_Precision);
	RETURN	v_WriteOffAmount;
END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION invoicewriteoff(numeric) OWNER TO adempiere;
