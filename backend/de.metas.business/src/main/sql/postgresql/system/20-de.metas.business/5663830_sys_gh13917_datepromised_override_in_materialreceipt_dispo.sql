-- 2022-11-09T22:58:32.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584884,579242,0,16,540524,'MovementDate_Override',TO_TIMESTAMP('2022-11-09 23:58:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.','de.metas.inoutcandidate',7,'B','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Abw. Bewegungsdatum','@ExportStatus/''X''@ = ''EXPORTED'' | @ExportStatus/''X''@ = ''EXPORTED_AND_FORWARDED'' | @ExportStatus/''X''@ = ''EXPORTED_FORWARD_ERROR''',0,0,TO_TIMESTAMP('2022-11-09 23:58:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-09T22:58:32.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584884 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-09T22:58:32.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579242) 
;

-- 2022-11-09T22:58:36.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN MovementDate_Override TIMESTAMP WITH TIME ZONE')
;

-- 2022-11-09T23:02:50.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581656,0,'MovementDate_Effective',TO_TIMESTAMP('2022-11-10 00:02:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','MovementDate Effektiv','MovementDate Effektiv',TO_TIMESTAMP('2022-11-10 00:02:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-09T23:02:50.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581656 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-09T23:03:22.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Movement Date Effective', PrintName='Movement Date Effective',Updated=TO_TIMESTAMP('2022-11-10 00:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581656 AND AD_Language='en_US'
;

-- 2022-11-09T23:03:22.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581656,'en_US') 
;

-- 2022-11-09T23:03:48.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584885,581656,0,16,540524,'MovementDate_Effective',TO_TIMESTAMP('2022-11-10 00:03:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',7,'B','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'MovementDate Effektiv','@ExportStatus/''X''@ = ''EXPORTED'' | @ExportStatus/''X''@ = ''EXPORTED_AND_FORWARDED'' | @ExportStatus/''X''@ = ''EXPORTED_FORWARD_ERROR''',0,0,TO_TIMESTAMP('2022-11-10 00:03:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-09T23:03:48.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584885 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-09T23:03:48.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581656) 
;

-- 2022-11-09T23:04:32.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='COALESCE(MovementDate_Override_ID, MovementDate)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-10 00:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584884
;

-- 2022-11-09T23:16:12.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='', IsLazyLoading='N',Updated=TO_TIMESTAMP('2022-11-10 00:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584884
;

-- 2022-11-09T23:16:15.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='COALESCE(MovementDate_Override_ID, MovementDate)', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-10 00:16:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584885
;

-- 2022-11-09T23:16:21.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_receiptschedule','MovementDate_Override','TIMESTAMP WITH TIME ZONE',null,null)
;


-- 2022-11-15T10:11:07.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAlwaysUpdateable='Y', IsUpdateable='Y',Updated=TO_TIMESTAMP('2022-11-15 11:11:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584884
;

-- 2022-11-15T10:11:44.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAlwaysUpdateable='N',Updated=TO_TIMESTAMP('2022-11-15 11:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584884
;

-- 2022-11-15T10:13:56.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584884,708058,0,540526,0,TO_TIMESTAMP('2022-11-15 11:13:56','YYYY-MM-DD HH24:MI:SS'),100,'Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.',0,'D',0,'Y','N','N','N','N','N','N','N','Abw. Bewegungsdatum',0,210,0,1,1,TO_TIMESTAMP('2022-11-15 11:13:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T10:13:56.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-15T10:13:56.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579242)
;

-- 2022-11-15T10:13:56.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708058
;

-- 2022-11-15T10:13:56.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(708058)
;

-- 2022-11-15T10:14:27.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584885,708059,0,540526,0,TO_TIMESTAMP('2022-11-15 11:14:27','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','MovementDate Effektiv',0,220,0,1,1,TO_TIMESTAMP('2022-11-15 11:14:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T10:14:27.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-15T10:14:27.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581656)
;

-- 2022-11-15T10:14:27.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708059
;

-- 2022-11-15T10:14:27.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(708059)
;

-- 2022-11-15T10:15:23.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,708058,0,540526,540133,613467,'F',TO_TIMESTAMP('2022-11-15 11:15:23','YYYY-MM-DD HH24:MI:SS'),100,'Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.','Y','N','N','Y','N','N','N',0,'Abw. Bewegungsdatum',50,0,0,TO_TIMESTAMP('2022-11-15 11:15:23','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-11-15T10:15:42.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,708059,0,540526,540133,613468,'F',TO_TIMESTAMP('2022-11-15 11:15:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'MovementDate Effektiv',60,0,0,TO_TIMESTAMP('2022-11-15 11:15:42','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-11-15T10:16:14.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2022-11-15 11:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613468
;

-- 2022-11-15T10:16:24.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2022-11-15 11:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613467
;

-- 2022-11-15T10:27:43.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='COALESCE(MovementDate_Override, MovementDate)',Updated=TO_TIMESTAMP('2022-11-15 11:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584885
;

-- 2022-11-11T11:14:04.351Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3405,0,293,540927,613441,'F',TO_TIMESTAMP('2022-11-11 12:14:04','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','Y','N','N','N',0,'Zugesagter Termin',250,0,0,TO_TIMESTAMP('2022-11-11 12:14:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-11T11:14:11.797Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2022-11-11 12:14:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613441
;

-- 2022-11-11T11:15:18.172Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2022-11-11 12:15:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613441
;


-- setup translations
-- 2022-11-15T19:31:42.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581684,0,TO_TIMESTAMP('2022-11-15 20:31:41','YYYY-MM-DD HH24:MI:SS'),100,'','D','','Y','Abw. Zugesagter Termin','Abw. Zugesagter Termin',TO_TIMESTAMP('2022-11-15 20:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T19:31:42.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581684 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-15T19:32:02.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-15 20:32:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581684 AND AD_Language='de_DE'
;

-- 2022-11-15T19:32:02.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581684,'de_DE')
;

-- 2022-11-15T19:32:02.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581684,'de_DE')
;

-- 2022-11-15T19:32:49.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Alt. Date Promised', PrintName='Alt. Date Promised',Updated=TO_TIMESTAMP('2022-11-15 20:32:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581684 AND AD_Language='en_US'
;

-- 2022-11-15T19:32:49.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581684,'en_US')
;

-- 2022-11-15T19:33:11.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=581684, Description='', Help='', Name='Abw. Zugesagter Termin',Updated=TO_TIMESTAMP('2022-11-15 20:33:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708058
;

-- 2022-11-15T19:33:11.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581684)
;

-- 2022-11-15T19:33:11.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708058
;

-- 2022-11-15T19:33:11.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(708058)
;

-- 2022-11-15T19:35:16.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581685,0,TO_TIMESTAMP('2022-11-15 20:35:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Effektiv','Effektiv',TO_TIMESTAMP('2022-11-15 20:35:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T19:35:16.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581685 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-15T19:36:21.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Effektiv Zugesagter Termin', PrintName='Effektiv Zugesagter Termin',Updated=TO_TIMESTAMP('2022-11-15 20:36:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581685 AND AD_Language='de_DE'
;

-- 2022-11-15T19:36:21.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581685,'de_DE')
;

-- 2022-11-15T19:36:21.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581685,'de_DE')
;

-- 2022-11-15T19:36:21.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Effektiv Zugesagter Termin', Description=NULL, Help=NULL WHERE AD_Element_ID=581685
;

-- 2022-11-15T19:36:21.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Effektiv Zugesagter Termin', Description=NULL, Help=NULL WHERE AD_Element_ID=581685 AND IsCentrallyMaintained='Y'
;

-- 2022-11-15T19:36:21.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Effektiv Zugesagter Termin', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581685) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581685)
;

-- 2022-11-15T19:36:21.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Effektiv Zugesagter Termin', Name='Effektiv Zugesagter Termin' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581685)
;

-- 2022-11-15T19:36:21.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Effektiv Zugesagter Termin', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581685
;

-- 2022-11-15T19:36:21.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Effektiv Zugesagter Termin', Description=NULL, Help=NULL WHERE AD_Element_ID = 581685
;

-- 2022-11-15T19:36:21.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Effektiv Zugesagter Termin', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581685
;

-- 2022-11-15T19:36:49.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Effective Date Promised', PrintName='Effective Date Promised',Updated=TO_TIMESTAMP('2022-11-15 20:36:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581685 AND AD_Language='en_US'
;

-- 2022-11-15T19:36:49.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581685,'en_US')
;


-- 2022-11-15T19:40:05.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=581685, Description=NULL, Help=NULL, Name='Effektiv Zugesagter Termin',Updated=TO_TIMESTAMP('2022-11-15 20:40:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708059
;

-- 2022-11-15T19:40:05.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581685)
;

-- 2022-11-15T19:40:05.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708059
;

-- 2022-11-15T19:40:05.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(708059)
;

-- 2022-11-15T19:40:21.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Effektiv Zugesagter Termin',Updated=TO_TIMESTAMP('2022-11-15 20:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581685 AND AD_Language='de_CH'
;

-- 2022-11-15T19:40:21.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581685,'de_CH')
;

-- 2022-11-15T19:40:26.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Effektiv Zugesagter Termin',Updated=TO_TIMESTAMP('2022-11-15 20:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581685 AND AD_Language='nl_NL'
;

-- 2022-11-15T19:40:26.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581685,'nl_NL')
;

