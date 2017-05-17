-- 2017-04-11T11:38:01.935
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-04-11 11:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556480
;

-- 2017-04-11T11:38:19.444
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2017-04-11 11:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556480
;
-- 2017-04-11T11:38:51.084
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556482,459,0,30,540808,'N','M_Warehouse_ID',TO_TIMESTAMP('2017-04-11 11:38:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Lager oder Ort für Dienstleistung','de.metas.material.dispo',10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Lager',0,TO_TIMESTAMP('2017-04-11 11:38:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-04-11T11:38:51.090
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556482 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 2017-04-11T11:39:04.550
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2017-04-11 11:39:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556480
;


-- 2017-04-11T11:38:23.346
-- URL zum Konzept
INSERT INTO t_alter_column values('md_candidate','M_Locator_ID','NUMERIC(10)',null,null)
;

-- 2017-04-11T11:38:23.361
-- URL zum Konzept
INSERT INTO t_alter_column values('md_candidate','M_Locator_ID',null,'NULL',null)
;



-- 2017-04-11T11:38:54.233
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('md_candidate','ALTER TABLE public.MD_Candidate ADD COLUMN M_Warehouse_ID NUMERIC(10) NOT NULL')
;

-- 2017-04-11T11:38:54.300
-- URL zum Konzept
ALTER TABLE MD_Candidate ADD CONSTRAINT MWarehouse_MDCandidate FOREIGN KEY (M_Warehouse_ID) REFERENCES M_Warehouse DEFERRABLE INITIALLY DEFERRED
;
