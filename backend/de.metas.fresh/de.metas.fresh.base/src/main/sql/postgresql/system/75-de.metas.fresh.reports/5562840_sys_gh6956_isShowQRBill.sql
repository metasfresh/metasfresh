DROP FUNCTION IF EXISTS report.isShowQRBill(IN p_AD_Client_ID numeric, IN p_AD_Org_ID numeric);

create or replace function report.isShowQRBill(IN p_AD_Client_ID numeric, IN p_AD_Org_ID numeric) RETURNS CHAR AS
$$
DECLARE
    isShowQRBill CHAR DEFAULT 'Y';
    foundValue   BOOLEAN DEFAULT FALSE;
    rec_config   RECORD;
    sysConfigs CURSOR FOR SELECT *
                          from AD_SysConfig
                          where Name = 'ShowQRBill';
BEGIN
    OPEN sysConfigs;

    LOOP
        -- fetch row into the film
        FETCH sysConfigs INTO rec_config;
        -- exit when no more row to fetch
        EXIT WHEN NOT FOUND;

        -- build the output
        IF rec_config.AD_Client_ID = p_AD_Client_ID AND rec_config.AD_Org_ID = p_AD_Org_ID THEN
            isShowQRBill := rec_config.value;
            foundValue := TRUE;
        END IF;

    END LOOP;

    -- Close the cursor
    CLOSE sysConfigs;


    IF foundValue = FALSE THEN
        SELECT VALUE
        INTO isShowQRBill
        from AD_SysConfig
        where Name = 'ShowQRBill' AND AD_Client_ID = 0 AND AD_Org_ID = 0;
    END IF;

    return isShowQRBill;
END
$$
    LANGUAGE plpgsql;

--INSERT INTO public.ad_sysconfig (ad_sysconfig_id, ad_client_id, ad_org_id, created, updated, createdby, updatedby, isactive, name, value, description, entitytype, configurationlevel)
--VALUES (nextval('ad_sysconfig_seq'), 1000000, 1000000, '2020-07-02 12:08:04.000000', '2020-07-02 12:09:08.000000', 100, 100, 'N', 'ShowQRBill', 'Y', null, 'D', 'O');