-- 2018-07-26T20:14:36.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Break Value Type','D','BreakValueType',544187,0,'Break Value Type',100,TO_TIMESTAMP('2018-07-26 20:14:36','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-26 20:14:36','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-26T20:14:37.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544187 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-26T20:15:38.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,CreatedBy,EntityType,IsOrderByValue,AD_Reference_ID,ValidationType,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'D','N',540886,'L',0,'M_DiscountSchema_BreakValueType',100,TO_TIMESTAMP('2018-07-26 20:15:38','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-26 20:15:38','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-26T20:15:38.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540886 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-26T20:16:06.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,CreatedBy,Created,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540886,0,'Y',100,TO_TIMESTAMP('2018-07-26 20:16:06','YYYY-MM-DD HH24:MI:SS'),'Q','D',541664,'Quantity',0,'Menge',100,TO_TIMESTAMP('2018-07-26 20:16:06','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-26T20:16:06.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541664 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-07-26T20:16:18.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,CreatedBy,Created,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540886,0,'Y',100,TO_TIMESTAMP('2018-07-26 20:16:18','YYYY-MM-DD HH24:MI:SS'),'A','D',541665,'Amount',0,'Amount',100,TO_TIMESTAMP('2018-07-26 20:16:18','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-26T20:16:18.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541665 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-07-26T20:16:39.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,CreatedBy,Created,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540886,0,'Y',100,TO_TIMESTAMP('2018-07-26 20:16:39','YYYY-MM-DD HH24:MI:SS'),'T','D',541666,'Attribute',0,'Merkmal',100,TO_TIMESTAMP('2018-07-26 20:16:39','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-26T20:16:39.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541666 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-07-26T20:17:23.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (17,'N','N','N','N',0,'Y',100,544187,'Y','N','N','N','N','N','N','N','Y','N','N','N',475,'N','N',540886,'BreakValueType',560719,'N','Y','N','N','N','N',0,100,'Break Value Type','D','N',1,0,0,TO_TIMESTAMP('2018-07-26 20:17:23','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-07-26 20:17:23','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-07-26T20:17:23.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560719 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-26T20:17:39.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Q',Updated=TO_TIMESTAMP('2018-07-26 20:17:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560719
;

-- 2018-07-26T20:18:52.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,Help,ColumnName,AD_Element_ID,Description,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Merkmal','D','','BreakValue_Attribute_ID',544188,'',0,'Merkmal',100,TO_TIMESTAMP('2018-07-26 20:18:52','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-26 20:18:52','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-26T20:18:52.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544188 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-26T20:19:16.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-26 20:19:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Break Value Attribute',PrintName='Break Value Attribute' WHERE AD_Element_ID=544188 AND AD_Language='en_US'
;

-- 2018-07-26T20:19:16.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544188,'en_US') 
;

-- 2018-07-26T20:19:52.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (30,'N','N','N','N',0,'Y',100,544188,'Y','N','N','N','N','N','N','N','Y','N','N','N',475,'N','N','',540880,'BreakValue_Attribute_ID',560720,'N','N','N','N','N','N','',0,100,'Merkmal','D','N',10,0,0,TO_TIMESTAMP('2018-07-26 20:19:52','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-07-26 20:19:52','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-07-26T20:19:53.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560720 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-26T20:20:22.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_DiscountSchema','ALTER TABLE public.M_DiscountSchema ADD COLUMN BreakValueType CHAR(1) DEFAULT ''Q'' NOT NULL')
;

-- 2018-07-26T20:21:46.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_DiscountSchema','ALTER TABLE public.M_DiscountSchema ADD COLUMN BreakValue_Attribute_ID NUMERIC(10)')
;

-- 2018-07-26T20:21:46.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_DiscountSchema ADD CONSTRAINT BreakValueAttribute_MDiscountSchema FOREIGN KEY (BreakValue_Attribute_ID) REFERENCES public.M_Attribute DEFERRABLE INITIALLY DEFERRED
;

