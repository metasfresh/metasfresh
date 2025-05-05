-- Run mode: SWING_CLIENT

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Pauschale - Vertragsperiode
-- Column: C_OrderLine.C_Flatrate_Term_ID
-- 2024-07-23T10:13:17.206Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2024-07-23 10:13:17.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=601388
;

-- Run mode: WEBUI

-- 2024-07-23T10:55:21.048Z
UPDATE C_DocType SET Name='Rahmenauftrag',Updated=TO_TIMESTAMP('2024-07-23 10:55:21.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541062
;

-- 2024-07-23T10:55:21.121Z
UPDATE C_DocType_Trl trl SET Name='Rahmenauftrag' WHERE C_DocType_ID=541062 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- 2024-07-23T10:55:22.422Z
UPDATE C_DocType SET PrintName='Rahmenauftrag',Updated=TO_TIMESTAMP('2024-07-23 10:55:22.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541062
;

-- 2024-07-23T10:55:22.427Z
UPDATE C_DocType_Trl trl SET PrintName='Rahmenauftrag' WHERE C_DocType_ID=541062 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- 2024-07-23T10:56:24.947Z
UPDATE C_DocType_Trl SET PrintName='Frame Order SO',Updated=TO_TIMESTAMP('2024-07-23 10:56:24.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541062
;

-- 2024-07-23T10:58:10.280Z
UPDATE C_DocType_Trl SET Name='Frame Order SO',Updated=TO_TIMESTAMP('2024-07-23 10:58:10.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541062
;
