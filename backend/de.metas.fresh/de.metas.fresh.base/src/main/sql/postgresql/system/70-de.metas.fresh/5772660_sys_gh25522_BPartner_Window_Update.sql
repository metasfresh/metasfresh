-- Run mode: SWING_CLIENT

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Lieferstandard
-- Column: C_BPartner_Location.IsShipTo
-- 2025-10-07T09:58:04.445Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753801,0,548422,553516,637773,'F',TO_TIMESTAMP('2025-10-07 09:58:03.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Liefer-Adresse für den Geschäftspartner','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.','Y','N','N','Y','N','N','N',0,'Lieferstandard',90,0,0,TO_TIMESTAMP('2025-10-07 09:58:03.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: IsShipperHasRoutingcode
-- 2025-10-08T07:30:09.372Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Versender hat Routingcode', PrintName='Versender hat Routingcode',Updated=TO_TIMESTAMP('2025-10-08 07:30:09.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583861 AND AD_Language='de_DE'
;

-- 2025-10-08T07:30:09.453Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-08T07:30:21.315Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583861,'de_DE')
;

-- 2025-10-08T07:30:21.394Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583861,'de_DE')
;

-- Element: IsShipperHasRoutingcode
-- 2025-10-08T07:30:37.562Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Versender hat Routingcode', PrintName='Versender hat Routingcode',Updated=TO_TIMESTAMP('2025-10-08 07:30:37.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583861 AND AD_Language='de_CH'
;

-- 2025-10-08T07:30:37.639Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-08T07:30:47.293Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583861,'de_CH')
;

-- Field: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> Leitcode
-- Column: C_BPartner_Location.M_Shipper_RoutingCode_ID
-- 2025-10-08T08:40:55.588Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2025-10-08 08:40:55.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753823
;

-- Field: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> Versender hat Routingcode
-- Column: C_BPartner_Location.IsShipperHasRoutingcode
-- 2025-10-08T08:41:29.699Z
UPDATE AD_Field SET DisplayLogic='1=2',Updated=TO_TIMESTAMP('2025-10-08 08:41:29.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753824
;

-- Field: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> Vorherige Adresse
-- Column: C_BPartner_Location.Previous_ID
-- 2025-10-08T08:42:26.935Z
UPDATE AD_Field SET DisplayLogic='@ValidFrom/''1970-01-01''@ > ''1970-01-01''',Updated=TO_TIMESTAMP('2025-10-08 08:42:26.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753818
;

-- Field: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> Lieferadresse
-- Column: C_BPartner_Location.IsShipTo
-- 2025-10-08T08:44:50.092Z
UPDATE AD_Field SET AD_Name_ID=580755, Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners', Name='Lieferadresse',Updated=TO_TIMESTAMP('2025-10-08 08:44:50.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753801
;

-- 2025-10-08T08:44:50.172Z
UPDATE AD_Field_Trl trl SET Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners',Help='Identifiziert die Adresse des Geschäftspartners',Name='Lieferadresse' WHERE AD_Field_ID=753801 AND AD_Language='de_DE'
;

-- 2025-10-08T08:44:50.251Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580755)
;

-- 2025-10-08T08:44:50.333Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753801
;

-- 2025-10-08T08:44:50.410Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753801)
;

-- Field: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> Lieferstandard
-- Column: C_BPartner_Location.IsShipToDefault
-- 2025-10-08T08:45:44.991Z
UPDATE AD_Field SET AD_Name_ID=580756, Description='Lieferstandard für den Geschäftspartner', Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.', Name='Lieferstandard',Updated=TO_TIMESTAMP('2025-10-08 08:45:44.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753828
;

-- 2025-10-08T08:45:45.069Z
UPDATE AD_Field_Trl trl SET Description='Lieferstandard für den Geschäftspartner',Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.',Name='Lieferstandard' WHERE AD_Field_ID=753828 AND AD_Language='de_DE'
;

-- 2025-10-08T08:45:45.152Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580756)
;

-- 2025-10-08T08:45:45.232Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753828
;

-- 2025-10-08T08:45:45.311Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753828)
;

-- Field: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> Rechnungsadresse
-- Column: C_BPartner_Location.IsBillTo
-- 2025-10-08T08:46:28.088Z
UPDATE AD_Field SET AD_Name_ID=580757, Description='Standort des Geschäftspartners für die Rechnungsstellung', Help=NULL, Name='Rechnungsadresse',Updated=TO_TIMESTAMP('2025-10-08 08:46:28.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753800
;

-- 2025-10-08T08:46:28.167Z
UPDATE AD_Field_Trl trl SET Description='Standort des Geschäftspartners für die Rechnungsstellung',Help=NULL,Name='Rechnungsadresse' WHERE AD_Field_ID=753800 AND AD_Language='de_DE'
;

-- 2025-10-08T08:46:28.248Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580757)
;

-- 2025-10-08T08:46:28.330Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753800
;

-- 2025-10-08T08:46:28.409Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753800)
;

-- Field: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> Rechnungsstandard
-- Column: C_BPartner_Location.IsBillToDefault
-- 2025-10-08T08:47:20.210Z
UPDATE AD_Field SET AD_Name_ID=580758, Description=NULL, Help=NULL, Name='Rechnungsstandard',Updated=TO_TIMESTAMP('2025-10-08 08:47:20.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753825
;

-- 2025-10-08T08:47:20.288Z
UPDATE AD_Field_Trl trl SET Name='Rechnungsstandard' WHERE AD_Field_ID=753825 AND AD_Language='de_DE'
;

-- 2025-10-08T08:47:20.367Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580758)
;

-- 2025-10-08T08:47:20.447Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753825
;

-- 2025-10-08T08:47:20.527Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753825)
;

-- Field: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> Abw. Firmenname
-- Column: C_BPartner_Location.BPartnerName
-- 2025-10-08T08:48:18.548Z
UPDATE AD_Field SET AD_Name_ID=577370, Description=NULL, Help=NULL, Name='Abw. Firmenname',Updated=TO_TIMESTAMP('2025-10-08 08:48:18.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753814
;

-- 2025-10-08T08:48:18.627Z
UPDATE AD_Field_Trl trl SET Name='Abw. Firmenname' WHERE AD_Field_ID=753814 AND AD_Language='de_DE'
;

-- 2025-10-08T08:48:18.706Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577370)
;

-- 2025-10-08T08:48:18.782Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753814
;

-- 2025-10-08T08:48:18.858Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753814)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Lieferstandard
-- Column: C_BPartner_Location.IsShipTo
-- 2025-10-08T08:50:50.705Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637143
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Besuchsadresse
-- Column: C_BPartner_Location.VisitorsAddress
-- 2025-10-08T08:52:58.341Z
UPDATE AD_UI_Element SET SeqNo=205,Updated=TO_TIMESTAMP('2025-10-08 08:52:58.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637153
;

-- Field: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> Gültig ab
-- Column: C_BPartner_Location.ValidFrom
-- 2025-10-08T08:56:03.315Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2025-10-08 08:56:03.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753817
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Name
-- Column: C_BPartner_Location.Name
-- 2025-10-08T08:58:47.664Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-08 08:58:47.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637136
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.GLN
-- Column: C_BPartner_Location.GLN
-- 2025-10-08T08:58:48.132Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-08 08:58:48.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637140
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Aktiv
-- Column: C_BPartner_Location.IsActive
-- 2025-10-08T08:58:48.596Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-08 08:58:48.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637142
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Liefer Standard Adresse
-- Column: C_BPartner_Location.IsShipToDefault
-- 2025-10-08T08:58:49.061Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-08 08:58:49.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637144
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Lieferstandard
-- Column: C_BPartner_Location.IsShipTo
-- 2025-10-08T08:58:49.524Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-08 08:58:49.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637773
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Replikations Standardwert
-- Column: C_BPartner_Location.IsReplicationLookupDefault
-- 2025-10-08T08:58:49.992Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-08 08:58:49.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637152
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Rechnung Standard Adresse
-- Column: C_BPartner_Location.IsBillToDefault
-- 2025-10-08T08:58:50.454Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-08 08:58:50.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637146
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Abladeort
-- Column: C_BPartner_Location.IsHandOverLocation
-- 2025-10-08T08:58:50.918Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-08 08:58:50.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637147
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Vorherige Adresse
-- Column: C_BPartner_Location.Previous_ID
-- 2025-10-08T08:58:51.382Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-10-08 08:58:51.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637157
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner_Location.AD_Org_ID
-- 2025-10-08T08:58:51.845Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-10-08 08:58:51.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637160
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Replikations Standardwert
-- Column: C_BPartner_Location.IsReplicationLookupDefault
-- 2025-10-08T09:01:32.091Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-10-08 09:01:32.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637152
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Vorbelegung Rechnung
-- Column: C_BPartner_Location.IsBillTo
-- 2025-10-08T09:01:32.567Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-08 09:01:32.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637145
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Vorherige Adresse
-- Column: C_BPartner_Location.Previous_ID
-- 2025-10-08T09:02:34.299Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-10-08 09:02:34.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637157
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Erstattungs-Adresse
-- Column: C_BPartner_Location.IsRemitTo
-- 2025-10-08T09:02:34.769Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-10-08 09:02:34.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637148
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Replikations Standardwert
-- Column: C_BPartner_Location.IsReplicationLookupDefault
-- 2025-10-08T09:02:35.236Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-10-08 09:02:35.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637152
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Besuchsadresse
-- Column: C_BPartner_Location.VisitorsAddress
-- 2025-10-08T09:02:35.708Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-10-08 09:02:35.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637153
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Einmaladresse(548422,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner_Location.AD_Org_ID
-- 2025-10-08T09:02:36.173Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-10-08 09:02:36.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637160
;

