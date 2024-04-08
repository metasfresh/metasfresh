-- Column: C_Project_TimeBooking.AD_Client_ID
-- 2024-04-08T05:29:33.390Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588153,102,0,19,542407,'AD_Client_ID',TO_TIMESTAMP('2024-04-08 08:29:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-04-08 08:29:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:33.392Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588153 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:33.396Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Project_TimeBooking.AD_Org_ID
-- 2024-04-08T05:29:33.848Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588154,113,0,30,542407,'AD_Org_ID',TO_TIMESTAMP('2024-04-08 08:29:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',40,0,TO_TIMESTAMP('2024-04-08 08:29:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:33.850Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588154 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:33.852Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Project_TimeBooking.AD_User_Performing_ID
-- 2024-04-08T05:29:34.298Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588155,577384,0,30,541129,542407,'AD_User_Performing_ID',TO_TIMESTAMP('2024-04-08 08:29:34','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','Erbringende Person',10,20,TO_TIMESTAMP('2024-04-08 08:29:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:34.299Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588155 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:34.301Z
/* DDL */  select update_Column_Translation_From_AD_Element(577384) 
;

-- Column: C_Project_TimeBooking.BookedDate
-- 2024-04-08T05:29:34.606Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588156,577651,0,15,542407,'BookedDate',TO_TIMESTAMP('2024-04-08 08:29:34','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,20,'B','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','Y','Booked date',30,0,TO_TIMESTAMP('2024-04-08 08:29:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:34.607Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588156 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:34.610Z
/* DDL */  select update_Column_Translation_From_AD_Element(577651) 
;

-- Column: C_Project_TimeBooking.BookedSeconds
-- 2024-04-08T05:29:34.941Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588157,577650,0,22,542407,'BookedSeconds',TO_TIMESTAMP('2024-04-08 08:29:34','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Booked seconds',0,0,TO_TIMESTAMP('2024-04-08 08:29:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:34.943Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588157 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:34.946Z
/* DDL */  select update_Column_Translation_From_AD_Element(577650) 
;

-- Column: C_Project_TimeBooking.Comments
-- 2024-04-08T05:29:35.249Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588158,230,0,14,542407,'Comments',TO_TIMESTAMP('2024-04-08 08:29:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Kommentar oder zusätzliche Information','D',0,4096,'"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Bemerkungen',0,0,TO_TIMESTAMP('2024-04-08 08:29:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:35.251Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588158 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:35.253Z
/* DDL */  select update_Column_Translation_From_AD_Element(230) 
;

-- Column: C_Project_TimeBooking.Created
-- 2024-04-08T05:29:35.557Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588159,245,0,16,542407,'Created',TO_TIMESTAMP('2024-04-08 08:29:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-04-08 08:29:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:35.559Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588159 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:35.562Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Project_TimeBooking.CreatedBy
-- 2024-04-08T05:29:35.955Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588160,246,0,18,110,542407,'CreatedBy',TO_TIMESTAMP('2024-04-08 08:29:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-04-08 08:29:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:35.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588160 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:35.959Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Project_TimeBooking.HoursAndMinutes
-- 2024-04-08T05:29:36.355Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588161,577665,0,10,542407,'HoursAndMinutes',TO_TIMESTAMP('2024-04-08 08:29:36','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,20,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Time (H:mm)',0,0,TO_TIMESTAMP('2024-04-08 08:29:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:36.357Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588161 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:36.359Z
/* DDL */  select update_Column_Translation_From_AD_Element(577665) 
;

-- Column: C_Project_TimeBooking.IsActive
-- 2024-04-08T05:29:36.673Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588162,348,0,20,542407,'IsActive',TO_TIMESTAMP('2024-04-08 08:29:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-04-08 08:29:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:36.674Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588162 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:36.677Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Project_TimeBooking.S_Issue_ID
-- 2024-04-08T05:29:37.128Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588163,577622,0,30,541122,542407,'S_Issue_ID',TO_TIMESTAMP('2024-04-08 08:29:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','Y','Issue',20,0,TO_TIMESTAMP('2024-04-08 08:29:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:37.129Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588163 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:37.132Z
/* DDL */  select update_Column_Translation_From_AD_Element(577622) 
;

-- 2024-04-08T05:29:37.437Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583074,0,'C_Project_TimeBooking_ID',TO_TIMESTAMP('2024-04-08 08:29:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Project Time','Project Time',TO_TIMESTAMP('2024-04-08 08:29:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:29:37.440Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583074 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_TimeBooking.C_Project_TimeBooking_ID
-- 2024-04-08T05:29:37.729Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588164,583074,0,13,542407,'C_Project_TimeBooking_ID',TO_TIMESTAMP('2024-04-08 08:29:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Project Time',0,0,TO_TIMESTAMP('2024-04-08 08:29:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:37.731Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588164 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:37.734Z
/* DDL */  select update_Column_Translation_From_AD_Element(583074) 
;

-- Column: C_Project_TimeBooking.Updated
-- 2024-04-08T05:29:38.037Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588165,607,0,16,542407,'Updated',TO_TIMESTAMP('2024-04-08 08:29:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-04-08 08:29:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:38.039Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588165 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:38.041Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Project_TimeBooking.UpdatedBy
-- 2024-04-08T05:29:38.461Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588166,608,0,18,110,542407,'UpdatedBy',TO_TIMESTAMP('2024-04-08 08:29:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-04-08 08:29:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:29:38.463Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588166 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:29:38.465Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: C_Project_TimeBooking.S_Issue_ID
-- 2024-04-08T05:29:53.914Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588163
;

-- 2024-04-08T05:29:53.919Z
DELETE FROM AD_Column WHERE AD_Column_ID=588163
;

-- Column: C_Project_TimeBooking.AD_User_Performing_ID
-- 2024-04-08T05:30:23.309Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588155
;

-- 2024-04-08T05:30:23.316Z
DELETE FROM AD_Column WHERE AD_Column_ID=588155
;

-- Column: C_Project_TimeBooking.C_Project_ID
-- 2024-04-08T05:31:02.648Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588167,208,0,30,541136,542407,'C_Project_ID',TO_TIMESTAMP('2024-04-08 08:31:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Projekt',0,10,TO_TIMESTAMP('2024-04-08 08:31:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:31:02.650Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588167 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:31:02.654Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- Column: C_Project_TimeBooking.CreatedBy
-- 2024-04-08T05:31:21.331Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-04-08 08:31:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588160
;

-- Column: C_Project_TimeBooking.HoursAndMinutes
-- 2024-04-08T05:31:32.162Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2024-04-08 08:31:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588161
;

-- Column: C_Project_TimeBooking.IsInvoiced
-- 2024-04-08T05:33:04.430Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588168,387,0,20,542407,'IsInvoiced',TO_TIMESTAMP('2024-04-08 08:33:04','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Fakturiert?','D',0,1,'If selected, invoices are created','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Fakturiert',0,0,TO_TIMESTAMP('2024-04-08 08:33:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-04-08T05:33:04.432Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588168 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T05:33:04.436Z
/* DDL */  select update_Column_Translation_From_AD_Element(387) 
;

-- 2024-04-08T05:33:18.977Z
/* DDL */ CREATE TABLE public.C_Project_TimeBooking (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, BookedDate TIMESTAMP WITHOUT TIME ZONE NOT NULL, BookedSeconds NUMERIC, Comments TEXT, C_Project_ID NUMERIC(10) NOT NULL, C_Project_TimeBooking_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, HoursAndMinutes VARCHAR(20) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsInvoiced CHAR(1) DEFAULT 'N' CHECK (IsInvoiced IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CProject_CProjectTimeBooking FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Project_TimeBooking_Key PRIMARY KEY (C_Project_TimeBooking_ID))
;

-- 2024-04-08T05:39:05.014Z
INSERT INTO t_alter_column values('c_project_timebooking','IsInvoiced','CHAR(1)',null,'N')
;

-- 2024-04-08T05:39:05.028Z
UPDATE C_Project_TimeBooking SET IsInvoiced='N' WHERE IsInvoiced IS NULL
;

-- Window: Project Time, InternalName=null
-- 2024-04-08T05:40:18.886Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583074,0,541796,TO_TIMESTAMP('2024-04-08 08:40:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Project Time','N',TO_TIMESTAMP('2024-04-08 08:40:18','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-04-08T05:40:18.887Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541796 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-04-08T05:40:18.891Z
/* DDL */  select update_window_translation_from_ad_element(583074) 
;

-- 2024-04-08T05:40:18.894Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541796
;

-- 2024-04-08T05:40:18.895Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541796)
;

-- Tab: Project Time -> Project Time
-- Table: C_Project_TimeBooking
-- 2024-04-08T05:42:42.086Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583074,0,547529,542407,541796,'Y',TO_TIMESTAMP('2024-04-08 08:42:41','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_TimeBooking','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Project Time','N',10,0,TO_TIMESTAMP('2024-04-08 08:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:42.088Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547529 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-04-08T05:42:42.090Z
/* DDL */  select update_tab_translation_from_ad_element(583074) 
;

-- 2024-04-08T05:42:42.094Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547529)
;

-- Field: Project Time -> Project Time -> Mandant
-- Column: C_Project_TimeBooking.AD_Client_ID
-- 2024-04-08T05:42:49.460Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588153,727790,0,547529,TO_TIMESTAMP('2024-04-08 08:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-04-08 08:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:49.463Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727790 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:49.465Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-04-08T05:42:49.944Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727790
;

-- 2024-04-08T05:42:49.947Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727790)
;

-- Field: Project Time -> Project Time -> Sektion
-- Column: C_Project_TimeBooking.AD_Org_ID
-- 2024-04-08T05:42:50.122Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588154,727791,0,547529,TO_TIMESTAMP('2024-04-08 08:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-04-08 08:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:50.123Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727791 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:50.124Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-04-08T05:42:50.317Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727791
;

-- 2024-04-08T05:42:50.319Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727791)
;

-- Field: Project Time -> Project Time -> Booked date
-- Column: C_Project_TimeBooking.BookedDate
-- 2024-04-08T05:42:50.430Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588156,727792,0,547529,TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100,20,'D','Y','N','N','N','N','N','N','N','Booked date',TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:50.431Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:50.432Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577651) 
;

-- 2024-04-08T05:42:50.434Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727792
;

-- 2024-04-08T05:42:50.435Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727792)
;

-- Field: Project Time -> Project Time -> Booked seconds
-- Column: C_Project_TimeBooking.BookedSeconds
-- 2024-04-08T05:42:50.522Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588157,727793,0,547529,TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Booked seconds',TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:50.524Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:50.525Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577650) 
;

-- 2024-04-08T05:42:50.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727793
;

-- 2024-04-08T05:42:50.528Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727793)
;

-- Field: Project Time -> Project Time -> Bemerkungen
-- Column: C_Project_TimeBooking.Comments
-- 2024-04-08T05:42:50.617Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588158,727794,0,547529,TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100,'Kommentar oder zusätzliche Information',4096,'D','"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.','Y','N','N','N','N','N','N','N','Bemerkungen',TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:50.619Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:50.620Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(230) 
;

-- 2024-04-08T05:42:50.624Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727794
;

-- 2024-04-08T05:42:50.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727794)
;

-- Field: Project Time -> Project Time -> Time (H:mm)
-- Column: C_Project_TimeBooking.HoursAndMinutes
-- 2024-04-08T05:42:50.717Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588161,727795,0,547529,TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100,20,'D','Y','N','N','N','N','N','N','N','Time (H:mm)',TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:50.718Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:50.720Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577665) 
;

-- 2024-04-08T05:42:50.724Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727795
;

-- 2024-04-08T05:42:50.725Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727795)
;

-- Field: Project Time -> Project Time -> Aktiv
-- Column: C_Project_TimeBooking.IsActive
-- 2024-04-08T05:42:50.810Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588162,727796,0,547529,TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-04-08 08:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:50.811Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:50.813Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-04-08T05:42:51.088Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727796
;

-- 2024-04-08T05:42:51.089Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727796)
;

-- Field: Project Time -> Project Time -> Project Time
-- Column: C_Project_TimeBooking.C_Project_TimeBooking_ID
-- 2024-04-08T05:42:51.218Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588164,727797,0,547529,TO_TIMESTAMP('2024-04-08 08:42:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Time',TO_TIMESTAMP('2024-04-08 08:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:51.219Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:51.220Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583074) 
;

-- 2024-04-08T05:42:51.223Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727797
;

-- 2024-04-08T05:42:51.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727797)
;

-- Field: Project Time -> Project Time -> Projekt
-- Column: C_Project_TimeBooking.C_Project_ID
-- 2024-04-08T05:42:51.323Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588167,727798,0,547529,TO_TIMESTAMP('2024-04-08 08:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2024-04-08 08:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:51.324Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:51.326Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2024-04-08T05:42:51.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727798
;

-- 2024-04-08T05:42:51.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727798)
;

-- Field: Project Time -> Project Time -> Fakturiert
-- Column: C_Project_TimeBooking.IsInvoiced
-- 2024-04-08T05:42:51.438Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588168,727799,0,547529,TO_TIMESTAMP('2024-04-08 08:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Fakturiert?',1,'D','If selected, invoices are created','Y','N','N','N','N','N','N','N','Fakturiert',TO_TIMESTAMP('2024-04-08 08:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:42:51.439Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:42:51.441Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(387) 
;

-- 2024-04-08T05:42:51.447Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727799
;

-- 2024-04-08T05:42:51.447Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727799)
;

-- 2024-04-08T05:43:15.605Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547529,546110,TO_TIMESTAMP('2024-04-08 08:43:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-04-08 08:43:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-04-08T05:43:15.607Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546110 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2024-04-08T05:43:15.723Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547462,546110,TO_TIMESTAMP('2024-04-08 08:43:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-04-08 08:43:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:43:15.804Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547463,546110,TO_TIMESTAMP('2024-04-08 08:43:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-04-08 08:43:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:43:15.919Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547462,551790,TO_TIMESTAMP('2024-04-08 08:43:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-04-08 08:43:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Projekt
-- Column: C_Project_TimeBooking.C_Project_ID
-- 2024-04-08T05:44:09.765Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727798,0,547529,551790,624351,'F',TO_TIMESTAMP('2024-04-08 08:44:09','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',10,0,0,TO_TIMESTAMP('2024-04-08 08:44:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Time (H:mm)
-- Column: C_Project_TimeBooking.HoursAndMinutes
-- 2024-04-08T05:44:55.100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727795,0,547529,551790,624352,'F',TO_TIMESTAMP('2024-04-08 08:44:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Time (H:mm)',20,0,0,TO_TIMESTAMP('2024-04-08 08:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Project Time
-- Column: C_Project_TimeBooking.C_Project_TimeBooking_ID
-- 2024-04-08T05:45:05.181Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727797,0,547529,551790,624353,'F',TO_TIMESTAMP('2024-04-08 08:45:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Project Time',30,0,0,TO_TIMESTAMP('2024-04-08 08:45:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Project Time
-- Column: C_Project_TimeBooking.C_Project_TimeBooking_ID
-- 2024-04-08T05:45:10.677Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2024-04-08 08:45:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624353
;

-- Field: Project Time -> Project Time -> Erstellt durch
-- Column: C_Project_TimeBooking.CreatedBy
-- 2024-04-08T05:47:05.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588160,727800,0,547529,0,TO_TIMESTAMP('2024-04-08 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'','Nutzer, der diesen Eintrag erstellt hat',0,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','Y','N','Erstellt durch',0,10,0,1,1,TO_TIMESTAMP('2024-04-08 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:47:05.428Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T05:47:05.430Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2024-04-08T05:47:05.467Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727800
;

-- 2024-04-08T05:47:05.469Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727800)
;

-- Column: C_Project_TimeBooking.CreatedBy
-- 2024-04-08T05:47:13.938Z
UPDATE AD_Column SET DefaultValue='@AD_User_ID@',Updated=TO_TIMESTAMP('2024-04-08 08:47:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588160
;

-- Field: Project Time -> Project Time -> Erstellt durch
-- Column: C_Project_TimeBooking.CreatedBy
-- 2024-04-08T05:47:29.315Z
UPDATE AD_Field SET DefaultValue='@AD_User_ID@',Updated=TO_TIMESTAMP('2024-04-08 08:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=727800
;

-- UI Element: Project Time -> Project Time.Erstellt durch
-- Column: C_Project_TimeBooking.CreatedBy
-- 2024-04-08T05:48:00.296Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727800,0,547529,551790,624354,'F',TO_TIMESTAMP('2024-04-08 08:48:00','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','N','N',0,'Erstellt durch',30,0,0,TO_TIMESTAMP('2024-04-08 08:48:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:48:17.310Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547463,551791,TO_TIMESTAMP('2024-04-08 08:48:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-04-08 08:48:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Aktiv
-- Column: C_Project_TimeBooking.IsActive
-- 2024-04-08T05:48:25.978Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727796,0,547529,551791,624355,'F',TO_TIMESTAMP('2024-04-08 08:48:25','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-04-08 08:48:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Fakturiert
-- Column: C_Project_TimeBooking.IsInvoiced
-- 2024-04-08T05:48:32.402Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727799,0,547529,551791,624356,'F',TO_TIMESTAMP('2024-04-08 08:48:32','YYYY-MM-DD HH24:MI:SS'),100,'Fakturiert?','If selected, invoices are created','Y','N','N','Y','N','N','N',0,'Fakturiert',20,0,0,TO_TIMESTAMP('2024-04-08 08:48:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T05:48:42.802Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547463,551792,TO_TIMESTAMP('2024-04-08 08:48:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','org+client',20,TO_TIMESTAMP('2024-04-08 08:48:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Sektion
-- Column: C_Project_TimeBooking.AD_Org_ID
-- 2024-04-08T05:49:10.312Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727791,0,547529,551792,624357,'F',TO_TIMESTAMP('2024-04-08 08:49:10','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2024-04-08 08:49:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Mandant
-- Column: C_Project_TimeBooking.AD_Client_ID
-- 2024-04-08T05:49:17.609Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727790,0,547529,551792,624358,'F',TO_TIMESTAMP('2024-04-08 08:49:17','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-04-08 08:49:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Booked date
-- Column: C_Project_TimeBooking.BookedDate
-- 2024-04-08T05:50:26.887Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727792,0,547529,551790,624359,'F',TO_TIMESTAMP('2024-04-08 08:50:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Booked date',40,0,0,TO_TIMESTAMP('2024-04-08 08:50:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time -> Project Time.Booked date
-- Column: C_Project_TimeBooking.BookedDate
-- 2024-04-08T05:50:34.265Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2024-04-08 08:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624359
;

-- 2024-04-08T06:17:44.286Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583074,542148,0,541796,TO_TIMESTAMP('2024-04-08 09:17:44','YYYY-MM-DD HH24:MI:SS'),100,'D','C_Project_TimeBooking','Y','N','N','N','N','Project Time',TO_TIMESTAMP('2024-04-08 09:17:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:17:44.288Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542148 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-04-08T06:17:44.290Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542148, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542148)
;

-- 2024-04-08T06:17:44.298Z
/* DDL */  select update_menu_translation_from_ad_element(583074) 
;

-- Reordering children of `Project Management`
-- Node name: `Project Setup and Use`
-- 2024-04-08T06:17:52.448Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=219 AND AD_Tree_ID=10
;

-- Node name: `Project Type (C_ProjectType)`
-- 2024-04-08T06:17:52.450Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=364 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2024-04-08T06:17:52.450Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=116 AND AD_Tree_ID=10
;

-- Node name: `Project (Lines/Issues) (C_Project)`
-- 2024-04-08T06:17:52.451Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=387 AND AD_Tree_ID=10
;

-- Node name: `Generate PO from Project (de.metas.project.process.legacy.ProjectGenPO)`
-- 2024-04-08T06:17:52.452Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=402 AND AD_Tree_ID=10
;

-- Node name: `Issue to Project (de.metas.project.process.legacy.ProjectIssue)`
-- 2024-04-08T06:17:52.453Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=401 AND AD_Tree_ID=10
;

-- Node name: `Project Lines not Issued`
-- 2024-04-08T06:17:52.454Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=404 AND AD_Tree_ID=10
;

-- Node name: `Project POs not Issued`
-- 2024-04-08T06:17:52.455Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=405 AND AD_Tree_ID=10
;

-- Node name: `Project Margin (Work Order)`
-- 2024-04-08T06:17:52.455Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=410 AND AD_Tree_ID=10
;

-- Node name: `Project Reporting (C_Cycle)`
-- 2024-04-08T06:17:52.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=258 AND AD_Tree_ID=10
;

-- Node name: `Project Status Summary (org.compiere.report.ProjectStatus)`
-- 2024-04-08T06:17:52.457Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=260 AND AD_Tree_ID=10
;

-- Node name: `Project Cycle Report`
-- 2024-04-08T06:17:52.458Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=398 AND AD_Tree_ID=10
;

-- Node name: `Project Detail Accounting Report`
-- 2024-04-08T06:17:52.459Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=403 AND AD_Tree_ID=10
;

-- Node name: `Project Time`
-- 2024-04-08T06:17:52.460Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542148 AND AD_Tree_ID=10
;

-- Reordering children of `Project Management`
-- Node name: `Project Time`
-- 2024-04-08T06:19:22.675Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542148 AND AD_Tree_ID=10
;

-- Node name: `Project Type (C_ProjectType)`
-- 2024-04-08T06:19:22.676Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2024-04-08T06:19:22.677Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Project (C_Project)`
-- 2024-04-08T06:19:22.678Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541577 AND AD_Tree_ID=10
;

-- 2024-04-08T06:25:19.645Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545386,0,TO_TIMESTAMP('2024-04-08 09:25:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Incorrect format! Please enter a value in H:mm format.
( e.g.
- 2:02 - two hours and two minutes,
- 100:02 - one hundred hours and two minutes,
- 2:00 - two hours,
- 0:02 - two minutes
)','E',TO_TIMESTAMP('2024-04-08 09:25:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.project.incorrectHmmFormat')
;

-- 2024-04-08T06:25:19.647Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545386 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Tab: Project Time -> Project Time
-- Table: C_Project_TimeBooking
-- 2024-04-08T06:32:11.040Z
UPDATE AD_Tab SET WhereClause='AD_User_ID = @AD_User_ID@',Updated=TO_TIMESTAMP('2024-04-08 09:32:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547529
;

-- Tab: Project Time -> Project Time
-- Table: C_Project_TimeBooking
-- 2024-04-08T06:33:39.032Z
UPDATE AD_Tab SET WhereClause='AD_User_ID = @AD_User_ID/#AD_User_ID@',Updated=TO_TIMESTAMP('2024-04-08 09:33:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547529
;

-- Tab: Project Time -> Project Time
-- Table: C_Project_TimeBooking
-- 2024-04-08T06:34:27.499Z
UPDATE AD_Tab SET WhereClause='AD_User_ID = @#AD_User_ID@',Updated=TO_TIMESTAMP('2024-04-08 09:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547529
;

-- Tab: Project Time -> Project Time
-- Table: C_Project_TimeBooking
-- 2024-04-08T06:35:14.650Z
UPDATE AD_Tab SET WhereClause='CreatedBy = @#AD_User_ID@',Updated=TO_TIMESTAMP('2024-04-08 09:35:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547529
;

-- UI Element: Project Time -> Project Time.Booked date
-- Column: C_Project_TimeBooking.BookedDate
-- 2024-04-08T06:36:56.339Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-04-08 09:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624359
;

-- UI Element: Project Time -> Project Time.Projekt
-- Column: C_Project_TimeBooking.C_Project_ID
-- 2024-04-08T06:36:56.347Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-04-08 09:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624351
;

-- UI Element: Project Time -> Project Time.Time (H:mm)
-- Column: C_Project_TimeBooking.HoursAndMinutes
-- 2024-04-08T06:36:56.354Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-04-08 09:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624352
;

-- UI Element: Project Time -> Project Time.Fakturiert
-- Column: C_Project_TimeBooking.IsInvoiced
-- 2024-04-08T06:36:56.359Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-04-08 09:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624356
;

-- UI Element: Project Time -> Project Time.Sektion
-- Column: C_Project_TimeBooking.AD_Org_ID
-- 2024-04-08T06:36:56.365Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-04-08 09:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624357
;

-- 2024-04-08T06:37:46.686Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583075,0,TO_TIMESTAMP('2024-04-08 09:37:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Project Time Admin','Project Time Admin',TO_TIMESTAMP('2024-04-08 09:37:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:37:46.689Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583075 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Project Time Admin, InternalName=null
-- 2024-04-08T06:38:00.151Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583075,0,541797,TO_TIMESTAMP('2024-04-08 09:38:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Project Time Admin','N',TO_TIMESTAMP('2024-04-08 09:38:00','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-04-08T06:38:00.152Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541797 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-04-08T06:38:00.155Z
/* DDL */  select update_window_translation_from_ad_element(583075) 
;

-- 2024-04-08T06:38:00.157Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541797
;

-- 2024-04-08T06:38:00.158Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541797)
;

-- 2024-04-08T06:39:21.387Z
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 541797
;

-- 2024-04-08T06:39:21.389Z
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541797, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 541796
;

-- Tab: Project Time Admin -> Project Time
-- Table: C_Project_TimeBooking
-- 2024-04-08T06:39:21.545Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,583074,0,547530,542407,541797,'Y',TO_TIMESTAMP('2024-04-08 09:39:21','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_TimeBooking','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Project Time','N',10,0,TO_TIMESTAMP('2024-04-08 09:39:21','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy = @#AD_User_ID@')
;

-- 2024-04-08T06:39:21.548Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547530 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-04-08T06:39:21.550Z
/* DDL */  select update_tab_translation_from_ad_element(583074) 
;

-- 2024-04-08T06:39:21.555Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547530)
;

-- 2024-04-08T06:39:21.562Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 547530
;

-- 2024-04-08T06:39:21.564Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 547530, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 547529
;

-- Field: Project Time Admin -> Project Time -> Mandant
-- Column: C_Project_TimeBooking.AD_Client_ID
-- 2024-04-08T06:39:21.687Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588153,727801,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2024-04-08 09:39:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:21.689Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:21.691Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-04-08T06:39:22.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727801
;

-- 2024-04-08T06:39:22.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727801)
;

-- 2024-04-08T06:39:22.078Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727801
;

-- 2024-04-08T06:39:22.080Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727801, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727790
;

-- Field: Project Time Admin -> Project Time -> Sektion
-- Column: C_Project_TimeBooking.AD_Org_ID
-- 2024-04-08T06:39:22.202Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588154,727802,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',20,1,1,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:22.205Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:22.208Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-04-08T06:39:22.355Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727802
;

-- 2024-04-08T06:39:22.358Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727802)
;

-- 2024-04-08T06:39:22.361Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727802
;

-- 2024-04-08T06:39:22.362Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727802, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727791
;

-- Field: Project Time Admin -> Project Time -> Booked date
-- Column: C_Project_TimeBooking.BookedDate
-- 2024-04-08T06:39:22.481Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588156,727803,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100,20,'D',0,'Y','N','N','N','N','N','N','N','Booked date',30,1,1,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:22.483Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:22.485Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577651) 
;

-- 2024-04-08T06:39:22.489Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727803
;

-- 2024-04-08T06:39:22.490Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727803)
;

-- 2024-04-08T06:39:22.494Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727803
;

-- 2024-04-08T06:39:22.495Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727803, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727792
;

-- Field: Project Time Admin -> Project Time -> Booked seconds
-- Column: C_Project_TimeBooking.BookedSeconds
-- 2024-04-08T06:39:22.600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588157,727804,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Booked seconds',40,1,1,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:22.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:22.604Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577650) 
;

-- 2024-04-08T06:39:22.609Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727804
;

-- 2024-04-08T06:39:22.610Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727804)
;

-- 2024-04-08T06:39:22.613Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727804
;

-- 2024-04-08T06:39:22.614Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727804, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727793
;

-- Field: Project Time Admin -> Project Time -> Bemerkungen
-- Column: C_Project_TimeBooking.Comments
-- 2024-04-08T06:39:22.719Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588158,727805,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100,'Kommentar oder zusätzliche Information',4096,'D','"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.',0,'Y','N','N','N','N','N','N','N','Bemerkungen',50,1,1,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:22.721Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:22.723Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(230) 
;

-- 2024-04-08T06:39:22.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727805
;

-- 2024-04-08T06:39:22.730Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727805)
;

-- 2024-04-08T06:39:22.734Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727805
;

-- 2024-04-08T06:39:22.735Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727805, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727794
;

-- Field: Project Time Admin -> Project Time -> Time (H:mm)
-- Column: C_Project_TimeBooking.HoursAndMinutes
-- 2024-04-08T06:39:22.860Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588161,727806,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100,20,'D',0,'Y','N','N','N','N','N','N','N','Time (H:mm)',60,1,1,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:22.862Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:22.864Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577665) 
;

-- 2024-04-08T06:39:22.868Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727806
;

-- 2024-04-08T06:39:22.869Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727806)
;

-- 2024-04-08T06:39:22.873Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727806
;

-- 2024-04-08T06:39:22.874Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727806, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727795
;

-- Field: Project Time Admin -> Project Time -> Aktiv
-- Column: C_Project_TimeBooking.IsActive
-- 2024-04-08T06:39:22.970Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588162,727807,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',70,1,1,TO_TIMESTAMP('2024-04-08 09:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:22.972Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:22.974Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-04-08T06:39:23.127Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727807
;

-- 2024-04-08T06:39:23.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727807)
;

-- 2024-04-08T06:39:23.131Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727807
;

-- 2024-04-08T06:39:23.132Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727807, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727796
;

-- Field: Project Time Admin -> Project Time -> Project Time
-- Column: C_Project_TimeBooking.C_Project_TimeBooking_ID
-- 2024-04-08T06:39:23.245Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588164,727808,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Project Time',80,1,1,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:23.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:23.248Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583074) 
;

-- 2024-04-08T06:39:23.251Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727808
;

-- 2024-04-08T06:39:23.253Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727808)
;

-- 2024-04-08T06:39:23.255Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727808
;

-- 2024-04-08T06:39:23.257Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727808, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727797
;

-- Field: Project Time Admin -> Project Time -> Projekt
-- Column: C_Project_TimeBooking.C_Project_ID
-- 2024-04-08T06:39:23.362Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588167,727809,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',90,1,1,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:23.364Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:23.367Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2024-04-08T06:39:23.398Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727809
;

-- 2024-04-08T06:39:23.401Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727809)
;

-- 2024-04-08T06:39:23.404Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727809
;

-- 2024-04-08T06:39:23.405Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727809, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727798
;

-- Field: Project Time Admin -> Project Time -> Fakturiert
-- Column: C_Project_TimeBooking.IsInvoiced
-- 2024-04-08T06:39:23.520Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588168,727810,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100,'Fakturiert?',1,'D','If selected, invoices are created',0,'Y','N','N','N','N','N','N','N','Fakturiert',100,1,1,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:23.522Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:23.523Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(387) 
;

-- 2024-04-08T06:39:23.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727810
;

-- 2024-04-08T06:39:23.530Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727810)
;

-- 2024-04-08T06:39:23.533Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727810
;

-- 2024-04-08T06:39:23.533Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727810, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727799
;

-- Field: Project Time Admin -> Project Time -> Erstellt durch
-- Column: C_Project_TimeBooking.CreatedBy
-- 2024-04-08T06:39:23.641Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588160,727811,0,547530,0,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100,'@AD_User_ID@','Nutzer, der diesen Eintrag erstellt hat',0,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','Y','N','Erstellt durch',110,10,0,1,1,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:23.643Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T06:39:23.644Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2024-04-08T06:39:23.684Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727811
;

-- 2024-04-08T06:39:23.686Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727811)
;

-- 2024-04-08T06:39:23.688Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 727811
;

-- 2024-04-08T06:39:23.690Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 727811, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 727800
;

-- 2024-04-08T06:39:23.794Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547530,546111,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-04-08T06:39:23.796Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546111 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2024-04-08T06:39:23.800Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546111
;

-- 2024-04-08T06:39:23.802Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 546111, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546110
;

-- 2024-04-08T06:39:23.915Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547464,546111,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:24.027Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547464,551793,TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-04-08 09:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time Admin -> Project Time.Project Time
-- Column: C_Project_TimeBooking.C_Project_TimeBooking_ID
-- 2024-04-08T06:39:24.152Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727808,0,547530,551793,624360,'F',TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Project Time',5,0,0,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time Admin -> Project Time.Projekt
-- Column: C_Project_TimeBooking.C_Project_ID
-- 2024-04-08T06:39:24.277Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727809,0,547530,551793,624361,'F',TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','Y','N','N',0,'Projekt',10,20,0,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time Admin -> Project Time.Booked date
-- Column: C_Project_TimeBooking.BookedDate
-- 2024-04-08T06:39:24.378Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727803,0,547530,551793,624362,'F',TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Booked date',15,10,0,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time Admin -> Project Time.Time (H:mm)
-- Column: C_Project_TimeBooking.HoursAndMinutes
-- 2024-04-08T06:39:24.485Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727806,0,547530,551793,624363,'F',TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Time (H:mm)',20,30,0,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time Admin -> Project Time.Erstellt durch
-- Column: C_Project_TimeBooking.CreatedBy
-- 2024-04-08T06:39:24.597Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727811,0,547530,551793,624364,'F',TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','N','N',0,'Erstellt durch',30,0,0,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:24.697Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547465,546111,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:24.793Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547465,551794,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time Admin -> Project Time.Aktiv
-- Column: C_Project_TimeBooking.IsActive
-- 2024-04-08T06:39:24.911Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727807,0,547530,551794,624365,'F',TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time Admin -> Project Time.Fakturiert
-- Column: C_Project_TimeBooking.IsInvoiced
-- 2024-04-08T06:39:25.014Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727810,0,547530,551794,624366,'F',TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Fakturiert?','If selected, invoices are created','Y','N','N','Y','Y','N','N',0,'Fakturiert',20,40,0,TO_TIMESTAMP('2024-04-08 09:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:39:25.126Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547465,551795,TO_TIMESTAMP('2024-04-08 09:39:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','org+client',20,TO_TIMESTAMP('2024-04-08 09:39:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time Admin -> Project Time.Sektion
-- Column: C_Project_TimeBooking.AD_Org_ID
-- 2024-04-08T06:39:25.238Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727802,0,547530,551795,624367,'F',TO_TIMESTAMP('2024-04-08 09:39:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N',0,'Sektion',10,50,0,TO_TIMESTAMP('2024-04-08 09:39:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Project Time Admin -> Project Time.Mandant
-- Column: C_Project_TimeBooking.AD_Client_ID
-- 2024-04-08T06:39:25.346Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727801,0,547530,551795,624368,'F',TO_TIMESTAMP('2024-04-08 09:39:25','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-04-08 09:39:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Project Time Admin -> Project Time
-- Table: C_Project_TimeBooking
-- 2024-04-08T06:39:35.176Z
UPDATE AD_Tab SET WhereClause='',Updated=TO_TIMESTAMP('2024-04-08 09:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547530
;

-- Tab: Project Time Admin -> Project Time Admin
-- Table: C_Project_TimeBooking
-- 2024-04-08T06:42:26.518Z
UPDATE AD_Tab SET AD_Element_ID=583075, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Project Time Admin',Updated=TO_TIMESTAMP('2024-04-08 09:42:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547530
;

-- 2024-04-08T06:42:26.519Z
/* DDL */  select update_tab_translation_from_ad_element(583075) 
;

-- 2024-04-08T06:42:26.523Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547530)
;

-- 2024-04-08T06:43:14.147Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583075,542149,0,541797,TO_TIMESTAMP('2024-04-08 09:43:14','YYYY-MM-DD HH24:MI:SS'),100,'D','C_Project_TimeBooking(Admin)','Y','N','N','N','N','Project Time Admin',TO_TIMESTAMP('2024-04-08 09:43:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-08T06:43:14.148Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542149 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-04-08T06:43:14.150Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542149, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542149)
;

-- 2024-04-08T06:43:14.151Z
/* DDL */  select update_menu_translation_from_ad_element(583075) 
;

-- Reordering children of `Project Management`
-- Node name: `Project Setup and Use`
-- 2024-04-08T06:43:22.311Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=219 AND AD_Tree_ID=10
;

-- Node name: `Project Type (C_ProjectType)`
-- 2024-04-08T06:43:22.312Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=364 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2024-04-08T06:43:22.313Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=116 AND AD_Tree_ID=10
;

-- Node name: `Project (Lines/Issues) (C_Project)`
-- 2024-04-08T06:43:22.315Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=387 AND AD_Tree_ID=10
;

-- Node name: `Generate PO from Project (de.metas.project.process.legacy.ProjectGenPO)`
-- 2024-04-08T06:43:22.315Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=402 AND AD_Tree_ID=10
;

-- Node name: `Issue to Project (de.metas.project.process.legacy.ProjectIssue)`
-- 2024-04-08T06:43:22.316Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=401 AND AD_Tree_ID=10
;

-- Node name: `Project Lines not Issued`
-- 2024-04-08T06:43:22.317Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=404 AND AD_Tree_ID=10
;

-- Node name: `Project POs not Issued`
-- 2024-04-08T06:43:22.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=405 AND AD_Tree_ID=10
;

-- Node name: `Project Margin (Work Order)`
-- 2024-04-08T06:43:22.319Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=410 AND AD_Tree_ID=10
;

-- Node name: `Project Reporting (C_Cycle)`
-- 2024-04-08T06:43:22.319Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=258 AND AD_Tree_ID=10
;

-- Node name: `Project Status Summary (org.compiere.report.ProjectStatus)`
-- 2024-04-08T06:43:22.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=260 AND AD_Tree_ID=10
;

-- Node name: `Project Cycle Report`
-- 2024-04-08T06:43:22.321Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=398 AND AD_Tree_ID=10
;

-- Node name: `Project Detail Accounting Report`
-- 2024-04-08T06:43:22.322Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=403 AND AD_Tree_ID=10
;

-- Node name: `Project Time Admin`
-- 2024-04-08T06:43:22.322Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542149 AND AD_Tree_ID=10
;

-- Reordering children of `Project Management`
-- Node name: `Project Time Admin`
-- 2024-04-08T06:44:06.166Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542149 AND AD_Tree_ID=10
;

-- Node name: `Project Time`
-- 2024-04-08T06:44:06.168Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542148 AND AD_Tree_ID=10
;

-- Node name: `Project Type (C_ProjectType)`
-- 2024-04-08T06:44:06.169Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2024-04-08T06:44:06.170Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Project (C_Project)`
-- 2024-04-08T06:44:06.171Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541577 AND AD_Tree_ID=10
;

