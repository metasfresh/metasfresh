
--
-- DDL
--
ALTER TABLE ad_table ADD COLUMN IsDLM character(1) NOT NULL DEFAULT 'N'::bpchar;

--
-- DML
-- ...also assigne the ad-to-dlm process to AD_Table
--

-- 19.10.2016 12:38
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='', Description='Die Tabelle, die für DLM eingerichtet werden soll', Help='', IsMandatory='Y',Updated=TO_TIMESTAMP('2016-10-19 12:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541038
;

-- 19.10.2016 12:38
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541038
;

-- 19.10.2016 12:49
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543199,0,'IsDLM',TO_TIMESTAMP('2016-10-19 12:49:24','YYYY-MM-DD HH24:MI:SS'),100,'Die Datensätze einer Tabelle mit aktiviertem DLM können vom System unterschiedlichen DLM-Levels zugeordnet werden','de.metas.dlm','Y','DLM aktiviert','DLM aktiviert',TO_TIMESTAMP('2016-10-19 12:49:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 12:49
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543199 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 19.10.2016 12:50
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,EntityType,Name,IsAdvancedText,AD_Table_ID,IsLazyLoading,IsCalculated,AD_Org_ID,AllowZoomTo,ColumnName,IsGenericZoomOrigin,IsGenericZoomKeyColumn,AD_Column_ID,Description,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-19 12:50:03','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-19 12:50:03','YYYY-MM-DD HH24:MI:SS'),100,543199,'Y','N','N','N','N','N','Y','N','de.metas.dlm','DLM aktiviert','N',100,'N','N',0,'N','IsDLM','N','N',555158,'Die Datensätze einer Tabelle mit aktiviertem DLM können vom System unterschiedlichen DLM-Levels zugeordnet werden','N','Y','N','N')
;

-- 19.10.2016 12:50
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555158 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 19.10.2016 12:52
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555158,557337,0,100,0,TO_TIMESTAMP('2016-10-19 12:52:14','YYYY-MM-DD HH24:MI:SS'),100,'Die Datensätze einer Tabelle mit aktiviertem DLM können vom System unterschiedlichen DLM-Levels zugeordnet werden',0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','N','N','DLM aktiviert',215,215,0,1,1,TO_TIMESTAMP('2016-10-19 12:52:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 12:52
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557337 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.10.2016 12:52
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=205, SeqNoGrid=205,Updated=TO_TIMESTAMP('2016-10-19 12:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557337
;

-- 19.10.2016 12:52
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-10-19 12:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557337
;

-- 19.10.2016 12:55
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540729,100,TO_TIMESTAMP('2016-10-19 12:55:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-10-19 12:55:23','YYYY-MM-DD HH24:MI:SS'),100)
;

