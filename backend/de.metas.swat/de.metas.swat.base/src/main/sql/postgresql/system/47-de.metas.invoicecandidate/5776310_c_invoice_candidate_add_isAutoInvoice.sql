/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- 2025-11-10T09:58:25.068Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584200,0,'IsAutoInvoice',TO_TIMESTAMP('2025-11-10 09:58:24.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Automatische Abrechnung','Automatische Abrechnung',TO_TIMESTAMP('2025-11-10 09:58:24.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-10T09:58:25.072Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584200 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAutoInvoice
-- 2025-11-10T09:58:43.993Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auto Invoice', PrintName='Auto Invoice',Updated=TO_TIMESTAMP('2025-11-10 09:58:43.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584200 AND AD_Language='en_US'
;

-- 2025-11-10T09:58:43.994Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T09:58:44.209Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584200,'en_US')
;

-- Element: IsAutoInvoice
-- 2025-11-10T09:58:45.105Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-10 09:58:45.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584200 AND AD_Language='de_CH'
;

-- 2025-11-10T09:58:45.108Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584200,'de_CH')
;

-- Element: IsAutoInvoice
-- 2025-11-10T09:58:46.360Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-10 09:58:46.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584200 AND AD_Language='de_DE'
;

-- 2025-11-10T09:58:46.362Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584200,'de_DE')
;

-- 2025-11-10T09:58:46.363Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584200,'de_DE')
;

-- Column: C_Invoice_Candidate.IsAutoInvoice
-- 2025-11-10T10:17:54.998Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591495,584200,0,20,540270,'XX','IsAutoInvoice',TO_TIMESTAMP('2025-11-10 10:17:54.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.invoicecandidate',0,1,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Automatische Abrechnung',0,0,TO_TIMESTAMP('2025-11-10 10:17:54.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-10T10:17:55.011Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591495 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-10T10:17:55.016Z
/* DDL */  select update_Column_Translation_From_AD_Element(584200)
;

-- 2025-11-12T15:33:44.651Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN IsAutoInvoice CHAR(1) DEFAULT ''N'' CHECK (IsAutoInvoice IN (''Y'',''N'')) NOT NULL')
;


-- 2025-11-14T07:19:54.599Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540838,0,540270,TO_TIMESTAMP('2025-11-14 07:19:54.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoicecandidate','Y','N','C_InvoiceCandidate_processed_isAutoInvoice_dateToInvoice','N',TO_TIMESTAMP('2025-11-14 07:19:54.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'isActive = ''Y''')
;

-- 2025-11-14T07:19:54.601Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540838 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-11-14T07:20:34.106Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,545771,541483,540838,0,TO_TIMESTAMP('2025-11-14 07:20:33.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoicecandidate','Y',10,TO_TIMESTAMP('2025-11-14 07:20:33.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-14T07:20:52.049Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591495,541484,540838,0,TO_TIMESTAMP('2025-11-14 07:20:51.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoicecandidate','Y',20,TO_TIMESTAMP('2025-11-14 07:20:51.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-14T07:25:04.018Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,546340,541485,540838,0,TO_TIMESTAMP('2025-11-14 07:25:03.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoicecandidate','Y',30,TO_TIMESTAMP('2025-11-14 07:25:03.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-14T07:25:13.837Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,546339,541486,540838,0,TO_TIMESTAMP('2025-11-14 07:25:13.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y',40,TO_TIMESTAMP('2025-11-14 07:25:13.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-14T07:25:26.685Z
UPDATE AD_Index_Column SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2025-11-14 07:25:26.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Column_ID=541486
;

-- 2025-11-14T07:31:05.261Z
UPDATE AD_Index_Column SET SeqNo=30,Updated=TO_TIMESTAMP('2025-11-14 07:31:05.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Column_ID=541484
;

-- 2025-11-14T07:31:13.306Z
UPDATE AD_Index_Column SET SeqNo=40,Updated=TO_TIMESTAMP('2025-11-14 07:31:13.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Column_ID=541485
;

-- 2025-11-14T07:31:19.380Z
UPDATE AD_Index_Column SET SeqNo=50,Updated=TO_TIMESTAMP('2025-11-14 07:31:19.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Column_ID=541486
;

-- 2025-11-14T07:31:50.041Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,549847,541487,540838,0,TO_TIMESTAMP('2025-11-14 07:31:49.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoicecandidate','Y',20,TO_TIMESTAMP('2025-11-14 07:31:49.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-14T07:31:57.822Z
CREATE INDEX C_InvoiceCandidate_processed_isAutoInvoice_dateToInvoice ON C_Invoice_Candidate (Processed,IsSOTrx,IsAutoInvoice,DateToInvoice_Override,DateToInvoice) WHERE isActive = 'Y'
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Automatische Abrechnung
-- Column: C_Invoice_Candidate.IsAutoInvoice
-- 2025-11-14T07:39:08.850Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591495,756141,0,540279,TO_TIMESTAMP('2025-11-14 07:39:08.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Automatische Abrechnung',TO_TIMESTAMP('2025-11-14 07:39:08.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-14T07:39:08.857Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756141 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-14T07:39:08.862Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584200)
;

-- 2025-11-14T07:39:08.886Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756141
;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- 2025-11-14T07:39:08.894Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756141)
;

-- UI Element: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> override.Automatische Abrechnung
-- Column: C_Invoice_Candidate.IsAutoInvoice
-- 2025-11-14T07:40:54.155Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756141,0,540279,541087,638713,'F',TO_TIMESTAMP('2025-11-14 07:40:53.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Automatische Abrechnung',35,0,0,TO_TIMESTAMP('2025-11-14 07:40:53.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

