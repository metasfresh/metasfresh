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
ALTER FUNCTION acctbalance(numeric, numeric, numeric)
  OWNER TO adempiere;
COMMENT ON FUNCTION acctbalance(numeric, numeric, numeric) IS 'Also used by mondrian cubes. Please keep in sync with (java) AccountBL.calculateBalance().'
