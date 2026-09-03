/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2026 metas GmbH
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

DO $$
BEGIN

-- Name: M_HU_PI LU matching M_HU_PI_Item_Product_ID
-- 2025-10-01T12:00:09.577Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540746,'    EXISTS (SELECT 1
            FROM M_HU_PI_Version lu_piv
                     INNER JOIN M_HU_PI_Item lu_pii ON lu_pii.M_HU_PI_Version_ID = lu_piv.M_HU_PI_Version_ID
            WHERE lu_piv.M_HU_PI_ID = M_HU_PI.M_HU_PI_ID
              AND lu_piv.IsActive = ''Y''
              AND lu_piv.IsCurrent = ''Y''
              AND lu_piv.HU_UnitType = ''LU''
              AND lu_pii.IsActive = ''Y''
              AND lu_pii.ItemType = ''HU''
              AND lu_pii.included_hu_pi_id IN (SELECT tu_pi.m_HU_PI_ID
                                               FROM M_HU_PI_Item_Product tu_piip
                                                        INNER JOIN M_HU_PI_Item tu_pii ON tu_pii.M_HU_PI_Item_ID = tu_piip.M_HU_PI_Item_ID
                                                        INNER JOIN M_HU_PI_Version tu_piv ON tu_piv.M_HU_PI_Version_ID = tu_pii.M_HU_PI_Version_ID
                                                        INNER JOIN M_HU_PI tu_pi ON tu_pi.M_HU_PI_ID = tu_piv.M_HU_PI_ID
                                               WHERE tu_piip.M_HU_PI_Item_Product_ID = (CASE WHEN @M_HU_PI_Item_Product_ID/0@ > 0 THEN @M_HU_PI_Item_Product_ID/0@ ELSE 101 END)
                                                 AND tu_pii.IsActive = ''Y''
                                                 AND tu_piv.IsActive = ''Y''
                                                 AND tu_piv.IsCurrent = ''Y''
                                                 AND tu_piv.HU_UnitType = ''TU''
                                                 AND tu_pi.IsActive = ''Y''))
',TO_TIMESTAMP('2025-10-01 12:00:09.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','M_HU_PI LU matching M_HU_PI_Item_Product_ID','S',TO_TIMESTAMP('2025-10-01 12:00:09.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_HU_PI LU matching M_HU_PI_Item_Product_ID
-- 2025-10-02T07:02:54.687Z
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT 1 FROM M_HU_PI_Version lu_piv INNER JOIN M_HU_PI_Item lu_pii ON lu_pii.M_HU_PI_Version_ID = lu_piv.M_HU_PI_Version_ID WHERE lu_piv.M_HU_PI_ID = M_HU_PI.M_HU_PI_ID AND lu_piv.IsActive = ''Y'' AND lu_piv.IsCurrent = ''Y'' AND lu_piv.HU_UnitType = ''LU'' AND lu_pii.IsActive = ''Y'' AND lu_pii.ItemType = ''HU'' AND lu_pii.included_hu_pi_id IN (SELECT tu_pi.m_HU_PI_ID FROM M_HU_PI_Item_Product tu_piip INNER JOIN M_HU_PI_Item tu_pii ON tu_pii.M_HU_PI_Item_ID = tu_piip.M_HU_PI_Item_ID INNER JOIN M_HU_PI_Version tu_piv ON tu_piv.M_HU_PI_Version_ID = tu_pii.M_HU_PI_Version_ID INNER JOIN M_HU_PI tu_pi ON tu_pi.M_HU_PI_ID = tu_piv.M_HU_PI_ID WHERE tu_piip.M_HU_PI_Item_Product_ID = (CASE WHEN @M_HU_PI_Item_Product_ID/0@ > 0 THEN @M_HU_PI_Item_Product_ID/0@ ELSE 101 END) AND tu_pii.IsActive = ''Y'' AND tu_piv.IsActive = ''Y'' AND tu_piv.IsCurrent = ''Y'' AND tu_piv.HU_UnitType IN (''TU'', ''V'') AND tu_pi.IsActive = ''Y''))',Updated=TO_TIMESTAMP('2025-10-02 07:02:54.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540746
;

EXCEPTION WHEN unique_violation THEN

    RAISE NOTICE 'valRule M_HU_PI LU matching M_HU_PI_Item_Product_ID already exists, skipping insert';-- taken from uat 5771920_M_Inventory_M_HU_PI_fields_validation.sql
end $$;

-- Column: C_OrderLine.M_LU_HU_PI_ID
-- 2026-01-26T17:57:14.857Z
-- old: M_HU_PI Only LUs (Still used on Quick input. On Quick input LU is the main first set pi, because qty is set for it )
-- new: M_HU_PI LU matching M_HU_PI_Item_Product_ID
UPDATE AD_Column SET AD_Val_Rule_ID=540746,Updated=TO_TIMESTAMP('2026-01-26 17:57:14.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590569
;
