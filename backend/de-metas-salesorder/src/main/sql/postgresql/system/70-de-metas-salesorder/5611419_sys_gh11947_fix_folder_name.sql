
-- on many exiting systems, these three files were added with projectname='${migration-sql-basedir}' 
-- only now i added them to a proper folder
select migrationscript_ignore('70-de-metas-salesorder/5611420_sys_gh11947_entity_de_metas_salesorder.sql');
select migrationscript_ignore('70-de-metas-salesorder/5611430_sys_gh11947_wp_CompleteShipAndInvoiceWorkpackageProcessor.sql');
select migrationscript_ignore('70-de-metas-salesorder/5612270_sys_gh11947_wp_ProcessOLCandsWorkpackageProcessor.sql');
