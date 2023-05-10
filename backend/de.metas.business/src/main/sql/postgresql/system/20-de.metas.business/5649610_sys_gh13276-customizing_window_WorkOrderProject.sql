-- Field: Work Order Project -> Projekt -> Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-08-09T08:46:11.895Z
UPDATE AD_Field SET AD_Name_ID=1557, Description='Finish or (planned) completion date', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an', Name='Projektabschluss',Updated=TO_TIMESTAMP('2022-08-09 10:46:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697980
;

-- 2022-08-09T08:46:11.922Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1557)
;

-- 2022-08-09T08:46:11.935Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697980
;

-- 2022-08-09T08:46:11.937Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697980)
;

-- 2022-08-09T08:48:16.312Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581257,0,TO_TIMESTAMP('2022-08-09 10:48:16','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.','D','Y','Abschlussdatum','Abschlussdatum',TO_TIMESTAMP('2022-08-09 10:48:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T08:48:16.313Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581257 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-08-09T08:48:22.853Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-09 10:48:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581257 AND AD_Language='de_DE'
;

-- 2022-08-09T08:48:22.860Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581257,'de_DE')
;

-- 2022-08-09T08:48:22.870Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581257,'de_DE')
;

-- 2022-08-09T08:48:48.387Z
UPDATE AD_Element_Trl SET Description='Date on which the order including the report is completed.', IsTranslated='Y', Name='Completion date', PrintName='Completion date',Updated=TO_TIMESTAMP('2022-08-09 10:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581257 AND AD_Language='en_US'
;

-- 2022-08-09T08:48:48.389Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581257,'en_US')
;

-- Field: Work Order Project -> Projekt -> Abschlussdatum
-- Column: C_Project.DateFinish
-- 2022-08-09T08:49:04.701Z
UPDATE AD_Field SET AD_Name_ID=581257, Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL, Name='Abschlussdatum',Updated=TO_TIMESTAMP('2022-08-09 10:49:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697980
;

-- 2022-08-09T08:49:04.703Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581257)
;

-- 2022-08-09T08:49:04.705Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697980
;

-- 2022-08-09T08:49:04.707Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697980)
;

-- 2022-08-09T08:50:07.645Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581258,0,TO_TIMESTAMP('2022-08-09 10:50:07','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag seitens des Auftraggebers eingegangen ist.','U','Y','Beauftragungsdatum','Beauftragungsdatum',TO_TIMESTAMP('2022-08-09 10:50:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T08:50:07.646Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581258 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-08-09T08:50:15.244Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-09 10:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581258 AND AD_Language='de_DE'
;

-- 2022-08-09T08:50:15.246Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581258,'de_DE')
;

-- 2022-08-09T08:50:15.252Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581258,'de_DE')
;

-- 2022-08-09T08:50:28.919Z
UPDATE AD_Element_Trl SET Description='Date on which the order was received from the customer.', IsTranslated='Y', Name='Commissioning date', PrintName='Commissioning date',Updated=TO_TIMESTAMP('2022-08-09 10:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581258 AND AD_Language='en_US'
;

-- 2022-08-09T08:50:28.921Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581258,'en_US')
;

-- 2022-08-09T08:50:37.207Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-08-09 10:50:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581258
;

-- Field: Work Order Project -> Projekt -> Beauftragungsdatum
-- Column: C_Project.DateContract
-- 2022-08-09T08:50:58.095Z
UPDATE AD_Field SET AD_Name_ID=581258, Description='Datum, an dem der Auftrag seitens des Auftraggebers eingegangen ist.', Help=NULL, Name='Beauftragungsdatum',Updated=TO_TIMESTAMP('2022-08-09 10:50:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697979
;

-- 2022-08-09T08:50:58.096Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581258)
;

-- 2022-08-09T08:50:58.099Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697979
;

-- 2022-08-09T08:50:58.099Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697979)
;

-- Field: Work Order Project -> Projekt -> Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-09T08:53:01.211Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583614,703939,581096,0,546289,0,TO_TIMESTAMP('2022-08-09 10:53:01','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Fachberater',0,380,0,1,1,TO_TIMESTAMP('2022-08-09 10:53:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T08:53:01.213Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T08:53:01.215Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581096)
;

-- 2022-08-09T08:53:01.220Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703939
;

-- 2022-08-09T08:53:01.220Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703939)
;

-- Field: Work Order Project -> Projekt -> Externe Projektreferenz
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-08-09T08:54:15.356Z
UPDATE AD_Field SET AD_Name_ID=580859, Description=NULL, Help=NULL, Name='Externe Projektreferenz',Updated=TO_TIMESTAMP('2022-08-09 10:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698001
;

-- 2022-08-09T08:54:15.357Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580859)
;

-- 2022-08-09T08:54:15.360Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698001
;

-- 2022-08-09T08:54:15.360Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698001)
;

-- Field: Work Order Project -> Projekt -> Geschäftspartner-Abteilung
-- Column: C_Project.BPartnerDepartment
-- 2022-08-09T08:55:46.751Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583615,703940,581097,0,546289,0,TO_TIMESTAMP('2022-08-09 10:55:46','YYYY-MM-DD HH24:MI:SS'),100,'Abteilung / Kostenträger des Geschäftspartners',0,'D',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner-Abteilung',0,390,0,1,1,TO_TIMESTAMP('2022-08-09 10:55:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T08:55:46.753Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703940 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T08:55:46.755Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581097)
;

-- 2022-08-09T08:55:46.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703940
;

-- 2022-08-09T08:55:46.758Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703940)
;

-- Field: Work Order Project -> Projekt -> Beistellungsdatum
-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-08-09T08:56:54.505Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583616,703941,581098,0,546289,0,TO_TIMESTAMP('2022-08-09 10:56:54','YYYY-MM-DD HH24:MI:SS'),100,'Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Beistellungsdatum',0,400,0,1,1,TO_TIMESTAMP('2022-08-09 10:56:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T08:56:54.507Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703941 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T08:56:54.508Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581098)
;

-- 2022-08-09T08:56:54.510Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703941
;

-- 2022-08-09T08:56:54.511Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703941)
;

-- Field: Work Order Project -> Projekt -> Ersteller Prüfauftrag
-- Column: C_Project.WOOwner
-- 2022-08-09T08:57:58.550Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583670,703942,581121,0,546289,0,TO_TIMESTAMP('2022-08-09 10:57:58','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Ersteller Prüfauftrag',0,410,0,1,1,TO_TIMESTAMP('2022-08-09 10:57:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T08:57:58.552Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T08:57:58.553Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581121)
;

-- 2022-08-09T08:57:58.556Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703942
;

-- 2022-08-09T08:57:58.557Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703942)
;

-- Field: Work Order Project -> Projekt -> Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-09T09:00:02.505Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583618,703943,581099,0,546289,0,TO_TIMESTAMP('2022-08-09 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Zieltermin des Geschäftspartners',0,420,0,1,1,TO_TIMESTAMP('2022-08-09 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T09:00:02.507Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T09:00:02.509Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581099)
;

-- 2022-08-09T09:00:02.511Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703943
;

-- 2022-08-09T09:00:02.512Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703943)
;

-- Field: Work Order Project -> Projekt -> Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-09T09:01:10.542Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583964,703944,581100,0,546289,0,TO_TIMESTAMP('2022-08-09 11:01:10','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Projekt erstellt',0,430,0,1,1,TO_TIMESTAMP('2022-08-09 11:01:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T09:01:10.544Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703944 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T09:01:10.545Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581100)
;

-- 2022-08-09T09:01:10.548Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703944
;

-- 2022-08-09T09:01:10.549Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703944)
;

-- Name: StatusCategory
-- 2022-08-09T09:03:23.980Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541632,TO_TIMESTAMP('2022-08-09 11:03:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','StatusCategory',TO_TIMESTAMP('2022-08-09 11:03:23','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-09T09:03:23.982Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541632 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: StatusCategory
-- Table: R_Status
-- Key: R_Status.R_Status_ID
-- 2022-08-09T09:04:16.634Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,13562,0,541632,776,TO_TIMESTAMP('2022-08-09 11:04:16','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N',TO_TIMESTAMP('2022-08-09 11:04:16','YYYY-MM-DD HH24:MI:SS'),100,'R_StatusCategory_ID=540010')
;

-- Name: R_StatusCategoryProject
-- 2022-08-09T09:06:06.953Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540595,'R_StatusCategory_ID=@ProjectCategory@',TO_TIMESTAMP('2022-08-09 11:06:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','R_StatusCategoryProject','S',TO_TIMESTAMP('2022-08-09 11:06:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T09:10:29.990Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545971,549712,TO_TIMESTAMP('2022-08-09 11:10:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','state',12,TO_TIMESTAMP('2022-08-09 11:10:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: StatusCategory
-- Table: R_Status
-- Key: R_Status.R_Status_ID
-- 2022-08-09T09:18:55.746Z
UPDATE AD_Ref_Table SET AD_Display=13570, EntityType='D',Updated=TO_TIMESTAMP('2022-08-09 11:18:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541632
;

-- Reference: StatusCategory
-- Table: R_StatusCategory
-- Key: R_StatusCategory.R_StatusCategory_ID
-- 2022-08-09T09:20:29.895Z
UPDATE AD_Ref_Table SET AD_Display=14997, AD_Key=14989, AD_Table_ID=844,Updated=TO_TIMESTAMP('2022-08-09 11:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541632
;
-- UI Element: Work Order Project -> Projekt.Aussendienst
-- Column: C_Project.SalesRep_ID
-- 2022-08-09T09:26:18.851Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-08-09 11:26:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608302
;

-- UI Element: Work Order Project -> Projekt.Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-09T09:27:24.201Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703939,0,546289,549194,611358,'F',TO_TIMESTAMP('2022-08-09 11:27:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Fachberater',25,0,0,TO_TIMESTAMP('2022-08-09 11:27:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Geschäftspartner-Abteilung
-- Column: C_Project.BPartnerDepartment
-- 2022-08-09T09:28:12.861Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703940,0,546289,549194,611359,'F',TO_TIMESTAMP('2022-08-09 11:28:12','YYYY-MM-DD HH24:MI:SS'),100,'Abteilung / Kostenträger des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Abteilung',60,0,0,TO_TIMESTAMP('2022-08-09 11:28:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Ersteller Prüfauftrag
-- Column: C_Project.WOOwner
-- 2022-08-09T09:28:27.064Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703942,0,546289,549194,611360,'F',TO_TIMESTAMP('2022-08-09 11:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Ersteller Prüfauftrag',70,0,0,TO_TIMESTAMP('2022-08-09 11:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Beistellungsdatum
-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-08-09T09:28:53.061Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703941,0,546289,549194,611361,'F',TO_TIMESTAMP('2022-08-09 11:28:52','YYYY-MM-DD HH24:MI:SS'),100,'Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.','Y','N','N','Y','N','N','N',0,'Beistellungsdatum',80,0,0,TO_TIMESTAMP('2022-08-09 11:28:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Referenz
-- Column: C_Project.POReference
-- 2022-08-09T09:29:46.047Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697986,0,546289,549712,611362,'F',TO_TIMESTAMP('2022-08-09 11:29:45','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Referenz',10,0,0,TO_TIMESTAMP('2022-08-09 11:29:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-09T09:30:00.854Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703943,0,546289,549712,611363,'F',TO_TIMESTAMP('2022-08-09 11:30:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Zieltermin des Geschäftspartners',20,0,0,TO_TIMESTAMP('2022-08-09 11:30:00','YYYY-MM-DD HH24:MI:SS'),100)
;


-- ADDING STUFF FROM OTHER REPO
--- BEGIN ---
-- 2022-06-10T09:17:40.303Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) 
--VALUES (
SELECT 0,559693,700634,0,546289,0,TO_TIMESTAMP('2022-06-10 10:17:39','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Projektstatus',0,410,0,1,1,TO_TIMESTAMP('2022-06-10 10:17:39','YYYY-MM-DD HH24:MI:SS'),100
--)
where not exists (select 1 from AD_Field where AD_Column_ID=559693 and AD_Tab_ID=546289);
;

-- 2022-06-10T09:17:40.386Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700634 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-10T09:17:40.466Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544077)
;

-- 2022-06-10T09:17:40.536Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700634
;

-- 2022-06-10T09:17:40.606Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(700634)
;

-- 2022-06-10T09:18:14.393Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) 
--VALUES (
select 0,700634,0,546289,549191,609539,'F',TO_TIMESTAMP('2022-06-10 10:18:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektstatus',65,0,0,TO_TIMESTAMP('2022-06-10 10:18:13','YYYY-MM-DD HH24:MI:SS'),100
where exists (select 1 from ad_ui_element where ad_ui_element_id=609539)
--)

;

-- 2022-06-10T09:19:47.282Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=580254, Description=NULL, Help=NULL, Name='Status',Updated=TO_TIMESTAMP('2022-06-10 10:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700634
;

-- 2022-06-10T09:19:47.352Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580254)
;

-- 2022-06-10T09:19:47.423Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700634
;

-- 2022-06-10T09:19:47.491Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(700634)
;
--- END ---


-- UI Element: Work Order Project -> Projekt.Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-09T09:33:48.332Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703944,0,546289,549712,611365,'F',TO_TIMESTAMP('2022-08-09 11:33:48','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.','Y','N','N','Y','N','N','N',0,'Projekt erstellt',40,0,0,TO_TIMESTAMP('2022-08-09 11:33:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Work Order Project -> Projekt -> Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-10T11:37:40.119Z
UPDATE AD_Field SET AD_Column_ID=583614, Description=NULL, Help=NULL, Name='Fachberater',Updated=TO_TIMESTAMP('2022-08-10 13:37:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703939
;

-- 2022-08-10T11:37:40.123Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581263)
;

-- 2022-08-10T11:37:40.134Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703939
;

-- 2022-08-10T11:37:40.136Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703939)
;

-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-10T11:38:14.104Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540401,Updated=TO_TIMESTAMP('2022-08-10 13:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583614
;

-- 2022-08-10T11:40:10.675Z
INSERT INTO t_alter_column values('c_project','Specialist_Consultant_ID','NUMERIC(10)',null,null)
;

-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-10T11:40:43.141Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-08-10 13:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583614
;

-- 2022-08-10T11:40:46.546Z
INSERT INTO t_alter_column values('c_project','Specialist_Consultant_ID','NUMERIC(10)',null,null)
;

-- Column: C_Project.Specialist_Consultant
-- 2022-08-10T12:00:30.534Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584012
;

-- 2022-08-10T12:00:30.553Z
DELETE FROM AD_Column WHERE AD_Column_ID=584012
;
