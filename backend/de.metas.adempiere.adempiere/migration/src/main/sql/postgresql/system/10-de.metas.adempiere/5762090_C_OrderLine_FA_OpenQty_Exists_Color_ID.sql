
DROP FUNCTION IF EXISTS C_OrderLine_FA_OpenQty_Exists_Color_ID_Compute (
    p_C_OrderLine numeric
)
;

CREATE FUNCTION C_OrderLine_FA_OpenQty_Exists_Color_ID_Compute(p_C_OrderLine numeric) RETURNS numeric
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_ColorID numeric;
    v_Red     numeric := getColor_ID_By_SysConfig('Red_Color');
    v_Yellow  numeric := getColor_ID_By_SysConfig('Yellow_Color');
    v_Green   numeric := getColor_ID_By_SysConfig('Green_Color');
BEGIN

    SELECT (CASE
                WHEN (summary.qtydeliveredinuom IS NULL)                                                THEN v_Red
                WHEN (summary.qtydeliveredinuom <= 0)                                                   THEN v_Red
                WHEN (summary.qtydeliveredinuom > 0 AND summary.qtydeliveredinuom < summary.qtyentered) THEN v_Yellow
                WHEN (summary.qtydeliveredinuom >= summary.qtyentered)                                  THEN v_Green
            END
               )

    FROM public.c_orderline ol
             LEFT JOIN c_callordersummary summary ON (summary.c_orderline_id = ol.c_orderline_id OR
                                                      EXISTS (SELECT 1 FROM c_callorderdetail detail WHERE detail.c_orderline_id = ol.c_orderline_id AND detail.c_callordersummary_id = summary.c_callordersummary_id))

    WHERE ol.c_orderline_id = p_C_OrderLine
    INTO v_ColorID;

    RETURN v_ColorID;
END;
$$
;


/* WITH colors AS (SELECT C_OrderLine_FA_OpenQty_Exists_Color_ID_Compute(ol.c_orderline_id) AS color, ol.*
                FROM c_orderline ol
                         JOIN c_order o ON o.c_order_id = ol.c_order_id)
SELECT name
FROM ad_color c
         JOIN colors o ON o.color = c.ad_color_id
; */







































-- 2025-08-04T15:03:52.470Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583848,0,'FA_OpenQty_Exists_Color_ID',TO_TIMESTAMP('2025-08-04 15:03:52.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'This indicator shows the shipping status of quantities related to a frame agreement. It is displayed in both frame agreements and their related call-off orders within the sales order window. The status is represented using the following color codes:  Red – No quantity from the frame agreement has been shipped.  Yellow – A partial quantity from the frame agreement has been shipped.  Green – The full frame agreement quantity has been shipped.','D','This indicator shows the shipping status of quantities related to a frame agreement. It is displayed in both frame agreements and their related call-off orders within the sales order window. The status is represented using the following color codes:  Red – No quantity from the frame agreement has been shipped.  Yellow – A partial quantity from the frame agreement has been shipped.  Green – The full frame agreement quantity has been shipped.','Y','Frame agreement with open quantity exists','Frame agreement with open quantity exists',TO_TIMESTAMP('2025-08-04 15:03:52.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-04T15:03:52.473Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583848 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: FA_OpenQty_Exists_Color_ID
-- 2025-08-04T15:16:48.697Z
UPDATE AD_Element_Trl SET Description='Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet: Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert. Gelb – Ein Teil der Menge wurde geliefert. Grün – Die gesamte vereinbarte Menge wurde geliefert.', Help='Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet:
Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert.
Gelb – Ein Teil der Menge wurde geliefert.
Grün – Die gesamte vereinbarte Menge wurde geliefert.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-04 15:16:48.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583848 AND AD_Language='de_CH'
;

-- 2025-08-04T15:16:48.700Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-04T15:16:49.086Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583848,'de_CH')
;

-- Element: FA_OpenQty_Exists_Color_ID
-- 2025-08-04T15:17:15.647Z
UPDATE AD_Element_Trl SET Name='Rahmenvertrag mit offener Menge vorhanden', PrintName='Rahmenvertrag mit offener Menge vorhanden',Updated=TO_TIMESTAMP('2025-08-04 15:17:15.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583848 AND AD_Language='de_CH'
;

-- 2025-08-04T15:17:15.648Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-04T15:17:15.858Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583848,'de_CH')
;

-- Element: FA_OpenQty_Exists_Color_ID
-- 2025-08-04T15:17:24.777Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rahmenvertrag mit offener Menge vorhanden', PrintName='Rahmenvertrag mit offener Menge vorhanden',Updated=TO_TIMESTAMP('2025-08-04 15:17:24.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583848 AND AD_Language='de_DE'
;

-- 2025-08-04T15:17:24.778Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-04T15:17:25.802Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583848,'de_DE')
;

-- 2025-08-04T15:17:25.804Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583848,'de_DE')
;

-- Element: FA_OpenQty_Exists_Color_ID
-- 2025-08-04T15:17:38.528Z
UPDATE AD_Element_Trl SET Description='Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet: Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert. Gelb – Ein Teil der Menge wurde geliefert. Grün – Die gesamte vereinbarte Menge wurde geliefert.', Help='Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet:
Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert.
Gelb – Ein Teil der Menge wurde geliefert.
Grün – Die gesamte vereinbarte Menge wurde geliefert.',Updated=TO_TIMESTAMP('2025-08-04 15:17:38.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583848 AND AD_Language='de_DE'
;

-- 2025-08-04T15:17:38.529Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-04T15:17:38.951Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583848,'de_DE')
;

-- 2025-08-04T15:17:38.952Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583848,'de_DE')
;

-- Column: C_OrderLine.FA_OpenQty_Exists_Color_ID
-- 2025-08-04T15:19:30.412Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590595,583848,0,27,260,'XX','FA_OpenQty_Exists_Color_ID',TO_TIMESTAMP('2025-08-04 15:19:30.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet: Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert. Gelb – Ein Teil der Menge wurde geliefert. Grün – Die gesamte vereinbarte Menge wurde geliefert.','D',0,10,'Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet:
Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert.
Gelb – Ein Teil der Menge wurde geliefert.
Grün – Die gesamte vereinbarte Menge wurde geliefert.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rahmenvertrag mit offener Menge vorhanden',0,0,TO_TIMESTAMP('2025-08-04 15:19:30.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-04T15:19:30.415Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590595 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-04T15:19:30.419Z
/* DDL */  select update_Column_Translation_From_AD_Element(583848)
;

-- Column: C_OrderLine.FA_OpenQty_Exists_Color_ID
-- 2025-08-04T16:05:23.708Z
UPDATE AD_Column SET ColumnSQL='(SELECT C_OrderLine_FA_OpenQty_Exists_Color_ID_Compute(ol.C_OrderLine_ID) from C_OrderLine ol where ol.C_OrderLine_ID = C_OrderLine.C_OrderLine_ID)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-08-04 16:05:23.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590595
;

-- Field: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> Rahmenvertrag mit offener Menge vorhanden
-- Column: C_OrderLine.FA_OpenQty_Exists_Color_ID
-- 2025-08-04T16:07:19.667Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590595,751762,0,187,TO_TIMESTAMP('2025-08-04 16:07:19.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet: Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert. Gelb – Ein Teil der Menge wurde geliefert. Grün – Die gesamte vereinbarte Menge wurde geliefert.',10,'D','Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet:
Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert.
Gelb – Ein Teil der Menge wurde geliefert.
Grün – Die gesamte vereinbarte Menge wurde geliefert.','Y','N','N','N','N','N','N','N','Rahmenvertrag mit offener Menge vorhanden',TO_TIMESTAMP('2025-08-04 16:07:19.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-04T16:07:19.670Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751762 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-04T16:07:19.673Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583848)
;

-- 2025-08-04T16:07:19.684Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751762
;

-- 2025-08-04T16:07:19.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751762)
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Rahmenvertrag mit offener Menge vorhanden
-- Column: C_OrderLine.FA_OpenQty_Exists_Color_ID
-- 2025-08-04T16:07:51.626Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,751762,0,187,1000005,636100,'F',TO_TIMESTAMP('2025-08-04 16:07:51.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet: Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert. Gelb – Ein Teil der Menge wurde geliefert. Grün – Die gesamte vereinbarte Menge wurde geliefert.','Dieser Indikator zeigt den Versandstatus der im Rahmenvertrag vereinbarten Menge an. Er ist sowohl im Rahmenvertrag selbst als auch in den zugehörigen Abrufaufträgen im Verkaufsbeleg sichtbar. Die Farbcodierung bedeutet:
Rot – Es wurde keine Menge aus dem Rahmenvertrag geliefert.
Gelb – Ein Teil der Menge wurde geliefert.
Grün – Die gesamte vereinbarte Menge wurde geliefert.','Y','N','Y','N','N','Rahmenvertrag mit offener Menge vorhanden',440,0,0,TO_TIMESTAMP('2025-08-04 16:07:51.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;



create index if not exists c_orderline_c_flatrate_conditions_id
    on c_orderline (c_flatrate_conditions_id);

