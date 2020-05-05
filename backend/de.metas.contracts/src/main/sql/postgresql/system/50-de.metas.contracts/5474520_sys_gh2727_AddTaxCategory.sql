-- 2017-10-16T14:55:02.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557527,211,0,19,540320,'N','C_TaxCategory_ID',TO_TIMESTAMP('2017-10-16 14:55:02','YYYY-MM-DD HH24:MI:SS'),100,'N','100','Steuerkategorie','de.metas.contracts',10,'Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Steuerkategorie',0,0,TO_TIMESTAMP('2017-10-16 14:55:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-10-16T14:55:02.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557527 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-16T14:55:08.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN C_TaxCategory_ID NUMERIC(10) DEFAULT 100 NOT NULL')
;

-- 2017-10-16T14:55:08.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Flatrate_Term ADD CONSTRAINT CTaxCategory_CFlatrateTerm FOREIGN KEY (C_TaxCategory_ID) REFERENCES public.C_TaxCategory DEFERRABLE INITIALLY DEFERRED
;

-- 2017-10-16T14:59:16.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557527,560395,0,540340,0,TO_TIMESTAMP('2017-10-16 14:59:16','YYYY-MM-DD HH24:MI:SS'),100,'Steuerkategorie',0,'de.metas.contracts','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
',0,'Y','Y','Y','Y','N','N','N','N','N','Steuerkategorie',470,450,0,1,1,TO_TIMESTAMP('2017-10-16 14:59:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-16T14:59:16.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560395 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-16T15:00:06.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548130
;

-- 2017-10-16T15:00:06.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548131
;

-- 2017-10-16T15:00:06.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548126
;

-- 2017-10-16T15:00:06.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548415
;

-- 2017-10-16T15:00:06.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548127
;

-- 2017-10-16T15:00:06.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548128
;

-- 2017-10-16T15:00:06.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548132
;

-- 2017-10-16T15:00:06.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551055
;

-- 2017-10-16T15:00:06.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551054
;

-- 2017-10-16T15:00:06.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551508
;

-- 2017-10-16T15:00:06.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560395
;

-- 2017-10-16T15:00:06.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550552
;

-- 2017-10-16T15:00:06.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550473
;

-- 2017-10-16T15:00:06.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550551
;

-- 2017-10-16T15:00:06.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550550
;

-- 2017-10-16T15:00:06.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548492
;

-- 2017-10-16T15:00:06.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548493
;

-- 2017-10-16T15:00:06.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548491
;

-- 2017-10-16T15:00:06.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550480
;

-- 2017-10-16T15:00:06.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550481
;

-- 2017-10-16T15:00:06.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550482
;

-- 2017-10-16T15:00:06.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548223
;

-- 2017-10-16T15:00:06.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547798
;

-- 2017-10-16T15:00:06.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550474
;

-- 2017-10-16T15:00:06.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548428
;

-- 2017-10-16T15:00:06.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548414
;

-- 2017-10-16T15:00:06.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548162
;

-- 2017-10-16T15:00:06.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548168
;

-- 2017-10-16T15:00:06.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548161
;

-- 2017-10-16T15:00:06.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548601
;

-- 2017-10-16T15:00:06.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=310,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548437
;

-- 2017-10-16T15:00:06.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550569
;

-- 2017-10-16T15:00:06.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550570
;

-- 2017-10-16T15:00:06.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=340,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548458
;

-- 2017-10-16T15:00:06.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=350,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548464
;

-- 2017-10-16T15:00:06.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=360,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548461
;

-- 2017-10-16T15:00:06.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548494
;

-- 2017-10-16T15:00:06.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=380,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548460
;

-- 2017-10-16T15:00:06.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=390,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548462
;

-- 2017-10-16T15:00:06.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=400,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548465
;

-- 2017-10-16T15:00:06.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=410,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550529
;

-- 2017-10-16T15:00:06.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=420,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548459
;

-- 2017-10-16T15:00:06.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=430,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548463
;

-- 2017-10-16T15:00:06.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=440,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556868
;

-- 2017-10-16T15:00:06.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=450,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556855
;

-- 2017-10-16T15:00:06.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=460,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548134
;

-- 2017-10-16T15:00:06.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=470,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548135
;

-- 2017-10-16T15:00:06.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=480,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554230
;

-- 2017-10-16T15:00:06.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=490,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548137
;

-- 2017-10-16T15:00:06.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=500,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559885
;

-- 2017-10-16T15:00:06.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=510,Updated=TO_TIMESTAMP('2017-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559886
;

------------------------------


-- 2017-10-16T15:02:28.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557527,560396,0,540859,0,TO_TIMESTAMP('2017-10-16 15:02:28','YYYY-MM-DD HH24:MI:SS'),100,'Steuerkategorie',0,'de.metas.contracts','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
',0,'Y','Y','Y','Y','N','N','N','N','N','Steuerkategorie',460,450,0,1,1,TO_TIMESTAMP('2017-10-16 15:02:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-16T15:02:28.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560396 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-16T15:02:40.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559754
;

-- 2017-10-16T15:02:40.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559755
;

-- 2017-10-16T15:02:40.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559758
;

-- 2017-10-16T15:02:40.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559759
;

-- 2017-10-16T15:02:40.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559760
;

-- 2017-10-16T15:02:40.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559761
;

-- 2017-10-16T15:02:40.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559762
;

-- 2017-10-16T15:02:40.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559764
;

-- 2017-10-16T15:02:40.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559765
;

-- 2017-10-16T15:02:40.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559766
;

-- 2017-10-16T15:02:40.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560396
;

-- 2017-10-16T15:02:40.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559767
;

-- 2017-10-16T15:02:40.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559768
;

-- 2017-10-16T15:02:40.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559769
;

-- 2017-10-16T15:02:40.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559770
;

-- 2017-10-16T15:02:40.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559771
;

-- 2017-10-16T15:02:40.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559772
;

-- 2017-10-16T15:02:40.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559773
;

-- 2017-10-16T15:02:40.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559774
;

-- 2017-10-16T15:02:40.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559775
;

-- 2017-10-16T15:02:40.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559776
;

-- 2017-10-16T15:02:40.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559777
;

-- 2017-10-16T15:02:40.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559778
;

-- 2017-10-16T15:02:40.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559779
;

-- 2017-10-16T15:02:40.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560373
;

-- 2017-10-16T15:02:40.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559780
;

-- 2017-10-16T15:02:40.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559781
;

-- 2017-10-16T15:02:40.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559782
;

-- 2017-10-16T15:02:40.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559783
;

-- 2017-10-16T15:02:40.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559784
;

-- 2017-10-16T15:02:40.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=310,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559785
;

-- 2017-10-16T15:02:40.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559786
;

-- 2017-10-16T15:02:40.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559787
;

-- 2017-10-16T15:02:40.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=340,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559788
;

-- 2017-10-16T15:02:40.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=350,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559789
;

-- 2017-10-16T15:02:40.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=360,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559790
;

-- 2017-10-16T15:02:40.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559791
;

-- 2017-10-16T15:02:40.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=380,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559792
;

-- 2017-10-16T15:02:40.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=390,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559793
;

-- 2017-10-16T15:02:40.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=400,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559794
;

-- 2017-10-16T15:02:40.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=410,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559795
;

-- 2017-10-16T15:02:40.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=420,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559796
;

-- 2017-10-16T15:02:40.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=430,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559797
;

-- 2017-10-16T15:02:40.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=440,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559798
;

-- 2017-10-16T15:02:40.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=450,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559799
;

-- 2017-10-16T15:02:40.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=460,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559800
;

-- 2017-10-16T15:02:40.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=470,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559801
;

-- 2017-10-16T15:02:40.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=480,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559802
;

-- 2017-10-16T15:02:40.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=490,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559803
;

-- 2017-10-16T15:02:40.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=500,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559804
;

-- 2017-10-16T15:02:40.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=510,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559888
;

-- 2017-10-16T15:02:40.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=520,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559887
;

-- 2017-10-16T15:02:40.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=530,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559945
;

-- 2017-10-16T15:02:40.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=540,Updated=TO_TIMESTAMP('2017-10-16 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559946
;

-- 2017-10-16T15:04:07.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560396,0,540859,549098,541104,'F',TO_TIMESTAMP('2017-10-16 15:04:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Steuerkategorie',100,0,0,TO_TIMESTAMP('2017-10-16 15:04:07','YYYY-MM-DD HH24:MI:SS'),100)
;

