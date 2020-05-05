-- 2017-04-12T17:06:36.257
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=2000,Updated=TO_TIMESTAMP('2017-04-12 17:06:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2853
;

-- 2017-04-12T17:06:39.593
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_process_trl','Description','VARCHAR(2000)',null,null)
;

-- 2017-04-12T17:06:48.955
-- URL zum Konzept
 INSERT INTO AD_InfoColumn_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_InfoColumn_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_InfoColumn_ID, t.Description, t.Help, t.Name
 FROM AD_InfoColumn t
 LEFT JOIN AD_InfoColumn_Trl trl ON (trl.AD_InfoColumn_ID = t.AD_InfoColumn_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_InfoColumn_ID IS NULL
;

-- 2017-04-12T17:06:48.958
-- URL zum Konzept
 INSERT INTO C_Currency_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Currency_ID, CurSymbol, Description)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Currency_ID, t.CurSymbol, t.Description
 FROM C_Currency t
 LEFT JOIN C_Currency_Trl trl ON (trl.C_Currency_ID = t.C_Currency_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_Currency_ID IS NULL
;

-- 2017-04-12T17:06:48.962
-- URL zum Konzept
 INSERT INTO PP_Order_BOM_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Order_BOM_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Order_BOM_ID, t.Description, t.Help, t.Name
 FROM PP_Order_BOM t
 LEFT JOIN PP_Order_BOM_Trl trl ON (trl.PP_Order_BOM_ID = t.PP_Order_BOM_ID AND trl.AD_Language='nl_NL')
 WHERE trl.PP_Order_BOM_ID IS NULL
;

-- 2017-04-12T17:06:48.964
-- URL zum Konzept
 INSERT INTO AD_Ref_List_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Ref_List_ID, Description, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Ref_List_ID, t.Description, t.Name
 FROM AD_Ref_List t
 LEFT JOIN AD_Ref_List_Trl trl ON (trl.AD_Ref_List_ID = t.AD_Ref_List_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Ref_List_ID IS NULL
;

-- 2017-04-12T17:06:48.984
-- URL zum Konzept
 INSERT INTO AD_Table_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Table_ID, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Table_ID, t.Name
 FROM AD_Table t
 LEFT JOIN AD_Table_Trl trl ON (trl.AD_Table_ID = t.AD_Table_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Table_ID IS NULL
;

-- 2017-04-12T17:06:48.996
-- URL zum Konzept
 INSERT INTO AD_Column_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Column_ID, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Column_ID, t.Name
 FROM AD_Column t
 LEFT JOIN AD_Column_Trl trl ON (trl.AD_Column_ID = t.AD_Column_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Column_ID IS NULL
;

-- 2017-04-12T17:06:49.234
-- URL zum Konzept
 INSERT INTO AD_WF_Node_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_WF_Node_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_WF_Node_ID, t.Description, t.Help, t.Name
 FROM AD_WF_Node t
 LEFT JOIN AD_WF_Node_Trl trl ON (trl.AD_WF_Node_ID = t.AD_WF_Node_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_WF_Node_ID IS NULL
;

-- 2017-04-12T17:06:49.239
-- URL zum Konzept
 INSERT INTO C_DunningLevel_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_DunningLevel_ID, Note, NoteHeader, PrintName)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_DunningLevel_ID, t.Note, t.NoteHeader, t.PrintName
 FROM C_DunningLevel t
 LEFT JOIN C_DunningLevel_Trl trl ON (trl.C_DunningLevel_ID = t.C_DunningLevel_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_DunningLevel_ID IS NULL
;

-- 2017-04-12T17:06:49.241
-- URL zum Konzept
 INSERT INTO C_Greeting_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Greeting_ID, Greeting, Letter_Salutation, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Greeting_ID, t.Greeting, t.Letter_Salutation, t.Name
 FROM C_Greeting t
 LEFT JOIN C_Greeting_Trl trl ON (trl.C_Greeting_ID = t.C_Greeting_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_Greeting_ID IS NULL
;

-- 2017-04-12T17:06:49.243
-- URL zum Konzept
 INSERT INTO AD_Index_Table_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Index_Table_ID, ErrorMsg)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Index_Table_ID, t.ErrorMsg
 FROM AD_Index_Table t
 LEFT JOIN AD_Index_Table_Trl trl ON (trl.AD_Index_Table_ID = t.AD_Index_Table_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Index_Table_ID IS NULL
;

-- 2017-04-12T17:06:49.246
-- URL zum Konzept
 INSERT INTO AD_PrintLabelLine_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_PrintLabelLine_ID, PrintName)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_PrintLabelLine_ID, t.PrintName
 FROM AD_PrintLabelLine t
 LEFT JOIN AD_PrintLabelLine_Trl trl ON (trl.AD_PrintLabelLine_ID = t.AD_PrintLabelLine_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_PrintLabelLine_ID IS NULL
;

-- 2017-04-12T17:06:49.248
-- URL zum Konzept
 INSERT INTO AD_Reference_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Reference_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Reference_ID, t.Description, t.Help, t.Name
 FROM AD_Reference t
 LEFT JOIN AD_Reference_Trl trl ON (trl.AD_Reference_ID = t.AD_Reference_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Reference_ID IS NULL
;

-- 2017-04-12T17:06:49.256
-- URL zum Konzept
 INSERT INTO R_MailText_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, R_MailText_ID, MailHeader, MailText, MailText2, MailText3, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.R_MailText_ID, t.MailHeader, t.MailText, t.MailText2, t.MailText3, t.Name
 FROM R_MailText t
 LEFT JOIN R_MailText_Trl trl ON (trl.R_MailText_ID = t.R_MailText_ID AND trl.AD_Language='nl_NL')
 WHERE trl.R_MailText_ID IS NULL
;

-- 2017-04-12T17:06:49.258
-- URL zum Konzept
 INSERT INTO C_Period_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Period_ID, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Period_ID, t.Name
 FROM C_Period t
 LEFT JOIN C_Period_Trl trl ON (trl.C_Period_ID = t.C_Period_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_Period_ID IS NULL
;

-- 2017-04-12T17:06:49.261
-- URL zum Konzept
 INSERT INTO CM_Container_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, CM_Container_ID, ContainerXML, Meta_Description, Meta_Keywords, Name, StructureXML, Title)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.CM_Container_ID, t.ContainerXML, t.Meta_Description, t.Meta_Keywords, t.Name, t.StructureXML, t.Title
 FROM CM_Container t
 LEFT JOIN CM_Container_Trl trl ON (trl.CM_Container_ID = t.CM_Container_ID AND trl.AD_Language='nl_NL')
 WHERE trl.CM_Container_ID IS NULL
;

-- 2017-04-12T17:06:49.263
-- URL zum Konzept
 INSERT INTO CM_CStage_Element_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, CM_CStage_Element_ID, ContentHTML, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.CM_CStage_Element_ID, t.ContentHTML, t.Description, t.Help, t.Name
 FROM CM_CStage_Element t
 LEFT JOIN CM_CStage_Element_Trl trl ON (trl.CM_CStage_Element_ID = t.CM_CStage_Element_ID AND trl.AD_Language='nl_NL')
 WHERE trl.CM_CStage_Element_ID IS NULL
;

-- 2017-04-12T17:06:49.265
-- URL zum Konzept
 INSERT INTO AD_Task_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Task_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Task_ID, t.Description, t.Help, t.Name
 FROM AD_Task t
 LEFT JOIN AD_Task_Trl trl ON (trl.AD_Task_ID = t.AD_Task_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Task_ID IS NULL
;

-- 2017-04-12T17:06:49.267
-- URL zum Konzept
 INSERT INTO AD_BoilerPlate_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_BoilerPlate_ID, TextSnippext)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_BoilerPlate_ID, t.TextSnippext
 FROM AD_BoilerPlate t
 LEFT JOIN AD_BoilerPlate_Trl trl ON (trl.AD_BoilerPlate_ID = t.AD_BoilerPlate_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_BoilerPlate_ID IS NULL
;

-- 2017-04-12T17:06:49.268
-- URL zum Konzept
 INSERT INTO C_Country_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Country_ID, Description, Name, RegionName)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Country_ID, t.Description, t.Name, t.RegionName
 FROM C_Country t
 LEFT JOIN C_Country_Trl trl ON (trl.C_Country_ID = t.C_Country_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_Country_ID IS NULL
;

-- 2017-04-12T17:06:49.273
-- URL zum Konzept
 INSERT INTO PP_Order_Workflow_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Order_Workflow_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Order_Workflow_ID, t.Description, t.Help, t.Name
 FROM PP_Order_Workflow t
 LEFT JOIN PP_Order_Workflow_Trl trl ON (trl.PP_Order_Workflow_ID = t.PP_Order_Workflow_ID AND trl.AD_Language='nl_NL')
 WHERE trl.PP_Order_Workflow_ID IS NULL
;

-- 2017-04-12T17:06:49.274
-- URL zum Konzept
 INSERT INTO AD_Menu_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb
 FROM AD_Menu t
 LEFT JOIN AD_Menu_Trl trl ON (trl.AD_Menu_ID = t.AD_Menu_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Menu_ID IS NULL
;

-- 2017-04-12T17:06:49.287
-- URL zum Konzept
 INSERT INTO AD_Process_Para_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Process_Para_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Process_Para_ID, t.Description, t.Help, t.Name
 FROM AD_Process_Para t
 LEFT JOIN AD_Process_Para_Trl trl ON (trl.AD_Process_Para_ID = t.AD_Process_Para_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Process_Para_ID IS NULL
;

-- 2017-04-12T17:06:49.304
-- URL zum Konzept
 INSERT INTO C_ElementValue_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_ElementValue_ID, Description, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_ElementValue_ID, t.Description, t.Name
 FROM C_ElementValue t
 LEFT JOIN C_ElementValue_Trl trl ON (trl.C_ElementValue_ID = t.C_ElementValue_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_ElementValue_ID IS NULL
;

-- 2017-04-12T17:06:49.310
-- URL zum Konzept
 INSERT INTO AD_Message_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Message_ID, MsgText, MsgTip)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Message_ID, t.MsgText, t.MsgTip
 FROM AD_Message t
 LEFT JOIN AD_Message_Trl trl ON (trl.AD_Message_ID = t.AD_Message_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Message_ID IS NULL
;

-- 2017-04-12T17:06:49.332
-- URL zum Konzept
 INSERT INTO C_UOM_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_UOM_ID, Description, Name, UOMSymbol)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_UOM_ID, t.Description, t.Name, t.UOMSymbol
 FROM C_UOM t
 LEFT JOIN C_UOM_Trl trl ON (trl.C_UOM_ID = t.C_UOM_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_UOM_ID IS NULL
;

-- 2017-04-12T17:06:49.335
-- URL zum Konzept
 INSERT INTO PP_Order_BOMLine_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Order_BOMLine_ID, Description, Help)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Order_BOMLine_ID, t.Description, t.Help
 FROM PP_Order_BOMLine t
 LEFT JOIN PP_Order_BOMLine_Trl trl ON (trl.PP_Order_BOMLine_ID = t.PP_Order_BOMLine_ID AND trl.AD_Language='nl_NL')
 WHERE trl.PP_Order_BOMLine_ID IS NULL
;

-- 2017-04-12T17:06:49.337
-- URL zum Konzept
 INSERT INTO AD_Element_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName
 FROM AD_Element t
 LEFT JOIN AD_Element_Trl trl ON (trl.AD_Element_ID = t.AD_Element_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Element_ID IS NULL
;

-- 2017-04-12T17:06:49.386
-- URL zum Konzept
 INSERT INTO M_Product_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, M_Product_ID, Description, DocumentNote, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.M_Product_ID, t.Description, t.DocumentNote, t.Name
 FROM M_Product t
 LEFT JOIN M_Product_Trl trl ON (trl.M_Product_ID = t.M_Product_ID AND trl.AD_Language='nl_NL')
 WHERE trl.M_Product_ID IS NULL
;

-- 2017-04-12T17:06:49.390
-- URL zum Konzept
 INSERT INTO AD_Desktop_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Desktop_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Desktop_ID, t.Description, t.Help, t.Name
 FROM AD_Desktop t
 LEFT JOIN AD_Desktop_Trl trl ON (trl.AD_Desktop_ID = t.AD_Desktop_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Desktop_ID IS NULL
;

-- 2017-04-12T17:06:49.392
-- URL zum Konzept
 INSERT INTO CM_CStage_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, CM_CStage_ID, ContainerXML, Meta_Description, Meta_Keywords, Name, StructureXML, Title)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.CM_CStage_ID, t.ContainerXML, t.Meta_Description, t.Meta_Keywords, t.Name, t.StructureXML, t.Title
 FROM CM_CStage t
 LEFT JOIN CM_CStage_Trl trl ON (trl.CM_CStage_ID = t.CM_CStage_ID AND trl.AD_Language='nl_NL')
 WHERE trl.CM_CStage_ID IS NULL
;

-- 2017-04-12T17:06:49.394
-- URL zum Konzept
 INSERT INTO PP_Product_BOMLine_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Product_BOMLine_ID, Description, Help)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Product_BOMLine_ID, t.Description, t.Help
 FROM PP_Product_BOMLine t
 LEFT JOIN PP_Product_BOMLine_Trl trl ON (trl.PP_Product_BOMLine_ID = t.PP_Product_BOMLine_ID AND trl.AD_Language='nl_NL')
 WHERE trl.PP_Product_BOMLine_ID IS NULL
;

-- 2017-04-12T17:06:49.397
-- URL zum Konzept
 INSERT INTO C_Tax_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Tax_ID, Description, Name, TaxIndicator)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Tax_ID, t.Description, t.Name, t.TaxIndicator
 FROM C_Tax t
 LEFT JOIN C_Tax_Trl trl ON (trl.C_Tax_ID = t.C_Tax_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_Tax_ID IS NULL
;

-- 2017-04-12T17:06:49.399
-- URL zum Konzept
 INSERT INTO W_Store_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, W_Store_ID, EMailFooter, EMailHeader, WebInfo, WebParam1, WebParam2, WebParam3, WebParam4, WebParam5, WebParam6)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.W_Store_ID, t.EMailFooter, t.EMailHeader, t.WebInfo, t.WebParam1, t.WebParam2, t.WebParam3, t.WebParam4, t.WebParam5, t.WebParam6
 FROM W_Store t
 LEFT JOIN W_Store_Trl trl ON (trl.W_Store_ID = t.W_Store_ID AND trl.AD_Language='nl_NL')
 WHERE trl.W_Store_ID IS NULL
;

-- 2017-04-12T17:06:49.401
-- URL zum Konzept
 INSERT INTO AD_Window_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Window_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Window_ID, t.Description, t.Help, t.Name
 FROM AD_Window t
 LEFT JOIN AD_Window_Trl trl ON (trl.AD_Window_ID = t.AD_Window_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Window_ID IS NULL
;

-- 2017-04-12T17:06:49.407
-- URL zum Konzept
 INSERT INTO AD_InfoWindow_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_InfoWindow_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_InfoWindow_ID, t.Description, t.Help, t.Name
 FROM AD_InfoWindow t
 LEFT JOIN AD_InfoWindow_Trl trl ON (trl.AD_InfoWindow_ID = t.AD_InfoWindow_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_InfoWindow_ID IS NULL
;

-- 2017-04-12T17:06:49.409
-- URL zum Konzept
 INSERT INTO AD_Process_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Process_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Process_ID, t.Description, t.Help, t.Name
 FROM AD_Process t
 LEFT JOIN AD_Process_Trl trl ON (trl.AD_Process_ID = t.AD_Process_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Process_ID IS NULL
;

-- 2017-04-12T17:06:49.418
-- URL zum Konzept
 INSERT INTO AD_Tab_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Tab_ID, CommitWarning, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name
 FROM AD_Tab t
 LEFT JOIN AD_Tab_Trl trl ON (trl.AD_Tab_ID = t.AD_Tab_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Tab_ID IS NULL
;

-- 2017-04-12T17:06:49.435
-- URL zum Konzept
 INSERT INTO AD_Form_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Form_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Form_ID, t.Description, t.Help, t.Name
 FROM AD_Form t
 LEFT JOIN AD_Form_Trl trl ON (trl.AD_Form_ID = t.AD_Form_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Form_ID IS NULL
;

-- 2017-04-12T17:06:49.439
-- URL zum Konzept
 INSERT INTO AD_PrintFormatItem_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_PrintFormatItem_ID, PrintName, PrintNameSuffix)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_PrintFormatItem_ID, t.PrintName, t.PrintNameSuffix
 FROM AD_PrintFormatItem t
 LEFT JOIN AD_PrintFormatItem_Trl trl ON (trl.AD_PrintFormatItem_ID = t.AD_PrintFormatItem_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_PrintFormatItem_ID IS NULL
;

-- 2017-04-12T17:06:49.495
-- URL zum Konzept
 INSERT INTO PP_Product_BOM_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Product_BOM_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Product_BOM_ID, t.Description, t.Help, t.Name
 FROM PP_Product_BOM t
 LEFT JOIN PP_Product_BOM_Trl trl ON (trl.PP_Product_BOM_ID = t.PP_Product_BOM_ID AND trl.AD_Language='nl_NL')
 WHERE trl.PP_Product_BOM_ID IS NULL
;

-- 2017-04-12T17:06:49.498
-- URL zum Konzept
 INSERT INTO AD_FieldGroup_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_FieldGroup_ID, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_FieldGroup_ID, t.Name
 FROM AD_FieldGroup t
 LEFT JOIN AD_FieldGroup_Trl trl ON (trl.AD_FieldGroup_ID = t.AD_FieldGroup_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_FieldGroup_ID IS NULL
;

-- 2017-04-12T17:06:49.502
-- URL zum Konzept
 INSERT INTO AD_Workflow_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Workflow_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Workflow_ID, t.Description, t.Help, t.Name
 FROM AD_Workflow t
 LEFT JOIN AD_Workflow_Trl trl ON (trl.AD_Workflow_ID = t.AD_Workflow_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Workflow_ID IS NULL
;

-- 2017-04-12T17:06:49.506
-- URL zum Konzept
 INSERT INTO C_PaymentTerm_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_PaymentTerm_ID, Description, DocumentNote, Name, Name_Invoice)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_PaymentTerm_ID, t.Description, t.DocumentNote, t.Name, t.Name_Invoice
 FROM C_PaymentTerm t
 LEFT JOIN C_PaymentTerm_Trl trl ON (trl.C_PaymentTerm_ID = t.C_PaymentTerm_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_PaymentTerm_ID IS NULL
;

-- 2017-04-12T17:06:49.508
-- URL zum Konzept
 INSERT INTO AD_Workbench_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Workbench_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Workbench_ID, t.Description, t.Help, t.Name
 FROM AD_Workbench t
 LEFT JOIN AD_Workbench_Trl trl ON (trl.AD_Workbench_ID = t.AD_Workbench_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Workbench_ID IS NULL
;

-- 2017-04-12T17:06:49.510
-- URL zum Konzept
 INSERT INTO PP_Order_Node_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PP_Order_Node_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.PP_Order_Node_ID, t.Description, t.Help, t.Name
 FROM PP_Order_Node t
 LEFT JOIN PP_Order_Node_Trl trl ON (trl.PP_Order_Node_ID = t.PP_Order_Node_ID AND trl.AD_Language='nl_NL')
 WHERE trl.PP_Order_Node_ID IS NULL
;

-- 2017-04-12T17:06:49.512
-- URL zum Konzept
 INSERT INTO CM_Container_Element_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, CM_Container_Element_ID, ContentHTML, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.CM_Container_Element_ID, t.ContentHTML, t.Description, t.Help, t.Name
 FROM CM_Container_Element t
 LEFT JOIN CM_Container_Element_Trl trl ON (trl.CM_Container_Element_ID = t.CM_Container_Element_ID AND trl.AD_Language='nl_NL')
 WHERE trl.CM_Container_Element_ID IS NULL
;

-- 2017-04-12T17:06:49.514
-- URL zum Konzept
 INSERT INTO AD_Field_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Field_ID, Description, Help, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.AD_Field_ID, t.Description, t.Help, t.Name
 FROM AD_Field t
 LEFT JOIN AD_Field_Trl trl ON (trl.AD_Field_ID = t.AD_Field_ID AND trl.AD_Language='nl_NL')
 WHERE trl.AD_Field_ID IS NULL
;

-- 2017-04-12T17:06:49.847
-- URL zum Konzept
 INSERT INTO W_MailMsg_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, W_MailMsg_ID, Message, Message2, Message3, Subject)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.W_MailMsg_ID, t.Message, t.Message2, t.Message3, t.Subject
 FROM W_MailMsg t
 LEFT JOIN W_MailMsg_Trl trl ON (trl.W_MailMsg_ID = t.W_MailMsg_ID AND trl.AD_Language='nl_NL')
 WHERE trl.W_MailMsg_ID IS NULL
;

-- 2017-04-12T17:06:49.849
-- URL zum Konzept
 INSERT INTO C_DocType_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_DocType_ID, DocumentNote, Name, PrintName)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_DocType_ID, t.DocumentNote, t.Name, t.PrintName
 FROM C_DocType t
 LEFT JOIN C_DocType_Trl trl ON (trl.C_DocType_ID = t.C_DocType_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_DocType_ID IS NULL
;

-- 2017-04-12T17:06:49.855
-- URL zum Konzept
 INSERT INTO C_TaxCategory_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_TaxCategory_ID, Description, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_TaxCategory_ID, t.Description, t.Name
 FROM C_TaxCategory t
 LEFT JOIN C_TaxCategory_Trl trl ON (trl.C_TaxCategory_ID = t.C_TaxCategory_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_TaxCategory_ID IS NULL
;

-- 2017-04-12T17:06:49.857
-- URL zum Konzept
 INSERT INTO C_Charge_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Charge_ID, Description, Name)
 SELECT 'nl_NL', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), 100, now(), 100, 'Y', t.C_Charge_ID, t.Description, t.Name
 FROM C_Charge t
 LEFT JOIN C_Charge_Trl trl ON (trl.C_Charge_ID = t.C_Charge_ID AND trl.AD_Language='nl_NL')
 WHERE trl.C_Charge_ID IS NULL
;

