
-- 15.02.2016 11:54
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542978,0,'EdiDESADVDefaultItemCapacity',TO_TIMESTAMP('2016-02-15 11:54:59','YYYY-MM-DD HH24:MI:SS'),100,'"CU pro TU"-Wert, den das System in ein DESADV-Dokument übergeben soll, wenn das Gebinde in metasfresh keine Gebindekapazität hinterlegt hat.','de.metas.esb.edi','Y','"CU pro TU" bei unbestimmter Verpackungskapazität','"CU pro TU" bei unbestimmter Verpackungskapazität',TO_TIMESTAMP('2016-02-15 11:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.02.2016 11:54
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542978 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 15.02.2016 11:56
-- URL zum Konzept
UPDATE AD_Element SET Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn das Gebinde in metasfresh keine Gebindekapazität hinterlegt hat.',Updated=TO_TIMESTAMP('2016-02-15 11:56:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542978
;

-- 15.02.2016 11:56
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542978
;

-- 15.02.2016 11:56
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='EdiDESADVDefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn das Gebinde in metasfresh keine Gebindekapazität hinterlegt hat.', Help=NULL WHERE AD_Element_ID=542978
;

-- 15.02.2016 11:56
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EdiDESADVDefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn das Gebinde in metasfresh keine Gebindekapazität hinterlegt hat.', Help=NULL, AD_Element_ID=542978 WHERE UPPER(ColumnName)='EDIDESADVDEFAULTITEMCAPACITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 15.02.2016 11:56
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EdiDESADVDefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn das Gebinde in metasfresh keine Gebindekapazität hinterlegt hat.', Help=NULL WHERE AD_Element_ID=542978 AND IsCentrallyMaintained='Y'
;

-- 15.02.2016 11:56
-- URL zum Konzept
UPDATE AD_Field SET Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn das Gebinde in metasfresh keine Gebindekapazität hinterlegt hat.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542978) AND IsCentrallyMaintained='Y'
;

-- 15.02.2016 11:57
-- URL zum Konzept
UPDATE AD_Element SET Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.',Updated=TO_TIMESTAMP('2016-02-15 11:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542978
;

-- 15.02.2016 11:57
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542978
;

-- 15.02.2016 11:57
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='EdiDESADVDefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL WHERE AD_Element_ID=542978
;

-- 15.02.2016 11:57
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EdiDESADVDefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL, AD_Element_ID=542978 WHERE UPPER(ColumnName)='EDIDESADVDEFAULTITEMCAPACITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 15.02.2016 11:57
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EdiDESADVDefaultItemCapacity', Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL WHERE AD_Element_ID=542978 AND IsCentrallyMaintained='Y'
;

-- 15.02.2016 11:57
-- URL zum Konzept
UPDATE AD_Field SET Name='"CU pro TU" bei unbestimmter Verpackungskapazität', Description='"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542978) AND IsCentrallyMaintained='Y'
;

-- 15.02.2016 12:31
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,553173,542978,0,29,291,'N','EdiDESADVDefaultItemCapacity',TO_TIMESTAMP('2016-02-15 12:31:32','YYYY-MM-DD HH24:MI:SS'),100,'N','"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.','de.metas.esb.edi',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','"CU pro TU" bei unbestimmter Verpackungskapazität',0,TO_TIMESTAMP('2016-02-15 12:31:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 15.02.2016 12:31
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=553173 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 15.02.2016 12:32
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-02-15 12:32:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553178
;

-- 15.02.2016 12:33
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,553173,556622,0,220,0,TO_TIMESTAMP('2016-02-15 12:33:11','YYYY-MM-DD HH24:MI:SS'),100,'"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.',0,'de.metas.esb.edi',0,'Y','Y','Y','Y','N','N','N','N','Y','"CU pro TU" bei unbestimmter Verpackungskapazität',144,134,0,1,1,TO_TIMESTAMP('2016-02-15 12:33:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.02.2016 12:33
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556622 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- 16.02.2016 07:14
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsEdiRecipient@=Y',Updated=TO_TIMESTAMP('2016-02-16 07:14:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556622
;

-- 16.02.2016 09:01
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='1',Updated=TO_TIMESTAMP('2016-02-16 09:01:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=553173
;


COMMIT;


-- 15.02.2016 12:31
-- URL zum Konzept
ALTER TABLE C_BPartner ADD EdiDESADVDefaultItemCapacity NUMERIC DEFAULT 1 
;


