

DROP VIEW IF EXISTS RV_BP_Changes;


CREATE OR REPLACE VIEW rv_bp_changes AS 
SELECT x.tablename
	,x.record_id
	,x.bpvalue
	,x.bpname
	,x.columnname
	,x.created
	,x.createdby
	,x.updated
	,x.updatedby
	,x.oldvalue
	,x.newvalue
	,x.isCBPartnerTable
	,x.isAdUserTable
	,x.isCBPartnerLocationTable
	,x.isCBPBankAccountTable
	,x.isC_BPCustomerAcctTable
	,x.isCBPVendorAcctTable
	,x.isCBPEmployeeAcctTable
	,x.isCBPartnerAllotmentTable
	,x.isCSponsorSalesRep
	,x.ad_org_id
FROM (
	(
		SELECT t.NAME AS tablename
			,ch.record_id
			,bp.value AS bpvalue
			,bp.NAME AS bpname
			,c.NAME AS columnname
			,ch.created::DATE AS created
			,ch.createdby
			,ch.updated::DATE AS updated
			,ch.updatedby
			,ch.oldvalue
			,ch.newvalue
			,'Y' as isCBPartnerTable
			,'N' as isAdUserTable
			,'N' as isCBPartnerLocationTable
			,'N' as isCBPBankAccountTable
			,'N' as isC_BPCustomerAcctTable
			,'N' as isCBPVendorAcctTable
			,'N' as isCBPEmployeeAcctTable
			,'N' as isCBPartnerAllotmentTable
			,'N' as isCSponsorSalesRep
			, s.ad_org_id
		FROM ad_changelog ch
		INNER JOIN ad_session s ON ch.ad_session_id = s.ad_session_id
		INNER JOIN ad_table t ON ch.ad_table_id = t.ad_table_id
			AND t.ad_table_id = get_table_id('C_BPartner'::VARCHAR)
		INNER JOIN ad_column c ON ch.ad_column_id = c.ad_column_id
		INNER JOIN c_bpartner bp ON ch.record_id = bp.c_bpartner_id
		
		UNION
		
		SELECT t.NAME AS tablename
			,ch.record_id
			,bp.value AS bpvalue
			,bp.NAME AS bpname
			,c.NAME AS columnname
			,ch.created::DATE AS created
			,ch.createdby
			,ch.updated::DATE AS updated
			,ch.updatedby
			,ch.oldvalue
			,ch.newvalue
			,'N' as isCBPartnerTable
			,'N' as isAdUserTable
			,'Y' as isCBPartnerLocationTable
			,'N' as isCBPBankAccountTable
			,'N' as isC_BPCustomerAcctTable
			,'N' as isCBPVendorAcctTable
			,'N' as isCBPEmployeeAcctTable
			,'N' as isCBPartnerAllotmentTable
			,'N' as isCSponsorSalesRep
			, s.ad_org_id
		FROM ad_changelog ch
		INNER JOIN ad_session s ON ch.ad_session_id = s.ad_session_id
		INNER JOIN ad_table t ON ch.ad_table_id = t.ad_table_id
			AND t.ad_table_id = get_table_id('C_BPartner_Location'::VARCHAR)
		INNER JOIN ad_column c ON ch.ad_column_id = c.ad_column_id
		INNER JOIN c_bpartner_location bpl ON ch.record_id = bpl.c_bpartner_location_id
		INNER JOIN c_bpartner bp ON bpl.c_bpartner_id = bp.c_bpartner_id
		)
	
	UNION
	
	SELECT t.NAME AS tablename
		,ch.record_id
		,bp.value AS bpvalue
		,bp.NAME AS bpname
		,c.NAME AS columnname
		,ch.created::DATE AS created
		,ch.createdby
		,ch.updated::DATE AS updated
		,ch.updatedby
		,ch.oldvalue
		,ch.newvalue		
		,'N' as isCBPartnerTable
		,'N' as isAdUserTable
		,'N' as isCBPartnerLocationTable
		,'Y' as isCBPBankAccountTable
		,'N' as isC_BPCustomerAcctTable
		,'N' as isCBPVendorAcctTable
		,'N' as isCBPEmployeeAcctTable
		,'N' as isCBPartnerAllotmentTable
		,'N' as isCSponsorSalesRep
		, s.ad_org_id
	FROM ad_changelog ch
	INNER JOIN ad_session s ON ch.ad_session_id = s.ad_session_id
	INNER JOIN ad_table t ON ch.ad_table_id = t.ad_table_id
		AND t.ad_table_id = get_table_id('C_BP_BankAccount'::VARCHAR)
	INNER JOIN ad_column c ON ch.ad_column_id = c.ad_column_id
	INNER JOIN c_bp_bankaccount bpa ON ch.record_id = bpa.c_bp_bankaccount_id
	INNER JOIN c_bpartner bp ON bpa.c_bpartner_id = bp.c_bpartner_id
	
	
	UNION
	
	SELECT t.NAME AS tablename
		,ch.record_id
		,bp.value AS bpvalue
		,bp.NAME AS bpname
		,c.NAME AS columnname
		,ch.created::DATE AS created
		,ch.createdby
		,ch.updated::DATE AS updated
		,ch.updatedby
		,ch.oldvalue
		,ch.newvalue
		,'N' as isCBPartnerTable
		,'Y' as isAdUserTable
		,'N' as isCBPartnerLocationTable
		,'N' as isCBPBankAccountTable
		,'N' as isC_BPCustomerAcctTable
		,'N' as isCBPVendorAcctTable
		,'N' as isCBPEmployeeAcctTable
		,'N' as isCBPartnerAllotmentTable
		,'N' as isCSponsorSalesRep
		, s.ad_org_id
	FROM ad_changelog ch
	INNER JOIN ad_session s ON ch.ad_session_id = s.ad_session_id
	INNER JOIN ad_table t ON ch.ad_table_id = t.ad_table_id
		AND t.ad_table_id = get_table_id('AD_User'::VARCHAR)
	INNER JOIN ad_column c ON ch.ad_column_id = c.ad_column_id
	INNER JOIN ad_user u ON ch.record_id = u.AD_User_ID
	INNER JOIN c_bpartner bp ON u.c_bpartner_id = bp.c_bpartner_id
	
	UNION
	
	SELECT t.NAME AS tablename
		,ch.record_id
		,bp.value AS bpvalue
		,bp.NAME AS bpname
		,c.NAME AS columnname
		,ch.created::DATE AS created
		,ch.createdby
		,ch.updated::DATE AS updated
		,ch.updatedby
		,ch.oldvalue
		,ch.newvalue
		,'N' as isCBPartnerTable
		,'N' as isAdUserTable
		,'N' as isCBPartnerLocationTable
		,'N' as isCBPBankAccountTable
		,'Y' as isC_BPCustomerAcctTable
		,'N' as isCBPVendorAcctTable
		,'N' as isCBPEmployeeAcctTable
		,'N' as isCBPartnerAllotmentTable
		,'N' as isCSponsorSalesRep
		, s.ad_org_id
	FROM ad_changelog ch
	INNER JOIN ad_session s ON ch.ad_session_id = s.ad_session_id
	INNER JOIN ad_table t ON ch.ad_table_id = t.ad_table_id
		AND t.ad_table_id = get_table_id('C_BP_Customer_Acct'::VARCHAR)
	INNER JOIN ad_column c ON ch.ad_column_id = c.ad_column_id
	INNER JOIN C_BP_Customer_Acct bpc ON ch.record_id = bpc.c_bpartner_id
	INNER JOIN c_bpartner bp ON bpc.c_bpartner_id = bp.c_bpartner_id
	
	UNION
	
	SELECT t.NAME AS tablename
		,ch.record_id
		,bp.value AS bpvalue
		,bp.NAME AS bpname
		,c.NAME AS columnname
		,ch.created::DATE AS created
		,ch.createdby
		,ch.updated::DATE AS updated
		,ch.updatedby
		,ch.oldvalue
		,ch.newvalue
		,'N' as isCBPartnerTable
		,'N' as isAdUserTable
		,'N' as isCBPartnerLocationTable
		,'N' as isCBPBankAccountTable
		,'N' as isC_BPCustomerAcctTable
		,'Y' as isCBPVendorAcctTable
		,'N' as isCBPEmployeeAcctTable
		,'N' as isCBPartnerAllotmentTable
		,'N' as isCSponsorSalesRep
		, s.ad_org_id
	FROM ad_changelog ch
	INNER JOIN ad_session s ON ch.ad_session_id = s.ad_session_id
	INNER JOIN ad_table t ON ch.ad_table_id = t.ad_table_id
	AND t.ad_table_id = get_table_id('C_BP_Vendor_Acct'::VARCHAR)
	INNER JOIN ad_column c ON ch.ad_column_id = c.ad_column_id
	INNER JOIN C_BP_Customer_Acct bpc ON ch.record_id = bpc.c_bpartner_id
	INNER JOIN c_bpartner bp ON bpc.c_bpartner_id = bp.c_bpartner_id
	
	UNION
	
	SELECT t.NAME AS tablename
		,ch.record_id
		,bp.value AS bpvalue
		,bp.NAME AS bpname
		,c.NAME AS columnname
		,ch.created::DATE AS created
		,ch.createdby
		,ch.updated::DATE AS updated
		,ch.updatedby
		,ch.oldvalue
		,ch.newvalue
		,'N' as isCBPartnerTable
		,'N' as isAdUserTable
		,'N' as isCBPartnerLocationTable
		,'N' as isCBPBankAccountTable
		,'N' as isC_BPCustomerAcctTable
		,'N' as isCBPVendorAcctTable
		,'Y' as isCBPEmployeeAcctTable
		,'N' as isCBPartnerAllotmentTable
		,'N' as isCSponsorSalesRep
		, s.ad_org_id
	FROM ad_changelog ch
	INNER JOIN ad_session s ON ch.ad_session_id = s.ad_session_id
	INNER JOIN ad_table t ON ch.ad_table_id = t.ad_table_id
	AND t.ad_table_id = get_table_id('C_BP_Employee_Acct'::VARCHAR)
	INNER JOIN ad_column c ON ch.ad_column_id = c.ad_column_id
	INNER JOIN C_BP_Customer_Acct bpc ON ch.record_id = bpc.c_bpartner_id
	INNER JOIN c_bpartner bp ON bpc.c_bpartner_id = bp.c_bpartner_id
	
	UNION
	
	SELECT t.NAME AS tablename
		,ch.record_id
		,bp.value AS bpvalue
		,bp.NAME AS bpname
		,c.NAME AS columnname
		,ch.created::DATE AS created
		,ch.createdby
		,ch.updated::DATE AS updated
		,ch.updatedby
		,ch.oldvalue
		,ch.newvalue
		,'N' as isCBPartnerTable
		,'N' as isAdUserTable
		,'N' as isCBPartnerLocationTable
		,'N' as isCBPBankAccountTable
		,'N' as isC_BPCustomerAcctTable
		,'N' as isCBPVendorAcctTable
		,'N' as isCBPEmployeeAcctTable
		,'Y' as isCBPartnerAllotmentTable
		,'N' as isCSponsorSalesRep
		, s.ad_org_id
	FROM ad_changelog ch
	INNER JOIN ad_session s ON ch.ad_session_id = s.ad_session_id
	INNER JOIN ad_table t ON ch.ad_table_id = t.ad_table_id
	AND t.ad_table_id = get_table_id('C_BPartner_Allotment'::VARCHAR)
	INNER JOIN ad_column c ON ch.ad_column_id = c.ad_column_id
	INNER JOIN C_BPartner_Allotment bpa ON ch.record_id = bpa.C_BPartner_Allotment_ID
	INNER JOIN c_bpartner bp ON bpa.c_bpartner_id = bp.c_bpartner_id
	
	UNION
	
	SELECT t.NAME AS tablename
		,ch.record_id
		,bp.value AS bpvalue
		,bp.NAME AS bpname
		,c.NAME AS columnname
		,ch.created::DATE AS created
		,ch.createdby
		,ch.updated::DATE AS updated
		,ch.updatedby
		,ch.oldvalue
		,ch.newvalue
		,'N' as isCBPartnerTable
		,'N' as isAdUserTable
		,'N' as isCBPartnerLocationTable
		,'N' as isCBPBankAccountTable
		,'N' as isC_BPCustomerAcctTable
		,'N' as isCBPVendorAcctTable
		,'N' as isCBPEmployeeAcctTable
		,'N' as isCBPartnerAllotmentTable
		,'Y' as isCSponsorSalesRep
		, s.ad_org_id
	FROM ad_changelog ch
	INNER JOIN ad_session s ON ch.ad_session_id = s.ad_session_id
	INNER JOIN ad_table t ON ch.ad_table_id = t.ad_table_id
	AND t.ad_table_id = get_table_id('C_Sponsor_SalesRep'::VARCHAR)
	INNER JOIN ad_column c ON ch.ad_column_id = c.ad_column_id
	INNER JOIN C_Sponsor_SalesRep ss ON ch.record_id = ss.C_Sponsor_SalesRep_ID
	INNER JOIN c_bpartner bp ON ss.c_bpartner_id = bp.c_bpartner_id
	
	) x
GROUP BY x.bpvalue
	,x.bpname
	,x.tablename
	,x.record_id
	,x.columnname
	,x.created
	,x.createdby
	,x.updated
	,x.updatedby
	,x.oldvalue
	,x.newvalue
	,x.isCBPartnerTable
	,x.isAdUserTable
	,x.isCBPartnerLocationTable
	,x.isCBPBankAccountTable
	,x.isC_BPCustomerAcctTable
	,x.isCBPVendorAcctTable
	,x.isCBPEmployeeAcctTable
	,x.isCBPartnerAllotmentTable
	,x.isCSponsorSalesRep
	,x.ad_org_id;
