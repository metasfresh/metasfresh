-- M_ReceiptSchedule: add IsClosed column (mirrors M_ShipmentSchedule.IsClosed)
-- When a purchase order is reactivated, receipt schedules should be closed (IsClosed=Y)
-- rather than hard-deleted, preserving user modifications.

-- AD_Column for M_ReceiptSchedule.IsClosed
-- AD_Column_ID=592114, AD_Table_ID=540524 (M_ReceiptSchedule), AD_Element_ID=2723 (IsClosed)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592114,2723,0,20,540524,'N','IsClosed',TO_TIMESTAMP('2026-03-02 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','de.metas.inoutcandidate',1,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Geschlossen','NP',0,0,TO_TIMESTAMP('2026-03-02 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592114
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- DDL: add the physical column
/* DDL */ SELECT public.db_alter_table('m_receiptschedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN IsClosed CHAR(1) DEFAULT ''N'' CHECK (IsClosed IN (''Y'',''N'')) NOT NULL')
;

-- AD_Field for M_ReceiptSchedule main tab (AD_Tab_ID=540526)
-- AD_Field_ID=774763, placed in org group (AD_UI_ElementGroup_ID=540134), after Processed (SeqNo=40) at SeqNo=50
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,592114,774763,0,540526,0,TO_TIMESTAMP('2026-03-02 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.inoutcandidate','',0,'Y','Y','Y','N','N','N','N','N','Y','N','Geschlossen',570,640,0,1,1,TO_TIMESTAMP('2026-03-02 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774763
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- AD_UI_Element to display the field in WebUI
-- AD_UI_Element_ID=648454, placed in org group (AD_UI_ElementGroup_ID=540134), after Processed at SeqNo=50
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,774763,0,540526,648454,540134,'F',TO_TIMESTAMP('2026-03-02 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Geschlossen',50,0,TO_TIMESTAMP('2026-03-02 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Propagate element translations to the new AD_Column_Trl and AD_Field_Trl rows
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2723)
;
SELECT update_Column_Translation_From_AD_Element(2723)
;
