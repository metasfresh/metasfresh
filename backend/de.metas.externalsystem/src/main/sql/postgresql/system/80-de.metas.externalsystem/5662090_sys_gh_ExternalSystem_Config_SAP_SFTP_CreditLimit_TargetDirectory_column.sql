-- 2022-10-26T09:16:45.131Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581623,0,'SFTP_CreditLimit_TargetDirectory',TO_TIMESTAMP('2022-10-26 12:16:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','SFTP Credit Limit TargetDirectory','SFTP Credit Limit TargetDirectory',TO_TIMESTAMP('2022-10-26 12:16:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-26T09:16:45.139Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581623 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-10-26T09:22:57.252Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584828,581623,0,10,542238,'SFTP_CreditLimit_TargetDirectory',TO_TIMESTAMP('2022-10-26 12:22:57','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SFTP Credit Limit TargetDirectory',0,0,TO_TIMESTAMP('2022-10-26 12:22:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-26T09:22:57.260Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584828 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-26T09:22:57.287Z
/* DDL */  select update_Column_Translation_From_AD_Element(581623) 
;

-- 2022-10-26T09:22:58.113Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN SFTP_CreditLimit_TargetDirectory VARCHAR(255)')
;

-- Reference: External_Request SAP
-- Value: startCreditLimitsSync
-- ValueName: Start Credit Limits Synchronization
-- 2022-10-26T09:55:11.259Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543329,541661,TO_TIMESTAMP('2022-10-26 12:55:11','YYYY-MM-DD HH24:MI:SS'),100,'Starts the credit limits sychronization with SAP external system','de.metas.externalsystem','Y','Start Credit Limits Synchronization',TO_TIMESTAMP('2022-10-26 12:55:11','YYYY-MM-DD HH24:MI:SS'),100,'startCreditLimitsSync','Start Credit Limits Synchronization')
;

-- 2022-10-26T09:55:11.264Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543329 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitsSync
-- ValueName: Stop Credit Limits Synchronization
-- 2022-10-26T09:56:06.936Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543330,541661,TO_TIMESTAMP('2022-10-26 12:56:06','YYYY-MM-DD HH24:MI:SS'),100,'Stops the credit limits synchronization with SAP external system','de.metas.externalsystem','Y','Stop Credit Limits Synchronization',TO_TIMESTAMP('2022-10-26 12:56:06','YYYY-MM-DD HH24:MI:SS'),100,'stopCreditLimitsSync','Stop Credit Limits Synchronization')
;

-- 2022-10-26T09:56:06.938Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543330 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP Credit Limit TargetDirectory
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-10-26T10:05:51.268Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584828,707881,0,546647,0,TO_TIMESTAMP('2022-10-26 13:05:51','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','SFTP Credit Limit TargetDirectory',0,110,0,1,1,TO_TIMESTAMP('2022-10-26 13:05:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-26T10:05:51.281Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-26T10:05:51.287Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581623) 
;

-- 2022-10-26T10:05:51.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707881
;

-- 2022-10-26T10:05:51.310Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707881)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP Credit Limit TargetDirectory
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-10-26T10:06:45.170Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707881,0,546647,613321,549954,'F',TO_TIMESTAMP('2022-10-26 13:06:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SFTP Credit Limit TargetDirectory',100,0,0,TO_TIMESTAMP('2022-10-26 13:06:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-07T10:45:09.687966200Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2022-10-07 13:45:09','YYYY-MM-DD HH24:MI:SS'),100,540003,'Y','SFTP Sync-CreditLimits','SAP',TO_TIMESTAMP('2022-10-07 13:45:09','YYYY-MM-DD HH24:MI:SS'),100,'SFTPSyncCreditLimits')
;

-- 2022-10-07T10:45:12.897805900Z
UPDATE ExternalSystem_Service SET Description='SFTP Sync-CreditLimits',Updated=TO_TIMESTAMP('2022-10-07 13:45:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540003
;

-- 2022-10-07T10:46:07.923288900Z
UPDATE ExternalSystem_Service SET EnableCommand='startCreditLimitsSync',Updated=TO_TIMESTAMP('2022-10-07 13:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540003
;

-- 2022-10-07T10:46:17.838934100Z
UPDATE ExternalSystem_Service SET DisableCommand='stopCreditLimitsSync',Updated=TO_TIMESTAMP('2022-10-07 13:46:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540003
;