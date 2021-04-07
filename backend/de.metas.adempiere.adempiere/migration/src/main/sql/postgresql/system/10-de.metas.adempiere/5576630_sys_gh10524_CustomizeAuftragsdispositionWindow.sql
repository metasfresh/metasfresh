-- 2021-01-12T14:27:46.794Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-01-12 16:27:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548695
;

-- 2021-01-12T14:35:04.400Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-01-12 16:35:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547259
;

-- 2021-01-12T14:37:12.283Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-01-12 16:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547261
;

-- 2021-01-12T15:07:31.142Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2021-01-12 17:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547215
;

-- 2021-01-12T15:07:31.507Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2021-01-12 17:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547261
;

-- 2021-01-12T15:07:31.696Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2021-01-12 17:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547260
;

-- 2021-01-12T15:07:31.908Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2021-01-12 17:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547259
;

-- 2021-01-12T15:07:32.099Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2021-01-12 17:07:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548695
;

-- 2021-01-12T15:09:50.196Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554080,0,540282,540962,576297,'F',TO_TIMESTAMP('2021-01-12 17:09:49','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Y','N','N','Y','N','N','N',0,'Zugesagter Termin eff.',640,0,0,TO_TIMESTAMP('2021-01-12 17:09:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-12T15:10:22.379Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-01-12 17:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547215
;

-- 2021-01-12T15:10:22.606Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2021-01-12 17:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576297
;

-- 2021-01-14T07:50:11.478Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578639,0,TO_TIMESTAMP('2021-01-14 09:50:10','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','de.metas.ordercandidate','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','Verarb.','Verarb.',TO_TIMESTAMP('2021-01-14 09:50:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-14T07:50:12.094Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578639 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-01-14T07:52:47.666Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='The document has been processed', Help='The Processed checkbox indicates that a document has been processed.', IsTranslated='Y', Name='Processed', PrintName='Processed',Updated=TO_TIMESTAMP('2021-01-14 09:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578639 AND AD_Language='en_US'
;

-- 2021-01-14T07:52:47.750Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578639,'en_US')
;

-- 2021-01-14T07:55:31.644Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2021-01-14 09:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547094
;

-- 2021-01-14T07:57:31.095Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=578639, Description='Checkbox sagt aus, ob der Beleg verarbeitet wurde. ', Help='Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.', IsDisplayedGrid='Y', Name='Verarb.',Updated=TO_TIMESTAMP('2021-01-14 09:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547094
;

-- 2021-01-14T07:57:31.208Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578639)
;

-- 2021-01-14T07:57:31.267Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=547094
;

-- 2021-01-14T07:57:31.309Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(547094)
;

-- 2021-01-14T08:29:00.835Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578640,0,TO_TIMESTAMP('2021-01-14 10:29:00','YYYY-MM-DD HH24:MI:SS'),100,'Preisdifferenz (imp. - int.)','de.metas.ordercandidate','Preisdifferenz (imp. - int.)','Y','Preisdiff.','Preisdiff.',TO_TIMESTAMP('2021-01-14 10:29:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-14T08:29:01.083Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578640 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-01-14T08:30:10.621Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Price diff.', PrintName='Price diff.',Updated=TO_TIMESTAMP('2021-01-14 10:30:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578640 AND AD_Language='en_US'
;

-- 2021-01-14T08:30:10.658Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578640,'en_US')
;

-- 2021-01-14T08:31:14.925Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=578640, Description='Preisdifferenz (imp. - int.)', Help='Preisdifferenz (imp. - int.)', Name='Preisdiff.',Updated=TO_TIMESTAMP('2021-01-14 10:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555186
;

-- 2021-01-14T08:31:15.005Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578640)
;

-- 2021-01-14T08:31:15.048Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=555186
;

-- 2021-01-14T08:31:15.087Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(555186)
;

-- 2021-01-14T08:37:24.769Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578641,0,TO_TIMESTAMP('2021-01-14 10:37:24','YYYY-MM-DD HH24:MI:SS'),100,'Freigabe erforderlich','org.adempiere.process.rpl','Freigabe erforderlich','Y','Freig.','Freig.',TO_TIMESTAMP('2021-01-14 10:37:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-14T08:37:24.850Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578641 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-01-14T08:38:31.610Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Approval needed', Help='Approval needed', IsTranslated='Y', Name='Approval needed', PrintName='Approval needed',Updated=TO_TIMESTAMP('2021-01-14 10:38:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578641 AND AD_Language='en_US'
;

-- 2021-01-14T08:38:31.651Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578641,'en_US')
;

-- 2021-01-14T08:39:07.268Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=578641, Description='Freigabe erforderlich', Help='Freigabe erforderlich', Name='Freig.',Updated=TO_TIMESTAMP('2021-01-14 10:39:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554017
;

-- 2021-01-14T08:39:07.540Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578641)
;

-- 2021-01-14T08:39:07.582Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554017
;

-- 2021-01-14T08:39:07.624Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(554017)
;
-- 2021-01-15T12:23:29.080Z
-- URL zum Konzept
UPDATE AD_Element SET Name='Zuges. Termin (eff.)',Updated=TO_TIMESTAMP('2021-01-15 14:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542422
;

-- 2021-01-15T12:23:29.630Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DatePromised_Effective', Name='Zuges. Termin (eff.)', Description='Zugesagter Termin für diesen Auftrag', Help=NULL WHERE AD_Element_ID=542422
;

-- 2021-01-15T12:23:29.669Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DatePromised_Effective', Name='Zuges. Termin (eff.)', Description='Zugesagter Termin für diesen Auftrag', Help=NULL, AD_Element_ID=542422 WHERE UPPER(ColumnName)='DATEPROMISED_EFFECTIVE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-01-15T12:23:29.708Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DatePromised_Effective', Name='Zuges. Termin (eff.)', Description='Zugesagter Termin für diesen Auftrag', Help=NULL WHERE AD_Element_ID=542422 AND IsCentrallyMaintained='Y'
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2021-01-15T12:23:29.748Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zuges. Termin (eff.)', Description='Zugesagter Termin für diesen Auftrag', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542422) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542422)
;

-- 2021-01-15T12:23:29.812Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zugesagter Termin eff.', Name='Zuges. Termin (eff.)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542422)
;

-- 2021-01-15T12:23:29.853Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zuges. Termin (eff.)', Description='Zugesagter Termin für diesen Auftrag', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542422
;

-- 2021-01-15T12:23:29.903Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zuges. Termin (eff.)', Description='Zugesagter Termin für diesen Auftrag', Help=NULL WHERE AD_Element_ID = 542422
;

-- 2021-01-15T12:23:29.941Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zuges. Termin (eff.)', Description = 'Zugesagter Termin für diesen Auftrag', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542422
;