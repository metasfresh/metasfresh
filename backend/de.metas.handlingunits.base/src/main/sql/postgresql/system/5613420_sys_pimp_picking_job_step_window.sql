-- 2021-11-15T08:18:26.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544863,543864,TO_TIMESTAMP('2021-11-15 10:18:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-11-15 10:18:26','YYYY-MM-DD HH24:MI:SS'),100,'Source Document Info')
;

-- 2021-11-15T08:18:26.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543864 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-11-15T08:18:35.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Value='default',Updated=TO_TIMESTAMP('2021-11-15 10:18:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=543864
;

-- 2021-11-15T08:18:41.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544772,543864,TO_TIMESTAMP('2021-11-15 10:18:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-11-15 10:18:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:18:44.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544773,543864,TO_TIMESTAMP('2021-11-15 10:18:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-11-15 10:18:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:19:03.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544773,547213,TO_TIMESTAMP('2021-11-15 10:19:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','Source Document Info',10,TO_TIMESTAMP('2021-11-15 10:19:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:19:27.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669050,0,544863,595237,547213,'F',TO_TIMESTAMP('2021-11-15 10:19:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Picking Job',10,0,0,TO_TIMESTAMP('2021-11-15 10:19:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:19:34.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669055,0,544863,595238,547213,'F',TO_TIMESTAMP('2021-11-15 10:19:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Picking Job Line',20,0,0,TO_TIMESTAMP('2021-11-15 10:19:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:19:41.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669067,0,544863,595239,547213,'F',TO_TIMESTAMP('2021-11-15 10:19:41','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Auftrag',30,0,0,TO_TIMESTAMP('2021-11-15 10:19:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:19:48.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669068,0,544863,595240,547213,'F',TO_TIMESTAMP('2021-11-15 10:19:48','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','Auftragsposition',40,0,0,TO_TIMESTAMP('2021-11-15 10:19:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:19:55.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669057,0,544863,595241,547213,'F',TO_TIMESTAMP('2021-11-15 10:19:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferdisposition',50,0,0,TO_TIMESTAMP('2021-11-15 10:19:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:20:12.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544773,547214,TO_TIMESTAMP('2021-11-15 10:20:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','Product & Qty To Pick',20,TO_TIMESTAMP('2021-11-15 10:20:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:20:32.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669056,0,544863,595242,547214,'F',TO_TIMESTAMP('2021-11-15 10:20:32','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',10,0,0,TO_TIMESTAMP('2021-11-15 10:20:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:20:40Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669058,0,544863,595243,547214,'F',TO_TIMESTAMP('2021-11-15 10:20:38','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',20,0,0,TO_TIMESTAMP('2021-11-15 10:20:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:20:47.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669059,0,544863,595244,547214,'F',TO_TIMESTAMP('2021-11-15 10:20:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Qty To Pick',30,0,0,TO_TIMESTAMP('2021-11-15 10:20:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:21:21.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544773,547215,TO_TIMESTAMP('2021-11-15 10:21:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','Pick From',30,TO_TIMESTAMP('2021-11-15 10:21:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:22:01.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669065,0,544863,595245,547215,'F',TO_TIMESTAMP('2021-11-15 10:22:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Pick From Locator',10,0,0,TO_TIMESTAMP('2021-11-15 10:22:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:22:09.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669063,0,544863,595246,547215,'F',TO_TIMESTAMP('2021-11-15 10:22:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Pick From HU',20,0,0,TO_TIMESTAMP('2021-11-15 10:22:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:23:16.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669070,0,544863,595247,547214,'F',TO_TIMESTAMP('2021-11-15 10:23:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift',40,0,0,TO_TIMESTAMP('2021-11-15 10:23:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:23:28.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2021-11-15 10:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595247
;

-- 2021-11-15T08:37:33.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=595247
;

-- 2021-11-15T08:37:57.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_Element_ID,AD_UI_ElementField_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,669070,0,595242,541319,TO_TIMESTAMP('2021-11-15 10:37:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2021-11-15 10:37:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:39:07.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544773,547216,TO_TIMESTAMP('2021-11-15 10:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','Qty Rejected',40,TO_TIMESTAMP('2021-11-15 10:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:39:26.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669060,0,544863,595248,547216,'F',TO_TIMESTAMP('2021-11-15 10:39:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Qty Rejected To Pick',10,0,0,TO_TIMESTAMP('2021-11-15 10:39:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:39:34.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669061,0,544863,595249,547216,'F',TO_TIMESTAMP('2021-11-15 10:39:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Reject Reason',20,0,0,TO_TIMESTAMP('2021-11-15 10:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:40:30.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544773,547217,TO_TIMESTAMP('2021-11-15 10:40:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Processing Status',50,TO_TIMESTAMP('2021-11-15 10:40:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:40:44.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669062,0,544863,595250,547217,'F',TO_TIMESTAMP('2021-11-15 10:40:44','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2021-11-15 10:40:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T08:41:54.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=544772, SeqNo=10,Updated=TO_TIMESTAMP('2021-11-15 10:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=547213
;

-- 2021-11-15T08:42:03.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=544772, SeqNo=20,Updated=TO_TIMESTAMP('2021-11-15 10:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=547214
;

-- 2021-11-15T08:42:12.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=544772, SeqNo=30,Updated=TO_TIMESTAMP('2021-11-15 10:42:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=547215
;

-- 2021-11-15T08:42:19.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2021-11-15 10:42:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=547217
;

-- 2021-11-15T08:42:22.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2021-11-15 10:42:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=547216
;

-- 2021-11-15T08:42:55.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@RejectReason@!''''',Updated=TO_TIMESTAMP('2021-11-15 10:42:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669061
;

-- 2021-11-15T08:42:59.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@RejectReason@!''''',Updated=TO_TIMESTAMP('2021-11-15 10:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669060
;

-- 2021-11-15T08:44:40.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595237
;

-- 2021-11-15T08:44:40.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595241
;

-- 2021-11-15T08:44:40.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595242
;

-- 2021-11-15T08:44:40.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595243
;

-- 2021-11-15T08:44:40.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595244
;

-- 2021-11-15T08:44:40.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595246
;

-- 2021-11-15T08:44:40.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595245
;

-- 2021-11-15T08:44:40.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595248
;

-- 2021-11-15T08:44:40.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595249
;

-- 2021-11-15T08:44:40.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-11-15 10:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595250
;

