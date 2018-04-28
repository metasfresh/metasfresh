-- 2018-04-28T10:26:30.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540344,541557,TO_TIMESTAMP('2018-04-28 10:26:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','notifications',30,TO_TIMESTAMP('2018-04-28 10:26:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T10:26:56.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58001,0,53282,541557,551691,'F',TO_TIMESTAMP('2018-04-28 10:26:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Benachrichtigungs Art',10,0,0,TO_TIMESTAMP('2018-04-28 10:26:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T10:27:14.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58006,0,53282,541557,551692,'F',TO_TIMESTAMP('2018-04-28 10:27:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Vorgesetzter',20,0,0,TO_TIMESTAMP('2018-04-28 10:27:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T10:27:28.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Teamleiter',Updated=TO_TIMESTAMP('2018-04-28 10:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551692
;

-- 2018-04-28T10:30:42.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545325
;

-- 2018-04-28T10:31:16.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545320
;

