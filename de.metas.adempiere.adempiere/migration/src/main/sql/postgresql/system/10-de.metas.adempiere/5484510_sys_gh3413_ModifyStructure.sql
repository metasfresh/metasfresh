-- 2018-02-01T18:37:47.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=1581, ColumnName='DateFrom', Description='Startdatum eines Abschnittes', Help='Datum von bezeichnet das Startdatum eines Abschnittes', Name='Datum von',Updated=TO_TIMESTAMP('2018-02-01 18:37:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558979
;

-- 2018-02-01T18:37:47.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Datum von', Description='Startdatum eines Abschnittes', Help='Datum von bezeichnet das Startdatum eines Abschnittes' WHERE AD_Column_ID=558979
;

-- 2018-02-01T18:38:06.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558989,1582,0,15,540929,'N','DateTo',TO_TIMESTAMP('2018-02-01 18:38:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Enddatum eines Abschnittes','D',7,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Datum bis',0,0,TO_TIMESTAMP('2018-02-01 18:38:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-01T18:38:06.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558989 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-01T18:38:09.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_CreditLimit','ALTER TABLE public.C_BPartner_CreditLimit ADD COLUMN DateTo TIMESTAMP WITHOUT TIME ZONE')
;


/* DDL */ SELECT public.db_alter_table('C_BPartner_CreditLimit','ALTER TABLE public.C_BPartner_CreditLimit RENAME COLUMN DateGeneral to DateFrom')
;


-- 2018-02-05T15:01:44.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558992,193,0,19,540929,'N','C_Currency_ID',TO_TIMESTAMP('2018-02-05 15:01:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Währung für diesen Eintrag','D',10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Währung',0,0,TO_TIMESTAMP('2018-02-05 15:01:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-05T15:01:44.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558992 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-05T15:03:15.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT C_Currency_ID from C_AcctSchema where C_AcctSchema_ID = getC_AcctSchema_ID(C_BPartner_CreditLimit.AD_Client_ID,C_BPartner_CreditLimit.AD_Org_ID))', IsUpdateable='N',Updated=TO_TIMESTAMP('2018-02-05 15:03:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558992
;

-- 2018-02-05T15:03:50.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,558992,561986,0,540994,0,TO_TIMESTAMP('2018-02-05 15:03:49','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',0,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','N','N','Währung',60,10,0,1,1,TO_TIMESTAMP('2018-02-05 15:03:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-05T15:03:50.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561986 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-05T15:03:50.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(193,NULL) 
;

-- 2018-02-05T15:04:23.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,561986,0,540994,550562,541427,'F',TO_TIMESTAMP('2018-02-05 15:04:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Währung',25,0,0,TO_TIMESTAMP('2018-02-05 15:04:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-05T15:04:33.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-02-05 15:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550562
;

-- 2018-02-05T15:04:33.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-02-05 15:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550546
;

-- 2018-02-05T15:04:33.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-02-05 15:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550547
;

-- 2018-02-05T15:05:04.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,558989,561987,0,540994,0,TO_TIMESTAMP('2018-02-05 15:05:04','YYYY-MM-DD HH24:MI:SS'),100,'Enddatum eines Abschnittes',0,'D','Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)',0,'Y','Y','Y','N','N','N','N','N','Datum bis',70,20,0,1,1,TO_TIMESTAMP('2018-02-05 15:05:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-05T15:05:04.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-05T15:05:04.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1582,NULL) 
;

-- 2018-02-05T15:05:38.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,561987,0,540994,550563,541427,'F',TO_TIMESTAMP('2018-02-05 15:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Datum bis',35,0,0,TO_TIMESTAMP('2018-02-05 15:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-05T15:05:44.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-02-05 15:05:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550563
;

-- 2018-02-05T15:05:44.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-02-05 15:05:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550547
;

-- 2018-02-05T15:06:13.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Datum von',Updated=TO_TIMESTAMP('2018-02-05 15:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550546
;



-- 2018-02-05T16:25:19.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FormatPattern='',Updated=TO_TIMESTAMP('2018-02-05 16:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;


-- 2018-02-05T16:28:58.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='coalesce(select round((s.SO_CreditUsed*100) /cl.Amount,3) from C_BPartner_Stats s join C_BPartner_CreditLimit cl on (s.C_BPartner_ID = cl.C_Bpartner_ID and s.C_Bpartner_ID = C_BPartner.C_BPartner_ID) order by Type limit 1),0)',Updated=TO_TIMESTAMP('2018-02-05 16:28:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- 2018-02-05T16:30:08.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='select round((s.SO_CreditUsed*100) /cl.Amount,3) from C_BPartner_Stats s join C_BPartner_CreditLimit cl on (s.C_BPartner_ID = cl.C_Bpartner_ID and s.C_Bpartner_ID = C_BPartner.C_BPartner_ID) order by Type limit 1)',Updated=TO_TIMESTAMP('2018-02-05 16:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- 2018-02-05T16:30:44.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select round((s.SO_CreditUsed*100) /cl.Amount,3) from C_BPartner_Stats s join C_BPartner_CreditLimit cl on (s.C_BPartner_ID = cl.C_Bpartner_ID and s.C_Bpartner_ID = C_BPartner.C_BPartner_ID) order by Type limit 1)',Updated=TO_TIMESTAMP('2018-02-05 16:30:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- 2018-02-05T16:32:51.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(coalesce((select round((s.SO_CreditUsed*100) /cl.Amount,3) from C_BPartner_Stats s  join C_BPartner_CreditLimit cl on (s.C_BPartner_ID = cl.C_Bpartner_ID and s.C_Bpartner_ID = C_BPartner.C_BPartner_ID) order by Type limit 1),0))',Updated=TO_TIMESTAMP('2018-02-05 16:32:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- 2018-02-05T17:00:22.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2018-02-05 17:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540929
;


/* DDL */ SELECT public.db_alter_table('C_BPartner_CreditLimit','ALTER TABLE public.C_BPartner_CreditLimit DROP CONSTRAINT creditlimit_uqtypebp')
;


/* DDL */ SELECT public.db_alter_table('C_BPartner_CreditLimit','ALTER TABLE public.C_BPartner_CreditLimit ADD CONSTRAINT creditlimit_uqtype_datebp UNIQUE (C_BPartner_ID, Type, DateTo, DateFrom)')
;


CREATE SEQUENCE IF NOT EXISTS public.c_bpartner_creditlimit_seq
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 2147483647
  START 1000000
  CACHE 1;
