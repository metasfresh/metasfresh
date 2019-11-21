-- 2019-11-19T11:23:25.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=2589, ColumnName='QtyEntered', Description='Die Eingegebene Menge basiert auf der gewählten Mengeneinheit', FieldLength=10, Help='Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt', Name='Menge',Updated=TO_TIMESTAMP('2019-11-19 12:23:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544285
;

-- 2019-11-19T11:23:25.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge', Description='Die Eingegebene Menge basiert auf der gewählten Mengeneinheit', Help='Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt' WHERE AD_Column_ID=544285
;

-- 2019-11-19T11:23:25.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(2589) 
;

select db_alter_table('C_OLCand', 'ALTER TABLE C_OLCand RENAME COLUMN Qty TO QtyEntered');


-- 2019-11-19T12:10:53.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Help='', Value='QtyEntered',Updated=TO_TIMESTAMP('2019-11-19 13:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=549081
;

-- 2019-11-20T05:28:00.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D', TechnicalNote='QtyItemCapacity is "managed" by the handling units module, but its value is required elsewhere too, to compute qtyOrdered in case of "TU-UOMs" such as "COLI" or "TU".
That''s why the ET is set to "Dictionary".',Updated=TO_TIMESTAMP('2019-11-20 06:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549765
;

-- 2019-11-20T05:41:00.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D', TechnicalNote='QtyItemCapacity is "managed" by the handling units module, but its value is required elsewhere too, to compute qtyOrdered in case of "TU-UOMs" such as "COLI" or "TU".
That''s why the ET is set to "Dictionary".',Updated=TO_TIMESTAMP('2019-11-20 06:41:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549983
;

-- 2019-11-20T05:46:36.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.ordercandidate', TechnicalNote='QtyItemCapacity is "managed" by the handling units module, but its value is required elsewhere too, to compute qtyOrdered in case of "TU-UOMs" such as "COLI" or "TU".
That''s why the ET is set to "de.metas.ordercandidate".',Updated=TO_TIMESTAMP('2019-11-20 06:46:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549983
;

DROP INDEX IF EXISTS public.c_olcand_externalid;

-- 2019-11-20T14:22:25.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540509,0,540244,TO_TIMESTAMP('2019-11-20 15:22:25','YYYY-MM-DD HH24:MI:SS'),100,'The combination of ExternalLineId and ExternalHeaderId needs to be unique per AD_Org_ID','de.metas.ordercandidate','Y','Y','C_OLCand_External_ID','N',TO_TIMESTAMP('2019-11-20 15:22:25','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2019-11-20T14:22:25.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540509 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-11-20T14:23:05.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,563706,540973,540509,0,'COALESCE(ExternalHeaderId,'''')',TO_TIMESTAMP('2019-11-20 15:23:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y',10,TO_TIMESTAMP('2019-11-20 15:23:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-20T14:23:25.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,559589,540974,540509,0,'COALESCE(ExternalLineId,'''')',TO_TIMESTAMP('2019-11-20 15:23:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y',20,TO_TIMESTAMP('2019-11-20 15:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-20T14:23:38.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,544273,540975,540509,0,TO_TIMESTAMP('2019-11-20 15:23:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y',30,TO_TIMESTAMP('2019-11-20 15:23:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-20T14:23:43.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_OLCand_External_ID ON C_OLCand (COALESCE(ExternalHeaderId,''),COALESCE(ExternalLineId,''),AD_Org_ID) WHERE IsActive='Y'
;

-- 2019-11-21T13:04:16.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544944,0,TO_TIMESTAMP('2019-11-21 14:04:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Die TU-Maßeinheit {0} der Auftragsdispozeile erfordert die Kapazitätsangabe einer Packvorschrift, damit die entsprechende CU-Menge ermittelt werden kann. ID der Auftragsdispozeile={1}.','E',TO_TIMESTAMP('2019-11-21 14:04:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.ordercandidate.OLCandPIIPPackingValidator')
;

-- 2019-11-21T13:04:16.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544944 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-11-21T13:06:16.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.handlingunits.ordercandidate.UOMForTUsCapacityRequired',Updated=TO_TIMESTAMP('2019-11-21 14:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544944
;

-- 2019-11-21T13:43:46.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_UOM_ID',Updated=TO_TIMESTAMP('2019-11-21 14:43:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547270
;

-- 2019-11-21T13:44:10.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547210
;

-- 2019-11-21T13:44:34.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='AD_DataDestination_ID',Updated=TO_TIMESTAMP('2019-11-21 14:44:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547202
;

-- 2019-11-21T13:44:45.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-11-21 14:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547202
;

-- 2019-11-21T13:46:16.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570748,0,540282,540962,564066,'F',TO_TIMESTAMP('2019-11-21 14:46:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalHeaderId',50,0,0,TO_TIMESTAMP('2019-11-21 14:46:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-21T13:46:33.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,569300,0,540282,540962,564067,'F',TO_TIMESTAMP('2019-11-21 14:46:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalLineId',60,0,0,TO_TIMESTAMP('2019-11-21 14:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-21T13:46:37.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-11-21 14:46:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564067
;

-- 2019-11-21T13:46:39.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-11-21 14:46:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564066
;

-- 2019-11-21T13:47:05.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-11-21 14:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569300
;

-- 2019-11-21T13:47:13.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManualDiscount/''N''@=''Y''',Updated=TO_TIMESTAMP('2019-11-21 14:47:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550287
;

-- 2019-11-21T16:07:38.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@PriceDifference/null@!=null',Updated=TO_TIMESTAMP('2019-11-21 17:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555186
;

