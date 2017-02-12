DROP FUNCTION IF EXISTS "de.metas.handlingunits".recursive_hu_trace_sub(trxline integer[]);
CREATE OR REPLACE FUNCTION "de.metas.handlingunits".recursive_hu_trace_sub(trxline integer[]) RETURNS TABLE
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
COMMENT ON FUNCTION "de.metas.handlingunits".recursive_hu_trace_sub(integer[]) IS '
 this function is currently used in "de.metas.handlingunits".recursive_hu_trace function
 use: it takes an array of M_HU_Trx_Line(s) and returns for each M_HU_Trx_Line the directly related M_HU_Trx_Line(s) which come from splitting an HU.
 in case an HU was split, you will have at least 2 M_HU_Trx_Line(s) with qty!=0 (one with + and one with -) which have on parent_hu_trx_line_id the M_HU_Trx_Line_ID of each other. The new split HU lies in it''s parent.    
 if the new HU is split again, then there is another M_HU_Trx_Line (a third one) with this HU. 
 that''s why first we search for the "latest" M_HU_Trx_Line, and if there is none we stay with the parent.
 You can picture this function in layers:
 Layer 1 is the transaction you have + the parent. Parent is the split HU. 
 Layer 2 is the third transaction, with the same HU from Layer 1, and it announces a second split. If there is no second split, then there is just 1 layer. This is where this function stops.
 To get to layer 3, you just have to call this function with the transaction(s) from Layer 2. And so on, until the HU is not split any more 

SEE https://github.com/metasfresh/metasfresh/issues/262
';
