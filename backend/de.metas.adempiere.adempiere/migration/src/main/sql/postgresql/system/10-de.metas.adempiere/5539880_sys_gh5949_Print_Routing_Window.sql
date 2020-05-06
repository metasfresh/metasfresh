-- 2019-12-19T15:32:00.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569787,126,0,30,540282,'AD_Table_ID',TO_TIMESTAMP('2019-12-19 17:32:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','de.metas.swat',10,'The Database Table provides the information of the table definition','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','DB-Tabelle',0,0,TO_TIMESTAMP('2019-12-19 17:32:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-12-19T15:32:00.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569787 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-19T15:32:00.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- 2019-12-19T15:32:03.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_PrinterRouting','ALTER TABLE public.AD_PrinterRouting ADD COLUMN AD_Table_ID NUMERIC(10)')
;

-- 2019-12-19T15:32:03.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_PrinterRouting ADD CONSTRAINT ADTable_ADPrinterRouting FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED
;

-- 2019-12-19T15:33:07.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569787,593809,0,540469,0,TO_TIMESTAMP('2019-12-19 17:33:07','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',0,'D','The Database Table provides the information of the table definition',0,'Y','Y','Y','N','N','N','N','N','DB-Tabelle',170,170,0,1,1,TO_TIMESTAMP('2019-12-19 17:33:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:33:07.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-12-19T15:33:07.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2019-12-19T15:33:07.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593809
;

-- 2019-12-19T15:33:07.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593809)
;

-- 2019-12-19T15:35:01.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540467,541692,TO_TIMESTAMP('2019-12-19 17:35:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-12-19 17:35:01','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-12-19T15:35:01.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541692 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-12-19T15:35:01.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542155,541692,TO_TIMESTAMP('2019-12-19 17:35:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-12-19 17:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:02.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542156,541692,TO_TIMESTAMP('2019-12-19 17:35:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-12-19 17:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:02.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542155,543282,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:02.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551276,0,540467,564748,543282,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kĂ¶nnen Daten ĂĽber Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','Sektion',10,0,10,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:02.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551271,0,540467,564749,543282,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Name of the Printer','Internal (Opereating System) Name of the Printer; Please mote that the printer name may be different on different clients. Enter a printer name, which applies to ALL clients (e.g. printer on a server). <p>
If none is entered, the default printer is used. You specify your default printer when you log in. You can also change the default printer in Preferences.','Y','N','Y','N','Y','Drucker',20,0,20,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:02.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551269,0,540467,564750,543282,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Beschreibung',30,0,30,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:02.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551266,0,540467,564751,543282,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei MĂ¶glichkeiten, einen Datensatz nicht mehr verfĂĽgbar zu machen: einer ist, ihn zu lĂ¶schen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr fĂĽr eine Auswahl verfĂĽgbar, aber verfĂĽgbar fĂĽr die Verwendung in Berichten. Es gibt zwei GrĂĽnde, DatensĂ¤tze zu deaktivieren und nicht zu lĂ¶schen: (1) Das System braucht den Datensatz fĂĽr Revisionszwecke. (2) Der Datensatz wird von anderen DatensĂ¤tzen referenziert. Z.B. kĂ¶nnen Sie keinen GeschĂ¤ftspartner lĂ¶schen, wenn es Rechnungen fĂĽr diesen GeschĂ¤ftspartner gibt. Sie deaktivieren den GeschĂ¤ftspartner und verhindern, dass dieser Eintrag in zukĂĽnftigen VorgĂ¤ngen verwendet wird.','Y','N','Y','N','Y','Aktiv',40,0,40,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:02.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540468,541693,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-12-19T15:35:02.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541693 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-12-19T15:35:02.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542157,541693,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:02.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542157,543283,TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-12-19 17:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:03.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551288,0,540468,564752,543283,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Logischer Drucker',0,0,10,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:03.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551285,0,540468,564753,543283,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','N','Y','Name',0,0,20,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:03.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551281,0,540468,564754,543283,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Beschreibung',0,0,30,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:03.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551278,0,540468,564755,543283,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei MĂ¶glichkeiten, einen Datensatz nicht mehr verfĂĽgbar zu machen: einer ist, ihn zu lĂ¶schen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr fĂĽr eine Auswahl verfĂĽgbar, aber verfĂĽgbar fĂĽr die Verwendung in Berichten. Es gibt zwei GrĂĽnde, DatensĂ¤tze zu deaktivieren und nicht zu lĂ¶schen: (1) Das System braucht den Datensatz fĂĽr Revisionszwecke. (2) Der Datensatz wird von anderen DatensĂ¤tzen referenziert. Z.B. kĂ¶nnen Sie keinen GeschĂ¤ftspartner lĂ¶schen, wenn es Rechnungen fĂĽr diesen GeschĂ¤ftspartner gibt. Sie deaktivieren den GeschĂ¤ftspartner und verhindern, dass dieser Eintrag in zukĂĽnftigen VorgĂ¤ngen verwendet wird.','Y','N','N','N','Y','Aktiv',0,0,40,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:03.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540469,541694,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-12-19T15:35:03.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541694 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-12-19T15:35:03.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542158,541694,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:03.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542158,543284,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:03.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,556239,0,540469,564756,543284,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','Y','Reihenfolge',0,0,10,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:03.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551289,0,540469,564757,543284,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei MĂ¶glichkeiten, einen Datensatz nicht mehr verfĂĽgbar zu machen: einer ist, ihn zu lĂ¶schen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr fĂĽr eine Auswahl verfĂĽgbar, aber verfĂĽgbar fĂĽr die Verwendung in Berichten. Es gibt zwei GrĂĽnde, DatensĂ¤tze zu deaktivieren und nicht zu lĂ¶schen: (1) Das System braucht den Datensatz fĂĽr Revisionszwecke. (2) Der Datensatz wird von anderen DatensĂ¤tzen referenziert. Z.B. kĂ¶nnen Sie keinen GeschĂ¤ftspartner lĂ¶schen, wenn es Rechnungen fĂĽr diesen GeschĂ¤ftspartner gibt. Sie deaktivieren den GeschĂ¤ftspartner und verhindern, dass dieser Eintrag in zukĂĽnftigen VorgĂ¤ngen verwendet wird.','Y','N','N','N','Y','Aktiv',0,0,20,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:04.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551297,0,540469,564758,543284,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Mandant fĂĽr diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie kĂ¶nnen keine Daten ĂĽber Mandanten hinweg verwenden. .','Y','N','N','N','Y','Mandant',0,0,30,TO_TIMESTAMP('2019-12-19 17:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:04.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551301,0,540469,564759,543284,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kĂ¶nnen Daten ĂĽber Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','Y','Sektion',0,0,40,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:04.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551292,0,540469,564760,543284,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben fĂĽr die Belegverarbeitung.','Y','N','N','N','Y','Belegart',0,0,50,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:04.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551299,0,540469,564761,543284,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','N','N','Y','Prozess',0,0,60,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:04.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551300,0,540469,564762,543284,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role','The Role determines security and access a user who has this Role will have in the System.','Y','N','N','N','Y','Rolle',0,0,70,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:04.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551304,0,540469,564763,543284,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','Y','Nutzer',0,0,80,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:04.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,555184,0,540469,564764,543284,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Seiten-Kriterium',0,0,90,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:05.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,555108,0,540469,564765,543284,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Von Seite',0,0,100,TO_TIMESTAMP('2019-12-19 17:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:05.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,555109,0,540469,564766,543284,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Bis Seite',0,0,110,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:05.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,555185,0,540469,564767,543284,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Letzte Seiten',0,0,120,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:05.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551302,0,540469,564768,543284,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Logischer Drucker',0,0,130,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:05.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551298,0,540469,564769,543284,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Logischer Schacht',0,0,140,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:05.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551293,0,540469,564770,543284,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Beschreibung',0,0,150,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:05.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593809,0,540469,564771,543284,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','N','Y','DB-Tabelle',0,0,160,TO_TIMESTAMP('2019-12-19 17:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:45.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542159,541694,TO_TIMESTAMP('2019-12-19 17:35:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-12-19 17:35:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:54.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542159,543285,TO_TIMESTAMP('2019-12-19 17:35:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2019-12-19 17:35:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:35:58.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542159,543286,TO_TIMESTAMP('2019-12-19 17:35:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2019-12-19 17:35:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:36:26.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543286, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-19 17:36:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564759
;

-- 2019-12-19T15:36:33.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543286, SeqNo=20,Updated=TO_TIMESTAMP('2019-12-19 17:36:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564758
;

-- 2019-12-19T15:37:11.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543285, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-19 17:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564757
;

-- 2019-12-19T15:38:23.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543284, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-19 17:38:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564757
;

-- 2019-12-19T15:38:34.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=543285
;

-- 2019-12-19T15:38:42.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543284, SeqNo=20,Updated=TO_TIMESTAMP('2019-12-19 17:38:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564759
;

-- 2019-12-19T15:38:49.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543284, SeqNo=30,Updated=TO_TIMESTAMP('2019-12-19 17:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564758
;

-- 2019-12-19T15:48:11.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-12-19 17:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551274
;

-- 2019-12-19T15:48:13.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-12-19 17:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551275
;

-- 2019-12-19T15:48:19.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-12-19 17:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551270
;

-- 2019-12-19T15:48:31.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542156,543287,TO_TIMESTAMP('2019-12-19 17:48:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2019-12-19 17:48:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:48:35.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542156,543288,TO_TIMESTAMP('2019-12-19 17:48:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2019-12-19 17:48:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:49:08.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543287, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-19 17:49:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564751
;

-- 2019-12-19T15:49:16.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543288, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-19 17:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564748
;

-- 2019-12-19T15:49:32.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551275,0,540467,564772,543282,'F',TO_TIMESTAMP('2019-12-19 17:49:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Printer Type',40,0,0,TO_TIMESTAMP('2019-12-19 17:49:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:50:01.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551270,0,540467,564773,543287,'F',TO_TIMESTAMP('2019-12-19 17:50:01','YYYY-MM-DD HH24:MI:SS'),100,'Print without dialog','The Direct Print checkbox indicates that this report will print without a print dialog box being displayed.','Y','N','N','Y','N','N','N',0,'Direct print',20,0,0,TO_TIMESTAMP('2019-12-19 17:50:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:52:03.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=543286
;

-- 2019-12-19T15:52:06.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=542159
;

-- 2019-12-19T15:52:51.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2019-12-19 17:52:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564758
;

-- 2019-12-19T15:52:53.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2019-12-19 17:52:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564759
;

-- 2019-12-19T15:52:57.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-12-19 17:52:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564760
;

-- 2019-12-19T15:52:59.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-12-19 17:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564761
;

-- 2019-12-19T15:53:03.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-12-19 17:53:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564771
;

-- 2019-12-19T15:53:07.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-12-19 17:53:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564762
;

-- 2019-12-19T15:53:10.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-12-19 17:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564763
;

-- 2019-12-19T15:53:14.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2019-12-19 17:53:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564764
;

-- 2019-12-19T15:53:16.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2019-12-19 17:53:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564765
;

-- 2019-12-19T15:53:19.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2019-12-19 17:53:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564766
;

-- 2019-12-19T15:53:21.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2019-12-19 17:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564767
;

-- 2019-12-19T15:53:24.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2019-12-19 17:53:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564768
;

-- 2019-12-19T15:53:27.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2019-12-19 17:53:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564769
;

-- 2019-12-19T15:53:30.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2019-12-19 17:53:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564770
;

-- 2019-12-19T15:53:38.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2019-12-19 17:53:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564759
;

-- 2019-12-19T15:53:41.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=450,Updated=TO_TIMESTAMP('2019-12-19 17:53:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564758
;

-- 2019-12-19T15:57:45.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,574159,541414,0,540168,TO_TIMESTAMP('2019-12-19 17:57:45','YYYY-MM-DD HH24:MI:SS'),100,'Logischer Drucker','de.metas.printing','540168 (Todo: Set Internal Name for UI testing)','Y','N','N','N','N','Logischer Drucker',TO_TIMESTAMP('2019-12-19 17:57:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T15:57:45.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541414 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-12-19T15:57:45.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541414, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541414)
;

-- 2019-12-19T15:57:45.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(574159) 
;

-- 2019-12-19T15:57:45.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:45.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-12-19T15:57:55.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

