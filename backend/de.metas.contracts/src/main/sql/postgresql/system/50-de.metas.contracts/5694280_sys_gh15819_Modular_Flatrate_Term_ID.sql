-- 2023-07-03T18:05:31.530020400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582523,0,'Modular_Flatrate_Term_ID',TO_TIMESTAMP('2023-07-03 21:05:30.48','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Modular Contract','Modular Contract',TO_TIMESTAMP('2023-07-03 21:05:30.48','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-07-03T18:05:31.542016300Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582523 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Modular_Flatrate_Term_ID
-- 2023-07-03T15:25:28.820570200Z
UPDATE AD_Element_Trl SET Description='A document linked to a modular contract will generate modular contract logs',Updated=TO_TIMESTAMP('2023-07-03 18:25:28.82','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582523 AND AD_Language='en_US'
;

-- 2023-07-03T15:25:28.848618Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582523,'en_US') 
;

-- Name: C_Flatrate_Term_Modular
-- 2023-07-03T18:09:19.403403100Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541780,TO_TIMESTAMP('2023-07-03 21:09:19.26','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Flatrate_Term_Modular',TO_TIMESTAMP('2023-07-03 21:09:19.26','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-03T18:09:19.407407900Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541780 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Flatrate_Term_Modular
-- Table: C_Flatrate_Term
-- Key: C_Flatrate_Term.C_Flatrate_Term_ID
-- 2023-07-03T18:12:35.681252600Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,545802,0,541780,540320,TO_TIMESTAMP('2023-07-03 21:12:35.63','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-03 21:12:35.63','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: C_Flatrate_Term_Modular
-- Table: C_Flatrate_Term
-- Key: C_Flatrate_Term.C_Flatrate_Term_ID
-- 2023-07-03T18:22:20.713678500Z
UPDATE AD_Ref_Table SET AD_Display=545802,Updated=TO_TIMESTAMP('2023-07-03 21:22:20.713','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541780
;

-- Column: M_InventoryLine.Modular_Flatrate_Term_ID
-- 2023-07-03T18:38:19.622042200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587023,582523,0,30,541780,322,'Modular_Flatrate_Term_ID',TO_TIMESTAMP('2023-07-03 21:38:19.424','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Modular Contract',0,0,TO_TIMESTAMP('2023-07-03 21:38:19.424','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-07-03T18:38:19.629043700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587023 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-03T18:38:20.239685300Z
/* DDL */  select update_Column_Translation_From_AD_Element(582523) 
;

-- 2023-07-03T18:38:36.556659Z
/* DDL */ SELECT public.db_alter_table('M_InventoryLine','ALTER TABLE public.M_InventoryLine ADD COLUMN Modular_Flatrate_Term_ID NUMERIC(10)')
;

-- 2023-07-03T18:38:36.844638600Z
ALTER TABLE M_InventoryLine ADD CONSTRAINT ModularFlatrateTerm_MInventoryLine FOREIGN KEY (Modular_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED
;

-- Name: Modular_Flatrate_Term_For_InventoryLine
-- 2023-07-04T09:45:12.138667100Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540643,'c_flatrate_term.c_flatrate_term_id IN (SELECT DISTINCT contract.c_flatrate_term_id
                                              FROM M_Warehouse warehouse
                                                       INNER JOIN c_flatrate_term contract ON warehouse.c_bpartner_id = contract.bill_bpartner_id
                                                       INNER JOIN C_Flatrate_Conditions conditions ON contract.c_flatrate_conditions_id = conditions.c_flatrate_conditions_id
                                              WHERE conditions.type_conditions = ''ModularContract''
                                                AND warehouse.M_Warehouse_ID = @M_Warehouse_ID/-1@)',TO_TIMESTAMP('2023-07-04 12:45:11.889','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Modular_Flatrate_Term_For_InventoryLine','S',TO_TIMESTAMP('2023-07-04 12:45:11.889','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_InventoryLine.Modular_Flatrate_Term_ID
-- 2023-07-04T09:45:48.094879600Z
UPDATE AD_Column SET AD_Val_Rule_ID=540643,Updated=TO_TIMESTAMP('2023-07-04 12:45:48.094','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587023
;

-- Element: Modular_Flatrate_Term_ID
-- 2023-07-05T16:31:50.971859300Z
UPDATE AD_Element_Trl SET Description='Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.', Name='Modularer Vertrag', PrintName='Modularer Vertrag',Updated=TO_TIMESTAMP('2023-07-05 19:31:50.971','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582523 AND AD_Language='de_CH'
;

-- 2023-07-05T16:31:50.996866200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582523,'de_CH') 
;

-- Element: Modular_Flatrate_Term_ID
-- 2023-07-05T16:32:04.181158Z
UPDATE AD_Element_Trl SET Description='Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.', Name='Modularer Vertrag', PrintName='Modularer Vertrag',Updated=TO_TIMESTAMP('2023-07-05 19:32:04.181','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582523 AND AD_Language='de_DE'
;

-- 2023-07-05T16:32:04.183192400Z
UPDATE AD_Element SET Description='Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.', Name='Modularer Vertrag', PrintName='Modularer Vertrag' WHERE AD_Element_ID=582523
;

-- 2023-07-05T16:32:04.558307100Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582523,'de_DE') 
;

-- 2023-07-05T16:32:04.561272200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582523,'de_DE') 
;

-- Element: Modular_Flatrate_Term_ID
-- 2023-07-05T16:32:11.771150100Z
UPDATE AD_Element_Trl SET Description='Document lines linked to a modular contract will generate contract module logs.',Updated=TO_TIMESTAMP('2023-07-05 19:32:11.771','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582523 AND AD_Language='en_US'
;

-- 2023-07-05T16:32:11.774151100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582523,'en_US') 
;

-- Element: Modular_Flatrate_Term_ID
-- 2023-07-05T16:32:23.212538600Z
UPDATE AD_Element_Trl SET Description='Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.', Name='Modularer Vertrag', PrintName='Modularer Vertrag',Updated=TO_TIMESTAMP('2023-07-05 19:32:23.211','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582523 AND AD_Language='fr_CH'
;

-- 2023-07-05T16:32:23.214502500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582523,'fr_CH') 
;

-- Element: Modular_Flatrate_Term_ID
-- 2023-07-05T16:32:33.853477800Z
UPDATE AD_Element_Trl SET Description='Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.', Name='Modularer Vertrag', PrintName='Modularer Vertrag',Updated=TO_TIMESTAMP('2023-07-05 19:32:33.853','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582523 AND AD_Language='it_IT'
;

-- 2023-07-05T16:32:33.856518400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582523,'it_IT') 
;

