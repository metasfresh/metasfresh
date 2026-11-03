/*
 * #%L
 * de.metas.serviceprovider.base
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

-- Column: S_ExternalReference.ExternalSystem_ID
-- 2025-09-17T12:01:57.810Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590922,583968,0,19,541486,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-09-17 12:01:57.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalreference',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'External System',0,0,TO_TIMESTAMP('2025-09-17 12:01:57.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-17T12:01:57.826Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590922 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-17T12:01:57.859Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-09-17T12:02:07.992Z
/* DDL */ SELECT public.db_alter_table('S_ExternalReference','ALTER TABLE public.S_ExternalReference ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-09-17T12:02:08.010Z
ALTER TABLE S_ExternalReference ADD CONSTRAINT ExternalSystem_SExternalReference FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Field: Externe Referenz(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> Externes System
-- Column: S_ExternalReference.ExternalSystem_ID
-- 2025-09-17T17:16:41.931Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590922,753779,0,542376,TO_TIMESTAMP('2025-09-17 17:16:41.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-09-17 17:16:41.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-17T17:16:41.932Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-17T17:16:41.933Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-09-17T17:16:41.941Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753779
;

-- 2025-09-17T17:16:41.942Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753779)
;

-- UI Element: Externe Referenz(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> main -> 10 -> default.Externes System
-- Column: S_ExternalReference.ExternalSystem_ID
-- 2025-09-17T17:18:03.653Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753779,0,542376,543614,637129,'F',TO_TIMESTAMP('2025-09-17 17:18:03.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',11,0,0,TO_TIMESTAMP('2025-09-17 17:18:03.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe Referenz(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> main -> 10 -> default.Externes System
-- Column: S_ExternalReference.ExternalSystem_ID
-- 2025-09-17T17:18:31.801Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-17 17:18:31.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637129
;

-- UI Element: Externe Referenz(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> main -> 10 -> default.External system
-- Column: S_ExternalReference.ExternalSystem
-- 2025-09-17T17:18:31.813Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-17 17:18:31.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=567184
;

-- UI Element: Externe Referenz(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> main -> 10 -> default.Art
-- Column: S_ExternalReference.Type
-- 2025-09-17T17:18:31.818Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-17 17:18:31.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=567185
;

-- UI Element: Externe Referenz(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> main -> 10 -> default.External reference
-- Column: S_ExternalReference.ExternalReference
-- 2025-09-17T17:18:31.825Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-17 17:18:31.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=567186
;

-- UI Element: Externe Referenz(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> main -> 10 -> default.Version
-- Column: S_ExternalReference.Version
-- 2025-09-17T17:18:31.830Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-17 17:18:31.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=586795
;

-- UI Element: Externe Referenz(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> main -> 10 -> default.Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2025-09-17T17:18:31.836Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-17 17:18:31.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=567191
;

-- UI Element: Externe Referenz(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> main -> 10 -> default.External System Config ID
-- Column: S_ExternalReference.ExternalSystem_Config_ID
-- 2025-09-17T17:18:31.841Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-09-17 17:18:31.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609006
;

-- Name: ExternalSystems
-- 2025-09-17T17:30:18.486Z
UPDATE AD_Reference SET Name='ExternalSystems',Updated=TO_TIMESTAMP('2025-09-17 17:30:18.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541117
;

-- 2025-09-17T17:30:18.488Z
UPDATE AD_Reference_Trl trl SET Name='ExternalSystems' WHERE AD_Reference_ID=541117 AND AD_Language='de_DE'
;

-- Name: ExternalSystem
-- 2025-09-17T17:30:46.694Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541988,TO_TIMESTAMP('2025-09-17 17:30:46.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalreference','Y','N','ExternalSystem',TO_TIMESTAMP('2025-09-17 17:30:46.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-09-17T17:30:46.696Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541988 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ExternalSystem
-- Table: ExternalSystem
-- Key: ExternalSystem.ExternalSystem_ID
-- 2025-09-17T17:31:40.729Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,590913,0,541988,542525,TO_TIMESTAMP('2025-09-17 17:31:40.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','Y','N',TO_TIMESTAMP('2025-09-17 17:31:40.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: S_ExternalReference.ExternalSystem_ID
-- 2025-09-17T17:32:20.861Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541988, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-09-17 17:32:20.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590922
;
