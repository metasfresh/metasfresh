-- Run mode: SWING_CLIENT

-- Element: ModCntr_Type_ID
-- 2024-09-19T10:37:36.811Z
UPDATE AD_Element_Trl SET PrintName='Computing Method',Updated=TO_TIMESTAMP('2024-09-19 12:37:36.811','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='en_US'
;

-- 2024-09-19T10:37:36.829Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'en_US')
;

-- Element: ModCntr_Type_ID
-- 2024-09-19T10:37:39.162Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-19 12:37:39.162','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='de_CH'
;

-- 2024-09-19T10:37:39.164Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'de_CH')
;

-- Element: ModCntr_Type_ID
-- 2024-09-19T10:37:54.180Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-19 12:37:54.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='de_DE'
;

-- 2024-09-19T10:37:54.186Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582395,'de_DE')
;

-- 2024-09-19T10:37:54.195Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'de_DE')
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Verkaufstransaktion
-- Column: ModCntr_Settings.IsSOTrx
-- 2024-09-19T11:03:21.219Z
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2024-09-19 13:03:21.219','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=717243
;

-- Column: ModCntr_Settings.IsSOTrx
-- 2024-09-23T06:59:41.436Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=319, DefaultValue='', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-09-23 08:59:41.436','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587164
;

-- 2024-09-19T11:03:21.219Z
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2024-09-19 13:03:21.219','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=717243
;

-- Column: ModCntr_Settings.IsSOTrx
-- 2024-09-23T06:59:41.436Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=319, DefaultValue='',Updated=TO_TIMESTAMP('2024-09-23 08:59:41.436','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587164
;

-- Column: ModCntr_Settings.IsSOTrx
-- 2024-09-23T08:00:20.697Z
UPDATE AD_Column SET ReadOnlyLogic='@IsSOTrx/''''@=''N'' | @IsSOTrx/''''@=''Y''',Updated=TO_TIMESTAMP('2024-09-23 10:00:20.697','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587164
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Verkaufstransaktion
-- Column: ModCntr_Settings.IsSOTrx
-- 2024-09-23T08:15:07.479Z
UPDATE AD_Field SET ReadOnlyLogic='@IsSOTrx/''''@=''N'' | @IsSOTrx/''''@=''Y''',Updated=TO_TIMESTAMP('2024-09-23 10:15:07.479','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=717243
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Lagerkosten Startdatum
-- Column: ModCntr_Settings.StorageCostStartDate
-- 2024-09-23T08:31:51.029Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''N''',Updated=TO_TIMESTAMP('2024-09-23 10:31:51.029','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728053
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Zinssatz
-- Column: ModCntr_Settings.InterestRate
-- 2024-09-23T08:33:05.138Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''N''',Updated=TO_TIMESTAMP('2024-09-23 10:33:05.138','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728700
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Zusätzliche Zinstage
-- Column: ModCntr_Settings.AddInterestDays
-- 2024-09-23T08:33:12.378Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''N''',Updated=TO_TIMESTAMP('2024-09-23 10:33:12.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728701
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Kuppelprodukt
-- Column: ModCntr_Settings.M_Co_Product_ID
-- 2024-09-23T08:33:28.780Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''N''',Updated=TO_TIMESTAMP('2024-09-23 10:33:28.78','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727314
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Verarbeitetes Erzeugnis
-- Column: ModCntr_Settings.M_Processed_Product_ID
-- 2024-09-23T08:33:37.197Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''N''',Updated=TO_TIMESTAMP('2024-09-23 10:33:37.197','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727315
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Akonto Preis %
-- Column: ModCntr_Settings.InterimPricePercent
-- 2024-09-23T08:34:01.425Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''N''',Updated=TO_TIMESTAMP('2024-09-23 10:34:01.425','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728998
;

-- Name: InvoicingGroup for IsSOTrx
-- 2024-09-23T09:28:33.408Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540690,' (CASE WHEN (''@IsSOTrx@''=''Y'') THEN AD_Ref_List.Value IN (''Service'') ELSE TRUE END)',TO_TIMESTAMP('2024-09-23 11:28:33.171','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','InvoicingGroup for IsSOTrx','S',TO_TIMESTAMP('2024-09-23 11:28:33.171','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: ModCntr_Module.InvoicingGroup
-- 2024-09-23T09:29:09.645Z
UPDATE AD_Column SET AD_Val_Rule_ID=540690,Updated=TO_TIMESTAMP('2024-09-23 11:29:09.645','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586807
;

-- Name: ModCntr_Type for Processed Product and isSOTrx
-- 2024-09-24T07:43:11.195Z
UPDATE AD_Val_Rule SET Code='((@M_Product_ID/-1@ = @M_Processed_Product_ID/-1@ OR modcntr_type.modularcontracthandlertype != ''SalesOnProcessedProduct'') AND ModCntr_Type.IsSOTrx = @IsSOTrx@)', Name='ModCntr_Type for Processed Product and isSOTrx',Updated=TO_TIMESTAMP('2024-09-24 09:43:11.193','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540674
;
