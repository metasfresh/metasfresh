CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop function if exists hash_column_value(character varying);
drop function if exists hash_column_value_if_needed(character varying);


create or replace function hash_column_value(valuePlain character varying)
returns character varying
as
$$
declare
	salt character varying;
begin
	-- IMPORTANT: Please keep it in sync with de.metas.hash.HashableString.hashValue(String, String) method
	salt := uuid_generate_v4();
	return 'sha512:' || encode(digest(valuePlain || salt, 'sha512'), 'hex') || ':' || salt;
end;
$$
LANGUAGE plpgsql
VOLATILE
;

create or replace function hash_column_value_if_needed(valuePlain character varying)
returns character varying
as
$$
	select case
		when valuePlain like 'sha512:%' then valuePlain
		else hash_column_value(valuePlain)
	end;
$$
LANGUAGE sql
VOLATILE
;

