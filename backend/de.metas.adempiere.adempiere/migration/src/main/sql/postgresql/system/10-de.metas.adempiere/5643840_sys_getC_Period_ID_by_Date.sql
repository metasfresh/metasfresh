DROP FUNCTION IF EXISTS getC_Period_ID_by_Date(
    p_Date          timestamp WITH TIME ZONE,
    p_AD_Client_ID  numeric,
    p_AD_Org_ID     numeric,
    p_C_Calendar_ID numeric)
;


CREATE OR REPLACE FUNCTION getC_Period_ID_by_Date(
    p_Date          timestamp WITH TIME ZONE,
    p_AD_Client_ID  numeric = 1000000,
    p_AD_Org_ID     numeric = 0,
    p_C_Calendar_ID numeric = NULL
)
    RETURNS numeric
    LANGUAGE plpgsql
    STABLE
AS
$BODY$
DECLARE
    v_C_Calendar_ID numeric;
    v_C_Period_ID   numeric;
BEGIN
    --
    -- Find C_Calendar_ID if needed
    IF (p_C_Calendar_ID IS NOT NULL AND p_C_Calendar_ID > 0) THEN
        v_C_Calendar_ID = p_C_Calendar_ID;
    ELSE
        v_C_Calendar_ID := getC_Calendar_ID(p_AD_Client_ID, p_AD_Org_ID);
        IF (v_C_Calendar_ID IS NULL OR v_C_Calendar_ID <= 0) THEN
            RAISE EXCEPTION 'No calendar found for AD_Org_ID=%, AD_Client_ID=%', p_AD_Org_ID, p_AD_Client_ID;
        END IF;
    END IF;


    --
    -- Find C_Period_ID
    --
    SELECT p.c_period_id
    INTO v_C_Period_ID
    FROM c_year y
             INNER JOIN c_period p ON p.c_year_id = y.c_year_id
    WHERE y.c_calendar_id = v_C_Calendar_ID
      AND p.periodtype = 'S'
      AND p.ad_client_id = p_AD_Client_ID
      AND p.startdate::date <= p_Date::date
      AND p.enddate::date >= p_Date::date
    ORDER BY p.startdate
    -- limit 1 -- shall not be needed
    ;
    IF (v_C_Period_ID IS NULL OR v_C_Period_ID <= 0) THEN
        RAISE EXCEPTION 'No C_Period_ID found for p_Date=%, C_Calendar_ID=%, AD_Client_ID=%', p_Date, v_C_Calendar_ID, p_AD_Client_ID;
    END IF;

    RETURN v_C_Period_ID;
END;
$BODY$
;

/* TEST:
SELECT cal.c_calendar_id,
       cal.name                                                                                                                  AS calendar_name,
       p.c_period_id,
       p.name,
       p.startdate::date,
       p.enddate::date,
       (SELECT p2.name FROM c_period p2 WHERE p2.c_period_id = getC_Period_ID_by_Date(p.startdate, p.ad_client_id, p.ad_org_id)) AS p1,
       (SELECT p2.name FROM c_period p2 WHERE p2.c_period_id = getC_Period_ID_by_Date(p.enddate, p.ad_client_id, p.ad_org_id))   AS p2
FROM c_period p
         INNER JOIN c_year y ON y.c_year_id = p.c_year_id
         INNER JOIN c_calendar cal ON cal.c_calendar_id = y.c_calendar_id
WHERE TRUE
  and y.c_calendar_id = 1000000
  AND (
            p.c_period_id != getC_Period_ID_by_Date(p.startdate, p.ad_client_id, p.ad_org_id)
        OR p.c_period_id != getC_Period_ID_by_Date(p.enddate, p.ad_client_id, p.ad_org_id))
ORDER BY p.startdate
 */



