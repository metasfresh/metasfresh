UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id
                      FROM ad_table
                      WHERE tablename IN (
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
                                          'RV_Fact_Acct'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname = 'Name'
  AND ad_table_id = get_table_id('AD_Archive')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname = 'StackTrace'
  AND ad_table_id = get_table_id('AD_Issue')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname IN ('Value', 'Name')
  AND ad_table_id = get_table_id('AD_Org')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id
                      FROM ad_table
                      WHERE tableName LIKE 'AD_%'
                        AND tablename NOT IN ('AD_Note', 'AD_Session', 'AD_PInstance_Log', 'AD_ChangeLog', 'AD_Replication_Log', 'AD_SchedulerLog', 
                                              'AD_Issue', 'AD_EventLog', 'AD_EventLog_Entry', 'AD_Org', 'AD_OrgInfo', 'AD_User', 'AD_User_Login'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname in ('Classname', 'MsgText')
  AND ad_table_id = get_table_id('AD_EventLog_Entry')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname in ('ViewId', 'WhereClause', 'Reference')
  AND ad_table_id = get_table_id('AD_Note')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname in ('EventTopicName','Event_UUID')
  AND ad_table_id = get_table_id('AD_EventLog')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id
                      FROM ad_table
                      WHERE tablename IN (
                                          'C_AcctSchema',
                                          'C_AcctSchema_GL',
                                          'C_AcctSchema_Element',
                                          'C_AcctSchema_Default'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname IN ('ExternalId', 'Value')
  AND ad_table_id = get_table_id('C_BPartner')
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
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('C_Currency_Trl', 'C_Currency_Acct', 'C_Currency'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id
                      FROM ad_table
                      WHERE tablename IN (
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
                          ))
;

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
WHERE columnname = 'ExternalId'
  AND ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN (
'C_Order',
'C_Tax',
'C_BPartner',
'C_BPartner_Location',
'C_Invoice',
'M_InOut',
'C_PaymentTerm',
'M_InventoryLine',
'C_Payment',
'I_Product',
'C_BP_Relation',
'M_Inventory',
'AD_User',
'M_Warehouse',
'M_Product',
'AD_InputDataSource',
'I_DataEntry_Record',
'PayPal_Order',
'S_Milestone',
'S_FailedTimeBooking'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname = 'ExternalHeaderId'
  AND ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN (
'I_Inventory',
'C_Invoice_Candidate',
'C_PurchaseCandidate',
'C_OLCand'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname = 'ExternalLineId'
  AND ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN (
'C_OLCand',
'C_PurchaseCandidate',
'C_Invoice_Candidate',
'I_Inventory'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname = 'ExternalOrderId'
  AND ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN (
'C_Payment'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname = 'DocumentNo';

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname in ('ClassName','JavaClass');

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('EXP_Processor_Type')
;
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('IMP_Processor_Type')
;

UPDATE ad_column SET personaldatacategory='NP' WHERE columnname='PayProcessorClass' and ad_table_id = get_table_id('C_PaymentProcessor');
UPDATE ad_column SET personaldatacategory='NP' WHERE columnname='SourceClassName' and ad_table_id = get_table_id('AD_Issue');
UPDATE ad_column SET personaldatacategory='NP' WHERE columnname='SourceClassName' and ad_table_id = get_table_id('R_IssueKnown');
UPDATE ad_column SET personaldatacategory='NP' WHERE columnname='StmtLoaderClass' and ad_table_id = get_table_id('C_BankStatementLoader');
UPDATE ad_column SET personaldatacategory='NP' WHERE columnname='' and ad_table_id = get_table_id('IMP_Processor_Type');

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id
                      FROM ad_table
                      WHERE tablename IN (
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
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('M_Product', 'M_Product_Trl', 'M_Product_Category', 'M_Product_Category_Trl', 'M_Product_BOM', 'M_Product_Nutrition', 'M_Product_Allergen', 'M_Product_Certificate', 'M_Product_Ingredients', 'M_Product_Allergen_Trace'))
;


UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id
                      FROM ad_table
                      WHERE tablename IN ('M_PriceList',
                                          'M_PriceList_Version',
                                          'M_PricingSystem',
                                          'M_ProductPriceVendorBreak',
                                          'M_ProductPrice',
                                          'M_ProductScalePrice',
                                          'M_ProductPrice_Attribute_Line',
                                          'M_ProductPrice_Attribute'
                          ))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('M_Warehouse_Routing', 'M_Warehouse', 'M_Warehouse_PickingGroup', 'M_Warehouse_Acct', 'M_Warehouse_Type'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('C_Calendar', 'C_Period', 'C_PeriodControl', 'C_Period_Trl'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('C_UOM', 'C_UOM_Conversion', 'C_UOM_Trl'))
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('C_PaymentTerm'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('S_ResourceType', 'S_ResourceUnAvailable', 'S_ResourceAssignment', 'S_Resource'))
;


UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id
                      FROM ad_table
                      WHERE tablename IN ('X_TableTemplate',
                                          'X_EventStoreTemplate',
                                          'X_ImportTableTemplate',
                                          'X_AcctDocTableTemplate'
                          ))
;

UPDATE ad_column
SET personaldatacategory='NP', technicalnote='Set PersonaldataCategory=NP because the Password is already hashed and shall not be scrambled'
WHERE ad_table_id = get_table_id('AD_User')
  AND ColumnName = 'Password'
;

UPDATE ad_column
SET personaldatacategory='NP', technicalnote='Set PersonaldataCategory=NP because we need the login to be not-scrambled'
WHERE ad_table_id = get_table_id('AD_User')
  AND ColumnName = 'Login'
;

