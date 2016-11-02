--
-- DDL for creating a single PK for AD_PInstance_Para
--
-- 26.10.2016 21:39
-- URL zum Konzept
ALTER TABLE AD_PInstance_Para ADD COLUMN AD_PInstance_Para_ID numeric(10,0) NOT NULL DEFAULT nextval('ad_pinstance_para_seq')
;

-- 26.10.2016 21:40
-- URL zum Konzept
ALTER TABLE AD_PInstance_Para DROP CONSTRAINT IF EXISTS ad_pinstance_para_pkey
;

-- 26.10.2016 21:40
-- URL zum Konzept
ALTER TABLE AD_PInstance_Para ADD CONSTRAINT ad_pinstance_para_pkey PRIMARY KEY (AD_PInstance_Para_ID)
;

--
-- DDL for creating a single PK for AD_PInstance_Log
--
-- 27.10.2016 07:07
-- URL zum Konzept
ALTER TABLE AD_PInstance_Log ADD COLUMN AD_PInstance_Log_ID numeric(10,0) NOT NULL DEFAULT nextval('ad_pinstance_log_seq')
;

-- 27.10.2016 07:09
-- URL zum Konzept
ALTER TABLE AD_PInstance_Log DROP CONSTRAINT IF EXISTS ad_pinstance_log_pkey
;

-- 27.10.2016 07:09
-- URL zum Konzept
ALTER TABLE AD_PInstance_Log ADD CONSTRAINT ad_pinstance_log_pkey PRIMARY KEY (AD_PInstance_Log_ID)
;

--
-- DDL to give M_MovementLineMA a single PK column (FRESH-618 gh #333)
--
-- 26.10.2016 14:51
-- URL zum Konzept
ALTER TABLE M_MovementLineMA ADD COLUMN M_MovementLineMA_ID numeric(10,0) NOT NULL DEFAULT nextval('m_movementlinema_seq')
;
-- 26.10.2016 14:51
-- URL zum Konzept
ALTER TABLE M_MovementLineMA DROP CONSTRAINT IF EXISTS m_movementlinema_pkey
;
-- 26.10.2016 14:51
-- URL zum Konzept
ALTER TABLE M_MovementLineMA ADD CONSTRAINT m_movementlinema_pkey PRIMARY KEY (M_MovementLineMA_ID)
;

--
-- DDL for creating a single PK for M_HU_Snapshot
--
-- 28.10.2016 06:34
-- URL zum Konzept
ALTER TABLE M_HU_Snapshot ADD COLUMN M_HU_Snapshot_ID numeric(10,0) NOT NULL DEFAULT nextval('m_hu_snapshot_seq')
;

-- 28.10.2016 06:34
-- URL zum Konzept
ALTER TABLE M_HU_Snapshot DROP CONSTRAINT IF EXISTS m_hu_snapshot_pkey
;

-- 28.10.2016 06:34
-- URL zum Konzept
ALTER TABLE M_HU_Snapshot ADD CONSTRAINT m_hu_snapshot_pkey PRIMARY KEY (M_HU_Snapshot_ID)
;

--
-- DDL for creating a single PK for M_HU_Storage_Snapshot
--
-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Storage_Snapshot ADD COLUMN M_HU_Storage_Snapshot_ID numeric(10,0) NOT NULL DEFAULT nextval('m_hu_storage_snapshot_seq')
;

-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Storage_Snapshot DROP CONSTRAINT IF EXISTS m_hu_storage_snapshot_pkey
;

-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Storage_Snapshot ADD CONSTRAINT m_hu_storage_snapshot_pkey PRIMARY KEY (M_HU_Storage_Snapshot_ID)
;

--
-- DDL for creating a single PK for M_HU_Attribute_Snapshot
--
-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Attribute_Snapshot ADD COLUMN M_HU_Attribute_Snapshot_ID numeric(10,0) NOT NULL DEFAULT nextval('m_hu_attribute_snapshot_seq')
;

-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Attribute_Snapshot DROP CONSTRAINT IF EXISTS m_hu_attribute_snapshot_pkey
;

-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Attribute_Snapshot ADD CONSTRAINT m_hu_attribute_snapshot_pkey PRIMARY KEY (M_HU_Attribute_Snapshot_ID)
;

--
-- DDL for creating a single PK for M_AttributeInstance
--

-- 31.10.2016 14:21
-- URL zum Konzept
ALTER TABLE M_AttributeInstance ADD COLUMN M_AttributeInstance_ID numeric(10,0) NOT NULL DEFAULT nextval('m_attributeinstance_seq')
;

-- 31.10.2016 14:22
-- URL zum Konzept
ALTER TABLE M_AttributeInstance DROP CONSTRAINT IF EXISTS m_attributeinstance_pkey
;

-- 31.10.2016 14:22
-- URL zum Konzept
ALTER TABLE M_AttributeInstance ADD CONSTRAINT m_attributeinstance_pkey PRIMARY KEY (M_AttributeInstance_ID)
;



COMMIT;



--
-- DML for creating a single PK for ad_pinstance_para
--

-- 26.10.2016 21:39
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543214,0,'AD_PInstance_Para_ID',TO_TIMESTAMP('2016-10-26 21:39:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_PInstance_Para','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:39:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:39
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543214 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 26.10.2016 21:39
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555290,543214,0,13,283,'N','AD_PInstance_Para_ID',TO_TIMESTAMP('2016-10-26 21:39:58','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:39:58','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 26.10.2016 21:39
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555290 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555290,557358,0,540633,TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.async','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557358 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555290,557359,0,664,TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557359 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555290,557360,0,53022,TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557360 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555290,557361,0,540672,TO_TIMESTAMP('2016-10-26 21:40:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.async','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:40:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557361 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 07:06
-- URL zum Konzept
UPDATE AD_Column SET IsKey='N',Updated=TO_TIMESTAMP('2016-10-27 07:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8779
;

-- 27.10.2016 07:10
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2016-10-27 07:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8779
;


--
-- DML for creating a single PK for ad_pinstance_Log
--


-- 27.10.2016 07:07
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543216,0,'AD_PInstance_Log_ID',TO_TIMESTAMP('2016-10-27 07:07:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_PInstance_Log','AD_PInstance_Log',TO_TIMESTAMP('2016-10-27 07:07:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 07:07
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543216 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 27.10.2016 07:07
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555296,543216,0,13,578,'N','AD_PInstance_Log_ID',TO_TIMESTAMP('2016-10-27 07:07:05','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AD_PInstance_Log',TO_TIMESTAMP('2016-10-27 07:07:05','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 27.10.2016 07:07
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555296 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 27.10.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555296,557362,0,540634,TO_TIMESTAMP('2016-10-27 07:09:42','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.async','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Log',TO_TIMESTAMP('2016-10-27 07:09:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557362 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555296,557363,0,665,TO_TIMESTAMP('2016-10-27 07:09:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Log',TO_TIMESTAMP('2016-10-27 07:09:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557363 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;


--
-- DML to give M_MovementLineMA a single PK column (FRESH-618 gh #333)
--
-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543212,0,'M_MovementLineMA_ID',TO_TIMESTAMP('2016-10-26 14:51:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_MovementLineMA','M_MovementLineMA',TO_TIMESTAMP('2016-10-26 14:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543212 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555276,543212,0,13,764,'N','M_MovementLineMA_ID',TO_TIMESTAMP('2016-10-26 14:51:08','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','M_MovementLineMA',TO_TIMESTAMP('2016-10-26 14:51:08','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555276 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555276,557357,0,750,TO_TIMESTAMP('2016-10-26 14:51:19','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','M_MovementLineMA',TO_TIMESTAMP('2016-10-26 14:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557357 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



--
-- DML to give M_HU_Snapshot a single PK column (FRESH-618 gh #333)
--
-- 28.10.2016 06:34
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555346,542807,0,13,540669,'N','M_HU_Snapshot_ID',TO_TIMESTAMP('2016-10-28 06:34:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Handling Units (snapshot)',TO_TIMESTAMP('2016-10-28 06:34:40','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 28.10.2016 06:34
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555346 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


--
-- DML to give M_HU_Storage_Snapshot_ID a single PK column (FRESH-618 gh #333)
--
-- 28.10.2016 06:35
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555347,542811,0,13,540672,'N','M_HU_Storage_Snapshot_ID',TO_TIMESTAMP('2016-10-28 06:35:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Handling Units Storage Snapshot',TO_TIMESTAMP('2016-10-28 06:35:17','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 28.10.2016 06:35
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555347 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

--
-- DML to give M_HU_Attribute_Snapshot_ID a single PK column (FRESH-618 gh #333)
--
-- 28.10.2016 06:35
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555348,542810,0,13,540671,'N','M_HU_Attribute_Snapshot_ID',TO_TIMESTAMP('2016-10-28 06:35:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Handling Units Attribute',TO_TIMESTAMP('2016-10-28 06:35:34','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 28.10.2016 06:35
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555348 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


--
-- DML for creating a single PK for M_AttributeInstance
--
-- 31.10.2016 14:21
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543223,0,'M_AttributeInstance_ID',TO_TIMESTAMP('2016-10-31 14:21:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_AttributeInstance','M_AttributeInstance',TO_TIMESTAMP('2016-10-31 14:21:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.10.2016 14:21
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543223 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 31.10.2016 14:21
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555375,543223,0,13,561,'N','M_AttributeInstance_ID',TO_TIMESTAMP('2016-10-31 14:21:01','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','M_AttributeInstance',TO_TIMESTAMP('2016-10-31 14:21:01','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 31.10.2016 14:21
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555375 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
