
DROP FUNCTION IF EXISTS public.migrationscript_ignore(character varying);
CREATE FUNCTION public.migrationscript_ignore(projectAndFileName character varying)
  RETURNS void AS
$BODY$
 INSERT INTO public.AD_MigrationScript (
	ad_client_id, ad_migrationscript_id, ad_org_id, created, createdby, description, developername, 
	isactive, name, 
	projectname, 
	reference, releaseno, scriptroll, status, url, updated, updatedby, isapply, filename, script
 ) VALUES (
	0, nextval('ad_migrationscript_seq') , 0, now(), 100, 'Inserted by migrationscript_ignore with parameter projectAndFileName='||projectAndFileName, NULL, 
	'Y', replace (projectAndFileName,'/','->'),
    left (projectAndFileName, position('/' in projectAndFileName )-1 )/*projectname*/, 
	NULL, '1' , NULL, 'CO', NULL, now(), 100, 'N', right (projectAndFileName, length(projectAndFileName)-position('/' in projectAndFileName)), NULL)
 ON CONFLICT DO NOTHING /*if the file is already recorded in AD_MigrationScript, then do nothing */;
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
COMMENT ON FUNCTION public.migrationscript_ignore(character varying) IS 'Inserts the given script into the AD_MigrationScript table, so it will be ignored on future rollouts.
usage example:
select migrationscript_ignore(''configuration/changingpartnerwindow.sql'');

Please keep in sync with http://docs.metasfresh.org/sql_collection/migrationscript_helper_functions.html
';
