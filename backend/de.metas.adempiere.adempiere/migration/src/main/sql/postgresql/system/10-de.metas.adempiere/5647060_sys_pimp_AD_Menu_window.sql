-- Column: AD_Menu.Action
-- 2022-07-15T08:19:24.995336400Z
UPDATE AD_Column SET MandatoryLogic='@IsSummary/X@=N',Updated=TO_TIMESTAMP('2022-07-15 11:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=232
;

-- UI Element: Menü -> Menü.Schreibgeschützt
-- Column: AD_Menu.IsReadOnly
-- 2022-07-15T08:22:49.092225900Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548901
;

-- UI Element: Menü -> Menü.Verkaufs Transaktion
-- Column: AD_Menu.IsSOTrx
-- 2022-07-15T08:22:49.151198800Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548902
;

-- Field: Menü -> Menü -> Schreibgeschützt
-- Column: AD_Menu.IsReadOnly
-- 2022-07-15T08:23:23.622220300Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2022-07-15 11:23:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5321
;

-- Field: Menü -> Menü -> Verkaufs-Transaktion
-- Column: AD_Menu.IsSOTrx
-- 2022-07-15T08:23:37.102043Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2022-07-15 11:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3315
;

-- Field: Menü -> Menü -> Aktion
-- Column: AD_Menu.Action
-- 2022-07-15T08:24:34.216735500Z
UPDATE AD_Field SET SeqNo=65,Updated=TO_TIMESTAMP('2022-07-15 11:24:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=205
;

-- Field: Menü -> Menü -> Entitäts-Art
-- Column: AD_Menu.EntityType
-- 2022-07-15T08:25:11.261928800Z
UPDATE AD_Field SET SeqNo=178,Updated=TO_TIMESTAMP('2022-07-15 11:25:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5827
;

-- Column: AD_Menu.AD_Window_ID
-- 2022-07-15T08:26:36.692554600Z
UPDATE AD_Column SET AD_Reference_ID=30, MandatoryLogic='@Action/-@=W',Updated=TO_TIMESTAMP('2022-07-15 11:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=233
;

-- Column: AD_Menu.WEBUI_Board_ID
-- 2022-07-15T08:27:41.011578800Z
UPDATE AD_Column SET AD_Reference_ID=30, MandatoryLogic='@Action/-@=K',Updated=TO_TIMESTAMP('2022-07-15 11:27:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556908
;

-- Column: AD_Menu.AD_Process_ID
-- 2022-07-15T08:28:16.239755500Z
UPDATE AD_Column SET AD_Reference_ID=30, MandatoryLogic='@Action/-@=P | @Action/-@=R',Updated=TO_TIMESTAMP('2022-07-15 11:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3375
;

