-- Column: M_Delivery_Planning.M_InOut_ID
-- 2023-01-25T15:13:39.460Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585614,1025,0,30,542259,'M_InOut_ID',TO_TIMESTAMP('2023-01-25 17:13:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Material Shipment Document','D',0,10,'The Material Shipment / Receipt ','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Shipment/ Receipt',0,0,TO_TIMESTAMP('2023-01-25 17:13:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T15:13:39.466Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585614 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T15:13:39.498Z
/* DDL */  select update_Column_Translation_From_AD_Element(1025) 
;

-- 2023-01-25T15:13:40.848Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN M_InOut_ID NUMERIC(10)')
;

-- 2023-01-25T15:13:40.939Z
ALTER TABLE M_Delivery_Planning ADD CONSTRAINT MInOut_MDeliveryPlanning FOREIGN KEY (M_InOut_ID) REFERENCES public.M_InOut DEFERRABLE INITIALLY DEFERRED
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Shipment/ Receipt
-- Column: M_Delivery_Planning.M_InOut_ID
-- 2023-01-25T15:20:56.099Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585614,710534,0,546674,TO_TIMESTAMP('2023-01-25 17:20:55','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',10,'D','The Material Shipment / Receipt ','Y','N','N','N','N','N','N','N','Shipment/ Receipt',TO_TIMESTAMP('2023-01-25 17:20:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T15:20:56.101Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710534 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T15:20:56.103Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1025) 
;

-- 2023-01-25T15:20:56.119Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710534
;

-- 2023-01-25T15:20:56.120Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710534)
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10
-- UI Element Group: Receipt/Shipment
-- 2023-01-25T15:22:48.911Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546454,550256,TO_TIMESTAMP('2023-01-25 17:22:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','Receipt/Shipment',30,TO_TIMESTAMP('2023-01-25 17:22:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> Receipt/Shipment.Shipment/ Receipt
-- Column: M_Delivery_Planning.M_InOut_ID
-- 2023-01-25T15:23:05.395Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710534,0,546674,550256,614857,'F',TO_TIMESTAMP('2023-01-25 17:23:05','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','Y','N','N','Shipment/ Receipt',10,0,0,TO_TIMESTAMP('2023-01-25 17:23:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Shipment/ Receipt
-- Column: M_Delivery_Planning.M_InOut_ID
-- 2023-01-25T15:23:39.330Z
UPDATE AD_Field SET DisplayLogic='@M_InOut_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-25 17:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710534
;

-- 2023-01-25T15:27:52.114Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581958,0,'DeliveryStatus_Color_ID',TO_TIMESTAMP('2023-01-25 17:27:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Status','Delivery Status',TO_TIMESTAMP('2023-01-25 17:27:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T15:27:52.115Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581958 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Delivery_Planning.DeliveryStatus_Color_ID
-- 2023-01-25T15:28:15.915Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585615,581958,0,27,542259,'DeliveryStatus_Color_ID',TO_TIMESTAMP('2023-01-25 17:28:15','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Delivery Status',0,0,TO_TIMESTAMP('2023-01-25 17:28:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T15:28:15.917Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585615 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T15:28:15.921Z
/* DDL */  select update_Column_Translation_From_AD_Element(581958) 
;

-- 2023-01-25T15:28:21.652Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN DeliveryStatus_Color_ID NUMERIC(10)')
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Delivery Status
-- Column: M_Delivery_Planning.DeliveryStatus_Color_ID
-- 2023-01-25T15:28:36.508Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585615,710535,0,546674,TO_TIMESTAMP('2023-01-25 17:28:36','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Delivery Status',TO_TIMESTAMP('2023-01-25 17:28:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T15:28:36.509Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710535 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T15:28:36.511Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581958) 
;

-- 2023-01-25T15:28:36.514Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710535
;

-- 2023-01-25T15:28:36.516Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710535)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Delivery Status
-- Column: M_Delivery_Planning.DeliveryStatus_Color_ID
-- 2023-01-25T15:29:04.001Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-25 17:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710535
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> Receipt/Shipment.Delivery Status
-- Column: M_Delivery_Planning.DeliveryStatus_Color_ID
-- 2023-01-25T15:29:23.149Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710535,0,546674,550256,614858,'F',TO_TIMESTAMP('2023-01-25 17:29:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Delivery Status',20,0,0,TO_TIMESTAMP('2023-01-25 17:29:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> Receipt/Shipment.Shipment/ Receipt
-- Column: M_Delivery_Planning.M_InOut_ID
-- 2023-01-25T15:29:35.989Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-01-25 17:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614857
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> Receipt/Shipment.Delivery Status
-- Column: M_Delivery_Planning.DeliveryStatus_Color_ID
-- 2023-01-25T15:29:38.202Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-01-25 17:29:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614858
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> Receipt/Shipment.Delivery Status
-- Column: M_Delivery_Planning.DeliveryStatus_Color_ID
-- 2023-01-25T16:51:53.941Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-01-25 18:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614858
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Created
-- Column: M_Delivery_Planning.Created
-- 2023-01-25T16:51:53.951Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-01-25 18:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613910
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Type
-- Column: M_Delivery_Planning.M_Delivery_Planning_Type
-- 2023-01-25T16:51:53.960Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-01-25 18:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613482
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.B2B
-- Column: M_Delivery_Planning.IsB2B
-- 2023-01-25T16:51:53.966Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-25 18:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613483
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Section Code
-- Column: M_Delivery_Planning.M_SectionCode_ID
-- 2023-01-25T16:51:53.974Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-25 18:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613484
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Incoterms
-- Column: M_Delivery_Planning.C_Incoterms_ID
-- 2023-01-25T16:51:53.980Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-01-25 18:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613485
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Incoterm Location
-- Column: M_Delivery_Planning.IncotermLocation
-- 2023-01-25T16:51:53.987Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-01-25 18:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614583
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Document No
-- Column: M_Delivery_Planning.OrderDocumentNo
-- 2023-01-25T16:51:53.994Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-01-25 18:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613486
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Reference
-- Column: M_Delivery_Planning.POReference
-- 2023-01-25T16:51:54Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613487
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Partner Name
-- Column: M_Delivery_Planning.BPartnerName
-- 2023-01-25T16:51:54.008Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613488
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Ship-to location
-- Column: M_Delivery_Planning.ShipToLocation_Name
-- 2023-01-25T16:51:54.013Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613490
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Name
-- Column: M_Delivery_Planning.ProductName
-- 2023-01-25T16:51:54.020Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613491
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Value
-- Column: M_Delivery_Planning.ProductValue
-- 2023-01-25T16:51:54.025Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613492
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Grade
-- Column: M_Delivery_Planning.Grade
-- 2023-01-25T16:51:54.031Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613493
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Qty Ordered
-- Column: M_Delivery_Planning.QtyOrdered
-- 2023-01-25T16:51:54.037Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613494
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Qty Total Open
-- Column: M_Delivery_Planning.QtyTotalOpen
-- 2023-01-25T16:51:54.042Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613495
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Warehouse Name
-- Column: M_Delivery_Planning.WarehouseName
-- 2023-01-25T16:51:54.048Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613496
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Status
-- Column: M_Delivery_Planning.OrderStatus
-- 2023-01-25T16:51:54.053Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613498
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Delivery Date
-- Column: M_Delivery_Planning.PlannedDeliveryDate
-- 2023-01-25T16:51:54.059Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613499
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Delivery Date
-- Column: M_Delivery_Planning.ActualDeliveryDate
-- 2023-01-25T16:51:54.064Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613500
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Delivery Time
-- Column: M_Delivery_Planning.DeliveryTime
-- 2023-01-25T16:51:54.070Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614580
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Delivery Instruction
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-25T16:51:54.076Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614775
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Release No
-- Column: M_Delivery_Planning.ReleaseNo
-- 2023-01-25T16:51:54.081Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613501
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Loading Date
-- Column: M_Delivery_Planning.PlannedLoadingDate
-- 2023-01-25T16:51:54.087Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613502
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Loading Date
-- Column: M_Delivery_Planning.ActualLoadingDate
-- 2023-01-25T16:51:54.092Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613503
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Loading Time
-- Column: M_Delivery_Planning.LoadingTime
-- 2023-01-25T16:51:54.098Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614579
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Load Qty
-- Column: M_Delivery_Planning.ActualLoadQty
-- 2023-01-25T16:51:54.103Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613504
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Delivered Qty
-- Column: M_Delivery_Planning.ActualDeliveredQty
-- 2023-01-25T16:51:54.109Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613505
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Planned Loaded Quantity
-- Column: M_Delivery_Planning.PlannedLoadedQuantity
-- 2023-01-25T16:51:54.114Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613647
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Planned Discharge Quantity
-- Column: M_Delivery_Planning.PlannedDischargeQuantity
-- 2023-01-25T16:51:54.120Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613648
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Discharge Quantity
-- Column: M_Delivery_Planning.ActualDischargeQuantity
-- 2023-01-25T16:51:54.125Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613649
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Batch
-- Column: M_Delivery_Planning.Batch
-- 2023-01-25T16:51:54.130Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=320,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613506
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Country Of Origin
-- Column: M_Delivery_Planning.OriginCountry
-- 2023-01-25T16:51:54.136Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613507
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Forwarder
-- Column: M_Delivery_Planning.M_Shipper_ID
-- 2023-01-25T16:51:54.141Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=340,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613509
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Details
-- Column: M_Delivery_Planning.TransportDetails
-- 2023-01-25T16:51:54.147Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=350,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613510
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Way Bill No
-- Column: M_Delivery_Planning.WayBillNo
-- 2023-01-25T16:51:54.152Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=360,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613511
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.UOM
-- Column: M_Delivery_Planning.C_UOM_ID
-- 2023-01-25T16:51:54.158Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=370,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613566
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Verarbeitet
-- Column: M_Delivery_Planning.Processed
-- 2023-01-25T16:51:54.164Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=380,Updated=TO_TIMESTAMP('2023-01-25 18:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613915
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> Receipt/Shipment.Delivery Status
-- Column: M_Delivery_Planning.DeliveryStatus_Color_ID
-- 2023-01-25T16:55:11.760Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-25 18:55:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614858
;

