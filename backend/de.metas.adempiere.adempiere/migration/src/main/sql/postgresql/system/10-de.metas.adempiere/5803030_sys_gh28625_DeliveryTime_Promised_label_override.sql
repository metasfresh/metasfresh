-- PO Date Provisioning: override label of DeliveryTime_Promised to "Purchase Transport Days"
-- in C_BPartner_Product context windows (BPartner window product tab + Product window BPartner tab).
--
-- The AD_Element for DeliveryTime_Promised (ID=1256) is NOT renamed globally — it keeps
-- "Zugesicherte Lieferzeit" for PP_Product_Planning and M_Product_PO contexts.
-- Instead, AD_Field.AD_Name_ID is set to the new PO_TransportDays element (584888)
-- only on these two C_BPartner_Product-context fields.
--
-- Affected fields:
--   AD_Field_ID=563649  Geschäftspartner window (123), tab "Produkt" (541077)
--   AD_Field_ID=565318  Produkt window (140), tab "Geschäftspartner" (562)

UPDATE AD_Field
SET AD_Name_ID = 584888,
    Name       = 'Einkauf Transporttage',
    Updated    = TO_TIMESTAMP('2026-05-18 14:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Field_ID IN (563649, 565318);

-- Propagate translations from element 584888 (Einkauf Transporttage / Purchase Transport Days)
-- to all AD_Field_Trl rows where AD_Name_ID=584888 — covers the two fields above as well as
-- the new PO_TransportDays field (780245) created in script 5803020.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584888);

-- =============================================================================
-- AD_UI_Element for Produkt window (140), tab Geschäftspartner (AD_Tab_ID=562)
--   Field 565318 = DeliveryTime_Promised relabelled to "Einkauf Transporttage".
--   No UI element existed before; add as advanced field.
--   Group: 1000021 (default), SeqNo=176 (after Wiederbeschaffung=175), SeqNoGrid=160
-- =============================================================================
INSERT INTO AD_UI_Element (
    AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy
) VALUES (
    0, 565318, 0, 562,
    1000021, 651701 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-05-18 14:10:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'Y',
    'Y', 'Y', 'N',
    'Einkauf Transporttage', 176, 160, 0,
    TO_TIMESTAMP('2026-05-18 14:10:01', 'YYYY-MM-DD HH24:MI:SS'), 100
);
