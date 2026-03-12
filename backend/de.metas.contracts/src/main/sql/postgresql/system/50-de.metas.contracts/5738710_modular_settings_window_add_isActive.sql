
-- Element: M_Raw_Product_ID
-- 2024-11-08T08:29:36.137Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Roh-/Produkt', PrintName='Roh-/Produkt',Updated=TO_TIMESTAMP('2024-11-08 09:29:36.137','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583068 AND AD_Language='de_CH'
;

-- 2024-11-08T08:29:36.139Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583068,'de_CH')
;

-- Element: M_Raw_Product_ID
-- 2024-11-08T08:32:04.153Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Roh-/Produkt', PrintName='Roh-/Produkt',Updated=TO_TIMESTAMP('2024-11-08 09:32:04.153','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583068 AND AD_Language='de_DE'
;

-- 2024-11-08T08:32:04.154Z
UPDATE AD_Element SET Name='Roh-/Produkt', PrintName='Roh-/Produkt' WHERE AD_Element_ID=583068
;

-- 2024-11-08T08:32:04.353Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583068,'de_DE')
;

-- 2024-11-08T08:32:04.355Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583068,'de_DE')
;

-- Element: M_Raw_Product_ID
-- 2024-11-08T08:32:33.725Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Raw-/Product', PrintName='Raw-/Product',Updated=TO_TIMESTAMP('2024-11-08 09:32:33.725','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583068 AND AD_Language='en_US'
;

-- 2024-11-08T08:32:33.727Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583068,'en_US')
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> flags.Aktiv
-- Column: ModCntr_Settings.IsActive
-- 2024-11-08T08:34:33.120Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716339,0,547013,550849,627002,'F',TO_TIMESTAMP('2024-11-08 09:34:32.991','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',5,0,0,TO_TIMESTAMP('2024-11-08 09:34:32.991','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> flags.Aktiv
-- Column: ModCntr_Settings.IsActive
-- 2024-11-08T08:35:09.071Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-11-08 09:35:09.071','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=627002
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> org+client.Organisation
-- Column: ModCntr_Settings.AD_Org_ID
-- 2024-11-08T08:35:09.077Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-11-08 09:35:09.077','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617989
;

-- Column: ModCntr_Settings.IsActive
-- 2024-11-08T08:35:34.069Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-11-08 09:35:34.069','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586786
;

-- Column: ModCntr_Settings.IsActive
-- 2024-11-08T09:32:12.185Z
UPDATE AD_Column SET FilterDefaultValue='Y',Updated=TO_TIMESTAMP('2024-11-08 10:32:12.185','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586786
;

-- Reference Item: _YesNo -> N_No
-- 2024-11-08T11:41:20.599Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nein',Updated=TO_TIMESTAMP('2024-11-08 12:41:20.599','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=668
;

-- Reference Item: _YesNo -> N_No
-- 2024-11-08T11:41:30.127Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nein',Updated=TO_TIMESTAMP('2024-11-08 12:41:30.127','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=668
;

-- 2024-11-08T11:41:30.128Z
UPDATE AD_Ref_List SET Name='Nein' WHERE AD_Ref_List_ID=668
;

-- Reference Item: _YesNo -> Y_Yes
-- 2024-11-08T11:41:45.783Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Ja',Updated=TO_TIMESTAMP('2024-11-08 12:41:45.783','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=667
;

-- Reference Item: _YesNo -> Y_Yes
-- 2024-11-08T11:41:52.533Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Ja',Updated=TO_TIMESTAMP('2024-11-08 12:41:52.533','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=667
;

-- 2024-11-08T11:41:52.535Z
UPDATE AD_Ref_List SET Name='Ja' WHERE AD_Ref_List_ID=667
;
