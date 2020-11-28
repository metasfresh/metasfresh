-- 22.09.2016 16:28
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543187,0,'IsFallBack',TO_TIMESTAMP('2016-09-22 16:28:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','IsFallBack','IsFallBack',TO_TIMESTAMP('2016-09-22 16:28:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.09.2016 16:28
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543187 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 22.09.2016 16:29
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555103,543187,0,20,540787,'N','IsFallBack',TO_TIMESTAMP('2016-09-22 16:29:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','de.metas.fresh',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','IsFallBack',0,TO_TIMESTAMP('2016-09-22 16:29:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 22.09.2016 16:29
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555103 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 22.09.2016 16:29
-- URL zum Konzept
UPDATE X_MRP_ProductInfo_Detail_MV SET IsFallBack='Y' WHERE IsFallBack IS NULL
;
--
-- DDL
--
COMMIT;

-- 22.09.2016 16:29
-- URL zum Konzept
INSERT INTO t_alter_column values('x_mrp_productinfo_detail_mv','IsFallBack','CHAR(1)',null,'Y')
;

--
--
-- set additional default values to make sure that if we copy any x_mrp_productinfo_detail_mv using metasfresh, the original's 0 values won't end up NULL in the copy result.
-- 
ALTER TABLE x_mrp_productinfo_detail_mv
   ALTER COLUMN asikey SET DEFAULT '';
ALTER TABLE x_mrp_productinfo_detail_mv
   ALTER COLUMN qtyreserved_ondate SET DEFAULT 0;
ALTER TABLE x_mrp_productinfo_detail_mv
   ALTER COLUMN qtyordered_ondate SET DEFAULT 0;
ALTER TABLE x_mrp_productinfo_detail_mv
   ALTER COLUMN qtyordered_sale_ondate SET DEFAULT 0;
ALTER TABLE x_mrp_productinfo_detail_mv
   ALTER COLUMN qtymaterialentnahme SET DEFAULT 0;
ALTER TABLE x_mrp_productinfo_detail_mv
   ALTER COLUMN fresh_qtyonhand_ondate SET DEFAULT 0;
ALTER TABLE x_mrp_productinfo_detail_mv
   ALTER COLUMN fresh_qtypromised SET DEFAULT 0;
ALTER TABLE x_mrp_productinfo_detail_mv
   ALTER COLUMN groupnames SET DEFAULT '{"Keine / Leer"}';
COMMENT ON COLUMN x_mrp_productinfo_detail_mv.qtyonhand
  IS 'null by default, to indicate that it needs to be set by javacode';
COMMENT ON COLUMN x_mrp_productinfo_detail_mv.qtyonhand IS 'null by default, to indicate that it needs to be set by javacode';

ALTER TABLE x_mrp_productinfo_detail_mv DROP COLUMN IF EXISTS fresh_qtymrp; -- this column is obsolete; instead of physically having it, we join the value from table "de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp
