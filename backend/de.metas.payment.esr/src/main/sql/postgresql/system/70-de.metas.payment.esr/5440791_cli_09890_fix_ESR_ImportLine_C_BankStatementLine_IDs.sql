drop table if exists TMP_ESR_ImportLine_ToChange;
create temp table TMP_ESR_ImportLine_ToChange as
select
	bs.DocumentNo as BS_DocumentNo
	, bs.DocStatus as BS_DocStatus
	, esrl.*
from ESR_ImportLine esrl
left outer join ESR_Import esr on (esr.ESR_Import_ID=esrl.ESR_Import_ID)
left outer join C_BankStatementLine bsl on (bsl.C_BankStatementLine_ID=esrl.C_BankStatementLine_ID)
left outer join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID)
where true
and esrl.C_BankStatementLine_ID is not null
and (bs.DocStatus is null or bs.DocStatus in ('VO', 'RE'))
;


--
-- Show result
-- select * from TMP_ESR_ImportLine_ToChange;

--
-- Update
create table backup.ESR_ImportLine_09890_bkp20160304 as select * from ESR_ImportLine;

update ESR_ImportLine l set C_BankStatementLine_ID=null, C_BankStatementLine_Ref_ID=null
from TMP_ESR_ImportLine_ToChange t
where t.ESR_ImportLine_ID=l.ESR_ImportLine_ID
;

