

-- View: rv_bp_changes

-- DROP VIEW rv_bp_changes;

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
		FROM ad_changelog ch
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
		FROM ad_changelog ch
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
	FROM ad_changelog ch
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
		
	FROM ad_changelog ch
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
	FROM ad_changelog ch
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
	FROM ad_changelog ch
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
	FROM ad_changelog ch
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
	FROM ad_changelog ch
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
	FROM ad_changelog ch
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
	,x.isCSponsorSalesRep;

------------------------








-- 09.02.2016 14:22
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542964,0,'isCBPartnerTable',TO_TIMESTAMP('2016-02-09 14:22:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.flatrate','Y','BPartner table change','BPartner table change',TO_TIMESTAMP('2016-02-09 14:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:22
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542964 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2016 14:23
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542965,0,'isAdUserTable',TO_TIMESTAMP('2016-02-09 14:23:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','AD_User table change','AD_User table change',TO_TIMESTAMP('2016-02-09 14:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:23
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542965 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2016 14:24
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542966,0,'isCBPartnerLocationTable',TO_TIMESTAMP('2016-02-09 14:24:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','C_BPartner_Location table change','C_BPartner_Location table change',TO_TIMESTAMP('2016-02-09 14:24:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:24
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542966 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2016 14:24
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542967,0,'isCBPBankAccountTable',TO_TIMESTAMP('2016-02-09 14:24:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','C_BP_BankAccount table change','C_BP_BankAccount table change',TO_TIMESTAMP('2016-02-09 14:24:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:24
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542967 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2016 14:25
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542968,0,'isC_BPCustomerAcctTable',TO_TIMESTAMP('2016-02-09 14:25:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','C_BP_Customer_Acct table change','C_BP_Customer_Acct table change',TO_TIMESTAMP('2016-02-09 14:25:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:25
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542968 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2016 14:25
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542969,0,'isCBPVendorAcctTable',TO_TIMESTAMP('2016-02-09 14:25:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','C_BP_Vendor_Acct table change','C_BP_Vendor_Acct table change',TO_TIMESTAMP('2016-02-09 14:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:25
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542969 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2016 14:25
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542970,0,'isCBPEmployeeAcctTable',TO_TIMESTAMP('2016-02-09 14:25:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','C_BP_Employee_Acct table change','C_BP_Employee_Acct table change',TO_TIMESTAMP('2016-02-09 14:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:25
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542970 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2016 14:27
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542964,0,540627,540876,20,'isCBPartnerTable',TO_TIMESTAMP('2016-02-09 14:27:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.flatrate',1,'Y','N','Y','N','Y','N','BPartner table change',30,TO_TIMESTAMP('2016-02-09 14:27:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:27
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540876 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.02.2016 14:27
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542965,0,540627,540877,20,'isAdUserTable',TO_TIMESTAMP('2016-02-09 14:27:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',1,'Y','N','Y','N','Y','N','AD_User table change',40,TO_TIMESTAMP('2016-02-09 14:27:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:27
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540877 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.02.2016 14:28
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542966,0,540627,540878,20,'isCBPartnerLocationTable',TO_TIMESTAMP('2016-02-09 14:28:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',1,'Y','N','Y','N','Y','N','C_BPartner_Location table change',50,TO_TIMESTAMP('2016-02-09 14:28:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:28
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540878 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;



-- 09.02.2016 14:28
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542967,0,540627,540880,20,'isCBPBankAccountTable',TO_TIMESTAMP('2016-02-09 14:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',1,'Y','N','Y','N','Y','N','C_BP_BankAccount table change',60,TO_TIMESTAMP('2016-02-09 14:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:28
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540880 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;


-- 09.02.2016 14:29
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542968,0,540627,540882,20,'isC_BPCustomerAcctTable',TO_TIMESTAMP('2016-02-09 14:29:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',1,'Y','N','Y','N','Y','N','C_BP_Customer_Acct table change',70,TO_TIMESTAMP('2016-02-09 14:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:29
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540882 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.02.2016 14:29
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542969,0,540627,540883,20,'isCBPVendorAcctTable',TO_TIMESTAMP('2016-02-09 14:29:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',1,'Y','N','Y','N','Y','N','C_BP_Vendor_Acct table change',80,TO_TIMESTAMP('2016-02-09 14:29:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:29
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540883 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.02.2016 14:29
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542970,0,540627,540884,20,'isCBPEmployeeAcctTable',TO_TIMESTAMP('2016-02-09 14:29:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',1,'Y','N','Y','N','Y','N','C_BP_Employee_Acct table change',90,TO_TIMESTAMP('2016-02-09 14:29:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 14:29
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540884 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.02.2016 18:11
-- URL zum Konzept
UPDATE AD_Table SET Name='Bankkonto',Updated=TO_TIMESTAMP('2016-02-09 18:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=298
;

-- 09.02.2016 18:11
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=298
;

-- 09.02.2016 18:12
-- URL zum Konzept
UPDATE AD_Table SET Name='Mitarbeiterkonten',Updated=TO_TIMESTAMP('2016-02-09 18:12:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=184
;

-- 09.02.2016 18:12
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=184
;

-- 09.02.2016 18:13
-- URL zum Konzept
UPDATE AD_Table SET Name='Lieferantenkonten',Updated=TO_TIMESTAMP('2016-02-09 18:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=185
;

-- 09.02.2016 18:13
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=185
;

-- 09.02.2016 18:13
-- URL zum Konzept
UPDATE AD_Table SET Name='Kundenkonten',Updated=TO_TIMESTAMP('2016-02-09 18:13:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=183
;

-- 09.02.2016 18:13
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=183
;

-- 09.02.2016 18:31
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='Geschäftspartner',Updated=TO_TIMESTAMP('2016-02-09 18:31:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540876
;

-- 09.02.2016 18:31
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=540876
;

-- 09.02.2016 18:31
-- URL zum Konzept
UPDATE AD_Element SET Name='Kontakt', PrintName='Kontakt',Updated=TO_TIMESTAMP('2016-02-09 18:31:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542965
;

-- 09.02.2016 18:31
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542965
;

-- 09.02.2016 18:31
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='isAdUserTable', Name='Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=542965
;

-- 09.02.2016 18:31
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isAdUserTable', Name='Kontakt', Description=NULL, Help=NULL, AD_Element_ID=542965 WHERE UPPER(ColumnName)='ISADUSERTABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 09.02.2016 18:31
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isAdUserTable', Name='Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=542965 AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:31
-- URL zum Konzept
UPDATE AD_Field SET Name='Kontakt', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542965) AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:31
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Kontakt', Name='Kontakt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542965)
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Element SET Name='Adresse', PrintName='Adresse',Updated=TO_TIMESTAMP('2016-02-09 18:32:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542966
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542966
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='isCBPartnerLocationTable', Name='Adresse', Description=NULL, Help=NULL WHERE AD_Element_ID=542966
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isCBPartnerLocationTable', Name='Adresse', Description=NULL, Help=NULL, AD_Element_ID=542966 WHERE UPPER(ColumnName)='ISCBPARTNERLOCATIONTABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isCBPartnerLocationTable', Name='Adresse', Description=NULL, Help=NULL WHERE AD_Element_ID=542966 AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Field SET Name='Adresse', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542966) AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Adresse', Name='Adresse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542966)
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Element SET Name='Bankkonto', PrintName='Bankkonto',Updated=TO_TIMESTAMP('2016-02-09 18:32:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542967
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542967
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='isCBPBankAccountTable', Name='Bankkonto', Description=NULL, Help=NULL WHERE AD_Element_ID=542967
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isCBPBankAccountTable', Name='Bankkonto', Description=NULL, Help=NULL, AD_Element_ID=542967 WHERE UPPER(ColumnName)='ISCBPBANKACCOUNTTABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isCBPBankAccountTable', Name='Bankkonto', Description=NULL, Help=NULL WHERE AD_Element_ID=542967 AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Field SET Name='Bankkonto', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542967) AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Bankkonto', Name='Bankkonto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542967)
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Element SET Name='Kundenkonten', PrintName='Kundenkonten',Updated=TO_TIMESTAMP('2016-02-09 18:32:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542968
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542968
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='isC_BPCustomerAcctTable', Name='Kundenkonten', Description=NULL, Help=NULL WHERE AD_Element_ID=542968
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isC_BPCustomerAcctTable', Name='Kundenkonten', Description=NULL, Help=NULL, AD_Element_ID=542968 WHERE UPPER(ColumnName)='ISC_BPCUSTOMERACCTTABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isC_BPCustomerAcctTable', Name='Kundenkonten', Description=NULL, Help=NULL WHERE AD_Element_ID=542968 AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_Field SET Name='Kundenkonten', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542968) AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:32
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Kundenkonten', Name='Kundenkonten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542968)
;

-- 09.02.2016 18:33
-- URL zum Konzept
UPDATE AD_Element SET Name='Lieferantenkonten', PrintName='Lieferantenkonten',Updated=TO_TIMESTAMP('2016-02-09 18:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542969
;

-- 09.02.2016 18:33
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542969
;

-- 09.02.2016 18:33
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='isCBPVendorAcctTable', Name='Lieferantenkonten', Description=NULL, Help=NULL WHERE AD_Element_ID=542969
;

-- 09.02.2016 18:33
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isCBPVendorAcctTable', Name='Lieferantenkonten', Description=NULL, Help=NULL, AD_Element_ID=542969 WHERE UPPER(ColumnName)='ISCBPVENDORACCTTABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 09.02.2016 18:33
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='isCBPVendorAcctTable', Name='Lieferantenkonten', Description=NULL, Help=NULL WHERE AD_Element_ID=542969 AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:33
-- URL zum Konzept
UPDATE AD_Field SET Name='Lieferantenkonten', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542969) AND IsCentrallyMaintained='Y'
;

-- 09.02.2016 18:33
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferantenkonten', Name='Lieferantenkonten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542969)
;

-- 09.02.2016 18:38
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542971,0,'isCBPartnerAllotmentTable',TO_TIMESTAMP('2016-02-09 18:38:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Geschäftspartnerparzelle','Geschäftspartnerparzelle',TO_TIMESTAMP('2016-02-09 18:38:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 18:38
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542971 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2016 18:38
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542971,0,540627,540885,20,'isCBPartnerAllotmentTable',TO_TIMESTAMP('2016-02-09 18:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',1,'Y','N','Y','N','Y','N','Geschäftspartnerparzelle',100,TO_TIMESTAMP('2016-02-09 18:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 18:38
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540885 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 09.02.2016 18:38
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542972,0,'isCSponsorSalesRep',TO_TIMESTAMP('2016-02-09 18:38:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Sponsor-Vertriebspartner','Sponsor-Vertriebspartner',TO_TIMESTAMP('2016-02-09 18:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 18:38
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542972 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2016 18:39
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542972,0,540627,540886,20,'isCSponsorSalesRep',TO_TIMESTAMP('2016-02-09 18:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',1,'Y','N','Y','N','Y','N','Sponsor-Vertriebspartner',110,TO_TIMESTAMP('2016-02-09 18:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2016 18:39
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540886 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

