-- Name: C_UOM_Weigth
-- 2025-04-16T07:11:59.176Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540706,'C_UOM.UOMType=''WE''',TO_TIMESTAMP('2025-04-16 07:11:58.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','C_UOM_Weigth','S',TO_TIMESTAMP('2025-04-16 07:11:58.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: C_UOM_Weight
-- 2025-04-16T08:40:13.181Z
UPDATE AD_Val_Rule SET Name='C_UOM_Weight',Updated=TO_TIMESTAMP('2025-04-16 08:40:12.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540706
;

-- Column: M_Product.GrossWeight_UOM_ID
-- Column: M_Product.GrossWeight_UOM_ID
-- 2025-04-16T07:13:39.838Z
UPDATE AD_Column SET AD_Val_Rule_ID=540706,Updated=TO_TIMESTAMP('2025-04-16 07:13:39.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=578971
;

-- Element: GrossWeight_UOM_ID
-- 2025-04-16T08:02:46.988Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gewicht brutto Maßeinheit', PrintName='Gewicht brutto Maßeinheit',Updated=TO_TIMESTAMP('2025-04-16 08:02:46.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583224 AND AD_Language='de_DE'
;

-- 2025-04-16T08:02:47.041Z
UPDATE AD_Element SET Name='Gewicht brutto Maßeinheit', PrintName='Gewicht brutto Maßeinheit', Updated=TO_TIMESTAMP('2025-04-16 08:02:47.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=583224
;

-- 2025-04-16T08:03:00.056Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583224,'de_DE') 
;

-- 2025-04-16T08:03:00.109Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583224,'de_DE') 
;

-- Element: GrossWeight_UOM_ID
-- 2025-04-16T08:03:14.419Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gewicht brutto Maßeinheit', PrintName='Gewicht brutto Maßeinheit',Updated=TO_TIMESTAMP('2025-04-16 08:03:14.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583224 AND AD_Language='de_CH'
;

-- 2025-04-16T08:03:14.525Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583224,'de_CH') 
;

