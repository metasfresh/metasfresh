/*
 * #%L
 * de.metas.manufacturing
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

-- Value: de.metas.manufacturing.callExternalSystem.NOT_AN_EXTERNAL_SYSTEM_ERR
-- 2024-07-26T08:13:33.266Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545440,0,TO_TIMESTAMP('2024-07-26 11:13:32','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Die gescannte Ressource hat kein externes System angeschlossen!','I',TO_TIMESTAMP('2024-07-26 11:13:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.manufacturing.callExternalSystem.NOT_AN_EXTERNAL_SYSTEM_ERR')
;

-- 2024-07-26T08:13:33.275Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545440 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.manufacturing.callExternalSystem.NOT_AN_EXTERNAL_SYSTEM_ERR
-- 2024-07-26T08:13:42.637Z
UPDATE AD_Message_Trl SET MsgText='Scanned resource does not have an external system attached!',Updated=TO_TIMESTAMP('2024-07-26 11:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545440
;

-- Value: PPOrderIssuePlanCreateCommand.CannotFullyAllocated
-- 2024-07-26T08:17:15.072Z
UPDATE AD_Message SET MsgText='Nicht genug Rohstoffe für {0} in {1} gefunden. Wir brauchen immer noch {2} von {3} erforderlich.',Updated=TO_TIMESTAMP('2024-07-26 11:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545084
;

-- Value: PPOrderIssuePlanCreateCommand.CannotFullyAllocated
-- 2024-07-26T08:17:22.002Z
UPDATE AD_Message_Trl SET MsgText='Nicht genug Rohstoffe für {0} in {1} gefunden. Wir brauchen immer noch {2} von {3} erforderlich.',Updated=TO_TIMESTAMP('2024-07-26 11:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545084
;

-- Value: PPOrderIssuePlanCreateCommand.CannotFullyAllocated
-- 2024-07-26T08:18:03.706Z
UPDATE AD_Message_Trl SET MsgText='Not enough raw materials found for {0} in {1}. We still need {2} of {3} required.',Updated=TO_TIMESTAMP('2024-07-26 11:18:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545084
;

-- Value: PPOrderIssuePlanCreateCommand.CannotFullyAllocated
-- 2024-07-26T08:18:15.724Z
UPDATE AD_Message_Trl SET MsgText='Nicht genug Rohstoffe für {0} in {1} gefunden. Wir brauchen immer noch {2} von {3} erforderlich.',Updated=TO_TIMESTAMP('2024-07-26 11:18:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545084
;

-- Value: PPOrderIssuePlanCreateCommand.CannotFullyAllocated
-- 2024-07-26T08:18:21.613Z
UPDATE AD_Message_Trl SET MsgText='Nicht genug Rohstoffe für {0} in {1} gefunden. Wir brauchen immer noch {2} von {3} erforderlich.',Updated=TO_TIMESTAMP('2024-07-26 11:18:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545084
;

