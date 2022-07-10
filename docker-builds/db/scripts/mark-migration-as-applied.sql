do $outer_fh345fg$
declare
	project varchar := '##project##';
	file varchar := '##file##';
	
begin
	insert
		into ad_migrationscript(projectname, filename, name, description, releaseno, created, updated, createdby, updatedby, isactive, isapply, status, ad_client_id, ad_org_id)
		values (project, file, project || '->' || file || '.sql', 'applied by psql', 1, now(), now(), 42, 42, 'Y', 'N', 'MZ', 0, 0);
end;
$outer_fh345fg$