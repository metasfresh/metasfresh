/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.settings.interceptor.InvoicingGroupNotFound
-- 2024-05-21T11:09:09.073Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545411,0,TO_TIMESTAMP('2024-05-21 14:09:08.697','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Keine passende Rechnungsgruppe gefunden.','E',TO_TIMESTAMP('2024-05-21 14:09:08.697','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.InvoicingGroupNotFound')
;

-- 2024-05-21T11:09:09.081Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545411 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.InvoicingGroupNotFound
-- 2024-05-21T11:11:31.197Z
UPDATE AD_Message_Trl SET MsgText='No matching invoice group found.',Updated=TO_TIMESTAMP('2024-05-21 14:11:31.197','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545411
;

