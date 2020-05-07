DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.recursive_hu_trace_sub(trxline integer[]);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.recursive_hu_trace_sub(trxline integer[]) RETURNS TABLE
(
	M_hu_trx_line_id numeric,
	m_hu_id numeric

)
  AS $$
  
DECLARE
  
    new_trxline Integer[] := Array[0];
    r record;
    v_sql text := 'SELECT m_hu_trx_line_id,m_hu_id FROM M_HU_Trx_Line WHERE M_HU_Trx_Line_ID = ANY( ARRAY[';
    
BEGIN
    for r in (

	SELECT   COALESCE(trx_line3.M_HU_Trx_Line_id::integer, trx_line2.M_HU_Trx_Line_id::integer) AS M_HU_Trx_Line_id, trx_line2.M_HU_ID as M_HU_id FROM M_HU_Trx_Line trx_line --, trx_line2.M_HU_Trx_Line_id::integer AS passed_trx_id 
		LEFT OUTER JOIN M_HU_Trx_hdr trx_hdr ON trx_line.M_HU_Trx_hdr_ID = trx_hdr.M_HU_Trx_Hdr_ID
		LEFT OUTER JOIN M_HU_Trx_Line trx_line2 ON trx_line2.parent_hu_trx_line_id = trx_line.M_HU_Trx_Line_ID
			
		--find another trxline/header for the same hu
		LEFT OUTER JOIN M_HU_Trx_Line trx_line3 ON trx_line2.M_HU_ID = trx_line3.M_HU_ID   AND  trx_line2.M_HU_Trx_Line_ID != trx_line3.M_HU_Trx_Line_ID AND trx_line.M_HU_Trx_Line_ID != trx_line3.M_HU_Trx_Line_ID
				AND 	trx_line3.qty!=0 
				
		WHERE trx_line.M_HU_Trx_Line_ID = ANY($1)
		) loop
       new_trxline := new_trxline || r.M_HU_Trx_Line_id;
  
    END LOOP;
    RETURN query execute v_sql || array_to_string(new_trxline,',') || ']);' ;
END;
$$ LANGUAGE plpgsql;



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
