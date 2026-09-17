-- Show the identifying columns in the ScriptedImportConversion gridview, on both the parent-config
-- child tab (548472) and the standalone window's root tab (548473). Previously only the endpoint
-- column was grid-displayed (the other fields were pinned off), so the list view showed a single
-- column. Flag Suchschlüssel / Skript-Kennung / Import-Benutzer for the grid and order the endpoint last.

-- ExternalSystemValue (Suchschlüssel)
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10, Updated=now(), UpdatedBy=100 WHERE AD_UI_Element_ID IN (637872, 637881);
-- ScriptIdentifier (Skript-Kennung)
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20, Updated=now(), UpdatedBy=100 WHERE AD_UI_Element_ID IN (637874, 637883);
-- AD_User_Import_ID (Import-Benutzer)
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30, Updated=now(), UpdatedBy=100 WHERE AD_UI_Element_ID IN (637875, 637884);
-- ExternalSystem_Endpoint_ID (Endpunkt) -- already grid-displayed; order it last
UPDATE AD_UI_Element SET SeqNoGrid=40, Updated=now(), UpdatedBy=100 WHERE AD_UI_Element_ID IN (652667, 648581);
