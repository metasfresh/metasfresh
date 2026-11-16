-- Picking: exclude fully-picked schedules whose qty is bound to a draft shipment (gh29437)
-- Backend-only filter column on the M_Packageable_V view-table (no AD_Field / no window placement;
-- same kind as IsCatchWeight / IsFixedDatePromised). Consumed by PackagingDAO via IQueryBL model columns.

-- AD_Element: IsPickQtyOnDraftShipment (German base name, English via en_US _Trl)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584934 /*From ID Server*/,0,'IsPickQtyOnDraftShipment',TO_TIMESTAMP('2026-06-03 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Kommissionierte Menge auf Entwurfs-Lieferschein','Kommissionierte Menge auf Entwurfs-Lieferschein',TO_TIMESTAMP('2026-06-03 09:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Seed _Trl rows for all active languages (copies the German base text)
SELECT add_missing_translations()
;

-- Override en_US with the English translation
UPDATE AD_Element_Trl
SET Name='Picked qty on draft shipment', PrintName='Picked qty on draft shipment', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 09:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584934 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584934,'en_US')
;

-- AD_Column: M_Packageable_V.IsPickQtyOnDraftShipment (YesNo, view column -> IsSyncDatabase='N', IsUpdateable='N')
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592700 /*From ID Server*/,584934,0,20,540823,'XX','IsPickQtyOnDraftShipment',TO_TIMESTAMP('2026-06-03 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Kommissionierte Menge ist (teilweise) einem Entwurfs-Lieferschein zugeordnet','de.metas.inoutcandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Kommissionierte Menge auf Entwurfs-Lieferschein','NP',0,0,TO_TIMESTAMP('2026-06-03 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Seed AD_Column_Trl rows for all active+base languages
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592700
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(584934)
;
