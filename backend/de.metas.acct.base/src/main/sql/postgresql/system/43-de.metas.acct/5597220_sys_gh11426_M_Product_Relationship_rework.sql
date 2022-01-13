-- 2021-07-07T12:18:20.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541366,TO_TIMESTAMP('2021-07-07 15:18:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Product_Relationship target for M_Product',TO_TIMESTAMP('2021-07-07 15:18:20','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-07-07T12:18:20.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541366 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-07-07T12:18:54.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540272,541077,540292,TO_TIMESTAMP('2021-07-07 15:18:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_Product -> PP_Product_Planning',TO_TIMESTAMP('2021-07-07 15:18:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-07T12:22:32.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1402,0,541366,208,TO_TIMESTAMP('2021-07-07 15:22:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-07-07 15:22:32','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS ( select 1 from M_Product pp  INNER JOIN M_Product_Relationship p ON pp.M_Product_ID = p.M_Product_ID where p.RelatedProduct_ID = @M_Product_ID@  AND pp.M_Product_ID = M_Product.M_Product_ID )')
;

-- 2021-07-07T12:24:12.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y', AD_Reference_Target_ID=541366, Name='M_Product -> M_Product(via M_Product_Relationship)',Updated=TO_TIMESTAMP('2021-07-07 15:24:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540292
;

-- 2021-07-07T12:43:17.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Relationstyp', PrintName='Relationstyp',Updated=TO_TIMESTAMP('2021-07-07 15:43:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54070 AND AD_Language='de_DE'
;

-- 2021-07-07T12:43:17.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(54070,'de_DE') 
;

-- 2021-07-07T12:43:17.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(54070,'de_DE') 
;

-- 2021-07-07T12:43:17.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_RelationType_ID', Name='Relationstyp', Description=NULL, Help=NULL WHERE AD_Element_ID=54070
;

-- 2021-07-07T12:43:17.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_RelationType_ID', Name='Relationstyp', Description=NULL, Help=NULL, AD_Element_ID=54070 WHERE UPPER(ColumnName)='AD_RELATIONTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-07T12:43:17.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_RelationType_ID', Name='Relationstyp', Description=NULL, Help=NULL WHERE AD_Element_ID=54070 AND IsCentrallyMaintained='Y'
;

-- 2021-07-07T12:43:17.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Relationstyp', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54070) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 54070)
;

-- 2021-07-07T12:43:18.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Relationstyp', Name='Relationstyp' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=54070)
;

-- 2021-07-07T12:43:18.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Relationstyp', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 54070
;

-- 2021-07-07T12:43:18.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Relationstyp', Description=NULL, Help=NULL WHERE AD_Element_ID = 54070
;

-- 2021-07-07T12:43:18.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Relationstyp', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 54070
;

-- 2021-07-07T12:43:22.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Relationstyp', PrintName='Relationstyp',Updated=TO_TIMESTAMP('2021-07-07 15:43:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54070 AND AD_Language='nl_NL'
;

-- 2021-07-07T12:43:22.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(54070,'nl_NL') 
;

-- 2021-07-07T12:43:27.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Relationstyp', PrintName='Relationstyp',Updated=TO_TIMESTAMP('2021-07-07 15:43:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54070 AND AD_Language='de_CH'
;

-- 2021-07-07T12:43:27.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(54070,'de_CH') 
;

-- 2021-07-07T12:46:08.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='Product Relation Types',Updated=TO_TIMESTAMP('2021-07-07 15:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541357
;

-- 2021-07-07T12:46:54.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Zusammenbauzeichnung', ValueName='Zusammenbauzeichnung',Updated=TO_TIMESTAMP('2021-07-07 15:46:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542685
;

-- 2021-07-07T12:47:06.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Zusammenbauzeichnung',Updated=TO_TIMESTAMP('2021-07-07 15:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542685
;

-- 2021-07-07T12:47:07.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Zusammenbauzeichnung',Updated=TO_TIMESTAMP('2021-07-07 15:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542685
;

-- 2021-07-07T12:47:10.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Zusammenbauzeichnung',Updated=TO_TIMESTAMP('2021-07-07 15:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542685
;

-- 2021-07-07T12:47:47.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Parent Product',Updated=TO_TIMESTAMP('2021-07-07 15:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542685
;

-- 2021-07-07T12:49:03.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET ValueName='Parent Product',Updated=TO_TIMESTAMP('2021-07-07 15:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542685
;

-- 2021-07-07T13:36:43.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574975,2432,0,30,540272,541733,'RelatedProduct_ID',TO_TIMESTAMP('2021-07-07 16:36:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Zugeordnetes Produkt','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Zugeordnetes Produkt',0,0,TO_TIMESTAMP('2021-07-07 16:36:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-07T13:36:43.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574975 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-07T13:36:43.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(2432)
;

DROP VIEW IF EXISTS M_Product_AttachmentEntry_ReferencedRecord_v
;

CREATE VIEW M_Product_AttachmentEntry_ReferencedRecord_v AS
    --parent attachments
SELECT rel.m_product_id,
       r.AD_Table_ID,
       r.Record_ID                                                               AS RelatedProduct_ID,
       r.AD_Attachment_MultiRef_ID                                               AS M_Product_AttachmentEntry_ReferencedRecord_v_ID,
       r.AD_Client_ID,
       r.AD_Org_ID,
       e.AD_AttachmentEntry_ID,
       e.BinaryData,
       e.ContentType,
       LEAST(e.Created, r.Created)                                               AS Created,
       CASE WHEN e.CreatedBy < r.CreatedBy THEN e.CreatedBy ELSE r.CreatedBy END AS CreatedBy,
       e.Description,
       e.FileName,
       CASE WHEN e.IsActive = 'Y' AND r.IsActive = 'Y' THEN 'Y' ELSE 'N' END     AS IsActive,
       e.Type,
       GREATEST(r.Updated, e.Updated)                                            AS Updated,
       CASE WHEN e.Updated > r.Updated THEN e.UpdatedBy ELSE r.UpdatedBy END     AS UpdatedBy,
       e.URL
FROM AD_Attachment_MultiRef r
         JOIN AD_AttachmentEntry e ON e.AD_AttachmentEntry_ID = r.AD_AttachmentEntry_ID
         JOIN M_Product_Relationship rel ON rel.ad_relationtype_id = 'Parent' AND rel.relatedproduct_id = r.record_id
WHERE r.ad_table_id = 208 --M_Product
;
