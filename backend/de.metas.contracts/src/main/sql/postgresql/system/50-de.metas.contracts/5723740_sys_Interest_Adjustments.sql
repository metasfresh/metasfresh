/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- Run mode: SWING_CLIENT

-- 2024-05-16T04:26:52.708Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest_Run','ALTER TABLE ModCntr_Interest_Run DROP COLUMN IF EXISTS DateStart')
;

-- Column: ModCntr_Interest_Run.DateStart
-- 2024-05-16T04:26:52.858Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588217
;

-- 2024-05-16T04:26:52.868Z
DELETE FROM AD_Column WHERE AD_Column_ID=588217
;

-- Column: ModCntr_Interest_Run.C_Currency_ID
-- 2024-05-16T04:27:27.182Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588266,193,0,19,542409,'C_Currency_ID',TO_TIMESTAMP('2024-05-16 07:27:27.033','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Die Währung für diesen Eintrag','de.metas.contracts',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2024-05-16 07:27:27.033','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-16T04:27:27.188Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588266 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-16T04:27:27.192Z
/* DDL */  select update_Column_Translation_From_AD_Element(193)
;

-- 2024-05-16T04:27:29.658Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest_Run','ALTER TABLE public.ModCntr_Interest_Run ADD COLUMN C_Currency_ID NUMERIC(10) NOT NULL')
;

-- 2024-05-16T04:27:29.676Z
ALTER TABLE ModCntr_Interest_Run ADD CONSTRAINT CCurrency_ModCntrInterestRun FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- 2024-05-16T04:29:40.282Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE ModCntr_Interest DROP COLUMN IF EXISTS InterimAmt')
;

-- Column: ModCntr_Interest.InterimAmt
-- 2024-05-16T04:29:40.305Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588231
;

-- 2024-05-16T04:29:40.323Z
DELETE FROM AD_Column WHERE AD_Column_ID=588231
;

-- 2024-05-16T04:30:58.925Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583109,0,'ShippingNotification_ModCntr_Log_ID',TO_TIMESTAMP('2024-05-16 07:30:58.787','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Shipping Notification Log','Shipping Notification Log',TO_TIMESTAMP('2024-05-16 07:30:58.787','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-16T04:30:58.929Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583109 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-05-16T04:31:26.027Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583110,0,'InterimInvoice_ModCntr_Log_ID',TO_TIMESTAMP('2024-05-16 07:31:25.887','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Interim Invoice Log','Interim Invoice Log',TO_TIMESTAMP('2024-05-16 07:31:25.887','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-16T04:31:26.030Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583110 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ModCntr_Interest.InterimInvoice_ModCntr_Log_ID
-- 2024-05-16T04:32:04.506Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588267,583110,0,30,541775,542410,'InterimInvoice_ModCntr_Log_ID',TO_TIMESTAMP('2024-05-16 07:32:04.37','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Interim Invoice Log',0,0,TO_TIMESTAMP('2024-05-16 07:32:04.37','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-16T04:32:04.508Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588267 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-16T04:32:04.511Z
/* DDL */  select update_Column_Translation_From_AD_Element(583110)
;

-- 2024-05-16T04:32:09.261Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE public.ModCntr_Interest ADD COLUMN InterimInvoice_ModCntr_Log_ID NUMERIC(10)')
;

-- 2024-05-16T04:32:09.273Z
ALTER TABLE ModCntr_Interest ADD CONSTRAINT InterimInvoiceModCntrLog_ModCntrInterest FOREIGN KEY (InterimInvoice_ModCntr_Log_ID) REFERENCES public.ModCntr_Log DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Interest.ShippingNotification_ModCntr_Log_ID
-- 2024-05-16T04:32:39.646Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588268,583109,0,30,541775,542410,'ShippingNotification_ModCntr_Log_ID',TO_TIMESTAMP('2024-05-16 07:32:39.48','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Shipping Notification Log',0,0,TO_TIMESTAMP('2024-05-16 07:32:39.48','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-16T04:32:39.648Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588268 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-16T04:32:39.651Z
/* DDL */  select update_Column_Translation_From_AD_Element(583109)
;

-- 2024-05-16T04:32:42.648Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE public.ModCntr_Interest ADD COLUMN ShippingNotification_ModCntr_Log_ID NUMERIC(10) NOT NULL')
;

-- 2024-05-16T04:32:42.657Z
ALTER TABLE ModCntr_Interest ADD CONSTRAINT ShippingNotificationModCntrLog_ModCntrInterest FOREIGN KEY (ShippingNotification_ModCntr_Log_ID) REFERENCES public.ModCntr_Log DEFERRABLE INITIALLY DEFERRED
;

-- 2024-05-16T04:33:15.462Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE ModCntr_Interest DROP COLUMN IF EXISTS ModCntr_Log_ID')
;

-- Column: ModCntr_Interest.ModCntr_Log_ID
-- 2024-05-16T04:33:15.477Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588237
;

-- 2024-05-16T04:33:15.483Z
DELETE FROM AD_Column WHERE AD_Column_ID=588237
;

-- 2024-05-16T04:33:30.518Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE ModCntr_Interest DROP COLUMN IF EXISTS C_Invoice_ID')
;

-- Column: ModCntr_Interest.C_Invoice_ID
-- 2024-05-16T04:33:30.534Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588236
;

-- 2024-05-16T04:33:30.542Z
DELETE FROM AD_Column WHERE AD_Column_ID=588236
;

-- 2024-05-16T04:33:59.719Z
INSERT INTO t_alter_column values('modcntr_interest_run','C_Currency_ID','NUMERIC(10)',null,null)
;

-- Column: ModCntr_Interest.InterestDays
-- 2024-05-16T04:39:53.905Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2024-05-16 07:39:53.905','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588232
;

-- 2024-05-16T04:39:56.281Z
INSERT INTO t_alter_column values('modcntr_interest','InterestDays','NUMERIC(10)',null,'0')
;

-- 2024-05-16T04:39:56.316Z
UPDATE ModCntr_Interest SET InterestDays=0 WHERE InterestDays IS NULL
;

-- 2024-05-16T04:39:56.317Z
INSERT INTO t_alter_column values('modcntr_interest','InterestDays',null,'NOT NULL',null)
;

-- Run mode: SWING_CLIENT

-- 2024-05-16T05:01:58.252Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540078,TO_TIMESTAMP('2024-05-16 08:01:58.112','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',1000,'InterestComputationQueue',1,TO_TIMESTAMP('2024-05-16 08:01:58.112','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-16T05:02:55.437Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540108,'de.metas.contracts.modular.interest.InterestComputationWorkPackageProcessor',TO_TIMESTAMP('2024-05-16 08:02:55.26','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','InterestComputationWorkPackageProcessor','Y',TO_TIMESTAMP('2024-05-16 08:02:55.26','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-16T05:03:33.024Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540108,540121,540078,TO_TIMESTAMP('2024-05-16 08:03:32.883','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',TO_TIMESTAMP('2024-05-16 08:03:32.883','YYYY-MM-DD HH24:MI:SS.US'),100)
;

