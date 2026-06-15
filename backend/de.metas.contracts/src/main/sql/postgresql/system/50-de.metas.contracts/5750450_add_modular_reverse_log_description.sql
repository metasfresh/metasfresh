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

-- Value: de.metas.contracts.modular.modularContractReverseLogDescription
-- 2025-03-31T14:49:49.446Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545517,0,TO_TIMESTAMP('2025-03-31 16:49:49.33','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Modularer Vertrag für Produkt {0} mit der Menge {1} wurde storniert.','I',TO_TIMESTAMP('2025-03-31 16:49:49.33','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.modularContractReverseLogDescription')
;

-- 2025-03-31T14:49:49.448Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545517 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.modularContractReverseLogDescription
-- 2025-03-31T14:50:30.208Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-03-31 16:50:30.208','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545517
;

-- Value: de.metas.contracts.modular.modularContractReverseLogDescription
-- 2025-03-31T14:50:31.562Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-03-31 16:50:31.562','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545517
;

-- Value: de.metas.contracts.modular.modularContractReverseLogDescription
-- 2025-03-31T14:50:46.316Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Modular Contract for product {0} with the quantity {1} was reversed.',Updated=TO_TIMESTAMP('2025-03-31 16:50:46.316','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545517
;

