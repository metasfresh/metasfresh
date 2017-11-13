-- 2017-11-11T12:42:05.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy,WidgetSize) VALUES (0,543472,0,'X1',TO_TIMESTAMP('2017-11-11 12:42:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Regal','Regal',TO_TIMESTAMP('2017-11-11 12:42:05','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2017-11-11T12:42:05.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543472 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-11-11T12:42:31.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-11 12:42:31','YYYY-MM-DD HH24:MI:SS'),Name='Rack',PrintName='Rack' WHERE AD_Element_ID=543472 AND AD_Language='en_US'
;

-- 2017-11-11T12:42:31.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543472,'en_US') 
;

-- 2017-11-11T12:42:36.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-11 12:42:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543472 AND AD_Language='en_US'
;

-- 2017-11-11T12:42:36.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543472,'en_US') 
;

-- 2017-11-11T13:04:15.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557832,543472,0,10,207,'N','X1',TO_TIMESTAMP('2017-11-11 13:04:15','YYYY-MM-DD HH24:MI:SS'),100,'N','D',60,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Regal',0,0,TO_TIMESTAMP('2017-11-11 13:04:15','YYYY-MM-DD HH24:MI:SS'),100,1.000000000000)
;

-- 2017-11-11T13:04:15.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557832 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-11T13:04:27.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=50,Updated=TO_TIMESTAMP('2017-11-11 13:04:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1401
;

-- 2017-11-11T13:04:30.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=40,Updated=TO_TIMESTAMP('2017-11-11 13:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1400
;

-- 2017-11-11T13:04:35.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2017-11-11 13:04:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557832
;

-- 2017-11-11T13:06:36.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557832,560486,0,178,67,TO_TIMESTAMP('2017-11-11 13:06:35','YYYY-MM-DD HH24:MI:SS'),100,60,'U',0,'Y','Y','Y','Y','N','N','N','N','N','Regal',95,85,999,1,TO_TIMESTAMP('2017-11-11 13:06:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-11T13:06:36.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560486 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-11T13:07:34.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560486,0,178,541165,549175,'F',TO_TIMESTAMP('2017-11-11 13:07:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Regal',55,0,0,TO_TIMESTAMP('2017-11-11 13:07:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-11T13:08:01.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-11-11 13:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549175
;

-- 2017-11-11T13:08:01.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-11-11 13:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548723
;

-- 2017-11-11T13:08:01.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-11-11 13:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548725
;

-- 2017-11-11T13:08:01.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-11-11 13:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548716
;

-- 2017-11-11T13:08:50.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Gang',Updated=TO_TIMESTAMP('2017-11-11 13:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1399
;

-- 2017-11-11T13:08:50.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gang', Description='X dimension, e.g., Aisle', Help='The X dimension indicates the Aisle a product is located in.' WHERE AD_Column_ID=1399 AND IsCentrallyMaintained='Y'
;

-- 2017-11-11T13:08:53.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Fach',Updated=TO_TIMESTAMP('2017-11-11 13:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1400
;

-- 2017-11-11T13:08:53.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fach', Description='Y dimension, e.g., Bin', Help='The Y dimension indicates the Bin a product is located in' WHERE AD_Column_ID=1400 AND IsCentrallyMaintained='Y'
;

-- 2017-11-11T13:09:07.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Ebene',Updated=TO_TIMESTAMP('2017-11-11 13:09:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1401
;

-- 2017-11-11T13:09:07.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ebene', Description='Z dimension, e.g., Level', Help='The Z dimension indicates the Level a product is located in.' WHERE AD_Column_ID=1401 AND IsCentrallyMaintained='Y'
;

-- 2017-11-11T13:09:17.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Gang',Updated=TO_TIMESTAMP('2017-11-11 13:09:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=633
;

-- 2017-11-11T13:09:17.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='X', Name='Gang', Description='X-Dimension, z.B. Gang', Help='Die X-Dimension zeigt den Gang an, in dem sich ein Produkt befindet.' WHERE AD_Element_ID=633
;

-- 2017-11-11T13:09:17.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='X', Name='Gang', Description='X-Dimension, z.B. Gang', Help='Die X-Dimension zeigt den Gang an, in dem sich ein Produkt befindet.', AD_Element_ID=633 WHERE UPPER(ColumnName)='X' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-11T13:09:17.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='X', Name='Gang', Description='X-Dimension, z.B. Gang', Help='Die X-Dimension zeigt den Gang an, in dem sich ein Produkt befindet.' WHERE AD_Element_ID=633 AND IsCentrallyMaintained='Y'
;

-- 2017-11-11T13:09:17.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gang', Description='X-Dimension, z.B. Gang', Help='Die X-Dimension zeigt den Gang an, in dem sich ein Produkt befindet.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=633) AND IsCentrallyMaintained='Y'
;

-- 2017-11-11T13:09:17.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='X', Name='Gang' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=633)
;

-- 2017-11-11T13:09:32.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Gang',Updated=TO_TIMESTAMP('2017-11-11 13:09:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=633
;

-- 2017-11-11T13:09:32.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gang', Name='Gang' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=633)
;

-- 2017-11-11T13:09:47.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-11 13:09:47','YYYY-MM-DD HH24:MI:SS'),Name='Aisle' WHERE AD_Element_ID=633 AND AD_Language='en_US'
;

-- 2017-11-11T13:09:47.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(633,'en_US') 
;

-- 2017-11-11T13:09:53.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-11 13:09:53','YYYY-MM-DD HH24:MI:SS'),PrintName='Aisle' WHERE AD_Element_ID=633 AND AD_Language='en_US'
;

-- 2017-11-11T13:09:53.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(633,'en_US') 
;

-- 2017-11-11T13:10:30.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-11 13:10:30','YYYY-MM-DD HH24:MI:SS'),Name='Rack' WHERE AD_Column_ID=557832 AND AD_Language='en_US'
;

-- 2017-11-11T13:10:45.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-11 13:10:45','YYYY-MM-DD HH24:MI:SS'),Name='Tray',IsTranslated='Y' WHERE AD_Column_ID=1400 AND AD_Language='en_US'
;

-- 2017-11-11T13:10:55.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Fach',Updated=TO_TIMESTAMP('2017-11-11 13:10:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=635
;

-- 2017-11-11T13:10:55.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Y', Name='Fach', Description='Y-Dimension, z.B. Fach', Help='Die Y-Dimension zeigt das Fach an, in dem sich ein Produkt befindet.' WHERE AD_Element_ID=635
;

-- 2017-11-11T13:10:55.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Y', Name='Fach', Description='Y-Dimension, z.B. Fach', Help='Die Y-Dimension zeigt das Fach an, in dem sich ein Produkt befindet.', AD_Element_ID=635 WHERE UPPER(ColumnName)='Y' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-11T13:10:55.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Y', Name='Fach', Description='Y-Dimension, z.B. Fach', Help='Die Y-Dimension zeigt das Fach an, in dem sich ein Produkt befindet.' WHERE AD_Element_ID=635 AND IsCentrallyMaintained='Y'
;

-- 2017-11-11T13:10:55.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fach', Description='Y-Dimension, z.B. Fach', Help='Die Y-Dimension zeigt das Fach an, in dem sich ein Produkt befindet.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=635) AND IsCentrallyMaintained='Y'
;

-- 2017-11-11T13:10:55.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Y', Name='Fach' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=635)
;

-- 2017-11-11T13:11:12.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-11 13:11:12','YYYY-MM-DD HH24:MI:SS'),Name='Tray',PrintName='Tray' WHERE AD_Element_ID=635 AND AD_Language='en_US'
;

-- 2017-11-11T13:11:12.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(635,'en_US') 
;

-- 2017-11-11T13:11:38.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Ebene', PrintName='Ebene',Updated=TO_TIMESTAMP('2017-11-11 13:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=637
;

-- 2017-11-11T13:11:38.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Z', Name='Ebene', Description='Z-Dimension, z.B. Ebene', Help='Die Z-Dimension zeigt an, auf welcher Ebene sich ein Produkt befindet.' WHERE AD_Element_ID=637
;

-- 2017-11-11T13:11:38.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Z', Name='Ebene', Description='Z-Dimension, z.B. Ebene', Help='Die Z-Dimension zeigt an, auf welcher Ebene sich ein Produkt befindet.', AD_Element_ID=637 WHERE UPPER(ColumnName)='Z' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-11T13:11:38.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Z', Name='Ebene', Description='Z-Dimension, z.B. Ebene', Help='Die Z-Dimension zeigt an, auf welcher Ebene sich ein Produkt befindet.' WHERE AD_Element_ID=637 AND IsCentrallyMaintained='Y'
;

-- 2017-11-11T13:11:38.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ebene', Description='Z-Dimension, z.B. Ebene', Help='Die Z-Dimension zeigt an, auf welcher Ebene sich ein Produkt befindet.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=637) AND IsCentrallyMaintained='Y'
;

-- 2017-11-11T13:11:38.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ebene', Name='Ebene' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=637)
;

-- 2017-11-11T13:11:51.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-11 13:11:51','YYYY-MM-DD HH24:MI:SS'),Name='Level',PrintName='Level' WHERE AD_Element_ID=637 AND AD_Language='en_US'
;

-- 2017-11-11T13:11:51.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(637,'en_US') 
;

-- 2017-11-11T13:12:10.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Locator','ALTER TABLE public.M_Locator ADD COLUMN X1 VARCHAR(60)')
;

-- 2017-11-11T13:12:32.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Fach',Updated=TO_TIMESTAMP('2017-11-11 13:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548723
;

-- 2017-11-11T13:12:36.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Gang',Updated=TO_TIMESTAMP('2017-11-11 13:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548722
;

-- 2017-11-11T13:12:41.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Ebene',Updated=TO_TIMESTAMP('2017-11-11 13:12:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548724
;

-- 2017-11-11T13:13:01.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560486,0,178,541165,549176,'F',TO_TIMESTAMP('2017-11-11 13:13:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Regal',74,0,0,TO_TIMESTAMP('2017-11-11 13:13:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-11T13:14:32.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2017-11-11 13:14:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548715
;

-- 2017-11-11T13:15:07.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549176
;

-- 2017-11-11T13:15:26.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-11-11 13:15:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549175
;

-- 2017-11-11T13:15:33.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-11-11 13:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548723
;

-- 2017-11-11T13:15:34.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-11-11 13:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548725
;

-- 2017-11-11T13:15:38.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-11-11 13:15:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548716
;

-- 2017-11-11T13:15:42.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2017-11-11 13:15:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548715
;

-- 2017-11-11T13:16:00.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-11-11 13:16:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548722
;

-- 2017-11-11T13:16:04.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-11-11 13:16:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549175
;

-- 2017-11-11T13:16:07.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-11-11 13:16:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548724
;

-- 2017-11-11T13:16:10.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-11-11 13:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548723
;

-- 2017-11-11T13:16:13.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-11-11 13:16:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548716
;

-- 2017-11-11T13:16:17.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-11-11 13:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548715
;

