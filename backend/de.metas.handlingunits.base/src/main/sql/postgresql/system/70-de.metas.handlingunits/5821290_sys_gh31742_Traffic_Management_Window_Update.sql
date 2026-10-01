-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job_Schedule_view.ProductName
-- 2026-08-31T10:38:35.504Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593439,2659,0,10,542514,'XX','ProductName','(SELECT p.Name from M_Product p where p.M_Product_ID = M_Picking_Job_Schedule_view.M_Product_ID)',TO_TIMESTAMP('2026-08-31 10:38:34.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Name des Produktes','de.metas.handlingunits',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Produktname',0,0,TO_TIMESTAMP('2026-08-31 10:38:34.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-31T10:38:35.577Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593439 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-08-31T10:38:35.739Z
/* DDL */  select update_Column_Translation_From_AD_Element(2659)
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Produktname
-- Column: M_Picking_Job_Schedule_view.ProductName
-- 2026-08-31T10:39:52.824Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593439,783045,0,548377,0,TO_TIMESTAMP('2026-08-31 10:39:51.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name des Produktes',0,'1=2','D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Produktname',0,0,85,0,1,1,TO_TIMESTAMP('2026-08-31 10:39:51.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-31T10:39:52.894Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-08-31T10:39:52.965Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659)
;

-- 2026-08-31T10:39:53.056Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783045
;

-- 2026-08-31T10:39:53.127Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(783045)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Produktname
-- Column: M_Picking_Job_Schedule_view.ProductName
-- 2026-08-31T10:40:34.861Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783045,0,548377,553427,653688,'F',TO_TIMESTAMP('2026-08-31 10:40:34.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name des Produktes','Y','N','N','Y','N','N','N',0,'Produktname',60,0,0,TO_TIMESTAMP('2026-08-31 10:40:34.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Produktname
-- Column: M_Picking_Job_Schedule_view.ProductName
-- 2026-08-31T10:40:53.191Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-08-31 10:40:53.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=653688
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> product.Produkt
-- Column: M_Picking_Job_Schedule_view.M_Product_ID
-- 2026-08-31T10:40:53.618Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-08-31 10:40:53.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636475
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Lieferland
-- Column: M_Picking_Job_Schedule_view.C_Country_ID
-- 2026-08-31T10:40:54.040Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-08-31 10:40:54.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=653204
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Übergabeadresse
-- Column: M_Picking_Job_Schedule_view.HandOver_Location_ID
-- 2026-08-31T10:40:54.471Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-08-31 10:40:54.471000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636484
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Belegart
-- Column: M_Picking_Job_Schedule_view.C_DocType_ID
-- 2026-08-31T10:40:54.907Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-08-31 10:40:54.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652340
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Versanddienstleister
-- Column: M_Picking_Job_Schedule_view.Carrier_Product_ID
-- 2026-08-31T10:40:55.337Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-08-31 10:40:55.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637919
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> dates.Bereitstellungsdatum
-- Column: M_Picking_Job_Schedule_view.PreparationDate
-- 2026-08-31T10:40:55.762Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-08-31 10:40:55.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636479
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> product.Gewicht brutto
-- Column: M_Picking_Job_Schedule_view.GrossWeight
-- 2026-08-31T10:40:56.197Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-08-31 10:40:56.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=653206
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Zugeordnet
-- Column: M_Picking_Job_Schedule_view.IsAssigned
-- 2026-08-31T10:40:56.629Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-08-31 10:40:56.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638559
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestand
-- Column: M_Picking_Job_Schedule_view.QtyOnHand
-- 2026-08-31T10:40:57.070Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-08-31 10:40:57.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641651
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestellt/ Beauftragt
-- Column: M_Picking_Job_Schedule_view.QtyOrdered
-- 2026-08-31T10:40:57.495Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-08-31 10:40:57.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641652
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Gelieferte Menge
-- Column: M_Picking_Job_Schedule_view.QtyDelivered
-- 2026-08-31T10:40:57.922Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-08-31 10:40:57.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641653
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Ausliefermenge
-- Column: M_Picking_Job_Schedule_view.QtyToDeliver
-- 2026-08-31T10:40:58.351Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-08-31 10:40:58.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636474
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Zu planende Menge
-- Column: M_Picking_Job_Schedule_view.QtyToScheduleForPicking
-- 2026-08-31T10:40:58.776Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-08-31 10:40:58.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=644707
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Qty To Pick
-- Column: M_Picking_Job_Schedule_view.QtyToPick
-- 2026-08-31T10:40:59.210Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-08-31 10:40:59.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636482
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Menge (Lagereinheit)
-- Column: M_Picking_Job_Schedule_view.QtyPicked
-- 2026-08-31T10:40:59.645Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-08-31 10:40:59.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=644708
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Arbeitsplatz
-- Column: M_Picking_Job_Schedule_view.C_Workplace_ID
-- 2026-08-31T10:41:00.086Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-08-31 10:41:00.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636481
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Verarbeitet
-- Column: M_Picking_Job_Schedule_view.Processed
-- 2026-08-31T10:41:00.510Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-08-31 10:41:00.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641654
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Neuplanung
-- Column: M_Picking_Job_Schedule_view.IsReschedule
-- 2026-08-31T10:41:00.942Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-08-31 10:41:00.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=644709
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Produktname
-- Column: M_Picking_Job_Schedule_view.ProductName
-- 2026-08-31T10:42:00.456Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2026-08-31 10:42:00.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=783045
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> product.Produkt
-- Column: M_Picking_Job_Schedule_view.M_Product_ID
-- 2026-08-31T10:42:59.767Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-08-31 10:42:59.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636475
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Lieferland
-- Column: M_Picking_Job_Schedule_view.C_Country_ID
-- 2026-08-31T10:43:00.187Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-08-31 10:43:00.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=653204
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Übergabeadresse
-- Column: M_Picking_Job_Schedule_view.HandOver_Location_ID
-- 2026-08-31T10:43:00.612Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-08-31 10:43:00.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636484
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Belegart
-- Column: M_Picking_Job_Schedule_view.C_DocType_ID
-- 2026-08-31T10:43:01.044Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-08-31 10:43:01.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652340
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Versanddienstleister
-- Column: M_Picking_Job_Schedule_view.Carrier_Product_ID
-- 2026-08-31T10:43:01.483Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-08-31 10:43:01.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637919
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> dates.Bereitstellungsdatum
-- Column: M_Picking_Job_Schedule_view.PreparationDate
-- 2026-08-31T10:43:01.914Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-08-31 10:43:01.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636479
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> product.Gewicht brutto
-- Column: M_Picking_Job_Schedule_view.GrossWeight
-- 2026-08-31T10:43:02.339Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-08-31 10:43:02.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=653206
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Zugeordnet
-- Column: M_Picking_Job_Schedule_view.IsAssigned
-- 2026-08-31T10:43:02.762Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-08-31 10:43:02.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638559
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestand
-- Column: M_Picking_Job_Schedule_view.QtyOnHand
-- 2026-08-31T10:43:03.181Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-08-31 10:43:03.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641651
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestellt/ Beauftragt
-- Column: M_Picking_Job_Schedule_view.QtyOrdered
-- 2026-08-31T10:43:03.599Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-08-31 10:43:03.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641652
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Gelieferte Menge
-- Column: M_Picking_Job_Schedule_view.QtyDelivered
-- 2026-08-31T10:43:04.021Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-08-31 10:43:04.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641653
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Ausliefermenge
-- Column: M_Picking_Job_Schedule_view.QtyToDeliver
-- 2026-08-31T10:43:04.447Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-08-31 10:43:04.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636474
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Zu planende Menge
-- Column: M_Picking_Job_Schedule_view.QtyToScheduleForPicking
-- 2026-08-31T10:43:04.872Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-08-31 10:43:04.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=644707
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Qty To Pick
-- Column: M_Picking_Job_Schedule_view.QtyToPick
-- 2026-08-31T10:43:05.292Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-08-31 10:43:05.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636482
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Menge (Lagereinheit)
-- Column: M_Picking_Job_Schedule_view.QtyPicked
-- 2026-08-31T10:43:05.714Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-08-31 10:43:05.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=644708
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Arbeitsplatz
-- Column: M_Picking_Job_Schedule_view.C_Workplace_ID
-- 2026-08-31T10:43:06.132Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-08-31 10:43:06.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636481
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Verarbeitet
-- Column: M_Picking_Job_Schedule_view.Processed
-- 2026-08-31T10:43:06.559Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-08-31 10:43:06.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641654
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Neuplanung
-- Column: M_Picking_Job_Schedule_view.IsReschedule
-- 2026-08-31T10:43:06.979Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-08-31 10:43:06.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=644709
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Produktname
-- Column: M_Picking_Job_Schedule_view.ProductName
-- 2026-08-31T10:44:02.152Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-08-31 10:44:02.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=653688
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Produkt
-- Column: M_Picking_Job_Schedule_view.ProductName
-- 2026-08-31T10:45:38.343Z
UPDATE AD_Field SET AD_Name_ID=572779, Description=NULL, Help=NULL, Name='Produkt',Updated=TO_TIMESTAMP('2026-08-31 10:45:38.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=783045
;

-- 2026-08-31T10:45:38.415Z
UPDATE AD_Field_Trl trl SET Description=NULL,Name='Produkt' WHERE AD_Field_ID=783045 AND AD_Language='de_DE'
;

-- 2026-08-31T10:45:40.507Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572779)
;

-- 2026-08-31T10:45:40.580Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783045
;

-- 2026-08-31T10:45:40.649Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(783045)
;

-- Column: M_Picking_Job_Schedule_view.M_Product_ID
-- 2026-08-31T10:47:33.131Z
UPDATE AD_Column SET AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2026-08-31 10:47:33.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590666
;

