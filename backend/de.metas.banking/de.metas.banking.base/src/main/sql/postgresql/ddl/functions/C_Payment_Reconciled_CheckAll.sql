create or replace function C_Payment_Reconciled_CheckAll()
RETURNS void AS
$BODY$
declare
	v_rowCount integer;
begin
	--
	-- Create C_Payment selection to be updated
	drop table if exists C_Payment_Reconciled_ToUpdate;
	create temporary table C_Payment_Reconciled_ToUpdate as
	select
		t.*
		-- Bank statement line ref
		, bs1.C_BankStatement_ID as C_BankStatement_ID1
		, bs1.DocStatus as BS_DocStatus1
		, bsl1.Line as BSL_Line1
		, bslr1.C_BankStatementLine_Ref_ID as C_BankStatementLine_Ref_ID1
		-- Bank statement line
		, bs2.C_BankStatement_ID as C_BankStatement_ID2
		, bs2.DocStatus as BS_DocStatus2
		, bsl2.Line as BSL_Line2
	from (
		select
			p.DocumentNo, p.C_Payment_ID, p.DateTrx, p.DateAcct
			, p.PayAmt
			, p.C_Currency_ID
			, p.IsReceipt
			, p.DocStatus
			, p.IsReconciled as IsReconciled_OLD
			, (case when
				(
					p.DocStatus IN ('RE', 'VO')
					or exists (select 1 from C_BankStatementLine_Ref r
							inner join C_BankStatementLine bsl on (bsl.C_BankStatementLine_ID=r.C_BankStatementLine_ID)
							inner join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID)
							where r.C_Payment_ID=p.C_Payment_ID
							and bs.DocStatus in ('CO', 'CL'))
					or exists (select 1 from C_BankStatementLine bsl
							inner join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID)
							where bsl.C_Payment_ID=p.C_Payment_ID
							and bs.DocStatus in ('CO', 'CL'))
				)
				then 'Y' else 'N' end) as IsReconciled_NEW
			--
		from C_Payment p
	) t
	-- Join via C_BankStatementLine_Ref (1)
	left outer join C_BankStatementLine_Ref bslr1 on (bslr1.C_Payment_ID=t.C_Payment_ID)
	left outer join C_BankStatementLine bsl1 on (bsl1.C_BankStatementLine_ID=bslr1.C_BankStatementLine_ID)
	left outer join C_BankStatement bs1 on (bs1.C_BankStatement_ID=bsl1.C_BankStatement_ID)
	-- Join via C_BankStatementLine (2)
	left outer join C_BankStatementLine bsl2 on (bsl2.C_Payment_ID=t.C_Payment_ID)
	left outer join C_BankStatement bs2 on (bs2.C_BankStatement_ID=bsl2.C_BankStatement_ID)
	--
	where true
	and t.IsReconciled_OLD<>t.IsReconciled_NEW
	;
	--
	create index on C_Payment_Reconciled_ToUpdate (C_Payment_ID);

	--
	-- Show results
	/*
	select * from C_Payment_Reconciled_ToUpdate t
	where true
	and (select count(1) from C_Payment_Reconciled_ToUpdate t2 where t2.C_Payment_ID=t.C_Payment_ID) > 1
	;
	*/

	--
	-- Update C_Payment.IsReconciled flag
	update C_Payment p set IsReconciled=t.IsReconciled_NEW
	from C_Payment_Reconciled_ToUpdate t
	where t.C_Payment_ID=p.C_Payment_ID;
	
	GET DIAGNOSTICS v_rowCount = ROW_COUNT;
	raise notice 'Updated % payments. For more info please check C_Payment_Reconciled_ToUpdate temporary table', v_rowCount;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

COMMENT ON FUNCTION C_Payment_Reconciled_CheckAll() IS 'Function used to check and update the C_Payment.IsReconciled flag';
