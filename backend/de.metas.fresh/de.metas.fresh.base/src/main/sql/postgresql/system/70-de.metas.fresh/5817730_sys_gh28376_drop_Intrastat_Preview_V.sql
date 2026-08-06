-- Companion drop to the consolidation migration in 10-de.metas.adempiere/5817720.
-- The new "Intrastat Vorschau" window and its backing table were deactivated there; here we
-- drop the underlying view since nothing references it anymore.
-- The source DDL file backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/views/Intrastat_Preview_V.sql
-- is also removed from the source tree by the same commit that lands this migration.

DROP VIEW IF EXISTS Intrastat_Preview_V;
DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.Intrastat_Preview_V;
