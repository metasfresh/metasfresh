-- Run mode: SWING_CLIENT

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Kontaktname Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_ContactName
-- 2026-04-20T14:49:49.417Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592370,777078,0,548456,TO_TIMESTAMP('2026-04-20 14:49:49.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Kontaktname Empfänger',TO_TIMESTAMP('2026-04-20 14:49:49.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-20T14:49:49.427Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-20T14:49:49.453Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584766)
;

-- 2026-04-20T14:49:49.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777078
;

-- 2026-04-20T14:49:49.479Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777078)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Abteilung Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Department
-- 2026-04-20T14:56:53.347Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592367,777079,0,548456,TO_TIMESTAMP('2026-04-20 14:56:53.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Abteilung Empfänger',TO_TIMESTAMP('2026-04-20 14:56:53.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-20T14:56:53.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-20T14:56:53.366Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584763)
;

-- 2026-04-20T14:56:53.369Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777079
;

-- 2026-04-20T14:56:53.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777079)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Kontaktname Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_ContactName
-- 2026-04-20T14:57:18.524Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592369,777080,0,548456,TO_TIMESTAMP('2026-04-20 14:57:18.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Kontaktname Lieferant',TO_TIMESTAMP('2026-04-20 14:57:18.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-20T14:57:18.529Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-20T14:57:18.534Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584765)
;

-- 2026-04-20T14:57:18.540Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777080
;

-- 2026-04-20T14:57:18.543Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777080)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Abteilung Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_Department
-- 2026-04-20T14:58:00.647Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592368,777081,0,548456,TO_TIMESTAMP('2026-04-20 14:58:00.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Abteilung Lieferant',TO_TIMESTAMP('2026-04-20 14:58:00.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-20T14:58:00.654Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-20T14:58:00.660Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584764)
;

-- 2026-04-20T14:58:00.668Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777081
;

-- 2026-04-20T14:58:00.670Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777081)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> E-Mail Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_Email
-- 2026-04-20T14:58:11.610Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592365,777082,0,548456,TO_TIMESTAMP('2026-04-20 14:58:11.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','E-Mail Lieferant',TO_TIMESTAMP('2026-04-20 14:58:11.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-20T14:58:11.613Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-20T14:58:11.616Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584761)
;

-- 2026-04-20T14:58:11.620Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777082
;

-- 2026-04-20T14:58:11.621Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777082)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Telefon Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_Phone
-- 2026-04-20T14:58:18.975Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592366,777083,0,548456,TO_TIMESTAMP('2026-04-20 14:58:18.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,20,'D','Y','N','N','N','N','N','N','N','Telefon Lieferant',TO_TIMESTAMP('2026-04-20 14:58:18.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-20T14:58:18.988Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-20T14:58:18.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584762)
;

-- 2026-04-20T14:58:18.999Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777083
;

-- 2026-04-20T14:58:19.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777083)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Kontaktname Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_ContactName
-- 2026-04-20T14:59:36.103Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777080,0,548456,553599,649827,'F',TO_TIMESTAMP('2026-04-20 14:59:35.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Kontaktname Lieferant',100,0,0,TO_TIMESTAMP('2026-04-20 14:59:35.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Abteilung Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_Department
-- 2026-04-20T14:59:46.314Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777081,0,548456,553599,649828,'F',TO_TIMESTAMP('2026-04-20 14:59:46.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Abteilung Lieferant',110,0,0,TO_TIMESTAMP('2026-04-20 14:59:46.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.E-Mail Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_Email
-- 2026-04-20T15:00:08.244Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777082,0,548456,553599,649829,'F',TO_TIMESTAMP('2026-04-20 15:00:08.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'E-Mail Lieferant',120,0,0,TO_TIMESTAMP('2026-04-20 15:00:08.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Telefon Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_Phone
-- 2026-04-20T15:00:21.448Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777083,0,548456,553599,649830,'F',TO_TIMESTAMP('2026-04-20 15:00:21.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Telefon Lieferant',130,0,0,TO_TIMESTAMP('2026-04-20 15:00:21.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Kontaktname Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_ContactName
-- 2026-04-20T15:00:47.668Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777078,0,548456,553601,649831,'F',TO_TIMESTAMP('2026-04-20 15:00:47.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Kontaktname Empfänger',130,0,0,TO_TIMESTAMP('2026-04-20 15:00:47.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Abteilung Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Department
-- 2026-04-20T15:01:13.195Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777079,0,548456,553601,649832,'F',TO_TIMESTAMP('2026-04-20 15:01:13.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Abteilung Empfänger',140,0,0,TO_TIMESTAMP('2026-04-20 15:01:13.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> Zolltarifnummer
-- Column: Carrier_ShipmentOrder_Item.CustomsTariffNumber
-- 2026-04-20T15:04:56.293Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592364,777084,0,548459,TO_TIMESTAMP('2026-04-20 15:04:56.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Zolltarifnummer',TO_TIMESTAMP('2026-04-20 15:04:56.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-20T15:04:56.304Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-20T15:04:56.307Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577247)
;

-- 2026-04-20T15:04:56.316Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777084
;

-- 2026-04-20T15:04:56.323Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777084)
;

-- UI Element: Versandauftragspaket(541957,D) -> Shipment Order Item(548459,D) -> main -> 10 -> main.Zolltarifnummer
-- Column: Carrier_ShipmentOrder_Item.CustomsTariffNumber
-- 2026-04-20T15:05:25.812Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777084,0,548459,553610,649833,'F',TO_TIMESTAMP('2026-04-20 15:05:25.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Zolltarifnummer',90,0,0,TO_TIMESTAMP('2026-04-20 15:05:25.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: Shipper_Name1
-- 2026-04-20T15:07:14.526Z
UPDATE AD_Element_Trl SET Name='Name 1 Shipper', PrintName='Name 1 Shipper',Updated=TO_TIMESTAMP('2026-04-20 15:07:14.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584020 AND AD_Language='en_US'
;

-- 2026-04-20T15:07:14.527Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:07:15.555Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584020,'en_US')
;

-- Element: Shipper_Name1
-- 2026-04-20T15:07:18.217Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:07:18.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584020 AND AD_Language='en_US'
;

-- 2026-04-20T15:07:18.227Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584020,'en_US')
;

-- Element: Shipper_Name1
-- 2026-04-20T15:07:23.310Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Name 1 Lieferant', PrintName='Name 1 Lieferant',Updated=TO_TIMESTAMP('2026-04-20 15:07:23.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584020 AND AD_Language='de_CH'
;

-- 2026-04-20T15:07:23.315Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:07:23.746Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584020,'de_CH')
;

-- Element: Shipper_Name1
-- 2026-04-20T15:07:30.509Z
UPDATE AD_Element_Trl SET Name='Name 1 Lieferant', PrintName='Name 1 Lieferant',Updated=TO_TIMESTAMP('2026-04-20 15:07:30.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584020 AND AD_Language='de_DE'
;

-- 2026-04-20T15:07:30.511Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:07:31.733Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584020,'de_DE')
;

-- 2026-04-20T15:07:31.735Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584020,'de_DE')
;

-- Element: Shipper_Name2
-- 2026-04-20T15:08:11.245Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Name 2 Shipper', PrintName='Name 2 Shipper',Updated=TO_TIMESTAMP('2026-04-20 15:08:11.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584021 AND AD_Language='en_US'
;

-- 2026-04-20T15:08:11.246Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:08:11.552Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584021,'en_US')
;

-- Element: Shipper_Name2
-- 2026-04-20T15:08:17.403Z
UPDATE AD_Element_Trl SET Name='Name 2 Lieferant', PrintName='Name 2 Lieferant',Updated=TO_TIMESTAMP('2026-04-20 15:08:17.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584021 AND AD_Language='de_DE'
;

-- 2026-04-20T15:08:17.405Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:08:17.811Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584021,'de_DE')
;

-- 2026-04-20T15:08:17.812Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584021,'de_DE')
;

-- Element: Shipper_Name2
-- 2026-04-20T15:08:23.316Z
UPDATE AD_Element_Trl SET Name='Name 2 Lieferant', PrintName='Name 2 Lieferant',Updated=TO_TIMESTAMP('2026-04-20 15:08:23.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584021 AND AD_Language='de_CH'
;

-- 2026-04-20T15:08:23.319Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:08:23.607Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584021,'de_CH')
;

-- Element: Shipper_Name2
-- 2026-04-20T15:08:24.448Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:08:24.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584021 AND AD_Language='de_DE'
;

-- 2026-04-20T15:08:24.453Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584021,'de_DE')
;

-- 2026-04-20T15:08:24.458Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584021,'de_DE')
;

-- Element: Shipper_Name2
-- 2026-04-20T15:08:26.050Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:08:26.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584021 AND AD_Language='de_CH'
;

-- 2026-04-20T15:08:26.053Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584021,'de_CH')
;

-- Element: Shipper_CountryISO2Code
-- 2026-04-20T15:09:11.761Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Country Code Shipper (ISO-2)', PrintName='Country Code Shipper (ISO-2)',Updated=TO_TIMESTAMP('2026-04-20 15:09:11.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584018 AND AD_Language='en_US'
;

-- 2026-04-20T15:09:11.762Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:09:12.224Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584018,'en_US')
;

-- Element: Shipper_CountryISO2Code
-- 2026-04-20T15:09:20.417Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ländercode Lieferant (ISO-2)', PrintName='Ländercode Lieferant (ISO-2)',Updated=TO_TIMESTAMP('2026-04-20 15:09:20.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584018 AND AD_Language='de_CH'
;

-- 2026-04-20T15:09:20.421Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:09:20.897Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584018,'de_CH')
;

-- Element: Shipper_CountryISO2Code
-- 2026-04-20T15:09:27.277Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ländercode Lieferant (ISO-2)', PrintName='Ländercode Lieferant (ISO-2)',Updated=TO_TIMESTAMP('2026-04-20 15:09:27.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584018 AND AD_Language='de_DE'
;

-- 2026-04-20T15:09:27.278Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:09:27.756Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584018,'de_DE')
;

-- 2026-04-20T15:09:27.759Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584018,'de_DE')
;

-- Element: Shipper_EORI
-- 2026-04-20T15:10:16.236Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EORI Number Shipper', PrintName='EORI Number Shipper',Updated=TO_TIMESTAMP('2026-04-20 15:10:16.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584038 AND AD_Language='en_US'
;

-- 2026-04-20T15:10:16.237Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:10:16.620Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584038,'en_US')
;

-- Element: Shipper_EORI
-- 2026-04-20T15:10:25.932Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EORI-Nummer Lieferant', PrintName='EORI-Nummer Lieferant',Updated=TO_TIMESTAMP('2026-04-20 15:10:25.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584038 AND AD_Language='de_CH'
;

-- 2026-04-20T15:10:25.933Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:10:26.247Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584038,'de_CH')
;

-- Element: Shipper_EORI
-- 2026-04-20T15:10:31.387Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EORI-Nummer Lieferant', PrintName='EORI-Nummer Lieferant',Updated=TO_TIMESTAMP('2026-04-20 15:10:31.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584038 AND AD_Language='de_DE'
;

-- 2026-04-20T15:10:31.388Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:10:31.833Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584038,'de_DE')
;

-- 2026-04-20T15:10:31.835Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584038,'de_DE')
;

-- Element: Receiver_CountryISO2Code
-- 2026-04-20T15:11:32.332Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Country Code Receiver (ISO-2)', PrintName='Country Code Receiver (ISO-2)',Updated=TO_TIMESTAMP('2026-04-20 15:11:32.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584005 AND AD_Language='en_US'
;

-- 2026-04-20T15:11:32.334Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:11:32.647Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584005,'en_US')
;

-- Element: Receiver_CountryISO2Code
-- 2026-04-20T15:11:36.479Z
UPDATE AD_Element_Trl SET Name='Ländercode Empfänger (ISO-2)', PrintName='Ländercode Empfänger (ISO-2)',Updated=TO_TIMESTAMP('2026-04-20 15:11:36.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584005 AND AD_Language='de_CH'
;

-- 2026-04-20T15:11:36.480Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:11:36.672Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584005,'de_CH')
;

-- Element: Receiver_CountryISO2Code
-- 2026-04-20T15:11:41.135Z
UPDATE AD_Element_Trl SET Name='Ländercode Empfänger (ISO-2)', PrintName='Ländercode Empfänger (ISO-2)',Updated=TO_TIMESTAMP('2026-04-20 15:11:41.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584005 AND AD_Language='de_DE'
;

-- 2026-04-20T15:11:41.136Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:11:41.525Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584005,'de_DE')
;

-- 2026-04-20T15:11:41.531Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584005,'de_DE')
;

-- Element: Receiver_CountryISO2Code
-- 2026-04-20T15:11:42.667Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:11:42.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584005 AND AD_Language='de_CH'
;

-- 2026-04-20T15:11:42.676Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584005,'de_CH')
;

-- Element: Receiver_CountryISO2Code
-- 2026-04-20T15:11:43.558Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:11:43.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584005 AND AD_Language='de_DE'
;

-- 2026-04-20T15:11:43.561Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584005,'de_DE')
;

-- 2026-04-20T15:11:43.563Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584005,'de_DE')
;

-- Element: Receiver_EORI
-- 2026-04-20T15:12:11.316Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EORI Number Receiver', PrintName='EORI Number Receiver',Updated=TO_TIMESTAMP('2026-04-20 15:12:11.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584039 AND AD_Language='en_US'
;

-- 2026-04-20T15:12:11.317Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:12:11.551Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584039,'en_US')
;

-- Element: Receiver_EORI
-- 2026-04-20T15:12:16.190Z
UPDATE AD_Element_Trl SET Name='EORI-Nummer Empfänger', PrintName='EORI-Nummer Empfänger',Updated=TO_TIMESTAMP('2026-04-20 15:12:16.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584039 AND AD_Language='de_DE'
;

-- 2026-04-20T15:12:16.193Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:12:16.468Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584039,'de_DE')
;

-- 2026-04-20T15:12:16.469Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584039,'de_DE')
;

-- Element: Receiver_EORI
-- 2026-04-20T15:12:24.011Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EORI-Nummer Empfänger', PrintName='EORI-Nummer Empfänger',Updated=TO_TIMESTAMP('2026-04-20 15:12:24.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584039 AND AD_Language='de_CH'
;

-- 2026-04-20T15:12:24.014Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:12:24.236Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584039,'de_CH')
;

-- Element: Receiver_EORI
-- 2026-04-20T15:12:25.908Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:12:25.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584039 AND AD_Language='de_DE'
;

-- 2026-04-20T15:12:25.916Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584039,'de_DE')
;

-- 2026-04-20T15:12:25.921Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584039,'de_DE')
;

-- Element: WeightInKg
-- 2026-04-20T15:17:20.364Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:17:20.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577306 AND AD_Language='de_CH'
;

-- 2026-04-20T15:17:20.369Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577306,'de_CH')
;

-- Element: WeightInKg
-- 2026-04-20T15:17:20.766Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:17:20.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577306 AND AD_Language='de_DE'
;

-- 2026-04-20T15:17:20.767Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577306,'de_DE')
;

-- 2026-04-20T15:17:20.768Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577306,'de_DE')
;

-- Element: WeightInKg
-- 2026-04-20T15:17:27.283Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:17:27.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577306 AND AD_Language='en_US'
;

-- 2026-04-20T15:17:27.290Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577306,'en_US')
;

-- Element: LengthInCm
-- 2026-04-20T15:17:59.381Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:17:59.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577307 AND AD_Language='en_US'
;

-- 2026-04-20T15:17:59.393Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577307,'en_US')
;

-- Element: LengthInCm
-- 2026-04-20T15:17:59.933Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:17:59.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577307 AND AD_Language='de_CH'
;

-- 2026-04-20T15:17:59.936Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577307,'de_CH')
;

-- Element: LengthInCm
-- 2026-04-20T15:18:01.108Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:18:01.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577307 AND AD_Language='de_DE'
;

-- 2026-04-20T15:18:01.111Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577307,'de_DE')
;

-- 2026-04-20T15:18:01.113Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577307,'de_DE')
;

-- Element: WidthInCm
-- 2026-04-20T15:18:16.131Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:18:16.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577308 AND AD_Language='de_CH'
;

-- 2026-04-20T15:18:16.136Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577308,'de_CH')
;

-- Element: WidthInCm
-- 2026-04-20T15:18:16.695Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:18:16.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577308 AND AD_Language='en_US'
;

-- 2026-04-20T15:18:16.704Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577308,'en_US')
;

-- Element: WidthInCm
-- 2026-04-20T15:18:17.747Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:18:17.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577308 AND AD_Language='de_DE'
;

-- 2026-04-20T15:18:17.750Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577308,'de_DE')
;

-- 2026-04-20T15:18:17.752Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577308,'de_DE')
;

-- Element: HeightInCm
-- 2026-04-20T15:18:47.184Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:18:47.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577309 AND AD_Language='en_US'
;

-- 2026-04-20T15:18:47.191Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577309,'en_US')
;

-- Element: HeightInCm
-- 2026-04-20T15:18:47.613Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:18:47.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577309 AND AD_Language='de_CH'
;

-- 2026-04-20T15:18:47.622Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577309,'de_CH')
;

-- Element: HeightInCm
-- 2026-04-20T15:18:48.453Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:18:48.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577309 AND AD_Language='de_DE'
;

-- 2026-04-20T15:18:48.454Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577309,'de_DE')
;

-- 2026-04-20T15:18:48.455Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577309,'de_DE')
;

-- Element: QtyShipped
-- 2026-04-20T15:19:17.664Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:19:17.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=579558 AND AD_Language='en_US'
;

-- 2026-04-20T15:19:17.671Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579558,'en_US')
;

-- Element: QtyShipped
-- 2026-04-20T15:19:18.254Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:19:18.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=579558 AND AD_Language='de_CH'
;

-- 2026-04-20T15:19:18.259Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579558,'de_CH')
;

-- Element: QtyShipped
-- 2026-04-20T15:19:19.817Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:19:19.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=579558 AND AD_Language='de_DE'
;

-- 2026-04-20T15:19:19.826Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579558,'de_DE')
;

-- 2026-04-20T15:19:19.829Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579558,'de_DE')
;

-- Element: ArticleValue
-- 2026-04-20T15:20:09.345Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Article Value', PrintName='Article Value',Updated=TO_TIMESTAMP('2026-04-20 15:20:09.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583535 AND AD_Language='en_US'
;

-- 2026-04-20T15:20:09.351Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:20:09.557Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583535,'en_US')
;

-- Element: ArticleValue
-- 2026-04-20T15:20:10.195Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:20:10.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583535 AND AD_Language='de_CH'
;

-- 2026-04-20T15:20:10.206Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583535,'de_CH')
;

-- Element: ArticleValue
-- 2026-04-20T15:20:11.296Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:20:11.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583535 AND AD_Language='de_DE'
;

-- 2026-04-20T15:20:11.299Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583535,'de_DE')
;

-- 2026-04-20T15:20:11.301Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583535,'de_DE')
;

-- Element: Carrier_ShipmentOrder_Item_ID
-- 2026-04-20T15:27:59.631Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:27:59.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584040 AND AD_Language='en_US'
;

-- 2026-04-20T15:27:59.647Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584040,'en_US')
;

-- Element: Carrier_ShipmentOrder_Item_ID
-- 2026-04-20T15:30:19.262Z
UPDATE AD_Element_Trl SET Name='Paketposition', PrintName='Paketposition',Updated=TO_TIMESTAMP('2026-04-20 15:30:19.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584040 AND AD_Language='de_CH'
;

-- 2026-04-20T15:30:19.263Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:30:19.438Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584040,'de_CH')
;

-- Element: Carrier_ShipmentOrder_Item_ID
-- 2026-04-20T15:31:03.880Z
UPDATE AD_Element_Trl SET Name='Paketposition', PrintName='Paketposition',Updated=TO_TIMESTAMP('2026-04-20 15:31:03.879000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584040 AND AD_Language='de_DE'
;

-- 2026-04-20T15:31:03.881Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:31:04.169Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584040,'de_DE')
;

-- 2026-04-20T15:31:04.177Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584040,'de_DE')
;

-- Element: Carrier_ShipmentOrder_Item_ID
-- 2026-04-20T15:31:10.711Z
UPDATE AD_Element_Trl SET Name='Parcel Line', PrintName='Parcel Line',Updated=TO_TIMESTAMP('2026-04-20 15:31:10.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584040 AND AD_Language='en_US'
;

-- 2026-04-20T15:31:10.715Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-20T15:31:10.913Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584040,'en_US')
;

-- Element: Carrier_ShipmentOrder_Item_ID
-- 2026-04-20T15:31:11.363Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-20 15:31:11.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584040 AND AD_Language='de_DE'
;

-- 2026-04-20T15:31:11.366Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584040,'de_DE')
;

-- 2026-04-20T15:31:11.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584040,'de_DE')
;

