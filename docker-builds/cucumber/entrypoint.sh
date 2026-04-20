#!/bin/bash
set -o errexit   # abort on nonzero exitstatus
set -o nounset   # abort on unbound variable
set -o pipefail  # don't hide errors within pipes

echo ""
echo "==================="
echo " preparing periods & opening period controls in the cucumber DB ..."
echo "==================="
# Legacy isOpen()/assert_period_open() on this branch only support PeriodStatus='O'
# (autoperiodcontrol='Y' raises "not yet implemented"). The preloaded DB:
#   - has C_Year rows for 2025/2026/2027 but NO monthly C_Period rows for those years
#   - seeds all existing C_PeriodControl rows with PeriodStatus='N'
# Feature files use 2021/2022 dates AND tests using current system time hit 2026.
# Without this, every document-completing scenario fails with @PeriodClosed@.
# Runs only in the ephemeral cucumber compose stack — no migration touches customer DBs.
PGPASSWORD=metasfresh psql -h db -U metasfresh -d metasfresh -v ON_ERROR_STOP=1 <<'SQL'
DO $$
DECLARE
    v_year       record;
    v_month      int;
    v_period_id  numeric;
    v_start      date;
    v_end        date;
    v_months_de  text[] := ARRAY['Januar','Februar','März','April','Mai','Juni','Juli','August','September','Oktober','November','Dezember'];
BEGIN
    -- Create monthly C_Period rows for every C_Year that has no periods yet
    FOR v_year IN
        SELECT y.c_year_id, y.ad_client_id, y.c_calendar_id, y.fiscalyear
        FROM c_year y
        WHERE y.isactive='Y'
          AND y.fiscalyear ~ '^[0-9]{4}$'
          AND y.fiscalyear::int BETWEEN 2020 AND 2030
          AND NOT EXISTS (SELECT 1 FROM c_period p WHERE p.c_year_id = y.c_year_id)
    LOOP
        FOR v_month IN 1..12 LOOP
            v_start := make_date(v_year.fiscalyear::int, v_month, 1);
            v_end   := (v_start + INTERVAL '1 month - 1 day')::date;
            v_period_id := nextval('c_period_seq');
            INSERT INTO c_period (
                c_period_id, ad_client_id, ad_org_id, c_year_id,
                name, periodno, periodtype, startdate, enddate,
                isactive, processing, created, createdby, updated, updatedby)
            VALUES (
                v_period_id, v_year.ad_client_id, 0, v_year.c_year_id,
                v_months_de[v_month] || '-' || substr(v_year.fiscalyear, 3, 2),
                v_month, 'S', v_start, v_end,
                'Y', 'N', now(), 99, now(), 99);
        END LOOP;
        RAISE NOTICE 'Created 12 monthly periods for fiscal year %', v_year.fiscalyear;
    END LOOP;

    -- Ensure every period has C_PeriodControl rows for every active DocBaseType, status='O'
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
    WHERE p.isactive='Y'
      AND rl.ad_reference_id = 183
      AND rl.isactive='Y'
      AND NOT EXISTS (
          SELECT 1 FROM c_periodcontrol pc
          WHERE pc.c_period_id = p.c_period_id AND pc.docbasetype = rl.value);
END $$;

-- Force all existing period controls to Open
UPDATE c_periodcontrol SET periodstatus='O', updated=now(), updatedby=99 WHERE periodstatus<>'O';
SQL

echo ""
echo "==================="
echo " running cucumber tests ..."
echo "==================="

mvn --offline surefire:test --fail-never "$@"

echo "==================="
echo " exporting reports ..."
echo "==================="

cp target/*.xml /reports
cp target/*.html /reports
