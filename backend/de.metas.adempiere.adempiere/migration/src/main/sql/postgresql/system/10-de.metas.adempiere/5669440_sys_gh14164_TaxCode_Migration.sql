CREATE TABLE backup.BKP_C_Tax_20122022 AS SELECT * FROM c_tax;




UPDATE C_Tax t SET Value = x.VatCode
FROM C_VAT_Code x
WHERE x.c_tax_id = t.c_tax_id;