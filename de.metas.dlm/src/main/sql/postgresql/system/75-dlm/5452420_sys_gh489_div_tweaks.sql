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
-- DDL for deleting DLM_Referenced_Table_Partition_Config_Line_ID
--
ALTER TABLE DLM_Partition_Config_Reference DROP COLUMN IF EXISTS DLM_Referenced_Table_Partition_Config_Line_ID;

COMMIT;

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
-- Change DLM_Level so that we also see records with are in the "test" level
--
-- 26.10.2016 15:44
-- URL zum Konzept
UPDATE AD_SysConfig SET Value='1',Updated=TO_TIMESTAMP('2016-10-26 15:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541045
;

--
-- DML for deleting DLM_Referenced_Table_Partition_Config_Line_ID
--
-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=557330
;

-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=557330
;

-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=555151
;

-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=555151
;

-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540350
;

