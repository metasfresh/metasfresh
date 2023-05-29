-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Standort (Address)
-- Column: C_Order.C_BPartner_Location_Value_ID
-- 2023-05-29T08:13:22.833Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573510,715987,0,186,TO_TIMESTAMP('2023-05-29 11:13:22','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Standort (Address)',TO_TIMESTAMP('2023-05-29 11:13:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:22.879Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:22.925Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579023) 
;

-- 2023-05-29T08:13:23.010Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715987
;

-- 2023-05-29T08:13:23.055Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715987)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Rechnungsstandort (Address)
-- Column: C_Order.Bill_Location_Value_ID
-- 2023-05-29T08:13:23.763Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573511,715988,0,186,TO_TIMESTAMP('2023-05-29 11:13:23','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','Y','Y','N','N','N','N','N','Rechnungsstandort (Address)',TO_TIMESTAMP('2023-05-29 11:13:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:23.807Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:23.849Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579024) 
;

-- 2023-05-29T08:13:23.900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715988
;

-- 2023-05-29T08:13:23.944Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715988)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Lieferadresse (Address)
-- Column: C_Order.DropShip_Location_Value_ID
-- 2023-05-29T08:13:24.687Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573512,715989,0,186,TO_TIMESTAMP('2023-05-29 11:13:24','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Location for shipping to',10,'D','Y','Y','N','N','N','N','N','Lieferadresse (Address)',TO_TIMESTAMP('2023-05-29 11:13:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:24.732Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:24.778Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579025) 
;

-- 2023-05-29T08:13:24.825Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715989
;

-- 2023-05-29T08:13:24.868Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715989)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Übergabeadresse (Address)
-- Column: C_Order.HandOver_Location_Value_ID
-- 2023-05-29T08:13:25.585Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573513,715990,0,186,TO_TIMESTAMP('2023-05-29 11:13:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Übergabeadresse (Address)',TO_TIMESTAMP('2023-05-29 11:13:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:25.628Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:25.672Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579026) 
;

-- 2023-05-29T08:13:25.717Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715990
;

-- 2023-05-29T08:13:25.760Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715990)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Async Batch
-- Column: C_Order.C_Async_Batch_ID
-- 2023-05-29T08:13:26.467Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575255,715991,0,186,TO_TIMESTAMP('2023-05-29 11:13:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Async Batch',TO_TIMESTAMP('2023-05-29 11:13:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:26.513Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715991 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:26.559Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542581) 
;

-- 2023-05-29T08:13:26.618Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715991
;

-- 2023-05-29T08:13:26.662Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715991)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Goods on consignment
-- Column: C_Order.IsOnConsignment
-- 2023-05-29T08:13:27.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585740,715992,0,186,TO_TIMESTAMP('2023-05-29 11:13:26','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Goods on consignment',TO_TIMESTAMP('2023-05-29 11:13:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:27.407Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:27.453Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581995) 
;

-- 2023-05-29T08:13:27.500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715992
;

-- 2023-05-29T08:13:27.542Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715992)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Additional Text for Invoice
-- Column: C_Order.InvoiceAdditionalText
-- 2023-05-29T08:13:28.287Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585922,715993,0,186,TO_TIMESTAMP('2023-05-29 11:13:27','YYYY-MM-DD HH24:MI:SS'),100,'',1024,'D','Y','Y','N','N','N','N','N','Additional Text for Invoice',TO_TIMESTAMP('2023-05-29 11:13:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:28.330Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715993 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:28.374Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582034) 
;

-- 2023-05-29T08:13:28.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715993
;

-- 2023-05-29T08:13:28.464Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715993)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Do not show Country of Origin
-- Column: C_Order.IsNotShowOriginCountry
-- 2023-05-29T08:13:29.221Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585925,715994,0,186,TO_TIMESTAMP('2023-05-29 11:13:28','YYYY-MM-DD HH24:MI:SS'),100,'If is NO, then the Country of Origin of the products is displayed in the invoice report',1,'D','Y','Y','N','N','N','N','N','Do not show Country of Origin',TO_TIMESTAMP('2023-05-29 11:13:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:29.265Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:29.310Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582035) 
;

-- 2023-05-29T08:13:29.360Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715994
;

-- 2023-05-29T08:13:29.403Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715994)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Payment Instruction
-- Column: C_Order.C_PaymentInstruction_ID
-- 2023-05-29T08:13:30.129Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586140,715995,0,186,TO_TIMESTAMP('2023-05-29 11:13:29','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Payment Instruction',TO_TIMESTAMP('2023-05-29 11:13:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:30.173Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715995 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:30.218Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582077) 
;

-- 2023-05-29T08:13:30.264Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715995
;

-- 2023-05-29T08:13:30.307Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715995)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Assignment
-- Column: C_Order.UserElementString1
-- 2023-05-29T08:13:31.029Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586714,715996,0,186,TO_TIMESTAMP('2023-05-29 11:13:30','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','Assignment',TO_TIMESTAMP('2023-05-29 11:13:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:31.072Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715996 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:31.117Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-05-29T08:13:31.167Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715996
;

-- 2023-05-29T08:13:31.209Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715996)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString2
-- Column: C_Order.UserElementString2
-- 2023-05-29T08:13:31.944Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586715,715997,0,186,TO_TIMESTAMP('2023-05-29 11:13:31','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2023-05-29 11:13:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:31.987Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:32.030Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-05-29T08:13:32.077Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715997
;

-- 2023-05-29T08:13:32.118Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715997)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString3
-- Column: C_Order.UserElementString3
-- 2023-05-29T08:13:32.867Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586716,715998,0,186,TO_TIMESTAMP('2023-05-29 11:13:32','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2023-05-29 11:13:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:32.911Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:32.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-05-29T08:13:33.004Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715998
;

-- 2023-05-29T08:13:33.048Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715998)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString4
-- Column: C_Order.UserElementString4
-- 2023-05-29T08:13:33.775Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586717,715999,0,186,TO_TIMESTAMP('2023-05-29 11:13:33','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-05-29 11:13:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:33.818Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:33.861Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-05-29T08:13:33.909Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715999
;

-- 2023-05-29T08:13:33.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715999)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString5
-- Column: C_Order.UserElementString5
-- 2023-05-29T08:13:34.664Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586718,716000,0,186,TO_TIMESTAMP('2023-05-29 11:13:34','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-05-29 11:13:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:34.711Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:34.754Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-05-29T08:13:34.803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716000
;

-- 2023-05-29T08:13:34.847Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716000)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString6
-- Column: C_Order.UserElementString6
-- 2023-05-29T08:13:35.539Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586719,716001,0,186,TO_TIMESTAMP('2023-05-29 11:13:34','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-05-29 11:13:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:13:35.582Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:13:35.624Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-05-29T08:13:35.695Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716001
;

-- 2023-05-29T08:13:35.742Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716001)
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> advanced edit -> 10 -> advanced edit.UserElementString2
-- Column: C_Order.UserElementString2
-- 2023-05-29T08:15:13.757Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715997,0,186,540499,617885,'F',TO_TIMESTAMP('2023-05-29 11:15:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'UserElementString2',470,0,0,TO_TIMESTAMP('2023-05-29 11:15:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> advanced edit -> 10 -> advanced edit.UserElementString3
-- Column: C_Order.UserElementString3
-- 2023-05-29T08:15:30.109Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715998,0,186,540499,617886,'F',TO_TIMESTAMP('2023-05-29 11:15:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString3',480,0,0,TO_TIMESTAMP('2023-05-29 11:15:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString2
-- Column: C_Order.UserElementString2
-- 2023-05-29T08:18:18.966Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=715997
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Assignment
-- Column: C_Order.UserElementString1
-- 2023-05-29T08:18:39.827Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:18:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=715996
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString3
-- Column: C_Order.UserElementString3
-- 2023-05-29T08:18:57.311Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:18:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=715998
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString4
-- Column: C_Order.UserElementString4
-- 2023-05-29T08:19:07.058Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=715999
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString5
-- Column: C_Order.UserElementString5
-- 2023-05-29T08:19:16.870Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:19:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716000
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString6
-- Column: C_Order.UserElementString6
-- 2023-05-29T08:19:29.861Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:19:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716001
;



-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Posting Error
-- Column: C_Order.PostingError_Issue_ID
-- 2023-05-29T08:22:44.941Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570876,716002,0,294,TO_TIMESTAMP('2023-05-29 11:22:44','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Posting Error',TO_TIMESTAMP('2023-05-29 11:22:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:44.985Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:45.028Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
;

-- 2023-05-29T08:22:45.083Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716002
;

-- 2023-05-29T08:22:45.126Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716002)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Delivery info
-- Column: C_Order.DeliveryInfo
-- 2023-05-29T08:22:45.884Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571551,716003,0,294,TO_TIMESTAMP('2023-05-29 11:22:45','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Delivery info',TO_TIMESTAMP('2023-05-29 11:22:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:45.927Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716003 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:45.973Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578126) 
;

-- 2023-05-29T08:22:46.020Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716003
;

-- 2023-05-29T08:22:46.064Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716003)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Sales Responsible
-- Column: C_Order.SalesRepIntern_ID
-- 2023-05-29T08:22:46.818Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572014,716004,0,294,TO_TIMESTAMP('2023-05-29 11:22:46','YYYY-MM-DD HH24:MI:SS'),100,'Sales Responsible Internal',10,'D','','Y','Y','N','N','N','N','N','Sales Responsible',TO_TIMESTAMP('2023-05-29 11:22:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:46.861Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:46.903Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543385) 
;

-- 2023-05-29T08:22:46.954Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716004
;

-- 2023-05-29T08:22:46.996Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716004)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Print Totals
-- Column: C_Order.PRINTER_OPTS_IsPrintTotals
-- 2023-05-29T08:22:47.724Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572179,716005,0,294,TO_TIMESTAMP('2023-05-29 11:22:47','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Print Totals',TO_TIMESTAMP('2023-05-29 11:22:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:47.770Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:47.813Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578552) 
;

-- 2023-05-29T08:22:47.861Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716005
;

-- 2023-05-29T08:22:47.902Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716005)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Frame Agreement Order
-- Column: C_Order.C_FrameAgreement_Order_ID
-- 2023-05-29T08:22:48.613Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573002,716006,0,294,TO_TIMESTAMP('2023-05-29 11:22:47','YYYY-MM-DD HH24:MI:SS'),100,'Reference to corresponding FrameAgreement Order',10,'D','Reference of the Sales Order to the corresponding FrameAgreement','Y','Y','N','N','N','N','N','Frame Agreement Order',TO_TIMESTAMP('2023-05-29 11:22:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:48.663Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:48.708Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578783) 
;

-- 2023-05-29T08:22:48.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716006
;

-- 2023-05-29T08:22:48.801Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716006)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Standort (Address)
-- Column: C_Order.C_BPartner_Location_Value_ID
-- 2023-05-29T08:22:49.560Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573510,716007,0,294,TO_TIMESTAMP('2023-05-29 11:22:48','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Standort (Address)',TO_TIMESTAMP('2023-05-29 11:22:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:49.604Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:49.648Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579023) 
;

-- 2023-05-29T08:22:49.698Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716007
;

-- 2023-05-29T08:22:49.745Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716007)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Rechnungsstandort (Address)
-- Column: C_Order.Bill_Location_Value_ID
-- 2023-05-29T08:22:50.453Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573511,716008,0,294,TO_TIMESTAMP('2023-05-29 11:22:49','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','Y','Y','N','N','N','N','N','Rechnungsstandort (Address)',TO_TIMESTAMP('2023-05-29 11:22:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:50.529Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:50.572Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579024) 
;

-- 2023-05-29T08:22:50.617Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716008
;

-- 2023-05-29T08:22:50.659Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716008)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Lieferadresse (Address)
-- Column: C_Order.DropShip_Location_Value_ID
-- 2023-05-29T08:22:51.365Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573512,716009,0,294,TO_TIMESTAMP('2023-05-29 11:22:50','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Location for shipping to',10,'D','Y','Y','N','N','N','N','N','Lieferadresse (Address)',TO_TIMESTAMP('2023-05-29 11:22:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:51.409Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:51.452Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579025) 
;

-- 2023-05-29T08:22:51.537Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716009
;

-- 2023-05-29T08:22:51.579Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716009)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Übergabeadresse (Address)
-- Column: C_Order.HandOver_Location_Value_ID
-- 2023-05-29T08:22:52.311Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573513,716010,0,294,TO_TIMESTAMP('2023-05-29 11:22:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Übergabeadresse (Address)',TO_TIMESTAMP('2023-05-29 11:22:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:52.356Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:52.401Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579026) 
;

-- 2023-05-29T08:22:52.446Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716010
;

-- 2023-05-29T08:22:52.517Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716010)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Async Batch
-- Column: C_Order.C_Async_Batch_ID
-- 2023-05-29T08:22:53.218Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575255,716011,0,294,TO_TIMESTAMP('2023-05-29 11:22:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Async Batch',TO_TIMESTAMP('2023-05-29 11:22:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:53.263Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:53.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542581) 
;

-- 2023-05-29T08:22:53.353Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716011
;

-- 2023-05-29T08:22:53.395Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716011)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Pharmacy
-- Column: C_Order.C_BPartner_Pharmacy_ID
-- 2023-05-29T08:22:54.120Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577420,716012,0,294,TO_TIMESTAMP('2023-05-29 11:22:53','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Pharmacy',TO_TIMESTAMP('2023-05-29 11:22:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:54.164Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:54.207Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579045) 
;

-- 2023-05-29T08:22:54.258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716012
;

-- 2023-05-29T08:22:54.299Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716012)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Partner Name
-- Column: C_Order.BPartnerName
-- 2023-05-29T08:22:55.032Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578892,716013,0,294,TO_TIMESTAMP('2023-05-29 11:22:54','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Partner Name',TO_TIMESTAMP('2023-05-29 11:22:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:55.075Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:55.119Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543350) 
;

-- 2023-05-29T08:22:55.172Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716013
;

-- 2023-05-29T08:22:55.214Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716013)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> eMail
-- Column: C_Order.EMail
-- 2023-05-29T08:22:55.985Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578893,716014,0,294,TO_TIMESTAMP('2023-05-29 11:22:55','YYYY-MM-DD HH24:MI:SS'),100,'',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','Y','N','N','N','N','N','eMail',TO_TIMESTAMP('2023-05-29 11:22:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:56.031Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:56.075Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881) 
;

-- 2023-05-29T08:22:56.157Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716014
;

-- 2023-05-29T08:22:56.200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716014)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Phone
-- Column: C_Order.Phone
-- 2023-05-29T08:22:56.944Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578894,716015,0,294,TO_TIMESTAMP('2023-05-29 11:22:56','YYYY-MM-DD HH24:MI:SS'),100,'Identifies a telephone number',40,'D','','Y','Y','N','N','N','N','N','Phone',TO_TIMESTAMP('2023-05-29 11:22:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:56.986Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:57.030Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505) 
;

-- 2023-05-29T08:22:57.107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716015
;

-- 2023-05-29T08:22:57.150Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716015)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Doc Sub Type
-- Column: C_Order.DocSubType
-- 2023-05-29T08:22:57.885Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579337,716016,0,294,TO_TIMESTAMP('2023-05-29 11:22:57','YYYY-MM-DD HH24:MI:SS'),100,'Document Sub Type',100,'D','The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document','Y','Y','N','N','N','N','N','Doc Sub Type',TO_TIMESTAMP('2023-05-29 11:22:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:57.927Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:57.970Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1018) 
;

-- 2023-05-29T08:22:58.019Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716016
;

-- 2023-05-29T08:22:58.061Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716016)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Goods on consignment
-- Column: C_Order.IsOnConsignment
-- 2023-05-29T08:22:58.812Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585740,716017,0,294,TO_TIMESTAMP('2023-05-29 11:22:58','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Goods on consignment',TO_TIMESTAMP('2023-05-29 11:22:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:58.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:58.900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581995) 
;

-- 2023-05-29T08:22:58.946Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716017
;

-- 2023-05-29T08:22:58.989Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716017)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Additional Text for Invoice
-- Column: C_Order.InvoiceAdditionalText
-- 2023-05-29T08:22:59.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585922,716018,0,294,TO_TIMESTAMP('2023-05-29 11:22:59','YYYY-MM-DD HH24:MI:SS'),100,'',1024,'D','Y','Y','N','N','N','N','N','Additional Text for Invoice',TO_TIMESTAMP('2023-05-29 11:22:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:22:59.721Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:22:59.765Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582034) 
;

-- 2023-05-29T08:22:59.811Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716018
;

-- 2023-05-29T08:22:59.855Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716018)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Do not show Country of Origin
-- Column: C_Order.IsNotShowOriginCountry
-- 2023-05-29T08:23:00.579Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585925,716019,0,294,TO_TIMESTAMP('2023-05-29 11:22:59','YYYY-MM-DD HH24:MI:SS'),100,'If is NO, then the Country of Origin of the products is displayed in the invoice report',1,'D','Y','Y','N','N','N','N','N','Do not show Country of Origin',TO_TIMESTAMP('2023-05-29 11:22:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:23:00.621Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:23:00.663Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582035) 
;

-- 2023-05-29T08:23:00.710Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716019
;

-- 2023-05-29T08:23:00.752Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716019)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Payment Instruction
-- Column: C_Order.C_PaymentInstruction_ID
-- 2023-05-29T08:23:01.484Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586140,716020,0,294,TO_TIMESTAMP('2023-05-29 11:23:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Payment Instruction',TO_TIMESTAMP('2023-05-29 11:23:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:23:01.528Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:23:01.598Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582077) 
;

-- 2023-05-29T08:23:01.643Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716020
;

-- 2023-05-29T08:23:01.685Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716020)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Assignment
-- Column: C_Order.UserElementString1
-- 2023-05-29T08:23:02.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586714,716021,0,294,TO_TIMESTAMP('2023-05-29 11:23:01','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','Assignment',TO_TIMESTAMP('2023-05-29 11:23:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:23:02.416Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:23:02.460Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-05-29T08:23:02.506Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716021
;

-- 2023-05-29T08:23:02.576Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716021)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString2
-- Column: C_Order.UserElementString2
-- 2023-05-29T08:23:03.392Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586715,716022,0,294,TO_TIMESTAMP('2023-05-29 11:23:02','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2023-05-29 11:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:23:03.440Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:23:03.485Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-05-29T08:23:03.532Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716022
;

-- 2023-05-29T08:23:03.581Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716022)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString3
-- Column: C_Order.UserElementString3
-- 2023-05-29T08:23:04.378Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586716,716023,0,294,TO_TIMESTAMP('2023-05-29 11:23:03','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2023-05-29 11:23:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:23:04.420Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:23:04.463Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-05-29T08:23:04.508Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716023
;

-- 2023-05-29T08:23:04.551Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716023)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString4
-- Column: C_Order.UserElementString4
-- 2023-05-29T08:23:05.266Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586717,716024,0,294,TO_TIMESTAMP('2023-05-29 11:23:04','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-05-29 11:23:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:23:05.313Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:23:05.355Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-05-29T08:23:05.401Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716024
;

-- 2023-05-29T08:23:05.442Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716024)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString5
-- Column: C_Order.UserElementString5
-- 2023-05-29T08:23:06.177Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586718,716025,0,294,TO_TIMESTAMP('2023-05-29 11:23:05','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-05-29 11:23:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:23:06.220Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:23:06.264Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-05-29T08:23:06.310Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716025
;

-- 2023-05-29T08:23:06.351Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716025)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString6
-- Column: C_Order.UserElementString6
-- 2023-05-29T08:23:07.094Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586719,716026,0,294,TO_TIMESTAMP('2023-05-29 11:23:06','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-05-29 11:23:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T08:23:07.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T08:23:07.181Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-05-29T08:23:07.227Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716026
;

-- 2023-05-29T08:23:07.276Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716026)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Assignment
-- Column: C_Order.UserElementString1
-- 2023-05-29T08:24:35.004Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:24:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716021
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString2
-- Column: C_Order.UserElementString2
-- 2023-05-29T08:24:45.170Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:24:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716022
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString3
-- Column: C_Order.UserElementString3
-- 2023-05-29T08:24:52.500Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716023
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString4
-- Column: C_Order.UserElementString4
-- 2023-05-29T08:24:59.955Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716024
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString5
-- Column: C_Order.UserElementString5
-- 2023-05-29T08:25:09.431Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:25:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716025
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString6
-- Column: C_Order.UserElementString6
-- 2023-05-29T08:25:19.390Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 11:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716026
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> advanced edit -> 10 -> advanced edit.UserElementString2
-- Column: C_Order.UserElementString2
-- 2023-05-29T08:36:00.056Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716022,0,294,540961,617887,'F',TO_TIMESTAMP('2023-05-29 11:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString2',300,0,0,TO_TIMESTAMP('2023-05-29 11:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> advanced edit -> 10 -> advanced edit.UserElementString3
-- Column: C_Order.UserElementString3
-- 2023-05-29T08:36:21.965Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716023,0,294,540961,617888,'F',TO_TIMESTAMP('2023-05-29 11:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString3',310,0,0,TO_TIMESTAMP('2023-05-29 11:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> advanced edit -> 10 -> advanced edit.UserElementString3
-- Column: C_Order.UserElementString3
-- 2023-05-29T08:37:57.616Z
UPDATE AD_UI_Element SET SeqNo=320,Updated=TO_TIMESTAMP('2023-05-29 11:37:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617888
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> advanced edit -> 10 -> advanced edit.UserElementString2
-- Column: C_Order.UserElementString2
-- 2023-05-29T08:38:07.602Z
UPDATE AD_UI_Element SET SeqNo=310,Updated=TO_TIMESTAMP('2023-05-29 11:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617887
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> advanced edit -> 10 -> advanced edit.Assignment
-- Column: C_Order.UserElementString1
-- 2023-05-29T08:38:27.377Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716021,0,294,540961,617889,'F',TO_TIMESTAMP('2023-05-29 11:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Assignment',300,0,0,TO_TIMESTAMP('2023-05-29 11:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> advanced edit -> 10 -> advanced edit.UserElementString4
-- Column: C_Order.UserElementString4
-- 2023-05-29T08:38:48.484Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716024,0,294,540961,617890,'F',TO_TIMESTAMP('2023-05-29 11:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString4',330,0,0,TO_TIMESTAMP('2023-05-29 11:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> advanced edit -> 10 -> advanced edit.UserElementString5
-- Column: C_Order.UserElementString5
-- 2023-05-29T08:39:36.373Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716025,0,294,540961,617891,'F',TO_TIMESTAMP('2023-05-29 11:39:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString5',340,0,0,TO_TIMESTAMP('2023-05-29 11:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> advanced edit -> 10 -> advanced edit.UserElementString6
-- Column: C_Order.UserElementString6
-- 2023-05-29T08:39:55.213Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716026,0,294,540961,617892,'F',TO_TIMESTAMP('2023-05-29 11:39:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString6',350,0,0,TO_TIMESTAMP('2023-05-29 11:39:54','YYYY-MM-DD HH24:MI:SS'),100)
;
















-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> advanced edit -> 10 -> advanced edit.UserElementString3
-- Column: C_Order.UserElementString3
-- 2023-05-29T08:42:57.879Z
UPDATE AD_UI_Element SET SeqNo=490,Updated=TO_TIMESTAMP('2023-05-29 11:42:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617886
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> advanced edit -> 10 -> advanced edit.UserElementString2
-- Column: C_Order.UserElementString2
-- 2023-05-29T08:43:05.142Z
UPDATE AD_UI_Element SET SeqNo=480,Updated=TO_TIMESTAMP('2023-05-29 11:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617885
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> advanced edit -> 10 -> advanced edit.Assignment
-- Column: C_Order.UserElementString1
-- 2023-05-29T08:43:26.380Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715996,0,186,540499,617893,'F',TO_TIMESTAMP('2023-05-29 11:43:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Assignment',470,0,0,TO_TIMESTAMP('2023-05-29 11:43:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> advanced edit -> 10 -> advanced edit.UserElementString4
-- Column: C_Order.UserElementString4
-- 2023-05-29T08:43:47.908Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715999,0,186,540499,617894,'F',TO_TIMESTAMP('2023-05-29 11:43:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString4',500,0,0,TO_TIMESTAMP('2023-05-29 11:43:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> advanced edit -> 10 -> advanced edit.UserElementString5
-- Column: C_Order.UserElementString5
-- 2023-05-29T08:44:05.671Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716000,0,186,540499,617895,'F',TO_TIMESTAMP('2023-05-29 11:44:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString5',510,0,0,TO_TIMESTAMP('2023-05-29 11:44:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> advanced edit -> 10 -> advanced edit.UserElementString6
-- Column: C_Order.UserElementString6
-- 2023-05-29T08:44:21.105Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716001,0,186,540499,617896,'F',TO_TIMESTAMP('2023-05-29 11:44:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString6',520,0,0,TO_TIMESTAMP('2023-05-29 11:44:20','YYYY-MM-DD HH24:MI:SS'),100)
;




-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString7
-- Column: C_Order.UserElementString7
-- 2023-05-29T12:59:26.103Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586720,716050,0,186,TO_TIMESTAMP('2023-05-29 15:59:25','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-05-29 15:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T12:59:26.140Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T12:59:26.180Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-05-29T12:59:26.227Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716050
;

-- 2023-05-29T12:59:26.266Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716050)
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> UserElementString7
-- Column: C_Order.UserElementString7
-- 2023-05-29T13:01:10.321Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 16:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716050
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> advanced edit -> 10 -> advanced edit.UserElementString7
-- Column: C_Order.UserElementString7
-- 2023-05-29T13:01:55.089Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716050,0,186,540499,617904,'F',TO_TIMESTAMP('2023-05-29 16:01:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString7',530,0,0,TO_TIMESTAMP('2023-05-29 16:01:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString7
-- Column: C_Order.UserElementString7
-- 2023-05-29T13:03:54.229Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586720,716051,0,294,TO_TIMESTAMP('2023-05-29 16:03:53','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-05-29 16:03:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T13:03:54.265Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T13:03:54.302Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-05-29T13:03:54.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716051
;

-- 2023-05-29T13:03:54.381Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716051)
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString7
-- Column: C_Order.UserElementString7
-- 2023-05-29T13:06:28.554Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 16:06:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716051
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> UserElementString7
-- Column: C_Order.UserElementString7
-- 2023-05-29T13:06:36.929Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 16:06:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716051
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> advanced edit -> 10 -> advanced edit.UserElementString7
-- Column: C_Order.UserElementString7
-- 2023-05-29T13:07:12.054Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716051,0,294,540961,617905,'F',TO_TIMESTAMP('2023-05-29 16:07:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString7',360,0,0,TO_TIMESTAMP('2023-05-29 16:07:11','YYYY-MM-DD HH24:MI:SS'),100)
;

