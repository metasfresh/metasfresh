
DROP VIEW IF EXISTS MD_Stock_From_HUs_V;
CREATE VIEW MD_Stock_From_HUs_V AS
SELECT 
	l.M_Warehouse_ID,
	hus.M_Product_ID,
	hus.C_UOM_ID,
	GenerateHUAttributesKey(hu.m_hu_id) as AttributesKey,
	SUM(hus.Qty) as QtyOnHand
FROM m_hu hu
	JOIN M_HU_Storage hus ON hus.M_HU_ID = hu.M_HU_ID
	JOIN M_Locator l ON l.M_Locator_ID=hu.M_Locator_ID
WHERE hu.isactive='Y'
	and M_HU_Item_Parent_ID IS NULL
GROUP BY 
	l.M_Warehouse_ID,
	hus.M_Product_ID,
	hus.C_UOM_ID,
	GenerateHUAttributesKey(hu.m_hu_id)
;
COMMENT ON VIEW MD_Stock_From_HUs_V IS 
'This view is used by the process MD_Stock_Reset_From_M_HUs to intitialize or reset the MD_stock table

Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762';


-- 2018-01-05T09:22:24.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558495,215,0,30,540892,'N','C_UOM_ID',TO_TIMESTAMP('2018-01-05 09:22:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','de.metas.material.cockpit',10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Maßeinheit',0,0,TO_TIMESTAMP('2018-01-05 09:22:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-01-05T09:22:24.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558495 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

