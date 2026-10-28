-- 2023-01-31T15:51:18.632Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710672
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Tatsächlich verladene Menge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualLoadQty
-- 2023-01-31T15:51:18.636Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710672
;

-- 2023-01-31T15:51:18.640Z
DELETE FROM AD_Field WHERE AD_Field_ID=710672
;

-- 2023-01-31T15:51:24.568Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710673
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Tatsächlich abgeladene Menge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualDischargeQuantity
-- 2023-01-31T15:51:24.572Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710673
;

-- 2023-01-31T15:51:24.576Z
DELETE FROM AD_Field WHERE AD_Field_ID=710673
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualDischargeQuantity
-- 2023-01-31T15:51:37.086Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585641
;

-- 2023-01-31T15:51:37.097Z
DELETE FROM AD_Column WHERE AD_Column_ID=585641
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualLoadQty
-- 2023-01-31T15:51:40.539Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585640
;

-- 2023-01-31T15:51:40.543Z
DELETE FROM AD_Column WHERE AD_Column_ID=585640
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.DocStatus
-- 2023-01-31T15:53:10.421Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=131,Updated=TO_TIMESTAMP('2023-01-31 17:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585631
;

-- Reference: M_ShipperTransportation target for M_Delivery_Planning
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-31T16:01:19.218Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS           (SELECT 1            from M_ShipperTransportation di                     JOIN M_Delivery_Planning dp on di.M_Delivery_Planning_id = dp.M_Delivery_Planning_id            where dp.M_Delivery_Planning_ID = @M_Delivery_Planning_ID / -1@              AND M_ShipperTransportation.M_ShipperTransportation_ID = di.M_ShipperTransportation_ID)',Updated=TO_TIMESTAMP('2023-01-31 18:01:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541708
;

-- 2023-01-31T16:24:27.986Z
UPDATE AD_RelationType SET IsTableRecordIdTarget='Y',Updated=TO_TIMESTAMP('2023-01-31 18:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540381
;

-- 2023-01-31T16:25:45.299Z
UPDATE AD_RelationType SET IsTableRecordIdTarget='N',Updated=TO_TIMESTAMP('2023-01-31 18:25:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540381
;

-- 2023-01-31T16:26:24.526Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541707,540382,TO_TIMESTAMP('2023-01-31 18:26:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_Delivery_Instruction ->M_Delivery_Planning ',TO_TIMESTAMP('2023-01-31 18:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Delivery Instruction source
-- 2023-01-31T16:28:53.083Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541710,TO_TIMESTAMP('2023-01-31 18:28:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Delivery Instruction source',TO_TIMESTAMP('2023-01-31 18:28:52','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-01-31T16:28:53.085Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541710 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Delivery Instruction source
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-31T16:31:18.307Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,540426,0,541710,540030,541657,TO_TIMESTAMP('2023-01-31 18:31:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Y',TO_TIMESTAMP('2023-01-31 18:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T16:31:43.004Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541710, IsTableRecordIdTarget='Y',Updated=TO_TIMESTAMP('2023-01-31 18:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540382
;

-- 2023-01-31T16:32:10.828Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=NULL, AD_Reference_Target_ID=541710,Updated=TO_TIMESTAMP('2023-01-31 18:32:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540382
;

-- 2023-01-31T16:39:01.117Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541710, AD_Reference_Target_ID=541707, IsTableRecordIdTarget='N',Updated=TO_TIMESTAMP('2023-01-31 18:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540382
;

-- Name: M_Delivery_Planning target
-- 2023-01-31T16:40:14.456Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541711,TO_TIMESTAMP('2023-01-31 18:40:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Delivery_Planning target',TO_TIMESTAMP('2023-01-31 18:40:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-01-31T16:40:14.457Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541711 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Delivery_Planning target
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-01-31T16:41:33.900Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,584986,0,541711,542259,TO_TIMESTAMP('2023-01-31 18:41:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-01-31 18:41:33','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS           (SELECT 1            from M_Delivery_Planning di                     JOIN m_shippertransportation dp on di.M_Delivery_Planning_id = dp.M_Delivery_Planning_id            where di.M_Delivery_Planning_ID = @M_Delivery_Planning_ID / -1@              AND M_Delivery_Planning.M_ShipperTransportation_ID = di.M_ShipperTransportation_ID)')
;

-- 2023-01-31T16:41:53.564Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541711,Updated=TO_TIMESTAMP('2023-01-31 18:41:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540382
;

-- Reference: M_Delivery_Planning target
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-01-31T16:42:59.270Z
UPDATE AD_Ref_Table SET AD_Window_ID=541632,Updated=TO_TIMESTAMP('2023-01-31 18:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541711
;

-- Reference: M_Delivery_Planning target
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-01-31T16:43:37.046Z
UPDATE AD_Ref_Table SET WhereClause=' EXISTS           (SELECT 1            from M_Delivery_Planning di                     JOIN m_shippertransportation dp on di.M_Delivery_Planning_id = dp.M_Delivery_Planning_id            where dp.M_ShipperTransportation_ID = @M_ShipperTransportation_ID / -1@              AND M_Delivery_Planning.M_ShipperTransportation_ID = di.M_ShipperTransportation_ID)',Updated=TO_TIMESTAMP('2023-01-31 18:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541711
;

-- Reference: M_Delivery_Planning target
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-01-31T16:48:02.366Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS           (SELECT 1            from M_Delivery_Planning di                     JOIN m_shippertransportation dp on di.M_Delivery_Planning_id = dp.M_Delivery_Planning_id            where dp.M_Delivery_Planning_ID = @M_Delivery_Planning_ID / -1@              AND M_Delivery_Planning.M_Delivery_Planning_ID = dp.M_Delivery_Planning_ID)',Updated=TO_TIMESTAMP('2023-01-31 18:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541711
;

-- 2023-01-31T16:49:35.164Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541711, IsTableRecordIdTarget='Y',Updated=TO_TIMESTAMP('2023-01-31 18:49:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540382
;

-- 2023-01-31T16:50:22.948Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541710, IsTableRecordIdTarget='N',Updated=TO_TIMESTAMP('2023-01-31 18:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540382
;

-- Reference: Delivery Instruction source
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-31T16:51:00.247Z
UPDATE AD_Ref_Table SET WhereClause='M_Delivery_Planning_ID = @M_Delivery_Planning_ID / -1@',Updated=TO_TIMESTAMP('2023-01-31 18:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541710
;

-- Column: M_ShipperTransportation.M_Delivery_Planning_ID
-- 2023-01-31T16:54:06.775Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2023-01-31 18:54:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585609
;

-- 2023-01-31T16:54:14.108Z
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2023-01-31 18:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540382
;

-- 2023-01-31T16:56:25.848Z
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540382
;

-- Name: M_Delivery_Planning target
-- 2023-01-31T16:56:31.003Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541711
;

-- 2023-01-31T16:56:31.006Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541711
;

-- Name: Delivery Instruction source
-- 2023-01-31T16:56:34.403Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541710
;

-- 2023-01-31T16:56:34.407Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541710
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Lieferplanung
-- Column: M_ShipperTransportation.M_Delivery_Planning_ID
-- 2023-01-31T16:57:24.869Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585609,710779,0,546732,0,TO_TIMESTAMP('2023-01-31 18:57:24','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Lieferplanung',0,190,0,1,1,TO_TIMESTAMP('2023-01-31 18:57:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T16:57:24.870Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-31T16:57:24.872Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581677) 
;

-- 2023-01-31T16:57:24.875Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710779
;

-- 2023-01-31T16:57:24.876Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710779)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> delivery dates.Lieferplanung
-- Column: M_ShipperTransportation.M_Delivery_Planning_ID
-- 2023-01-31T16:58:27.399Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710779,0,546732,550206,614920,'F',TO_TIMESTAMP('2023-01-31 18:58:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Lieferplanung',5,0,0,TO_TIMESTAMP('2023-01-31 18:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> doctype.Abholung am
-- Column: M_ShipperTransportation.DateToBeFetched
-- 2023-01-31T17:00:45.489Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-31 19:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614592
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> doctype.Belegart
-- Column: M_ShipperTransportation.C_DocType_ID
-- 2023-01-31T17:00:45.494Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-31 19:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614589
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> doctype.Beleg Nr.
-- Column: M_ShipperTransportation.DocumentNo
-- 2023-01-31T17:00:45.499Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-01-31 19:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614590
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> doctype.Belegdatum
-- Column: M_ShipperTransportation.DateDoc
-- 2023-01-31T17:00:45.505Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-01-31 19:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614591
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> advanced edit -> 10 -> advanced edit.Gewicht Brutto
-- Column: M_ShipperTransportation.PackageWeight
-- 2023-01-31T17:00:45.510Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-01-31 19:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614599
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> advanced edit -> 10 -> advanced edit.Gewicht Netto
-- Column: M_ShipperTransportation.PackageNetTotal
-- 2023-01-31T17:00:45.515Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-01-31 19:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614600
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> delivery dates.Lieferplanung
-- Column: M_ShipperTransportation.M_Delivery_Planning_ID
-- 2023-01-31T17:00:45.520Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-01-31 19:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614920
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> org.Sektion
-- Column: M_ShipperTransportation.AD_Org_ID
-- 2023-01-31T17:00:45.525Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-01-31 19:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614595
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> rest.Belegstatus
-- Column: M_ShipperTransportation.DocStatus
-- 2023-01-31T17:00:45.530Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-01-31 19:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614597
;

-- Column: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-31T17:03:26.870Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-31 19:03:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540426
;

-- Column: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-31T17:04:07.336Z
UPDATE AD_Column SET IsGenericZoomOrigin='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-31 19:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540426
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> rest.Belegstatus
-- Column: M_ShipperTransportation.DocStatus
-- 2023-01-31T17:11:41.695Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-01-31 19:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614597
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> delivery dates.Lieferplanung
-- Column: M_ShipperTransportation.M_Delivery_Planning_ID
-- 2023-01-31T17:11:41.700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-01-31 19:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614920
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> org.Sektion
-- Column: M_ShipperTransportation.AD_Org_ID
-- 2023-01-31T17:11:41.706Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-01-31 19:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614595
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> delivery dates.Lieferplanung
-- Column: M_ShipperTransportation.M_Delivery_Planning_ID
-- 2023-01-31T17:11:49.638Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-01-31 19:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614920
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> rest.Belegstatus
-- Column: M_ShipperTransportation.DocStatus
-- 2023-01-31T17:11:49.642Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-01-31 19:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614597
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Mandant
-- Column: M_ShipperTransportation_Delivery_Instructions_V.AD_Client_ID
-- 2023-01-31T17:12:26.187Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614877
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Aktiv
-- Column: M_ShipperTransportation_Delivery_Instructions_V.IsActive
-- 2023-01-31T17:12:33.115Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614875
;

