DROP FUNCTION IF EXISTS report.Get_Predecessor_Period_recursive( p_C_Period_ID numeric, p_Steps int );
CREATE OR REPLACE FUNCTION report.Get_Predecessor_Period_recursive(p_C_Period_ID numeric, p_Steps int = 1)
RETURNS numeric AS
$BODY$
/**
 * Gets an earlier period than the given one, based on the given steps.
 * 0 steps (or negative) means given period,
 * 1 step (DEFAULT) means the previous period,
 * 2 steps means the one before the previous etc.
 */
DECLARE
	v_C_Period_ID numeric;
BEGIN
	if (p_Steps <= 0) then
		return p_C_Period_ID;
	end if;
	
	select t.C_Period_ID
	into v_C_Period_ID
	from (
		select 
			p.C_Period_ID
			, rank() over (order by y.FiscalYear desc, p.PeriodNo desc) as Rel_PeriodNo
			-- for debugging:
			, p.Name, y.FiscalYear, p.PeriodNo, y.C_Calendar_ID
			, ref_p.Name as Ref_Period, ref_p.PeriodNo as Ref_PeriodNo, ref_y.FiscalYear Ref_Year, ref_y.C_Calendar_ID as Ref_Calendar_ID
		from C_Period ref_p
		inner join C_Year ref_y on (ref_y.C_Year_ID=ref_p.C_Year_ID)
		, C_Period p
		inner join C_Year y on (y.C_Year_ID=p.C_Year_ID)
		--
		where true
			and ref_p.C_Period_ID=p_C_Period_ID
			-- Periods shall be on same calendar as our reference period
			and y.C_Calendar_ID=ref_y.C_Calendar_ID
			-- Only periods which are before our reference period
			and (
				y.FiscalYear::integer < ref_y.FiscalYear::integer
				or (y.FiscalYear::integer = ref_y.FiscalYear::integer and p.PeriodNo <= ref_p.PeriodNo)
			)
		--
		order by y.FiscalYear desc, p.PeriodNo desc
	) t
	where true
	-- Enfore how many steps we go back
	-- (note: the Rel_PeriodNo is 1 based while p_Steps is 0 based, that's why we are subtracting 1)
	and t.Rel_PeriodNo - 1 = p_Steps
	;

	return v_C_Period_ID;
END;
$BODY$
LANGUAGE plpgsql STABLE;
