-- Run mode: SWING_CLIENT

-- Column: C_Order.DeliveryStatus
-- Column SQL (old): ( CASE WHEN C_Order.QtyOrdered <= C_Order.QtyMoved AND C_Order.QtyOrdered > 0 THEN 'CD' WHEN C_Order.QtyOrdered > C_Order.QtyMoved AND C_Order.QtyMoved > 0 THEN 'PD' ELSE 'O' END )
-- 2026-04-22T10:22:00.807Z
UPDATE AD_Column SET ColumnSQL='C_Order_DeliveryStatus_Compute(C_Order)',Updated=TO_TIMESTAMP('2026-04-22 10:22:00.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=552710
;

-- Column: C_Order.InvoiceStatus
-- Column SQL (old): ( CASE WHEN C_Order.QtyOrdered <= C_Order.QtyInvoiced AND C_Order.QtyOrdered > 0 THEN 'CI' WHEN C_Order.QtyOrdered > C_Order.QtyInvoiced AND C_Order.QtyInvoiced > 0 THEN 'PI' ELSE 'O' END )
-- 2026-04-22T11:24:51.323Z
UPDATE AD_Column SET ColumnSQL='C_Order_InvoiceStatus_Compute(C_Order)',Updated=TO_TIMESTAMP('2026-04-22 11:24:51.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=552695
;


-- 2026-04-22T11:39:41.059Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584777,0,'ProcessStatusColor_ID',TO_TIMESTAMP('2026-04-22 11:39:40.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C','Y','Process Status','Process Status',TO_TIMESTAMP('2026-04-22 11:39:40.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-22T11:39:41.062Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584777 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Order.ProcessStatusColor_ID
-- 2026-04-22T11:40:44.824Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592405,584777,0,27,259,'XX','ProcessStatusColor_ID','C_Order_ProcessStatus_Color_ID_Compute(C_Order)',TO_TIMESTAMP('2026-04-22 11:40:44.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','C',0,10,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Process Status',0,0,TO_TIMESTAMP('2026-04-22 11:40:44.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-04-22T11:40:44.830Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592405 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-04-22T11:40:44.836Z
/* DDL */  select update_Column_Translation_From_AD_Element(584777)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Process Status
-- Column: C_Order.ProcessStatusColor_ID
-- 2026-04-22T11:41:38.752Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592405,777223,0,294,0,TO_TIMESTAMP('2026-04-22 11:41:38.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'C',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Process Status',0,0,260,0,1,1,TO_TIMESTAMP('2026-04-22 11:41:38.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-22T11:41:38.759Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777223 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-22T11:41:38.764Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584777)
;

-- 2026-04-22T11:41:38.785Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777223
;

-- 2026-04-22T11:41:38.789Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777223)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 20 -> posted.Process Status
-- Column: C_Order.ProcessStatusColor_ID
-- 2026-04-22T11:47:33.396Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777223,0,294,543268,649948,'F',TO_TIMESTAMP('2026-04-22 11:47:33.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N','N','N',0,'Process Status',30,0,0,TO_TIMESTAMP('2026-04-22 11:47:33.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 20 -> posted.Process Status
-- Column: C_Order.ProcessStatusColor_ID
-- 2026-04-22T11:48:34.145Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=649948
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Warenannahme
-- Column: C_Order.M_Warehouse_ID
-- 2026-04-22T11:48:34.154Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541802
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Streckengeschäft
-- Column: C_Order.IsDropShip
-- 2026-04-22T11:48:34.162Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547189
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Streckengeschäft Partner
-- Column: C_Order.DropShip_BPartner_ID
-- 2026-04-22T11:48:34.169Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547190
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Tour
-- Column: C_Order.M_Tour_ID
-- 2026-04-22T11:48:34.177Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=565151
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 10 -> pricing.Preissystem
-- Column: C_Order.M_PricingSystem_ID
-- 2026-04-22T11:48:34.185Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541288
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 10 -> pricing.Währung
-- Column: C_Order.C_Currency_ID
-- 2026-04-22T11:48:34.193Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541289
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 20 -> dates.Belegstatus
-- Column: C_Order.DocStatus
-- 2026-04-22T11:48:34.200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541291
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 20 -> posted.Verbucht
-- Column: C_Order.Posted
-- 2026-04-22T11:48:34.206Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541259
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 20 -> org.Sektion
-- Column: C_Order.AD_Org_ID
-- 2026-04-22T11:48:34.214Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-04-22 11:48:34.214000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541286
;

-- Element: ProcessStatusColor_ID
-- 2026-04-22T11:49:57.889Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Prozess Status', PrintName='Prozess Status', Description='Prozessstatus der Bestellung: Grün: "Abgeschlossen", Gelb: "In Bearbeitung" oder Rot: "Nicht Bearbeitet".', Help='Berechnung des Prozessstatus:

- Grün "Abgeschlossen": Bestellung wurde vollständig geliefert und vollständig abgerechnet.
- Gelb "In Bearbeitung": Bestellung wurde teilweise geliefert oder teilweise abgerechnet.
- Rot "Nicht bearbeitet": Bestellung wurde nicht geliefert und nicht abgerechnet.',Updated=TO_TIMESTAMP('2026-04-22 11:49:57.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584777 AND AD_Language='de_DE'
;

-- 2026-04-22T11:49:57.891Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-22T11:50:00.046Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584777,'de_DE')
;

-- 2026-04-22T11:50:00.051Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584777,'de_DE')
;

-- Element: ProcessStatusColor_ID
-- 2026-04-22T11:50:05.144Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Prozess Status', PrintName='Prozess Status', Description='Prozessstatus der Bestellung: Grün: "Abgeschlossen", Gelb: "In Bearbeitung" oder Rot: "Nicht Bearbeitet".', Help='Berechnung des Prozessstatus:

- Grün "Abgeschlossen": Bestellung wurde vollständig geliefert und vollständig abgerechnet.
- Gelb "In Bearbeitung": Bestellung wurde teilweise geliefert oder teilweise abgerechnet.
- Rot "Nicht bearbeitet": Bestellung wurde nicht geliefert und nicht abgerechnet.',Updated=TO_TIMESTAMP('2026-04-22 11:50:05.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584777 AND AD_Language='de_CH'
;

-- 2026-04-22T11:50:05.145Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-22T11:50:05.747Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584777,'de_CH')
;

-- Element: ProcessStatusColor_ID
-- 2026-04-22T11:51:13.829Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-22 11:51:13.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584777 AND AD_Language='en_US'
;

-- 2026-04-22T11:51:13.832Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584777,'en_US')
;

-- Element: ProcessStatusColor_ID
-- 2026-04-22T12:10:18.469Z
UPDATE AD_Element_Trl SET Description='Process State of the Order: Green: "Completed", Yellow: "In Progress", Red: "Not Started"', Help='Calculation of the Process State:  - Green "Completed": Order is fully received and fully invoiced. - Yellow "In Progress": Order is partially received or partially invoiced. - Red "Not Started": Order is not received and not invoiced.',Updated=TO_TIMESTAMP('2026-04-22 12:10:18.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584777 AND AD_Language='en_US'
;

-- 2026-04-22T12:10:18.470Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-22T12:10:18.888Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584777,'en_US')
;

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Prozess Status
-- Column: C_Order.ProcessStatusColor_ID
-- 2026-04-22T12:16:01.369Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592405,777224,0,186,0,TO_TIMESTAMP('2026-04-22 12:16:01.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prozessstatus der Bestellung: Grün: "Abgeschlossen", Gelb: "In Bearbeitung" oder Rot: "Nicht Bearbeitet".',0,'C',0,'Berechnung des Prozessstatus:  - Grün "Abgeschlossen": Bestellung wurde vollständig geliefert und vollständig abgerechnet. - Gelb "In Bearbeitung": Bestellung wurde teilweise geliefert oder teilweise abgerechnet. - Rot "Nicht bearbeitet": Bestellung wurde nicht geliefert und nicht abgerechnet.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Prozess Status',0,0,800,0,1,1,TO_TIMESTAMP('2026-04-22 12:16:01.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-22T12:16:01.371Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-22T12:16:01.374Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584777)
;

-- 2026-04-22T12:16:01.378Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777224
;

-- 2026-04-22T12:16:01.382Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777224)
;

-- UI Column: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20
-- UI Element Group: posted
-- 2026-04-22T12:17:07.523Z
UPDATE AD_UI_ElementGroup SET IsActive='Y',Updated=TO_TIMESTAMP('2026-04-22 12:17:07.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543272
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> posted.Prozess Status
-- Column: C_Order.ProcessStatusColor_ID
-- 2026-04-22T12:17:36.365Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777224,0,186,543272,649949,'F',TO_TIMESTAMP('2026-04-22 12:17:36.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prozessstatus der Bestellung: Grün: "Abgeschlossen", Gelb: "In Bearbeitung" oder Rot: "Nicht Bearbeitet".','Berechnung des Prozessstatus:  - Grün "Abgeschlossen": Bestellung wurde vollständig geliefert und vollständig abgerechnet. - Gelb "In Bearbeitung": Bestellung wurde teilweise geliefert oder teilweise abgerechnet. - Rot "Nicht bearbeitet": Bestellung wurde nicht geliefert und nicht abgerechnet.','Y','N','N','N','N','N','N',0,'Prozess Status',30,0,0,TO_TIMESTAMP('2026-04-22 12:17:36.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> posted.Prozess Status
-- Column: C_Order.ProcessStatusColor_ID
-- 2026-04-22T12:18:25.284Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=649949
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> DocType.Referenz
-- Column: C_Order.POReference
-- 2026-04-22T12:18:25.311Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544768
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> Commission.C_BPartner_SalesRep_ID
-- Column: C_Order.C_BPartner_SalesRep_ID
-- 2026-04-22T12:18:25.320Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.319000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=562100
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abw. Lieferanschrift
-- Column: C_Order.IsDropShip
-- 2026-04-22T12:18:25.334Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544809
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 10 -> pricing.Incoterm Standort
-- Column: C_Order.IncotermLocation
-- 2026-04-22T12:18:25.345Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544771
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abw. Kunde
-- Column: C_Order.DropShip_BPartner_ID
-- 2026-04-22T12:18:25.355Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544810
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> Org und Lager.Organisation
-- Column: C_Order.AD_Org_ID
-- 2026-04-22T12:18:25.362Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Abladeort
-- Column: C_Order.HandOver_Location_ID
-- 2026-04-22T12:18:25.369Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548025
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 10 -> pricing.Preissystem
-- Column: C_Order.M_PricingSystem_ID
-- 2026-04-22T12:18:25.377Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000210
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 10 -> pricing.Währung
-- Column: C_Order.C_Currency_ID
-- 2026-04-22T12:18:25.384Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541299
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> Rest.Belegstatus
-- Column: C_Order.DocStatus
-- 2026-04-22T12:18:25.391Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000024
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> DocType.Auf Kommission bis
-- Column: C_Order.Commission_DateFrom
-- 2026-04-22T12:18:25.397Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-04-22 12:18:25.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637034
;
