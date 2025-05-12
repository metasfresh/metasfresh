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

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Frühablieferungsabzug Enddatum
-- Column: ModCntr_Settings.ReceiptAVEndDate
-- 2025-05-08T09:36:10.422Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589958,742024,0,547013,TO_TIMESTAMP('2025-05-08 11:36:10.262','YYYY-MM-DD HH24:MI:SS.US'),100,7,'de.metas.contracts','Y','N','N','N','N','N','N','N','Frühablieferungsabzug Enddatum',TO_TIMESTAMP('2025-05-08 11:36:10.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-08T09:36:10.439Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-08T09:36:10.461Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583615)
;

-- 2025-05-08T09:36:10.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742024
;

-- 2025-05-08T09:36:10.496Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742024)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Frühablieferungsabzug Enddatum
-- Column: ModCntr_Settings.ReceiptAVEndDate
-- 2025-05-08T09:37:43.171Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742024,0,547013,551809,631432,'F',TO_TIMESTAMP('2025-05-08 11:37:43.021','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Frühablieferungsabzug Enddatum',15,0,0,TO_TIMESTAMP('2025-05-08 11:37:43.021','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Frühablieferungsabzug Enddatum
-- Column: ModCntr_Settings.ReceiptAVEndDate
-- 2025-05-08T12:16:18.676Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''N''',Updated=TO_TIMESTAMP('2025-05-08 14:16:18.676','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=742024
;

