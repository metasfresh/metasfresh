-- Change currencyConvert, currencyRound and currencyBase from VOLATILE to STABLE.
--
-- These functions only read from tables (C_Conversion_Rate, C_Currency, AD_ClientInfo, C_AcctSchema)
-- and return deterministic results for the same inputs within a single SQL statement.
--
-- currencyRate is already STABLE.
--
-- With VOLATILE, PostgreSQL re-executes the function for every row even when the arguments
-- are identical (e.g., same currency pair across 1000 invoice lines).
-- With STABLE, the planner can cache results for repeated identical arguments.

-- currencyConvert
ALTER FUNCTION currencyconvert(numeric, numeric, numeric, timestamp with time zone, numeric, numeric, numeric) STABLE;

-- currencyRound
ALTER FUNCTION currencyround(numeric, numeric, character varying) STABLE;

-- currencyBase (6-arg overload)
ALTER FUNCTION currencybase(numeric, numeric, timestamp with time zone, numeric, numeric, numeric) STABLE;

-- currencyBase (5-arg overload)
ALTER FUNCTION currencybase(numeric, numeric, timestamp with time zone, numeric, numeric) STABLE;
