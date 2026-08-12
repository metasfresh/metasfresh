-- Run mode: SWING_CLIENT

-- Field: Inventur(168,D) -> Bestandszählungs Position(256,D) -> Packvorschrift (LU)
-- Column: M_InventoryLine.M_LU_HU_PI_ID
-- 2025-09-29T20:18:19.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591167,754213,0,256,TO_TIMESTAMP('2025-09-29 20:18:18.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Packvorschrift (LU)',TO_TIMESTAMP('2025-09-29 20:18:18.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T20:18:19.121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754213 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T20:18:19.128Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542487)
;

-- 2025-09-29T20:18:19.194Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754213
;

-- 2025-09-29T20:18:19.201Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754213)
;
















-- Run mode: SWING_CLIENT

-- UI Section: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main
-- UI Column: 20
-- 2025-09-29T20:22:53.495Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548479,540680,TO_TIMESTAMP('2025-09-29 20:22:53.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-09-29 20:22:53.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-09-29T20:23:19.020Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548479,553550,TO_TIMESTAMP('2025-09-29 20:23:18.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org&client',10,TO_TIMESTAMP('2025-09-29 20:23:18.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 20 -> org&client.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2025-09-29T20:23:32.666Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553550, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2025-09-29 20:23:32.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551246
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 20 -> org&client.Mandant
-- Column: M_InventoryLine.AD_Client_ID
-- 2025-09-29T20:23:44.865Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553550, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2025-09-29 20:23:44.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551245
;

-- UI Column: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10
-- UI Element Group: packing
-- 2025-09-29T20:23:55.677Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540911,553551,TO_TIMESTAMP('2025-09-29 20:23:55.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','packing',20,TO_TIMESTAMP('2025-09-29 20:23:55.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> packing.Packvorschrift
-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- 2025-09-29T20:24:10.263Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553551, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2025-09-29 20:24:10.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551319
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> packing.Packvorschrift (LU)
-- Column: M_InventoryLine.M_LU_HU_PI_ID
-- 2025-09-29T20:24:20.377Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754213,0,256,553551,637306,'F',TO_TIMESTAMP('2025-09-29 20:24:20.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Packvorschrift (LU)',20,0,0,TO_TIMESTAMP('2025-09-29 20:24:20.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> packing.Packvorschrift
-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- 2025-09-29T20:25:42.461Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-09-29 20:25:42.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551319
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> packing.Packvorschrift (LU)
-- Column: M_InventoryLine.M_LU_HU_PI_ID
-- 2025-09-29T20:25:42.470Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-09-29 20:25:42.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637306
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Handling Unit
-- Column: M_InventoryLine.M_HU_ID
-- 2025-09-29T20:25:42.479Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-09-29 20:25:42.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551318
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.Description
-- 2025-09-29T20:25:42.487Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-09-29 20:25:42.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551254
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Inventory Type
-- Column: M_InventoryLine.InventoryType
-- 2025-09-29T20:25:42.494Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-09-29 20:25:42.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551255
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 20 -> org&client.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2025-09-29T20:25:42.502Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-09-29 20:25:42.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551246
;

