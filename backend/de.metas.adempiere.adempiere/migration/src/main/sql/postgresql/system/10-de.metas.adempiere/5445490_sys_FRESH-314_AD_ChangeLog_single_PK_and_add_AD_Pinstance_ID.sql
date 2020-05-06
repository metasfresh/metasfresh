
--
-- make sure each AD_Changelog record has a unique ID
--
create or replace temp view ad_changelog_ids_to_rekey AS
select ad_changelog_id , ad_session_id , ad_table_id , ad_column_id from ad_changelog c
where 
	exists (
	select 1 from ad_changelog c2 where c.ad_changelog_id=c2.ad_changelog_id AND 
		(c.ad_session_id<c2.ad_session_id OR c.ad_table_id<c2.ad_table_id OR c.ad_column_id<c2.ad_column_id)
	)
;
--select * from ad_changelog_ids_to_rekey
UPDATE ad_changelog
SET ad_changelog_id= nextval('ad_changelog_seq')
WHERE (ad_changelog_id , ad_session_id , ad_table_id , ad_column_id) IN (select * from ad_changelog_ids_to_rekey);

--
-- make AD_Session_ID non-mandatory (need to removed if from the PK first)
--
ALTER TABLE ad_changelog DROP CONSTRAINT ad_changelog_pkey;
ALTER TABLE ad_changelog
  ADD CONSTRAINT ad_changelog_pkey PRIMARY KEY(ad_changelog_id);

-- 17.05.2016 15:27
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-05-17 15:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8813
;


--
-- set AD_Session_ID to search
--
-- 17.05.2016 15:28
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, IsUpdateable='N',Updated=TO_TIMESTAMP('2016-05-17 15:28:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8813
;

--
-- Add AD_Pinstance_ID
--
-- 17.05.2016 15:29
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554396,114,0,30,580,'N','AD_PInstance_ID',TO_TIMESTAMP('2016-05-17 15:29:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Instanz eines Prozesses','D',10,'Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Prozess-Instanz',0,TO_TIMESTAMP('2016-05-17 15:29:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 17.05.2016 15:29
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554396 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 17.05.2016 15:57
-- URL zum Konzept
UPDATE AD_Column SET IsKey='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-05-17 15:57:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8811
;


-- 17.05.2016 16:10
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554396,556933,0,488,0,TO_TIMESTAMP('2016-05-17 16:10:29','YYYY-MM-DD HH24:MI:SS'),100,'Instanz eines Prozesses',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Prozess-Instanz',35,35,0,1,1,TO_TIMESTAMP('2016-05-17 16:10:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.05.2016 16:10
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556933 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 17.05.2016 16:10
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-05-17 16:10:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556933
;

COMMIT;

-- DDL

-- 17.05.2016 15:27
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_changelog','AD_Session_ID','NUMERIC(10)',null,'NULL')
;
-- 17.05.2016 15:27
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_changelog','AD_Session_ID',null,'NULL',null)
;

-- 17.05.2016 15:57
-- URL zum Konzept
ALTER TABLE AD_ChangeLog ADD AD_PInstance_ID NUMERIC(10) DEFAULT NULL 
;
