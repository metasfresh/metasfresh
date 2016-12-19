-- PLEASE KEEP IN SYNC WITH org.adempiere.mm.attributes.api.ILotNumberBL.calculateLotNumber(Date).


DROP FUNCTION IF EXISTS "de.metas.handlingunits".HU_LotNumberDate_ToString(p_Date date);

CREATE OR REPLACE FUNCTION "de.metas.handlingunits".HU_LotNumberDate_ToString(p_Date date)
RETURNS character varying AS
$$
declare
	v_WeekNo integer;
	v_DayOfWeek integer;
	v_Result character varying := '';
begin
	-- respect the STRICT contract
	if (p_Date is null) then
		return null;
	end if;
	
	v_WeekNo := extract(WEEK from p_Date);
	v_DayOfWeek := extract(DOW from p_Date);

	-- Convert DOW from (0 to 6, 0-Sunday, 6-Saturday) form to (1 to 7, 1-Monday, 7-Sunday) form.
	if (v_DayOfWeek = 0) then
		v_DayOfWeek := 7;
	end if;

	--
	-- Build the result string: 3 chars having the format: WWD (WW - week number, D - day of the week)
	if (v_WeekNo < 10) then
		v_Result := v_Result || '0';
	end if;
	v_Result := v_Result || v_WeekNo || v_DayOfWeek;

	return v_Result;
end;
$$
LANGUAGE PLPGSQL
IMMUTABLE 
STRICT
;

COMMENT ON FUNCTION
"de.metas.handlingunits".HU_LotNumberDate_ToString(date)
IS
'09670: Converts a given date to WWD format: WW - week number, D - day of the week (1-Monday, 7-Sunday)'
;






--
-- Tests:
DO $$
declare
	v_CountErrors integer := 0;
begin
	select count(*)
	into v_CountErrors
	from (
		select t.TestDate, t.ExpectedResult, "de.metas.handlingunits".HU_LotNumberDate_ToString(TestDate) as ActualResult
		from (
			select '2016-01-01'::date as TestDate, '535' as ExpectedResult
			union all
			select '2016-01-02'::date, '536'
			union all
			select '2016-01-03'::date, '537'
			union all
			select '2016-01-04'::date, '011'
		) t
	) t
	where ExpectedResult<>ActualResult;
	
	if (v_CountErrors <> 0) then
		raise exception 'Found errors in HU_LotNumberDate_ToString function. Please check the script';
	end if;
end $$;

