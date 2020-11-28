
-- Add field

-- cleanup in case the field was added ad-hoc which is the case on some DBs
DELETE FROM AD_Field WHERE AD_Column_ID=556076 /*IsDefault*/ AND  AD_Tab_ID=192 /*ProductPrice*/;

-- 2017-03-23T10:53:03.493
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556076,557976,0,192,0,TO_TIMESTAMP('2017-03-23 10:53:03','YYYY-MM-DD HH24:MI:SS'),100,'Wenn das System keinen Preis mit passenden Attributen oder Packvorschrift findet, kann es auf einen Standardpreis für das jeweilige Produkt zurückgreifen.
Beim Importieren von Auftragsdispo-Datensätzen sowie bei der Auftrag-Schnellerfassung können außerdem die im Standardpreis hinterlegten Packvorschriften und Attribute in die jeweilige Position übernommen werden.',0,'org.adempiere.pricing',0,'Y','N','Y','Y','N','N','N','N','N','Standard-Preisdatensatz',95,75,0,1,1,TO_TIMESTAMP('2017-03-23 10:53:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-23T10:53:03.500
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557976 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--------------------

-- Migrate
/*
drop table if exists M_ProductPrice_IsDefault_ToUpdate;
create temporary table M_ProductPrice_IsDefault_ToUpdate as
select
    t.M_ProductPrice_ID
    , pa.IsDefault
from backup.TMP_M_ProductPrice_New t
inner join backup.M_ProductPrice_Attribute_BKP pa on (pa.M_ProductPrice_Attribute_ID=t.Old_ProductPrice_Attribute_ID)
;

--
create index on M_ProductPrice_IsDefault_ToUpdate(M_ProductPrice_ID);

-- Check it
select * from M_ProductPrice_IsDefault_ToUpdate;
*/
--
-- Update
/*
update M_ProductPrice pp set IsDefault='Y'
from M_ProductPrice_IsDefault_ToUpdate t
where pp.M_ProductPrice_ID=t.M_ProductPrice_ID
;
*/

