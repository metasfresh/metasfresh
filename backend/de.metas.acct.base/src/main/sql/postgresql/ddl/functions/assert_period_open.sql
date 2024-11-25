DROP FUNCTION IF EXISTS "de_metas_acct".assert_period_open(
    p_DateAcct     timestamp WITH TIME ZONE,
    p_DocBaseType  varchar(3),
    p_AD_Client_ID numeric,
    p_AD_Org_ID    numeric)
;


CREATE OR REPLACE FUNCTION "de_metas_acct".assert_period_open(
    p_DateAcct     timestamp WITH TIME ZONE,
    p_DocBaseType  varchar(3),
    p_AD_Client_ID numeric = 1000000,
    p_AD_Org_ID    numeric = 0
)
    RETURNS void
AS
$BODY$
DECLARE
    v_C_Calendar_ID   numeric;
    v_C_Period_ID     numeric;
    v_C_AcctSchema_ID numeric;
    v_acctSchema      c_acctschema%rowtype;
    v_PeriodStatus    varchar;
BEGIN
    -- RAISE NOTICE 'assert_period_open: checking for p_DateAcct=%, p_DocBaseType=%, p_AD_Client_ID=%, p_AD_Org_ID=%', p_DateAcct, p_DocBaseType, p_AD_Client_ID, p_AD_Org_ID;

    --
    -- Find C_Period_ID
    --
    v_C_Period_ID := getC_Period_ID_by_Date(p_DateAcct, p_AD_Client_ID, p_AD_Org_ID);
    IF (v_C_Period_ID IS NULL OR v_C_Period_ID <= 0) THEN
        RAISE EXCEPTION 'No C_Period_ID found for p_DateAcct=%, C_Calendar_ID=%, AD_Client_ID=%', p_DateAcct, v_C_Calendar_ID, p_AD_Client_ID;
    END IF;

    --
    -- Find C_AcctSchema_ID
    --
    v_C_AcctSchema_ID := getc_acctschema_id(p_AD_Client_ID, p_AD_Org_ID);
    IF (v_C_AcctSchema_ID IS NULL OR v_C_AcctSchema_ID <= 0) THEN
        RAISE EXCEPTION 'No C_AcctSchema_ID found for AD_Client_ID=%, AD_Org_ID=%', p_AD_Client_ID, p_AD_Org_ID;
    END IF;

    SELECT cas.*
    INTO v_acctSchema
    FROM c_acctschema cas
    WHERE cas.c_acctschema_id = v_C_AcctSchema_ID;

    IF (v_acctSchema.autoperiodcontrol = 'Y') THEN
        RAISE EXCEPTION 'Automatic period control is not yet implemented';
    ELSE
        IF (p_DocBaseType IS NULL) THEN
            RAISE EXCEPTION 'DocBaseType shall not be null';
        END IF;

        SELECT pc.periodstatus
        INTO v_PeriodStatus
        FROM c_periodcontrol pc
        WHERE pc.c_period_id = v_C_Period_ID
          AND pc.docbasetype = p_DocBaseType;

        IF (v_PeriodStatus != 'O') THEN
            RAISE EXCEPTION 'Period not open (PeriodStatus=%) for DateAcct=%, C_Period_ID=%, DocBaseType=%', v_PeriodStatus, p_DateAcct, v_C_Period_ID, p_DocBaseType;
        END IF;
    END IF;

    RAISE NOTICE 'assert_period_open: OK, period open for DateAcct=%, C_Period_ID=%, DocBaseType=%', p_DateAcct, v_C_Period_ID, p_DocBaseType;
END;
$BODY$
    LANGUAGE plpgsql
    STABLE
    COST 100
;



/*
SELECT "de_metas_acct".assert_period_open(
               p_DateAcct := '2020-12-01'::timestamp WITH TIME ZONE,
               p_DocBaseType := 'API'::char(3),
               p_AD_Client_ID := 1000000::numeric,
               p_AD_Org_ID := 1000001::numeric)
;
 */