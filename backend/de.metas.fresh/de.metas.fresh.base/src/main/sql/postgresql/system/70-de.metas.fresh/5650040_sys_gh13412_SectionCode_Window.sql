-- 2022-08-08T13:40:02.993Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581238,0,541589,TO_TIMESTAMP('2022-08-08 14:40:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Section Code','N',TO_TIMESTAMP('2022-08-08 14:40:00','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-08-08T13:40:03.106Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541589 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-08-08T13:40:03.324Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(581238) 
;

-- 2022-08-08T13:40:03.429Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541589
;

-- 2022-08-08T13:40:03.526Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541589)
;

-- 2022-08-08T13:45:02.452Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581238,0,546546,542200,541589,'Y',TO_TIMESTAMP('2022-08-08 14:45:01','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_SectionCode','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Section Code','N',10,0,TO_TIMESTAMP('2022-08-08 14:45:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:45:02.555Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546546 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-08-08T13:45:02.657Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(581238) 
;

-- 2022-08-08T13:45:02.752Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546546)
;

-- 2022-08-08T13:45:32.020Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583966,703916,0,546546,TO_TIMESTAMP('2022-08-08 14:45:30','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2022-08-08 14:45:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:45:32.153Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T13:45:32.252Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-08-08T13:45:32.525Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703916
;

-- 2022-08-08T13:45:32.638Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703916)
;

-- 2022-08-08T13:45:34.088Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583967,703917,0,546546,TO_TIMESTAMP('2022-08-08 14:45:32','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-08-08 14:45:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:45:34.206Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T13:45:34.338Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-08-08T13:45:34.545Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703917
;

-- 2022-08-08T13:45:34.676Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703917)
;

-- 2022-08-08T13:45:36.389Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583970,703918,0,546546,TO_TIMESTAMP('2022-08-08 14:45:34','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2022-08-08 14:45:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:45:36.494Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703918 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T13:45:36.592Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-08-08T13:45:36.833Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703918
;

-- 2022-08-08T13:45:36.933Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703918)
;

-- 2022-08-08T13:45:38.251Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583973,703919,0,546546,TO_TIMESTAMP('2022-08-08 14:45:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 14:45:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:45:38.351Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T13:45:38.449Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-08T13:45:38.544Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703919
;

-- 2022-08-08T13:45:38.639Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703919)
;

-- 2022-08-08T13:45:40.314Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583974,703920,0,546546,TO_TIMESTAMP('2022-08-08 14:45:38','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique',40,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Search Key',TO_TIMESTAMP('2022-08-08 14:45:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:45:40.459Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T13:45:40.561Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2022-08-08T13:45:40.677Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703920
;

-- 2022-08-08T13:45:40.775Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703920)
;

-- 2022-08-08T13:45:42.157Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583975,703921,0,546546,TO_TIMESTAMP('2022-08-08 14:45:41','YYYY-MM-DD HH24:MI:SS'),100,'',40,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-08-08 14:45:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:45:42.256Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703921 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T13:45:42.356Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-08-08T13:45:42.500Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703921
;

-- 2022-08-08T13:45:42.623Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703921)
;

-- 2022-08-08T13:49:04.387Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546546,545166,TO_TIMESTAMP('2022-08-08 14:49:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-08 14:49:03','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-08-08T13:49:04.484Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545166 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-08-08T13:49:27.374Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546277,545166,TO_TIMESTAMP('2022-08-08 14:49:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-08 14:49:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:49:38.064Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546278,545166,TO_TIMESTAMP('2022-08-08 14:49:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-08-08 14:49:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:50:17.695Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546277,549704,TO_TIMESTAMP('2022-08-08 14:50:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','Section',10,'primary',TO_TIMESTAMP('2022-08-08 14:50:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:51:08.650Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703920,0,546546,549704,611330,'F',TO_TIMESTAMP('2022-08-08 14:51:07','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Search Key',10,0,0,TO_TIMESTAMP('2022-08-08 14:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:51:32.857Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703921,0,546546,549704,611331,'F',TO_TIMESTAMP('2022-08-08 14:51:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2022-08-08 14:51:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:52:15.615Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546278,549705,TO_TIMESTAMP('2022-08-08 14:52:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-08-08 14:52:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:53:01.053Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703918,0,546546,549705,611332,'F',TO_TIMESTAMP('2022-08-08 14:52:59','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2022-08-08 14:52:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:53:32.391Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546278,549706,TO_TIMESTAMP('2022-08-08 14:53:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-08-08 14:53:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:54:08.210Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703917,0,546546,549706,611333,'F',TO_TIMESTAMP('2022-08-08 14:54:07','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2022-08-08 14:54:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:54:40.052Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703916,0,546546,549706,611334,'F',TO_TIMESTAMP('2022-08-08 14:54:39','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N',0,'Client',20,0,0,TO_TIMESTAMP('2022-08-08 14:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:59:33.735Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581238,541989,0,541589,TO_TIMESTAMP('2022-08-08 14:59:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Section Code','Y','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 14:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:59:33.834Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541989 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-08-08T13:59:33.933Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541989, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541989)
;

-- 2022-08-08T13:59:34.036Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(581238) 
;

-- 2022-08-08T13:59:40.557Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53133, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=431 AND AD_Tree_ID=10
;

-- 2022-08-08T13:59:40.653Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53133, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=430 AND AD_Tree_ID=10
;

-- 2022-08-08T13:59:40.748Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53133, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=474 AND AD_Tree_ID=10
;

-- 2022-08-08T13:59:40.841Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53133, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=400 AND AD_Tree_ID=10
;

-- 2022-08-08T13:59:40.938Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53133, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=399 AND AD_Tree_ID=10
;

-- 2022-08-08T13:59:41.032Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53133, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=348 AND AD_Tree_ID=10
;

-- 2022-08-08T13:59:41.127Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53133, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541989 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:00.968Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541989 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.066Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541000 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.163Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541001 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.257Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541008 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.352Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541002 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.447Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541003 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.542Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540956 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540881 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.732Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540882 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.827Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540842 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:01.923Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541098 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:02.018Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540843 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:02.112Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541097 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:02.208Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540810 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:02.304Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540812 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:02.399Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541107 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:02.493Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540813 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:02.589Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540780 AND AD_Tree_ID=10
;

-- 2022-08-08T14:00:02.683Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541103 AND AD_Tree_ID=10
;

