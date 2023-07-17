/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

-- Name: Externalsystem_Config Type PrintingClient
-- 2023-07-17T19:27:13.475Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540646,'AD_PrinterHW.ExternalSystem_Config_ID IN (SELECT externalsystem_config_id FROM externalsystem_config WHERE value = ''PC'' AND isactive = ''Y'')',TO_TIMESTAMP('2023-07-17 21:27:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Externalsystem_Config Type PrintingClient','S',TO_TIMESTAMP('2023-07-17 21:27:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: AD_PrinterHW.ExternalSystem_Config_ID
-- Column: AD_PrinterHW.ExternalSystem_Config_ID
-- 2023-07-17T19:29:22.129Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587125,578728,0,19,540438,540646,'ExternalSystem_Config_ID',TO_TIMESTAMP('2023-07-17 21:29:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.printing',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'External System Config',0,0,TO_TIMESTAMP('2023-07-17 21:29:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-17T19:29:22.133Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587125 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-17T19:29:22.168Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728)
;

-- 2023-07-17T19:44:26.193Z
/* DDL */ SELECT public.db_alter_table('AD_PrinterHW','ALTER TABLE public.AD_PrinterHW ADD COLUMN ExternalSystem_Config_ID NUMERIC(10)')
;

-- 2023-07-17T19:44:26.380Z
ALTER TABLE AD_PrinterHW ADD CONSTRAINT ExternalSystemConfig_ADPrinterHW FOREIGN KEY (ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

