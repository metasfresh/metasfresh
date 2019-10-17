
-- modify the unique index to only apply to active records
-- might be needed further down, when we rename a legacy C_UOM record
DROP INDEX public.c_uom_name;
CREATE UNIQUE INDEX c_uom_name
    ON public.c_uom (ad_client_id, name) where IsActive='Y';

COMMIT;

-- 2019-08-01T14:17:56.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568550,576982,0,29,540542,'QtyDeliveredCatch',TO_TIMESTAMP('2019-08-01 16:17:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Tatsächlich gelieferte Menge','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geliefert Catch',0,0,TO_TIMESTAMP('2019-08-01 16:17:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-08-01T14:17:56.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568550 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-08-01T14:17:56.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576982) 
;

-- 2019-08-01T14:17:56.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_QtyPicked','ALTER TABLE public.M_ShipmentSchedule_QtyPicked ADD COLUMN QtyDeliveredCatch NUMERIC')
;

-- 2019-08-01T14:19:23.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2019-08-01 16:19:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540542
;

-- 2019-08-01T14:19:42.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568549,582525,0,540564,TO_TIMESTAMP('2019-08-01 16:19:42','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit.',10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Catch Einheit',TO_TIMESTAMP('2019-08-01 16:19:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-01T14:19:42.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582525 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-01T14:19:42.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576953) 
;

-- 2019-08-01T14:19:42.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582525
;

-- 2019-08-01T14:19:42.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582525)
;

-- 2019-08-01T14:19:42.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568550,582526,0,540564,TO_TIMESTAMP('2019-08-01 16:19:42','YYYY-MM-DD HH24:MI:SS'),100,'Tatsächlich gelieferte Menge',10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Geliefert Catch',TO_TIMESTAMP('2019-08-01 16:19:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-01T14:19:42.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582526 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-01T14:19:42.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576982) 
;

-- 2019-08-01T14:19:42.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582526
;

-- 2019-08-01T14:19:42.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582526)
;

-- 2019-08-01T14:20:05.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582525,0,540564,540020,560366,'F',TO_TIMESTAMP('2019-08-01 16:20:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Catch_UOM_ID',110,0,0,TO_TIMESTAMP('2019-08-01 16:20:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-01T14:20:20.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582526,0,540564,540020,560367,'F',TO_TIMESTAMP('2019-08-01 16:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyDeliveredCatch',120,0,0,TO_TIMESTAMP('2019-08-01 16:20:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-01T14:20:55.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560367
;

-- 2019-08-01T14:20:55.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560366
;

-- 2019-08-01T14:20:55.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540625
;

-- 2019-08-01T14:20:55.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540624
;

-- 2019-08-01T14:20:55.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540623
;

-- 2019-08-01T14:20:55.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540627
;

-- 2019-08-01T14:20:55.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540628
;

-- 2019-08-01T14:20:55.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540629
;

-- 2019-08-01T14:20:55.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540626
;

-- 2019-08-01T14:20:55.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-08-01 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547317
;

-- 2019-08-01T14:21:37.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576973, Name='Geliefert (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-01 16:21:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553766
;

-- 2019-08-01T14:21:37.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576973) 
;

-- 2019-08-01T14:21:37.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=553766
;

-- 2019-08-01T14:21:37.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(553766)
;

-- 2019-08-01T14:22:28.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-08-01 16:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540623
;

-- 2019-08-01T14:22:28.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-08-01 16:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540625
;

-- 2019-08-01T14:22:28.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-08-01 16:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540624
;

-- 2019-08-01T14:23:08.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540625
;

-- 2019-08-01T14:23:10.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540624
;

-- 2019-08-01T14:23:12.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540623
;

-- 2019-08-01T14:23:14.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540627
;

-- 2019-08-01T14:23:18.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560367
;

-- 2019-08-01T14:23:20.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560366
;

-- 2019-08-01T14:23:23.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547318
;

-- 2019-08-01T14:23:24.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547317
;

-- 2019-08-01T14:23:26.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540629
;

-- 2019-08-01T14:23:28.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540626
;

-- 2019-08-01T14:23:31.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-08-01 16:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540628
;

-- 2019-08-01T14:23:58.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=14,Updated=TO_TIMESTAMP('2019-08-01 16:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560366
;

-- 2019-08-01T14:24:06.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=12,Updated=TO_TIMESTAMP('2019-08-01 16:24:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560367
;

-- 2019-08-01T14:24:09.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-08-01 16:24:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547318
;

-- 2019-08-01T14:24:31.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562809
;

-- 2019-08-01T14:24:32.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553766
;

-- 2019-08-01T14:24:32.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562808
;

-- 2019-08-01T14:24:33.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553759
;

-- 2019-08-01T14:24:36.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553760
;

-- 2019-08-01T14:24:36.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553768
;

-- 2019-08-01T14:24:38.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553764
;

-- 2019-08-01T14:24:39.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553758
;

-- 2019-08-01T14:24:39.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556130
;

-- 2019-08-01T14:24:40.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553762
;

-- 2019-08-01T14:24:40.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553761
;

-- 2019-08-01T14:24:41.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553765
;

-- 2019-08-01T14:24:43.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-01 16:24:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553767
;

-- 2019-08-01T14:25:17.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562809,0,540564,540020,560368,'F',TO_TIMESTAMP('2019-08-01 16:25:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyLU',110,0,0,TO_TIMESTAMP('2019-08-01 16:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-01T14:25:38.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562808,0,540564,540020,560369,'F',TO_TIMESTAMP('2019-08-01 16:25:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyTU',120,0,0,TO_TIMESTAMP('2019-08-01 16:25:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-01T14:26:15.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562809,0,540564,540020,560370,'F',TO_TIMESTAMP('2019-08-01 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyLU',130,0,0,TO_TIMESTAMP('2019-08-01 16:26:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-01T14:26:22.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560370
;

-- 2019-08-01T14:27:12.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='LU Anzahl', PrintName='LU Anzahl',Updated=TO_TIMESTAMP('2019-08-01 16:27:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542491 AND AD_Language='de_CH'
;

-- 2019-08-01T14:27:12.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542491,'de_CH') 
;

-- 2019-08-01T14:27:45.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Number of LUs', PrintName='Number of LUs',Updated=TO_TIMESTAMP('2019-08-01 16:27:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542491 AND AD_Language='en_US'
;

-- 2019-08-01T14:27:45.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542491,'en_US') 
;

-- 2019-08-01T14:27:54.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='LU Anzahl', PrintName='LU Anzahl',Updated=TO_TIMESTAMP('2019-08-01 16:27:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542491 AND AD_Language='de_DE'
;

-- 2019-08-01T14:27:54.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542491,'de_DE') 
;

-- 2019-08-01T14:27:54.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542491,'de_DE') 
;

-- 2019-08-01T14:27:54.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyLU', Name='LU Anzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=542491
;

-- 2019-08-01T14:27:54.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyLU', Name='LU Anzahl', Description=NULL, Help=NULL, AD_Element_ID=542491 WHERE UPPER(ColumnName)='QTYLU' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-01T14:27:54.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyLU', Name='LU Anzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=542491 AND IsCentrallyMaintained='Y'
;

-- 2019-08-01T14:27:54.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LU Anzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542491) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542491)
;

-- 2019-08-01T14:27:54.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LU Anzahl', Name='LU Anzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542491)
;

-- 2019-08-01T14:27:54.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LU Anzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542491
;

-- 2019-08-01T14:27:54.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LU Anzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 542491
;

-- 2019-08-01T14:27:54.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LU Anzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542491
;

-- 2019-08-01T14:28:20.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='TU Anzahl', PrintName='TU Anzahl',Updated=TO_TIMESTAMP('2019-08-01 16:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542490 AND AD_Language='de_CH'
;

-- 2019-08-01T14:28:20.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542490,'de_CH') 
;

-- 2019-08-01T14:28:31.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Number of TUs', PrintName='Number of TUs',Updated=TO_TIMESTAMP('2019-08-01 16:28:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542490 AND AD_Language='en_US'
;

-- 2019-08-01T14:28:31.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542490,'en_US') 
;

-- 2019-08-01T14:28:42.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='TU Anzahl', PrintName='TU Anzahl',Updated=TO_TIMESTAMP('2019-08-01 16:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542490 AND AD_Language='de_DE'
;

-- 2019-08-01T14:28:42.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542490,'de_DE') 
;

-- 2019-08-01T14:28:42.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542490,'de_DE') 
;

-- 2019-08-01T14:28:42.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyTU', Name='TU Anzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=542490
;

-- 2019-08-01T14:28:42.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyTU', Name='TU Anzahl', Description=NULL, Help=NULL, AD_Element_ID=542490 WHERE UPPER(ColumnName)='QTYTU' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-01T14:28:42.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyTU', Name='TU Anzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=542490 AND IsCentrallyMaintained='Y'
;

-- 2019-08-01T14:28:42.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='TU Anzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542490) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542490)
;

-- 2019-08-01T14:28:42.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='TU Anzahl', Name='TU Anzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542490)
;

-- 2019-08-01T14:28:42.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='TU Anzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542490
;

-- 2019-08-01T14:28:42.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='TU Anzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 542490
;

-- 2019-08-01T14:28:42.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'TU Anzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542490
;

-- 2019-08-01T14:30:13.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Menge (Lagereinheit)', PrintName='Menge (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-01 16:30:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542270 AND AD_Language='de_CH'
;

-- 2019-08-01T14:30:13.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542270,'de_CH') 
;

-- 2019-08-01T14:30:21.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Qty picked', PrintName='Qty picked',Updated=TO_TIMESTAMP('2019-08-01 16:30:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542270 AND AD_Language='en_US'
;

-- 2019-08-01T14:30:21.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542270,'en_US') 
;

-- 2019-08-01T14:30:27.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Menge (Lagereinheit)', PrintName='Menge (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-01 16:30:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542270 AND AD_Language='de_DE'
;

-- 2019-08-01T14:30:27.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542270,'de_DE') 
;

-- 2019-08-01T14:30:27.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542270,'de_DE') 
;

-- 2019-08-01T14:30:27.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyPicked', Name='Menge (Lagereinheit)', Description=NULL, Help=NULL WHERE AD_Element_ID=542270
;

-- 2019-08-01T14:30:27.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPicked', Name='Menge (Lagereinheit)', Description=NULL, Help=NULL, AD_Element_ID=542270 WHERE UPPER(ColumnName)='QTYPICKED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-01T14:30:27.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPicked', Name='Menge (Lagereinheit)', Description=NULL, Help=NULL WHERE AD_Element_ID=542270 AND IsCentrallyMaintained='Y'
;

-- 2019-08-01T14:30:27.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge (Lagereinheit)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542270) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542270)
;

-- 2019-08-01T14:30:27.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Menge (Lagereinheit)', Name='Menge (Lagereinheit)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542270)
;

-- 2019-08-01T14:30:27.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Menge (Lagereinheit)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542270
;

-- 2019-08-01T14:30:27.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Menge (Lagereinheit)', Description=NULL, Help=NULL WHERE AD_Element_ID = 542270
;

-- 2019-08-01T14:30:27.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Menge (Lagereinheit)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542270
;

-- 2019-08-01T14:30:45.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Picked quantity', PrintName='Picked quantity',Updated=TO_TIMESTAMP('2019-08-01 16:30:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542270 AND AD_Language='en_US'
;

-- 2019-08-01T14:30:45.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542270,'en_US') 
;

-- 2019-08-01T14:31:28.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='CU', PrintName='CU',Updated=TO_TIMESTAMP('2019-08-01 16:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542583 AND AD_Language='de_CH'
;

-- 2019-08-01T14:31:28.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542583,'de_CH') 
;

-- 2019-08-01T14:31:38.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='CU', PrintName='CU',Updated=TO_TIMESTAMP('2019-08-01 16:31:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542583 AND AD_Language='en_US'
;

-- 2019-08-01T14:31:38.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542583,'en_US') 
;

-- 2019-08-01T14:31:46.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='CU', PrintName='CU',Updated=TO_TIMESTAMP('2019-08-01 16:31:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542583 AND AD_Language='de_DE'
;

-- 2019-08-01T14:31:46.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542583,'de_DE') 
;

-- 2019-08-01T14:31:46.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542583,'de_DE') 
;

-- 2019-08-01T14:31:46.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='VHU_ID', Name='CU', Description=NULL, Help=NULL WHERE AD_Element_ID=542583
;

-- 2019-08-01T14:31:46.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VHU_ID', Name='CU', Description=NULL, Help=NULL, AD_Element_ID=542583 WHERE UPPER(ColumnName)='VHU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-01T14:31:46.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VHU_ID', Name='CU', Description=NULL, Help=NULL WHERE AD_Element_ID=542583 AND IsCentrallyMaintained='Y'
;

-- 2019-08-01T14:31:46.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='CU', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542583) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542583)
;

-- 2019-08-01T14:31:46.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='CU', Name='CU' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542583)
;

-- 2019-08-01T14:31:46.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='CU', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542583
;

-- 2019-08-01T14:31:46.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='CU', Description=NULL, Help=NULL WHERE AD_Element_ID = 542583
;

-- 2019-08-01T14:31:46.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'CU', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542583
;

-- 2019-08-01T14:32:23.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='TU', PrintName='TU',Updated=TO_TIMESTAMP('2019-08-01 16:32:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542462 AND AD_Language='de_CH'
;

-- 2019-08-01T14:32:23.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542462,'de_CH') 
;

-- 2019-08-01T14:32:33.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='TU', PrintName='TU',Updated=TO_TIMESTAMP('2019-08-01 16:32:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542462 AND AD_Language='en_US'
;

-- 2019-08-01T14:32:33.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542462,'en_US') 
;

-- 2019-08-01T14:32:44.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='TU', PrintName='TU',Updated=TO_TIMESTAMP('2019-08-01 16:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542462 AND AD_Language='de_DE'
;

-- 2019-08-01T14:32:44.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542462,'de_DE') 
;

-- 2019-08-01T14:32:44.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542462,'de_DE') 
;

-- 2019-08-01T14:32:44.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_TU_HU_ID', Name='TU', Description='', Help=NULL WHERE AD_Element_ID=542462
;

-- 2019-08-01T14:32:44.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_TU_HU_ID', Name='TU', Description='', Help=NULL, AD_Element_ID=542462 WHERE UPPER(ColumnName)='M_TU_HU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-01T14:32:44.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_TU_HU_ID', Name='TU', Description='', Help=NULL WHERE AD_Element_ID=542462 AND IsCentrallyMaintained='Y'
;

-- 2019-08-01T14:32:44.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='TU', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542462) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542462)
;

-- 2019-08-01T14:32:44.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='TU', Name='TU' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542462)
;

-- 2019-08-01T14:32:44.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='TU', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542462
;

-- 2019-08-01T14:32:44.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='TU', Description='', Help=NULL WHERE AD_Element_ID = 542462
;

-- 2019-08-01T14:32:44.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'TU', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542462
;

-- 2019-08-01T14:33:14.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='LU', PrintName='LU',Updated=TO_TIMESTAMP('2019-08-01 16:33:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542455 AND AD_Language='de_CH'
;

-- 2019-08-01T14:33:14.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542455,'de_CH') 
;

-- 2019-08-01T14:33:26.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Loading Unit', IsTranslated='Y', Name='LU', PrintName='LU',Updated=TO_TIMESTAMP('2019-08-01 16:33:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542455 AND AD_Language='en_US'
;

-- 2019-08-01T14:33:26.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542455,'en_US') 
;

-- 2019-08-01T14:33:49.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Loading Unit', IsTranslated='Y', Name='LU', PrintName='LU',Updated=TO_TIMESTAMP('2019-08-01 16:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542455 AND AD_Language='de_DE'
;

-- 2019-08-01T14:33:49.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542455,'de_DE') 
;

-- 2019-08-01T14:33:49.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542455,'de_DE') 
;

-- 2019-08-01T14:33:49.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_LU_HU_ID', Name='LU', Description='Loading Unit', Help=NULL WHERE AD_Element_ID=542455
;

-- 2019-08-01T14:33:49.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_LU_HU_ID', Name='LU', Description='Loading Unit', Help=NULL, AD_Element_ID=542455 WHERE UPPER(ColumnName)='M_LU_HU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-01T14:33:49.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_LU_HU_ID', Name='LU', Description='Loading Unit', Help=NULL WHERE AD_Element_ID=542455 AND IsCentrallyMaintained='Y'
;

-- 2019-08-01T14:33:49.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LU', Description='Loading Unit', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542455) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542455)
;

-- 2019-08-01T14:33:49.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LU', Name='LU' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542455)
;

-- 2019-08-01T14:33:49.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LU', Description='Loading Unit', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542455
;

-- 2019-08-01T14:33:49.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LU', Description='Loading Unit', Help=NULL WHERE AD_Element_ID = 542455
;

-- 2019-08-01T14:33:49.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LU', Description = 'Loading Unit', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542455
;

-- 2019-08-01T14:33:59.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Loading Unit',Updated=TO_TIMESTAMP('2019-08-01 16:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542455 AND AD_Language='de_CH'
;

-- 2019-08-01T14:33:59.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542455,'de_CH') 
;

-- 2019-08-01T14:34:40.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Trading Unit',Updated=TO_TIMESTAMP('2019-08-01 16:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542462 AND AD_Language='de_CH'
;

-- 2019-08-01T14:34:40.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542462,'de_CH') 
;

-- 2019-08-01T14:34:43.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Trading Unit',Updated=TO_TIMESTAMP('2019-08-01 16:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542462 AND AD_Language='en_US'
;

-- 2019-08-01T14:34:43.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542462,'en_US') 
;

-- 2019-08-01T14:34:48.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Trading Unit',Updated=TO_TIMESTAMP('2019-08-01 16:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542462 AND AD_Language='de_DE'
;

-- 2019-08-01T14:34:48.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542462,'de_DE') 
;

-- 2019-08-01T14:34:48.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542462,'de_DE') 
;

-- 2019-08-01T14:34:48.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_TU_HU_ID', Name='TU', Description='Trading Unit', Help=NULL WHERE AD_Element_ID=542462
;

-- 2019-08-01T14:34:48.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_TU_HU_ID', Name='TU', Description='Trading Unit', Help=NULL, AD_Element_ID=542462 WHERE UPPER(ColumnName)='M_TU_HU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-01T14:34:48.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_TU_HU_ID', Name='TU', Description='Trading Unit', Help=NULL WHERE AD_Element_ID=542462 AND IsCentrallyMaintained='Y'
;

-- 2019-08-01T14:34:48.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='TU', Description='Trading Unit', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542462) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542462)
;

-- 2019-08-01T14:34:48.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='TU', Description='Trading Unit', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542462
;

-- 2019-08-01T14:34:48.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='TU', Description='Trading Unit', Help=NULL WHERE AD_Element_ID = 542462
;

-- 2019-08-01T14:34:48.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'TU', Description = 'Trading Unit', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542462
;

-- 2019-08-01T14:35:06.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Customer Unit',Updated=TO_TIMESTAMP('2019-08-01 16:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542583 AND AD_Language='de_CH'
;

-- 2019-08-01T14:35:06.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542583,'de_CH') 
;

-- 2019-08-01T14:35:09.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Customer Unit',Updated=TO_TIMESTAMP('2019-08-01 16:35:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542583 AND AD_Language='en_US'
;

-- 2019-08-01T14:35:09.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542583,'en_US') 
;

-- 2019-08-01T14:35:11.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Customer Unit',Updated=TO_TIMESTAMP('2019-08-01 16:35:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542583 AND AD_Language='de_DE'
;

-- 2019-08-01T14:35:11.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542583,'de_DE') 
;

-- 2019-08-01T14:35:11.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542583,'de_DE') 
;

-- 2019-08-01T14:35:11.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='VHU_ID', Name='CU', Description='Customer Unit', Help=NULL WHERE AD_Element_ID=542583
;

-- 2019-08-01T14:35:11.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VHU_ID', Name='CU', Description='Customer Unit', Help=NULL, AD_Element_ID=542583 WHERE UPPER(ColumnName)='VHU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-01T14:35:11.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VHU_ID', Name='CU', Description='Customer Unit', Help=NULL WHERE AD_Element_ID=542583 AND IsCentrallyMaintained='Y'
;

-- 2019-08-01T14:35:11.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='CU', Description='Customer Unit', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542583) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542583)
;

-- 2019-08-01T14:35:11.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='CU', Description='Customer Unit', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542583
;

-- 2019-08-01T14:35:11.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='CU', Description='Customer Unit', Help=NULL WHERE AD_Element_ID = 542583
;

-- 2019-08-01T14:35:11.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'CU', Description = 'Customer Unit', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542583
;

-- 2019-08-01T14:36:58.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560367
;

-- 2019-08-01T14:36:58.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560366
;

-- 2019-08-01T14:36:58.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540622
;

-- 2019-08-01T14:36:58.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560369
;

-- 2019-08-01T14:36:58.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560368
;

-- 2019-08-01T14:36:58.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540625
;

-- 2019-08-01T14:36:58.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540627
;

-- 2019-08-01T14:36:58.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540628
;

-- 2019-08-01T14:36:58.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540629
;

-- 2019-08-01T14:36:58.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540626
;

-- 2019-08-01T14:36:58.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-08-01 16:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547317
;

-- 2019-08-02T05:52:08.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568498
;

-- 2019-08-02T05:52:08.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568498
;

-- 2019-08-02T05:52:51.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=576963
;

-- 2019-08-02T05:52:51.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=576963
;

-- 2019-08-02T05:53:15.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule','Catch_UOM_ID','NUMERIC(10)',null,null)
;

-- 2019-08-02T05:53:21.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule','QtyToDeliverCatch_Override','NUMERIC',null,null)
;


select db_alter_table('M_ShipmentSchedule','ALTER TABLE M_ShipmentSchedule DROP COLUMN IF EXISTS QtyPickedCatch;');

-- 2019-08-02T06:23:59.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551617,582527,0,500221,TO_TIMESTAMP('2019-08-02 08:23:59','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Menge TU (berechnet)',TO_TIMESTAMP('2019-08-02 08:23:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T06:23:59.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582527 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T06:23:59.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542669) 
;

-- 2019-08-02T06:23:59.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582527
;

-- 2019-08-02T06:23:59.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582527)
;

-- 2019-08-02T06:24:00.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551641,582528,0,500221,TO_TIMESTAMP('2019-08-02 08:23:59','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Packvorschrift-Produkt Zuordnung (berechnet)',TO_TIMESTAMP('2019-08-02 08:23:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T06:24:00.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582528 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T06:24:00.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542670) 
;

-- 2019-08-02T06:24:00.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582528
;

-- 2019-08-02T06:24:00.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582528)
;

-- 2019-08-02T09:00:32.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540027,542709,TO_TIMESTAMP('2019-08-02 11:00:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','catch',25,TO_TIMESTAMP('2019-08-02 11:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T09:01:52.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582461,0,500221,542709,560371,'F',TO_TIMESTAMP('2019-08-02 11:01:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Catch_UOM_ID',10,0,0,TO_TIMESTAMP('2019-08-02 11:01:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T09:02:22.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582462,0,500221,542709,560372,'F',TO_TIMESTAMP('2019-08-02 11:02:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyToDeliverCatch_Override',20,0,0,TO_TIMESTAMP('2019-08-02 11:02:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T09:03:02.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Catch_UOM_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 11:03:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582461
;

-- 2019-08-02T09:03:23.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Catch_UOM_ID/0@>0',Updated=TO_TIMESTAMP('2019-08-02 11:03:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582462
;

-- 2019-08-02T10:17:59.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-08-02 12:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540586
;

-- 2019-08-02T10:26:37.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541285,'O',TO_TIMESTAMP('2019-08-02 12:26:36','YYYY-MM-DD HH24:MI:SS'),100,'If > 0, and an UOM conversion is updated, then the system waits the given number of milliseconds be before asynchronously updating the respective shipment schedules'' catch UOM ID.
If = 0, the update is done synchonously
If < 0, no update is done; note that in this case, the catch UOM ID is still updated during the regular updating of invalid shipment schedules.','D','Y','de.metas.inoutcandidate.M_ShipmentSchedule.CatchUOM_ID_update_delay_ms',TO_TIMESTAMP('2019-08-02 12:26:36','YYYY-MM-DD HH24:MI:SS'),100,'10000')
;

-- 2019-08-02T10:41:28.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If > 0, and an UOM conversion is updated, then the system waits the given number of milliseconds be before asynchronously updating the respective shipment schedules'' catch UOM ID.
If = 0, the update is done synchonously
If < 0, no update is done; note that in this case, the catch UOM ID is still updated during the regular updating of invalid shipment schedules.
TODO: move this to a dedicated shipping related config window asap.',Updated=TO_TIMESTAMP('2019-08-02 12:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541285
;
-- 2019-08-02T11:00:30.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576990,0,'UOMToType',TO_TIMESTAMP('2019-08-02 13:00:29','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','Typ der Zieleinheit','Typ der Zieleinheit',TO_TIMESTAMP('2019-08-02 13:00:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T11:00:30.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576990 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-08-02T11:00:37.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-02 13:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576990 AND AD_Language='de_CH'
;

-- 2019-08-02T11:00:37.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576990,'de_CH') 
;

-- 2019-08-02T11:00:40.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-02 13:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576990 AND AD_Language='de_DE'
;

-- 2019-08-02T11:00:40.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576990,'de_DE') 
;

-- 2019-08-02T11:00:40.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576990,'de_DE') 
;

-- 2019-08-02T11:00:51.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Type of target UOM', PrintName='Type of target UOM',Updated=TO_TIMESTAMP('2019-08-02 13:00:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576990 AND AD_Language='en_US'
;

-- 2019-08-02T11:00:51.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576990,'en_US') 
;

-- 2019-08-02T11:02:18.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568551,576990,0,10,175,'UOMToType','(select UOMType from C_UOM u where u.C_UOM_ID=C_UOM_Conversion.C_UOM_To_ID)',TO_TIMESTAMP('2019-08-02 13:02:18','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',2,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Typ der Zieleinheit',0,0,TO_TIMESTAMP('2019-08-02 13:02:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-08-02T11:02:18.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568551 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-08-02T11:02:18.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576990) 
;

-- 2019-08-02T11:03:15.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsRefreshAllOnActivate='Y',Updated=TO_TIMESTAMP('2019-08-02 13:03:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=134
;

-- 2019-08-02T11:03:17.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568488,582529,0,134,TO_TIMESTAMP('2019-08-02 13:03:17','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird',1,'D','Y','N','N','N','N','N','N','N','Ziel ist Catch-Maßeinheit',TO_TIMESTAMP('2019-08-02 13:03:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T11:03:17.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582529 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T11:03:17.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576956) 
;

-- 2019-08-02T11:03:17.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582529
;

-- 2019-08-02T11:03:17.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582529)
;

-- 2019-08-02T11:03:17.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568551,582530,0,134,TO_TIMESTAMP('2019-08-02 13:03:17','YYYY-MM-DD HH24:MI:SS'),100,'',2,'D','Y','N','N','N','N','N','N','N','Typ der Zieleinheit',TO_TIMESTAMP('2019-08-02 13:03:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T11:03:17.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582530 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T11:03:17.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576990) 
;

-- 2019-08-02T11:03:17.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582530
;

-- 2019-08-02T11:03:17.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582530)
;

-- 2019-08-02T11:03:22.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsRefreshAllOnActivate='N',Updated=TO_TIMESTAMP('2019-08-02 13:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=134
;

-- 2019-08-02T11:04:09.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582530,0,134,540248,560373,'F',TO_TIMESTAMP('2019-08-02 13:04:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UOMToType',25,0,0,TO_TIMESTAMP('2019-08-02 13:04:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T11:04:23.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-08-02 13:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560373
;

-- 2019-08-02T11:04:23.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-08-02 13:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542938
;

-- 2019-08-02T11:04:23.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-08-02 13:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542939
;

-- 2019-08-02T11:04:23.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-08-02 13:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542940
;

-- 2019-08-02T11:04:23.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-08-02 13:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542941
;

-- 2019-08-02T11:04:23.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-08-02 13:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542935
;

-- 2019-08-02T11:04:59.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568551,582531,0,53246,TO_TIMESTAMP('2019-08-02 13:04:59','YYYY-MM-DD HH24:MI:SS'),100,'',2,'D','Y','N','N','N','N','N','N','N','Typ der Zieleinheit',TO_TIMESTAMP('2019-08-02 13:04:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T11:04:59.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582531 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T11:04:59.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576990) 
;

-- 2019-08-02T11:04:59.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582531
;

-- 2019-08-02T11:04:59.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582531)
;

-- 2019-08-02T11:05:47.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582531,0,53246,1000024,560374,'F',TO_TIMESTAMP('2019-08-02 13:05:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UOMToType',25,0,0,TO_TIMESTAMP('2019-08-02 13:05:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T11:06:06.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-08-02 13:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560374
;

-- 2019-08-02T11:06:06.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-08-02 13:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000205
;

-- 2019-08-02T11:06:06.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-08-02 13:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000206
;

-- 2019-08-02T11:06:06.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-08-02 13:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560297
;

-- 2019-08-02T11:06:06.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-08-02 13:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000202
;

-- 2019-08-02T11:06:06.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-08-02 13:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000200
;

-- 2019-08-02T11:06:54.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@UOMToType/XX@=''WE',Updated=TO_TIMESTAMP('2019-08-02 13:06:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582446
;

-- 2019-08-02T11:07:01.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@UOMToType/XX@=''WE''',Updated=TO_TIMESTAMP('2019-08-02 13:07:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582446
;

-- 2019-08-02T11:07:52.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird.', Help='Die Option ist nur für Gewichtseinheiten verfügbar.',Updated=TO_TIMESTAMP('2019-08-02 13:07:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576956 AND AD_Language='de_CH'
;

-- 2019-08-02T11:07:52.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576956,'de_CH') 
;

-- 2019-08-02T11:07:55.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Die Option ist nur für Gewichtseinheiten verfügbar.',Updated=TO_TIMESTAMP('2019-08-02 13:07:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576956 AND AD_Language='de_DE'
;

-- 2019-08-02T11:07:55.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576956,'de_DE') 
;

-- 2019-08-02T11:07:55.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576956,'de_DE') 
;

-- 2019-08-02T11:07:55.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCatchUOMForProduct', Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help='Die Option ist nur für Gewichtseinheiten verfügbar.' WHERE AD_Element_ID=576956
;

-- 2019-08-02T11:07:55.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCatchUOMForProduct', Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help='Die Option ist nur für Gewichtseinheiten verfügbar.', AD_Element_ID=576956 WHERE UPPER(ColumnName)='ISCATCHUOMFORPRODUCT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-02T11:07:55.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCatchUOMForProduct', Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help='Die Option ist nur für Gewichtseinheiten verfügbar.' WHERE AD_Element_ID=576956 AND IsCentrallyMaintained='Y'
;

-- 2019-08-02T11:07:55.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help='Die Option ist nur für Gewichtseinheiten verfügbar.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576956) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576956)
;

-- 2019-08-02T11:07:55.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help='Die Option ist nur für Gewichtseinheiten verfügbar.', CommitWarning = NULL WHERE AD_Element_ID = 576956
;

-- 2019-08-02T11:07:55.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help='Die Option ist nur für Gewichtseinheiten verfügbar.' WHERE AD_Element_ID = 576956
;

-- 2019-08-02T11:07:55.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ziel ist Catch-Maßeinheit', Description = 'Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576956
;

-- 2019-08-02T11:08:06.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Only available for weight units.',Updated=TO_TIMESTAMP('2019-08-02 13:08:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576956 AND AD_Language='en_US'
;

-- 2019-08-02T11:08:06.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576956,'en_US') 
;

-- 2019-08-02T11:11:08.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='LE',Updated=TO_TIMESTAMP('2019-08-02 13:11:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540028
;

-- 2019-08-02T11:11:29.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='TM',Updated=TO_TIMESTAMP('2019-08-02 13:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=107
;

-- 2019-08-02T11:11:35.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='TM',Updated=TO_TIMESTAMP('2019-08-02 13:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=104
;

-- 2019-08-02T11:12:02.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='TM',Updated=TO_TIMESTAMP('2019-08-02 13:12:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540023
;

-- 2019-08-02T11:12:28.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='WE',Updated=TO_TIMESTAMP('2019-08-02 13:12:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540019
;

-- 2019-08-02T11:12:41.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='TM',Updated=TO_TIMESTAMP('2019-08-02 13:12:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=108
;

-- 2019-08-02T11:12:55.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='LE',Updated=TO_TIMESTAMP('2019-08-02 13:12:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540018
;

-- 2019-08-02T11:13:07.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='VL',Updated=TO_TIMESTAMP('2019-08-02 13:13:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540015
;

-- 2019-08-02T11:13:13.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='LE',Updated=TO_TIMESTAMP('2019-08-02 13:13:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540012
;

-- 2019-08-02T11:13:45.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='WE',Updated=TO_TIMESTAMP('2019-08-02 13:13:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540016
;

-- 2019-08-02T11:15:05.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET IsActive='Y',Updated=TO_TIMESTAMP('2019-08-02 13:15:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540017
;

-- 2019-08-02T11:15:26.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET IsActive='Y',Updated=TO_TIMESTAMP('2019-08-02 13:15:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540019
;

-- 2019-08-02T11:16:01.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM_Conversion (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_Conversion_ID,C_UOM_ID,C_UOM_To_ID,DivideRate,IsActive,IsCatchUOMForProduct,MultiplyRate,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2019-08-02 13:16:00','YYYY-MM-DD HH24:MI:SS'),100,540009,540019,540017,1000.000000000000,'Y','N',0.001000000000,TO_TIMESTAMP('2019-08-02 13:16:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T11:16:08.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET IsActive='N',Updated=TO_TIMESTAMP('2019-08-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540019
;

-- 2019-08-02T11:16:11.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET IsActive='Y',Updated=TO_TIMESTAMP('2019-08-02 13:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540016
;

-- 2019-08-02T11:16:30.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM_Conversion (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_Conversion_ID,C_UOM_ID,C_UOM_To_ID,DivideRate,IsActive,IsCatchUOMForProduct,MultiplyRate,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2019-08-02 13:16:29','YYYY-MM-DD HH24:MI:SS'),100,540010,540017,540016,1000.000000000000,'Y','N',0.001000000000,TO_TIMESTAMP('2019-08-02 13:16:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T11:16:45.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET IsActive='N',Updated=TO_TIMESTAMP('2019-08-02 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540016
;

-- copied this from 5528460_sys_gh5384-app_deactivate_legacy_KGM_uom.sql to avoid a name conflict
-- 2019-08-02T11:22:05.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET Description='Please don''t use this UOM (anymore). Instead, please use the "KGM" UOM with ID=540017', IsActive='N',Updated=TO_TIMESTAMP('2019-08-02 13:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=1000000
/*added additional conditions only here: */and AD_Client_ID=0 and Name='Kilogramm'
;
-- 2019-08-02T11:22:05.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET Description='Please don''t use this UOM (anymore). Instead, please use the "KGM" UOM with ID=540017', Name='Kilogramm', UOMSymbol='kg', IsTranslated='Y' WHERE C_UOM_ID=1000000
/*added additional conditions only here: */and AD_Client_ID=0 and Name='Kilogramm'
;

-- 2019-08-02T11:21:06.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMSymbol='kg',Updated=TO_TIMESTAMP('2019-08-02 13:21:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540017
;

-- 2019-08-02T11:21:06.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET Description=NULL, Name='Kilogram', UOMSymbol='kg', IsTranslated='Y' WHERE C_UOM_ID=540017
;

-- 2019-08-02T11:26:18.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET Name='Kilogramm',Updated=TO_TIMESTAMP('2019-08-02 13:26:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540017
;

-- 2019-08-02T11:26:18.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET Description=NULL, Name='Kilogramm', UOMSymbol='kg', IsTranslated='Y' WHERE C_UOM_ID=540017
;

-- 2019-08-02T11:26:29.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET Name='Kilogram',Updated=TO_TIMESTAMP('2019-08-02 13:26:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_UOM_ID=540017
;

-- 2019-08-02T12:01:59.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-08-02 14:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582531
;

-- 2019-08-02T12:04:29.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=53323,Updated=TO_TIMESTAMP('2019-08-02 14:04:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568551
;

-- 2019-08-02T12:11:22.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540448,'C_UOM.C_UOM_ID != @C_UOM_ID/-1@',TO_TIMESTAMP('2019-08-02 14:11:22','YYYY-MM-DD HH24:MI:SS'),100,'Any C_UOM_ID that is not the current @C_UOM_ID@','D','Y','C_UOM_not_ctx_C_UOM_ID','S',TO_TIMESTAMP('2019-08-02 14:11:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:11:32.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540448,Updated=TO_TIMESTAMP('2019-08-02 14:11:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1011
;

-- 2019-08-02T12:14:51.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@UOMToType/XX@!=''WE''', TechnicalNote='Column is relevant only for product-specific UOM conversions to a weight-UOM (e.g. PCE to KGM)',Updated=TO_TIMESTAMP('2019-08-02 14:14:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568488
;

-- 2019-08-02T12:39:06.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,52010,582532,0,258,TO_TIMESTAMP('2019-08-02 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Return Material Authorization Line',22,'D','Detail information about the returned goods','Y','N','N','N','N','N','N','N','RMA-Position',TO_TIMESTAMP('2019-08-02 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:06.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582532 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:06.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2413) 
;

-- 2019-08-02T12:39:06.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582532
;

-- 2019-08-02T12:39:06.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582532)
;

-- 2019-08-02T12:39:07.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,56355,582533,0,258,TO_TIMESTAMP('2019-08-02 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Storno-Zeile',TO_TIMESTAMP('2019-08-02 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:07.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582533 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:07.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53689) 
;

-- 2019-08-02T12:39:07.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582533
;

-- 2019-08-02T12:39:07.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582533)
;

-- 2019-08-02T12:39:07.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546591,582534,0,258,TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Prod.-Beschr. ändern',TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:07.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582534 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:07.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(504490) 
;

-- 2019-08-02T12:39:07.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582534
;

-- 2019-08-02T12:39:07.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582534)
;

-- 2019-08-02T12:39:07.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549844,582535,0,258,TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','von Rechnungsdispo verarbeitet',TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:07.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582535 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:07.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542255) 
;

-- 2019-08-02T12:39:07.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582535
;

-- 2019-08-02T12:39:07.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582535)
;

-- 2019-08-02T12:39:07.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550495,582536,0,258,TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','','Y','N','N','N','N','N','N','N','Strittig',TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:07.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582536 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:07.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2543) 
;

-- 2019-08-02T12:39:07.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582536
;

-- 2019-08-02T12:39:07.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582536)
;

-- 2019-08-02T12:39:07.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550504,582537,0,258,TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Qualitätsabzug %',TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:07.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582537 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:07.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542262) 
;

-- 2019-08-02T12:39:07.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582537
;

-- 2019-08-02T12:39:07.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582537)
;

-- 2019-08-02T12:39:07.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550574,582538,0,258,TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Qualität-Notiz',TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:07.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582538 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:07.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542322) 
;

-- 2019-08-02T12:39:07.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582538
;

-- 2019-08-02T12:39:07.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582538)
;

-- 2019-08-02T12:39:07.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550854,582539,0,258,TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Unterlieferanten',TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:07.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582539 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:07.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542467) 
;

-- 2019-08-02T12:39:07.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582539
;

-- 2019-08-02T12:39:07.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582539)
;

-- 2019-08-02T12:39:07.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550886,582540,0,258,TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Verpackungsmaterial',TO_TIMESTAMP('2019-08-02 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:07.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582540 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:07.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542246) 
;

-- 2019-08-02T12:39:08.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582540
;

-- 2019-08-02T12:39:08.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582540)
;

-- 2019-08-02T12:39:08.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551294,582541,0,258,TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Material-Vorgang-ID',TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:08.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582541 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:08.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542532) 
;

-- 2019-08-02T12:39:08.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582541
;

-- 2019-08-02T12:39:08.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582541)
;

-- 2019-08-02T12:39:08.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551754,582542,0,258,TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','EDI_DesadvLine',TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:08.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582542 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:08.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542692) 
;

-- 2019-08-02T12:39:08.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582542
;

-- 2019-08-02T12:39:08.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582542)
;

-- 2019-08-02T12:39:08.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552812,582543,0,258,TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','M_PackingMaterial_InOutLine_ID',TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:08.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582543 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:08.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542919) 
;

-- 2019-08-02T12:39:08.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582543
;

-- 2019-08-02T12:39:08.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582543)
;

-- 2019-08-02T12:39:08.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556514,582544,0,258,TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Original receipt line containing the products that are returned.',10,'D','Y','N','N','N','N','N','N','N','Return_Origin_InOutLine_ID',TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:08.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582544 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:08.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543319) 
;

-- 2019-08-02T12:39:08.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582544
;

-- 2019-08-02T12:39:08.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582544)
;

-- 2019-08-02T12:39:08.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556810,582545,0,258,TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Gebindekonfiguration',TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:08.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582545 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:08.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542486) 
;

-- 2019-08-02T12:39:08.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582545
;

-- 2019-08-02T12:39:08.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582545)
;

-- 2019-08-02T12:39:08.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556811,582546,0,258,TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Set if this record is prepared from HU module perspective',1,'D','Y','N','N','N','N','N','N','N','HU Prepared',TO_TIMESTAMP('2019-08-02 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:08.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582546 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:08.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542643) 
;

-- 2019-08-02T12:39:08.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582546
;

-- 2019-08-02T12:39:09.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582546)
;

-- 2019-08-02T12:39:09.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557321,582547,0,258,TO_TIMESTAMP('2019-08-02 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Abo-Verlauf',TO_TIMESTAMP('2019-08-02 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:09.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582547 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:09.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540080) 
;

-- 2019-08-02T12:39:09.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582547
;

-- 2019-08-02T12:39:09.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582547)
;

-- 2019-08-02T12:39:09.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568479,582548,0,258,TO_TIMESTAMP('2019-08-02 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit.',10,'D','Y','N','N','N','N','N','N','N','Catch Einheit',TO_TIMESTAMP('2019-08-02 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:09.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582548 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:09.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576953) 
;

-- 2019-08-02T12:39:09.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582548
;

-- 2019-08-02T12:39:09.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582548)
;

-- 2019-08-02T12:39:09.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568480,582549,0,258,TO_TIMESTAMP('2019-08-02 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Tatsächlich gelieferte Menge',10,'D','Y','N','N','N','N','N','N','N','Geliefert Catch',TO_TIMESTAMP('2019-08-02 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:39:09.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582549 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T12:39:09.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576982) 
;

-- 2019-08-02T12:39:09.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582549
;

-- 2019-08-02T12:39:09.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582549)
;

-- 2019-08-02T12:40:12.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582548,0,258,540976,560375,'F',TO_TIMESTAMP('2019-08-02 14:40:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Catch_UOM_ID',65,0,0,TO_TIMESTAMP('2019-08-02 14:40:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:40:39.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582549,0,258,540976,560376,'F',TO_TIMESTAMP('2019-08-02 14:40:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyDeliveredCatch',180,0,0,TO_TIMESTAMP('2019-08-02 14:40:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T12:40:44.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=63,Updated=TO_TIMESTAMP('2019-08-02 14:40:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560376
;

-- 2019-08-02T12:41:06.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560376
;

-- 2019-08-02T12:41:06.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560375
;

-- 2019-08-02T12:41:06.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547331
;

-- 2019-08-02T12:41:06.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547332
;

-- 2019-08-02T12:41:06.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547333
;

-- 2019-08-02T12:41:06.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547334
;

-- 2019-08-02T12:41:06.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547335
;

-- 2019-08-02T12:41:06.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547336
;

-- 2019-08-02T12:41:06.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547337
;

-- 2019-08-02T12:41:06.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2019-08-02 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548906
;

