-- Run mode: SWING_CLIENT

-- Value: ERR_MISSING_PRODUCT
-- 2025-09-16T16:14:46.435Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545575,0,TO_TIMESTAMP('2025-09-16 16:14:46.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','MANDATORY_PRODUCT_MISSING','Y','Pflichtfeld fehlt: Produkt','E',TO_TIMESTAMP('2025-09-16 16:14:46.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_MISSING_PRODUCT')
;

-- 2025-09-16T16:14:46.447Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545575 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_MISSING_PRODUCT
-- 2025-09-16T16:14:56.590Z
UPDATE AD_Message_Trl SET MsgText='Missing mandatory field: Product',Updated=TO_TIMESTAMP('2025-09-16 16:14:56.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545575
;

-- 2025-09-16T16:14:56.590Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_CURRENCY_NOT_FOUND
-- 2025-09-16T16:19:14.888Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545576,0,TO_TIMESTAMP('2025-09-16 16:19:14.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','CURRENCY_NOT_FOUND','Y','Währung nicht gefunden','E',TO_TIMESTAMP('2025-09-16 16:19:14.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_CURRENCY_NOT_FOUND')
;

-- 2025-09-16T16:19:14.891Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545576 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_CURRENCY_NOT_FOUND
-- 2025-09-16T16:19:29.607Z
UPDATE AD_Message_Trl SET MsgText='Currency not found',Updated=TO_TIMESTAMP('2025-09-16 16:19:29.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545576
;

-- 2025-09-16T16:19:29.608Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_PRICING_SYSTEM_NOT_FOUND
-- 2025-09-16T16:21:03.048Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545577,0,TO_TIMESTAMP('2025-09-16 16:21:02.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','PRICING_SYSTEM_NOT_FOUND','Y','Preissystem nicht gefunden','E',TO_TIMESTAMP('2025-09-16 16:21:02.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_PRICING_SYSTEM_NOT_FOUND')
;

-- 2025-09-16T16:21:03.049Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545577 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_PRICING_SYSTEM_NOT_FOUND
-- 2025-09-16T16:21:11.140Z
UPDATE AD_Message_Trl SET MsgText='Pricing system not found',Updated=TO_TIMESTAMP('2025-09-16 16:21:11.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545577
;

-- 2025-09-16T16:21:11.142Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_TAX_CATEGORY_NOT_FOUND
-- 2025-09-16T16:22:30.406Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545578,0,TO_TIMESTAMP('2025-09-16 16:22:30.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','TAX_CATEGORY_NOT_FOUND','Y','Steuerkategorie nicht gefunden','E',TO_TIMESTAMP('2025-09-16 16:22:30.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_TAX_CATEGORY_NOT_FOUND')
;

-- 2025-09-16T16:22:30.407Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545578 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_TAX_CATEGORY_NOT_FOUND
-- 2025-09-16T16:22:36.754Z
UPDATE AD_Message_Trl SET MsgText='Tax category not found',Updated=TO_TIMESTAMP('2025-09-16 16:22:36.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545578
;

-- 2025-09-16T16:22:36.754Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_Bill_Location_Inactive
-- 2025-09-16T16:24:35.310Z
UPDATE AD_Message SET ErrorCode='BILL_LOCATION_INACTIVE',Updated=TO_TIMESTAMP('2025-09-16 16:24:35.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=543731
;

-- Value: ERR_C_BPartner_Location_Effective_Inactive
-- 2025-09-16T16:25:38.761Z
UPDATE AD_Message SET ErrorCode='BPARTNER_LOCATION_INACTIVE',Updated=TO_TIMESTAMP('2025-09-16 16:25:38.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=543732
;

-- Value: ERR_DropShip_Location_Inactive
-- 2025-09-16T16:26:08.426Z
UPDATE AD_Message SET ErrorCode='DROPSHIP_LOCATION_INACTIVE',Updated=TO_TIMESTAMP('2025-09-16 16:26:08.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=543733
;

-- Value: ERR_HandOver_Location_Inactive
-- 2025-09-16T16:26:35.604Z
UPDATE AD_Message SET ErrorCode='HANDOVER_LOCATION_INACTIVE',Updated=TO_TIMESTAMP('2025-09-16 16:26:35.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=543734
;

-- Value: ERR_ITEM_CAPACITY_NOT_FOUND
-- 2025-09-16T16:27:34.812Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545579,0,TO_TIMESTAMP('2025-09-16 16:27:34.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','ITEM_CAPACITY_NOT_FOUND','Y','Artikelkapazität nicht gefunden','E',TO_TIMESTAMP('2025-09-16 16:27:34.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_ITEM_CAPACITY_NOT_FOUND')
;

-- 2025-09-16T16:27:34.813Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545579 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_ITEM_CAPACITY_NOT_FOUND
-- 2025-09-16T16:27:46.093Z
UPDATE AD_Message_Trl SET MsgText='Item capacity not found',Updated=TO_TIMESTAMP('2025-09-16 16:27:46.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545579
;

-- 2025-09-16T16:27:46.093Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: NoUOMConversion_Params
-- 2025-09-16T16:30:35.526Z
UPDATE AD_Message SET ErrorCode='UOM_CONVERSION_RULE_MISSING',Updated=TO_TIMESTAMP('2025-09-16 16:30:35.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=544950
;

-- Value: ERR_PRICE_LIST_NOT_FOUND
-- 2025-09-16T16:44:40.671Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545580,0,TO_TIMESTAMP('2025-09-16 16:44:40.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','PRICE_LIST_NOT_FOUND','Y','Preisliste nicht gefunden für Preissystem {0}, Lieferadresse {1}, Währung {2}','E',TO_TIMESTAMP('2025-09-16 16:44:40.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_PRICE_LIST_NOT_FOUND')
;

-- 2025-09-16T16:44:40.679Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545580 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_PRICE_LIST_NOT_FOUND
-- 2025-09-16T16:44:51.668Z
UPDATE AD_Message_Trl SET MsgText='Price list not found for Pricing System {0}, Ship To Location {1}, Currency {2}',Updated=TO_TIMESTAMP('2025-09-16 16:44:51.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545580
;

-- 2025-09-16T16:44:51.668Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_ORDER_LINE_CANDIDATES_MISSING
-- 2025-09-16T16:47:05.244Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545581,0,TO_TIMESTAMP('2025-09-16 16:47:05.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','ORDER_LINE_CANDIDATES_MISSING','Y','Keine Auftragszeilen-Kandidaten für die angefragte Abfrage {0} gefunden','E',TO_TIMESTAMP('2025-09-16 16:47:05.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_ORDER_LINE_CANDIDATES_MISSING')
;

/*
 * #%L
 * de.metas.salescandidate.base
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

-- 2025-09-16T16:47:05.245Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545581 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_ORDER_LINE_CANDIDATES_MISSING
-- 2025-09-16T16:47:20.598Z
UPDATE AD_Message_Trl SET MsgText='Missing order line candidates for the given query {0}',Updated=TO_TIMESTAMP('2025-09-16 16:47:20.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545581
;

-- 2025-09-16T16:47:20.599Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

