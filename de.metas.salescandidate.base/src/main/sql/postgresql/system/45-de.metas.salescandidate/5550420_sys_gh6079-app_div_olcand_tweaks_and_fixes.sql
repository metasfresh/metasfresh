-- 2020-01-24T08:18:44.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_User.C_BPartner_ID=@DropShip_BPartner_ID/0@',Updated=TO_TIMESTAMP('2020-01-24 09:18:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=168
;

-- 2020-01-24T08:19:00.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569886,53460,0,18,110,540244,168,'DropShip_User_ID',TO_TIMESTAMP('2020-01-24 09:19:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Business Partner Contact for drop shipment','de.metas.ordercandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Lieferkontakt',0,0,TO_TIMESTAMP('2020-01-24 09:19:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-01-24T08:19:00.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569886 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-01-24T08:19:00.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(53460) 
;

-- 2020-01-24T08:21:17.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_User.C_BPartner_ID=COALESCE(NULLIF(@DropShip_BPartner_Override_ID/0@,0), @DropShip_BPartner_ID/0@)', Description='works whether DropShip_BPartner_Override_ID is in context or not',Updated=TO_TIMESTAMP('2020-01-24 09:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=168
;

-- 2020-01-24T08:21:25.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_User.C_BPartner_ID=COALESCE(NULLIF(@DropShip_BPartner_Override_ID/0@, 0), @DropShip_BPartner_ID/0@)',Updated=TO_TIMESTAMP('2020-01-24 09:21:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=168
;

-- 2020-01-24T08:22:59.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN DropShip_User_ID NUMERIC(10)')
;

-- 2020-01-24T08:22:59.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OLCand ADD CONSTRAINT DropShipUser_COLCand FOREIGN KEY (DropShip_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2020-01-24T08:24:05.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergabe-Kontakt', PrintName='Übergabe-Kontakt',Updated=TO_TIMESTAMP('2020-01-24 09:24:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577442 AND AD_Language='de_CH'
;

-- 2020-01-24T08:24:05.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577442,'de_CH') 
;

-- 2020-01-24T08:24:12.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergabe-Kontakt', PrintName='Übergabe-Kontakt',Updated=TO_TIMESTAMP('2020-01-24 09:24:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577442 AND AD_Language='de_DE'
;

-- 2020-01-24T08:24:12.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577442,'de_DE') 
;

-- 2020-01-24T08:24:12.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577442,'de_DE') 
;

-- 2020-01-24T08:24:12.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HandOver_User_ID', Name='Übergabe-Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=577442
;

-- 2020-01-24T08:24:12.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_User_ID', Name='Übergabe-Kontakt', Description=NULL, Help=NULL, AD_Element_ID=577442 WHERE UPPER(ColumnName)='HANDOVER_USER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-24T08:24:12.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_User_ID', Name='Übergabe-Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=577442 AND IsCentrallyMaintained='Y'
;

-- 2020-01-24T08:24:12.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Übergabe-Kontakt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577442) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577442)
;

-- 2020-01-24T08:24:12.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Übergabe-Kontakt', Name='Übergabe-Kontakt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577442)
;

-- 2020-01-24T08:24:12.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Übergabe-Kontakt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577442
;

-- 2020-01-24T08:24:12.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Übergabe-Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID = 577442
;

-- 2020-01-24T08:24:12.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Übergabe-Kontakt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577442
;

-- 2020-01-24T08:24:26.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Handover contact', PrintName='Handover contact',Updated=TO_TIMESTAMP('2020-01-24 09:24:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577442 AND AD_Language='en_US'
;

-- 2020-01-24T08:24:26.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577442,'en_US') 
;

-- 2020-01-24T08:25:37.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569887,577442,0,18,110,540244,540477,'HandOver_User_ID',TO_TIMESTAMP('2020-01-24 09:25:37','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Übergabe-Kontakt',0,0,TO_TIMESTAMP('2020-01-24 09:25:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-01-24T08:25:37.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569887 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-01-24T08:25:37.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577442) 
;

-- 2020-01-24T08:45:25.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_User.C_BPartner_ID=COALESCE(NULLIF(@HandOver_Partner_Override_ID/0@, 0), @HandOver_Partner_ID/0@)', Description='works whether HandOver_Partner_Override_ID is in the ctx or not',Updated=TO_TIMESTAMP('2020-01-24 09:45:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540477
;

-- 2020-01-24T08:47:07.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU_PI_Item_Product.M_Product_ID=COALESCE(NULLIF(@M_Product_ID/0@), @M_Product_Override_ID/0@)
AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DateCandidate@'' AND ( ''@DateCandidate@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
	( SELECT i.M_HU_PI_Item_ID 
	from M_HU_PI_Item i 
	where i.M_HU_PI_Version_ID IN
		(SELECT v.M_HU_PI_Version_ID 
		from M_HU_PI_Version v 
		where v.HU_UnitType = ''TU''
		)
	)',Updated=TO_TIMESTAMP('2020-01-24 09:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540266
;

-- 2020-01-24T08:52:54.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU_PI_Item_Product.M_Product_ID=COALESCE(NULLIF(@M_Product_Override_ID/0@, 0), @M_Product_ID/0@)
AND ( M_HU_PI_Item_Product.C_BPartner_ID = COALESCE(NULLIF(@C_BPartner_Override_ID/0@, 0), @C_BPartner_ID/0@) OR COALESCE(M_HU_PI_Item_Product.C_BPartner_ID, 0) = 0)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DateCandidate@'' 
AND ( ''@DateCandidate@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
	( SELECT i.M_HU_PI_Item_ID 
	from M_HU_PI_Item i 
	where i.M_HU_PI_Version_ID IN
		(SELECT v.M_HU_PI_Version_ID 
		from M_HU_PI_Version v 
		where v.HU_UnitType = ''TU''
		)
	)', EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2020-01-24 09:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540266
;

-- 2020-01-24T10:53:43.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569886,595931,0,540282,TO_TIMESTAMP('2020-01-24 11:53:43','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Contact for drop shipment',10,'de.metas.ordercandidate','Y','Y','N','N','N','N','N','Lieferkontakt',TO_TIMESTAMP('2020-01-24 11:53:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T10:53:43.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=595931 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-01-24T10:53:43.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53460) 
;

-- 2020-01-24T10:53:43.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=595931
;

-- 2020-01-24T10:53:43.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(595931)
;

-- 2020-01-24T10:53:43.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569887,595932,0,540282,TO_TIMESTAMP('2020-01-24 11:53:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','Y','N','N','N','N','N','Übergabe-Kontakt',TO_TIMESTAMP('2020-01-24 11:53:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T10:53:43.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=595932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-01-24T10:53:43.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577442) 
;

-- 2020-01-24T10:53:43.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=595932
;

-- 2020-01-24T10:53:43.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(595932)
;

-- 2020-01-24T10:59:42.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN HandOver_User_ID NUMERIC(10)')
;

-- 2020-01-24T10:59:42.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OLCand ADD CONSTRAINT HandOverUser_COLCand FOREIGN KEY (HandOver_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2020-01-24T11:01:28.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540968, SeqNo=60,Updated=TO_TIMESTAMP('2020-01-24 12:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548675
;

-- 2020-01-24T11:03:20.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541156, SeqNo=90,Updated=TO_TIMESTAMP('2020-01-24 12:03:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548675
;

-- 2020-01-24T11:03:51.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2020-01-24 12:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548675
;

-- 2020-01-24T11:06:21.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547063,0,540282,541156,565154,'F',TO_TIMESTAMP('2020-01-24 12:06:21','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','N','N',0,'AD_User_ID',90,0,0,TO_TIMESTAMP('2020-01-24 12:06:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T11:06:42.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=565154
;

-- 2020-01-24T11:09:00.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergabe-Partner eff.', PrintName='Übergabe-Partner eff.',Updated=TO_TIMESTAMP('2020-01-24 12:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543090 AND AD_Language='de_CH'
;

-- 2020-01-24T11:09:00.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543090,'de_CH') 
;

-- 2020-01-24T11:13:47.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Handover Partner eff.', PrintName='Handover Partner eff.',Updated=TO_TIMESTAMP('2020-01-24 12:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543090 AND AD_Language='en_US'
;

-- 2020-01-24T11:13:47.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543090,'en_US') 
;

-- 2020-01-24T11:14:05.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='HandOver_Partner_Effective_ID',Updated=TO_TIMESTAMP('2020-01-24 12:14:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548682
;

-- 2020-01-24T11:14:19.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='HandOver_Location_Effective_ID',Updated=TO_TIMESTAMP('2020-01-24 12:14:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548681
;

-- 2020-01-24T11:14:30.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='DropShip_BPartner_Effective_ID',Updated=TO_TIMESTAMP('2020-01-24 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548680
;

-- 2020-01-24T11:14:39.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='DropShip_Location_Effective_ID',Updated=TO_TIMESTAMP('2020-01-24 12:14:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548679
;

-- 2020-01-24T11:14:48.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_BP_Location_Effective_ID',Updated=TO_TIMESTAMP('2020-01-24 12:14:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548678
;

-- 2020-01-24T11:14:58.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_BPartner_Effective_ID',Updated=TO_TIMESTAMP('2020-01-24 12:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548677
;

-- 2020-01-24T11:15:53.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2020-01-24 12:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548680
;

-- 2020-01-24T11:20:12.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='HandOver_Partner_Override_ID',Updated=TO_TIMESTAMP('2020-01-24 12:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548690
;

-- 2020-01-24T11:20:30.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=65,Updated=TO_TIMESTAMP('2020-01-24 12:20:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548682
;

-- 2020-01-24T11:21:05.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='HandOver_Location_Override_ID',Updated=TO_TIMESTAMP('2020-01-24 12:21:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548689
;

-- 2020-01-24T11:21:17.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='DropShip_BPartner_Override_ID',Updated=TO_TIMESTAMP('2020-01-24 12:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548688
;

-- 2020-01-24T11:21:27.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='DropShip_Location_Override_ID',Updated=TO_TIMESTAMP('2020-01-24 12:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548687
;

-- 2020-01-24T11:21:42.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_BP_Location_Override_ID',Updated=TO_TIMESTAMP('2020-01-24 12:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548686
;

-- 2020-01-24T11:21:56.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_BPartner_Override_ID',Updated=TO_TIMESTAMP('2020-01-24 12:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548685
;

-- 2020-01-24T11:22:14.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='M_HU_PI_Item_Product_Override_ID',Updated=TO_TIMESTAMP('2020-01-24 12:22:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548684
;

-- 2020-01-24T11:22:26.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='M_Product_Override_ID',Updated=TO_TIMESTAMP('2020-01-24 12:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548683
;

-- 2020-01-24T11:22:52.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=65,Updated=TO_TIMESTAMP('2020-01-24 12:22:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548690
;

-- 2020-01-24T11:23:08.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2020-01-24 12:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548688
;

-- 2020-01-24T11:25:55.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2020-01-24 12:25:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548678
;

-- 2020-01-24T11:27:41.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,554082,0,540565,548677,TO_TIMESTAMP('2020-01-24 12:27:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-01-24 12:27:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T11:28:00.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2020-01-24 12:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548679
;

-- 2020-01-24T11:28:12.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,556942,0,540566,548680,TO_TIMESTAMP('2020-01-24 12:28:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-01-24 12:28:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T11:28:33.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2020-01-24 12:28:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548678
;

-- 2020-01-24T11:28:37.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2020-01-24 12:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548681
;

-- 2020-01-24T11:28:48.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,556946,0,540567,548682,TO_TIMESTAMP('2020-01-24 12:28:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-01-24 12:28:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T12:23:37.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2020-01-24 13:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548689
;

-- 2020-01-24T12:23:51.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,556945,0,540568,548690,TO_TIMESTAMP('2020-01-24 13:23:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-01-24 13:23:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T12:23:58.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2020-01-24 13:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548687
;

-- 2020-01-24T12:24:07.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,556940,0,540569,548688,TO_TIMESTAMP('2020-01-24 13:24:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-01-24 13:24:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T12:24:13.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2020-01-24 13:24:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548686
;

-- 2020-01-24T12:24:21.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,554065,0,540570,548685,TO_TIMESTAMP('2020-01-24 13:24:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-01-24 13:24:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T12:27:51.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2020-01-24 13:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548684
;

-- 2020-01-24T12:28:08.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,555178,0,540571,548683,TO_TIMESTAMP('2020-01-24 13:28:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-01-24 13:28:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T12:40:50.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2020-01-24 13:40:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548676
;

-- 2020-01-24T12:41:10.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,555179,0,540572,548675,TO_TIMESTAMP('2020-01-24 13:41:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-01-24 13:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T13:03:45.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergabe-Partner eff.', PrintName='Übergabe-Partner eff.',Updated=TO_TIMESTAMP('2020-01-24 14:03:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543090 AND AD_Language='de_DE'
;

-- 2020-01-24T13:03:45.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543090,'de_DE') 
;

-- 2020-01-24T13:03:45.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543090,'de_DE') 
;

-- 2020-01-24T13:03:45.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HandOver_Partner_Effective_ID', Name='Übergabe-Partner eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=543090
;

-- 2020-01-24T13:03:45.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_Effective_ID', Name='Übergabe-Partner eff.', Description=NULL, Help=NULL, AD_Element_ID=543090 WHERE UPPER(ColumnName)='HANDOVER_PARTNER_EFFECTIVE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-24T13:03:45.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_Effective_ID', Name='Übergabe-Partner eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=543090 AND IsCentrallyMaintained='Y'
;

-- 2020-01-24T13:03:45.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Übergabe-Partner eff.', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543090) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543090)
;

-- 2020-01-24T13:03:45.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Übergabe-Partner eff.', Name='Übergabe-Partner eff.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543090)
;

-- 2020-01-24T13:03:45.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Übergabe-Partner eff.', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543090
;

-- 2020-01-24T13:03:45.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Übergabe-Partner eff.', Description=NULL, Help=NULL WHERE AD_Element_ID = 543090
;

-- 2020-01-24T13:03:45.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Übergabe-Partner eff.', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543090
;

-- 2020-01-24T13:04:20.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergabe-Partner', PrintName='Übergabe-Partner',Updated=TO_TIMESTAMP('2020-01-24 14:04:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542280 AND AD_Language='de_CH'
;

-- 2020-01-24T13:04:20.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542280,'de_CH') 
;

-- 2020-01-24T13:04:35.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Handover partner', PrintName='Handover partner',Updated=TO_TIMESTAMP('2020-01-24 14:04:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542280 AND AD_Language='en_US'
;

-- 2020-01-24T13:04:35.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542280,'en_US') 
;

-- 2020-01-24T13:04:41.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergabe-Partner', PrintName='Übergabe-Partner',Updated=TO_TIMESTAMP('2020-01-24 14:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542280 AND AD_Language='de_DE'
;

-- 2020-01-24T13:04:41.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542280,'de_DE') 
;

-- 2020-01-24T13:04:41.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542280,'de_DE') 
;

-- 2020-01-24T13:04:41.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HandOver_Partner_ID', Name='Übergabe-Partner', Description=NULL, Help=NULL WHERE AD_Element_ID=542280
;

-- 2020-01-24T13:04:41.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_ID', Name='Übergabe-Partner', Description=NULL, Help=NULL, AD_Element_ID=542280 WHERE UPPER(ColumnName)='HANDOVER_PARTNER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-24T13:04:41.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_ID', Name='Übergabe-Partner', Description=NULL, Help=NULL WHERE AD_Element_ID=542280 AND IsCentrallyMaintained='Y'
;

-- 2020-01-24T13:04:41.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Übergabe-Partner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542280) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542280)
;

-- 2020-01-24T13:04:41.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Übergabe-Partner', Name='Übergabe-Partner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542280)
;

-- 2020-01-24T13:04:41.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Übergabe-Partner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542280
;

-- 2020-01-24T13:04:41.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Übergabe-Partner', Description=NULL, Help=NULL WHERE AD_Element_ID = 542280
;

-- 2020-01-24T13:04:41.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Übergabe-Partner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542280
;

-- 2020-01-24T13:05:12.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergabe-Partner abw.', PrintName='Übergabe-Partner abw.',Updated=TO_TIMESTAMP('2020-01-24 14:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='de_CH'
;

-- 2020-01-24T13:05:12.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'de_CH') 
;

-- 2020-01-24T13:05:22.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Handover partner override', PrintName='Handover partner override',Updated=TO_TIMESTAMP('2020-01-24 14:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='en_US'
;

-- 2020-01-24T13:05:22.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'en_US') 
;

-- 2020-01-24T13:05:30.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergabe-Partner abw.', PrintName='Übergabe-Partner abw.',Updated=TO_TIMESTAMP('2020-01-24 14:05:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='de_DE'
;

-- 2020-01-24T13:05:30.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'de_DE') 
;

-- 2020-01-24T13:05:30.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543089,'de_DE') 
;

-- 2020-01-24T13:05:30.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HandOver_Partner_Override_ID', Name='Übergabe-Partner abw.', Description=NULL, Help=NULL WHERE AD_Element_ID=543089
;

-- 2020-01-24T13:05:30.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_Override_ID', Name='Übergabe-Partner abw.', Description=NULL, Help=NULL, AD_Element_ID=543089 WHERE UPPER(ColumnName)='HANDOVER_PARTNER_OVERRIDE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-24T13:05:30.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_Override_ID', Name='Übergabe-Partner abw.', Description=NULL, Help=NULL WHERE AD_Element_ID=543089 AND IsCentrallyMaintained='Y'
;

-- 2020-01-24T13:05:30.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Übergabe-Partner abw.', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543089) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543089)
;

-- 2020-01-24T13:05:30.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Übergabe-Partner abw.', Name='Übergabe-Partner abw.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543089)
;

-- 2020-01-24T13:05:30.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Übergabe-Partner abw.', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543089
;

-- 2020-01-24T13:05:30.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Übergabe-Partner abw.', Description=NULL, Help=NULL WHERE AD_Element_ID = 543089
;

-- 2020-01-24T13:05:30.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Übergabe-Partner abw.', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543089
;

-- 2020-01-24T13:07:15.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', Name='Käufer', PrintName='Käufer',Updated=TO_TIMESTAMP('2020-01-24 14:07:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000947 AND AD_Language='de_CH'
;

-- 2020-01-24T13:07:15.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000947,'de_CH') 
;

-- 2020-01-24T13:07:27.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', Name='Buyer', PrintName='Buyer',Updated=TO_TIMESTAMP('2020-01-24 14:07:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000947 AND AD_Language='en_US'
;

-- 2020-01-24T13:07:27.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000947,'en_US') 
;

-- 2020-01-24T13:07:38.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Käufer', PrintName='Käufer',Updated=TO_TIMESTAMP('2020-01-24 14:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000947 AND AD_Language='de_DE'
;

-- 2020-01-24T13:07:38.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000947,'de_DE') 
;

-- 2020-01-24T13:07:38.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1000947,'de_DE') 
;

-- 2020-01-24T13:07:38.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Käufer', Description='', Help='' WHERE AD_Element_ID=1000947
;

-- 2020-01-24T13:07:38.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Käufer', Description='', Help='' WHERE AD_Element_ID=1000947 AND IsCentrallyMaintained='Y'
;

-- 2020-01-24T13:07:38.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Käufer', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1000947) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1000947)
;

-- 2020-01-24T13:07:38.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Käufer', Name='Käufer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1000947)
;

-- 2020-01-24T13:07:38.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Käufer', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 1000947
;

-- 2020-01-24T13:07:38.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Käufer', Description='', Help='' WHERE AD_Element_ID = 1000947
;

-- 2020-01-24T13:07:38.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Käufer', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1000947
;

-- 2020-01-24T13:08:32.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Buyer override', PrintName='Buyer override',Updated=TO_TIMESTAMP('2020-01-24 14:08:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000816 AND AD_Language='en_US'
;

-- 2020-01-24T13:08:32.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000816,'en_US') 
;

-- 2020-01-24T13:08:42.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', Name='Käufer abw.', PrintName='Käufer abw.',Updated=TO_TIMESTAMP('2020-01-24 14:08:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000816 AND AD_Language='de_DE'
;

-- 2020-01-24T13:08:42.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000816,'de_DE') 
;

-- 2020-01-24T13:08:42.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1000816,'de_DE') 
;

-- 2020-01-24T13:08:42.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Käufer abw.', Description='', Help='' WHERE AD_Element_ID=1000816
;

-- 2020-01-24T13:08:42.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Käufer abw.', Description='', Help='' WHERE AD_Element_ID=1000816 AND IsCentrallyMaintained='Y'
;

-- 2020-01-24T13:08:42.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Käufer abw.', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1000816) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1000816)
;

-- 2020-01-24T13:08:42.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Käufer abw.', Name='Käufer abw.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1000816)
;

-- 2020-01-24T13:08:42.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Käufer abw.', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 1000816
;

-- 2020-01-24T13:08:42.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Käufer abw.', Description='', Help='' WHERE AD_Element_ID = 1000816
;

-- 2020-01-24T13:08:42.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Käufer abw.', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1000816
;

-- 2020-01-24T13:08:46.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-24 14:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000816 AND AD_Language='de_DE'
;

-- 2020-01-24T13:08:46.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000816,'de_DE') 
;

-- 2020-01-24T13:08:46.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1000816,'de_DE') 
;

-- 2020-01-24T13:09:00.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Käufer abw.', PrintName='Käufer abw.',Updated=TO_TIMESTAMP('2020-01-24 14:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000816 AND AD_Language='de_CH'
;

-- 2020-01-24T13:09:00.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000816,'de_CH') 
;

-- 2020-01-24T13:12:22.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner.C_BPartner_ID in (
select C_BPartnerRelation_ID from C_BP_Relation r 
where r.C_BPartner_ID=COALESCE(NULLIF(@C_BPartner_Override_ID/0@, 0), @C_BPartner_ID/0@)
and r.IsHandOverLocation =''Y'')',Updated=TO_TIMESTAMP('2020-01-24 14:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540336
;

-- 2020-01-24T13:12:45.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET EntityType='de.metas.ordercandidate',Updated=TO_TIMESTAMP('2020-01-24 14:12:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540336
;

-- 2020-01-24T13:14:08.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2020-01-24 14:14:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=678
;

-- 2020-01-24T13:14:33.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='C_BP_Relation',Updated=TO_TIMESTAMP('2020-01-24 14:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=313
;

-- 2020-01-24T13:17:09.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeord=Ja".',Updated=TO_TIMESTAMP('2020-01-24 14:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='de_CH'
;

-- 2020-01-24T13:17:09.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'de_CH') 
;

-- 2020-01-24T13:17:19.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeord=Ja".',Updated=TO_TIMESTAMP('2020-01-24 14:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='nl_NL'
;

-- 2020-01-24T13:17:19.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'nl_NL') 
;

-- 2020-01-24T13:17:29.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Übergabe-Partner abw.', PrintName='Übergabe-Partner abw.',Updated=TO_TIMESTAMP('2020-01-24 14:17:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='nl_NL'
;

-- 2020-01-24T13:17:29.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'nl_NL') 
;

-- 2020-01-24T13:17:32.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Übergabe-Partner abw.',Updated=TO_TIMESTAMP('2020-01-24 14:17:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='de_DE'
;

-- 2020-01-24T13:17:32.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'de_DE') 
;

-- 2020-01-24T13:17:32.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543089,'de_DE') 
;

-- 2020-01-24T13:17:32.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HandOver_Partner_Override_ID', Name='Übergabe-Partner abw.', Description='Übergabe-Partner abw.', Help=NULL WHERE AD_Element_ID=543089
;

-- 2020-01-24T13:17:32.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_Override_ID', Name='Übergabe-Partner abw.', Description='Übergabe-Partner abw.', Help=NULL, AD_Element_ID=543089 WHERE UPPER(ColumnName)='HANDOVER_PARTNER_OVERRIDE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-24T13:17:32.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_Override_ID', Name='Übergabe-Partner abw.', Description='Übergabe-Partner abw.', Help=NULL WHERE AD_Element_ID=543089 AND IsCentrallyMaintained='Y'
;

-- 2020-01-24T13:17:32.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Übergabe-Partner abw.', Description='Übergabe-Partner abw.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543089) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543089)
;

-- 2020-01-24T13:17:32.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Übergabe-Partner abw.', Description='Übergabe-Partner abw.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543089
;

-- 2020-01-24T13:17:32.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Übergabe-Partner abw.', Description='Übergabe-Partner abw.', Help=NULL WHERE AD_Element_ID = 543089
;

-- 2020-01-24T13:17:32.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Übergabe-Partner abw.', Description = 'Übergabe-Partner abw.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543089
;

-- 2020-01-24T13:17:59.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja"',Updated=TO_TIMESTAMP('2020-01-24 14:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='nl_NL'
;

-- 2020-01-24T13:17:59.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'nl_NL') 
;

-- 2020-01-24T13:18:05.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja".',Updated=TO_TIMESTAMP('2020-01-24 14:18:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='de_DE'
;

-- 2020-01-24T13:18:05.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'de_DE') 
;

-- 2020-01-24T13:18:05.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543089,'de_DE') 
;

-- 2020-01-24T13:18:05.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HandOver_Partner_Override_ID', Name='Übergabe-Partner abw.', Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja".', Help=NULL WHERE AD_Element_ID=543089
;

-- 2020-01-24T13:18:05.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_Override_ID', Name='Übergabe-Partner abw.', Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja".', Help=NULL, AD_Element_ID=543089 WHERE UPPER(ColumnName)='HANDOVER_PARTNER_OVERRIDE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-24T13:18:05.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HandOver_Partner_Override_ID', Name='Übergabe-Partner abw.', Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja".', Help=NULL WHERE AD_Element_ID=543089 AND IsCentrallyMaintained='Y'
;

-- 2020-01-24T13:18:05.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Übergabe-Partner abw.', Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja".', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543089) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543089)
;

-- 2020-01-24T13:18:05.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Übergabe-Partner abw.', Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja".', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543089
;

-- 2020-01-24T13:18:05.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Übergabe-Partner abw.', Description='Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja".', Help=NULL WHERE AD_Element_ID = 543089
;

-- 2020-01-24T13:18:05.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Übergabe-Partner abw.', Description = 'Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja".', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543089
;

-- 2020-01-24T13:18:57.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Selectable partners have a relation with "handover location=Yes" to the buyer',Updated=TO_TIMESTAMP('2020-01-24 14:18:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543089 AND AD_Language='en_US'
;

-- 2020-01-24T13:18:57.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543089,'en_US') 
;

-- 2020-01-24T13:22:16.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=138,Updated=TO_TIMESTAMP('2020-01-24 14:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554411
;

-- 2020-01-24T13:27:56.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Käufer eff.', PrintName='Käufer eff.',Updated=TO_TIMESTAMP('2020-01-24 14:27:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542420 AND AD_Language='de_CH'
;

-- 2020-01-24T13:27:56.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542420,'de_CH') 
;

-- 2020-01-24T13:28:03.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Buyer eff.', PrintName='Buyer eff.',Updated=TO_TIMESTAMP('2020-01-24 14:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542420 AND AD_Language='en_US'
;

-- 2020-01-24T13:28:03.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542420,'en_US') 
;

-- 2020-01-24T13:28:22.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Käufer eff.', PrintName='Käufer eff.',Updated=TO_TIMESTAMP('2020-01-24 14:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542420 AND AD_Language='de_DE'
;

-- 2020-01-24T13:28:22.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542420,'de_DE') 
;

-- 2020-01-24T13:28:22.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542420,'de_DE') 
;

-- 2020-01-24T13:28:22.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_Effective_ID', Name='Käufer eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=542420
;

-- 2020-01-24T13:28:22.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Effective_ID', Name='Käufer eff.', Description=NULL, Help=NULL, AD_Element_ID=542420 WHERE UPPER(ColumnName)='C_BPARTNER_EFFECTIVE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-24T13:28:22.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Effective_ID', Name='Käufer eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=542420 AND IsCentrallyMaintained='Y'
;

-- 2020-01-24T13:28:22.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Käufer eff.', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542420) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542420)
;

-- 2020-01-24T13:28:22.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Käufer eff.', Name='Käufer eff.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542420)
;

-- 2020-01-24T13:28:22.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Käufer eff.', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542420
;

-- 2020-01-24T13:28:22.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Käufer eff.', Description=NULL, Help=NULL WHERE AD_Element_ID = 542420
;

-- 2020-01-24T13:28:22.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Käufer eff.', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542420
;

-- 2020-01-24T13:28:26.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-24 14:28:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542420 AND AD_Language='de_DE'
;

-- 2020-01-24T13:28:26.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542420,'de_DE') 
;

-- 2020-01-24T13:28:26.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542420,'de_DE') 
;

-- 2020-01-24T13:38:26.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2020-01-24 14:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551513
;

-- 2020-01-24T13:40:20.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_BPartner_ID',Updated=TO_TIMESTAMP('2020-01-24 14:40:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547218
;

-- 2020-01-24T14:17:12.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541156, SeqNo=70,Updated=TO_TIMESTAMP('2020-01-24 15:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547225
;

-- 2020-01-24T14:17:32.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2020-01-24 15:17:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547225
;

-- 2020-01-24T14:17:48.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2020-01-24 15:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547225
;

-- 2020-01-24T14:35:26.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='C_BP_Group',Updated=TO_TIMESTAMP('2020-01-24 15:35:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=192
;

-- 2020-01-24T14:37:57.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577487,0,'SO_PricingSystem_ID',TO_TIMESTAMP('2020-01-24 15:37:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Verkaufspreissystem','Verkaufspreissystem',TO_TIMESTAMP('2020-01-24 15:37:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-24T14:37:57.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577487 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-01-24T14:38:01.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-24 15:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577487 AND AD_Language='de_CH'
;

-- 2020-01-24T14:38:01.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577487,'de_CH') 
;

-- 2020-01-24T14:38:03.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-24 15:38:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577487 AND AD_Language='de_DE'
;

-- 2020-01-24T14:38:03.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577487,'de_DE') 
;

-- 2020-01-24T14:38:03.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577487,'de_DE') 
;

-- 2020-01-24T14:38:23.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sales pricing system', PrintName='Sales pricing system',Updated=TO_TIMESTAMP('2020-01-24 15:38:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577487 AND AD_Language='en_US'
;

-- 2020-01-24T14:38:23.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577487,'en_US') 
;

-- 2020-01-24T14:40:15.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577487, Description=NULL, Help=NULL, Name='Verkaufspreissystem',Updated=TO_TIMESTAMP('2020-01-24 15:40:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=505274
;

-- 2020-01-24T14:40:15.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577487) 
;

-- 2020-01-24T14:40:15.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=505274
;

-- 2020-01-24T14:40:15.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(505274)
;

-- 2020-01-24T14:41:31.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540055,Updated=TO_TIMESTAMP('2020-01-24 15:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=505274
;

-- 2020-01-24T14:42:09.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577487, Description=NULL, Help=NULL, Name='Verkaufspreissystem',Updated=TO_TIMESTAMP('2020-01-24 15:42:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545322
;

-- 2020-01-24T14:42:09.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577487) 
;

-- 2020-01-24T14:42:09.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=545322
;

-- 2020-01-24T14:42:09.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(545322)
;

