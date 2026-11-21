-- Run mode: SWING_CLIENT

-- Tab: Preislisten-Schema(337,D) -> Schema Position
-- Table: M_DiscountSchemaLine
-- 2026-11-20T09:10:47.409Z
UPDATE AD_Tab SET IsActive='Y',Updated=TO_TIMESTAMP('2026-11-20 09:10:47.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=405
;

-- UI Element: Preislisten-Schema(337,D) -> Schema Position(405,D) -> main -> 10 -> default.Kursart
-- Column: M_DiscountSchemaLine.C_ConversionType_ID
-- 2026-11-20T09:15:56.277Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8642,0,405,540841,656513,'F',TO_TIMESTAMP('2026-11-20 09:15:55.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kursart','Dieses Fenster ermöglicht Ihnen, die verschiedenen Kursarten anzulegen wie z.B. Spot, Firmenrate und/oder Kauf-/Verkaufrate.','Y','N','N','Y','N','N','N',0,'Kursart',82,0,0,TO_TIMESTAMP('2026-11-20 09:15:55.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten-Schema(337,D) -> Schema Position(405,D) -> main -> 10 -> default.Konvertierungsdatum
-- Column: M_DiscountSchemaLine.ConversionDate
-- 2026-11-20T09:16:25.197Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5253,0,405,540841,656514,'F',TO_TIMESTAMP('2026-11-20 09:16:24.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum für den gewählten Umrechnungskurs','The Conversion Date identifies the date used for currency conversion. The conversion rate chosen must include this date in it''s date range','Y','N','N','Y','N','N','N',0,'Konvertierungsdatum',84,0,0,TO_TIMESTAMP('2026-11-20 09:16:24.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten-Schema(337,D) -> Schema Position(405,D) -> main -> 10 -> default.Abschlag % auf Standardpreis
-- Column: M_DiscountSchemaLine.Std_Discount
-- 2026-11-20T09:16:56.823Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5262,0,405,540841,656515,'F',TO_TIMESTAMP('2026-11-20 09:16:56.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Discount percentage to subtract from base price','The Standard Price Discount Percentage indicates the percentage discount which will be subtracted from the base price.  A negative amount indicates the percentage which will be added to the base price.','Y','N','N','Y','N','N','N',0,'Abschlag % auf Standardpreis',86,0,0,TO_TIMESTAMP('2026-11-20 09:16:56.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten-Schema(337,D) -> Schema Position(405,D) -> main -> 10 -> default.Basis Standardpreis
-- Column: M_DiscountSchemaLine.Std_Base
-- 2026-11-20T09:17:16.721Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5260,0,405,540841,656516,'F',TO_TIMESTAMP('2026-11-20 09:17:16.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Base price for calculating new standard price','The Standard Price Base indicates the price to use as the basis for the calculation of a new price standard.
','Y','N','N','Y','N','N','N',0,'Basis Standardpreis',88,0,0,TO_TIMESTAMP('2026-11-20 09:17:16.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
