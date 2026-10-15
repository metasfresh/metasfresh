-- Boni refund config (tab 541106):
--   1) the description of the bonus product says that it is required if no product is given
--      (AD_Element 585512, AD_Column 593706, AD_Field 785602 and their translations)
--   2) RefundMode: the list value of the tiered mode was renamed from 'S' to 'T' in 5507110, but the default stayed 'S',
--      so a new refund line started with an empty mode. The default is the tiered mode 'T' (as 'S' was).

UPDATE AD_Element SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585512
;

UPDATE AD_Element_Trl SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585512 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585512 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585512 AND AD_Language='fr_CH'
;

UPDATE AD_Element_Trl SET Description='Product of the credit memo line. The tax and accounts of the credit memo follow this product. Required if no product is given.', Updated=TO_TIMESTAMP('2026-10-06 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585512 AND AD_Language='en_US'
;

UPDATE AD_Column SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593706
;

UPDATE AD_Column_Trl SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593706 AND AD_Language='de_DE'
;

UPDATE AD_Column_Trl SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593706 AND AD_Language='de_CH'
;

UPDATE AD_Column_Trl SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593706 AND AD_Language='fr_CH'
;

UPDATE AD_Column_Trl SET Description='Product of the credit memo line. The tax and accounts of the credit memo follow this product. Required if no product is given.', Updated=TO_TIMESTAMP('2026-10-06 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593706 AND AD_Language='en_US'
;

UPDATE AD_Field SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785602
;

UPDATE AD_Field_Trl SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785602 AND AD_Language='de_DE'
;

UPDATE AD_Field_Trl SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785602 AND AD_Language='de_CH'
;

UPDATE AD_Field_Trl SET Description='Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt. Pflicht, wenn kein Produkt angegeben ist.', Updated=TO_TIMESTAMP('2026-10-06 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785602 AND AD_Language='fr_CH'
;

UPDATE AD_Field_Trl SET Description='Product of the credit memo line. The tax and accounts of the credit memo follow this product. Required if no product is given.', Updated=TO_TIMESTAMP('2026-10-06 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785602 AND AD_Language='en_US'
;

UPDATE AD_Column SET DefaultValue='T', Updated=TO_TIMESTAMP('2026-10-06 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=560755 -- C_Flatrate_RefundConfig.RefundMode
;

/* DDL */ SELECT public.db_alter_table('C_Flatrate_RefundConfig','ALTER TABLE public.C_Flatrate_RefundConfig ALTER COLUMN RefundMode SET DEFAULT ''T''')
;
