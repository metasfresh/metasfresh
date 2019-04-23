-- 2019-04-22T13:47:08.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='/*whereclause from AD_Tab_ID=255*/ M_Inventory.M_Inventory_ID in (select i.M_Inventory_ID from  M_Inventory i  join C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  where  dt.DocBaseType = ''MMI'' and dt.DocSubType IN (''IAH'',''ISH''))',Updated=TO_TIMESTAMP('2019-04-22 13:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=255
;


-- 2019-04-22T14:08:11.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567654,580077,0,256,TO_TIMESTAMP('2019-04-22 14:08:11','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','HU aggregation type',TO_TIMESTAMP('2019-04-22 14:08:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-22T14:08:11.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-22T14:08:11.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576580) 
;

-- 2019-04-22T14:08:11.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580077
;

-- 2019-04-22T14:08:11.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(580077)
;

-- 2019-04-22T14:09:36.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-04-22 14:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=580077
;

-- 2019-04-22T14:27:28.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@HUAggregationType/''S''@=''S''',Updated=TO_TIMESTAMP('2019-04-22 14:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563119
;

-- 2019-04-22T14:27:36.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@HUAggregationType/''S''@=''S''',Updated=TO_TIMESTAMP('2019-04-22 14:27:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563120
;

-- 2019-04-23T15:50:38.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-04-23 15:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567664
;

-- 2019-04-23T15:50:39.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inventoryline_hu','M_HU_ID','NUMERIC(10)',null,null)
;

-- 2019-04-23T15:50:39.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inventoryline_hu','M_HU_ID',null,'NULL',null)
;

-- 2019-04-23T15:51:43.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N', TechnicalNote='M_HU_ID is not mandatory; it might be empty until the respective M_Inventory is completed (that behavior is analog to M_InventoryLine''s M_HU_ID).',Updated=TO_TIMESTAMP('2019-04-23 15:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567664
;

-- 2019-04-23T18:50:13.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType = ''MMI'' AND C_DocType. DocSubType IN (''ISH'', ''IAH'')',Updated=TO_TIMESTAMP('2019-04-23 18:50:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540393
;

-- 2019-04-23T19:06:27.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='M_Inventory',Updated=TO_TIMESTAMP('2019-04-23 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=168
;

-- 2019-04-23T19:07:09.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@HUAggregationType/''S''@=''S''',Updated=TO_TIMESTAMP('2019-04-23 19:07:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565630
;

-- 2019-04-23T19:07:16.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@HUAggregationType/''S''@=''S''',Updated=TO_TIMESTAMP('2019-04-23 19:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565629
;

-- 2019-04-23T20:21:33.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.inventory.interceptor.NotAllLinesCounted',Updated=TO_TIMESTAMP('2019-04-23 20:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544771
;

-- 2019-04-23T20:22:55.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544913,0,TO_TIMESTAMP('2019-04-23 20:22:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Es gibt bereits Inventurzeilen mit einem abweichenden Aggregations-Typ','E',TO_TIMESTAMP('2019-04-23 20:22:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.inventory.ExistingLinesWithDifferentHUAggregationType')
;

-- 2019-04-23T20:22:55.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544913 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-04-23T20:24:26.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-23 20:24:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544913
;

-- 2019-04-23T20:24:45.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='There are inventory lines with a deviating HU aggregation type',Updated=TO_TIMESTAMP('2019-04-23 20:24:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544913
;

