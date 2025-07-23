-- Run mode: SWING_CLIENT

-- Column: C_OrderLine.M_LU_HU_PI_ID
-- 2025-07-21T05:17:48.654Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590569,542487,0,30,540396,260,'XX','M_LU_HU_PI_ID',TO_TIMESTAMP('2025-07-21 05:17:48.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Packvorschrift (LU)',0,0,TO_TIMESTAMP('2025-07-21 05:17:48.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-21T05:17:48.657Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590569 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-21T05:17:48.682Z
/* DDL */  select update_Column_Translation_From_AD_Element(542487)
;

-- Name: M_HU_PI Only LUs
-- 2025-07-21T05:41:02.256Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540737,'EXISTS (SELECT 1 FROM m_hu_pi_version piv WHERE piv.M_HU_PI_ID = M_HU_PI.M_HU_PI_ID AND piv.isactive = ''Y'' AND piv.iscurrent = ''Y'' AND piv.hu_unittype IN (''LU'', ''V'')) AND M_HU_PI.M_HU_PI_ID != 100',TO_TIMESTAMP('2025-07-21 05:41:02.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','M_HU_PI Only LUs','S',TO_TIMESTAMP('2025-07-21 05:41:02.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_OrderLine.M_LU_HU_PI_ID
-- 2025-07-21T05:41:11.922Z
UPDATE AD_Column SET AD_Val_Rule_ID=540737,Updated=TO_TIMESTAMP('2025-07-21 05:41:11.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590569
;

-- 2025-07-21T05:44:37.433Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN M_LU_HU_PI_ID NUMERIC(10)')
;

-- 2025-07-21T05:44:38.605Z
ALTER TABLE C_OrderLine ADD CONSTRAINT MLUHUPI_COrderLine FOREIGN KEY (M_LU_HU_PI_ID) REFERENCES public.M_HU_PI DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_OrderLine.QtyLU
-- 2025-07-21T05:46:29.256Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590570,542491,0,29,260,'XX','QtyLU',TO_TIMESTAMP('2025-07-21 05:46:29.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'LU Anzahl',0,0,TO_TIMESTAMP('2025-07-21 05:46:29.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-21T05:46:29.258Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590570 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-21T05:46:29.359Z
/* DDL */  select update_Column_Translation_From_AD_Element(542491)
;

-- 2025-07-21T05:46:31.388Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN QtyLU NUMERIC')
;

-- 2025-07-21T06:07:23.090Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583817,0,TO_TIMESTAMP('2025-07-21 06:07:22.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Menge LU','Menge LU',TO_TIMESTAMP('2025-07-21 06:07:22.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-21T06:07:23.093Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583817 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-07-21T06:07:43.835Z
UPDATE AD_Element_Trl SET Name='Quantity TU', PrintName='Quantity TU',Updated=TO_TIMESTAMP('2025-07-21 06:07:43.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583817 AND AD_Language='en_US'
;

-- 2025-07-21T06:07:43.836Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-21T06:07:44.101Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583817,'en_US')
;

-- Name: M_HU_PI_Item_Product for selected BP / M_LU_HU_PI_ID
-- 2025-07-21T14:51:10.965Z
INSERT INTO AD_Val_Rule (ad_val_rule_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, type, code, entitytype, classname) VALUES (540738, 0, 0, 'Y', '2025-07-21 14:49:43.620000 +00:00', 100, '2025-07-21 14:51:10.963000 +00:00', 100, 'M_HU_PI_Item_Product for selected BP / M_LU_HU_PI_ID', null, 'S', e'/* GENERAL*/
    M_HU_PI_Item_Product.AD_Org_ID IN (0, @AD_Org_ID@)
  AND (M_HU_PI_Item_Product.M_Product_ID =@ M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct = \'Y\' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID NOT IN (100)))
  AND (M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
  AND M_HU_PI_Item_Product.ValidFrom <= \'@DatePromised@\'
  AND (\'@DatePromised@\' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL)
  AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
      (SELECT i.M_HU_PI_Item_ID
       FROM M_HU_PI_Item i
       WHERE i.M_HU_PI_Version_ID IN
             (SELECT v.M_HU_PI_Version_ID
              FROM M_HU_PI_Version v
              WHERE v.HU_UnitType = \'TU\'))
  /* Price list*/
  AND (
    (
        M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID IN
        (SELECT pp.M_HU_PI_Item_Product_ID
         FROM M_ProductPrice pp
         WHERE pp.M_Product_ID = @M_Product_ID@
           AND pp.IsActive = \'Y\'
           AND pp.M_PriceList_Version_ID =
               (SELECT M_PriceList_Version.M_PriceList_Version_ID
                FROM M_PriceList_Version
                WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
                  AND M_PriceList_Version.IsActive = \'Y\'
                  AND M_PriceList_Version.ValidFrom =
                      (SELECT MAX(M_PriceList_Version.ValidFrom)
                       FROM M_PriceList_Version
                       WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
                         AND M_PriceList_Version.ValidFrom <= \'@DatePromised@\'
                         AND M_PriceList_Version.IsActive = \'Y\'
                       GROUP BY M_PriceList_ID)))
        )
        OR M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID IN
           (SELECT pp.M_HU_PI_Item_Product_ID
            FROM M_ProductPrice pp
            WHERE pp.M_Product_ID = @M_Product_ID@
              AND pp.IsActive = \'Y\'
              AND pp.M_PriceList_Version_ID =
                  (SELECT M_PriceList_Version.M_PriceList_Version_ID
                   FROM M_PriceList_Version
                   WHERE M_PriceList_Version.M_PriceList_ID = (SELECT basepricelist_id FROM M_Pricelist WHERE m_pricelist_id = @M_PriceList_ID@)
                     AND M_PriceList_Version.IsActive = \'Y\'
                     AND M_PriceList_Version.ValidFrom =
                         (SELECT MAX(M_PriceList_Version.ValidFrom)
                          FROM M_PriceList_Version
                          WHERE M_PriceList_Version.m_pricelist_id = (SELECT basepricelist_id FROM M_Pricelist WHERE m_pricelist_id = @M_PriceList_ID@)
                            AND M_PriceList_Version.ValidFrom <= \'@DatePromised@\'
                            AND M_PriceList_Version.IsActive = \'Y\'
                          GROUP BY M_PriceList_ID)))
        OR EXISTS
        (SELECT 1
         FROM M_ProductPrice pp
         WHERE pp.M_Product_ID = @M_Product_ID@
           AND pp.IsActive = \'Y\'
           AND pp.M_PriceList_Version_ID = (SELECT M_PriceList_Version.M_PriceList_Version_ID
                                            FROM M_PriceList_Version
                                            WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
                                              AND M_PriceList_Version.IsActive = \'Y\'
                                              AND M_PriceList_Version.ValidFrom =
                                                  (SELECT MAX(M_PriceList_Version.ValidFrom)
                                                   FROM M_PriceList_Version
                                                   WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
                                                     AND M_PriceList_Version.ValidFrom <= \'@DatePromised@\'
                                                     AND M_PriceList_Version.IsActive = \'Y\'
                                                   GROUP BY M_PriceList_ID))
           AND pp.m_hu_pi_item_product_id IS NULL)
        OR EXISTS
        (SELECT 1
         FROM M_ProductPrice pp
         WHERE pp.M_Product_ID = @M_Product_ID@
           AND pp.IsActive = \'Y\'
           AND pp.M_PriceList_Version_ID = (SELECT M_PriceList_Version.M_PriceList_Version_ID
                                            FROM M_PriceList_Version
                                            WHERE M_PriceList_Version.M_PriceList_ID = (SELECT basepricelist_id FROM M_Pricelist WHERE m_pricelist_id = @M_PriceList_ID@)
                                              AND M_PriceList_Version.IsActive = \'Y\'
                                              AND M_PriceList_Version.ValidFrom =
                                                  (SELECT MAX(M_PriceList_Version.ValidFrom)
                                                   FROM M_PriceList_Version
                                                   WHERE M_PriceList_Version.M_PriceList_ID = (SELECT basepricelist_id FROM M_Pricelist WHERE m_pricelist_id = @M_PriceList_ID@)
                                                     AND M_PriceList_Version.ValidFrom <= \'@DatePromised@\'
                                                     AND M_PriceList_Version.IsActive = \'Y\'
                                                   GROUP BY M_PriceList_ID))
           AND pp.m_hu_pi_item_product_id IS NULL)
    )
 /*Only those that can be packed in M_LU_HU_PI_ID*/
AND EXISTS (SELECT 1
              FROM M_HU_PI_Version lu_hupiVersion
                       INNER JOIN m_hu_pi_item lu_hupiItem ON lu_hupiItem.m_hu_pi_version_id = lu_hupiVersion.m_hu_pi_version_id AND (lu_hupiItem.C_BPartner_ID = @C_BPartner_ID/-1@ OR lu_hupiItem.C_BPartner_ID IS NULL)
                       INNER JOIN m_hu_pi hupi ON lu_hupiItem.included_hu_pi_id = hupi.m_hu_pi_id AND lu_hupiItem.isactive = \'Y\'
                  AND lu_hupiItem.itemtype = \'HU\'
                  AND lu_hupiVersion.isactive = \'Y\'
                  AND lu_hupiVersion.iscurrent = \'Y\'
                  AND lu_hupiVersion.hu_unittype = \'LU\'
                       INNER JOIN m_hu_pi_version tu_hupiVersion ON lu_hupiItem.included_hu_pi_id = tu_hupiVersion.m_hu_pi_id
                  AND tu_hupiVersion.iscurrent = \'Y\'
                  AND tu_hupiVersion.isactive = \'Y\'
                  AND tu_hupiVersion.hu_unittype = \'TU\'
                       INNER JOIN m_hu_pi_item tu_hupiItem ON tu_hupiItem.m_hu_pi_version_id = tu_hupiVersion.m_hu_pi_version_id AND tu_hupiItem.itemtype = \'MI\' AND tu_hupiItem.isactive = \'Y\' AND (tu_hupiItem.C_BPartner_ID = @C_BPartner_ID/-1@ OR tu_hupiItem.C_BPartner_ID IS NULL)
                       INNER JOIN m_hu_pi_item_product tu_hupiip ON tu_hupiip.m_hu_pi_item_id = tu_hupiItem.m_hu_pi_item_id AND tu_hupiip.isactive = \'Y\' and tu_hupiip.M_Product_ID = @M_Product_ID/-1@
              WHERE lu_hupiVersion.m_hu_pi_id = @M_LU_HU_PI_ID/-1@
                AND m_hu_pi_item_product.m_hu_pi_item_product_id = tu_hupiip.m_hu_pi_item_product_id)', 'D', null)
;

-- SysConfig Name: webui.quickinput.EnableLUFields
-- SysConfig Value: N
-- 2025-07-21T15:19:52.441Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541767,'S',TO_TIMESTAMP('2025-07-21 15:19:52.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enable the LU related fields in Order Line quick input.
','D','Y','webui.quickinput.EnableLUFields',TO_TIMESTAMP('2025-07-21 15:19:52.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- Field: Bestellung(181,D) -> Bestellposition(293,D) -> LU Anzahl
-- Column: C_OrderLine.QtyLU
-- 2025-07-22T15:34:14.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590570,750396,0,293,0,TO_TIMESTAMP('2025-07-22 15:34:14.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.handlingunits',0,0,'Y','Y','Y','N','N','N','N','N','Y','N',0,'LU Anzahl',0,0,170,0,1,1,TO_TIMESTAMP('2025-07-22 15:34:14.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-22T15:34:14.428Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750396 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-22T15:34:14.430Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542491)
;

-- 2025-07-22T15:34:14.436Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750396
;

-- 2025-07-22T15:34:14.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750396)
;

-- Field: Bestellung(181,D) -> Bestellposition(293,D) -> Packvorschrift (LU)
-- Column: C_OrderLine.M_LU_HU_PI_ID
-- 2025-07-22T15:34:27.876Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590569,750397,0,293,0,TO_TIMESTAMP('2025-07-22 15:34:27.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.handlingunits',0,0,'Y','Y','Y','N','N','N','N','N','Y','N',0,'Packvorschrift (LU)',0,0,180,0,1,1,TO_TIMESTAMP('2025-07-22 15:34:27.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-22T15:34:27.877Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750397 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-22T15:34:27.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542487)
;

-- 2025-07-22T15:34:27.881Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750397
;

-- 2025-07-22T15:34:27.882Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750397)
;

-- UI Element: Bestellung(181,D) -> Bestellposition(293,D) -> main -> 10 -> main.LU Anzahl
-- Column: C_OrderLine.QtyLU
-- 2025-07-22T15:34:53.753Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750396,0,293,540927,635307,'F',TO_TIMESTAMP('2025-07-22 15:34:53.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'LU Anzahl',260,0,0,TO_TIMESTAMP('2025-07-22 15:34:53.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung(181,D) -> Bestellposition(293,D) -> main -> 10 -> main.Packvorschrift (LU)
-- Column: C_OrderLine.M_LU_HU_PI_ID
-- 2025-07-22T15:35:03.735Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750397,0,293,540927,635308,'F',TO_TIMESTAMP('2025-07-22 15:35:03.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Packvorschrift (LU)',270,0,0,TO_TIMESTAMP('2025-07-22 15:35:03.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

