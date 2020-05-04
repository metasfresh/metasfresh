-- 2017-11-29T22:14:07.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (19,'N','N','N','N',0,'Y',100,543497,'Y','N','N','N','N','N','N','Y','N','N','N',190,'N','N','M_Warehouse_PickingGroup_ID','N',558002,'N','N','N','N','N','N',0,100,'Warehouse Picking Group','D','N',10,0,0,TO_TIMESTAMP('2017-11-29 22:14:07','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2017-11-29 22:14:07','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-11-29T22:14:07.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558002 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-29T22:14:12.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Warehouse','ALTER TABLE public.M_Warehouse ADD COLUMN M_Warehouse_PickingGroup_ID NUMERIC(10)')
;

-- 2017-11-29T22:14:12.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Warehouse ADD CONSTRAINT MWarehousePickingGroup_MWareho FOREIGN KEY (M_Warehouse_PickingGroup_ID) REFERENCES public.M_Warehouse_PickingGroup DEFERRABLE INITIALLY DEFERRED
;

-- 2017-11-29T22:14:35.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,EntityType,UpdatedBy,DisplayLength,Created,Updated) VALUES (177,'Y','N','N','N','N',0,'Y',100,'N','Y',560644,558002,0,'Warehouse Picking Group','D',100,10,TO_TIMESTAMP('2017-11-29 22:14:35','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-11-29 22:14:35','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-11-29T22:14:35.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560644 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-29T22:15:24.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=170, SeqNoGrid=170,Updated=TO_TIMESTAMP('2017-11-29 22:15:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560644
;

-- 2017-11-29T22:17:52.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES (549495,560644,'Y',100,0,100,'Y',540173,'Y','N','N',0,177,0,'Warehouse Picking Group','F',TO_TIMESTAMP('2017-11-29 22:17:52','YYYY-MM-DD HH24:MI:SS'),50,0,TO_TIMESTAMP('2017-11-29 22:17:52','YYYY-MM-DD HH24:MI:SS'))
;

