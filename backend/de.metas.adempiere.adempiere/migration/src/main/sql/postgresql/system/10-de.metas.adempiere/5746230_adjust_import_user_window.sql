-- Run mode: SWING_CLIENT

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 20 -> flags.Importiert
-- Column: I_User.I_IsImported
-- 2025-02-10T15:37:12.471Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2025-02-10 15:37:12.471000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556932
;

-- UI Column: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 20
-- UI Element Group: add
-- 2025-02-10T15:38:09.573Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541463,552477,TO_TIMESTAMP('2025-02-10 15:38:09.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','add',15,TO_TIMESTAMP('2025-02-10 15:38:09.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 20 -> add.Import-Fehlermeldung
-- Column: I_User.I_ErrorMsg
-- 2025-02-10T15:38:53.701Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560328,0,540879,552477,629410,'F',TO_TIMESTAMP('2025-02-10 15:38:53.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Meldungen, die durch den Importprozess generiert wurden','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','Y','N','N','N',0,'Import-Fehlermeldung',10,0,0,TO_TIMESTAMP('2025-02-10 15:38:53.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> Import-Fehlermeldung
-- Column: I_User.I_ErrorMsg
-- 2025-02-10T15:54:21.876Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-02-10 15:54:21.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=560328
;

-- Field: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> Import-Fehlermeldung
-- Column: I_User.I_ErrorMsg
-- 2025-02-10T16:00:49.468Z
UPDATE AD_Field SET DisplayLogic='@I_IsImported/''E''@=''E''',Updated=TO_TIMESTAMP('2025-02-10 16:00:49.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=560328
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 10 -> advanced.Import-Fehlermeldung
-- Column: I_User.I_ErrorMsg
-- 2025-02-10T16:05:48.032Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=556931
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 20 -> org.Mandant
-- Column: I_User.AD_Client_ID
-- 2025-02-10T16:06:04.025Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556920
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 20 -> org.Sektion
-- Column: I_User.AD_Org_ID
-- 2025-02-10T16:06:04.033Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556921
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 10 -> default.BPValue
-- Column: I_User.BPValue
-- 2025-02-10T16:06:04.041Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556922
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 10 -> default.Geschäftspartner
-- Column: I_User.C_BPartner_ID
-- 2025-02-10T16:06:04.047Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556923
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 10 -> default.Vorname
-- Column: I_User.Firstname
-- 2025-02-10T16:06:04.053Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556924
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 10 -> default.Nachname
-- Column: I_User.Lastname
-- 2025-02-10T16:06:04.058Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556925
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 10 -> advanced.eMail
-- Column: I_User.EMail
-- 2025-02-10T16:06:04.063Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556926
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 10 -> advanced.Login
-- Column: I_User.Login
-- 2025-02-10T16:06:04.068Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556927
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 20 -> flags.Systembenutzer
-- Column: I_User.IsSystemUser
-- 2025-02-10T16:06:04.073Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556928
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 10 -> default.Role name
-- Column: I_User.RoleName
-- 2025-02-10T16:06:04.077Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556929
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 10 -> default.Rolle
-- Column: I_User.AD_Role_ID
-- 2025-02-10T16:06:04.081Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556930
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 20 -> flags.Importiert
-- Column: I_User.I_IsImported
-- 2025-02-10T16:06:04.085Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556932
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 20 -> add.Import-Fehlermeldung
-- Column: I_User.I_ErrorMsg
-- 2025-02-10T16:06:04.089Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=629410
;

-- UI Element: Import Nutzer(540368,D) -> Import Nutzer(540879,D) -> main -> 20 -> flags.Verarbeitet
-- Column: I_User.Processed
-- 2025-02-10T16:06:04.093Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-02-10 16:06:04.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=556933
;
