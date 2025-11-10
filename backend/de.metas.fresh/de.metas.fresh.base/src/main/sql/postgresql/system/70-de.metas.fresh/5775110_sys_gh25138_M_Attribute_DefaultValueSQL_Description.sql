-- Run mode: SWING_CLIENT

-- Element: DefaultValueSQL
-- 2025-10-30T14:06:27.481Z
UPDATE AD_Element_Trl SET Description='SQL-Ausdruck, der verwendet wird, um den Standardwert für dieses Attribut zu berechnen. Der Ausdruck kann variable Platzhalter enthalten (z. B. @M_Product_ID@, @C_Order_ID@, @TableName@), die zur Laufzeit durch die tatsächlichen Kontextwerte ersetzt werden. Der resultierende Wert muss dem für das Attribut definierten Wertetyp entsprechen. Wenn dieses Feld leer ist, besitzt das Attribut keinen SQL-basierten Standardwert.', Help='SQL-Ausdruck, der verwendet wird, um den Standardwert für dieses Attribut zu berechnen.
Der Ausdruck kann variable Platzhalter enthalten (z. B. @M_Product_ID@, @C_Order_ID@, @TableName@), die zur Laufzeit durch die tatsächlichen Kontextwerte ersetzt werden. Der resultierende Wert muss dem für das Attribut definierten Wertetyp entsprechen. Wenn dieses Feld leer ist, besitzt das Attribut keinen SQL-basierten Standardwert.',Updated=TO_TIMESTAMP('2025-10-30 14:06:27.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584138 AND AD_Language='de_CH'
;

-- 2025-10-30T14:06:27.559Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-30T14:06:33.653Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584138,'de_CH')
;

-- Element: DefaultValueSQL
-- 2025-10-30T14:06:52.227Z
UPDATE AD_Element_Trl SET Description='SQL-Ausdruck, der verwendet wird, um den Standardwert für dieses Attribut zu berechnen. Der Ausdruck kann variable Platzhalter enthalten (z. B. @M_Product_ID@, @C_Order_ID@, @TableName@), die zur Laufzeit durch die tatsächlichen Kontextwerte ersetzt werden. Der resultierende Wert muss dem für das Attribut definierten Wertetyp entsprechen. Wenn dieses Feld leer ist, besitzt das Attribut keinen SQL-basierten Standardwert.', Help='SQL-Ausdruck, der verwendet wird, um den Standardwert für dieses Attribut zu berechnen.
Der Ausdruck kann variable Platzhalter enthalten (z. B. @M_Product_ID@, @C_Order_ID@, @TableName@), die zur Laufzeit durch die tatsächlichen Kontextwerte ersetzt werden. Der resultierende Wert muss dem für das Attribut definierten Wertetyp entsprechen. Wenn dieses Feld leer ist, besitzt das Attribut keinen SQL-basierten Standardwert.',Updated=TO_TIMESTAMP('2025-10-30 14:06:52.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584138 AND AD_Language='de_DE'
;

-- 2025-10-30T14:06:52.294Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-30T14:06:57.889Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584138,'de_DE')
;

-- 2025-10-30T14:06:57.953Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584138,'de_DE')
;

-- Element: DefaultValueSQL
-- 2025-10-30T14:07:13.853Z
UPDATE AD_Element_Trl SET Description='SQL expression used to compute the default value for this attribute. The expression may contain variable placeholders (e.g., @M_Product_ID@, @C_Order_ID@, @TableName@) that are replaced at runtime with actual context values. The resulting value must match the attribute’s defined value type. If empty, the attribute has no SQL-based default.', Help='SQL expression used to compute the default value for this attribute. The expression may contain variable placeholders (e.g., @M_Product_ID@, @C_Order_ID@, @TableName@) that are replaced at runtime with actual context values. The resulting value must match the attribute’s defined value type. If empty, the attribute has no SQL-based default.',Updated=TO_TIMESTAMP('2025-10-30 14:07:13.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584138 AND AD_Language='en_US'
;

-- 2025-10-30T14:07:13.920Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-30T14:07:23.150Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584138,'en_US')
;

