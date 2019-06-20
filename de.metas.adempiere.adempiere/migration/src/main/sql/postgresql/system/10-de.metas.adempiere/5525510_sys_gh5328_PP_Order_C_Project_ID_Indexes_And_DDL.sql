






SELECT public.db_alter_table('PP_Order','ALTER TABLE PP_Order DROP COLUMN C_Project_ID');






















-- 2019-06-19T18:04:37.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,2593,0,186,560012,1000026,'F',TO_TIMESTAMP('2019-06-19 18:04:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'project',70,0,0,TO_TIMESTAMP('2019-06-19 18:04:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-19T18:09:59.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540499, SeqNo=420,Updated=TO_TIMESTAMP('2019-06-19 18:09:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560012
;

-- 2019-06-19T18:10:25.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-06-19 18:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560012
;

-- 2019-06-19T18:10:47.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560012
;

-- 2019-06-19T18:23:26.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT COALESCE(ol.C_Project_ID, o.C_Project_ID) from  C_OrderLine ol JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID where ol.C_OrderLine_ID = PP_Order.C_OrderLine_ID) ', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-06-19 18:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53631
;

-- 2019-06-19T18:26:40.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540494,0,53027,TO_TIMESTAMP('2019-06-19 18:26:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','PP_Order_C_OrderLine','N',TO_TIMESTAMP('2019-06-19 18:26:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-19T18:26:40.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540494 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-06-19T18:26:55.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,53630,540950,540494,0,TO_TIMESTAMP('2019-06-19 18:26:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2019-06-19 18:26:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-19T18:26:57.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX PP_Order_C_OrderLine ON PP_Order (C_OrderLine_ID)
;

-- 2019-06-19T18:34:10.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540495,0,259,TO_TIMESTAMP('2019-06-19 18:34:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order_C_Project_ID','N',TO_TIMESTAMP('2019-06-19 18:34:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-19T18:34:10.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540495 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-06-19T18:34:19.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,3402,540951,540495,0,TO_TIMESTAMP('2019-06-19 18:34:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2019-06-19 18:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-19T18:34:20.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX C_Order_C_Project_ID ON C_Order (C_Project_ID)
;

-- 2019-06-19T18:36:02.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-06-19 18:36:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53631
;

