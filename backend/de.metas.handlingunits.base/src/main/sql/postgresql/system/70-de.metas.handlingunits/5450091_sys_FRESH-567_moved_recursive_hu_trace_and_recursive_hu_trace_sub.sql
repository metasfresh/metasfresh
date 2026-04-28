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




DROP FUNCTION IF EXISTS "de.metas.handlingunits".recursive_hu_trace(trxline integer);
CREATE OR REPLACE FUNCTION "de.metas.handlingunits".recursive_hu_trace(trxline integer) RETURNS TABLE
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
		for r in (SELECT m_hu_trx_line_id::integer, m_hu_id::integer FROM "de.metas.handlingunits".recursive_hu_trace_sub(rez)) loop
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

COMMENT ON FUNCTION "de.metas.handlingunits".recursive_hu_trace(integer) IS '
-----
Usage
-----
 use: it takes an M_HU_Trx_Line and returns all HUs and transactions which come from splitting the HU from the given M_HU_Trx_Line. Note that it will return HU duplicates so you might want to select distinct if you only need HUs
 you can uncomment "RAISE NOTICE" to debug (but don''t forget to comment them back when done)
 this function calls recursively "de.metas.handlingunits".recursive_hu_trace_sub function
 first it calls the function with the given parameter, and then with the set of results (rez) of the same function (if function returns 001 and 002, we call the function with [001, 002])
 to avoid infinite loop, we save each transaction in a variable (all_trx) and on each loop we check if the transaction was already called. This way we call the function once per transaction.
 the function will return all hus and transaction_ids from all_trx 
 
 Summary
 Parameter: trxline = the transaction from the HU you want to start with
 Variables:	
			last_trx = list of transactions that result from calling recursive_hu_trace_sub function. When for loop ends it becomes 0
			rez =  It''s used to call recursive_hu_trace_sub function. First is trxline parameter and then takes the value of last_trx before it becomes 0.
			all_trx = list of all transactions returned by the recursive_hu_trace_sub function
			v_sql = a query which is used to return all hus and transaction_ids for all_trx  

--------
Testcase
--------
Testcase to check the function. It also helps if we need to modify recursive_hu_trace or recursive_hu_trace_sub

I did a purchase order with 10 IFCOx5. Went to receipt pos and moved the HUs in an empty warehouse (for visibility purpose). 
Went to Handling Units pos, opened my warehouse and split my HUs 3 times as following:  

My HUs: (5 x 10 x 1)
LU 1000650 (active)
TU 1000651,1000653,1000655,1000657,1000659, (destroyed by split)
   1000661,1000663,1000665,1000667,1000669 (active)

1st Split (5 x 5 x 1 from 1000650)
LU 1000671 (active)
TU 1000672,1000674,1000676 (destroyed by split)
   1000678,1000680 (active)

2nd Split (5 x 3 x 1 from 1000671)
LU 1000682 (destroyed by split)
TU 1000683, 1000685, 1000687 (destroyed by split)

3rd Split (5 x 2 x 2 from 1000682)
LU 1000689 (active)
TU 1000690, 1000692 (active)

LU 1000694 (active)
TU 1000695 (active)
------------------------

Run the function for the M_HU_TRX_Line(s) of my HUs 
find the trx for e.g. TU 1000651 : select m_hu_trx_line_id,* from m_hu_trx_line where m_hu_id = 1000651
select * from "de.metas.handlingunits".recursive_hu_trace(m_hu_trx_line_id)

LU 1000650: 1000650

TU:
1000651: 1000651, 1000672, 1000683, 1000690 
1000653: 1000653, 1000674, 1000685, 1000692, 1000671
1000655: 1000655, 1000676, 1000687, 1000695
1000657: 1000657, 1000678, 1000671
1000659: 1000659, 1000680, 1000671
1000661: 1000661, 1000671
1000663: 1000663
1000665: 1000665
1000667: 1000667
1000669: 1000669

Run the function for the M_HU_TRX_Line(s) of last split HUs (the other way around)

LU 1000689: 1000655, 1000676, 1000687, 1000695, 1000653, 1000674, 1000685, 1000692, 1000651, 1000672, 1000683, 1000690

TU
1000690: 1000651, 1000672, 1000683, 1000690
1000692: 1000653, 1000674, 1000685, 1000692

(note: here i put only the distinct HUs. They can appear more because of multiple transaction lines)

See task https://github.com/metasfresh/metasfresh/issues/262
';

