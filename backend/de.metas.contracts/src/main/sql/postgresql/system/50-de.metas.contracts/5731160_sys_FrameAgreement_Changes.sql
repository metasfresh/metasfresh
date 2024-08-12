-- Run mode: WEBUI

-- 2024-08-08T14:42:37.717Z
UPDATE C_DocType SET IsActive='Y',Updated=TO_TIMESTAMP('2024-08-08 17:42:37.717','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541010
;



-- 2024-08-08T16:03:36.187Z
UPDATE C_DocType SET Name='Abrufauftrag_OLD',Updated=TO_TIMESTAMP('2024-08-08 19:03:36.187','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541028
;

-- 2024-08-08T16:03:36.202Z
UPDATE C_DocType_Trl trl SET Name='Abrufauftrag_OLD' WHERE C_DocType_ID=541028 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- 2024-08-08T16:03:38.707Z
UPDATE C_DocType SET IsActive='N',Updated=TO_TIMESTAMP('2024-08-08 19:03:38.706','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541028
;




-- 2024-08-08T16:06:56.972Z
UPDATE C_DocType_Trl SET Name='Abrufauftrag',Updated=TO_TIMESTAMP('2024-08-08 19:06:56.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541062
;

-- 2024-08-08T16:06:57.634Z
UPDATE C_DocType_Trl SET PrintName='Abrufauftrag',Updated=TO_TIMESTAMP('2024-08-08 19:06:57.634','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541062
;




-- 2024-08-08T16:07:12.954Z
UPDATE C_DocType_Trl SET Name='Abrufauftrag',Updated=TO_TIMESTAMP('2024-08-08 19:07:12.954','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541062
;

-- 2024-08-08T16:07:12.960Z
UPDATE C_DocType SET Name='Abrufauftrag' WHERE C_DocType_ID=541062
;





-- 2024-08-08T16:09:36.972Z
UPDATE C_DocType SET DocNoSequence_ID=545479,Updated=TO_TIMESTAMP('2024-08-08 19:09:36.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541062
;



-- 2024-08-08T16:10:18.730Z
UPDATE C_DocType SET DocSubType='CAO',Updated=TO_TIMESTAMP('2024-08-08 19:10:18.73','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541062
;



-- 2024-08-08T16:19:41.891Z
UPDATE C_DocType SET DocNoSequence_ID=545465,Updated=TO_TIMESTAMP('2024-08-08 19:19:41.891','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541034
;



-- 2024-08-09T14:01:25.490Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000011,Updated=TO_TIMESTAMP('2024-08-09 17:01:25.478','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541057
;



-- Run mode: SWING_CLIENT

-- Reference: Type_Conditions
-- Value: CallOrder
-- ValueName: CallOrder
-- 2024-08-08T16:32:04.795Z
UPDATE AD_Ref_List SET Name='Rahmenvertrag / Abrufvertrag',Updated=TO_TIMESTAMP('2024-08-08 19:32:04.795','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543134
;

-- 2024-08-08T16:32:04.838Z
UPDATE AD_Ref_List_Trl trl SET Name='Rahmenvertrag / Abrufvertrag' WHERE AD_Ref_List_ID=543134 AND AD_Language='de_DE'
;

-- Reference Item: Type_Conditions -> CallOrder_CallOrder
-- 2024-08-08T16:32:13.014Z
UPDATE AD_Ref_List_Trl SET Name='Rahmenvertrag / Abrufvertrag',Updated=TO_TIMESTAMP('2024-08-08 19:32:13.014','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543134
;











-- Field: Auftrag(143,D) -> Auftragsposition(187,D) -> Rahmenvertrag
-- Column: C_OrderLine.C_Flatrate_Term_ID
-- 2024-08-08T16:34:59.192Z
UPDATE AD_Field SET AD_Name_ID=578783, Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz', Name='Rahmenvertrag',Updated=TO_TIMESTAMP('2024-08-08 19:34:59.192','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=680655
;

-- 2024-08-08T16:34:59.194Z
UPDATE AD_Field_Trl trl SET Description='Rahmenvertrag Referenz',Help='Rahmenvertrag Referenz',Name='Rahmenvertrag' WHERE AD_Field_ID=680655 AND AD_Language='de_DE'
;

-- 2024-08-08T16:34:59.233Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578783)
;

-- 2024-08-08T16:34:59.250Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=680655
;

-- 2024-08-08T16:34:59.251Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(680655)
;






-- Field: Bestellung(181,D) -> Bestellposition(293,D) -> Rahmenvertrag
-- Column: C_OrderLine.C_Flatrate_Term_ID
-- 2024-08-08T16:42:59.655Z
UPDATE AD_Field SET AD_Name_ID=578783, Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz', Name='Rahmenvertrag',Updated=TO_TIMESTAMP('2024-08-08 19:42:59.655','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=691395
;

-- 2024-08-08T16:42:59.656Z
UPDATE AD_Field_Trl trl SET Description='Rahmenvertrag Referenz',Help='Rahmenvertrag Referenz',Name='Rahmenvertrag' WHERE AD_Field_ID=691395 AND AD_Language='de_DE'
;

-- 2024-08-08T16:42:59.658Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578783)
;

-- 2024-08-08T16:42:59.661Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691395
;

-- 2024-08-08T16:42:59.662Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691395)
;






-- Field: Abrufauftrag Übersicht(541430,de.metas.contracts) -> Abrufauftrag Übersicht(545426,de.metas.contracts) -> Rahmenvertrag
-- Column: C_CallOrderSummary.C_Flatrate_Term_ID
-- 2024-08-08T16:45:25.735Z
UPDATE AD_Field SET AD_Name_ID=578783, Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz', Name='Rahmenvertrag',Updated=TO_TIMESTAMP('2024-08-08 19:45:25.734','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=680620
;

-- 2024-08-08T16:45:25.740Z
UPDATE AD_Field_Trl trl SET Description='Rahmenvertrag Referenz',Help='Rahmenvertrag Referenz',Name='Rahmenvertrag' WHERE AD_Field_ID=680620 AND AD_Language='de_DE'
;

-- 2024-08-08T16:45:25.748Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578783)
;

-- 2024-08-08T16:45:25.766Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=680620
;

-- 2024-08-08T16:45:25.770Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(680620)
;




-- Run mode: SWING_CLIENT

-- Name: CallOrderContractForPartner
-- 2024-08-09T11:14:39.462Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540686,'C_Flatrate_Term.Bill_BPartner_ID=@C_BPartner_ID@ AND C_Flatrate_Term.Type_Conditions = ''CallOrder''',TO_TIMESTAMP('2024-08-09 14:14:39.192','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','CallOrderContractForPartner','S',TO_TIMESTAMP('2024-08-09 14:14:39.192','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: CallOrderContractForPartner
-- 2024-08-09T11:38:30.303Z
UPDATE AD_Val_Rule SET Code='C_Flatrate_Term.Bill_BPartner_ID=@C_BPartner_ID@ AND C_Flatrate_Term.Type_Conditions = ''CallOrder'' AND (C_Flatrate_Term.MasterEndDate > @DatePromised@ OR C_Flatrate_Term.MasterEndDate IS NULL)',Updated=TO_TIMESTAMP('2024-08-09 14:38:30.301','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540686
;

-- Column: C_OrderLine.C_Flatrate_Term_ID
-- 2024-08-09T11:39:37.215Z
UPDATE AD_Column SET AD_Val_Rule_ID=540686,Updated=TO_TIMESTAMP('2024-08-09 14:39:37.215','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=579333
;

-- Column: C_Flatrate_Term.MasterStartDate
-- 2024-08-09T13:35:12.363Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2024-08-09 16:35:12.363','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=557139
;

-- Column: C_Flatrate_Term.MasterStartDate
-- 2024-08-09T13:35:31.463Z
UPDATE AD_Column SET AD_Column_ID=557139, AD_Element_ID=543413, ColumnName='MasterStartDate', Created=TO_TIMESTAMP('2017-09-11 16:21:41.0','YYYY-MM-DD HH24:MI:SS.US'), IsIdentifier='Y', Name='Vertragsbeginn', SeqNo=40, Updated=TO_TIMESTAMP('2024-08-09 16:35:12.363','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=557139
;

-- Column: C_Flatrate_Term.MasterEndDate
-- 2024-08-09T13:35:33.771Z
UPDATE AD_Column SET SeqNo=50,Updated=TO_TIMESTAMP('2024-08-09 16:35:33.77','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=557140
;


-- Column: C_Flatrate_Term.MasterEndDate
-- 2024-08-09T13:47:10.721Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2024-08-09 16:47:10.721','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=557140
;



-- Name: CallOrderContractForPartner
-- 2024-08-09T13:42:28.893Z
UPDATE AD_Val_Rule SET Code='C_Flatrate_Term.Bill_BPartner_ID=@C_BPartner_ID@ AND C_Flatrate_Term.Type_Conditions = ''CallOrder'' AND (C_Flatrate_Term.MasterEndDate > ''@DatePromised@'' OR C_Flatrate_Term.MasterEndDate IS NULL)',Updated=TO_TIMESTAMP('2024-08-09 16:42:28.892','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540686
;




-- Name: CallOrderContractForPartner
-- 2024-08-09T14:59:59.822Z
UPDATE AD_Val_Rule SET Code='C_Flatrate_Term.Bill_BPartner_ID=@C_BPartner_ID@ AND C_Flatrate_Term.Type_Conditions = ''CallOrder'' AND (C_Flatrate_Term.MasterEndDate > ''@DatePromised@'' OR C_Flatrate_Term.MasterEndDate IS NULL) AND (C_Flatrate_Term.M_Product_ID = @M_Product_ID/-1@ OR C_Flatrate_Term.M_Product_ID IS NULL)',Updated=TO_TIMESTAMP('2024-08-09 17:59:59.82','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540686
;






-- Value: C_OrderLine_OverlappingTerms
-- 2024-08-09T15:17:06.802Z
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=545147
;

-- 2024-08-09T15:17:06.808Z
DELETE FROM AD_Message WHERE AD_Message_ID=545147
;





-- Name: OngoingCallOrderContractForPartnerAndProduct
-- 2024-08-09T15:23:10.544Z
UPDATE AD_Val_Rule SET Name='OngoingCallOrderContractForPartnerAndProduct',Updated=TO_TIMESTAMP('2024-08-09 18:23:10.542','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540686
;

-- Name: OngoingCallOrderContractForPartnerAndProduct
-- 2024-08-09T15:25:36.094Z
UPDATE AD_Val_Rule SET Code='C_Flatrate_Term.Bill_BPartner_ID=@C_BPartner_ID@ AND C_Flatrate_Term.Type_Conditions = ''CallOrder'' AND (C_Flatrate_Term.MasterEndDate > ''@DatePromised@'' OR C_Flatrate_Term.MasterEndDate IS NULL) AND (C_Flatrate_Term.M_Product_ID = @M_Product_ID/-1@ OR C_Flatrate_Term.M_Product_ID IS NULL) AND EXISTS (SELECT 1 from C_Order o WHERE o.IsSOTrx = @IsSOTrx@ AND o.C_Order_ID = C_Flatrate_Term.C_Order_Term_ID)',Updated=TO_TIMESTAMP('2024-08-09 18:25:36.092','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540686
;

-- Name: OngoingCallOrderContractForPartnerAndProduct
-- 2024-08-09T15:29:29.407Z
UPDATE AD_Val_Rule SET Code='C_Flatrate_Term.Bill_BPartner_ID=@C_BPartner_ID@ AND C_Flatrate_Term.Type_Conditions = ''CallOrder'' AND (C_Flatrate_Term.MasterEndDate > ''@DatePromised@'' OR C_Flatrate_Term.MasterEndDate IS NULL) AND (C_Flatrate_Term.M_Product_ID = @M_Product_ID/-1@ OR C_Flatrate_Term.M_Product_ID IS NULL) AND EXISTS (SELECT 1 from C_Order o WHERE o.IsSOTrx = ''@IsSOTrx@'' AND o.C_Order_ID = C_Flatrate_Term.C_Order_Term_ID)',Updated=TO_TIMESTAMP('2024-08-09 18:29:29.405','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540686
;




-- Column: C_Order.C_FrameAgreement_Order_ID
-- 2024-08-12T13:57:03.372Z
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2024-08-12 16:57:03.372','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=573002
;







-- 2024-08-12T14:43:46.053Z
UPDATE C_DocType_Trl SET Name='Call Order SO',Updated=TO_TIMESTAMP('2024-08-12 17:43:46.004','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541062
;

-- 2024-08-12T14:43:48.116Z
UPDATE C_DocType_Trl SET Name='Call Order SO',Updated=TO_TIMESTAMP('2024-08-12 17:43:48.116','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_FR' AND C_DocType_ID=541062
;

-- 2024-08-12T14:43:49.891Z
UPDATE C_DocType_Trl SET Name='Call Order SO',Updated=TO_TIMESTAMP('2024-08-12 17:43:49.891','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541062
;

-- 2024-08-12T14:43:51.884Z
UPDATE C_DocType_Trl SET Name='Call Order SO',Updated=TO_TIMESTAMP('2024-08-12 17:43:51.884','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND C_DocType_ID=541062
;

-- 2024-08-12T14:43:53.442Z
UPDATE C_DocType_Trl SET PrintName='Call Order SO',Updated=TO_TIMESTAMP('2024-08-12 17:43:53.442','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541062
;

-- 2024-08-12T14:43:55.494Z
UPDATE C_DocType_Trl SET PrintName='Call Order SO',Updated=TO_TIMESTAMP('2024-08-12 17:43:55.494','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_FR' AND C_DocType_ID=541062
;

-- 2024-08-12T14:43:57.746Z
UPDATE C_DocType_Trl SET PrintName='Call Order SO',Updated=TO_TIMESTAMP('2024-08-12 17:43:57.746','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541062
;

-- 2024-08-12T14:43:59.125Z
UPDATE C_DocType_Trl SET PrintName='Call Order SO',Updated=TO_TIMESTAMP('2024-08-12 17:43:59.125','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND C_DocType_ID=541062
;





-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Produkt
-- Column: C_Flatrate_Term.M_Product_ID
-- 2024-08-12T14:57:10.298Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''X''@=''Subscr''|@Type_Conditions/''X''@=''QualityBsd''|@Type_Conditions/''X''@=''Procuremnt''|@Type_Conditions/''X''@=''Refund''|@Type_Conditions/''X''@=''Commission''|@Type_Conditions/''X''@=''MediatedCommission''|@Type_Conditions/''X''@=''MarginCommission''|@Type_Conditions/''X''@=''ModularContract''|@Type_Conditions/''X''@=''InterimInvoice''|@Type_Conditions/''X''@=''CallOrder''',Updated=TO_TIMESTAMP('2024-08-12 17:57:10.298','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=559779
;





-- Name: OngoingCallOrderContractForPartnerAndProduct
-- 2024-08-12T15:39:52.281Z
UPDATE AD_Val_Rule SET Code='C_Flatrate_Term.Bill_BPartner_ID=@C_BPartner_ID@ AND C_Flatrate_Term.Type_Conditions = ''CallOrder'' AND (C_Flatrate_Term.MasterEndDate > ''@DatePromised@'' OR C_Flatrate_Term.MasterEndDate IS NULL) AND (C_Flatrate_Term.MasterStartDate <= ''@DatePromised@'' OR C_Flatrate_Term.MasterStartDate IS NULL) AND (C_Flatrate_Term.M_Product_ID = @M_Product_ID/-1@ OR C_Flatrate_Term.M_Product_ID IS NULL) AND EXISTS (SELECT 1 from C_Order o WHERE o.IsSOTrx = ''@IsSOTrx@'' AND o.C_Order_ID = C_Flatrate_Term.C_Order_Term_ID)',Updated=TO_TIMESTAMP('2024-08-12 18:39:52.271','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540686
;




-- 2024-08-12T16:31:09.792Z
UPDATE C_DocType_Trl SET Name='Frame Agreement PO',Updated=TO_TIMESTAMP('2024-08-12 19:31:09.791','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541057
;

-- 2024-08-12T16:31:21.378Z
UPDATE C_DocType_Trl SET Name='Frame Agreement PO',Updated=TO_TIMESTAMP('2024-08-12 19:31:21.377','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541057
;

-- 2024-08-12T16:31:22.793Z
UPDATE C_DocType_Trl SET Name='Frame Agreement PO',Updated=TO_TIMESTAMP('2024-08-12 19:31:22.793','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND C_DocType_ID=541057
;

-- 2024-08-12T16:31:24.661Z
UPDATE C_DocType_Trl SET PrintName='Frame Agreement PO',Updated=TO_TIMESTAMP('2024-08-12 19:31:24.661','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND C_DocType_ID=541057
;

-- 2024-08-12T16:31:26.658Z
UPDATE C_DocType_Trl SET PrintName='Frame Agreement PO',Updated=TO_TIMESTAMP('2024-08-12 19:31:26.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541057
;

-- 2024-08-12T16:31:28.219Z
UPDATE C_DocType_Trl SET PrintName='Frame Agreement PO',Updated=TO_TIMESTAMP('2024-08-12 19:31:28.219','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541057
;

