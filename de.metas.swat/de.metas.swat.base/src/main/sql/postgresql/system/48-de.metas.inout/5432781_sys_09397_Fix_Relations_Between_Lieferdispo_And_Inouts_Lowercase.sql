-- 09.11.2015 18:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	exists 
	(
	select 1 from  M_ShipmentSchedule ss
	inner join M_ShipmentSchedule_QtyPicked sqp on  ss.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID
	inner join M_InOutLine iol on sqp.M_InOutLine_ID = iol.M_InOutLine_ID
	
		where iol.M_InOut_ID = @M_InOut_ID@
		and M_ShipmentSchedule.M_ShipmentSchedule_ID =  ss.M_ShipmentSchedule_ID
	)',Updated=TO_TIMESTAMP('2015-11-09 18:21:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 09.11.2015 18:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists 
	(
		select 1 from M_InOut io
		join M_InOutLine iol on io.M_InOut_ID = iol.M_InOut_ID 
		join M_ShipmentSchedule_QtyPicked sqp on iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		join M_ShipmentSchedule ss on sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		
		where  ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			and M_InOut.M_InOut_ID = io.M_InOut_ID
			
	)',Updated=TO_TIMESTAMP('2015-11-09 18:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

