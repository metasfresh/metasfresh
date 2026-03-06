-- Run mode: SWING_CLIENT

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 10 -> primary.Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- 2025-09-25T16:11:47.927Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731877,0,547621,553549,637303,'F',TO_TIMESTAMP('2025-09-25 16:11:47.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Responsibility Role','The Role determines security and access a user who has this Role will have in the System.','Y','N','Y','N','N','Rolle',20,0,0,TO_TIMESTAMP('2025-09-25 16:11:47.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2025-09-25T16:11:58.175Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2025-09-25 16:11:58.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637300
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 10 -> primary.Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- 2025-09-25T16:12:04.883Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-25 16:12:04.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637303
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2025-09-25T16:12:04.890Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-25 16:12:04.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637300
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 20 -> flags.Allow all Actions
-- Column: Mobile_Application_Access.IsAllowAllActions
-- 2025-09-25T16:12:04.897Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-25 16:12:04.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637299
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 20 -> flags.Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2025-09-25T16:12:04.902Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-25 16:12:04.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637298
;

