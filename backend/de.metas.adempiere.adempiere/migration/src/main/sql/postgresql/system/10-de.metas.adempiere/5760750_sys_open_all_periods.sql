-- Create and open ALL accounting periods for cucumber tests
-- Creates years 2026-2030 with all periods and opens them
-- Must be idempotent: won't fail if data already exists

DO $$
DECLARE
    v_calendar_id numeric := 1000000;
    v_client_id numeric := 1000000;
    v_org_id numeric := 0;
    v_year record;
    v_year_id numeric;
    v_period_id numeric;
    v_month_names text[] := ARRAY['Jan', 'Feb', 'Mrz', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'];
    v_month int;
    v_docbasetype record;
    v_next_year_id numeric;
    v_next_period_id numeric;
    v_next_periodcontrol_id numeric;
BEGIN
    -- Get next IDs
    SELECT COALESCE(MAX(c_year_id), 540030) + 1 INTO v_next_year_id FROM c_year;
    SELECT COALESCE(MAX(c_period_id), 540400) + 1 INTO v_next_period_id FROM c_period;
    SELECT COALESCE(MAX(c_periodcontrol_id), 560000) + 1 INTO v_next_periodcontrol_id FROM c_periodcontrol;

    -- Create years 2026-2030 if they don't exist
    FOR v_year IN SELECT unnest(ARRAY[2026, 2027, 2028, 2029, 2030]) AS yr LOOP
        -- Check if year exists
        SELECT c_year_id INTO v_year_id
        FROM c_year
        WHERE fiscalyear = v_year.yr::text
          AND c_calendar_id = v_calendar_id
          AND ad_client_id = v_client_id;

        IF v_year_id IS NULL THEN
            v_year_id := v_next_year_id;
            v_next_year_id := v_next_year_id + 1;

            INSERT INTO c_year (c_year_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                               fiscalyear, processing, c_calendar_id)
            VALUES (v_year_id, v_client_id, v_org_id, 'Y', now(), 100, now(), 100,
                   v_year.yr::text, 'N', v_calendar_id);
            RAISE NOTICE 'Created year %', v_year.yr;
        ELSE
            RAISE NOTICE 'Year % already exists with id %', v_year.yr, v_year_id;
        END IF;

        -- Create periods for this year
        FOR v_month IN 1..12 LOOP
            -- Check if period exists
            SELECT c_period_id INTO v_period_id
            FROM c_period
            WHERE c_year_id = v_year_id
              AND periodno = v_month
              AND ad_client_id = v_client_id;

            IF v_period_id IS NULL THEN
                v_period_id := v_next_period_id;
                v_next_period_id := v_next_period_id + 1;

                INSERT INTO c_period (c_period_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                                      c_year_id, periodno, name, startdate, enddate, periodtype, processing)
                VALUES (v_period_id, v_client_id, v_org_id, 'Y', now(), 100, now(), 100,
                       v_year_id, v_month,
                       v_month_names[v_month] || '.-' || RIGHT(v_year.yr::text, 2),
                       make_date(v_year.yr, v_month, 1),
                       (make_date(v_year.yr, v_month, 1) + interval '1 month' - interval '1 day')::date,
                       'S', 'N');
                RAISE NOTICE 'Created period % for year %', v_month, v_year.yr;
            END IF;

            -- Create period controls for all doc base types
            FOR v_docbasetype IN SELECT DISTINCT docbasetype FROM c_doctype WHERE docbasetype IS NOT NULL
                                 UNION SELECT DISTINCT docbasetype FROM c_periodcontrol WHERE docbasetype IS NOT NULL LOOP
                IF NOT EXISTS (SELECT 1 FROM c_periodcontrol
                               WHERE c_period_id = v_period_id
                                 AND docbasetype = v_docbasetype.docbasetype
                                 AND ad_client_id = v_client_id) THEN
                    INSERT INTO c_periodcontrol (c_periodcontrol_id, ad_client_id, ad_org_id, isactive,
                                                 created, createdby, updated, updatedby,
                                                 c_period_id, docbasetype, periodstatus, periodaction, processing)
                    VALUES (v_next_periodcontrol_id, v_client_id, v_org_id, 'Y',
                           now(), 100, now(), 100,
                           v_period_id, v_docbasetype.docbasetype, 'O', 'N', 'N');
                    v_next_periodcontrol_id := v_next_periodcontrol_id + 1;
                END IF;
            END LOOP;
        END LOOP;
    END LOOP;

    RAISE NOTICE 'Finished creating years and periods';
END $$;

-- Open ALL existing periods that aren't already open
UPDATE c_periodcontrol
SET periodstatus = 'O',
    periodaction = 'N',
    updated = now(),
    updatedby = 100
WHERE periodstatus <> 'O';
