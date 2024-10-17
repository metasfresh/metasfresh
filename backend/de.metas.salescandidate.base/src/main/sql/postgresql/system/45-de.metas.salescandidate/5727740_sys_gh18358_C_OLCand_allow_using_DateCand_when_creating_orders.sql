-- 2024-07-03T10:08:34.761Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583163,0,'desadv_documentno',TO_TIMESTAMP('2024-07-03 12:08:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','desadv_documentno','desadv_documentno',TO_TIMESTAMP('2024-07-03 12:08:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-03T10:08:34.791Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583163 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
 
-- Column: EDI_cctop_invoic_v.desadv_documentno
-- 2024-07-03T10:10:00.037Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588662,583163,0,10,540462,'desadv_documentno',TO_TIMESTAMP('2024-07-03 12:09:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,100,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'desadv_documentno',0,0,TO_TIMESTAMP('2024-07-03 12:09:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-03T10:10:00.065Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588662 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-03T10:10:00.149Z
/* DDL */  select update_Column_Translation_From_AD_Element(583163) 
;

-- 2024-07-03T10:11:03.112Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588662,0,TO_TIMESTAMP('2024-07-03 12:11:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540220,550699,'Y','N','N','desadv_documentno',510,'E',TO_TIMESTAMP('2024-07-03 12:11:02','YYYY-MM-DD HH24:MI:SS'),100,'desadv_documentno')
;

-- Column: EDI_Desadv.shipment_documentno
-- Column SQL (old): null
-- 2024-07-03T11:29:03.093Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588663,541980,0,10,540644,'shipment_documentno','select string_agg(io.DocumentNo, '','') from m_inout io where io.EDI_Desadv_ID=EDI_Desadv.EDI_Desadv_ID',TO_TIMESTAMP('2024-07-03 13:29:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,512,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'shipment_documentno',0,0,TO_TIMESTAMP('2024-07-03 13:29:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-03T11:29:03.122Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588663 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-03T11:29:03.178Z
/* DDL */  select update_Column_Translation_From_AD_Element(541980) 
;

-- 2024-07-03T11:30:56.513Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,588663,0,540159,540644,TO_TIMESTAMP('2024-07-03 13:30:56','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',551753,551753,319,TO_TIMESTAMP('2024-07-03 13:30:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: EDI_Desadv.DatePromised
-- Column SQL (old): null
-- 2024-07-03T11:33:24.403Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588664,269,0,15,540644,'DatePromised','select o.DatePromised from c_order o where o.EDI_Desadv_ID=EDI_Desadv.EDI_Desadv_ID',TO_TIMESTAMP('2024-07-03 13:33:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Zugesagter Termin für diesen Auftrag','de.metas.esb.edi',0,7,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Zugesagter Termin',0,0,TO_TIMESTAMP('2024-07-03 13:33:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-03T11:33:24.431Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588664 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-03T11:33:24.486Z
/* DDL */  select update_Column_Translation_From_AD_Element(269) 
;

-- 2024-07-03T11:34:26.884Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,588664,0,540160,540644,TO_TIMESTAMP('2024-07-03 13:34:26','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',552035,552035,259,TO_TIMESTAMP('2024-07-03 13:34:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-03T11:36:21.700Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588663,0,TO_TIMESTAMP('2024-07-03 13:36:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550700,'Y','N','N','shipment_documentno',360,'E',TO_TIMESTAMP('2024-07-03 13:36:21','YYYY-MM-DD HH24:MI:SS'),100,'shipment_documentno')
;

-- 2024-07-03T11:36:55.763Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588664,0,TO_TIMESTAMP('2024-07-03 13:36:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550701,'Y','N','N','DatePromised',370,'E',TO_TIMESTAMP('2024-07-03 13:36:55','YYYY-MM-DD HH24:MI:SS'),100,'DatePromised')
;

-- 2024-07-03T11:39:13.799Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=3791,Updated=TO_TIMESTAMP('2024-07-03 13:39:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540159
;

-- 2024-07-03T11:39:40.482Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=2182,Updated=TO_TIMESTAMP('2024-07-03 13:39:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540160
;

-- 2024-07-03T12:14:13.746Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541724,'O',TO_TIMESTAMP('2024-07-03 14:14:13','YYYY-MM-DD HH24:MI:SS'),100,'If Y, and C_OLCands are turned into sales orders, then C_OLCand.DateCandidate is used as C_Order.DateOrdered.
If N, then the current date at the time of order-creation is used as DateOrdered.
Set to N on system level for backwards compatibility. Can be overwritten on client. and org-level.','de.metas.ordercandidate','Y','de.metas.ordercandidate.Use_DateCandidate_As_DateOrdered',TO_TIMESTAMP('2024-07-03 14:14:13','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2024-07-03T12:25:14.154Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,568733,0,TO_TIMESTAMP('2024-07-03 14:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags','de.metas.esb.edi',540378,550720,'Bezeichnet das Datum, an dem die Ware bestellt wurde.','N','N','Auftragsdatum',700,'E',TO_TIMESTAMP('2024-07-03 14:25:13','YYYY-MM-DD HH24:MI:SS'),100,'DateOrdered')
;

-- 2024-07-03T12:39:15.034Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-07-03 14:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550720
;

-- 2024-07-03T12:45:11.470Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If Y, and C_OLCands are turned into sales orders, then C_OLCand.DateCandidate is used as C_Order.DateOrdered.
If N, then C_OLCand.DateOrdered is used as C_Order.DateOrdered.
Note that C_OLCand.DateCandidate is mandatory, while C_OLCand.DateOrdered is not. If N and C_OLCand.DateOrdered is null, then the current date at the time of order-creation is used as DateOrdered.
Set to N on system level for backwards compatibility. Can be overwritten on client- and org-level.', Value='N',Updated=TO_TIMESTAMP('2024-07-03 14:45:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541724
;

