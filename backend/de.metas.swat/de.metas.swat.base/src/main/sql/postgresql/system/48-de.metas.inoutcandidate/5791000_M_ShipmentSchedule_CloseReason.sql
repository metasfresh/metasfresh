-- 2026-03-02
-- https://github.com/metasfresh/me03/issues/27550
-- M_ShipmentSchedule.CloseReason: tracks why a shipment schedule was closed

-- 1) AD_Element: CloseReason
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584599,0,'CloseReason',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Schlussgrund','Schlussgrund',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584599
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Schlussgrund', PrintName='Schlussgrund', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584599 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Close Reason', PrintName='Close Reason', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584599 AND AD_Language='en_US'
;

-- 2) AD_Reference (List): M_ShipmentSchedule_CloseReason
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542061,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','N','M_ShipmentSchedule_CloseReason',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=542061
AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 3) AD_Ref_List values (9 reasons)
-- 3a) Manual
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542061,544135,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Manuell',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MA','MA')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544135
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Manuell', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544135 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Manual', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544135 AND AD_Language='en_US'
;

-- 3b) OrderReactivated
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542061,544136,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Bestellung reaktiviert',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'OR','OR')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544136
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Bestellung reaktiviert', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544136 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Order Reactivated', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544136 AND AD_Language='en_US'
;

-- 3c) PartiallyShipped
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542061,544137,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Teilweise geliefert',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'PS','PS')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544137
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Teilweise geliefert', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544137 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Partially Shipped', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544137 AND AD_Language='en_US'
;

-- 3d) InvoiceCandidateClosed
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542061,544138,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Rechnungskandidat geschlossen',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'IC','IC')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544138
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Rechnungskandidat geschlossen', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544138 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Invoice Candidate Closed', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544138 AND AD_Language='en_US'
;

-- 3e) FlatrateTerm
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542061,544139,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Pauschalvertrag',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'FT','FT')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544139
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Pauschalvertrag', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544139 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Flatrate Term', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544139 AND AD_Language='en_US'
;

-- 3f) ContractPause
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542061,544140,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Vertragspause',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'CP','CP')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544140
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Vertragspause', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544140 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Contract Pause', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544140 AND AD_Language='en_US'
;

-- 3g) ShipmentProcessed
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542061,544141,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Lieferung verarbeitet',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'SP','SP')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544141
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Lieferung verarbeitet', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544141 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Shipment Processed', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544141 AND AD_Language='en_US'
;

-- 3h) PickingRejected
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542061,544142,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Kommissionierung abgelehnt',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'PR','PR')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544142
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Kommissionierung abgelehnt', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544142 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Picking Rejected', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544142 AND AD_Language='en_US'
;

-- 3i) OutOfStock
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,542061,544143,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Nicht auf Lager',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'OS','OS')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544143
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nicht auf Lager', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544143 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Out of Stock', Updated=TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544143 AND AD_Language='en_US'
;

-- 4) AD_Column on M_ShipmentSchedule (AD_Table_ID=500221)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,
    ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,
    IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,
    IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,
    IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,
    IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,
    IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,
    Updated,UpdatedBy,Version)
VALUES (0,592115,584599,0,17,542061,500221,
    'CloseReason',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',0,2,
    'Y','N','Y','N','N','N',
    'N','N','N','N','N','N',
    'N','N','N','N','N','N','N',
    'N','N','N','N','N','N',
    'Y','N',0,'Schlussgrund','NP',0,0,
    TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=592115
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584599)
;

-- 5) DDL: add column to table (using db_alter_table wrapper)
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN CloseReason VARCHAR(2)')
;

-- 6) Check constraint for List reference column
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule',
    $$ALTER TABLE public.M_ShipmentSchedule ADD CONSTRAINT CloseReason_Check CHECK (CloseReason IS NULL OR CloseReason IN ('MA','OR','PS','IC','FT','CP','SP','PR','OS'))$$)
;

-- 7) AD_Field on tab 500221
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,
    Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,Name,AD_Org_ID,EntityType)
VALUES (500221,'Y',2,'N','N','N','N',0,'Y',
    TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,774764,592115,'Schlussgrund',0,'de.metas.inoutcandidate')
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=774764
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584599)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=774764
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(774764)
;

-- 8) AD_UI_Element: between IsClosed (SeqNo=50) and Status (SeqNo=60), read-only
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,
    AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,
    Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,774764,0,500221,540972,648455,
    'F',TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N',
    'Schlussgrund',55,0,0,TO_TIMESTAMP('2026-03-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 9) Final propagation: re-run after all AD_Field_Trl rows exist
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584599)
;
