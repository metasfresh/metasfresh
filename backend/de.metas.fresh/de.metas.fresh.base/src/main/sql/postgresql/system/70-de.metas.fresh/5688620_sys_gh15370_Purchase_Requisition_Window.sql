-- Column: M_Requisition.Author_ID
-- 2023-05-22T08:16:01.490030700Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-05-22 09:16:01.489','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586661
;

-- 2023-05-22T08:16:06.589555800Z
INSERT INTO t_alter_column values('m_requisition','Author_ID','NUMERIC(10)',null,null)
;

-- Column: M_Requisition.Author_ID
-- 2023-05-22T08:16:53.974330800Z
UPDATE AD_Column SET DefaultValue='@AD_User_ID@',Updated=TO_TIMESTAMP('2023-05-22 09:16:53.974','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586661
;

-- Column: M_Requisition.Author_ID
-- 2023-05-22T08:18:05.763789100Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2023-05-22 09:18:05.763','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586661
;

-- Column: M_Requisition.Author_ID
-- 2023-05-22T08:19:26.365109500Z
UPDATE AD_Column SET DefaultValue='@#AD_User_ID@',Updated=TO_TIMESTAMP('2023-05-22 09:19:26.365','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586661
;

-- Column: M_Requisition.Author_ID
-- 2023-05-22T08:20:12.311582900Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2023-05-22 09:20:12.311','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586661
;

-- Column: M_Requisition.Author_ID
-- 2023-05-22T08:20:16.356302900Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2023-05-22 09:20:16.355','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586661
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Autor
-- Column: M_Requisition.Author_ID
-- 2023-05-22T08:20:26.905891300Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-05-22 09:20:26.905','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715363
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> main.Lieferkontakt
-- Column: M_Requisition.AD_User_ID
-- 2023-05-22T08:21:21.785454200Z
UPDATE AD_UI_Element SET SeqNo=12,Updated=TO_TIMESTAMP('2023-05-22 09:21:21.785','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617439
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> main.Autor
-- Column: M_Requisition.Author_ID
-- 2023-05-22T08:21:29.502587700Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-05-22 09:21:29.502','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617455
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Neuer Lieferant Name
-- Column: M_RequisitionLine.NewSupplierName
-- 2023-05-22T08:27:19.352160900Z
UPDATE AD_Field SET DisplayLogic='@IsNewSupplier@=''Y''',Updated=TO_TIMESTAMP('2023-05-22 09:27:19.352','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715380
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Neue Lieferantenadresse Details
-- Column: M_RequisitionLine.NewSupplierAddress
-- 2023-05-22T08:29:09.928322900Z
UPDATE AD_Field SET DisplayLogic='@IsNewSupplierAddress@=''Y''',Updated=TO_TIMESTAMP('2023-05-22 09:29:09.928','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715384
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Lieferant genehmigt
-- Column: M_RequisitionLine.IsVendorApproved
-- 2023-05-22T08:31:13.185757300Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617483
;

-- 2023-05-22T08:31:13.193333600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715381
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Lieferant genehmigt
-- Column: M_RequisitionLine.IsVendorApproved
-- 2023-05-22T08:31:13.198176800Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=715381
;

-- 2023-05-22T08:31:13.202383Z
DELETE FROM AD_Field WHERE AD_Field_ID=715381
;

-- Column: M_RequisitionLine.IsVendorApproved
-- 2023-05-22T08:32:07.749929200Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586678
;

-- 2023-05-22T08:32:07.754613200Z
DELETE FROM AD_Column WHERE AD_Column_ID=586678
;

-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T08:35:57.984005400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586685,426,0,20,703,'IsVendor',TO_TIMESTAMP('2023-05-22 09:35:57.58','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist','D',0,1,'The Vendor checkbox indicates if this Business Partner is a Vendor.  If it is selected, additional fields will display which further identify this vendor.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lieferant',0,0,TO_TIMESTAMP('2023-05-22 09:35:57.58','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-22T08:35:57.989838700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586685 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-22T08:35:58.447959300Z
/* DDL */  select update_Column_Translation_From_AD_Element(426) 
;

-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T08:38:47.659079900Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT IsVendor FROM C_BPartner WHERE C_BPartner.C_BPartner_ID=@C_BPartner_ID@',Updated=TO_TIMESTAMP('2023-05-22 09:38:47.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586685
;

-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T08:39:29.066782100Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(IsVendor,''N'') FROM C_BPartner WHERE C_BPartner.C_BPartner_ID=@C_BPartner_ID@',Updated=TO_TIMESTAMP('2023-05-22 09:39:29.066','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586685
;

-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T08:39:48.426352600Z
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2023-05-22 09:39:48.425','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586685
;

-- 2023-05-22T08:39:50.769290300Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN IsVendor CHAR(1) DEFAULT ''N'' CHECK (IsVendor IN (''Y'',''N'')) NOT NULL')
;

-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T08:39:55.209384Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(IsVendor,''N'') FROM C_BPartner WHERE C_BPartner.C_BPartner_ID=@C_BPartner_ID@',Updated=TO_TIMESTAMP('2023-05-22 09:39:55.208','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586685
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Lieferant
-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T08:41:58.201008700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586685,715388,0,642,0,TO_TIMESTAMP('2023-05-22 09:41:50.882','YYYY-MM-DD HH24:MI:SS.US'),100,'Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist',0,'D','The Vendor checkbox indicates if this Business Partner is a Vendor.  If it is selected, additional fields will display which further identify this vendor.',0,'Y','Y','Y','N','N','N','N','N','Lieferant',0,230,0,1,1,TO_TIMESTAMP('2023-05-22 09:41:50.882','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-22T08:41:58.204718900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715388 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-22T08:41:58.206830500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(426) 
;

-- 2023-05-22T08:41:58.214256300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715388
;

-- 2023-05-22T08:41:58.215796900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715388)
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Lieferant genehmigt
-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T08:42:18.428792900Z
UPDATE AD_Field SET AD_Name_ID=582354, Description=NULL, Help=NULL, Name='Lieferant genehmigt',Updated=TO_TIMESTAMP('2023-05-22 09:42:18.428','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715388
;

-- 2023-05-22T08:42:18.431998300Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Lieferant genehmigt' WHERE AD_Field_ID=715388 AND AD_Language='de_DE'
;

-- 2023-05-22T08:42:18.433592Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582354) 
;

-- 2023-05-22T08:42:18.436729800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715388
;

-- 2023-05-22T08:42:18.437787100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715388)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Lieferant genehmigt
-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T08:42:32.092045900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715388,0,642,550704,617491,'F',TO_TIMESTAMP('2023-05-22 09:42:31.808','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Lieferant genehmigt',190,0,0,TO_TIMESTAMP('2023-05-22 09:42:31.808','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T08:46:14.769375Z
UPDATE AD_Column SET ColumnSQL='(SELECT IsVendor from C_BPartner bp where bp.C_BPartner_ID=M_RequisitionLine.C_BPartner_ID)', DefaultValue='', IsLazyLoading='Y', IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-05-22 09:46:14.769','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586685
;

-- Column: M_RequisitionLine.IsVendor
-- Column SQL (old): (SELECT IsVendor from C_BPartner bp where bp.C_BPartner_ID=M_RequisitionLine.C_BPartner_ID)
-- 2023-05-22T08:49:13.593154600Z
UPDATE AD_Column SET ColumnSQL='', DefaultValue='N', IsLazyLoading='N',Updated=TO_TIMESTAMP('2023-05-22 09:49:13.593','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586685
;

-- Column: M_RequisitionLine.M_Product_ID
-- 2023-05-22T08:50:15.480832300Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-05-22 09:50:15.48','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=11501
;

-- Column: M_RequisitionLine.C_BPartner_ID
-- 2023-05-22T08:51:56.684101600Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-05-22 09:51:56.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=56816
;

-- 2023-05-22T08:52:01.155760500Z
INSERT INTO t_alter_column values('m_requisitionline','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2023-05-22T08:52:01.159528100Z
INSERT INTO t_alter_column values('m_requisitionline','C_BPartner_ID',null,'NOT NULL',null)
;

-- Column: M_Requisition.IsQuotesExist
-- 2023-05-22T08:56:30.720112100Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2023-05-22 09:56:30.72','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586667
;

-- 2023-05-22T08:56:32.303404400Z
INSERT INTO t_alter_column values('m_requisition','IsQuotesExist','CHAR(1)',null,'Y')
;

-- 2023-05-22T08:56:32.432976200Z
UPDATE M_Requisition SET IsQuotesExist='Y' WHERE IsQuotesExist IS NULL
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Fehlende Angebotsnotiz
-- Column: M_Requisition.MissingQuoteNote
-- 2023-05-22T08:56:47.774338500Z
UPDATE AD_Field SET DisplayLogic='@IsQuotesExist@=''N''',Updated=TO_TIMESTAMP('2023-05-22 09:56:47.774','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715371
;

-- Column: M_Requisition.IsBudgetPlanned
-- 2023-05-22T08:58:55.797610600Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2023-05-22 09:58:55.797','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586673
;

-- 2023-05-22T08:58:57.582506400Z
INSERT INTO t_alter_column values('m_requisition','IsBudgetPlanned','CHAR(1)',null,'Y')
;

-- 2023-05-22T08:58:57.662965Z
UPDATE M_Requisition SET IsBudgetPlanned='Y' WHERE IsBudgetPlanned IS NULL
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Fehlender Budgetvermerk
-- Column: M_Requisition.MissingBudgetNote
-- 2023-05-22T08:59:07.076109400Z
UPDATE AD_Field SET DisplayLogic='@IsBudgetPlanned@=''N''',Updated=TO_TIMESTAMP('2023-05-22 09:59:07.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715377
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Bestellungsnummer
-- Column: M_Requisition.PurchaseOrderNo
-- 2023-05-22T09:00:16.537869700Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-05-22 10:00:16.537','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715378
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Zeile Nr.
-- Column: M_RequisitionLine.Line
-- 2023-05-22T09:01:10.973125600Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-05-22 10:01:10.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=10042
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Lieferant genehmigt
-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T09:02:12.153853700Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-05-22 10:02:12.153','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715388
;

-- Column: M_RequisitionLine.Description
-- 2023-05-22T09:04:25.356271800Z
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=2000, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-05-22 10:04:25.356','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=11492
;

-- 2023-05-22T09:04:26.443380600Z
INSERT INTO t_alter_column values('m_requisitionline','Description','VARCHAR(2000)',null,null)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10
-- UI Element Group: line
-- 2023-05-22T09:09:05.242043Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546826,550705,TO_TIMESTAMP('2023-05-22 10:08:49.968','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','line',20,TO_TIMESTAMP('2023-05-22 10:08:49.968','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> line.Produkt
-- Column: M_RequisitionLine.M_Product_ID
-- 2023-05-22T09:10:23.209715700Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550705, SeqNo=10,Updated=TO_TIMESTAMP('2023-05-22 10:10:23.209','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617474
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> line.Maßeinheit
-- Column: M_RequisitionLine.C_UOM_ID
-- 2023-05-22T09:10:47.277021400Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550705, SeqNo=20,Updated=TO_TIMESTAMP('2023-05-22 10:10:47.277','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617475
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> line.Menge
-- Column: M_RequisitionLine.Qty
-- 2023-05-22T09:10:59.386676700Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550705, SeqNo=30,Updated=TO_TIMESTAMP('2023-05-22 10:10:59.386','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617476
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> line.Einzelpreis
-- Column: M_RequisitionLine.PriceActual
-- 2023-05-22T09:12:28.135626800Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550705, SeqNo=40,Updated=TO_TIMESTAMP('2023-05-22 10:12:28.135','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617477
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> line.Beschreibung
-- Column: M_RequisitionLine.Description
-- 2023-05-22T09:13:04.281882100Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550705, SeqNo=50,Updated=TO_TIMESTAMP('2023-05-22 10:13:04.281','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617478
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> line.Zeilennetto
-- Column: M_RequisitionLine.LineNetAmt
-- 2023-05-22T09:14:15.979019800Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550705, SeqNo=60,Updated=TO_TIMESTAMP('2023-05-22 10:14:15.979','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617479
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Standort
-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-05-22T09:16:09.242343200Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-05-22 10:16:09.242','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617485
;

-- 2023-05-22T09:16:58.829193200Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,715383,0,541716,617473,TO_TIMESTAMP('2023-05-22 10:16:58.556','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,'widget',TO_TIMESTAMP('2023-05-22 10:16:58.556','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Standort
-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-05-22T09:17:50.723116400Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-05-22 10:17:50.723','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617485
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> line.Bestellposition
-- Column: M_RequisitionLine.C_OrderLine_ID
-- 2023-05-22T09:19:09.321120400Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550705, SeqNo=70,Updated=TO_TIMESTAMP('2023-05-22 10:19:09.321','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617480
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.eMail
-- Column: M_RequisitionLine.EMail
-- 2023-05-22T09:20:14.678460400Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-05-22 10:20:14.678','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617489
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Kanal der Bestellung
-- Column: M_RequisitionLine.OrderChannel
-- 2023-05-22T09:20:55.827463700Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-05-22 10:20:55.827','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617488
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Zahlungsweise
-- Column: M_RequisitionLine.PaymentRule
-- 2023-05-22T09:21:03.830478700Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-05-22 10:21:03.83','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617490
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Lieferant genehmigt
-- Column: M_RequisitionLine.IsVendor
-- 2023-05-22T09:21:17.148636200Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2023-05-22 10:21:17.148','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617491
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Neuer Lieferant
-- Column: M_RequisitionLine.IsNewSupplier
-- 2023-05-22T09:21:59.450204300Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2023-05-22 10:21:59.45','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617481
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Neuer Lieferant Name
-- Column: M_RequisitionLine.NewSupplierName
-- 2023-05-22T09:22:05.959215300Z
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2023-05-22 10:22:05.959','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617482
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Adresse des neuen Lieferanten
-- Column: M_RequisitionLine.IsNewSupplierAddress
-- 2023-05-22T09:22:12.659976Z
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2023-05-22 10:22:12.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617484
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Neue Lieferantenadresse Details
-- Column: M_RequisitionLine.NewSupplierAddress
-- 2023-05-22T09:22:19.017240800Z
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2023-05-22 10:22:19.017','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617487
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> line.Beschreibung
-- Column: M_RequisitionLine.Description
-- 2023-05-22T09:23:54.137183300Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2023-05-22 10:23:54.137','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617478
;

