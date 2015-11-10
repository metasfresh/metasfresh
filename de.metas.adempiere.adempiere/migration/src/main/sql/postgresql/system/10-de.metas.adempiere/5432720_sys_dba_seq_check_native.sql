-- Function: dba_seq_check_native()

-- DROP FUNCTION dba_seq_check_native();

CREATE OR REPLACE FUNCTION dba_seq_check_native()
RETURNS void AS
$BODY$
declare
	t   record;
	firstId  bigint := 1000000;
	nextId  bigint;
	seqName  character varying;
begin
	for t in
	(
		select tt.tableName, c.relname as seqname
		from AD_Table tt
		left outer join pg_class c on (c.relname=lower(tt.tableName||'_seq') and c.relkind='S')
		where tt.IsView='N' and tt.IsActive='Y'
		and exists (
			select 1 from AD_Column c
			where c.AD_Table_ID=tt.AD_Table_ID
				and lower(c.ColumnName)=lower(tt.TableName||'_ID')
				and (c.IsKey='Y' or c.IsParent='Y')
		)
		and tt.tableName not like 'X!_%Template' escape '!'
		order by tt.TableName
	)
	loop
		begin
			execute 'select max('||t.TableName||'_ID) from '||t.TableName
				into nextId;
	
			if (nextId is null)
			then
				nextId := firstId;
			else
				nextId := nextId + 1;
			end if;
	
			if (nextId < firstId)
			then
				nextId := firstId;
			end if;
	
			if (t.seqName is null)
			then
				seqName := lower(t.tableName||'_seq');
	
				execute 'CREATE SEQUENCE '||seqName
					||' INCREMENT 1'
					||' MINVALUE 0'
					||' MAXVALUE 2147483647' -- MAX java integer
					||' START '||nextId
				;
				-- execute 'ALTER TABLE '||seqName||' OWNER TO **USERNAME**';
	
				raise notice 'Sequence %: created - nextId=%', seqName, nextId;
			else
				seqName := t.seqName;
	
				perform setval(seqName, nextId, false);
				-- execute 'ALTER SEQUENCE '||seqName
				--	||' INCREMENT 1'
				--	||' MINVALUE 0'
				--	||' MAXVALUE 2147483647'
				--	||' START '||nextId
				-- ;
				
				raise notice 'Sequence %: updated - nextId=%', seqName, nextId;
			end if;
		exception when others then
			raise warning 'Sequence %: error while creating: % (%)', t.seqName, SQLERRM, SQLSTATE;
		end;
	end loop;
end;
$BODY$
	LANGUAGE plpgsql VOLATILE
	COST 100;

