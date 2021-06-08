
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN (
                                                                           'A_Asset_Acct',
                                                                           'A_Asset_Group_Acct',
                                                                           'C_BP_BankAccount_Acct',
                                                                           'C_BP_Customer_Acct',
                                                                           'C_BP_Employee_Acct',
                                                                           'C_BP_Group_Acct',
                                                                           'C_BP_Vendor_Acct',
                                                                           'C_CashBook_Acct',
                                                                           'C_Charge_Acct',
                                                                           'C_Currency_Acct',
                                                                           'C_InterOrg_Acct',
                                                                           'C_Project_Acct',
                                                                           'C_Tax_Acct',
                                                                           'C_Withholding_Acct',
                                                                           'Fact_Acct',
                                                                           'HR_Concept_Acct',
                                                                           'M_Product_Acct',
                                                                           'M_Product_Category_Acct',
                                                                           'M_Warehouse_Acct',
                                                                           'RV_Fact_Acct'   ))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname = 'StackTrace'
  AND ad_table_id = get_table_id('AD_Issue')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id
                      FROM ad_table
                      WHERE tableName LIKE 'AD_%'
                        AND tablename NOT IN ('AD_Note', 'AD_Session', 'AD_PInstance_Log', 'AD_ChangeLog', 'AD_Replication_Log', 'AD_SchedulerLog', 'AD_Issue', 'AD_EventLog', 'AD_EventLog_Entry', 'AD_Org', 'AD_OrgInfo', 'AD_User', 'AD_User_Login'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_Country')
;
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_CountryArea')
;
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_CountryArea_Assign')
;
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_Country_Sequence')
;
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_Country_Trl')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('C_Currency_Trl','C_Currency_Acct','C_Currency'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (select ad_table_id from ad_table where tablename in (
                                                                           'C_DocType',
                                                                           'C_DocType_Trl',
                                                                           'C_DocTypeCounter',
                                                                           'C_DocLine_Sort',
                                                                           'C_Doc_Responsible',
                                                                           'C_DocLine_Sort_Item',
                                                                           'C_DocBaseType_Counter',
                                                                           'C_DocType_Sequence',
                                                                           'C_Doc_Outbound_Log',
                                                                           'C_Doc_Outbound_Log_Line',
                                                                           'C_Doc_Outbound_Config',
                                                                           'C_DocType_PrintOptions'
    ));
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_Element')
;
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_ElementValue')
;
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_ElementValue_Trl')
;
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_Region')
;
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('C_Tax', 'C_Tax_Trl', 'C_TaxCategory', 'C_TaxCategory_Trl'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN (
                                                                           'M_AttributeSetInstance',
                                                                           'M_AttributeInstance',
                                                                           'M_AttributeSearch',
                                                                           'M_AttributeSet',
                                                                           'M_AttributeValue',
                                                                           'M_AttributeSetExclude',
                                                                           'M_AttributeSetExcludeLine',
                                                                           'M_Attribute',
                                                                           'M_AttributeValue_Mapping',
                                                                           'M_AttributeUse'
    ))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('M_Product','M_Product_Trl','M_Product_Category','M_Product_Category_Trl','M_Product_BOM','M_Product_Nutrition','M_Product_Allergen','M_Product_Certificate','M_Product_Ingredients','M_Product_Allergen_Trace'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN (
                                                                           'X_TableTemplate',
                                                                           'X_EventStoreTemplate',
                                                                           'X_ImportTableTemplate',
                                                                           'X_AcctDocTableTemplate'
    ))
;

