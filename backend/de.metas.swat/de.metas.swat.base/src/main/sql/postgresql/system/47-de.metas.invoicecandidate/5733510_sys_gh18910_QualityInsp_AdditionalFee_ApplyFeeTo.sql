-- 2024-09-10T10:08:20.608Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554812
;

-- Field: Lagerkonferenz -> Zusätzlicher Beitrag -> Beitrag pro Einheit
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- Field: Lagerkonferenz(540230,de.metas.materialtracking.ch.lagerkonf) -> Zusätzlicher Beitrag(540620,de.metas.materialtracking.ch.lagerkonf) -> Beitrag pro Einheit
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- 2024-09-10T10:08:20.727Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554812
;

-- 2024-09-10T10:08:20.746Z
DELETE FROM AD_Field WHERE AD_Field_ID=554812
;

-- UI Element: Lagerkonferenz Version -> Zusätzlicher Beitrag.Beitrag pro Einheit
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- UI Element: Lagerkonferenz Version(540364,de.metas.materialtracking.ch.lagerkonf) -> Zusätzlicher Beitrag(540869,de.metas.materialtracking.ch.lagerkonf) -> main -> 10 -> default.Beitrag pro Einheit
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- 2024-09-10T10:08:20.926Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548409
;

-- 2024-09-10T10:08:20.929Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=559893
;

-- Field: Lagerkonferenz Version -> Zusätzlicher Beitrag -> Beitrag pro Einheit
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- Field: Lagerkonferenz Version(540364,de.metas.materialtracking.ch.lagerkonf) -> Zusätzlicher Beitrag(540869,de.metas.materialtracking.ch.lagerkonf) -> Beitrag pro Einheit
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- 2024-09-10T10:08:20.935Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=559893
;

-- 2024-09-10T10:08:20.938Z
DELETE FROM AD_Field WHERE AD_Field_ID=559893
;

-- 2024-09-10T10:08:20.992Z
/* DDL */ SELECT public.db_alter_table('M_QualityInsp_LagerKonf_AdditionalFee','ALTER TABLE M_QualityInsp_LagerKonf_AdditionalFee DROP COLUMN IF EXISTS Additional_Fee_Amt_Per_UOM')
;

-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- 2024-09-10T10:08:21.068Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551269
;

-- 2024-09-10T10:08:21.072Z
DELETE FROM AD_Column WHERE AD_Column_ID=551269
;

-- Name: QualityInsp_AdditionalFee_ApplyFeeTo
-- 2024-09-16T14:13:51.306Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541889,TO_TIMESTAMP('2024-09-16 14:13:51.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','QualityInsp_AdditionalFee_ApplyFeeTo',TO_TIMESTAMP('2024-09-16 14:13:51.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2024-09-16T14:13:51.309Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541889 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: QualityInsp_AdditionalFee_ApplyFeeTo
-- Value: ProducedTotalWithoutByProducts
-- ValueName: Produced Total Without By Products
-- 2024-09-16T14:16:39.980Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543713,541889,TO_TIMESTAMP('2024-09-16 14:16:39.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Total Produced without By-Products (Main + Co-Products)','D','Y','Produced Total Without By Products',TO_TIMESTAMP('2024-09-16 14:16:39.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ProducedTotalWithoutByProducts','Produced Total Without By Products')
;

-- 2024-09-16T14:16:39.983Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543713 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET name='Produziert Insgesamt ohne Nebenprodukte', description='Gesamterzeugung ohne Nebenerzeugnisse (Haupt- und Nebenerzeugnisse)', updated = TO_TIMESTAMP('2024-09-16 14:16:39.844000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', updatedby = 100 where ad_ref_list_id = 543713 and ad_language IN ('de_CH', 'de_DE', 'fr_CH', 'nl_NL')
;

-- Reference: QualityInsp_AdditionalFee_ApplyFeeTo
-- Value: Raw
-- ValueName: Raw
-- 2024-09-16T14:22:49.667Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543714,541889,TO_TIMESTAMP('2024-09-16 14:22:49.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RAW Material','D','Y','Raw',TO_TIMESTAMP('2024-09-16 14:22:49.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Raw','Raw')
;

-- 2024-09-16T14:22:49.670Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543714 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET name='Produziert Insgesamt ohne Nebenprodukte', description='Gesamterzeugung ohne Nebenerzeugnisse (Haupt- und Nebenerzeugnisse)', updated = TO_TIMESTAMP('2024-09-16 14:16:39.844000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', updatedby = 100 where ad_ref_list_id = 543713 and ad_language IN ('de_CH', 'de_DE', 'fr_CH', 'nl_NL')
;

UPDATE AD_Ref_List_Trl SET name='Insgesamt empfangenes Rohmaterial', description='RAW-Material', updated = TO_TIMESTAMP('2024-09-16 14:16:39.844000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', updatedby = 100 where ad_ref_list_id = 543714 and ad_language IN ('de_CH', 'de_DE', 'fr_CH', 'nl_NL')
;

-- 2024-09-16T14:33:06.408Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583255,0,'ApplyFeeTo',TO_TIMESTAMP('2024-09-16 14:33:06.214000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gebühr auf Qualitätsprüfungslinienart anwenden','D','Y','Gebühr anwenden auf','Gebühr anwenden auf',TO_TIMESTAMP('2024-09-16 14:33:06.214000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-16T14:33:06.425Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583255 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE ad_element_trl SET name='Apply Fee To', description='Apply Fee To Quality Inspection Line Type', printname='Apply Fee To', updated = TO_TIMESTAMP('2024-09-16 14:16:39.844000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', updatedby = 100 where ad_element_id = 583255 and ad_language = 'en_US'
;
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- 2024-09-16T14:40:40.690Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588981,583255,0,10,540619,'ApplyFeeTo',TO_TIMESTAMP('2024-09-16 14:40:40.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Gebühr auf Qualitätsprüfungslinienart anwenden','de.metas.materialtracking.ch.lagerkonf',0,50,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Gebühr anwenden auf',0,0,TO_TIMESTAMP('2024-09-16 14:40:40.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-09-16T14:40:40.694Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588981 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-16T14:40:40.740Z
/* DDL */  select update_Column_Translation_From_AD_Element(583255) 
;

-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- 2024-09-16T14:41:03.157Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-16 14:41:03.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588981
;

-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- 2024-09-16T14:41:22.813Z
UPDATE AD_Column SET DefaultValue='ProducedTotalWithoutByProducts',Updated=TO_TIMESTAMP('2024-09-16 14:41:22.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588981
;

-- 2024-09-16T14:41:24.358Z
/* DDL */ SELECT public.db_alter_table('M_QualityInsp_LagerKonf_AdditionalFee','ALTER TABLE public.M_QualityInsp_LagerKonf_AdditionalFee ADD COLUMN ApplyFeeTo VARCHAR(50) DEFAULT ''ProducedTotalWithoutByProducts'' NOT NULL')
;

-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- 2024-09-16T14:47:33.343Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541889,Updated=TO_TIMESTAMP('2024-09-16 14:47:33.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588981
;

-- 2024-09-16T14:47:35.308Z
INSERT INTO t_alter_column values('m_qualityinsp_lagerkonf_additionalfee','ApplyFeeTo','VARCHAR(50)',null,'ProducedTotalWithoutByProducts')
;

-- 2024-09-16T14:47:35.318Z
UPDATE M_QualityInsp_LagerKonf_AdditionalFee SET ApplyFeeTo='ProducedTotalWithoutByProducts' WHERE ApplyFeeTo IS NULL
;

-- Field: Lagerkonferenz Version -> Zusätzlicher Beitrag -> Gebühr anwenden auf
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- Field: Lagerkonferenz Version(540364,de.metas.materialtracking.ch.lagerkonf) -> Zusätzlicher Beitrag(540869,de.metas.materialtracking.ch.lagerkonf) -> Gebühr anwenden auf
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- 2024-09-16T15:01:24.415Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588981,729888,0,540869,TO_TIMESTAMP('2024-09-16 15:01:24.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gebühr auf Qualitätsprüfungslinienart anwenden',50,'de.metas.materialtracking.ch.lagerkonf','Y','N','N','N','N','N','N','N','Gebühr anwenden auf',TO_TIMESTAMP('2024-09-16 15:01:24.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-16T15:01:24.419Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-16T15:01:24.422Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583255) 
;

-- 2024-09-16T15:01:24.453Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729888
;

-- 2024-09-16T15:01:24.461Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729888)
;

-- UI Element: Lagerkonferenz Version -> Zusätzlicher Beitrag.Gebühr anwenden auf
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- UI Element: Lagerkonferenz Version(540364,de.metas.materialtracking.ch.lagerkonf) -> Zusätzlicher Beitrag(540869,de.metas.materialtracking.ch.lagerkonf) -> main -> 10 -> default.Gebühr anwenden auf
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- 2024-09-16T15:01:59.497Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729888,0,540869,625342,541124,'F',TO_TIMESTAMP('2024-09-16 15:01:59.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gebühr auf Qualitätsprüfungslinienart anwenden','Y','N','N','Y','N','N','N',0,'Gebühr anwenden auf',25,0,0,TO_TIMESTAMP('2024-09-16 15:01:59.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lagerkonferenz Version -> Zusätzlicher Beitrag.Gebühr anwenden auf
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- UI Element: Lagerkonferenz Version(540364,de.metas.materialtracking.ch.lagerkonf) -> Zusätzlicher Beitrag(540869,de.metas.materialtracking.ch.lagerkonf) -> main -> 10 -> default.Gebühr anwenden auf
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.ApplyFeeTo
-- 2024-09-16T15:03:24.101Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-16 15:03:24.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625342
;

-- UI Element: Lagerkonferenz Version -> Zusätzlicher Beitrag.Beitrag pro Einheit
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- UI Element: Lagerkonferenz Version(540364,de.metas.materialtracking.ch.lagerkonf) -> Zusätzlicher Beitrag(540869,de.metas.materialtracking.ch.lagerkonf) -> main -> 10 -> default.Beitrag pro Einheit
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.Additional_Fee_Amt_Per_UOM
-- 2024-09-16T15:03:24.109Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-09-16 15:03:24.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548409
;

-- UI Element: Lagerkonferenz Version -> Zusätzlicher Beitrag.Aktiv
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.IsActive
-- UI Element: Lagerkonferenz Version(540364,de.metas.materialtracking.ch.lagerkonf) -> Zusätzlicher Beitrag(540869,de.metas.materialtracking.ch.lagerkonf) -> main -> 10 -> default.Aktiv
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.IsActive
-- 2024-09-16T15:03:24.120Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-09-16 15:03:24.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548408
;

-- UI Element: Lagerkonferenz Version -> Zusätzlicher Beitrag.Sektion
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.AD_Org_ID
-- UI Element: Lagerkonferenz Version(540364,de.metas.materialtracking.ch.lagerkonf) -> Zusätzlicher Beitrag(540869,de.metas.materialtracking.ch.lagerkonf) -> main -> 10 -> default.Sektion
-- Column: M_QualityInsp_LagerKonf_AdditionalFee.AD_Org_ID
-- 2024-09-16T15:03:24.130Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-09-16 15:03:24.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548410
;
