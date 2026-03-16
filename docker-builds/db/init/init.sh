#!/bin/bash
set -e

add_pg_stat_statements_extension=${ADD_PG_STAT_STATEMENTS_EXTENSION:-n}

echo ""
echo "==================="
echo " Creating role ..."
echo "==================="
psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE ROLE metasfresh LOGIN ENCRYPTED PASSWORD 'metasfresh' SUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
EOSQL
echo "==========="
echo " ... done!"
echo "==========="

echo ""
echo "======================================="
echo " Creating database and permissions ..."
echo "======================================="
psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE DATABASE metasfresh WITH OWNER = metasfresh;
GRANT ALL PRIVILEGES ON DATABASE metasfresh to metasfresh;
EOSQL
echo "==========="
echo " ... done!"
echo "==========="

echo ""
echo "==================="
echo "Restoring pgdump"
echo "==================="

# running without "--exit-on-error" because with our current dumps we get "ERROR:  schema "public" already exists"
# also disabling "fail on error" because pg_restore might return with a non-zero exit status
set +e
pg_restore -Fc --username metasfresh --dbname metasfresh /metasfresh.pgdump
set -e

echo "=========="
echo " ...done!"
echo "=========="

echo ""
echo "======================================="
echo " Adjusting CI/test configuration ..."
echo "======================================="

# Enable automatic period control so document completion doesn't fail with @PeriodClosed@
psql -v ON_ERROR_STOP=1 --username=metasfresh --dbname=metasfresh <<- 'EOSQL'
UPDATE C_AcctSchema SET AutoPeriodControl='Y', Period_OpenHistory=9999, Period_OpenFuture=9999 WHERE AD_Client_ID >= 0;

-- Also open all existing period controls directly (belt and suspenders)
UPDATE C_PeriodControl SET PeriodStatus='O', PeriodAction='O' WHERE PeriodStatus != 'O';

-- Create years and periods through 2030 so AutoPeriodControl has entries to work with
DO $$
DECLARE
    v_calendar_id integer;
    v_year_id integer;
    v_period_id integer;
    v_periodcontrol_id integer;
    v_year integer;
    v_month integer;
    v_start_date date;
    v_end_date date;
    v_docbasetype text;
BEGIN
    SELECT C_Calendar_ID INTO v_calendar_id FROM C_Calendar WHERE AD_Client_ID=1000000 AND IsActive='Y' ORDER BY C_Calendar_ID LIMIT 1;
    IF v_calendar_id IS NULL THEN RETURN; END IF;

    FOR v_year IN 2023..2030 LOOP
        -- Check if year exists
        SELECT C_Year_ID INTO v_year_id FROM C_Year WHERE C_Calendar_ID=v_calendar_id AND FiscalYear=v_year::text;
        IF v_year_id IS NULL THEN
            SELECT COALESCE(MAX(C_Year_ID),540000)+1 INTO v_year_id FROM C_Year;
            INSERT INTO C_Year(C_Year_ID, AD_Client_ID, AD_Org_ID, C_Calendar_ID, FiscalYear, IsActive, Created, CreatedBy, Updated, UpdatedBy)
            VALUES(v_year_id, 1000000, 0, v_calendar_id, v_year::text, 'Y', now(), 0, now(), 0);
        END IF;

        FOR v_month IN 1..12 LOOP
            v_start_date := make_date(v_year, v_month, 1);
            v_end_date := (v_start_date + interval '1 month' - interval '1 day')::date;

            -- Check if period exists
            SELECT C_Period_ID INTO v_period_id FROM C_Period WHERE C_Year_ID=v_year_id AND PeriodNo=v_month;
            IF v_period_id IS NULL THEN
                SELECT COALESCE(MAX(C_Period_ID),540000)+1 INTO v_period_id FROM C_Period;
                INSERT INTO C_Period(C_Period_ID, AD_Client_ID, AD_Org_ID, C_Year_ID, PeriodNo, Name, StartDate, EndDate, PeriodType, IsActive, Created, CreatedBy, Updated, UpdatedBy)
                VALUES(v_period_id, 1000000, 0, v_year_id, v_month,
                       to_char(v_start_date, 'Mon') || '-' || to_char(v_start_date, 'YY'),
                       v_start_date, v_end_date, 'S', 'Y', now(), 0, now(), 0);

                -- Create period controls for all document base types
                FOR v_docbasetype IN SELECT DocBaseType FROM (VALUES ('AVI'),('API'),('ARC'),('ARI'),('ARR'),('APP'),('CMC'),('DOO'),('GLD'),('GLJ'),('MCC'),('MMI'),('MMP'),('MMM'),('MMR'),('MMS'),('MXI'),('MXP'),('PJI'),('POO'),('SOO'),('HRP'),('FAI'),('FAD'),('FDP'),('MQI'),('MQP')) AS t(DocBaseType) LOOP
                    SELECT COALESCE(MAX(C_PeriodControl_ID),540000)+1 INTO v_periodcontrol_id FROM C_PeriodControl;
                    INSERT INTO C_PeriodControl(C_PeriodControl_ID, AD_Client_ID, AD_Org_ID, C_Period_ID, DocBaseType, PeriodStatus, PeriodAction, IsActive, Created, CreatedBy, Updated, UpdatedBy)
                    VALUES(v_periodcontrol_id, 1000000, 0, v_period_id, v_docbasetype, 'O', 'O', 'Y', now(), 0, now(), 0);
                END LOOP;
            ELSE
                -- Open existing period controls
                UPDATE C_PeriodControl SET PeriodStatus='O', PeriodAction='O' WHERE C_Period_ID=v_period_id AND PeriodStatus != 'O';
            END IF;
        END LOOP;
    END LOOP;
END$$;
EOSQL

# Set server URLs for Docker Compose environment
psql -v ON_ERROR_STOP=1 --username=metasfresh --dbname=metasfresh <<- EOSQL
UPDATE ad_sysconfig SET value='http://metasfresh-app:8282/adempiereJasper/ReportServlet' WHERE name='de.metas.adempiere.report.jasper.JRServerServlet';
UPDATE ad_sysconfig SET value='http://metasfresh-app:8282/adempiereJasper/BarcodeServlet' WHERE name='de.metas.adempiere.report.barcode.BarcodeServlet';
UPDATE ad_sysconfig SET value='' WHERE name='webui.frontend.url';
EOSQL

# Create test user (cynthia) for automated tests
psql -v ON_ERROR_STOP=1 --username=metasfresh --dbname=metasfresh <<- EOSQL
INSERT INTO ad_user(ad_user_id, name, description, value, login, password, ad_language, isactive, issystemuser, isfullbpaccess, isdefaultcontact, ad_client_id, ad_org_id, createdby, updatedby, notificationtype, isinpayroll, issubjectmattercontact, issalescontact, isaccountlocked, fresh_gift, ispurchasecontact, ismfprocurementuser, issalescontact_default, ispurchasecontact_default, isloginashostkey, isbilltocontact_default, isshiptocontact_default, isnewsletter, isdecider, ismanagement, ismultiplier, isauthorizedsignatory, ismembershipcontact)
VALUES (142, 'cynthia', 'user for cypress tests', 'cynthia', 'cynthia', 'sha512:fa4902a30453c242c30378c483bca2ba2272689ff0cc143b8fca94653eef26be5243767dbc937efbe6dfc8c4066159af0d50ee533573746d3b93feb08056ef78:37d25dd5-5909-4d51-83c4-8bb9fc365b79', 'en_US', 'Y', 'Y', 'Y', 'Y', 0, 0, 0, 0, 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N')
ON CONFLICT (ad_user_id) DO NOTHING;

INSERT INTO ad_user_roles(ad_user_id, ad_role_id, ad_client_id, ad_org_id, isactive, createdby, updatedby)
VALUES (142, 540024, 1000000, 1000000, 'Y', 142, 142)
ON CONFLICT DO NOTHING;
EOSQL

echo "==========="
echo " ... done!"
echo "==========="

activate_extensions()
{
	if [ "${add_pg_stat_statements_extension}" != "n" ]; then
		# needs shared_preload_libraries = 'pg_stat_statements'	in postgresql.conf
		echo "==========================================="
		echo " activate pg_stat_statements extension ..."
		echo "==========================================="
		psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;
EOSQL
		echo "==========="
		echo " ... done!"
		echo "==========="
	fi
}

activate_extensions
