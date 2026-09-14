








-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-20T15:28:21.946Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585602,540089,0,30,542259,'M_ShipperTransportation_ID',TO_TIMESTAMP('2023-01-20 17:28:21','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Transport Auftrag',0,0,TO_TIMESTAMP('2023-01-20 17:28:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-20T15:28:21.951Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585602 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-20T15:28:21.955Z
/* DDL */  select update_Column_Translation_From_AD_Element(540089) 
;

-- 2023-01-20T15:28:22.680Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN M_ShipperTransportation_ID NUMERIC(10)')
;

-- 2023-01-20T15:28:22.747Z
ALTER TABLE M_Delivery_Planning ADD CONSTRAINT MShipperTransportation_MDeliveryPlanning FOREIGN KEY (M_ShipperTransportation_ID) REFERENCES public.M_ShipperTransportation DEFERRABLE INITIALLY DEFERRED
;

-- Field: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> Transport Auftrag
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-20T15:28:46.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585602,710345,0,546674,TO_TIMESTAMP('2023-01-20 17:28:46','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Transport Auftrag',TO_TIMESTAMP('2023-01-20 17:28:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-20T15:28:46.278Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710345 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-20T15:28:46.281Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540089) 
;

-- 2023-01-20T15:28:46.296Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710345
;

-- 2023-01-20T15:28:46.298Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710345)
;

-- Field: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> Delivery Instruction
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-20T15:29:17.258Z
UPDATE AD_Field SET AD_Name_ID=581903, Description=NULL, Help=NULL, Name='Delivery Instruction',Updated=TO_TIMESTAMP('2023-01-20 17:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710345
;

-- 2023-01-20T15:29:17.260Z
UPDATE AD_Field_Trl trl SET Name='Delivery Instruction' WHERE AD_Field_ID=710345 AND AD_Language='de_DE'
;

-- 2023-01-20T15:29:17.262Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581903) 
;

-- 2023-01-20T15:29:17.266Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710345
;

-- 2023-01-20T15:29:17.267Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710345)
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 10 -> links.Delivery Instruction
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-20T15:29:47.049Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710345,0,546674,550033,614775,'F',TO_TIMESTAMP('2023-01-20 17:29:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Delivery Instruction',140,0,0,TO_TIMESTAMP('2023-01-20 17:29:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 10 -> links.Delivery Instruction
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-20T15:30:02.074Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614775
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 10 -> default.Release No
-- Column: M_Delivery_Planning.ReleaseNo
-- 2023-01-20T15:30:02.082Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613501
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 20 -> dates.Planned Loading Date
-- Column: M_Delivery_Planning.PlannedLoadingDate
-- 2023-01-20T15:30:02.088Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613502
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 20 -> dates.Actual Loading Date
-- Column: M_Delivery_Planning.ActualLoadingDate
-- 2023-01-20T15:30:02.095Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613503
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 20 -> dates.Loading Time
-- Column: M_Delivery_Planning.LoadingTime
-- 2023-01-20T15:30:02.102Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614579
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 20 -> qtys.Actual Load Qty
-- Column: M_Delivery_Planning.ActualLoadQty
-- 2023-01-20T15:30:02.108Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613504
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 20 -> qtys.Actual Delivered Qty
-- Column: M_Delivery_Planning.ActualDeliveredQty
-- 2023-01-20T15:30:02.114Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613505
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 20 -> qtys.Planned Loaded Quantity
-- Column: M_Delivery_Planning.PlannedLoadedQuantity
-- 2023-01-20T15:30:02.121Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613647
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 20 -> qtys.Planned Discharge Quantity
-- Column: M_Delivery_Planning.PlannedDischargeQuantity
-- 2023-01-20T15:30:02.127Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613648
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 20 -> qtys.Actual Discharge Quantity
-- Column: M_Delivery_Planning.ActualDischargeQuantity
-- 2023-01-20T15:30:02.133Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613649
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 10 -> default.Batch
-- Column: M_Delivery_Planning.Batch
-- 2023-01-20T15:30:02.139Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613506
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 10 -> default.Country Of Origin
-- Column: M_Delivery_Planning.OriginCountry
-- 2023-01-20T15:30:02.144Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=320,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613507
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 10 -> default.Forwarder
-- Column: M_Delivery_Planning.M_Shipper_ID
-- 2023-01-20T15:30:02.150Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613509
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 10 -> default.Transport Details
-- Column: M_Delivery_Planning.TransportDetails
-- 2023-01-20T15:30:02.155Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=340,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613510
;

-- UI Element: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> main -> 10 -> default.Way Bill No
-- Column: M_Delivery_Planning.WayBillNo
-- 2023-01-20T15:30:02.161Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=350,Updated=TO_TIMESTAMP('2023-01-20 17:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613511
;



-- 2023-01-20T16:37:04.089Z
INSERT INTO t_alter_column values('m_delivery_planning','M_ShipperTransportation_ID','NUMERIC(10)',null,null)
;

-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-20T16:40:48.936Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-20 18:40:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585602
;

-- 2023-01-20T16:40:49.677Z
INSERT INTO t_alter_column values('m_delivery_planning','M_ShipperTransportation_ID','NUMERIC(10)',null,null)
;




