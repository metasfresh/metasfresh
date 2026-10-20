-- Records the top-level DB transaction that wrote a recompute marker, so a change deduplicates only against its own pending marker.
-- IDs from idserver.metas.de on 2026-10-19: AD_MigrationScript 5829750, AD_Element 585546, AD_Column 593752
SET LOCAL lock_timeout = '10s'
;

-- 2026-10-19T10:00:01.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585546/*From ID Server*/,0,'DBTransactionNo',TO_TIMESTAMP('2026-10-19 12:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Nummer der Datenbank-Transaktion, die den Eintrag geschrieben hat.','de.metas.inoutcandidate','Y','DB-Transaktionsnummer','DB-Transaktionsnummer',TO_TIMESTAMP('2026-10-19 12:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-10-19T10:00:02.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585546 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-10-19T10:00:03.000Z
UPDATE AD_Element_Trl SET Name='DB transaction number',PrintName='DB transaction number',Description='Number of the database transaction that wrote the record.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-19 12:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585546
;

-- 2026-10-19T10:00:04.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593752/*From ID Server*/,585546,0,22,541488,'DBTransactionNo',TO_TIMESTAMP('2026-10-19 12:00:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Nummer der Datenbank-Transaktion, die den Eintrag geschrieben hat.','de.metas.inoutcandidate',0,22,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'DB-Transaktionsnummer','NP',0,0,TO_TIMESTAMP('2026-10-19 12:00:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-10-19T10:00:05.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-10-19T10:00:06.000Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585546)
;

-- 2026-10-19T10:00:07.000Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_Recompute','ALTER TABLE public.M_ShipmentSchedule_Recompute ADD COLUMN DBTransactionNo NUMERIC')
;

-- 2026-10-19T10:00:08.000Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_Recompute','ALTER TABLE public.M_ShipmentSchedule_Recompute ALTER COLUMN DBTransactionNo SET DEFAULT pg_current_xact_id()::text::numeric')
;
