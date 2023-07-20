DROP FUNCTION IF EXISTS getc_calendar_id(
    p_ad_client_id numeric,
    p_ad_org_id    numeric
)
;

CREATE FUNCTION getc_calendar_id(
    p_ad_client_id numeric,
    p_ad_org_id    numeric
)
    RETURNS numeric
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_C_Calendar_ID numeric;
BEGIN
    IF (p_AD_Org_ID IS NOT NULL OR p_AD_Org_ID > 0) THEN
        SELECT oi.c_calendar_id
        INTO v_C_Calendar_ID
        FROM ad_orginfo oi
        WHERE oi.ad_org_id = p_AD_Org_ID;
    END IF;

    IF (v_C_Calendar_ID IS NULL OR v_C_Calendar_ID <= 0) THEN
        IF (p_AD_Client_ID IS NULL OR p_AD_Client_ID <= 0) THEN
            RAISE EXCEPTION 'p_AD_Client_ID shall be greater than 0 but it was %', p_AD_Client_ID;
        END IF;

        SELECT ci.c_calendar_id
        INTO v_C_Calendar_ID
        FROM ad_clientinfo ci
        WHERE ci.ad_client_id = p_AD_Client_ID;
    END IF;

    IF (v_C_Calendar_ID IS NULL OR v_C_Calendar_ID <= 0) THEN
        RAISE EXCEPTION 'No calendar found for AD_Org_ID=%, AD_Client_ID=%', p_AD_Org_ID, p_AD_Client_ID;
    END IF;

    RETURN v_C_Calendar_ID;
END;
$$
;

