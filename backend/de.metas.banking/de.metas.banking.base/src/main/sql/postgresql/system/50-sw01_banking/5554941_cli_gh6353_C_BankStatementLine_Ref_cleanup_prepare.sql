--
-- Delete C_BankStatementLine_Ref records where C_BankStatement.DocStatus='VO'
-- reason: we had a lot of records where C_Payment_ID is null but in next script we want to enforce C_Payment_ID is not null
create table backup.c_bankstatementline_ref_deleted_because_bankstatement_was_voided as
select r.*
from c_bankstatementline_ref r
inner join c_bankstatementline bsl on r.c_bankstatementline_id = bsl.c_bankstatementline_id
INNER JOIN c_bankstatement bs on bsl.c_bankstatement_id = bs.c_bankstatement_id
where bs.docstatus='VO'
;

delete from c_bankstatementline_ref
where c_bankstatementline_ref_id in (select c_bankstatementline_ref_id from backup.c_bankstatementline_ref_deleted_because_bankstatement_was_voided)
;


/* checking script:
select bs.documentno, bs.docstatus,
       bsl.line, bsl.trxamt, bsl.ismultiplepaymentorinvoice, bsl.ismultiplepayment,
       r.trxamt, r.c_invoice_id, r.c_payment_id
from c_bankstatementline_ref r
inner join c_bankstatementline bsl on r.c_bankstatementline_id = bsl.c_bankstatementline_id
INNER JOIN c_bankstatement bs on bsl.c_bankstatement_id = bs.c_bankstatement_id
where r.c_payment_id is null
and bs.docstatus <>'VO'
*/

-- ------------------------------------------------------------------------------------------------------------------------------------

