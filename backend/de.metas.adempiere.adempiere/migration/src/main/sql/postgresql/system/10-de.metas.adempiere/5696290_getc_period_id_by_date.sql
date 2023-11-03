DROP FUNCTION IF EXISTS getc_period_id_by_date(
    p_date          timestamp WITH TIME ZONE,
    p_ad_client_id  numeric,
    p_ad_org_id     numeric,
    p_c_calendar_id numeric
)
;

CREATE FUNCTION getc_period_id_by_date(
    p_date          timestamp WITH TIME ZONE,
    p_ad_client_id  numeric DEFAULT 1000000,
    p_ad_org_id     numeric DEFAULT 0,
    p_c_calendar_id numeric DEFAULT NULL::numeric
)
    RETURNS numeric
    STABLE
    LANGUAGE plpgsql
AS
$$
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
$$
;
