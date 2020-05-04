-- 2018-02-23T19:30:35.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540836,0,'Y',TO_TIMESTAMP('2018-02-23 19:30:35','YYYY-MM-DD HH24:MI:SS'),100,'F','de.metas.contracts',541594,'Flatrate',0,'Flatrate',100,TO_TIMESTAMP('2018-02-23 19:30:35','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T19:30:35.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541594 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-02-23T19:31:27.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (19,'N','N','N','N',0,'Y',100,541423,'Y','N','N','N','N','N','N','Y','N','N','N',540941,'N','N','C_Flatrate_Conditions_ID','N',559465,'N','N','N','N','N','N',0,100,'Vertragsbedingungen','de.metas.order','N',10,0,0,TO_TIMESTAMP('2018-02-23 19:31:27','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-23 19:31:27','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T19:31:27.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559465 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-23T19:31:44.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_SchemaLine','ALTER TABLE public.C_CompensationGroup_SchemaLine ADD COLUMN C_Flatrate_Conditions_ID NUMERIC(10)')
;

-- 2018-02-23T19:31:44.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_CompensationGroup_SchemaLine ADD CONSTRAINT CFlatrateConditions_CCompensationGroupSchemaLine FOREIGN KEY (C_Flatrate_Conditions_ID) REFERENCES public.C_Flatrate_Conditions DEFERRABLE INITIALLY DEFERRED
;

-- 2018-02-23T19:31:59.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order',563025,559465,0,'Vertragsbedingungen',100,10,TO_TIMESTAMP('2018-02-23 19:31:58','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-23 19:31:58','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T19:31:59.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-23T19:33:22.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (IsAllowFiltering,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES ('N',551153,563025,'N',100,0,100,'Y',541468,'Y','N','N',0,541042,0,'Vertragsbedingungen','F',TO_TIMESTAMP('2018-02-23 19:33:21','YYYY-MM-DD HH24:MI:SS'),90,0,TO_TIMESTAMP('2018-02-23 19:33:21','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T19:33:28.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-02-23 19:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551153
;

-- 2018-02-23T19:35:19.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type/-@=F',Updated=TO_TIMESTAMP('2018-02-23 19:35:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563025
;

