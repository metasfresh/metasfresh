-- Rows with UpdatedBy=-1 (no valid user) break saves that resolve the last editor as a user.
-- Set them to the System user (0) — the value the server now writes when no user is logged in; Updated is left unchanged.

SELECT backup_table('M_Product', '_updby_minus1');
UPDATE M_Product SET UpdatedBy=0 WHERE UpdatedBy=-1;

SELECT backup_table('M_Product_Trl', '_updby_minus1');
UPDATE M_Product_Trl SET UpdatedBy=0 WHERE UpdatedBy=-1;

SELECT backup_table('PP_Product_BOM', '_updby_minus1');
UPDATE PP_Product_BOM SET UpdatedBy=0 WHERE UpdatedBy=-1;

SELECT backup_table('PP_Product_BOM_Trl', '_updby_minus1');
UPDATE PP_Product_BOM_Trl SET UpdatedBy=0 WHERE UpdatedBy=-1;

SELECT backup_table('PP_Product_BOMLine', '_updby_minus1');
UPDATE PP_Product_BOMLine SET UpdatedBy=0 WHERE UpdatedBy=-1;

SELECT backup_table('PP_Product_BOMLine_Trl', '_updby_minus1');
UPDATE PP_Product_BOMLine_Trl SET UpdatedBy=0 WHERE UpdatedBy=-1;
