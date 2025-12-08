drop function IF EXISTS createC_BP_Employee_Acct();

create function createC_BP_Employee_Acct()
    returns void
    language plpgsql
as
$$

BEGIN

    WITH partners AS (
        SELECT bp.AD_Client_ID, bp.AD_Org_ID, bp.C_BPartner_Id
        FROM C_BPartner bp
        where not exists(
                select 1 from C_BP_Employee_Acct acct where acct.C_BPartner_Id = bp.C_BPartner_Id)
    )

    INSERT
    INTO C_BP_Employee_Acct (C_BPartner_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
                             Updated, UpdatedBy, E_Expense_Acct, E_Prepayment_Acct)
    SELECT partners.C_BPartner_Id,
           p.C_AcctSchema_ID,
           partners.AD_Client_ID,
           partners.AD_Org_ID,
           'Y',
           now(),
           99,
           now(),
           99,
           p.E_Expense_Acct,
           p.E_Prepayment_Acct
    FROM C_AcctSchema_Default p,
         partners
    WHERE p.AD_Client_ID = 1000000
      AND NOT EXISTS(SELECT 1
                     FROM C_BP_Employee_Acct e
                     WHERE e.C_AcctSchema_ID = p.C_AcctSchema_ID
                       AND e.C_BPartner_ID = partners.C_BPartner_Id);

END;
$$;