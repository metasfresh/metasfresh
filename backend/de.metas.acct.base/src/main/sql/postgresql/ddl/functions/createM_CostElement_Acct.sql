DROP FUNCTION IF EXISTS createM_CostElement_Acct()
;

CREATE FUNCTION createM_CostElement_Acct()
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_rowCount numeric;
BEGIN
    INSERT INTO M_CostElement_Acct (M_CostElement_Acct_ID,
                                    M_CostElement_ID,
                                    C_AcctSchema_ID,
                                    AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                    P_CostClearing_Acct)
    SELECT NEXTVAL('m_costelement_acct_seq'),
           ce.m_costelement_id,
           defaults.c_acctschema_id,
           ce.AD_Client_ID,
           ce.AD_Org_ID,
           'Y',
           NOW(),
           99,
           NOW(),
           99,
           defaults.p_costclearing_acct
    FROM C_AcctSchema_Default defaults,
         M_CostElement ce
    WHERE defaults.AD_Client_ID = 1000000
      AND NOT EXISTS(SELECT 1 FROM m_costelement_acct e WHERE e.C_AcctSchema_ID = defaults.C_AcctSchema_ID AND e.m_costelement_id = ce.m_costelement_id);

    GET DIAGNOSTICS v_rowCount = ROW_COUNT;
    RAISE NOTICE 'Created % M_CostElement_Acct records', v_rowCount;
END;
$$
;

/*
select createM_CostElement_Acct();
 */


