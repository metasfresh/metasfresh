-- 2018-10-10T10:50:04.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544442,0,'PickStatus',TO_TIMESTAMP('2018-10-10 10:50:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Pick Status','Pick Status',TO_TIMESTAMP('2018-10-10 10:50:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-10T10:50:04.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544442 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-10T10:50:25.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544443,0,'ApprovalStatus',TO_TIMESTAMP('2018-10-10 10:50:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Approval Status','Approval Status',TO_TIMESTAMP('2018-10-10 10:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-10T10:50:25.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544443 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-10T10:58:54.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540919,TO_TIMESTAMP('2018-10-10 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','N','M_Picking_Candidate_PickStatus',TO_TIMESTAMP('2018-10-10 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-10-10T10:58:54.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540919 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-10-10T10:59:01.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2018-10-10 10:59:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540919
;

-- 2018-10-10T10:59:52.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541742,540919,TO_TIMESTAMP('2018-10-10 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','To Be Picked',TO_TIMESTAMP('2018-10-10 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'?','ToBePicked')
;

-- 2018-10-10T10:59:52.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541742 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-10T11:00:02.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541743,540919,TO_TIMESTAMP('2018-10-10 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Picked',TO_TIMESTAMP('2018-10-10 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'P','Picked')
;

-- 2018-10-10T11:00:02.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541743 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-10T11:01:05.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541744,540919,TO_TIMESTAMP('2018-10-10 11:01:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Will Not Be Picked',TO_TIMESTAMP('2018-10-10 11:01:04','YYYY-MM-DD HH24:MI:SS'),100,'N','WillNotBePicked')
;

-- 2018-10-10T11:01:05.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541744 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-10T11:01:36.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType) VALUES (17,'?',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-10 11:01:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-10 11:01:36','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540831,'N',540919,'PickStatus',563251,'N','Y','N','N','N','N','N','N',0,0,'Pick Status',544442,'N','N','de.metas.handlingunits')
;

-- 2018-10-10T11:01:36.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563251 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-10T11:01:39.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Candidate','ALTER TABLE public.M_Picking_Candidate ADD COLUMN PickStatus CHAR(1) DEFAULT ''?'' NOT NULL')
;

-- 2018-10-10T11:02:54.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540920,TO_TIMESTAMP('2018-10-10 11:02:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','M_Picking_Candidate_ApprovalStatus',TO_TIMESTAMP('2018-10-10 11:02:53','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-10-10T11:02:54.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540920 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-10-10T11:03:29.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541745,540920,TO_TIMESTAMP('2018-10-10 11:03:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','To Be Approved',TO_TIMESTAMP('2018-10-10 11:03:29','YYYY-MM-DD HH24:MI:SS'),100,'?','ToBeApproved')
;

-- 2018-10-10T11:03:29.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541745 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-10T11:03:48.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541746,540920,TO_TIMESTAMP('2018-10-10 11:03:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Approved',TO_TIMESTAMP('2018-10-10 11:03:48','YYYY-MM-DD HH24:MI:SS'),100,'A','Approved')
;

-- 2018-10-10T11:03:48.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541746 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-10T11:04:03.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541747,540920,TO_TIMESTAMP('2018-10-10 11:04:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Rejected',TO_TIMESTAMP('2018-10-10 11:04:03','YYYY-MM-DD HH24:MI:SS'),100,'R','Rejected')
;

-- 2018-10-10T11:04:03.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541747 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-10T11:04:26.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType) VALUES (17,'?',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-10 11:04:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-10 11:04:26','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540831,'N',540920,'ApprovalStatus',563252,'N','Y','N','N','N','N','N','N',0,0,'Approval Status',544443,'N','N','de.metas.handlingunits')
;

-- 2018-10-10T11:04:26.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563252 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-10T11:04:29.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Candidate','ALTER TABLE public.M_Picking_Candidate ADD COLUMN ApprovalStatus CHAR(1) DEFAULT ''?'' NOT NULL')
;

-- 2018-10-10T11:05:48.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544444,0,'PickFrom_HU_ID',TO_TIMESTAMP('2018-10-10 11:05:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Pick From HU','Pick From HU',TO_TIMESTAMP('2018-10-10 11:05:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-10T11:05:48.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544444 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-10T11:06:16.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544445,0,'PackTo_HU_PI_ID',TO_TIMESTAMP('2018-10-10 11:06:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Pack To','Pack To',TO_TIMESTAMP('2018-10-10 11:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-10T11:06:16.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544445 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-10T11:06:41.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-10 11:06:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-10 11:06:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540831,'N',540499,'PickFrom_HU_ID',563253,'N','N','N','N','N','N','N','N',0,0,'Pick From HU',544444,'N','N','de.metas.handlingunits')
;

-- 2018-10-10T11:06:41.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563253 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-10T11:06:46.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Candidate','ALTER TABLE public.M_Picking_Candidate ADD COLUMN PickFrom_HU_ID NUMERIC(10)')
;

-- 2018-10-10T11:06:46.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Picking_Candidate ADD CONSTRAINT PickFromHU_MPickingCandidate FOREIGN KEY (PickFrom_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- 2018-10-10T11:07:04.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-10 11:07:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-10 11:07:04','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540831,'N',540396,'PackTo_HU_PI_ID',563254,'N','N','N','N','N','N','N','N',0,0,'Pack To',544445,'N','N','de.metas.handlingunits')
;

-- 2018-10-10T11:07:04.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563254 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-10T11:10:54.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Picking_Candidate','ALTER TABLE public.M_Picking_Candidate ADD COLUMN PackTo_HU_PI_ID NUMERIC(10)')
;

-- 2018-10-10T11:10:54.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Picking_Candidate ADD CONSTRAINT PackToHUPI_MPickingCandidate FOREIGN KEY (PackTo_HU_PI_ID) REFERENCES public.M_HU_PI DEFERRABLE INITIALLY DEFERRED
;

