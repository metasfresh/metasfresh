--
-- Set Fact_Acct.SubLine_ID for C_BankStatement 
--

create table backup.Fact_Acct_BKP_before_09549 as select * from Fact_Acct;

drop table if exists TMP_Fact_Acct_ToUpdate;
create temporary table TMP_Fact_Acct_ToUpdate as
select *
from Fact_Acct fa
where true
and fa.SubLine_ID is null
and fa.AD_Table_ID=get_table_id('C_BankStatement')
and exists (select 1 from C_BankStatementLine_Ref bslr where bslr.C_BankStatementLine_ID=fa.Line_ID)
and fa.Account_ID=(select ev.C_ElementValue_ID from C_ElementValue ev where ev.Value='1060')
;
--
create index on TMP_Fact_Acct_ToUpdate(Fact_Acct_ID);


drop table if exists TMP_BSLR_ToMatch;
create temporary table TMP_BSLR_ToMatch as
select 
	exists (select 1 from Fact_Acct fa
		where fa.AD_Table_ID=get_table_id('C_BankStatement')
		and fa.Record_ID=l.C_BankStatement_ID
		and fa.Line_ID=r.C_BankStatementLine_ID
		and fa.SubLine_ID=r.C_BankStatementLine_Ref_ID)
	as IsMatched
	--
	, l.C_BankStatement_ID
	, r.*
from C_BankStatementLine_Ref r
inner join C_BankStatementLine l on (l.C_BankStatementLine_ID=r.C_BankStatementLine_ID)
where true
;
--
create index on TMP_BSLR_ToMatch (IsMatched);
-- select * from TMP_BSLR_ToMatch where IsMatched=false



--
-- Match
SET client_min_messages TO NOTICE;
DO $$
declare
	fl record;
	v_Fact_count numeric;
	v_Fact_idx integer := 0;
	v_Fact_countMatched integer := 0;
	v_C_BankStatementLine_Ref_ID numeric;
begin
	select count(*) into v_Fact_count from TMP_Fact_Acct_ToUpdate where SubLine_ID is not null;
	raise notice '% facts alread matched', v_Fact_count;
	select count(*) into v_Fact_count from TMP_Fact_Acct_ToUpdate where SubLine_ID is null;
	raise notice '% facts to match', v_Fact_count;

	--
	for fl in (select * from TMP_Fact_Acct_ToUpdate where SubLine_ID is null)
	loop
		v_Fact_idx := v_Fact_idx + 1;
		raise debug '-----------------------------------------------------------------';
		raise debug '%/%. FactLine: ID=%, Record_ID=%, Line_ID=%, AmtSourceDr=%, AmtSourceCr=%, C_Currency_ID=%, C_BPartner_ID=%'
			, v_Fact_idx, v_Fact_count, fl.Fact_Acct_ID
			, fl.Record_ID, fl.Line_ID
			, fl.AmtSourceDr, fl.AmtSourceCr
			, fl.C_Currency_ID, fl.C_BPartner_ID
		;
	
		--
		-- Search for C_BankStatementLine_Ref_ID to match
		select bslr.C_BankStatementLine_Ref_ID
		into v_C_BankStatementLine_Ref_ID
		from TMP_BSLR_ToMatch bslr
		where true
		and bslr.IsMatched=false -- not already matched
		and bslr.C_BankStatementLine_ID=fl.Line_ID
		and bslr.C_Currency_ID=fl.C_Currency_ID
		and bslr.C_BPartner_ID=fl.C_BPartner_ID
		and ((bslr.TrxAmt = 0 - fl.AmtSourceDr) or (bslr.TrxAmt = fl.AmtSourceCr))
		limit 1;
		--
		if (v_C_BankStatementLine_Ref_ID is null) then
			raise debug 'No BSLR found for Fact_Acct_ID=%', fl.Fact_Acct_ID;
		else
			--
			-- Update
			raise debug 'Found C_BankStatementLine_Ref_ID=%', v_C_BankStatementLine_Ref_ID;
			update TMP_Fact_Acct_ToUpdate set SubLine_ID=v_C_BankStatementLine_Ref_ID where Fact_Acct_ID=fl.Fact_Acct_ID;
			update TMP_BSLR_ToMatch set IsMatched=true where C_BankStatementLine_Ref_ID=v_C_BankStatementLine_Ref_ID;

			v_Fact_countMatched := v_Fact_countMatched + 1;
		end if;
		

		--
		--
		if (v_Fact_idx % 200 = 0) then
			raise notice 'STATUS: % facts checked, % facts matched', v_Fact_idx, v_Fact_countMatched;
		end if;
	end loop;
	
	raise notice 'DONE: % facts checked, % facts matched', v_Fact_idx, v_Fact_countMatched;
end $$;


--
-- Update Fact_Acct table
update Fact_Acct fa set SubLine_ID=t.SubLine_ID
from TMP_Fact_Acct_ToUpdate t
where fa.Fact_Acct_ID=t.Fact_Acct_ID
	and fa.SubLine_ID is null
	and t.SubLine_ID is not null;
