-- 2023-04-24T07:50:21.443Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582257,0,'ShipmentDocumentNo',TO_TIMESTAMP('2023-04-24 10:50:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','Shipment Document No.','Shipment Document No.',TO_TIMESTAMP('2023-04-24 10:50:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-24T07:50:21.443Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582257 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ShipmentDocumentNo
-- 2023-04-24T07:50:35.057Z
UPDATE AD_Element_Trl SET Name='Versandpapier-Nr.', PrintName='Versandpapier-Nr.',Updated=TO_TIMESTAMP('2023-04-24 10:50:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582257 AND AD_Language='de_CH'
;

-- 2023-04-24T07:50:35.088Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582257,'de_CH') 
;

-- Element: ShipmentDocumentNo
-- 2023-04-24T07:50:40.635Z
UPDATE AD_Element_Trl SET Name='Versandpapier-Nr.', PrintName='Versandpapier-Nr.',Updated=TO_TIMESTAMP('2023-04-24 10:50:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582257 AND AD_Language='de_DE'
;

-- 2023-04-24T07:50:40.635Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582257,'de_DE') 
;

-- 2023-04-24T07:50:40.650Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582257,'de_DE') 
;

-- Element: ShipmentDocumentNo
-- 2023-04-24T07:50:47.151Z
UPDATE AD_Element_Trl SET Name='Versandpapier-Nr.', PrintName='Versandpapier-Nr.',Updated=TO_TIMESTAMP('2023-04-24 10:50:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582257 AND AD_Language='fr_CH'
;

-- 2023-04-24T07:50:47.151Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582257,'fr_CH') 
;

-- Element: ShipmentDocumentNo
-- 2023-04-24T07:50:55.210Z
UPDATE AD_Element_Trl SET Name='Versandpapier-Nr.', PrintName='Versandpapier-Nr.',Updated=TO_TIMESTAMP('2023-04-24 10:50:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582257 AND AD_Language='nl_NL'
;

-- 2023-04-24T07:50:55.210Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582257,'nl_NL') 
;

-- Column: EDI_Desadv.ShipmentDocumentNo
-- Column SQL (old): null
-- Column: EDI_Desadv.ShipmentDocumentNo
-- 2023-04-24T12:53:21.882Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586492,582257,0,10,540644,'ShipmentDocumentNo','(select string_agg(m.documentno, '','')         from M_InOut m         where m.edi_desadv_id is not null           and m.edi_desadv_id = EDI_Desadv.edi_desadv_id)',TO_TIMESTAMP('2023-04-24 15:53:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Versandpapier-Nr.',0,0,TO_TIMESTAMP('2023-04-24 15:53:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-24T12:53:21.888Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586492 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-24T12:53:21.898Z
/* DDL */  select update_Column_Translation_From_AD_Element(582257) 
;

-- Field: EDI Lieferavis (DESADV) -> DESADV -> Versandpapier-Nr.
-- Column: EDI_Desadv.ShipmentDocumentNo
-- Field: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> DESADV(540662,de.metas.esb.edi) -> Versandpapier-Nr.
-- Column: EDI_Desadv.ShipmentDocumentNo
-- 2023-04-24T12:55:40.621Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586492,715030,0,540662,TO_TIMESTAMP('2023-04-24 15:55:40','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Versandpapier-Nr.',TO_TIMESTAMP('2023-04-24 15:55:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-24T12:55:40.621Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-24T12:55:40.621Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582257) 
;

-- 2023-04-24T12:55:40.649Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715030
;

-- 2023-04-24T12:55:40.653Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715030)
;

-- UI Element: EDI Lieferavis (DESADV) -> DESADV.Versandpapier-Nr.
-- Column: EDI_Desadv.ShipmentDocumentNo
-- UI Element: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> DESADV(540662,de.metas.esb.edi) -> main -> 10 -> further_infos.Versandpapier-Nr.
-- Column: EDI_Desadv.ShipmentDocumentNo
-- 2023-04-24T12:56:20.905Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715030,0,540662,617261,543298,'F',TO_TIMESTAMP('2023-04-24 15:56:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Versandpapier-Nr.',40,0,0,TO_TIMESTAMP('2023-04-24 15:56:20','YYYY-MM-DD HH24:MI:SS'),100)
;

