
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-12T15:53:18.687Z
UPDATE AD_Column SET DefaultValue='N/A', TechnicalNote='Need to set a default value when we have the mandatory logic',Updated=TO_TIMESTAMP('2024-12-12 15:53:18.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589480
;

-- Field: Externes System -> External system config Leich + Mehl -> PLU-Datei senden an
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- Field: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> PLU-Datei senden an
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- 2024-12-12T15:54:00.543Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589479,734081,0,546100,TO_TIMESTAMP('2024-12-12 15:54:00.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,4,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','PLU-Datei senden an',TO_TIMESTAMP('2024-12-12 15:54:00.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-12T15:54:00.544Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-12T15:54:00.569Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583388)
;

-- 2024-12-12T15:54:00.581Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734081
;

-- 2024-12-12T15:54:00.582Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734081)
;

-- Field: Externes System -> External system config Leich + Mehl -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Field: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-12T15:54:00.683Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589480,734082,0,546100,TO_TIMESTAMP('2024-12-12 15:54:00.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespeichert wird',4096,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Server-Verzeichnis',TO_TIMESTAMP('2024-12-12 15:54:00.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-12T15:54:00.685Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-12T15:54:00.686Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583389)
;

-- 2024-12-12T15:54:00.690Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734082
;

-- 2024-12-12T15:54:00.690Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734082)
;

-- Field: Externes System -> External system config Leich + Mehl -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Field: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-12T15:55:50.156Z
UPDATE AD_Field SET DisplayLogic='@PluFileDestination/1TCP@=2DISK',Updated=TO_TIMESTAMP('2024-12-12 15:55:50.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734082
;

-- Field: Externes System -> External system config Leich + Mehl -> LANScale Adresse
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- Field: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> LANScale Adresse
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- 2024-12-12T15:55:52.978Z
UPDATE AD_Field SET DisplayLogic='@PluFileDestination/1TCP@=1TCP',Updated=TO_TIMESTAMP('2024-12-12 15:55:52.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=700751
;

-- Field: Externes System -> External system config Leich + Mehl -> LANScale Port
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- Field: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> LANScale Port
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- 2024-12-12T15:56:03.076Z
UPDATE AD_Field SET DisplayLogic='@PluFileDestination/1TCP@=1TCP',Updated=TO_TIMESTAMP('2024-12-12 15:56:03.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=700752
;

-- UI Column: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10
-- UI Element Group: plu_destincation
-- 2024-12-12T15:56:35.134Z
UPDATE AD_UI_ElementGroup SET Name='plu_destincation',Updated=TO_TIMESTAMP('2024-12-12 15:56:35.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549384
;

-- UI Element: Externes System -> External system config Leich + Mehl.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> plu_destincation.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- 2024-12-12T15:56:50.642Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2024-12-12 15:56:50.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609629
;

-- UI Element: Externes System -> External system config Leich + Mehl.TCP_PortNumber
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> plu_destincation.TCP_PortNumber
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- 2024-12-12T15:56:56.228Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2024-12-12 15:56:56.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609628
;

-- UI Element: Externes System -> External system config Leich + Mehl.PluFileDestination
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> plu_destincation.PluFileDestination
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- 2024-12-12T15:57:21.645Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734081,0,546100,549384,627392,'F',TO_TIMESTAMP('2024-12-12 15:57:21.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'PluFileDestination',10,0,0,TO_TIMESTAMP('2024-12-12 15:57:21.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externes System -> External system config Leich + Mehl.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> plu_destincation.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-12T15:57:38.621Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734082,0,546100,549384,627393,'F',TO_TIMESTAMP('2024-12-12 15:57:38.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespeichert wird','Y','N','N','Y','N','N','N',0,'PluFileLocalFolder',40,0,0,TO_TIMESTAMP('2024-12-12 15:57:38.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.External System Config
-- Column: ExternalSystem_Config_LeichMehl.ExternalSystem_Config_ID
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> default.External System Config
-- Column: ExternalSystem_Config_LeichMehl.ExternalSystem_Config_ID
-- 2024-12-12T15:59:34.125Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609616
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> template_directory.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- 2024-12-12T15:59:34.133Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627372
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.Suchschlüssel
-- Column: ExternalSystem_Config_LeichMehl.ExternalSystemValue
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> default.Suchschlüssel
-- Column: ExternalSystem_Config_LeichMehl.ExternalSystemValue
-- 2024-12-12T15:59:34.140Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609617
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.Product_BaseFolderName
-- Column: ExternalSystem_Config_LeichMehl.Product_BaseFolderName
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> template_directory.Product_BaseFolderName
-- Column: ExternalSystem_Config_LeichMehl.Product_BaseFolderName
-- 2024-12-12T15:59:34.148Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609633
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.PLU-Datei Exportprüfung aktivieren
-- Column: ExternalSystem_Config_LeichMehl.IsPluFileExportAuditEnabled
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 20 -> file export audit.PLU-Datei Exportprüfung aktivieren
-- Column: ExternalSystem_Config_LeichMehl.IsPluFileExportAuditEnabled
-- 2024-12-12T15:59:34.155Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=610513
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.PluFileDestination
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> plu_destincation.PluFileDestination
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- 2024-12-12T15:59:34.162Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627388
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> plu_destincation.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- 2024-12-12T15:59:34.170Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609632
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.TCP_PortNumber
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> plu_destincation.TCP_PortNumber
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- 2024-12-12T15:59:34.177Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609631
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> plu_destincation.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-12T15:59:34.184Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627389
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.Aktiv
-- Column: ExternalSystem_Config_LeichMehl.IsActive
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 20 -> flags.Aktiv
-- Column: ExternalSystem_Config_LeichMehl.IsActive
-- 2024-12-12T15:59:34.190Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609618
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.Sektion
-- Column: ExternalSystem_Config_LeichMehl.AD_Org_ID
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Config_LeichMehl.AD_Org_ID
-- 2024-12-12T15:59:34.196Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-12-12 15:59:34.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609619
;

-- UI Element: Externes System -> External system config Leich + Mehl.ExternalSystem_Config_LeichMehl
-- Column: ExternalSystem_Config_LeichMehl.ExternalSystem_Config_LeichMehl_ID
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> default.ExternalSystem_Config_LeichMehl
-- Column: ExternalSystem_Config_LeichMehl.ExternalSystem_Config_LeichMehl_ID
-- 2024-12-12T15:59:41.261Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-12-12 15:59:41.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=605382
;

-- UI Element: Externes System -> External system config Leich + Mehl.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> plu_destincation.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- 2024-12-12T15:59:41.268Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-12-12 15:59:41.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609629
;

-- UI Element: Externes System -> External system config Leich + Mehl.TCP_PortNumber
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> plu_destincation.TCP_PortNumber
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- 2024-12-12T15:59:41.274Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-12-12 15:59:41.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609628
;

-- UI Element: Externes System -> External system config Leich + Mehl.Suchschlüssel
-- Column: ExternalSystem_Config_LeichMehl.ExternalSystemValue
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> default.Suchschlüssel
-- Column: ExternalSystem_Config_LeichMehl.ExternalSystemValue
-- 2024-12-12T15:59:41.280Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-12-12 15:59:41.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=605383
;

-- UI Element: Externes System -> External system config Leich + Mehl.PluFileDestination
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> plu_destincation.PluFileDestination
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- 2024-12-12T15:59:41.286Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-12-12 15:59:41.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627392
;

-- UI Element: Externes System -> External system config Leich + Mehl.Product_BaseFolderName
-- Column: ExternalSystem_Config_LeichMehl.Product_BaseFolderName
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> directory.Product_BaseFolderName
-- Column: ExternalSystem_Config_LeichMehl.Product_BaseFolderName
-- 2024-12-12T15:59:41.292Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-12-12 15:59:41.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609630
;

-- UI Element: Externes System -> External system config Leich + Mehl.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> directory.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- 2024-12-12T15:59:41.298Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-12-12 15:59:41.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627373
;

-- UI Element: Externes System -> External system config Leich + Mehl.PLU-Datei Exportprüfung aktivieren
-- Column: ExternalSystem_Config_LeichMehl.IsPluFileExportAuditEnabled
-- UI Element: Externes System(541024,de.metas.externalsystem) -> External system config Leich + Mehl(546100,de.metas.externalsystem) -> main -> 20 -> file export audit.PLU-Datei Exportprüfung aktivieren
-- Column: ExternalSystem_Config_LeichMehl.IsPluFileExportAuditEnabled
-- 2024-12-12T15:59:41.304Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-12-12 15:59:41.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=610514
;

