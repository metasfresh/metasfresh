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
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,743166,0,547012,550777,631893,'F',TO_TIMESTAMP('2025-05-13 21:31:54.668','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Basisbausteine',17,0,0,TO_TIMESTAMP('2025-05-13 21:31:54.668','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20
-- UI Element Group: interest
-- 2025-05-13T19:32:56.151Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546864,552828,TO_TIMESTAMP('2025-05-13 21:32:55.902','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','interest',25,TO_TIMESTAMP('2025-05-13 21:32:55.902','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> interest.Zinstage
-- Column: ModCntr_Log.InterestDays
-- 2025-05-13T19:33:19.456Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,743164,0,547012,552828,631894,'F',TO_TIMESTAMP('2025-05-13 21:33:19.344','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zinstage',10,0,0,TO_TIMESTAMP('2025-05-13 21:33:19.344','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> interest.Zinssatz
-- Column: ModCntr_Log.InterestRate
-- 2025-05-13T19:33:34.026Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,743165,0,547012,552828,631895,'F',TO_TIMESTAMP('2025-05-13 21:33:33.918','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zinssatz',20,0,0,TO_TIMESTAMP('2025-05-13 21:33:33.918','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Basisbausteine
-- Column: ModCntr_Log.ModCntr_BaseModule_ID
-- 2025-05-14T14:26:53.430Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.43','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631893
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Vertragsbaustein Typ
-- Column: ModCntr_Log.ModCntr_Type_ID
-- 2025-05-14T14:26:53.435Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.435','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624937
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Vertragsart
-- Column: ModCntr_Log.ContractType
-- 2025-05-14T14:26:53.441Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.441','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620334
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Rechnungskandidat
-- Column: ModCntr_Log.C_Invoice_Candidate_ID
-- 2025-05-14T14:26:53.446Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.445','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617980
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> flags.Aktiv
-- Column: ModCntr_Log.IsActive
-- 2025-05-14T14:26:53.451Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.451','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617970
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: ModCntr_Log.IsSOTrx
-- 2025-05-14T14:26:53.455Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.455','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617981
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> flags.Abrechenbar
-- Column: ModCntr_Log.IsBillable
-- 2025-05-14T14:26:53.460Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.46','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624521
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> flags.Verarbeitet
-- Column: ModCntr_Log.Processed
-- 2025-05-14T14:26:53.464Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.464','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617971
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Document Type
-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2025-05-14T14:26:53.469Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.469','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618088
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Erntejahr
-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2025-05-14T14:26:53.474Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.474','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618089
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Rechnungspartner
-- Column: ModCntr_Log.Bill_BPartner_ID
-- 2025-05-14T14:26:53.478Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.478','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618090
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Menge
-- Column: ModCntr_Log.Qty
-- 2025-05-14T14:26:53.483Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.483','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618091
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Maßeinheit
-- Column: ModCntr_Log.C_UOM_ID
-- 2025-05-14T14:26:53.492Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.492','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618092
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Einzelpreis
-- Column: ModCntr_Log.PriceActual
-- 2025-05-14T14:26:53.505Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.505','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620462
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Preiseinheit
-- Column: ModCntr_Log.Price_UOM_ID
-- 2025-05-14T14:26:53.514Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.514','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620463
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Betrag
-- Column: ModCntr_Log.Amount
-- 2025-05-14T14:26:53.524Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.524','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618093
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Währung
-- Column: ModCntr_Log.C_Currency_ID
-- 2025-05-14T14:26:53.533Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.533','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618094
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.HL
-- Column: ModCntr_Log.UserElementNumber1
-- 2025-05-14T14:26:53.543Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.543','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624780
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Protein
-- Column: ModCntr_Log.UserElementNumber2
-- 2025-05-14T14:26:53.552Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.552','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624781
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> records.DB-Tabelle
-- Column: ModCntr_Log.AD_Table_ID
-- 2025-05-14T14:26:53.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.562','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617977
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> records.Datensatz-ID
-- Column: ModCntr_Log.Record_ID
-- 2025-05-14T14:26:53.571Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.571','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617978
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> org.Organisation
-- Column: ModCntr_Log.AD_Org_ID
-- 2025-05-14T14:26:53.580Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=320,Updated=TO_TIMESTAMP('2025-05-14 16:26:53.58','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617973
;

