CREATE OR REPLACE FUNCTION C_AcctSchema_Clone (
    p_AD_Org_ID           NUMERIC,
    p_New_AcctSchema_ID   NUMERIC,
    p_Source_AcctSchema_ID NUMERIC,
    p_New_Name            VARCHAR,
    p_Date                TIMESTAMP DEFAULT now()
)
    RETURNS VOID AS $$
BEGIN
    -- 1. Create the Main Accounting Schema Header
    INSERT INTO c_acctschema (
        c_acctschema_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
        name, description, gaap, isaccrual, costingmethod, c_currency_id, autoperiodcontrol,
        c_period_id, period_openhistory, period_openfuture, separator, hasalias, hascombination,
        istradediscountposted, isdiscountcorrectstax, m_costtype_id, costinglevel, isadjustcogs,
        ad_orgonly_id, ispostservices, isexplicitcostadjustment, commitmenttype, processing,
        taxcorrectiontype, isallownegativeposting, ispostifclearingequal, isautosetdebtoridandcreditorid,
        debtoridprefix, creditoridprefix
    )
    SELECT
        p_New_AcctSchema_ID, ad_client_id, p_AD_Org_ID, isactive, p_Date, 99, p_Date, 99,
        p_New_Name, NULL, gaap, isaccrual, costingmethod, c_currency_id, autoperiodcontrol,
        c_period_id, period_openhistory, period_openfuture, separator, hasalias, hascombination,
        istradediscountposted, isdiscountcorrectstax, m_costtype_id, costinglevel, isadjustcogs,
        p_AD_Org_ID, ispostservices, isexplicitcostadjustment, commitmenttype, processing,
        taxcorrectiontype, isallownegativeposting, ispostifclearingequal, isautosetdebtoridandcreditorid,
        debtoridprefix, creditoridprefix
    FROM c_acctschema
    WHERE c_acctschema_id = p_Source_AcctSchema_ID;

    -- 2. Copy Accounting Elements (Dimensions)
    INSERT INTO c_acctschema_element (
        c_acctschema_element_id, isactive, created, createdby, updated, ad_org_id, updatedby,
        c_acctschema_id, elementtype, name, seqno, c_element_id, ad_client_id, ismandatory,
        isbalanced, org_id, c_elementvalue_id, m_product_id, c_bpartner_id, c_location_id,
        c_salesregion_id, c_project_id, c_campaign_id, c_activity_id, ad_column_id, isdisplayineditor,
        userelementstring1, userelementstring2, userelementstring3, userelementstring4,
        userelementstring5, userelementstring6, userelementstring7
    )
    SELECT
        NEXTVAL('c_acctschema_element_seq'), isactive, p_Date, 99, p_Date, p_AD_Org_ID, 99,
        p_New_AcctSchema_ID, elementtype, name, seqno, c_element_id, ad_client_id, ismandatory,
        isbalanced, p_AD_Org_ID, c_elementvalue_id, m_product_id, c_bpartner_id, c_location_id,
        c_salesregion_id, c_project_id, c_campaign_id, c_activity_id, ad_column_id, isdisplayineditor,
        userelementstring1, userelementstring2, userelementstring3, userelementstring4,
        userelementstring5, userelementstring6, userelementstring7
    FROM c_acctschema_element
    WHERE c_acctschema_id = p_Source_AcctSchema_ID;

    -- 3. Copy Default Accounts
    INSERT INTO c_acctschema_default (
        c_acctschema_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
        w_inventory_acct, w_invactualadjust_acct, w_differences_acct, w_revaluation_acct,
        p_revenue_acct, p_expense_acct, p_asset_acct, p_purchasepricevariance_acct,
        p_invoicepricevariance_acct, p_tradediscountrec_acct, p_tradediscountgrant_acct,
        p_cogs_acct, c_receivable_acct, c_prepayment_acct, v_liability_acct,
        v_liability_services_acct, v_prepayment_acct, paydiscount_exp_acct, writeoff_acct,
        paydiscount_rev_acct, unrealizedgain_acct, unrealizedloss_acct, realizedgain_acct,
        realizedloss_acct, withholding_acct, e_prepayment_acct, e_expense_acct, pj_asset_acct,
        pj_wip_acct, t_expense_acct, t_liability_acct, t_receivables_acct, t_due_acct,
        t_credit_acct, b_intransit_acct, b_asset_acct, b_expense_acct, b_interestrev_acct,
        b_interestexp_acct, b_unidentified_acct, b_unallocatedcash_acct, b_paymentselect_acct,
        b_settlementgain_acct, b_settlementloss_acct, b_revaluationgain_acct, b_revaluationloss_acct,
        ch_expense_acct, ch_revenue_acct, unearnedrevenue_acct, notinvoicedreceivables_acct,
        notinvoicedrevenue_acct, notinvoicedreceipts_acct, cb_asset_acct, cb_cashtransfer_acct,
        cb_differences_acct, cb_expense_acct, cb_receipt_acct, processing, c_receivable_services_acct,
        p_inventoryclearing_acct, p_costadjustment_acct, p_wip_acct, p_methodchangevariance_acct,
        p_usagevariance_acct, p_ratevariance_acct, p_mixvariance_acct, p_floorstock_acct,
        p_costofproduction_acct, p_labor_acct, p_burden_acct, p_outsideprocessing_acct,
        p_overhead_acct, p_scrap_acct, t_revenue_acct, t_paydiscount_rev_acct, t_paydiscount_exp_acct,
        c_acctschema_default_id, paybankfee_acct, p_costclearing_acct
    )
    SELECT
        p_New_AcctSchema_ID, ad_client_id, p_AD_Org_ID, isactive, p_Date, 99, p_Date, 99,
        w_inventory_acct, w_invactualadjust_acct, w_differences_acct, w_revaluation_acct,
        p_revenue_acct, p_expense_acct, p_asset_acct, p_purchasepricevariance_acct,
        p_invoicepricevariance_acct, p_tradediscountrec_acct, p_tradediscountgrant_acct,
        p_cogs_acct, c_receivable_acct, c_prepayment_acct, v_liability_acct,
        v_liability_services_acct, v_prepayment_acct, paydiscount_exp_acct, writeoff_acct,
        paydiscount_rev_acct, unrealizedgain_acct, unrealizedloss_acct, realizedgain_acct,
        realizedloss_acct, withholding_acct, e_prepayment_acct, e_expense_acct, pj_asset_acct,
        pj_wip_acct, t_expense_acct, t_liability_acct, t_receivables_acct, t_due_acct,
        t_credit_acct, b_intransit_acct, b_asset_acct, b_expense_acct, b_interestrev_acct,
        b_interestexp_acct, b_unidentified_acct, b_unallocatedcash_acct, b_paymentselect_acct,
        b_settlementgain_acct, b_settlementloss_acct, b_revaluationgain_acct, b_revaluationloss_acct,
        ch_expense_acct, ch_revenue_acct, unearnedrevenue_acct, notinvoicedreceivables_acct,
        notinvoicedrevenue_acct, notinvoicedreceipts_acct, cb_asset_acct, cb_cashtransfer_acct,
        cb_differences_acct, cb_expense_acct, cb_receipt_acct, processing, c_receivable_services_acct,
        p_inventoryclearing_acct, p_costadjustment_acct, p_wip_acct, p_methodchangevariance_acct,
        p_usagevariance_acct, p_ratevariance_acct, p_mixvariance_acct, p_floorstock_acct,
        p_costofproduction_acct, p_labor_acct, p_burden_acct, p_outsideprocessing_acct,
        p_overhead_acct, p_scrap_acct, t_revenue_acct, t_paydiscount_rev_acct, t_paydiscount_exp_acct,
        NEXTVAL('c_acctschema_default_seq'), paybankfee_acct, p_costclearing_acct
    FROM c_acctschema_default
    WHERE c_acctschema_id = p_Source_AcctSchema_ID;

    -- 4. Copy GL Accounts
    INSERT INTO c_acctschema_gl (
        c_acctschema_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
        usesuspensebalancing, suspensebalancing_acct, usesuspenseerror, suspenseerror_acct,
        usecurrencybalancing, currencybalancing_acct, retainedearning_acct, incomesummary_acct,
        intercompanydueto_acct, intercompanyduefrom_acct, ppvoffset_acct, commitmentoffset_acct,
        commitmentoffsetsales_acct, c_acctschema_gl_id, cashrounding_acct
    )
    SELECT
        p_New_AcctSchema_ID, ad_client_id, p_AD_Org_ID, isactive, p_Date, 99, p_Date, 99,
        usesuspensebalancing, suspensebalancing_acct, usesuspenseerror, suspenseerror_acct,
        usecurrencybalancing, currencybalancing_acct, retainedearning_acct, incomesummary_acct,
        intercompanydueto_acct, intercompanyduefrom_acct, ppvoffset_acct, commitmentoffset_acct,
        commitmentoffsetsales_acct, NEXTVAL('c_acctschema_gl_seq'), cashrounding_acct
    FROM c_acctschema_gl
    WHERE c_acctschema_id = p_Source_AcctSchema_ID;

    -- 5. Copy VAT Codes
    DELETE FROM c_vat_code WHERE c_acctschema_id = p_New_AcctSchema_ID;

    INSERT INTO c_vat_code (
        ad_client_id, ad_org_id, c_acctschema_id, created, createdby, c_tax_id,
        c_vat_code_id, description, isactive, issotrx, updated, updatedby,
        validfrom, validto, vatcode
    )
    SELECT
        ad_client_id, p_AD_Org_ID, p_New_AcctSchema_ID, p_Date, 99, c_tax_id,
        NEXTVAL('c_vat_code_seq'), description, isactive, issotrx, p_Date, 99,
        validfrom, validto, vatcode
    FROM c_vat_code
    WHERE c_acctschema_id = p_Source_AcctSchema_ID;

END;
$$ LANGUAGE plpgsql;