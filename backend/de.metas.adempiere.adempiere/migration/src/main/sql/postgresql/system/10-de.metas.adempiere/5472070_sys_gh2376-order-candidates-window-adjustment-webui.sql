-- 2017-09-19T15:42:53.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540282,540490,TO_TIMESTAMP('2017-09-19 15:42:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-09-19 15:42:53','YYYY-MM-DD HH24:MI:SS'),100,'sub')
;

-- 2017-09-19T15:42:53.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540490 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-09-19T15:42:56.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET SeqNo=30,Updated=TO_TIMESTAMP('2017-09-19 15:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540407
;

-- 2017-09-19T15:43:07.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540658,540490,TO_TIMESTAMP('2017-09-19 15:43:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-19 15:43:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:43:08.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540659,540490,TO_TIMESTAMP('2017-09-19 15:43:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-09-19 15:43:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:48:06.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540658,541156,TO_TIMESTAMP('2017-09-19 15:48:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','read',10,TO_TIMESTAMP('2017-09-19 15:48:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:49:29.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555175,0,540282,541156,548675,TO_TIMESTAMP('2017-09-19 15:49:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produkt eff.',10,0,0,TO_TIMESTAMP('2017-09-19 15:49:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:49:45.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555179,0,540282,541156,548676,TO_TIMESTAMP('2017-09-19 15:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift eff.',20,0,0,TO_TIMESTAMP('2017-09-19 15:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:50:07.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554081,0,540282,541156,548677,TO_TIMESTAMP('2017-09-19 15:50:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner eff.',30,0,0,TO_TIMESTAMP('2017-09-19 15:50:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:50:29.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554082,0,540282,541156,548678,TO_TIMESTAMP('2017-09-19 15:50:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Standort eff.',40,0,0,TO_TIMESTAMP('2017-09-19 15:50:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:50:58.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556942,0,540282,541156,548679,TO_TIMESTAMP('2017-09-19 15:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferadresse eff.',50,0,0,TO_TIMESTAMP('2017-09-19 15:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:51:18.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556941,0,540282,541156,548680,TO_TIMESTAMP('2017-09-19 15:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferempfänger eff.',60,0,0,TO_TIMESTAMP('2017-09-19 15:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:51:35.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556946,0,540282,541156,548681,TO_TIMESTAMP('2017-09-19 15:51:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Übergabeadresse eff.',70,0,0,TO_TIMESTAMP('2017-09-19 15:51:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:51:49.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556944,0,540282,541156,548682,TO_TIMESTAMP('2017-09-19 15:51:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Übergabe an eff.',80,0,0,TO_TIMESTAMP('2017-09-19 15:51:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:52:04.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540659,541157,TO_TIMESTAMP('2017-09-19 15:52:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','write',10,TO_TIMESTAMP('2017-09-19 15:52:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:52:18.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555174,0,540282,541157,548683,TO_TIMESTAMP('2017-09-19 15:52:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produkt abw.',10,0,0,TO_TIMESTAMP('2017-09-19 15:52:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:52:35.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555178,0,540282,541157,548684,TO_TIMESTAMP('2017-09-19 15:52:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift abw.',20,0,0,TO_TIMESTAMP('2017-09-19 15:52:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:52:59.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554064,0,540282,541157,548685,TO_TIMESTAMP('2017-09-19 15:52:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartbner abw.',30,0,0,TO_TIMESTAMP('2017-09-19 15:52:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:53:19.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554065,0,540282,541157,548686,TO_TIMESTAMP('2017-09-19 15:53:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Standort abw.',40,0,0,TO_TIMESTAMP('2017-09-19 15:53:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:53:26.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Geschäftspartner abw.',Updated=TO_TIMESTAMP('2017-09-19 15:53:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548685
;

-- 2017-09-19T15:53:48.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556940,0,540282,541157,548687,TO_TIMESTAMP('2017-09-19 15:53:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferadresse abw.',50,0,0,TO_TIMESTAMP('2017-09-19 15:53:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:54:07.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556939,0,540282,541157,548688,TO_TIMESTAMP('2017-09-19 15:54:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferempfänger abw.',60,0,0,TO_TIMESTAMP('2017-09-19 15:54:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:55:10.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556945,0,540282,541157,548689,TO_TIMESTAMP('2017-09-19 15:55:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Übergabeadresse abw.',70,0,0,TO_TIMESTAMP('2017-09-19 15:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:55:33.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556943,0,540282,541157,548690,TO_TIMESTAMP('2017-09-19 15:55:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Übergabe an abw.',80,0,0,TO_TIMESTAMP('2017-09-19 15:55:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T15:58:13.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547204
;

-- 2017-09-19T15:58:13.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547206
;

-- 2017-09-19T15:58:39.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547219
;

-- 2017-09-19T15:58:49.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547220
;

-- 2017-09-19T15:59:03.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547227
;

-- 2017-09-19T15:59:04.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547228
;

-- 2017-09-19T15:59:04.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547229
;

-- 2017-09-19T15:59:04.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547230
;

-- 2017-09-19T15:59:20.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547248
;

-- 2017-09-19T15:59:20.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547249
;

-- 2017-09-19T15:59:20.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547250
;

-- 2017-09-19T15:59:20.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547251
;

-- 2017-09-19T16:07:22.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547268
;

-- 2017-09-19T16:07:22.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547279
;

-- 2017-09-19T16:07:32.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540182
;

-- 2017-09-19T16:07:36.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547266
;

-- 2017-09-19T16:16:08.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,549447,0,540282,541156,548691,TO_TIMESTAMP('2017-09-19 16:16:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lager',90,0,0,TO_TIMESTAMP('2017-09-19 16:16:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:16:21.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551069,0,540282,541156,548692,TO_TIMESTAMP('2017-09-19 16:16:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferart',100,0,0,TO_TIMESTAMP('2017-09-19 16:16:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:16:25.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548691
;

-- 2017-09-19T16:16:27.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548692
;

-- 2017-09-19T16:17:47.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547262
;

-- 2017-09-19T16:17:47.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547263
;

-- 2017-09-19T16:17:50.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540966
;

-- 2017-09-19T16:17:58.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547276
;

-- 2017-09-19T16:17:58.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547277
;

-- 2017-09-19T16:20:08.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540965, SeqNo=50,Updated=TO_TIMESTAMP('2017-09-19 16:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547275
;

-- 2017-09-19T16:20:13.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540965, SeqNo=60,Updated=TO_TIMESTAMP('2017-09-19 16:20:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547278
;

-- 2017-09-19T16:20:18.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540969
;

-- 2017-09-19T16:20:59.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540965, SeqNo=70,Updated=TO_TIMESTAMP('2017-09-19 16:20:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547274
;

-- 2017-09-19T16:34:24.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551070,0,540282,540962,548693,TO_TIMESTAMP('2017-09-19 16:34:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferung',520,0,0,TO_TIMESTAMP('2017-09-19 16:34:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:34:37.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547278
;

-- 2017-09-19T16:35:04.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551085,0,540282,540962,548694,TO_TIMESTAMP('2017-09-19 16:35:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preissystem',530,0,0,TO_TIMESTAMP('2017-09-19 16:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:35:19.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547273
;

-- 2017-09-19T16:36:05.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-19 16:36:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548693
;

-- 2017-09-19T16:36:07.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-19 16:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548694
;

-- 2017-09-19T16:36:37.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540659,541158,TO_TIMESTAMP('2017-09-19 16:36:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-09-19 16:36:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:36:50.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547060,0,540282,541158,548695,TO_TIMESTAMP('2017-09-19 16:36:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-09-19 16:36:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:37:01.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547055,0,540282,541158,548696,TO_TIMESTAMP('2017-09-19 16:37:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-09-19 16:37:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:37:15.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540658,541159,TO_TIMESTAMP('2017-09-19 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','ship',20,TO_TIMESTAMP('2017-09-19 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:37:38.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551069,0,540282,541159,548697,TO_TIMESTAMP('2017-09-19 16:37:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferart',10,0,0,TO_TIMESTAMP('2017-09-19 16:37:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:37:57.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,549447,0,540282,541159,548698,TO_TIMESTAMP('2017-09-19 16:37:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lager',20,0,0,TO_TIMESTAMP('2017-09-19 16:37:57','YYYY-MM-DD HH24:MI:SS'),100)
;

