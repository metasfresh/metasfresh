-- Run mode: SWING_CLIENT

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Neues Gebinde zulassen
-- Column: MobileUI_UserProfile_Picking.IsPickingWithNewLU
-- 2025-09-10T15:41:20.200Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625021
;

-- 2025-09-10T15:41:20.220Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729105
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Kommissionieren mit LU
-- Column: MobileUI_UserProfile_Picking.IsPickingWithNewLU
-- 2025-09-10T15:41:20.231Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=729105
;

-- 2025-09-10T15:41:20.236Z
DELETE FROM AD_Field WHERE AD_Field_ID=729105
;

-- 2025-09-10T15:41:20.396Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE MobileUI_UserProfile_Picking DROP COLUMN IF EXISTS IsPickingWithNewLU')
;

-- Column: MobileUI_UserProfile_Picking.IsPickingWithNewLU
-- 2025-09-10T15:41:20.462Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588864
;

-- 2025-09-10T15:41:20.467Z
DELETE FROM AD_Column WHERE AD_Column_ID=588864
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Allow new TU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- 2025-09-10T15:41:32.933Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625298
;

-- 2025-09-10T15:41:32.936Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729840
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Allow new TU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- 2025-09-10T15:41:32.952Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=729840
;

-- 2025-09-10T15:41:32.959Z
DELETE FROM AD_Field WHERE AD_Field_ID=729840
;

-- 2025-09-10T15:41:32.961Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE MobileUI_UserProfile_Picking DROP COLUMN IF EXISTS IsAllowNewTU')
;

-- Column: MobileUI_UserProfile_Picking.IsAllowNewTU
-- 2025-09-10T15:41:32.975Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588932
;

-- 2025-09-10T15:41:32.982Z
DELETE FROM AD_Column WHERE AD_Column_ID=588932
;

-- UI Element: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> main -> 20 -> flags.Allow new TU
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowNewTU
-- 2025-09-10T15:41:58.362Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=629427
;

-- 2025-09-10T15:41:58.365Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737667
;

-- Field: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> Allow new TU
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowNewTU
-- 2025-09-10T15:41:58.370Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=737667
;

-- 2025-09-10T15:41:58.377Z
DELETE FROM AD_Field WHERE AD_Field_ID=737667
;

-- 2025-09-10T15:41:58.379Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking_Job','ALTER TABLE MobileUI_UserProfile_Picking_Job DROP COLUMN IF EXISTS IsAllowNewTU')
;

-- Column: MobileUI_UserProfile_Picking_Job.IsAllowNewTU
-- 2025-09-10T15:41:58.395Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589653
;

-- 2025-09-10T15:41:58.401Z
DELETE FROM AD_Column WHERE AD_Column_ID=589653
;

-- UI Element: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> main -> 20 -> flags.Kommissionieren mit LU
-- Column: MobileUI_UserProfile_Picking_Job.IsPickingWithNewLU
-- 2025-09-10T15:42:12.155Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=629426
;

-- 2025-09-10T15:42:12.157Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737673
;

-- Field: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> Kommissionieren mit LU
-- Column: MobileUI_UserProfile_Picking_Job.IsPickingWithNewLU
-- 2025-09-10T15:42:12.162Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=737673
;

-- 2025-09-10T15:42:12.167Z
DELETE FROM AD_Field WHERE AD_Field_ID=737673
;

-- 2025-09-10T15:42:12.183Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking_Job','ALTER TABLE MobileUI_UserProfile_Picking_Job DROP COLUMN IF EXISTS IsPickingWithNewLU')
;

-- Column: MobileUI_UserProfile_Picking_Job.IsPickingWithNewLU
-- 2025-09-10T15:42:12.191Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589659
;

-- 2025-09-10T15:42:12.197Z
DELETE FROM AD_Column WHERE AD_Column_ID=589659
;

