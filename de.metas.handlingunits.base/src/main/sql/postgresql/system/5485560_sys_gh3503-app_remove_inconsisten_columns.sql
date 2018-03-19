
SELECT db_alter_table('M_ShipmentSchedule', 'ALTER TABLE public.M_ShipmentSchedule DROP COLUMN QtyDelivered_LU;');
SELECT db_alter_table('M_ShipmentSchedule', 'ALTER TABLE public.M_ShipmentSchedule DROP COLUMN QtyDelivered_TU;');
SELECT db_alter_table('M_ShipmentSchedule', 'ALTER TABLE public.M_ShipmentSchedule DROP COLUMN QtyToDeliver_LU;');
SELECT db_alter_table('M_ShipmentSchedule', 'ALTER TABLE public.M_ShipmentSchedule DROP COLUMN QtyToDeliver_TU;');

DELETE FROM ad_ui_element WHERE AD_Field_ID=554652;

-- 2018-02-15T17:26:25.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554655
;

-- 2018-02-15T17:26:25.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554655
;

-- 2018-02-15T17:26:30.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554652
;

-- 2018-02-15T17:26:30.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554652
;

-- 2018-02-15T17:26:38.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554654
;

-- 2018-02-15T17:26:38.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554654
;

-- 2018-02-15T17:26:41.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554653
;

-- 2018-02-15T17:26:41.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554653
;

-- 2018-02-15T17:27:27.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551068
;

-- 2018-02-15T17:27:27.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=551068
;

-- 2018-02-15T17:27:37.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551065
;

-- 2018-02-15T17:27:37.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=551065
;

-- 2018-02-15T17:27:45.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551067
;

-- 2018-02-15T17:27:45.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=551067
;

-- 2018-02-15T17:27:50.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551066
;

-- 2018-02-15T17:27:50.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=551066
;

-- 2018-02-16T07:24:14.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559121,542490,0,29,540542,'N','QtyTU',TO_TIMESTAMP('2018-02-16 07:24:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Menge TU',0,0,TO_TIMESTAMP('2018-02-16 07:24:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-16T07:24:14.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559121 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T07:24:23.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_QtyPicked','ALTER TABLE public.M_ShipmentSchedule_QtyPicked ADD COLUMN QtyTU NUMERIC')
;

-- 2018-02-16T07:24:43.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559122,542491,0,29,540542,'N','QtyLU',TO_TIMESTAMP('2018-02-16 07:24:43','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Menge LU',0,0,TO_TIMESTAMP('2018-02-16 07:24:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-16T07:24:43.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559122 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T07:24:49.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2018-02-16 07:24:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559122
;

-- 2018-02-16T07:24:51.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_QtyPicked','ALTER TABLE public.M_ShipmentSchedule_QtyPicked ADD COLUMN QtyLU NUMERIC')
;

-- 2018-02-16T08:23:30.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554578
;

-- 2018-02-16T08:23:30.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554578
;

-- 2018-02-16T08:24:38.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554575
;

-- 2018-02-16T08:24:38.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554575
;

-- QtyToDeliver_LU_Ausliefermenge (LU)
delete from ad_ui_element where ad_field_id=554578;

-- fields referencing QtyToDeliver_LU
delete from ad_ui_element where ad_field_id in (select ad_field_id from ad_field where ad_column_id=551038);

delete from ad_field where ad_column_id=551038;

SELECT db_alter_table('M_DeliveryDay_Alloc', 'ALTER TABLE M_DeliveryDay_Alloc DROP COLUMN QtyToDeliver_LU');

-- 2018-02-16T08:32:31.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551038
;

-- 2018-02-16T08:32:31.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=551038
;


-- QtyToDeliver_TU_Ausliefermenge (TU)
delete from ad_ui_element where ad_field_id=554575;

-- 2018-02-16T08:34:12.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554610
;

-- 2018-02-16T08:34:12.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554610
;

-- QtyToDeliver_TU
delete from ad_ui_element where ad_field_id in (select ad_field_id from ad_field where ad_column_id=551035);

delete from ad_field where ad_column_id=551035;

-- 2018-02-16T08:36:32.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551035
;

-- 2018-02-16T08:36:32.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=551035
;

