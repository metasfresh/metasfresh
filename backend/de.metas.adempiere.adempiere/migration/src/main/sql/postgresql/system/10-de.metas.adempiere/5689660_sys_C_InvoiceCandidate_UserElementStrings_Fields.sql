-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Invoice candidate
-- Column: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2023-05-29T09:10:26.471Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,544906,716027,0,540279,TO_TIMESTAMP('2023-05-29 12:10:25','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Invoice candidate',TO_TIMESTAMP('2023-05-29 12:10:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:26.518Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:26.562Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541266) 
;

-- 2023-05-29T09:10:26.622Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716027
;

-- 2023-05-29T09:10:26.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716027)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Invoicable quantity in price UOM
-- Column: C_Invoice_Candidate.QtyToInvoiceInPriceUOM
-- 2023-05-29T09:10:27.377Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568528,716028,0,540279,TO_TIMESTAMP('2023-05-29 12:10:26','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Invoicable quantity in price UOM',TO_TIMESTAMP('2023-05-29 12:10:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:27.420Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:27.464Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542529) 
;

-- 2023-05-29T09:10:27.510Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716028
;

-- 2023-05-29T09:10:27.551Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716028)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Assignment
-- Column: C_Invoice_Candidate.UserElementString1
-- 2023-05-29T09:10:28.267Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572500,716029,0,540279,TO_TIMESTAMP('2023-05-29 12:10:27','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Assignment',TO_TIMESTAMP('2023-05-29 12:10:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:28.312Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:28.357Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-05-29T09:10:28.408Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716029
;

-- 2023-05-29T09:10:28.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716029)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString2
-- Column: C_Invoice_Candidate.UserElementString2
-- 2023-05-29T09:10:29.159Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572501,716030,0,540279,TO_TIMESTAMP('2023-05-29 12:10:28','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2023-05-29 12:10:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:29.204Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:29.249Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-05-29T09:10:29.301Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716030
;

-- 2023-05-29T09:10:29.345Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716030)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString3
-- Column: C_Invoice_Candidate.UserElementString3
-- 2023-05-29T09:10:30.092Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572502,716031,0,540279,TO_TIMESTAMP('2023-05-29 12:10:29','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2023-05-29 12:10:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:30.134Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:30.180Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-05-29T09:10:30.232Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716031
;

-- 2023-05-29T09:10:30.276Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716031)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString4
-- Column: C_Invoice_Candidate.UserElementString4
-- 2023-05-29T09:10:31.042Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572503,716032,0,540279,TO_TIMESTAMP('2023-05-29 12:10:30','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-05-29 12:10:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:31.085Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:31.128Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-05-29T09:10:31.180Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716032
;

-- 2023-05-29T09:10:31.221Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716032)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString5
-- Column: C_Invoice_Candidate.UserElementString5
-- 2023-05-29T09:10:31.934Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572504,716033,0,540279,TO_TIMESTAMP('2023-05-29 12:10:31','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-05-29 12:10:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:31.997Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:32.047Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-05-29T09:10:32.094Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716033
;

-- 2023-05-29T09:10:32.136Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716033)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString6
-- Column: C_Invoice_Candidate.UserElementString6
-- 2023-05-29T09:10:32.837Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572505,716034,0,540279,TO_TIMESTAMP('2023-05-29 12:10:32','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-05-29 12:10:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:32.881Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:32.923Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-05-29T09:10:33.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716034
;

-- 2023-05-29T09:10:33.055Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716034)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString7
-- Column: C_Invoice_Candidate.UserElementString7
-- 2023-05-29T09:10:33.783Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572506,716035,0,540279,TO_TIMESTAMP('2023-05-29 12:10:33','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-05-29 12:10:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:33.825Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:33.868Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-05-29T09:10:33.915Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716035
;

-- 2023-05-29T09:10:33.956Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716035)
;
--
-- -- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Project
-- -- Column: C_Invoice_Candidate.C_Project_ID
-- -- 2023-05-29T09:10:34.648Z
-- INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572537,716036,0,540279,TO_TIMESTAMP('2023-05-29 12:10:34','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'de.metas.invoicecandidate','A Project allows you to track and control internal or external activities.','Y','Y','N','N','N','N','N','Project',TO_TIMESTAMP('2023-05-29 12:10:34','YYYY-MM-DD HH24:MI:SS'),100)
-- ;
--
-- -- 2023-05-29T09:10:34.692Z
-- INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
-- ;

-- 2023-05-29T09:10:34.736Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2023-05-29T09:10:34.874Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716036
;

-- 2023-05-29T09:10:34.919Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716036)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Campaign
-- Column: C_Invoice_Candidate.C_Campaign_ID
-- 2023-05-29T09:10:35.652Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572538,716037,0,540279,TO_TIMESTAMP('2023-05-29 12:10:35','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',10,'de.metas.invoicecandidate','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','Y','N','N','N','N','N','Campaign',TO_TIMESTAMP('2023-05-29 12:10:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:35.696Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:35.739Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550) 
;

-- 2023-05-29T09:10:35.847Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716037
;

-- 2023-05-29T09:10:35.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716037)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Rechnungsstandort (Address)
-- Column: C_Invoice_Candidate.Bill_Location_Value_ID
-- 2023-05-29T09:10:36.586Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573528,716038,0,540279,TO_TIMESTAMP('2023-05-29 12:10:35','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Rechnungsstandort (Address)',TO_TIMESTAMP('2023-05-29 12:10:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:36.629Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:36.675Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579024) 
;

-- 2023-05-29T09:10:36.722Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716038
;

-- 2023-05-29T09:10:36.764Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716038)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Rechungsadresse abw.
-- Column: C_Invoice_Candidate.Bill_Location_Override_Value_ID
-- 2023-05-29T09:10:37.498Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573529,716039,0,540279,TO_TIMESTAMP('2023-05-29 12:10:36','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Rechungsadresse abw.',TO_TIMESTAMP('2023-05-29 12:10:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:37.544Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:37.587Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579030) 
;

-- 2023-05-29T09:10:37.633Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716039
;

-- 2023-05-29T09:10:37.675Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716039)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Shipping Location
-- Column: C_Invoice_Candidate.C_Shipping_Location_ID
-- 2023-05-29T09:10:38.371Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578954,716040,0,540279,TO_TIMESTAMP('2023-05-29 12:10:37','YYYY-MM-DD HH24:MI:SS'),100,20,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Shipping Location',TO_TIMESTAMP('2023-05-29 12:10:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:38.416Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:38.466Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580381) 
;

-- 2023-05-29T09:10:38.513Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716040
;

-- 2023-05-29T09:10:38.555Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716040)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Payment Rule
-- Column: C_Invoice_Candidate.PaymentRule
-- 2023-05-29T09:10:39.277Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579213,716041,0,540279,TO_TIMESTAMP('2023-05-29 12:10:38','YYYY-MM-DD HH24:MI:SS'),100,'How you pay the invoice',1,'de.metas.invoicecandidate','The Payment Rule indicates the method of invoice payment.','Y','Y','N','N','N','N','N','Payment Rule',TO_TIMESTAMP('2023-05-29 12:10:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:39.320Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:39.363Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1143) 
;

-- 2023-05-29T09:10:39.439Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716041
;

-- 2023-05-29T09:10:39.481Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716041)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Incoterm Location
-- Column: C_Invoice_Candidate.IncotermLocation
-- 2023-05-29T09:10:40.175Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579246,716042,0,540279,TO_TIMESTAMP('2023-05-29 12:10:39','YYYY-MM-DD HH24:MI:SS'),100,'Location to be specified for commercial clause',2000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Incoterm Location',TO_TIMESTAMP('2023-05-29 12:10:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:40.220Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:40.263Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608) 
;

-- 2023-05-29T09:10:40.321Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716042
;

-- 2023-05-29T09:10:40.363Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716042)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Flatrate Term
-- Column: C_Invoice_Candidate.C_Flatrate_Term_ID
-- 2023-05-29T09:10:41.069Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579362,716043,0,540279,TO_TIMESTAMP('2023-05-29 12:10:40','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Flatrate Term',TO_TIMESTAMP('2023-05-29 12:10:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:41.118Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:41.160Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447) 
;

-- 2023-05-29T09:10:41.219Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716043
;

-- 2023-05-29T09:10:41.263Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716043)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Inputsource
-- Column: C_Invoice_Candidate.AD_InputDataSource_ID
-- 2023-05-29T09:10:41.949Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582930,716044,0,540279,TO_TIMESTAMP('2023-05-29 12:10:41','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Inputsource',TO_TIMESTAMP('2023-05-29 12:10:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:41.995Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:42.084Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541291) 
;

-- 2023-05-29T09:10:42.136Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716044
;

-- 2023-05-29T09:10:42.178Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716044)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Additional Text for Invoice
-- Column: C_Invoice_Candidate.InvoiceAdditionalText
-- 2023-05-29T09:10:42.862Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585923,716045,0,540279,TO_TIMESTAMP('2023-05-29 12:10:42','YYYY-MM-DD HH24:MI:SS'),100,'',1024,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Additional Text for Invoice',TO_TIMESTAMP('2023-05-29 12:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:42.905Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:42.950Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582034) 
;

-- 2023-05-29T09:10:42.999Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716045
;

-- 2023-05-29T09:10:43.041Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716045)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Do not show Country of Origin
-- Column: C_Invoice_Candidate.IsNotShowOriginCountry
-- 2023-05-29T09:10:43.726Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585926,716046,0,540279,TO_TIMESTAMP('2023-05-29 12:10:43','YYYY-MM-DD HH24:MI:SS'),100,'If is NO, then the Country of Origin of the products is displayed in the invoice report',1,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Do not show Country of Origin',TO_TIMESTAMP('2023-05-29 12:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:43.769Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:43.813Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582035) 
;

-- 2023-05-29T09:10:43.861Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716046
;

-- 2023-05-29T09:10:43.903Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716046)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Payment Instruction
-- Column: C_Invoice_Candidate.C_PaymentInstruction_ID
-- 2023-05-29T09:10:44.634Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586142,716047,0,540279,TO_TIMESTAMP('2023-05-29 12:10:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Payment Instruction',TO_TIMESTAMP('2023-05-29 12:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:44.678Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:44.722Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582077) 
;

-- 2023-05-29T09:10:44.768Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716047
;

-- 2023-05-29T09:10:44.812Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716047)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Sales Order
-- Column: C_Invoice_Candidate.C_OrderSO_ID
-- 2023-05-29T09:10:45.512Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586275,716048,0,540279,TO_TIMESTAMP('2023-05-29 12:10:44','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','','Y','Y','N','N','N','N','N','Sales Order',TO_TIMESTAMP('2023-05-29 12:10:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:45.554Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:45.597Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543479) 
;

-- 2023-05-29T09:10:45.647Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716048
;

-- 2023-05-29T09:10:45.688Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716048)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Business Partner (2)
-- Column: C_Invoice_Candidate.C_BPartner2_ID
-- 2023-05-29T09:10:46.430Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586301,716049,0,540279,TO_TIMESTAMP('2023-05-29 12:10:45','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Business Partner (2)',TO_TIMESTAMP('2023-05-29 12:10:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T09:10:46.475Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T09:10:46.518Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582129) 
;

-- 2023-05-29T09:10:46.571Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716049
;

-- 2023-05-29T09:10:46.614Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716049)
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Assignment
-- Column: C_Invoice_Candidate.UserElementString1
-- 2023-05-29T12:45:24.956Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716029,0,540279,540056,617897,'F',TO_TIMESTAMP('2023-05-29 15:45:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Assignment',1060,0,0,TO_TIMESTAMP('2023-05-29 15:45:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString2
-- Column: C_Invoice_Candidate.UserElementString2
-- 2023-05-29T12:45:37.869Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716030,0,540279,540056,617898,'F',TO_TIMESTAMP('2023-05-29 15:45:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString2',1070,0,0,TO_TIMESTAMP('2023-05-29 15:45:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString3
-- Column: C_Invoice_Candidate.UserElementString3
-- 2023-05-29T12:45:52.989Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716031,0,540279,540056,617899,'F',TO_TIMESTAMP('2023-05-29 15:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString3',1080,0,0,TO_TIMESTAMP('2023-05-29 15:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString4
-- Column: C_Invoice_Candidate.UserElementString4
-- 2023-05-29T12:46:08.860Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716032,0,540279,540056,617900,'F',TO_TIMESTAMP('2023-05-29 15:46:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString4',1090,0,0,TO_TIMESTAMP('2023-05-29 15:46:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString5
-- Column: C_Invoice_Candidate.UserElementString5
-- 2023-05-29T12:46:36.869Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716033,0,540279,540056,617901,'F',TO_TIMESTAMP('2023-05-29 15:46:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString5',1100,0,0,TO_TIMESTAMP('2023-05-29 15:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString6
-- Column: C_Invoice_Candidate.UserElementString6
-- 2023-05-29T12:46:50.723Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716034,0,540279,540056,617902,'F',TO_TIMESTAMP('2023-05-29 15:46:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString6',1110,0,0,TO_TIMESTAMP('2023-05-29 15:46:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString7
-- Column: C_Invoice_Candidate.UserElementString7
-- 2023-05-29T12:47:04.797Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716035,0,540279,540056,617903,'F',TO_TIMESTAMP('2023-05-29 15:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString7',1120,0,0,TO_TIMESTAMP('2023-05-29 15:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Assignment
-- Column: C_Invoice_Candidate.UserElementString1
-- 2023-05-29T12:47:40.977Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 15:47:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716029
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString2
-- Column: C_Invoice_Candidate.UserElementString2
-- 2023-05-29T12:47:48.544Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 15:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716030
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString3
-- Column: C_Invoice_Candidate.UserElementString3
-- 2023-05-29T12:47:56.609Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 15:47:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716031
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString4
-- Column: C_Invoice_Candidate.UserElementString4
-- 2023-05-29T12:48:04.243Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 15:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716032
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString5
-- Column: C_Invoice_Candidate.UserElementString5
-- 2023-05-29T12:48:12.373Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 15:48:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716033
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString6
-- Column: C_Invoice_Candidate.UserElementString6
-- 2023-05-29T12:48:20.938Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 15:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716034
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> UserElementString7
-- Column: C_Invoice_Candidate.UserElementString7
-- 2023-05-29T12:48:28.303Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716035
;




-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Assignment
-- Column: C_Invoice_Candidate.UserElementString1
-- 2023-05-30T08:27:43.009Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-05-30 11:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617897
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString2
-- Column: C_Invoice_Candidate.UserElementString2
-- 2023-05-30T08:27:45.842Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-05-30 11:27:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617898
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString3
-- Column: C_Invoice_Candidate.UserElementString3
-- 2023-05-30T08:27:46.989Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-05-30 11:27:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617899
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString4
-- Column: C_Invoice_Candidate.UserElementString4
-- 2023-05-30T08:27:48.096Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-05-30 11:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617900
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString5
-- Column: C_Invoice_Candidate.UserElementString5
-- 2023-05-30T08:27:49.150Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-05-30 11:27:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617901
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString6
-- Column: C_Invoice_Candidate.UserElementString6
-- 2023-05-30T08:27:51.482Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-05-30 11:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617902
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString7
-- Column: C_Invoice_Candidate.UserElementString7
-- 2023-05-30T08:27:54.420Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-05-30 11:27:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617903
;












-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Assignment
-- Column: C_Invoice_Candidate.UserElementString1
-- 2023-05-30T08:39:35.385Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572500,716123,0,543052,TO_TIMESTAMP('2023-05-30 11:39:34','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Assignment',TO_TIMESTAMP('2023-05-30 11:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:35.418Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716123 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:35.450Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-05-30T08:39:35.489Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716123
;

-- 2023-05-30T08:39:35.520Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716123)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString2
-- Column: C_Invoice_Candidate.UserElementString2
-- 2023-05-30T08:39:36.119Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572501,716124,0,543052,TO_TIMESTAMP('2023-05-30 11:39:35','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2023-05-30 11:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:36.151Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716124 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:36.184Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-05-30T08:39:36.217Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716124
;

-- 2023-05-30T08:39:36.247Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716124)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString3
-- Column: C_Invoice_Candidate.UserElementString3
-- 2023-05-30T08:39:36.902Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572502,716125,0,543052,TO_TIMESTAMP('2023-05-30 11:39:36','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2023-05-30 11:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:36.934Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716125 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:36.965Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-05-30T08:39:36.999Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716125
;

-- 2023-05-30T08:39:37.029Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716125)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString4
-- Column: C_Invoice_Candidate.UserElementString4
-- 2023-05-30T08:39:37.700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572503,716126,0,543052,TO_TIMESTAMP('2023-05-30 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-05-30 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:37.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716126 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:37.766Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-05-30T08:39:37.801Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716126
;

-- 2023-05-30T08:39:37.837Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716126)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString5
-- Column: C_Invoice_Candidate.UserElementString5
-- 2023-05-30T08:39:38.488Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572504,716127,0,543052,TO_TIMESTAMP('2023-05-30 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-05-30 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:38.520Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716127 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:38.555Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-05-30T08:39:38.594Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716127
;

-- 2023-05-30T08:39:38.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716127)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString6
-- Column: C_Invoice_Candidate.UserElementString6
-- 2023-05-30T08:39:39.259Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572505,716128,0,543052,TO_TIMESTAMP('2023-05-30 11:39:38','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-05-30 11:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:39.290Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716128 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:39.321Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-05-30T08:39:39.365Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716128
;

-- 2023-05-30T08:39:39.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716128)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString7
-- Column: C_Invoice_Candidate.UserElementString7
-- 2023-05-30T08:39:39.995Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572506,716129,0,543052,TO_TIMESTAMP('2023-05-30 11:39:39','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-05-30 11:39:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:40.028Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716129 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:40.060Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-05-30T08:39:40.094Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716129
;

-- 2023-05-30T08:39:40.125Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716129)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Project
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2023-05-30T08:39:40.710Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572537,716130,0,543052,TO_TIMESTAMP('2023-05-30 11:39:40','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'de.metas.invoicecandidate','A Project allows you to track and control internal or external activities.','Y','Y','N','N','N','N','N','Project',TO_TIMESTAMP('2023-05-30 11:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:40.742Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716130 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:40.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2023-05-30T08:39:40.829Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716130
;

-- 2023-05-30T08:39:40.859Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716130)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Campaign
-- Column: C_Invoice_Candidate.C_Campaign_ID
-- 2023-05-30T08:39:41.437Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572538,716131,0,543052,TO_TIMESTAMP('2023-05-30 11:39:40','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',10,'de.metas.invoicecandidate','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','Y','N','N','N','N','N','Campaign',TO_TIMESTAMP('2023-05-30 11:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:41.469Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716131 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:41.501Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550) 
;

-- 2023-05-30T08:39:41.546Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716131
;

-- 2023-05-30T08:39:41.578Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716131)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Packing Material
-- Column: C_Invoice_Candidate.packingmaterialname
-- 2023-05-30T08:39:42.130Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572624,716132,0,543052,TO_TIMESTAMP('2023-05-30 11:39:41','YYYY-MM-DD HH24:MI:SS'),100,500,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Packing Material',TO_TIMESTAMP('2023-05-30 11:39:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:42.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716132 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:42.194Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542326) 
;

-- 2023-05-30T08:39:42.231Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716132
;

-- 2023-05-30T08:39:42.269Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716132)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Rechnungsstandort (Address)
-- Column: C_Invoice_Candidate.Bill_Location_Value_ID
-- 2023-05-30T08:39:42.801Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573528,716133,0,543052,TO_TIMESTAMP('2023-05-30 11:39:42','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Rechnungsstandort (Address)',TO_TIMESTAMP('2023-05-30 11:39:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:42.833Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716133 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:42.864Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579024) 
;

-- 2023-05-30T08:39:42.898Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716133
;

-- 2023-05-30T08:39:42.930Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716133)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Rechungsadresse abw.
-- Column: C_Invoice_Candidate.Bill_Location_Override_Value_ID
-- 2023-05-30T08:39:43.544Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573529,716134,0,543052,TO_TIMESTAMP('2023-05-30 11:39:42','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Rechungsadresse abw.',TO_TIMESTAMP('2023-05-30 11:39:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:43.575Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716134 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:43.611Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579030) 
;

-- 2023-05-30T08:39:43.647Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716134
;

-- 2023-05-30T08:39:43.678Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716134)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Invoicing error
-- Column: C_Invoice_Candidate.IsInvoicingError
-- 2023-05-30T08:39:44.221Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574114,716135,0,543052,TO_TIMESTAMP('2023-05-30 11:39:43','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Invoicing error',TO_TIMESTAMP('2023-05-30 11:39:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:44.254Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:44.286Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579262) 
;

-- 2023-05-30T08:39:44.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716135
;

-- 2023-05-30T08:39:44.353Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716135)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Error message
-- Column: C_Invoice_Candidate.InvoicingErrorMsg
-- 2023-05-30T08:39:44.935Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574115,716136,0,543052,TO_TIMESTAMP('2023-05-30 11:39:44','YYYY-MM-DD HH24:MI:SS'),100,'Error that occured while metasfresh tried to invoice this record.',2000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Error message',TO_TIMESTAMP('2023-05-30 11:39:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:44.966Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716136 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:44.997Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579263) 
;

-- 2023-05-30T08:39:45.034Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716136
;

-- 2023-05-30T08:39:45.064Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716136)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Async Batch
-- Column: C_Invoice_Candidate.C_Async_Batch_ID
-- 2023-05-30T08:39:45.641Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575016,716137,0,543052,TO_TIMESTAMP('2023-05-30 11:39:45','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Async Batch',TO_TIMESTAMP('2023-05-30 11:39:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:45.673Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716137 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:45.707Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542581) 
;

-- 2023-05-30T08:39:45.744Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716137
;

-- 2023-05-30T08:39:45.775Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716137)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Quantity (stock unit)
-- Column: C_Invoice_Candidate.QtyPicked
-- 2023-05-30T08:39:46.312Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577693,716138,0,543052,TO_TIMESTAMP('2023-05-30 11:39:45','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Quantity (stock unit)',TO_TIMESTAMP('2023-05-30 11:39:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:46.344Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716138 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:46.378Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542270) 
;

-- 2023-05-30T08:39:46.415Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716138
;

-- 2023-05-30T08:39:46.477Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716138)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> QtyPickedInUOM
-- Column: C_Invoice_Candidate.QtyPickedInUOM
-- 2023-05-30T08:39:47Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577694,716139,0,543052,TO_TIMESTAMP('2023-05-30 11:39:46','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','QtyPickedInUOM',TO_TIMESTAMP('2023-05-30 11:39:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:47.032Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716139 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:47.064Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580026) 
;

-- 2023-05-30T08:39:47.099Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716139
;

-- 2023-05-30T08:39:47.131Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716139)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Shipment Candidate
-- Column: C_Invoice_Candidate.M_ShipmentSchedule_ID
-- 2023-05-30T08:39:47.732Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577695,716140,0,543052,TO_TIMESTAMP('2023-05-30 11:39:47','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Shipment Candidate',TO_TIMESTAMP('2023-05-30 11:39:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:47.769Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716140 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:47.804Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500221) 
;

-- 2023-05-30T08:39:47.840Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716140
;

-- 2023-05-30T08:39:47.871Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716140)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Incoterms
-- Column: C_Invoice_Candidate.C_Incoterms_ID
-- 2023-05-30T08:39:48.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578583,716141,0,543052,TO_TIMESTAMP('2023-05-30 11:39:47','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Incoterms',TO_TIMESTAMP('2023-05-30 11:39:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:48.445Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716141 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:48.522Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927) 
;

-- 2023-05-30T08:39:48.560Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716141
;

-- 2023-05-30T08:39:48.590Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716141)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Shipping Location
-- Column: C_Invoice_Candidate.C_Shipping_Location_ID
-- 2023-05-30T08:39:49.121Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578954,716142,0,543052,TO_TIMESTAMP('2023-05-30 11:39:48','YYYY-MM-DD HH24:MI:SS'),100,20,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Shipping Location',TO_TIMESTAMP('2023-05-30 11:39:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:49.151Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:49.185Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580381) 
;

-- 2023-05-30T08:39:49.219Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716142
;

-- 2023-05-30T08:39:49.250Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716142)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Payment Rule
-- Column: C_Invoice_Candidate.PaymentRule
-- 2023-05-30T08:39:49.824Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579213,716143,0,543052,TO_TIMESTAMP('2023-05-30 11:39:49','YYYY-MM-DD HH24:MI:SS'),100,'How you pay the invoice',1,'de.metas.invoicecandidate','The Payment Rule indicates the method of invoice payment.','Y','Y','N','N','N','N','N','Payment Rule',TO_TIMESTAMP('2023-05-30 11:39:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:49.855Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:49.887Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1143) 
;

-- 2023-05-30T08:39:49.928Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716143
;

-- 2023-05-30T08:39:49.958Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716143)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Incoterm Location
-- Column: C_Invoice_Candidate.IncotermLocation
-- 2023-05-30T08:39:50.537Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579246,716144,0,543052,TO_TIMESTAMP('2023-05-30 11:39:50','YYYY-MM-DD HH24:MI:SS'),100,'Location to be specified for commercial clause',2000,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Incoterm Location',TO_TIMESTAMP('2023-05-30 11:39:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:50.567Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716144 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:50.599Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608) 
;

-- 2023-05-30T08:39:50.636Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716144
;

-- 2023-05-30T08:39:50.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716144)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> eMail
-- Column: C_Invoice_Candidate.EMail
-- 2023-05-30T08:39:51.204Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579252,716145,0,543052,TO_TIMESTAMP('2023-05-30 11:39:50','YYYY-MM-DD HH24:MI:SS'),100,'',250,'de.metas.invoicecandidate','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','Y','N','N','N','N','N','eMail',TO_TIMESTAMP('2023-05-30 11:39:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:51.237Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716145 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:51.267Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881) 
;

-- 2023-05-30T08:39:51.309Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716145
;

-- 2023-05-30T08:39:51.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716145)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Flatrate Term
-- Column: C_Invoice_Candidate.C_Flatrate_Term_ID
-- 2023-05-30T08:39:51.920Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579362,716146,0,543052,TO_TIMESTAMP('2023-05-30 11:39:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Flatrate Term',TO_TIMESTAMP('2023-05-30 11:39:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:51.955Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716146 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:51.986Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447) 
;

-- 2023-05-30T08:39:52.026Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716146
;

-- 2023-05-30T08:39:52.056Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716146)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Inputsource
-- Column: C_Invoice_Candidate.AD_InputDataSource_ID
-- 2023-05-30T08:39:52.624Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582930,716147,0,543052,TO_TIMESTAMP('2023-05-30 11:39:52','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Inputsource',TO_TIMESTAMP('2023-05-30 11:39:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:52.656Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716147 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:52.687Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541291) 
;

-- 2023-05-30T08:39:52.724Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716147
;

-- 2023-05-30T08:39:52.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716147)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Section Code
-- Column: C_Invoice_Candidate.M_SectionCode_ID
-- 2023-05-30T08:39:53.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584388,716148,0,543052,TO_TIMESTAMP('2023-05-30 11:39:52','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Section Code',TO_TIMESTAMP('2023-05-30 11:39:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:53.302Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:53.333Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-05-30T08:39:53.375Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716148
;

-- 2023-05-30T08:39:53.406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716148)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Additional Text for Invoice
-- Column: C_Invoice_Candidate.InvoiceAdditionalText
-- 2023-05-30T08:39:53.999Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585923,716149,0,543052,TO_TIMESTAMP('2023-05-30 11:39:53','YYYY-MM-DD HH24:MI:SS'),100,'',1024,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Additional Text for Invoice',TO_TIMESTAMP('2023-05-30 11:39:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:54.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:54.062Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582034) 
;

-- 2023-05-30T08:39:54.097Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716149
;

-- 2023-05-30T08:39:54.127Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716149)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Do not show Country of Origin
-- Column: C_Invoice_Candidate.IsNotShowOriginCountry
-- 2023-05-30T08:39:54.732Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585926,716150,0,543052,TO_TIMESTAMP('2023-05-30 11:39:54','YYYY-MM-DD HH24:MI:SS'),100,'If is NO, then the Country of Origin of the products is displayed in the invoice report',1,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Do not show Country of Origin',TO_TIMESTAMP('2023-05-30 11:39:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:54.764Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:54.797Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582035) 
;

-- 2023-05-30T08:39:54.834Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716150
;

-- 2023-05-30T08:39:54.866Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716150)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Payment Instruction
-- Column: C_Invoice_Candidate.C_PaymentInstruction_ID
-- 2023-05-30T08:39:55.417Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586142,716151,0,543052,TO_TIMESTAMP('2023-05-30 11:39:54','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Payment Instruction',TO_TIMESTAMP('2023-05-30 11:39:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:55.449Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716151 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:55.480Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582077) 
;

-- 2023-05-30T08:39:55.568Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716151
;

-- 2023-05-30T08:39:55.601Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716151)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Act Load Date
-- Column: C_Invoice_Candidate.ActualLoadingDate
-- 2023-05-30T08:39:56.124Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586144,716152,0,543052,TO_TIMESTAMP('2023-05-30 11:39:55','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Act Load Date',TO_TIMESTAMP('2023-05-30 11:39:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:56.158Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:56.192Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581689) 
;

-- 2023-05-30T08:39:56.230Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716152
;

-- 2023-05-30T08:39:56.261Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716152)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Sales Order
-- Column: C_Invoice_Candidate.C_OrderSO_ID
-- 2023-05-30T08:39:56.823Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586275,716153,0,543052,TO_TIMESTAMP('2023-05-30 11:39:56','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','','Y','Y','N','N','N','N','N','Sales Order',TO_TIMESTAMP('2023-05-30 11:39:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:56.864Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:56.897Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543479) 
;

-- 2023-05-30T08:39:56.933Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716153
;

-- 2023-05-30T08:39:56.963Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716153)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Business Partner (2)
-- Column: C_Invoice_Candidate.C_BPartner2_ID
-- 2023-05-30T08:39:57.496Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586301,716154,0,543052,TO_TIMESTAMP('2023-05-30 11:39:57','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Business Partner (2)',TO_TIMESTAMP('2023-05-30 11:39:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T08:39:57.589Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T08:39:57.627Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582129) 
;

-- 2023-05-30T08:39:57.661Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716154
;

-- 2023-05-30T08:39:57.694Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716154)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Assignment
-- Column: C_Invoice_Candidate.UserElementString1
-- 2023-05-30T08:40:55.928Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:40:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716123
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString2
-- Column: C_Invoice_Candidate.UserElementString2
-- 2023-05-30T08:41:03.389Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:41:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716124
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString3
-- Column: C_Invoice_Candidate.UserElementString3
-- 2023-05-30T08:41:10.094Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:41:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716125
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString4
-- Column: C_Invoice_Candidate.UserElementString4
-- 2023-05-30T08:41:18.862Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:41:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716126
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString5
-- Column: C_Invoice_Candidate.UserElementString5
-- 2023-05-30T08:41:25.934Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:41:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716127
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString6
-- Column: C_Invoice_Candidate.UserElementString6
-- 2023-05-30T08:41:31.611Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716128
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> UserElementString7
-- Column: C_Invoice_Candidate.UserElementString7
-- 2023-05-30T08:41:39.998Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:41:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716129
;




-- UI Element: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Assignment
-- Column: C_Invoice_Candidate.UserElementString1
-- 2023-05-30T08:54:03.971Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716123,0,543052,544370,617920,'F',TO_TIMESTAMP('2023-05-30 11:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Assignment',1050,0,0,TO_TIMESTAMP('2023-05-30 11:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString2
-- Column: C_Invoice_Candidate.UserElementString2
-- 2023-05-30T08:54:17.484Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716124,0,543052,544370,617921,'F',TO_TIMESTAMP('2023-05-30 11:54:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString2',1060,0,0,TO_TIMESTAMP('2023-05-30 11:54:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString3
-- Column: C_Invoice_Candidate.UserElementString3
-- 2023-05-30T08:54:30.219Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716125,0,543052,544370,617922,'F',TO_TIMESTAMP('2023-05-30 11:54:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString3',1070,0,0,TO_TIMESTAMP('2023-05-30 11:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString4
-- Column: C_Invoice_Candidate.UserElementString4
-- 2023-05-30T08:54:43.114Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716126,0,543052,544370,617923,'F',TO_TIMESTAMP('2023-05-30 11:54:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString4',1080,0,0,TO_TIMESTAMP('2023-05-30 11:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString5
-- Column: C_Invoice_Candidate.UserElementString5
-- 2023-05-30T08:54:57.522Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716127,0,543052,544370,617924,'F',TO_TIMESTAMP('2023-05-30 11:54:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString5',1090,0,0,TO_TIMESTAMP('2023-05-30 11:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString6
-- Column: C_Invoice_Candidate.UserElementString6
-- 2023-05-30T08:55:10.684Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716128,0,543052,544370,617925,'F',TO_TIMESTAMP('2023-05-30 11:55:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString6',1100,0,0,TO_TIMESTAMP('2023-05-30 11:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.UserElementString7
-- Column: C_Invoice_Candidate.UserElementString7
-- 2023-05-30T08:55:24.430Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716129,0,543052,544370,617926,'F',TO_TIMESTAMP('2023-05-30 11:55:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString7',1110,0,0,TO_TIMESTAMP('2023-05-30 11:55:23','YYYY-MM-DD HH24:MI:SS'),100)
;

