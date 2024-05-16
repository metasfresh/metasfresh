-- Run mode: SWING_CLIENT

-- Element: AddInterestDays
-- 2024-05-15T11:46:21.017Z
UPDATE AD_Element_Trl SET Name='Zusätzliche Zinstage', PrintName='Zusätzliche Zinstage',Updated=TO_TIMESTAMP('2024-05-15 14:46:21.017','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583089 AND AD_Language='de_CH'
;

-- 2024-05-15T11:46:21.048Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583089,'de_CH')
;

-- Element: AddInterestDays
-- 2024-05-15T11:46:23.313Z
UPDATE AD_Element_Trl SET Name='Zusätzliche Zinstage', PrintName='Zusätzliche Zinstage',Updated=TO_TIMESTAMP('2024-05-15 14:46:23.313','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583089 AND AD_Language='de_DE'
;

-- 2024-05-15T11:46:23.314Z
UPDATE AD_Element SET Name='Zusätzliche Zinstage', PrintName='Zusätzliche Zinstage' WHERE AD_Element_ID=583089
;

-- 2024-05-15T11:46:23.609Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583089,'de_DE')
;

-- 2024-05-15T11:46:23.611Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583089,'de_DE')
;

-- Element: AddInterestDays
-- 2024-05-15T11:46:25.415Z
UPDATE AD_Element_Trl SET Name='Zusätzliche Zinstage', PrintName='Zusätzliche Zinstage',Updated=TO_TIMESTAMP('2024-05-15 14:46:25.415','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583089 AND AD_Language='fr_CH'
;

-- 2024-05-15T11:46:25.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583089,'fr_CH')
;

-- Element: AddInterestDays
-- 2024-05-15T11:46:28.409Z
UPDATE AD_Element_Trl SET Name='Zusätzliche Zinstage', PrintName='Zusätzliche Zinstage',Updated=TO_TIMESTAMP('2024-05-15 14:46:28.408','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583089 AND AD_Language='it_IT'
;

-- 2024-05-15T11:46:28.410Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583089,'it_IT')
;

-- Element: MatchedAmt
-- 2024-05-15T11:46:55.180Z
UPDATE AD_Element_Trl SET Name='Übereinstimmender Betrag', PrintName='Übereinstimmender Betrag',Updated=TO_TIMESTAMP('2024-05-15 14:46:55.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583099 AND AD_Language='de_CH'
;

-- 2024-05-15T11:46:55.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583099,'de_CH')
;

-- Element: MatchedAmt
-- 2024-05-15T11:46:57.225Z
UPDATE AD_Element_Trl SET Name='Übereinstimmender Betrag', PrintName='Übereinstimmender Betrag',Updated=TO_TIMESTAMP('2024-05-15 14:46:57.225','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583099 AND AD_Language='de_DE'
;

-- 2024-05-15T11:46:57.226Z
UPDATE AD_Element SET Name='Übereinstimmender Betrag', PrintName='Übereinstimmender Betrag' WHERE AD_Element_ID=583099
;

-- 2024-05-15T11:46:57.483Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583099,'de_DE')
;

-- 2024-05-15T11:46:57.484Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583099,'de_DE')
;

-- Element: MatchedAmt
-- 2024-05-15T11:47:00.303Z
UPDATE AD_Element_Trl SET Name='Übereinstimmender Betrag', PrintName='Übereinstimmender Betrag',Updated=TO_TIMESTAMP('2024-05-15 14:47:00.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583099 AND AD_Language='fr_CH'
;

-- 2024-05-15T11:47:00.304Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583099,'fr_CH')
;

-- Element: MatchedAmt
-- 2024-05-15T11:47:02.335Z
UPDATE AD_Element_Trl SET Name='Übereinstimmender Betrag', PrintName='Übereinstimmender Betrag',Updated=TO_TIMESTAMP('2024-05-15 14:47:02.335','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583099 AND AD_Language='it_IT'
;

-- 2024-05-15T11:47:02.337Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583099,'it_IT')
;

-- Element: MatchedAmt
-- 2024-05-15T11:47:05.241Z
UPDATE AD_Element_Trl SET PrintName='Matched amount',Updated=TO_TIMESTAMP('2024-05-15 14:47:05.241','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583099 AND AD_Language='en_US'
;

-- 2024-05-15T11:47:05.243Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583099,'en_US')
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Währung
-- Column: ModCntr_InvoicingGroup.C_Currency_ID
-- 2024-05-15T11:53:54.592Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588264,728707,0,547205,0,TO_TIMESTAMP('2024-05-15 14:53:54.417','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag',0,'de.metas.contracts','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','N','N','N','Währung',0,40,0,1,1,TO_TIMESTAMP('2024-05-15 14:53:54.417','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-15T11:53:54.599Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728707 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-15T11:53:54.600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2024-05-15T11:53:54.618Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728707
;

-- 2024-05-15T11:53:54.619Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728707)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> amts.Währung
-- Column: ModCntr_InvoicingGroup.C_Currency_ID
-- 2024-05-15T11:54:40.928Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728707,0,547205,551831,624735,'F',TO_TIMESTAMP('2024-05-15 14:54:40.752','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',20,0,0,TO_TIMESTAMP('2024-05-15 14:54:40.752','YYYY-MM-DD HH24:MI:SS.US'),100)
;
--No longer used
-- -- Value: de.metas.contracts.modular.interest.InterestNotCalculated
-- -- 2024-05-15T12:36:30.498Z
-- INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545398,0,TO_TIMESTAMP('2024-05-15 15:36:30.371','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Die Zinsen müssen zunächst für die Rechnungsstellungsgruppe {} berechnet werden.','E',TO_TIMESTAMP('2024-05-15 15:36:30.371','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.interest.InterestNotCalculated')
-- ;
--
-- -- 2024-05-15T12:36:30.499Z
-- INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545398 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
-- ;
--
-- -- Value: de.metas.contracts.modular.interest.InterestNotCalculated
-- -- 2024-05-15T12:36:46.103Z
-- UPDATE AD_Message_Trl SET MsgText='The interest must first be calculated for the invoicing group {}.',Updated=TO_TIMESTAMP('2024-05-15 15:36:46.103','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545398
-- ;

-- Value: de.metas.contracts.modular.settings.interceptor.InterimRequiredInvoicingGroup
-- 2024-05-15T18:41:38.441Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545399,0,TO_TIMESTAMP('2024-05-15 21:41:38.197','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Interim kann nicht für eine andere Fakturierungsgruppe als {} eingestellt werden!','E',TO_TIMESTAMP('2024-05-15 21:41:38.197','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.InterimRequiredInvoicingGroup')
;

-- 2024-05-15T18:41:38.445Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545399 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.InterimRequiredInvoicingGroup
-- 2024-05-15T18:42:45.844Z
UPDATE AD_Message_Trl SET MsgText='Interim cannot be set for an invoicing group other than: {}!',Updated=TO_TIMESTAMP('2024-05-15 21:42:45.844','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545399
;

