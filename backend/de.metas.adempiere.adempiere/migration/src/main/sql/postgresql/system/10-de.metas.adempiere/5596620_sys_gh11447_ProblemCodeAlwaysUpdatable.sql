-- Problembereichscode always updatable
-- 2021-07-01T08:20:27.658Z
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2021-07-01 10:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572814
;

-- Change PO Reference Type
-- 2021-07-01T08:25:47.557Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=1024,Updated=TO_TIMESTAMP('2021-07-01 10:25:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3799
;

-- Table Changes
-- 2021-07-01T08:25:50.609Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_inout','POReference','VARCHAR(1024)',null,null)
;

--Make it identifier
-- 2021-07-01T09:44:28.307Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2021-07-01 11:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3799;

-- Display it in window
-- 2021-07-01T09:46:55.825Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,627585,0,543272,544711,586934,'F',TO_TIMESTAMP('2021-07-01 11:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Referenz',60,0,0,TO_TIMESTAMP('2021-07-01 11:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T09:48:44.025Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2021-07-01 11:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586934
;

-- 2021-07-01T09:49:50.507Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-07-01 11:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575913
;

-- 2021-07-01T09:50:08.510Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=586934
;

-- Project Field TRL
-- 2021-07-06T06:34:07.044Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Projekt', PrintName='Projekt',Updated=TO_TIMESTAMP('2021-07-06 08:34:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=208 AND AD_Language='fr_CH'
;

-- 2021-07-06T06:34:07.060Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(208,'fr_CH')
;

-- 2021-07-06T06:34:13.530Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-06 08:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=208 AND AD_Language='en_GB'
;

-- 2021-07-06T06:34:13.534Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(208,'en_GB')
;

-- 2021-07-06T06:34:34.035Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Projekt', PrintName='Projekt',Updated=TO_TIMESTAMP('2021-07-06 08:34:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=208 AND AD_Language='de_CH'
;

-- 2021-07-06T06:34:34.036Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(208,'de_CH')
;

-- 2021-07-06T06:34:50.067Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Projekt', PrintName='Projekt',Updated=TO_TIMESTAMP('2021-07-06 08:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=208 AND AD_Language='nl_NL'
;

-- 2021-07-06T06:34:50.071Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(208,'nl_NL')
;

-- 2021-07-06T06:35:01.644Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Projekt', PrintName='Projekt',Updated=TO_TIMESTAMP('2021-07-06 08:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=208 AND AD_Language='de_DE'
;

-- 2021-07-06T06:35:01.645Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(208,'de_DE')
;

-- 2021-07-06T06:35:01.655Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(208,'de_DE')
;

-- 2021-07-06T06:35:01.671Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Project_ID', Name='Projekt', Description='Financial Project', Help='A Project allows you to track and control internal or external activities.' WHERE AD_Element_ID=208
;

-- 2021-07-06T06:35:01.676Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Project_ID', Name='Projekt', Description='Financial Project', Help='A Project allows you to track and control internal or external activities.', AD_Element_ID=208 WHERE UPPER(ColumnName)='C_PROJECT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T06:35:01.677Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Project_ID', Name='Projekt', Description='Financial Project', Help='A Project allows you to track and control internal or external activities.' WHERE AD_Element_ID=208 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T06:35:01.677Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Projekt', Description='Financial Project', Help='A Project allows you to track and control internal or external activities.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=208) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 208)
;

-- 2021-07-06T06:35:01.696Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Projekt', Name='Projekt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=208)
;

-- 2021-07-06T06:35:01.701Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Projekt', Description='Financial Project', Help='A Project allows you to track and control internal or external activities.', CommitWarning = NULL WHERE AD_Element_ID = 208
;

-- 2021-07-06T06:35:01.702Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Projekt', Description='Financial Project', Help='A Project allows you to track and control internal or external activities.' WHERE AD_Element_ID = 208
;

-- 2021-07-06T06:35:01.703Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Projekt', Description = 'Financial Project', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 208
;

-- Summary Field TRl from Request Window
-- 2021-07-06T06:43:15.414Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Notiz', PrintName='Notiz',Updated=TO_TIMESTAMP('2021-07-06 08:43:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1521 AND AD_Language='fr_CH'
;

-- 2021-07-06T06:43:15.417Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1521,'fr_CH')
;

-- 2021-07-06T06:43:21.581Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Notiz', PrintName='Notiz',Updated=TO_TIMESTAMP('2021-07-06 08:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1521 AND AD_Language='it_CH'
;

-- 2021-07-06T06:43:21.584Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1521,'it_CH')
;

-- 2021-07-06T06:43:26.509Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-06 08:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1521 AND AD_Language='en_GB'
;

-- 2021-07-06T06:43:26.509Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1521,'en_GB')
;

-- 2021-07-06T06:43:33.734Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Notiz', PrintName='Notiz',Updated=TO_TIMESTAMP('2021-07-06 08:43:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1521 AND AD_Language='de_CH'
;

-- 2021-07-06T06:43:33.736Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1521,'de_CH')
;

-- 2021-07-06T06:43:42.651Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Notiz', PrintName='Notiz',Updated=TO_TIMESTAMP('2021-07-06 08:43:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1521 AND AD_Language='nl_NL'
;

-- 2021-07-06T06:43:42.652Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1521,'nl_NL')
;

-- 2021-07-06T06:43:49.912Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Notiz', PrintName='Notiz',Updated=TO_TIMESTAMP('2021-07-06 08:43:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1521 AND AD_Language='de_DE'
;

-- 2021-07-06T06:43:49.914Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1521,'de_DE')
;

-- 2021-07-06T06:43:49.920Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(1521,'de_DE')
;

-- 2021-07-06T06:43:49.921Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Summary', Name='Notiz', Description='Textual summary of this request', Help='The Summary allows free form text entry of a recap of this request.' WHERE AD_Element_ID=1521
;

-- 2021-07-06T06:43:49.923Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Summary', Name='Notiz', Description='Textual summary of this request', Help='The Summary allows free form text entry of a recap of this request.', AD_Element_ID=1521 WHERE UPPER(ColumnName)='SUMMARY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T06:43:49.924Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Summary', Name='Notiz', Description='Textual summary of this request', Help='The Summary allows free form text entry of a recap of this request.' WHERE AD_Element_ID=1521 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T06:43:49.925Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Notiz', Description='Textual summary of this request', Help='The Summary allows free form text entry of a recap of this request.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1521) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1521)
;

-- 2021-07-06T06:43:49.950Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Notiz', Name='Notiz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1521)
;

-- 2021-07-06T06:43:49.951Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Notiz', Description='Textual summary of this request', Help='The Summary allows free form text entry of a recap of this request.', CommitWarning = NULL WHERE AD_Element_ID = 1521
;

-- 2021-07-06T06:43:49.953Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Notiz', Description='Textual summary of this request', Help='The Summary allows free form text entry of a recap of this request.' WHERE AD_Element_ID = 1521
;

-- 2021-07-06T06:43:49.953Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Notiz', Description = 'Textual summary of this request', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1521
;

-- Request Type Trl
-- 2021-07-06T06:48:52.318Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aufgabentyp', PrintName='Aufgabentyp',Updated=TO_TIMESTAMP('2021-07-06 08:48:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1894 AND AD_Language='de_DE'
;

-- 2021-07-06T06:48:52.319Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1894,'de_DE')
;

-- 2021-07-06T06:48:52.323Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(1894,'de_DE')
;

-- 2021-07-06T06:48:52.332Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='R_RequestType_ID', Name='Aufgabentyp', Description='Type of request (e.g. Inquiry, Complaint, ..)', Help='Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.' WHERE AD_Element_ID=1894
;

-- 2021-07-06T06:48:52.333Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='R_RequestType_ID', Name='Aufgabentyp', Description='Type of request (e.g. Inquiry, Complaint, ..)', Help='Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.', AD_Element_ID=1894 WHERE UPPER(ColumnName)='R_REQUESTTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T06:48:52.334Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='R_RequestType_ID', Name='Aufgabentyp', Description='Type of request (e.g. Inquiry, Complaint, ..)', Help='Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.' WHERE AD_Element_ID=1894 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T06:48:52.334Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Aufgabentyp', Description='Type of request (e.g. Inquiry, Complaint, ..)', Help='Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1894) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1894)
;

-- 2021-07-06T06:48:52.346Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Aufgabentyp', Name='Aufgabentyp' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1894)
;

-- 2021-07-06T06:48:52.346Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Aufgabentyp', Description='Type of request (e.g. Inquiry, Complaint, ..)', Help='Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.', CommitWarning = NULL WHERE AD_Element_ID = 1894
;

-- 2021-07-06T06:48:52.347Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Aufgabentyp', Description='Type of request (e.g. Inquiry, Complaint, ..)', Help='Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.' WHERE AD_Element_ID = 1894
;

-- 2021-07-06T06:48:52.348Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Aufgabentyp', Description = 'Type of request (e.g. Inquiry, Complaint, ..)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1894
;

-- 2021-07-06T06:49:16.956Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aufgabentyp', PrintName='Aufgabentyp',Updated=TO_TIMESTAMP('2021-07-06 08:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1894 AND AD_Language='nl_NL'
;

-- 2021-07-06T06:49:16.959Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1894,'nl_NL')
;

-- 2021-07-06T06:49:23.981Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aufgabentyp', PrintName='Aufgabentyp',Updated=TO_TIMESTAMP('2021-07-06 08:49:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1894 AND AD_Language='de_CH'
;

-- 2021-07-06T06:49:23.984Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1894,'de_CH')
;

-- 2021-07-06T06:49:28.021Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-06 08:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1894 AND AD_Language='en_GB'
;

-- 2021-07-06T06:49:28.022Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1894,'en_GB')
;

-- 2021-07-06T06:49:33.422Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Aufgabentyp', PrintName='Aufgabentyp',Updated=TO_TIMESTAMP('2021-07-06 08:49:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1894 AND AD_Language='it_CH'
;

-- 2021-07-06T06:49:33.425Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1894,'it_CH')
;

-- 2021-07-06T06:49:39.116Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Aufgabentyp', PrintName='Aufgabentyp',Updated=TO_TIMESTAMP('2021-07-06 08:49:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1894 AND AD_Language='fr_CH'
;

-- 2021-07-06T06:49:39.116Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1894,'fr_CH')
;

--Lieferung/Wareneingang filter column
-- 2021-07-06T07:16:31.344Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-07-06 10:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=13573
;

-- Update Duration param reference
-- 2021-07-01T08:16:22.817Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2021-07-01 10:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541905
;