-- Tax Declaration: backup tables, wipe legacy data, remove obsolete Jasper AD entries

-- Backups (must run before any schema changes)
SELECT backup_table('c_taxdeclaration',     '_iter4_29628');
SELECT backup_table('c_taxdeclarationline', '_iter4_29628');
SELECT backup_table('c_taxdeclarationacct', '_iter4_29628');

-- Gap-2 data wipe: legacy rows are structurally incompatible with the new aggregation model
DELETE FROM C_TaxDeclarationAcct;
DELETE FROM C_TaxDeclarationLine;
DELETE FROM C_TaxDeclaration;

-- Remove obsolete Jasper report AD entries (Steueranmeldung + Steuerinfo)
-- Must remove all references first due to foreign key constraints
DELETE FROM AD_Table_Process WHERE AD_Process_ID IN (540054, 540466);
DELETE FROM AD_Tab           WHERE AD_Process_ID IN (540054, 540466);
DELETE FROM AD_TreeNodeMM    WHERE Node_ID = 540070;
DELETE FROM AD_Menu          WHERE AD_Menu_ID = 540070;
DELETE FROM AD_Process       WHERE AD_Process_ID IN (540054, 540466);
