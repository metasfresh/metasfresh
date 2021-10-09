-- 2020-11-20T10:14:25.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=220
;

-- 2020-11-20T10:15:00.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:15:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3887
;

-- 2020-11-20T10:16:31.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554162
;

-- 2020-11-20T10:17:00.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:17:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550690
;

-- 2020-11-20T10:17:22.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550689
;

-- 2020-11-20T10:17:41.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:17:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550688
;

-- 2020-11-20T10:19:03.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:19:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548290
;

-- 2020-11-20T10:19:24.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549002
;

-- 2020-11-20T10:19:44.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:19:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549004
;

-- 2020-11-20T10:20:01.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:20:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549003
;

-- 2020-11-20T10:22:53.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:22:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11264
;

-- 2020-11-20T10:23:20.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:23:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11257
;

-- 2020-11-20T10:23:56.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2020-11-20 12:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545143
;

update ad_table set ischangelog = 'Y',Updated=TO_TIMESTAMP('2020-11-20 12:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 where tablename in (
                                                          'AD_BoilerPlate',
                                                          'AD_BoilerPlate_Trl',
                                                          'AD_Client',
                                                          'AD_ClientInfo',
                                                          'AD_Column',
                                                          'AD_ColumnCallout',
                                                          'AD_Element',
                                                          'AD_Element_Link',
                                                          'AD_Element_Trl',
                                                          'AD_EntityType',
                                                          'AD_Field',
                                                          'AD_Field_ContextMenu',
                                                          'AD_Field_Trl',
                                                          'AD_FieldGroup',
                                                          'AD_FieldGroup_Trl',
                                                          'AD_LabelPrinter',
                                                          'AD_LabelPrinterFunction',
                                                          'AD_Language',
                                                          'AD_Menu',
                                                          'AD_Menu_Trl',
                                                          'AD_MigrationScript',
                                                          'AD_Org',
                                                          'AD_OrgInfo',
                                                          'AD_Printer',
                                                          'AD_Printer_Tray',
                                                          'AD_PrinterHW',
                                                          'AD_PrinterHW_Calibration',
                                                          'AD_PrinterHW_MediaSize',
                                                          'AD_PrinterHW_MediaTray',
                                                          'AD_PrinterRouting',
                                                          'AD_PrintFormatItem',
                                                          'AD_Process',
                                                          'AD_Ref_List',
                                                          'AD_Ref_List_Trl',
                                                          'AD_Ref_Table',
                                                          'AD_Reference',
                                                          'AD_Reference_Trl',
                                                          'AD_Relation',
                                                          'AD_RelationType',
                                                          'AD_Role',
                                                          'AD_Role_Included',
                                                          'AD_Role_OrgAccess',
                                                          'AD_Role_PermRequest',
                                                          'AD_Role_Record_Access_Config',
                                                          'AD_Scheduler',
                                                          'AD_Scheduler_Para',
                                                          'AD_System',
                                                          'AD_Tab',
                                                          'AD_Tab_Callout',
                                                          'AD_Tab_Trl',
                                                          'AD_Table',
                                                          'AD_Table_Access',
                                                          'AD_Table_Process',
                                                          'AD_Table_ScriptValidator',
                                                          'AD_Table_Trl',
                                                          'AD_Tree',
                                                          'AD_TreeBar',
                                                          'AD_TreeNode',
                                                          'AD_TreeNodeBP',
                                                          'AD_TreeNodeCMC',
                                                          'AD_TreeNodeCMM',
                                                          'AD_TreeNodeCMS',
                                                          'AD_TreeNodeCMT',
                                                          'AD_TreeNodeMM',
                                                          'AD_TreeNodePR',
                                                          'AD_TreeNodeU1',
                                                          'AD_TreeNodeU2',
                                                          'AD_TreeNodeU3',
                                                          'AD_TreeNodeU4',
                                                          'AD_User_Roles',
                                                          'AD_UserQuery',
                                                          'AD_Val_Rule',
                                                          'AD_Window',
                                                          'AD_Window_Access',
                                                          'AD_Window_Trl',
                                                          'C_AcctSchema',
                                                          'C_AcctSchema_Default',
                                                          'C_AcctSchema_Element',
                                                          'C_AcctSchema_GL',
                                                          'C_Activity',
                                                          'C_AllocationHdr',
                                                          'C_AllocationLine',
                                                          'C_Async_Batch',
                                                          'C_Async_Batch_Type',
                                                          'C_Bank',
                                                          'C_BankAccount',
                                                          'C_BankStatement',
                                                          'C_BankStatementLine',
                                                          'C_BankStatementLine_Ref',
                                                          'C_BP_BankAccount_Acct',
                                                          'C_BP_Customer_Acct',
                                                          'C_BP_DocLine_Sort',
                                                          'C_BP_EDI',
                                                          'C_BP_Employee_Acct',
                                                          'C_BP_Group',
                                                          'C_BP_Group_Acct',
                                                          'C_BPartner_Allotment',
                                                          'C_BPartner_Attribute',
                                                          'C_Country',
                                                          'C_Country_Trl',
                                                          'C_Currency',
                                                          'C_Customs_Invoice',
                                                          'C_Customs_Invoice_Line',
                                                          'C_Dunning',
                                                          'C_DunningDoc',
                                                          'C_DunningDoc_Line',
                                                          'C_DunningDoc_Line_Source',
                                                          'C_DunningLevel',
                                                          'C_DunningLevel_Trl',
                                                          'C_Greeting',
                                                          'C_Greeting_Trl',
                                                          'C_OrderTax',
                                                          'C_Payment',
                                                          'C_Payment_Request',
                                                          'C_Payment_Reservation',
                                                          'C_Payment_Reservation_Capture',
                                                          'C_PaymentAllocate',
                                                          'C_PaymentBatch',
                                                          'C_PaymentProcessor',
                                                          'C_PaymentTerm',
                                                          'C_PaymentTerm_Trl',
                                                          'C_PaySelection',
                                                          'C_Period',
                                                          'C_Period_Trl',
                                                          'C_PeriodControl',
                                                          'C_Phase',
                                                          'C_Phonecall_Schedule',
                                                          'C_Phonecall_Schema',
                                                          'C_Phonecall_Schema_Version',
                                                          'C_Phonecall_Schema_Version_Line',
                                                          'C_Postal',
                                                          'C_PricingRule',
                                                          'C_Project',
                                                          'C_Project_Acct',
                                                          'C_Project_User',
                                                          'C_PurchaseCandidate',
                                                          'C_RfQ',
                                                          'C_RfQLine',
                                                          'C_RfQLineQty',
                                                          'C_RfQResponse',
                                                          'C_RfQResponseLine',
                                                          'C_RfQResponseLineQty',
                                                          'C_Tax',
                                                          'C_Tax_Trl',
                                                          'C_TaxBase',
                                                          'C_TaxCategory',
                                                          'C_TaxCategory_Trl',
                                                          'C_UOM',
                                                          'C_UOM_Conversion',
                                                          'C_UOM_Trl',
                                                          'C_Year',
                                                          'DD_Order',
                                                          'DD_OrderLine',
                                                          'ESR_Import',
                                                          'ESR_ImportLine',
                                                          'EXP_Format',
                                                          'EXP_FormatLine',
                                                          'Fact_Acct',
                                                          'Fact_Acct_ActivityChangeRequest',
                                                          'Fact_Acct_ActivityChangeRequest_Source_v',
                                                          'Fact_Acct_Balance',
                                                          'Fact_Acct_EndingBalance',
                                                          'Fact_Acct_Log',
                                                          'Fact_Acct_Summary',
                                                          'M_Allergen_Trace',
                                                          'M_Allergen_Trace_Trl', 'AD_Sequence', 'C_Queue_WorkPackage', 'M_Product_Allergen_Trace');

update ad_table set ischangelog = 'N',Updated=TO_TIMESTAMP('2020-11-20 12:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 where tablename in ('AD_EventLog', 'C_Queue_WorkPackage_Param');

