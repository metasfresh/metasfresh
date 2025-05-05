-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString1
-- Column: SAP_GLJournalLine.UserElementString1
-- 2023-04-03T14:19:40.097Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586394,713589,0,546731,TO_TIMESTAMP('2023-04-03 17:19:39','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.acct','Y','N','N','N','N','N','N','N','UserElementString1',TO_TIMESTAMP('2023-04-03 17:19:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:19:40.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713589 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:19:40.101Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-04-03T14:19:40.107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713589
;

-- 2023-04-03T14:19:40.108Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713589)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> UserElementString1
-- Column: SAP_GLJournalLine.UserElementString1
-- 2023-04-03T14:20:30.159Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/''X''@=Y',Updated=TO_TIMESTAMP('2023-04-03 17:20:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713589
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString1
-- Column: SAP_GLJournalLine.UserElementString1
-- 2023-04-03T14:21:44.763Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713589,0,546731,550193,616498,'F',TO_TIMESTAMP('2023-04-03 17:21:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString1',50,0,0,TO_TIMESTAMP('2023-04-03 17:21:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString1
-- Column: SAP_GLJournalLine.UserElementString1
-- 2023-04-03T14:21:52.559Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-04-03 17:21:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616498
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString1
-- Column: SAP_GLJournalLine.UserElementString1
-- 2023-04-03T14:23:53.039Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-04-03 17:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616498
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Sales order
-- Column: SAP_GLJournalLine.C_OrderSO_ID
-- 2023-04-03T14:23:53.047Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-04-03 17:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614562
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2023-04-03T14:23:53.053Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-04-03 17:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614563
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> org&client.Organisation
-- Column: SAP_GLJournalLine.AD_Org_ID
-- 2023-04-03T14:23:53.059Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-04-03 17:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614564
;

