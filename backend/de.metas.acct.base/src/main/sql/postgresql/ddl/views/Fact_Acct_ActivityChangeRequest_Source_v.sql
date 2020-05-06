create or replace view Fact_Acct_ActivityChangeRequest_Source_v as
select
	fa.DocumentNo
	, fa.C_DocType_ID
	, fa.DocBaseType
	, ev.IsMandatoryActivity
	, fa.AD_Table_ID, fa.Record_ID, fa.Line_ID, fa.SubLine_ID
	, fa.Description
	, fa.DateTrx, fa.DateAcct, fa.C_Period_ID
	, fa.PostingType
	, fa.C_AcctSchema_ID, fa.Account_ID
	, fa.C_Currency_ID
	, fa.AmtSourceDr, fa.AmtSourceCr, fa.AmtAcctDr, fa.AmtAcctCr
	, fa.Qty, fa.C_UOM_ID
	, fa.M_Product_ID, fa.C_BPartner_ID
	, fa.C_Activity_ID
	--
	, fa.AD_Client_ID, fa.AD_Org_ID, fa.Created, fa.CreatedBy, fa.Updated, fa.UpdatedBy
	, fa.Fact_Acct_ID
from Fact_Acct fa
inner join C_ElementValue ev on (ev.C_ElementValue_ID=fa.Account_ID)
inner join AD_Table t on (t.AD_Table_ID=fa.AD_Table_ID)
where true
;

