/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2020-11-25T15:14:51.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ad_val_rule SET code = '-- GENERAL
M_HU_PI_Item_Product.AD_Org_ID IN (0, @AD_Org_ID@)
AND (M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DatePromised@'' AND ( ''@DatePromised@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
    ( SELECT i.M_HU_PI_Item_ID
    from M_HU_PI_Item i
    where i.M_HU_PI_Version_ID IN
        (SELECT v.M_HU_PI_Version_ID
        from M_HU_PI_Version v
        where v.HU_UnitType = ''TU''
        )
    )

-- Price list
AND
(
    -- AUFTRAG
    (
        ''@IsSOTrx@'' = ''Y''
    )

    -- BESTELLUNG
    OR
    (
        ''@IsSOTrx@'' = ''N''
        AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID IN
        (
            SELECT pp.M_HU_PI_Item_Product_ID
            from M_ProductPrice pp
            where pp.M_Product_ID = @M_Product_ID@ AND pp.IsActive = ''Y''
            AND pp.M_PriceList_Version_ID =
            (
                SELECT M_PriceList_Version.M_PriceList_Version_ID
                from M_PriceList_Version
                where M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
                AND M_PriceList_Version.IsActive = ''Y''
                AND M_PriceList_Version.ValidFrom =
                (
                    SELECT MAX(M_PriceList_Version.ValidFrom)
                    from M_PriceList_Version
                    where M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
                    AND M_PriceList_Version.ValidFrom <=  ''@DatePromised@''
                    GROUP BY M_PriceList_ID
                )
            )
        )
    )
)
',Updated=TO_TIMESTAMP('2020-11-27 09:14:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540365
;

