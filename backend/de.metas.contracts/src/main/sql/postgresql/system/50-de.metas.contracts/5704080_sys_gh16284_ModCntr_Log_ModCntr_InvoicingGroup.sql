-- Run mode: SWING_CLIENT

-- Column: ModCntr_Log.ModCntr_InvoicingGroup_ID
-- 2023-09-25T11:47:28.697Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587482,582647,0,30,542338,'ModCntr_InvoicingGroup_ID',TO_TIMESTAMP('2023-09-25 14:47:28.441','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsgruppe',0,0,TO_TIMESTAMP('2023-09-25 14:47:28.441','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-25T11:47:28.715Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587482 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-25T11:47:28.758Z
/* DDL */  select update_Column_Translation_From_AD_Element(582647)
;

-- 2023-09-25T11:47:31.841Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN ModCntr_InvoicingGroup_ID NUMERIC(10)')
;

-- 2023-09-25T11:47:32.020Z
ALTER TABLE ModCntr_Log ADD CONSTRAINT ModCntrInvoicingGroup_ModCntrLog FOREIGN KEY (ModCntr_InvoicingGroup_ID) REFERENCES public.ModCntr_InvoicingGroup DEFERRABLE INITIALLY DEFERRED
;

-- Run mode: SWING_CLIENT

-- Column: ModCntr_Log.ModCntr_InvoicingGroup_ID
-- 2023-09-25T11:48:09.270Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-09-25 14:48:09.27','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587482
;

-- Run mode: SWING_CLIENT

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Rechnungsgruppe
-- Column: ModCntr_Log.ModCntr_InvoicingGroup_ID
-- 2023-09-25T11:49:45.531Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587482,720541,0,547012,TO_TIMESTAMP('2023-09-25 14:49:45.34','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Rechnungsgruppe',TO_TIMESTAMP('2023-09-25 14:49:45.34','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-25T11:49:45.534Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720541 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-25T11:49:45.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582647)
;

-- 2023-09-25T11:49:45.555Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720541
;

-- 2023-09-25T11:49:45.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720541)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> primary.Rechnungsgruppe
-- Column: ModCntr_Log.ModCntr_InvoicingGroup_ID
-- 2023-09-25T11:50:18.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720541,0,547012,620491,550775,'F',TO_TIMESTAMP('2023-09-25 14:50:18.258','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungsgruppe',35,0,0,TO_TIMESTAMP('2023-09-25 14:50:18.258','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> primary.Rechnungsgruppe
-- Column: ModCntr_Log.ModCntr_InvoicingGroup_ID
-- 2023-09-25T11:50:28.086Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.086','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620491
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> primary.Lager
-- Column: ModCntr_Log.M_Warehouse_ID
-- 2023-09-25T11:50:28.094Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.093','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617976
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> dates.Vorgangsdatum
-- Column: ModCntr_Log.DateTrx
-- 2023-09-25T11:50:28.102Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.101','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617972
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> description.Beschreibung
-- Column: ModCntr_Log.Description
-- 2023-09-25T11:50:28.107Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.107','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617969
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Pauschale - Vertragsperiode
-- Column: ModCntr_Log.C_Flatrate_Term_ID
-- 2023-09-25T11:50:28.112Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.112','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617979
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Bausteine
-- Column: ModCntr_Log.ModCntr_Module_ID
-- 2023-09-25T11:50:28.118Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.118','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620464
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Vertragsart
-- Column: ModCntr_Log.ContractType
-- 2023-09-25T11:50:28.124Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.124','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620334
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Rechnungskandidat
-- Column: ModCntr_Log.C_Invoice_Candidate_ID
-- 2023-09-25T11:50:28.128Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.128','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617980
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> flags.Aktiv
-- Column: ModCntr_Log.IsActive
-- 2023-09-25T11:50:28.133Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.133','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617970
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: ModCntr_Log.IsSOTrx
-- 2023-09-25T11:50:28.138Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.138','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617981
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> flags.Verarbeitet
-- Column: ModCntr_Log.Processed
-- 2023-09-25T11:50:28.141Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.141','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617971
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Document Type
-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-09-25T11:50:28.144Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.144','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618088
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Erntejahr
-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2023-09-25T11:50:28.147Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.147','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618089
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Rechnungspartner
-- Column: ModCntr_Log.Bill_BPartner_ID
-- 2023-09-25T11:50:28.151Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.151','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618090
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Menge
-- Column: ModCntr_Log.Qty
-- 2023-09-25T11:50:28.154Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.154','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618091
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Maßeinheit
-- Column: ModCntr_Log.C_UOM_ID
-- 2023-09-25T11:50:28.159Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.159','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618092
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Einzelpreis
-- Column: ModCntr_Log.PriceActual
-- 2023-09-25T11:50:28.162Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.162','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620462
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Preiseinheit
-- Column: ModCntr_Log.Price_UOM_ID
-- 2023-09-25T11:50:28.167Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.167','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620463
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Betrag
-- Column: ModCntr_Log.Amount
-- 2023-09-25T11:50:28.171Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618093
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Währung
-- Column: ModCntr_Log.C_Currency_ID
-- 2023-09-25T11:50:28.175Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.175','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618094
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> records.DB-Tabelle
-- Column: ModCntr_Log.AD_Table_ID
-- 2023-09-25T11:50:28.179Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.179','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617977
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> records.Datensatz-ID
-- Column: ModCntr_Log.Record_ID
-- 2023-09-25T11:50:28.183Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.183','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617978
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> org.Organisation
-- Column: ModCntr_Log.AD_Org_ID
-- 2023-09-25T11:50:28.187Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2023-09-25 14:50:28.187','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617973
;

-- Run mode: SWING_CLIENT

-- Element: ModCntr_InvoicingGroup_ID
-- 2023-09-25T13:12:10.083Z
UPDATE AD_Element_Trl SET Name='Invoice Group', PrintName='Invoice Group',Updated=TO_TIMESTAMP('2023-09-25 16:12:10.083','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582647 AND AD_Language='en_US'
;

-- 2023-09-25T13:12:10.091Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582647,'en_US')
;

-- Run mode: SWING_CLIENT

-- Column: ModCntr_InvoicingGroup.Group_Product_ID
-- 2023-09-26T11:15:54.673Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=2,Updated=TO_TIMESTAMP('2023-09-26 14:15:54.673','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587290
;

-- Run mode: SWING_CLIENT

-- Column: ModCntr_Log.M_Product_ID
-- 2023-09-27T09:13:56.412Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2023-09-27 12:13:56.412','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586766
;

-- Column: ModCntr_Log.ModCntr_InvoicingGroup_ID
-- 2023-09-27T09:13:57.613Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2023-09-27 12:13:57.613','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587482
;

-- Column: ModCntr_Log.IsSOTrx
-- 2023-09-27T09:13:58.324Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2023-09-27 12:13:58.324','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586767
;

-- Column: ModCntr_Log.AD_Table_ID
-- 2023-09-27T09:13:58.752Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2023-09-27 12:13:58.752','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586769
;

-- Column: ModCntr_Log.M_Warehouse_ID
-- 2023-09-27T09:13:59.261Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2023-09-27 12:13:59.261','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586770
;

-- Column: ModCntr_Log.ModCntr_Type_ID
-- 2023-09-27T09:13:59.798Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2023-09-27 12:13:59.798','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586771
;

-- Column: ModCntr_Log.CollectionPoint_BPartner_ID
-- 2023-09-27T09:14:00.364Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2023-09-27 12:14:00.364','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586776
;

-- Column: ModCntr_Log.Producer_BPartner_ID
-- 2023-09-27T09:14:01.021Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2023-09-27 12:14:01.021','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586777
;

-- Column: ModCntr_Log.ContractType
-- 2023-09-27T09:14:01.571Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2023-09-27 12:14:01.571','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587273
;

-- Column: ModCntr_Log.DateTrx
-- 2023-09-27T09:14:02.131Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2023-09-27 12:14:02.131','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586772
;

