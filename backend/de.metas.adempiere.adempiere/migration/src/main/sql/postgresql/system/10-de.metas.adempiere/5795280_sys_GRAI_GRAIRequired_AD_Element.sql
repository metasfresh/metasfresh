-- 2026-03-23
-- AD_Element and translations for GRAIRequired

-- AD_Element: GRAIRequired
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, Name, PrintName, Description, EntityType, AD_Element_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        'GRAIRequired', 'GRAI Pflicht', 'GRAI Pflicht',
        'Bestimmt ob GRAIs bei der Kommissionierung erforderlich sind',
        'de.metas.handlingunits', 584694 /*From ID Server*/);

-- AD_Element_Trl: de_DE
INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Element_ID, Name, PrintName, Description, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        584694 /*From ID Server*/,
        'GRAI Pflicht', 'GRAI Pflicht',
        'Bestimmt ob GRAIs bei der Kommissionierung erforderlich sind', 'N');

-- AD_Element_Trl: de_CH
INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Element_ID, Name, PrintName, Description, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        584694 /*From ID Server*/,
        'GRAI Pflicht', 'GRAI Pflicht',
        'Bestimmt ob GRAIs bei der Kommissionierung erforderlich sind', 'N');

-- AD_Element_Trl: en_US
INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Element_ID, Name, PrintName, Description, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        584694 /*From ID Server*/,
        'GRAI Required', 'GRAI Required',
        'Determines whether GRAIs are required at picking completion', 'Y');
