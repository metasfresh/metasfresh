-- 2025-01-30T16:15:35.111Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583461,0,'MaterialReceiptInfo',TO_TIMESTAMP('2025-01-30 17:15:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Wareneingangsinfo','Optionale Info, die in der Wareneingangs angezeigt wird',TO_TIMESTAMP('2025-01-30 17:15:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-01-30T16:15:35.423Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583461 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: MaterialReceiptInfo
-- 2025-01-30T16:16:34.756Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Material Receipt Info', PrintName='Optional info that is displayed in the goods receipt',Updated=TO_TIMESTAMP('2025-01-30 17:16:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583461 AND AD_Language='en_US'
;

-- 2025-01-30T16:16:34.885Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583461,'en_US') 
;

-- Column: C_BPartner.MaterialReceiptInfo
-- Column: C_BPartner.MaterialReceiptInfo
-- 2025-01-30T16:21:04.998Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589627,583461,0,14,291,'XX','MaterialReceiptInfo',TO_TIMESTAMP('2025-01-30 17:21:04','YYYY-MM-DD HH24:MI:SS'),100,'N','D',2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Wareneingangsinfo',0,0,TO_TIMESTAMP('2025-01-30 17:21:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2025-01-30T16:21:05.157Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589627 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-30T16:21:05.260Z
/* DDL */  select update_Column_Translation_From_AD_Element(583461) 
;

-- 2025-01-30T17:23:54.532Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN MaterialReceiptInfo VARCHAR(2000)')
;

-- Field: Geschäftspartner_OLD -> Lieferant -> Wareneingangsinfo
-- Column: C_BPartner.MaterialReceiptInfo
-- Field: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> Wareneingangsinfo
-- Column: C_BPartner.MaterialReceiptInfo
-- 2025-01-30T17:32:47.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589627,735599,0,224,0,TO_TIMESTAMP('2025-01-30 18:32:47','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Wareneingangsinfo',0,200,0,1,1,TO_TIMESTAMP('2025-01-30 18:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-01-30T17:32:48.151Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=735599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-30T17:32:48.202Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583461) 
;

-- 2025-01-30T17:32:48.255Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=735599
;

-- 2025-01-30T17:32:48.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(735599)
;

-- UI Element: Geschäftspartner_OLD -> Lieferant.Wareneingangsinfo
-- Column: C_BPartner.MaterialReceiptInfo
-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Wareneingangsinfo
-- Column: C_BPartner.MaterialReceiptInfo
-- 2025-01-30T17:34:02.123Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,735599,0,224,1000033,628434,'F',TO_TIMESTAMP('2025-01-30 18:34:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Wareneingangsinfo',65,0,0,TO_TIMESTAMP('2025-01-30 18:34:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- Column SQL (old): null
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2025-01-30T17:38:50.432Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589629,583461,0,14,540524,540472,'XX','MaterialReceiptInfo','(SELECT MaterialReceiptInfo from C_BPartner where C_BPartner_ID=M_ReceiptSchedule.C_BPartner_ID)',TO_TIMESTAMP('2025-01-30 18:38:49','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',2000,'Y','N','Y','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Wareneingangsinfo',0,0,TO_TIMESTAMP('2025-01-30 18:38:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2025-01-30T17:38:50.689Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589629 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-30T17:38:50.795Z
/* DDL */  select update_Column_Translation_From_AD_Element(583461) 
;

-- Field: Wareneingangsdisposition_OLD -> Wareneingangsdisposition -> Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- Field: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2025-01-30T17:42:14.801Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589629,735600,0,540526,0,TO_TIMESTAMP('2025-01-30 18:42:13','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Wareneingangsinfo',0,230,0,1,1,TO_TIMESTAMP('2025-01-30 18:42:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-01-30T17:42:15.107Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=735600 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-30T17:42:15.157Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583461) 
;

-- 2025-01-30T17:42:15.212Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=735600
;

-- 2025-01-30T17:42:15.263Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(735600)
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2025-01-30T17:43:30.300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,735600,0,540526,541315,628435,'F',TO_TIMESTAMP('2025-01-30 18:43:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Wareneingangsinfo',5,0,0,TO_TIMESTAMP('2025-01-30 18:43:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2025-01-30T17:44:45.692Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-01-30 18:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=628435
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Bestellte TU-Menge
-- Column: M_ReceiptSchedule.QtyOrderedTU
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Bestellte TU-Menge
-- Column: M_ReceiptSchedule.QtyOrderedTU
-- 2025-01-30T17:44:46.149Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-01-30 18:44:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542028
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Bewegte TU-Menge
-- Column: M_ReceiptSchedule.QtyMovedTU
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Bewegte TU-Menge
-- Column: M_ReceiptSchedule.QtyMovedTU
-- 2025-01-30T17:44:46.606Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-01-30 18:44:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541847
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Packbeschreibung
-- Column: M_ReceiptSchedule.PackDescription
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Packbeschreibung
-- Column: M_ReceiptSchedule.PackDescription
-- 2025-01-30T17:44:47.063Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-01-30 18:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541844
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Bestellt
-- Column: M_ReceiptSchedule.QtyOrdered
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Bestellt
-- Column: M_ReceiptSchedule.QtyOrdered
-- 2025-01-30T17:44:47.523Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-01-30 18:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541872
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Eingegangen
-- Column: M_ReceiptSchedule.QtyMoved
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Eingegangen
-- Column: M_ReceiptSchedule.QtyMoved
-- 2025-01-30T17:44:47.978Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-01-30 18:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541874
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Mengeneinheit
-- Column: M_ReceiptSchedule.C_UOM_ID
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Mengeneinheit
-- Column: M_ReceiptSchedule.C_UOM_ID
-- 2025-01-30T17:44:48.434Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-01-30 18:44:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541873
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Eingangsdatum
-- Column: M_ReceiptSchedule.MovementDate
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> dates.Eingangsdatum
-- Column: M_ReceiptSchedule.MovementDate
-- 2025-01-30T17:44:48.888Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-01-30 18:44:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541878
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- 2025-01-30T17:44:49.345Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-01-30 18:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541817
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- 2025-01-30T17:44:49.801Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-01-30 18:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541818
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> org.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2025-01-30T17:44:50.259Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-01-30 18:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541883
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2025-01-30T17:44:50.705Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-01-30 18:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541821
;

-- UI Element: Wareneingangsdisposition_OLD -> Wareneingangsdisposition.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- UI Element: Wareneingangsdisposition_OLD(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> org.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2025-01-30T17:44:51.151Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-01-30 18:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541880
;

-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2025-01-30T18:09:15.934Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-30 19:09:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589629
;
