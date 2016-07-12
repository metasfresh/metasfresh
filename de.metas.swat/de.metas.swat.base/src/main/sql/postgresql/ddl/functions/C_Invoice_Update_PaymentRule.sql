

DROP FUNCTION IF EXISTS C_Invoice_Update_PaymentRule ();

-- FRESH-506
-- This function changes the PaymentRule from 'S' (Check) to 'P' (OnCredit)
CREATE OR REPLACE FUNCTION C_Invoice_Update_PaymentRule ()

RETURNS void

AS
$$

UPDATE
	C_Invoice
SET
	PaymentRule = 'P'
WHERE 
	PaymentRule = 'S'

	
$$ 
LANGUAGE sql VOLATILE
;

