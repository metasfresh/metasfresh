-- 2019-11-25T09:09:27.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (Created,CreatedBy,Updated,AD_Client_ID,AD_Index_Table_ID,IsActive,AD_Column_ID,SeqNo,UpdatedBy,AD_Index_Column_ID,ColumnSQL,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2019-11-25 11:09:27','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-11-25 11:09:27','YYYY-MM-DD HH24:MI:SS'),0,540376,'Y',554319,50,100,540982,'COALESCE',0,'U')
;

-- 2019-11-25T09:09:54.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(M_AttributeSetInstance_ID,0)', EntityType='de.metas.procurement',Updated=TO_TIMESTAMP('2019-11-25 11:09:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540982
;

-- 2019-11-25T09:10:16.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS pmm_product_uc
;

-- 2019-11-25T09:10:16.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX PMM_Product_UC ON PMM_Product (COALESCE(C_BPartner_ID,0),M_Product_ID,COALESCE(M_HU_PI_Item_Product_ID,0),COALESCE(SeqNo,0),COALESCE(M_AttributeSetInstance_ID,0)) WHERE IsActive='Y'
;

-- 2019-11-25T09:23:24.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=40,Updated=TO_TIMESTAMP('2019-11-25 11:23:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554319
;

-- 2019-11-25T10:16:53.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2019-11-25 12:16:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8479
;

-- 2019-11-25T11:07:59.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,AD_Org_ID,Updated,UpdatedBy,AD_Element_ID,ColumnName,PrintName,Name,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-25 13:07:59','YYYY-MM-DD HH24:MI:SS'),100,0,TO_TIMESTAMP('2019-11-25 13:07:59','YYYY-MM-DD HH24:MI:SS'),100,577385,'ASIAllAttributes','AttributeSetInstanceAllAttributes','AttributeSetInstanceAllAttributes','D')
;

-- 2019-11-25T11:07:59.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577385 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-11-25T11:08:26.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,EntityType) VALUES (10,1024,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-25 13:08:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-25 13:08:26','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540751,'N',569673,'N','N','N','N','N','N','N','N',0,577385,'N','N','ASIAllAttributes','N','AttributeSetInstanceAllAttributes',0,'de.metas.procurement')
;

-- 2019-11-25T11:08:26.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569673 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-25T11:08:26.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577385) 
;

-- 2019-11-25T11:08:31.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PMM_Product','ALTER TABLE public.PMM_Product ADD COLUMN ASIAllAttributes VARCHAR(1024)')
;

-- 2019-11-25T12:12:06.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET AD_Column_ID=569673, ColumnSQL='COALESCE(ASIAllAttributes,"0")',Updated=TO_TIMESTAMP('2019-11-25 14:12:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540982
;

-- 2019-11-25T12:12:27.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(ASIAllAttributes,''0'')',Updated=TO_TIMESTAMP('2019-11-25 14:12:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540982
;

-- 2019-11-25T12:25:42.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS pmm_product_uc
;

-- 2019-11-25T12:25:42.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX PMM_Product_UC ON PMM_Product (COALESCE(C_BPartner_ID,0),M_Product_ID,COALESCE(M_HU_PI_Item_Product_ID,0),COALESCE(SeqNo,0),COALESCE(ASIAllAttributes,'0')) WHERE IsActive='Y'
;

-- 2019-11-25T12:40:05.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540982
;

-- 2019-11-25T12:42:11.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS pmm_product_uc
;

-- 2019-11-25T12:42:11.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX PMM_Product_UC ON PMM_Product (COALESCE(C_BPartner_ID,0),M_Product_ID,COALESCE(M_HU_PI_Item_Product_ID,0),COALESCE(SeqNo,0)) WHERE IsActive='Y'
;

