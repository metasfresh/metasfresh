--- Add year 2026 in case it does not already exist

DO $$
    BEGIN

        INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2024-01-02 12:02:04.738','YYYY-MM-DD HH24:MI:SS.US'),100,nextval('c_year_seq'),'2026','Y','N',TO_TIMESTAMP('2024-01-02 12:02:04.738','YYYY-MM-DD HH24:MI:SS.US'),100)
        ;

    EXCEPTION WHEN unique_violation THEN

        RAISE NOTICE 'year 2026 already exists';

    end $$;


--- Add year 2027 in case it does not already exist

DO $$
    BEGIN

        INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2024-01-02 12:02:04.738','YYYY-MM-DD HH24:MI:SS.US'),100,nextval('c_year_seq'),'2027','Y','N',TO_TIMESTAMP('2024-01-02 12:02:04.738','YYYY-MM-DD HH24:MI:SS.US'),100)
        ;

    EXCEPTION WHEN unique_violation THEN

        RAISE NOTICE 'year 2027 already exists';

    end $$;