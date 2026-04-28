

-- 29.10.2015 17:21
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542911,0,'PP_Order_Issue_ID',TO_TIMESTAMP('2015-10-29 17:21:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','Zugeteilt zu Prod.-Auftrag','Zugeteilt zu Prod.-Auftrag',TO_TIMESTAMP('2015-10-29 17:21:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.10.2015 17:21
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542911 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 29.10.2015 17:23
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552790,542911,0,30,540503,540682,'N','PP_Order_Issue_ID',TO_TIMESTAMP('2015-10-29 17:23:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Zugeteilt zu Prod.-Auftrag',0,TO_TIMESTAMP('2015-10-29 17:23:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 29.10.2015 17:23
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552790 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 29.10.2015 17:25
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542912,0,'PP_Order_Receipt_ID',TO_TIMESTAMP('2015-10-29 17:25:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Empf. Prod.-Auftrag','Empf. Prod.-Auftrag',TO_TIMESTAMP('2015-10-29 17:25:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.10.2015 17:25
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542912 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 29.10.2015 17:25
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552791,542912,0,30,540503,540682,'N','PP_Order_Receipt_ID',TO_TIMESTAMP('2015-10-29 17:25:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Empf. Prod.-Auftrag',0,TO_TIMESTAMP('2015-10-29 17:25:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 29.10.2015 17:25
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552791 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 29.10.2015 17:26
-- URL zum Konzept
UPDATE AD_Element SET Name='Empf. aus Prod.-Auftrag', PrintName='Empf. aus Prod.-Auftrag',Updated=TO_TIMESTAMP('2015-10-29 17:26:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542912
;

-- 29.10.2015 17:26
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542912
;

-- 29.10.2015 17:26
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PP_Order_Receipt_ID', Name='Empf. aus Prod.-Auftrag', Description=NULL, Help=NULL WHERE AD_Element_ID=542912
;

-- 29.10.2015 17:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PP_Order_Receipt_ID', Name='Empf. aus Prod.-Auftrag', Description=NULL, Help=NULL, AD_Element_ID=542912 WHERE UPPER(ColumnName)='PP_ORDER_RECEIPT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 29.10.2015 17:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PP_Order_Receipt_ID', Name='Empf. aus Prod.-Auftrag', Description=NULL, Help=NULL WHERE AD_Element_ID=542912 AND IsCentrallyMaintained='Y'
;

-- 29.10.2015 17:26
-- URL zum Konzept
UPDATE AD_Field SET Name='Empf. aus Prod.-Auftrag', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542912) AND IsCentrallyMaintained='Y'
;

-- 29.10.2015 17:26
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Empf. aus Prod.-Auftrag', Name='Empf. aus Prod.-Auftrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542912)
;

-- 29.10.2015 17:26
-- URL zum Konzept
UPDATE AD_Element SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2015-10-29 17:26:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542911
;

-- 29.10.2015 17:28
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Karotten-ID',Updated=TO_TIMESTAMP('2015-10-29 17:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556279
;

-- 29.10.2015 17:28
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556279
;

-- 29.10.2015 17:28
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-10-29 17:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556288
;

-- 29.10.2015 17:28
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-10-29 17:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556287
;

-- 29.10.2015 17:29
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='HU',Updated=TO_TIMESTAMP('2015-10-29 17:29:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556280
;

-- 29.10.2015 17:29
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556280
;

-- 29.10.2015 17:30
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=65, SeqNoGrid=65,Updated=TO_TIMESTAMP('2015-10-29 17:30:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556282
;

-- 29.10.2015 17:31
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=85, SeqNoGrid=85,Updated=TO_TIMESTAMP('2015-10-29 17:31:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556285
;

-- 29.10.2015 17:31
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-10-29 17:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556286
;

-- 29.10.2015 17:32
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552791,556370,0,540702,0,TO_TIMESTAMP('2015-10-29 17:32:19','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.handlingunits',0,'Y','Y','Y','Y','N','N','N','N','N','Empf. aus Prod.-Auftrag',120,120,0,TO_TIMESTAMP('2015-10-29 17:32:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.10.2015 17:32
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556370 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.10.2015 17:32
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552790,556371,0,540702,0,TO_TIMESTAMP('2015-10-29 17:32:34','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.handlingunits',0,'Y','Y','Y','Y','N','N','N','N','Y','Zugeteilt zu Prod.-Auftrag',130,130,0,TO_TIMESTAMP('2015-10-29 17:32:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.10.2015 17:32
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556371 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;


-- 30.10.2015 09:48
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=NULL, IsUpdateable='N',Updated=TO_TIMESTAMP('2015-10-30 09:48:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552697
;

-- 30.10.2015 09:48
-- URL zum Konzept
UPDATE AD_Column SET IsKey='N',Updated=TO_TIMESTAMP('2015-10-30 09:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552697
;

-- 30.10.2015 09:51
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542913,0,'RV_M_Material_Tracking_HU_Details_ID',TO_TIMESTAMP('2015-10-30 09:51:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','RV_M_Material_Tracking_HU_Details_ID','RV_M_Material_Tracking_HU_Details_ID',TO_TIMESTAMP('2015-10-30 09:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.10.2015 09:51
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542913 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 30.10.2015 09:51
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552793,542913,0,13,540682,'N','RV_M_Material_Tracking_HU_Details_ID',TO_TIMESTAMP('2015-10-30 09:51:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',10,'Y','N','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','RV_M_Material_Tracking_HU_Details_ID',0,TO_TIMESTAMP('2015-10-30 09:51:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 30.10.2015 09:51
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552793 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 30.10.2015 09:51
-- URL zum Konzept
UPDATE AD_Element SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2015-10-30 09:51:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542913
;

-- 30.10.2015 09:52
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SortNo,Updated,UpdatedBy) VALUES (0,552793,556376,0,540702,0,TO_TIMESTAMP('2015-10-30 09:52:53','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.handlingunits',0,'Y','Y','N','N','N','N','N','N','N','RV_M_Material_Tracking_HU_Details_ID',0,TO_TIMESTAMP('2015-10-30 09:52:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.10.2015 09:52
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556376 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;





COMMIT;
-- ddl:

DROP VIEW IF EXISTS RV_M_Material_Tracking_HU_Details;

CREATE OR REPLACE VIEW RV_M_Material_Tracking_HU_Details AS
SELECT
	mt.Lot
	, mt.M_Material_Tracking_ID
	, hu.M_HU_ID
	, hu.M_HU_ID AS RV_M_Material_Tracking_HU_Details_ID
	, hu.HUStatus
	, hu.M_Locator_ID
	, hs.M_Product_ID
	, hs.Qty, hs.C_UOM_ID
	-- Standard columns
	, hu.Created
	, hu.CreatedBy
	, hu.Updated
	, hu.UpdatedBy
	, hu.IsActive
	, hu.AD_Client_ID
	, hu.AD_Org_ID
	, po_issue.PP_Order_ID AS pp_order_issue_id
	, po_issue.C_DocType_ID AS pp_order_issue_doctype_id
	, po_issue.DocStatus AS pp_order_issue_docstatus
	, po_receipt.PP_Order_ID AS pp_order_receipt_id
	, po_receipt.C_DocType_ID AS pp_order_receipt_doctype_id
	, po_receipt.DocStatus AS pp_order_receipt_docstatus
	, io_receipt.M_InOut_ID AS M_InOut_Receipt_ID
	, io_shipment.M_InOut_ID AS M_InOut_Shipment_ID

--	, cc_issue.PP_Cost_Collector_ID
--	, hu_as_iol.M_HU_Assignment_ID
--	, iol.M_InOutLine_ID
--	,hu_as_po.M_HU_Assignment_ID
--	,hu_as_pobl.M_HU_Assignment_ID
--	,pobl.*
FROM 
	M_Material_Tracking mt 
	JOIN M_Attribute a ON a.Value='M_Material_Tracking_ID'
		JOIN M_HU_Attribute hu_at ON hu_at.M_Attribute_ID=a.M_Attribute_ID AND hu_at.Value=mt.M_Material_Tracking_ID::text
			JOIN M_HU hu ON hu_at.M_HU_ID=hu.M_HU_ID
				JOIN M_HU_Storage hs ON hu.M_HU_ID = hs.M_HU_ID
				
				/*
				 * Receive-PP_Orders
				 */
				LEFT JOIN M_HU_Assignment hu_as_po ON hu_as_po.M_HU_ID=hu.M_HU_ID AND hu_as_po.AD_Table_ID=get_table_id('PP_Order') /* received main product*/
						AND hu_as_po.M_LU_HU_ID IS NULL AND hu_as_po.M_TU_HU_ID IS NULL AND hu_as_po.VHU_ID IS NULL /* only the toplevel assignment */
				LEFT JOIN M_HU_Assignment hu_as_pobl ON hu_as_pobl.M_HU_ID=hu.M_HU_ID AND hu_as_pobl.AD_Table_ID=get_table_id('PP_Order_BOMLine') /* received by- and co- products, but not issued products, because we don'T create a hu_as for them!*/
						AND hu_as_pobl.M_LU_HU_ID IS NULL AND hu_as_pobl.M_TU_HU_ID IS NULL AND hu_as_pobl.VHU_ID IS NULL /* only the toplevel assignment */
					LEFT JOIN PP_Order_BOMLine pobl ON pobl.PP_Order_BOMLine_ID=hu_as_pobl.Record_ID
						LEFT JOIN PP_Order po_receipt ON (
							po_receipt.PP_Order_ID=pobl.PP_Order_ID AND pobl.ComponentType IN ('CP', 'BY') /* received co-product or by-product */
							OR
							po_receipt.PP_Order_ID=hu_as_po.Record_ID)
						/* we don't have HU-Assignments for issues! */							
						/* LEFT JOIN PP_Order po_issue ON po_issue.PP_Order_ID=pobl.PP_Order_ID AND pobl.ComponentType IN ('CO','VA','PK') */ /* component, variant or package material */
				/*
				 * Issue-PP_Orders
				 */
				LEFT JOIN M_HU_Assignment hu_as_cc ON hu_as_cc.M_HU_ID=hu.M_HU_ID AND hu_as_cc.AD_Table_ID=get_table_id('PP_Cost_Collector')
						AND hu_as_cc.M_LU_HU_ID IS NULL AND hu_as_cc.M_TU_HU_ID IS NULL AND hu_as_cc.VHU_ID IS NULL /* only the toplevel assignment */
					LEFT JOIN PP_Cost_Collector cc_issue ON cc_issue.PP_Cost_Collector_ID=hu_as_cc.Record_ID AND cc_issue.DocStatus IN ('CO','CL') AND cc_issue.CostCollectorType='110' /*110=ComponentIssue*/
						LEFT JOIN PP_Order po_issue ON po_issue.PP_Order_ID=cc_issue.PP_Order_ID
				/*
				 * Material Receipts and shipments
				 */
				LEFT JOIN M_HU_Assignment hu_as_iol ON hu_as_iol.M_HU_ID=hu.M_HU_ID AND hu_as_iol.AD_Table_ID=get_table_id('M_InOutLine') 
						AND hu_as_iol.M_LU_HU_ID IS NULL AND hu_as_iol.M_TU_HU_ID IS NULL AND hu_as_iol.VHU_ID IS NULL /* only the toplevel assignment */
					LEFT JOIN M_InOutLine iol ON iol.M_InOutLine_ID=hu_as_iol.Record_ID
						LEFT JOIN M_InOut io_receipt ON io_receipt.M_InOut_ID=iol.M_InOut_ID AND io_receipt.MovementType IN ('V+', 'C+') /* 'V+'=VendorReceipts, 'C+'=CustomerReturns*/
						LEFT JOIN M_InOut io_shipment ON io_shipment.M_InOut_ID=iol.M_InOut_ID AND io_shipment.MovementType IN ('V-', 'C-') /* 'V-'=VendorReturns, 'C-'=CustomerShipment*/
WHERE 
	a.Value='M_Material_Tracking_ID'
	AND hu.M_HU_Item_Parent_ID IS NULL -- only show top-level-HUs
	AND NOT (hu.HUStatus='D' AND M_HU_PI_Version_ID=101) -- hide destroyed virtual-PI-HUs, because they don't have a parent set, but still their also destroyed parent is there
--	AND (hu.HUStatus!='D' OR EXISTS (select 1 from M_HU_Assignment hu_any where hu_any.M_HU_ID=hu.M_HU_ID)) -- also hide destroyed HUs that don't have any HU_Assignement at all. They were planned HUs that got discarded
--	AND mt.M_Material_Tracking_ID = 1000443
--	AND pobl.PP_Order_BOMLine_ID=1120157
--	AND po_issue.PP_Order_ID=1046130
--	AND hu.M_HU_ID=10100810 --has 2 rows..2 iol-ID
;
