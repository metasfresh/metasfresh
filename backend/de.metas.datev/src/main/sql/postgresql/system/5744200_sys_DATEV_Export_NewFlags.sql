


-- 2025-01-20T16:16:16.355Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583434,0,'IsNegateInboundAmounts',TO_TIMESTAMP('2025-01-20 18:16:15.644','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Negate Inbound Amounts','Negate Inbound Amounts',TO_TIMESTAMP('2025-01-20 18:16:15.644','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-01-20T16:16:16.385Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583434 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- 2025-01-20T16:28:56.195Z
UPDATE AD_Element SET Description='If ticked, for inbound amounts (sales invoices and purchase credit memos), the amount and tax are multiplied with -1.',Updated=TO_TIMESTAMP('2025-01-20 17:28:56.194','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583434
;

-- 2025-01-20T16:28:56.199Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583434,'de_DE') 
;

-- 2025-01-20T16:28:58.144Z
UPDATE AD_Element SET Help='If ticked, for inbound amounts (sales invoices and purchase credit memos), the amount and tax are multiplied with -1.',Updated=TO_TIMESTAMP('2025-01-20 17:28:58.144','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583434
;

-- 2025-01-20T16:28:58.145Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583434,'de_DE') 
;



-- Column: DATEV_Export.IsNegateInboundAmounts
-- Column: DATEV_Export.IsNegateInboundAmounts
-- 2025-01-20T16:52:11.837Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589600,583434,0,20,540934,'IsNegateInboundAmounts',TO_TIMESTAMP('2025-01-20 18:52:11.311','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.datev',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Negate Inbound Amounts',0,0,TO_TIMESTAMP('2025-01-20 18:52:11.311','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-01-20T16:52:11.868Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589600 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-20T16:52:12.094Z
/* DDL */  select update_Column_Translation_From_AD_Element(583434) 
;

-- 2025-01-20T16:52:19.083Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN IsNegateInboundAmounts CHAR(1) DEFAULT ''N'' CHECK (IsNegateInboundAmounts IN (''Y'',''N'')) NOT NULL')
;

-- Field: Buchungen Export_OLD -> Buchungen Export -> Negate Inbound Amounts
-- Column: DATEV_Export.IsNegateInboundAmounts
-- Field: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Negate Inbound Amounts
-- Column: DATEV_Export.IsNegateInboundAmounts
-- 2025-01-20T16:57:33.572Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589600,734668,0,541036,TO_TIMESTAMP('2025-01-20 18:57:32.968','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.datev','Y','N','N','N','N','N','N','N','Negate Inbound Amounts',TO_TIMESTAMP('2025-01-20 18:57:32.968','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-01-20T16:57:33.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734668 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-20T16:57:33.630Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583434) 
;

-- 2025-01-20T16:57:33.664Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734668
;

-- 2025-01-20T16:57:33.693Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734668)
;

-- UI Element: Buchungen Export_OLD -> Buchungen Export.Negate Inbound Amounts
-- Column: DATEV_Export.IsNegateInboundAmounts
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Negate Inbound Amounts
-- Column: DATEV_Export.IsNegateInboundAmounts
-- 2025-01-20T16:57:58.630Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734668,0,541036,541481,627809,'F',TO_TIMESTAMP('2025-01-20 18:57:58.107','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Negate Inbound Amounts',40,0,0,TO_TIMESTAMP('2025-01-20 18:57:58.107','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Element: IsNegateInboundAmounts
-- 2025-01-21T11:47:29.726Z
UPDATE AD_Element_Trl SET Description='When ticked, the tax and amount are negated.',Updated=TO_TIMESTAMP('2025-01-21 13:47:29.725','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583434 AND AD_Language='de_CH'
;

-- 2025-01-21T11:47:29.782Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583434,'de_CH') 
;






-- 2025-01-21T11:50:37.302Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583436,0,'IsPlaceBPAccountsOnCredit',TO_TIMESTAMP('2025-01-21 13:50:36.839','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Place BP Accounts On Credit','Place BP Accounts On Credit',TO_TIMESTAMP('2025-01-21 13:50:36.839','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-01-21T11:50:37.329Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583436 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsNegateInboundAmounts
-- 2025-01-21T11:51:04.055Z
UPDATE AD_Element_Trl SET Description='When ticked, the tax and amount of ''APC'' and ''ARI'' invoices are negated.', Help='When ticked, the tax and amount of ''APC'' and ''ARI'' invoices are negated.',Updated=TO_TIMESTAMP('2025-01-21 13:51:04.055','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583434 AND AD_Language='en_US'
;

-- 2025-01-21T11:51:04.113Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583434,'en_US') 
;

-- Element: IsPlaceBPAccountsOnCredit
-- 2025-01-21T11:52:01.558Z
UPDATE AD_Element_Trl SET Description='When ticked, the partner accounts are moved from debit to credit side.',Updated=TO_TIMESTAMP('2025-01-21 13:52:01.558','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583436 AND AD_Language='en_US'
;

-- 2025-01-21T11:52:01.614Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583436,'en_US') 
;

-- Column: DATEV_Export.IsPlaceBPAccountsOnCredit
-- Column: DATEV_Export.IsPlaceBPAccountsOnCredit
-- 2025-01-21T11:52:41.100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589601,583436,0,20,540934,'IsPlaceBPAccountsOnCredit',TO_TIMESTAMP('2025-01-21 13:52:40.697','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.datev',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Place BP Accounts On Credit',0,0,TO_TIMESTAMP('2025-01-21 13:52:40.697','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-01-21T11:52:41.128Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589601 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-21T11:52:41.184Z
/* DDL */  select update_Column_Translation_From_AD_Element(583436) 
;

-- 2025-01-21T11:52:45.325Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN IsPlaceBPAccountsOnCredit CHAR(1) DEFAULT ''N'' CHECK (IsPlaceBPAccountsOnCredit IN (''Y'',''N'')) NOT NULL')
;



-- Field: Buchungen Export_OLD -> Buchungen Export -> Place BP Accounts On Credit
-- Column: DATEV_Export.IsPlaceBPAccountsOnCredit
-- Field: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Place BP Accounts On Credit
-- Column: DATEV_Export.IsPlaceBPAccountsOnCredit
-- 2025-01-21T12:02:32.056Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589601,734669,0,541036,TO_TIMESTAMP('2025-01-21 14:02:31.541','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.datev','Y','N','N','N','N','N','N','N','Place BP Accounts On Credit',TO_TIMESTAMP('2025-01-21 14:02:31.541','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-01-21T12:02:32.083Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734669 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-21T12:02:32.111Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583436) 
;

-- 2025-01-21T12:02:32.143Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734669
;

-- 2025-01-21T12:02:32.171Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734669)
;

-- UI Element: Buchungen Export_OLD -> Buchungen Export.Place BP Accounts On Credit
-- Column: DATEV_Export.IsPlaceBPAccountsOnCredit
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Place BP Accounts On Credit
-- Column: DATEV_Export.IsPlaceBPAccountsOnCredit
-- 2025-01-21T12:03:00.934Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734669,0,541036,541481,627810,'F',TO_TIMESTAMP('2025-01-21 14:03:00.326','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Place BP Accounts On Credit',50,0,0,TO_TIMESTAMP('2025-01-21 14:03:00.326','YYYY-MM-DD HH24:MI:SS.US'),100)
;




