-- Cucumber-only DB setup: ensure every test date has an open period.
--
-- Context:
--   Legacy isOpen() / assert_period_open() on this branch only consult
--   C_Period + C_PeriodControl.PeriodStatus='O'. The preloaded DB:
--     - has C_Year rows up to 2027 but NO monthly C_Period rows for 2025+
--     - seeds existing C_PeriodControl.PeriodStatus='N' (Never Opened)
--   Feature files use 2021/2022 dates; some scenarios run on the system
--   clock (currently 2026). Without this setup every document-completing
--   scenario fails with @PeriodClosed@.
--
-- Safe to run multiple times. Touches only the ephemeral cucumber DB.

DO $$
DECLARE
    v_year      record;
    v_month     int;
    v_start     date;
BEGIN
    -- 1. Create 12 monthly C_Period rows for every C_Year in 2020-2030 that has none yet.
    FOR v_year IN
        SELECT y.c_year_id, y.ad_client_id, y.fiscalyear
        FROM c_year y
        WHERE y.isactive = 'Y'
          AND y.fiscalyear ~ '^[0-9]{4}$'
          AND y.fiscalyear::int BETWEEN 2020 AND 2030
          AND NOT EXISTS (SELECT 1 FROM c_period p WHERE p.c_year_id = y.c_year_id)
    LOOP
        FOR v_month IN 1..12 LOOP
            v_start := make_date(v_year.fiscalyear::int, v_month, 1);
            INSERT INTO c_period (
                c_period_id, ad_client_id, ad_org_id, c_year_id,
                name, periodno, periodtype, startdate, enddate,
                isactive, processing, created, createdby, updated, updatedby)
            VALUES (
                nextval('c_period_seq'), v_year.ad_client_id, 0, v_year.c_year_id,
                to_char(v_start, 'Mon-YY'),
                v_month, 'S', v_start, (v_start + INTERVAL '1 month - 1 day')::date,
                'Y', 'N', now(), 99, now(), 99);
        END LOOP;
        RAISE NOTICE 'Created 12 monthly periods for fiscal year %', v_year.fiscalyear;
    END LOOP;
END $$;

-- 2. Insert missing C_PeriodControl rows (one per period × DocBaseType) with status Open.
INSERT INTO c_periodcontrol (
    c_periodcontrol_id, ad_client_id, ad_org_id, c_period_id,
    docbasetype, periodstatus, periodaction, processing,
    isactive, created, createdby, updated, updatedby)
SELECT
    nextval('c_periodcontrol_seq'), p.ad_client_id, 0, p.c_period_id,
    rl.value, 'O', 'N', 'N',
    'Y', now(), 99, now(), 99
FROM c_period p
CROSS JOIN ad_ref_list rl
WHERE p.isactive = 'Y'
  AND rl.ad_reference_id = 183  -- DocBaseType reference
  AND rl.isactive = 'Y'
  AND NOT EXISTS (
      SELECT 1 FROM c_periodcontrol pc
      WHERE pc.c_period_id = p.c_period_id
        AND pc.docbasetype = rl.value);

-- 3. Force every existing C_PeriodControl row to Open.
UPDATE c_periodcontrol
SET periodstatus = 'O', updated = now(), updatedby = 99
WHERE periodstatus <> 'O';
