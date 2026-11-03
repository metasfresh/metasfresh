CREATE UNIQUE INDEX c_uom_x12de355_uc
    ON c_uom (x12de355)
    WHERE x12de355 IN ('PCE', 'KGM');