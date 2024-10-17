-- Tab: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos)
-- UI Section: main
-- 2024-10-17T14:21:11.435Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547622,546209,TO_TIMESTAMP('2024-10-17 17:21:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-17 17:21:11','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-10-17T14:21:11.437Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546209 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main
-- UI Column: 10
-- 2024-10-17T14:21:11.568Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547595,546209,TO_TIMESTAMP('2024-10-17 17:21:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-17 17:21:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main
-- UI Column: 20
-- 2024-10-17T14:21:11.678Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547596,546209,TO_TIMESTAMP('2024-10-17 17:21:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-10-17 17:21:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 10
-- UI Element Group: default
-- 2024-10-17T14:21:11.791Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547595,552045,TO_TIMESTAMP('2024-10-17 17:21:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-10-17 17:21:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Product Category -> POS Product Category.Name
-- Column: C_POS_ProductCategory.Name
-- UI Element: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 10 -> default.Name
-- Column: C_POS_ProductCategory.Name
-- 2024-10-17T14:21:39.732Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731916,0,547622,552045,626198,'F',TO_TIMESTAMP('2024-10-17 17:21:39','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2024-10-17 17:21:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Product Category -> POS Product Category.Beschreibung
-- Column: C_POS_ProductCategory.Description
-- UI Element: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 10 -> default.Beschreibung
-- Column: C_POS_ProductCategory.Description
-- 2024-10-17T14:21:47.373Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731917,0,547622,552045,626199,'F',TO_TIMESTAMP('2024-10-17 17:21:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2024-10-17 17:21:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Product Category -> POS Product Category.Bild
-- Column: C_POS_ProductCategory.AD_Image_ID
-- UI Element: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 10 -> default.Bild
-- Column: C_POS_ProductCategory.AD_Image_ID
-- 2024-10-17T14:21:52.956Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731918,0,547622,552045,626200,'F',TO_TIMESTAMP('2024-10-17 17:21:52','YYYY-MM-DD HH24:MI:SS'),100,'Image or Icon','Images and Icon can be used to display supported graphic formats (gif, jpg, png).
You can either load the image (in the database) or point to a graphic via a URI (i.e. it can point to a resource, http address)','Y','N','Y','N','N','Bild',30,0,0,TO_TIMESTAMP('2024-10-17 17:21:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 20
-- UI Element Group: flags
-- 2024-10-17T14:22:03.593Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547596,552046,TO_TIMESTAMP('2024-10-17 17:22:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-10-17 17:22:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Product Category -> POS Product Category.Aktiv
-- Column: C_POS_ProductCategory.IsActive
-- UI Element: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 20 -> flags.Aktiv
-- Column: C_POS_ProductCategory.IsActive
-- 2024-10-17T14:22:13.295Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731914,0,547622,552046,626201,'F',TO_TIMESTAMP('2024-10-17 17:22:13','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-10-17 17:22:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 20
-- UI Element Group: org&client
-- 2024-10-17T14:22:22.828Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547596,552047,TO_TIMESTAMP('2024-10-17 17:22:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','org&client',20,TO_TIMESTAMP('2024-10-17 17:22:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Product Category -> POS Product Category.Organisation
-- Column: C_POS_ProductCategory.AD_Org_ID
-- UI Element: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 20 -> org&client.Organisation
-- Column: C_POS_ProductCategory.AD_Org_ID
-- 2024-10-17T14:22:32.905Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731913,0,547622,552047,626202,'F',TO_TIMESTAMP('2024-10-17 17:22:31','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2024-10-17 17:22:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Product Category -> POS Product Category.Mandant
-- Column: C_POS_ProductCategory.AD_Client_ID
-- UI Element: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 20 -> org&client.Mandant
-- Column: C_POS_ProductCategory.AD_Client_ID
-- 2024-10-17T14:22:43.254Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731912,0,547622,552047,626203,'F',TO_TIMESTAMP('2024-10-17 17:22:43','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2024-10-17 17:22:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Product Category -> POS Product Category.Name
-- Column: C_POS_ProductCategory.Name
-- UI Element: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 10 -> default.Name
-- Column: C_POS_ProductCategory.Name
-- 2024-10-17T14:23:02.730Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-10-17 17:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626198
;

-- UI Element: POS Product Category -> POS Product Category.Beschreibung
-- Column: C_POS_ProductCategory.Description
-- UI Element: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 10 -> default.Beschreibung
-- Column: C_POS_ProductCategory.Description
-- 2024-10-17T14:23:02.740Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-10-17 17:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626199
;

-- UI Element: POS Product Category -> POS Product Category.Aktiv
-- Column: C_POS_ProductCategory.IsActive
-- UI Element: POS Product Category(541828,de.metas.pos) -> POS Product Category(547622,de.metas.pos) -> main -> 20 -> flags.Aktiv
-- Column: C_POS_ProductCategory.IsActive
-- 2024-10-17T14:23:02.747Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-10-17 17:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626201
;

