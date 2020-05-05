-- 16.12.2015 16:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAlwaysUpdateable='N',Updated=TO_TIMESTAMP('2015-12-16 16:11:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552916
;

-- 16.12.2015 16:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2015-12-16 16:11:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552910
;

-- 16.12.2015 16:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552943,223,0,19,540692,'N','C_Year_ID',TO_TIMESTAMP('2015-12-16 16:11:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Kalenderjahr','de.metas.materialtracking.ch.lagerkonf',10,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Jahr',0,TO_TIMESTAMP('2015-12-16 16:11:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 16.12.2015 16:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552943 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 16.12.2015 16:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--ALTER TABLE M_Material_Tracking_Report ADD C_Year_ID NUMERIC(10) NOT NULL
;

-- 16.12.2015 16:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2015-12-16 16:12:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552943
;

-- 16.12.2015 16:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Material_Tracking_Report ADD C_Year_ID NUMERIC(10) DEFAULT NULL 
;


 -- update existing values 
UPDATE M_Material_Tracking_Report 
SET C_Year_ID = ( select C_Year_ID from C_Period where C_Period_ID = M_Material_Tracking_Report.C_Period_ID)
WHERE C_Year_ID IS NULL;



-- 16.12.2015 16:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2015-12-16 16:15:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552943
;
commit;
-- 16.12.2015 16:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_material_tracking_report','C_Year_ID','NUMERIC(10)',null,null)
;
commit;
-- 16.12.2015 16:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_material_tracking_report','C_Year_ID',null,'NOT NULL',null)
;
commit;
-- 16.12.2015 16:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=199,Updated=TO_TIMESTAMP('2015-12-16 16:17:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552915
;


