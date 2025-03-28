/*
 * #%L
 * de.metas.contracts
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

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasBillableLogs
-- 2025-03-28T09:56:07.649Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545513,0,TO_TIMESTAMP('2025-03-28 10:56:07.515','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Zum Vertrag mit ID {0} existieren noch abrechenbare Logs.','I',TO_TIMESTAMP('2025-03-28 10:56:07.515','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.ModularContractService.ContractHasBillableLogs')
;

-- 2025-03-28T09:56:07.660Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545513 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasBillableLogs
-- 2025-03-28T09:56:52.017Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The contract with ID {0} still has invoicable logs.',Updated=TO_TIMESTAMP('2025-03-28 10:56:52.017','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545513
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasBillableLogs
-- 2025-03-28T09:56:53.144Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-03-28 10:56:53.144','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545513
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasBillableLogs
-- 2025-03-28T09:56:55.385Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-03-28 10:56:55.385','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545513
;

-- Value: de.metas.contracts.modular.ModularContractService.NotEligibleModularContractSet
-- 2025-03-28T10:04:39.476Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545514,0,TO_TIMESTAMP('2025-03-28 11:04:39.341','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Der modulare Einkaufsvertrag in Auftragsposion {0} ist nicht mehr zulässig.','I',TO_TIMESTAMP('2025-03-28 11:04:39.341','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.ModularContractService.NotEligibleModularContractSet')
;

-- 2025-03-28T10:04:39.477Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545514 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.ModularContractService.NotEligibleModularContractSet
-- 2025-03-28T10:05:50.230Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The modular purchasecontract in orderline {0} isn''t eligible anymore.',Updated=TO_TIMESTAMP('2025-03-28 11:05:50.229','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545514
;

-- Value: de.metas.contracts.modular.ModularContractService.NotEligibleModularContractSet
-- 2025-03-28T10:05:51.281Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-03-28 11:05:51.281','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545514
;

-- Value: de.metas.contracts.modular.ModularContractService.NotEligibleModularContractSet
-- 2025-03-28T10:05:52.716Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-03-28 11:05:52.716','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545514
;

-- Value: de.metas.contracts.modular.ModularContractService.NotEligibleModularContractSet
-- 2025-03-28T10:05:52.716Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-03-28 11:05:52.716','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545514
;

-- Value: Different_Contracts
-- 2025-03-28T11:03:17.162Z
UPDATE AD_Message SET MsgText='Auswahl beinhaltet nicht modulare oder geschlossene Verträge!',Updated=TO_TIMESTAMP('2025-03-28 12:03:17.16','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545426
;

-- 2025-03-28T11:03:17.175Z
UPDATE AD_Message_Trl trl SET MsgText='Auswahl beinhaltet nicht modulare oder geschlossene Verträge!' WHERE AD_Message_ID=545426 AND AD_Language='de_DE'
;

-- Value: Different_Contracts
-- 2025-03-28T11:03:29.887Z
UPDATE AD_Message_Trl SET MsgText='Auswahl beinhaltet nicht modulare oder geschlossene Verträge!',Updated=TO_TIMESTAMP('2025-03-28 12:03:29.887','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545426
;

-- Value: Different_Contracts
-- 2025-03-28T11:03:43.164Z
UPDATE AD_Message_Trl SET MsgText='Selection contains not modular types or closed contracts!',Updated=TO_TIMESTAMP('2025-03-28 12:03:43.164','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545426
;
