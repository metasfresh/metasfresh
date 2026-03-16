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
psql -v ON_ERROR_STOP=1 --username=metasfresh --dbname=metasfresh <<- EOSQL
UPDATE C_AcctSchema SET AutoPeriodControl='Y', Period_OpenHistory=9999, Period_OpenFuture=9999 WHERE AD_Client_ID >= 0;
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
