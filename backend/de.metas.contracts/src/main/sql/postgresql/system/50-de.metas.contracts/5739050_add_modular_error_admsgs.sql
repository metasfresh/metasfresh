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

-- Value: de.metas.contracts.modular.ModularContractService.MoreThanOneModularPurchaseContractCandidateFound
-- 2024-11-12T14:51:42.782Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545479,0,TO_TIMESTAMP('2024-11-12 15:51:42.56','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Es wurde mehr als ein modularer Einkaufsvertragkandidat gefunden. Bitte setzen Sie einen in der Auftragsposition.','E',TO_TIMESTAMP('2024-11-12 15:51:42.56','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.ModularContractService.MoreThanOneModularPurchaseContractCandidateFound')
;

-- 2024-11-12T14:51:42.789Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545479 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.ModularContractService.MoreThanOneModularPurchaseContractCandidateFound
-- 2024-11-12T14:52:59.835Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='More than one modular purchase contract candidate found. Pls set one in the orderline',Updated=TO_TIMESTAMP('2024-11-12 15:52:59.835','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545479
;

-- Value: de.metas.contracts.modular.ModularContractService.MoreThanOneModularPurchaseContractCandidateFound
-- 2024-11-12T14:53:00.667Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-12 15:53:00.667','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545479
;

-- Value: de.metas.contracts.modular.ModularContractService.MoreThanOneModularPurchaseContractCandidateFound
-- 2024-11-12T14:53:01.983Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-12 15:53:01.983','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545479
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsUOMEach
-- 2024-11-12T14:56:13.552Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545480,0,TO_TIMESTAMP('2024-11-12 15:56:13.424','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Dieser Vertragsbaustein Typ benötigt ein Produkt mit Maßeinheit Stück','E',TO_TIMESTAMP('2024-11-12 15:56:13.424','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsUOMEach')
;

-- 2024-11-12T14:56:13.554Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545480 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsUOMEach
-- 2024-11-12T14:56:57.612Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='This computing method requires a product with UOM EACH',Updated=TO_TIMESTAMP('2024-11-12 15:56:57.612','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545480
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsUOMEach
-- 2024-11-12T14:56:58.292Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-12 15:56:58.292','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545480
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsUOMEach
-- 2024-11-12T14:57:19.889Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-12 15:57:19.889','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545480
;

