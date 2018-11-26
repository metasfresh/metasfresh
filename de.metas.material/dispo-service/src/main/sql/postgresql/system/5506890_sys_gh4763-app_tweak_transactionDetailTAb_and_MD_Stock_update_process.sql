-- 2018-11-22T18:07:34.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563603,570694,0,540885,TO_TIMESTAMP('2018-11-22 18:07:34','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Prozesslauf "Lagerbestand zurücksetzen"',TO_TIMESTAMP('2018-11-22 18:07:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T18:07:34.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570694 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-22T18:07:35.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563604,570695,0,540885,TO_TIMESTAMP('2018-11-22 18:07:34','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Bestand',TO_TIMESTAMP('2018-11-22 18:07:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T18:07:35.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570695 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-22T18:07:43.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-11-22 18:07:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570695
;

-- 2018-11-22T18:07:51.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-11-22 18:07:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570694
;

-- 2018-11-22T18:07:52.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-11-22 18:07:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570695
;

-- 2018-11-22T18:07:56.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-11-22 18:07:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570694
;

-- 2018-11-22T18:08:52.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570694,0,540885,541217,554402,'F',TO_TIMESTAMP('2018-11-22 18:08:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AD_PInstance_ResetStock_ID',70,0,0,TO_TIMESTAMP('2018-11-22 18:08:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T18:09:20.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570695,0,540885,541217,554403,'F',TO_TIMESTAMP('2018-11-22 18:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'MD_Stock_ID',80,0,0,TO_TIMESTAMP('2018-11-22 18:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T18:09:45.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=33,Updated=TO_TIMESTAMP('2018-11-22 18:09:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554402
;

-- 2018-11-22T18:09:50.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2018-11-22 18:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554403
;

-- 2018-11-22T18:10:26.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-11-22 18:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554403
;

-- 2018-11-22T18:10:26.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-11-22 18:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554402
;

-- 2018-11-22T18:10:26.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-11-22 18:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549142
;

-- 2018-11-22T18:10:26.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-11-22 18:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549143
;

-- 2018-11-22T18:10:26.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-11-22 18:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549144
;

-- 2018-11-22T18:10:26.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-11-22 18:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549135
;

-- 2018-11-22T18:10:51.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=620588
;

-- 2018-11-22T18:10:51.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1038,625024,560435,0,540334,TO_TIMESTAMP('2018-11-22 18:10:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-11-22 18:10:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T18:10:53.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=526, Description='Menge', Help='Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.', Name='Menge',Updated=TO_TIMESTAMP('2018-11-22 18:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560435
;

-- 2018-11-22T18:10:53.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625024
;

-- 2018-11-22T18:10:53.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,526,625025,560435,0,540334,TO_TIMESTAMP('2018-11-22 18:10:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-11-22 18:10:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T18:10:53.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526) 
;

-- 2018-11-22T18:11:11.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625025
;

-- 2018-11-22T18:11:11.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,526,625026,560435,0,540334,TO_TIMESTAMP('2018-11-22 18:11:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-11-22 18:11:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T18:11:12.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625026
;

-- 2018-11-22T18:11:12.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,526,625027,560435,0,540334,TO_TIMESTAMP('2018-11-22 18:11:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-11-22 18:11:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T18:11:13.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=543680, Description=NULL, Help=NULL, Name='Bestandsänderung',Updated=TO_TIMESTAMP('2018-11-22 18:11:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560435
;

-- 2018-11-22T18:11:13.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625027
;

-- 2018-11-22T18:11:13.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543680,625028,560435,0,540334,TO_TIMESTAMP('2018-11-22 18:11:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-11-22 18:11:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T18:11:13.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543680) 
;

-- 2018-11-22T18:22:44.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs', Description='Aktualisiert den Lagerbestand (MD_Stock) mit Hilfe von HU-Daten (M_HU, M_HUStorage u.A.).', Name='Lagerbestand mit HU-Daten korrigieren', Value='MD_Stock_Update_From_M_HUs',Updated=TO_TIMESTAMP('2018-11-22 18:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540907
;

-- 2018-11-22T18:22:44.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Aktualisiert den Lagerbestand (MD_Stock) mit Hilfe von HU-Daten (M_HU, M_HUStorage u.A.).', IsActive='Y', Name='Lagerbestand mit HU-Daten korrigieren',Updated=TO_TIMESTAMP('2018-11-22 18:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541009
;

-- 2018-11-22T18:22:54.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 18:22:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Lagerbestand mit HU-Daten korrigieren' WHERE AD_Process_ID=540907 AND AD_Language='de_CH'
;

-- 2018-11-22T18:22:57.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 18:22:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Lagerbestand mit HU-Daten korrigieren' WHERE AD_Process_ID=540907 AND AD_Language='de_DE'
;

-- 2018-11-22T18:23:14.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 18:23:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Update stock data from HU data' WHERE AD_Process_ID=540907 AND AD_Language='en_US'
;

-- 2018-11-22T18:23:28.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Aktualisiert den Lagerbestand (MD_Stock) mit Hilfe von HU-Daten (M_HU, M_HU_Storage u.A.).',Updated=TO_TIMESTAMP('2018-11-22 18:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540907
;

-- 2018-11-22T18:23:28.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Aktualisiert den Lagerbestand (MD_Stock) mit Hilfe von HU-Daten (M_HU, M_HU_Storage u.A.).', IsActive='Y', Name='Lagerbestand mit HU-Daten korrigieren',Updated=TO_TIMESTAMP('2018-11-22 18:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541009
;

-- 2018-11-22T18:58:33.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy)
SELECT 0,0,540907,0,550047,TO_TIMESTAMP('2018-11-22 18:58:32','YYYY-MM-DD HH24:MI:SS'),100,'*/15 * * * *','Update MD_Stock (and thus also MD_Candidate) every 15 minutes; will become obsolete once we addressed the last problems.','de.metas.material.dispo',0,'D','Y','N',7,'N','MD_Stock_Update_From_M_HUs','N','P','C','NEW',0,TO_TIMESTAMP('2018-11-22 18:58:32','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from AD_Scheduler where AD_Process_ID=540907)
;

CREATE INDEX IF NOT EXISTS m_product_c_uom_id
   ON public.m_product (c_uom_id ASC NULLS LAST);
COMMENT ON INDEX public.m_product_c_uom_id
  IS 'The purpose of this index is to support ccases where products are left-join just because their C_UOM_ID is needed.
Concrete example: MD_Stock_From_HUs_V';
