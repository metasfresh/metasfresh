-- 2018-08-08T08:57:38.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544203,0,'IsUseInProfitCalculation',TO_TIMESTAMP('2018-08-08 08:57:38','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob die Rückvergütungsparameter in die Berechnung des erwarteten Roherlöses (d.h. Ertrag/Marge) einfließen soll.','de.metas.contracts','Y','Roherlösberechnung','Roherlösberechnung',TO_TIMESTAMP('2018-08-08 08:57:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T08:57:38.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544203 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-08T08:58:29.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='In Roherlösberechnung', PrintName='In Roherlösberechnung',Updated=TO_TIMESTAMP('2018-08-08 08:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544203
;

-- 2018-08-08T08:58:29.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsUseInProfitCalculation', Name='In Roherlösberechnung', Description='Legt fest, ob die Rückvergütungsparameter in die Berechnung des erwarteten Roherlöses (d.h. Ertrag/Marge) einfließen soll.', Help=NULL WHERE AD_Element_ID=544203
;

-- 2018-08-08T08:58:29.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUseInProfitCalculation', Name='In Roherlösberechnung', Description='Legt fest, ob die Rückvergütungsparameter in die Berechnung des erwarteten Roherlöses (d.h. Ertrag/Marge) einfließen soll.', Help=NULL, AD_Element_ID=544203 WHERE UPPER(ColumnName)='ISUSEINPROFITCALCULATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-08T08:58:29.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUseInProfitCalculation', Name='In Roherlösberechnung', Description='Legt fest, ob die Rückvergütungsparameter in die Berechnung des erwarteten Roherlöses (d.h. Ertrag/Marge) einfließen soll.', Help=NULL WHERE AD_Element_ID=544203 AND IsCentrallyMaintained='Y'
;

-- 2018-08-08T08:58:29.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='In Roherlösberechnung', Description='Legt fest, ob die Rückvergütungsparameter in die Berechnung des erwarteten Roherlöses (d.h. Ertrag/Marge) einfließen soll.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544203) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544203)
;

-- 2018-08-08T08:58:29.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='In Roherlösberechnung', Name='In Roherlösberechnung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544203)
;

-- 2018-08-08T08:58:43.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='In Roherlösberechnung',PrintName='In Roherlösberechnung' WHERE AD_Element_ID=544203 AND AD_Language='de_CH'
;

-- 2018-08-08T08:58:43.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544203,'de_CH') 
;

-- 2018-08-08T09:00:04.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='In profit calculation',PrintName='In profit calculation',Description='Specifies whether the refund parameters shall be included when calculating the expected profit.' WHERE AD_Element_ID=544203 AND AD_Language='en_US'
;

-- 2018-08-08T09:00:04.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544203,'en_US') 
;

-- 2018-08-08T09:00:34.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560756,544203,0,20,540980,'IsUseInProfitCalculation',TO_TIMESTAMP('2018-08-08 09:00:34','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Legt fest, ob die Rückvergütungsparameter in die Berechnung des erwarteten Roherlöses (d.h. Ertrag/Marge) einfließen soll.','de.metas.contracts',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','In Roherlösberechnung',0,0,TO_TIMESTAMP('2018-08-08 09:00:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-08-08T09:00:34.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560756 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-08T09:00:39.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_RefundConfig','ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN IsUseInProfitCalculation CHAR(1) DEFAULT ''N'' CHECK (IsUseInProfitCalculation IN (''Y'',''N'')) NOT NULL')
;

-- 2018-08-08T09:03:21.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540447,0,540980,'IsUseInProfitCalculation=''N''','SQLS',TO_TIMESTAMP('2018-08-08 09:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Y','UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation','N',TO_TIMESTAMP('2018-08-08 09:03:21','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2018-08-08T09:03:21.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540447 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-08-08T09:04:05.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsUseInProfitCalculation=''N'' AND IsActive=''Y''',Updated=TO_TIMESTAMP('2018-08-08 09:04:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540447
;

-- 2018-08-08T09:04:19.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560129,540881,540447,0,TO_TIMESTAMP('2018-08-08 09:04:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',10,TO_TIMESTAMP('2018-08-08 09:04:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T09:04:41.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560130,540882,540447,0,'COALESCE(C_Flatrate_RefundConfig.M_Product_ID, 0)',TO_TIMESTAMP('2018-08-08 09:04:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',20,TO_TIMESTAMP('2018-08-08 09:04:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T09:05:04.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560756,540883,540447,0,TO_TIMESTAMP('2018-08-08 09:05:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',30,TO_TIMESTAMP('2018-08-08 09:05:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T09:05:11.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation ON C_Flatrate_RefundConfig (C_Flatrate_Conditions_ID,COALESCE(C_Flatrate_RefundConfig.M_Product_ID, 0),IsUseInProfitCalculation) WHERE IsUseInProfitCalculation='N' AND IsActive='Y'
;

-- 2018-08-08T09:05:11.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tgfn()
 RETURNS TRIGGER AS $UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE C_Flatrate_RefundConfig SET IsUseInProfitCalculation='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_Flatrate_Conditions_ID=NEW.C_Flatrate_Conditions_ID AND COALESCE(C_Flatrate_RefundConfig.M_Product_ID, 0)=COALESCE(NEW.C_Flatrate_RefundConfig.M_Product_ID, 0) AND IsUseInProfitCalculation=NEW.IsUseInProfitCalculation AND C_Flatrate_RefundConfig_ID<>NEW.C_Flatrate_RefundConfig_ID AND IsUseInProfitCalculation='N' AND IsActive='Y';
 ELSE
IF OLD.C_Flatrate_Conditions_ID<>NEW.C_Flatrate_Conditions_ID OR OLD.COALESCE(C_Flatrate_RefundConfig.M_Product_ID, 0)<>NEW.COALESCE(C_Flatrate_RefundConfig.M_Product_ID, 0) OR OLD.IsUseInProfitCalculation<>NEW.IsUseInProfitCalculation THEN
UPDATE C_Flatrate_RefundConfig SET IsUseInProfitCalculation='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_Flatrate_Conditions_ID=NEW.C_Flatrate_Conditions_ID AND COALESCE(C_Flatrate_RefundConfig.M_Product_ID, 0)=COALESCE(NEW.C_Flatrate_RefundConfig.M_Product_ID, 0) AND IsUseInProfitCalculation=NEW.IsUseInProfitCalculation AND C_Flatrate_RefundConfig_ID<>NEW.C_Flatrate_RefundConfig_ID AND IsUseInProfitCalculation='N' AND IsActive='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg$ LANGUAGE plpgsql;
;

-- 2018-08-08T09:05:11.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg ON C_Flatrate_RefundConfig
;

-- 2018-08-08T09:05:11.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg BEFORE INSERT OR UPDATE  ON C_Flatrate_RefundConfig FOR EACH ROW EXECUTE PROCEDURE UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tgfn()
;

-- 2018-08-08T09:06:18.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560756,565579,0,541106,TO_TIMESTAMP('2018-08-08 09:06:18','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob die Rückvergütungsparameter in die Berechnung des erwarteten Roherlöses (d.h. Ertrag/Marge) einfließen soll.',1,'de.metas.contracts','Y','N','N','N','N','N','N','N','In Roherlösberechnung',TO_TIMESTAMP('2018-08-08 09:06:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T09:06:18.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-08-08T09:06:48.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', SeqNo=70,Updated=TO_TIMESTAMP('2018-08-08 09:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565579
;

-- 2018-08-08T09:07:38.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,565579,0,541106,541612,552501,'F',TO_TIMESTAMP('2018-08-08 09:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','In Roherlösberechnung',120,0,0,TO_TIMESTAMP('2018-08-08 09:07:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T09:07:49.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-08-08 09:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552501
;

-- 2018-08-08T09:07:49.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2018-08-08 09:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552054
;

-- 2018-08-08T09:07:49.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2018-08-08 09:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552052
;

-- 2018-08-08T09:08:12.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2018-08-08 09:08:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552501
;

-- 2018-08-08T09:15:39.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(MinQty, 0)',Updated=TO_TIMESTAMP('2018-08-08 09:15:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540880
;

-- 2018-08-08T09:15:54.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS uc_c_flatrate_refundconfig
;

-- 2018-08-08T09:15:54.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_C_Flatrate_RefundConfig ON C_Flatrate_RefundConfig (C_Flatrate_Conditions_ID,COALESCE(C_Flatrate_RefundConfig.M_Product_ID, 0),COALESCE(MinQty, 0)) WHERE IsActive='Y'
;

-- 2018-08-08T09:16:08.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(M_Product_ID, 0)',Updated=TO_TIMESTAMP('2018-08-08 09:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540882
;

-- 2018-08-08T09:16:14.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(M_Product_ID, 0)',Updated=TO_TIMESTAMP('2018-08-08 09:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540862
;

-- 2018-08-08T09:16:27.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS uc_c_flatrate_refundconfig_isuseinprofitcalculation
;

-- 2018-08-08T09:16:27.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation ON C_Flatrate_RefundConfig (C_Flatrate_Conditions_ID,COALESCE(M_Product_ID, 0),IsUseInProfitCalculation) WHERE IsUseInProfitCalculation='N' AND IsActive='Y'
;

-- 2018-08-08T09:16:27.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tgfn()
 RETURNS TRIGGER AS $UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE C_Flatrate_RefundConfig SET IsUseInProfitCalculation='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_Flatrate_Conditions_ID=NEW.C_Flatrate_Conditions_ID AND COALESCE(M_Product_ID, 0)=COALESCE(NEW.M_Product_ID, 0) AND IsUseInProfitCalculation=NEW.IsUseInProfitCalculation AND C_Flatrate_RefundConfig_ID<>NEW.C_Flatrate_RefundConfig_ID AND IsUseInProfitCalculation='N' AND IsActive='Y';
 ELSE
IF OLD.C_Flatrate_Conditions_ID<>NEW.C_Flatrate_Conditions_ID OR COALESCE(OLD.M_Product_ID, 0)<>COALESCE(NEW.M_Product_ID, 0) OR OLD.IsUseInProfitCalculation<>NEW.IsUseInProfitCalculation THEN
UPDATE C_Flatrate_RefundConfig SET IsUseInProfitCalculation='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_Flatrate_Conditions_ID=NEW.C_Flatrate_Conditions_ID AND COALESCE(M_Product_ID, 0)=COALESCE(NEW.M_Product_ID, 0) AND IsUseInProfitCalculation=NEW.IsUseInProfitCalculation AND C_Flatrate_RefundConfig_ID<>NEW.C_Flatrate_RefundConfig_ID AND IsUseInProfitCalculation='N' AND IsActive='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg$ LANGUAGE plpgsql;
;

-- 2018-08-08T09:16:27.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg ON C_Flatrate_RefundConfig
;

-- 2018-08-08T09:16:27.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg BEFORE INSERT OR UPDATE  ON C_Flatrate_RefundConfig FOR EACH ROW EXECUTE PROCEDURE UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tgfn()
;

-- 2018-08-08T09:20:51.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsUseInProfitCalculation=''Y'' AND IsActive=''Y''',Updated=TO_TIMESTAMP('2018-08-08 09:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540447
;

-- 2018-08-08T09:21:14.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS uc_c_flatrate_refundconfig_isuseinprofitcalculation
;

-- 2018-08-08T09:21:14.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation ON C_Flatrate_RefundConfig (C_Flatrate_Conditions_ID,COALESCE(M_Product_ID, 0),IsUseInProfitCalculation) WHERE IsUseInProfitCalculation='Y' AND IsActive='Y'
;

CREATE OR REPLACE FUNCTION UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tgfn()
 RETURNS TRIGGER AS $UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE C_Flatrate_RefundConfig SET IsUseInProfitCalculation='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_Flatrate_Conditions_ID=NEW.C_Flatrate_Conditions_ID AND COALESCE(M_Product_ID, 0)=COALESCE(NEW.M_Product_ID, 0) AND IsUseInProfitCalculation=NEW.IsUseInProfitCalculation AND C_Flatrate_RefundConfig_ID<>NEW.C_Flatrate_RefundConfig_ID AND IsUseInProfitCalculation='N' AND IsActive='Y';
 ELSE
IF OLD.C_Flatrate_Conditions_ID<>NEW.C_Flatrate_Conditions_ID OR COALESCE(OLD.M_Product_ID, 0)<>COALESCE(NEW.M_Product_ID, 0) OR OLD.IsUseInProfitCalculation<>NEW.IsUseInProfitCalculation THEN
UPDATE C_Flatrate_RefundConfig SET IsUseInProfitCalculation='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_Flatrate_Conditions_ID=NEW.C_Flatrate_Conditions_ID AND COALESCE(M_Product_ID, 0)=COALESCE(NEW.M_Product_ID, 0) AND IsUseInProfitCalculation=NEW.IsUseInProfitCalculation AND C_Flatrate_RefundConfig_ID<>NEW.C_Flatrate_RefundConfig_ID AND IsUseInProfitCalculation='N' AND IsActive='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg$ LANGUAGE plpgsql;
;

-- 2018-08-08T09:21:14.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg ON C_Flatrate_RefundConfig
;

-- 2018-08-08T09:21:14.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tg BEFORE INSERT OR UPDATE  ON C_Flatrate_RefundConfig FOR EACH ROW EXECUTE PROCEDURE UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation_tgfn()
;

-- 2018-08-08T09:24:14.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET BeforeChangeCode='', ErrorMsg='Pro Produkt kann nur ein Datensatz für die Rohertragsberechnung benutzt werden.',Updated=TO_TIMESTAMP('2018-08-08 09:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540447
;

-- 2018-08-08T09:24:19.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET BeforeChangeCodeType='',Updated=TO_TIMESTAMP('2018-08-08 09:24:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540447
;

-- 2018-08-08T09:24:22.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS uc_c_flatrate_refundconfig_isuseinprofitcalculation
;

-- 2018-08-08T09:24:22.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_C_Flatrate_RefundConfig_IsUseInProfitCalculation ON C_Flatrate_RefundConfig (C_Flatrate_Conditions_ID,COALESCE(M_Product_ID, 0),IsUseInProfitCalculation) WHERE IsUseInProfitCalculation='Y' AND IsActive='Y'
;

-- 2018-08-08T09:24:29.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',ErrorMsg='Pro Produkt kann nur ein Datensatz für die Rohertragsberechnung benutzt werden.' WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540447
;

-- 2018-08-08T09:24:59.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',ErrorMsg='Only one record per product can be used to calculate expected profit.' WHERE AD_Language='en_US' AND AD_Index_Table_ID=540447
;

-- 2018-08-08T09:25:38.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET ErrorMsg='Pro Produkt kann nur ein Datensatz benutzt werden, um den erwarteten Roherlös zu berechnen.' WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540447
;

