-- 2018-06-18T18:02:14.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,540980,'Y','de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Make_HuReservation','N',TO_TIMESTAMP('2018-06-18 18:02:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','Y',0,'Verfügbare Handling Units anzeigen','N','Y','Java',TO_TIMESTAMP('2018-06-18 18:02:14','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_C_OrderLineSO_Make_HuReservation')
;

-- 2018-06-18T18:02:14.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540980 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-06-18T18:02:17.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 18:02:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540980 AND AD_Language='de_CH'
;

-- 2018-06-18T18:02:31.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 18:02:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Show available handling units' WHERE AD_Process_ID=540980 AND AD_Language='en_US'
;

-- 2018-06-18T18:03:13.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540980,259,143,TO_TIMESTAMP('2018-06-18 18:03:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2018-06-18 18:03:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N')
;

-- 2018-06-18T18:30:02.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction='N',Updated=TO_TIMESTAMP('2018-06-18 18:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540980 AND AD_Table_ID=259
;

-- 2018-06-19T07:17:02.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Launch_HUEditor', Value='WEBUI_C_OrderLineSO_Launch_HUEditor',Updated=TO_TIMESTAMP('2018-06-19 07:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540980
;

-- 2018-06-20T13:04:59.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540478,541652,TO_TIMESTAMP('2018-06-20 13:04:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Reserviert',TO_TIMESTAMP('2018-06-20 13:04:59','YYYY-MM-DD HH24:MI:SS'),100,'R','Reserved')
;

-- 2018-06-20T13:04:59.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541652 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-06-20T13:05:02.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-20 13:05:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541652 AND AD_Language='de_CH'
;

-- 2018-06-20T13:05:13.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-20 13:05:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reserved' WHERE AD_Ref_List_ID=541652 AND AD_Language='en_US'
;

-- 2018-06-20T13:06:35.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-20 13:06:35','YYYY-MM-DD HH24:MI:SS'),Description='Sortof similar to active, but is assigned to a particular portner or sale order line. Can be moved around, but not reserved or picked/shipped to another bpartner.' WHERE AD_Ref_List_ID=541652 AND AD_Language='en_US'
;

-- 2018-06-20T13:07:18.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Ähnlich wie "Aktiv", aber einer Verkaufsauftragsposition oder einem Kunden zugewiesen.',Updated=TO_TIMESTAMP('2018-06-20 13:07:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541652
;

-- 2018-06-20T13:07:30.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-20 13:07:30','YYYY-MM-DD HH24:MI:SS'),Description='Ähnlich wie "Aktiv", aber einer Verkaufsauftragsposition oder einem Kunden zugewiesen.' WHERE AD_Ref_List_ID=541652 AND AD_Language='de_CH'
;






























-- 2018-06-22T18:55:33.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','WEBUI_C_OrderLineSO_Make_HuReservation','3','de.metas.handlingunits','Y','N','N','N',540983,'N','Y','N','Java','N','N',0,0,'Reserve','de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Make_HuReservation',100,TO_TIMESTAMP('2018-06-22 18:55:33','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-22 18:55:33','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-06-22T18:55:33.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540983 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

