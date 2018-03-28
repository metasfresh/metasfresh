CREATE OR REPLACE FUNCTION public.invoicecandidatetaxamt(p_C_Invoice_Candidate_ID numeric)
  RETURNS numeric AS
$BODY$
DECLARE
	v_TaxAmount  	NUMERIC := 0;
BEGIN
	--	compute tax amt
	BEGIN
		SELECT CASE
				WHEN (coalesce(ic.IsTaxIncluded_Override, ic.isTaxIncluded) ='N' AND t.isWholeTax='N')  THEN
					round((ic.linenetamt * round(t.rate/100, 12)),  c.StdPrecision)
				ELSE 0
		END as TaxAmt	
		INTO	v_TaxAmount
		FROM C_Invoice_Candidate ic
		JOIN C_Tax t ON (ic.C_Tax_ID = t.C_Tax_ID)
		JOIN C_Currency c ON (ic.C_Currency_ID = ic.C_Currency_ID)
		WHERE ic.C_Invoice_Candidate_ID = p_C_Invoice_Candidate_ID;
	END;
	RETURN	v_TaxAmount;
END;
$BODY$
  LANGUAGE plpgsql STABLE
  COST 100;
COMMENT ON FUNCTION public.invoicecandidatetaxamt(numeric) IS 'This function computes the tax amount when isTaxIncluded=N and isWholeTax=N. In these cases return 0 ';
