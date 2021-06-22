
COMMENT ON FUNCTION public.scramble_metasfresh(boolean)
    IS 'Uses the function scramble_table to scramble the string-columns of all metasfresh-tables, unless they are marked with PersonalDataCategory=NP (not-personal).
If called with p_dryRun := TRUE (the default value!), then the corresponding update statements are just constructed but not executed.
    
"Scrambled" means that all numbers, characters etc are replaced with random chars. 
Only whitespaces and a few other chars are left unchanged, so that e.g. the formatting of an address field is left intact.
See the DB-function `public.scramble_string(character varying, character varying)` for more details.

scramble_metasfresh() might take quite some time to finish on large databases.
You might consider emptying certain large tables before running it.
For example: 

-- empty large tables that would take very long to scramble    
truncate table ad_changelog;
truncate table ad_issue;
delete from AD_EventLog_Entry where true;
delete from AD_EventLog where true;
delete from AD_PInstance_Log where true;
delete from AD_PInstance p 
where not exists (select 1 from c_async_batch b where b.ad_pinstance_id=p.ad_pinstance_id)
and not exists (select 1 from c_flatrate_term b where b.ad_pinstance_endofterm_id=p.ad_pinstance_id);
truncate table C_Queue_WorkPackage_Log;
truncate table C_Queue_Element;
delete from C_Queue_WorkPackage where true;
update M_ShipmentSchedule_ExportAudit set forwardeddata=null where true;

-- scramble
select scramble_metasfresh(false);
';
