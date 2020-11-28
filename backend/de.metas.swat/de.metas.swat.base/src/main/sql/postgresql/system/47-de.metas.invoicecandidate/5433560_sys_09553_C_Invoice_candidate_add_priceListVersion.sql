-- 17.11.2015 08:02
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552844,450,0,30,540270,'N','M_PriceList_Version_ID',TO_TIMESTAMP('2015-11-17 08:02:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet eine einzelne Version der Preisliste','de.metas.invoicecandidate',10,'Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Version Preisliste',0,TO_TIMESTAMP('2015-11-17 08:02:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 17.11.2015 08:02
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552844 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 17.11.2015 08:05
-- URL zum Konzept
UPDATE AD_Tab SET IsCheckParentsChanged='N',Updated=TO_TIMESTAMP('2015-11-17 08:05:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540279
;

-- 17.11.2015 08:06
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=142, SeqNoGrid=42,Updated=TO_TIMESTAMP('2015-11-17 08:06:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555636
;

-- 17.11.2015 08:07
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552844,556415,0,540279,0,TO_TIMESTAMP('2015-11-17 08:07:44','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste',0,'de.metas.invoicecandidate','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ',0,'Y','Y','Y','Y','N','N','N','Y','Y','Version Preisliste',172,322,0,TO_TIMESTAMP('2015-11-17 08:07:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.11.2015 08:07
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556415 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- make sure that if the plv is set, then different PLVs won't end up in the same invoice
-- 17.11.2015 08:09
-- URL zum Konzept
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (0,552844,0,540003,540022,TO_TIMESTAMP('2015-11-17 08:09:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2015-11-17 08:09:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- for the time beeing, only show if set
-- 17.11.2015 10:05
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@M_PriceList_Version_ID/0@!0',Updated=TO_TIMESTAMP('2015-11-17 10:05:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556415
;



COMMIT;


-- 17.11.2015 08:02
-- URL zum Konzept
ALTER TABLE C_Invoice_Candidate ADD M_PriceList_Version_ID NUMERIC(10) DEFAULT NULL 
;
