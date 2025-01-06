-- Run mode: SWING_CLIENT






-- Column: C_BPartner.M_WarehousePO_ID
-- 2024-10-21T15:52:59.976Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589334,543850,0,30,540420,291,'M_WarehousePO_ID',TO_TIMESTAMP('2024-10-21 18:52:59.65','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Lager, an das der Lieferant eine Bestellung liefern soll.','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Liefer-Lager',0,0,TO_TIMESTAMP('2024-10-21 18:52:59.65','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-21T15:52:59.981Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589334 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-21T15:53:00.016Z
/* DDL */  select update_Column_Translation_From_AD_Element(543850)
;

-- 2024-10-21T15:53:01.346Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN M_WarehousePO_ID NUMERIC(10)')
;

-- 2024-10-21T15:53:03.214Z
ALTER TABLE C_BPartner ADD CONSTRAINT MWarehousePO_CBPartner FOREIGN KEY (M_WarehousePO_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
;







-- Field: Geschäftspartner(123,D) -> Lieferant(224,D) -> Liefer-Lager
-- Column: C_BPartner.M_WarehousePO_ID
-- 2024-10-21T15:54:42.209Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589334,731933,0,224,TO_TIMESTAMP('2024-10-21 18:54:42.029','YYYY-MM-DD HH24:MI:SS.US'),100,'Lager, an das der Lieferant eine Bestellung liefern soll.',10,'D','','Y','N','N','N','N','N','N','N','Liefer-Lager',TO_TIMESTAMP('2024-10-21 18:54:42.029','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-21T15:54:42.210Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-21T15:54:42.212Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543850)
;

-- 2024-10-21T15:54:42.224Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731933
;

-- 2024-10-21T15:54:42.225Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731933)
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Liefer-Lager
-- Column: C_BPartner.M_WarehousePO_ID
-- 2024-10-21T15:55:35.024Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731933,0,224,1000033,626205,'F',TO_TIMESTAMP('2024-10-21 18:55:34.872','YYYY-MM-DD HH24:MI:SS.US'),100,'Lager, an das der Lieferant eine Bestellung liefern soll.','','Y','N','Y','N','N','Liefer-Lager',170,0,0,TO_TIMESTAMP('2024-10-21 18:55:34.872','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Mandant
-- Column: C_BPartner.AD_Client_ID
-- 2024-10-21T15:56:07.287Z
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2024-10-21 18:56:07.287','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=546538
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner.AD_Org_ID
-- 2024-10-21T15:56:10.452Z
UPDATE AD_UI_Element SET SeqNo=160,Updated=TO_TIMESTAMP('2024-10-21 18:56:10.452','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=546537
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Liefer-Lager
-- Column: C_BPartner.M_WarehousePO_ID
-- 2024-10-21T15:56:15.688Z
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2024-10-21 18:56:15.688','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626205
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Liefer-Lager
-- Column: C_BPartner.M_WarehousePO_ID
-- 2024-10-21T15:56:24.958Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-10-21 18:56:24.958','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626205
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Anbauplanung
-- Column: C_BPartner.IsPlanning
-- 2024-10-21T15:56:24.964Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-10-21 18:56:24.964','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000280
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Urproduzent
-- Column: C_BPartner.Fresh_Urproduzent
-- 2024-10-21T15:56:24.970Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-10-21 18:56:24.97','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000282
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner.AD_Org_ID
-- 2024-10-21T15:56:24.976Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-10-21 18:56:24.976','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=546537
;




-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Liefer-Lager
-- Column: C_BPartner.M_WarehousePO_ID
-- 2024-10-21T16:11:30.687Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-10-21 19:11:30.687','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626205
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Anbauplanung
-- Column: C_BPartner.IsPlanning
-- 2024-10-21T16:11:30.694Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-10-21 19:11:30.693','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000280
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Urproduzent
-- Column: C_BPartner.Fresh_Urproduzent
-- 2024-10-21T16:11:30.699Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-10-21 19:11:30.699','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000282
;

-- UI Element: Geschäftspartner(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner.AD_Org_ID
-- 2024-10-21T16:11:30.703Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-10-21 19:11:30.703','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=546537
;






-- Column: C_BPartner.M_Warehouse_ID
-- 2024-10-22T15:28:36.963Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-10-22 18:28:36.962','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=550682
;

-- Column: C_BPartner.M_WarehousePO_ID
-- 2024-10-22T15:28:58.918Z
UPDATE AD_Column SET AD_Val_Rule_ID=540326,Updated=TO_TIMESTAMP('2024-10-22 18:28:58.918','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589334
;

-- 2024-10-22T15:29:00.201Z
INSERT INTO t_alter_column values('c_bpartner','M_WarehousePO_ID','NUMERIC(10)',null,null)
;

