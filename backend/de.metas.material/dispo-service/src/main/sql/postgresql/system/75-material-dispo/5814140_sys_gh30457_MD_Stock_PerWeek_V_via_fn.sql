-- Source DDL: backend/de.metas.material/dispo-service/src/main/sql/postgresql/ddl/de_metas_material/MD_Stock_PerWeek_V.sql
--
-- gh#30457 — redefine MD_Stock_PerWeek_V as a thin wrapper over MD_Stock_PerWeek_fn (5814050).
-- The view no longer owns the ATP algebra, the single-scan CTE rewrite, or the push-down-friendly
-- synthetic primary key — those now live solely in MD_Stock_PerWeek_fn's DDL.
-- fn(NULL, NULL) degrades to "no filter on either dimension", i.e. exactly this view's full,
-- unfiltered result set. Column names/types/order are unchanged.

DROP VIEW IF EXISTS MD_Stock_PerWeek_V$new;

CREATE OR REPLACE VIEW MD_Stock_PerWeek_V$new AS
SELECT * FROM MD_Stock_PerWeek_fn(NULL::numeric, NULL::numeric);

SELECT db_alter_view(
    'MD_Stock_PerWeek_V',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('MD_Stock_PerWeek_V$new'))
);

DROP VIEW IF EXISTS MD_Stock_PerWeek_V$new;
