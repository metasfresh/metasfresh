

-- 2018-11-22T17:42:11.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575889,0,'QtyOnHandChange',TO_TIMESTAMP('2018-11-22 17:42:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bestandsänderung','Bestandsänderung',TO_TIMESTAMP('2018-11-22 17:42:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T17:42:11.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575889 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-11-22T17:43:21.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 17:43:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575889 AND AD_Language='de_CH'
;

-- 2018-11-22T17:43:21.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575889,'de_CH') 
;

-- 2018-11-22T17:43:25.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 17:43:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575889 AND AD_Language='de_DE'
;

-- 2018-11-22T17:43:25.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575889,'de_DE') 
;

-- 2018-11-22T17:43:25.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575889,'de_DE') 
;

-- 2018-11-22T17:43:36.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 17:43:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='On hand quantity change',PrintName='On hand quantity change' WHERE AD_Element_ID=575889 AND AD_Language='en_US'
;

-- 2018-11-22T17:43:36.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575889,'en_US') 
;

-- 2018-11-22T17:46:11.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,563609,575889,0,29,540892,'QtyOnHandChange',TO_TIMESTAMP('2018-11-22 17:46:11','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.cockpit',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Bestandsänderung',0,0,TO_TIMESTAMP('2018-11-22 17:46:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-11-22T17:46:11.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563609 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


DROP VIEW IF EXISTS MD_Stock_From_HUs_V;
CREATE VIEW MD_Stock_From_HUs_V AS
SELECT 
	COALESCE(hu_agg.AD_Client_ID, s.AD_Client_ID) AS AD_Client_ID,
	COALESCE(hu_agg.AD_Org_ID, s.AD_Org_ID) AS AD_Org_ID,
	COALESCE(hu_agg.M_Warehouse_ID, s.M_Warehouse_ID) AS M_Warehouse_ID,
	COALESCE(hu_agg.M_Product_ID, s.M_Product_ID) AS M_Product_ID,
	COALESCE(hu_agg.C_UOM_ID, p.C_UOM_ID) AS C_UOM_ID,
	COALESCE(hu_agg.AttributesKey, s.AttributesKey) AS AttributesKey,
	COALESCE(hu_agg.QtyOnHand, 0) AS QtyOnHand,
	COALESCE(hu_agg.QtyOnHand, 0) - COALESCE(s.QtyOnHand, 0) AS QtyOnHandChange
FROM 
	MD_Stock s
	LEFT JOIN M_Product p ON p.M_Product_ID = s.M_Product_ID /*needed for its C_UOM_ID*/
	LEFT OUTER JOIN 
	(
		SELECT 
			hu.AD_Client_ID,
			hu.AD_Org_ID,
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

			/*please keep in sync with de.metas.handlingunits.IHUStatusBL.isPhysicalHU(I_M_HU)*/
			and hu.HuStatus NOT IN ('P'/*Planning*/,'D'/*Destroyed*/,'E'/*Shipped*/) 
		GROUP BY 
			hu.AD_Client_ID,
			hu.AD_Org_ID,
			l.M_Warehouse_ID,
			hus.M_Product_ID,
			hus.C_UOM_ID,
			GenerateHUAttributesKey(hu.m_hu_id)
	) hu_agg ON true
		AND hu_agg.AD_Client_ID = s.AD_Client_ID 
		AND hu_agg.AD_Org_ID=s.AD_Org_ID
		AND hu_agg.M_Warehouse_ID = s.M_Warehouse_ID
		AND hu_agg.M_Product_ID = s.M_Product_ID
		AND hu_agg.AttributesKey = s.AttributesKey
;
COMMENT ON VIEW MD_Stock_From_HUs_V IS 
'This view is used by the process MD_Stock_Reset_From_M_HUs to intitialize or reset the MD_stock table.
Note that due to the outer join, existing MD_Stock records that currently don''t have any HU-storage are also represented (with qty=0)
Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762';

