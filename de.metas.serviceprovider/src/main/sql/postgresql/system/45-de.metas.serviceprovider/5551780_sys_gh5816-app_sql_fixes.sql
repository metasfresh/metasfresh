-- 2020-02-11T20:02:57.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('S_Effort_Issue','ALTER TABLE public.S_Effort_Issue ADD COLUMN S_Budget_Issue_ID NUMERIC(10)')
;

-- 2020-02-11T20:02:57.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE S_Effort_Issue ADD CONSTRAINT SBudgetIssue_SEffortIssue FOREIGN KEY (S_Budget_Issue_ID) REFERENCES public.S_Budget_Issue DEFERRABLE INITIALLY DEFERRED
;

-- 2020-02-11T20:05:29.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-02-11 21:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569657
;

-- 2020-02-11T20:05:32.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('s_effort_issue','Effort_UOM_ID','NUMERIC(10)',null,null)
;

-- 2020-02-11T20:05:32.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('s_effort_issue','Effort_UOM_ID',null,'NOT NULL',null)
;

-- 2020-02-11T20:06:45.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542285,543496,TO_TIMESTAMP('2020-02-11 21:06:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','other_stuff',20,TO_TIMESTAMP('2020-02-11 21:06:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T20:06:58.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543496, SeqNo=10,Updated=TO_TIMESTAMP('2020-02-11 21:06:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566301
;

-- 2020-02-11T20:07:13.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569668,597942,0,542126,TO_TIMESTAMP('2020-02-11 21:07:13','YYYY-MM-DD HH24:MI:SS'),100,'Ursprünglich geplanter oder erwarteter Aufwand.',14,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Budgetiert',TO_TIMESTAMP('2020-02-11 21:07:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T20:07:13.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=597942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-02-11T20:07:13.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577381) 
;

-- 2020-02-11T20:07:13.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=597942
;

-- 2020-02-11T20:07:13.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(597942)
;

-- 2020-02-11T20:07:13.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569969,597943,0,542126,TO_TIMESTAMP('2020-02-11 21:07:13','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Aktueller Aufwand',TO_TIMESTAMP('2020-02-11 21:07:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T20:07:13.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=597943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-02-11T20:07:13.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577513) 
;

-- 2020-02-11T20:07:13.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=597943
;

-- 2020-02-11T20:07:13.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(597943)
;

-- 2020-02-11T20:07:13.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569993,597944,0,542126,TO_TIMESTAMP('2020-02-11 21:07:13','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Budget-Issue',TO_TIMESTAMP('2020-02-11 21:07:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T20:07:13.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=597944 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-02-11T20:07:13.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577512) 
;

-- 2020-02-11T20:07:13.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=597944
;

-- 2020-02-11T20:07:13.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(597944)
;

-- 2020-02-11T20:08:28.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,597944,0,542126,543496,566336,TO_TIMESTAMP('2020-02-11 21:08:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Budget-Issue',20,0,0,TO_TIMESTAMP('2020-02-11 21:08:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T20:08:39.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2020-02-11 21:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566301
;

-- 2020-02-11T20:08:51.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-02-11 21:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566336
;

-- 2020-02-11T20:08:51.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-02-11 21:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566302
;

-- 2020-02-11T20:08:51.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-02-11 21:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566303
;

-- 2020-02-11T20:08:51.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-02-11 21:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566304
;

-----------------

-- 2020-02-11T20:19:30.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('S_Budget_Issue','ALTER TABLE public.S_Budget_Issue ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- 2020-02-11T20:21:08.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-02-11 21:21:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=597937
;

-- 2020-02-11T20:21:12.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-02-11 21:21:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=597940
;

-- 2020-02-11T20:21:33.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutoApplyValidationRule='Y', IsAutocomplete='Y',Updated=TO_TIMESTAMP('2020-02-11 21:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569966
;

