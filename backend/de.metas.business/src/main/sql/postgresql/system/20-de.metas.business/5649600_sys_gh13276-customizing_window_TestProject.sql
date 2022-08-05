-- Field: Prüf Projekt -> Projekt -> Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T10:14:19.567Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583964,703906,581100,0,546542,0,TO_TIMESTAMP('2022-08-05 12:14:19','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.',0,'U',0,'Y','Y','Y','N','N','N','N','N','Projekt erstellt',0,380,0,1,1,TO_TIMESTAMP('2022-08-05 12:14:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T10:14:19.569Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703906 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T10:14:19.595Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581100)
;

-- 2022-08-05T10:14:19.607Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703906
;

-- 2022-08-05T10:14:19.609Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703906)
;

-- 2022-08-04T12:49:52.240Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581215,0,TO_TIMESTAMP('2022-08-04 14:49:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abschlussdatum','Abschlussdatum',TO_TIMESTAMP('2022-08-04 14:49:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-04T12:49:52.241Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581215 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-08-04T12:49:57.817Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-04 14:49:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581215 AND AD_Language='de_DE'
;

-- 2022-08-04T12:49:57.818Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581215,'de_DE')
;

-- 2022-08-04T12:49:57.825Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581215,'de_DE')
;

-- 2022-08-04T12:50:11.552Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Completion date', PrintName='Completion date',Updated=TO_TIMESTAMP('2022-08-04 14:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581215 AND AD_Language='en_US'
;

-- 2022-08-04T12:50:11.553Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581215,'en_US')
;

-- 2022-08-04T12:51:08.137Z
UPDATE AD_Element_Trl SET Description='Date on which the order including the report is completed.',Updated=TO_TIMESTAMP('2022-08-04 14:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581215 AND AD_Language='en_US'
;

-- 2022-08-04T12:51:08.138Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581215,'en_US')
;

-- 2022-08-04T12:51:17.144Z
UPDATE AD_Element SET Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.',Updated=TO_TIMESTAMP('2022-08-04 14:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581215
;

-- 2022-08-04T12:51:17.148Z
UPDATE AD_Column SET ColumnName=NULL, Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL WHERE AD_Element_ID=581215
;

-- 2022-08-04T12:51:17.149Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL WHERE AD_Element_ID=581215 AND IsCentrallyMaintained='Y'
;

-- 2022-08-04T12:51:17.150Z
UPDATE AD_Field SET Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581215) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581215)
;

-- 2022-08-04T12:51:17.155Z
UPDATE AD_Tab SET Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581215
;

-- 2022-08-04T12:51:17.156Z
UPDATE AD_WINDOW SET Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL WHERE AD_Element_ID = 581215
;

-- 2022-08-04T12:51:17.158Z
UPDATE AD_Menu SET   Name = 'Abschlussdatum', Description = 'Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581215
;

-- 2022-08-04T12:51:27.378Z
UPDATE AD_Element_Trl SET Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.',Updated=TO_TIMESTAMP('2022-08-04 14:51:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581215 AND AD_Language='de_DE'
;

-- 2022-08-04T12:51:27.380Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581215,'de_DE')
;

-- 2022-08-04T12:51:27.386Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581215,'de_DE')
;

-- 2022-08-04T12:51:27.387Z
UPDATE AD_Column SET ColumnName=NULL, Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL WHERE AD_Element_ID=581215
;

-- 2022-08-04T12:51:27.387Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL WHERE AD_Element_ID=581215 AND IsCentrallyMaintained='Y'
;

-- 2022-08-04T12:51:27.388Z
UPDATE AD_Field SET Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581215) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581215)
;

-- 2022-08-04T12:51:27.393Z
UPDATE AD_Tab SET Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581215
;

-- 2022-08-04T12:51:27.394Z
UPDATE AD_WINDOW SET Name='Abschlussdatum', Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL WHERE AD_Element_ID = 581215
;

-- 2022-08-04T12:51:27.395Z
UPDATE AD_Menu SET   Name = 'Abschlussdatum', Description = 'Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581215
;

-- Field: Prüf Projekt -> Projekt -> Abschlussdatum
-- Column: C_Project.DateFinish
-- 2022-08-04T12:52:47.408Z
UPDATE AD_Field SET AD_Name_ID=581215, Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help=NULL, Name='Abschlussdatum',Updated=TO_TIMESTAMP('2022-08-04 14:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703686
;

-- 2022-08-04T12:52:47.409Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581215)
;

-- 2022-08-04T12:52:47.411Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703686
;

-- 2022-08-04T12:52:47.411Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703686)
;

-- 2022-08-04T12:53:37.894Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581216,0,TO_TIMESTAMP('2022-08-04 14:53:37','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag seitens des Auftraggebers eingegangen ist.','U','Y','Beauftragungsdatum','Beauftragungsdatum',TO_TIMESTAMP('2022-08-04 14:53:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-04T12:53:37.895Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581216 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-08-04T12:53:44.489Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-04 14:53:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581216 AND AD_Language='de_DE'
;

-- 2022-08-04T12:53:44.490Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581216,'de_DE')
;

-- 2022-08-04T12:53:44.499Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581216,'de_DE')
;

-- 2022-08-04T12:54:05.189Z
UPDATE AD_Element_Trl SET Description='Date on which the order was received from the customer.', IsTranslated='Y', Name='Commissioning date', PrintName='Commissioning date',Updated=TO_TIMESTAMP('2022-08-04 14:54:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581216 AND AD_Language='en_US'
;

-- 2022-08-04T12:54:05.192Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581216,'en_US')
;

-- 2022-08-04T13:03:35.924Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581218,0,TO_TIMESTAMP('2022-08-04 15:03:35','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','VA Nummer','VA Nummer',TO_TIMESTAMP('2022-08-04 15:03:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-04T13:03:35.926Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581218 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-08-04T13:03:43.786Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-04 15:03:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581218 AND AD_Language='de_DE'
;

-- 2022-08-04T13:03:43.788Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581218,'de_DE')
;

-- 2022-08-04T13:03:43.796Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581218,'de_DE')
;

-- Field: Prüf Projekt -> Projekt -> VA Nummer
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-08-04T13:10:47.043Z
UPDATE AD_Field SET AD_Name_ID=581218, Description=NULL, Help=NULL, Name='VA Nummer',Updated=TO_TIMESTAMP('2022-08-04 15:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703707
;

-- 2022-08-04T13:10:47.045Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581218)
;

-- 2022-08-04T13:10:47.046Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703707
;

-- 2022-08-04T13:10:47.046Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703707)
;



-- Field: Prüf Projekt -> Projekt -> Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-04T13:28:15.402Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583614,703733,581096,0,546542,0,TO_TIMESTAMP('2022-08-04 15:28:15','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Fachberater',0,380,0,1,1,TO_TIMESTAMP('2022-08-04 15:28:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-04T13:28:15.403Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703733 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-04T13:28:15.405Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581096)
;

-- 2022-08-04T13:28:15.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703733
;

-- 2022-08-04T13:28:15.406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703733)
;

-- Field: Prüf Projekt -> Projekt -> Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-04T13:29:19.422Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2022-08-04 15:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703733
;







-- UI Element: Prüf Projekt -> Projekt.Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-04T13:30:09.858Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703733,0,546542,549668,611200,'F',TO_TIMESTAMP('2022-08-04 15:30:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Fachberater',35,0,0,TO_TIMESTAMP('2022-08-04 15:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Prüf Projekt -> Projekt -> Geschäftspartner-Abteilung
-- Column: C_Project.BPartnerDepartment
-- 2022-08-04T13:41:28.959Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583615,703734,581097,0,546542,0,TO_TIMESTAMP('2022-08-04 15:41:28','YYYY-MM-DD HH24:MI:SS'),100,'Abteilung / Kostenträger des Geschäftspartners',0,'D',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner-Abteilung',0,390,0,1,1,TO_TIMESTAMP('2022-08-04 15:41:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-04T13:41:28.960Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703734 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-04T13:41:28.961Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581097)
;

-- 2022-08-04T13:41:28.962Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703734
;

-- 2022-08-04T13:41:28.964Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703734)
;








-- UI Element: Prüf Projekt -> Projekt.Geschäftspartner-Abteilung
-- Column: C_Project.BPartnerDepartment
-- 2022-08-04T13:42:44.912Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703734,0,546542,549668,611210,'F',TO_TIMESTAMP('2022-08-04 15:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Abteilung / Kostenträger des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Abteilung',20,0,0,TO_TIMESTAMP('2022-08-04 15:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Field: Prüf Projekt -> Projekt -> Beistellungsdatum
-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-08-05T07:08:42.793Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583616,703783,581098,0,546542,0,TO_TIMESTAMP('2022-08-05 09:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Beistellungsdatum',0,410,0,1,1,TO_TIMESTAMP('2022-08-05 09:08:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T07:08:42.796Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703783 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T07:08:42.798Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581098)
;

-- 2022-08-05T07:08:42.802Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703783
;

-- 2022-08-05T07:08:42.803Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703783)
;

-- Field: Prüf Projekt -> Projekt -> Beauftragungsdatum
-- Column: C_Project.DateContract
-- 2022-08-05T07:13:12.188Z
UPDATE AD_Field SET AD_Name_ID=581216, Description='Datum, an dem der Auftrag seitens des Auftraggebers eingegangen ist.', Help=NULL, Name='Beauftragungsdatum',Updated=TO_TIMESTAMP('2022-08-05 09:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703685
;

-- 2022-08-05T07:13:12.191Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581216)
;

-- 2022-08-05T07:13:12.193Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703685
;

-- 2022-08-05T07:13:12.195Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703685)
;

-- UI Element: Prüf Projekt -> Projekt.Datum AE
-- Column: C_Project.DateContract
-- 2022-08-05T07:14:22.862Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611185
;

-- UI Element: Prüf Projekt -> Projekt.Beauftragungsdatum
-- Column: C_Project.DateContract
-- 2022-08-05T07:14:39.452Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703685,0,546542,549668,611254,'F',TO_TIMESTAMP('2022-08-05 09:14:39','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag seitens des Auftraggebers eingegangen ist.','Y','N','N','Y','N','N','N',0,'Beauftragungsdatum',40,0,0,TO_TIMESTAMP('2022-08-05 09:14:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-08-05T07:15:10.063Z
UPDATE AD_UI_Element SET Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.',Updated=TO_TIMESTAMP('2022-08-05 09:15:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611186
;

-- UI Element: Prüf Projekt -> Projekt.Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-05T07:15:51.293Z
UPDATE AD_UI_Element SET Description='Name des Fachberater für den Versuchsauftrag (VA)',Updated=TO_TIMESTAMP('2022-08-05 09:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611200
;

-- UI Element: Prüf Projekt -> Projekt.Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T07:16:36.069Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2022-08-05 09:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611253
;

-- UI Element: Prüf Projekt -> Projekt.Beistellungsdatum
-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-08-05T07:16:58.690Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703783,0,546542,549668,611255,'F',TO_TIMESTAMP('2022-08-05 09:16:58','YYYY-MM-DD HH24:MI:SS'),100,'Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.','Y','N','N','Y','N','N','N',0,'Beistellungsdatum',70,0,0,TO_TIMESTAMP('2022-08-05 09:16:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Prüf Projekt -> Projekt -> Name der Prüfung
-- Column: C_Project.Name
-- 2022-08-05T07:17:45.999Z
UPDATE AD_Field SET AD_Name_ID=581220, Description=NULL, Help=NULL, Name='Name der Prüfung',Updated=TO_TIMESTAMP('2022-08-05 09:17:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703677
;

-- 2022-08-05T07:17:46.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581220)
;

-- 2022-08-05T07:17:46.006Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703677
;

-- 2022-08-05T07:17:46.007Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703677)
;

-- UI Element: Prüf Projekt -> Projekt.Name der Prüfung
-- Column: C_Project.Name
-- 2022-08-05T07:19:06.488Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703677,0,546542,549668,611256,'F',TO_TIMESTAMP('2022-08-05 09:19:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name der Prüfung',90,0,0,TO_TIMESTAMP('2022-08-05 09:19:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Prüf Projekt -> Projekt -> Ersteller Prüfauftrag
-- Column: C_Project.WOOwner
-- 2022-08-05T07:21:43.460Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583670,703784,581121,0,546542,0,TO_TIMESTAMP('2022-08-05 09:21:43','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Ersteller Prüfauftrag',0,420,0,1,1,TO_TIMESTAMP('2022-08-05 09:21:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T07:21:43.462Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T07:21:43.464Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581121)
;

-- 2022-08-05T07:21:43.466Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703784
;

-- 2022-08-05T07:21:43.466Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703784)
;

-- UI Element: Prüf Projekt -> Projekt.Ersteller Prüfauftrag
-- Column: C_Project.WOOwner
-- 2022-08-05T07:23:56.616Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703784,0,546542,549668,611257,'F',TO_TIMESTAMP('2022-08-05 09:23:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Ersteller Prüfauftrag',80,0,0,TO_TIMESTAMP('2022-08-05 09:23:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T07:26:15.899Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581235,0,TO_TIMESTAMP('2022-08-05 09:26:15','YYYY-MM-DD HH24:MI:SS'),100,'Kunden Projektreferenz Nr.','U','Y','Kunden Projektreferenz Nr.','Kunden Projektreferenz Nr.',TO_TIMESTAMP('2022-08-05 09:26:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T07:26:15.901Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581235 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Prüf Projekt -> Projekt -> Kunden Projektreferenz Nr.
-- Column: C_Project.POReference
-- 2022-08-05T07:26:31.539Z
UPDATE AD_Field SET AD_Name_ID=581235, Description='Kunden Projektreferenz Nr.', Help=NULL, Name='Kunden Projektreferenz Nr.',Updated=TO_TIMESTAMP('2022-08-05 09:26:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703692
;

-- 2022-08-05T07:26:31.540Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581235)
;

-- 2022-08-05T07:26:31.544Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703692
;

-- 2022-08-05T07:26:31.545Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703692)
;

-- UI Element: Prüf Projekt -> Projekt.Kunden Projektreferenz Nr.
-- Column: C_Project.POReference
-- 2022-08-05T07:28:05.395Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703692,0,546542,549669,611258,'F',TO_TIMESTAMP('2022-08-05 09:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Kunden Projektreferenz Nr.','Y','N','N','Y','N','N','N',0,'Kunden Projektreferenz Nr.',0,0,0,TO_TIMESTAMP('2022-08-05 09:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Prüf Projekt -> Projekt -> Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-05T07:32:30.945Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583618,703785,581099,0,546542,0,TO_TIMESTAMP('2022-08-05 09:32:30','YYYY-MM-DD HH24:MI:SS'),100,20,'D',0,'Y','Y','Y','N','N','N','N','N','Zieltermin des Geschäftspartners',0,430,0,1,1,TO_TIMESTAMP('2022-08-05 09:32:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T07:32:30.947Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703785 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T07:32:30.948Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581099)
;

-- 2022-08-05T07:32:30.950Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703785
;

-- 2022-08-05T07:32:30.950Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703785)
;

-- UI Element: Prüf Projekt -> Projekt.Version Preisliste
-- Column: C_Project.M_PriceList_Version_ID
-- 2022-08-05T07:35:49.949Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611187
;

-- UI Element: Prüf Projekt -> Projekt.Währung
-- Column: C_Project.C_Currency_ID
-- 2022-08-05T07:35:52.157Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611188
;

-- UI Element: Prüf Projekt -> Projekt.Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-05T07:36:17.628Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703785,0,546542,549669,611259,'F',TO_TIMESTAMP('2022-08-05 09:36:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Zieltermin des Geschäftspartners',10,0,0,TO_TIMESTAMP('2022-08-05 09:36:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T07:36:55.904Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703782,0,546542,549669,611260,'F',TO_TIMESTAMP('2022-08-05 09:36:55','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.','Y','N','N','Y','N','N','N',0,'Projekt erstellt',30,0,0,TO_TIMESTAMP('2022-08-05 09:36:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: R_StatusCategory
-- 2022-08-05T08:19:46.412Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541622,TO_TIMESTAMP('2022-08-05 10:19:46','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','R_StatusCategory',TO_TIMESTAMP('2022-08-05 10:19:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-05T08:19:46.414Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541622 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: R_StatusCategory
-- Table: R_StatusCategory
-- Key: R_StatusCategory.R_StatusCategory_ID
-- 2022-08-05T08:20:26.924Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,14989,0,541622,844,TO_TIMESTAMP('2022-08-05 10:20:26','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N',TO_TIMESTAMP('2022-08-05 10:20:26','YYYY-MM-DD HH24:MI:SS'),100,'R_StatusCategory_ID=540010')
;

-- Name: R_StatusCategory
-- 2022-08-05T08:24:52.829Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540593,'R_StatusCategory_ID=@R_StatusCategory_ID@',TO_TIMESTAMP('2022-08-05 10:24:52','YYYY-MM-DD HH24:MI:SS'),100,'Test Project','D','Y','R_StatusCategory','S',TO_TIMESTAMP('2022-08-05 10:24:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Project.R_Project_Status_ID
-- 2022-08-05T08:27:05.334Z
UPDATE AD_Column SET AD_Reference_Value_ID=541622,Updated=TO_TIMESTAMP('2022-08-05 10:27:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;

-- Column: C_Project.R_Project_Status_ID
-- 2022-08-05T08:28:37.005Z
UPDATE AD_Column SET AD_Val_Rule_ID=540593,Updated=TO_TIMESTAMP('2022-08-05 10:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;

-- Field: Prüf Projekt -> Projekt -> Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-08-05T08:32:27.185Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Tab_ID,AD_Val_Rule_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559693,703787,544077,0,18,541622,546542,540593,0,TO_TIMESTAMP('2022-08-05 10:32:27','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Projektstatus',0,440,0,1,1,TO_TIMESTAMP('2022-08-05 10:32:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T08:32:27.187Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-05T08:32:27.190Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544077)
;

-- 2022-08-05T08:32:27.194Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703787
;

-- 2022-08-05T08:32:27.195Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703787)
;

-- UI Element: Prüf Projekt -> Projekt.Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-08-05T08:36:34.796Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703787,0,546542,549669,611261,'F',TO_TIMESTAMP('2022-08-05 10:36:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektstatus',20,0,0,TO_TIMESTAMP('2022-08-05 10:36:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-05T08:42:27.075Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2022-08-05 10:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611200
;

-- UI Element: Prüf Projekt -> Projekt.VA Nummer
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-08-05T08:43:55.061Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703707,0,546542,549668,611262,'F',TO_TIMESTAMP('2022-08-05 10:43:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'VA Nummer',35,0,0,TO_TIMESTAMP('2022-08-05 10:43:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Geschäftspartner-Abteilung
-- Column: C_Project.BPartnerDepartment
-- 2022-08-05T08:44:19.370Z
UPDATE AD_UI_Element SET SeqNo=37,Updated=TO_TIMESTAMP('2022-08-05 10:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611210
;

-- UI Element: Prüf Projekt -> Projekt.Abschlussdatum
-- Column: C_Project.DateFinish
-- 2022-08-05T08:45:22.654Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703686,0,546542,549668,611263,'F',TO_TIMESTAMP('2022-08-05 10:45:22','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.','Y','N','N','Y','N','N','N',0,'Abschlussdatum',20,0,0,TO_TIMESTAMP('2022-08-05 10:45:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Beauftragungsdatum
-- Column: C_Project.DateContract
-- 2022-08-05T08:45:56.943Z
UPDATE AD_UI_Element SET SeqNo=22,Updated=TO_TIMESTAMP('2022-08-05 10:45:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611254
;

-- UI Element: Prüf Projekt -> Projekt.Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-08-05T08:48:47.648Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611186
;

-- UI Element: Prüf Projekt -> Projekt.Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T08:48:51.425Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611253
;

-- UI Element: Prüf Projekt -> Projekt.Abschlussdatum
-- Column: C_Project.DateFinish
-- 2022-08-05T08:49:42.764Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703686,0,546542,549669,611264,'F',TO_TIMESTAMP('2022-08-05 10:49:42','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.','Y','N','N','Y','N','N','N',0,'Abschlussdatum',25,0,0,TO_TIMESTAMP('2022-08-05 10:49:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T08:51:29.085Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546259,549685,TO_TIMESTAMP('2022-08-05 10:51:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','state',12,TO_TIMESTAMP('2022-08-05 10:51:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Kunden Projektreferenz Nr.
-- Column: C_Project.POReference
-- 2022-08-05T08:53:12.402Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703692,0,546542,549685,611265,'F',TO_TIMESTAMP('2022-08-05 10:53:12','YYYY-MM-DD HH24:MI:SS'),100,'Kunden Projektreferenz Nr.','Y','N','N','Y','N','N','N',0,'Kunden Projektreferenz Nr.',10,0,0,TO_TIMESTAMP('2022-08-05 10:53:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-05T08:53:27.089Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703785,0,546542,549685,611266,'F',TO_TIMESTAMP('2022-08-05 10:53:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Zieltermin des Geschäftspartners',20,0,0,TO_TIMESTAMP('2022-08-05 10:53:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-08-05T08:53:38.347Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703787,0,546542,549685,611267,'F',TO_TIMESTAMP('2022-08-05 10:53:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektstatus',30,0,0,TO_TIMESTAMP('2022-08-05 10:53:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Abschlussdatum
-- Column: C_Project.DateFinish
-- 2022-08-05T08:53:48.733Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703686,0,546542,549685,611268,'F',TO_TIMESTAMP('2022-08-05 10:53:48','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.','Y','N','N','Y','N','N','N',0,'Abschlussdatum',40,0,0,TO_TIMESTAMP('2022-08-05 10:53:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T08:54:01.066Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703782,0,546542,549685,611269,'F',TO_TIMESTAMP('2022-08-05 10:54:00','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.','Y','N','N','Y','N','N','N',0,'Projekt erstellt',50,0,0,TO_TIMESTAMP('2022-08-05 10:54:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T08:54:55.654Z
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2022-08-05 10:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549669
;

-- 2022-08-05T08:54:57.041Z
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2022-08-05 10:54:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549670
;

-- 2022-08-05T08:55:06.743Z
UPDATE AD_UI_ElementGroup SET IsActive='Y',Updated=TO_TIMESTAMP('2022-08-05 10:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549669
;

-- UI Element: Prüf Projekt -> Projekt.Kunden Projektreferenz Nr.
-- Column: C_Project.POReference
-- 2022-08-05T08:55:09.970Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611258
;

-- UI Element: Prüf Projekt -> Projekt.Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-05T08:55:11.681Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611259
;

-- UI Element: Prüf Projekt -> Projekt.Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-08-05T08:55:13.249Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611261
;

-- UI Element: Prüf Projekt -> Projekt.Abschlussdatum
-- Column: C_Project.DateFinish
-- 2022-08-05T08:55:14.746Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611264
;

-- UI Element: Prüf Projekt -> Projekt.Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-05T08:55:16.393Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611260
;

-- UI Element: Prüf Projekt -> Projekt.Währung
-- Column: C_Project.C_Currency_ID
-- 2022-08-05T08:55:34.566Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703696,0,546542,549669,611270,'F',TO_TIMESTAMP('2022-08-05 10:55:34','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',10,0,0,TO_TIMESTAMP('2022-08-05 10:55:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt -> Projekt.Währung
-- Column: C_Project.C_Currency_ID
-- 2022-08-05T08:55:51.438Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-08-05 10:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611270
;

-- UI Element: Prüf Projekt -> Projekt.Version Preisliste
-- Column: C_Project.M_PriceList_Version_ID
-- 2022-08-05T08:56:04.705Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703695,0,546542,549669,611271,'F',TO_TIMESTAMP('2022-08-05 10:56:04','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','N','Y','N','N','N',0,'Version Preisliste',10,0,0,TO_TIMESTAMP('2022-08-05 10:56:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-05T08:56:23.866Z
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2022-08-05 10:56:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549669
;

