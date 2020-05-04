-- -- 2018-10-17T11:08:41.848
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_BoilerPlate_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_BoilerPlate_ID, TextSnippet)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_BoilerPlate_ID, t.TextSnippet
 -- FROM AD_BoilerPlate t
 -- LEFT JOIN AD_BoilerPlate_Trl trl ON (trl.AD_BoilerPlate_ID = t.AD_BoilerPlate_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_BoilerPlate_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:41.862
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Column_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Column_ID, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Column_ID, t.Name
 -- FROM AD_Column t
 -- LEFT JOIN AD_Column_Trl trl ON (trl.AD_Column_ID = t.AD_Column_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Column_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:42.255
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Desktop_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Desktop_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Desktop_ID, t.Description, t.Help, t.Name
 -- FROM AD_Desktop t
 -- LEFT JOIN AD_Desktop_Trl trl ON (trl.AD_Desktop_ID = t.AD_Desktop_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Desktop_ID IS NULL
-- ;

-- 2018-10-17T11:08:42.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 INSERT INTO AD_Element_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName)
 SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName
 FROM AD_Element t
 LEFT JOIN AD_Element_Trl trl ON (trl.AD_Element_ID = t.AD_Element_ID AND trl.AD_Language='de_DE')
 WHERE trl.AD_Element_ID IS NULL
;

-- -- 2018-10-17T11:08:42.471
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Field_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Field_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Field_ID, t.Description, t.Help, t.Name
 -- FROM AD_Field t
 -- LEFT JOIN AD_Field_Trl trl ON (trl.AD_Field_ID = t.AD_Field_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Field_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:42.870
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_FieldGroup_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_FieldGroup_ID, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_FieldGroup_ID, t.Name
 -- FROM AD_FieldGroup t
 -- LEFT JOIN AD_FieldGroup_Trl trl ON (trl.AD_FieldGroup_ID = t.AD_FieldGroup_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_FieldGroup_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:42.877
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Form_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Form_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Form_ID, t.Description, t.Help, t.Name
 -- FROM AD_Form t
 -- LEFT JOIN AD_Form_Trl trl ON (trl.AD_Form_ID = t.AD_Form_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Form_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:42.886
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Index_Table_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Index_Table_ID, ErrorMsg)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Index_Table_ID, t.ErrorMsg
 -- FROM AD_Index_Table t
 -- LEFT JOIN AD_Index_Table_Trl trl ON (trl.AD_Index_Table_ID = t.AD_Index_Table_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Index_Table_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:42.905
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_InfoColumn_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_InfoColumn_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_InfoColumn_ID, t.Description, t.Help, t.Name
 -- FROM AD_InfoColumn t
 -- LEFT JOIN AD_InfoColumn_Trl trl ON (trl.AD_InfoColumn_ID = t.AD_InfoColumn_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_InfoColumn_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:42.925
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_InfoWindow_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_InfoWindow_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_InfoWindow_ID, t.Description, t.Help, t.Name
 -- FROM AD_InfoWindow t
 -- LEFT JOIN AD_InfoWindow_Trl trl ON (trl.AD_InfoWindow_ID = t.AD_InfoWindow_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_InfoWindow_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:42.932
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Menu_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb
 -- FROM AD_Menu t
 -- LEFT JOIN AD_Menu_Trl trl ON (trl.AD_Menu_ID = t.AD_Menu_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Menu_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:42.960
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Message_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Message_ID, MsgText, MsgTip)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Message_ID, t.MsgText, t.MsgTip
 -- FROM AD_Message t
 -- LEFT JOIN AD_Message_Trl trl ON (trl.AD_Message_ID = t.AD_Message_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Message_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.015
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_PrintFormatItem_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_PrintFormatItem_ID, PrintName, PrintNameSuffix)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_PrintFormatItem_ID, t.PrintName, t.PrintNameSuffix
 -- FROM AD_PrintFormatItem t
 -- LEFT JOIN AD_PrintFormatItem_Trl trl ON (trl.AD_PrintFormatItem_ID = t.AD_PrintFormatItem_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_PrintFormatItem_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.103
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_PrintLabelLine_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_PrintLabelLine_ID, PrintName)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_PrintLabelLine_ID, t.PrintName
 -- FROM AD_PrintLabelLine t
 -- LEFT JOIN AD_PrintLabelLine_Trl trl ON (trl.AD_PrintLabelLine_ID = t.AD_PrintLabelLine_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_PrintLabelLine_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.119
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Process_Para_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Process_Para_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Process_Para_ID, t.Description, t.Help, t.Name
 -- FROM AD_Process_Para t
 -- LEFT JOIN AD_Process_Para_Trl trl ON (trl.AD_Process_Para_ID = t.AD_Process_Para_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Process_Para_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.150
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Process_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Process_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Process_ID, t.Description, t.Help, t.Name
 -- FROM AD_Process t
 -- LEFT JOIN AD_Process_Trl trl ON (trl.AD_Process_ID = t.AD_Process_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Process_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.167
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Ref_List_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Ref_List_ID, Description, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Ref_List_ID, t.Description, t.Name
 -- FROM AD_Ref_List t
 -- LEFT JOIN AD_Ref_List_Trl trl ON (trl.AD_Ref_List_ID = t.AD_Ref_List_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Ref_List_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.208
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Reference_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Reference_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Reference_ID, t.Description, t.Help, t.Name
 -- FROM AD_Reference t
 -- LEFT JOIN AD_Reference_Trl trl ON (trl.AD_Reference_ID = t.AD_Reference_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Reference_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.230
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Tab_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Tab_ID, CommitWarning, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name
 -- FROM AD_Tab t
 -- LEFT JOIN AD_Tab_Trl trl ON (trl.AD_Tab_ID = t.AD_Tab_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Tab_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.263
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Table_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Table_ID, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Table_ID, t.Name
 -- FROM AD_Table t
 -- LEFT JOIN AD_Table_Trl trl ON (trl.AD_Table_ID = t.AD_Table_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Table_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.299
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Task_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Task_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Task_ID, t.Description, t.Help, t.Name
 -- FROM AD_Task t
 -- LEFT JOIN AD_Task_Trl trl ON (trl.AD_Task_ID = t.AD_Task_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Task_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.306
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_UI_Section_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_UI_Section_ID, Description, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_UI_Section_ID, t.Description, t.Name
 -- FROM AD_UI_Section t
 -- LEFT JOIN AD_UI_Section_Trl trl ON (trl.AD_UI_Section_ID = t.AD_UI_Section_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_UI_Section_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.343
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_WF_Node_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_WF_Node_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_WF_Node_ID, t.Description, t.Help, t.Name
 -- FROM AD_WF_Node t
 -- LEFT JOIN AD_WF_Node_Trl trl ON (trl.AD_WF_Node_ID = t.AD_WF_Node_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_WF_Node_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.355
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Window_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Window_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Window_ID, t.Description, t.Help, t.Name
 -- FROM AD_Window t
 -- LEFT JOIN AD_Window_Trl trl ON (trl.AD_Window_ID = t.AD_Window_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Window_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.373
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Workbench_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Workbench_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Workbench_ID, t.Description, t.Help, t.Name
 -- FROM AD_Workbench t
 -- LEFT JOIN AD_Workbench_Trl trl ON (trl.AD_Workbench_ID = t.AD_Workbench_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Workbench_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.378
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO AD_Workflow_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Workflow_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Workflow_ID, t.Description, t.Help, t.Name
 -- FROM AD_Workflow t
 -- LEFT JOIN AD_Workflow_Trl trl ON (trl.AD_Workflow_ID = t.AD_Workflow_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.AD_Workflow_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.384
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_BPartner_Product_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_BPartner_Product_ID, CustomerLabelName, Ingredients)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_BPartner_Product_ID, t.CustomerLabelName, t.Ingredients
 -- FROM C_BPartner_Product t
 -- LEFT JOIN C_BPartner_Product_Trl trl ON (trl.C_BPartner_Product_ID = t.C_BPartner_Product_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_BPartner_Product_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.412
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_Charge_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Charge_ID, Description, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Charge_ID, t.Description, t.Name
 -- FROM C_Charge t
 -- LEFT JOIN C_Charge_Trl trl ON (trl.C_Charge_ID = t.C_Charge_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_Charge_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.430
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_Country_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Country_ID, Description, Name, RegionName)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Country_ID, t.Description, t.Name, t.RegionName
 -- FROM C_Country t
 -- LEFT JOIN C_Country_Trl trl ON (trl.C_Country_ID = t.C_Country_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_Country_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.447
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_Currency_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Currency_ID, CurSymbol, Description)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Currency_ID, t.CurSymbol, t.Description
 -- FROM C_Currency t
 -- LEFT JOIN C_Currency_Trl trl ON (trl.C_Currency_ID = t.C_Currency_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_Currency_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.470
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_DocType_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_DocType_ID, Description, DocumentNote, Name, PrintName)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_DocType_ID, t.Description, t.DocumentNote, t.Name, t.PrintName
 -- FROM C_DocType t
 -- LEFT JOIN C_DocType_Trl trl ON (trl.C_DocType_ID = t.C_DocType_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_DocType_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.484
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_DunningLevel_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_DunningLevel_ID, Note, NoteHeader, PrintName)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_DunningLevel_ID, t.Note, t.NoteHeader, t.PrintName
 -- FROM C_DunningLevel t
 -- LEFT JOIN C_DunningLevel_Trl trl ON (trl.C_DunningLevel_ID = t.C_DunningLevel_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_DunningLevel_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.493
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_ElementValue_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_ElementValue_ID, Description, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_ElementValue_ID, t.Description, t.Name
 -- FROM C_ElementValue t
 -- LEFT JOIN C_ElementValue_Trl trl ON (trl.C_ElementValue_ID = t.C_ElementValue_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_ElementValue_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.538
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_Greeting_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Greeting_ID, Greeting, Letter_Salutation, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Greeting_ID, t.Greeting, t.Letter_Salutation, t.Name
 -- FROM C_Greeting t
 -- LEFT JOIN C_Greeting_Trl trl ON (trl.C_Greeting_ID = t.C_Greeting_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_Greeting_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.549
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_PaymentTerm_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_PaymentTerm_ID, Description, DocumentNote, Name, Name_Invoice)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_PaymentTerm_ID, t.Description, t.DocumentNote, t.Name, t.Name_Invoice
 -- FROM C_PaymentTerm t
 -- LEFT JOIN C_PaymentTerm_Trl trl ON (trl.C_PaymentTerm_ID = t.C_PaymentTerm_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_PaymentTerm_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.560
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_Period_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Period_ID, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Period_ID, t.Name
 -- FROM C_Period t
 -- LEFT JOIN C_Period_Trl trl ON (trl.C_Period_ID = t.C_Period_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_Period_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.590
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_Tax_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Tax_ID, Description, Name, TaxIndicator)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Tax_ID, t.Description, t.Name, t.TaxIndicator
 -- FROM C_Tax t
 -- LEFT JOIN C_Tax_Trl trl ON (trl.C_Tax_ID = t.C_Tax_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_Tax_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.610
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_TaxCategory_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_TaxCategory_ID, Description, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_TaxCategory_ID, t.Description, t.Name
 -- FROM C_TaxCategory t
 -- LEFT JOIN C_TaxCategory_Trl trl ON (trl.C_TaxCategory_ID = t.C_TaxCategory_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_TaxCategory_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.628
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO C_UOM_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_UOM_ID, Description, Name, UOMSymbol)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_UOM_ID, t.Description, t.Name, t.UOMSymbol
 -- FROM C_UOM t
 -- LEFT JOIN C_UOM_Trl trl ON (trl.C_UOM_ID = t.C_UOM_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.C_UOM_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.641
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO CM_Container_Element_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, CM_Container_Element_ID, ContentHTML, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.CM_Container_Element_ID, t.ContentHTML, t.Description, t.Help, t.Name
 -- FROM CM_Container_Element t
 -- LEFT JOIN CM_Container_Element_Trl trl ON (trl.CM_Container_Element_ID = t.CM_Container_Element_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.CM_Container_Element_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.654
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO CM_Container_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, CM_Container_ID, ContainerXML, Meta_Description, Meta_Keywords, Name, StructureXML, Title)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.CM_Container_ID, t.ContainerXML, t.Meta_Description, t.Meta_Keywords, t.Name, t.StructureXML, t.Title
 -- FROM CM_Container t
 -- LEFT JOIN CM_Container_Trl trl ON (trl.CM_Container_ID = t.CM_Container_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.CM_Container_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.666
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO CM_CStage_Element_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, CM_CStage_Element_ID, ContentHTML, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.CM_CStage_Element_ID, t.ContentHTML, t.Description, t.Help, t.Name
 -- FROM CM_CStage_Element t
 -- LEFT JOIN CM_CStage_Element_Trl trl ON (trl.CM_CStage_Element_ID = t.CM_CStage_Element_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.CM_CStage_Element_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.679
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO CM_CStage_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, CM_CStage_ID, ContainerXML, Meta_Description, Meta_Keywords, Name, StructureXML, Title)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.CM_CStage_ID, t.ContainerXML, t.Meta_Description, t.Meta_Keywords, t.Name, t.StructureXML, t.Title
 -- FROM CM_CStage t
 -- LEFT JOIN CM_CStage_Trl trl ON (trl.CM_CStage_ID = t.CM_CStage_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.CM_CStage_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.688
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO M_Allergen_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, M_Allergen_ID, Description, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.M_Allergen_ID, t.Description, t.Name
 -- FROM M_Allergen t
 -- LEFT JOIN M_Allergen_Trl trl ON (trl.M_Allergen_ID = t.M_Allergen_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.M_Allergen_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.696
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO M_Nutrition_Fact_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, M_Nutrition_Fact_ID, Description, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.M_Nutrition_Fact_ID, t.Description, t.Name
 -- FROM M_Nutrition_Fact t
 -- LEFT JOIN M_Nutrition_Fact_Trl trl ON (trl.M_Nutrition_Fact_ID = t.M_Nutrition_Fact_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.M_Nutrition_Fact_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.702
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO M_Product_Category_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, M_Product_Category_ID, Description, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.M_Product_Category_ID, t.Description, t.Name
 -- FROM M_Product_Category t
 -- LEFT JOIN M_Product_Category_Trl trl ON (trl.M_Product_Category_ID = t.M_Product_Category_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.M_Product_Category_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.716
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO M_Product_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, M_Product_ID, CustomerLabelName, Description, DocumentNote, Ingredients, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.M_Product_ID, t.CustomerLabelName, t.Description, t.DocumentNote, t.Ingredients, t.Name
 -- FROM M_Product t
 -- LEFT JOIN M_Product_Trl trl ON (trl.M_Product_ID = t.M_Product_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.M_Product_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.756
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO PP_Order_BOM_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Order_BOM_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Order_BOM_ID, t.Description, t.Help, t.Name
 -- FROM PP_Order_BOM t
 -- LEFT JOIN PP_Order_BOM_Trl trl ON (trl.PP_Order_BOM_ID = t.PP_Order_BOM_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.PP_Order_BOM_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.772
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO PP_Order_BOMLine_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Order_BOMLine_ID, Description, Help)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Order_BOMLine_ID, t.Description, t.Help
 -- FROM PP_Order_BOMLine t
 -- LEFT JOIN PP_Order_BOMLine_Trl trl ON (trl.PP_Order_BOMLine_ID = t.PP_Order_BOMLine_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.PP_Order_BOMLine_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.789
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO PP_Order_Node_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Order_Node_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Order_Node_ID, t.Description, t.Help, t.Name
 -- FROM PP_Order_Node t
 -- LEFT JOIN PP_Order_Node_Trl trl ON (trl.PP_Order_Node_ID = t.PP_Order_Node_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.PP_Order_Node_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.800
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO PP_Order_Workflow_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Order_Workflow_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Order_Workflow_ID, t.Description, t.Help, t.Name
 -- FROM PP_Order_Workflow t
 -- LEFT JOIN PP_Order_Workflow_Trl trl ON (trl.PP_Order_Workflow_ID = t.PP_Order_Workflow_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.PP_Order_Workflow_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.807
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO PP_Product_BOM_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Product_BOM_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Product_BOM_ID, t.Description, t.Help, t.Name
 -- FROM PP_Product_BOM t
 -- LEFT JOIN PP_Product_BOM_Trl trl ON (trl.PP_Product_BOM_ID = t.PP_Product_BOM_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.PP_Product_BOM_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.828
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO PP_Product_BOMLine_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Product_BOMLine_ID, Description, Help)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Product_BOMLine_ID, t.Description, t.Help
 -- FROM PP_Product_BOMLine t
 -- LEFT JOIN PP_Product_BOMLine_Trl trl ON (trl.PP_Product_BOMLine_ID = t.PP_Product_BOMLine_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.PP_Product_BOMLine_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.846
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO R_MailText_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, R_MailText_ID, MailHeader, MailText, MailText2, MailText3, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.R_MailText_ID, t.MailHeader, t.MailText, t.MailText2, t.MailText3, t.Name
 -- FROM R_MailText t
 -- LEFT JOIN R_MailText_Trl trl ON (trl.R_MailText_ID = t.R_MailText_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.R_MailText_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.868
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO R_RequestType_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, R_RequestType_ID, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.R_RequestType_ID, t.Name
 -- FROM R_RequestType t
 -- LEFT JOIN R_RequestType_Trl trl ON (trl.R_RequestType_ID = t.R_RequestType_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.R_RequestType_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.878
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO R_Status_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, R_Status_ID, Description, Help, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.R_Status_ID, t.Description, t.Help, t.Name
 -- FROM R_Status t
 -- LEFT JOIN R_Status_Trl trl ON (trl.R_Status_ID = t.R_Status_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.R_Status_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.888
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO W_MailMsg_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, W_MailMsg_ID, Message, Message2, Message3, Subject)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.W_MailMsg_ID, t.Message, t.Message2, t.Message3, t.Subject
 -- FROM W_MailMsg t
 -- LEFT JOIN W_MailMsg_Trl trl ON (trl.W_MailMsg_ID = t.W_MailMsg_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.W_MailMsg_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.898
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO W_Store_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, W_Store_ID, EMailFooter, EMailHeader, WebInfo, WebParam1, WebParam2, WebParam3, WebParam4, WebParam5, WebParam6)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.W_Store_ID, t.EMailFooter, t.EMailHeader, t.WebInfo, t.WebParam1, t.WebParam2, t.WebParam3, t.WebParam4, t.WebParam5, t.WebParam6
 -- FROM W_Store t
 -- LEFT JOIN W_Store_Trl trl ON (trl.W_Store_ID = t.W_Store_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.W_Store_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.904
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO WEBUI_DashboardItem_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, WEBUI_DashboardItem_ID, Name)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.WEBUI_DashboardItem_ID, t.Name
 -- FROM WEBUI_DashboardItem t
 -- LEFT JOIN WEBUI_DashboardItem_Trl trl ON (trl.WEBUI_DashboardItem_ID = t.WEBUI_DashboardItem_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.WEBUI_DashboardItem_ID IS NULL
-- ;

-- -- 2018-10-17T11:08:43.917
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
 -- INSERT INTO WEBUI_KPI_Field_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, WEBUI_KPI_Field_ID, Name, OffsetName)
 -- SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.WEBUI_KPI_Field_ID, t.Name, t.OffsetName
 -- FROM WEBUI_KPI_Field t
 -- LEFT JOIN WEBUI_KPI_Field_Trl trl ON (trl.WEBUI_KPI_Field_ID = t.WEBUI_KPI_Field_ID AND trl.AD_Language='de_DE')
 -- WHERE trl.WEBUI_KPI_Field_ID IS NULL
-- ;

