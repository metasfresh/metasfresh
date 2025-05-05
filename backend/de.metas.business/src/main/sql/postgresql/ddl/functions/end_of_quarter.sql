DROP FUNCTION IF EXISTS end_of_quarter(date)
;

CREATE FUNCTION end_of_quarter(IN p_date date DEFAULT NOW())
    RETURNS date
AS
$$
SELECT CAST(DATE_TRUNC('quarter', p_date) + INTERVAL '3 months' - INTERVAL '1 day' AS date)
$$
    LANGUAGE SQL IMMUTABLE
;
