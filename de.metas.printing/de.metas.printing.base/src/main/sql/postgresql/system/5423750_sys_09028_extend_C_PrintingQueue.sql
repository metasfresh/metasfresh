
-- 12.08.2015 15:04
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542878,0,'AD_User_Printout_ID',TO_TIMESTAMP('2015-08-12 15:04:30','YYYY-MM-DD HH24:MI:SS'),100,NULL,'de.metas.printing','Y','Ausdruck für','Ausdruck für',TO_TIMESTAMP('2015-08-12 15:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.08.2015 15:04
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542878 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='AD_User_Printer_ID', Name='Ausdruck durch', PrintName='Ausdruck durch',Updated=TO_TIMESTAMP('2015-08-12 15:05:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542878
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542878
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_Printer_ID', Name='Ausdruck durch', Description=NULL, Help=NULL, AD_Element_ID=542878 WHERE UPPER(ColumnName)='AD_USER_PRINTER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_Printer_ID', Name='Ausdruck durch', Description=NULL, Help=NULL WHERE AD_Element_ID=542878 AND IsCentrallyMaintained='Y'
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_Field SET Name='Ausdruck durch', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542878) AND IsCentrallyMaintained='Y'
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Ausdruck durch', Name='Ausdruck durch' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542878)
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='AD_User_Print_ID',Updated=TO_TIMESTAMP('2015-08-12 15:05:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542878
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_User_Print_ID', Name='Ausdruck durch', Description=NULL, Help=NULL WHERE AD_Element_ID=542878
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_Print_ID', Name='Ausdruck durch', Description=NULL, Help=NULL, AD_Element_ID=542878 WHERE UPPER(ColumnName)='AD_USER_PRINT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 12.08.2015 15:05
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_Print_ID', Name='Ausdruck durch', Description=NULL, Help=NULL WHERE AD_Element_ID=542878 AND IsCentrallyMaintained='Y'
;

-- 12.08.2015 15:06
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552673,542878,0,30,110,540435,'N','AD_User_Print_ID',TO_TIMESTAMP('2015-08-12 15:06:01','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.printing',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ausdruck durch',0,TO_TIMESTAMP('2015-08-12 15:06:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 15:06
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552673 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 15:15
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=556260
;

-- 12.08.2015 15:15
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=556260
;

-- 12.08.2015 15:15
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552673,556261,0,540460,0,TO_TIMESTAMP('2015-08-12 15:15:46','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.printing',0,'Y','Y','Y','Y','N','N','N','N','Y','Ausdruck durch',62,62,0,TO_TIMESTAMP('2015-08-12 15:15:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.08.2015 15:15
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556261 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;


--DDL
-- 12.08.2015 15:06
-- URL zum Konzept
--don'T do it, the AD_Column is deleted in the next SQL script of this task
--ALTER TABLE C_Printing_Queue ADD AD_User_Print_ID NUMERIC(10) DEFAULT NULL 
--;

--UPDATE C_Printing_Queue SEt AD_User_Print_ID=AD_User_ID WHERE AD_User_Print_ID IS NULL;
