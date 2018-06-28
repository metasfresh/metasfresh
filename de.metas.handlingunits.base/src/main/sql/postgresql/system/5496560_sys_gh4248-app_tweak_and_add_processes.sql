-- 2018-06-24T18:16:00.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540445,0,540988,TO_TIMESTAMP('2018-06-24 18:15:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Y','UC_M_HU_Reservation_VHU_ID','N',TO_TIMESTAMP('2018-06-24 18:15:59','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2018-06-24T18:16:00.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540445 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-06-24T18:16:46.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560367,540877,540445,0,TO_TIMESTAMP('2018-06-24 18:16:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',10,TO_TIMESTAMP('2018-06-24 18:16:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-24T18:31:11.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_HU_Reservation (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_BPartner_Customer_ID NUMERIC(10) NOT NULL, C_OrderLineSO_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_HU_Reservation_ID NUMERIC(10) NOT NULL, QtyReserved NUMERIC NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, VHU_ID NUMERIC(10) NOT NULL, CONSTRAINT CBPartnerCustomer_MHUReservation FOREIGN KEY (C_BPartner_Customer_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrderLineSO_MHUReservation FOREIGN KEY (C_OrderLineSO_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CUOM_MHUReservation FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_HU_Reservation_Key PRIMARY KEY (M_HU_Reservation_ID), CONSTRAINT VHU_MHUReservation FOREIGN KEY (VHU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED)
;

update AD_Index_Table set name = 'UC_M_HU_Reservation_VHU_ID' where  AD_Index_Table_ID=540445;

-- 2018-06-24T18:32:29.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_M_HU_Reservation_VHU_ID ON M_HU_Reservation (VHU_ID) WHERE IsActive='Y'
;

/*
-- 2018-06-25T11:25:53.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540984,'Y','WEBUI_C_OrderLineSO_Make_HuReservation','N',TO_TIMESTAMP('2018-06-25 11:25:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'Reservieren','N','Y','Java',TO_TIMESTAMP('2018-06-25 11:25:53','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_C_OrderLineSO_Make_HuReservation')
;

-- 2018-06-25T11:25:53.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540984 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-06-25T11:29:25.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='1', EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2018-06-25 11:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540984
;

*/

-- 2018-06-25T11:30:25.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET TechnicalNote='This process is made available to the user by HUsToReserveViewFactory when the process WEBUI_C_OrderLineSO_Launch_HUEditor is run.',Updated=TO_TIMESTAMP('2018-06-25 11:30:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540980
;

-- 2018-06-25T11:33:09.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Make_HuReservation',Updated=TO_TIMESTAMP('2018-06-25 11:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540980
;

-- 2018-06-25T12:55:24.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-25 12:55:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540980 AND AD_Language='de_CH'
;

-- 2018-06-25T12:55:32.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-25 12:55:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reserve' WHERE AD_Process_ID=540980 AND AD_Language='en_US'
;

-- 2018-06-25T13:57:26.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540980,541321,29,'QtyToReserve',TO_TIMESTAMP('2018-06-25 13:57:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','Y','N','Menge',10,TO_TIMESTAMP('2018-06-25 13:57:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-25T13:57:26.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541321 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-06-25T13:57:29.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-25 13:57:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_Para_ID=541321 AND AD_Language='de_CH'
;

-- 2018-06-25T13:57:36.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-25 13:57:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quantity' WHERE AD_Process_Para_ID=541321 AND AD_Language='en_US'
;


-- 2018-06-25T14:32:43.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,540985,'Y','de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Delete_HuReservation','N',TO_TIMESTAMP('2018-06-25 14:32:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','Y',0,'Reservierung löschen','N','Y','Java',TO_TIMESTAMP('2018-06-25 14:32:43','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_C_OrderLineSO_Delete_HuReservation')
;

-- 2018-06-25T14:32:43.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540985 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-06-25T14:35:22.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540985,540516,TO_TIMESTAMP('2018-06-25 14:35:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2018-06-25 14:35:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N')
;

-- 2018-06-25T14:35:30.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET TechnicalNote='',Updated=TO_TIMESTAMP('2018-06-25 14:35:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540985
;

CREATE INDEX IF NOT EXISTS m_hu_reservation_c_orderlineso_id
   ON public.m_hu_reservation (c_orderlineso_id ASC NULLS LAST);
