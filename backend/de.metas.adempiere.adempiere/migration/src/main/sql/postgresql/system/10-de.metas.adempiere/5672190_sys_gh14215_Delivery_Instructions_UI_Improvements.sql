-- Table: M_Delivery_Planning_Delivery_Instructions_V
-- 2023-01-17T15:03:47.511Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-01-17 17:03:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542280
;

-- Tab: Lieferplanung(541632,D) -> Delivery Instructions for Delivery Planning
-- Table: M_Delivery_Planning_Delivery_Instructions_V
-- 2023-01-17T15:04:33.838Z
UPDATE AD_Tab SET IsRefreshAllOnActivate='Y',Updated=TO_TIMESTAMP('2023-01-17 17:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546737
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Client
-- Column: M_ShipperTransportation.AD_Client_ID
-- 2023-01-17T15:18:47.543Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710075
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Organisation
-- Column: M_ShipperTransportation.AD_Org_ID
-- 2023-01-17T15:18:48.415Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710076
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Assign anonymously picked HUs
-- Column: M_ShipperTransportation.AssignAnonymouslyPickedHUs
-- 2023-01-17T15:18:48.907Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710111
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Delivery Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Delivery_ID
-- 2023-01-17T15:18:49.289Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710115
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Loading Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Loading_ID
-- 2023-01-17T15:18:50.329Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710112
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Loading Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Loading_ID
-- 2023-01-17T15:18:53.486Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710112
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Delivery Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Delivery_ID
-- 2023-01-17T15:18:53.810Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710115
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Assign anonymously picked HUs
-- Column: M_ShipperTransportation.AssignAnonymouslyPickedHUs
-- 2023-01-17T15:18:54.099Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710111
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Organisation
-- Column: M_ShipperTransportation.AD_Org_ID
-- 2023-01-17T15:18:54.424Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710076
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Client
-- Column: M_ShipperTransportation.AD_Client_ID
-- 2023-01-17T15:18:55.856Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710075
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Transporteur
-- Column: M_ShipperTransportation.Shipper_BPartner_ID
-- 2023-01-17T15:21:40.701Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614584
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Ladeort
-- Column: M_ShipperTransportation.Shipper_Location_ID
-- 2023-01-17T15:21:42.808Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614585
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Incoterms
-- Column: M_ShipperTransportation.C_Incoterms_ID
-- 2023-01-17T15:22:31.180Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550198, SeqNo=40,Updated=TO_TIMESTAMP('2023-01-17 17:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614620
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Incoterm Location
-- Column: M_ShipperTransportation.IncotermLocation
-- 2023-01-17T15:22:43.369Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550198, SeqNo=50,Updated=TO_TIMESTAMP('2023-01-17 17:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614621
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10
-- UI Element Group: incoterm
-- 2023-01-17T15:22:50.157Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550207
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10
-- UI Element Group: description
-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> description.Tour
-- Column: M_ShipperTransportation.M_Tour_ID
-- 2023-01-17T15:23:13.294Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614588
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> description.Beschreibung
-- Column: M_ShipperTransportation.Description
-- 2023-01-17T15:23:13.306Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614587
;

-- 2023-01-17T15:23:13.311Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550199
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Means of Transportation
-- Column: M_ShipperTransportation.M_MeansOfTransportation_ID
-- 2023-01-17T15:23:34.645Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710120,0,546732,550198,614701,'F',TO_TIMESTAMP('2023-01-17 17:23:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Means of Transportation',60,0,0,TO_TIMESTAMP('2023-01-17 17:23:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> delivery details.Means of Transportation
-- Column: M_ShipperTransportation.M_MeansOfTransportation_ID
-- 2023-01-17T15:24:35.245Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614622
;




-- Value: M_Delivery_Planning_GenerateDeliveryInstruction
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateDeliveryInstruction
-- 2023-01-17T15:31:17.693Z
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2023-01-17 17:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585176
;

