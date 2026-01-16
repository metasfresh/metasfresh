-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job_Schedule_view.Processed
-- 2026-01-16T09:27:56.927Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591861,1047,0,20,542514,'XX','Processed',TO_TIMESTAMP('2026-01-16 09:27:56.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','de.metas.handlingunits',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2026-01-16 09:27:56.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-16T09:27:56.939Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591861 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-16T09:27:56.969Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047)
;

-- Column: M_Picking_Job_Schedule_view.QtyOnHand
-- 2026-01-16T09:28:29.411Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591862,530,0,29,542514,'XX','QtyOnHand',TO_TIMESTAMP('2026-01-16 09:28:29.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Bestand gibt die Menge des Produktes an, die im Lager verfügbar ist.','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Bestand',0,0,TO_TIMESTAMP('2026-01-16 09:28:29.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-16T09:28:29.420Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591862 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-16T09:28:29.561Z
/* DDL */  select update_Column_Translation_From_AD_Element(530)
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Status der Lieferweg-Abfrage
-- Column: M_Picking_Job_Schedule_view.Carrier_Advising_Status
-- 2026-01-16T09:33:51.695Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591390,761686,0,548377,TO_TIMESTAMP('2026-01-16 09:33:51.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,3,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Status der Lieferweg-Abfrage',TO_TIMESTAMP('2026-01-16 09:33:51.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T09:33:51.708Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=761686 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-16T09:33:51.726Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584115)
;

-- 2026-01-16T09:33:51.745Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761686
;

-- 2026-01-16T09:33:51.753Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761686)
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Materialzuordnung je Lieferweg
-- Column: M_Picking_Job_Schedule_view.Carrier_Goods_Type_ID
-- 2026-01-16T09:33:51.855Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591392,761687,0,548377,TO_TIMESTAMP('2026-01-16 09:33:51.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Materialzuordnung je Lieferweg',TO_TIMESTAMP('2026-01-16 09:33:51.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T09:33:51.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=761687 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-16T09:33:51.857Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584112)
;

-- 2026-01-16T09:33:51.860Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761687
;

-- 2026-01-16T09:33:51.860Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761687)
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Verarbeitet
-- Column: M_Picking_Job_Schedule_view.Processed
-- 2026-01-16T09:33:51.947Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591861,761688,0,548377,TO_TIMESTAMP('2026-01-16 09:33:51.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'de.metas.handlingunits','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2026-01-16 09:33:51.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T09:33:51.952Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=761688 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-16T09:33:51.965Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2026-01-16T09:33:52.054Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761688
;

-- 2026-01-16T09:33:52.055Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761688)
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Bestand
-- Column: M_Picking_Job_Schedule_view.QtyOnHand
-- 2026-01-16T09:33:52.137Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591862,761689,0,548377,TO_TIMESTAMP('2026-01-16 09:33:52.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestand gibt die Menge des Produktes an, die im Lager verfügbar ist.',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Bestand',TO_TIMESTAMP('2026-01-16 09:33:52.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T09:33:52.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=761689 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-16T09:33:52.139Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(530)
;

-- 2026-01-16T09:33:52.142Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761689
;

-- 2026-01-16T09:33:52.143Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761689)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Ausliefermenge
-- Column: M_Picking_Job_Schedule_view.QtyToDeliver
-- 2026-01-16T09:35:33.232Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2026-01-16 09:35:33.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636474
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Maßeinheit
-- Column: M_Picking_Job_Schedule_view.C_UOM_ID
-- 2026-01-16T09:35:41.348Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2026-01-16 09:35:41.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636477
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestand
-- Column: M_Picking_Job_Schedule_view.QtyOnHand
-- 2026-01-16T09:36:12.828Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,761689,0,548377,553424,641651,'F',TO_TIMESTAMP('2026-01-16 09:36:12.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestand gibt die Menge des Produktes an, die im Lager verfügbar ist.','Y','N','N','Y','N','N','N',0,'Bestand',10,0,0,TO_TIMESTAMP('2026-01-16 09:36:12.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestellt/ Beauftragt
-- Column: M_Picking_Job_Schedule_view.QtyOrdered
-- 2026-01-16T09:36:38.315Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752539,0,548377,553424,641652,'F',TO_TIMESTAMP('2026-01-16 09:36:38.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','Y','N','N','N',0,'Bestellt/ Beauftragt',20,0,0,TO_TIMESTAMP('2026-01-16 09:36:38.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Gelieferte Menge
-- Column: M_Picking_Job_Schedule_view.QtyDelivered
-- 2026-01-16T09:37:16.474Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752541,0,548377,553424,641653,'F',TO_TIMESTAMP('2026-01-16 09:37:16.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gelieferte Menge','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','N','Y','N','N','N',0,'Gelieferte Menge',50,0,0,TO_TIMESTAMP('2026-01-16 09:37:16.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestand
-- Column: M_Picking_Job_Schedule_view.QtyOnHand
-- 2026-01-16T09:38:34.005Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-01-16 09:38:34.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641651
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestellt/ Beauftragt
-- Column: M_Picking_Job_Schedule_view.QtyOrdered
-- 2026-01-16T09:38:34.012Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-01-16 09:38:34.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641652
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Gelieferte Menge
-- Column: M_Picking_Job_Schedule_view.QtyDelivered
-- 2026-01-16T09:38:34.017Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-01-16 09:38:34.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641653
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Ausliefermenge
-- Column: M_Picking_Job_Schedule_view.QtyToDeliver
-- 2026-01-16T09:38:34.022Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-01-16 09:38:34.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636474
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Qty To Pick
-- Column: M_Picking_Job_Schedule_view.QtyToPick
-- 2026-01-16T09:38:34.027Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-01-16 09:38:34.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636482
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Arbeitsplatz
-- Column: M_Picking_Job_Schedule_view.C_Workplace_ID
-- 2026-01-16T09:38:34.032Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-01-16 09:38:34.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636481
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Verarbeitet
-- Column: M_Picking_Job_Schedule_view.Processed
-- 2026-01-16T09:39:22.492Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,761688,0,548377,553427,641654,'F',TO_TIMESTAMP('2026-01-16 09:39:22.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',30,0,0,TO_TIMESTAMP('2026-01-16 09:39:22.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Verarbeitet
-- Column: M_Picking_Job_Schedule_view.Processed
-- 2026-01-16T09:39:29.782Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-01-16 09:39:29.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641654
;

-- Column: M_Picking_Job_Schedule_view.Processed
-- 2026-01-16T09:55:06.712Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-01-16 09:55:06.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591861
;

