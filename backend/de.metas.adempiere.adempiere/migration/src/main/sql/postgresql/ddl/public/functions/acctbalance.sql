/*
Take care if you want to use `AcctBalance` when calculating an account balance (saldo) in a report.
It could be that the report result is okay, but the user sees it wrong because it is not
    similar with the results of `acctBalanceToDate` which is used in the report `Saldobilanz`.

It's not a problem of correctness, but of consistency with what the user expects: `acctBalanceToDate` directly uses `debit - credit`,
it doesn't take into account account type and account sign like `acctBalance` does.

If in doubt, please ask mark, and give him the link below.


More explanations in https://github.com/metasfresh/metasfresh/issues/6121#issuecomment-598564338
*/



-- DROP FUNCTION acctbalance(numeric, numeric, numeric);

CREATE OR REPLACE FUNCTION acctbalance(p_account_id numeric, p_amtdr numeric, p_amtcr numeric)
  RETURNS numeric AS
$BODY$
DECLARE
	v_balance	NUMERIC;
	v_AccountType   C_ElementValue.AccountType%TYPE;
    v_AccountSign   C_ElementValue.AccountSign%TYPE;

BEGIN
	    v_balance := p_AmtDr - p_AmtCr;
	    --
	    IF (p_Account_ID > 0) THEN
	        SELECT AccountType, AccountSign
	          INTO v_AccountType, v_AccountSign
	        FROM C_ElementValue
	        WHERE C_ElementValue_ID=p_Account_ID;
	   --   DBMS_OUTPUT.PUT_LINE('Type=' || v_AccountType || ' - Sign=' || v_AccountSign);
	        --  Natural Account Sign
	        IF (v_AccountSign='N') THEN
	            IF (v_AccountType IN ('A','E')) THEN
	                v_AccountSign := 'D';
	            ELSE
	                v_AccountSign := 'C';
	            END IF;
	        --  DBMS_OUTPUT.PUT_LINE('Type=' || v_AccountType || ' - Sign=' || v_AccountSign);
	        END IF;
	        --  Debit Balance
	        IF (v_AccountSign = 'C') THEN
	            v_balance := p_AmtCr - p_AmtDr;
	        END IF;
	    END IF;
	    --
	    RETURN v_balance;
	EXCEPTION WHEN OTHERS THEN
	    -- In case Acct not found
    	RETURN  p_AmtDr - p_AmtCr;

END;

$BODY$
  LANGUAGE plpgsql STABLE
  COST 100;
COMMENT ON FUNCTION acctbalance(numeric, numeric, numeric) IS 'Also used by mondrian cubes. Please keep in sync with (java) AccountBL.calculateBalance().'
