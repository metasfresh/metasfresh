-- 2017-05-22T14:05:41.010
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543346,0,'ReturnFromCustomer',TO_TIMESTAMP('2017-05-22 14:05:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','ReturnFromCustomer','ReturnFromCustomer',TO_TIMESTAMP('2017-05-22 14:05:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T14:05:41.020
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543346 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-05-22T15:56:24.486
-- URL zum Konzept
-- UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?
-- ;

-- 2017-05-22T15:56:24.495
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540794,'Y','de.metas.handlingunits.inout.process.M_InOut_ReturnFromCustomer','N',TO_TIMESTAMP('2017-05-22 15:56:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','Y',0,'ReturnHUsToCustomer','N','Y','Java',TO_TIMESTAMP('2017-05-22 15:56:24','YYYY-MM-DD HH24:MI:SS'),100,'10000001')
;

-- 2017-05-22T15:56:24.502
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540794 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-05-22T17:37:05.377
-- URL zum Konzept
INSERT INTO AD_Form (AccessLevel,AD_Client_ID,AD_Form_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsOneInstanceOnly,Modal,Name,Updated,UpdatedBy) VALUES ('3',0,540070,0,TO_TIMESTAMP('2017-05-22 17:37:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','M_InOut_ReturnFromCustomer',TO_TIMESTAMP('2017-05-22 17:37:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:37:05.382
-- URL zum Konzept
INSERT INTO AD_Form_Trl (AD_Language,AD_Form_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Form_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Form t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Form_ID=540070 AND NOT EXISTS (SELECT * FROM AD_Form_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Form_ID=t.AD_Form_ID)
;

-- 2017-05-22T17:40:51.288
-- URL zum Konzept
UPDATE AD_Process SET Classname='', Value='ReturnHUsToCustomer',Updated=TO_TIMESTAMP('2017-05-22 17:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540794
;

-- 2017-05-23T15:31:11.298
-- URL zum Konzept
UPDATE AD_Form SET Classname='de.metas.handlingunits.client.terminal.shipment.form.ReturnFromCustomerHUSelectForm',Updated=TO_TIMESTAMP('2017-05-23 15:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Form_ID=540070
;

-- 2017-05-23T15:33:40.205
-- URL zum Konzept
UPDATE AD_Process SET AD_Form_ID=540070,Updated=TO_TIMESTAMP('2017-05-23 15:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540794
;

-- 2017-05-23T15:33:56.438
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556818,543346,0,540794,28,319,'N','ReturnFromCustomer',TO_TIMESTAMP('2017-05-23 15:33:56','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',25,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','ReturnFromCustomer',0,TO_TIMESTAMP('2017-05-23 15:33:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-05-23T15:33:56.447
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556818 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-05-23T15:33:59.912
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('m_inout','ALTER TABLE public.M_InOut ADD COLUMN ReturnFromCustomer CHAR(25)')
;

-- 2017-05-23T15:36:08.757
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_FieldGroup_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556818,558503,101,0,257,0,TO_TIMESTAMP('2017-05-23 15:36:08','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.handlingunits',0,'Y','Y','Y','Y','N','N','N','N','N','ReturnFromCustomer',399,399,0,1,1,TO_TIMESTAMP('2017-05-23 15:36:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:36:08.761
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558503 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-05-23T16:11:52.692
-- URL zum Konzept
UPDATE AD_Process SET AllowProcessReRun='N', RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2017-05-23 16:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540794
;

-- 2017-05-23T16:30:33.710
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2017-05-23 16:30:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556818
;

-- 2017-05-23T18:26:06.070
-- URL zum Konzept
UPDATE AD_Form SET Classname='de.metas.handlingunits.client.terminal.shipment.form.ReturnFromCustomerHUEditorForm',Updated=TO_TIMESTAMP('2017-05-23 18:26:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Form_ID=540070
;

-- 2017-05-25T17:58:36.661
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1025,0,540794,541181,19,'M_InOut_ID',TO_TIMESTAMP('2017-05-25 17:58:36','YYYY-MM-DD HH24:MI:SS'),100,'@M_InOut_ID@','Material Shipment Document','de.metas.handlingunits',0,'The Material Shipment / Receipt ','Y','N','Y','N','N','N','Lieferung/Wareneingang',10,TO_TIMESTAMP('2017-05-25 17:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T17:58:36.673
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541181 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-05-25T18:08:20.321
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.handlingunits.client.terminal.shipment.form.ReturnFromCustomerHUEditorForm',Updated=TO_TIMESTAMP('2017-05-25 18:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540794
;

-- 2017-05-25T18:11:57.095
-- URL zum Konzept
UPDATE AD_Process SET Classname='',Updated=TO_TIMESTAMP('2017-05-25 18:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540794
;

-- 2017-05-26T11:08:09.581
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.handlingunits.client.terminal.shipment.process.M_InOut_Create_ReturnFromVendor',Updated=TO_TIMESTAMP('2017-05-26 11:08:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540794
;

-- 2017-05-26T12:03:59.587
-- URL zum Konzept
UPDATE AD_Process SET AD_Form_ID=NULL,Updated=TO_TIMESTAMP('2017-05-26 12:03:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540794
;

-- 2017-05-26T16:18:08.301
-- URL zum Konzept
UPDATE AD_Process SET AD_Form_ID=540070, Classname='',Updated=TO_TIMESTAMP('2017-05-26 16:18:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540794
;

-- 2017-05-26T16:18:15.038
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541181
;

-- 2017-05-26T16:18:15.051
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541181
;

