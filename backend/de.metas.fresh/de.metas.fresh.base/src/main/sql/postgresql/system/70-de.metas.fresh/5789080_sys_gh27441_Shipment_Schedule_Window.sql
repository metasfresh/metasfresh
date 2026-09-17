-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T13:37:48.844Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,592027,576557,0,27,500221,'XX','InsufficientQtyAvailableForSalesColor_ID','( select ol.InsufficientQtyAvailableForSalesColor_ID from C_OrderLine ol where ol.C_OrderLine_ID = M_ShipmentSchedule.C_OrderLine_ID)',TO_TIMESTAMP('2026-02-18 13:37:48.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N',0,'Farbe für kurzfr. Verfügbarkeitsproblem','',0,TO_TIMESTAMP('2026-02-18 13:37:48.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1.000000000000)
;

-- 2026-02-18T13:37:48.916Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592027 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-18T13:37:49.060Z
/* DDL */  select update_Column_Translation_From_AD_Element(576557)
;

-- Column: M_ShipmentSchedule.QtyAvailableForSales
-- 2026-02-18T13:42:15.229Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,592029,576524,0,29,500221,'XX','QtyAvailableForSales','( select ol.QtyAvailableForSales from C_OrderLine ol where ol.C_OrderLine_ID = M_ShipmentSchedule.C_OrderLine_ID)',TO_TIMESTAMP('2026-02-18 13:42:14.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N',0,'kurzfr. Verfügbar','',0,TO_TIMESTAMP('2026-02-18 13:42:14.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1.000000000000)
;

-- 2026-02-18T13:42:15.288Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592029 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-18T13:42:17.817Z
/* DDL */  select update_Column_Translation_From_AD_Element(576524)
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Verfügbar
-- Column: M_ShipmentSchedule.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T13:44:16.174Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592027,774164,1762,0,500221,0,TO_TIMESTAMP('2026-02-18 13:44:15.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ressource ist verfügbar',0,'D',0,'Ressource ist verfügbar für Zuordnungen',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Verfügbar',0,0,790,0,1,1,TO_TIMESTAMP('2026-02-18 13:44:15.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T13:44:16.236Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-18T13:44:16.298Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1762)
;

-- 2026-02-18T13:44:16.367Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774164
;

-- 2026-02-18T13:44:16.428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774164)
;

-- 2026-02-18T13:45:52.564Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584542,0,TO_TIMESTAMP('2026-02-18 13:45:52.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Verfügbare Menge','Verfügbare Menge',TO_TIMESTAMP('2026-02-18 13:45:52.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T13:45:52.631Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584542 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-02-18T13:46:30.540Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Available Quantity', PrintName='Available Quantity',Updated=TO_TIMESTAMP('2026-02-18 13:46:30.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584542 AND AD_Language='en_US'
;

-- 2026-02-18T13:46:30.781Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-18T13:46:43.027Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584542,'en_US')
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Verfügbare Menge
-- Column: M_ShipmentSchedule.QtyAvailableForSales
-- 2026-02-18T13:46:54.754Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592029,774165,584542,0,500221,0,TO_TIMESTAMP('2026-02-18 13:46:54.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Verfügbare Menge',0,0,800,0,1,1,TO_TIMESTAMP('2026-02-18 13:46:54.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T13:46:54.814Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-18T13:46:54.876Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584542)
;

-- 2026-02-18T13:46:54.936Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774165
;

-- 2026-02-18T13:46:54.994Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774165)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Verfügbar
-- Column: M_ShipmentSchedule.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T13:47:58.352Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774164,0,500221,540050,648129,'F',TO_TIMESTAMP('2026-02-18 13:47:57.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ressource ist verfügbar','Ressource ist verfügbar für Zuordnungen','Y','N','N','Y','N','N','N',0,'Verfügbar',100,0,0,TO_TIMESTAMP('2026-02-18 13:47:57.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Verfügbare Menge
-- Column: M_ShipmentSchedule.QtyAvailableForSales
-- 2026-02-18T13:48:22.117Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774165,0,500221,540050,648130,'F',TO_TIMESTAMP('2026-02-18 13:48:21.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Verfügbare Menge',110,0,0,TO_TIMESTAMP('2026-02-18 13:48:21.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Verfügbare Menge
-- Column: M_ShipmentSchedule.QtyAvailableForSales
-- 2026-02-18T13:49:03.134Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-18 13:49:03.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648130
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Verfügbar
-- Column: M_ShipmentSchedule.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T13:49:03.571Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-18 13:49:03.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648129
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> grid fields.Lieferadresse
-- Column: M_ShipmentSchedule.C_BPartner_Location_ID
-- 2026-02-18T13:49:03.932Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-18 13:49:03.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547300
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> order dates.Auftrag
-- Column: M_ShipmentSchedule.C_Order_ID
-- 2026-02-18T13:49:04.282Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-18 13:49:04.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540934
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Delivery Status_DeliveryStatusColor_ID
-- Column: M_ShipmentSchedule.DeliveryStatusColor_ID
-- 2026-02-18T13:49:04.630Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-18 13:49:04.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646828
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> order dates.Referenz
-- Column: M_ShipmentSchedule.POReference
-- 2026-02-18T13:49:04.983Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-18 13:49:04.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540935
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Produkt
-- Column: M_ShipmentSchedule.M_Product_ID
-- 2026-02-18T13:49:05.334Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-18 13:49:05.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540636
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Produkt Kategorie
-- Column: M_ShipmentSchedule.M_Product_Category_ID
-- 2026-02-18T13:49:05.694Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-02-18 13:49:05.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636486
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Merkmale
-- Column: M_ShipmentSchedule.M_AttributeSetInstance_ID
-- 2026-02-18T13:49:06.047Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-02-18 13:49:06.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540638
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Mindesthaltbarkeitsdatum
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2026-02-18T13:49:07.047Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-02-18 13:49:07.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627351
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Beauftragte Gebindemenge
-- Column: M_ShipmentSchedule.QtyOrdered_TU
-- 2026-02-18T13:49:07.387Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-02-18 13:49:07.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547313
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Packvorschrift
-- Column: M_ShipmentSchedule.M_HU_PI_Item_Product_ID
-- 2026-02-18T13:49:07.728Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-02-18 13:49:07.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548121
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Beauftragt
-- Column: M_ShipmentSchedule.QtyOrdered_Calculated
-- 2026-02-18T13:49:08.070Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-02-18 13:49:08.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547299
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.QtyOrdered_Override
-- Column: M_ShipmentSchedule.QtyOrdered_Override
-- 2026-02-18T13:49:08.415Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-02-18 13:49:08.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547310
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Liefermenge
-- Column: M_ShipmentSchedule.QtyToDeliver
-- 2026-02-18T13:49:08.756Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-02-18 13:49:08.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540943
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Auf Packzettel
-- Column: M_ShipmentSchedule.QtyPickList
-- 2026-02-18T13:49:09.097Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-02-18 13:49:09.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540944
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Geliefert
-- Column: M_ShipmentSchedule.QtyDelivered
-- 2026-02-18T13:49:09.439Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-02-18 13:49:09.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540945
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Streckengeschäft
-- Column: M_ShipmentSchedule.IsDropShip
-- 2026-02-18T13:49:09.779Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-02-18 13:49:09.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547308
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Zu Akt.
-- Column: M_ShipmentSchedule.IsToRecompute
-- 2026-02-18T13:49:10.120Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2026-02-18 13:49:10.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547306
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Verarbeitet
-- Column: M_ShipmentSchedule.Processed
-- 2026-02-18T13:49:10.471Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2026-02-18 13:49:10.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547307
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> org.Sektion
-- Column: M_ShipmentSchedule.AD_Org_ID
-- 2026-02-18T13:49:10.811Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2026-02-18 13:49:10.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547302
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Verfügbare Menge
-- Column: M_ShipmentSchedule.QtyAvailableForSales
-- 2026-02-18T13:49:59.130Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-18 13:49:59.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648130
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Verfügbar
-- Column: M_ShipmentSchedule.InsufficientQtyAvailableForSalesColor_ID
-- 2026-02-18T13:50:03.524Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-18 13:50:03.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648129
;

