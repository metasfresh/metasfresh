-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Zinstage
-- Column: ModCntr_Log.InterestDays
-- 2025-05-13T19:30:17.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589983,743164,0,547012,TO_TIMESTAMP('2025-05-13 21:30:17.243','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Zinstage',TO_TIMESTAMP('2025-05-13 21:30:17.243','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-13T19:30:17.367Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=743164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-13T19:30:17.369Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583101)
;

-- 2025-05-13T19:30:17.372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=743164
;

-- 2025-05-13T19:30:17.374Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(743164)
;

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Zinssatz
-- Column: ModCntr_Log.InterestRate
-- 2025-05-13T19:30:17.466Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589984,743165,0,547012,TO_TIMESTAMP('2025-05-13 21:30:17.378','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Zinssatz',TO_TIMESTAMP('2025-05-13 21:30:17.378','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-13T19:30:17.467Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=743165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-13T19:30:17.468Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583090)
;

-- 2025-05-13T19:30:17.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=743165
;

-- 2025-05-13T19:30:17.471Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(743165)
;

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

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Basisbausteine
-- Column: ModCntr_Log.ModCntr_BaseModule_ID
-- 2025-05-13T19:30:17.556Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589985,743166,0,547012,TO_TIMESTAMP('2025-05-13 21:30:17.473','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Basisbausteine',TO_TIMESTAMP('2025-05-13 21:30:17.473','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-13T19:30:17.559Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=743166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-13T19:30:17.560Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583622)
;

-- 2025-05-13T19:30:17.561Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=743166
;

-- 2025-05-13T19:30:17.562Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(743166)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Basisbausteine
-- Column: ModCntr_Log.ModCntr_BaseModule_ID
-- 2025-05-13T19:31:54.794Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,743166,0,547012,550777,631893,'F',TO_TIMESTAMP('2025-05-13 21:31:54.668','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Basisbausteine',17,0,0,TO_TIMESTAMP('2025-05-13 21:31:54.668','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20
-- UI Element Group: interest
-- 2025-05-13T19:32:56.151Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546864,552828,TO_TIMESTAMP('2025-05-13 21:32:55.902','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','interest',25,TO_TIMESTAMP('2025-05-13 21:32:55.902','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> interest.Zinstage
-- Column: ModCntr_Log.InterestDays
-- 2025-05-13T19:33:19.456Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,743164,0,547012,552828,631894,'F',TO_TIMESTAMP('2025-05-13 21:33:19.344','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Zinstage',10,0,0,TO_TIMESTAMP('2025-05-13 21:33:19.344','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> interest.Zinssatz
-- Column: ModCntr_Log.InterestRate
-- 2025-05-13T19:33:34.026Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,743165,0,547012,552828,631895,'F',TO_TIMESTAMP('2025-05-13 21:33:33.918','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zinssatz',20,0,0,TO_TIMESTAMP('2025-05-13 21:33:33.918','YYYY-MM-DD HH24:MI:SS.US'),100)
;

