/*
 * #%L
 * de.metas.salescandidate.base
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

-- Run mode: SWING_CLIENT

-- Column: C_OLCand.C_Incoterms_ID
-- 2025-12-01T10:31:21.159Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591611,579927,0,19,540244,'XX','C_Incoterms_ID',TO_TIMESTAMP('2025-12-01 10:31:21.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.ordercandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterms',0,0,TO_TIMESTAMP('2025-12-01 10:31:21.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-01T10:31:21.171Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591611 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-01T10:31:21.216Z
/* DDL */  select update_Column_Translation_From_AD_Element(579927)
;

-- 2025-12-01T10:31:23.505Z
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN C_Incoterms_ID NUMERIC(10)')
;

-- 2025-12-01T10:31:23.794Z
ALTER TABLE C_OLCand ADD CONSTRAINT CIncoterms_COLCand FOREIGN KEY (C_Incoterms_ID) REFERENCES public.C_Incoterms DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_OLCand.IncotermLocation
-- 2025-12-01T10:31:50.435Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591612,501608,0,10,540244,'XX','IncotermLocation',TO_TIMESTAMP('2025-12-01 10:31:50.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Anzugebender Ort für Handelsklausel','de.metas.ordercandidate',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@C_Incoterms_ID/-1@>0',0,'Incoterm Ort',0,0,TO_TIMESTAMP('2025-12-01 10:31:50.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-01T10:31:50.444Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591612 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-01T10:31:50.534Z
/* DDL */  select update_Column_Translation_From_AD_Element(501608)
;

-- 2025-12-01T10:33:01.741Z
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN IncotermLocation VARCHAR(255)')
;


-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Incoterms
-- Column: C_OLCand.C_Incoterms_ID
-- 2025-12-01T15:28:07.702Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591611,758480,0,548442,TO_TIMESTAMP('2025-12-01 15:28:07.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Incoterms',TO_TIMESTAMP('2025-12-01 15:28:07.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-01T15:28:07.711Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758480 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-01T15:28:07.736Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927)
;

-- 2025-12-01T15:28:07.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758480
;

-- 2025-12-01T15:28:07.794Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758480)
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Incoterm Ort
-- Column: C_OLCand.IncotermLocation
-- 2025-12-01T15:28:27.384Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591612,758481,0,548442,TO_TIMESTAMP('2025-12-01 15:28:27.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel',255,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Incoterm Ort',TO_TIMESTAMP('2025-12-01 15:28:27.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-01T15:28:27.387Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758481 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-01T15:28:27.390Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608)
;

-- 2025-12-01T15:28:27.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758481
;

-- 2025-12-01T15:28:27.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758481)
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Incoterm Ort
-- Column: C_OLCand.IncotermLocation
-- 2025-12-01T15:29:38.385Z
UPDATE AD_Field SET DisplayLogic='@C_Incoterms_ID/-1@>0',Updated=TO_TIMESTAMP('2025-12-01 15:29:38.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=758481
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Incoterms
-- Column: C_OLCand.C_Incoterms_ID
-- 2025-12-01T15:30:54.497Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758480,0,548442,553564,639725,'F',TO_TIMESTAMP('2025-12-01 15:30:54.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Incoterms',720,0,0,TO_TIMESTAMP('2025-12-01 15:30:54.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Incoterm Ort
-- Column: C_OLCand.IncotermLocation
-- 2025-12-01T15:31:08.626Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758481,0,548442,553564,639726,'F',TO_TIMESTAMP('2025-12-01 15:31:08.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel','Y','Y','N','Y','N','N','N',0,'Incoterm Ort',730,0,0,TO_TIMESTAMP('2025-12-01 15:31:08.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Incoterms
-- Column: C_OLCand.C_Incoterms_ID
-- 2025-12-01T15:31:33.650Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591611,758482,0,540282,TO_TIMESTAMP('2025-12-01 15:31:33.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Incoterms',TO_TIMESTAMP('2025-12-01 15:31:33.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-01T15:31:33.653Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758482 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-01T15:31:33.655Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927)
;

-- 2025-12-01T15:31:33.659Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758482
;

-- 2025-12-01T15:31:33.660Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758482)
;

-- Field: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Incoterm Ort
-- Column: C_OLCand.IncotermLocation
-- 2025-12-01T15:31:51.742Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591612,758483,0,540282,TO_TIMESTAMP('2025-12-01 15:31:51.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel',255,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Incoterm Ort',TO_TIMESTAMP('2025-12-01 15:31:51.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-01T15:31:51.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758483 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-01T15:31:51.748Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608)
;

/*
 * #%L
 * de.metas.salescandidate.base
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

-- 2025-12-01T15:31:51.751Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758483
;

-- 2025-12-01T15:31:51.751Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758483)
;

-- Field: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Incoterm Ort
-- Column: C_OLCand.IncotermLocation
-- 2025-12-01T15:32:16.098Z
UPDATE AD_Field SET DisplayLogic='@C_Incoterms_ID/-1@>0',Updated=TO_TIMESTAMP('2025-12-01 15:32:16.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=758483
;

-- UI Element: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Incoterms
-- Column: C_OLCand.C_Incoterms_ID
-- 2025-12-01T15:32:37.256Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758482,0,540282,540962,639727,'F',TO_TIMESTAMP('2025-12-01 15:32:36.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Incoterms',710,0,0,TO_TIMESTAMP('2025-12-01 15:32:36.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Incoterm Ort
-- Column: C_OLCand.IncotermLocation
-- 2025-12-01T15:32:50.791Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758483,0,540282,540962,639728,'F',TO_TIMESTAMP('2025-12-01 15:32:50.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel','Y','Y','N','Y','N','N','N',0,'Incoterm Ort',720,0,0,TO_TIMESTAMP('2025-12-01 15:32:50.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
