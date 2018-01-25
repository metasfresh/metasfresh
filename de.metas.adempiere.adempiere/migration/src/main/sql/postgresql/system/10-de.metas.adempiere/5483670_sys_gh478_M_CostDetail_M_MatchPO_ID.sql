-- 2018-01-22T16:54:56.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (IsAllowFiltering,AD_UI_Element_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Labels_Tab_ID,Labels_Selector_Field_ID,Created,SeqNo,SeqNoGrid,Updated) VALUES ('Y',550398,'N',100,0,100,'Y',1000013,'Y','N','N',0,220,0,'Attributes','L',496,7015,TO_TIMESTAMP('2018-01-22 16:54:56','YYYY-MM-DD HH24:MI:SS'),43,0,TO_TIMESTAMP('2018-01-22 16:54:56','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-22T18:12:15.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Banks', Labels_Tab_ID=226, Labels_Selector_Field_ID=2200,Updated=TO_TIMESTAMP('2018-01-22 18:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550398
;

-- 2018-01-22T19:20:05.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2018-01-22 19:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550398
;

-- 2018-01-23T15:02:41.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (30,'N','N','N','N',0,'Y',100,1690,'N','N','N','N','N','N','N','Y','N','N','N',808,'N','N','The matching record is usually created automatically.  If price matching is enabled on business partner group level, the matching might have to be approved.','M_MatchPO_ID','N',558621,'N','N','N','N','N','N','Match Purchase Order to Shipment/Receipt and Invoice',0,100,'Abgleich Bestellung','D','N',10,0,0,TO_TIMESTAMP('2018-01-23 15:02:40','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-01-23 15:02:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-23T15:02:41.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558621 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-23T15:02:59.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_CostDetail','ALTER TABLE public.M_CostDetail ADD COLUMN M_MatchPO_ID NUMERIC(10)')
;

-- 2018-01-23T15:02:59.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_CostDetail ADD CONSTRAINT MMatchPO_MCostDetail FOREIGN KEY (M_MatchPO_ID) REFERENCES public.M_MatchPO DEFERRABLE INITIALLY DEFERRED
;

