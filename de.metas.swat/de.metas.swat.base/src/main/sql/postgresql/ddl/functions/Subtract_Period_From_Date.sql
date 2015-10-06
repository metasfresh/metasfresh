DROP FUNCTION IF EXISTS report.subtract_period_from_date( p_C_Calendar_ID numeric, p_Date date, p_numberOfPeriods int);
CREATE OR REPLACE FUNCTION report.subtract_period_from_date( p_C_Calendar_ID numeric, p_Date date, p_numberOfPeriods int) RETURNS date AS
/**
 * This Function subtracts the given number of Periods from the given Calendar from the given date. 
 * Example: You give it 05.05.20XX as a Date, and tell it to subtract 1 period and all periods are 1 month long.
 * -> it will return the 05.04.20XX 
 * If the periods do not have the same length, it will return a date that fits within the targeted period.
 * Example: You give it 31.05.2015 and, tell it to subtract 3 periods and all periods are 1 month long
 * -> it will give you 28.02.2015
 * 
 * The Function retrieves the length of a peroid in days, so it doesn't matter if the periods are 
 * months, quartals, tertials or years. 
 */
$BODY$
DECLARE 
	v_C_Period_ID numeric;
	v_DayOfPeriod int;
	v_Result date;
BEGIN
	SELECT report.Get_Period( p_C_Calendar_ID, p_Date ) INTO v_C_Period_ID;
	
	SELECT p_Date::Date - StartDate::Date INTO v_DayOfPeriod
	FROM C_Period WHERE C_Period_ID = v_C_Period_ID;

	SELECT 	StartDate + CASE WHEN EndDate::date - StartDate::date < v_DayOfPeriod THEN EndDate::date - StartDate::date ELSE v_DayOfPeriod END
	INTO 	v_Result 
	FROM	C_Period
	WHERE	C_Period_ID = report.Get_Predecessor_Period_Recursive( v_C_Period_ID, p_numberOfPeriods );
	
	RETURN v_Result;
END;
$BODY$
LANGUAGE plpgsql STABLE;