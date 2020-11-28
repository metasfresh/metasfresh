

--
-- Create single PK for M_InOutLineMA
--
-- 21.10.2016 18:13
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543201,0,'M_InOutLineMA_ID',TO_TIMESTAMP('2016-10-21 18:13:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_InOutLineMA_ID','M_InOutLineMA_ID',TO_TIMESTAMP('2016-10-21 18:13:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 21.10.2016 18:13
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543201 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 21.10.2016 18:14
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555198,543201,0,13,762,'N','M_InOutLineMA_ID',TO_TIMESTAMP('2016-10-21 18:14:31','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','M_InOutLineMA_ID',0,TO_TIMESTAMP('2016-10-21 18:14:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 21.10.2016 18:14
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555198 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


CREATE SEQUENCE M_InOutineMA_SEQ
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 2147483647
  START 1000000
  CACHE 1;

ALTER TABLE M_InOutLineMA ADD M_InOutLineMA_ID NUMERIC(10) NOT NULL DEFAULT nextval('M_InOutineMA_Seq'); 
ALTER TABLE M_InOutLineMA DROP CONSTRAINT IF EXISTS m_inoutlinema_pkey;
ALTER TABLE M_InOutLineMA ADD CONSTRAINT m_inoutlinema_pkey PRIMARY KEY (M_InOutLineMA_ID);

