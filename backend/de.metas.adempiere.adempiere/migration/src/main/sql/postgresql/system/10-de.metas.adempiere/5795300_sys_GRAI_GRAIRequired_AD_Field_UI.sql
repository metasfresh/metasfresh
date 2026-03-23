-- 2026-03-23
-- AD_Field on Kunde tab and AD_UI_Element for GRAIRequired

-- AD_Field: GRAIRequired on Kunde (Customer) tab
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine,
                      IsEncrypted, EntityType, AD_Field_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        541964, 592261 /*From ID Server*/, 'GRAI Pflicht', 'Y', 'N', 'N',
        'N', 'de.metas.handlingunits', 775480 /*From ID Server*/);

-- AD_UI_Element: GRAIRequired
-- Place it in the last UI element group used on this tab, with next SeqNo
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Field_ID, AD_UI_ElementGroup_ID, SeqNo, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           IsAdvancedField, EntityType, AD_UI_Element_ID)
SELECT 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
       775480 /*From ID Server*/,
       uie.AD_UI_ElementGroup_ID,
       COALESCE(MAX(uie2.SeqNo), 0) + 10,
       'Y', 'N', 'N',
       'N', 'de.metas.handlingunits', 648881 /*From ID Server*/
FROM AD_UI_Element uie
JOIN AD_Field f ON f.AD_Field_ID = uie.AD_Field_ID
LEFT JOIN AD_UI_Element uie2 ON uie2.AD_UI_ElementGroup_ID = uie.AD_UI_ElementGroup_ID AND uie2.IsActive = 'Y'
WHERE f.AD_Tab_ID = 541964
  AND f.IsActive = 'Y'
  AND uie.IsActive = 'Y'
GROUP BY uie.AD_UI_ElementGroup_ID
ORDER BY MAX(uie.SeqNo) DESC
LIMIT 1;
