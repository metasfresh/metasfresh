/*
NOTE:
* we assume 5437822_sys_09110_optimize_fact_acct_log_tg_fn.sql was already executed
* this is a long running script
*/


update Fact_Acct fa set   C_DocType_ID=null, DocumentNo=d.DocumentNo from C_AllocationHdr d where fa.AD_Table_ID=735 and fa.Record_ID=d.C_AllocationHdr_ID;
update Fact_Acct fa set   C_DocType_ID=null, DocumentNo=d.DocumentNo from C_BankStatement d where fa.AD_Table_ID=392 and fa.Record_ID=d.C_BankStatement_ID;
update Fact_Acct fa set   C_DocType_ID=null, DocumentNo=d.Name from C_Cash d where fa.AD_Table_ID=407 and fa.Record_ID=d.C_Cash_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from C_Invoice d where fa.AD_Table_ID=318 and fa.Record_ID=d.C_Invoice_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from C_Order d where fa.AD_Table_ID=259 and fa.Record_ID=d.C_Order_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from C_Payment d where fa.AD_Table_ID=335 and fa.Record_ID=d.C_Payment_ID;
update Fact_Acct fa set   C_DocType_ID=null, DocumentNo=null from C_ProjectIssue d where fa.AD_Table_ID=623 and fa.Record_ID=d.C_ProjectIssue_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from DD_Order d where fa.AD_Table_ID=53037 and fa.Record_ID=d.DD_Order_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from GL_Journal d where fa.AD_Table_ID=224 and fa.Record_ID=d.GL_Journal_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from HR_Process d where fa.AD_Table_ID=53092 and fa.Record_ID=d.HR_Process_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from M_InOut d where fa.AD_Table_ID=319 and fa.Record_ID=d.M_InOut_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from M_Inventory d where fa.AD_Table_ID=321 and fa.Record_ID=d.M_Inventory_ID;
update Fact_Acct fa set   C_DocType_ID=null, DocumentNo=d.DocumentNo from M_MatchInv d where fa.AD_Table_ID=472 and fa.Record_ID=d.M_MatchInv_ID;
update Fact_Acct fa set   C_DocType_ID=null, DocumentNo=d.DocumentNo from M_MatchPO d where fa.AD_Table_ID=473 and fa.Record_ID=d.M_MatchPO_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from M_Movement d where fa.AD_Table_ID=323 and fa.Record_ID=d.M_Movement_ID;
update Fact_Acct fa set   C_DocType_ID=null, DocumentNo=d.Name from M_Production d where fa.AD_Table_ID=325 and fa.Record_ID=d.M_Production_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from M_Requisition d where fa.AD_Table_ID=702 and fa.Record_ID=d.M_Requisition_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from PP_Cost_Collector d where fa.AD_Table_ID=53035 and fa.Record_ID=d.PP_Cost_Collector_ID;
update Fact_Acct fa set   C_DocType_ID=d.C_DocType_ID, DocumentNo=d.DocumentNo from PP_Order d where fa.AD_Table_ID=53027 and fa.Record_ID=d.PP_Order_ID;


--
-- Update DocBaseType
update Fact_Acct fa set DocBaseType=(case
	when fa.C_DocType_ID is not null then (select dt.DocBaseType from C_DocType dt where dt.C_DocType_ID=fa.C_DocType_ID)
	when fa.AD_Table_ID=735 then 'CMA' -- C_AllocationHdr
	when fa.AD_Table_ID=392 then 'CMB' -- C_BankStatement_ID;
	when fa.AD_Table_ID=407 then 'CMC' --  C_Cash
	when fa.AD_Table_ID=623 then 'PJI' -- C_ProjectIssue
	when fa.AD_Table_ID=472 then 'MXI' -- M_MatchInv
	when fa.AD_Table_ID=473 then 'MXP' -- M_MatchPO
	when fa.AD_Table_ID=325 then 'MMP' -- M_Production
	else null
	end)
;


--
-- Check the results
/*
select * from Fact_Acct where DocumentNo is null or DocBaseType is null limit 100; -- shall return zero results
*/


/* script used to generate what we need to update:
DO $$
declare
	r record;
	v_resultSql text := '';
begin
	for r in (
		select t.AD_Table_ID, t.TableName
		, t.TableName||'_ID' as KeyColumnName
		, exists (select 1 from AD_Column z where z.AD_Table_ID=t.AD_Table_ID and ColumnName='C_DocType_ID') as Has_C_DocType_ID
		, exists (select 1 from AD_Column z where z.AD_Table_ID=t.AD_Table_ID and ColumnName='DocumentNo') as Has_DocumentNo
		, exists (select 1 from AD_Column z where z.AD_Table_ID=t.AD_Table_ID and z.ColumnName='Name') as Has_Name
		from AD_Column c
		inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
		where c.ColumnName='Posted'
		and t.IsView='N'
		order by t.TableName
	)
	loop
		-- raise notice 'TableName: %', r.TableName;

		v_resultSql := v_resultSql || chr(13)
		|| 'update Fact_Acct fa set '
		|| '  C_DocType_ID='||(case when r.Has_C_DocType_ID then 'd.C_DocType_ID' else 'null' end)
		|| ', DocumentNo='||(case 
			when r.Has_DocumentNo then 'd.DocumentNo'
			when r.Has_Name then 'd.Name'
			else 'null'
			end)
		||' from '||r.TableName||' d'
		||' where fa.AD_Table_ID='||r.AD_Table_ID
		||' and fa.Record_ID=d.'||r.KeyColumnName
		||';';
	end loop;

	raise notice 'Update script: %', v_resultSql;
end $$;
*/
