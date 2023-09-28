-- Run mode: SWING_CLIENT

-- Element: ModCntr_Log_DocumentType
-- 2023-09-27T16:01:53.844Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Belegart', PrintName='Belegart',Updated=TO_TIMESTAMP('2023-09-27 17:01:53.844','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582470 AND AD_Language='de_DE'
;

-- 2023-09-27T16:01:53.850Z
UPDATE AD_Element SET Name='Belegart', PrintName='Belegart' WHERE AD_Element_ID=582470
;

-- 2023-09-27T16:01:54.341Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582470,'de_DE')
;

-- 2023-09-27T16:01:54.344Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582470,'de_DE')
;

-- 2023-09-27T16:06:18.585Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Produktionsauftrag',Updated=TO_TIMESTAMP('2023-09-27 17:06:18.583','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=53027
;

-- 2023-09-27T16:06:18.593Z
UPDATE AD_Table SET Name='Produktionsauftrag' WHERE AD_Table_ID=53027
;

-- 2023-09-27T16:26:18.827Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Inventurposten',Updated=TO_TIMESTAMP('2023-09-27 17:26:18.825','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=322
;

-- 2023-09-27T16:26:18.830Z
UPDATE AD_Table SET Name='Inventurposten' WHERE AD_Table_ID=322
;

-- 2023-09-27T16:31:40.948Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Auftragsposition/Bestellposition',Updated=TO_TIMESTAMP('2023-09-27 17:31:40.946','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=260
;

-- 2023-09-27T16:31:40.949Z
UPDATE AD_Table SET Name='Auftragsposition/Bestellposition' WHERE AD_Table_ID=260
;

-- 2023-09-27T16:32:07.569Z
UPDATE AD_Table_Trl SET Name='Sales Order Line/Purchase Order Line',Updated=TO_TIMESTAMP('2023-09-27 17:32:07.567','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=260
;

-- Table: C_OrderLine
-- 2023-09-27T16:32:51.005Z
UPDATE AD_Table SET Description='Auftragsposition/Bestellposition',Updated=TO_TIMESTAMP('2023-09-27 17:32:51.003','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=260
;

-- Column: ModCntr_Log.C_Flatrate_Term_ID
-- 2023-09-27T16:48:31.651Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2023-09-27 17:48:31.651','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586778
;

-- Column: ModCntr_Log.C_Flatrate_Term_ID
-- 2023-09-27T17:11:05.355Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-09-27 18:11:05.355','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586778
;

-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2023-09-27T17:11:39.008Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-09-27 18:11:39.008','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586853
;

-- Column: ModCntr_Log.C_Invoice_Candidate_ID
-- 2023-09-27T17:11:59.089Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-09-27 18:11:59.089','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586773
;

-- Column: ModCntr_Log.AD_Table_ID
-- 2023-09-27T17:12:21.394Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-09-27 18:12:21.394','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586769
;

-- Column: ModCntr_Log.M_Warehouse_ID
-- 2023-09-27T17:12:40.171Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-09-27 18:12:40.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586770
;

-- Field: Inventur(168,D) -> Bestandszählungs Position(256,D) -> Modularer Vertrag
-- Column: M_InventoryLine.Modular_Flatrate_Term_ID
-- 2023-09-27T17:27:45.013Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587023,720681,0,256,0,TO_TIMESTAMP('2023-09-27 18:27:44.681','YYYY-MM-DD HH24:MI:SS.US'),100,'Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.',0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Modularer Vertrag',0,190,0,1,1,TO_TIMESTAMP('2023-09-27 18:27:44.681','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-27T17:27:45.017Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720681 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/*
 * #%L
 * de.metas.contracts
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

-- 2023-09-27T17:27:45.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582523)
;

-- 2023-09-27T17:27:45.029Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720681
;

-- 2023-09-27T17:27:45.031Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720681)
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Modularer Vertrag
-- Column: M_InventoryLine.Modular_Flatrate_Term_ID
-- 2023-09-27T17:28:16.939Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720681,0,256,541513,620598,'F',TO_TIMESTAMP('2023-09-27 18:28:16.696','YYYY-MM-DD HH24:MI:SS.US'),100,'Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.','Y','N','N','Y','N','N','N',0,'Modularer Vertrag',170,0,0,TO_TIMESTAMP('2023-09-27 18:28:16.696','YYYY-MM-DD HH24:MI:SS.US'),100)
;



-- Column: C_Flatrate_Term.C_Flatrate_Term_ID
-- 2023-09-27T17:39:58.331Z
UPDATE AD_Column SET IsIdentifier='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-09-27 18:39:58.331','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545802
;

-- Column: C_Flatrate_Term.DocumentNo
-- 2023-09-27T17:40:24.984Z
UPDATE AD_Column SET IsIdentifier='N',Updated=TO_TIMESTAMP('2023-09-27 18:40:24.984','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=557170
;

-- Column: C_Flatrate_Term.DocumentNo
-- 2023-09-27T17:40:31.202Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-09-27 18:40:31.202','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=557170
;

-- Reference: C_Flatrate_Term_Modular
-- Table: C_Flatrate_Term
-- Key: C_Flatrate_Term.C_Flatrate_Term_ID
-- 2023-09-27T17:43:02.287Z
UPDATE AD_Ref_Table SET AD_Display=557170,Updated=TO_TIMESTAMP('2023-09-27 18:43:02.287','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541780
;

-- Column: C_Flatrate_Conditions.ModCntr_Settings_ID
-- 2023-09-27T18:28:13.023Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-09-27 19:28:13.023','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586810
;

