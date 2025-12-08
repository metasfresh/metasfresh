-- Column: C_InvoiceSchedule.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-27T09:04:34.086Z
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2024-08-27 09:04:34.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- Column: C_InvoiceSchedule.InvoiceDay
-- Column: C_InvoiceSchedule.InvoiceDay
-- 2024-08-27T09:06:10.381Z
UPDATE AD_Column SET ReadOnlyLogic='@C_InvoiceSchedule_ID/0@!0', TechnicalNote='Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.
',Updated=TO_TIMESTAMP('2024-08-27 09:06:10.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2148
;

-- Column: C_InvoiceSchedule.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-27T09:06:31.805Z
UPDATE AD_Column SET ReadOnlyLogic='@C_InvoiceSchedule_ID/0@!0', TechnicalNote='Example: InvoiceFrequency = Montly and InvoiceDistance = 1 means that there can be one invoice per month; 3 means that there can be one invoice per quarter.
This value is ignored if the schedule is set to "TwiceMonthly".
Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues. 
Read-Only-Logic="@C_InvoiceSchedule_ID/0@!0" is a workaround as "Updateable=N" doesn''t seem to work.',Updated=TO_TIMESTAMP('2024-08-27 09:06:31.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- Column: C_InvoiceSchedule.InvoiceFrequency
-- Column: C_InvoiceSchedule.InvoiceFrequency
-- 2024-08-27T09:07:56.010Z
UPDATE AD_Column SET ReadOnlyLogic='@C_InvoiceSchedule_ID/0@!0', TechnicalNote='Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.
Read-Only-Logic="@C_InvoiceSchedule_ID/0@!0" is a workaround as "Updateable=N" doesn''t seem to work.',Updated=TO_TIMESTAMP('2024-08-27 09:07:56.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2146
;

-- Column: C_InvoiceSchedule.InvoiceDay
-- Column: C_InvoiceSchedule.InvoiceDay
-- 2024-08-27T09:08:13.273Z
UPDATE AD_Column SET TechnicalNote='Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.
Read-Only-Logic="@C_InvoiceSchedule_ID/0@!0" is a workaround as "Updateable=N" doesn''t seem to work.',Updated=TO_TIMESTAMP('2024-08-27 09:08:13.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2148
;

-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- 2024-08-27T09:08:31.368Z
UPDATE AD_Column SET ReadOnlyLogic='@C_InvoiceSchedule_ID/0@!0', TechnicalNote='Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.
Read-Only-Logic="@C_InvoiceSchedule_ID/0@!0" is a workaround as "Updateable=N" doesn''t seem to work.',Updated=TO_TIMESTAMP('2024-08-27 09:08:31.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2147
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: Amt
-- 2024-08-27T09:11:30.062Z
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2024-08-27 09:11:30.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542888
;

-- Column: C_InvoiceSchedule.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-27T09:15:25.406Z
UPDATE AD_Column SET IsUpdateable='Y', ReadOnlyLogic='', TechnicalNote='Example: InvoiceFrequency = Montly and InvoiceDistance = 1 means that there can be one invoice per month; 3 means that there can be one invoice per quarter.
This value is ignored if the schedule is set to "TwiceMonthly".
Not updatable is not evaluated by the WebUI, but causes an exception when trying to save those values in the backend.
Once a recaord is created, the values shall be changeable only via C_InvoiceSchedule_SetValues. 
Note that read-only-Logic="@C_InvoiceSchedule_ID/0@!0" also doesn''t work in the WebUI',Updated=TO_TIMESTAMP('2024-08-27 09:15:25.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- Column: C_InvoiceSchedule.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-27T09:16:46.208Z
UPDATE AD_Column SET TechnicalNote='Example: InvoiceFrequency = Montly and InvoiceDistance = 1 means that there can be one invoice per month; 3 means that there can be one invoice per quarter.
This value is ignored if the schedule is set to "TwiceMonthly".
Not updatable is not evaluated by the WebUI, but causes an exception when trying to save those values in the backend.
Note that read-only-Logic="@C_InvoiceSchedule_ID/0@!0" also doesn''t work in the WebUI
Records shall be created and changed only via C_InvoiceSchedule_CreateOrUpdate. 
',Updated=TO_TIMESTAMP('2024-08-27 09:16:46.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- Value: C_InvoiceSchedule_CreateOrUpdate
-- Classname: de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate
-- 2024-08-27T09:17:50.603Z
UPDATE AD_Process SET Classname='de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate', Description='Erlaubt erstellen eines neuen Terminplans oder das Ändern der Werte, die relevant für Rechnungskandidaten sind.
Nach der Änderung werden alle betroffenen Rechnungskandidate als "zu aktualisieren" markiert.', Name='Terminplan erstellen/ändern', Value='C_InvoiceSchedule_CreateOrUpdate',Updated=TO_TIMESTAMP('2024-08-27 09:17:50.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585421
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- 2024-08-27T09:17:59.947Z
UPDATE AD_Process_Trl SET Description='Erlaubt erstellen eines neuen Terminplans oder das Ändern der Werte, die relevant für Rechnungskandidaten sind.
Nach der Änderung werden alle betroffenen Rechnungskandidate als "zu aktualisieren" markiert.',Updated=TO_TIMESTAMP('2024-08-27 09:17:59.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585421
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- 2024-08-27T09:18:07.879Z
UPDATE AD_Process_Trl SET Name='Terminplan erstellen/ändern',Updated=TO_TIMESTAMP('2024-08-27 09:18:07.879000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585421
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- 2024-08-27T09:18:19.087Z
UPDATE AD_Process_Trl SET Description='Erlaubt erstellen eines neuen Terminplans oder das Ändern der Werte, die relevant für Rechnungskandidaten sind.
Nach der Änderung werden alle betroffenen Rechnungskandidate als "zu aktualisieren" markiert.', IsTranslated='Y', Name='Terminplan erstellen/ändern',Updated=TO_TIMESTAMP('2024-08-27 09:18:19.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585421
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- 2024-08-27T09:18:58.122Z
UPDATE AD_Process_Trl SET Description='Allows you to create a new schedule or change the values that are relevant for invoice candidates. 
After the change, all affected invoice candidates are marked as "to be updated".', Name='Create/change schedule',Updated=TO_TIMESTAMP('2024-08-27 09:18:58.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585421
;

-- Column: C_InvoiceSchedule.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-27T09:19:45.090Z
UPDATE AD_Column SET TechnicalNote='Example: InvoiceFrequency = Montly and InvoiceDistance = 1 means that there can be one invoice per month; 3 means that there can be one invoice per quarter.
This value is ignored if the schedule is set to "TwiceMonthly".
Updatable=N is not evaluated by the WebUI, but causes an exception when trying to save those values in the backend.
Note that read-only-Logic="@C_InvoiceSchedule_ID/0@!0" also doesn''t work in the WebUI
Records shall be created and changed only via C_InvoiceSchedule_CreateOrUpdate. 
',Updated=TO_TIMESTAMP('2024-08-27 09:19:45.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- Column: C_InvoiceSchedule.InvoiceFrequency
-- Column: C_InvoiceSchedule.InvoiceFrequency
-- 2024-08-27T09:20:19.159Z
UPDATE AD_Column SET IsUpdateable='Y', TechnicalNote='IsUpdatable=N is not evaluated by the WebUI, but causes an exception when trying to save those values in the backend.
Note that read-only-Logic="@C_InvoiceSchedule_ID/0@!0" also doesn''t work in the WebUI
Records shall be created and changed only via C_InvoiceSchedule_CreateOrUpdate. 
',Updated=TO_TIMESTAMP('2024-08-27 09:20:19.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2146
;

-- Column: C_InvoiceSchedule.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-27T09:20:27.323Z
UPDATE AD_Column SET TechnicalNote='Example: InvoiceFrequency = Montly and InvoiceDistance = 1 means that there can be one invoice per month; 3 means that there can be one invoice per quarter.
This value is ignored if the schedule is set to "TwiceMonthly".
IsUpdatable=N is not evaluated by the WebUI, but causes an exception when trying to save those values in the backend.
Note that read-only-Logic="@C_InvoiceSchedule_ID/0@!0" also doesn''t work in the WebUI
Records shall be created and changed only via C_InvoiceSchedule_CreateOrUpdate. 
',Updated=TO_TIMESTAMP('2024-08-27 09:20:27.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- Column: C_InvoiceSchedule.InvoiceDay
-- Column: C_InvoiceSchedule.InvoiceDay
-- 2024-08-27T09:20:37.939Z
UPDATE AD_Column SET TechnicalNote='IsUpdatable=N is not evaluated by the WebUI, but causes an exception when trying to save those values in the backend.
Note that read-only-Logic="@C_InvoiceSchedule_ID/0@!0" also doesn''t work in the WebUI
Records shall be created and changed only via C_InvoiceSchedule_CreateOrUpdate. 
',Updated=TO_TIMESTAMP('2024-08-27 09:20:37.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2148
;

-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- 2024-08-27T09:20:48.457Z
UPDATE AD_Column SET TechnicalNote='IsUpdatable=N is not evaluated by the WebUI, but causes an exception when trying to save those values in the backend.
Note that read-only-Logic="@C_InvoiceSchedule_ID/0@!0" also doesn''t work in the WebUI
Records shall be created and changed only via C_InvoiceSchedule_CreateOrUpdate. 
',Updated=TO_TIMESTAMP('2024-08-27 09:20:48.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2147
;

-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- 2024-08-27T09:20:50.888Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2024-08-27 09:20:50.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2147
;

-- Column: C_InvoiceSchedule.InvoiceDay
-- Column: C_InvoiceSchedule.InvoiceDay
-- 2024-08-27T09:21:01.936Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2024-08-27 09:21:01.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2148
;

-- Column: C_InvoiceSchedule.IsAmount
-- Column: C_InvoiceSchedule.IsAmount
-- 2024-08-27T09:21:14.994Z
UPDATE AD_Column SET IsUpdateable='Y', TechnicalNote='IsUpdatable=N is not evaluated by the WebUI, but causes an exception when trying to save those values in the backend.
Note that read-only-Logic="@C_InvoiceSchedule_ID/0@!0" also doesn''t work in the WebUI
Records shall be created and changed only via C_InvoiceSchedule_CreateOrUpdate. 
',Updated=TO_TIMESTAMP('2024-08-27 09:21:14.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2144
;

-- Column: C_InvoiceSchedule.Amt
-- Column: C_InvoiceSchedule.Amt
-- 2024-08-27T09:21:31.340Z
UPDATE AD_Column SET IsUpdateable='Y', TechnicalNote='IsUpdatable=N is not evaluated by the WebUI, but causes an exception when trying to save those values in the backend.
Note that read-only-Logic="@C_InvoiceSchedule_ID/0@!0" also doesn''t work in the WebUI
Records shall be created and changed only via C_InvoiceSchedule_CreateOrUpdate. 
',Updated=TO_TIMESTAMP('2024-08-27 09:21:31.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2145
;

-- Field: Terminplan Rechnung -> Terminplan Rechnung -> Rechnungsinterval
-- Column: C_InvoiceSchedule.InvoiceDistance
-- Field: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> Rechnungsinterval
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-27T09:22:04.046Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-27 09:22:04.046000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=564238
;

-- Field: Terminplan Rechnung -> Terminplan Rechnung -> Rechnungsinterval Einh.
-- Column: C_InvoiceSchedule.InvoiceFrequency
-- Field: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> Rechnungsinterval Einh.
-- Column: C_InvoiceSchedule.InvoiceFrequency
-- 2024-08-27T09:22:13.468Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-27 09:22:13.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=1203
;

-- Field: Terminplan Rechnung -> Terminplan Rechnung -> Rechnungstag
-- Column: C_InvoiceSchedule.InvoiceDay
-- Field: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> Rechnungstag
-- Column: C_InvoiceSchedule.InvoiceDay
-- 2024-08-27T09:22:21.488Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-27 09:22:21.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=1205
;

-- Field: Terminplan Rechnung -> Terminplan Rechnung -> Wochentag
-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- Field: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> Wochentag
-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- 2024-08-27T09:22:29.506Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-27 09:22:29.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=1204
;

-- Field: Terminplan Rechnung -> Terminplan Rechnung -> Betragsgrenze
-- Column: C_InvoiceSchedule.IsAmount
-- Field: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> Betragsgrenze
-- Column: C_InvoiceSchedule.IsAmount
-- 2024-08-27T09:22:37.886Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-27 09:22:37.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=1201
;

-- Field: Terminplan Rechnung -> Terminplan Rechnung -> Betrag
-- Column: C_InvoiceSchedule.Amt
-- Field: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> Betrag
-- Column: C_InvoiceSchedule.Amt
-- 2024-08-27T09:22:44.308Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-27 09:22:44.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=1202
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- ParameterName: Amt
-- 2024-08-27T10:09:39.522Z
UPDATE AD_Process_Para SET DisplayLogic='@IsAmount/''N''@=''Y''',Updated=TO_TIMESTAMP('2024-08-27 10:09:39.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542888
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- ParameterName: InvoiceDistance
-- 2024-08-27T10:09:49.373Z
UPDATE AD_Process_Para SET DisplayLogic='@IsAmount/''N''@=''Y''',Updated=TO_TIMESTAMP('2024-08-27 10:09:49.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542883
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- ParameterName: InvoiceDistance
-- 2024-08-27T10:10:23.253Z
UPDATE AD_Process_Para SET DisplayLogic='@InvoiceFrequency/''M''@ ! ''T''',Updated=TO_TIMESTAMP('2024-08-27 10:10:23.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542883
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- ParameterName: Name
-- 2024-08-27T10:22:57.710Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,469,0,585421,542890,10,'Name',TO_TIMESTAMP('2024-08-27 10:22:57.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@Name/''''@','D',0,'Y','N','Y','N','Y','N','Name',5,TO_TIMESTAMP('2024-08-27 10:22:57.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-27T10:22:57.714Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542890 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Beschreibung
-- Column: C_InvoiceSchedule.Description
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 20 -> flags.Beschreibung
-- Column: C_InvoiceSchedule.Description
-- 2024-08-27T10:28:23.693Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540400, SeqNo=30,Updated=TO_TIMESTAMP('2024-08-27 10:28:23.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544031
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- ParameterName: Name
-- 2024-08-27T10:31:08.274Z
UPDATE AD_Process_Para SET DefaultValue='',Updated=TO_TIMESTAMP('2024-08-27 10:31:08.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542890
;

-- Value: C_InvoiceSchedule_CreateOrUpdate
-- Classname: de.metas.invoice.process.C_InvoiceSchedule_CreateOrUpdate
-- 2024-08-27T10:31:59.714Z
UPDATE AD_Process SET IsFormatExcelFile='N',Updated=TO_TIMESTAMP('2024-08-27 10:31:59.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585421
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> default.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-27T10:33:10.659Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625297
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Rechnungshäufigkeit
-- Column: C_InvoiceSchedule.InvoiceFrequency
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> default.Rechnungshäufigkeit
-- Column: C_InvoiceSchedule.InvoiceFrequency
-- 2024-08-27T10:33:10.677Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544030
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Rechnungstag
-- Column: C_InvoiceSchedule.InvoiceDay
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> default.Rechnungstag
-- Column: C_InvoiceSchedule.InvoiceDay
-- 2024-08-27T10:33:10.694Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544038
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Betrag
-- Column: C_InvoiceSchedule.Amt
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> description.Betrag
-- Column: C_InvoiceSchedule.Amt
-- 2024-08-27T10:33:10.713Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544033
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Betragsgrenze
-- Column: C_InvoiceSchedule.IsAmount
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> description.Betragsgrenze
-- Column: C_InvoiceSchedule.IsAmount
-- 2024-08-27T10:33:10.731Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544032
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Beschreibung
-- Column: C_InvoiceSchedule.Description
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 20 -> flags.Beschreibung
-- Column: C_InvoiceSchedule.Description
-- 2024-08-27T10:33:10.749Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544031
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Aktiv
-- Column: C_InvoiceSchedule.IsActive
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 20 -> flags.Aktiv
-- Column: C_InvoiceSchedule.IsActive
-- 2024-08-27T10:33:10.767Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544034
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Standard
-- Column: C_InvoiceSchedule.IsDefault
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 20 -> flags.Standard
-- Column: C_InvoiceSchedule.IsDefault
-- 2024-08-27T10:33:10.784Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544035
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Letzter Tag der Lieferungen
-- Column: C_InvoiceSchedule.InvoiceDayCutoff
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> description.Letzter Tag der Lieferungen
-- Column: C_InvoiceSchedule.InvoiceDayCutoff
-- 2024-08-27T10:33:10.798Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544039
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Letzter Wochentag der Lieferungen
-- Column: C_InvoiceSchedule.InvoiceWeekDayCutoff
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> description.Letzter Wochentag der Lieferungen
-- Column: C_InvoiceSchedule.InvoiceWeekDayCutoff
-- 2024-08-27T10:33:10.815Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544041
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Wochentag
-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> default.Wochentag
-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- 2024-08-27T10:33:10.829Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544040
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Sektion
-- Column: C_InvoiceSchedule.AD_Org_ID
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 20 -> org.Sektion
-- Column: C_InvoiceSchedule.AD_Org_ID
-- 2024-08-27T10:33:10.843Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-08-27 10:33:10.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544036
;

-- Value: C_InvoiceSchedule_CreateOrUpdate
-- Classname: de.metas.ui.web.invoice.process.C_InvoiceSchedule_CreateOrUpdate
-- 2024-08-27T10:47:13.679Z
UPDATE AD_Process SET Classname='de.metas.ui.web.invoice.process.C_InvoiceSchedule_CreateOrUpdate', TechnicalNote='It''s a UI-process because IDK how else to make it display new records that were added by this process in the UI',Updated=TO_TIMESTAMP('2024-08-27 10:47:13.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585421
;

