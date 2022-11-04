-- 2022-09-15T07:32:52.119Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581459,0,TO_TIMESTAMP('2022-09-15 10:32:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Effort control','Effost control',TO_TIMESTAMP('2022-09-15 10:32:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T07:32:52.132Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581459 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Effort control, InternalName=Effort control
-- 2022-09-15T07:33:16.124Z
UPDATE AD_Window SET AD_Element_ID=581459, Description=NULL, Help=NULL, Name='Effort control',Updated=TO_TIMESTAMP('2022-09-15 10:33:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541615
;

-- Name: Effort control
-- Action Type: W
-- Window: Effort control(541615,de.metas.serviceprovider)
-- 2022-09-15T07:33:16.141Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Effort control',Updated=TO_TIMESTAMP('2022-09-15 10:33:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542008
;

-- 2022-09-15T07:33:16.183Z
/* DDL */  select update_window_translation_from_ad_element(581459) 
;

-- 2022-09-15T07:33:16.196Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541615
;

-- 2022-09-15T07:33:16.207Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541615)
;

-- Name: Effort control
-- Action Type: W
-- Window: Effort control(541615,de.metas.serviceprovider)
-- 2022-09-15T07:34:31.757Z
UPDATE AD_Menu SET AD_Element_ID=581459, Description=NULL, Name='Effort control', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2022-09-15 10:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542008
;

-- 2022-09-15T07:34:31.765Z
/* DDL */  select update_menu_translation_from_ad_element(581459) 
;

-- Column: S_EffortControl.C_Activity_ID
-- 2022-09-15T07:36:54.438Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2022-09-15 10:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584349
;

/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- Column: S_EffortControl.C_Project_ID
-- 2022-09-15T07:36:54.879Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2022-09-15 10:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584350
;

-- Column: S_EffortControl.Updated
-- 2022-09-15T07:36:55.301Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2022-09-15 10:36:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584338
;

-- Column: S_EffortControl.IsIssueClosed
-- 2022-09-15T07:36:55.682Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2022-09-15 10:36:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584346
;

-- Column: S_EffortControl.IsOverBuget
-- 2022-09-15T07:36:56.062Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2022-09-15 10:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584345
;

-- Column: S_EffortControl.IsProcessed
-- 2022-09-15T07:36:56.436Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2022-09-15 10:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584347
;

-- Column: S_EffortControl.Budget
-- 2022-09-15T07:36:56.794Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2022-09-15 10:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584343
;

