-- Column: I_Invoice_Candidate.DescriptionBottom
-- Column: I_Invoice_Candidate.DescriptionBottom
-- 2022-11-23T08:13:56.182Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585124,501680,0,14,542207,'DescriptionBottom',TO_TIMESTAMP('2022-11-23 10:13:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2048,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Schlusstext',0,0,TO_TIMESTAMP('2022-11-23 10:13:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-23T08:13:56.184Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585124 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-23T08:13:56.211Z
/* DDL */  select update_Column_Translation_From_AD_Element(501680) 
;

-- 2022-11-23T08:14:21.091Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN DescriptionBottom VARCHAR(2048)')
;

-- Column: I_Invoice_Candidate.Discount
-- Column: I_Invoice_Candidate.Discount
-- 2022-11-23T08:18:14.585Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585125,280,0,22,542207,'Discount',TO_TIMESTAMP('2022-11-23 10:18:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Abschlag in Prozent','D',0,22,'"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rabatt %',0,0,TO_TIMESTAMP('2022-11-23 10:18:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-23T08:18:14.586Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585125 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-23T08:18:14.589Z
/* DDL */  select update_Column_Translation_From_AD_Element(280) 
;

-- 2022-11-23T08:18:18.178Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Discount NUMERIC')
;

-- Column: I_Invoice_Candidate.AD_User_InCharge_ID
-- Column: I_Invoice_Candidate.AD_User_InCharge_ID
-- 2022-11-23T08:19:20.800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585126,541510,0,18,110,542207,164,'AD_User_InCharge_ID',TO_TIMESTAMP('2022-11-23 10:19:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Person, die bei einem fachlichen Problem vom System informiert wird.','de.metas.swat',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Betreuer',0,0,TO_TIMESTAMP('2022-11-23 10:19:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-23T08:19:20.802Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585126 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-23T08:19:20.804Z
/* DDL */  select update_Column_Translation_From_AD_Element(541510) 
;

-- 2022-11-23T08:19:24.200Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN AD_User_InCharge_ID NUMERIC(10)')
;

-- 2022-11-23T08:19:24.205Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT ADUserInCharge_IInvoiceCandidate FOREIGN KEY (AD_User_InCharge_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_Invoice_Candidate.Price
-- Column: I_Invoice_Candidate.Price
-- 2022-11-23T08:28:33.218Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585127,1416,0,37,542207,'Price',TO_TIMESTAMP('2022-11-23 10:28:33','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Preis','D',0,10,'Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preis',0,0,TO_TIMESTAMP('2022-11-23 10:28:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-23T08:28:33.220Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585127 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-23T08:28:33.222Z
/* DDL */  select update_Column_Translation_From_AD_Element(1416) 
;

-- 2022-11-23T08:28:35.182Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Price NUMERIC ')
;

-- 2022-11-23T08:37:29.429Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581722,0,'User_InCharge',TO_TIMESTAMP('2022-11-23 10:37:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','User In Charge','User In Charge',TO_TIMESTAMP('2022-11-23 10:37:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T08:37:29.432Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581722 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: I_Invoice_Candidate.User_InCharge
-- Column: I_Invoice_Candidate.User_InCharge
-- 2022-11-23T08:37:39.334Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585128,581722,0,10,542207,'User_InCharge',TO_TIMESTAMP('2022-11-23 10:37:39','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,60,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'User In Charge',0,0,TO_TIMESTAMP('2022-11-23 10:37:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-23T08:37:39.336Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585128 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-23T08:37:39.338Z
/* DDL */  select update_Column_Translation_From_AD_Element(581722) 
;

-- 2022-11-23T08:37:42.918Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN User_InCharge VARCHAR(60)')
;

-- Column: I_Invoice_Candidate.AD_User_InCharge_ID
-- Column: I_Invoice_Candidate.AD_User_InCharge_ID
-- 2022-11-23T08:39:08.971Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-23 10:39:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585126
;

-- 2022-11-23T14:32:05.219Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE I_Invoice_Candidate DROP COLUMN IF EXISTS User_InCharge')
;

-- Column: I_Invoice_Candidate.User_InCharge
-- Column: I_Invoice_Candidate.User_InCharge
-- 2022-11-23T14:32:05.270Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585128
;

-- 2022-11-23T14:32:05.280Z
DELETE FROM AD_Column WHERE AD_Column_ID=585128
;

-- 2022-11-23T14:32:24.861Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=581722
;

-- 2022-11-23T14:32:24.865Z
DELETE FROM AD_Element WHERE AD_Element_ID=581722
;



-- Table: I_Invoice_Candidate
-- Table: I_Invoice_Candidate
-- 2022-11-24T14:58:31.742Z
UPDATE AD_Table SET AD_Window_ID=541605,Updated=TO_TIMESTAMP('2022-11-24 16:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542207
;

-- Field: Import - Rechnungskandidaten -> Import - Invoice candiate -> Schlusstext
-- Column: I_Invoice_Candidate.DescriptionBottom
-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Schlusstext
-- Column: I_Invoice_Candidate.DescriptionBottom
-- 2022-11-24T14:58:48.817Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585124,708205,0,546594,TO_TIMESTAMP('2022-11-24 16:58:48','YYYY-MM-DD HH24:MI:SS'),100,2048,'D','Y','N','N','N','N','N','N','N','Schlusstext',TO_TIMESTAMP('2022-11-24 16:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T14:58:48.818Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708205 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T14:58:48.820Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501680) 
;

-- 2022-11-24T14:58:48.832Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708205
;

-- 2022-11-24T14:58:48.833Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708205)
;

-- Field: Import - Rechnungskandidaten -> Import - Invoice candiate -> Rabatt %
-- Column: I_Invoice_Candidate.Discount
-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Rabatt %
-- Column: I_Invoice_Candidate.Discount
-- 2022-11-24T14:58:48.918Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585125,708206,0,546594,TO_TIMESTAMP('2022-11-24 16:58:48','YYYY-MM-DD HH24:MI:SS'),100,'Abschlag in Prozent',22,'D','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','N','N','N','N','N','N','Rabatt %',TO_TIMESTAMP('2022-11-24 16:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T14:58:48.919Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708206 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T14:58:48.920Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(280) 
;

-- 2022-11-24T14:58:48.925Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708206
;

-- 2022-11-24T14:58:48.926Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708206)
;

-- Field: Import - Rechnungskandidaten -> Import - Invoice candiate -> Betreuer
-- Column: I_Invoice_Candidate.AD_User_InCharge_ID
-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Betreuer
-- Column: I_Invoice_Candidate.AD_User_InCharge_ID
-- 2022-11-24T14:58:49.014Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585126,708207,0,546594,TO_TIMESTAMP('2022-11-24 16:58:48','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.',10,'D','Y','N','N','N','N','N','N','N','Betreuer',TO_TIMESTAMP('2022-11-24 16:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T14:58:49.015Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708207 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T14:58:49.017Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541510) 
;

-- 2022-11-24T14:58:49.020Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708207
;

-- 2022-11-24T14:58:49.021Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708207)
;

-- Field: Import - Rechnungskandidaten -> Import - Invoice candiate -> Preis
-- Column: I_Invoice_Candidate.Price
-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Preis
-- Column: I_Invoice_Candidate.Price
-- 2022-11-24T14:58:49.100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585127,708208,0,546594,TO_TIMESTAMP('2022-11-24 16:58:49','YYYY-MM-DD HH24:MI:SS'),100,'Preis',10,'D','Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','N','N','N','N','N','N','Preis',TO_TIMESTAMP('2022-11-24 16:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T14:58:49.101Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T14:58:49.102Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1416) 
;

-- 2022-11-24T14:58:49.106Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708208
;

-- 2022-11-24T14:58:49.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708208)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Preis
-- Column: I_Invoice_Candidate.Price
-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Preis
-- Column: I_Invoice_Candidate.Price
-- 2022-11-24T14:59:25.233Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708208,0,546594,549841,613582,'F',TO_TIMESTAMP('2022-11-24 16:59:25','YYYY-MM-DD HH24:MI:SS'),100,'Preis','Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','N','Y','N','N','N',0,'Preis',50,0,0,TO_TIMESTAMP('2022-11-24 16:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Rabatt %
-- Column: I_Invoice_Candidate.Discount
-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Rabatt %
-- Column: I_Invoice_Candidate.Discount
-- 2022-11-24T14:59:34.885Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708206,0,546594,549841,613583,'F',TO_TIMESTAMP('2022-11-24 16:59:34','YYYY-MM-DD HH24:MI:SS'),100,'Abschlag in Prozent','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','N','Y','N','N','N',0,'Rabatt %',60,0,0,TO_TIMESTAMP('2022-11-24 16:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Schlusstext
-- Column: I_Invoice_Candidate.DescriptionBottom
-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Schlusstext
-- Column: I_Invoice_Candidate.DescriptionBottom
-- 2022-11-24T15:00:19.788Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708205,0,546594,549841,613584,'F',TO_TIMESTAMP('2022-11-24 17:00:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Schlusstext',70,0,0,TO_TIMESTAMP('2022-11-24 17:00:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Betreuer
-- Column: I_Invoice_Candidate.AD_User_InCharge_ID
-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Betreuer
-- Column: I_Invoice_Candidate.AD_User_InCharge_ID
-- 2022-11-24T15:00:28.663Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708207,0,546594,549841,613585,'F',TO_TIMESTAMP('2022-11-24 17:00:28','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.','Y','N','N','Y','N','N','N',0,'Betreuer',80,0,0,TO_TIMESTAMP('2022-11-24 17:00:28','YYYY-MM-DD HH24:MI:SS'),100)
;





-- adjust import foramt---



-- 2022-11-23T15:47:27.112Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,585124,540077,541802,0,TO_TIMESTAMP('2022-11-23 17:47:26','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Schlusstext',180,18,TO_TIMESTAMP('2022-11-23 17:47:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T15:48:07.822Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,585126,540077,541803,0,TO_TIMESTAMP('2022-11-23 17:48:07','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N','Y','Betreuer',20,2,TO_TIMESTAMP('2022-11-23 17:48:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T15:48:25.859Z
UPDATE AD_ImpFormat_Row SET SeqNo=190, StartNo=19,Updated=TO_TIMESTAMP('2022-11-23 17:48:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541803
;

-- 2022-11-23T15:48:52.582Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,585125,540077,541804,0,TO_TIMESTAMP('2022-11-23 17:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N',0,'Y','Rabatt ',200,20,TO_TIMESTAMP('2022-11-23 17:48:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T15:49:10.419Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,585127,540077,541805,0,TO_TIMESTAMP('2022-11-23 17:49:10','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N',0,'Y','Price',210,21,TO_TIMESTAMP('2022-11-23 17:49:10','YYYY-MM-DD HH24:MI:SS'),100)
;


--