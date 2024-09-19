-- 2024-09-18T11:35:06.257Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583258,0,'TU_GTIN',TO_TIMESTAMP('2024-09-18 13:35:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','GTIN_TU','TU-GTIN',TO_TIMESTAMP('2024-09-18 13:35:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-18T11:35:06.268Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583258 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TU_GTIN
-- 2024-09-18T11:35:11.163Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-18 13:35:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583258 AND AD_Language='de_CH'
;

-- 2024-09-18T11:35:11.200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583258,'de_CH') 
;

-- Element: TU_GTIN
-- 2024-09-18T11:35:12.966Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-18 13:35:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583258 AND AD_Language='de_DE'
;

-- 2024-09-18T11:35:12.970Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583258,'de_DE') 
;

-- 2024-09-18T11:35:12.973Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583258,'de_DE') 
;

-- Element: TU_GTIN
-- 2024-09-18T11:35:31.209Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-18 13:35:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583258 AND AD_Language='en_US'
;

-- 2024-09-18T11:35:31.214Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583258,'en_US') 
;

-- 2024-09-18T11:36:09.416Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583259,0,'GTIN_CU',TO_TIMESTAMP('2024-09-18 13:36:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CU-GTIN','CU-GTIN',TO_TIMESTAMP('2024-09-18 13:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-18T11:36:09.421Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583259 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: GTIN_CU
-- 2024-09-18T11:36:15.181Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-18 13:36:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583259 AND AD_Language='de_CH'
;

-- 2024-09-18T11:36:15.185Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583259,'de_CH') 
;

-- Element: GTIN_CU
-- 2024-09-18T11:36:16.596Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-18 13:36:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583259 AND AD_Language='en_US'
;

-- 2024-09-18T11:36:16.601Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583259,'en_US') 
;

-- Element: GTIN_CU
-- 2024-09-18T11:36:17.077Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-18 13:36:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583259 AND AD_Language='de_DE'
;

-- 2024-09-18T11:36:17.081Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583259,'de_DE') 
;

-- 2024-09-18T11:36:17.083Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583259,'de_DE') 
;

-- Element: GTIN_CU
-- 2024-09-18T11:36:26.305Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-18 13:36:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583259 AND AD_Language='fr_CH'
;

-- 2024-09-18T11:36:26.309Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583259,'fr_CH') 
;

-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- 2024-09-18T11:38:57.518Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588985,583258,0,10,542171,'XX','TU_GTIN',TO_TIMESTAMP('2024-09-18 13:38:57','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,20,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'GTIN_TU',0,0,TO_TIMESTAMP('2024-09-18 13:38:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-18T11:38:57.521Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588985 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-18T11:38:57.529Z
/* DDL */  select update_Column_Translation_From_AD_Element(583258) 
;

-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- 2024-09-18T11:39:35.911Z
UPDATE AD_Column SET FieldLength=50,Updated=TO_TIMESTAMP('2024-09-18 13:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588985
;

-- 2024-09-18T11:39:40.223Z
/* DDL */ SELECT public.db_alter_table('EDI_Desadv_Pack_Item','ALTER TABLE public.EDI_Desadv_Pack_Item ADD COLUMN TU_GTIN VARCHAR(50)')
;


-- Column: EDI_DesadvLine.GTIN_CU
-- Column: EDI_DesadvLine.GTIN_CU
-- 2024-09-18T11:40:17.726Z
UPDATE AD_Column SET AD_Element_ID=583259, ColumnName='GTIN_CU', Description=NULL, Help=NULL, IsExcludeFromZoomTargets='Y', Name='CU-GTIN',Updated=TO_TIMESTAMP('2024-09-18 13:40:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568981
;

-- 2024-09-18T11:40:17.729Z
UPDATE AD_Field SET Name='CU-GTIN', Description=NULL, Help=NULL WHERE AD_Column_ID=568981
;

-- 2024-09-18T11:40:17.738Z
/* DDL */  select update_Column_Translation_From_AD_Element(583259) 
;


DROP VIEW IF EXISTS edi_desadvpack_sscc_label;


SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine RENAME COLUMN GTIN TO GTIN_CU')
;

create or replace view edi_desadvpack_sscc_label
            (no_of_labels, sscc, order_reference, date_shipped, gtin, product_description, amount, amount_lu, weight,
             netweight, best_before, lot_no, bp_name, bp_address_gln, bp_address_name1, bp_address_name2,
             bp_address_street, bp_address_zip_code, bp_address_city, bp_address_country, ho_name, ho_address_gln,
             ho_address_name1, ho_address_name2, ho_address_street, ho_address_zip_code, ho_address_city, cnt_packs,
             cnt_in_packs, p_instance_id)
as
SELECT 1                                 AS no_of_labels,
       agg_pack.ipa_sscc18               AS sscc,
       dh.poreference                    AS order_reference,
       dh.movementdate                   AS date_shipped,
       agg_pack.GTIN_CU,
       agg_pack.product_description      AS product_description,
       agg_pack.amount                   AS amount,
       agg_pack.amount_lu                AS amount_lu,
       p.weight as netweight,
       p.grossweight as weight,
       agg_pack.best_before              AS best_before,
       agg_pack.lot_no                   AS lot_no,
       COALESCE(bp.companyname, bp.name) AS bp_name,
       bp_address.gln                    AS bp_address_gln,
       bp_address_location.address1      AS bp_address_name1,
       bp_address_location.address2      AS bp_address_name2,
       bp_address_location.address2      AS bp_address_street,
       bp_address_location.postal        AS bp_address_zip_code,
       bp_address_location.city          AS bp_address_city,
       bp_address_country.name           AS bp_address_country,
       COALESCE(ho.companyname, ho.name) AS ho_name,
       ho_address.gln                    AS ho_address_gln,
       ho_address_location.address1      AS ho_address_name1,
       ho_address_location.address2      AS ho_address_name2,
       ho_address_location.address2      AS ho_address_street,
       ho_address_location.postal        AS ho_address_zip_code,
       ho_address_location.city          AS ho_address_city,
       agg_pack.cnt_packs                AS cnt_packs,
       agg_pack.cnt_in_packs             AS cnt_in_packs,
       agg_pack.p_instance_id            AS p_instance_id
FROM (select dl_pack.edi_desadv_pack_id                       as pack_id,
             dl_pack.ipa_sscc18,
             dl_pack.edi_desadv_id,
             dl.m_product_id                                  as m_product_id,
             dl.GTIN_CU                                       AS GTIN_CU,
             dl.productdescription                            AS product_description,
             t_sel.ad_pinstance_id                            AS p_instance_id,
             (SELECT count(1) AS count
              FROM edi_desadv_pack pk
              WHERE pk.edi_desadv_id = dl_pack.edi_desadv_id) AS cnt_packs,
             dl_pack.edi_desadv_pack_id - ((SELECT min(pk.edi_desadvline_pack_id) AS min
                                            FROM edi_desadvline_pack pk
                                            WHERE pk.edi_desadv_id = dl_pack.edi_desadv_id)) +
             1::numeric                                       AS cnt_in_packs,
             min(dl_pack_item.bestbeforedate)                 AS best_before,
             STRING_AGG(distinct dl_pack_item.lotnumber, '')  AS lot_no,
             sum(dl_pack_item.qtytu)                          AS amount,
             sum(dl_pack_item.qtycusperlu)                    AS amount_lu
      FROM t_selection t_sel
               JOIN edi_desadv_pack dl_pack ON t_sel.t_selection_id = dl_pack.edi_desadv_pack_id
               JOIN edi_desadv_pack_item dl_pack_item ON dl_pack.edi_desadv_pack_id = dl_pack_item.edi_desadv_pack_id
               JOIN edi_desadvline dl ON dl_pack_item.edi_desadvline_id = dl.edi_desadvline_id
      group by dl_pack.edi_desadv_pack_id, dl.GTIN_CU, dl.productdescription, t_sel.ad_pinstance_id, dl.m_product_id) agg_pack
         inner JOIN edi_desadv dh ON dh.edi_desadv_id = agg_pack.edi_desadv_id
         LEFT JOIN c_bpartner_location bp_address ON dh.c_bpartner_location_id = bp_address.c_bpartner_location_id
         LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = bp_address.c_bpartner_id
         LEFT JOIN c_location bp_address_location ON bp_address.c_location_id = bp_address_location.c_location_id
         LEFT JOIN c_country bp_address_country ON bp_address_location.c_country_id = bp_address_country.c_country_id
         LEFT JOIN c_bpartner_location ho_address ON dh.handover_location_id = ho_address.c_bpartner_location_id
         LEFT JOIN c_bpartner ho ON ho.c_bpartner_id = ho_address.c_bpartner_id
         LEFT JOIN c_location ho_address_location ON ho_address.c_location_id = ho_address_location.c_location_id
         LEFT JOIN m_product p ON p.m_product_id = agg_pack.m_product_id;

-- 2024-09-18T11:49:10.022Z
INSERT INTO t_alter_column values('edi_desadv_pack_item','TU_GTIN','VARCHAR(50)',null,null)
;

-- 2024-09-18T12:18:47.207Z
INSERT INTO t_alter_column values('edi_desadv_pack_item','TU_GTIN','VARCHAR(50)',null,null)
;

-- 2024-09-18T12:20:07.639Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588984,0,TO_TIMESTAMP('2024-09-18 14:20:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540418,550785,'Y','Y','N','Line',250,'E',TO_TIMESTAMP('2024-09-18 14:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Line')
;

-- 2024-09-18T12:20:38.776Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588985,0,TO_TIMESTAMP('2024-09-18 14:20:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540418,550786,'Y','N','N','TU_GTIN',260,'E',TO_TIMESTAMP('2024-09-18 14:20:38','YYYY-MM-DD HH24:MI:SS'),100,'TU_GTIN')
;

-- 2024-09-18T12:27:29.469Z
UPDATE EXP_FormatLine SET Name='GTIN_CU', Value='GTIN_CU',Updated=TO_TIMESTAMP('2024-09-18 14:27:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550237
;

-- 2024-09-18T12:28:06.273Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-09-18 14:28:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550236
;

-- 2024-09-18T12:28:08.091Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-09-18 14:28:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550238
;

-- 2024-09-18T12:28:23.756Z
UPDATE EXP_FormatLine SET Name='CU-GTIN',Updated=TO_TIMESTAMP('2024-09-18 14:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550237
;

-- Column: EDI_DesadvLine.EAN_TU
-- Column: EDI_DesadvLine.EAN_TU
-- 2024-09-18T12:29:37.609Z
UPDATE AD_Column SET TechnicalNote='Deprecated. This info shall be in EDI_Desadv_Pack_Item',Updated=TO_TIMESTAMP('2024-09-18 14:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568980
;

-- Column: EDI_DesadvLine.UPC_TU
-- Column: EDI_DesadvLine.UPC_TU
-- 2024-09-18T12:29:53.138Z
UPDATE AD_Column SET IsActive='N', TechnicalNote='Deprecated. This info shall be in EDI_Desadv_Pack_Item',Updated=TO_TIMESTAMP('2024-09-18 14:29:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568985
;

-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- 2024-09-18T12:30:16.761Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588986,577133,0,10,542171,'XX','EAN_TU',TO_TIMESTAMP('2024-09-18 14:30:16','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,50,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'TU-EAN',0,0,TO_TIMESTAMP('2024-09-18 14:30:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-18T12:30:16.764Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588986 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-18T12:30:16.771Z
/* DDL */  select update_Column_Translation_From_AD_Element(577133) 
;

-- 2024-09-18T12:30:17.564Z
/* DDL */ SELECT public.db_alter_table('EDI_Desadv_Pack_Item','ALTER TABLE public.EDI_Desadv_Pack_Item ADD COLUMN EAN_TU VARCHAR(50)')
;

-- Column: EDI_Desadv_Pack_Item.UPC_TU
-- Column: EDI_Desadv_Pack_Item.UPC_TU
-- 2024-09-18T12:30:29.195Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588987,577137,0,10,542171,'XX','UPC_TU',TO_TIMESTAMP('2024-09-18 14:30:29','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,50,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'TU-UPC',0,0,TO_TIMESTAMP('2024-09-18 14:30:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-18T12:30:29.198Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588987 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-18T12:30:29.204Z
/* DDL */  select update_Column_Translation_From_AD_Element(577137) 
;

-- 2024-09-18T12:30:30.452Z
/* DDL */ SELECT public.db_alter_table('EDI_Desadv_Pack_Item','ALTER TABLE public.EDI_Desadv_Pack_Item ADD COLUMN UPC_TU VARCHAR(50)')
;

-- Field: EDI Lieferavis Pack (DESADV) -> Pack Item -> Zeile Nr.
-- Column: EDI_Desadv_Pack_Item.Line
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> Zeile Nr.
-- Column: EDI_Desadv_Pack_Item.Line
-- 2024-09-18T12:31:24.229Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588984,729890,0,546396,TO_TIMESTAMP('2024-09-18 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument',22,'de.metas.esb.edi','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','N','N','N','N','N','Zeile Nr.',TO_TIMESTAMP('2024-09-18 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-18T12:31:24.235Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729890 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-18T12:31:24.242Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439) 
;

-- 2024-09-18T12:31:24.318Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729890
;

-- 2024-09-18T12:31:24.333Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729890)
;

-- Field: EDI Lieferavis Pack (DESADV) -> Pack Item -> GTIN_TU
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> GTIN_TU
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- 2024-09-18T12:31:24.471Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588985,729891,0,546396,TO_TIMESTAMP('2024-09-18 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','GTIN_TU',TO_TIMESTAMP('2024-09-18 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-18T12:31:24.473Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-18T12:31:24.475Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583258) 
;

-- 2024-09-18T12:31:24.479Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729891
;

-- 2024-09-18T12:31:24.481Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729891)
;

-- Field: EDI Lieferavis Pack (DESADV) -> Pack Item -> TU-EAN
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> TU-EAN
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- 2024-09-18T12:31:24.589Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588986,729892,0,546396,TO_TIMESTAMP('2024-09-18 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','TU-EAN',TO_TIMESTAMP('2024-09-18 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-18T12:31:24.592Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-18T12:31:24.595Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577133) 
;

-- 2024-09-18T12:31:24.605Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729892
;

-- 2024-09-18T12:31:24.607Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729892)
;

-- Field: EDI Lieferavis Pack (DESADV) -> Pack Item -> TU-UPC
-- Column: EDI_Desadv_Pack_Item.UPC_TU
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> TU-UPC
-- Column: EDI_Desadv_Pack_Item.UPC_TU
-- 2024-09-18T12:31:24.709Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588987,729893,0,546396,TO_TIMESTAMP('2024-09-18 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','TU-UPC',TO_TIMESTAMP('2024-09-18 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-18T12:31:24.711Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-18T12:31:24.714Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577137) 
;

-- 2024-09-18T12:31:24.721Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729893
;

-- 2024-09-18T12:31:24.723Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729893)
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Sektion
-- Column: EDI_Desadv_Pack_Item.AD_Org_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Sektion
-- Column: EDI_Desadv_Pack_Item.AD_Org_ID
-- 2024-09-18T12:32:10.824Z
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2024-09-18 14:32:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609680
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Aktiv
-- Column: EDI_Desadv_Pack_Item.IsActive
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Aktiv
-- Column: EDI_Desadv_Pack_Item.IsActive
-- 2024-09-18T12:32:13.201Z
UPDATE AD_UI_Element SET SeqNo=160,Updated=TO_TIMESTAMP('2024-09-18 14:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609679
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.TU Verpackungscode
-- Column: EDI_Desadv_Pack_Item.M_HU_PackagingCode_TU_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.TU Verpackungscode
-- Column: EDI_Desadv_Pack_Item.M_HU_PackagingCode_TU_ID
-- 2024-09-18T12:32:15.241Z
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2024-09-18 14:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609684
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.TU Gebinde-GTIN
-- Column: EDI_Desadv_Pack_Item.GTIN_TU_PackingMaterial
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.TU Gebinde-GTIN
-- Column: EDI_Desadv_Pack_Item.GTIN_TU_PackingMaterial
-- 2024-09-18T12:32:17.797Z
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2024-09-18 14:32:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609683
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.GTIN_TU
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.GTIN_TU
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- 2024-09-18T12:32:52.346Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729891,0,546396,549399,625344,'F',TO_TIMESTAMP('2024-09-18 14:32:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'GTIN_TU',130,0,0,TO_TIMESTAMP('2024-09-18 14:32:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.GTIN_TU
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.GTIN_TU
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- 2024-09-18T12:34:23.833Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-09-18 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625344
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.TU Anzahl
-- Column: EDI_Desadv_Pack_Item.QtyTU
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.TU Anzahl
-- Column: EDI_Desadv_Pack_Item.QtyTU
-- 2024-09-18T12:34:23.851Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-18 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609674
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Menge CU/TU
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Menge CU/TU
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU
-- 2024-09-18T12:34:23.868Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-18 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609675
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.QtyCUsPerLU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.QtyCUsPerLU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- 2024-09-18T12:34:23.884Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-09-18 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621315
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Menge CU/LU
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Menge CU/LU
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU
-- 2024-09-18T12:34:23.899Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-09-18 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609676
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.QtyCUsPerTU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.QtyCUsPerTU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- 2024-09-18T12:34:23.916Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-09-18 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621314
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Mindesthaltbarkeitsdatum
-- Column: EDI_Desadv_Pack_Item.BestBeforeDate
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Mindesthaltbarkeitsdatum
-- Column: EDI_Desadv_Pack_Item.BestBeforeDate
-- 2024-09-18T12:34:23.930Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-09-18 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609677
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Chargennummer
-- Column: EDI_Desadv_Pack_Item.LotNumber
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Chargennummer
-- Column: EDI_Desadv_Pack_Item.LotNumber
-- 2024-09-18T12:34:23.947Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-09-18 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609678
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Aktiv
-- Column: EDI_Desadv_Pack_Item.IsActive
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Aktiv
-- Column: EDI_Desadv_Pack_Item.IsActive
-- 2024-09-18T12:34:23.961Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-09-18 14:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609679
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.EAN_TU
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.EAN_TU
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- 2024-09-18T12:35:59.789Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729892,0,546396,549399,625345,'F',TO_TIMESTAMP('2024-09-18 14:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'EAN_TU',135,0,0,TO_TIMESTAMP('2024-09-18 14:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: TU_GTIN
-- 2024-09-18T12:36:25.045Z
UPDATE AD_Element_Trl SET Name='TU-GTIN',Updated=TO_TIMESTAMP('2024-09-18 14:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583258 AND AD_Language='de_CH'
;

-- 2024-09-18T12:36:25.051Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583258,'de_CH') 
;

-- Element: TU_GTIN
-- 2024-09-18T12:36:27.615Z
UPDATE AD_Element_Trl SET Name='TU-GTIN',Updated=TO_TIMESTAMP('2024-09-18 14:36:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583258 AND AD_Language='de_DE'
;

-- 2024-09-18T12:36:27.619Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583258,'de_DE') 
;

-- 2024-09-18T12:36:27.626Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583258,'de_DE') 
;

-- Element: TU_GTIN
-- 2024-09-18T12:36:31.154Z
UPDATE AD_Element_Trl SET Name='TU-GTIN',Updated=TO_TIMESTAMP('2024-09-18 14:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583258 AND AD_Language='en_US'
;

-- 2024-09-18T12:36:31.159Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583258,'en_US') 
;

-- Element: TU_GTIN
-- 2024-09-18T12:36:34.379Z
UPDATE AD_Element_Trl SET Name='TU-GTIN',Updated=TO_TIMESTAMP('2024-09-18 14:36:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583258 AND AD_Language='fr_CH'
;

-- 2024-09-18T12:36:34.383Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583258,'fr_CH') 
;

-- Element: TU_GTIN
-- 2024-09-18T12:36:36.208Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-18 14:36:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583258 AND AD_Language='fr_CH'
;

-- 2024-09-18T12:36:36.212Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583258,'fr_CH') 
;

-- 2024-09-18T12:37:03.400Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550238
;

-- 2024-09-18T12:37:07.955Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550236
;

-- 2024-09-18T12:37:48.237Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588986,0,TO_TIMESTAMP('2024-09-18 14:37:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540418,550787,'N','N','TU-EAN',270,'E',TO_TIMESTAMP('2024-09-18 14:37:48','YYYY-MM-DD HH24:MI:SS'),100,'EAN_TU')
;

-- 2024-09-18T12:37:48.351Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588987,0,TO_TIMESTAMP('2024-09-18 14:37:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540418,550788,'N','N','TU-UPC',280,'E',TO_TIMESTAMP('2024-09-18 14:37:48','YYYY-MM-DD HH24:MI:SS'),100,'UPC_TU')
;

-- 2024-09-18T12:37:59.751Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-09-18 14:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550787
;

-- 2024-09-18T12:51:50.006Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-09-18 14:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550788
;

-- 2024-09-18T12:51:54.608Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550786
;

-- 2024-09-18T12:51:57.444Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550787
;

-- 2024-09-18T12:52:00.279Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550788
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.GTIN_TU
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.GTIN_TU
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- 2024-09-18T13:10:32.840Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625344
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.EAN_TU
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.EAN_TU
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- 2024-09-18T13:10:35.632Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625345
;

-- 2024-09-18T13:10:45.531Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729891
;

-- Field: EDI Lieferavis Pack (DESADV) -> Pack Item -> TU-GTIN
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> TU-GTIN
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- 2024-09-18T13:10:45.543Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=729891
;

-- 2024-09-18T13:10:45.552Z
DELETE FROM AD_Field WHERE AD_Field_ID=729891
;

-- 2024-09-18T13:10:56.925Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729893
;

-- Field: EDI Lieferavis Pack (DESADV) -> Pack Item -> TU-UPC
-- Column: EDI_Desadv_Pack_Item.UPC_TU
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> TU-UPC
-- Column: EDI_Desadv_Pack_Item.UPC_TU
-- 2024-09-18T13:10:56.936Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=729893
;

-- 2024-09-18T13:10:56.947Z
DELETE FROM AD_Field WHERE AD_Field_ID=729893
;

-- 2024-09-18T13:11:04.918Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729892
;

-- Field: EDI Lieferavis Pack (DESADV) -> Pack Item -> TU-EAN
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> TU-EAN
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- 2024-09-18T13:11:04.929Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=729892
;

-- 2024-09-18T13:11:04.939Z
DELETE FROM AD_Field WHERE AD_Field_ID=729892
;


-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- Column: EDI_Desadv_Pack_Item.TU_GTIN
-- 2024-09-18T13:12:07.588Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588985
;

-- 2024-09-18T13:12:07.596Z
DELETE FROM AD_Column WHERE AD_Column_ID=588985
;

-- Column: EDI_Desadv_Pack_Item.UPC_TU
-- Column: EDI_Desadv_Pack_Item.UPC_TU
-- 2024-09-18T13:12:25.786Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588987
;

-- 2024-09-18T13:12:25.802Z
DELETE FROM AD_Column WHERE AD_Column_ID=588987
;

-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- Column: EDI_Desadv_Pack_Item.EAN_TU
-- 2024-09-18T13:12:38.273Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588986
;

-- 2024-09-18T13:12:38.282Z
DELETE FROM AD_Column WHERE AD_Column_ID=588986
;


SELECT public.db_alter_table('EDI_Desadv_Pack_Item','ALTER TABLE public.EDI_Desadv_Pack_Item DROP COLUMN TU_GTIN');
SELECT public.db_alter_table('EDI_Desadv_Pack_Item','ALTER TABLE public.EDI_Desadv_Pack_Item DROP COLUMN UPC_TU');
SELECT public.db_alter_table('EDI_Desadv_Pack_Item','ALTER TABLE public.EDI_Desadv_Pack_Item DROP COLUMN EAN_TU');

-- Column: EDI_DesadvLine.UPC_TU
-- Column: EDI_DesadvLine.UPC_TU
-- 2024-09-18T13:14:19.378Z
UPDATE AD_Column SET IsActive='Y', TechnicalNote='',Updated=TO_TIMESTAMP('2024-09-18 15:14:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568985
;

-- Column: EDI_DesadvLine.EAN_TU
-- Column: EDI_DesadvLine.EAN_TU
-- 2024-09-18T13:14:27.742Z
UPDATE AD_Column SET TechnicalNote='',Updated=TO_TIMESTAMP('2024-09-18 15:14:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568980
;

-- Column: EDI_DesadvLine.TU_GTIN
-- Column: EDI_DesadvLine.TU_GTIN
-- 2024-09-18T13:14:47.692Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588988,583258,0,10,540645,'XX','TU_GTIN',TO_TIMESTAMP('2024-09-18 15:14:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,50,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'TU-GTIN',0,0,TO_TIMESTAMP('2024-09-18 15:14:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-18T13:14:47.695Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588988 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-18T13:14:47.701Z
/* DDL */  select update_Column_Translation_From_AD_Element(583258) 
;

-- 2024-09-18T13:14:49.316Z
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN TU_GTIN VARCHAR(50)')
;

-- 2024-09-18T13:15:39.006Z
UPDATE EXP_FormatLine SET Value='UPC_CU',Updated=TO_TIMESTAMP('2024-09-18 15:15:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550041
;

-- 2024-09-18T13:15:50.276Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,568980,0,TO_TIMESTAMP('2024-09-18 15:15:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550789,'N','N','TU-EAN',480,'E',TO_TIMESTAMP('2024-09-18 15:15:50','YYYY-MM-DD HH24:MI:SS'),100,'EAN_TU')
;

-- 2024-09-18T13:15:50.410Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588988,0,TO_TIMESTAMP('2024-09-18 15:15:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550790,'N','N','TU-GTIN',490,'E',TO_TIMESTAMP('2024-09-18 15:15:50','YYYY-MM-DD HH24:MI:SS'),100,'TU_GTIN')
;

-- 2024-09-18T13:15:50.513Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,568985,0,TO_TIMESTAMP('2024-09-18 15:15:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550791,'N','N','TU-UPC',500,'E',TO_TIMESTAMP('2024-09-18 15:15:50','YYYY-MM-DD HH24:MI:SS'),100,'UPC_TU')
;

-- 2024-09-18T13:15:58.977Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-09-18 15:15:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550789
;

-- 2024-09-18T13:15:59.566Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-09-18 15:15:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550790
;

-- 2024-09-18T13:16:08.729Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-09-18 15:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550791
;

-- 2024-09-18T13:16:29.874Z
UPDATE EXP_FormatLine SET Position=280,Updated=TO_TIMESTAMP('2024-09-18 15:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550789
;

-- 2024-09-18T13:16:32.047Z
UPDATE EXP_FormatLine SET Position=290,Updated=TO_TIMESTAMP('2024-09-18 15:16:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550790
;

-- 2024-09-18T13:17:16.259Z
UPDATE AD_Element SET ColumnName='GTIN_TU',Updated=TO_TIMESTAMP('2024-09-18 15:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583258
;

-- 2024-09-18T13:17:16.267Z
UPDATE AD_Column SET ColumnName='GTIN_TU' WHERE AD_Element_ID=583258
;

-- 2024-09-18T13:17:16.270Z
UPDATE AD_Process_Para SET ColumnName='GTIN_TU' WHERE AD_Element_ID=583258
;

-- 2024-09-18T13:17:16.278Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583258,'de_DE') 
;

-- 2024-09-18T13:24:51.186Z
UPDATE EXP_FormatLine SET Value='GTIN_TU',Updated=TO_TIMESTAMP('2024-09-18 15:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550790
;

-- 2024-09-18T14:59:58.970Z
UPDATE EXP_FormatLine SET Name='SeqNo', Value='SeqNo',Updated=TO_TIMESTAMP('2024-09-18 16:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550771
;

-- 2024-09-19T07:41:25.708Z
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN GTIN_TU VARCHAR(50)')
;

-- 2024-09-19T14:56:44.083Z
UPDATE EXP_FormatLine SET Name='M_HU_PackagingCode_Text', Value='M_HU_PackagingCode_Text',Updated=TO_TIMESTAMP('2024-09-19 16:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550460
;

-- 2024-09-19T14:56:54.145Z
UPDATE EXP_FormatLine SET Value='GTIN_PackingMaterial',Updated=TO_TIMESTAMP('2024-09-19 16:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550453
;

-- Column: EDI_Desadv_Pack.M_HU_PackagingCode_Text
-- Column SQL (old): (select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_ID)
-- Column: EDI_Desadv_Pack.M_HU_PackagingCode_Text
-- Column SQL (old): (select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_ID)
-- 2024-09-19T14:59:35.092Z
UPDATE AD_Column SET ColumnSQL='(select c.PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=EDI_Desadv_Pack.M_HU_PackagingCode_ID)',Updated=TO_TIMESTAMP('2024-09-19 16:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583430
;

