CREATE OR REPLACE VIEW db_triggers AS 
 SELECT relname as TableName, tgname as TriggerName, tgtype AS TriggerType, 
 tgenabled as TriggerEnabled, 
 ('ALTER TABLE '::text || relname || ' DISABLE TRIGGER '::text || tgname || ';' ) AS DisableSql,
 ('ALTER TABLE '::text || relname || ' ENABLE TRIGGER '::text || tgname || ';' ) AS EnableSql
 FROM pg_trigger 
 JOIN pg_class on (tgrelid=pg_class.oid)
 JOIN pg_proc on (tgfoid=pg_proc.oid)
;

 