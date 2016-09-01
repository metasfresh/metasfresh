/* 
 use: it takes an M_HU_Trx_Line and returns all HUs and transactions which come from splitting the HU from the given M_HU_Trx_Line.
 you can uncomment "RAISE NOTICE" to debug (but don't forget to comment them back when done)
 this function calls recursively "de.metas.handlingunits".recursive_hu_trace_sub function
 first it calls the function with the given parameter, and then with the set of results (rez) of the same function (if function returns 001 and 002, we call the function with [001, 002])
 to avoid infinite loop, we save each transaction in a variable (all_trx) and on each loop we check if the transaction was already called. This way we call the function once per transaction.
 the function will return all hus and transaction_ids from all_trx 
 
 Summary
 Parameter: trxline = the transaction from the HU you want to start with
 Variables:	
			last_trx = list of transactions that result from calling recursive_hu_trace_sub function. When for loop ends it becomes 0
			rez =  It's used to call recursive_hu_trace_sub function. First is trxline parameter and then takes the value of last_trx before it becomes 0.
			all_trx = list of all transactions returned by the recursive_hu_trace_sub function
			v_sql = a query which is used to return all hus and transaction_ids for all_trx  
 */

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
