
DROP FUNCTION IF EXISTS report.Get_Successor_Period_recursive( p_C_Period_ID numeric, p_Steps int );
CREATE OR REPLACE FUNCTION report.Get_Successor_Period_recursive( p_C_Period_ID numeric, p_Steps int ) RETURNS numeric AS
$BODY$
/**
 * report.Get_Predecessor_Period_recursive
 * 
 * Gets a earlier period than the given one, based on the given steps. 1 step means the previous period, 2 steps means 
 * the one before the previous. is skips years    
 */
DECLARE
	v_C_Period_ID numeric;
BEGIN
	IF(p_steps <= 0) 
		THEN RETURN p_C_Period_ID;
	END IF;
	SELECT report.Get_Successor_Period( p_C_Period_ID ) INTO v_C_Period_ID;
	RETURN report.Get_Successor_Period_recursive( v_C_Period_ID, p_steps-1 );
END;
$BODY$
LANGUAGE plpgsql STABLE;


ALTER FUNCTION report.Get_Successor_Period_recursive( p_C_Period_ID numeric, p_Steps int ) OWNER TO adempiere;