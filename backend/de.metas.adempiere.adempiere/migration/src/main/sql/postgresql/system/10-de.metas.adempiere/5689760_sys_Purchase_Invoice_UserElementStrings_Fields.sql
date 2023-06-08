
-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Sales partner
-- Column: C_Invoice.C_BPartner_SalesRep_ID
-- 2023-05-30T07:19:33.576Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570093,716099,0,290,TO_TIMESTAMP('2023-05-30 10:19:32','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Sales partner',TO_TIMESTAMP('2023-05-30 10:19:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:33.614Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:33.648Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541357) 
;

-- 2023-05-30T07:19:33.688Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716099
;

-- 2023-05-30T07:19:33.719Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716099)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Sales partner code
-- Column: C_Invoice.SalesPartnerCode
-- 2023-05-30T07:19:34.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570094,716100,0,290,TO_TIMESTAMP('2023-05-30 10:19:33','YYYY-MM-DD HH24:MI:SS'),100,100,'D','Y','Y','N','N','N','N','N','Sales partner code',TO_TIMESTAMP('2023-05-30 10:19:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:34.301Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716100 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:34.332Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577239) 
;

-- 2023-05-30T07:19:34.368Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716100
;

-- 2023-05-30T07:19:34.402Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716100)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Sales partner required
-- Column: C_Invoice.IsSalesPartnerRequired
-- 2023-05-30T07:19:35.011Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570096,716101,0,290,TO_TIMESTAMP('2023-05-30 10:19:34','YYYY-MM-DD HH24:MI:SS'),100,'Specifies for a bill partner whether a sales partner has to be specified in a sales order.',1,'D','Y','Y','N','N','N','N','N','Sales partner required',TO_TIMESTAMP('2023-05-30 10:19:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:35.043Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:35.075Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577106) 
;

-- 2023-05-30T07:19:35.110Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716101
;

-- 2023-05-30T07:19:35.141Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716101)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Beneficiary
-- Column: C_Invoice.Beneficiary_BPartner_ID
-- 2023-05-30T07:19:35.681Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570613,716102,0,290,TO_TIMESTAMP('2023-05-30 10:19:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Beneficiary',TO_TIMESTAMP('2023-05-30 10:19:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:35.713Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:35.745Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577655) 
;

-- 2023-05-30T07:19:35.779Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716102
;

-- 2023-05-30T07:19:35.810Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716102)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Beneficiary Address
-- Column: C_Invoice.Beneficiary_Location_ID
-- 2023-05-30T07:19:36.335Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570614,716103,0,290,TO_TIMESTAMP('2023-05-30 10:19:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Beneficiary Address',TO_TIMESTAMP('2023-05-30 10:19:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:36.367Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:36.398Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577656) 
;

-- 2023-05-30T07:19:36.433Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716103
;

-- 2023-05-30T07:19:36.464Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716103)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Beneficiary contact
-- Column: C_Invoice.Beneficiary_Contact_ID
-- 2023-05-30T07:19:37.070Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570615,716104,0,290,TO_TIMESTAMP('2023-05-30 10:19:36','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Beneficiary contact',TO_TIMESTAMP('2023-05-30 10:19:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:37.102Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:37.133Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577657) 
;

-- 2023-05-30T07:19:37.173Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716104
;

-- 2023-05-30T07:19:37.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716104)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Posting Error
-- Column: C_Invoice.PostingError_Issue_ID
-- 2023-05-30T07:19:37.785Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570872,716105,0,290,TO_TIMESTAMP('2023-05-30 10:19:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Posting Error',TO_TIMESTAMP('2023-05-30 10:19:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:37.816Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:37.848Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
;

-- 2023-05-30T07:19:37.883Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716105
;

-- 2023-05-30T07:19:37.916Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716105)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Warehouse
-- Column: C_Invoice.M_Warehouse_ID
-- 2023-05-30T07:19:38.461Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571520,716106,0,290,TO_TIMESTAMP('2023-05-30 10:19:37','YYYY-MM-DD HH24:MI:SS'),100,'Storage Warehouse and Service Point',10,'D','The Warehouse identifies a unique Warehouse where products are stored or Services are provided.','Y','Y','N','N','N','N','N','Warehouse',TO_TIMESTAMP('2023-05-30 10:19:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:38.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:38.526Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2023-05-30T07:19:38.637Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716106
;

-- 2023-05-30T07:19:38.668Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716106)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Standort (Address)
-- Column: C_Invoice.C_BPartner_Location_Value_ID
-- 2023-05-30T07:19:39.214Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573516,716107,0,290,TO_TIMESTAMP('2023-05-30 10:19:38','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Standort (Address)',TO_TIMESTAMP('2023-05-30 10:19:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:39.246Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:39.280Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579023) 
;

-- 2023-05-30T07:19:39.317Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716107
;

-- 2023-05-30T07:19:39.347Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716107)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> eMail
-- Column: C_Invoice.EMail
-- 2023-05-30T07:19:39.931Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579210,716108,0,290,TO_TIMESTAMP('2023-05-30 10:19:39','YYYY-MM-DD HH24:MI:SS'),100,'',250,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','Y','N','N','N','N','N','eMail',TO_TIMESTAMP('2023-05-30 10:19:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:39.963Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:39.996Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881) 
;

-- 2023-05-30T07:19:40.034Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716108
;

-- 2023-05-30T07:19:40.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716108)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Inputsource
-- Column: C_Invoice.AD_InputDataSource_ID
-- 2023-05-30T07:19:40.653Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582929,716109,0,290,TO_TIMESTAMP('2023-05-30 10:19:40','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Inputsource',TO_TIMESTAMP('2023-05-30 10:19:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:40.684Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:40.716Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541291) 
;

-- 2023-05-30T07:19:40.752Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716109
;

-- 2023-05-30T07:19:40.783Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716109)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Due Date
-- Column: C_Invoice.DueDate
-- 2023-05-30T07:19:41.319Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584270,716110,0,290,TO_TIMESTAMP('2023-05-30 10:19:40','YYYY-MM-DD HH24:MI:SS'),100,'Date when the payment is due',7,'D','Date when the payment is due without deductions or discount','Y','Y','N','N','N','N','N','Due Date',TO_TIMESTAMP('2023-05-30 10:19:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:41.350Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:41.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2000) 
;

-- 2023-05-30T07:19:41.417Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716110
;

-- 2023-05-30T07:19:41.447Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716110)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Additional Text for Invoice
-- Column: C_Invoice.InvoiceAdditionalText
-- 2023-05-30T07:19:42.056Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585924,716111,0,290,TO_TIMESTAMP('2023-05-30 10:19:41','YYYY-MM-DD HH24:MI:SS'),100,'',1024,'D','Y','Y','N','N','N','N','N','Additional Text for Invoice',TO_TIMESTAMP('2023-05-30 10:19:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:42.087Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:42.118Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582034) 
;

-- 2023-05-30T07:19:42.154Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716111
;

-- 2023-05-30T07:19:42.185Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716111)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Do not show Country of Origin
-- Column: C_Invoice.IsNotShowOriginCountry
-- 2023-05-30T07:19:42.747Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585927,716112,0,290,TO_TIMESTAMP('2023-05-30 10:19:42','YYYY-MM-DD HH24:MI:SS'),100,'If is NO, then the Country of Origin of the products is displayed in the invoice report',1,'D','Y','Y','N','N','N','N','N','Do not show Country of Origin',TO_TIMESTAMP('2023-05-30 10:19:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:42.779Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:42.812Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582035) 
;

-- 2023-05-30T07:19:42.846Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716112
;

-- 2023-05-30T07:19:42.877Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716112)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Payment Instruction
-- Column: C_Invoice.C_PaymentInstruction_ID
-- 2023-05-30T07:19:43.408Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586141,716113,0,290,TO_TIMESTAMP('2023-05-30 10:19:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Payment Instruction',TO_TIMESTAMP('2023-05-30 10:19:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:43.441Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:43.472Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582077) 
;

-- 2023-05-30T07:19:43.506Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716113
;

-- 2023-05-30T07:19:43.537Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716113)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> IsFixedInvoice
-- Column: C_Invoice.IsFixedInvoice
-- 2023-05-30T07:19:44.131Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586289,716114,0,290,TO_TIMESTAMP('2023-05-30 10:19:43','YYYY-MM-DD HH24:MI:SS'),100,'Invoices with this set to Y will not have the docActions RE, RC and VO available',1,'D','Y','Y','N','N','N','N','N','IsFixedInvoice',TO_TIMESTAMP('2023-05-30 10:19:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:44.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:44.195Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582125) 
;

-- 2023-05-30T07:19:44.229Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716114
;

-- 2023-05-30T07:19:44.260Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716114)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Assignment
-- Column: C_Invoice.UserElementString1
-- 2023-05-30T07:19:45.145Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586401,716115,0,290,TO_TIMESTAMP('2023-05-30 10:19:44','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','Assignment',TO_TIMESTAMP('2023-05-30 10:19:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:45.208Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716115 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:45.321Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-05-30T07:19:45.403Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716115
;

-- 2023-05-30T07:19:45.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716115)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString2
-- Column: C_Invoice.UserElementString2
-- 2023-05-30T07:19:47.077Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586402,716116,0,290,TO_TIMESTAMP('2023-05-30 10:19:45','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2023-05-30 10:19:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:47.125Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:47.295Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-05-30T07:19:47.499Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716116
;

-- 2023-05-30T07:19:47.568Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716116)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString3
-- Column: C_Invoice.UserElementString3
-- 2023-05-30T07:19:48.190Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586403,716117,0,290,TO_TIMESTAMP('2023-05-30 10:19:47','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2023-05-30 10:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:48.223Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716117 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:48.255Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-05-30T07:19:48.291Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716117
;

-- 2023-05-30T07:19:48.322Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716117)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString4
-- Column: C_Invoice.UserElementString4
-- 2023-05-30T07:19:48.901Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586404,716118,0,290,TO_TIMESTAMP('2023-05-30 10:19:48','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-05-30 10:19:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:48.936Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716118 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:48.967Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-05-30T07:19:49.002Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716118
;

-- 2023-05-30T07:19:49.032Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716118)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString5
-- Column: C_Invoice.UserElementString5
-- 2023-05-30T07:19:49.578Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586405,716119,0,290,TO_TIMESTAMP('2023-05-30 10:19:49','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-05-30 10:19:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:49.611Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716119 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:49.643Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-05-30T07:19:49.724Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716119
;

-- 2023-05-30T07:19:49.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716119)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString6
-- Column: C_Invoice.UserElementString6
-- 2023-05-30T07:19:50.288Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586406,716120,0,290,TO_TIMESTAMP('2023-05-30 10:19:49','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-05-30 10:19:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:50.323Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716120 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:50.354Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-05-30T07:19:50.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716120
;

-- 2023-05-30T07:19:50.425Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716120)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString7
-- Column: C_Invoice.UserElementString7
-- 2023-05-30T07:19:51.025Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586407,716121,0,290,TO_TIMESTAMP('2023-05-30 10:19:50','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-05-30 10:19:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:51.057Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716121 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:51.089Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-05-30T07:19:51.122Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716121
;

-- 2023-05-30T07:19:51.153Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716121)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Print local currency VAT
-- Column: C_Invoice.IsPrintLocalCurrencyInfo
-- 2023-05-30T07:19:51.737Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586412,716122,0,290,TO_TIMESTAMP('2023-05-30 10:19:51','YYYY-MM-DD HH24:MI:SS'),100,'Determines if local currency VAT informations should be printed on sales invoices. Can be configured in tab tax reporting in window document type. If the field is left none or set to Yes, the tax report is printed.',1,'D','Y','Y','N','N','N','N','N','Print local currency VAT',TO_TIMESTAMP('2023-05-30 10:19:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-30T07:19:51.772Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716122 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-30T07:19:51.804Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582207) 
;

-- 2023-05-30T07:19:51.838Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716122
;

-- 2023-05-30T07:19:51.869Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716122)
;



-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Assignment
-- Column: C_Invoice.UserElementString1
-- 2023-05-30T08:21:54.233Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716115
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString2
-- Column: C_Invoice.UserElementString2
-- 2023-05-30T08:22:00.167Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716116
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString3
-- Column: C_Invoice.UserElementString3
-- 2023-05-30T08:22:07.364Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:22:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716117
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString4
-- Column: C_Invoice.UserElementString4
-- 2023-05-30T08:22:15.184Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:22:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716118
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString5
-- Column: C_Invoice.UserElementString5
-- 2023-05-30T08:22:27.320Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716119
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString6
-- Column: C_Invoice.UserElementString6
-- 2023-05-30T08:22:33.704Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716120
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> UserElementString7
-- Column: C_Invoice.UserElementString7
-- 2023-05-30T08:22:41.758Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/''X''@=Y',Updated=TO_TIMESTAMP('2023-05-30 11:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716121
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> advanced edit -> 10 -> advanced edit.Assignment
-- Column: C_Invoice.UserElementString1
-- 2023-05-30T08:23:34.983Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716115,0,290,540218,617913,'F',TO_TIMESTAMP('2023-05-30 11:23:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Assignment',360,0,0,TO_TIMESTAMP('2023-05-30 11:23:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> advanced edit -> 10 -> advanced edit.UserElementString2
-- Column: C_Invoice.UserElementString2
-- 2023-05-30T08:23:49.385Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716116,0,290,540218,617914,'F',TO_TIMESTAMP('2023-05-30 11:23:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString2',370,0,0,TO_TIMESTAMP('2023-05-30 11:23:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> advanced edit -> 10 -> advanced edit.UserElementString3
-- Column: C_Invoice.UserElementString3
-- 2023-05-30T08:24:03.298Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716117,0,290,540218,617915,'F',TO_TIMESTAMP('2023-05-30 11:24:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString3',380,0,0,TO_TIMESTAMP('2023-05-30 11:24:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> advanced edit -> 10 -> advanced edit.UserElementString4
-- Column: C_Invoice.UserElementString4
-- 2023-05-30T08:24:58.485Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716118,0,290,540218,617916,'F',TO_TIMESTAMP('2023-05-30 11:24:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString4',390,0,0,TO_TIMESTAMP('2023-05-30 11:24:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> advanced edit -> 10 -> advanced edit.UserElementString5
-- Column: C_Invoice.UserElementString5
-- 2023-05-30T08:25:13.602Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716119,0,290,540218,617917,'F',TO_TIMESTAMP('2023-05-30 11:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString5',400,0,0,TO_TIMESTAMP('2023-05-30 11:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> advanced edit -> 10 -> advanced edit.UserElementString6
-- Column: C_Invoice.UserElementString6
-- 2023-05-30T08:25:27.034Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716120,0,290,540218,617918,'F',TO_TIMESTAMP('2023-05-30 11:25:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString6',410,0,0,TO_TIMESTAMP('2023-05-30 11:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> advanced edit -> 10 -> advanced edit.UserElementString7
-- Column: C_Invoice.UserElementString7
-- 2023-05-30T08:25:40.211Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716121,0,290,540218,617919,'F',TO_TIMESTAMP('2023-05-30 11:25:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString7',420,0,0,TO_TIMESTAMP('2023-05-30 11:25:39','YYYY-MM-DD HH24:MI:SS'),100)
;

