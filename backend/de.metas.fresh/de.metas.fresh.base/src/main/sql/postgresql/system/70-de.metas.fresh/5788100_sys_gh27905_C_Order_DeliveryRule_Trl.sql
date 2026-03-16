-- Run mode: SWING_CLIENT

-- Reference: C_Order DeliveryRule
-- Value: F
-- ValueName: Force
-- 2026-02-12T20:03:08.575Z
UPDATE AD_Ref_List SET Description='Lieferung wird unabhängig von Verfügbarkeit und Standardregeln ausgelöst.',Updated=TO_TIMESTAMP('2026-02-12 20:03:08.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=678
;

-- 2026-02-12T20:03:08.649Z
UPDATE AD_Ref_List_Trl trl SET Description='Lieferung wird unabhängig von Verfügbarkeit und Standardregeln ausgelöst.' WHERE AD_Ref_List_ID=678 AND AD_Language='de_DE'
;

-- Reference Item: C_Order DeliveryRule -> F_Force
-- 2026-02-12T20:03:31.727Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 20:03:31.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=678
;

-- Reference Item: C_Order DeliveryRule -> F_Force
-- 2026-02-12T20:04:10.686Z
UPDATE AD_Ref_List_Trl SET Description='Lieferung wird unabhängig von Verfügbarkeit und Standardregeln ausgelöst.',Updated=TO_TIMESTAMP('2026-02-12 20:04:10.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=678
;

-- 2026-02-12T20:04:10.757Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> F_Force
-- 2026-02-12T20:04:13.230Z
UPDATE AD_Ref_List_Trl SET Description='Lieferung wird unabhängig von Verfügbarkeit und Standardregeln ausgelöst.',Updated=TO_TIMESTAMP('2026-02-12 20:04:13.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Ref_List_ID=678
;

-- 2026-02-12T20:04:13.307Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='it_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> F_Force
-- 2026-02-12T20:04:24.759Z
UPDATE AD_Ref_List_Trl SET Description='Creates delivery regardless of availability and default rules.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 20:04:24.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=678
;

-- 2026-02-12T20:04:24.818Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> F_Force
-- 2026-02-12T20:04:32.317Z
UPDATE AD_Ref_List_Trl SET Description='Lieferung wird unabhängig von Verfügbarkeit und Standardregeln ausgelöst.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 20:04:32.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=678
;

-- 2026-02-12T20:04:32.374Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> F_Force
-- 2026-02-12T20:04:39.213Z
UPDATE AD_Ref_List_Trl SET Description='Creates delivery regardless of availability and default rules.',Updated=TO_TIMESTAMP('2026-02-12 20:04:39.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=678
;

-- 2026-02-12T20:04:39.277Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: C_Order DeliveryRule
-- Value: M
-- ValueName: Manual
-- 2026-02-12T20:05:25.817Z
UPDATE AD_Ref_List SET Description='Lieferung wird nur manuell erstellt.',Updated=TO_TIMESTAMP('2026-02-12 20:05:25.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=791
;

-- 2026-02-12T20:05:25.872Z
UPDATE AD_Ref_List_Trl trl SET Description='Lieferung wird nur manuell erstellt.' WHERE AD_Ref_List_ID=791 AND AD_Language='de_DE'
;

-- Reference Item: C_Order DeliveryRule -> M_Manual
-- 2026-02-12T20:05:31.586Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 20:05:31.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=791
;

-- Reference Item: C_Order DeliveryRule -> M_Manual
-- 2026-02-12T20:05:36.911Z
UPDATE AD_Ref_List_Trl SET Description='Delivery is created manually only.',Updated=TO_TIMESTAMP('2026-02-12 20:05:36.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=791
;

-- 2026-02-12T20:05:36.973Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> M_Manual
-- 2026-02-12T20:05:42.693Z
UPDATE AD_Ref_List_Trl SET Description='Lieferung wird nur manuell erstellt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 20:05:42.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=791
;

-- 2026-02-12T20:05:42.756Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> M_Manual
-- 2026-02-12T20:05:52.869Z
UPDATE AD_Ref_List_Trl SET Description='Delivery is created manually only.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 20:05:52.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=791
;

-- 2026-02-12T20:05:52.925Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> M_Manual
-- 2026-02-12T20:05:59.641Z
UPDATE AD_Ref_List_Trl SET Description='Lieferung wird nur manuell erstellt.',Updated=TO_TIMESTAMP('2026-02-12 20:05:59.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Ref_List_ID=791
;

-- 2026-02-12T20:05:59.704Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='it_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> M_Manual
-- 2026-02-12T20:06:04.297Z
UPDATE AD_Ref_List_Trl SET Description='Lieferung wird nur manuell erstellt.',Updated=TO_TIMESTAMP('2026-02-12 20:06:04.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=791
;

-- 2026-02-12T20:06:04.359Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: C_Order DeliveryRule
-- Value: S
-- ValueName: MitNaechsterAbolieferung
-- 2026-02-12T20:10:46.367Z
UPDATE AD_Ref_List SET Description='Lieferung erfolgt zusammen mit der nächsten Abolieferung.',Updated=TO_TIMESTAMP('2026-02-12 20:10:46.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=540017
;

-- 2026-02-12T20:10:46.517Z
UPDATE AD_Ref_List_Trl trl SET Description='Lieferung erfolgt zusammen mit der nächsten Abolieferung.' WHERE AD_Ref_List_ID=540017 AND AD_Language='de_DE'
;

-- Reference Item: C_Order DeliveryRule -> S_MitNaechsterAbolieferung
-- 2026-02-12T20:11:00.547Z
UPDATE AD_Ref_List_Trl SET Description='Lieferung erfolgt zusammen mit der nächsten Abolieferung.',Updated=TO_TIMESTAMP('2026-02-12 20:11:00.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=540017
;

-- 2026-02-12T20:11:00.689Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> S_MitNaechsterAbolieferung
-- 2026-02-12T20:11:04.940Z
UPDATE AD_Ref_List_Trl SET Description='Lieferung erfolgt zusammen mit der nächsten Abolieferung.',Updated=TO_TIMESTAMP('2026-02-12 20:11:04.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Ref_List_ID=540017
;

-- 2026-02-12T20:11:05.087Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='it_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> S_MitNaechsterAbolieferung
-- 2026-02-12T20:11:17.203Z
UPDATE AD_Ref_List_Trl SET Description='Delivery is performed together with the next subscription delivery.', IsTranslated='Y', Name='With next subscription delivery',Updated=TO_TIMESTAMP('2026-02-12 20:11:17.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=540017
;

-- 2026-02-12T20:11:17.356Z
UPDATE AD_Ref_List base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> S_MitNaechsterAbolieferung
-- 2026-02-12T20:12:21.991Z
UPDATE AD_Ref_List_Trl SET Description='Lieferung erfolgt zusammen mit der nächsten Abolieferung.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 20:12:21.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=540017
;

-- 2026-02-12T20:12:22.144Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> S_MitNaechsterAbolieferung
-- 2026-02-12T20:12:33.866Z
UPDATE AD_Ref_List_Trl SET Description='Delivery is performed together with the next subscription delivery.', IsTranslated='Y', Name='With next subscription delivery',Updated=TO_TIMESTAMP('2026-02-12 20:12:33.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=540017
;

-- 2026-02-12T20:12:33.979Z
UPDATE AD_Ref_List base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_Order DeliveryRule -> S_MitNaechsterAbolieferung
-- 2026-02-12T20:12:40.016Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 20:12:40.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=540017
;

