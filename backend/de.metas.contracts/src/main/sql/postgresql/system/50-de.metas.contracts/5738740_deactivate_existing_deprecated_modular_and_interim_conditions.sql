UPDATE c_flatrate_conditions
SET isactive = 'N',
    updatedby = 99,
    updated = '2024-11-08'
WHERE type_conditions IN ('ModularContract', 'InterimInvoice')
;
