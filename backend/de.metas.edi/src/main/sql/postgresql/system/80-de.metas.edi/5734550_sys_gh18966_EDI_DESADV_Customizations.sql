-- Column: M_InOut.IsEdiEnabled
-- Column: M_InOut.IsEdiEnabled
-- 2024-09-24T15:11:15.096Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-24 15:11:15.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549872
;

-- 2024-09-24T15:58:07.276Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583285,0,'IsShipmentsNotSent',TO_TIMESTAMP('2024-09-24 15:58:07.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Nicht abgeschickte Sendungen','Nicht abgeschickte Sendungen',TO_TIMESTAMP('2024-09-24 15:58:07.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-24T15:58:07.279Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583285 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsShipmentsNotSent
-- 2024-09-24T15:58:20.114Z
UPDATE AD_Element_Trl SET Name='Shipments not sent', PrintName='Shipments not sent',Updated=TO_TIMESTAMP('2024-09-24 15:58:20.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583285 AND AD_Language='en_US'
;

-- 2024-09-24T15:58:20.118Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583285,'en_US') 
;

-- Column: EDI_Desadv.IsShipmentsNotSent
-- Column SQL (old): null
-- Column: EDI_Desadv.IsShipmentsNotSent
-- 2024-09-24T16:08:46.048Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589161,583285,0,20,540644,'IsShipmentsNotSent','(CASE WHEN (EXISTS(SELECT 1 FROM m_inout shipment WHERE shipment.edi_desadv_id = EDI_Desadv.edi_desadv_id AND shipment.edi_exportstatus NOT IN (''S''))) THEN ''Y'' ELSE ''N'' END)',TO_TIMESTAMP('2024-09-24 16:08:45.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','de.metas.esb.edi',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Nicht abgeschickte Sendungen',0,0,TO_TIMESTAMP('2024-09-24 16:08:45.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-09-24T16:08:46.056Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589161 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-24T16:08:46.068Z
/* DDL */  select update_Column_Translation_From_AD_Element(583285) 
;

-- Column: EDI_Desadv.IsShipmentsNotSent
-- Column: EDI_Desadv.IsShipmentsNotSent
-- 2024-09-24T16:08:54.305Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-24 16:08:54.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589161
;

-- UI Element: Lieferung -> Lieferung.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2024-09-24T17:21:26.359Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,553214,0,257,626087,540975,'F',TO_TIMESTAMP('2024-09-24 17:21:25.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N','N','N',0,'EDI-Sendestatus',40,0,0,TO_TIMESTAMP('2024-09-24 17:21:25.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferung -> Lieferung.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2024-09-24T17:21:57.499Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-09-24 17:21:57.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=626087
;

-- UI Element: Lieferung -> Lieferung.Status
-- Column: M_InOut.DocStatus
-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.Status
-- Column: M_InOut.DocStatus
-- 2024-09-24T17:21:57.513Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-09-24 17:21:57.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547319
;

-- UI Element: Lieferung -> Lieferung.Posted
-- Column: M_InOut.Posted
-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> posted.Posted
-- Column: M_InOut.Posted
-- 2024-09-24T17:21:57.528Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-09-24 17:21:57.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564741
;

-- UI Element: Lieferung -> Lieferung.Transport Auftrag
-- Column: M_InOut.M_ShipperTransportation_ID
-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.Transport Auftrag
-- Column: M_InOut.M_ShipperTransportation_ID
-- 2024-09-24T17:21:57.546Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-09-24 17:21:57.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564603
;

-- UI Element: Lieferung -> Lieferung.Tour
-- Column: M_InOut.M_Tour_ID
-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> dates.Tour
-- Column: M_InOut.M_Tour_ID
-- 2024-09-24T17:21:57.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-09-24 17:21:57.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564599
;

-- UI Element: Lieferung -> Lieferung.Sektion
-- Column: M_InOut.AD_Org_ID
-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> org.Sektion
-- Column: M_InOut.AD_Org_ID
-- 2024-09-24T17:21:57.577Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-09-24 17:21:57.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540617
;

-- 2024-09-25T09:48:02.466Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583287,0,'EDI_ExportStatus_VirtualColumn',TO_TIMESTAMP('2024-09-25 09:48:02.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','EDI-Sendestatus','EDI-Sendestatus',TO_TIMESTAMP('2024-09-25 09:48:02.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-25T09:48:02.471Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583287 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: EDI_ExportStatus_VirtualColumn
-- 2024-09-25T09:48:14.072Z
UPDATE AD_Element_Trl SET Name='EDI export status', PrintName='EDI export status',Updated=TO_TIMESTAMP('2024-09-25 09:48:14.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583287 AND AD_Language='en_US'
;

-- 2024-09-25T09:48:14.129Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583287,'en_US')
;

-- Column: M_InOut.EDI_ExportStatus_VirtualColumn
-- Column SQL (old): null
-- Column: M_InOut.EDI_ExportStatus_VirtualColumn
-- 2024-09-25T09:50:05.875Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589166,583287,0,10,319,'EDI_ExportStatus_VirtualColumn','select M_InOut.EDI_ExportStatus',TO_TIMESTAMP('2024-09-25 09:50:05.688000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.esb.edi',0,1,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N',0,'EDI-Sendestatus',0,0,TO_TIMESTAMP('2024-09-25 09:50:05.688000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-09-25T09:50:05.878Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589166 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-25T09:50:05.882Z
/* DDL */  select update_Column_Translation_From_AD_Element(583287)
;

-- Column: M_InOut.EDI_ExportStatus_VirtualColumn
-- Column: M_InOut.EDI_ExportStatus_VirtualColumn
-- 2024-09-25T09:52:30.301Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540381,Updated=TO_TIMESTAMP('2024-09-25 09:52:30.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589166
;
