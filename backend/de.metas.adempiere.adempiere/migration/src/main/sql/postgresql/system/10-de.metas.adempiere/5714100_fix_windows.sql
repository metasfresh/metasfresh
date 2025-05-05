-- Run mode: SWING_CLIENT

-- Tab: Dashboard Item translation(541172,de.metas.ui.web) -> Dashboard Item translation
-- Table: WEBUI_DashboardItem_Trl
-- 2023-12-20T14:08:44.181Z
UPDATE AD_Tab SET IsTranslationTab='N',Updated=TO_TIMESTAMP('2023-12-20 14:08:44.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=544114
;

-- Window: AdvCommision Docs View (internal), InternalName=540060 (Todo: Set Internal Name for UI testing)
-- 2023-12-20T14:09:15.585Z
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 14:09:15.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540060
;

-- Window: Provisionsart, InternalName=540057 (Todo: Set Internal Name for UI testing)
-- 2023-12-20T14:09:33.669Z
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 14:09:33.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540057
;

-- Name: Provisionsart
-- Action Type: W
-- Window: Provisionsart(540057,de.metas.commission_legacy)
-- 2023-12-20T14:09:33.685Z
UPDATE AD_Menu SET Description=NULL, IsActive='N', Name='Provisionsart',Updated=TO_TIMESTAMP('2023-12-20 14:09:33.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540129
;

-- Window: Provisionsabrechnung, InternalName=540035 (Todo: Set Internal Name for UI testing)
-- 2023-12-20T14:09:49.293Z
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 14:09:49.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540035
;

-- Window: Provisionsdatensatz Warteschlange, InternalName=540032 (Todo: Set Internal Name for UI testing)
-- 2023-12-20T14:10:05.579Z
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 14:10:05.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540032
;

-- Name: Provisionsdatensatz Warteschlange
-- Action Type: W
-- Window: Provisionsdatensatz Warteschlange(540032,de.metas.commission_legacy)
-- 2023-12-20T14:10:05.586Z
UPDATE AD_Menu SET Description=NULL, IsActive='N', Name='Provisionsdatensatz Warteschlange',Updated=TO_TIMESTAMP('2023-12-20 14:10:05.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540084
;

-- 2023-12-20T14:10:05.590Z
UPDATE AD_Menu_Trl trl SET Name='Provisionsdatensatz Warteschlange' WHERE AD_Menu_ID=540084 AND AD_Language='de_DE'
;

-- Window: Vergütungsgruppe - Prognose, InternalName=540084 (Todo: Set Internal Name for UI testing)
-- 2023-12-20T14:10:19.622Z
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 14:10:19.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540084
;

-- Name: Vergütungsgruppe - Prognose
-- Action Type: W
-- Window: Vergütungsgruppe - Prognose(540084,de.metas.commission_legacy)
-- 2023-12-20T14:10:19.628Z
UPDATE AD_Menu SET Description=NULL, IsActive='N', Name='Vergütungsgruppe - Prognose',Updated=TO_TIMESTAMP('2023-12-20 14:10:19.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540212
;

-- Window: Vergütungsgruppe, InternalName=540056 (Todo: Set Internal Name for UI testing)
-- 2023-12-20T14:16:15.549Z
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 14:16:15.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540056
;

-- Name: Vergütungsgruppe
-- Action Type: W
-- Window: Vergütungsgruppe(540056,de.metas.commission_legacy)
-- 2023-12-20T14:16:15.556Z
UPDATE AD_Menu SET Description=NULL, IsActive='N', Name='Vergütungsgruppe',Updated=TO_TIMESTAMP('2023-12-20 14:16:15.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540128
;

-- Run mode: SWING_CLIENT

-- Window: Provisionsberechnung, InternalName=540028 (Todo: Set Internal Name for UI testing)
-- 2023-12-20T15:36:21.770Z
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2023-12-20 15:36:21.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540028
;

-- UI Element: Leergut Rücknahme(540323,D) -> Leergut Rücknahme(540782,D) -> main -> 20 -> dates.Aktiv
-- Column: M_InOut.IsActive
-- 2023-12-20T15:53:24.822Z
UPDATE AD_UI_Element SET AD_Field_ID=557686, Description='Der Eintrag ist im System aktiv', Help='Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',Updated=TO_TIMESTAMP('2023-12-20 15:53:24.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543303
;

