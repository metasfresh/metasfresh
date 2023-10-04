/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

-- Value: de.metas.contracts.modular.workpackage.impl.SalesProFormaModularContractLogsHandler.CompleteLogDescription
-- 2023-10-04T07:30:47.830Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545347, 0, TO_TIMESTAMP('2023-10-04 09:30:47.717', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'ProForma Modularer Vertrag für Produkt {0} mit der Menge {1} wurde fertiggestellt.', 'I', TO_TIMESTAMP('2023-10-04 09:30:47.717', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.workpackage.impl.SalesProFormaModularContractLogsHandler.CompleteLogDescription')
;

-- 2023-10-04T07:30:47.833Z
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545347
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.workpackage.impl.SalesProFormaModularContractLogsHandler.CompleteLogDescription
-- 2023-10-04T07:30:57.092Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-04 09:30:57.092', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545347
;

-- Value: de.metas.contracts.modular.workpackage.impl.SalesProFormaModularContractLogsHandler.CompleteLogDescription
-- 2023-10-04T07:30:58.945Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-04 09:30:58.945', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545347
;

-- Value: de.metas.contracts.modular.workpackage.impl.SalesProFormaModularContractLogsHandler.CompleteLogDescription
-- 2023-10-04T07:32:16.556Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='ProForma Modular Contract for Product {0} with Quantity {1} was completed.', Updated=TO_TIMESTAMP('2023-10-04 09:32:16.556', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545347
;

-- Value: de.metas.contracts.modular.workpackage.IModularContractLogHandler.CompleteLogDescription
-- 2023-10-04T07:37:14.376Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545348, 0, TO_TIMESTAMP('2023-10-04 09:37:14.263', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', '{0} für Produkt {1} mit der Menge {2} wurde fertiggestellt.', 'I', TO_TIMESTAMP('2023-10-04 09:37:14.263', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.workpackage.IModularContractLogHandler.CompleteLogDescription')
;

-- 2023-10-04T07:37:14.380Z
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545348
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.workpackage.IModularContractLogHandler.CompleteLogDescription
-- 2023-10-04T07:37:21.852Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-04 09:37:21.852', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545348
;

-- Value: de.metas.contracts.modular.workpackage.IModularContractLogHandler.CompleteLogDescription
-- 2023-10-04T07:37:22.718Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-04 09:37:22.718', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545348
;

-- Value: de.metas.contracts.modular.workpackage.IModularContractLogHandler.CompleteLogDescription
-- 2023-10-04T07:37:55.664Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='{0} for Product {1} with Quantity {2} was completed.', Updated=TO_TIMESTAMP('2023-10-04 09:37:55.664', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545348
;

