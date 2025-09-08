-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Approved
-- Column: SAP_GLJournal.IsApproved
-- 2022-12-15T14:51:16.277Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-15 16:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709989
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Total Credit
-- Column: SAP_GLJournal.TotalCr
-- 2022-12-15T14:51:19.273Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-15 16:51:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709998
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Total Debit
-- Column: SAP_GLJournal.TotalDr
-- 2022-12-15T14:51:20.681Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-15 16:51:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709999
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Status
-- Column: SAP_GLJournal.DocStatus
-- 2022-12-15T14:51:41.082Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-15 16:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709982
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Posting status
-- Column: SAP_GLJournal.Posted
-- 2022-12-15T14:51:45.150Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-15 16:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709992
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Posting Error
-- Column: SAP_GLJournal.PostingError_Issue_ID
-- 2022-12-15T14:51:47.863Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-15 16:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709993
;

-- 2022-12-15T14:53:46.854Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581877,0,'Acct_Currency_ID',TO_TIMESTAMP('2022-12-15 16:53:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Accounting Currency','Accounting Currency',TO_TIMESTAMP('2022-12-15 16:53:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:46.855Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581877 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SAP_GLJournal.Acct_Currency_ID
-- 2022-12-15T14:54:29.292Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585374,581877,0,30,112,542275,'Acct_Currency_ID',TO_TIMESTAMP('2022-12-15 16:54:29','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Accounting Currency',0,0,TO_TIMESTAMP('2022-12-15 16:54:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T14:54:29.294Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585374 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T14:54:29.297Z
/* DDL */  select update_Column_Translation_From_AD_Element(581877) 
;

-- 2022-12-15T14:54:31.929Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournal','ALTER TABLE public.SAP_GLJournal ADD COLUMN Acct_Currency_ID NUMERIC(10) NOT NULL')
;

-- 2022-12-15T14:54:31.936Z
ALTER TABLE SAP_GLJournal ADD CONSTRAINT AcctCurrency_SAPGLJournal FOREIGN KEY (Acct_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Field: GL Journal(540356,D) -> Journal(540854,D) -> Reversal ID
-- Column: GL_Journal.Reversal_ID
-- 2022-12-15T15:18:00.571Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,55306,710020,0,540854,TO_TIMESTAMP('2022-12-15 17:18:00','YYYY-MM-DD HH24:MI:SS'),100,'ID of document reversal',22,'D','Y','N','N','N','N','N','N','N','Reversal ID',TO_TIMESTAMP('2022-12-15 17:18:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T15:18:00.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T15:18:00.576Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53457) 
;

-- 2022-12-15T15:18:00.582Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710020
;

-- 2022-12-15T15:18:00.583Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710020)
;

-- Field: GL Journal(540356,D) -> Journal(540854,D) -> Posting Error
-- Column: GL_Journal.PostingError_Issue_ID
-- 2022-12-15T15:18:00.688Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570869,710021,0,540854,TO_TIMESTAMP('2022-12-15 17:18:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Posting Error',TO_TIMESTAMP('2022-12-15 17:18:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T15:18:00.690Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T15:18:00.691Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
;

-- 2022-12-15T15:18:00.695Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710021
;

-- 2022-12-15T15:18:00.695Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710021)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Accounting Currency
-- Column: SAP_GLJournal.Acct_Currency_ID
-- 2022-12-15T15:18:32.524Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585374,710022,0,546730,TO_TIMESTAMP('2022-12-15 17:18:32','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','Accounting Currency',TO_TIMESTAMP('2022-12-15 17:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T15:18:32.526Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T15:18:32.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581877) 
;

-- 2022-12-15T15:18:32.533Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710022
;

-- 2022-12-15T15:18:32.534Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710022)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Conversiontype
-- Column: SAP_GLJournal.C_ConversionType_ID
-- 2022-12-15T15:19:27.795Z
UPDATE AD_Field SET DisplayLogic='@Acct_Currency_ID/-1@!@C_Currency_ID/-2@',Updated=TO_TIMESTAMP('2022-12-15 17:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709973
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Currency Rate
-- Column: SAP_GLJournal.CurrencyRate
-- 2022-12-15T15:19:45.752Z
UPDATE AD_Field SET DisplayLogic='@Acct_Currency_ID/-1@!@C_Currency_ID/-2@',Updated=TO_TIMESTAMP('2022-12-15 17:19:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709977
;

