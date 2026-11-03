-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job_Schedule_view.GrossWeight
-- 2026-08-19T15:19:11.520Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,593336,583223,0,22,542514,'GrossWeight',TO_TIMESTAMP('2026-08-19 15:19:10.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','Gewicht brutto',TO_TIMESTAMP('2026-08-19 15:19:10.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-19T15:19:11.594Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593336 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-08-19T15:19:24.644Z
/* DDL */  select update_Column_Translation_From_AD_Element(583223)
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Gewicht brutto
-- Column: M_Picking_Job_Schedule_view.GrossWeight
-- 2026-08-19T15:22:51.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593336,782380,0,548377,0,TO_TIMESTAMP('2026-08-19 15:22:49.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Gewicht brutto',0,0,75,0,1,1,TO_TIMESTAMP('2026-08-19 15:22:49.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-19T15:22:52.141Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782380 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-08-19T15:22:52.323Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583223)
;

-- 2026-08-19T15:22:52.404Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782380
;

-- 2026-08-19T15:22:52.479Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(782380)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> product.Gewicht brutto
-- Column: M_Picking_Job_Schedule_view.GrossWeight
-- 2026-08-19T15:26:31.243Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782380,0,548377,553425,653206,'F',TO_TIMESTAMP('2026-08-19 15:26:18.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Gewicht brutto',30,0,0,TO_TIMESTAMP('2026-08-19 15:26:18.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> product.Gewicht brutto
-- Column: M_Picking_Job_Schedule_view.GrossWeight
-- 2026-08-19T15:27:08.160Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-08-19 15:27:08.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=653206
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Zugeordnet
-- Column: M_Picking_Job_Schedule_view.IsAssigned
-- 2026-08-19T15:27:08.770Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-08-19 15:27:08.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638559
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestand
-- Column: M_Picking_Job_Schedule_view.QtyOnHand
-- 2026-08-19T15:27:09.350Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-08-19 15:27:09.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641651
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Bestellt/ Beauftragt
-- Column: M_Picking_Job_Schedule_view.QtyOrdered
-- 2026-08-19T15:27:09.956Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-08-19 15:27:09.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641652
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Gelieferte Menge
-- Column: M_Picking_Job_Schedule_view.QtyDelivered
-- 2026-08-19T15:27:10.537Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-08-19 15:27:10.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641653
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Ausliefermenge
-- Column: M_Picking_Job_Schedule_view.QtyToDeliver
-- 2026-08-19T15:27:11.088Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-08-19 15:27:11.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636474
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Zu planende Menge
-- Column: M_Picking_Job_Schedule_view.QtyToScheduleForPicking
-- 2026-08-19T15:27:11.654Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-08-19 15:27:11.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=644707
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Qty To Pick
-- Column: M_Picking_Job_Schedule_view.QtyToPick
-- 2026-08-19T15:27:12.231Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-08-19 15:27:12.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636482
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Menge (Lagereinheit)
-- Column: M_Picking_Job_Schedule_view.QtyPicked
-- 2026-08-19T15:27:12.770Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-08-19 15:27:12.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=644708
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Arbeitsplatz
-- Column: M_Picking_Job_Schedule_view.C_Workplace_ID
-- 2026-08-19T15:27:13.317Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-08-19 15:27:13.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636481
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Verarbeitet
-- Column: M_Picking_Job_Schedule_view.Processed
-- 2026-08-19T15:27:13.892Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-08-19 15:27:13.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641654
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Neuplanung
-- Column: M_Picking_Job_Schedule_view.IsReschedule
-- 2026-08-19T15:27:14.448Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-08-19 15:27:14.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=644709
;

