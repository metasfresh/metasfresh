-- Run mode: SWING_CLIENT

-- Column: ModCntr_Type.IsActive
-- 2024-05-31T06:22:05.892Z
UPDATE AD_Column SET FilterDefaultValue='Y', FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-05-31 09:22:05.892','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586744
;

-- Element: IsReadyForDefinitiveInvoice
-- 2024-05-31T09:22:20.712Z
UPDATE AD_Element_Trl SET Name='Ready for definitive final invoice', PrintName='Ready for definitive final invoice',Updated=TO_TIMESTAMP('2024-05-31 12:22:20.712','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583126 AND AD_Language='en_US'
;

-- 2024-05-31T09:22:20.759Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583126,'en_US')
;

-- Element: IsReadyForDefinitiveInvoice
-- 2024-05-31T09:22:22.782Z
UPDATE AD_Element_Trl SET Name='Bereit für endgültige Schlussrechnung', PrintName='Bereit für endgültige Schlussrechnung',Updated=TO_TIMESTAMP('2024-05-31 12:22:22.782','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583126 AND AD_Language='de_CH'
;

-- 2024-05-31T09:22:22.784Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583126,'de_CH')
;

-- Element: IsReadyForDefinitiveInvoice
-- 2024-05-31T09:22:24.539Z
UPDATE AD_Element_Trl SET Name='Bereit für endgültige Schlussrechnung', PrintName='Bereit für endgültige Schlussrechnung',Updated=TO_TIMESTAMP('2024-05-31 12:22:24.539','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583126 AND AD_Language='de_DE'
;

-- 2024-05-31T09:22:24.542Z
UPDATE AD_Element SET Name='Bereit für endgültige Schlussrechnung', PrintName='Bereit für endgültige Schlussrechnung' WHERE AD_Element_ID=583126
;

-- 2024-05-31T09:22:24.825Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583126,'de_DE')
;

-- 2024-05-31T09:22:24.826Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583126,'de_DE')
;

-- Element: IsReadyForDefinitiveInvoice
-- 2024-05-31T09:22:26.719Z
UPDATE AD_Element_Trl SET Name='Bereit für endgültige Schlussrechnung', PrintName='Bereit für endgültige Schlussrechnung',Updated=TO_TIMESTAMP('2024-05-31 12:22:26.719','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583126 AND AD_Language='fr_CH'
;

-- 2024-05-31T09:22:26.721Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583126,'fr_CH')
;

-- Element: IsReadyForDefinitiveInvoice
-- 2024-05-31T09:22:31.463Z
UPDATE AD_Element_Trl SET Name='Bereit für endgültige Schlussrechnung', PrintName='Bereit für endgültige Schlussrechnung',Updated=TO_TIMESTAMP('2024-05-31 12:22:31.463','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583126 AND AD_Language='it_IT'
;

-- 2024-05-31T09:22:31.465Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583126,'it_IT')
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- 2024-05-31T09:24:54.909Z
UPDATE AD_Process_Trl SET Name='Endgültige Schlussrechnung erstellen',Updated=TO_TIMESTAMP('2024-05-31 12:24:54.909','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585396
;

-- 2024-05-31T09:24:54.910Z
UPDATE AD_Process SET Name='Endgültige Schlussrechnung erstellen' WHERE AD_Process_ID=585396
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- 2024-05-31T09:24:56.302Z
UPDATE AD_Process_Trl SET Name='Endgültige Schlussrechnung erstellen',Updated=TO_TIMESTAMP('2024-05-31 12:24:56.302','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585396
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- 2024-05-31T09:24:58.711Z
UPDATE AD_Process_Trl SET Name='Endgültige Schlussrechnung erstellen',Updated=TO_TIMESTAMP('2024-05-31 12:24:58.711','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585396
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- 2024-05-31T09:25:03.217Z
UPDATE AD_Process_Trl SET Name='Endgültige Schlussrechnung erstellen',Updated=TO_TIMESTAMP('2024-05-31 12:25:03.217','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Process_ID=585396
;

-- Reference Item: C_DocType SubType -> DCM_DefinitiveCreditMemo
-- 2024-05-31T09:30:25.758Z
UPDATE AD_Ref_List_Trl SET Name='Credit Memo for Definitive Final Invoice',Updated=TO_TIMESTAMP('2024-05-31 12:30:25.758','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543691
;

-- Reference Item: C_DocType SubType -> DCM_DefinitiveCreditMemo
-- 2024-05-31T09:30:27.804Z
UPDATE AD_Ref_List_Trl SET Name='Gutschrift zu endgültiger Schlussrechnung',Updated=TO_TIMESTAMP('2024-05-31 12:30:27.804','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543691
;

-- Reference Item: C_DocType SubType -> DCM_DefinitiveCreditMemo
-- 2024-05-31T09:30:28.655Z
UPDATE AD_Ref_List_Trl SET Name='Gutschrift zu endgültiger Schlussrechnung',Updated=TO_TIMESTAMP('2024-05-31 12:30:28.655','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Ref_List_ID=543691
;

-- Reference Item: C_DocType SubType -> DCM_DefinitiveCreditMemo
-- 2024-05-31T09:30:29.225Z
UPDATE AD_Ref_List_Trl SET Name='Gutschrift zu endgültiger Schlussrechnung',Updated=TO_TIMESTAMP('2024-05-31 12:30:29.225','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543691
;

-- 2024-05-31T09:30:29.226Z
UPDATE AD_Ref_List SET Name='Gutschrift zu endgültiger Schlussrechnung' WHERE AD_Ref_List_ID=543691
;

-- Reference Item: C_DocType SubType -> DCM_DefinitiveCreditMemo
-- 2024-05-31T09:30:30.028Z
UPDATE AD_Ref_List_Trl SET Name='Gutschrift zu endgültiger Schlussrechnung',Updated=TO_TIMESTAMP('2024-05-31 12:30:30.028','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543691
;

