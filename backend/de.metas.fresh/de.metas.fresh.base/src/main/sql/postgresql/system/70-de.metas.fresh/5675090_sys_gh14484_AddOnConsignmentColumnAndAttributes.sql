-- 2023-02-02T07:56:01.637Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,Description,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'L',TO_TIMESTAMP('2023-02-02 09:56:01','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','N','N','Y','N','N','N','N','N','N',540114,'Kommissionsware',TO_TIMESTAMP('2023-02-02 09:56:01','YYYY-MM-DD HH24:MI:SS'),100,'OnConsignment',0,0)
;

-- 2023-02-02T07:59:07.605Z
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2023-02-02 09:59:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540042,540066,'Yes',TO_TIMESTAMP('2023-02-02 09:59:07','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

update M_AttributeValue set m_attribute_id=540114 where M_AttributeValue_ID=540066;


-- 2023-02-02T08:00:56.402Z
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2023-02-02 10:00:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540114,540067,'No',TO_TIMESTAMP('2023-02-02 10:00:56','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2023-02-02T08:19:54.630Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,C_UOM_ID,DescriptionPattern,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,'N',TO_TIMESTAMP('2023-02-02 10:19:54','YYYY-MM-DD HH24:MI:SS'),100,100,'','Y','N','N','N','Y','N','N','N','N','Y','N',540115,'Goods on consignment storage period',TO_TIMESTAMP('2023-02-02 10:19:54','YYYY-MM-DD HH24:MI:SS'),100,'OnConsignment_StoragePeriod')
;

-- 2023-02-02T08:20:41.572Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,C_UOM_ID,DescriptionPattern,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,'N',TO_TIMESTAMP('2023-02-02 10:20:41','YYYY-MM-DD HH24:MI:SS'),100,100,'','Y','N','N','N','Y','N','N','N','N','Y','N',540116,'Goods on consignment End Date',TO_TIMESTAMP('2023-02-02 10:20:41','YYYY-MM-DD HH24:MI:SS'),100,'OnConsignment_EndDate ')
;

-- 2023-02-02T08:21:30.757Z
UPDATE M_Attribute SET AttributeValueType='D', C_UOM_ID=NULL,Updated=TO_TIMESTAMP('2023-02-02 10:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540116
;

-- 2023-02-02T08:21:50.231Z
UPDATE M_Attribute SET IsStorageRelevant='Y',Updated=TO_TIMESTAMP('2023-02-02 10:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540114
;

-- 2023-02-02T08:23:26.427Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'S',TO_TIMESTAMP('2023-02-02 10:23:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N','N','N',540117,'Vendor Reference',TO_TIMESTAMP('2023-02-02 10:23:26','YYYY-MM-DD HH24:MI:SS'),100,'VendorReference',0,0)
;

-- 2023-02-02T08:23:39.485Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'S',TO_TIMESTAMP('2023-02-02 10:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N','N','N',540118,'Internal Reference',TO_TIMESTAMP('2023-02-02 10:23:39','YYYY-MM-DD HH24:MI:SS'),100,'InternalReference',0,0)
;

-- 2023-02-02T08:28:36.754Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581995,0,'IsOnConsignment',TO_TIMESTAMP('2023-02-02 10:28:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','On Consignment','On Consignment',TO_TIMESTAMP('2023-02-02 10:28:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T08:28:36.755Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581995 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsOnConsignment
-- 2023-02-02T08:29:03.282Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Goods on consignment', PrintName='Goods on consignment',Updated=TO_TIMESTAMP('2023-02-02 10:29:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581995 AND AD_Language='en_US'
;

-- 2023-02-02T08:29:03.308Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581995,'en_US') 
;

-- Element: IsOnConsignment
-- 2023-02-02T08:29:09.015Z
UPDATE AD_Element_Trl SET Name='Goods on consignment', PrintName='Goods on consignment',Updated=TO_TIMESTAMP('2023-02-02 10:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581995 AND AD_Language='fr_CH'
;

-- 2023-02-02T08:29:09.017Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581995,'fr_CH') 
;

-- Element: IsOnConsignment
-- 2023-02-02T08:29:19.457Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kommissionsware', PrintName='Kommissionsware',Updated=TO_TIMESTAMP('2023-02-02 10:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581995 AND AD_Language='de_CH'
;

-- 2023-02-02T08:29:19.460Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581995,'de_CH') 
;

-- Element: IsOnConsignment
-- 2023-02-02T08:29:23.390Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kommissionsware', PrintName='Kommissionsware',Updated=TO_TIMESTAMP('2023-02-02 10:29:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581995 AND AD_Language='de_DE'
;

-- 2023-02-02T08:29:23.391Z
UPDATE AD_Element SET Name='Kommissionsware', PrintName='Kommissionsware' WHERE AD_Element_ID=581995
;

-- 2023-02-02T08:29:23.677Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581995,'de_DE') 
;

-- 2023-02-02T08:29:23.678Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581995,'de_DE') 
;

-- Column: C_Order.IsOnConsignment
-- 2023-02-02T08:29:36.182Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585740,581995,0,20,259,'IsOnConsignment',TO_TIMESTAMP('2023-02-02 10:29:36','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Kommissionsware',0,0,TO_TIMESTAMP('2023-02-02 10:29:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T08:29:36.184Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585740 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T08:29:36.186Z
/* DDL */  select update_Column_Translation_From_AD_Element(581995) 
;

-- 2023-02-02T08:29:39.507Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN IsOnConsignment CHAR(1) DEFAULT ''N'' CHECK (IsOnConsignment IN (''Y'',''N'')) NOT NULL')
;

----- attribute set




-- 2023-02-02T08:49:08.886Z
INSERT INTO M_AttributeSet (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsInstanceAttribute,MandatoryType,M_AttributeSet_ID,Name,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-02-02 10:49:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N',540014,'OnConsignment',TO_TIMESTAMP('2023-02-02 10:49:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T08:49:08.888Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='Y'AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- 2023-02-02T08:49:22.045Z
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-02-02 10:49:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',540114,540014,540035,10,TO_TIMESTAMP('2023-02-02 10:49:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T08:49:22.046Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='N' AND (EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2023-02-02T08:49:22.047Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='Y'	AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- 2023-02-02T08:49:28.157Z
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-02-02 10:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',540115,540014,540036,20,TO_TIMESTAMP('2023-02-02 10:49:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T08:49:28.158Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='N' AND (EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2023-02-02T08:49:28.159Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='Y' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- 2023-02-02T08:49:41.575Z
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-02-02 10:49:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',540116,540014,540037,30,TO_TIMESTAMP('2023-02-02 10:49:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T08:49:41.576Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='N' AND (EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2023-02-02T08:49:41.577Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='Y' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- 2023-02-02T08:49:46.538Z
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-02-02 10:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',540117,540014,540038,40,TO_TIMESTAMP('2023-02-02 10:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T08:49:46.539Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='N' AND (EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2023-02-02T08:49:46.540Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='Y' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- 2023-02-02T08:49:50.354Z
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-02-02 10:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',540118,540014,540039,50,TO_TIMESTAMP('2023-02-02 10:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T08:49:50.355Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='N' AND (EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2023-02-02T08:49:50.356Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='Y' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;


-- 2023-02-02T08:52:56.750Z
UPDATE M_AttributeSet SET Name='Goods on consignment',Updated=TO_TIMESTAMP('2023-02-02 10:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_AttributeSet_ID=540014
;

-- 2023-02-02T08:52:56.751Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540014 AND IsInstanceAttribute='Y' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- 2023-02-02T08:53:13.487Z
UPDATE M_Attribute SET Name='Goods on consignment',Updated=TO_TIMESTAMP('2023-02-02 10:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540114
;





-- 2023-02-02T08:56:15.422Z
UPDATE M_Attribute SET Description='Is this a Goods on consignment Order.Retailer agrees to pay the seller a provision for this merchandise, after the item sells.',Updated=TO_TIMESTAMP('2023-02-02 10:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540114
;

-- 2023-02-02T08:56:42.175Z
UPDATE M_Attribute SET Description='Shows the Goods on consignment storage period in months',Updated=TO_TIMESTAMP('2023-02-02 10:56:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540115
;

-- 2023-02-02T08:57:04.662Z
UPDATE M_Attribute SET Description='Shows the Goods on consignment end date',Updated=TO_TIMESTAMP('2023-02-02 10:57:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540116
;

-- 2023-02-02T09:21:23.391Z
UPDATE M_Attribute SET IsInstanceAttribute='Y', IsStorageRelevant='Y',Updated=TO_TIMESTAMP('2023-02-02 11:21:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540117
;

-- 2023-02-02T09:21:23.394Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540117)
;

-- 2023-02-02T09:21:39.559Z
UPDATE M_Attribute SET IsInstanceAttribute='Y', IsStorageRelevant='Y',Updated=TO_TIMESTAMP('2023-02-02 11:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540118
;

-- 2023-02-02T09:21:39.560Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540118)
;

-- 2023-02-02T09:22:08.631Z
UPDATE M_Attribute SET DescriptionPattern='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-02 11:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540114
;

-- 2023-02-02T09:22:31.246Z
UPDATE M_Attribute SET Name='Storage period',Updated=TO_TIMESTAMP('2023-02-02 11:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540115
;

-- 2023-02-02T09:22:39.775Z
UPDATE M_Attribute SET Name='End Date',Updated=TO_TIMESTAMP('2023-02-02 11:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540116
;

-- 2023-02-02T14:33:02.470Z
UPDATE M_Attribute SET DescriptionPattern='',Updated=TO_TIMESTAMP('2023-02-02 16:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540114
;

update m_attribute set value='OnConsignment_EndDate' where M_Attribute_ID=540116;



-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 10 -> pricing.Mikoshi
-- Column: C_Order.IsOnConsignment
-- 2023-02-03T10:39:14.043Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615370
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 10 -> main.Lieferung von
-- Column: C_Order.C_BPartner_ID
-- 2023-02-03T10:39:14.048Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611535
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 10 -> main.Rechnung von
-- Column: C_Order.Bill_BPartner_ID
-- 2023-02-03T10:39:14.054Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611536
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 10 -> main.Warenannahme
-- Column: C_Order.M_Warehouse_ID
-- 2023-02-03T10:39:14.059Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611537
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 10 -> main.Streckengeschäft
-- Column: C_Order.IsDropShip
-- 2023-02-03T10:39:14.065Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611538
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 10 -> main.Streckengeschäft Partner
-- Column: C_Order.DropShip_BPartner_ID
-- 2023-02-03T10:39:14.070Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611539
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 10 -> main.Tour
-- Column: C_Order.M_Tour_ID
-- 2023-02-03T10:39:14.075Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611540
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 10 -> pricing.Preissystem
-- Column: C_Order.M_PricingSystem_ID
-- 2023-02-03T10:39:14.080Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611541
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 10 -> pricing.Währung
-- Column: C_Order.C_Currency_ID
-- 2023-02-03T10:39:14.084Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611542
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 20 -> dates.Belegstatus
-- Column: C_Order.DocStatus
-- 2023-02-03T10:39:14.089Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611548
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 20 -> posted.Verbucht
-- Column: C_Order.Posted
-- 2023-02-03T10:39:14.093Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611553
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 20 -> org.Section Code
-- Column: C_Order.M_SectionCode_ID
-- 2023-02-03T10:39:14.096Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611555
;

-- UI Element: Bestellung(541595,D) -> Bestellung(546550,D) -> main -> 20 -> org.Sektion
-- Column: C_Order.AD_Org_ID
-- 2023-02-03T10:39:14.099Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-02-03 12:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611556
;


--------

-- Column: C_OrderLine.IsOnConsignment
-- 2023-02-03T12:28:00.151Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585841,581995,0,20,260,'IsOnConsignment',TO_TIMESTAMP('2023-02-03 14:27:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Kommissionsware',0,0,TO_TIMESTAMP('2023-02-03 14:27:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T12:28:00.152Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585841 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T12:28:00.156Z
/* DDL */  select update_Column_Translation_From_AD_Element(581995) 
;

-- 2023-02-03T12:29:54.861Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN IsOnConsignment CHAR(1) DEFAULT ''N'' CHECK (IsOnConsignment IN (''Y'',''N'')) NOT NULL')
;


-- 2023-02-03T14:28:43.684Z
UPDATE M_Attribute SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-02-03 16:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540114
;

