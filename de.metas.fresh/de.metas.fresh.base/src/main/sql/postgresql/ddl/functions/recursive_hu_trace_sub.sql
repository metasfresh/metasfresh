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
