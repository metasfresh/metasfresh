-- UI Element: Projektzeit - Admin -> Projektzeit - Admin.Erstellt durch
-- Column: C_Project_TimeBooking.CreatedBy
-- 2024-04-08T10:19:58.964Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-04-08 13:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624364
;

-- UI Element: Projektzeit - Admin -> Projektzeit - Admin.Booked date
-- Column: C_Project_TimeBooking.BookedDate
-- 2024-04-08T10:19:58.972Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-04-08 13:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624362
;

-- UI Element: Projektzeit - Admin -> Projektzeit - Admin.Projekt
-- Column: C_Project_TimeBooking.C_Project_ID
-- 2024-04-08T10:19:58.978Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-04-08 13:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624361
;

-- UI Element: Projektzeit - Admin -> Projektzeit - Admin.Time (H:mm)
-- Column: C_Project_TimeBooking.HoursAndMinutes
-- 2024-04-08T10:19:58.984Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-04-08 13:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624363
;

-- UI Element: Projektzeit - Admin -> Projektzeit - Admin.Fakturiert
-- Column: C_Project_TimeBooking.IsInvoiced
-- 2024-04-08T10:19:58.989Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-04-08 13:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624366
;

-- UI Element: Projektzeit - Admin -> Projektzeit - Admin.Sektion
-- Column: C_Project_TimeBooking.AD_Org_ID
-- 2024-04-08T10:19:58.995Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-04-08 13:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624367
;


-- Column: C_Project.Project_BookedTime
-- Column SQL (old): (SELECT CONCAT(FLOOR(bookedSeconds.totalBookedSeconds / 3600), ':', LPAD(CAST(FLOOR((bookedSeconds.totalBookedSeconds % 3600) / 60) AS VARCHAR), 2, '0')) from (SELECT SUM(bookedSeconds) as totalBookedSeconds from C_Project_TimeBooking where C_Project_TimeBooking.C_Project_ID = C_Project.C_Project_ID) bookedSeconds)
-- 2024-04-08T10:42:02.104Z
UPDATE AD_Column SET ColumnSQL='(SELECT CONCAT(coalesce(FLOOR(bookedSeconds.totalBookedSeconds / 3600),''0''), '':'', coalesce(LPAD(CAST(FLOOR((bookedSeconds.totalBookedSeconds % 3600) / 60) AS VARCHAR), 2, ''0''), ''00'')) from (SELECT SUM(bookedSeconds) as totalBookedSeconds from C_Project_TimeBooking where C_Project_TimeBooking.C_Project_ID = C_Project.C_Project_ID) bookedSeconds)',Updated=TO_TIMESTAMP('2024-04-08 13:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588175
;

