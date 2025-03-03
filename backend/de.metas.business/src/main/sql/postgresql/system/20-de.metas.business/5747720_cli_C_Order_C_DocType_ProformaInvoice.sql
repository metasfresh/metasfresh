/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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



-- 2025-02-25T16:08:47.050Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,541131,TO_TIMESTAMP('2025-02-25 17:08:47','YYYY-MM-DD HH24:MI:SS'),100,'SOO',1,'D',1000001,'N','N','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Proforma Rechnung','Proforma Rechnung',TO_TIMESTAMP('2025-02-25 17:08:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-02-25T16:08:47.055Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541131 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2025-02-25T16:08:47.057Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541131 AND rol.IsManual='N')
;

-- 2025-02-25T16:09:06.036Z
UPDATE C_DocType SET DocNoSequence_ID=545476,Updated=TO_TIMESTAMP('2025-02-25 17:09:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541131
;

-- 2025-02-25T16:09:34.427Z
UPDATE C_DocType SET DocSubType='ON',Updated=TO_TIMESTAMP('2025-02-25 17:09:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541131
;

-- 2025-02-25T16:09:43.750Z
UPDATE C_DocType SET IsCreateCounter='N',Updated=TO_TIMESTAMP('2025-02-25 17:09:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541131
;

-- 2025-02-25T16:17:50.751Z
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2025-02-25 17:17:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188325 WHERE C_DocType_ID=541131
;


-- 2025-02-25T16:23:41.278Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000010,Updated=TO_TIMESTAMP('2025-02-25 17:23:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188325 WHERE C_DocType_ID=541131
;
