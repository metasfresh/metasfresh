drop table if exists TMP_C_PaySelectionLine_WithVoidedBankStatements;
create temporary table TMP_C_PaySelectionLine_WithVoidedBankStatements as
select psl.*
from C_PaySelectionLine psl
left outer join C_BankStatementLine_Ref bslr on (bslr.C_BankStatementLine_Ref_ID=psl.C_BankStatementLine_Ref_ID)
inner join C_BankStatementLine bsl on (bsl.C_BankStatementLine_ID=psl.C_BankStatementLine_ID or bsl.C_BankStatementLine_ID=bslr.C_BankStatementLine_ID)
inner join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID)
where 
	bs.C_BankStatement_ID is not null
	and bs.DocStatus not in ('CO', 'CL')
;
--
create index on TMP_C_PaySelectionLine_WithVoidedBankStatements(C_PaySelectionLine_ID);

--
-- Backup
create table C_PaySelectionLine_before_09645 as select * from C_PaySelectionLine psl where exists (select 1 from TMP_C_PaySelectionLine_WithVoidedBankStatements t where t.C_PaySelectionLine_ID=psl.C_PaySelectionLine_ID);

--
-- Update:

update C_PaySelectionLine psl set C_BankStatementLine_ID=null, C_BankStatementLine_Ref_ID=null
from TMP_C_PaySelectionLine_WithVoidedBankStatements t
where t.C_PaySelectionLine_ID=psl.C_PaySelectionLine_ID
;

