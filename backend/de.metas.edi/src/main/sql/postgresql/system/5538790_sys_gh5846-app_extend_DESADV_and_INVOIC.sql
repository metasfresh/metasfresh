
DROP VIEW IF EXISTS public.EDI_M_Product_Lookup_UPC_v;
CREATE OR REPLACE VIEW public.EDI_M_Product_Lookup_UPC_v AS
SELECT DISTINCT ON (lookup.UPC, bpl.GLN)
	lookup.*,
	TRIM(BOTH ' ' FROM bpl.GLN) AS GLN
FROM
(
	SELECT
		'M_HU_PI_Item_Product.GTIN' AS source,
		piip.M_Product_ID,
		piip.C_BPartner_ID,
		piip.GTIN AS UPC /* backwards compatibility*/,
		piip.IsActive,
		'Y' AS UsedForCustomer -- use default Y for PIIPs
	FROM M_HU_PI_Item_Product piip
	WHERE piip.IsActive='Y'
	UNION SELECT
		'M_HU_PI_Item_Product.EAN_TU' AS source,
		piip.M_Product_ID,
		piip.C_BPartner_ID,
		piip.EAN_TU,
		piip.IsActive,
		'Y' AS UsedForCustomer -- use default Y for PIIPs
	FROM M_HU_PI_Item_Product piip
	WHERE piip.IsActive='Y'
	UNION SELECT
		'M_HU_PI_Item_Product.UPC' AS source,
		piip.M_Product_ID,
		piip.C_BPartner_ID,
		piip.UPC,
		piip.IsActive,
		'Y' AS UsedForCustomer -- use default Y for PIIPs
	FROM M_HU_PI_Item_Product piip
	WHERE piip.IsActive='Y'
	UNION SELECT
		'C_BPartner_Product.EAN_CU' AS source,
		bpp.M_Product_ID,
		bpp.C_BPartner_ID,
		bpp.EAN_CU,
		bpp.IsActive,
		bpp.UsedForCustomer
	FROM C_BPartner_Product bpp
	WHERE bpp.IsActive='Y' AND NOT EXISTS (SELECT 1 FROM M_HU_PI_Item_Product piip WHERE piip.EAN_TU=bpp.EAN_CU)
	UNION SELECT
		'C_BPartner_Product.UPC' AS source,
		bpp.M_Product_ID,
		bpp.C_BPartner_ID,
		bpp.UPC,
		bpp.IsActive,
		bpp.UsedForCustomer
	FROM C_BPartner_Product bpp
	WHERE bpp.IsActive='Y' AND NOT EXISTS (SELECT 1 FROM M_HU_PI_Item_Product piip WHERE piip.UPC=bpp.UPC)
	
) lookup
LEFT JOIN C_BPartner_Location bpl ON bpl.C_BPartner_ID=lookup.C_BPartner_ID AND bpl.GLN IS NOT NULL AND TRIM(BOTH ' ' FROM bpl.GLN)!=''
WHERE lookup.UPC IS NOT NULL AND TRIM(BOTH ' ' FROM lookup.UPC)!=''
;
COMMENT ON VIEW EDI_M_Product_Lookup_UPC_v IS
'Lookup of M_Product_ID via a M_HU_PI_Item_Product''s GTIN, TU-EAN (column EAN_TU), UPC (UPC) or C_BPartner_Product''s EAN_CU or UPC.';

---------------------------------------------------

DROP VIEW IF EXISTS public.edi_cctop_invoic_500_v;
CREATE OR REPLACE VIEW public.edi_cctop_invoic_500_v AS
SELECT 
    sum(il.qtyEntered) AS QtyInvoiced,
    CASE
        WHEN u.x12de355 = 'TU' THEN 'PCE'
        ELSE u.x12de355
    END AS eancom_uom, /* C_InvoiceLine's UOM */
    CASE
        WHEN u_ordered.x12de355 IN ('TU', 'COLI') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
        ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced) 
    END AS QtyInvoicedInOrderedUOM,
    u_ordered.x12de355 AS eancom_ordered_uom, /* leaving out that CASE-mumbo-jumbo from the other uoms; IDK if it's needed (anymore) */
    min(il.c_invoiceline_id) AS edi_cctop_invoic_500_v_id,
    sum(il.linenetamt) AS linenetamt,
    min(il.line) AS line,
    il.c_invoice_id,
    il.c_invoice_id AS edi_cctop_invoic_v_id,
    il.priceactual,
    il.pricelist,
    pp.UPC AS UPC_CU,
    pp.EAN_CU,
    p.value,
    pp.productno AS vendorproductno,
    substr(p.name, 1, 35) AS name,
    substr(p.name, 36, 70) AS name2,
    t.rate,
    CASE
        WHEN u_price.x12de355 = 'TU' THEN 'PCE'
        ELSE u_price.x12de355
    END AS eancom_price_uom /* C_InvoiceLine's Price-UOM */,
    CASE
        WHEN t.rate = 0 THEN 'Y'
        ELSE ''
    END AS taxfree,
    c.iso_code,
    il.ad_client_id,
    il.ad_org_id,
    min(il.created) AS created,
    min(il.createdby)::numeric(10,0) AS createdby,
    max(il.updated) AS updated,
    max(il.updatedby)::numeric(10,0) AS updatedby,
    il.isactive,
    CASE pc.value
        WHEN 'Leergut' THEN 'P'
        ELSE ''
    END AS leergut,
    COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''), p.name)::character varying AS productdescription,
    COALESCE(ol.line, il.line) AS orderline,
	COALESCE(NULLIF(o.poreference, ''), i.poreference)::character varying(40) AS orderporeference,
    il.c_orderline_id,
    sum(il.taxamtinfo) AS taxamtinfo,
    pip.GTIN,
    pip.EAN_TU,
    pip.UPC AS UPC_TU
FROM c_invoiceline il
    LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id AND ol.isactive = 'Y'
        LEFT JOIN M_HU_PI_Item_Product pip ON ol.M_HU_PI_Item_Product_ID=pip.M_HU_PI_Item_Product_ID
        LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
        LEFT JOIN c_uom u_ordered ON u_ordered.c_uom_id = COALESCE(ol.c_uom_id, il.c_uom_id)
    LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
        LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
    LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
        LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
    LEFT JOIN c_bpartner_product pp ON pp.c_bpartner_id = i.c_bpartner_id AND pp.m_product_id = il.m_product_id AND pp.isactive = 'Y'
    LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
    LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id
    LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
WHERE true 
    AND il.m_product_id IS NOT NULL 
    AND il.isactive = 'Y'
    AND il.qtyentered <> 0
GROUP BY 
  	il.c_invoice_id, 
	il.priceactual, 
	il.pricelist, 
    pp.UPC, 
    pp.EAN_CU, 
    p.value, 
    pp.productno, 
	(substr(p.name, 1, 35)), 
	(substr(p.name, 36, 70)), 
	t.rate, 
	(CASE
        WHEN u.x12de355 = 'TU' THEN 'PCE'
        ELSE u.x12de355
    END), 
	(CASE
        WHEN u_price.x12de355 = 'TU' THEN 'PCE'
        ELSE u_price.x12de355
    END), 
    (CASE
        WHEN u_ordered.x12de355 IN ('TU', 'COLI') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
        ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced) 
    END),
    u_ordered.x12de355,
	(CASE
        WHEN t.rate = 0 THEN 'Y'
        ELSE ''
    END), 
	c.iso_code, 
	il.ad_client_id, 
	il.ad_org_id, 
	il.isactive, 
	(CASE pc.value
        WHEN 'Leergut' THEN 'P'
        ELSE ''
    END), 
	(COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''), p.name)), 
	(COALESCE(NULLIF(o.poreference, ''), i.poreference)), 
	(COALESCE(ol.line, il.line)), 
	il.c_orderline_id,
    pip.UPC, pip.GTIN, pip.EAN_TU
ORDER BY COALESCE(ol.line, il.line);

COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
';


-- 2019-12-12T08:21:23.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577424,0,'QtyInvoicedInOrderedUOM',TO_TIMESTAMP('2019-12-12 09:21:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fakturierte Menge in Auftrags-Maßeinheit','Fakturierte Menge in Auftrags-Maßeinheit',TO_TIMESTAMP('2019-12-12 09:21:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T08:21:23.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577424 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-12T08:21:26.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-12 09:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577424 AND AD_Language='de_CH'
;

-- 2019-12-12T08:21:26.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577424,'de_CH') 
;

-- 2019-12-12T08:21:28.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-12 09:21:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577424 AND AD_Language='de_DE'
;

-- 2019-12-12T08:21:28.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577424,'de_DE') 
;

-- 2019-12-12T08:21:28.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577424,'de_DE') 
;

-- 2019-12-12T08:21:47.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoiced quantity in ordered UOM', PrintName='Invoiced quantity in ordered UOM',Updated=TO_TIMESTAMP('2019-12-12 09:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577424 AND AD_Language='en_US'
;

-- 2019-12-12T08:21:47.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577424,'en_US') 
;

-- 2019-12-12T08:22:03.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569745,577424,0,29,540463,'QtyInvoicedInOrderedUOM',TO_TIMESTAMP('2019-12-12 09:22:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Fakturierte Menge in Auftrags-Maßeinheit',0,0,TO_TIMESTAMP('2019-12-12 09:22:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-12-12T08:22:03.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-12T08:22:03.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577424) 
;

-- 2019-12-12T08:23:11.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577425,0,'OrderedUOM_ID',TO_TIMESTAMP('2019-12-12 09:23:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Auftrags-Maßeinheit','Auftrags-Maßeinheit',TO_TIMESTAMP('2019-12-12 09:23:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T08:23:11.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577425 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-12T08:23:16.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-12 09:23:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577425 AND AD_Language='de_CH'
;

-- 2019-12-12T08:23:16.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577425,'de_CH') 
;

-- 2019-12-12T08:23:18.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-12 09:23:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577425 AND AD_Language='de_DE'
;

-- 2019-12-12T08:23:18.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577425,'de_DE') 
;

-- 2019-12-12T08:23:18.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577425,'de_DE') 
;

-- 2019-12-12T08:23:28.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ordered UOM', PrintName='Ordered UOM',Updated=TO_TIMESTAMP('2019-12-12 09:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577425 AND AD_Language='en_US'
;

-- 2019-12-12T08:23:28.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577425,'en_US') 
;

-- 2019-12-12T08:23:51.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569746,577425,0,30,114,540463,'OrderedUOM_ID',TO_TIMESTAMP('2019-12-12 09:23:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Auftrags-Maßeinheit',0,0,TO_TIMESTAMP('2019-12-12 09:23:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-12-12T08:23:51.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-12T08:23:51.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577425) 
;

-- 2019-12-12T08:24:06.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569746,0,TO_TIMESTAMP('2019-12-12 09:24:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550288,'N','N','Auftrags-Maßeinheit',350,'R',TO_TIMESTAMP('2019-12-12 09:24:06','YYYY-MM-DD HH24:MI:SS'),100,'OrderedUOM_ID')
;

-- 2019-12-12T08:24:06.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569745,0,TO_TIMESTAMP('2019-12-12 09:24:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550289,'N','N','Fakturierte Menge in Auftrags-Maßeinheit',360,'E',TO_TIMESTAMP('2019-12-12 09:24:06','YYYY-MM-DD HH24:MI:SS'),100,'QtyInvoicedInOrderedUOM')
;

-- 2019-12-12T08:24:13.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-12-12 09:24:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550288
;

-- 2019-12-12T08:24:21.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-12-12 09:24:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550289
;

-- 2019-12-12T08:24:27.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Position=203,Updated=TO_TIMESTAMP('2019-12-12 09:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550288
;

-- 2019-12-12T08:24:35.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Position=205,Updated=TO_TIMESTAMP('2019-12-12 09:24:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550289
;

-- 2019-12-12T08:24:50.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Position=207,Updated=TO_TIMESTAMP('2019-12-12 09:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550288
;

-- 2019-12-12T08:27:33.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Name='EanCom_Ordered_UOM', Type='E', Value='EanCom_Ordered_UOM',Updated=TO_TIMESTAMP('2019-12-12 09:27:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550288
;

-- 2019-12-12T08:29:57.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='EanCom_Ordered_UOM',Updated=TO_TIMESTAMP('2019-12-12 09:29:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577425
;

-- 2019-12-12T08:29:57.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EanCom_Ordered_UOM', Name='Auftrags-Maßeinheit', Description=NULL, Help=NULL WHERE AD_Element_ID=577425
;

-- 2019-12-12T08:29:57.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EanCom_Ordered_UOM', Name='Auftrags-Maßeinheit', Description=NULL, Help=NULL, AD_Element_ID=577425 WHERE UPPER(ColumnName)='EANCOM_ORDERED_UOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-12T08:29:57.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EanCom_Ordered_UOM', Name='Auftrags-Maßeinheit', Description=NULL, Help=NULL WHERE AD_Element_ID=577425 AND IsCentrallyMaintained='Y'
;

-- 2019-12-12T08:30:19.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='EanCom_UOM',Updated=TO_TIMESTAMP('2019-12-12 09:30:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541986
;

-- 2019-12-12T08:30:19.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EanCom_UOM', Name='eancom_uom', Description=NULL, Help=NULL WHERE AD_Element_ID=541986
;

-- 2019-12-12T08:30:19.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EanCom_UOM', Name='eancom_uom', Description=NULL, Help=NULL, AD_Element_ID=541986 WHERE UPPER(ColumnName)='EANCOM_UOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-12T08:30:19.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EanCom_UOM', Name='eancom_uom', Description=NULL, Help=NULL WHERE AD_Element_ID=541986 AND IsCentrallyMaintained='Y'
;

-- 2019-12-12T08:33:33.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569747,576652,0,10,540676,'LotNumber',TO_TIMESTAMP('2019-12-12 09:33:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',512,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Chargennummer',0,0,TO_TIMESTAMP('2019-12-12 09:33:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-12-12T08:33:33.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569747 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-12T08:33:33.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576652) 
;

-- 2019-12-12T08:33:40.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_Pack','ALTER TABLE public.EDI_DesadvLine_Pack ADD COLUMN LotNumber VARCHAR(512)')
;

-- 2019-12-12T08:33:55.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569747,593781,0,542152,TO_TIMESTAMP('2019-12-12 09:33:55','YYYY-MM-DD HH24:MI:SS'),100,512,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Chargennummer',TO_TIMESTAMP('2019-12-12 09:33:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T08:33:55.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593781 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-12-12T08:33:55.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576652) 
;

-- 2019-12-12T08:33:55.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593781
;

-- 2019-12-12T08:33:55.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593781)
;

-- 2019-12-12T08:34:05.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-12-12 09:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593781
;

-- 2019-12-12T08:34:59.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2019-12-12 09:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564560
;

-- 2019-12-12T08:35:02.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2019-12-12 09:35:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564359
;

-- 2019-12-12T08:35:04.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2019-12-12 09:35:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564360
;

-- 2019-12-12T08:35:07.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2019-12-12 09:35:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564361
;

-- 2019-12-12T08:35:10.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=119,Updated=TO_TIMESTAMP('2019-12-12 09:35:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564362
;

-- 2019-12-12T08:35:36.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2019-12-12 09:35:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564362
;

-- 2019-12-12T08:35:39.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2019-12-12 09:35:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564561
;

-- 2019-12-12T08:35:50.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2019-12-12 09:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564353
;

-- 2019-12-12T08:36:21.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,593781,0,542152,543218,564724,'F',TO_TIMESTAMP('2019-12-12 09:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'LotNumber',60,0,0,TO_TIMESTAMP('2019-12-12 09:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T08:36:48.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-12-12 09:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564724
;

-- 2019-12-12T08:36:48.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-12-12 09:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564560
;

-- 2019-12-12T08:36:48.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-12-12 09:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564359
;

-- 2019-12-12T08:36:48.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-12-12 09:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564361
;

-- 2019-12-12T08:36:48.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-12-12 09:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564360
;

-- 2019-12-12T08:36:48.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-12-12 09:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564561
;

-- 2019-12-12T08:36:48.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-12-12 09:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564362
;

-- 2019-12-12T08:36:48.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-12-12 09:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564353
;

-- 2019-12-12T08:38:19.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=111,Updated=TO_TIMESTAMP('2019-12-12 09:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564561
;

-- 2019-12-12T08:38:21.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2019-12-12 09:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564362
;

-- 2019-12-12T08:38:25.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2019-12-12 09:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564561
;

