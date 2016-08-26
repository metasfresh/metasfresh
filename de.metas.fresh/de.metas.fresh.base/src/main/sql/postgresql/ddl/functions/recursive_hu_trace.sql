DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.recursive_hu_trace(trxline integer[]);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.recursive_hu_trace(trxline integer) RETURNS TABLE
(
	trx_id numeric,
	hu_id numeric
)

  AS $$	
  DECLARE

	r record;
	rez integer[]:= Array[$1];
	all_trx integer[] := '{}';
	last_trx integer[] := '{}';
	 v_sql text := 'SELECT m_hu_trx_line_id,m_hu_id FROM M_HU_Trx_Line WHERE M_HU_Trx_Line_ID = ANY( ARRAY[';
	
  BEGIN
	while(0 != any(rez))
	LOOP
		for r in (SELECT m_hu_trx_line_id::integer, m_hu_id::integer FROM de_metas_endcustomer_fresh_reports.recursive_hu_trace_sub(rez)) loop
			if (Array[r.m_hu_trx_line_id] <@ all_trx) 
			then last_trx := last_trx;
			--RAISE NOTICE 'removed %',r.m_hu_trx_line_id;
			else
			last_trx := last_trx || r.m_hu_trx_line_id; 
			end if;
			--	RAISE NOTICE 'last_trx %', last_trx;
			all_trx := all_trx || r.m_hu_trx_line_id;
			--	RAISE NOTICE 'all_trx %', all_trx;
			
		end loop;

		rez := last_trx;-----------------
		--RAISE NOTICE 'rez %', rez;
		last_trx := Array[0];
		
    END LOOP;
    RETURN query execute v_sql || array_to_string(all_trx,',') || ']::integer[]);' ;
END;
$$ LANGUAGE plpgsql;
