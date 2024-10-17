-- Column: EDI_DesadvLine.QtyOrdered_Override
-- 2024-09-11T09:06:45.366Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588948,542680,0,29,540645,'QtyOrdered_Override',TO_TIMESTAMP('2024-09-11 12:06:45.179','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.esb.edi',10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bestellt abw.',0,0,TO_TIMESTAMP('2024-09-11 12:06:45.179','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-11T09:06:45.373Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588948 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-11T09:06:45.418Z
/* DDL */  select update_Column_Translation_From_AD_Element(542680)
;

-- 2024-09-11T09:07:30.303Z
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN QtyOrdered_Override NUMERIC')
;

-- Field: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> DESADV Zeile(540663,de.metas.esb.edi) -> Bestellt abw.
-- Column: EDI_DesadvLine.QtyOrdered_Override
-- 2024-09-11T09:08:29.361Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588948,729858,0,540663,0,TO_TIMESTAMP('2024-09-11 12:08:29.237','YYYY-MM-DD HH24:MI:SS.US'),100,10,'U',0,'Y','N','N','N','N','N','Y','N','Bestellt abw.',1,1,TO_TIMESTAMP('2024-09-11 12:08:29.237','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-11T09:08:29.363Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T09:08:29.366Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542680)
;

-- 2024-09-11T09:08:29.380Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729858
;

-- 2024-09-11T09:08:29.383Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729858)
;

-- Field: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> DESADV Zeile(540663,de.metas.esb.edi) -> Bestellt abw.
-- Column: EDI_DesadvLine.QtyOrdered_Override
-- 2024-09-11T09:08:36.500Z
UPDATE AD_Field SET EntityType='de.metas.esb.edi',Updated=TO_TIMESTAMP('2024-09-11 12:08:36.5','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=729858
;


/*
 * #%L
 * de.metas.edi
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


--columnname = 'IsDeliveryClosed'
UPDATE ad_column SET columnsql = '(case when qtydeliveredinstockinguom >= coalesce(QtyOrdered_Override,qtyordered) then ''Y'' else ''N'' end)' WHERE ad_column_id = 588622;
