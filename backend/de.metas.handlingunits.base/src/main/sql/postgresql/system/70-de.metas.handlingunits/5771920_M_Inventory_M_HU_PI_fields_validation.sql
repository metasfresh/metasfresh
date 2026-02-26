-- Run mode: SWING_CLIENT

-- Name: M_HU_PI_Item_Product_For_Product_and_MovementDate
-- 2025-10-01T11:42:59.895Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540745,'(M_HU_PI_Item_Product.M_Product_ID =@M_Product_ID@ OR M_HU_PI_Item_Product.isAllowAnyProduct = ''Y'')
AND (M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID/0@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
AND M_HU_PI_Item_Product.ValidFrom <= ''@MovementDate@''
AND (M_HU_PI_Item_Product.ValidTo <= ''@MovementDate@'' OR M_HU_PI_Item_Product.ValidTo IS NULL)
AND EXISTS (SELECT 1
              FROM M_HU_PI_Item pii
                       INNER JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = pii.M_HU_PI_Version_ID
                       INNER JOIN M_HU_PI pi ON pi.M_HU_PI_ID = piv.M_HU_PI_ID
              WHERE pii.M_HU_PI_Item_ID = M_HU_PI_Item_Product.M_HU_PI_Item_ID
                AND pii.IsActive = ''Y''
                AND piv.IsActive = ''Y''
                AND piv.IsCurrent = ''Y''
                AND piv.HU_UnitType = ''TU''
                AND pi.IsActive = ''Y'')
',TO_TIMESTAMP('2025-10-01 11:42:59.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','M_HU_PI_Item_Product_For_Product_and_MovementDate','S',TO_TIMESTAMP('2025-10-01 11:42:59.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- 2025-10-01T11:43:39.903Z
UPDATE AD_Column SET AD_Val_Rule_ID=540745,Updated=TO_TIMESTAMP('2025-10-01 11:43:39.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=556506
;

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

-- Column: M_InventoryLine.M_LU_HU_PI_ID
-- 2025-10-01T12:00:58.362Z
UPDATE AD_Column SET AD_Val_Rule_ID=540746,Updated=TO_TIMESTAMP('2025-10-01 12:00:58.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591167
;

