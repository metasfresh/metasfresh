-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Ship-from
-- Column: SAP_GLJournalLine.UserElementString2
-- 2023-07-16T06:39:10.347Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586395,716673,0,546731,TO_TIMESTAMP('2023-07-16 09:39:09','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.acct','Y','N','N','N','N','N','N','N','Ship-from',TO_TIMESTAMP('2023-07-16 09:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:10.357Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716673 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:10.437Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-07-16T06:39:10.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716673
;

-- 2023-07-16T06:39:10.475Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716673)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Ship-to
-- Column: SAP_GLJournalLine.UserElementString3
-- 2023-07-16T06:39:10.605Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586396,716674,0,546731,TO_TIMESTAMP('2023-07-16 09:39:10','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.acct','Y','N','N','N','N','N','N','N','Ship-to',TO_TIMESTAMP('2023-07-16 09:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:10.607Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716674 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:10.610Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-07-16T06:39:10.617Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716674
;

-- 2023-07-16T06:39:10.619Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716674)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString4
-- Column: SAP_GLJournalLine.UserElementString4
-- 2023-07-16T06:39:10.743Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586397,716675,0,546731,TO_TIMESTAMP('2023-07-16 09:39:10','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.acct','Y','N','N','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-07-16 09:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:10.747Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716675 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:10.750Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-07-16T06:39:10.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716675
;

-- 2023-07-16T06:39:10.760Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716675)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString5
-- Column: SAP_GLJournalLine.UserElementString5
-- 2023-07-16T06:39:10.885Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586398,716676,0,546731,TO_TIMESTAMP('2023-07-16 09:39:10','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.acct','Y','N','N','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-07-16 09:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:10.889Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716676 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:10.893Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-07-16T06:39:10.901Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716676
;

-- 2023-07-16T06:39:10.904Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716676)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString6
-- Column: SAP_GLJournalLine.UserElementString6
-- 2023-07-16T06:39:11.048Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586399,716677,0,546731,TO_TIMESTAMP('2023-07-16 09:39:10','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.acct','Y','N','N','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-07-16 09:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:11.056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716677 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:11.063Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-07-16T06:39:11.080Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716677
;

-- 2023-07-16T06:39:11.086Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716677)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString7
-- Column: SAP_GLJournalLine.UserElementString7
-- 2023-07-16T06:39:11.258Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586400,716678,0,546731,TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.acct','Y','N','N','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:11.266Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716678 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:11.274Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-07-16T06:39:11.295Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716678
;

-- 2023-07-16T06:39:11.298Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716678)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Open Item Key
-- Column: SAP_GLJournalLine.OpenItemKey
-- 2023-07-16T06:39:11.441Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587043,716679,0,546731,TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.acct','Y','N','N','N','N','N','N','N','Open Item Key',TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:11.444Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716679 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:11.447Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582532) 
;

-- 2023-07-16T06:39:11.454Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716679
;

-- 2023-07-16T06:39:11.457Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716679)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Business Partner
-- Column: SAP_GLJournalLine.C_BPartner_ID
-- 2023-07-16T06:39:11.589Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587044,716680,0,546731,TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.acct','','Y','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:11.593Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716680 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:11.596Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-07-16T06:39:11.617Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716680
;

-- 2023-07-16T06:39:11.620Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716680)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Fields are Read Only for User
-- Column: SAP_GLJournalLine.IsFieldsReadOnlyInUI
-- 2023-07-16T06:39:11.740Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587046,716681,0,546731,TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.acct','Y','N','N','N','N','N','N','N','Fields are Read Only for User',TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:11.743Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716681 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:11.746Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582537) 
;

-- 2023-07-16T06:39:11.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716681
;

-- 2023-07-16T06:39:11.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716681)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Open Item Transaction Type
-- Column: SAP_GLJournalLine.OI_TrxType
-- 2023-07-16T06:39:11.889Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587049,716682,0,546731,TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.acct','Y','N','N','N','N','N','N','N','Open Item Transaction Type',TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:11.891Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716682 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:11.894Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582538) 
;

-- 2023-07-16T06:39:11.901Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716682
;

-- 2023-07-16T06:39:11.902Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716682)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Processed
-- Column: SAP_GLJournalLine.Processed
-- 2023-07-16T06:39:12.024Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587115,716683,0,546731,TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100,'',1,'de.metas.acct','','Y','N','N','N','N','N','N','N','Processed',TO_TIMESTAMP('2023-07-16 09:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:12.027Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716683 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:12.029Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-07-16T06:39:12.044Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716683
;

-- 2023-07-16T06:39:12.047Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716683)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Invoice
-- Column: SAP_GLJournalLine.OI_Invoice_ID
-- 2023-07-16T06:39:12.185Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587118,716684,0,546731,TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','OI Clearing Invoice',TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:12.189Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716684 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:12.192Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582553) 
;

-- 2023-07-16T06:39:12.200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716684
;

-- 2023-07-16T06:39:12.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716684)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Payment
-- Column: SAP_GLJournalLine.OI_Payment_ID
-- 2023-07-16T06:39:12.336Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587119,716685,0,546731,TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','OI Clearing Payment',TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:12.343Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716685 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:12.347Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582554) 
;

-- 2023-07-16T06:39:12.353Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716685
;

-- 2023-07-16T06:39:12.356Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716685)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Bank Statement
-- Column: SAP_GLJournalLine.OI_BankStatement_ID
-- 2023-07-16T06:39:12.498Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587120,716686,0,546731,TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','OI Clearing Bank Statement',TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:12.502Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716686 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:12.506Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582555) 
;

-- 2023-07-16T06:39:12.513Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716686
;

-- 2023-07-16T06:39:12.515Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716686)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Bank Statement Line
-- Column: SAP_GLJournalLine.OI_BankStatementLine_ID
-- 2023-07-16T06:39:12.657Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587121,716687,0,546731,TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','OI Clearing Bank Statement Line',TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:12.660Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716687 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:12.664Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582556) 
;

-- 2023-07-16T06:39:12.672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716687
;

-- 2023-07-16T06:39:12.674Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716687)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Bank Statement Line Reference
-- Column: SAP_GLJournalLine.OI_BankStatementLine_Ref_ID
-- 2023-07-16T06:39:12.821Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587122,716688,0,546731,TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','OI Clearing Bank Statement Line Reference',TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:12.825Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716688 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:12.829Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582557) 
;

-- 2023-07-16T06:39:12.837Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716688
;

-- 2023-07-16T06:39:12.840Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716688)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Account conceptual name
-- Column: SAP_GLJournalLine.OI_AccountConceptualName
-- 2023-07-16T06:39:12.985Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587123,716689,0,546731,TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.acct','Y','N','N','N','N','N','N','N','OI Clearing Account conceptual name',TO_TIMESTAMP('2023-07-16 09:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T06:39:12.989Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716689 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T06:39:12.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582558) 
;

-- 2023-07-16T06:39:13Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716689
;

-- 2023-07-16T06:39:13.002Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716689)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Business Partner
-- Column: SAP_GLJournalLine.C_BPartner_ID
-- 2023-07-16T06:40:05.591Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716680,0,546731,550193,618257,'F',TO_TIMESTAMP('2023-07-16 09:40:05','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Business Partner',50,0,0,TO_TIMESTAMP('2023-07-16 09:40:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Ship-from
-- Column: SAP_GLJournalLine.UserElementString2
-- 2023-07-16T06:43:23.182Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716673,0,546731,550193,618258,'F',TO_TIMESTAMP('2023-07-16 09:43:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ship-from',60,0,0,TO_TIMESTAMP('2023-07-16 09:43:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Ship-to
-- Column: SAP_GLJournalLine.UserElementString3
-- 2023-07-16T06:43:57.212Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716674,0,546731,550193,618259,'F',TO_TIMESTAMP('2023-07-16 09:43:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ship-to',70,0,0,TO_TIMESTAMP('2023-07-16 09:43:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString4
-- Column: SAP_GLJournalLine.UserElementString4
-- 2023-07-16T06:44:04.253Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716675,0,546731,550193,618260,'F',TO_TIMESTAMP('2023-07-16 09:44:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString4',80,0,0,TO_TIMESTAMP('2023-07-16 09:44:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString5
-- Column: SAP_GLJournalLine.UserElementString5
-- 2023-07-16T06:44:10.667Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716676,0,546731,550193,618261,'F',TO_TIMESTAMP('2023-07-16 09:44:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString5',90,0,0,TO_TIMESTAMP('2023-07-16 09:44:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString6
-- Column: SAP_GLJournalLine.UserElementString6
-- 2023-07-16T06:44:17.452Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716677,0,546731,550193,618262,'F',TO_TIMESTAMP('2023-07-16 09:44:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString6',100,0,0,TO_TIMESTAMP('2023-07-16 09:44:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString7
-- Column: SAP_GLJournalLine.UserElementString7
-- 2023-07-16T06:44:26.515Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716678,0,546731,550193,618263,'F',TO_TIMESTAMP('2023-07-16 09:44:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString7',110,0,0,TO_TIMESTAMP('2023-07-16 09:44:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Ship-from
-- Column: SAP_GLJournalLine.UserElementString2
-- 2023-07-16T06:44:51.159Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/''X''@=Y',Updated=TO_TIMESTAMP('2023-07-16 09:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716673
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Ship-to
-- Column: SAP_GLJournalLine.UserElementString3
-- 2023-07-16T06:44:54.020Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/''X''@=Y',Updated=TO_TIMESTAMP('2023-07-16 09:44:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716674
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString4
-- Column: SAP_GLJournalLine.UserElementString4
-- 2023-07-16T06:44:57.208Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/''X''@=Y',Updated=TO_TIMESTAMP('2023-07-16 09:44:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716675
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString5
-- Column: SAP_GLJournalLine.UserElementString5
-- 2023-07-16T06:45:00.643Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/''X''@=Y',Updated=TO_TIMESTAMP('2023-07-16 09:45:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716676
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString6
-- Column: SAP_GLJournalLine.UserElementString6
-- 2023-07-16T06:45:03.784Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/''X''@=Y',Updated=TO_TIMESTAMP('2023-07-16 09:45:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716677
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString7
-- Column: SAP_GLJournalLine.UserElementString7
-- 2023-07-16T06:45:08.041Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/''X''@=Y',Updated=TO_TIMESTAMP('2023-07-16 09:45:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716678
;

-- UI Column: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20
-- UI Element Group: Open item
-- 2023-07-16T06:45:30.640Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546538,550827,TO_TIMESTAMP('2023-07-16 09:45:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Open item',20,TO_TIMESTAMP('2023-07-16 09:45:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20
-- UI Element Group: org&client
-- 2023-07-16T06:45:32.954Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2023-07-16 09:45:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550194
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> Open item.Open Item Transaction Type
-- Column: SAP_GLJournalLine.OI_TrxType
-- 2023-07-16T06:46:06.837Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716682,0,546731,550827,618264,'F',TO_TIMESTAMP('2023-07-16 09:46:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Open Item Transaction Type',10,0,0,TO_TIMESTAMP('2023-07-16 09:46:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> Open item.Open Item Key
-- Column: SAP_GLJournalLine.OpenItemKey
-- 2023-07-16T06:46:14.025Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716679,0,546731,550827,618265,'F',TO_TIMESTAMP('2023-07-16 09:46:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Open Item Key',20,0,0,TO_TIMESTAMP('2023-07-16 09:46:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> Open item.OI Clearing Account conceptual name
-- Column: SAP_GLJournalLine.OI_AccountConceptualName
-- 2023-07-16T06:46:24.543Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716689,0,546731,550827,618266,'F',TO_TIMESTAMP('2023-07-16 09:46:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','OI Clearing Account conceptual name',30,0,0,TO_TIMESTAMP('2023-07-16 09:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> Open item.OI Clearing Invoice
-- Column: SAP_GLJournalLine.OI_Invoice_ID
-- 2023-07-16T06:46:40.286Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716684,0,546731,550827,618267,'F',TO_TIMESTAMP('2023-07-16 09:46:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','OI Clearing Invoice',40,0,0,TO_TIMESTAMP('2023-07-16 09:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> Open item.OI Clearing Payment
-- Column: SAP_GLJournalLine.OI_Payment_ID
-- 2023-07-16T06:46:45.882Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716685,0,546731,550827,618268,'F',TO_TIMESTAMP('2023-07-16 09:46:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','OI Clearing Payment',50,0,0,TO_TIMESTAMP('2023-07-16 09:46:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> Open item.OI Clearing Bank Statement
-- Column: SAP_GLJournalLine.OI_BankStatement_ID
-- 2023-07-16T06:46:53.815Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716686,0,546731,550827,618269,'F',TO_TIMESTAMP('2023-07-16 09:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','OI Clearing Bank Statement',60,0,0,TO_TIMESTAMP('2023-07-16 09:46:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> Open item.OI Clearing Bank Statement Line
-- Column: SAP_GLJournalLine.OI_BankStatementLine_ID
-- 2023-07-16T06:47:01.150Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716687,0,546731,550827,618270,'F',TO_TIMESTAMP('2023-07-16 09:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','OI Clearing Bank Statement Line',70,0,0,TO_TIMESTAMP('2023-07-16 09:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> Open item.OI Clearing Bank Statement Line Reference
-- Column: SAP_GLJournalLine.OI_BankStatementLine_Ref_ID
-- 2023-07-16T06:47:08.084Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716688,0,546731,550827,618271,'F',TO_TIMESTAMP('2023-07-16 09:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','OI Clearing Bank Statement Line Reference',80,0,0,TO_TIMESTAMP('2023-07-16 09:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString7
-- Column: SAP_GLJournalLine.UserElementString7
-- 2023-07-16T06:47:19.278Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:47:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716678
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString6
-- Column: SAP_GLJournalLine.UserElementString6
-- 2023-07-16T06:47:21.020Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716677
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString5
-- Column: SAP_GLJournalLine.UserElementString5
-- 2023-07-16T06:47:22.214Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716676
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString4
-- Column: SAP_GLJournalLine.UserElementString4
-- 2023-07-16T06:47:23.581Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716675
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Ship-to
-- Column: SAP_GLJournalLine.UserElementString3
-- 2023-07-16T06:47:28.054Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716674
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Ship-from
-- Column: SAP_GLJournalLine.UserElementString2
-- 2023-07-16T06:47:29.308Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716673
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-16T06:48:13.896Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716634
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Open Item Key
-- Column: SAP_GLJournalLine.OpenItemKey
-- 2023-07-16T06:48:32.327Z
UPDATE AD_Field SET DisplayLogic='@IsOpenItem/X@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:48:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716679
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Account conceptual name
-- Column: SAP_GLJournalLine.OI_AccountConceptualName
-- 2023-07-16T06:48:41.504Z
UPDATE AD_Field SET DisplayLogic='@IsOpenItem/X@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716689
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Bank Statement
-- Column: SAP_GLJournalLine.OI_BankStatement_ID
-- 2023-07-16T06:48:45.423Z
UPDATE AD_Field SET DisplayLogic='@IsOpenItem/X@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:48:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716686
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Bank Statement Line
-- Column: SAP_GLJournalLine.OI_BankStatementLine_ID
-- 2023-07-16T06:48:48.558Z
UPDATE AD_Field SET DisplayLogic='@IsOpenItem/X@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716687
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Bank Statement Line Reference
-- Column: SAP_GLJournalLine.OI_BankStatementLine_Ref_ID
-- 2023-07-16T06:48:54.397Z
UPDATE AD_Field SET DisplayLogic='@IsOpenItem/X@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716688
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Invoice
-- Column: SAP_GLJournalLine.OI_Invoice_ID
-- 2023-07-16T06:48:57.841Z
UPDATE AD_Field SET DisplayLogic='@IsOpenItem/X@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716684
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI Clearing Payment
-- Column: SAP_GLJournalLine.OI_Payment_ID
-- 2023-07-16T06:49:01.553Z
UPDATE AD_Field SET DisplayLogic='@IsOpenItem/X@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:49:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716685
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Open Item Transaction Type
-- Column: SAP_GLJournalLine.OI_TrxType
-- 2023-07-16T06:49:05.014Z
UPDATE AD_Field SET DisplayLogic='@IsOpenItem/X@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-16 09:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716682
;

