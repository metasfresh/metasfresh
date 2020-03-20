drop view if exists C_BankStatementLine_v1;
create or replace view C_BankStatementLine_v1 as
select
	l.C_BankStatement_ID
	, r.C_BankStatementLine_ID
	, r.C_BankStatementLine_Ref_ID

	-- Amounts
	, r.TrxAmt
	, 0::numeric as DiscountAmt
	, 0::numeric as WriteOffAmt
	, 'N'::char(1) as IsOverUnderPayment
	, 0::numeric as OverUnderAmt

	, r.C_Currency_ID
	
	-- Dates
	, l.DateAcct, l.ValutaDate

	-- Documents
	, r.C_Invoice_ID, r.C_Payment_ID
	
	-- Bank transfer fields:
	-- NOTE: here they shall be null because we have line references
	, l.C_BP_BankAccountTo_ID
	, l.Link_BankStatementLine_ID

	-- Other
	, l.Line
	, r.C_BPartner_ID
	, r.Processed
	, r.ReferenceNo

	-- Standard fields
	, r.AD_Client_ID, r.AD_Org_ID, r.Created, r.CreatedBy, r.Updated, r.UpdatedBy, r.IsActive
from C_BankStatementLine_Ref r
inner join C_BankStatementLine l on (l.C_BankStatementLine_ID=r.C_BankStatementLine_ID)
--
union all
select
	l.C_BankStatement_ID
	, l.C_BankStatementLine_ID
	, null::numeric as C_BankStatementLine_Ref_ID

	-- Amounts
	, l.TrxAmt
	, l.DiscountAmt
	, l.WriteOffAmt
	, l.IsOverUnderPayment
	, l.OverUnderAmt

	, l.C_Currency_ID

	-- Dates
	, l.DateAcct, l.ValutaDate
	
	-- Documents
	, l.C_Invoice_ID, l.C_Payment_ID

	-- Bank transfer fields:
	, l.C_BP_BankAccountTo_ID
	, l.Link_BankStatementLine_ID

	-- Other
	, l.Line
	, l.C_BPartner_ID
	, l.Processed
	, l.ReferenceNo

	-- Standard fields
	, l.AD_Client_ID, l.AD_Org_ID, l.Created, l.CreatedBy, l.Updated, l.UpdatedBy, l.IsActive
from C_BankStatementLine l
;

