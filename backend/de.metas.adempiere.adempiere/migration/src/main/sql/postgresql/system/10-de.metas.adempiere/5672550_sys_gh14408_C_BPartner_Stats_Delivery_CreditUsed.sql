-- 2023-01-19T09:45:38.514Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581934,0,'Delivery_CreditUsed',TO_TIMESTAMP('2023-01-19 11:45:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Specifies the sum of money that was already used from the credit for completed delivery instructions. ','Y','Delivery Credit Used','Delivery Credit Used',TO_TIMESTAMP('2023-01-19 11:45:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T09:45:38.519Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581934 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Delivery_CreditUsed
-- 2023-01-19T09:47:40.811Z
UPDATE AD_Element_Trl SET Help='Specifies the amount of money that was already used from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-19 11:47:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='de_CH'
;

-- 2023-01-19T09:47:40.841Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'de_CH') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-19T09:47:46.646Z
UPDATE AD_Element_Trl SET Help='Specifies the amount of money that was already used from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-19 11:47:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='de_DE'
;

-- 2023-01-19T09:47:46.647Z
UPDATE AD_Element SET Help='Specifies the amount of money that was already used from the credit for completed delivery instructions. ' WHERE AD_Element_ID=581934
;

-- 2023-01-19T09:47:46.995Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581934,'de_DE') 
;

-- 2023-01-19T09:47:46.996Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'de_DE') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-19T09:48:00.482Z
UPDATE AD_Element_Trl SET Help='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-19 11:48:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='en_US'
;

-- 2023-01-19T09:48:00.484Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'en_US') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-19T09:48:04.179Z
UPDATE AD_Element_Trl SET Help='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-19 11:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='fr_CH'
;

-- 2023-01-19T09:48:04.181Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'fr_CH') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-19T09:48:07.176Z
UPDATE AD_Element_Trl SET Help='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-19 11:48:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='nl_NL'
;

-- 2023-01-19T09:48:07.178Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'nl_NL') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-19T09:48:15.807Z
UPDATE AD_Element_Trl SET Help='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-19 11:48:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='de_DE'
;

-- 2023-01-19T09:48:15.808Z
UPDATE AD_Element SET Help='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ' WHERE AD_Element_ID=581934
;

-- 2023-01-19T09:48:16.143Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581934,'de_DE') 
;

-- 2023-01-19T09:48:16.144Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'de_DE') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-19T09:48:19.109Z
UPDATE AD_Element_Trl SET Help='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-19 11:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='de_CH'
;

-- 2023-01-19T09:48:19.111Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'de_CH') 
;

-- Column: C_BPartner_Stats.Delivery_CreditUsed
-- 2023-01-19T09:48:26.620Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585529,581934,0,12,540763,'Delivery_CreditUsed',TO_TIMESTAMP('2023-01-19 11:48:26','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,14,'Specifies the amount of money that was already spent from the credit for completed delivery instructions. ','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Delivery Credit Used',0,0,TO_TIMESTAMP('2023-01-19 11:48:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T09:48:26.622Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585529 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T09:48:26.625Z
/* DDL */  select update_Column_Translation_From_AD_Element(581934) 
;

-- 2023-01-19T09:48:27.550Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Stats','ALTER TABLE public.C_BPartner_Stats ADD COLUMN Delivery_CreditUsed NUMERIC')
;











------------------------------------



-- Field: Geschäftspartner(123,D) -> Statistik(540739,D) -> Credit limit indicator %
-- Column: C_BPartner_Stats.CreditLimitIndicator
-- 2023-01-19T09:52:00.166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559022,710281,0,540739,TO_TIMESTAMP('2023-01-19 11:51:59','YYYY-MM-DD HH24:MI:SS'),100,'Percent of Credit used from the limit',10,'D','','Y','Y','N','N','N','N','N','Credit limit indicator %',TO_TIMESTAMP('2023-01-19 11:51:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T09:52:00.167Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710281 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T09:52:00.169Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543845) 
;

-- 2023-01-19T09:52:00.180Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710281
;

-- 2023-01-19T09:52:00.182Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710281)
;

-- Field: Geschäftspartner(123,D) -> Statistik(540739,D) -> Delivery Credit Used
-- Column: C_BPartner_Stats.Delivery_CreditUsed
-- 2023-01-19T09:52:00.273Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585529,710282,0,540739,TO_TIMESTAMP('2023-01-19 11:52:00','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Specifies the amount of money that was already spent from the credit for completed delivery instructions. ','Y','Y','N','N','N','N','N','Delivery Credit Used',TO_TIMESTAMP('2023-01-19 11:52:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T09:52:00.274Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710282 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T09:52:00.276Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581934) 
;

-- 2023-01-19T09:52:00.278Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710282
;

-- 2023-01-19T09:52:00.279Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710282)
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Delivery Credit Used
-- Column: C_BPartner_Stats.Delivery_CreditUsed
-- 2023-01-19T11:38:29.185Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710282,0,540739,1000038,614738,'F',TO_TIMESTAMP('2023-01-19 13:38:28','YYYY-MM-DD HH24:MI:SS'),100,'Specifies the amount of money that was already spent from the credit for completed delivery instructions. ','Y','N','N','Y','N','N','N',0,'Delivery Credit Used',35,0,0,TO_TIMESTAMP('2023-01-19 13:38:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Delivery Credit Used
-- Column: C_BPartner_Stats.Delivery_CreditUsed
-- 2023-01-19T11:38:35.218Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-19 13:38:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614738
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Offene Posten
-- Column: C_BPartner_Stats.OpenItems
-- 2023-01-19T11:38:35.225Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-19 13:38:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000362
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Währung
-- Column: C_BPartner_Stats.C_Currency_ID
-- 2023-01-19T11:38:35.232Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-01-19 13:38:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550943
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Aktiv
-- Column: C_BPartner_Stats.IsActive
-- 2023-01-19T11:38:35.239Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-01-19 13:38:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000358
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner_Stats.AD_Org_ID
-- 2023-01-19T11:38:35.246Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-01-19 13:38:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546570
;

-- 2023-01-19T16:03:29.982Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581938,0,'Delivery_CreditStatus',TO_TIMESTAMP('2023-01-19 18:03:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Credit Status','Delivery Credit Status',TO_TIMESTAMP('2023-01-19 18:03:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T16:03:29.984Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581938 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_Stats.Delivery_CreditStatus
-- 2023-01-19T16:03:56.876Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585543,581938,0,10,540763,'Delivery_CreditStatus',TO_TIMESTAMP('2023-01-19 18:03:56','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Delivery Credit Status',0,0,TO_TIMESTAMP('2023-01-19 18:03:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T16:03:56.877Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585543 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T16:03:56.881Z
/* DDL */  select update_Column_Translation_From_AD_Element(581938) 
;

-- Column: C_BPartner_Stats.Delivery_CreditStatus
-- 2023-01-19T16:04:19.841Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=289,Updated=TO_TIMESTAMP('2023-01-19 18:04:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585543
;

-- 2023-01-19T16:04:29.731Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Stats','ALTER TABLE public.C_BPartner_Stats ADD COLUMN Delivery_CreditStatus CHAR(1)')
;


