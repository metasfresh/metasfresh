-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('4',0,0,0,540750,'N',TO_TIMESTAMP('2016-03-04 12:57:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','N','Y','N','N','Y','N','N','N',0,'JAX-RS-Endpoint','L','AD_JAXRS_Endpoint',TO_TIMESTAMP('2016-03-04 12:57:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540750 AND NOT EXISTS (SELECT * FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;


-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554309,TO_TIMESTAMP('2016-03-04 12:57:33','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table AD_JAXRS_Endpoint',1,'Y','N','Y','Y','AD_JAXRS_Endpoint','N',1000000,TO_TIMESTAMP('2016-03-04 12:57:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554195,102,0,19,540750,'AD_Client_ID',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.jax.rs',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554195 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554196,113,0,19,540750,'AD_Org_ID',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.jax.rs',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','Y','N','N','Y','N','N','Sektion',0,TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554196 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554197,245,0,16,540750,'Created',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.jax.rs',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554197 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554198,246,0,18,110,540750,'CreatedBy',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.jax.rs',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554198 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554199,348,0,20,540750,'IsActive',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.jax.rs',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554199 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554200,607,0,16,540750,'Updated',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.jax.rs',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554200 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554201,608,0,18,110,540750,'UpdatedBy',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.jax.rs',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554201 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543033,0,'AD_JAXRS_Endpoint_ID',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','JAX-RS-Endpoint','JAX-RS-Endpoint',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543033 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554202,543033,0,13,540750,'AD_JAXRS_Endpoint_ID',TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs',10,'Y','N','N','N','Y','Y','N','N','Y','N','N','JAX-RS-Endpoint',0,TO_TIMESTAMP('2016-03-04 12:57:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 12:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554202 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 12:58
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554203,542195,0,30,540750,'N','AD_JavaClass_ID',TO_TIMESTAMP('2016-03-04 12:58:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.jax.rs',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','AD_JavaClass',0,TO_TIMESTAMP('2016-03-04 12:58:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 12:58
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554203 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 12:59
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543034,0,'EndpointType',TO_TIMESTAMP('2016-03-04 12:59:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','Endpoint type','Endpoint type',TO_TIMESTAMP('2016-03-04 12:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 12:59
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543034 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 04.03.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540646,TO_TIMESTAMP('2016-03-04 13:00:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','N','EndpointType',TO_TIMESTAMP('2016-03-04 13:00:12','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 04.03.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540646 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 04.03.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540646,541185,TO_TIMESTAMP('2016-03-04 13:00:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','client',TO_TIMESTAMP('2016-03-04 13:00:31','YYYY-MM-DD HH24:MI:SS'),100,'C','client')
;

-- 04.03.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541185 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 04.03.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540646,541186,TO_TIMESTAMP('2016-03-04 13:00:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','server',TO_TIMESTAMP('2016-03-04 13:00:46','YYYY-MM-DD HH24:MI:SS'),100,'S','server')
;

-- 04.03.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541186 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 04.03.2016 13:01
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554204,543034,0,17,540646,540750,'N','EndpointType',TO_TIMESTAMP('2016-03-04 13:01:08','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.jax.rs',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Endpoint type',0,TO_TIMESTAMP('2016-03-04 13:01:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 13:01
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554204 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 04.03.2016 13:52
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554205,1682,0,18,389,540750,'N','EntityType',TO_TIMESTAMP('2016-03-04 13:52:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Dictionary Entity Type; Determines ownership and synchronization','de.metas.jax.rs',60,'The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Entitäts-Art',0,TO_TIMESTAMP('2016-03-04 13:52:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 13:52
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554205 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 13:52
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-03-04 13:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554205
;


-- 04.03.2016 13:53
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='de.metas.jax.rs',Updated=TO_TIMESTAMP('2016-03-04 13:53:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554205
;

-- 04.03.2016 14:09
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543035,0,'IsEndpointActive',TO_TIMESTAMP('2016-03-04 14:09:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','Y','Endpoint active','Endpoint active',TO_TIMESTAMP('2016-03-04 14:09:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 14:09
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543035 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 04.03.2016 14:09
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554206,543035,0,20,540750,'N','IsEndpointActive',TO_TIMESTAMP('2016-03-04 14:09:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.jax.rs',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Endpoint active',0,TO_TIMESTAMP('2016-03-04 14:09:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 14:09
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554206 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

COMMIT;


-- 04.03.2016 12:57
-- URL zum Konzept
CREATE SEQUENCE AD_JAXRS_ENDPOINT_SEQ INCREMENT 1 MINVALUE 0 MAXVALUE 2147483647 START 1000000
;

-- 04.03.2016 13:01
-- URL zum Konzept
CREATE TABLE AD_JAXRS_Endpoint (AD_Client_ID NUMERIC(10) NOT NULL, AD_JavaClass_ID NUMERIC(10) NOT NULL, AD_JAXRS_Endpoint_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, EndpointType CHAR(1) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT AD_JAXRS_Endpoint_Key PRIMARY KEY (AD_JAXRS_Endpoint_ID))
;


-- 04.03.2016 13:52
-- URL zum Konzept
ALTER TABLE AD_JAXRS_Endpoint ADD EntityType VARCHAR(60) NOT NULL
;

-- 04.03.2016 13:53
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_jaxrs_endpoint','EntityType','VARCHAR(60)',null,'de.metas.jax.rs')
;



-- 04.03.2016 14:09
-- URL zum Konzept
ALTER TABLE AD_JAXRS_Endpoint ADD IsEndpointActive CHAR(1) DEFAULT 'N' CHECK (IsEndpointActive IN ('Y','N')) NOT NULL
;

-- 04.03.2016 13:53
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET EntityType='de.metas.jax.rs' WHERE EntityType IS NULL
;

