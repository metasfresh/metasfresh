-- Run mode: SWING_CLIENT

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Rechnungspartner
-- Column: ModCntr_Interest_V.Bill_BPartner_ID
-- 2024-05-22T19:19:06.121Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728755,0,547546,551836,624768,'F',TO_TIMESTAMP('2024-05-22 22:19:05.744','YYYY-MM-DD HH24:MI:SS.US'),100,'Geschäftspartner für die Rechnungsstellung','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','Y','N','N','N',0,'Rechnungspartner',40,0,0,TO_TIMESTAMP('2024-05-22 22:19:05.744','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T19:20:24.998Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583124,0,'modcntr_interest_v_id',TO_TIMESTAMP('2024-05-22 22:20:24.853','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','modcntr_interest_v_id','modcntr_interest_v_id',TO_TIMESTAMP('2024-05-22 22:20:24.853','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T19:20:25.003Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583124 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-05-22T19:21:01.863Z
UPDATE AD_Element SET ColumnName='ModCntr_Interest_V_ID',Updated=TO_TIMESTAMP('2024-05-22 22:21:01.863','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583124
;

-- 2024-05-22T19:21:01.864Z
UPDATE AD_Column SET ColumnName='ModCntr_Interest_V_ID' WHERE AD_Element_ID=583124
;

-- 2024-05-22T19:21:01.865Z
UPDATE AD_Process_Para SET ColumnName='ModCntr_Interest_V_ID' WHERE AD_Element_ID=583124
;

-- 2024-05-22T19:21:01.899Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583124,'de_DE')
;

-- Column: ModCntr_Interest_V.ModCntr_Interest_V_ID
-- 2024-05-22T19:21:22.390Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588319,583124,0,13,542412,'ModCntr_Interest_V_ID',TO_TIMESTAMP('2024-05-22 22:21:22.239','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'modcntr_interest_v_id',0,0,TO_TIMESTAMP('2024-05-22 22:21:22.239','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T19:21:22.391Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588319 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T19:21:22.393Z
/* DDL */  select update_Column_Translation_From_AD_Element(583124)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Name
-- 2024-05-22T19:26:43.060Z
UPDATE AD_UI_Element SET AD_Field_ID=NULL,Updated=TO_TIMESTAMP('2024-05-22 22:26:43.06','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624750
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> modcntr_interest_v_id
-- Column: ModCntr_Interest_V.ModCntr_Interest_V_ID
-- 2024-05-22T19:27:35.988Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588319,728760,0,547546,TO_TIMESTAMP('2024-05-22 22:27:35.847','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','modcntr_interest_v_id',TO_TIMESTAMP('2024-05-22 22:27:35.847','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T19:27:35.989Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728760 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T19:27:35.990Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583124)
;

-- 2024-05-22T19:27:36.001Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728760
;

-- 2024-05-22T19:27:36.002Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728760)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Ursprüngliches Produkt
-- Column: ModCntr_Interest_V.Initial_Product_ID
-- 2024-05-22T19:29:05.735Z
UPDATE AD_UI_Element SET AD_Field_ID=728757, Name='Ursprüngliches Produkt',Updated=TO_TIMESTAMP('2024-05-22 22:29:05.735','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624750
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Rechnungsgruppe
-- Column: ModCntr_Interest_V.ModCntr_InvoicingGroup_ID
-- 2024-05-22T19:29:57.905Z
UPDATE AD_UI_Element SET AD_Field_ID=728756, Name='Rechnungsgruppe',Updated=TO_TIMESTAMP('2024-05-22 22:29:57.905','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624755
;

-- 2024-05-22T19:30:27.645Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728753
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Produktschlüssel
-- Column: ModCntr_Interest_V.ProductValue
-- 2024-05-22T19:30:27.648Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728753
;

-- 2024-05-22T19:30:27.651Z
DELETE FROM AD_Field WHERE AD_Field_ID=728753
;

-- 2024-05-22T19:30:32.109Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728749
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Produktname
-- Column: ModCntr_Interest_V.ProductName
-- 2024-05-22T19:30:32.111Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728749
;

-- 2024-05-22T19:30:32.115Z
DELETE FROM AD_Field WHERE AD_Field_ID=728749
;

-- 2024-05-22T19:30:35.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728754
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Name
-- Column: ModCntr_Interest_V.Name
-- 2024-05-22T19:30:35.324Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728754
;

-- 2024-05-22T19:30:35.327Z
DELETE FROM AD_Field WHERE AD_Field_ID=728754
;

-- 2024-05-22T19:30:54.250Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728732
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Rechnungspartner Suchschlüssel
-- Column: ModCntr_Interest_V.Bill_BPartner_Value
-- 2024-05-22T19:30:54.252Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728732
;

-- 2024-05-22T19:30:54.255Z
DELETE FROM AD_Field WHERE AD_Field_ID=728732
;

-- 2024-05-22T19:30:58.024Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728733
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Name Rechnungspartner
-- Column: ModCntr_Interest_V.Bill_BPartner_Name
-- 2024-05-22T19:30:58.026Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728733
;

-- 2024-05-22T19:30:58.029Z
DELETE FROM AD_Field WHERE AD_Field_ID=728733
;

-- 2024-05-22T19:31:12.998Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728735
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Vorfinanzierungsnummer
-- Column: ModCntr_Interest_V.InterimInvoice_documentNo
-- 2024-05-22T19:31:13Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728735
;

-- 2024-05-22T19:31:13.003Z
DELETE FROM AD_Field WHERE AD_Field_ID=728735
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Name der Abrechnungszeilengruppe
-- Column: ModCntr_Interest_V.InvoicingGroup_Name
-- 2024-05-22T19:31:20.667Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624756
;

-- 2024-05-22T19:31:20.669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728734
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Name der Abrechnungszeilengruppe
-- Column: ModCntr_Interest_V.InvoicingGroup_Name
-- 2024-05-22T19:31:20.670Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728734
;

-- 2024-05-22T19:31:20.673Z
DELETE FROM AD_Field WHERE AD_Field_ID=728734
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Endgültige Zinsen
-- Column: ModCntr_Interest_V.FinalInterest
-- 2024-05-22T19:31:52.104Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-05-22 22:31:52.104','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624767
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Pauschale - Vertragsperiode
-- Column: ModCntr_Interest_V.C_Flatrate_Term_ID
-- 2024-05-22T19:34:02.340Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.34','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624751
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zusätzliche Zinstage
-- Column: ModCntr_Interest_V.AddInterestDays
-- 2024-05-22T19:34:02.344Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.344','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624760
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Rechnungspartner
-- Column: ModCntr_Interest_V.Bill_BPartner_ID
-- 2024-05-22T19:34:02.349Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.349','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624768
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Vorgangsdatum
-- Column: ModCntr_Interest_V.DateTrx
-- 2024-05-22T19:34:02.353Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.353','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624757
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Endgültige Zinsen
-- Column: ModCntr_Interest_V.FinalInterest
-- 2024-05-22T19:34:02.357Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.357','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624767
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Ursprüngliches Produkt
-- Column: ModCntr_Interest_V.Initial_Product_ID
-- 2024-05-22T19:34:02.361Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.361','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624750
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zinstage
-- Column: ModCntr_Interest_V.InterestDays
-- 2024-05-22T19:34:02.365Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.365','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624765
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zinssatz
-- Column: ModCntr_Interest_V.InterestRate
-- 2024-05-22T19:34:02.368Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.368','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624761
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zinsnummer
-- Column: ModCntr_Interest_V.InterestScore
-- 2024-05-22T19:34:02.371Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.371','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624766
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Vorfinanzierungsbetrag
-- Column: ModCntr_Interest_V.InterimAmt
-- 2024-05-22T19:34:02.375Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.375','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624762
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Übereinstimmender Betrag
-- Column: ModCntr_Interest_V.MatchedAmt
-- 2024-05-22T19:34:02.378Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624763
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Rechnungsgruppe
-- Column: ModCntr_Interest_V.ModCntr_InvoicingGroup_ID
-- 2024-05-22T19:34:02.381Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.381','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624755
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Handlertyp für modularen Vertrag
-- Column: ModCntr_Interest_V.ModularContractHandlerType
-- 2024-05-22T19:34:02.385Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.385','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624752
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Menge
-- Column: ModCntr_Interest_V.Qty
-- 2024-05-22T19:34:02.388Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.388','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624758
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Gesamtbetrag
-- Column: ModCntr_Interest_V.TotalAmt
-- 2024-05-22T19:34:02.391Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.391','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624764
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Mengeneinheit
-- Column: ModCntr_Interest_V.UOM
-- 2024-05-22T19:34:02.395Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624759
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Schlussabrechnung
-- Column: ModCntr_Interest_V.C_FinalInvoice_ID
-- 2024-05-22T19:34:02.398Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-05-22 22:34:02.398','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624749
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Akontorechnung
-- Column: ModCntr_Interest_V.C_InterimInvoice_ID
-- 2024-05-22T19:34:45.456Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728758,0,547546,551836,624769,'F',TO_TIMESTAMP('2024-05-22 22:34:45.334','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Akontorechnung',180,0,0,TO_TIMESTAMP('2024-05-22 22:34:45.334','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Maßeinheit
-- Column: ModCntr_Interest_V.C_UOM_ID
-- 2024-05-22T19:35:52.843Z
UPDATE AD_UI_Element SET AD_Field_ID=728759, Description='Maßeinheit', Help='Eine eindeutige (nicht monetäre) Maßeinheit', Name='Maßeinheit',Updated=TO_TIMESTAMP('2024-05-22 22:35:52.843','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624759
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Schlussabrechnung
-- Column: ModCntr_Interest_V.C_FinalInvoice_ID
-- 2024-05-22T19:36:30.990Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2024-05-22 22:36:30.99','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624749
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Rechnungsgruppe
-- Column: ModCntr_Interest_V.ModCntr_InvoicingGroup_ID
-- 2024-05-22T19:36:38.487Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2024-05-22 22:36:38.487','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624755
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zusätzliche Zinstage
-- Column: ModCntr_Interest_V.AddInterestDays
-- 2024-05-22T19:36:57.474Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.474','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624760
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Rechnungspartner
-- Column: ModCntr_Interest_V.Bill_BPartner_ID
-- 2024-05-22T19:36:57.479Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.479','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624768
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Schlussabrechnung
-- Column: ModCntr_Interest_V.C_FinalInvoice_ID
-- 2024-05-22T19:36:57.483Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.483','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624749
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Pauschale - Vertragsperiode
-- Column: ModCntr_Interest_V.C_Flatrate_Term_ID
-- 2024-05-22T19:36:57.487Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.487','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624751
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Maßeinheit
-- Column: ModCntr_Interest_V.C_UOM_ID
-- 2024-05-22T19:36:57.490Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.49','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624759
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Vorgangsdatum
-- Column: ModCntr_Interest_V.DateTrx
-- 2024-05-22T19:36:57.494Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.494','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624757
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Endgültige Zinsen
-- Column: ModCntr_Interest_V.FinalInterest
-- 2024-05-22T19:36:57.498Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.498','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624767
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Ursprüngliches Produkt
-- Column: ModCntr_Interest_V.Initial_Product_ID
-- 2024-05-22T19:36:57.502Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.502','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624750
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zinstage
-- Column: ModCntr_Interest_V.InterestDays
-- 2024-05-22T19:36:57.505Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.505','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624765
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zinssatz
-- Column: ModCntr_Interest_V.InterestRate
-- 2024-05-22T19:36:57.508Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.508','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624761
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zinsnummer
-- Column: ModCntr_Interest_V.InterestScore
-- 2024-05-22T19:36:57.511Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.511','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624766
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Vorfinanzierungsbetrag
-- Column: ModCntr_Interest_V.InterimAmt
-- 2024-05-22T19:36:57.514Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.514','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624762
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Übereinstimmender Betrag
-- Column: ModCntr_Interest_V.MatchedAmt
-- 2024-05-22T19:36:57.517Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.517','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624763
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Rechnungsgruppe
-- Column: ModCntr_Interest_V.ModCntr_InvoicingGroup_ID
-- 2024-05-22T19:36:57.520Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.52','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624755
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Handlertyp für modularen Vertrag
-- Column: ModCntr_Interest_V.ModularContractHandlerType
-- 2024-05-22T19:36:57.523Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.523','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624752
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Menge
-- Column: ModCntr_Interest_V.Qty
-- 2024-05-22T19:36:57.526Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.526','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624758
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Gesamtbetrag
-- Column: ModCntr_Interest_V.TotalAmt
-- 2024-05-22T19:36:57.529Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-22 22:36:57.529','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624764
;

