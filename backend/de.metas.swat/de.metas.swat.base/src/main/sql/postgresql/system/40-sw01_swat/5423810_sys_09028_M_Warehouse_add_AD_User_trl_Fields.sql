-- 14.08.2015 05:11
-- URL zum Konzept
UPDATE AD_Element SET Name='Beanstandungslager', PrintName='Beanstandungslager',Updated=TO_TIMESTAMP('2015-08-14 05:11:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542406
;

-- 14.08.2015 05:11
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542406
;

-- 14.08.2015 05:11
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsIssueWarehouse', Name='Beanstandungslager', Description=NULL, Help=NULL WHERE AD_Element_ID=542406
;

-- 14.08.2015 05:11
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsIssueWarehouse', Name='Beanstandungslager', Description=NULL, Help=NULL, AD_Element_ID=542406 WHERE UPPER(ColumnName)='ISISSUEWAREHOUSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 14.08.2015 05:11
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsIssueWarehouse', Name='Beanstandungslager', Description=NULL, Help=NULL WHERE AD_Element_ID=542406 AND IsCentrallyMaintained='Y'
;

-- 14.08.2015 05:11
-- URL zum Konzept
UPDATE AD_Field SET Name='Beanstandungslager', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542406) AND IsCentrallyMaintained='Y'
;

-- 14.08.2015 05:11
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Beanstandungslager', Name='Beanstandungslager' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542406)
;

-- 14.08.2015 05:13
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2015-08-14 05:13:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53294
;

-- 14.08.2015 05:14
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552686,138,0,30,190,'N','AD_User_ID',TO_TIMESTAMP('2015-08-14 05:14:07','YYYY-MM-DD HH24:MI:SS'),100,'N','User within the system - Internal or Business Partner Contact','D',10,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ansprechpartner',0,TO_TIMESTAMP('2015-08-14 05:14:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 14.08.2015 05:14
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552686 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 14.08.2015 05:14
-- URL zum Konzept
ALTER TABLE M_Warehouse ADD AD_User_ID NUMERIC(10) DEFAULT NULL 
;

-- 14.08.2015 05:15
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=19, AD_Val_Rule_ID=164,Updated=TO_TIMESTAMP('2015-08-14 05:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552686
;

-- 14.08.2015 05:15
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-08-14 05:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552686
;

-- 14.08.2015 05:15
-- URL zum Konzept
INSERT INTO t_alter_column values('m_warehouse','AD_User_ID','NUMERIC(10)',null,'NULL')
;

-- 14.08.2015 05:16
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Transitlager',Updated=TO_TIMESTAMP('2015-08-14 05:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54334
;

-- 14.08.2015 05:16
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=54334
;

-- 14.08.2015 05:18
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552686,556263,0,177,0,TO_TIMESTAMP('2015-08-14 05:18:43','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',0,'de.metas.swat','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','Y','N','N','N','N','Y','Ansprechpartner',115,105,0,TO_TIMESTAMP('2015-08-14 05:18:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.08.2015 05:18
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556263 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;


------
------
ALTER TABLE M_Warehouse DROP CONSTRAINT IF EXISTS ADUser_MWarehouse;
ALTER TABLE M_Warehouse ADD CONSTRAINT ADUser_MWarehouse FOREIGN KEY (AD_User_ID) REFERENCES AD_User DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Warehouse DROP CONSTRAINT IF EXISTS PPPlant_MWarehouse;
ALTER TABLE M_Warehouse ADD CONSTRAINT PPPlant_MWarehouse FOREIGN KEY (PP_Plant_ID) REFERENCES S_Resource DEFERRABLE INITIALLY DEFERRED;
