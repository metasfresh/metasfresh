
DROP FUNCTION IF EXISTS report.Get_Week_Number_and_Limit(IN week integer, IN year numeric, IN weeks_diff integer);

create or replace function report.Get_Week_Number_and_Limit(IN week integer, IN year numeric, IN weeks_diff integer)
RETURNS TABLE
(
 week_no integer,
 startdate date,
 enddate date
)
 AS
$$
SELECT week_no, startdate, enddate FROM 
(
	select EXTRACT(WEEK FROM (select (to_timestamp($1 || ' ' || $2 ,'IW IYYY'))+ (7 * $3) )::timestamp)::integer AS week_no,
	(select (to_timestamp($1 || ' ' || $2 ,'IW IYYY'))) + (7 * $3) AS startdate,
	(select (to_timestamp($1 || ' ' || $2 ,'IW IYYY'))) + (7 * $3) + 6 AS enddate
) rez	

$$ 
LANGUAGE sql STABLE;

COMMENT ON FUNCTION report.Get_Week_Number_and_Limit(integer, numeric, integer) IS 
'week 30 in 2016 is July 25, 2016 to July 31, 2016
E.g. 30, 2016, 0 => 30, 2016-07-25, 2016-07-31
E.g. 30, 2016, 1 => 31, 2016-08-01, 2016-08-07
E.g. 30, 2016, -1 => 29, 2016-07-18, 2016-07-24
';