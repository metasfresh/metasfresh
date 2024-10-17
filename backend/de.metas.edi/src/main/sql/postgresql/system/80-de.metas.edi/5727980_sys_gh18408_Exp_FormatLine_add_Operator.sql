-- Name: FilterOperator_for_EXP_FormatLine
-- 2024-07-05T13:01:44.082Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541875,TO_TIMESTAMP('2024-07-05 13:01:43.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.replication','Y','N','FilterOperator_for_EXP_FormatLine',TO_TIMESTAMP('2024-07-05 13:01:43.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2024-07-05T13:01:44.087Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541875 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: FilterOperator_for_EXP_FormatLine
-- Value: E
-- ValueName: Equals
-- 2024-07-05T13:02:54.635Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541875,543701,TO_TIMESTAMP('2024-07-05 13:02:54.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Means either ''='' or ''IS NULL`','de.metas.replication','Y','Equals',TO_TIMESTAMP('2024-07-05 13:02:54.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'E','Equals')
;

-- 2024-07-05T13:02:54.639Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543701 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: FilterOperator_for_EXP_FormatLine
-- Value: L
-- ValueName: Like
-- 2024-07-05T13:04:01.560Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541875,543702,TO_TIMESTAMP('2024-07-05 13:04:01.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Means "ILIKE ''%...%''"','de.metas.replication','Y','Like',TO_TIMESTAMP('2024-07-05 13:04:01.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L','Like')
;

-- 2024-07-05T13:04:01.562Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543702 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: FilterOperator_for_EXP_FormatLine
-- Value: E
-- ValueName: Equals
-- 2024-07-05T13:04:28.755Z
UPDATE AD_Ref_List SET Description='Means either "= ..." or "IS NULL"',Updated=TO_TIMESTAMP('2024-07-05 13:04:28.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543701
;

-- Column: EXP_FormatLine.FilterOperator
-- Column: EXP_FormatLine.FilterOperator
-- 2024-07-05T13:05:01.328Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588691,578627,0,17,541875,53073,'FilterOperator',TO_TIMESTAMP('2024-07-05 13:05:00.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','E','EE05',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Filter Operator',0,0,TO_TIMESTAMP('2024-07-05 13:05:00.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-07-05T13:05:01.334Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588691 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:05:01.375Z
/* DDL */  select update_Column_Translation_From_AD_Element(578627) 
;

-- 2024-07-05T13:05:03.718Z
/* DDL */ SELECT public.db_alter_table('EXP_FormatLine','ALTER TABLE public.EXP_FormatLine ADD COLUMN FilterOperator CHAR(1) DEFAULT ''E''')
;

-- Column: EXP_FormatLine.FilterOperator
-- Column: EXP_FormatLine.FilterOperator
-- 2024-07-05T13:05:13.250Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-07-05 13:05:13.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588691
;

-- Field: Export Format -> Export Format Line -> Filter Operator
-- Column: EXP_FormatLine.FilterOperator
-- Field: Export Format(53025,EE05) -> Export Format Line(53086,EE05) -> Filter Operator
-- Column: EXP_FormatLine.FilterOperator
-- 2024-07-05T13:06:50.265Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588691,729035,0,53086,0,TO_TIMESTAMP('2024-07-05 13:06:50.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.replication',0,0,'Y','Y','Y','N','N','N','N','N','Y',0,'Filter Operator',0,0,185,0,1,1,TO_TIMESTAMP('2024-07-05 13:06:50.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-07-05T13:06:50.270Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:06:50.276Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578627) 
;

-- 2024-07-05T13:06:50.290Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729035
;

-- 2024-07-05T13:06:50.299Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729035)
;

-- Field: Export Format -> Export Format Line -> Filter Operator
-- Column: EXP_FormatLine.FilterOperator
-- Field: Export Format(53025,EE05) -> Export Format Line(53086,EE05) -> Filter Operator
-- Column: EXP_FormatLine.FilterOperator
-- 2024-07-05T13:07:47.541Z
UPDATE AD_Field SET SeqNo=175,Updated=TO_TIMESTAMP('2024-07-05 13:07:47.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729035
;

-- Field: Export Format -> Export Format Line -> Filter Operator
-- Column: EXP_FormatLine.FilterOperator
-- Field: Export Format(53025,EE05) -> Export Format Line(53086,EE05) -> Filter Operator
-- Column: EXP_FormatLine.FilterOperator
-- 2024-07-05T13:10:37.211Z
UPDATE AD_Field SET DisplayLogic='@Type@=''E'' | @Type@=''R''',Updated=TO_TIMESTAMP('2024-07-05 13:10:37.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729035
;

-- 2024-07-05T13:26:32.638Z
UPDATE EXP_FormatLine SET FilterOperator='L', Help='FilterOperator="Like", so the import-XML''s value can be a null, empty or a substring of C_BPartner.Lookup_Label.',Updated=TO_TIMESTAMP('2024-07-05 13:26:32.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550768
;

COMMIT;

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

-- 2024-07-05T13:05:13.851Z
INSERT INTO t_alter_column values('exp_formatline','FilterOperator','CHAR(1)',null,'E')
;

-- 2024-07-05T13:05:13.864Z
UPDATE EXP_FormatLine SET FilterOperator='E' WHERE FilterOperator IS NULL
;

-- 2024-07-05T13:05:13.869Z
INSERT INTO t_alter_column values('exp_formatline','FilterOperator',null,'NOT NULL',null)
;
