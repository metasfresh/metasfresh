-- noinspection SqlWithoutWhereForFile
-- noinspection SqlResolveForFile @ table/"tmp_inventory_valuation"

SELECT db_drop_functions('de_metas_acct.InventoryValue_Correct_ErrorAmt')
;

CREATE OR REPLACE FUNCTION de_metas_acct.InventoryValue_Correct_ErrorAmt(
    p_DateAcct            timestamp WITH TIME ZONE,
    p_M_Product_ID        numeric(10, 0) = 0,
    p_C_AcctSchema_ID     numeric(10, 0) = NULL,
    p_GL_Journal_DateAcct timestamp WITH TIME ZONE = NULL,
    p_GL_Journal_ID       NUMERIC(10, 0) = 0,
    p_Source              varchar = 'Acct_ErrorAmt'
)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_Count                   numeric;
    v_record                  record;
    v_GL_Journal_ID           numeric;
    v_GL_Journal_DateAcct     timestamp WITH TIME ZONE;
    v_GL_Journal_DocType_ID   numeric;
    v_GL_Journal_DocType_Name varchar;
    v_AD_Client_ID            numeric;
    v_AD_Org_ID               numeric;
    v_C_AcctSchema_ID         numeric;
    v_Acct_Currency_ID        numeric;
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
    -- GL_Journal_ID.DateAcct
    v_GL_Journal_DateAcct := COALESCE(p_GL_Journal_DateAcct, p_DateAcct);
    RAISE NOTICE 'Using GL_Journal.DateAcct: %', v_GL_Journal_DateAcct;

    --
    --
    --
    -- Remove the existing draft GL Journal if any.
    -- Do it before computing the Inventory Valuation.
    --
    --
    --

    DELETE FROM fact_acct fa WHERE fa.ad_table_id = get_table_id('GL_Journal') AND fa.record_id = v_GL_Journal_ID;
    DELETE FROM gl_journalline jl WHERE jl.gl_journal_id = v_GL_Journal_ID;
    DELETE FROM gl_journal j WHERE j.gl_journal_id = v_GL_Journal_ID;

    --
    -- Running Inventory Valuation report
    RAISE NOTICE 'Running Inventory Valuation report...';
    PERFORM de_metas_acct.report_InventoryValue(
            p_DateAcct => p_DateAcct,
            p_M_Product_ID => p_M_Product_ID,
            p_ad_language => 'en_US'
            );

    --
    -- Select from where are we taking the ErrorAmt
    ALTER TABLE tmp_inventory_valuation
        ADD COLUMN ErrorAmt numeric;
    IF (p_Source = 'Acct_ErrorAmt') THEN
        UPDATE tmp_inventory_valuation SET ErrorAmt = Acct_ErrorAmt;
    ELSIF (p_Source = 'Costing_ErrorAmt') THEN
        UPDATE tmp_inventory_valuation SET ErrorAmt = Costing_ErrorAmt;
    ELSE
        RAISE EXCEPTION 'Unknown p_Source: %', p_Source;
    END IF;

    --
    -- Remove not relevant cases
    RAISE NOTICE 'Removing not relevant cases';
    DELETE FROM tmp_inventory_valuation WHERE m_warehouse_id IS NULL; -- remove those without a warehouse
    DELETE FROM tmp_inventory_valuation WHERE m_product_id IS NULL; -- remove those without a product
    DELETE FROM tmp_inventory_valuation WHERE ErrorAmt = 0;
    IF (COALESCE(p_C_AcctSchema_ID, 0) > 0) THEN
        DELETE FROM tmp_inventory_valuation t WHERE t.c_acctschema_id != p_C_AcctSchema_ID;
    END IF;


    --
    -- Check if we still have something to adjust
    SELECT COUNT(1) INTO v_Count FROM tmp_inventory_valuation;
    IF (v_Count <= 0) THEN
        RAISE NOTICE 'No cases to adjust. Stopping here.';
        RETURN;
    END IF;
    RAISE NOTICE 'Found % cases to correct', v_Count;

    --
    -- AD_Client_ID, AD_Org_ID
    SELECT DISTINCT ad_client_id, ad_org_id INTO v_AD_Client_ID, v_AD_Org_ID FROM tmp_inventory_valuation;
    RAISE NOTICE 'Using AD_Client_ID: %', v_AD_Client_ID;
    RAISE NOTICE 'Using AD_Org_ID: %', v_AD_Org_ID;

    --
    -- C_DocType_ID
    SELECT dt.c_doctype_id, dt.name
    INTO v_GL_Journal_DocType_ID, v_GL_Journal_DocType_Name
    FROM c_doctype dt
    WHERE dt.isactive = 'Y'
      AND dt.docbasetype = 'GLJ'
      AND dt.ad_client_id = v_AD_Client_ID
      AND dt.ad_org_id IN (0, v_AD_Org_ID)
    ORDER BY dt.ad_org_id DESC, dt.c_doctype_id
    LIMIT 1;
    RAISE NOTICE 'Using GL_Journal.C_DocType_ID: % (%)', v_GL_Journal_DocType_ID, v_GL_Journal_DocType_Name;
    IF (COALESCE(v_GL_Journal_DocType_ID, 0) <= 0) THEN
        RAISE EXCEPTION 'No GL Journal DocType found';
    END IF;

    --
    -- C_AcctSchema_ID
    SELECT DISTINCT c_acctschema_id INTO v_C_AcctSchema_ID FROM tmp_inventory_valuation;
    RAISE NOTICE 'Using C_AcctSchema_ID: %', v_C_AcctSchema_ID;
    IF (COALESCE(v_C_AcctSchema_ID, 0) <= 0) THEN
        RAISE EXCEPTION 'No accounting schema found';
    END IF;

    --
    -- Account Schema Currency
    SELECT cas.c_currency_id INTO v_Acct_Currency_ID FROM c_acctschema cas WHERE cas.c_acctschema_id = v_C_AcctSchema_ID;
    RAISE NOTICE 'Using C_AcctSchema.C_Currency_ID: %', v_Acct_Currency_ID;

    --
    -- P_Asset_Acct
    ALTER TABLE tmp_inventory_valuation
        ADD COLUMN p_asset_acct numeric;
    --
    UPDATE tmp_inventory_valuation t SET p_asset_acct=de_metas_acct.get_c_validcombination(p_ElementValue =>t.AccountValue, p_C_AcctSchema_ID => t.C_AcctSchema_ID);
    --
    ALTER TABLE tmp_inventory_valuation
        ALTER COLUMN p_asset_acct SET NOT NULL;

    --
    -- W_Differences_Acct
    ALTER TABLE tmp_inventory_valuation
        ADD COLUMN w_differences_acct numeric;
    --
    UPDATE tmp_inventory_valuation t SET w_differences_acct=(SELECT wa.w_differences_acct FROM m_warehouse_acct wa WHERE wa.m_warehouse_id = t.m_warehouse_id AND wa.c_acctschema_id = t.c_acctschema_id);
    --
    FOR v_record IN (SELECT DISTINCT t.WarehouseName FROM tmp_inventory_valuation t WHERE w_differences_acct IS NULL)
        LOOP
            RAISE WARNING 'No W_Differences_Acct found for %', v_record.WarehouseName;
        END LOOP;
    --
    DELETE FROM tmp_inventory_valuation t WHERE w_differences_acct IS NULL;
    --
    ALTER TABLE tmp_inventory_valuation
        ALTER COLUMN w_differences_acct SET NOT NULL;

    --
    -- M_Locator_ID
    ALTER TABLE tmp_inventory_valuation
        ADD COLUMN m_locator_id numeric;
    --
    UPDATE tmp_inventory_valuation t
    SET m_locator_id=(SELECT loc.m_locator_id FROM m_locator loc WHERE loc.m_warehouse_id = t.m_warehouse_id ORDER BY loc.isdefault DESC, loc.m_locator_id LIMIT 1)
    WHERE t.m_locator_id IS NULL;
    --
    ALTER TABLE tmp_inventory_valuation
        ALTER COLUMN m_locator_id SET NOT NULL;


    --
    --
    -- GL Journal ready columns
    ALTER TABLE tmp_inventory_valuation
        ADD COLUMN jl_dr_acct numeric;
    ALTER TABLE tmp_inventory_valuation
        ADD COLUMN jl_dr_accountconceptualname varchar(255);
    ALTER TABLE tmp_inventory_valuation
        ADD COLUMN jl_cr_acct numeric;
    ALTER TABLE tmp_inventory_valuation
        ADD COLUMN jl_cr_accountconceptualname varchar(255);
    ALTER TABLE tmp_inventory_valuation
        ADD COLUMN jl_amt numeric;
    --
    UPDATE tmp_inventory_valuation t
    SET jl_dr_acct=t.w_differences_acct,
        jl_dr_accountconceptualname=NULL,
        jl_cr_acct=t.p_asset_acct,
        jl_cr_accountconceptualname='P_Asset_Acct',
        jl_amt= -t.ErrorAmt
    WHERE t.ErrorAmt < 0;
    --
    UPDATE tmp_inventory_valuation t
    SET jl_dr_acct=t.p_asset_acct,
        jl_dr_accountconceptualname='P_Asset_Acct',
        jl_cr_acct=t.w_differences_acct,
        jl_cr_accountconceptualname=NULL,
        jl_amt= t.ErrorAmt
    WHERE t.ErrorAmt > 0;

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
    SELECT v_GL_Journal_ID                                                      AS gl_journal_id,
           v_AD_Client_ID                                                       AS ad_client_id,
           v_AD_Org_ID                                                          AS ad_org_id,
           'Y'                                                                  AS isactive,
           NOW()                                                                AS created,
           99                                                                   AS createdby,
           NOW()                                                                AS updated,
           99                                                                   AS updatedby,
           v_C_AcctSchema_ID                                                    AS c_acctschema_id,
           v_GL_Journal_DocType_ID                                              AS c_doctype_id,
           'GEN' || v_GL_Journal_ID                                             AS documentno,
           'DR'                                                                 AS docstatus,
           'CO'                                                                 AS docaction,
           'Correct Inventory Value error amounts until ' || (p_DateAcct::date) AS description,
           'A'                                                                  AS postingtype,
           NULL                                                                 AS gl_budget_id,
           1000002                                                              AS gl_category_id, -- manual
           v_GL_Journal_DateAcct                                                AS datedoc,
           v_GL_Journal_DateAcct                                                AS dateacct,
           v_Acct_Currency_ID                                                   AS c_currency_id,
           1                                                                    AS currencyrate,
           0                                                                    AS totaldr,
           0                                                                    AS totalcr,
           0                                                                    AS controlamt,
           'N'                                                                  AS processing,
           'N'                                                                  AS processed,
           'N'                                                                  AS posted,
           114                                                                     c_conversiontype_id -- Spot
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
    SELECT NEXTVAL('gl_journalline_seq')                                     AS gl_journalline_id,
           glj.ad_client_id                                                  AS ad_client_id,
           glj.ad_org_id                                                     AS ad_org_id,
           'Y'                                                               AS isactive,
           glj.created                                                       AS created,
           glj.createdby                                                     AS createdby,
           glj.updated                                                       AS updated,
           glj.updatedby                                                     AS updatedby,
           glj.gl_journal_id,
           10 * ROW_NUMBER() OVER (ORDER BY t.productvalue, t.WarehouseName) AS line,
           'N'                                                               AS isgenerated,
           NULL                                                              AS description,
           t.jl_amt                                                          AS amtsourcedr,
           t.jl_amt                                                          AS amtsourcecr,
           glj.c_currency_id,
           1                                                                 AS currencyrate,
           glj.dateacct,
           t.jl_amt                                                          AS amtacctdr,
           t.jl_amt                                                          AS amtacctcr,
           NULL                                                              AS c_uom_id,
           NULL                                                              AS qty,
           NULL                                                              AS c_validcombination_id,
           glj.c_conversiontype_id,
           'N'                                                               AS processed,
           t.jl_dr_acct                                                      AS account_dr_id,
           t.jl_cr_acct                                                      AS account_cr_id,
           'Y'                                                               AS isallowaccountcr,
           'Y'                                                               AS isallowaccountdr,
           10 * ROW_NUMBER() OVER (ORDER BY t.productvalue, t.WarehouseName) AS gl_journalline_group, -- same as lineno
           t.m_product_id                                                    AS dr_m_product_id,
           t.m_product_id                                                    AS cr_m_product_id,
           t.jl_dr_accountconceptualname                                     AS dr_accountconceptualname,
           t.jl_cr_accountconceptualname                                     AS cr_accountconceptualname,
           t.m_locator_id                                                    AS dr_locator_id,
           t.m_locator_id                                                    AS cr_locator_id
    FROM tmp_inventory_valuation t
             LEFT OUTER JOIN gl_journal glj ON glj.gl_journal_id = v_GL_Journal_ID
    ORDER BY t.productvalue, t.WarehouseName;


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


--
--
-- Test:
/*
SELECT *
FROM de_metas_acct.InventoryValue_Correct_ErrorAmt(
        p_DateAcct => '2024-12-31',
    -- p_M_Product_ID => (SELECT m_product_id FROM m_product WHERE value = '100917'),
    --
        p_GL_Journal_ID => /*540012*/540013
     )
;
*/