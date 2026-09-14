-- Run mode: SWING_CLIENT

-- Process: WEBUI_PP_Order_Receipt(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Receipt)
-- ParameterName: QtyTU
-- 2026-09-07T11:34:01.264Z
UPDATE AD_Process_Para SET SeqNo=25,Updated=TO_TIMESTAMP('2026-09-07 11:34:01.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=541174
;

-- Element: QtyCUsPerTU
-- 2026-09-07T11:35:38.401Z
UPDATE AD_Element_Trl SET Name='Menge pro TU', PrintName='Menge pro TU',Updated=TO_TIMESTAMP('2026-09-07 11:35:38.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='de_DE'
;

-- 2026-09-07T11:35:38.459Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-09-07T11:35:47.093Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(542492,'de_DE')
;

-- 2026-09-07T11:35:47.153Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'de_DE')
;

-- Element: QtyCUsPerTU
-- 2026-09-07T11:35:57.161Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-07 11:35:57.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='de_DE'
;

-- 2026-09-07T11:35:57.281Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(542492,'de_DE')
;

-- 2026-09-07T11:35:57.340Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'de_DE')
;

-- Element: QtyCUsPerTU
-- 2026-09-07T11:36:58.528Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Menge pro TU', PrintName='Menge pro TU',Updated=TO_TIMESTAMP('2026-09-07 11:36:58.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='de_CH'
;

-- 2026-09-07T11:36:58.586Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-09-07T11:37:09.186Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'de_CH')
;

