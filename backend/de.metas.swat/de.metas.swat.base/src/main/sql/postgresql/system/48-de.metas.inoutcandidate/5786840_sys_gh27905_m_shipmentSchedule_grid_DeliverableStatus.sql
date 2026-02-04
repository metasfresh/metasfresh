
create or replace function m_shipmentschedule_deliverablestatus_color_id_compute(p_m_shipmentschedule m_shipmentschedule) returns numeric
    stable
    language plpgsql
as
$$
DECLARE
    v_ColorID numeric;
    v_Red     numeric := getColor_ID_By_SysConfig('Red_Color');
    v_Yellow  numeric := getColor_ID_By_SysConfig('Yellow_Color');
    v_Green   numeric := getColor_ID_By_SysConfig('Green_Color');
    v_Blue    numeric := getColor_ID_By_SysConfig('Blue_Color');
BEGIN

    SELECT (
               CASE
                   WHEN p_m_shipmentschedule.qtyreserved = 0     THEN NULL
                   WHEN p_m_shipmentschedule.qtytodeliver = 0     THEN v_Red
                   WHEN (p_m_shipmentschedule.qtytodeliver > 0 AND p_m_shipmentschedule.qtytodeliver < p_m_shipmentschedule.qtyreserved) THEN v_Yellow
                   WHEN (p_m_shipmentschedule.qtytodeliver = p_m_shipmentschedule.qtyreserved)                                          THEN v_Green
                   WHEN (p_m_shipmentschedule.qtytodeliver > p_m_shipmentschedule.qtyreserved)                                          THEN v_Blue
               END
               )

    INTO v_ColorID;

    RETURN v_ColorID;
END;
$$;



-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.DeliveryStatusColor_ID
-- 2026-02-04T21:01:34.088Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591948,583563,0,27,500221,'XX','DeliveryStatusColor_ID','',TO_TIMESTAMP('2026-02-04 21:01:33.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Rot: Keine Artikel geliefert. Gelb: Teillieferung erfolgt. Grün: Die gesamte Bestellung wurde geliefert. Blau: Es wurden mehr Artikel als bestellt geliefert.','de.metas.inoutcandidate',0,10,'Rot: Keine Artikel geliefert.
Gelb: Teillieferung erfolgt.
Grün: Die gesamte Bestellung wurde geliefert.
Blau: Es wurden mehr Artikel als bestellt geliefert.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferstatus',0,0,TO_TIMESTAMP('2026-02-04 21:01:33.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-04T21:01:34.097Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591948 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-04T21:01:34.150Z
/* DDL */  select update_Column_Translation_From_AD_Element(583563)
;

UPDATE AD_Column SET ColumnSQL='M_ShipmentSchedule_DeliverableStatus_Color_ID_Compute(M_ShipmentSchedule)' WHERE AD_Column_ID=591948;


-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Lieferstatus
-- Column: M_ShipmentSchedule.DeliveryStatusColor_ID
-- 2026-02-04T21:05:57.870Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591948,771890,0,500221,0,TO_TIMESTAMP('2026-02-04 21:05:57.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Rot: Keine Artikel geliefert. Gelb: Teillieferung erfolgt. Grün: Die gesamte Bestellung wurde geliefert. Blau: Es wurden mehr Artikel als bestellt geliefert.',0,'de.metas.inoutcandidate',0,'Rot: Keine Artikel geliefert.
Gelb: Teillieferung erfolgt.
Grün: Die gesamte Bestellung wurde geliefert.
Blau: Es wurden mehr Artikel als bestellt geliefert.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Lieferstatus',0,0,780,0,1,1,TO_TIMESTAMP('2026-02-04 21:05:57.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T21:05:57.875Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771890 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T21:05:57.881Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583563)
;

-- 2026-02-04T21:05:57.898Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771890
;

-- 2026-02-04T21:05:57.904Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771890)
;

-- Element: DeliveryStatusColor_ID
-- 2026-02-04T21:06:28.024Z
UPDATE AD_Element_Trl SET Description='Rot: Keine Artikel geliefert. Gelb: Teillieferung erfolgt. Grün: Die gesamte Bestellung wurde geliefert. Blau: Es wurden mehr Artikel als bestellt geliefert.',Updated=TO_TIMESTAMP('2026-02-04 21:06:28.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583563 AND AD_Language='de_CH'
;

-- 2026-02-04T21:06:28.037Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T21:06:28.431Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583563,'de_CH')
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Delivery Status_DeliveryStatusColor_ID
-- Column: M_ShipmentSchedule.DeliveryStatusColor_ID
-- 2026-02-04T21:10:13.560Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771890,0,500221,540972,646828,'F',TO_TIMESTAMP('2026-02-04 21:10:13.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Rot: Keine Artikel geliefert. Gelb: Teillieferung erfolgt. Grün: Die gesamte Bestellung wurde geliefert. Blau: Es wurden mehr Artikel als bestellt geliefert.','Rot: Keine Artikel geliefert.
Gelb: Teillieferung erfolgt.
Grün: Die gesamte Bestellung wurde geliefert.
Blau: Es wurden mehr Artikel als bestellt geliefert.','Y','N','N','Y','N','N','N',0,'Delivery Status_DeliveryStatusColor_ID',70,0,0,TO_TIMESTAMP('2026-02-04 21:10:13.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Delivery Status_DeliveryStatusColor_ID
-- Column: M_ShipmentSchedule.DeliveryStatusColor_ID
-- 2026-02-04T21:10:32.488Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-04 21:10:32.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646828
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> order dates.Referenz
-- Column: M_ShipmentSchedule.POReference
-- 2026-02-04T21:10:32.497Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-04 21:10:32.497000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540935
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Produkt
-- Column: M_ShipmentSchedule.M_Product_ID
-- 2026-02-04T21:10:32.504Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-04 21:10:32.504000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540636
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Merkmale
-- Column: M_ShipmentSchedule.M_AttributeSetInstance_ID
-- 2026-02-04T21:10:32.512Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-04 21:10:32.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540638
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Delivery Status_DeliveryStatusColor_ID
-- Column: M_ShipmentSchedule.DeliveryStatusColor_ID
-- 2026-02-04T21:35:01.737Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540052, SeqNo=410,Updated=TO_TIMESTAMP('2026-02-04 21:35:01.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646828
;

