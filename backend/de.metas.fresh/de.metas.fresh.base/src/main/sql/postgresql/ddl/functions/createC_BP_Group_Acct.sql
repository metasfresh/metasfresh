drop function IF EXISTS createC_BP_Group_Acct();

create function createC_BP_Group_Acct()
    returns void
    language plpgsql
as
$$

BEGIN

    WITH groups AS (
        SELECT bpg.AD_Client_ID, bpg.AD_Org_ID, bpg.C_BP_Group_id
        FROM C_BP_Group bpg
        where not exists(
                select 1 from C_BP_Group_Acct acct where acct.C_BP_Group_id = bpg.C_BP_Group_id)
    )
    INSERT
    INTO C_BP_Group_Acct (C_BP_Group_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
                          Updated, UpdatedBy, C_Prepayment_Acct, C_Receivable_Acct, C_Receivable_Services_Acct,
                          NotInvoicedReceipts_Acct, NotInvoicedReceivables_Acct, NotInvoicedRevenue_Acct,
                          PayDiscount_Exp_Acct, PayDiscount_Rev_Acct, UnEarnedRevenue_Acct, V_Liability_Acct,
                          V_Liability_Services_Acct, V_Prepayment_Acct, WriteOff_Acct)
    SELECT groups.C_BP_Group_ID,
           p.C_AcctSchema_ID,
           groups.AD_Client_ID,
           groups.AD_Org_ID,
           'Y',
           now(),
           99,
           now(),
           99,
           p.C_Prepayment_Acct,
           p.C_Receivable_Acct,
           p.C_Receivable_Services_Acct,
           p.NotInvoicedReceipts_Acct,
           p.NotInvoicedReceivables_Acct,
           p.NotInvoicedRevenue_Acct,
           p.PayDiscount_Exp_Acct,
           p.PayDiscount_Rev_Acct,
           p.UnEarnedRevenue_Acct,
           p.V_Liability_Acct,
           p.V_Liability_Services_Acct,
           p.V_Prepayment_Acct,
           p.WriteOff_Acct
    FROM C_AcctSchema_Default p,
         groups
    WHERE p.AD_Client_ID = 1000000
      AND NOT EXISTS(SELECT 1
                     FROM C_BP_Group_Acct e
                     WHERE e.C_AcctSchema_ID = p.C_AcctSchema_ID
                       AND e.C_BP_Group_ID = groups.C_BP_Group_ID);

END;
$$;
