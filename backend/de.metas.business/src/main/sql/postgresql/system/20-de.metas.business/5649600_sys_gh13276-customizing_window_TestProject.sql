-- Field: Prüf Projekt -> Projekt -> Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-08-05T13:04:26.647Z
UPDATE AD_Field SET AD_Name_ID=1557, Description='Finish or (planned) completion date', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an', Name='Projektabschluss',Updated=TO_TIMESTAMP('2022-08-05 15:04:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703847
;

-- 2022-08-05T13:04:26.688Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1557)
;

-- 2022-08-05T13:04:26.707Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703847
;

-- 2022-08-05T13:04:26.709Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703847)
;

-- 2022-08-05T13:05:36.237Z
UPDATE AD_Element_Trl SET Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Name='Abschlussdatum', PrintName='Abschlussdatum',Updated=TO_TIMESTAMP('2022-08-05 15:05:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1557 AND AD_Language='fr_CH'
;

-- 2022-08-05T13:05:36.248Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1557,'fr_CH')
;

-- 2022-08-05T13:05:59.350Z
UPDATE AD_Element_Trl SET Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Name='Abschlussdatum', PrintName='Abschlussdatum',Updated=TO_TIMESTAMP('2022-08-05 15:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1557 AND AD_Language='de_DE'
;

-- 2022-08-05T13:05:59.351Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1557,'de_DE')
;

-- 2022-08-05T13:05:59.358Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(1557,'de_DE')
;

-- 2022-08-05T13:05:59.360Z
UPDATE AD_Column SET ColumnName='DateFinish', Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an' WHERE AD_Element_ID=1557
;

-- 2022-08-05T13:05:59.361Z
UPDATE AD_Process_Para SET ColumnName='DateFinish', Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an', AD_Element_ID=1557 WHERE UPPER(ColumnName)='DATEFINISH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-05T13:05:59.362Z
UPDATE AD_Process_Para SET ColumnName='DateFinish', Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an' WHERE AD_Element_ID=1557 AND IsCentrallyMaintained='Y'
;

-- 2022-08-05T13:05:59.363Z
UPDATE AD_Field SET Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1557) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1557)
;

-- 2022-08-05T13:05:59.375Z
UPDATE AD_PrintFormatItem pi SET PrintName='Abschlussdatum', Name='Abschlussdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1557)
;

-- 2022-08-05T13:05:59.377Z
UPDATE AD_Tab SET Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an', CommitWarning = NULL WHERE AD_Element_ID = 1557
;

-- 2022-08-05T13:05:59.379Z
UPDATE AD_WINDOW SET Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an' WHERE AD_Element_ID = 1557
;

-- 2022-08-05T13:05:59.380Z
UPDATE AD_Menu SET   Name = 'Abschlussdatum', Description = 'Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1557
;

-- 2022-08-05T13:06:05.948Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-05 15:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1557 AND AD_Language='de_DE'
;

-- 2022-08-05T13:06:05.949Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1557,'de_DE')
;

-- 2022-08-05T13:06:05.961Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(1557,'de_DE')
;

-- 2022-08-05T13:06:29.660Z
UPDATE AD_Element_Trl SET Description='Date on which the order including the report is completed.', Name='Completion date', PrintName='Completion date',Updated=TO_TIMESTAMP('2022-08-05 15:06:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1557 AND AD_Language='en_US'
;

-- 2022-08-05T13:06:29.662Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1557,'en_US')
;

-- 2022-08-05T13:11:17.505Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581236,0,TO_TIMESTAMP('2022-08-05 15:11:17','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag seitens des Auftraggebers eingegangen ist.','D','The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.','Y','Beauftragungsdatum','Beauftragungsdatum',TO_TIMESTAMP('2022-08-05 15:11:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T13:11:17.507Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581236 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-08-05T13:11:27.068Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-05 15:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581236 AND AD_Language='de_DE'
;

-- 2022-08-05T13:11:27.070Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581236,'de_DE')
;

-- 2022-08-05T13:11:27.080Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581236,'de_DE')
;

-- 2022-08-05T13:11:44.047Z
UPDATE AD_Element_Trl SET Description='Date on which the order was received from the customer.', IsTranslated='Y', Name='Commissioning date', PrintName='Commissioning date',Updated=TO_TIMESTAMP('2022-08-05 15:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581236 AND AD_Language='en_US'
;

-- 2022-08-05T13:11:44.049Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581236,'en_US')
;

-- Field: Prüf Projekt -> Projekt -> Beauftragungsdatum
-- Column: C_Project.DateContract
-- 2022-08-05T13:12:18.447Z
UPDATE AD_Field SET AD_Name_ID=581236, Description='Datum, an dem der Auftrag seitens des Auftraggebers eingegangen ist.', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.', Name='Beauftragungsdatum',Updated=TO_TIMESTAMP('2022-08-05 15:12:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703846
;

-- 2022-08-05T13:12:18.449Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581236)
;

-- 2022-08-05T13:12:18.452Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703846
;

-- 2022-08-05T13:12:18.452Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703846)
;

-- Field: Prüf Projekt -> Projekt -> Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-05T13:13:57.316Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583614,703908,581096,0,546542,0,TO_TIMESTAMP('2022-08-05 15:13:57','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Fachberater',0,380,0,1,1,TO_TIMESTAMP('2022-08-05 15:13:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T13:13:57.317Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703908 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T13:13:57.319Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581096)
;

-- 2022-08-05T13:13:57.321Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703908
;

-- 2022-08-05T13:13:57.322Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703908)
;

-- Field: Prüf Projekt -> Projekt -> Externe Projektreferenz
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-08-05T13:21:14.861Z
UPDATE AD_Field SET AD_Name_ID=580859, Description=NULL, Help=NULL, Name='Externe Projektreferenz',Updated=TO_TIMESTAMP('2022-08-05 15:21:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703868
;

-- 2022-08-05T13:21:14.862Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580859)
;

-- 2022-08-05T13:21:14.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703868
;

-- 2022-08-05T13:21:14.865Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703868)
;

-- Field: Prüf Projekt -> Projekt -> Geschäftspartner-Abteilung
-- Column: C_Project.BPartnerDepartment
-- 2022-08-05T13:22:21.578Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583615,703909,581097,0,546542,0,TO_TIMESTAMP('2022-08-05 15:22:21','YYYY-MM-DD HH24:MI:SS'),100,'Abteilung / Kostenträger des Geschäftspartners',0,'U',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner-Abteilung',0,390,0,1,1,TO_TIMESTAMP('2022-08-05 15:22:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T13:22:21.579Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703909 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T13:22:21.581Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581097)
;

-- 2022-08-05T13:22:21.584Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703909
;

-- 2022-08-05T13:22:21.585Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703909)
;

-- Field: Prüf Projekt -> Projekt -> Beistellungsdatum
-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-08-05T13:23:53.163Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583616,703910,581098,0,546542,0,TO_TIMESTAMP('2022-08-05 15:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.',0,'U',0,'Y','Y','Y','N','N','N','N','N','Beistellungsdatum',0,400,0,1,1,TO_TIMESTAMP('2022-08-05 15:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T13:23:53.164Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T13:23:53.166Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581098)
;

-- 2022-08-05T13:23:53.168Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703910
;

-- 2022-08-05T13:23:53.169Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703910)
;

-- Field: Prüf Projekt -> Projekt -> Betreuer
-- Column: C_Project.AD_User_InCharge_ID
-- 2022-08-05T13:26:45.885Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583617,703911,541510,0,546542,0,TO_TIMESTAMP('2022-08-05 15:26:45','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Betreuer',0,410,0,1,1,TO_TIMESTAMP('2022-08-05 15:26:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T13:26:45.886Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703911 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T13:26:45.888Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541510)
;

-- 2022-08-05T13:26:45.891Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703911
;

-- 2022-08-05T13:26:45.892Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703911)
;

-- Field: Prüf Projekt -> Projekt -> Referenz
-- Column: C_Project.POReference
-- 2022-08-05T13:28:36.421Z
UPDATE AD_Field SET AD_Name_ID=952, Description='Referenz-Nummer des Kunden', Help='The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.', Name='Referenz',Updated=TO_TIMESTAMP('2022-08-05 15:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703853
;

-- 2022-08-05T13:28:36.423Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952)
;

-- 2022-08-05T13:28:36.429Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703853
;

-- 2022-08-05T13:28:36.430Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703853)
;

-- Field: Prüf Projekt -> Projekt -> Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-05T13:31:29.221Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583618,703912,581099,0,546542,0,TO_TIMESTAMP('2022-08-05 15:31:29','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsSummary@=N','U',0,'Y','Y','Y','N','N','N','N','N','Zieltermin des Geschäftspartners',0,420,0,1,1,TO_TIMESTAMP('2022-08-05 15:31:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T13:31:29.223Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703912 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T13:31:29.225Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581099)
;

-- 2022-08-05T13:31:29.227Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703912
;

-- 2022-08-05T13:31:29.228Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703912)
;

-- Field: Prüf Projekt -> Projekt -> Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T13:33:11.437Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583964,703913,581100,0,546542,0,TO_TIMESTAMP('2022-08-05 15:33:11','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.',0,'U',0,'Y','Y','Y','N','N','N','N','N','Projekt erstellt',0,430,0,1,1,TO_TIMESTAMP('2022-08-05 15:33:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T13:33:11.438Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703913 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T13:33:11.439Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581100)
;

-- 2022-08-05T13:33:11.441Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703913
;

-- 2022-08-05T13:33:11.443Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703913)
;

-- Name: R_StatusCategory
-- 2022-08-05T13:36:40.172Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540594,'R_StatusCategory_ID=@R_StatusCategory_ID@',TO_TIMESTAMP('2022-08-05 15:36:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','R_StatusCategory','S',TO_TIMESTAMP('2022-08-05 15:36:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: ProjectCategoryStatus
-- 2022-08-05T13:38:16.034Z
UPDATE AD_Reference SET Description='Project Status Test Project', EntityType='D', Name='ProjectCategoryStatus',Updated=TO_TIMESTAMP('2022-08-05 15:38:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540849
;

-- Reference: ProjectCategoryStatus
-- Table: R_Status
-- Key: R_Status.R_Status_ID
-- 2022-08-05T13:39:15.087Z
UPDATE AD_Ref_Table SET WhereClause='R_StatusCategory_ID=540010',Updated=TO_TIMESTAMP('2022-08-05 15:39:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540849
;

-- Field: Prüf Projekt -> Projekt -> Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-08-05T13:43:51.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Tab_ID,AD_Val_Rule_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559693,703914,544077,0,18,540849,546542,540594,0,TO_TIMESTAMP('2022-08-05 15:43:51','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Projektstatus',0,440,0,1,1,TO_TIMESTAMP('2022-08-05 15:43:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T13:43:51.272Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T13:43:51.273Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544077)
;

-- 2022-08-05T13:43:51.276Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703914
;

-- 2022-08-05T13:43:51.277Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703914)
;

-- 2022-08-05T13:45:54.338Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546270,549702,TO_TIMESTAMP('2022-08-05 15:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','state',12,TO_TIMESTAMP('2022-08-05 15:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T13:45:57.191Z
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2022-08-05 15:45:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549690
;

-- 2022-08-05T13:45:58.285Z
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2022-08-05 15:45:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549691
;

-- UI Element: Prüf Projekt -> Projekt.Kunden Projektreferenz Nr.
-- Column: C_Project.POReference
-- 2022-08-05T13:47:57.911Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703853,0,546542,549702,611316,'F',TO_TIMESTAMP('2022-08-05 15:47:57','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Kunden Projektreferenz Nr.',10,0,0,TO_TIMESTAMP('2022-08-05 15:47:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-05T13:48:36.115Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703912,0,546542,549702,611317,'F',TO_TIMESTAMP('2022-08-05 15:48:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Zieltermin des Geschäftspartners',20,0,0,TO_TIMESTAMP('2022-08-05 15:48:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-08-05T13:48:59.096Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703914,0,546542,549702,611318,'F',TO_TIMESTAMP('2022-08-05 15:48:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektstatus',30,0,0,TO_TIMESTAMP('2022-08-05 15:48:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Ausstelldatum VA
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T13:49:26.113Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703913,0,546542,549702,611319,'F',TO_TIMESTAMP('2022-08-05 15:49:25','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.','Y','N','N','Y','N','N','N',0,'Ausstelldatum VA',40,0,0,TO_TIMESTAMP('2022-08-05 15:49:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Auftragszustand (nur Klartext)
-- Column: C_Project.R_Project_Status_ID
-- 2022-08-05T13:49:35.089Z
UPDATE AD_UI_Element SET Name='Auftragszustand (nur Klartext)',Updated=TO_TIMESTAMP('2022-08-05 15:49:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611318
;

-- UI Element: Prüf Projekt -> Projekt.Abschlussdatum
-- Column: C_Project.DateFinish
-- 2022-08-05T13:49:59.325Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703847,0,546542,549702,611320,'F',TO_TIMESTAMP('2022-08-05 15:49:59','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.','Dieses Datum gibt das erwartete oder tatsächliche Projektende an','Y','N','N','Y','N','N','N',0,'Abschlussdatum',50,0,0,TO_TIMESTAMP('2022-08-05 15:49:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Abschlussdatum Versuchsauftrag (VA)
-- Column: C_Project.DateFinish
-- 2022-08-05T13:50:54.923Z
UPDATE AD_UI_Element SET Name='Abschlussdatum Versuchsauftrag (VA)',Updated=TO_TIMESTAMP('2022-08-05 15:50:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611282
;

-- UI Element: Prüf Projekt -> Projekt.Abschlussdatum Versuchsauftrag (VA)
-- Column: C_Project.DateFinish
-- 2022-08-05T13:51:24.366Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-08-05 15:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611282
;

-- UI Element: Prüf Projekt -> Projekt.Beauftragungsdatum
-- Column: C_Project.DateContract
-- 2022-08-05T13:52:29.581Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703846,0,546542,549689,611321,'F',TO_TIMESTAMP('2022-08-05 15:52:29','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag seitens des Auftraggebers eingegangen ist.','The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.','Y','N','N','Y','N','N','N',0,'Beauftragungsdatum',25,0,0,TO_TIMESTAMP('2022-08-05 15:52:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Aussendienst
-- Column: C_Project.SalesRep_ID
-- 2022-08-05T13:52:35.859Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2022-08-05 15:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611280
;

-- UI Element: Prüf Projekt -> Projekt.Datum AE
-- Column: C_Project.DateContract
-- 2022-08-05T13:52:45.366Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2022-08-05 15:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611281
;

-- UI Element: Prüf Projekt -> Projekt.Datum AE
-- Column: C_Project.DateContract
-- 2022-08-05T13:53:08.391Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611281
;

-- UI Element: Prüf Projekt -> Projekt.Aussendienst
-- Column: C_Project.SalesRep_ID
-- 2022-08-05T13:53:56.200Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611280
;

-- UI Element: Prüf Projekt -> Projekt.Name des Fachberaters für den Versuchsauftrag (VA)
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-05T13:54:36.066Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703908,0,546542,549689,611322,'F',TO_TIMESTAMP('2022-08-05 15:54:35','YYYY-MM-DD HH24:MI:SS'),100,'Name des Fachberaters für den Versuchsauftrag (VA)','Y','N','N','Y','N','N','N',0,'Name des Fachberaters für den Versuchsauftrag (VA)',35,0,0,TO_TIMESTAMP('2022-08-05 15:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.VA Nummer
-- Column: C_Project.POReference
-- 2022-08-05T13:56:03.069Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703853,0,546542,549689,611323,'F',TO_TIMESTAMP('2022-08-05 15:56:02','YYYY-MM-DD HH24:MI:SS'),100,'Versuchsauftrag Nummer','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'VA Nummer',45,0,0,TO_TIMESTAMP('2022-08-05 15:56:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Abteilung / Kostenträger des Auftraggebers
-- Column: C_Project.BPartnerDepartment
-- 2022-08-05T13:57:44.740Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703909,0,546542,549689,611324,'F',TO_TIMESTAMP('2022-08-05 15:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Abteilung / Kostenträger des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Abteilung / Kostenträger des Auftraggebers',50,0,0,TO_TIMESTAMP('2022-08-05 15:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Anliefertermin Kunde (geplanter Anliefertermin Prüfgegenstände durch Kunden)
-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-08-05T13:58:09.880Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703910,0,546542,549689,611325,'F',TO_TIMESTAMP('2022-08-05 15:58:09','YYYY-MM-DD HH24:MI:SS'),100,'Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.','Y','N','N','Y','N','N','N',0,'Anliefertermin Kunde (geplanter Anliefertermin Prüfgegenstände durch Kunden)',60,0,0,TO_TIMESTAMP('2022-08-05 15:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Prüf Projekt -> Projekt -> Ersteller Prüfauftrag
-- Column: C_Project.WOOwner
-- 2022-08-05T14:00:13.120Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583670,703915,581121,0,546542,0,TO_TIMESTAMP('2022-08-05 16:00:13','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Ersteller Prüfauftrag',0,450,0,999,1,TO_TIMESTAMP('2022-08-05 16:00:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T14:00:13.121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703915 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T14:00:13.123Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581121)
;

-- 2022-08-05T14:00:13.127Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703915
;

-- 2022-08-05T14:00:13.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703915)
;

-- UI Element: Prüf Projekt -> Projekt.Ersteller Prüfauftrag
-- Column: C_Project.WOOwner
-- 2022-08-05T14:01:03.158Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703915,0,546542,549689,611326,'F',TO_TIMESTAMP('2022-08-05 16:01:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Ersteller Prüfauftrag',70,0,0,TO_TIMESTAMP('2022-08-05 16:01:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Name der Prüfung
-- Column: C_Project.Name
-- 2022-08-05T14:01:28.257Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703838,0,546542,549689,611327,'F',TO_TIMESTAMP('2022-08-05 16:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name der Prüfung',80,0,0,TO_TIMESTAMP('2022-08-05 16:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Prüf Projekt
-- Window: Prüf Projekt
-- 2022-08-05T14:28:36.826Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581212,541988,0,541586,TO_TIMESTAMP('2022-08-05 16:28:36','YYYY-MM-DD HH24:MI:SS'),100,'D','TestProject','Y','N','N','Y','N','Prüf Projekt',TO_TIMESTAMP('2022-08-05 16:28:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T14:28:36.828Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541988 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-08-05T14:28:36.830Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541988, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541988)
;

-- 2022-08-05T14:28:36.833Z
/* DDL */  select update_menu_translation_from_ad_element(581212)
;

-- Reordering children of `Projekt-Verwaltung`
-- Node name: `Projektart (C_ProjectType)`
-- 2022-08-05T14:28:45.196Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- Node name: `Projekt (C_Project)`
-- 2022-08-05T14:28:45.198Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- Node name: `Service/Reparatur Projekt (C_Project)`
-- 2022-08-05T14:28:45.199Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541577 AND AD_Tree_ID=10
;

-- Node name: `Budget Project (C_Project)`
-- 2022-08-05T14:28:45.200Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541957 AND AD_Tree_ID=10
;

-- Node name: `Work Order Project (C_Project)`
-- 2022-08-05T14:28:45.202Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541961 AND AD_Tree_ID=10
;

-- Node name: `Prüf Projekt`
-- 2022-08-05T14:28:45.203Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541988 AND AD_Tree_ID=10
;

-- 2022-08-05T14:45:15.163Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN WOProjectCreatedDate TIMESTAMP WITHOUT TIME ZONE')
;
