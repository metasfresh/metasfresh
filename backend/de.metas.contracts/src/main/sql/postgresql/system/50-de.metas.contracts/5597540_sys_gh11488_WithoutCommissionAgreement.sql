-- 2021-07-08T17:06:53.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_HierarchyCommissionSettings (AD_Client_ID,AD_Org_ID,C_HierarchyCommissionSettings_ID,Commission_Product_ID,Created,CreatedBy,IsActive,IsCreateShareForOwnRevenue,IsSubtractLowerLevelCommissionFromBase,Name,Description,PointsPrecision,Updated,UpdatedBy) VALUES (1000000,1000000,540010,540420,TO_TIMESTAMP('2021-07-08 20:06:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Ohne Provisionsvereinbarung','0%-Provisionseinstellungen für Geschäftspartner, die Teil einer Provisionshierarchie sind, aber keine Provisionen erhalten. Wenn die zugehörigen Vertragsbedingungen aktiv und fertiggestellt sind, dann erstellt metasfresh zu diesen Geschäftspartnern Buchauzug-Datensätze.',2,TO_TIMESTAMP('2021-07-08 20:06:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-08T17:06:59.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_HierarchyCommissionSettings SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-07-08 20:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_HierarchyCommissionSettings_ID=540010
;

-- 2021-07-08T17:07:06.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_CommissionSettingsLine (AD_Client_ID,AD_Org_ID,C_CommissionSettingsLine_ID,C_HierarchyCommissionSettings_ID,Created,CreatedBy,IsActive,IsExcludeBPGroup,IsExcludeProductCategory,PercentOfBasePoints,SeqNo,Updated,UpdatedBy) VALUES (1000000,0,540005,540010,TO_TIMESTAMP('2021-07-08 20:07:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N',0,10,TO_TIMESTAMP('2021-07-08 20:07:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-08T17:08:04.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Flatrate_Conditions (AD_Client_ID,AD_Org_ID,C_Flatrate_Conditions_ID,C_HierarchyCommissionSettings_ID,ClearingAmtBaseOn,Created,CreatedBy,DocAction,DocStatus,InvoiceRule,IsActive,IsClosingWithActualSum,IsClosingWithCorrectionSum,IsCorrectionAmtAtClosing,IsCreateNoInvoice,IsFreeOfCharge,IsManualPrice,IsSimulation,Margin_Max,Margin_Min,Name,Processed,Processing,Type_Clearing,Type_Conditions,Type_Flatrate,Updated,UpdatedBy) VALUES (1000000,1000000,540047,540010,'FlatrateAmount',TO_TIMESTAMP('2021-07-08 20:08:04','YYYY-MM-DD HH24:MI:SS'),100,'CO','DR','I','Y','N','N','N','N','N','N','N',0.00,0.00,'Ohne Provisionsvereinbarung','N','N','EX','Commission','NONE',TO_TIMESTAMP('2021-07-08 20:08:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-08T17:08:09.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Flatrate_Conditions SET C_Flatrate_Transition_ID=1000003,Updated=TO_TIMESTAMP('2021-07-08 20:08:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Flatrate_Conditions_ID=540047
;
-- NOTE: if this UPDATE fails, you have a really long-time-running metasfresh instance.
-- You can then insert the missing c_flatrate_conditions record with 
--
-- INSERT INTO public.c_flatrate_transition (ad_client_id, ad_org_id, c_flatrate_conditions_next_id, c_flatrate_transition_id, created, createdby, description, isactive, isautocompletenewterm, isnotifyuserincharge, name, termduration, termdurationunit, termofnotice, updated, updatedby, docaction, docstatus, processing, termofnoticeunit, processed, c_calendar_contract_id, deliveryintervalunit, deliveryinterval, endswithcalendaryear, extensiontype) 
-- select 1000000, 1000000, null, 1000003, '2015-07-15 11:37:45.000000', 100, null, 'Y', 'Y', 'N', '1 Jahr, autom. verlängern', 1, 'year', 0, '2015-07-15 11:37:54.000000', 100, 'RE', 'CO', 'N', 'day', 'Y', 1000000, null, 0, 'N', 'EO'
-- where not exists (select 1 from public.c_flatrate_transition where c_flatrate_transition_id=1000003)
-- ;
--
-- If that insert fails, because there is already a c_flatrate_conditions with name "1 Jahr, autom. verlängern", then you can do the following,
-- where 1000002 is that existing record's c_flatrate_transition_id:
-- begin;
-- update c_flatrate_conditions set c_flatrate_transition_ID=1000003 where c_flatrate_transition_id=1000002;
-- update c_contract_change set c_flatrate_transition_ID=1000003 where c_flatrate_transition_id=1000002;
-- update c_flatrate_matching set c_flatrate_transition_ID=1000003 where c_flatrate_transition_id=1000002;
-- update c_flatrate_transition set c_flatrate_transition_ID=1000003 where c_flatrate_transition_id=1000002;
-- commit;
--


-- 2021-07-08T17:08:13.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Flatrate_Conditions SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-07-08 20:08:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Flatrate_Conditions_ID=540047
;

-- 2021-07-08T17:09:01.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Process (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,Priority,Processed,Processing,Record_ID,Updated,UpdatedBy,WF_Initial_User_ID,WFState) VALUES (1000000,1000000,540311,100,540197,101,540023,TO_TIMESTAMP('2021-07-08 20:09:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'N','N',540047,TO_TIMESTAMP('2021-07-08 20:09:00','YYYY-MM-DD HH24:MI:SS'),100,100,'ON')
;

-- 2021-07-08T17:09:01.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Process SET AD_Issue_ID=NULL, AD_Table_ID=540311, AD_User_ID=100, AD_WF_Responsible_ID=101, AD_Workflow_ID=540023, Priority=0, Processed='Y', Record_ID=540047, TextMsg=NULL, WF_Initial_User_ID=100, WFState='CC',Updated=TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Process_ID=540197
;

-- 2021-07-08T17:09:01.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Activity (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_Activity_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,Priority,Processed,Processing,Record_ID,Updated,UpdatedBy,WFState) VALUES (1000000,1000000,540311,100,540522,540080,540197,101,540023,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'Y','N',540047,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'CC')
;

-- 2021-07-08T17:09:01.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Activity (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_Activity_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,Priority,Processed,Processing,Record_ID,TextMsg,Updated,UpdatedBy,WFState) VALUES (1000000,1000000,540311,100,540523,540079,540197,101,540023,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'Y','N',540047,'Contract Terms 540047',TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'CC')
;

-- 2021-07-08T17:09:01.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Activity (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_Activity_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,Priority,Processed,Processing,Record_ID,TextMsg,Updated,UpdatedBy,WFState) VALUES (1000000,1000000,540311,100,540524,540078,540197,101,540023,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'Y','N',540047,'Contract Terms 540047',TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'CC')
;

-- 2021-07-08T17:09:01.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_EventAudit (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_EventAudit_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,Created,CreatedBy,ElapsedTimeMS,EventType,IsActive,Record_ID,Updated,UpdatedBy,WFState) VALUES (1000000,0,540311,100,540652,540080,540197,101,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,53,'PC','Y',540047,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'ON')
;

-- 2021-07-08T17:09:01.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_EventAudit (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_EventAudit_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,Created,CreatedBy,ElapsedTimeMS,EventType,IsActive,Record_ID,Updated,UpdatedBy,WFState) VALUES (1000000,0,540311,100,540653,540080,540197,101,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,58,'SC','Y',540047,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'OR')
;

-- 2021-07-08T17:09:01.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_EventAudit (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_EventAudit_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,Created,CreatedBy,ElapsedTimeMS,EventType,IsActive,Record_ID,Updated,UpdatedBy,WFState) VALUES (1000000,0,540311,100,540654,540080,540197,101,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,70,'PX','Y',540047,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'CC')
;

-- 2021-07-08T17:09:01.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_EventAudit (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_EventAudit_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,Created,CreatedBy,ElapsedTimeMS,EventType,IsActive,Record_ID,Updated,UpdatedBy,WFState) VALUES (1000000,0,540311,100,540655,540079,540197,101,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,0,'PC','Y',540047,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'ON')
;

-- 2021-07-08T17:09:02.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_EventAudit (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_EventAudit_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,Created,CreatedBy,ElapsedTimeMS,EventType,IsActive,Record_ID,Updated,UpdatedBy,WFState) VALUES (1000000,0,540311,100,540656,540079,540197,101,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,0,'SC','Y',540047,TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),100,'OR')
;

-- 2021-07-08T17:09:02.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_EventAudit (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_EventAudit_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,Created,CreatedBy,ElapsedTimeMS,EventType,IsActive,Record_ID,TextMsg,Updated,UpdatedBy,WFState) VALUES (1000000,0,540311,100,540657,540079,540197,101,TO_TIMESTAMP('2021-07-08 20:09:02','YYYY-MM-DD HH24:MI:SS'),100,43,'PX','Y',540047,'Contract Terms 540047',TO_TIMESTAMP('2021-07-08 20:09:02','YYYY-MM-DD HH24:MI:SS'),100,'CC')
;

-- 2021-07-08T17:09:02.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_EventAudit (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_EventAudit_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,Created,CreatedBy,ElapsedTimeMS,EventType,IsActive,Record_ID,Updated,UpdatedBy,WFState) VALUES (1000000,0,540311,100,540658,540078,540197,101,TO_TIMESTAMP('2021-07-08 20:09:02','YYYY-MM-DD HH24:MI:SS'),100,0,'PC','Y',540047,TO_TIMESTAMP('2021-07-08 20:09:02','YYYY-MM-DD HH24:MI:SS'),100,'ON')
;

-- 2021-07-08T17:09:02.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_EventAudit (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_EventAudit_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,Created,CreatedBy,ElapsedTimeMS,EventType,IsActive,Record_ID,Updated,UpdatedBy,WFState) VALUES (1000000,0,540311,100,540659,540078,540197,101,TO_TIMESTAMP('2021-07-08 20:09:02','YYYY-MM-DD HH24:MI:SS'),100,0,'SC','Y',540047,TO_TIMESTAMP('2021-07-08 20:09:02','YYYY-MM-DD HH24:MI:SS'),100,'OR')
;

-- 2021-07-08T17:09:02.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_EventAudit (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_User_ID,AD_WF_EventAudit_ID,AD_WF_Node_ID,AD_WF_Process_ID,AD_WF_Responsible_ID,Created,CreatedBy,ElapsedTimeMS,EventType,IsActive,Record_ID,TextMsg,Updated,UpdatedBy,WFState) VALUES (1000000,0,540311,100,540660,540078,540197,101,TO_TIMESTAMP('2021-07-08 20:09:02','YYYY-MM-DD HH24:MI:SS'),100,23,'PX','Y',540047,'Contract Terms 540047',TO_TIMESTAMP('2021-07-08 20:09:02','YYYY-MM-DD HH24:MI:SS'),100,'CC')
;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

-- 2021-07-08T17:09:01.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Flatrate_Conditions SET DocStatus='IP',Updated=TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Flatrate_Conditions_ID=540047
;

-- 2021-07-08T17:09:01.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Flatrate_Conditions SET DocAction='RE', DocStatus='CO', Processed='Y',Updated=TO_TIMESTAMP('2021-07-08 20:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Flatrate_Conditions_ID=540047
;

