-- Column: C_Invoice_Candidate.M_Warehouse_ID
-- 2023-08-08T07:08:40.856986Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587247,459,0,19,540270,'M_Warehouse_ID',TO_TIMESTAMP('2023-08-08 10:08:40.606','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Lager oder Ort für Dienstleistung','de.metas.invoicecandidate',0,10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lager',0,0,TO_TIMESTAMP('2023-08-08 10:08:40.606','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-08T07:08:41.201961200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587247 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-08T07:08:41.359185300Z
/* DDL */  select update_Column_Translation_From_AD_Element(459)
;

-- Column: C_Invoice_Candidate.C_Harvesting_Calendar_ID
-- 2023-08-08T07:10:07.514701200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587248,581157,0,30,540260,540270,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-08-08 10:10:07.337','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2023-08-08 10:10:07.337','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-08T07:10:07.514701200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587248 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-08T07:10:08.015597300Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157)
;

-- Column: C_Invoice_Candidate.Harvesting_Year_ID
-- 2023-08-08T07:10:45.814907700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587249,582471,0,30,540133,540270,540647,'Harvesting_Year_ID',TO_TIMESTAMP('2023-08-08 10:10:45.641','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-08-08 10:10:45.641','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-08T07:10:45.830373900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587249 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-08T07:10:46.332952200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471)
;

-- 2023-08-08T07:10:49.237198700Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-08-08T07:10:49.679028200Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT HarvestingYear_CInvoiceCandidate FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- 2023-08-08T07:11:01.170187700Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2023-08-08T07:11:01.484545300Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CHarvestingCalendar_CInvoiceCandidate FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- 2023-08-08T07:11:15.903857400Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN M_Warehouse_ID NUMERIC(10)')
;

-- 2023-08-08T07:11:16.250012200Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT MWarehouse_CInvoiceCandidate FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice_Candidate.ProductDescription
-- 2023-08-08T07:11:43.640112Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2023-08-08 10:11:43.64','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585322
;

-- Column: C_Invoice_Candidate.C_Async_Batch_ID
-- 2023-08-08T07:11:44.117547Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2023-08-08 10:11:44.117','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575016
;

-- Column: C_Invoice_Candidate.Bill_BPartner_ID
-- 2023-08-08T07:11:44.668359800Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2023-08-08 10:11:44.668','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=544920
;

-- Column: C_Invoice_Candidate.POReference
-- 2023-08-08T07:11:45.228808700Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2023-08-08 10:11:45.228','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551469
;

-- Column: C_Invoice_Candidate.ExternalHeaderId
-- 2023-08-08T07:11:45.891430300Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2023-08-08 10:11:45.891','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=569216
;

-- Column: C_Invoice_Candidate.C_Activity_ID
-- 2023-08-08T07:11:46.347441500Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2023-08-08 10:11:46.347','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551072
;

-- Column: C_Invoice_Candidate.C_DocTypeInvoice_ID
-- 2023-08-08T07:11:46.948976700Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2023-08-08 10:11:46.948','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551286
;

-- Column: C_Invoice_Candidate.C_ILCandHandler_ID
-- 2023-08-08T07:11:47.436982300Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2023-08-08 10:11:47.436','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=546203
;

-- Column: C_Invoice_Candidate.C_InvoiceSchedule_ID
-- 2023-08-08T07:11:47.839036900Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2023-08-08 10:11:47.839','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=546600
;

-- Column: C_Invoice_Candidate.C_Order_BPartner
-- 2023-08-08T07:11:48.385915600Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2023-08-08 10:11:48.385','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551953
;

-- Column: C_Invoice_Candidate.C_Order_ID
-- 2023-08-08T07:11:48.983906600Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2023-08-08 10:11:48.983','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=544913
;

-- Column: C_Invoice_Candidate.DateToInvoice
-- 2023-08-08T07:11:49.429803100Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=120,Updated=TO_TIMESTAMP('2023-08-08 10:11:49.429','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=546339
;

-- Column: C_Invoice_Candidate.DeliveryDate
-- 2023-08-08T07:11:49.962120700Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=130,Updated=TO_TIMESTAMP('2023-08-08 10:11:49.962','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551761
;

-- Column: C_Invoice_Candidate.ApprovalForInvoicing
-- 2023-08-08T07:11:50.557961100Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=140,Updated=TO_TIMESTAMP('2023-08-08 10:11:50.557','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551293
;

-- Column: C_Invoice_Candidate.IsEdiInvoicRecipient
-- 2023-08-08T07:11:50.919050600Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=150,Updated=TO_TIMESTAMP('2023-08-08 10:11:50.919','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=552564
;

-- Column: C_Invoice_Candidate.IsError
-- 2023-08-08T07:11:51.312218600Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=160,Updated=TO_TIMESTAMP('2023-08-08 10:11:51.312','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=546227
;

-- Column: C_Invoice_Candidate.IsMaterialTracking
-- 2023-08-08T07:11:51.721544300Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=170,Updated=TO_TIMESTAMP('2023-08-08 10:11:51.721','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551796
;

-- Column: C_Invoice_Candidate.Processed
-- 2023-08-08T07:11:52.145722800Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=180,Updated=TO_TIMESTAMP('2023-08-08 10:11:52.145','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545771
;

-- Column: C_Invoice_Candidate.IsSOTrx
-- 2023-08-08T07:11:52.509140300Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=190,Updated=TO_TIMESTAMP('2023-08-08 10:11:52.509','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=549847
;

-- Column: C_Invoice_Candidate.IsInDispute
-- 2023-08-08T07:11:52.915014400Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=200,Updated=TO_TIMESTAMP('2023-08-08 10:11:52.915','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=550515
;

-- Column: C_Invoice_Candidate.IsInEffect
-- 2023-08-08T07:11:53.275950200Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=210,Updated=TO_TIMESTAMP('2023-08-08 10:11:53.275','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584279
;

-- Column: C_Invoice_Candidate.IsInterimInvoice
-- 2023-08-08T07:11:53.715312Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=220,Updated=TO_TIMESTAMP('2023-08-08 10:11:53.715','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=583863
;

-- Column: C_Invoice_Candidate.M_InOut_ID
-- 2023-08-08T07:11:54.076735400Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=230,Updated=TO_TIMESTAMP('2023-08-08 10:11:54.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551762
;

-- Column: C_Invoice_Candidate.M_Material_Tracking_ID
-- 2023-08-08T07:11:54.422992100Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=240,Updated=TO_TIMESTAMP('2023-08-08 10:11:54.422','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551290
;

-- Column: C_Invoice_Candidate.M_PricingSystem_ID
-- 2023-08-08T07:11:54.815683200Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=250,Updated=TO_TIMESTAMP('2023-08-08 10:11:54.815','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545852
;

-- Column: C_Invoice_Candidate.M_Product_Category_ID
-- 2023-08-08T07:11:55.334754800Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=260,Updated=TO_TIMESTAMP('2023-08-08 10:11:55.334','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=552289
;

-- Column: C_Invoice_Candidate.M_Product_ID
-- 2023-08-08T07:11:55.822230200Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=270,Updated=TO_TIMESTAMP('2023-08-08 10:11:55.822','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=544923
;

-- Column: C_Invoice_Candidate.QtyDelivered
-- 2023-08-08T07:11:56.245862100Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=280,Updated=TO_TIMESTAMP('2023-08-08 10:11:56.245','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545314
;

-- Column: C_Invoice_Candidate.C_Currency_ID
-- 2023-08-08T07:11:56.733649100Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=290,Updated=TO_TIMESTAMP('2023-08-08 10:11:56.733','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545316
;

-- Column: C_Invoice_Candidate.C_Harvesting_Calendar_ID
-- 2023-08-08T07:11:57.267277600Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=300,Updated=TO_TIMESTAMP('2023-08-08 10:11:57.267','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587248
;

-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2023-08-08T07:11:57.819828200Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=310,Updated=TO_TIMESTAMP('2023-08-08 10:11:57.819','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=544902
;

-- Column: C_Invoice_Candidate.Harvesting_Year_ID
-- 2023-08-08T07:29:18.625623100Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=310,Updated=TO_TIMESTAMP('2023-08-08 10:29:18.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587249
;

-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2023-08-08T07:29:19.266615Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=320,Updated=TO_TIMESTAMP('2023-08-08 10:29:19.266','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=544902
;

-- Column: C_Invoice_Candidate.M_Warehouse_ID
-- 2023-08-08T07:29:28.429610Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=320,Updated=TO_TIMESTAMP('2023-08-08 10:29:28.429','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587247
;

-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2023-08-08T07:29:28.775343100Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=330,Updated=TO_TIMESTAMP('2023-08-08 10:29:28.775','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=544902
;



/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */



-- Column: C_Invoice_Candidate.C_Harvesting_Calendar_ID
-- 2023-08-08T10:11:37.441345800Z
UPDATE AD_Column SET FilterOperator='E',Updated=TO_TIMESTAMP('2023-08-08 13:11:37.441','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587248
;

-- Column: C_Invoice_Candidate.C_Harvesting_Calendar_ID
-- 2023-08-08T10:11:47.375180400Z
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-08-08 13:11:47.375','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587248
;

-- Column: C_Invoice_Candidate.Harvesting_Year_ID
-- 2023-08-08T10:12:01.478490300Z
UPDATE AD_Column SET FilterOperator='E',Updated=TO_TIMESTAMP('2023-08-08 13:12:01.478','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587249
;

-- Column: C_Invoice_Candidate.Harvesting_Year_ID
-- 2023-08-08T10:12:07.006968800Z
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-08-08 13:12:07.006','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587249
;

-- Column: C_Invoice_Candidate.M_Warehouse_ID
-- 2023-08-08T10:12:24.303061Z
UPDATE AD_Column SET FilterOperator='E', ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-08-08 13:12:24.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587247
;


-- Column: C_Invoice_Candidate.M_Warehouse_ID
-- 2023-08-08T10:48:17.268119300Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-08-08 13:48:17.268','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587247
;



