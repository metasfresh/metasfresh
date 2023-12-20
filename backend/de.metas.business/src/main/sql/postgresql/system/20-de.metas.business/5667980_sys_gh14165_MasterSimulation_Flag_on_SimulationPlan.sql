-- Field: Simulationsplan -> Simulationsplan -> Hauptsimulation
-- Column: C_SimulationPlan.IsMainSimulation
-- 2022-12-09T13:12:11.682Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585277,708974,0,546390,TO_TIMESTAMP('2022-12-09 15:12:11','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Hauptsimulation',TO_TIMESTAMP('2022-12-09 15:12:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-09T13:12:11.686Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-09T13:12:11.747Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581843) 
;

-- 2022-12-09T13:12:11.768Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708974
;

-- 2022-12-09T13:12:11.774Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708974)
;

-- 2022-12-09T13:13:15.185Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546091,550126,TO_TIMESTAMP('2022-12-09 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',20,TO_TIMESTAMP('2022-12-09 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-09T13:13:22.806Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2022-12-09 15:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549389
;

-- 2022-12-09T13:13:28.742Z
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2022-12-09 15:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550126
;

-- UI Element: Simulationsplan -> Simulationsplan.Hauptsimulation
-- Column: C_SimulationPlan.IsMainSimulation
-- 2022-12-09T13:13:49.078Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708974,0,546390,613972,550126,'F',TO_TIMESTAMP('2022-12-09 15:13:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Hauptsimulation',10,0,0,TO_TIMESTAMP('2022-12-09 15:13:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulationsplan -> Simulationsplan.Hauptsimulation
-- Column: C_SimulationPlan.IsMainSimulation
-- 2022-12-09T13:14:01.238Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-12-09 15:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613972
;

/*
 * #%L
 * de.metas.business
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

-- Tab: Simulationsplan -> Simulationsplan
-- Table: C_SimulationPlan
-- 2022-12-09T13:15:52.462Z
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-12-09 15:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546390
;

-- Field: Simulationsplan -> Simulationsplan -> Name
-- Column: C_SimulationPlan.Name
-- 2022-12-09T13:16:38.453Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-09 15:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700762
;

-- Field: Simulationsplan -> Simulationsplan -> Verantwortlicher Benutzer
-- Column: C_SimulationPlan.AD_User_Responsible_ID
-- 2022-12-09T13:16:53.654Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-09 15:16:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700788
;

-- UI Element: Simulationsplan -> Simulationsplan.Hauptsimulation
-- Column: C_SimulationPlan.IsMainSimulation
-- 2022-12-09T13:18:18.415Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-12-09 15:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613972
;

