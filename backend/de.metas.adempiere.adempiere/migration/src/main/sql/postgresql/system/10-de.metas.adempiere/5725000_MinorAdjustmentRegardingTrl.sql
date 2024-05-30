-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spaltenname
-- Column: ModCntr_Type.ColumnName
-- 2024-05-30T05:37:02.584Z
UPDATE AD_Field SET EntityType='de.metas.contracts', IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2024-05-30 08:37:02.583','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spaltenname
-- Column: ModCntr_Type.ColumnName
-- 2024-05-30T05:37:05.558Z
UPDATE AD_Field SET IsAlwaysUpdateable='N',Updated=TO_TIMESTAMP('2024-05-30 08:37:05.558','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Spaltenname
-- Column: ModCntr_Type.ColumnName
-- 2024-05-30T05:39:04.344Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-05-30 08:39:04.344','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728716
;


UPDATE modcntr_type
set value ='Protein - Average Added Value On Shipped Quantity',
    name = 'Protein - Durchschnittliche gelieferte Menge',
    description= 'Erstellt die Lieferlogs inklusive Protein für Protein Rechnungspositionen und Details in der Schlusszahlung'
where modcntr_type_id=540025;



UPDATE modcntr_type
set value ='HL - Average Added Value On Shipped Quantity',
    name = 'HL - Durchschnittliche gelieferte Menge',
    description= 'Erstellt die Lieferlogs inklusive HL für HL Rechnungspositionen und Details in der Schlusszahlung'
where modcntr_type_id=540024;

-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-30T06:33:16.975Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-05-30 09:33:16.975','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588286
;

-- 2024-05-30T06:33:18.732Z
INSERT INTO t_alter_column values('m_productprice','ScalePriceQuantityFrom','VARCHAR(5)',null,'Q')
;

-- 2024-05-30T06:33:18.745Z
UPDATE M_ProductPrice SET ScalePriceQuantityFrom='Q' WHERE ScalePriceQuantityFrom IS NULL
;

-- 2024-05-30T06:33:18.750Z
INSERT INTO t_alter_column values('m_productprice','ScalePriceQuantityFrom',null,'NOT NULL',null)
;


-- Value: MSG_ModularContractPrice_NoScalePrice
-- 2024-05-30T13:47:08.462Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545417,0,TO_TIMESTAMP('2024-05-30 16:47:08.284','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Es konnte kein Staffelpreis.','E',TO_TIMESTAMP('2024-05-30 16:47:08.284','YYYY-MM-DD HH24:MI:SS.US'),100,'MSG_ModularContractPrice_NoScalePrice')
;

-- 2024-05-30T13:47:08.494Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545417 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: MSG_ModularContractPrice_NoScalePrice
-- 2024-05-30T13:47:16.320Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-30 16:47:16.32','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545417
;

-- Value: MSG_ModularContractPrice_NoScalePrice
-- 2024-05-30T13:47:24.176Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='There is no scale price.',Updated=TO_TIMESTAMP('2024-05-30 16:47:24.176','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545417
;

-- Value: MSG_ModularContractPrice_NoScalePrice
-- 2024-05-30T13:47:28.347Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-30 16:47:28.347','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545417
;

--------------------



DROP INDEX ModCntr_Specific_Price_MinValue;


-- 2024-05-30T15:11:25.427Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540793,0,542405,TO_TIMESTAMP('2024-05-30 18:11:25.242','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Shall be one price with minimum value for one contract.','Y','Y','ModCntr_Specific_Price_MinValue','N',TO_TIMESTAMP('2024-05-30 18:11:25.242','YYYY-MM-DD HH24:MI:SS.US'),100,'')
;

-- 2024-05-30T15:11:25.431Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540793 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-05-30T15:11:43.948Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588321,541402,540793,0,TO_TIMESTAMP('2024-05-30 18:11:43.813','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',10,TO_TIMESTAMP('2024-05-30 18:11:43.813','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-30T15:12:15.711Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588099,541403,540793,0,TO_TIMESTAMP('2024-05-30 18:12:15.584','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',20,TO_TIMESTAMP('2024-05-30 18:12:15.584','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-30T15:12:24.997Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588320,541404,540793,0,TO_TIMESTAMP('2024-05-30 18:12:24.88','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',30,TO_TIMESTAMP('2024-05-30 18:12:24.88','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-30T15:12:32.473Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588100,541405,540793,0,TO_TIMESTAMP('2024-05-30 18:12:32.356','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',40,TO_TIMESTAMP('2024-05-30 18:12:32.356','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-30T15:12:43.076Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588097,541406,540793,0,TO_TIMESTAMP('2024-05-30 18:12:42.959','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',50,TO_TIMESTAMP('2024-05-30 18:12:42.959','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-30T15:12:48.003Z
DROP INDEX IF EXISTS modcntr_specific_price_minvalue
;

-- 2024-05-30T15:12:48.010Z
CREATE UNIQUE INDEX ModCntr_Specific_Price_MinValue ON ModCntr_Specific_Price (MinValue,ModCntr_Module_ID,IsScalePrice,M_Product_ID,C_Flatrate_Term_ID)
;


UPDATE ad_index_table_trl
SET errormsg = 'Shall be one price with minimum value for one contract.', istranslated = 'Y'
WHERE ad_index_table_id = 540793
  AND ad_language IN ('de_CH', 'de_DE')
;

