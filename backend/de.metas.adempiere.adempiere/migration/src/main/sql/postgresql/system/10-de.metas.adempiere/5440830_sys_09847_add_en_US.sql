
-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Language SET IsActive='Y', IsSystemLanguage='Y',Updated=TO_TIMESTAMP('2016-02-26 09:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language_ID=192
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_BoilerPlate_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_BoilerPlate_ID, TextSnippext)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_BoilerPlate_ID, t.TextSnippext
 FROM AD_BoilerPlate t LEFT JOIN AD_BoilerPlate_Trl trl            ON trl.AD_BoilerPlate_ID = t.AD_BoilerPlate_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_BoilerPlate_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Column_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Column_ID, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Column_ID, t.Name
 FROM AD_Column t LEFT JOIN AD_Column_Trl trl            ON trl.AD_Column_ID = t.AD_Column_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Column_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Desktop_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Desktop_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Desktop_ID, t.Description, t.Help, t.Name
 FROM AD_Desktop t LEFT JOIN AD_Desktop_Trl trl            ON trl.AD_Desktop_ID = t.AD_Desktop_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Desktop_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Element_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName
 FROM AD_Element t LEFT JOIN AD_Element_Trl trl            ON trl.AD_Element_ID = t.AD_Element_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Element_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Field_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Field_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Field_ID, t.Description, t.Help, t.Name
 FROM AD_Field t LEFT JOIN AD_Field_Trl trl            ON trl.AD_Field_ID = t.AD_Field_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Field_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_FieldGroup_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_FieldGroup_ID, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_FieldGroup_ID, t.Name
 FROM AD_FieldGroup t LEFT JOIN AD_FieldGroup_Trl trl            ON trl.AD_FieldGroup_ID = t.AD_FieldGroup_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_FieldGroup_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Form_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Form_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Form_ID, t.Description, t.Help, t.Name
 FROM AD_Form t LEFT JOIN AD_Form_Trl trl            ON trl.AD_Form_ID = t.AD_Form_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Form_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Index_Table_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Index_Table_ID, ErrorMsg)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Index_Table_ID, t.ErrorMsg
 FROM AD_Index_Table t LEFT JOIN AD_Index_Table_Trl trl            ON trl.AD_Index_Table_ID = t.AD_Index_Table_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Index_Table_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_InfoColumn_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_InfoColumn_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_InfoColumn_ID, t.Description, t.Help, t.Name
 FROM AD_InfoColumn t LEFT JOIN AD_InfoColumn_Trl trl            ON trl.AD_InfoColumn_ID = t.AD_InfoColumn_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_InfoColumn_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_InfoWindow_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_InfoWindow_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_InfoWindow_ID, t.Description, t.Help, t.Name
 FROM AD_InfoWindow t LEFT JOIN AD_InfoWindow_Trl trl            ON trl.AD_InfoWindow_ID = t.AD_InfoWindow_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_InfoWindow_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Menu_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Menu_ID, Description, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Menu_ID, t.Description, t.Name
 FROM AD_Menu t LEFT JOIN AD_Menu_Trl trl            ON trl.AD_Menu_ID = t.AD_Menu_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Menu_ID IS NULL
;

-- 26.02.2016 09:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Message_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Message_ID, MsgText, MsgTip)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Message_ID, t.MsgText, t.MsgTip
 FROM AD_Message t LEFT JOIN AD_Message_Trl trl            ON trl.AD_Message_ID = t.AD_Message_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Message_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_PrintFormatItem_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_PrintFormatItem_ID, PrintName, PrintNameSuffix)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_PrintFormatItem_ID, t.PrintName, t.PrintNameSuffix
 FROM AD_PrintFormatItem t LEFT JOIN AD_PrintFormatItem_Trl trl            ON trl.AD_PrintFormatItem_ID = t.AD_PrintFormatItem_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_PrintFormatItem_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_PrintLabelLine_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_PrintLabelLine_ID, PrintName)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_PrintLabelLine_ID, t.PrintName
 FROM AD_PrintLabelLine t LEFT JOIN AD_PrintLabelLine_Trl trl            ON trl.AD_PrintLabelLine_ID = t.AD_PrintLabelLine_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_PrintLabelLine_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Process_Para_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Process_Para_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Process_Para_ID, t.Description, t.Help, t.Name
 FROM AD_Process_Para t LEFT JOIN AD_Process_Para_Trl trl            ON trl.AD_Process_Para_ID = t.AD_Process_Para_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Process_Para_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Process_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Process_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Process_ID, t.Description, t.Help, t.Name
 FROM AD_Process t LEFT JOIN AD_Process_Trl trl            ON trl.AD_Process_ID = t.AD_Process_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Process_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Ref_List_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Ref_List_ID, Description, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Ref_List_ID, t.Description, t.Name
 FROM AD_Ref_List t LEFT JOIN AD_Ref_List_Trl trl            ON trl.AD_Ref_List_ID = t.AD_Ref_List_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Ref_List_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Reference_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Reference_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Reference_ID, t.Description, t.Help, t.Name
 FROM AD_Reference t LEFT JOIN AD_Reference_Trl trl            ON trl.AD_Reference_ID = t.AD_Reference_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Reference_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Tab_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Tab_ID, CommitWarning, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name
 FROM AD_Tab t LEFT JOIN AD_Tab_Trl trl            ON trl.AD_Tab_ID = t.AD_Tab_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Tab_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Table_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Table_ID, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Table_ID, t.Name
 FROM AD_Table t LEFT JOIN AD_Table_Trl trl            ON trl.AD_Table_ID = t.AD_Table_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Table_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Task_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Task_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Task_ID, t.Description, t.Help, t.Name
 FROM AD_Task t LEFT JOIN AD_Task_Trl trl            ON trl.AD_Task_ID = t.AD_Task_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Task_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_WF_Node_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_WF_Node_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_WF_Node_ID, t.Description, t.Help, t.Name
 FROM AD_WF_Node t LEFT JOIN AD_WF_Node_Trl trl            ON trl.AD_WF_Node_ID = t.AD_WF_Node_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_WF_Node_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Window_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Window_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Window_ID, t.Description, t.Help, t.Name
 FROM AD_Window t LEFT JOIN AD_Window_Trl trl            ON trl.AD_Window_ID = t.AD_Window_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Window_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Workbench_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Workbench_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Workbench_ID, t.Description, t.Help, t.Name
 FROM AD_Workbench t LEFT JOIN AD_Workbench_Trl trl            ON trl.AD_Workbench_ID = t.AD_Workbench_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Workbench_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Workflow_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, AD_Workflow_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.AD_Workflow_ID, t.Description, t.Help, t.Name
 FROM AD_Workflow t LEFT JOIN AD_Workflow_Trl trl            ON trl.AD_Workflow_ID = t.AD_Workflow_ID			 AND trl.AD_Language='en_US'
 WHERE trl.AD_Workflow_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_Charge_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_Charge_ID, Description, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_Charge_ID, t.Description, t.Name
 FROM C_Charge t LEFT JOIN C_Charge_Trl trl            ON trl.C_Charge_ID = t.C_Charge_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_Charge_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_Country_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_Country_ID, Description, Name, RegionName)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_Country_ID, t.Description, t.Name, t.RegionName
 FROM C_Country t LEFT JOIN C_Country_Trl trl            ON trl.C_Country_ID = t.C_Country_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_Country_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_Currency_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_Currency_ID, CurSymbol, Description)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_Currency_ID, t.CurSymbol, t.Description
 FROM C_Currency t LEFT JOIN C_Currency_Trl trl            ON trl.C_Currency_ID = t.C_Currency_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_Currency_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_DocType_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_DocType_ID, DocumentNote, Name, PrintName)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_DocType_ID, t.DocumentNote, t.Name, t.PrintName
 FROM C_DocType t LEFT JOIN C_DocType_Trl trl            ON trl.C_DocType_ID = t.C_DocType_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_DocType_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_DunningLevel_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_DunningLevel_ID, Note, NoteHeader, PrintName)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_DunningLevel_ID, t.Note, t.NoteHeader, t.PrintName
 FROM C_DunningLevel t LEFT JOIN C_DunningLevel_Trl trl            ON trl.C_DunningLevel_ID = t.C_DunningLevel_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_DunningLevel_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_ElementValue_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_ElementValue_ID, Description, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_ElementValue_ID, t.Description, t.Name
 FROM C_ElementValue t LEFT JOIN C_ElementValue_Trl trl            ON trl.C_ElementValue_ID = t.C_ElementValue_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_ElementValue_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_Greeting_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_Greeting_ID, Greeting, Letter_Salutation, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_Greeting_ID, t.Greeting, t.Letter_Salutation, t.Name
 FROM C_Greeting t LEFT JOIN C_Greeting_Trl trl            ON trl.C_Greeting_ID = t.C_Greeting_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_Greeting_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_PaymentTerm_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_PaymentTerm_ID, Description, DocumentNote, Name, Name_Invoice)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_PaymentTerm_ID, t.Description, t.DocumentNote, t.Name, t.Name_Invoice
 FROM C_PaymentTerm t LEFT JOIN C_PaymentTerm_Trl trl            ON trl.C_PaymentTerm_ID = t.C_PaymentTerm_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_PaymentTerm_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_Tax_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_Tax_ID, Description, Name, TaxIndicator)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_Tax_ID, t.Description, t.Name, t.TaxIndicator
 FROM C_Tax t LEFT JOIN C_Tax_Trl trl            ON trl.C_Tax_ID = t.C_Tax_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_Tax_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_TaxCategory_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_TaxCategory_ID, Description, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_TaxCategory_ID, t.Description, t.Name
 FROM C_TaxCategory t LEFT JOIN C_TaxCategory_Trl trl            ON trl.C_TaxCategory_ID = t.C_TaxCategory_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_TaxCategory_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO C_UOM_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, C_UOM_ID, Description, Name, UOMSymbol)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.C_UOM_ID, t.Description, t.Name, t.UOMSymbol
 FROM C_UOM t LEFT JOIN C_UOM_Trl trl            ON trl.C_UOM_ID = t.C_UOM_ID			 AND trl.AD_Language='en_US'
 WHERE trl.C_UOM_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO CM_Container_Element_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, CM_Container_Element_ID, ContentHTML, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.CM_Container_Element_ID, t.ContentHTML, t.Description, t.Help, t.Name
 FROM CM_Container_Element t LEFT JOIN CM_Container_Element_Trl trl            ON trl.CM_Container_Element_ID = t.CM_Container_Element_ID			 AND trl.AD_Language='en_US'
 WHERE trl.CM_Container_Element_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO CM_Container_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, CM_Container_ID, ContainerXML, Meta_Description, Meta_Keywords, Name, StructureXML, Title)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.CM_Container_ID, t.ContainerXML, t.Meta_Description, t.Meta_Keywords, t.Name, t.StructureXML, t.Title
 FROM CM_Container t LEFT JOIN CM_Container_Trl trl            ON trl.CM_Container_ID = t.CM_Container_ID			 AND trl.AD_Language='en_US'
 WHERE trl.CM_Container_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO CM_CStage_Element_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, CM_CStage_Element_ID, ContentHTML, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.CM_CStage_Element_ID, t.ContentHTML, t.Description, t.Help, t.Name
 FROM CM_CStage_Element t LEFT JOIN CM_CStage_Element_Trl trl            ON trl.CM_CStage_Element_ID = t.CM_CStage_Element_ID			 AND trl.AD_Language='en_US'
 WHERE trl.CM_CStage_Element_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO CM_CStage_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, CM_CStage_ID, ContainerXML, Meta_Description, Meta_Keywords, Name, StructureXML, Title)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.CM_CStage_ID, t.ContainerXML, t.Meta_Description, t.Meta_Keywords, t.Name, t.StructureXML, t.Title
 FROM CM_CStage t LEFT JOIN CM_CStage_Trl trl            ON trl.CM_CStage_ID = t.CM_CStage_ID			 AND trl.AD_Language='en_US'
 WHERE trl.CM_CStage_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO M_Product_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, M_Product_ID, Description, DocumentNote, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.M_Product_ID, t.Description, t.DocumentNote, t.Name
 FROM M_Product t LEFT JOIN M_Product_Trl trl            ON trl.M_Product_ID = t.M_Product_ID			 AND trl.AD_Language='en_US'
 WHERE trl.M_Product_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO PP_Order_BOM_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, PP_Order_BOM_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.PP_Order_BOM_ID, t.Description, t.Help, t.Name
 FROM PP_Order_BOM t LEFT JOIN PP_Order_BOM_Trl trl            ON trl.PP_Order_BOM_ID = t.PP_Order_BOM_ID			 AND trl.AD_Language='en_US'
 WHERE trl.PP_Order_BOM_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO PP_Order_BOMLine_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, PP_Order_BOMLine_ID, Description, Help)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.PP_Order_BOMLine_ID, t.Description, t.Help
 FROM PP_Order_BOMLine t LEFT JOIN PP_Order_BOMLine_Trl trl            ON trl.PP_Order_BOMLine_ID = t.PP_Order_BOMLine_ID			 AND trl.AD_Language='en_US'
 WHERE trl.PP_Order_BOMLine_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO PP_Order_Node_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, PP_Order_Node_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.PP_Order_Node_ID, t.Description, t.Help, t.Name
 FROM PP_Order_Node t LEFT JOIN PP_Order_Node_Trl trl            ON trl.PP_Order_Node_ID = t.PP_Order_Node_ID			 AND trl.AD_Language='en_US'
 WHERE trl.PP_Order_Node_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO PP_Order_Workflow_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, PP_Order_Workflow_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.PP_Order_Workflow_ID, t.Description, t.Help, t.Name
 FROM PP_Order_Workflow t LEFT JOIN PP_Order_Workflow_Trl trl            ON trl.PP_Order_Workflow_ID = t.PP_Order_Workflow_ID			 AND trl.AD_Language='en_US'
 WHERE trl.PP_Order_Workflow_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO PP_Product_BOM_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, PP_Product_BOM_ID, Description, Help, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.PP_Product_BOM_ID, t.Description, t.Help, t.Name
 FROM PP_Product_BOM t LEFT JOIN PP_Product_BOM_Trl trl            ON trl.PP_Product_BOM_ID = t.PP_Product_BOM_ID			 AND trl.AD_Language='en_US'
 WHERE trl.PP_Product_BOM_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO PP_Product_BOMLine_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, PP_Product_BOMLine_ID, Description, Help)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.PP_Product_BOMLine_ID, t.Description, t.Help
 FROM PP_Product_BOMLine t LEFT JOIN PP_Product_BOMLine_Trl trl            ON trl.PP_Product_BOMLine_ID = t.PP_Product_BOMLine_ID			 AND trl.AD_Language='en_US'
 WHERE trl.PP_Product_BOMLine_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO R_MailText_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, R_MailText_ID, MailHeader, MailText, MailText2, MailText3, Name)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.R_MailText_ID, t.MailHeader, t.MailText, t.MailText2, t.MailText3, t.Name
 FROM R_MailText t LEFT JOIN R_MailText_Trl trl            ON trl.R_MailText_ID = t.R_MailText_ID			 AND trl.AD_Language='en_US'
 WHERE trl.R_MailText_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO W_MailMsg_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, W_MailMsg_ID, Message, Message2, Message3, Subject)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.W_MailMsg_ID, t.Message, t.Message2, t.Message3, t.Subject
 FROM W_MailMsg t LEFT JOIN W_MailMsg_Trl trl            ON trl.W_MailMsg_ID = t.W_MailMsg_ID			 AND trl.AD_Language='en_US'
 WHERE trl.W_MailMsg_ID IS NULL
;

-- 26.02.2016 09:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO W_Store_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Createdby, UpdatedBy, W_Store_ID, EMailFooter, EMailHeader, WebInfo, WebParam1, WebParam2, WebParam3, WebParam4, WebParam5, WebParam6)
 SELECT 'en_US', 'N', t.AD_Client_ID, t.AD_Org_ID, 100, 100, t.W_Store_ID, t.EMailFooter, t.EMailHeader, t.WebInfo, t.WebParam1, t.WebParam2, t.WebParam3, t.WebParam4, t.WebParam5, t.WebParam6
 FROM W_Store t LEFT JOIN W_Store_Trl trl            ON trl.W_Store_ID = t.W_Store_ID			 AND trl.AD_Language='en_US'
 WHERE trl.W_Store_ID IS NULL
;

