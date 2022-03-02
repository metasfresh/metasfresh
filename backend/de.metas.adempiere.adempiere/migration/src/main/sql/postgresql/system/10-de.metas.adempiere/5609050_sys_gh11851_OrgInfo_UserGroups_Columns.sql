-- 2021-10-12T18:56:53.285Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580036,0,'C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID',TO_TIMESTAMP('2021-10-12 21:56:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Supplier Approval Expiration Notify User Group','Supplier Approval Expiration Notify User Group',TO_TIMESTAMP('2021-10-12 21:56:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-12T18:56:53.366Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580036 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-12T18:57:34.362Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577719,580036,0,30,541355,228,'C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID',TO_TIMESTAMP('2021-10-12 21:57:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Supplier Approval Expiration Notify User Group',0,0,TO_TIMESTAMP('2021-10-12 21:57:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-12T18:57:34.439Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577719 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-12T18:57:34.621Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580036) 
;

-- 2021-10-12T18:58:22.352Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_OrgInfo','ALTER TABLE public.AD_OrgInfo ADD COLUMN C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID NUMERIC(10)')
;

-- 2021-10-12T18:58:22.661Z
-- URL zum Konzept
ALTER TABLE AD_OrgInfo ADD CONSTRAINT CBPSupplierApprovalExpirationNotifyUserGroup_ADOrgInfo FOREIGN KEY (C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID) REFERENCES public.AD_UserGroup DEFERRABLE INITIALLY DEFERRED
;

-- 2021-10-12T18:59:53.959Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580037,0,'C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID',TO_TIMESTAMP('2021-10-12 21:59:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Partner Created From Another Org Notify UserGroup','Partner Created From Another Org Notify UserGroup',TO_TIMESTAMP('2021-10-12 21:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-12T18:59:54.100Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580037 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-12T19:00:36.351Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577720,580037,0,30,541355,228,'C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID',TO_TIMESTAMP('2021-10-12 22:00:35','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Partner Created From Another Org Notify UserGroup',0,0,TO_TIMESTAMP('2021-10-12 22:00:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-12T19:00:36.430Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577720 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-12T19:00:36.600Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580037) 
;

-- 2021-10-12T19:00:47.149Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_OrgInfo','ALTER TABLE public.AD_OrgInfo ADD COLUMN C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID NUMERIC(10)')
;

-- 2021-10-12T19:00:47.427Z
-- URL zum Konzept
ALTER TABLE AD_OrgInfo ADD CONSTRAINT CBPartnerCreatedFromAnotherOrgNotifyUserGroup_ADOrgInfo FOREIGN KEY (C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID) REFERENCES public.AD_UserGroup DEFERRABLE INITIALLY DEFERRED
;
























-- 2021-10-13T07:45:06.479Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577719,663473,0,170,TO_TIMESTAMP('2021-10-13 10:45:05','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Supplier Approval Expiration Notify User Group',TO_TIMESTAMP('2021-10-13 10:45:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-13T07:45:06.554Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=663473 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-10-13T07:45:06.669Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580036) 
;

-- 2021-10-13T07:45:06.761Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=663473
;

-- 2021-10-13T07:45:06.816Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(663473)
;

-- 2021-10-13T07:45:07.794Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577720,663474,0,170,TO_TIMESTAMP('2021-10-13 10:45:06','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Partner Created From Another Org Notify UserGroup',TO_TIMESTAMP('2021-10-13 10:45:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-13T07:45:07.856Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=663474 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-10-13T07:45:07.936Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580037) 
;

-- 2021-10-13T07:45:08.026Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=663474
;

-- 2021-10-13T07:45:08.088Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(663474)
;

-- 2021-10-13T08:47:55.530Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,663473,0,170,540463,592814,'F',TO_TIMESTAMP('2021-10-13 11:47:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Supplier Approval Expiration Notify User Group',171,0,0,TO_TIMESTAMP('2021-10-13 11:47:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-13T08:49:02.130Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,663474,0,170,540463,592815,'F',TO_TIMESTAMP('2021-10-13 11:49:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Partner Created From Another Org Notify UserGroup',172,0,0,TO_TIMESTAMP('2021-10-13 11:49:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-13T08:49:21.470Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2021-10-13 11:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=592814
;

-- 2021-10-13T08:49:21.795Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2021-10-13 11:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=592815
;

-- 2021-10-13T08:49:22.090Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2021-10-13 11:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544524
;

-- 2021-10-13T08:49:22.412Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2021-10-13 11:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544525
;

