-- Field: Work Order Project -> Projekt -> Datum Auftragseingang
-- Column: C_Project.DateContract
-- 2022-08-09T07:16:58.730Z
UPDATE AD_Field SET AD_Name_ID=1556, Description='Datum des Auftragseingangs', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.', Name='Datum Auftragseingang',Updated=TO_TIMESTAMP('2022-08-09 09:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697979
;

-- 2022-08-09T07:16:58.782Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1556)
;

-- 2022-08-09T07:16:58.823Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697979
;

-- 2022-08-09T07:16:58.827Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697979)
;

-- Field: Work Order Project -> Projekt -> Abschlussdatum
-- Column: C_Project.DateFinish
-- 2022-08-09T07:18:11.140Z
UPDATE AD_Field SET AD_Name_ID=1557, Description='Datum, an dem der Auftrag inklusive Bericht abgeschlossen ist.', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an', Name='Abschlussdatum',Updated=TO_TIMESTAMP('2022-08-09 09:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697980
;

-- 2022-08-09T07:18:11.142Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1557)
;

-- 2022-08-09T07:18:11.151Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697980
;

-- 2022-08-09T07:18:11.153Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697980)
;

-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-09T07:19:59.419Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540401, FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-08-09 09:19:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583614
;

-- Field: Work Order Project -> Projekt -> Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-09T07:20:15.392Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583614,703932,581096,0,546289,0,TO_TIMESTAMP('2022-08-09 09:20:15','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Fachberater',0,380,0,1,1,TO_TIMESTAMP('2022-08-09 09:20:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T07:20:15.394Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T07:20:15.396Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581096)
;

-- 2022-08-09T07:20:15.399Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703932
;

-- 2022-08-09T07:20:15.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703932)
;

-- Field: Work Order Project -> Projekt -> VA Nummer
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-08-09T07:21:34.483Z
UPDATE AD_Field SET AD_Name_ID=580859, Description='Externe Projektreferenz Nummer', Help=NULL, Name='VA Nummer',Updated=TO_TIMESTAMP('2022-08-09 09:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698001
;

-- 2022-08-09T07:21:34.485Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580859)
;

-- 2022-08-09T07:21:34.491Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698001
;

-- 2022-08-09T07:21:34.492Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698001)
;

-- Field: Work Order Project -> Projekt -> Geschäftspartner-Abteilung
-- Column: C_Project.BPartnerDepartment
-- 2022-08-09T07:23:03.975Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583615,703933,581097,0,546289,0,TO_TIMESTAMP('2022-08-09 09:23:03','YYYY-MM-DD HH24:MI:SS'),100,'Abteilung / Kostenträger des Geschäftspartners',0,'D',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner-Abteilung',0,390,0,1,1,TO_TIMESTAMP('2022-08-09 09:23:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T07:23:03.978Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T07:23:03.980Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581097)
;

-- 2022-08-09T07:23:03.983Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703933
;

-- 2022-08-09T07:23:03.984Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703933)
;

-- Field: Work Order Project -> Projekt -> Beistellungsdatum
-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-08-09T07:25:18.936Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583616,703934,581098,0,546289,0,TO_TIMESTAMP('2022-08-09 09:25:18','YYYY-MM-DD HH24:MI:SS'),100,'Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Beistellungsdatum',0,400,0,1,1,TO_TIMESTAMP('2022-08-09 09:25:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T07:25:18.938Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T07:25:18.940Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581098)
;

-- 2022-08-09T07:25:18.944Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703934
;

-- 2022-08-09T07:25:18.945Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703934)
;

-- Field: Work Order Project -> Projekt -> Name der Prüfung
-- Column: C_Project.Name
-- 2022-08-09T07:28:09.024Z
UPDATE AD_Field SET AD_Name_ID=581237, Description='Name der Prüfung', Help=NULL, Name='Name der Prüfung',Updated=TO_TIMESTAMP('2022-08-09 09:28:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697971
;

-- 2022-08-09T07:28:09.026Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581237)
;

-- 2022-08-09T07:28:09.030Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697971
;

-- 2022-08-09T07:28:09.032Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697971)
;

-- Field: Work Order Project -> Projekt -> Ersteller Prüfauftrag
-- Column: C_Project.WOOwner
-- 2022-08-09T07:29:35.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583670,703935,581121,0,546289,0,TO_TIMESTAMP('2022-08-09 09:29:35','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Ersteller Prüfauftrag',0,410,0,1,1,TO_TIMESTAMP('2022-08-09 09:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T07:29:35.368Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T07:29:35.370Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581121)
;

-- 2022-08-09T07:29:35.372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703935
;

-- 2022-08-09T07:29:35.373Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703935)
;

-- Field: Work Order Project -> Projekt -> Kunden Projektreferenz Nr.
-- Column: C_Project.POReference
-- 2022-08-09T07:31:37.335Z
UPDATE AD_Field SET AD_Name_ID=952, Description='Referenz-Nummer des Kunden', Help='The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.', Name='Kunden Projektreferenz Nr.',Updated=TO_TIMESTAMP('2022-08-09 09:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697986
;

-- 2022-08-09T07:31:37.337Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952)
;

-- 2022-08-09T07:31:37.387Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697986
;

-- 2022-08-09T07:31:37.388Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697986)
;

-- Field: Work Order Project -> Projekt -> Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-09T07:32:47.349Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583618,703936,581099,0,546289,0,TO_TIMESTAMP('2022-08-09 09:32:47','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','N','N','Zieltermin des Geschäftspartners',0,420,0,1,1,TO_TIMESTAMP('2022-08-09 09:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T07:32:47.351Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703936 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T07:32:47.352Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581099)
;

-- 2022-08-09T07:32:47.354Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703936
;

-- 2022-08-09T07:32:47.355Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703936)
;

-- Field: Work Order Project -> Projekt -> Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-09T07:34:04.267Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583964,703937,581100,0,546289,0,TO_TIMESTAMP('2022-08-09 09:34:04','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Projekt erstellt',0,430,0,1,1,TO_TIMESTAMP('2022-08-09 09:34:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T07:34:04.269Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T07:34:04.271Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581100)
;

-- 2022-08-09T07:34:04.274Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703937
;

-- 2022-08-09T07:34:04.275Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703937)
;

-- Field: Work Order Project -> Projekt -> Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-08-09T07:36:18.424Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Tab_ID,AD_Val_Rule_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559693,703938,544077,0,18,540849,546289,540594,0,TO_TIMESTAMP('2022-08-09 09:36:18','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Projektstatus',0,440,0,1,1,TO_TIMESTAMP('2022-08-09 09:36:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T07:36:18.426Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703938 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-09T07:36:18.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544077)
;

-- 2022-08-09T07:36:18.434Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703938
;

-- 2022-08-09T07:36:18.434Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703938)
;

-- UI Element: Work Order Project -> Projekt.Fachberater
-- Column: C_Project.Specialist_Consultant_ID
-- 2022-08-09T07:51:17.291Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703932,0,546289,549194,611349,'F',TO_TIMESTAMP('2022-08-09 09:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Fachberater',35,0,0,TO_TIMESTAMP('2022-08-09 09:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Geschäftspartner-Abteilung
-- Column: C_Project.BPartnerDepartment
-- 2022-08-09T07:52:32.519Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703933,0,546289,549194,611350,'F',TO_TIMESTAMP('2022-08-09 09:52:32','YYYY-MM-DD HH24:MI:SS'),100,'Abteilung / Kostenträger des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Abteilung',37,0,0,TO_TIMESTAMP('2022-08-09 09:52:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Beistellungsdatum
-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-08-09T07:54:12.736Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703934,0,546289,549194,611351,'F',TO_TIMESTAMP('2022-08-09 09:54:12','YYYY-MM-DD HH24:MI:SS'),100,'Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.','Y','N','N','Y','N','N','N',0,'Beistellungsdatum',60,0,0,TO_TIMESTAMP('2022-08-09 09:54:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Ersteller Prüfauftrag
-- Column: C_Project.WOOwner
-- 2022-08-09T07:54:28.144Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703935,0,546289,549194,611352,'F',TO_TIMESTAMP('2022-08-09 09:54:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Ersteller Prüfauftrag',70,0,0,TO_TIMESTAMP('2022-08-09 09:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Kunden Projektreferenz Nr.
-- Column: C_Project.POReference
-- 2022-08-09T07:55:28.935Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697986,0,546289,549194,611353,'F',TO_TIMESTAMP('2022-08-09 09:55:28','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Kunden Projektreferenz Nr.',80,0,0,TO_TIMESTAMP('2022-08-09 09:55:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Kunden Projektreferenz Nr.
-- Column: C_Project.POReference
-- 2022-08-09T07:56:09.526Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611353
;

-- 2022-08-09T07:56:21.739Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545971,549711,TO_TIMESTAMP('2022-08-09 09:56:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','state',12,TO_TIMESTAMP('2022-08-09 09:56:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Kunden Projektreferenz Nr.
-- Column: C_Project.POReference
-- 2022-08-09T07:56:46.092Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697986,0,546289,549711,611354,'F',TO_TIMESTAMP('2022-08-09 09:56:45','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Kunden Projektreferenz Nr.',10,0,0,TO_TIMESTAMP('2022-08-09 09:56:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Zieltermin des Geschäftspartners
-- Column: C_Project.BPartnerTargetDate
-- 2022-08-09T07:56:59.330Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703936,0,546289,549711,611355,'F',TO_TIMESTAMP('2022-08-09 09:56:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Zieltermin des Geschäftspartners',20,0,0,TO_TIMESTAMP('2022-08-09 09:56:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Projektstatus
-- Column: C_Project.R_Project_Status_ID
-- 2022-08-09T07:57:13.695Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703938,0,546289,549711,611356,'F',TO_TIMESTAMP('2022-08-09 09:57:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektstatus',30,0,0,TO_TIMESTAMP('2022-08-09 09:57:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Projekt erstellt
-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-09T07:57:30.844Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703937,0,546289,549711,611357,'F',TO_TIMESTAMP('2022-08-09 09:57:30','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.','Y','N','N','Y','N','N','N',0,'Projekt erstellt',40,0,0,TO_TIMESTAMP('2022-08-09 09:57:30','YYYY-MM-DD HH24:MI:SS'),100)
;

