-- 2022-01-19T13:37:21.851Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580503,0,TO_TIMESTAMP('2022-01-19 14:37:16','YYYY-MM-DD HH24:MI:SS'),100,'Define Product','U','The Product Tab defines each product and identifies it for use in price lists and orders. The Location is the default location when receiving the stored product.','Y','Product_ALT','Product_ALT',TO_TIMESTAMP('2022-01-19 14:37:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T13:37:21.855Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580503 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-19T13:40:28.654Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=580503, Description='Define Product', Help='The Product Tab defines each product and identifies it for use in price lists and orders. The Location is the default location when receiving the stored product.', Name='Product_ALT',Updated=TO_TIMESTAMP('2022-01-19 14:40:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=140
;

-- 2022-01-19T13:40:28.658Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Define Product', IsActive='Y', Name='Product_ALT',Updated=TO_TIMESTAMP('2022-01-19 14:40:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=126
;

-- 2022-01-19T13:40:28.661Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Define Product', IsActive='Y', Name='Product_ALT',Updated=TO_TIMESTAMP('2022-01-19 14:40:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000034
;

-- 2022-01-19T13:40:28.667Z
-- URL zum Konzept
UPDATE AD_WF_Node SET Description='Define Product', Help='The Product Tab defines each product and identifies it for use in price lists and orders. The Location is the default location when receiving the stored product.', Name='Product_ALT',Updated=TO_TIMESTAMP('2022-01-19 14:40:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=161
;

-- 2022-01-19T13:40:28.694Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(580503) 
;

-- 2022-01-19T13:40:28.700Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=140
;

-- 2022-01-19T13:40:28.713Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(140)
;

-- 2022-01-19T13:44:09.853Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=580498, Description='Verwalten von Produkten', Help='Das Fenster "Produkt" definiert alle Produkte, die von einer Organisation verwendet werden. Dies schliesst die ein, die an Kunden verkauft werden und solche, die von Lieferanten eingekauft werden.', Name='Produkt_OLD',Updated=TO_TIMESTAMP('2022-01-19 14:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=140
;

-- 2022-01-19T13:44:09.859Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Verwalten von Produkten', IsActive='Y', Name='Produkt_OLD',Updated=TO_TIMESTAMP('2022-01-19 14:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=126
;

-- 2022-01-19T13:44:09.862Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Verwalten von Produkten', IsActive='Y', Name='Produkt_OLD',Updated=TO_TIMESTAMP('2022-01-19 14:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000034
;

-- 2022-01-19T13:44:09.867Z
-- URL zum Konzept
UPDATE AD_WF_Node SET Description='Verwalten von Produkten', Help='Das Fenster "Produkt" definiert alle Produkte, die von einer Organisation verwendet werden. Dies schliesst die ein, die an Kunden verkauft werden und solche, die von Lieferanten eingekauft werden.', Name='Produkt_OLD',Updated=TO_TIMESTAMP('2022-01-19 14:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=161
;

-- 2022-01-19T13:44:09.870Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(580498) 
;

-- 2022-01-19T13:44:09.874Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=140
;

-- 2022-01-19T13:44:09.875Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(140)
;

-- 2022-01-19T13:46:48.897Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,580503,0,541398,TO_TIMESTAMP('2022-01-19 14:46:43','YYYY-MM-DD HH24:MI:SS'),100,'Define Product','U','The Product Tab defines each product and identifies it for use in price lists and orders. The Location is the default location when receiving the stored product.','Y','N','N','N','N','N','N','Y','Product_ALT','N',TO_TIMESTAMP('2022-01-19 14:46:43','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-01-19T13:46:48.899Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541398 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-01-19T13:46:48.901Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(580503) 
;

-- 2022-01-19T13:46:48.903Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541398
;

-- 2022-01-19T13:46:48.904Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541398)
;

-- 2022-01-19T14:03:24.048Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541398
;

-- 2022-01-19T14:03:24.050Z
-- URL zum Konzept
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=541398
;

-- 2022-01-19T14:03:24.054Z
-- URL zum Konzept
DELETE FROM AD_Window WHERE AD_Window_ID=541398
;

-- 2022-01-19T14:03:41.319Z
-- URL zum Konzept
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=580503
;

-- 2022-01-19T14:03:41.321Z
-- URL zum Konzept
DELETE FROM AD_Element WHERE AD_Element_ID=580503
;

-- 2022-01-19T14:11:36.107Z
-- URL zum Konzept
UPDATE AD_Window SET Overrides_Window_ID=NULL,Updated=TO_TIMESTAMP('2022-01-19 15:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541397
;

-- 2022-01-19T14:11:40.820Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- 2022-01-19T14:13:04.941Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='de.metas.handlingunits', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=540192, WindowType='M', WinHeight=0, WinWidth=0, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2022-01-19 15:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541397
;

-- 2022-01-19T14:13:04.945Z
-- URL zum Konzept
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 541397
;

-- 2022-01-19T14:13:04.946Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541397, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 540192
;

-- 2022-01-19T14:13:10.037Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572772,0,545339,540519,541397,'Y',TO_TIMESTAMP('2022-01-19 15:13:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','N','M_HU_PackingMaterial','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N','Packmittel','N',10,0,TO_TIMESTAMP('2022-01-19 15:13:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:10.040Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=545339 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-01-19T14:13:10.043Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572772) 
;

-- 2022-01-19T14:13:10.050Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(545339)
;

-- 2022-01-19T14:13:10.054Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545339
;

-- 2022-01-19T14:13:10.055Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545339, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540521
;

-- 2022-01-19T14:13:10.118Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549414,677904,0,545339,73,TO_TIMESTAMP('2022-01-19 15:13:10','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits',0,'Y','Y','Y','N','N','N','N','N','Packmittel',30,30,1,1,TO_TIMESTAMP('2022-01-19 15:13:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:10.121Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677904 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:10.124Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542185) 
;

-- 2022-01-19T14:13:10.134Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677904
;

-- 2022-01-19T14:13:10.135Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677904)
;

-- 2022-01-19T14:13:10.138Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677904
;

-- 2022-01-19T14:13:10.139Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677904, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552420
;

-- 2022-01-19T14:13:10.196Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549411,677905,0,545339,44,TO_TIMESTAMP('2022-01-19 15:13:10','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.handlingunits','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',60,60,1,1,TO_TIMESTAMP('2022-01-19 15:13:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:10.197Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677905 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:10.199Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-01-19T14:13:11.184Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677905
;

-- 2022-01-19T14:13:11.185Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677905)
;

-- 2022-01-19T14:13:11.187Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677905
;

-- 2022-01-19T14:13:11.187Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677905, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552421
;

-- 2022-01-19T14:13:11.281Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549412,677906,0,545339,0,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.handlingunits','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','Aktualisiert',70,0,1,1,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:11.282Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677906 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:11.284Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2022-01-19T14:13:11.381Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677906
;

-- 2022-01-19T14:13:11.381Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677906)
;

-- 2022-01-19T14:13:11.383Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677906
;

-- 2022-01-19T14:13:11.384Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677906, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552422
;

-- 2022-01-19T14:13:11.438Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549413,677907,0,545339,0,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.handlingunits','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','Aktualisiert durch',80,0,1,1,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:11.440Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677907 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:11.442Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2022-01-19T14:13:11.514Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677907
;

-- 2022-01-19T14:13:11.514Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677907)
;

-- 2022-01-19T14:13:11.516Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677907
;

-- 2022-01-19T14:13:11.517Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677907, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552423
;

-- 2022-01-19T14:13:11.583Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549416,677908,0,545339,105,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100,200,'de.metas.handlingunits',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',50,50,999,1,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:11.585Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677908 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:11.586Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-01-19T14:13:11.755Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677908
;

-- 2022-01-19T14:13:11.756Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677908)
;

-- 2022-01-19T14:13:11.757Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677908
;

-- 2022-01-19T14:13:11.757Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677908, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552424
;

-- 2022-01-19T14:13:11.816Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549420,677909,0,545339,47,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits',0,'Y','Y','Y','N','N','N','N','N','Breite',80,80,1,1,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:11.817Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677909 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:11.818Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540037) 
;

-- 2022-01-19T14:13:11.822Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677909
;

-- 2022-01-19T14:13:11.822Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677909)
;

-- 2022-01-19T14:13:11.832Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677909
;

-- 2022-01-19T14:13:11.832Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677909, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552425
;

-- 2022-01-19T14:13:11.884Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549421,677910,0,545339,121,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.',10,'de.metas.handlingunits','Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.',0,'Y','Y','Y','N','N','N','N','N','Einheit Abessungen',110,110,1,1,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:11.886Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:11.887Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542186) 
;

-- 2022-01-19T14:13:11.888Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677910
;

-- 2022-01-19T14:13:11.888Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677910)
;

-- 2022-01-19T14:13:11.890Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677910
;

-- 2022-01-19T14:13:11.890Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677910, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552426
;

-- 2022-01-19T14:13:11.937Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549422,677911,0,545339,128,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100,'Standardmaßeinheit für Gewicht',10,'de.metas.handlingunits','"Maßeinheit für Gewicht" bezeichnet die Standardmaßeinheit, die bei Produkten mit Gewichtsangabe auf Belegen verwendet wird.',0,'Y','Y','Y','N','N','N','N','Y','Maßeinheit für Gewicht',120,120,1,1,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:11.938Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677911 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:11.940Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(221) 
;

-- 2022-01-19T14:13:11.948Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677911
;

-- 2022-01-19T14:13:11.948Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677911)
;

-- 2022-01-19T14:13:11.951Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677911
;

-- 2022-01-19T14:13:11.951Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677911, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552427
;

-- 2022-01-19T14:13:11.999Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549409,677912,0,545339,0,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.handlingunits','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','Erstellt',130,0,1,1,TO_TIMESTAMP('2022-01-19 15:13:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:12.001Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677912 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:12.002Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2022-01-19T14:13:12.111Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677912
;

-- 2022-01-19T14:13:12.111Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677912)
;

-- 2022-01-19T14:13:12.113Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677912
;

-- 2022-01-19T14:13:12.114Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677912, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552428
;

-- 2022-01-19T14:13:12.184Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549410,677913,0,545339,0,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.handlingunits','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','Erstellt durch',140,0,1,1,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:12.186Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677913 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:12.188Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2022-01-19T14:13:12.246Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677913
;

-- 2022-01-19T14:13:12.247Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677913)
;

-- 2022-01-19T14:13:12.249Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677913
;

-- 2022-01-19T14:13:12.250Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677913, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552429
;

-- 2022-01-19T14:13:12.320Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549426,677914,0,545339,45,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.handlingunits','Der Füllgrad gibt prozentual an, bis zu welchem Grad eine Handling Unit gefüllt werden soll. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.',0,'Y','Y','Y','N','N','N','N','N','Füllgrad',130,130,1,1,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:12.322Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:12.323Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542190) 
;

-- 2022-01-19T14:13:12.325Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677914
;

-- 2022-01-19T14:13:12.325Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677914)
;

-- 2022-01-19T14:13:12.328Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677914
;

-- 2022-01-19T14:13:12.328Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677914, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552430
;

-- 2022-01-19T14:13:12.378Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549429,677915,1001611,0,545339,82,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100,'In diesem Feld kann gesteuert werden, ob beim Verpacken das Gesamtvolumen verändert wird oder gleich bleibt.
Beispiel
Beim Verpacken einer offenen Palette ändert sich das Gesamtvolumen.
Beim Verpacken einer geschlossenen Kiste ändert sich das Gesamtvolumen nicht.',1,'de.metas.handlingunits',0,'Y','Y','Y','N','N','N','N','N','Geschlossen',190,190,1,1,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:12.380Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677915 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:12.382Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001611) 
;

-- 2022-01-19T14:13:12.384Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677915
;

-- 2022-01-19T14:13:12.385Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677915)
;

-- 2022-01-19T14:13:12.387Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677915
;

-- 2022-01-19T14:13:12.388Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677915, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552431
;

-- 2022-01-19T14:13:12.436Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549418,677916,0,545339,46,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits',0,'Y','Y','Y','N','N','N','N','Y','Höhe',90,90,1,1,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:12.438Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:12.440Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540036) 
;

-- 2022-01-19T14:13:12.442Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677916
;

-- 2022-01-19T14:13:12.442Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677916)
;

-- 2022-01-19T14:13:12.445Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677916
;

-- 2022-01-19T14:13:12.446Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677916, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552432
;

-- 2022-01-19T14:13:12.496Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549419,677917,0,545339,49,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits',0,'Y','Y','Y','N','N','N','N','Y','Länge',100,100,1,1,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:12.498Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:12.500Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540038) 
;

-- 2022-01-19T14:13:12.502Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677917
;

-- 2022-01-19T14:13:12.502Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677917)
;

-- 2022-01-19T14:13:12.505Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677917
;

-- 2022-01-19T14:13:12.506Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677917, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552433
;

-- 2022-01-19T14:13:12.554Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549407,677918,0,545339,65,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.handlingunits','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:12.555Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677918 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:12.557Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-01-19T14:13:12.929Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677918
;

-- 2022-01-19T14:13:12.929Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677918)
;

-- 2022-01-19T14:13:12.930Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677918
;

-- 2022-01-19T14:13:12.931Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677918, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552434
;

-- 2022-01-19T14:13:13.009Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549415,677919,0,545339,105,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100,'',60,'de.metas.handlingunits','',0,'Y','Y','Y','N','N','N','N','N','Name',40,40,999,1,TO_TIMESTAMP('2022-01-19 15:13:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:13.010Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:13.012Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-01-19T14:13:13.153Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677919
;

-- 2022-01-19T14:13:13.153Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677919)
;

-- 2022-01-19T14:13:13.154Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677919
;

-- 2022-01-19T14:13:13.155Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677919, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552435
;

-- 2022-01-19T14:13:13.215Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549417,677920,0,545339,250,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'de.metas.handlingunits','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',70,70,1,1,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:13.217Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:13.218Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-01-19T14:13:13.434Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677920
;

-- 2022-01-19T14:13:13.434Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677920)
;

-- 2022-01-19T14:13:13.435Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677920
;

-- 2022-01-19T14:13:13.435Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677920, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552436
;

-- 2022-01-19T14:13:13.536Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549408,677921,0,545339,78,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.handlingunits','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:13.537Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677921 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:13.538Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-01-19T14:13:13.872Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677921
;

-- 2022-01-19T14:13:13.872Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677921)
;

-- 2022-01-19T14:13:13.874Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677921
;

-- 2022-01-19T14:13:13.874Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677921, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552437
;

-- 2022-01-19T14:13:13.927Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549425,677922,0,545339,107,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.handlingunits','Der Stapelbarkeitsfaktor gibt an, wie viele Packmittel (z.B. Paletten) aufeinander gestapelt werden können. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.',0,'Y','Y','Y','N','N','N','N','N','Stapelbarkeitsfaktor',140,140,1,1,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:13.928Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:13.928Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542189) 
;

-- 2022-01-19T14:13:13.929Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677922
;

-- 2022-01-19T14:13:13.929Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677922)
;

-- 2022-01-19T14:13:13.930Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677922
;

-- 2022-01-19T14:13:13.931Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677922, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552438
;

-- 2022-01-19T14:13:13.970Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549427,677923,0,545339,116,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.handlingunits','In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsgewicht einer Handling Unit überschritten werden kann.',0,'Y','Y','Y','N','N','N','N','N','Übergewichtstoleranz',150,150,1,1,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:13.971Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:13.972Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542191) 
;

-- 2022-01-19T14:13:13.973Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677923
;

-- 2022-01-19T14:13:13.973Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677923)
;

-- 2022-01-19T14:13:13.974Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677923
;

-- 2022-01-19T14:13:13.975Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677923, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552439
;

-- 2022-01-19T14:13:14.014Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549428,677924,0,545339,119,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.handlingunits','In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsvolumen einer Handling Unit überschritten werden kann.',0,'Y','Y','Y','N','N','N','N','Y','Übervolumentoleranz',160,160,1,1,TO_TIMESTAMP('2022-01-19 15:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.014Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:14.015Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542192) 
;

-- 2022-01-19T14:13:14.016Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677924
;

-- 2022-01-19T14:13:14.017Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677924)
;

-- 2022-01-19T14:13:14.018Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677924
;

-- 2022-01-19T14:13:14.018Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677924, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552440
;

-- 2022-01-19T14:13:14.064Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549423,677925,0,545339,184,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.handlingunits','In diesem Feld kann ein maximales Ladungsgewicht für eine Handling Unit eingegeben werden. Wird das zulässige Gewicht beim Verpacken erreicht wird der weitere Verpackungsvorgang ggf. mit einer neuen Handling Unit durchgeführt.',0,'Y','Y','Y','N','N','N','N','N','Zulässiges Verpackungsgewicht',170,170,1,1,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.065Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677925 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:14.066Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542187) 
;

-- 2022-01-19T14:13:14.067Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677925
;

-- 2022-01-19T14:13:14.067Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677925)
;

-- 2022-01-19T14:13:14.069Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677925
;

-- 2022-01-19T14:13:14.069Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677925, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552441
;

-- 2022-01-19T14:13:14.110Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549424,677926,0,545339,189,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.handlingunits','In diesem Feld kann ein maximales Ladungsvolumen für eine Handling Unit eingegeben werden. Wird das zulässige Volumen beim Verpacken erreicht, wird der weitere Verpackungsvorgang ggf. in einer neuen Handling Unit durchgeführt.',0,'Y','Y','Y','N','N','N','N','Y','Zulässiges Verpackungsvolumen',180,180,1,1,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.111Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677926 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T14:13:14.113Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542188) 
;

-- 2022-01-19T14:13:14.114Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677926
;

-- 2022-01-19T14:13:14.114Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677926)
;

-- 2022-01-19T14:13:14.116Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677926
;

-- 2022-01-19T14:13:14.117Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677926, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552442
;

-- 2022-01-19T14:13:14.224Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,545339,544293,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-01-19T14:13:14.226Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544293 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-01-19T14:13:14.231Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544293
;

-- 2022-01-19T14:13:14.234Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544293, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540228
;

-- 2022-01-19T14:13:14.352Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545242,544293,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.430Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545242,547959,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.557Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,677919,0,545339,547959,600046,'F',TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Name',10,10,10,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-01-19T14:13:14.617Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,677920,0,545339,547959,600047,'F',TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Produkt',20,20,20,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-01-19T14:13:14.657Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545242,547960,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','dimensions',20,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.726Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677908,0,545339,547960,600048,'F',TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beschreibung',5,30,0,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.782Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677917,0,545339,547960,600049,'F',TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Länge',10,80,0,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.851Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677909,0,545339,547960,600050,'F',TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Breite',20,90,0,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.901Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677916,0,545339,547960,600051,'F',TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Höhe',30,100,0,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.940Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545243,544293,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:14.980Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545243,547961,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.046Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677905,0,545339,547961,600052,'F',TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Aktiv',10,40,30,TO_TIMESTAMP('2022-01-19 15:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.118Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677915,0,545339,547961,600053,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Geschlossen',20,50,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.166Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545243,547962,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','units',20,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.231Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677910,0,545339,547962,600054,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Maßeinheit Abmessungen',10,0,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.286Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677911,0,545339,547962,600055,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Maßeinhait für Gewicht',20,0,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.325Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545243,547963,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.394Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,677921,0,545339,547963,600056,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Sektion',10,60,40,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2022-01-19T14:13:15.452Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,677918,0,545339,547963,600057,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2022-01-19T14:13:15.494Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,545339,544294,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2022-01-19T14:13:15.494Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544294 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-01-19T14:13:15.496Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544294
;

-- 2022-01-19T14:13:15.497Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544294, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540229
;

-- 2022-01-19T14:13:15.536Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545244,544294,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.574Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545244,547964,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.635Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677914,0,545339,547964,600058,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Füllgrad',10,0,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.677Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,677922,0,545339,547964,600059,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Stapelbarkeitsfaktor',20,0,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-01-19T14:13:15.718Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677923,0,545339,547964,600060,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Übergewichtstoleranz',30,0,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.758Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677925,0,545339,547964,600061,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Zulässiges Verpackungsgewicht',35,0,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.799Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677924,0,545339,547964,600062,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Übervolumentoleranz',40,0,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:13:15.845Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677926,0,545339,547964,600063,'F',TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Zulässiges Verpackungsvolumen',50,0,0,TO_TIMESTAMP('2022-01-19 15:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:14:00.368Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=103, AD_Org_ID=0, EntityType='D', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='N', Overrides_Window_ID=541389, WindowType='M', WinHeight=NULL, WinWidth=NULL, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2022-01-19 15:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541397
;

-- 2022-01-19T14:14:00.371Z
-- URL zum Konzept
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 541397
;

-- 2022-01-19T14:14:00.371Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541397, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 541389
;

-- 2022-01-19T14:14:00.382Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545316
;

-- 2022-01-19T14:14:00.382Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545316, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545270
;

-- 2022-01-19T14:14:00.411Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677525
;

-- 2022-01-19T14:14:00.411Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677525, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676766
;

-- 2022-01-19T14:14:00.412Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677526
;

-- 2022-01-19T14:14:00.412Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677526, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676767
;

-- 2022-01-19T14:14:00.413Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677527
;

-- 2022-01-19T14:14:00.413Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677527, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676768
;

-- 2022-01-19T14:14:00.413Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677528
;

-- 2022-01-19T14:14:00.413Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677528, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676769
;

-- 2022-01-19T14:14:00.414Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677529
;

-- 2022-01-19T14:14:00.414Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677529, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676770
;

-- 2022-01-19T14:14:00.415Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677530
;

-- 2022-01-19T14:14:00.415Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677530, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676771
;

-- 2022-01-19T14:14:00.415Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677531
;

-- 2022-01-19T14:14:00.416Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677531, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676772
;

-- 2022-01-19T14:14:00.416Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677532
;

-- 2022-01-19T14:14:00.416Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677532, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676773
;

-- 2022-01-19T14:14:00.417Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677533
;

-- 2022-01-19T14:14:00.417Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677533, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676774
;

-- 2022-01-19T14:14:00.418Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677534
;

-- 2022-01-19T14:14:00.418Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677534, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676775
;

-- 2022-01-19T14:14:00.418Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677535
;

-- 2022-01-19T14:14:00.419Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677535, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676776
;

-- 2022-01-19T14:14:00.419Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677536
;

-- 2022-01-19T14:14:00.419Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677536, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676777
;

-- 2022-01-19T14:14:00.420Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677537
;

-- 2022-01-19T14:14:00.420Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677537, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676778
;

-- 2022-01-19T14:14:00.421Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677538
;

-- 2022-01-19T14:14:00.421Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677538, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676779
;

-- 2022-01-19T14:14:00.422Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677539
;

-- 2022-01-19T14:14:00.422Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677539, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676780
;

-- 2022-01-19T14:14:00.422Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677540
;

-- 2022-01-19T14:14:00.423Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677540, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676781
;

-- 2022-01-19T14:14:00.423Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677541
;

-- 2022-01-19T14:14:00.423Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677541, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676782
;

-- 2022-01-19T14:14:00.424Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677542
;

-- 2022-01-19T14:14:00.424Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677542, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676783
;

-- 2022-01-19T14:14:00.425Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677543
;

-- 2022-01-19T14:14:00.425Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677543, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676784
;

-- 2022-01-19T14:14:00.426Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677544
;

-- 2022-01-19T14:14:00.426Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677544, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676785
;

-- 2022-01-19T14:14:00.427Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677545
;

-- 2022-01-19T14:14:00.427Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677545, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676786
;

-- 2022-01-19T14:14:00.428Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677546
;

-- 2022-01-19T14:14:00.428Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677546, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676787
;

-- 2022-01-19T14:14:00.429Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677547
;

-- 2022-01-19T14:14:00.429Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677547, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676788
;

-- 2022-01-19T14:14:00.432Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677548
;

-- 2022-01-19T14:14:00.432Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677548, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676789
;

-- 2022-01-19T14:14:00.433Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677549
;

-- 2022-01-19T14:14:00.433Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677549, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676790
;

-- 2022-01-19T14:14:00.434Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677550
;

-- 2022-01-19T14:14:00.435Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677550, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676791
;

-- 2022-01-19T14:14:00.435Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677551
;

-- 2022-01-19T14:14:00.436Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677551, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676792
;

-- 2022-01-19T14:14:00.436Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677552
;

-- 2022-01-19T14:14:00.436Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677552, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676793
;

-- 2022-01-19T14:14:00.437Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677553
;

-- 2022-01-19T14:14:00.437Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677553, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676794
;

-- 2022-01-19T14:14:00.437Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677554
;

-- 2022-01-19T14:14:00.438Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677554, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676795
;

-- 2022-01-19T14:14:00.438Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677555
;

-- 2022-01-19T14:14:00.438Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677555, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676796
;

-- 2022-01-19T14:14:00.439Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677556
;

-- 2022-01-19T14:14:00.439Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677556, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676797
;

-- 2022-01-19T14:14:00.440Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677557
;

-- 2022-01-19T14:14:00.440Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677557, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676798
;

-- 2022-01-19T14:14:00.441Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677558
;

-- 2022-01-19T14:14:00.441Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677558, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676799
;

-- 2022-01-19T14:14:00.442Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677559
;

-- 2022-01-19T14:14:00.442Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677559, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676800
;

-- 2022-01-19T14:14:00.443Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677560
;

-- 2022-01-19T14:14:00.443Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677560, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676801
;

-- 2022-01-19T14:14:00.444Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677561
;

-- 2022-01-19T14:14:00.444Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677561, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676802
;

-- 2022-01-19T14:14:00.444Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677562
;

-- 2022-01-19T14:14:00.445Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677562, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676803
;

-- 2022-01-19T14:14:00.445Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677563
;

-- 2022-01-19T14:14:00.445Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677563, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676804
;

-- 2022-01-19T14:14:00.446Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677564
;

-- 2022-01-19T14:14:00.446Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677564, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676805
;

-- 2022-01-19T14:14:00.447Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677565
;

-- 2022-01-19T14:14:00.447Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677565, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676806
;

-- 2022-01-19T14:14:00.447Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677566
;

-- 2022-01-19T14:14:00.448Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677566, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676807
;

-- 2022-01-19T14:14:00.448Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677567
;

-- 2022-01-19T14:14:00.448Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677567, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676808
;

-- 2022-01-19T14:14:00.449Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677568
;

-- 2022-01-19T14:14:00.449Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677568, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676809
;

-- 2022-01-19T14:14:00.449Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677569
;

-- 2022-01-19T14:14:00.450Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677569, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676810
;

-- 2022-01-19T14:14:00.450Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677570
;

-- 2022-01-19T14:14:00.450Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677570, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676811
;

-- 2022-01-19T14:14:00.451Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677571
;

-- 2022-01-19T14:14:00.451Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677571, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676812
;

-- 2022-01-19T14:14:00.452Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677572
;

-- 2022-01-19T14:14:00.452Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677572, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676813
;

-- 2022-01-19T14:14:00.452Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677573
;

-- 2022-01-19T14:14:00.453Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677573, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676814
;

-- 2022-01-19T14:14:00.453Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677574
;

-- 2022-01-19T14:14:00.454Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677574, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676815
;

-- 2022-01-19T14:14:00.455Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677575
;

-- 2022-01-19T14:14:00.455Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677575, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676816
;

-- 2022-01-19T14:14:00.456Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677576
;

-- 2022-01-19T14:14:00.456Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677576, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676817
;

-- 2022-01-19T14:14:00.457Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677577
;

-- 2022-01-19T14:14:00.457Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677577, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676818
;

-- 2022-01-19T14:14:00.457Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677578
;

-- 2022-01-19T14:14:00.458Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677578, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676819
;

-- 2022-01-19T14:14:00.458Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677579
;

-- 2022-01-19T14:14:00.458Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677579, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676820
;

-- 2022-01-19T14:14:00.459Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677580
;

-- 2022-01-19T14:14:00.459Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677580, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676821
;

-- 2022-01-19T14:14:00.459Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677581
;

-- 2022-01-19T14:14:00.459Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677581, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676822
;

-- 2022-01-19T14:14:00.460Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677582
;

-- 2022-01-19T14:14:00.460Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677582, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676823
;

-- 2022-01-19T14:14:00.460Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677583
;

-- 2022-01-19T14:14:00.461Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677583, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676824
;

-- 2022-01-19T14:14:00.461Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677584
;

-- 2022-01-19T14:14:00.461Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677584, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676825
;

-- 2022-01-19T14:14:00.462Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677585
;

-- 2022-01-19T14:14:00.462Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677585, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676826
;

-- 2022-01-19T14:14:00.463Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677586
;

-- 2022-01-19T14:14:00.463Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677586, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676827
;

-- 2022-01-19T14:14:00.464Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677587
;

-- 2022-01-19T14:14:00.464Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677587, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676828
;

-- 2022-01-19T14:14:00.464Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677588
;

-- 2022-01-19T14:14:00.464Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677588, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676829
;

-- 2022-01-19T14:14:00.465Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677589
;

-- 2022-01-19T14:14:00.465Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677589, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676830
;

-- 2022-01-19T14:14:00.465Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677590
;

-- 2022-01-19T14:14:00.465Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677590, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676831
;

-- 2022-01-19T14:14:00.466Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677591
;

-- 2022-01-19T14:14:00.466Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677591, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676832
;

-- 2022-01-19T14:14:00.466Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677592
;

-- 2022-01-19T14:14:00.467Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677592, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676833
;

-- 2022-01-19T14:14:00.467Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677593
;

-- 2022-01-19T14:14:00.467Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677593, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676834
;

-- 2022-01-19T14:14:00.468Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677594
;

-- 2022-01-19T14:14:00.468Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677594, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676835
;

-- 2022-01-19T14:14:00.468Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677595
;

-- 2022-01-19T14:14:00.468Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677595, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676836
;

-- 2022-01-19T14:14:00.469Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677596
;

-- 2022-01-19T14:14:00.469Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677596, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676837
;

-- 2022-01-19T14:14:00.469Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677597
;

-- 2022-01-19T14:14:00.470Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677597, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676838
;

-- 2022-01-19T14:14:00.470Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677598
;

-- 2022-01-19T14:14:00.470Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677598, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676839
;

-- 2022-01-19T14:14:00.470Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677599
;

-- 2022-01-19T14:14:00.471Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677599, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676840
;

-- 2022-01-19T14:14:00.471Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677600
;

-- 2022-01-19T14:14:00.471Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677600, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676841
;

-- 2022-01-19T14:14:00.472Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677601
;

-- 2022-01-19T14:14:00.472Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677601, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676842
;

-- 2022-01-19T14:14:00.472Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677602
;

-- 2022-01-19T14:14:00.472Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677602, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676843
;

-- 2022-01-19T14:14:00.473Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677603
;

-- 2022-01-19T14:14:00.473Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677603, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676844
;

-- 2022-01-19T14:14:00.473Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677604
;

-- 2022-01-19T14:14:00.474Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677604, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676845
;

-- 2022-01-19T14:14:00.474Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677605
;

-- 2022-01-19T14:14:00.474Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677605, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676846
;

-- 2022-01-19T14:14:00.474Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677606
;

-- 2022-01-19T14:14:00.475Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677606, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676847
;

-- 2022-01-19T14:14:00.475Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677607
;

-- 2022-01-19T14:14:00.475Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677607, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676848
;

-- 2022-01-19T14:14:00.476Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677608
;

-- 2022-01-19T14:14:00.476Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677608, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676849
;

-- 2022-01-19T14:14:00.476Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677609
;

-- 2022-01-19T14:14:00.476Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677609, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676850
;

-- 2022-01-19T14:14:00.477Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677610
;

-- 2022-01-19T14:14:00.477Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677610, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676851
;

-- 2022-01-19T14:14:00.477Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677611
;

-- 2022-01-19T14:14:00.477Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677611, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676852
;

-- 2022-01-19T14:14:00.478Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677612
;

-- 2022-01-19T14:14:00.478Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677612, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676853
;

-- 2022-01-19T14:14:00.478Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677613
;

-- 2022-01-19T14:14:00.479Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677613, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676854
;

-- 2022-01-19T14:14:00.479Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677614
;

-- 2022-01-19T14:14:00.479Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677614, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676855
;

-- 2022-01-19T14:14:00.480Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677615
;

-- 2022-01-19T14:14:00.480Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677615, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676856
;

-- 2022-01-19T14:14:00.480Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677616
;

-- 2022-01-19T14:14:00.480Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677616, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676857
;

-- 2022-01-19T14:14:00.481Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677617
;

-- 2022-01-19T14:14:00.481Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677617, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676858
;

-- 2022-01-19T14:14:00.481Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677618
;

-- 2022-01-19T14:14:00.481Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677618, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676859
;

-- 2022-01-19T14:14:00.482Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677619
;

-- 2022-01-19T14:14:00.482Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677619, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676860
;

-- 2022-01-19T14:14:00.482Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677620
;

-- 2022-01-19T14:14:00.482Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677620, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676861
;

-- 2022-01-19T14:14:00.483Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677621
;

-- 2022-01-19T14:14:00.483Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677621, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676862
;

-- 2022-01-19T14:14:00.484Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677622
;

-- 2022-01-19T14:14:00.484Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677622, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676863
;

-- 2022-01-19T14:14:00.485Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677623
;

-- 2022-01-19T14:14:00.485Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677623, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676864
;

-- 2022-01-19T14:14:00.485Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677624
;

-- 2022-01-19T14:14:00.485Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677624, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676865
;

-- 2022-01-19T14:14:00.486Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677625
;

-- 2022-01-19T14:14:00.486Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677625, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676866
;

-- 2022-01-19T14:14:00.486Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677626
;

-- 2022-01-19T14:14:00.487Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677626, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676867
;

-- 2022-01-19T14:14:00.487Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677627
;

-- 2022-01-19T14:14:00.487Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677627, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676868
;

-- 2022-01-19T14:14:00.487Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677628
;

-- 2022-01-19T14:14:00.488Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677628, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676869
;

-- 2022-01-19T14:14:00.488Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677629
;

-- 2022-01-19T14:14:00.488Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677629, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676870
;

-- 2022-01-19T14:14:00.488Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677630
;

-- 2022-01-19T14:14:00.489Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677630, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676871
;

-- 2022-01-19T14:14:00.489Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677631
;

-- 2022-01-19T14:14:00.489Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677631, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676872
;

-- 2022-01-19T14:14:00.490Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677632
;

-- 2022-01-19T14:14:00.490Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677632, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676873
;

-- 2022-01-19T14:14:00.490Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545317
;

-- 2022-01-19T14:14:00.490Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545317, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545271
;

-- 2022-01-19T14:14:00.498Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677633
;

-- 2022-01-19T14:14:00.498Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677633, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676874
;

-- 2022-01-19T14:14:00.499Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677634
;

-- 2022-01-19T14:14:00.499Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677634, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676875
;

-- 2022-01-19T14:14:00.499Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677635
;

-- 2022-01-19T14:14:00.500Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677635, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676876
;

-- 2022-01-19T14:14:00.500Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677636
;

-- 2022-01-19T14:14:00.501Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677636, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676877
;

-- 2022-01-19T14:14:00.502Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677637
;

-- 2022-01-19T14:14:00.502Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677637, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676878
;

-- 2022-01-19T14:14:00.502Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677638
;

-- 2022-01-19T14:14:00.503Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677638, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676879
;

-- 2022-01-19T14:14:00.504Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677639
;

-- 2022-01-19T14:14:00.504Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677639, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676880
;

-- 2022-01-19T14:14:00.505Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677640
;

-- 2022-01-19T14:14:00.505Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677640, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676881
;

-- 2022-01-19T14:14:00.506Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677641
;

-- 2022-01-19T14:14:00.507Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677641, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676882
;

-- 2022-01-19T14:14:00.507Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677642
;

-- 2022-01-19T14:14:00.507Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677642, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676883
;

-- 2022-01-19T14:14:00.509Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677643
;

-- 2022-01-19T14:14:00.509Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677643, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676884
;

-- 2022-01-19T14:14:00.510Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677644
;

-- 2022-01-19T14:14:00.510Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677644, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676885
;

-- 2022-01-19T14:14:00.511Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677645
;

-- 2022-01-19T14:14:00.511Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677645, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676886
;

-- 2022-01-19T14:14:00.512Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677646
;

-- 2022-01-19T14:14:00.513Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677646, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676887
;

-- 2022-01-19T14:14:00.513Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677647
;

-- 2022-01-19T14:14:00.514Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677647, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676888
;

-- 2022-01-19T14:14:00.514Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677648
;

-- 2022-01-19T14:14:00.514Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677648, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676889
;

-- 2022-01-19T14:14:00.515Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677649
;

-- 2022-01-19T14:14:00.515Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677649, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676890
;

-- 2022-01-19T14:14:00.516Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677650
;

-- 2022-01-19T14:14:00.516Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677650, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676891
;

-- 2022-01-19T14:14:00.516Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677651
;

-- 2022-01-19T14:14:00.517Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677651, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676892
;

-- 2022-01-19T14:14:00.517Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677652
;

-- 2022-01-19T14:14:00.518Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677652, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676893
;

-- 2022-01-19T14:14:00.518Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677653
;

-- 2022-01-19T14:14:00.518Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677653, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676894
;

-- 2022-01-19T14:14:00.518Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677654
;

-- 2022-01-19T14:14:00.519Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677654, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676895
;

-- 2022-01-19T14:14:00.520Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677655
;

-- 2022-01-19T14:14:00.520Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677655, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676896
;

-- 2022-01-19T14:14:00.520Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677656
;

-- 2022-01-19T14:14:00.521Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677656, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676897
;

-- 2022-01-19T14:14:00.521Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677657
;

-- 2022-01-19T14:14:00.521Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677657, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676898
;

-- 2022-01-19T14:14:00.522Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677658
;

-- 2022-01-19T14:14:00.522Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677658, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676899
;

-- 2022-01-19T14:14:00.522Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677659
;

-- 2022-01-19T14:14:00.522Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677659, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676900
;

-- 2022-01-19T14:14:00.523Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545318
;

-- 2022-01-19T14:14:00.523Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545318, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545272
;

-- 2022-01-19T14:14:00.528Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677660
;

-- 2022-01-19T14:14:00.529Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677660, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676901
;

-- 2022-01-19T14:14:00.529Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677661
;

-- 2022-01-19T14:14:00.530Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677661, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676902
;

-- 2022-01-19T14:14:00.530Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677662
;

-- 2022-01-19T14:14:00.530Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677662, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676903
;

-- 2022-01-19T14:14:00.531Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677663
;

-- 2022-01-19T14:14:00.531Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677663, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676904
;

-- 2022-01-19T14:14:00.532Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677664
;

-- 2022-01-19T14:14:00.532Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677664, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676905
;

-- 2022-01-19T14:14:00.533Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677665
;

-- 2022-01-19T14:14:00.533Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677665, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676906
;

-- 2022-01-19T14:14:00.534Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677666
;

-- 2022-01-19T14:14:00.534Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677666, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676907
;

-- 2022-01-19T14:14:00.534Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677667
;

-- 2022-01-19T14:14:00.534Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677667, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676908
;

-- 2022-01-19T14:14:00.535Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677668
;

-- 2022-01-19T14:14:00.535Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677668, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676909
;

-- 2022-01-19T14:14:00.536Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677669
;

-- 2022-01-19T14:14:00.536Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677669, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676910
;

-- 2022-01-19T14:14:00.536Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677670
;

-- 2022-01-19T14:14:00.536Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677670, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676911
;

-- 2022-01-19T14:14:00.537Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677671
;

-- 2022-01-19T14:14:00.537Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677671, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676912
;

-- 2022-01-19T14:14:00.538Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677672
;

-- 2022-01-19T14:14:00.538Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677672, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676913
;

-- 2022-01-19T14:14:00.538Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677673
;

-- 2022-01-19T14:14:00.538Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677673, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676914
;

-- 2022-01-19T14:14:00.539Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545319
;

-- 2022-01-19T14:14:00.539Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545319, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545273
;

-- 2022-01-19T14:14:00.546Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677674
;

-- 2022-01-19T14:14:00.546Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677674, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676915
;

-- 2022-01-19T14:14:00.547Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677675
;

-- 2022-01-19T14:14:00.548Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677675, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676916
;

-- 2022-01-19T14:14:00.548Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677676
;

-- 2022-01-19T14:14:00.548Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677676, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676917
;

-- 2022-01-19T14:14:00.549Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677677
;

-- 2022-01-19T14:14:00.549Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677677, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676918
;

-- 2022-01-19T14:14:00.549Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677678
;

-- 2022-01-19T14:14:00.549Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677678, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676919
;

-- 2022-01-19T14:14:00.550Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677679
;

-- 2022-01-19T14:14:00.550Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677679, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676920
;

-- 2022-01-19T14:14:00.550Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677680
;

-- 2022-01-19T14:14:00.550Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677680, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676921
;

-- 2022-01-19T14:14:00.551Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677681
;

-- 2022-01-19T14:14:00.552Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677681, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676922
;

-- 2022-01-19T14:14:00.553Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677682
;

-- 2022-01-19T14:14:00.553Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677682, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676923
;

-- 2022-01-19T14:14:00.554Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677683
;

-- 2022-01-19T14:14:00.555Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677683, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676924
;

-- 2022-01-19T14:14:00.555Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677684
;

-- 2022-01-19T14:14:00.555Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677684, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676925
;

-- 2022-01-19T14:14:00.556Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677685
;

-- 2022-01-19T14:14:00.556Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677685, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676926
;

-- 2022-01-19T14:14:00.556Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677686
;

-- 2022-01-19T14:14:00.556Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677686, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676927
;

-- 2022-01-19T14:14:00.557Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677687
;

-- 2022-01-19T14:14:00.557Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677687, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676928
;

-- 2022-01-19T14:14:00.557Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677688
;

-- 2022-01-19T14:14:00.557Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677688, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676929
;

-- 2022-01-19T14:14:00.558Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677689
;

-- 2022-01-19T14:14:00.558Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677689, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676930
;

-- 2022-01-19T14:14:00.559Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677690
;

-- 2022-01-19T14:14:00.559Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677690, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676931
;

-- 2022-01-19T14:14:00.560Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677691
;

-- 2022-01-19T14:14:00.560Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677691, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676932
;

-- 2022-01-19T14:14:00.560Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677692
;

-- 2022-01-19T14:14:00.560Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677692, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676933
;

-- 2022-01-19T14:14:00.561Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677693
;

-- 2022-01-19T14:14:00.561Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677693, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676934
;

-- 2022-01-19T14:14:00.561Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677694
;

-- 2022-01-19T14:14:00.561Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677694, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676935
;

-- 2022-01-19T14:14:00.562Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677695
;

-- 2022-01-19T14:14:00.562Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677695, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676936
;

-- 2022-01-19T14:14:00.563Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677696
;

-- 2022-01-19T14:14:00.563Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677696, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676937
;

-- 2022-01-19T14:14:00.563Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677697
;

-- 2022-01-19T14:14:00.564Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677697, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676938
;

-- 2022-01-19T14:14:00.564Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677698
;

-- 2022-01-19T14:14:00.564Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677698, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676939
;

-- 2022-01-19T14:14:00.565Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545320
;

-- 2022-01-19T14:14:00.565Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545320, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545274
;

-- 2022-01-19T14:14:00.570Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677699
;

-- 2022-01-19T14:14:00.570Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677699, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676940
;

-- 2022-01-19T14:14:00.570Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677700
;

-- 2022-01-19T14:14:00.571Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677700, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676941
;

-- 2022-01-19T14:14:00.571Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677701
;

-- 2022-01-19T14:14:00.572Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677701, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676942
;

-- 2022-01-19T14:14:00.572Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677702
;

-- 2022-01-19T14:14:00.572Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677702, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676943
;

-- 2022-01-19T14:14:00.572Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677703
;

-- 2022-01-19T14:14:00.573Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677703, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676944
;

-- 2022-01-19T14:14:00.574Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677704
;

-- 2022-01-19T14:14:00.574Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677704, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676945
;

-- 2022-01-19T14:14:00.575Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677705
;

-- 2022-01-19T14:14:00.575Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677705, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676946
;

-- 2022-01-19T14:14:00.577Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677706
;

-- 2022-01-19T14:14:00.577Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677706, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676947
;

-- 2022-01-19T14:14:00.578Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677707
;

-- 2022-01-19T14:14:00.578Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677707, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676948
;

-- 2022-01-19T14:14:00.578Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677708
;

-- 2022-01-19T14:14:00.582Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677708, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676949
;

-- 2022-01-19T14:14:00.583Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677709
;

-- 2022-01-19T14:14:00.586Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677709, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676950
;

-- 2022-01-19T14:14:00.589Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677710
;

-- 2022-01-19T14:14:00.590Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677710, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676951
;

-- 2022-01-19T14:14:00.591Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677711
;

-- 2022-01-19T14:14:00.592Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677711, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676952
;

-- 2022-01-19T14:14:00.595Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545321
;

-- 2022-01-19T14:14:00.595Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545321, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545275
;

-- 2022-01-19T14:14:00.605Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677712
;

-- 2022-01-19T14:14:00.606Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677712, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676953
;

-- 2022-01-19T14:14:00.609Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677713
;

-- 2022-01-19T14:14:00.609Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677713, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676954
;

-- 2022-01-19T14:14:00.610Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677714
;

-- 2022-01-19T14:14:00.610Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677714, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676955
;

-- 2022-01-19T14:14:00.611Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677715
;

-- 2022-01-19T14:14:00.611Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677715, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676956
;

-- 2022-01-19T14:14:00.612Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677716
;

-- 2022-01-19T14:14:00.612Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677716, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676957
;

-- 2022-01-19T14:14:00.612Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677717
;

-- 2022-01-19T14:14:00.612Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677717, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676958
;

-- 2022-01-19T14:14:00.613Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677718
;

-- 2022-01-19T14:14:00.613Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677718, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676959
;

-- 2022-01-19T14:14:00.613Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677719
;

-- 2022-01-19T14:14:00.614Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677719, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676960
;

-- 2022-01-19T14:14:00.614Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677720
;

-- 2022-01-19T14:14:00.615Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677720, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676961
;

-- 2022-01-19T14:14:00.615Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677721
;

-- 2022-01-19T14:14:00.615Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677721, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676962
;

-- 2022-01-19T14:14:00.616Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677722
;

-- 2022-01-19T14:14:00.616Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677722, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676963
;

-- 2022-01-19T14:14:00.617Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677723
;

-- 2022-01-19T14:14:00.617Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677723, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676964
;

-- 2022-01-19T14:14:00.617Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677724
;

-- 2022-01-19T14:14:00.618Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677724, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676965
;

-- 2022-01-19T14:14:00.618Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677725
;

-- 2022-01-19T14:14:00.618Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677725, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676966
;

-- 2022-01-19T14:14:00.618Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677726
;

-- 2022-01-19T14:14:00.619Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677726, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676967
;

-- 2022-01-19T14:14:00.619Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677727
;

-- 2022-01-19T14:14:00.619Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677727, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676968
;

-- 2022-01-19T14:14:00.620Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677728
;

-- 2022-01-19T14:14:00.620Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677728, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676969
;

-- 2022-01-19T14:14:00.620Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677729
;

-- 2022-01-19T14:14:00.620Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677729, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676970
;

-- 2022-01-19T14:14:00.621Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677730
;

-- 2022-01-19T14:14:00.621Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677730, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676971
;

-- 2022-01-19T14:14:00.622Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677731
;

-- 2022-01-19T14:14:00.624Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677731, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676972
;

-- 2022-01-19T14:14:00.625Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677732
;

-- 2022-01-19T14:14:00.625Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677732, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676973
;

-- 2022-01-19T14:14:00.626Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677733
;

-- 2022-01-19T14:14:00.626Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677733, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676974
;

-- 2022-01-19T14:14:00.626Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677734
;

-- 2022-01-19T14:14:00.626Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677734, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676975
;

-- 2022-01-19T14:14:00.627Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677735
;

-- 2022-01-19T14:14:00.627Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677735, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676976
;

-- 2022-01-19T14:14:00.628Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677736
;

-- 2022-01-19T14:14:00.628Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677736, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676977
;

-- 2022-01-19T14:14:00.631Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677737
;

-- 2022-01-19T14:14:00.631Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677737, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676978
;

-- 2022-01-19T14:14:00.631Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677738
;

-- 2022-01-19T14:14:00.632Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677738, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676979
;

-- 2022-01-19T14:14:00.632Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677739
;

-- 2022-01-19T14:14:00.632Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677739, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676980
;

-- 2022-01-19T14:14:00.632Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677740
;

-- 2022-01-19T14:14:00.633Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677740, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676981
;

-- 2022-01-19T14:14:00.633Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677741
;

-- 2022-01-19T14:14:00.633Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677741, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676982
;

-- 2022-01-19T14:14:00.633Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677742
;

-- 2022-01-19T14:14:00.634Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677742, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676983
;

-- 2022-01-19T14:14:00.634Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677743
;

-- 2022-01-19T14:14:00.634Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677743, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676984
;

-- 2022-01-19T14:14:00.635Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677744
;

-- 2022-01-19T14:14:00.635Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677744, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676985
;

-- 2022-01-19T14:14:00.636Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677745
;

-- 2022-01-19T14:14:00.636Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677745, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676986
;

-- 2022-01-19T14:14:00.636Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677746
;

-- 2022-01-19T14:14:00.637Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677746, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676987
;

-- 2022-01-19T14:14:00.637Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677747
;

-- 2022-01-19T14:14:00.637Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677747, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676988
;

-- 2022-01-19T14:14:00.638Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677748
;

-- 2022-01-19T14:14:00.638Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677748, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676989
;

-- 2022-01-19T14:14:00.639Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677749
;

-- 2022-01-19T14:14:00.639Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677749, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676990
;

-- 2022-01-19T14:14:00.639Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677750
;

-- 2022-01-19T14:14:00.639Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677750, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676991
;

-- 2022-01-19T14:14:00.640Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545322
;

-- 2022-01-19T14:14:00.640Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545322, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545276
;

-- 2022-01-19T14:14:00.644Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677751
;

-- 2022-01-19T14:14:00.644Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677751, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676992
;

-- 2022-01-19T14:14:00.644Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677752
;

-- 2022-01-19T14:14:00.644Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677752, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676993
;

-- 2022-01-19T14:14:00.645Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677753
;

-- 2022-01-19T14:14:00.648Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677753, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676994
;

-- 2022-01-19T14:14:00.649Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677754
;

-- 2022-01-19T14:14:00.649Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677754, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676995
;

-- 2022-01-19T14:14:00.649Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677755
;

-- 2022-01-19T14:14:00.649Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677755, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676996
;

-- 2022-01-19T14:14:00.650Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677756
;

-- 2022-01-19T14:14:00.650Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677756, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676997
;

-- 2022-01-19T14:14:00.651Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677757
;

-- 2022-01-19T14:14:00.651Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677757, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676998
;

-- 2022-01-19T14:14:00.652Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677758
;

-- 2022-01-19T14:14:00.652Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677758, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 676999
;

-- 2022-01-19T14:14:00.653Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545323
;

-- 2022-01-19T14:14:00.653Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545323, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545277
;

-- 2022-01-19T14:14:00.661Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677759
;

-- 2022-01-19T14:14:00.661Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677759, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677000
;

-- 2022-01-19T14:14:00.662Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677760
;

-- 2022-01-19T14:14:00.662Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677760, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677001
;

-- 2022-01-19T14:14:00.664Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677761
;

-- 2022-01-19T14:14:00.664Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677761, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677002
;

-- 2022-01-19T14:14:00.664Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677762
;

-- 2022-01-19T14:14:00.664Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677762, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677003
;

-- 2022-01-19T14:14:00.665Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677763
;

-- 2022-01-19T14:14:00.665Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677763, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677004
;

-- 2022-01-19T14:14:00.665Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677764
;

-- 2022-01-19T14:14:00.666Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677764, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677005
;

-- 2022-01-19T14:14:00.669Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677765
;

-- 2022-01-19T14:14:00.669Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677765, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677006
;

-- 2022-01-19T14:14:00.670Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677766
;

-- 2022-01-19T14:14:00.670Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677766, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677007
;

-- 2022-01-19T14:14:00.671Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677767
;

-- 2022-01-19T14:14:00.671Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677767, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677008
;

-- 2022-01-19T14:14:00.672Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677768
;

-- 2022-01-19T14:14:00.672Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677768, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677009
;

-- 2022-01-19T14:14:00.673Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677769
;

-- 2022-01-19T14:14:00.673Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677769, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677010
;

-- 2022-01-19T14:14:00.673Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677770
;

-- 2022-01-19T14:14:00.673Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677770, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677011
;

-- 2022-01-19T14:14:00.674Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677771
;

-- 2022-01-19T14:14:00.674Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677771, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677012
;

-- 2022-01-19T14:14:00.677Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677772
;

-- 2022-01-19T14:14:00.677Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677772, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677013
;

-- 2022-01-19T14:14:00.678Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677773
;

-- 2022-01-19T14:14:00.678Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677773, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677014
;

-- 2022-01-19T14:14:00.679Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677774
;

-- 2022-01-19T14:14:00.679Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677774, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677015
;

-- 2022-01-19T14:14:00.681Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677775
;

-- 2022-01-19T14:14:00.682Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677775, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677016
;

-- 2022-01-19T14:14:00.682Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677776
;

-- 2022-01-19T14:14:00.682Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677776, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677017
;

-- 2022-01-19T14:14:00.683Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677777
;

-- 2022-01-19T14:14:00.683Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677777, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677018
;

-- 2022-01-19T14:14:00.686Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677778
;

-- 2022-01-19T14:14:00.688Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677778, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677019
;

-- 2022-01-19T14:14:00.688Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677779
;

-- 2022-01-19T14:14:00.688Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677779, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677020
;

-- 2022-01-19T14:14:00.689Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677780
;

-- 2022-01-19T14:14:00.689Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677780, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677021
;

-- 2022-01-19T14:14:00.690Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677781
;

-- 2022-01-19T14:14:00.690Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677781, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677022
;

-- 2022-01-19T14:14:00.690Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677782
;

-- 2022-01-19T14:14:00.690Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677782, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677023
;

-- 2022-01-19T14:14:00.691Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677783
;

-- 2022-01-19T14:14:00.691Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677783, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677024
;

-- 2022-01-19T14:14:00.692Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677784
;

-- 2022-01-19T14:14:00.692Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677784, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677025
;

-- 2022-01-19T14:14:00.693Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677785
;

-- 2022-01-19T14:14:00.693Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677785, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677026
;

-- 2022-01-19T14:14:00.693Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677786
;

-- 2022-01-19T14:14:00.693Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677786, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677027
;

-- 2022-01-19T14:14:00.694Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677787
;

-- 2022-01-19T14:14:00.694Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677787, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677028
;

-- 2022-01-19T14:14:00.694Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545324
;

-- 2022-01-19T14:14:00.694Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545324, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545278
;

-- 2022-01-19T14:14:00.699Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677788
;

-- 2022-01-19T14:14:00.700Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677788, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677029
;

-- 2022-01-19T14:14:00.701Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677789
;

-- 2022-01-19T14:14:00.704Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677789, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677030
;

-- 2022-01-19T14:14:00.705Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677790
;

-- 2022-01-19T14:14:00.705Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677790, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677031
;

-- 2022-01-19T14:14:00.705Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677791
;

-- 2022-01-19T14:14:00.705Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677791, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677032
;

-- 2022-01-19T14:14:00.706Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677792
;

-- 2022-01-19T14:14:00.706Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677792, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677033
;

-- 2022-01-19T14:14:00.706Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677793
;

-- 2022-01-19T14:14:00.707Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677793, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677034
;

-- 2022-01-19T14:14:00.707Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677794
;

-- 2022-01-19T14:14:00.707Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677794, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677035
;

-- 2022-01-19T14:14:00.708Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677795
;

-- 2022-01-19T14:14:00.708Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677795, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677036
;

-- 2022-01-19T14:14:00.708Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677796
;

-- 2022-01-19T14:14:00.708Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677796, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677037
;

-- 2022-01-19T14:14:00.709Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677797
;

-- 2022-01-19T14:14:00.709Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677797, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677038
;

-- 2022-01-19T14:14:00.709Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677798
;

-- 2022-01-19T14:14:00.710Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677798, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677039
;

-- 2022-01-19T14:14:00.710Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677799
;

-- 2022-01-19T14:14:00.710Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677799, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677040
;

-- 2022-01-19T14:14:00.710Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677800
;

-- 2022-01-19T14:14:00.711Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677800, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677041
;

-- 2022-01-19T14:14:00.711Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545325
;

-- 2022-01-19T14:14:00.711Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545325, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545279
;

-- 2022-01-19T14:14:00.715Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677801
;

-- 2022-01-19T14:14:00.716Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677801, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677042
;

-- 2022-01-19T14:14:00.716Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677802
;

-- 2022-01-19T14:14:00.716Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677802, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677043
;

-- 2022-01-19T14:14:00.717Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677803
;

-- 2022-01-19T14:14:00.717Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677803, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677044
;

-- 2022-01-19T14:14:00.717Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677804
;

-- 2022-01-19T14:14:00.717Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677804, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677045
;

-- 2022-01-19T14:14:00.718Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677805
;

-- 2022-01-19T14:14:00.718Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677805, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677046
;

-- 2022-01-19T14:14:00.718Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677806
;

-- 2022-01-19T14:14:00.718Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677806, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677047
;

-- 2022-01-19T14:14:00.719Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677807
;

-- 2022-01-19T14:14:00.719Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677807, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677048
;

-- 2022-01-19T14:14:00.720Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677808
;

-- 2022-01-19T14:14:00.720Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677808, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677049
;

-- 2022-01-19T14:14:00.720Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677809
;

-- 2022-01-19T14:14:00.721Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677809, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677050
;

-- 2022-01-19T14:14:00.721Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677810
;

-- 2022-01-19T14:14:00.721Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677810, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677051
;

-- 2022-01-19T14:14:00.722Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677811
;

-- 2022-01-19T14:14:00.722Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677811, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677052
;

-- 2022-01-19T14:14:00.722Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545326
;

-- 2022-01-19T14:14:00.722Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545326, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545280
;

-- 2022-01-19T14:14:00.725Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677812
;

-- 2022-01-19T14:14:00.726Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677812, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677053
;

-- 2022-01-19T14:14:00.726Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677813
;

-- 2022-01-19T14:14:00.726Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677813, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677054
;

-- 2022-01-19T14:14:00.727Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677814
;

-- 2022-01-19T14:14:00.727Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677814, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677055
;

-- 2022-01-19T14:14:00.728Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677815
;

-- 2022-01-19T14:14:00.728Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677815, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677056
;

-- 2022-01-19T14:14:00.729Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677816
;

-- 2022-01-19T14:14:00.729Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677816, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677057
;

-- 2022-01-19T14:14:00.729Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677817
;

-- 2022-01-19T14:14:00.729Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677817, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677058
;

-- 2022-01-19T14:14:00.730Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545327
;

-- 2022-01-19T14:14:00.730Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545327, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545281
;

-- 2022-01-19T14:14:00.733Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677818
;

-- 2022-01-19T14:14:00.734Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677818, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677059
;

-- 2022-01-19T14:14:00.734Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677819
;

-- 2022-01-19T14:14:00.734Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677819, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677060
;

-- 2022-01-19T14:14:00.735Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677820
;

-- 2022-01-19T14:14:00.735Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677820, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677061
;

-- 2022-01-19T14:14:00.735Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677821
;

-- 2022-01-19T14:14:00.736Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677821, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677062
;

-- 2022-01-19T14:14:00.736Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677822
;

-- 2022-01-19T14:14:00.736Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677822, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677063
;

-- 2022-01-19T14:14:00.737Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677823
;

-- 2022-01-19T14:14:00.737Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677823, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677064
;

-- 2022-01-19T14:14:00.737Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677824
;

-- 2022-01-19T14:14:00.738Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677824, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677065
;

-- 2022-01-19T14:14:00.738Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677825
;

-- 2022-01-19T14:14:00.741Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677825, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677066
;

-- 2022-01-19T14:14:00.742Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545328
;

-- 2022-01-19T14:14:00.742Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545328, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545282
;

-- 2022-01-19T14:14:00.745Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677826
;

-- 2022-01-19T14:14:00.745Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677826, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677067
;

-- 2022-01-19T14:14:00.745Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677827
;

-- 2022-01-19T14:14:00.745Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677827, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677068
;

-- 2022-01-19T14:14:00.746Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677828
;

-- 2022-01-19T14:14:00.746Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677828, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677069
;

-- 2022-01-19T14:14:00.746Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677829
;

-- 2022-01-19T14:14:00.747Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677829, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677070
;

-- 2022-01-19T14:14:00.747Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677830
;

-- 2022-01-19T14:14:00.748Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677830, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677071
;

-- 2022-01-19T14:14:00.748Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677831
;

-- 2022-01-19T14:14:00.749Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677831, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677072
;

-- 2022-01-19T14:14:00.749Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545329
;

-- 2022-01-19T14:14:00.749Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545329, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545283
;

-- 2022-01-19T14:14:00.753Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677832
;

-- 2022-01-19T14:14:00.753Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677832, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677073
;

-- 2022-01-19T14:14:00.753Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677833
;

-- 2022-01-19T14:14:00.753Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677833, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677074
;

-- 2022-01-19T14:14:00.754Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677834
;

-- 2022-01-19T14:14:00.754Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677834, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677075
;

-- 2022-01-19T14:14:00.754Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677835
;

-- 2022-01-19T14:14:00.754Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677835, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677076
;

-- 2022-01-19T14:14:00.755Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677836
;

-- 2022-01-19T14:14:00.755Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677836, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677077
;

-- 2022-01-19T14:14:00.756Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677837
;

-- 2022-01-19T14:14:00.756Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677837, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677078
;

-- 2022-01-19T14:14:00.756Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677838
;

-- 2022-01-19T14:14:00.759Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677838, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677079
;

-- 2022-01-19T14:14:00.759Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677839
;

-- 2022-01-19T14:14:00.760Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677839, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677080
;

-- 2022-01-19T14:14:00.761Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545330
;

-- 2022-01-19T14:14:00.761Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545330, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545284
;

-- 2022-01-19T14:14:00.764Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677840
;

-- 2022-01-19T14:14:00.764Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677840, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677081
;

-- 2022-01-19T14:14:00.765Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677841
;

-- 2022-01-19T14:14:00.765Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677841, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677082
;

-- 2022-01-19T14:14:00.765Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677842
;

-- 2022-01-19T14:14:00.766Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677842, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677083
;

-- 2022-01-19T14:14:00.768Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677843
;

-- 2022-01-19T14:14:00.769Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677843, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677084
;

-- 2022-01-19T14:14:00.769Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677844
;

-- 2022-01-19T14:14:00.769Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677844, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677085
;

-- 2022-01-19T14:14:00.770Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677845
;

-- 2022-01-19T14:14:00.770Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677845, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677086
;

-- 2022-01-19T14:14:00.771Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545331
;

-- 2022-01-19T14:14:00.771Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545331, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545285
;

-- 2022-01-19T14:14:00.774Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677846
;

-- 2022-01-19T14:14:00.774Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677846, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677087
;

-- 2022-01-19T14:14:00.775Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677847
;

-- 2022-01-19T14:14:00.775Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677847, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677088
;

-- 2022-01-19T14:14:00.775Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677848
;

-- 2022-01-19T14:14:00.776Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677848, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677089
;

-- 2022-01-19T14:14:00.776Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677849
;

-- 2022-01-19T14:14:00.776Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677849, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677090
;

-- 2022-01-19T14:14:00.777Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677850
;

-- 2022-01-19T14:14:00.777Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677850, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677091
;

-- 2022-01-19T14:14:00.777Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677851
;

-- 2022-01-19T14:14:00.777Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677851, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677092
;

-- 2022-01-19T14:14:00.778Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545332
;

-- 2022-01-19T14:14:00.778Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545332, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545286
;

-- 2022-01-19T14:14:00.781Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677852
;

-- 2022-01-19T14:14:00.781Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677852, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677093
;

-- 2022-01-19T14:14:00.781Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677853
;

-- 2022-01-19T14:14:00.782Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677853, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677094
;

-- 2022-01-19T14:14:00.782Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677854
;

-- 2022-01-19T14:14:00.782Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677854, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677095
;

-- 2022-01-19T14:14:00.783Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677855
;

-- 2022-01-19T14:14:00.783Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677855, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677096
;

-- 2022-01-19T14:14:00.783Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677856
;

-- 2022-01-19T14:14:00.783Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677856, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677097
;

-- 2022-01-19T14:14:00.784Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677857
;

-- 2022-01-19T14:14:00.784Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677857, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677098
;

-- 2022-01-19T14:14:00.784Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545333
;

-- 2022-01-19T14:14:00.785Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545333, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545287
;

-- 2022-01-19T14:14:00.789Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677858
;

-- 2022-01-19T14:14:00.789Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677858, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677099
;

-- 2022-01-19T14:14:00.790Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677859
;

-- 2022-01-19T14:14:00.790Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677859, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677100
;

-- 2022-01-19T14:14:00.790Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677860
;

-- 2022-01-19T14:14:00.790Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677860, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677101
;

-- 2022-01-19T14:14:00.791Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677861
;

-- 2022-01-19T14:14:00.791Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677861, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677102
;

-- 2022-01-19T14:14:00.791Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677862
;

-- 2022-01-19T14:14:00.792Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677862, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677103
;

-- 2022-01-19T14:14:00.792Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677863
;

-- 2022-01-19T14:14:00.792Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677863, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677104
;

-- 2022-01-19T14:14:00.793Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677864
;

-- 2022-01-19T14:14:00.793Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677864, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677105
;

-- 2022-01-19T14:14:00.793Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677865
;

-- 2022-01-19T14:14:00.793Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677865, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677106
;

-- 2022-01-19T14:14:00.794Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677866
;

-- 2022-01-19T14:14:00.794Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677866, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677107
;

-- 2022-01-19T14:14:00.794Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677867
;

-- 2022-01-19T14:14:00.795Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677867, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677108
;

-- 2022-01-19T14:14:00.795Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677868
;

-- 2022-01-19T14:14:00.795Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677868, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677109
;

-- 2022-01-19T14:14:00.796Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677869
;

-- 2022-01-19T14:14:00.796Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677869, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677110
;

-- 2022-01-19T14:14:00.796Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677870
;

-- 2022-01-19T14:14:00.797Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677870, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677111
;

-- 2022-01-19T14:14:00.797Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545334
;

-- 2022-01-19T14:14:00.797Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545334, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545288
;

-- 2022-01-19T14:14:00.800Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677871
;

-- 2022-01-19T14:14:00.801Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677871, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677112
;

-- 2022-01-19T14:14:00.801Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677872
;

-- 2022-01-19T14:14:00.801Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677872, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677113
;

-- 2022-01-19T14:14:00.802Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677873
;

-- 2022-01-19T14:14:00.802Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677873, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677114
;

-- 2022-01-19T14:14:00.803Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677874
;

-- 2022-01-19T14:14:00.803Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677874, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677115
;

-- 2022-01-19T14:14:00.803Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677875
;

-- 2022-01-19T14:14:00.804Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677875, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677116
;

-- 2022-01-19T14:14:00.804Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677876
;

-- 2022-01-19T14:14:00.804Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677876, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677117
;

-- 2022-01-19T14:14:00.808Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677877
;

-- 2022-01-19T14:14:00.808Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677877, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677118
;

-- 2022-01-19T14:14:00.809Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545335
;

-- 2022-01-19T14:14:00.809Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545335, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545289
;

-- 2022-01-19T14:14:00.812Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677878
;

-- 2022-01-19T14:14:00.812Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677878, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677119
;

-- 2022-01-19T14:14:00.813Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677879
;

-- 2022-01-19T14:14:00.813Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677879, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677120
;

-- 2022-01-19T14:14:00.813Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677880
;

-- 2022-01-19T14:14:00.814Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677880, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677121
;

-- 2022-01-19T14:14:00.814Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677881
;

-- 2022-01-19T14:14:00.814Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677881, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677122
;

-- 2022-01-19T14:14:00.815Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677882
;

-- 2022-01-19T14:14:00.815Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677882, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677123
;

-- 2022-01-19T14:14:00.816Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677883
;

-- 2022-01-19T14:14:00.816Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677883, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677124
;

-- 2022-01-19T14:14:00.816Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677884
;

-- 2022-01-19T14:14:00.817Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677884, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677125
;

-- 2022-01-19T14:14:00.817Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545336
;

-- 2022-01-19T14:14:00.818Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545336, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545290
;

-- 2022-01-19T14:14:00.821Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677885
;

-- 2022-01-19T14:14:00.821Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677885, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677126
;

-- 2022-01-19T14:14:00.821Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677886
;

-- 2022-01-19T14:14:00.821Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677886, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677127
;

-- 2022-01-19T14:14:00.822Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677887
;

-- 2022-01-19T14:14:00.822Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677887, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677128
;

-- 2022-01-19T14:14:00.822Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677888
;

-- 2022-01-19T14:14:00.823Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677888, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677129
;

-- 2022-01-19T14:14:00.823Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677889
;

-- 2022-01-19T14:14:00.823Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677889, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677130
;

-- 2022-01-19T14:14:00.824Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677890
;

-- 2022-01-19T14:14:00.824Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677890, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677131
;

-- 2022-01-19T14:14:00.824Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677891
;

-- 2022-01-19T14:14:00.825Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677891, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677132
;

-- 2022-01-19T14:14:00.828Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545337
;

-- 2022-01-19T14:14:00.828Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545337, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545291
;

-- 2022-01-19T14:14:00.831Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677892
;

-- 2022-01-19T14:14:00.832Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677892, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677133
;

-- 2022-01-19T14:14:00.832Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677893
;

-- 2022-01-19T14:14:00.832Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677893, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677134
;

-- 2022-01-19T14:14:00.833Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677894
;

-- 2022-01-19T14:14:00.833Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677894, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677135
;

-- 2022-01-19T14:14:00.833Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677895
;

-- 2022-01-19T14:14:00.833Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677895, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677136
;

-- 2022-01-19T14:14:00.834Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677896
;

-- 2022-01-19T14:14:00.834Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677896, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677137
;

-- 2022-01-19T14:14:00.834Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677897
;

-- 2022-01-19T14:14:00.835Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677897, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677138
;

-- 2022-01-19T14:14:00.835Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 545338
;

-- 2022-01-19T14:14:00.835Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 545338, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 545292
;

-- 2022-01-19T14:14:00.838Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677898
;

-- 2022-01-19T14:14:00.838Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677898, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677139
;

-- 2022-01-19T14:14:00.839Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677899
;

-- 2022-01-19T14:14:00.839Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677899, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677140
;

-- 2022-01-19T14:14:00.839Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677900
;

-- 2022-01-19T14:14:00.840Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677900, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677141
;

-- 2022-01-19T14:14:00.840Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677901
;

-- 2022-01-19T14:14:00.840Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677901, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677142
;

-- 2022-01-19T14:14:00.841Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677902
;

-- 2022-01-19T14:14:00.841Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677902, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677143
;

-- 2022-01-19T14:14:00.841Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 677903
;

-- 2022-01-19T14:14:00.842Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 677903, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 677144
;

-- 2022-01-19T14:14:00.844Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544270
;

-- 2022-01-19T14:14:00.845Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544270, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544224
;

-- 2022-01-19T14:14:01.398Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544271
;

-- 2022-01-19T14:14:01.399Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544271, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544225
;

-- 2022-01-19T14:14:01.690Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544272
;

-- 2022-01-19T14:14:01.690Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544272, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544226
;

-- 2022-01-19T14:14:01.738Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544273
;

-- 2022-01-19T14:14:01.739Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544273, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544227
;

-- 2022-01-19T14:14:01.773Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544274
;

-- 2022-01-19T14:14:01.774Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544274, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544228
;

-- 2022-01-19T14:14:01.849Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544275
;

-- 2022-01-19T14:14:01.850Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544275, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544229
;

-- 2022-01-19T14:14:01.871Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544276
;

-- 2022-01-19T14:14:01.872Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544276, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544230
;

-- 2022-01-19T14:14:02.034Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544277
;

-- 2022-01-19T14:14:02.035Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544277, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544231
;

-- 2022-01-19T14:14:02.159Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544278
;

-- 2022-01-19T14:14:02.160Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544278, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544232
;

-- 2022-01-19T14:14:02.193Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544279
;

-- 2022-01-19T14:14:02.194Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544279, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544233
;

-- 2022-01-19T14:14:02.224Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544280
;

-- 2022-01-19T14:14:02.225Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544280, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544234
;

-- 2022-01-19T14:14:02.235Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544281
;

-- 2022-01-19T14:14:02.236Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544281, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544235
;

-- 2022-01-19T14:14:02.252Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544282
;

-- 2022-01-19T14:14:02.253Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544282, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544236
;

-- 2022-01-19T14:14:02.264Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544283
;

-- 2022-01-19T14:14:02.264Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544283, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544237
;

-- 2022-01-19T14:14:02.281Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544284
;

-- 2022-01-19T14:14:02.282Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544284, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544238
;

-- 2022-01-19T14:14:02.294Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544285
;

-- 2022-01-19T14:14:02.294Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544285, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544239
;

-- 2022-01-19T14:14:02.306Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544286
;

-- 2022-01-19T14:14:02.307Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544286, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544240
;

-- 2022-01-19T14:14:02.321Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544287
;

-- 2022-01-19T14:14:02.322Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544287, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544241
;

-- 2022-01-19T14:14:02.361Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544288
;

-- 2022-01-19T14:14:02.362Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544288, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544242
;

-- 2022-01-19T14:14:02.377Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544289
;

-- 2022-01-19T14:14:02.378Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544289, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544243
;

-- 2022-01-19T14:14:02.393Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544290
;

-- 2022-01-19T14:14:02.395Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544290, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544244
;

-- 2022-01-19T14:14:02.409Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544291
;

-- 2022-01-19T14:14:02.409Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544291, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544245
;

-- 2022-01-19T14:14:02.412Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544292
;

-- 2022-01-19T14:14:02.412Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544292, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544246
;

-- 2022-01-19T14:18:31.433Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=580497, Description='Verwalten von Produkten', Help='Das Fenster "Produkt" definiert alle Produkte, die von einer Organisation verwendet werden. Dies schliesst die ein, die an Kunden verkauft werden und solche, die von Lieferanten eingekauft werden.', Name='Produkt_old',Updated=TO_TIMESTAMP('2022-01-19 15:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=140
;

-- 2022-01-19T14:18:31.437Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Verwalten von Produkten', IsActive='Y', Name='Produkt_old',Updated=TO_TIMESTAMP('2022-01-19 15:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=126
;

-- 2022-01-19T14:18:31.443Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Verwalten von Produkten', IsActive='Y', Name='Produkt_old',Updated=TO_TIMESTAMP('2022-01-19 15:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000034
;

-- 2022-01-19T14:18:31.450Z
-- URL zum Konzept
UPDATE AD_WF_Node SET Description='Verwalten von Produkten', Help='Das Fenster "Produkt" definiert alle Produkte, die von einer Organisation verwendet werden. Dies schliesst die ein, die an Kunden verkauft werden und solche, die von Lieferanten eingekauft werden.', Name='Produkt_old',Updated=TO_TIMESTAMP('2022-01-19 15:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=161
;

-- 2022-01-19T14:18:31.452Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(580497) 
;

-- 2022-01-19T14:18:31.458Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=140
;

-- 2022-01-19T14:18:31.499Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(140)
;

-- 2022-01-19T14:22:35.904Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580504,0,TO_TIMESTAMP('2022-01-19 15:22:30','YYYY-MM-DD HH24:MI:SS'),100,'Produkt_switch','U','Produkt_switch','Y','Produkt_switch','Produkt_switch',TO_TIMESTAMP('2022-01-19 15:22:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:22:35.906Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580504 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-19T14:23:35.233Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=580504, Description='Produkt_switch', Help='Produkt_switch', Name='Produkt_switch', Overrides_Window_ID=NULL,Updated=TO_TIMESTAMP('2022-01-19 15:23:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541389
;

-- 2022-01-19T14:23:35.238Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(580504) 
;

-- 2022-01-19T14:23:35.241Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541389
;

-- 2022-01-19T14:23:35.244Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541389)
;

-- 2022-01-19T14:24:51.006Z
-- URL zum Konzept
UPDATE AD_Window SET Overrides_Window_ID=NULL,Updated=TO_TIMESTAMP('2022-01-19 15:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541397
;

-- 2022-01-19T14:26:10.937Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=580498, Description='Verwalten von Produkten', Help='Das Fenster "Produkt" definiert alle Produkte, die von einer Organisation verwendet werden. Dies schliesst die ein, die an Kunden verkauft werden und solche, die von Lieferanten eingekauft werden.', Name='Produkt_OLD',Updated=TO_TIMESTAMP('2022-01-19 15:26:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541389
;

-- 2022-01-19T14:26:10.940Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(580498) 
;

-- 2022-01-19T14:26:10.942Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541389
;

-- 2022-01-19T14:26:10.943Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541389)
;

-- 2022-01-19T14:29:47.192Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,580502,541894,0,541389,TO_TIMESTAMP('2022-01-19 15:29:42','YYYY-MM-DD HH24:MI:SS'),100,'Verwalten von Produkten','D','541389','Y','Y','N','N','N','Product_OLD2',TO_TIMESTAMP('2022-01-19 15:29:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:29:47.195Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541894 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-01-19T14:29:47.197Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541894, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541894)
;

-- 2022-01-19T14:29:47.206Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(580502) 
;

-- 2022-01-19T14:29:55.285Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=268 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.286Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=125 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.287Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=422 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.288Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=107 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.288Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=130 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.289Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=188 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.290Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=227 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.291Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=381 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.291Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=126 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.292Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=421 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.292Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=267 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.293Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=534 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.293Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=490 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.294Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=132 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.294Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=310 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.295Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53432 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.295Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=128 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.296Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=585 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.296Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53210 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.297Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=187 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.297Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53211 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.298Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540646 AND AD_Tree_ID=10
;

-- 2022-01-19T14:29:55.299Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.063Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.064Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=268 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.064Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=125 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.064Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=422 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.065Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=107 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.065Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=130 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.065Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=188 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.066Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=227 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.066Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=381 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.067Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=126 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.067Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=421 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.067Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=267 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.068Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=534 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.068Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=490 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.068Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=132 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.069Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=310 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.069Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53432 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.070Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=128 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.070Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=585 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.070Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53210 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.071Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=187 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.071Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53211 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:39.071Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540646 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.455Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.456Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.456Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.457Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.458Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.458Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.459Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.459Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.460Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.460Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.461Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.461Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.462Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.462Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.463Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.463Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.464Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.464Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.465Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.465Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.466Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.466Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.467Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.467Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.468Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.468Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.469Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.469Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.470Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.470Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.471Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.471Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.472Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.472Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.473Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.473Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.474Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.474Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.475Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.475Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.476Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.476Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.477Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.478Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.478Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.479Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.479Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.480Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.481Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.481Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.482Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.482Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.483Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.483Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.484Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.484Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.485Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.485Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.486Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.486Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.486Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.487Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.487Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.488Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.488Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.489Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.489Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.490Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.490Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.491Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.491Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.492Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.492Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.493Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:47.493Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.981Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.981Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.982Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.982Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.982Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.983Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.983Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.983Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.983Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.984Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.984Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.984Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.984Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.985Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.985Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.985Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.985Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.986Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.986Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.986Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.986Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.987Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.987Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.987Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.988Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.988Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.988Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.988Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.989Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.989Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.989Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.990Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.990Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.990Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.991Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.991Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.991Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.991Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.992Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.992Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.992Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.992Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.993Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.993Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.993Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.993Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.993Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.994Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.994Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.994Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.994Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.995Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.995Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.995Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.995Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.996Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.996Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.996Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.996Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.997Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.997Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.997Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.997Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.998Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.998Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.998Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.999Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.999Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:53.999Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:54Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:54Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:54Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:54.001Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:54.001Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- 2022-01-19T14:32:54.001Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.634Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.635Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.635Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.637Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.638Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.638Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.639Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.640Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.641Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.641Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.642Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.642Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.643Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.644Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.644Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.645Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.645Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.646Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.647Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.647Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.648Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.649Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.649Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.650Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.651Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.651Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.652Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.652Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.653Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.654Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.654Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.655Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.655Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.656Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.656Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.657Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.657Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.658Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.658Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.659Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.659Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.660Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.660Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.661Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.662Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.662Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.663Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.664Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.664Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.665Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.665Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.666Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.666Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.667Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.668Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.668Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.669Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.670Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.670Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.671Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.671Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.672Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.673Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.673Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.674Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.674Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.675Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.676Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.676Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.677Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.677Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.678Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.678Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:00.679Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.372Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.373Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.374Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.374Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.375Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.376Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.379Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.380Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.381Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.381Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.382Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.383Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.384Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.384Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.385Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.386Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.386Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.386Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.387Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.387Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.388Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.388Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.389Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.389Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.390Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.390Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.391Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.391Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.392Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.392Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.392Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.393Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.393Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.394Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.394Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.396Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.396Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.397Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.397Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.398Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.398Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.398Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.399Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.399Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.400Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.400Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.401Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.401Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.402Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.402Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.402Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.403Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.403Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.404Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.404Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.405Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.405Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.406Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.406Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.406Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.407Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.407Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.408Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.408Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.409Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.409Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.410Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.410Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.410Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.411Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.411Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:20.412Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.523Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.524Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541392 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.525Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.525Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.526Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.527Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.527Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541409 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.528Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.529Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.529Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.530Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541571 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:28.531Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541609 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.016Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541735 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.018Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.018Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.019Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.020Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.020Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.021Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.021Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.022Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.023Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.023Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.024Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.025Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.025Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.026Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.026Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.027Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.027Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.028Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:36.028Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.267Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541735 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.268Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.268Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.269Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541894 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.269Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.269Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.270Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.270Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.270Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.271Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.271Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.271Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.272Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.272Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.272Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.273Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.273Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.273Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.273Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2022-01-19T14:33:49.274Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2022-01-19T14:36:54.056Z
-- URL zum Konzept
UPDATE AD_Window SET IsOverrideInMenu='Y', Overrides_Window_ID=140,Updated=TO_TIMESTAMP('2022-01-19 15:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541397
;

-- 2022-01-19T14:38:51.432Z
-- URL zum Konzept
UPDATE AD_Menu SET AD_Window_ID=541397, InternalName='P02',Updated=TO_TIMESTAMP('2022-01-19 15:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541894
;

-- 2022-01-19T14:47:59.213Z
-- URL zum Konzept
UPDATE AD_Menu SET AD_Element_ID=580497, AD_Window_ID=140, Description='Verwalten von Produkten', InternalName='M_Product', Name='Produkt_old', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2022-01-19 15:47:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541894
;

-- 2022-01-19T14:47:59.214Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(580497) 
;

-- 2022-01-19T14:48:56.725Z
-- URL zum Konzept
UPDATE AD_Menu SET AD_Element_ID=580502, Description='Verwalten von Produkten', Name='Product_OLD2', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2022-01-19 15:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541894
;

-- 2022-01-19T14:48:56.726Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(580502) 
;

-- 2022-01-19T14:49:47.938Z
-- URL zum Konzept
UPDATE AD_Menu SET AD_Window_ID=541389, InternalName='541389',Updated=TO_TIMESTAMP('2022-01-19 15:49:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541894
;

-- 2022-01-19T14:53:47.493Z
-- URL zum Konzept
UPDATE AD_Window SET Overrides_Window_ID=NULL,Updated=TO_TIMESTAMP('2022-01-19 15:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541397
;

-- 2022-01-19T14:54:52.303Z
-- URL zum Konzept
UPDATE AD_Menu SET AD_Window_ID=541397, InternalName='PO2',Updated=TO_TIMESTAMP('2022-01-19 15:54:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541894
;

