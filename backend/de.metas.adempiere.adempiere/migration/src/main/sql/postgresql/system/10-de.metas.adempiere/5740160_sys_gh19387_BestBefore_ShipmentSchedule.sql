-- 2024-11-20T16:23:03.521Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540810,0,540504,TO_TIMESTAMP('2024-11-20 18:23:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','M_HU_Attribute_MHD','N',TO_TIMESTAMP('2024-11-20 18:23:03','YYYY-MM-DD HH24:MI:SS'),100,'M_Attribute_ID=1000025')
;

-- 2024-11-20T16:23:03.526Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540810 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-11-20T16:23:31.779Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,549252,541436,540810,0,TO_TIMESTAMP('2024-11-20 18:23:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',10,TO_TIMESTAMP('2024-11-20 18:23:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-20T16:23:40.603Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,549256,541437,540810,0,TO_TIMESTAMP('2024-11-20 18:23:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',20,TO_TIMESTAMP('2024-11-20 18:23:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-20T16:23:49.157Z
CREATE INDEX M_HU_Attribute_MHD ON M_HU_Attribute (M_HU_ID,M_Attribute_ID) WHERE M_Attribute_ID=1000025
;

-- 2024-11-20T15:33:28.781Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583374,0,'MHD',TO_TIMESTAMP('2024-11-20 17:33:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','MHD','MHD',TO_TIMESTAMP('2024-11-20 17:33:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-20T15:33:28.796Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583374 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: MHD
-- 2024-11-20T15:33:43.802Z
UPDATE AD_Element_Trl SET Name='Best Before', PrintName='Best Before',Updated=TO_TIMESTAMP('2024-11-20 17:33:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583374 AND AD_Language='en_US'
;

-- 2024-11-20T15:33:43.834Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583374,'en_US') 
;

-- Column: M_ShipmentSchedule.BestBeforeDate
-- Column SQL (old): null
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2024-11-20T17:13:36.841Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589435,577375,0,15,500221,'XX','BestBeforeDate','(SELECT mhd.valuedate         from M_HU_Attribute mhd                  INNER JOIN M_ShipmentSchedule_QtyPicked qtyPicked on qtypicked.vhu_id = mhd.m_hu_id                  INNER JOIN M_ShipmentSchedule ss on qtyPicked.m_shipmentschedule_id = ss.m_shipmentschedule_id         where ss.m_shipmentschedule_id = M_ShipmentSchedule.M_ShipmentSchedule_ID           AND mhd.m_attribute_id = 540020         order by qtyPicked.created         LIMIT 1)',TO_TIMESTAMP('2024-11-20 19:13:36','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Mindesthaltbarkeitsdatum',0,0,TO_TIMESTAMP('2024-11-20 19:13:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-20T17:13:36.845Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589435 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-20T17:13:36.850Z
/* DDL */  select update_Column_Translation_From_AD_Element(577375) 
;

-- Field: Lieferdisposition -> Auslieferplan -> Mindesthaltbarkeitsdatum
-- Column: M_ShipmentSchedule.BestBeforeDate
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Mindesthaltbarkeitsdatum
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2024-11-20T17:13:59.640Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589435,734027,0,500221,TO_TIMESTAMP('2024-11-20 19:13:59','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Mindesthaltbarkeitsdatum',TO_TIMESTAMP('2024-11-20 19:13:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-20T17:13:59.642Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-20T17:13:59.645Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577375) 
;

-- 2024-11-20T17:13:59.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734027
;

-- 2024-11-20T17:13:59.661Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734027)
;

-- UI Element: Lieferdisposition -> Auslieferplan.Mindesthaltbarkeitsdatum
-- Column: M_ShipmentSchedule.BestBeforeDate
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Mindesthaltbarkeitsdatum
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2024-11-20T17:14:23.961Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734027,0,500221,627351,540021,'F',TO_TIMESTAMP('2024-11-20 19:14:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mindesthaltbarkeitsdatum',35,0,0,TO_TIMESTAMP('2024-11-20 19:14:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferdisposition -> Auslieferplan.Mindesthaltbarkeitsdatum
-- Column: M_ShipmentSchedule.BestBeforeDate
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Mindesthaltbarkeitsdatum
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2024-11-20T17:14:36.470Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-11-20 19:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=627351
;

-- Field: Lieferdisposition -> Auslieferplan -> MHD
-- Column: M_ShipmentSchedule.BestBeforeDate
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> MHD
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2024-11-20T17:15:49.110Z
UPDATE AD_Field SET AD_Name_ID=583374, Description=NULL, Help=NULL, Name='MHD',Updated=TO_TIMESTAMP('2024-11-20 19:15:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=734027
;

-- 2024-11-20T17:15:49.111Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583374) 
;

-- 2024-11-20T17:15:49.117Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734027
;

-- 2024-11-20T17:15:49.118Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734027)
;

-- UI Element: Lieferdisposition -> Auslieferplan.Beauftragte Gebindemenge
-- Column: M_ShipmentSchedule.QtyOrdered_TU
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Beauftragte Gebindemenge
-- Column: M_ShipmentSchedule.QtyOrdered_TU
-- 2024-11-20T18:08:43.338Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547313
;

-- UI Element: Lieferdisposition -> Auslieferplan.Packvorschrift
-- Column: M_ShipmentSchedule.M_HU_PI_Item_Product_ID
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Packvorschrift
-- Column: M_ShipmentSchedule.M_HU_PI_Item_Product_ID
-- 2024-11-20T18:08:43.354Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548121
;

-- UI Element: Lieferdisposition -> Auslieferplan.Beauftragt
-- Column: M_ShipmentSchedule.QtyOrdered_Calculated
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Beauftragt
-- Column: M_ShipmentSchedule.QtyOrdered_Calculated
-- 2024-11-20T18:08:43.370Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547299
;

-- UI Element: Lieferdisposition -> Auslieferplan.QtyOrdered_Override
-- Column: M_ShipmentSchedule.QtyOrdered_Override
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.QtyOrdered_Override
-- Column: M_ShipmentSchedule.QtyOrdered_Override
-- 2024-11-20T18:08:43.386Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547310
;

-- UI Element: Lieferdisposition -> Auslieferplan.Liefermenge
-- Column: M_ShipmentSchedule.QtyToDeliver
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Liefermenge
-- Column: M_ShipmentSchedule.QtyToDeliver
-- 2024-11-20T18:08:43.401Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540943
;

-- UI Element: Lieferdisposition -> Auslieferplan.Auf Packzettel
-- Column: M_ShipmentSchedule.QtyPickList
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> quantities.Auf Packzettel
-- Column: M_ShipmentSchedule.QtyPickList
-- 2024-11-20T18:08:43.414Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540944
;

-- UI Element: Lieferdisposition -> Auslieferplan.Streckengeschäft
-- Column: M_ShipmentSchedule.IsDropShip
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Streckengeschäft
-- Column: M_ShipmentSchedule.IsDropShip
-- 2024-11-20T18:08:43.475Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547308
;

-- UI Element: Lieferdisposition -> Auslieferplan.Zu Akt.
-- Column: M_ShipmentSchedule.IsToRecompute
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Zu Akt.
-- Column: M_ShipmentSchedule.IsToRecompute
-- 2024-11-20T18:08:43.488Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547306
;

-- UI Element: Lieferdisposition -> Auslieferplan.Verarbeitet
-- Column: M_ShipmentSchedule.Processed
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Verarbeitet
-- Column: M_ShipmentSchedule.Processed
-- 2024-11-20T18:08:43.500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547307
;

-- UI Element: Lieferdisposition -> Auslieferplan.Sektion
-- Column: M_ShipmentSchedule.AD_Org_ID
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> org.Sektion
-- Column: M_ShipmentSchedule.AD_Org_ID
-- 2024-11-20T18:08:43.513Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-11-20 20:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547302
;

-- Field: Lieferdisposition -> Auslieferplan -> MHD
-- Column: M_ShipmentSchedule.BestBeforeDate
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> MHD
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2024-11-20T18:09:16.542Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-11-20 20:09:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=734027
;

-- Column: M_ShipmentSchedule.BestBeforeDate
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2024-11-20T18:09:39.538Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-11-20 20:09:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589435
;

-- Field: Lieferdisposition -> Auslieferplan -> MHD
-- Column: M_ShipmentSchedule.BestBeforeDate
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> MHD
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2024-11-20T18:52:13.431Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2024-11-20 20:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=734027
;
