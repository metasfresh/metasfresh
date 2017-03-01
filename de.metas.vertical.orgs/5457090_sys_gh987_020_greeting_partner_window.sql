
-- 23.02.2017 11:56
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543279,0,'contactgreeting',TO_TIMESTAMP('2017-02-23 11:56:25','YYYY-MM-DD HH24:MI:SS'),100,'Für Briefe - z.B. "Sehr geehrter {0}" oder "Sehr geehrter Herr {0}" - Zur Laufzeit wird  "{0}" durch den Namen ersetzt','de.metas.eoss.verband','Anrede definiert, was auf einem Brief an einen Geschäftspartner gedruckt wird.','Y','Anrede Kontakt','Anrede',TO_TIMESTAMP('2017-02-23 11:56:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.02.2017 11:56
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543279 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 23.02.2017 11:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556288,543279,0,30,291,'N','contactgreeting','(SELECT MAX(u.C_Greeting_ID) FROM AD_User u WHERE u.C_BPartner_ID=C_BPartner.C_BPartner_ID AND u.IsActive=''Y'' AND u.IsDefaultContact=''Y'')',TO_TIMESTAMP('2017-02-23 11:57:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Für Briefe - z.B. "Sehr geehrter {0}" oder "Sehr geehrter Herr {0}" - Zur Laufzeit wird  "{0}" durch den Namen ersetzt','de.metas.eoss.verband',10,'Anrede definiert, was auf einem Brief an einen Geschäftspartner gedruckt wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Anrede Kontakt',0,TO_TIMESTAMP('2017-02-23 11:57:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 23.02.2017 11:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556288 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 23.02.2017 11:58
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556288,557839,0,220,0,TO_TIMESTAMP('2017-02-23 11:58:09','YYYY-MM-DD HH24:MI:SS'),100,'Für Briefe - z.B. "Sehr geehrter {0}" oder "Sehr geehrter Herr {0}" - Zur Laufzeit wird  "{0}" durch den Namen ersetzt',0,'de.metas.eoss.verband','Anrede definiert, was auf einem Brief an einen Geschäftspartner gedruckt wird.',0,'Y','Y','N','N','N','N','N','N','N','Anrede Kontakt',270,300,0,1,1,TO_TIMESTAMP('2017-02-23 11:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.02.2017 11:58
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557839 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 23.02.2017 11:58
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557839,0,220,1000010,541806,TO_TIMESTAMP('2017-02-23 11:58:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','a',60,0,0,TO_TIMESTAMP('2017-02-23 11:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.02.2017 11:58
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Anrede',Updated=TO_TIMESTAMP('2017-02-23 11:58:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541806
;

-- 23.02.2017 11:59
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-02-23 11:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541806
;

-- 23.02.2017 11:59
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-02-23 11:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000072
;

-- 23.02.2017 11:59
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-02-23 11:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000076
;

-- 23.02.2017 12:02
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=356,Updated=TO_TIMESTAMP('2017-02-23 12:02:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556288
;
