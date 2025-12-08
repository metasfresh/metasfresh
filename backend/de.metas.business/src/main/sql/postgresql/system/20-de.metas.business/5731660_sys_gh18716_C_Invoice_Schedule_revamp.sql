-- Field: Terminplan Rechnung -> Terminplan Rechnung -> Anz. Einheiten zwischen zwei Rechnungsstellungen
-- Column: C_InvoiceSchedule.InvoiceDistance
-- Field: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> Anz. Einheiten zwischen zwei Rechnungsstellungen
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-26T07:44:08.787Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2024-08-26 07:44:08.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=564238
;

-- Column: C_InvoiceSchedule.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-26T07:46:12.629Z
UPDATE AD_Column SET ReadOnlyLogic='@InvoiceFrequency/''X''@ = ''T''',Updated=TO_TIMESTAMP('2024-08-26 07:46:12.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> default.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-26T13:17:39.312Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564238,0,193,540398,625297,'F',TO_TIMESTAMP('2024-08-26 13:17:38.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'InvoiceDistance',15,0,0,TO_TIMESTAMP('2024-08-26 13:17:38.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;


-- Column: C_InvoiceSchedule.Amt
-- Column: C_InvoiceSchedule.Amt
-- 2024-08-26T13:24:14.943Z
UPDATE AD_Column SET TechnicalNote='',Updated=TO_TIMESTAMP('2024-08-26 13:24:14.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2145
;

-- Element: IsAmount
-- 2024-08-26T13:26:00.489Z
UPDATE AD_Element_Trl SET Description='Rechnungen nur erstellen, wenn der Betrag die Betragsgrenze übersteigt', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-26 13:26:00.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=349 AND AD_Language='de_DE'
;

-- 2024-08-26T13:26:00.591Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(349,'de_DE') 
;

-- Element: IsAmount
-- 2024-08-26T13:26:16.049Z
UPDATE AD_Element_Trl SET Description='Rechnungen nur erstellen, wenn der Betrag die Betragsgrenze übersteigt.', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-26 13:26:16.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=349 AND AD_Language='de_CH'
;

-- 2024-08-26T13:26:16.105Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(349,'de_CH') 
;

-- 2024-08-26T13:26:16.133Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(349,'de_CH') 
;

-- Element: IsAmount
-- 2024-08-26T13:26:18.458Z
UPDATE AD_Element_Trl SET Description='Rechnungen nur erstellen, wenn der Betrag die Betragsgrenze übersteigt.',Updated=TO_TIMESTAMP('2024-08-26 13:26:18.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=349 AND AD_Language='de_DE'
;

-- 2024-08-26T13:26:18.510Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(349,'de_DE') 
;

-- Element: IsAmount
-- 2024-08-26T13:26:31.456Z
UPDATE AD_Element_Trl SET Help='',Updated=TO_TIMESTAMP('2024-08-26 13:26:31.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=349 AND AD_Language='en_US'
;

-- 2024-08-26T13:26:31.511Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(349,'en_US') 
;

-- Element: InvoiceDay
-- 2024-08-26T13:41:20.344Z
UPDATE AD_Element_Trl SET Description='Tag des Monats für die Rechnungserstellung.', Help='Die Zahl wird ggf auf den Monats-Ultimo des jeweiligen monats verringert (z.B. bei 31 wird im November wird am 30. fakturiert)', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-26 13:41:20.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=340 AND AD_Language='de_DE'
;

-- 2024-08-26T13:41:20.402Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(340,'de_DE') 
;

-- Element: InvoiceDay
-- 2024-08-26T13:41:52.498Z
UPDATE AD_Element_Trl SET Description='Tag des Monats für die Rechnungserstellung.', Help='Die Zahl wird ggf auf den Monats-Ultimo des jeweiligen monats verringert (z.B. bei 31 wird im November wird am 30. fakturiert).',Updated=TO_TIMESTAMP('2024-08-26 13:41:52.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=340 AND AD_Language='de_CH'
;

-- 2024-08-26T13:41:52.551Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(340,'de_CH') 
;

-- 2024-08-26T13:41:52.578Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(340,'de_CH') 
;

-- Element: InvoiceDay
-- 2024-08-26T13:41:56.597Z
UPDATE AD_Element_Trl SET Help='Die Zahl wird ggf auf den Monats-Ultimo des jeweiligen monats verringert (z.B. bei 31 wird im November wird am 30. fakturiert).',Updated=TO_TIMESTAMP('2024-08-26 13:41:56.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=340 AND AD_Language='de_DE'
;

-- 2024-08-26T13:41:56.651Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(340,'de_DE') 
;

-- Element: InvoiceDay
-- 2024-08-26T13:41:58.886Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-26 13:41:58.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=340 AND AD_Language='de_CH'
;

-- 2024-08-26T13:41:58.940Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(340,'de_CH') 
;

-- 2024-08-26T13:41:58.965Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(340,'de_CH') 
;

-- Element: InvoiceWeekDay
-- 2024-08-26T13:43:20.868Z
UPDATE AD_Element_Trl SET Description='Wochentag für die Rechnungserstellung.', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-26 13:43:20.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=342 AND AD_Language='de_DE'
;

-- 2024-08-26T13:43:20.921Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(342,'de_DE') 
;

-- Element: InvoiceWeekDay
-- 2024-08-26T13:43:33.247Z
UPDATE AD_Element_Trl SET Description='Wochentag für die Rechnungserstellung.', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-26 13:43:33.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=342 AND AD_Language='de_CH'
;

-- 2024-08-26T13:43:33.299Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(342,'de_CH') 
;

-- 2024-08-26T13:43:33.326Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(342,'de_CH') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:49:15.284Z
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Rechnungsinterval Einh.', PrintName='Rechnungsinterval Einh.',Updated=TO_TIMESTAMP('2024-08-26 13:49:15.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='de_DE'
;

-- 2024-08-26T13:49:15.337Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'de_DE') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:49:34.106Z
UPDATE AD_Element_Trl SET Description='Einheit des Rechnungsintervals',Updated=TO_TIMESTAMP('2024-08-26 13:49:34.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='de_DE'
;

-- 2024-08-26T13:49:34.160Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'de_DE') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:49:56.347Z
UPDATE AD_Element_Trl SET Description='Einheit des Rechnungsintervals', Help='', Name='Rechnungsinterval Einh.', PrintName='Rechnungsinterval Einh.',Updated=TO_TIMESTAMP('2024-08-26 13:49:56.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='de_CH'
;

-- 2024-08-26T13:49:56.398Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(341,'de_CH') 
;

-- 2024-08-26T13:49:56.427Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'de_CH') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:49:58.500Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-26 13:49:58.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='de_CH'
;

-- 2024-08-26T13:49:58.552Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(341,'de_CH') 
;

-- 2024-08-26T13:49:58.577Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'de_CH') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:50:35.135Z
UPDATE AD_Element_Trl SET Help='',Updated=TO_TIMESTAMP('2024-08-26 13:50:35.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='en_US'
;

-- 2024-08-26T13:50:35.188Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'en_US') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:52:13.267Z
UPDATE AD_Element_Trl SET Name='Invoice Interval unit', PrintName='Invoice Interval unit',Updated=TO_TIMESTAMP('2024-08-26 13:52:13.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='de_DE'
;

-- 2024-08-26T13:52:13.321Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'de_DE') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:52:20.641Z
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2024-08-26 13:52:20.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='de_DE'
;

-- 2024-08-26T13:52:20.696Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'de_DE') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:52:59.955Z
UPDATE AD_Element_Trl SET Description='', Name='Invoice Interval unit', PrintName='Invoice Interval unit',Updated=TO_TIMESTAMP('2024-08-26 13:52:59.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='en_US'
;

-- 2024-08-26T13:53:00.008Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'en_US') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:53:35.609Z
UPDATE AD_Element_Trl SET Name='Rechnungsinterval Einh.', PrintName='Rechnungsinterval Einh.',Updated=TO_TIMESTAMP('2024-08-26 13:53:35.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='de_DE'
;

-- 2024-08-26T13:53:35.661Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'de_DE') 
;

-- Element: InvoiceFrequency
-- 2024-08-26T13:53:56.731Z
UPDATE AD_Element_Trl SET Description='Einheit des Rechnungsintervals',Updated=TO_TIMESTAMP('2024-08-26 13:53:56.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=341 AND AD_Language='de_DE'
;

-- 2024-08-26T13:53:56.783Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(341,'de_DE') 
;

-- Element: InvoiceDistance
-- 2024-08-26T13:55:15.736Z
UPDATE AD_Element_Trl SET Description='Betrag der Zeitspanne zwischen zwei Rechnungen', IsTranslated='Y', Name='Rechnungsinterval', PrintName='Rechnungsinterval',Updated=TO_TIMESTAMP('2024-08-26 13:55:15.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=544087 AND AD_Language='de_CH'
;

-- 2024-08-26T13:55:15.788Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(544087,'de_CH') 
;

-- 2024-08-26T13:55:15.813Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544087,'de_CH') 
;

-- Element: InvoiceDistance
-- 2024-08-26T13:55:27.127Z
UPDATE AD_Element_Trl SET Description='Betrag der Zeitspanne zwischen zwei Rechnungen', IsTranslated='Y', Name='Rechnungsinterval', PrintName='Rechnungsinterval',Updated=TO_TIMESTAMP('2024-08-26 13:55:27.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=544087 AND AD_Language='de_DE'
;

-- 2024-08-26T13:55:27.179Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544087,'de_DE') 
;

-- Element: InvoiceDistance
-- 2024-08-26T13:56:02.851Z
UPDATE AD_Element_Trl SET Description='Amount of time between two invoices', Name='Invoice Interval', PrintName='Invoice Interval',Updated=TO_TIMESTAMP('2024-08-26 13:56:02.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=544087 AND AD_Language='en_US'
;

-- 2024-08-26T13:56:02.904Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544087,'en_US') 
;

-- Value: C_InvoiceSchedule_SetValues
-- Classname: de.metas.invoice.process.C_InvoiceSchedule_SetValues
-- 2024-08-26T14:11:54.987Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585421,'Y','de.metas.invoice.process.C_InvoiceSchedule_SetValues','N',TO_TIMESTAMP('2024-08-26 14:11:54.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Erlaubt das Ändern der Werte, die relevant für Rechnungskandidaten sind.
Nach der Änderung werden alle betroffenen Rechnungskandidate als "zu aktualisieren" Markiert.','D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Werte Ändern','json','Y','Y','xls','Java',TO_TIMESTAMP('2024-08-26 14:11:54.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_InvoiceSchedule_SetValues')
;

-- 2024-08-26T14:11:55.015Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585421 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- 2024-08-26T14:12:17.117Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-26 14:12:17.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585421
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- 2024-08-26T14:13:08.421Z
UPDATE AD_Process_Trl SET Description='Allows you to change the values that are relevant for invoice candidates. 
After the change, all affected invoice candidates are marked as "to be updated".', IsTranslated='Y', Name='Change Settings',Updated=TO_TIMESTAMP('2024-08-26 14:13:08.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585421
;

-- Column: C_InvoiceSchedule.InvoiceFrequency
-- Column: C_InvoiceSchedule.InvoiceFrequency
-- 2024-08-26T14:14:58.040Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2024-08-26 14:14:58.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2146
;

-- Column: C_InvoiceSchedule.InvoiceDistance
-- Column: C_InvoiceSchedule.InvoiceDistance
-- 2024-08-26T14:16:07.202Z
UPDATE AD_Column SET IsUpdateable='N', TechnicalNote='Example: InvoiceFrequency = Montly and InvoiceDistance = 1 means that there can be one invoice per month; 3 means that there can be one invoice per quarter.
This value is ignored if the schedule is set to "TwiceMonthly".
Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.',Updated=TO_TIMESTAMP('2024-08-26 14:16:07.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- Column: C_InvoiceSchedule.InvoiceFrequency
-- Column: C_InvoiceSchedule.InvoiceFrequency
-- 2024-08-26T14:16:25.987Z
UPDATE AD_Column SET TechnicalNote='Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.',Updated=TO_TIMESTAMP('2024-08-26 14:16:25.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2146
;

-- Column: C_InvoiceSchedule.InvoiceDay
-- Column: C_InvoiceSchedule.InvoiceDay
-- 2024-08-26T14:16:48.410Z
UPDATE AD_Column SET IsUpdateable='N', TechnicalNote='Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.',Updated=TO_TIMESTAMP('2024-08-26 14:16:48.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2148
;

-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- Column: C_InvoiceSchedule.InvoiceWeekDay
-- 2024-08-26T14:17:06.539Z
UPDATE AD_Column SET IsUpdateable='N', TechnicalNote='Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.',Updated=TO_TIMESTAMP('2024-08-26 14:17:06.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2147
;

-- Column: C_InvoiceSchedule.IsAmount
-- Column: C_InvoiceSchedule.IsAmount
-- 2024-08-26T14:17:26.212Z
UPDATE AD_Column SET IsUpdateable='N', TechnicalNote='Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.',Updated=TO_TIMESTAMP('2024-08-26 14:17:26.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2144
;

-- Column: C_InvoiceSchedule.Amt
-- Column: C_InvoiceSchedule.Amt
-- 2024-08-26T14:18:03.523Z
UPDATE AD_Column SET IsUpdateable='N', TechnicalNote='Not updatable: once a recaord is created, the values can be changed via C_InvoiceSchedule_SetValues.',Updated=TO_TIMESTAMP('2024-08-26 14:18:03.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2145
;

---
--- Add the Process
---

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- Table: C_InvoiceSchedule
-- EntityType: D
-- 2024-08-26T14:18:46.852Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585421,257,541520,TO_TIMESTAMP('2024-08-26 14:18:46.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2024-08-26 14:18:46.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','Y')
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceDistance
-- 2024-08-26T14:21:05.598Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMin) VALUES (0,544087,0,585421,542883,11,'InvoiceDistance',TO_TIMESTAMP('2024-08-26 14:21:05.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'1','Betrag der Zeitspanne zwischen zwei Rechnungen','D',0,'Y','N','Y','N','Y','N','Rechnungsinterval',10,TO_TIMESTAMP('2024-08-26 14:21:05.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'1')
;

-- 2024-08-26T14:21:05.626Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542883 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceFrequency
-- 2024-08-26T14:22:56.844Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,341,0,585421,542884,17,168,'InvoiceFrequency',TO_TIMESTAMP('2024-08-26 14:22:56.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@InvoiceFrequency/M@','Einheit des Rechnungsintervals','D',0,'Y','N','Y','N','Y','N','Rechnungsinterval Einh.',20,TO_TIMESTAMP('2024-08-26 14:22:56.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-26T14:22:56.872Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542884 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceDistance
-- 2024-08-26T14:23:48.700Z
UPDATE AD_Process_Para SET DefaultValue='@InvoiceDistance/1@',Updated=TO_TIMESTAMP('2024-08-26 14:23:48.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542883
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceDistance
-- 2024-08-26T14:23:58.756Z
UPDATE AD_Process_Para SET DefaultValue='1',Updated=TO_TIMESTAMP('2024-08-26 14:23:58.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542883
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceDay
-- 2024-08-26T14:25:18.395Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin) VALUES (0,340,0,585421,542885,11,'InvoiceDay',TO_TIMESTAMP('2024-08-26 14:25:18.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tag des Monats für die Rechnungserstellung.','@InvoiceFrequency/M@=W','D',0,'Die Zahl wird ggf auf den Monats-Ultimo des jeweiligen monats verringert (z.B. bei 31 wird im November wird am 30. fakturiert).','Y','N','Y','N','N','N','Rechnungstag',30,TO_TIMESTAMP('2024-08-26 14:25:18.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'31','1')
;

-- 2024-08-26T14:25:18.421Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542885 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceDay
-- 2024-08-26T14:25:41.903Z
UPDATE AD_Process_Para SET DisplayLogic='@InvoiceFrequency/W@=M',Updated=TO_TIMESTAMP('2024-08-26 14:25:41.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542885
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceDay
-- 2024-08-26T14:26:17.972Z
UPDATE AD_Process_Para SET DefaultValue='1',Updated=TO_TIMESTAMP('2024-08-26 14:26:17.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542885
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceDay
-- 2024-08-26T14:26:26.110Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-08-26 14:26:26.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542885
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceWeekDay
-- 2024-08-26T14:27:53.538Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,342,0,585421,542886,17,167,'InvoiceWeekDay',TO_TIMESTAMP('2024-08-26 14:27:53.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'1','Wochentag für die Rechnungserstellung.','@InvoiceFrequency/M@=W','U',0,'Y','N','Y','N','Y','N','Wochentag',40,TO_TIMESTAMP('2024-08-26 14:27:53.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-26T14:27:53.567Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542886 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: IsAmount
-- 2024-08-26T14:31:43.630Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,349,0,585421,542887,20,'IsAmount',TO_TIMESTAMP('2024-08-26 14:31:43.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@IsAmount/N@','Rechnungen nur erstellen, wenn der Betrag die Betragsgrenze übersteigt.','U',1,'Y','N','Y','N','Y','N','Betragsgrenze',50,TO_TIMESTAMP('2024-08-26 14:31:43.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-26T14:31:43.655Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542887 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: Amt
-- 2024-08-26T14:33:09.347Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,160,0,585421,542888,12,'Amt',TO_TIMESTAMP('2024-08-26 14:33:08.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@Amt/0@','Betrag','@IsAmt/N@=Y','D',0,'Betrag','Y','N','Y','N','Y','N','Betrag',60,TO_TIMESTAMP('2024-08-26 14:33:08.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-26T14:33:09.374Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542888 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: IsAmount
-- 2024-08-26T14:33:19.880Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2024-08-26 14:33:19.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542887
;

-- Process: C_InvoiceSchedule_SetValues(de.metas.invoice.process.C_InvoiceSchedule_SetValues)
-- ParameterName: InvoiceWeekDay
-- 2024-08-26T14:33:32.114Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2024-08-26 14:33:32.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542886
;

---
--- Don't display the two Cutoff-Fields - they aren't used in the code!
---

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Letzter Tag der Lieferungen
-- Column: C_InvoiceSchedule.InvoiceDayCutoff
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> description.Letzter Tag der Lieferungen
-- Column: C_InvoiceSchedule.InvoiceDayCutoff
-- 2024-08-26T14:47:31.447Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2024-08-26 14:47:31.447000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544039
;

-- UI Element: Terminplan Rechnung -> Terminplan Rechnung.Letzter Wochentag der Lieferungen
-- Column: C_InvoiceSchedule.InvoiceWeekDayCutoff
-- UI Element: Terminplan Rechnung(147,D) -> Terminplan Rechnung(193,D) -> main -> 10 -> description.Letzter Wochentag der Lieferungen
-- Column: C_InvoiceSchedule.InvoiceWeekDayCutoff
-- 2024-08-26T14:47:42.982Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2024-08-26 14:47:42.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544041
;

