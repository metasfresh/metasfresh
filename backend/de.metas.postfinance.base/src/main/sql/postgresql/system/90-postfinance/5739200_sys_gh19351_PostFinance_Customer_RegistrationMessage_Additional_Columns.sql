-- Column: PostFinance_Customer_Registration_Message.C_BPartner_Location_ID
-- 2024-11-14T12:50:23.757Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589389,189,0,19,542391,131,'C_BPartner_Location_ID',TO_TIMESTAMP('2024-11-14 14:50:23.557','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','de.metas.postfinance',0,22,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort',0,0,TO_TIMESTAMP('2024-11-14 14:50:23.557','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-14T12:50:23.769Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589389 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-14T12:50:23.802Z
/* DDL */  select update_Column_Translation_From_AD_Element(189)
;

-- 2024-11-14T12:50:26.941Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Customer_Registration_Message','ALTER TABLE public.PostFinance_Customer_Registration_Message ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- 2024-11-14T12:50:26.973Z
ALTER TABLE PostFinance_Customer_Registration_Message ADD CONSTRAINT CBPartnerLocation_PostFinanceCustomerRegistrationMessage FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: PostFinance_Customer_Registration_Message.AD_User_ID
-- 2024-11-14T12:51:06.085Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589390,138,0,19,542391,123,'AD_User_ID',TO_TIMESTAMP('2024-11-14 14:51:05.795','YYYY-MM-DD HH24:MI:SS.US'),100,'N','-1','User within the system - Internal or Business Partner Contact','de.metas.postfinance',0,22,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ansprechpartner',0,0,TO_TIMESTAMP('2024-11-14 14:51:05.795','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-14T12:51:06.088Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589390 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-14T12:51:06.092Z
/* DDL */  select update_Column_Translation_From_AD_Element(138)
;

-- 2024-11-14T12:51:15.501Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Customer_Registration_Message','ALTER TABLE public.PostFinance_Customer_Registration_Message ADD COLUMN AD_User_ID NUMERIC(10)')
;

-- 2024-11-14T12:51:15.514Z
ALTER TABLE PostFinance_Customer_Registration_Message ADD CONSTRAINT ADUser_PostFinanceCustomerRegistrationMessage FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: PostFinance Kunden-Registrierungsnachricht(541769,D) -> PostFinance Kunden-Registrierungsnachricht(547410,D) -> Ansprechpartner
-- Column: PostFinance_Customer_Registration_Message.AD_User_ID
-- 2024-11-14T12:52:40.737Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589390,733825,0,547410,TO_TIMESTAMP('2024-11-14 14:52:40.5','YYYY-MM-DD HH24:MI:SS.US'),100,'User within the system - Internal or Business Partner Contact',22,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2024-11-14 14:52:40.5','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-14T12:52:40.742Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-14T12:52:40.746Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138)
;

-- 2024-11-14T12:52:40.785Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733825
;

-- 2024-11-14T12:52:40.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733825)
;

-- Field: PostFinance Kunden-Registrierungsnachricht(541769,D) -> PostFinance Kunden-Registrierungsnachricht(547410,D) -> Standort
-- Column: PostFinance_Customer_Registration_Message.C_BPartner_Location_ID
-- 2024-11-14T12:52:46.412Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589389,733826,0,547410,TO_TIMESTAMP('2024-11-14 14:52:46.246','YYYY-MM-DD HH24:MI:SS.US'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',22,'D','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2024-11-14 14:52:46.246','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-14T12:52:46.415Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-14T12:52:46.418Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189)
;

-- 2024-11-14T12:52:46.432Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733826
;

-- 2024-11-14T12:52:46.439Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733826)
;

