DROP FUNCTION IF EXISTS logDebug(msg text, previousTime timestamp);

CREATE OR REPLACE FUNCTION logDebug(msg text, previousTime timestamp=now())
    RETURNS timestamp AS
$$
DECLARE
    v_now             timestamp;
    deltaSinceLastLog text;
    totalRuntime      text;
BEGIN
    v_now := clock_timestamp();
    deltaSinceLastLog := extract(EPOCH FROM v_now::timestamp(0) - previousTime::timestamp(0));
    totalRuntime := extract(EPOCH FROM v_now::timestamp(0) - now()::timestamp(0));

    -- todo: not sure if raise notice already prints the current timestamp and v_now is a duplicate
    RAISE NOTICE '[%](%s) [Δ=%s]: % ', v_now, totalRuntime, deltaSinceLastLog, msg;

    RETURN v_now;
END;
$$
    LANGUAGE plpgsql IMMUTABLE
                     COST 1;

/*
How to use:
Pass the message you want to appear in the console, and also the timestamp returned by the function, so that you get the time difference from the previous output.

DECLARE
v_debugTime timestamp;

v_debugTime := logDebug('start');
[...]
v_debugTime := logDebug('created temporary table', v_debugTime);
[...]
v_debugTime := logDebug('inserted beginningBalance', v_debugTime);
[...]
*/
