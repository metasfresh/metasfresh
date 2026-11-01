-- Run mode: SWING_CLIENT

-- Reference: Mapping Attribut Wert
-- Value: ProductValue
-- ValueName: ProductValue
-- 2026-04-17T08:38:44.673Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542001,544211,TO_TIMESTAMP('2026-04-17 08:38:44.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Paketinhalt Produkt Suchschlüssel',TO_TIMESTAMP('2026-04-17 08:38:44.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ProductValue','ProductValue')
;

-- 2026-04-17T08:38:44.685Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544211 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Mapping Attribut Wert
-- Value: ShipmentOrderItemId
-- ValueName: ShipmentOrderItemId
-- 2026-04-17T08:38:55.883Z
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2026-04-17 08:38:55.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544033
;

-- Reference Item: Mapping Attribut Wert -> ProductValue_ProductValue
-- 2026-04-17T08:39:20.005Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Parcel Content Product Value',Updated=TO_TIMESTAMP('2026-04-17 08:39:20.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544211
;

-- 2026-04-17T08:39:20.006Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Mapping Attribut Wert -> ProductValue_ProductValue
-- 2026-04-17T08:39:20.638Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-17 08:39:20.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544211
;

-- Reference Item: Mapping Attribut Wert -> ProductValue_ProductValue
-- 2026-04-17T08:39:29.929Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-17 08:39:29.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544211
;

-- Reference: Mapping Attribut Wert
-- Value: CustomsTariff
-- ValueName: CustomsTariff
-- 2026-04-17T08:41:55.333Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542001,544212,TO_TIMESTAMP('2026-04-17 08:41:55.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Paketinhalt Zolltarifnummer',TO_TIMESTAMP('2026-04-17 08:41:55.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CustomsTariff','CustomsTariff')
;

-- 2026-04-17T08:41:55.344Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544212 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Mapping Attribut Wert
-- Value: ReceiverCountryCode
-- ValueName: ReceiverCountryCode
-- 2026-04-17T08:42:13.592Z
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2026-04-17 08:42:13.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544019
;

-- Reference Item: Mapping Attribut Wert -> CustomsTariff_CustomsTariff
-- 2026-04-17T08:42:42.583Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Parcel Content Customs Tariff',Updated=TO_TIMESTAMP('2026-04-17 08:42:42.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544212
;

-- 2026-04-17T08:42:42.584Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Mapping Attribut Wert -> CustomsTariff_CustomsTariff
-- 2026-04-17T08:42:43.030Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-17 08:42:43.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544212
;

-- Reference Item: Mapping Attribut Wert -> CustomsTariff_CustomsTariff
-- 2026-04-17T08:42:44.483Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-17 08:42:44.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544212
;

