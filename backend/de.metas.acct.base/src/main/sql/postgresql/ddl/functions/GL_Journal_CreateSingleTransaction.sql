SELECT db_drop_functions('de_metas_acct.GL_Journal_CreateSingleTransaction')
;

CREATE OR REPLACE FUNCTION de_metas_acct.GL_Journal_CreateSingleTransaction(
    p_DateAcct        timestamp WITH TIME ZONE,
    --
    p_DR_Acct         varchar,
    p_CR_Acct         varchar,
    p_Amt             numeric,
    p_Description     varchar,
    --
    p_GL_Journal_ID   NUMERIC(10, 0) = NULL,
    p_C_AcctSchema_ID NUMERIC(10, 0) = NULL,
    p_AD_Client_ID    NUMERIC(10, 0) = 1000000,
    p_AD_Org_ID       NUMERIC(10, 0) = 1000000,
    p_M_Product_ID    NUMERIC(10, 0) = NULL,
    p_M_Locator_ID    NUMERIC(10, 0) = NULL
)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_GL_Journal_ID            numeric;
    v_GL_Journal_DocType_ID    numeric;
    v_GL_Journal_DocType_Name  varchar;
    v_C_AcctSchema_ID          numeric;
    v_Acct_Currency_ID         numeric;
    v_account_dr_id            numeric;
    v_account_cr_id            numeric;
    v_DR_AccountConceptualName varchar;
    v_CR_AccountConceptualName varchar;
BEGIN
    --
    -- GL_Journal_ID
    IF (COALESCE(p_GL_Journal_ID, 0) <= 0) THEN
        SELECT NEXTVAL('gl_journal_seq') INTO v_GL_Journal_ID;
    ELSE
        v_GL_Journal_ID := p_GL_Journal_ID;
    END IF;
    RAISE NOTICE 'Using GL_Journal_ID: %', v_GL_Journal_ID;

    --
    -- C_DocType_ID
    SELECT dt.c_doctype_id, dt.name
    INTO v_GL_Journal_DocType_ID, v_GL_Journal_DocType_Name
    FROM c_doctype dt
    WHERE dt.isactive = 'Y'
      AND dt.docbasetype = 'GLJ'
      AND dt.ad_client_id = p_AD_Client_ID
      AND dt.ad_org_id IN (0, p_AD_Org_ID)
    ORDER BY dt.ad_org_id DESC, dt.c_doctype_id
    LIMIT 1;
    RAISE NOTICE 'Using GL_Journal.C_DocType_ID: % (%)', v_GL_Journal_DocType_ID, v_GL_Journal_DocType_Name;
    IF (COALESCE(v_GL_Journal_DocType_ID, 0) <= 0) THEN
        RAISE EXCEPTION 'No GL Journal DocType found';
    END IF;

    --
    -- C_AcctSchema_ID
    IF (COALESCE(p_C_AcctSchema_ID, 0) <= 0) THEN
        v_C_AcctSchema_ID := getc_acctschema_id(p_AD_Client_ID, p_AD_Org_ID);
    ELSE
        v_C_AcctSchema_ID := p_C_AcctSchema_ID;
    END IF;
    RAISE NOTICE 'Using C_AcctSchema_ID: %', v_C_AcctSchema_ID;
    IF (COALESCE(v_C_AcctSchema_ID, 0) <= 0) THEN
        RAISE EXCEPTION 'No accounting schema found';
    END IF;

    --
    -- Account Schema Currency
    SELECT cas.c_currency_id INTO v_Acct_Currency_ID FROM c_acctschema cas WHERE cas.c_acctschema_id = v_C_AcctSchema_ID;
    RAISE NOTICE 'Using C_AcctSchema.C_Currency_ID: %', v_Acct_Currency_ID;

    --
    -- Accounts
    v_account_dr_id := metasfresh.de_metas_acct.get_c_validcombination(p_elementvalue => p_DR_Acct, p_c_acctschema_id => v_C_AcctSchema_ID);
    v_account_cr_id := metasfresh.de_metas_acct.get_c_validcombination(p_elementvalue => p_CR_Acct, p_c_acctschema_id => v_C_AcctSchema_ID);

    --
    -- AccountConceptualName
    SELECT NULLIF(TRIM(ev.accountconceptualname), '')
    INTO v_DR_AccountConceptualName
    FROM c_validcombination vc
             INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = vc.account_id
    WHERE vc.c_validcombination_id = v_account_dr_id;
    RAISE NOTICE 'Using DR_AccountConceptualName: %', v_DR_AccountConceptualName;
    --
    SELECT NULLIF(TRIM(ev.accountconceptualname), '')
    INTO v_CR_AccountConceptualName
    FROM c_validcombination vc
             INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = vc.account_id
    WHERE vc.c_validcombination_id = v_account_cr_id;
    RAISE NOTICE 'Using CR_AccountConceptualName: %', v_CR_AccountConceptualName;

    --
    --
    --
    -- Remove the existing draft GL Journal if any
    --
    --
    --
    BEGIN
        -- Make sure the period is open!
        SELECT "de_metas_acct".assert_period_open(
                       p_DateAcct=>j.dateacct,
                       p_DocBaseType =>dt.docbasetype,
                       p_AD_Client_ID =>j.ad_client_id,
                       p_AD_Org_ID =>j.ad_org_id)
        FROM gl_journal j
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = j.c_doctype_id
        WHERE j.gl_journal_id = v_GL_Journal_ID
          AND j.processed = 'Y';


        DELETE FROM fact_acct fa WHERE fa.ad_table_id = get_table_id('GL_Journal') AND fa.record_id = v_GL_Journal_ID;
        DELETE FROM gl_journalline jl WHERE jl.gl_journal_id = v_GL_Journal_ID;
        DELETE FROM gl_journal j WHERE j.gl_journal_id = v_GL_Journal_ID;
    END;
    
    

    --
    --
    --
    -- Create GL Journal
    --
    --
    --

    INSERT INTO gl_journal (gl_journal_id,
                            ad_client_id,
                            ad_org_id,
                            isactive,
                            created,
                            createdby,
                            updated,
                            updatedby,
                            c_acctschema_id,
                            c_doctype_id,
                            documentno,
                            docstatus,
                            docaction,
                            description,
                            postingtype,
                            gl_budget_id,
                            gl_category_id,
                            datedoc,
                            dateacct,
                            c_currency_id,
                            currencyrate,
                            totaldr,
                            totalcr,
                            controlamt,
                            processing,
                            processed,
                            posted,
                            c_conversiontype_id)
    SELECT v_GL_Journal_ID          AS gl_journal_id,
           p_AD_Client_ID           AS ad_client_id,
           p_AD_Org_ID              AS ad_org_id,
           'Y'                      AS isactive,
           NOW()                    AS created,
           99                       AS createdby,
           NOW()                    AS updated,
           99                       AS updatedby,
           v_C_AcctSchema_ID        AS c_acctschema_id,
           v_GL_Journal_DocType_ID  AS c_doctype_id,
           'GEN' || v_GL_Journal_ID AS documentno,
           'DR'                     AS docstatus,
           'CO'                     AS docaction,
           p_Description            AS description,
           'A'                      AS postingtype,
           NULL                     AS gl_budget_id,
           1000002                  AS gl_category_id, -- manual
           p_DateAcct               AS datedoc,
           p_DateAcct               AS dateacct,
           v_Acct_Currency_ID       AS c_currency_id,
           1                        AS currencyrate,
           0                        AS totaldr,
           0                        AS totalcr,
           0                        AS controlamt,
           'N'                      AS processing,
           'N'                      AS processed,
           'N'                      AS posted,
           114                         c_conversiontype_id -- Spot
    ;

    INSERT INTO gl_journalline (gl_journalline_id,
                                ad_client_id,
                                ad_org_id,
                                isactive,
                                created,
                                createdby,
                                updated,
                                updatedby,
                                gl_journal_id,
                                line,
                                isgenerated,
                                description,
                                amtsourcedr,
                                amtsourcecr,
                                c_currency_id,
                                currencyrate,
                                dateacct,
                                amtacctdr,
                                amtacctcr,
                                c_uom_id,
                                qty,
                                c_validcombination_id,
                                c_conversiontype_id,
                                processed,
                                account_dr_id,
                                account_cr_id,
                                isallowaccountcr,
                                isallowaccountdr,
                                gl_journalline_group,
                                dr_m_product_id,
                                cr_m_product_id,
                                dr_accountconceptualname,
                                cr_accountconceptualname,
                                dr_locator_id,
                                cr_locator_id)
    SELECT NEXTVAL('gl_journalline_seq') AS gl_journalline_id,
           glj.ad_client_id              AS ad_client_id,
           glj.ad_org_id                 AS ad_org_id,
           'Y'                           AS isactive,
           glj.created                   AS created,
           glj.createdby                 AS createdby,
           glj.updated                   AS updated,
           glj.updatedby                 AS updatedby,
           glj.gl_journal_id,
           10                            AS line,
           'N'                           AS isgenerated,
           NULL                          AS description,
           p_Amt                         AS amtsourcedr,
           p_Amt                         AS amtsourcecr,
           glj.c_currency_id,
           1                             AS currencyrate,
           glj.dateacct,
           p_Amt                         AS amtacctdr,
           p_Amt                         AS amtacctcr,
           NULL                          AS c_uom_id,
           NULL                          AS qty,
           NULL                          AS c_validcombination_id,
           glj.c_conversiontype_id,
           'N'                           AS processed,
           v_account_dr_id               AS account_dr_id,
           v_account_cr_id               AS account_cr_id,
           'Y'                           AS isallowaccountcr,
           'Y'                           AS isallowaccountdr,
           10                            AS gl_journalline_group, -- same as lineno
           p_M_Product_ID                AS dr_m_product_id,
           p_M_Product_ID                AS cr_m_product_id,
           v_DR_AccountConceptualName    AS dr_accountconceptualname,
           v_CR_AccountConceptualName    AS cr_accountconceptualname,
           p_M_Locator_ID                AS dr_locator_id,
           p_M_Locator_ID                AS cr_locator_id
    FROM gl_journal glj
    WHERE glj.gl_journal_id = v_GL_Journal_ID;


    --
    -- Update GL_Journal totals
    WITH totals AS (SELECT jl.gl_journal_id, COALESCE(SUM(AmtAcctDr), 0) AS TotalDr, COALESCE(SUM(AmtAcctCr), 0) AS TotalCr
                    FROM GL_JournalLine jl
                    WHERE jl.IsActive = 'Y'
                      AND jl.GL_Journal_ID = v_GL_Journal_ID
                    GROUP BY jl.gl_journal_id)
    UPDATE GL_Journal j
    SET totaldr=totals.TotalDr, totalcr=totals.TotalCr
    FROM totals
    WHERE j.gl_journal_id = totals.gl_journal_id;

    --
    --
    RAISE NOTICE 'Created GL_Journal: %', v_GL_Journal_ID;
END;
$$
;


-- Test
/*
SELECT *
FROM de_metas_acct.GL_Journal_CreateSingleTransaction(
        p_DateAcct => '2024-12-31'::date,
    --
        p_DR_Acct => '4800',
        p_CR_Acct => '12700',
        p_Amt => 123.45,
        p_Description => 'bla bla',
    --
        p_GL_Journal_ID => 1234
     )
;
*/


