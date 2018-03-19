DROP FUNCTION IF EXISTS public.add_migrationscript(varchar);
CREATE FUNCTION public.add_migrationscript(varchar)
  returns void as 
$$
INSERT INTO public.ad_migrationscript (
	ad_client_id, ad_migrationscript_id, ad_org_id, created, createdby, description, developername, 
	isactive, name, 
	projectname, reference, releaseno, scriptroll, status, url, updated, updatedby, isapply, filename, script
) VALUES (
	0, nextval('ad_migrationscript_seq') , 0, now(), 100, 'Applied manually', NULL, 
	'Y', replace ($1,'/','->'),
    left ($1, position('/' in $1 )-1 ), NULL, '1' , NULL, 'CO', NULL, now(), 100, 'N', right ($1, length($1)-position('/' in $1)), NULL);
$$ LANGUAGE 'sql';
COMMENT ON FUNCTION public.add_migrationscript(varchar) 
IS 'Inserts the give script into the ad_migrationscript table, so it will be ignored on future rollouts.
usage example:
select add_migrationscript(''configuration/changingpartnerwindow.sql'');

Also see http://docs.metasfresh.org/sql_collection/ad_migrationscript.html
';