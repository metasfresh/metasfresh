-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Verbuchungsfehler
-- Column: C_Invoice.PostingError_Issue_ID
-- 2023-05-31T15:30:28.036Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570872,716156,0,263,TO_TIMESTAMP('2023-05-31 18:30:27','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Verbuchungsfehler',TO_TIMESTAMP('2023-05-31 18:30:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:28.038Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:28.063Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
;

-- 2023-05-31T15:30:28.076Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716156
;

-- 2023-05-31T15:30:28.078Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716156)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Standort (Address)
-- Column: C_Invoice.C_BPartner_Location_Value_ID
-- 2023-05-31T15:30:28.196Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573516,716157,0,263,TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Standort (Address)',TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:28.197Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:28.199Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579023) 
;

-- 2023-05-31T15:30:28.202Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716157
;

-- 2023-05-31T15:30:28.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716157)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Anzahl Debitoren Rechnungen
-- Column: C_Invoice.Sales_Invoice_Count
-- 2023-05-31T15:30:28.316Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578293,716158,0,263,TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl zugeordneter debitoren Rechnungen',10,'D','Y','Y','N','N','N','N','N','Anzahl Debitoren Rechnungen',TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:28.317Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716158 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:28.318Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580172) 
;

-- 2023-05-31T15:30:28.321Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716158
;

-- 2023-05-31T15:30:28.322Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716158)
;


-- 2023-05-31T15:30:28.436Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2000) 
;

-- 2023-05-31T15:30:28.440Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716159
;

-- 2023-05-31T15:30:28.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716159)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Zusätzlicher Text für Rechnungen
-- Column: C_Invoice.InvoiceAdditionalText
-- 2023-05-31T15:30:28.559Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585924,716160,0,263,TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100,'',1024,'D','Y','Y','N','N','N','N','N','Zusätzlicher Text für Rechnungen',TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:28.561Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716160 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:28.562Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582034) 
;

-- 2023-05-31T15:30:28.565Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716160
;

-- 2023-05-31T15:30:28.566Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716160)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Herkunftsland nicht anzeigen
-- Column: C_Invoice.IsNotShowOriginCountry
-- 2023-05-31T15:30:28.684Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585927,716161,0,263,TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100,'Wenn NEIN, dann wird das Herkunftsland der Produkte im Rechnungsbericht angezeigt',1,'D','Y','Y','N','N','N','N','N','Herkunftsland nicht anzeigen',TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:28.685Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:28.687Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582035) 
;

-- 2023-05-31T15:30:28.693Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716161
;

-- 2023-05-31T15:30:28.693Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716161)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Payment Instruction
-- Column: C_Invoice.C_PaymentInstruction_ID
-- 2023-05-31T15:30:28.820Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586141,716162,0,263,TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Payment Instruction',TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:28.821Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:28.822Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582077) 
;

-- 2023-05-31T15:30:28.825Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716162
;

-- 2023-05-31T15:30:28.826Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716162)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> IsFixedInvoice
-- Column: C_Invoice.IsFixedInvoice
-- 2023-05-31T15:30:28.959Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586289,716163,0,263,TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100,'Invoices with this set to Y will not have the docActions RE, RC and VO available',1,'D','Y','Y','N','N','N','N','N','IsFixedInvoice',TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:28.961Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:28.963Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582125) 
;

-- 2023-05-31T15:30:28.966Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716163
;

-- 2023-05-31T15:30:28.967Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716163)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString1
-- Column: C_Invoice.UserElementString1
-- 2023-05-31T15:30:29.079Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586401,716164,0,263,TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString1',TO_TIMESTAMP('2023-05-31 18:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:29.081Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:29.082Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-05-31T15:30:29.085Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716164
;

-- 2023-05-31T15:30:29.086Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716164)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString2
-- Column: C_Invoice.UserElementString2
-- 2023-05-31T15:30:29.189Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586402,716165,0,263,TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:29.190Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:29.192Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-05-31T15:30:29.195Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716165
;

-- 2023-05-31T15:30:29.196Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716165)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString3
-- Column: C_Invoice.UserElementString3
-- 2023-05-31T15:30:29.296Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586403,716166,0,263,TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:29.297Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:29.299Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-05-31T15:30:29.302Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716166
;

-- 2023-05-31T15:30:29.303Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716166)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString4
-- Column: C_Invoice.UserElementString4
-- 2023-05-31T15:30:29.408Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586404,716167,0,263,TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:29.409Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716167 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:29.411Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-05-31T15:30:29.414Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716167
;

-- 2023-05-31T15:30:29.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716167)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString5
-- Column: C_Invoice.UserElementString5
-- 2023-05-31T15:30:29.522Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586405,716168,0,263,TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:29.524Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716168 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:29.525Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-05-31T15:30:29.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716168
;

-- 2023-05-31T15:30:29.530Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716168)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString6
-- Column: C_Invoice.UserElementString6
-- 2023-05-31T15:30:29.638Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586406,716169,0,263,TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:29.639Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716169 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:29.641Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-05-31T15:30:29.644Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716169
;

-- 2023-05-31T15:30:29.645Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716169)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString7
-- Column: C_Invoice.UserElementString7
-- 2023-05-31T15:30:29.784Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586407,716170,0,263,TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-05-31 18:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-31T15:30:29.787Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716170 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T15:30:29.789Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-05-31T15:30:29.793Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716170
;

-- 2023-05-31T15:30:29.794Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716170)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.UserElementString1
-- Column: C_Invoice.UserElementString1
-- 2023-05-31T15:31:21.172Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716164,0,263,541214,617928,'F',TO_TIMESTAMP('2023-05-31 18:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString1',110,0,0,TO_TIMESTAMP('2023-05-31 18:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.UserElementString2
-- Column: C_Invoice.UserElementString2
-- 2023-05-31T15:31:33.011Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716165,0,263,541214,617929,'F',TO_TIMESTAMP('2023-05-31 18:31:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString2',120,0,0,TO_TIMESTAMP('2023-05-31 18:31:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.UserElementString3
-- Column: C_Invoice.UserElementString3
-- 2023-05-31T15:31:51.112Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716166,0,263,541214,617930,'F',TO_TIMESTAMP('2023-05-31 18:31:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString3',130,0,0,TO_TIMESTAMP('2023-05-31 18:31:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.UserElementString4
-- Column: C_Invoice.UserElementString4
-- 2023-05-31T15:32:00.658Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716167,0,263,541214,617931,'F',TO_TIMESTAMP('2023-05-31 18:32:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString4',140,0,0,TO_TIMESTAMP('2023-05-31 18:32:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.UserElementString5
-- Column: C_Invoice.UserElementString5
-- 2023-05-31T15:32:12.765Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716168,0,263,541214,617932,'F',TO_TIMESTAMP('2023-05-31 18:32:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString5',150,0,0,TO_TIMESTAMP('2023-05-31 18:32:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.UserElementString5
-- Column: C_Invoice.UserElementString5
-- 2023-05-31T15:32:23.736Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716168,0,263,541214,617933,'F',TO_TIMESTAMP('2023-05-31 18:32:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString5',160,0,0,TO_TIMESTAMP('2023-05-31 18:32:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.UserElementString6
-- Column: C_Invoice.UserElementString6
-- 2023-05-31T15:32:31.131Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716169,0,263,541214,617934,'F',TO_TIMESTAMP('2023-05-31 18:32:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString6',170,0,0,TO_TIMESTAMP('2023-05-31 18:32:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.UserElementString7
-- Column: C_Invoice.UserElementString7
-- 2023-05-31T15:32:41.932Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716170,0,263,541214,617935,'F',TO_TIMESTAMP('2023-05-31 18:32:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString7',180,0,0,TO_TIMESTAMP('2023-05-31 18:32:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString1
-- Column: C_Invoice.UserElementString1
-- 2023-05-31T15:34:47.315Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/X@=Y',Updated=TO_TIMESTAMP('2023-05-31 18:34:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716164
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString1
-- Column: C_Invoice.UserElementString1
-- 2023-05-31T15:38:14.962Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-31 18:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716164
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString2
-- Column: C_Invoice.UserElementString2
-- 2023-05-31T15:38:25.038Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-31 18:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716165
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString3
-- Column: C_Invoice.UserElementString3
-- 2023-05-31T15:38:30.371Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-31 18:38:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716166
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString4
-- Column: C_Invoice.UserElementString4
-- 2023-05-31T15:38:36.218Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-31 18:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716167
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString5
-- Column: C_Invoice.UserElementString5
-- 2023-05-31T15:38:41.401Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-31 18:38:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716168
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString6
-- Column: C_Invoice.UserElementString6
-- 2023-05-31T15:38:46.990Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-31 18:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716169
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> UserElementString7
-- Column: C_Invoice.UserElementString7
-- 2023-05-31T15:38:50.484Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-31 18:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716170
;

