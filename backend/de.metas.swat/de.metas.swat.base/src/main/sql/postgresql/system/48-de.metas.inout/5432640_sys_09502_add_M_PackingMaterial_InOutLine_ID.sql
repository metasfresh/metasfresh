
-- 06.11.2015 17:53
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542919,0,'M_PackingMaterial_InOutLine_ID',TO_TIMESTAMP('2015-11-06 17:53:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','M_PackingMaterial_InOutLine_ID','M_PackingMaterial_InOutLine_ID',TO_TIMESTAMP('2015-11-06 17:53:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.11.2015 17:53
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542919 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.11.2015 17:54
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552812,542919,0,30,295,320,'N','M_PackingMaterial_InOutLine_ID',TO_TIMESTAMP('2015-11-06 17:54:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inout',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','M_PackingMaterial_InOutLine_ID',0,TO_TIMESTAMP('2015-11-06 17:54:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.11.2015 17:54
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552812 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.11.2015 17:54
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2015-11-06 17:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550886
;

COMMIT;

-- 06.11.2015 17:55
-- URL zum Konzept
ALTER TABLE M_InOutLine ADD M_PackingMaterial_InOutLine_ID NUMERIC(10) DEFAULT NULL 
;

ALTER TABLE M_InOutLine DROP CONSTRAINT IF EXISTS MPackingMaterialInOutLine_MInO;
ALTER TABLE M_InOutLine ADD CONSTRAINT MPackingMaterialInOutLine_MInO FOREIGN KEY (M_PackingMaterial_InOutLine_ID) REFERENCES M_InOutLine DEFERRABLE INITIALLY DEFERRED;
