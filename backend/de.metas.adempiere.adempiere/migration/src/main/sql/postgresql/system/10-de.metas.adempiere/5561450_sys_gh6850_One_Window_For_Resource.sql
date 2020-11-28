-- 2020-06-15T14:50:32.610Z
-- description fields
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,414,542062,TO_TIMESTAMP('2020-06-15 17:50:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-06-15 17:50:31','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-06-15T14:50:32.656Z
-- description fields
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=542062 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-06-15T14:50:33.072Z
-- description fields
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542589,542062,TO_TIMESTAMP('2020-06-15 17:50:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-06-15 17:50:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:33.439Z
-- description fields
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542590,542062,TO_TIMESTAMP('2020-06-15 17:50:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2020-06-15 17:50:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:33.852Z
-- description fields
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542589,543924,TO_TIMESTAMP('2020-06-15 17:50:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-06-15 17:50:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:34.484Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5481,0,414,543924,570074,TO_TIMESTAMP('2020-06-15 17:50:34','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2020-06-15 17:50:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:34.934Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5480,0,414,543924,570075,TO_TIMESTAMP('2020-06-15 17:50:34','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2020-06-15 17:50:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:35.355Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5478,0,414,543924,570076,TO_TIMESTAMP('2020-06-15 17:50:35','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',30,30,0,TO_TIMESTAMP('2020-06-15 17:50:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:35.790Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5477,0,414,543924,570077,TO_TIMESTAMP('2020-06-15 17:50:35','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2020-06-15 17:50:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:36.224Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5476,0,414,543924,570078,TO_TIMESTAMP('2020-06-15 17:50:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2020-06-15 17:50:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:36.687Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5479,0,414,543924,570079,TO_TIMESTAMP('2020-06-15 17:50:36','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',60,60,0,TO_TIMESTAMP('2020-06-15 17:50:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:37.112Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5475,0,414,543924,570080,TO_TIMESTAMP('2020-06-15 17:50:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ressourcenart',70,70,0,TO_TIMESTAMP('2020-06-15 17:50:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:37.555Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6526,0,414,543924,570081,TO_TIMESTAMP('2020-06-15 17:50:37','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','Y','N','Lieferkontakt',80,80,0,TO_TIMESTAMP('2020-06-15 17:50:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:38.020Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5473,0,414,543924,570082,TO_TIMESTAMP('2020-06-15 17:50:37','YYYY-MM-DD HH24:MI:SS'),100,'Ressource ist verfügbar','Ressource ist verfügbar für Zuordnungen','Y','N','Y','Y','N','Verfügbar',90,90,0,TO_TIMESTAMP('2020-06-15 17:50:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:38.453Z
-- description fields
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,416,542063,TO_TIMESTAMP('2020-06-15 17:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-06-15 17:50:38','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-06-15T14:50:38.489Z
-- description fields
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=542063 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-06-15T14:50:38.860Z
-- description fields
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542591,542063,TO_TIMESTAMP('2020-06-15 17:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-06-15 17:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:39.222Z
-- description fields
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542591,543925,TO_TIMESTAMP('2020-06-15 17:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-06-15 17:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:39.712Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5497,0,416,543925,570083,TO_TIMESTAMP('2020-06-15 17:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2020-06-15 17:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:40.138Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5496,0,416,543925,570084,TO_TIMESTAMP('2020-06-15 17:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2020-06-15 17:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:40.563Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5494,0,416,543925,570085,TO_TIMESTAMP('2020-06-15 17:50:40','YYYY-MM-DD HH24:MI:SS'),100,'Ressource','Y','N','N','Y','N','Ressource',0,30,0,TO_TIMESTAMP('2020-06-15 17:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:41.003Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5495,0,416,543925,570086,TO_TIMESTAMP('2020-06-15 17:50:40','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2020-06-15 17:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:41.449Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5499,0,416,543925,570087,TO_TIMESTAMP('2020-06-15 17:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes','Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','N','Y','N','Datum von',0,50,0,TO_TIMESTAMP('2020-06-15 17:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:41.908Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5493,0,416,543925,570088,TO_TIMESTAMP('2020-06-15 17:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Enddatum eines Abschnittes','Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','N','Y','N','Datum bis',0,60,0,TO_TIMESTAMP('2020-06-15 17:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:42.340Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5492,0,416,543925,570089,TO_TIMESTAMP('2020-06-15 17:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,70,0,TO_TIMESTAMP('2020-06-15 17:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:42.730Z
-- description fields
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,417,542064,TO_TIMESTAMP('2020-06-15 17:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-06-15 17:50:42','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-06-15T14:50:42.766Z
-- description fields
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=542064 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-06-15T14:50:43.146Z
-- description fields
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542592,542064,TO_TIMESTAMP('2020-06-15 17:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-06-15 17:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:43.543Z
-- description fields
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542592,543926,TO_TIMESTAMP('2020-06-15 17:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-06-15 17:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:44.065Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5501,0,417,543926,570090,TO_TIMESTAMP('2020-06-15 17:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2020-06-15 17:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:44.526Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5502,0,417,543926,570091,TO_TIMESTAMP('2020-06-15 17:50:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2020-06-15 17:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:44.971Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5514,0,417,543926,570092,TO_TIMESTAMP('2020-06-15 17:50:44','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Suchschlüssel',0,30,0,TO_TIMESTAMP('2020-06-15 17:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:45.404Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5504,0,417,543926,570093,TO_TIMESTAMP('2020-06-15 17:50:45','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','N','Name',0,40,0,TO_TIMESTAMP('2020-06-15 17:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:45.868Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5505,0,417,543926,570094,TO_TIMESTAMP('2020-06-15 17:50:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,50,0,TO_TIMESTAMP('2020-06-15 17:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:46.314Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5526,0,417,543926,570095,TO_TIMESTAMP('2020-06-15 17:50:45','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','N','Y','N','Kommentar/Hilfe',0,60,0,TO_TIMESTAMP('2020-06-15 17:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:46.779Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5525,0,417,543926,570096,TO_TIMESTAMP('2020-06-15 17:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Information für den Kunden','"Notiz" wird für zusätzliche Informationen zu diesem Produkt verwendet.','Y','N','N','Y','N','Notiz / Zeilentext',0,70,0,TO_TIMESTAMP('2020-06-15 17:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:47.220Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5517,0,417,543926,570097,TO_TIMESTAMP('2020-06-15 17:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)','Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.','Y','N','N','Y','N','UPC',0,80,0,TO_TIMESTAMP('2020-06-15 17:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:47.695Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5518,0,417,543926,570098,TO_TIMESTAMP('2020-06-15 17:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Stock Keeping Unit','"SKU" bezeichnet eine Einheit, die zur Identifizierung eines bestimmten Produktes in der Logistikkette dient, meist in Form einer Buchstaben-Nummern-Kombination. Das Feld kann z.B. für einen zusätzlichen Barcode oder Ihr eigenes System verwendet werden.','Y','N','N','Y','N','SKU',0,90,0,TO_TIMESTAMP('2020-06-15 17:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:48.131Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5503,0,417,543926,570099,TO_TIMESTAMP('2020-06-15 17:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,100,0,TO_TIMESTAMP('2020-06-15 17:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:48.558Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5506,0,417,543926,570100,TO_TIMESTAMP('2020-06-15 17:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.','Y','N','N','Y','N','Zusammenfassungseintrag',0,110,0,TO_TIMESTAMP('2020-06-15 17:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:49.022Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5515,0,417,543926,570101,TO_TIMESTAMP('2020-06-15 17:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','Y','N','Produkt Kategorie',0,120,0,TO_TIMESTAMP('2020-06-15 17:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:49.472Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5527,0,417,543926,570102,TO_TIMESTAMP('2020-06-15 17:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Klassifizierung für die Gruppierung','Die "Klassifizierung" kann für die optionale Gruppierung von Produkten verwendet werden.','Y','N','N','Y','N','Klassifizierung',0,130,0,TO_TIMESTAMP('2020-06-15 17:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:49.940Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5529,0,417,543926,570103,TO_TIMESTAMP('2020-06-15 17:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Methode für die Realisierung des Umsatzes','"Umsatzrealisierung" gibt an, auf welche Weise der Umsatz zu diesem Produkt realisiert wird.','Y','N','N','Y','N','Umsatzrealisierung',0,140,0,TO_TIMESTAMP('2020-06-15 17:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:50.463Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5508,0,417,543926,570104,TO_TIMESTAMP('2020-06-15 17:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','Maßeinheit',0,150,0,TO_TIMESTAMP('2020-06-15 17:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:50.927Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5528,0,417,543926,570105,TO_TIMESTAMP('2020-06-15 17:50:50','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','N','Kundenbetreuer',0,160,0,TO_TIMESTAMP('2020-06-15 17:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:51.365Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5890,0,417,543926,570106,TO_TIMESTAMP('2020-06-15 17:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Art von Produkt','Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.','Y','N','N','Y','N','Produktart',0,170,0,TO_TIMESTAMP('2020-06-15 17:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:51.824Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5510,0,417,543926,570107,TO_TIMESTAMP('2020-06-15 17:50:51','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','N','Wird Eingekauft',0,180,0,TO_TIMESTAMP('2020-06-15 17:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:52.252Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5511,0,417,543926,570108,TO_TIMESTAMP('2020-06-15 17:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Die Organisation verkauft dieses Produkt','Das Selektionsfeld "Verkauft" zeigt an, ob dieses Produkt von dieser Organisation verkauft wird.','Y','N','N','Y','N','Verkauft',0,190,0,TO_TIMESTAMP('2020-06-15 17:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:52.715Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5523,0,417,543926,570109,TO_TIMESTAMP('2020-06-15 17:50:52','YYYY-MM-DD HH24:MI:SS'),100,'Dieses Produkt ist nicht mehr verfügbar','Das Selektionsfeld "Eingestellt" zeigt ein Produkt an, das nicht länger verfügbar ist.','Y','N','N','Y','N','Eingestellt',0,200,0,TO_TIMESTAMP('2020-06-15 17:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:53.137Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5524,0,417,543926,570110,TO_TIMESTAMP('2020-06-15 17:50:52','YYYY-MM-DD HH24:MI:SS'),100,'Eingestellt durch','"Eingestellt durch" zeigt an, welche Person dieses Produkt eingestellt hat.','Y','N','N','Y','N','Eingestellt durch',0,210,0,TO_TIMESTAMP('2020-06-15 17:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:53.553Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5914,0,417,543926,570111,TO_TIMESTAMP('2020-06-15 17:50:53','YYYY-MM-DD HH24:MI:SS'),100,'URL of  image','URL of image; The image is not stored in the database, but retrieved at runtime. The image can be a gif, jpeg or png.','Y','N','N','Y','N','Bild-URL',0,220,0,TO_TIMESTAMP('2020-06-15 17:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:54.004Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5915,0,417,543926,570112,TO_TIMESTAMP('2020-06-15 17:50:53','YYYY-MM-DD HH24:MI:SS'),100,'URL für die Beschreibung','Y','N','N','Y','N','Beschreibungs-URL',0,230,0,TO_TIMESTAMP('2020-06-15 17:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:54.394Z
-- description fields
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,415,542065,TO_TIMESTAMP('2020-06-15 17:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-06-15 17:50:54','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-06-15T14:50:54.432Z
-- description fields
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=542065 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-06-15T14:50:54.824Z
-- description fields
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542593,542065,TO_TIMESTAMP('2020-06-15 17:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-06-15 17:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:55.192Z
-- description fields
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542593,543927,TO_TIMESTAMP('2020-06-15 17:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-06-15 17:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:55.693Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5490,0,415,543927,570113,TO_TIMESTAMP('2020-06-15 17:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2020-06-15 17:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:56.133Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5489,0,415,543927,570114,TO_TIMESTAMP('2020-06-15 17:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2020-06-15 17:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:56.581Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5487,0,415,543927,570115,TO_TIMESTAMP('2020-06-15 17:50:56','YYYY-MM-DD HH24:MI:SS'),100,'Ressource','Y','N','N','Y','N','Ressource',0,30,0,TO_TIMESTAMP('2020-06-15 17:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:57.044Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5486,0,415,543927,570116,TO_TIMESTAMP('2020-06-15 17:50:56','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab','Beginn Zuordnung','Y','N','N','Y','N','Zuordnung von',0,40,0,TO_TIMESTAMP('2020-06-15 17:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:57.472Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5485,0,415,543927,570117,TO_TIMESTAMP('2020-06-15 17:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis','Zuordnung endet','Y','N','N','Y','N','Zuordnung bis',0,50,0,TO_TIMESTAMP('2020-06-15 17:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:57.910Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5484,0,415,543927,570118,TO_TIMESTAMP('2020-06-15 17:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','Menge',0,60,0,TO_TIMESTAMP('2020-06-15 17:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:58.356Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5483,0,415,543927,570119,TO_TIMESTAMP('2020-06-15 17:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Zuordnung ist bestätigt','Zuordnung der Ressource ist bestätigt','Y','N','N','Y','N','bestätigt',0,70,0,TO_TIMESTAMP('2020-06-15 17:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:58.807Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5635,0,415,543927,570120,TO_TIMESTAMP('2020-06-15 17:50:58','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','N','Name',0,80,0,TO_TIMESTAMP('2020-06-15 17:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-15T14:50:59.237Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5636,0,415,543927,570121,TO_TIMESTAMP('2020-06-15 17:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,90,0,TO_TIMESTAMP('2020-06-15 17:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

















UPDATE ad_ref_table set ad_window_ID = 236 WHERE ad_window_id=53004;



delete from ad_wf_node where ad_window_id = 53004;








-- 2020-06-15T16:25:17.083Z
-- description fields
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53018
;

-- 2020-06-15T16:25:17.303Z
-- description fields
DELETE FROM AD_Menu WHERE AD_Menu_ID=53018
;

-- 2020-06-15T16:25:17.495Z
-- description fields
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53018 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2020-06-15T16:25:41.120Z
-- description fields
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=319
;

-- 2020-06-15T16:25:41.338Z
-- description fields
DELETE FROM AD_Menu WHERE AD_Menu_ID=319
;

-- 2020-06-15T16:25:41.412Z
-- description fields
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=319 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;








-- 2020-06-15T16:55:01.878Z
-- description fields
UPDATE AD_Menu SET InternalName='R_Ressource',Updated=TO_TIMESTAMP('2020-06-15 19:55:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540818
;

-- 2020-06-15T16:55:35.052Z
-- description fields
UPDATE AD_Menu SET AD_Element_ID=574125, AD_Window_ID=236, Description='Ressourcen verwalten', EntityType='D', InternalName='R_Resource', Name='Ressource', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2020-06-15 19:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540818
;

-- 2020-06-15T16:55:35.167Z
-- description fields
/* DDL */  select update_menu_translation_from_ad_element(574125) 
;








-- 2020-06-16T07:27:46.292Z
-- description fields
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542590,543928,TO_TIMESTAMP('2020-06-16 10:27:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2020-06-16 10:27:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T07:27:53.614Z
-- description fields
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542590,543929,TO_TIMESTAMP('2020-06-16 10:27:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2020-06-16 10:27:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T07:28:36.698Z
-- description fields
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543929, SeqNo=10,Updated=TO_TIMESTAMP('2020-06-16 10:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570074
;

-- 2020-06-16T07:28:49.330Z
-- description fields
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543929, SeqNo=20,Updated=TO_TIMESTAMP('2020-06-16 10:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570075
;

-- 2020-06-16T07:29:07.644Z
-- description fields
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543928, SeqNo=10,Updated=TO_TIMESTAMP('2020-06-16 10:29:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570079
;

-- 2020-06-16T07:29:46.930Z
-- description fields
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542589,543930,TO_TIMESTAMP('2020-06-16 10:29:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','details',20,TO_TIMESTAMP('2020-06-16 10:29:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T07:31:19.854Z
-- description fields
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543930, SeqNo=10,Updated=TO_TIMESTAMP('2020-06-16 10:31:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570078
;

-- 2020-06-16T07:31:37.316Z
-- description fields
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543930, SeqNo=20,Updated=TO_TIMESTAMP('2020-06-16 10:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570080
;

-- 2020-06-16T07:56:42.722Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56150,614858,0,414,0,TO_TIMESTAMP('2020-06-16 10:56:42','YYYY-MM-DD HH24:MI:SS'),100,'The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.',0,'D','The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.',0,'Y','Y','Y','N','N','N','N','N','Planning Horizon',130,130,0,1,1,TO_TIMESTAMP('2020-06-16 10:56:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T07:56:42.763Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T07:56:42.806Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53656) 
;

-- 2020-06-16T07:56:42.938Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614858
;

-- 2020-06-16T07:56:42.978Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614858)
;

-- 2020-06-16T07:57:16.464Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,614858,0,414,543930,570122,'F',TO_TIMESTAMP('2020-06-16 10:57:16','YYYY-MM-DD HH24:MI:SS'),100,'The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.','The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.','Y','N','N','Y','N','N','N',0,'Planning Horizon',30,0,0,TO_TIMESTAMP('2020-06-16 10:57:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T07:59:45.673Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 10:59:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570074
;

-- 2020-06-16T07:59:45.825Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-06-16 10:59:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570076
;

-- 2020-06-16T07:59:45.971Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-06-16 10:59:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570077
;

-- 2020-06-16T07:59:46.119Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-06-16 10:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570080
;

-- 2020-06-16T07:59:46.262Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-06-16 10:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570081
;

-- 2020-06-16T07:59:46.413Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-06-16 10:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570079
;

-- 2020-06-16T07:59:46.559Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-06-16 10:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570082
;

-- 2020-06-16T07:59:46.704Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-06-16 10:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570078
;

-- 2020-06-16T07:59:46.851Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2020-06-16 10:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570122
;

-- 2020-06-16T07:59:46.995Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2020-06-16 10:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570075
;

-- 2020-06-16T08:02:09.533Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2020-06-16 11:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570088
;

-- 2020-06-16T08:02:13.161Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2020-06-16 11:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570087
;

-- 2020-06-16T08:02:56.199Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2020-06-16 11:02:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570088
;

-- 2020-06-16T08:03:03.972Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2020-06-16 11:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570086
;

-- 2020-06-16T08:03:08.481Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2020-06-16 11:03:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570089
;

-- 2020-06-16T08:03:12.628Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2020-06-16 11:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570084
;

-- 2020-06-16T08:03:19.387Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2020-06-16 11:03:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570083
;

-- 2020-06-16T08:03:20.342Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:03:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570089
;

-- 2020-06-16T08:03:21.136Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:03:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570088
;

-- 2020-06-16T08:03:21.952Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:03:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570087
;

-- 2020-06-16T08:03:22.798Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570086
;

-- 2020-06-16T08:03:23.660Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:03:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570084
;

-- 2020-06-16T08:03:30.094Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:03:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570083
;

-- 2020-06-16T08:05:36.874Z
-- description fields
UPDATE AD_Field SET SeqNo=250,Updated=TO_TIMESTAMP('2020-06-16 11:05:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5500
;

-- 2020-06-16T08:06:12.362Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5500,0,417,543926,570123,'F',TO_TIMESTAMP('2020-06-16 11:06:11','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2020-06-16 11:06:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:06:42.721Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2020-06-16 11:06:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570092
;

-- 2020-06-16T08:06:47.360Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2020-06-16 11:06:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570093
;

-- 2020-06-16T08:07:06.818Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2020-06-16 11:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570094
;

-- 2020-06-16T08:07:11.485Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2020-06-16 11:07:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570097
;

-- 2020-06-16T08:07:26.556Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2020-06-16 11:07:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570098
;

-- 2020-06-16T08:07:38.367Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2020-06-16 11:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570101
;

-- 2020-06-16T08:07:43.514Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2020-06-16 11:07:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570104
;

-- 2020-06-16T08:08:10.246Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2020-06-16 11:08:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570099
;

-- 2020-06-16T08:08:17.150Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2020-06-16 11:08:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570100
;

-- 2020-06-16T08:08:21.083Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2020-06-16 11:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570102
;

-- 2020-06-16T08:08:24.962Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2020-06-16 11:08:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570103
;

-- 2020-06-16T08:08:30.191Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2020-06-16 11:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570105
;

-- 2020-06-16T08:08:33.966Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2020-06-16 11:08:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570107
;

-- 2020-06-16T08:08:37.854Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=160,Updated=TO_TIMESTAMP('2020-06-16 11:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570108
;

-- 2020-06-16T08:08:45.975Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2020-06-16 11:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570109
;

-- 2020-06-16T08:08:49.782Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=180,Updated=TO_TIMESTAMP('2020-06-16 11:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570110
;

-- 2020-06-16T08:08:53.741Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=190,Updated=TO_TIMESTAMP('2020-06-16 11:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570111
;

-- 2020-06-16T08:08:58.225Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=200,Updated=TO_TIMESTAMP('2020-06-16 11:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570112
;

-- 2020-06-16T08:09:04.580Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=210,Updated=TO_TIMESTAMP('2020-06-16 11:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570096
;

-- 2020-06-16T08:09:08.073Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=220,Updated=TO_TIMESTAMP('2020-06-16 11:09:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570095
;

-- 2020-06-16T08:09:11.921Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=230,Updated=TO_TIMESTAMP('2020-06-16 11:09:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570091
;

-- 2020-06-16T08:09:17.770Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2020-06-16 11:09:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570090
;

-- 2020-06-16T08:09:18.585Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570112
;

-- 2020-06-16T08:09:19.427Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570111
;

-- 2020-06-16T08:09:20.227Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570110
;

-- 2020-06-16T08:09:21.031Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570109
;

-- 2020-06-16T08:09:21.825Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570108
;

-- 2020-06-16T08:09:22.619Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570107
;

-- 2020-06-16T08:09:23.339Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570105
;

-- 2020-06-16T08:09:24.172Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570103
;

-- 2020-06-16T08:09:24.975Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570102
;

-- 2020-06-16T08:09:25.770Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570100
;

-- 2020-06-16T08:09:26.551Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570099
;

-- 2020-06-16T08:09:27.343Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570096
;

-- 2020-06-16T08:09:28.166Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570095
;

-- 2020-06-16T08:09:29.005Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570091
;

-- 2020-06-16T08:09:29.804Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570092
;

-- 2020-06-16T08:09:30.593Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570093
;

-- 2020-06-16T08:09:31.452Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570097
;

-- 2020-06-16T08:09:32.220Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570098
;

-- 2020-06-16T08:09:32.957Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570094
;

-- 2020-06-16T08:09:33.890Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570101
;

-- 2020-06-16T08:09:34.710Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570104
;

-- 2020-06-16T08:09:37.502Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570106
;

-- 2020-06-16T08:10:43.598Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570094
;

-- 2020-06-16T08:10:43.742Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570112
;

-- 2020-06-16T08:10:43.888Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570111
;

-- 2020-06-16T08:10:44.039Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570109
;

-- 2020-06-16T08:10:44.185Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570110
;

-- 2020-06-16T08:10:44.328Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570102
;

-- 2020-06-16T08:10:44.473Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570095
;

-- 2020-06-16T08:10:44.616Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570105
;

-- 2020-06-16T08:10:44.757Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570090
;

-- 2020-06-16T08:10:44.899Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570096
;

-- 2020-06-16T08:10:45.042Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570106
;

-- 2020-06-16T08:10:45.186Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570098
;

-- 2020-06-16T08:10:45.334Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570103
;

-- 2020-06-16T08:10:45.478Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570108
;

-- 2020-06-16T08:10:45.626Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570107
;

-- 2020-06-16T08:10:45.778Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570100
;

-- 2020-06-16T08:10:45.916Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-06-16 11:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570123
;

-- 2020-06-16T08:10:46.054Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-06-16 11:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570092
;

-- 2020-06-16T08:10:46.198Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-06-16 11:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570093
;

-- 2020-06-16T08:10:46.339Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-06-16 11:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570101
;

-- 2020-06-16T08:10:46.473Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-06-16 11:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570104
;

-- 2020-06-16T08:10:46.613Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-06-16 11:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570097
;

-- 2020-06-16T08:10:46.761Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-06-16 11:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570099
;

-- 2020-06-16T08:10:46.901Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2020-06-16 11:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570091
;

-- 2020-06-16T08:11:26.844Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2020-06-16 11:11:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570120
;

-- 2020-06-16T08:11:34.382Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2020-06-16 11:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570118
;

-- 2020-06-16T08:11:37.093Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570120
;

-- 2020-06-16T08:11:38.100Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570114
;

-- 2020-06-16T08:11:39.816Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:11:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570113
;

-- 2020-06-16T08:11:52.389Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2020-06-16 11:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570116
;

-- 2020-06-16T08:11:56.314Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2020-06-16 11:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570117
;

-- 2020-06-16T08:11:59.582Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2020-06-16 11:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570119
;

-- 2020-06-16T08:12:04.033Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2020-06-16 11:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570121
;

-- 2020-06-16T08:12:11.244Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2020-06-16 11:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570114
;

-- 2020-06-16T08:12:15.127Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2020-06-16 11:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570113
;

-- 2020-06-16T08:12:39.979Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:12:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570121
;

-- 2020-06-16T08:12:40.125Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570113
;

-- 2020-06-16T08:12:40.271Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570115
;

-- 2020-06-16T08:12:40.412Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-06-16 11:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570120
;

-- 2020-06-16T08:12:40.556Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-06-16 11:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570118
;

-- 2020-06-16T08:12:40.699Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-06-16 11:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570119
;

-- 2020-06-16T08:12:40.848Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-06-16 11:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570114
;

-- 2020-06-16T08:12:47.020Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570116
;

-- 2020-06-16T08:12:47.838Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570117
;

-- 2020-06-16T08:12:48.694Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:12:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570119
;

-- 2020-06-16T08:13:01.827Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570121
;

-- 2020-06-16T08:15:01.439Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53274,614859,0,414,0,TO_TIMESTAMP('2020-06-16 11:15:00','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Manufacturing Resource',140,140,0,1,1,TO_TIMESTAMP('2020-06-16 11:15:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:15:01.478Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:15:01.518Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53232) 
;

-- 2020-06-16T08:15:01.559Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614859
;

-- 2020-06-16T08:15:01.595Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614859)
;

-- 2020-06-16T08:15:17.644Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551770,614860,0,414,0,TO_TIMESTAMP('2020-06-16 11:15:16','YYYY-MM-DD HH24:MI:SS'),100,'',0,'U',0,'Y','Y','Y','N','N','N','N','N','MRP ausschliessen',150,150,0,1,1,TO_TIMESTAMP('2020-06-16 11:15:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:15:17.682Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:15:17.721Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542697) 
;

-- 2020-06-16T08:15:17.774Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614860
;

-- 2020-06-16T08:15:17.809Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614860)
;

-- 2020-06-16T08:15:52.169Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5473,0,414,543928,570124,'F',TO_TIMESTAMP('2020-06-16 11:15:51','YYYY-MM-DD HH24:MI:SS'),100,'Ressource ist verfügbar','Ressource ist verfügbar für Zuordnungen','Y','N','N','Y','N','N','N',0,'Verfügbar',20,0,0,TO_TIMESTAMP('2020-06-16 11:15:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:16:13.695Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,614859,0,414,543928,570125,'F',TO_TIMESTAMP('2020-06-16 11:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Manufacturing Resource',30,0,0,TO_TIMESTAMP('2020-06-16 11:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:16:29.285Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,614860,0,414,543928,570126,'F',TO_TIMESTAMP('2020-06-16 11:16:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'MRP ausschliessen',40,0,0,TO_TIMESTAMP('2020-06-16 11:16:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:16:53.540Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:16:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570082
;

-- 2020-06-16T08:16:53.688Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-06-16 11:16:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570125
;

-- 2020-06-16T08:17:25.904Z
-- description fields
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=570082
;

-- 2020-06-16T08:17:58.549Z
-- description fields
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=570081
;

-- 2020-06-16T08:18:12.122Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5475,0,414,543924,570127,'F',TO_TIMESTAMP('2020-06-16 11:18:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Ressourcenart',50,0,0,TO_TIMESTAMP('2020-06-16 11:18:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:20:49.585Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6526,0,414,543924,570128,'F',TO_TIMESTAMP('2020-06-16 11:20:49','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','N','N',0,'Lieferkontakt',60,0,0,TO_TIMESTAMP('2020-06-16 11:20:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:22:36.937Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53276,614862,0,414,0,TO_TIMESTAMP('2020-06-16 11:22:36','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Manufacturing Resource Type',160,160,0,1,1,TO_TIMESTAMP('2020-06-16 11:22:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:22:36.975Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:22:37.014Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53233) 
;

-- 2020-06-16T08:22:37.061Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614862
;

-- 2020-06-16T08:22:37.098Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614862)
;

-- 2020-06-16T08:23:00.161Z
-- description fields
UPDATE AD_UI_Element SET AD_Field_ID=614862, Name='Manufacturing Resource Type',Updated=TO_TIMESTAMP('2020-06-16 11:23:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570080
;

-- 2020-06-16T08:29:12.093Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2020-06-16 11:29:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570106
;

-- 2020-06-16T08:29:24.617Z
-- description fields
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2020-06-16 11:29:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570092
;

-- 2020-06-16T08:30:45.579Z
-- description fields
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2020-06-16 11:30:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5537
;

-- 2020-06-16T08:31:53.781Z
-- description fields
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-16 11:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5537
;

-- 2020-06-16T08:32:26.185Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayed='N', SeqNo=20,Updated=TO_TIMESTAMP('2020-06-16 11:32:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570123
;

-- 2020-06-16T08:33:10.549Z
-- description fields
UPDATE AD_Tab SET IsSingleRow='N',Updated=TO_TIMESTAMP('2020-06-16 11:33:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=417
;

-- 2020-06-16T08:34:22.962Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-06-16 11:34:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570092
;

-- 2020-06-16T08:34:23.109Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-06-16 11:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570123
;

-- -- 2020-06-16T08:36:41.741Z
-- -- description fields
-- UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2020-06-16 11:36:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1413
-- ;

-- -- 2020-06-16T08:36:46.055Z
-- -- description fields
-- INSERT INTO t_alter_column values('m_product','IsSummary','CHAR(1)',null,'N')
-- ;

-- -- 2020-06-16T08:36:47.204Z
-- -- description fields
-- UPDATE M_Product SET IsSummary='N' WHERE IsSummary IS NULL
-- ;

-- 2020-06-16T08:40:26.928Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,14505,614863,0,417,TO_TIMESTAMP('2020-06-16 11:40:26','YYYY-MM-DD HH24:MI:SS'),100,'Ausnehmen von Automatischer Lieferung',1,'D','The product is excluded from generating Shipments.  This allows manual creation of shipments for high demand items. If selected, you need to create the shipment manually.
But, the item is always included, when the delivery rule of the Order is Force (e.g. for POS). 
This allows finer granularity of the Delivery Rule Manual.','Y','N','N','N','N','N','N','N','Ausnehmen von Automatischer Lieferung',TO_TIMESTAMP('2020-06-16 11:40:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:26.967Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:27.006Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2867) 
;

-- 2020-06-16T08:40:27.056Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614863
;

-- 2020-06-16T08:40:27.093Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614863)
;

-- 2020-06-16T08:40:27.624Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,52061,614864,0,417,TO_TIMESTAMP('2020-06-16 11:40:27','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Group1',TO_TIMESTAMP('2020-06-16 11:40:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:27.660Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:27.697Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(52018) 
;

-- 2020-06-16T08:40:27.742Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614864
;

-- 2020-06-16T08:40:27.777Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614864)
;

-- 2020-06-16T08:40:28.298Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,52062,614865,0,417,TO_TIMESTAMP('2020-06-16 11:40:27','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Group2',TO_TIMESTAMP('2020-06-16 11:40:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:28.334Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:28.370Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(52019) 
;

-- 2020-06-16T08:40:28.407Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614865
;

-- 2020-06-16T08:40:28.441Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614865)
;

-- 2020-06-16T08:40:28.951Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,52116,614866,0,417,TO_TIMESTAMP('2020-06-16 11:40:28','YYYY-MM-DD HH24:MI:SS'),100,'The Units Per Pack indicates the no of units of a product packed together.',14,'D','Y','N','N','N','N','N','N','N','UnitsPerPack',TO_TIMESTAMP('2020-06-16 11:40:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:28.987Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:29.024Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(52054) 
;

-- 2020-06-16T08:40:29.069Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614866
;

-- 2020-06-16T08:40:29.104Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614866)
;

-- 2020-06-16T08:40:29.641Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,53408,614867,0,417,TO_TIMESTAMP('2020-06-16 11:40:29','YYYY-MM-DD HH24:MI:SS'),100,8,'D','Y','N','N','N','N','N','N','N','Low Level',TO_TIMESTAMP('2020-06-16 11:40:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:29.675Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:29.711Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53274) 
;

-- 2020-06-16T08:40:29.751Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614867
;

-- 2020-06-16T08:40:29.787Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614867)
;

-- 2020-06-16T08:40:30.289Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540357,614868,0,417,TO_TIMESTAMP('2020-06-16 11:40:29','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','IsDiverse',TO_TIMESTAMP('2020-06-16 11:40:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:30.325Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:30.363Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540062) 
;

-- 2020-06-16T08:40:30.407Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614868
;

-- 2020-06-16T08:40:30.441Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614868)
;

-- 2020-06-16T08:40:30.957Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,544981,614869,0,417,TO_TIMESTAMP('2020-06-16 11:40:30','YYYY-MM-DD HH24:MI:SS'),100,'Produkt entspricht der Kategorie',1,'D','Erlaubt die Zuordnung eines Produktes als Prototyp zu einer Kategorie.
Beispiel: Produkt "Kleidung" in der Kategorie Kleidung. Weitere Produkte der Kategorie Kleidung könnten "Hemd", "Hose" usw. sein. Diese weiteren Produkte wären allerdings keine "Kategorie-Produkte", weil sie nur einen Teil der Kategorie repäsentieren','Y','N','N','N','N','N','N','N','Kategorie-Produkt',TO_TIMESTAMP('2020-06-16 11:40:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:30.995Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:31.032Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541289) 
;

-- 2020-06-16T08:40:31.073Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614869
;

-- 2020-06-16T08:40:31.107Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614869)
;

-- 2020-06-16T08:40:31.606Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549658,614870,0,417,TO_TIMESTAMP('2020-06-16 11:40:31','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Anbauplanung',TO_TIMESTAMP('2020-06-16 11:40:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:31.641Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:31.677Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542217) 
;

-- 2020-06-16T08:40:31.713Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614870
;

-- 2020-06-16T08:40:31.747Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614870)
;

-- 2020-06-16T08:40:32.273Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549659,614871,0,417,TO_TIMESTAMP('2020-06-16 11:40:31','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Waschprobe erforderlich',TO_TIMESTAMP('2020-06-16 11:40:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:32.310Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:32.348Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542218) 
;

-- 2020-06-16T08:40:32.387Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614871
;

-- 2020-06-16T08:40:32.422Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614871)
;

-- 2020-06-16T08:40:32.947Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551768,614872,0,417,TO_TIMESTAMP('2020-06-16 11:40:32','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','N','N','N','N','N','N','N','MRP ausschliessen',TO_TIMESTAMP('2020-06-16 11:40:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:32.983Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:33.021Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542697) 
;

-- 2020-06-16T08:40:33.064Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614872
;

-- 2020-06-16T08:40:33.098Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614872)
;

-- 2020-06-16T08:40:33.614Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552718,614873,0,417,TO_TIMESTAMP('2020-06-16 11:40:33','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Wird produziert',TO_TIMESTAMP('2020-06-16 11:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:33.651Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:33.689Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542424) 
;

-- 2020-06-16T08:40:33.733Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614873
;

-- 2020-06-16T08:40:33.768Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614873)
;

-- 2020-06-16T08:40:34.290Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552999,614874,0,417,TO_TIMESTAMP('2020-06-16 11:40:33','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Produkt-Zuordnung',TO_TIMESTAMP('2020-06-16 11:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:34.326Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:34.364Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542944) 
;

-- 2020-06-16T08:40:34.407Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614874
;

-- 2020-06-16T08:40:34.442Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614874)
;

-- 2020-06-16T08:40:34.995Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,553024,614875,0,417,TO_TIMESTAMP('2020-06-16 11:40:34','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','N','N','N','N','N','N','N','Statistik Gruppe',TO_TIMESTAMP('2020-06-16 11:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:35.031Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:35.067Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542949) 
;

-- 2020-06-16T08:40:35.107Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614875
;

-- 2020-06-16T08:40:35.141Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614875)
;

-- 2020-06-16T08:40:35.640Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,553157,614876,0,417,TO_TIMESTAMP('2020-06-16 11:40:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Salesgroup_UOM_ID',TO_TIMESTAMP('2020-06-16 11:40:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:35.678Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:35.716Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542962) 
;

-- 2020-06-16T08:40:35.758Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614876
;

-- 2020-06-16T08:40:35.792Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614876)
;

-- 2020-06-16T08:40:36.283Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557781,614877,0,417,TO_TIMESTAMP('2020-06-16 11:40:35','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Compensation Type',TO_TIMESTAMP('2020-06-16 11:40:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:36.320Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:36.355Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543461) 
;

-- 2020-06-16T08:40:36.398Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614877
;

-- 2020-06-16T08:40:36.432Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614877)
;

-- 2020-06-16T08:40:36.964Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557782,614878,0,417,TO_TIMESTAMP('2020-06-16 11:40:36','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Compensation Amount Type',TO_TIMESTAMP('2020-06-16 11:40:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:36.999Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:37.033Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543462) 
;

-- 2020-06-16T08:40:37.069Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614878
;

-- 2020-06-16T08:40:37.102Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614878)
;

-- 2020-06-16T08:40:37.611Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558029,614879,0,417,TO_TIMESTAMP('2020-06-16 11:40:37','YYYY-MM-DD HH24:MI:SS'),100,'',7,'D','','Y','N','N','N','N','N','N','N','Pck. Gr.',TO_TIMESTAMP('2020-06-16 11:40:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:37.648Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:37.686Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543503) 
;

-- 2020-06-16T08:40:37.731Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614879
;

-- 2020-06-16T08:40:37.766Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614879)
;

-- 2020-06-16T08:40:38.270Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558030,614880,0,417,TO_TIMESTAMP('2020-06-16 11:40:37','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Verpackungseinheit',TO_TIMESTAMP('2020-06-16 11:40:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:38.308Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:38.345Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543504) 
;

-- 2020-06-16T08:40:38.389Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614880
;

-- 2020-06-16T08:40:38.424Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614880)
;

-- 2020-06-16T08:40:38.934Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558031,614881,0,417,TO_TIMESTAMP('2020-06-16 11:40:38','YYYY-MM-DD HH24:MI:SS'),100,'Hersteller des Produktes',10,'D','','Y','N','N','N','N','N','N','N','Hersteller',TO_TIMESTAMP('2020-06-16 11:40:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:38.972Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:39.011Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544128) 
;

-- 2020-06-16T08:40:39.054Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614881
;

-- 2020-06-16T08:40:39.088Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614881)
;

-- 2020-06-16T08:40:39.608Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558046,614882,0,417,TO_TIMESTAMP('2020-06-16 11:40:39','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Dosage Form',TO_TIMESTAMP('2020-06-16 11:40:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:39.643Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614882 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:39.679Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543505) 
;

-- 2020-06-16T08:40:39.723Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614882
;

-- 2020-06-16T08:40:39.758Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614882)
;

-- 2020-06-16T08:40:40.303Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558047,614883,0,417,TO_TIMESTAMP('2020-06-16 11:40:39','YYYY-MM-DD HH24:MI:SS'),100,'Is checked if the product needs prescription',1,'D','Y','N','N','N','N','N','N','N','Prescription',TO_TIMESTAMP('2020-06-16 11:40:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:40.338Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614883 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:40.373Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543506) 
;

-- 2020-06-16T08:40:40.408Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614883
;

-- 2020-06-16T08:40:40.441Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614883)
;

-- 2020-06-16T08:40:40.936Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558048,614884,0,417,TO_TIMESTAMP('2020-06-16 11:40:40','YYYY-MM-DD HH24:MI:SS'),100,'Is checked if the product needs be stored in refrigirator',1,'D','Y','N','N','N','N','N','N','N','Cold Chain',TO_TIMESTAMP('2020-06-16 11:40:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:40.972Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614884 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:41.011Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543507) 
;

-- 2020-06-16T08:40:41.052Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614884
;

-- 2020-06-16T08:40:41.101Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614884)
;

-- 2020-06-16T08:40:41.603Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558049,614885,0,417,TO_TIMESTAMP('2020-06-16 11:40:41','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Narcotic',TO_TIMESTAMP('2020-06-16 11:40:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:41.639Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614885 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:41.674Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543508) 
;

-- 2020-06-16T08:40:41.710Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614885
;

-- 2020-06-16T08:40:41.745Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614885)
;

-- 2020-06-16T08:40:42.264Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558050,614886,0,417,TO_TIMESTAMP('2020-06-16 11:40:41','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','TFG',TO_TIMESTAMP('2020-06-16 11:40:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:42.299Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614886 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:42.336Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543509) 
;

-- 2020-06-16T08:40:42.375Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614886
;

-- 2020-06-16T08:40:42.413Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614886)
;

-- 2020-06-16T08:40:42.914Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558061,614887,0,417,TO_TIMESTAMP('2020-06-16 11:40:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Indication',TO_TIMESTAMP('2020-06-16 11:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:42.950Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614887 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:42.987Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543510) 
;

-- 2020-06-16T08:40:43.029Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614887
;

-- 2020-06-16T08:40:43.067Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614887)
;

-- 2020-06-16T08:40:43.566Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558062,614888,0,417,TO_TIMESTAMP('2020-06-16 11:40:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FAM/ZUB',TO_TIMESTAMP('2020-06-16 11:40:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:43.612Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:43.648Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543511) 
;

-- 2020-06-16T08:40:43.689Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614888
;

-- 2020-06-16T08:40:43.724Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614888)
;

-- 2020-06-16T08:40:44.218Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558983,614889,0,417,TO_TIMESTAMP('2020-06-16 11:40:43','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','M_ProductPlanningSchema_Selector',TO_TIMESTAMP('2020-06-16 11:40:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:44.254Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614889 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:44.291Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543841) 
;

-- 2020-06-16T08:40:44.334Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614889
;

-- 2020-06-16T08:40:44.368Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614889)
;

-- 2020-06-16T08:40:44.878Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559491,614890,0,417,TO_TIMESTAMP('2020-06-16 11:40:44','YYYY-MM-DD HH24:MI:SS'),100,1999,'D','Y','N','N','N','N','N','N','N','Auszeichnungsname',TO_TIMESTAMP('2020-06-16 11:40:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:44.913Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614890 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:44.948Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543486) 
;

-- 2020-06-16T08:40:44.992Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614890
;

-- 2020-06-16T08:40:45.025Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614890)
;

-- 2020-06-16T08:40:45.574Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559551,614891,0,417,TO_TIMESTAMP('2020-06-16 11:40:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Pharma Product Category',TO_TIMESTAMP('2020-06-16 11:40:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:45.611Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:45.646Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543925) 
;

-- 2020-06-16T08:40:45.685Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614891
;

-- 2020-06-16T08:40:45.720Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614891)
;

-- 2020-06-16T08:40:46.226Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560725,614892,0,417,TO_TIMESTAMP('2020-06-16 11:40:45','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Zutaten',TO_TIMESTAMP('2020-06-16 11:40:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:46.261Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:46.298Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544186) 
;

-- 2020-06-16T08:40:46.339Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614892
;

-- 2020-06-16T08:40:46.375Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614892)
;

-- 2020-06-16T08:40:46.892Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,561005,614893,0,417,TO_TIMESTAMP('2020-06-16 11:40:46','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Obligatorische Zusatzangaben',TO_TIMESTAMP('2020-06-16 11:40:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:46.929Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:46.966Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544254) 
;

-- 2020-06-16T08:40:47.008Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614893
;

-- 2020-06-16T08:40:47.043Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614893)
;

-- 2020-06-16T08:40:47.556Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,561006,614894,0,417,TO_TIMESTAMP('2020-06-16 11:40:47','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Lager- und Transporttemperatur',TO_TIMESTAMP('2020-06-16 11:40:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:47.590Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614894 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:47.624Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544255) 
;

-- 2020-06-16T08:40:47.659Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614894
;

-- 2020-06-16T08:40:47.692Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614894)
;

-- 2020-06-16T08:40:48.209Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564266,614895,0,417,TO_TIMESTAMP('2020-06-16 11:40:47','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Ist Angebotsgruppe',TO_TIMESTAMP('2020-06-16 11:40:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:48.246Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:48.281Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576156) 
;

-- 2020-06-16T08:40:48.322Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614895
;

-- 2020-06-16T08:40:48.355Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614895)
;

-- 2020-06-16T08:40:48.837Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567933,614896,0,417,TO_TIMESTAMP('2020-06-16 11:40:48','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Pharma Product',TO_TIMESTAMP('2020-06-16 11:40:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:48.871Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:48.904Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576727) 
;

-- 2020-06-16T08:40:48.938Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614896
;

-- 2020-06-16T08:40:48.970Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614896)
;

-- 2020-06-16T08:40:49.474Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568973,614897,0,417,TO_TIMESTAMP('2020-06-16 11:40:49','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','External ID',TO_TIMESTAMP('2020-06-16 11:40:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:49.510Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614897 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:49.547Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2020-06-16T08:40:49.590Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614897
;

-- 2020-06-16T08:40:49.626Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614897)
;

-- 2020-06-16T08:40:50.144Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569088,614898,0,417,TO_TIMESTAMP('2020-06-16 11:40:49','YYYY-MM-DD HH24:MI:SS'),100,100,'D','Y','N','N','N','N','N','N','N','GTIN',TO_TIMESTAMP('2020-06-16 11:40:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:50.181Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614898 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:50.217Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577135) 
;

-- 2020-06-16T08:40:50.262Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614898
;

-- 2020-06-16T08:40:50.297Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614898)
;

-- 2020-06-16T08:40:50.831Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569331,614899,0,417,TO_TIMESTAMP('2020-06-16 11:40:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Customs Tariff',TO_TIMESTAMP('2020-06-16 11:40:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:50.868Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:50.906Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577257) 
;

-- 2020-06-16T08:40:50.948Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614899
;

-- 2020-06-16T08:40:50.984Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614899)
;

-- 2020-06-16T08:40:51.503Z
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570072,614900,0,417,TO_TIMESTAMP('2020-06-16 11:40:51','YYYY-MM-DD HH24:MI:SS'),100,'Markiert Produkte, zu denen prinzipiell eine Provisionierung stattfinden kann, sofern die entsprechenden Einstellungen und Verträge hinterlegt sind.',1,'D','Y','N','N','N','N','N','N','N','Wird provisioniert',TO_TIMESTAMP('2020-06-16 11:40:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T08:40:51.540Z
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-16T08:40:51.576Z
-- description fields
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577571) 
;

-- 2020-06-16T08:40:51.617Z
-- description fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614900
;

-- 2020-06-16T08:40:51.654Z
-- description fields
/* DDL */ select AD_Element_Link_Create_Missing_Field(614900)
;

-- 2020-06-16T08:50:16.834Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 11:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570123
;

-- 2020-06-16T08:50:16.977Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-06-16 11:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570093
;

-- 2020-06-16T08:50:17.120Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-06-16 11:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570101
;

-- 2020-06-16T08:50:17.268Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-06-16 11:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570104
;

-- 2020-06-16T08:50:17.411Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-06-16 11:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570097
;

-- 2020-06-16T08:50:17.556Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-06-16 11:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570099
;

-- 2020-06-16T08:50:17.700Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-06-16 11:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570091
;

-- 2020-06-16T08:50:47.686Z
-- description fields
UPDATE AD_Tab SET Parent_Column_ID=6862,Updated=TO_TIMESTAMP('2020-06-16 11:50:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=417
;

-- 2020-06-16T08:56:12.520Z
-- description fields
UPDATE AD_Tab SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2020-06-16 11:56:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=417
;

-- 2020-06-16T14:52:36.087Z
-- description fields
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2020-06-16 17:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=417
;

-- 2020-06-16T14:55:11.585Z
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5383,0,180,1000039,570133,'F',TO_TIMESTAMP('2020-06-16 17:55:11','YYYY-MM-DD HH24:MI:SS'),100,'Ressource','Y','N','N','Y','N','N','N',0,'Ressource',10,0,0,TO_TIMESTAMP('2020-06-16 17:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-16T14:57:41.301Z
-- description fields
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2020-06-16 17:57:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5383
;

-- 2020-06-16T15:08:36.228Z
-- description fields
UPDATE AD_Field SET DisplayLogic='@ProductType@=''R''',Updated=TO_TIMESTAMP('2020-06-16 18:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5383
;

-- 2020-06-16T15:10:23.321Z
-- description fields
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2020-06-16 18:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5383
;

-- 2020-06-16T15:10:38.365Z
-- description fields
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2020-06-16 18:10:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6773
;

-- 2020-06-16T15:12:20.854Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 18:12:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570083
;

-- 2020-06-16T15:12:21.042Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 18:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570085
;

-- 2020-06-16T15:12:21.224Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-16 18:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570084
;

-- 2020-06-16T15:12:21.356Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-06-16 18:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570087
;

-- 2020-06-16T15:12:21.508Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-06-16 18:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570088
;

-- 2020-06-16T15:12:21.702Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-06-16 18:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570086
;

-- 2020-06-16T15:12:21.872Z
-- description fields
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-06-16 18:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570089
;

-- 2020-06-16T15:15:06.184Z
-- description fields
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2020-06-16 18:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570088
;

-- 2020-06-16T15:15:12.755Z
-- description fields
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2020-06-16 18:15:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570087
;

-- 2020-06-16T15:15:26.043Z
-- description fields
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2020-06-16 18:15:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570087
;

-- 2020-06-16T15:15:29.487Z
-- description fields
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2020-06-16 18:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570088
;


