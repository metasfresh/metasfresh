-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Mahnpauschale
-- Column: C_DunningLevel.FeeAmt
-- 2024-07-05T14:16:28.213558900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2917,0,268,541186,624979,'F',TO_TIMESTAMP('2024-07-05 15:16:25.398','YYYY-MM-DD HH24:MI:SS.US'),100,'Pauschale Mahngebühr','The Fee Amount indicates the charge amount on a dunning letter for overdue invoices.  This field will only display if the charge fee checkbox has been selected.','Y','N','N','Y','N','N','N',0,'Mahnpauschale',55,0,0,TO_TIMESTAMP('2024-07-05 15:16:25.398','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Mahnpauschale
-- Column: C_DunningLevel.FeeAmt
-- 2024-07-05T14:17:05.709109700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-07-05 15:17:05.708','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624979
;

-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Aktiv
-- Column: C_DunningLevel.IsActive
-- 2024-07-05T14:17:07.085674900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-07-05 15:17:07.083','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548943
;

-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Delivery stop
-- Column: C_DunningLevel.IsDeliveryStop
-- 2024-07-05T14:17:08.459940500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-07-05 15:17:08.459','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548939
;

-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Alle offenen Rechnungen berücksichtigen
-- Column: C_DunningLevel.IsShowAllDue
-- 2024-07-05T14:17:09.833242900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-07-05 15:17:09.832','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548946
;

-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Notiz Header
-- Column: C_DunningLevel.NoteHeader
-- 2024-07-05T14:17:11.202316Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-07-05 15:17:11.201','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548948
;

-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Mahntext
-- Column: C_DunningLevel.Note
-- 2024-07-05T14:17:12.573122200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-07-05 15:17:12.573','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548949
;

-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Sektion
-- Column: C_DunningLevel.AD_Org_ID
-- 2024-07-05T14:17:13.938839500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-07-05 15:17:13.938','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548961
;

-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Mahngebühr
-- Column: C_DunningLevel.ChargeFee
-- 2024-07-05T14:22:41.162326900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2913,0,268,541186,624980,'F',TO_TIMESTAMP('2024-07-05 15:22:38.384','YYYY-MM-DD HH24:MI:SS.US'),100,'Zeigt an, ob Gebühren auf fällige Rechnungen berechnet werden','Das Auswahlfeld "Mahngebühr" zeigt an, ob eine Gebühr auf überfällige Rechnungsbeträge berechnet wird.','Y','N','N','Y','N','N','N',0,'Mahngebühr',83,0,0,TO_TIMESTAMP('2024-07-05 15:22:38.384','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Mahnart(159,de.metas.dunning) -> Stufen(268,D) -> main -> 10 -> default.Mahnpauschale
-- Column: C_DunningLevel.FeeAmt
-- 2024-07-05T14:23:43.494838400Z
UPDATE AD_UI_Element SET SeqNo=87,Updated=TO_TIMESTAMP('2024-07-05 15:23:43.494','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624979
;

