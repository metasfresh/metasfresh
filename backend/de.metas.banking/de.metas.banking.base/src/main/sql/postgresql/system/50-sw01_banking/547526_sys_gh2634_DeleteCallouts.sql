-- delete callouts for C_BankStatementLine
delete from AD_ColumnCallout
where AD_ColumnCallout_ID in (
540755,
540754,
540757,
540760,
540753,
540910,
540899);

-- delete callouts for C_BankStatementLine_Ref
delete from AD_ColumnCallout
where AD_ColumnCallout_ID in (
540762,
540763,
540764,
540765,
540766,
540767,
540768,
540769
);