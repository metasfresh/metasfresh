drop view if exists RV_DATEV_Export_Fact_Acct_Invoice;
create or replace view RV_DATEV_Export_Fact_Acct_Invoice as
select
(case when fa.AmtAcctDr<>0 then ev.Value else ev2.Value end) as DR_Account
,(case when fa.AmtAcctDr<>0 then ev2.Value else ev.Value end) as CR_Account
,(case when fa.AmtAcctDr<>0 then fa.AmtAcctDr else fa.AmtAcctCr end) as Amt
, a.Name as ActivityName
, a.C_Activity_ID
, fa.DocumentNo
, fa.DateAcct
, bp.Value as BPValue
, bp.Name as BPName
, paymentTermDueDate(i.C_PaymentTerm_ID,i.DateInvoiced) as DueDate
, fa.Description
--
, bp.C_BPartner_ID
, fa.Record_ID as C_Invoice_ID
, fa.DocBaseType
, fa.Fact_Acct_ID
, fa.Fact_Acct_ID as RV_DATEV_Export_Fact_Acct_Invoice_ID
, fa.AD_Client_ID
, fa.AD_Org_ID
--
from Fact_Acct fa
inner join Fact_Acct fa2 on (fa2.Fact_Acct_ID=fa.Counterpart_Fact_Acct_ID)
inner join C_ElementValue ev on (ev.C_ElementValue_ID=fa.Account_ID)
inner join C_ElementValue ev2 on (ev2.C_ElementValue_ID=fa2.Account_ID)
inner join C_BPartner bp on (bp.C_BPartner_ID=fa.C_BPartner_ID)
left outer join C_Activity a on (a.C_Activity_ID=coalesce(fa.C_Activity_ID, fa2.C_Activity_ID))
inner join C_Invoice i on (i.C_Invoice_ID=fa.Record_ID)
where fa.AD_Table_ID=get_Table_ID('C_Invoice')
;

/*
select * from RV_DATEV_Export_Fact_Acct_Invoice order by C_Invoice_ID;
*/


